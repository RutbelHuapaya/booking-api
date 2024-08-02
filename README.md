# Booking API

## Descripción

API REST que permite reservar una casa. La API valida el request, verifica el código de descuento a través de otra API y guarda los datos en una base de datos PostgreSQL.

## Tecnologías

- Java 17
- Spring Boot 3.2.8
- PostgreSQL
- Docker Compose

## Ejecución

1. Clonar el repositorio:

    ```sh
    git clone https://github.com/RutbelHuapaya/booking-api.git
    cd booking-api
    ```

2. Construir y ejecutar el proyecto con Docker Compose:

    ```sh
    mvn clean package
    docker-compose up --build
    ```

3. La API estará disponible en `http://localhost:8080`.

## Endpoints

### Reserva Casa

- **URL:** `/book`
- **Método:** `POST`
- **Request Body:**

    ```json
    {
        "id": "14564088-4",
        "name": "Gonzalo",
        "lastname": "Pérez",
        "age": 33,
        "phoneNumber": "56988123222",
        "startDate": "2024-07-22",
        "endDate": "2024-07-23",
        "houseId": "213132",
        "discountCode": "D0542A23"
    }
    ```

- **Response Body:**

    ```json
    {
        "code": 200,
        "message": "Book Accepted"
    }
    ```

- **Errores:**

    ```json
    {
        "statusCode": 400,
        "error": "Bad Request",
        "message": "required property 'houseId'"
    }
    ```

## Pruebas

Las pruebas unitarias están incluidas en el directorio `src/test/java/com/bideafactory`.

Para ejecutar las pruebas:

```sh
./mvnw test
