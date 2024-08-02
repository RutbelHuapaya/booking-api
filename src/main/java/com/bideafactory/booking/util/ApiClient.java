package com.bideafactory.booking.util;

import com.bideafactory.booking.dto.DtoInDiscount;
import com.bideafactory.booking.dto.DtoOutDiscount;
import com.bideafactory.booking.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ApiClient {

    private static final Logger LOG = LoggerFactory.getLogger(ApiClient.class);

    private final RetryTemplate retryTemplate;

    private final RestTemplate restTemplate;

    @Value("${spring.discount.validation.service.url}")
    String discountApiUrl;

    @Retryable()
    public boolean validateDiscount(DtoInDiscount dtoInDiscount) {
        return retryTemplate.execute(retryContext -> {
            try {
                LOG.info("Attempt {} for discount validation", retryContext.getRetryCount() + 1);
                HttpEntity<DtoInDiscount> request = new HttpEntity<>(dtoInDiscount);
                ResponseEntity<DtoOutDiscount> response = restTemplate.exchange(
                        discountApiUrl, HttpMethod.POST, request, DtoOutDiscount.class
                );

                LOG.debug("Received response: {}", response.getBody());

                if (response.getBody() != null) {
                    return response.getBody().isStatus();
                } else {
                    LOG.warn("Received null response body");
                    return false;
                }

            } catch (Exception e) {
                LOG.error("Discount API request failed", e);
                throw new BusinessException(500, HttpStatus.INTERNAL_SERVER_ERROR, "Discount API not responding");
            }
        });

    }

}
