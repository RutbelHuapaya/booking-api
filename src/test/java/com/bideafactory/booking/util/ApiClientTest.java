package com.bideafactory.booking.util;

import com.bideafactory.booking.dto.DtoInDiscount;
import com.bideafactory.booking.dto.DtoOutDiscount;
import com.bideafactory.booking.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiClientTest {

    private static final String DISCOUNT_API_URL = "https://sbv2bumkomidlxwffpgbh4k6jm0ydskh.lambda-url.us-east-1.on.aws/";

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RetryTemplate retryTemplate;

    @InjectMocks
    private ApiClient apiClient;

    @BeforeEach
    void setUp() {
        apiClient.discountApiUrl = DISCOUNT_API_URL;
    }

    @Test
    void testValidateDiscount_Success() {

        DtoInDiscount dtoInDiscount = new DtoInDiscount();
        DtoOutDiscount dtoOutDiscount = new DtoOutDiscount();
        dtoOutDiscount.setStatus(true);

        ResponseEntity<DtoOutDiscount> responseEntity = new ResponseEntity<>(dtoOutDiscount, HttpStatus.OK);

        when(retryTemplate.execute(any(RetryCallback.class))).thenAnswer(invocation -> {
            RetryCallback<Boolean, Exception> retryCallback = invocation.getArgument(0);
            RetryContext retryContext = mock(RetryContext.class);
            when(retryContext.getRetryCount()).thenReturn(0);
            return retryCallback.doWithRetry(retryContext);
        });

        when(restTemplate.exchange(
                eq(DISCOUNT_API_URL),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(DtoOutDiscount.class)
        )).thenReturn(responseEntity);

        boolean result = apiClient.validateDiscount(dtoInDiscount);

        assertTrue(result);
        verify(restTemplate, times(1)).exchange(
                eq(DISCOUNT_API_URL),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(DtoOutDiscount.class)
        );
    }

    @Test
    void testValidateDiscount_Failure() {
        DtoInDiscount dtoInDiscount = new DtoInDiscount();

        when(retryTemplate.execute(any(RetryCallback.class))).thenAnswer(invocation -> {
            RetryCallback<Boolean, Exception> retryCallback = invocation.getArgument(0);
            RetryContext retryContext = mock(RetryContext.class);
            when(retryContext.getRetryCount()).thenReturn(0);
            return retryCallback.doWithRetry(retryContext);
        });

        when(restTemplate.exchange(
                eq(DISCOUNT_API_URL),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(DtoOutDiscount.class)
        )).thenThrow(new RuntimeException("API Failure"));

        BusinessException exception = assertThrows(BusinessException.class, () -> apiClient.validateDiscount(dtoInDiscount));

        assertEquals(500, exception.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getError());
        assertEquals("Discount API not responding", exception.getMessage());

        verify(restTemplate, times(1)).exchange(
                eq(DISCOUNT_API_URL),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(DtoOutDiscount.class)
        );
    }
}