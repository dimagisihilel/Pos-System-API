# POS System API

 **API Documentation -** [Click here to view ](https://documenter.getpostman.com/view/36189383/2sAXjGdEjC)



## Overview

The POS System API provides backend functionality for a Point of Sale (POS) system, enabling operations such as customer management, item management, order processing, and more. Developed using Jakarta EE with MySQL as the database and AJAX for communication, this API follows a layered architecture for modularity and maintainability.


## Tech Stack

- **Backend:** Jakarta EE
- **Database:** MySQL
- **Communication:** AJAX 
- **Database Configuration:** JNDI
- **Logging:** Logback framework


## Architecture

The application follows a layered architecture:

- **Controller Layer:** Handles HTTP requests and directs them to the appropriate service layer.
- **Service Layer:** Contains business logic and orchestrates data flow between the controller and DAO layers.
- **DAO Layer:** Directly interacts with the database, executing SQL queries and returning results to the service layer.
- **Model Layer:** Represents the data structures used throughout the application.


## Authentication

This API does not currently implement any authentication or authorization mechanisms. All endpoints are publicly accessible.


## Logging

Logging is implemented with different levels:

- **INFO:** General application workflow information.
- **DEBUG:** Detailed debugging information.
- **ERROR:** Error messages for encountered issues.


## API Documentation

This collection includes detailed documentation for each endpoint, including:

- **Endpoint URL**: The URL to which requests should be sent.

- **Method**: The HTTP method used for the request (GET, POST, PUT, DELETE).

- **Description**: A brief explanation of what the endpoint does.

- **Request Headers**: Required headers for the request, such as `Content-Type`.

- **Request Parameters**: Path, query, and body parameters expected by the endpoint.

- **Example Requests**: Sample curl commands and JSON payloads for testing.

- **Response**: Expected response status, body, and content type.

- **Error Handling**: Possible errors and their corresponding status codes.

- **Notes**: Additional information relevant to the endpoint, such as idempotency, constraints, or specific behavior.


For a detailed list of all endpoints and their descriptions, please refer to the Postman Collection .
Each endpoint includes clear documentation with sample requests and expected responses to facilitate easy integration and testing.
[API Documentation From Postman ](https://documenter.getpostman.com/view/36189383/2sAXjGdEjC).

## Example Requests

### Save Customer

**Endpoint:**  `POST http://localhost:8080/possystem/customer`

**Description:** Adds a new customer to the POS system.

**Request Body:**


```
{
    "customerid": "C008",
    "name": "John Doe",
    "address": "123 Elm Street",
    "contact": "0712345678"
}
```
**Response:**

Status: `200 OK`

Body: `Customer saved successfully`

**Error Handling:**

`400 Bad Request` : If any required fields are missing or invalid.

### Get All Items

**Endpoint:**` GET http://localhost:8080/possystem/item?all=true`

**Description:** Retrieves a list of all items in the POS system.

**Response:**

Status: `200 OK`

Body:

```
[
    {
        "itemId": "I001",
        "description": "Widget",
        "unitPrice": 50.00,
        "qtyOnHand": 100
    },
    {
        "itemId": "I002",
        "description": "Gadget",
        "unitPrice": 75.00,
        "qtyOnHand": 150
    }
]
```

**Error Handling:**

`500 Internal Server Error` : If an unexpected error occurs.


## How to Use This Collection

### Setting Up

- Ensure that your Jakarta EE server is running and connected to the MySQL database.
- Verify that the database schema is correctly set up as per the provided scripts.

### Testing Endpoints

- Use tools like **Postman** or **curl** to send requests to the API endpoints.
- For detailed examples and requests, refer to the [Postman Collection](https://documenter.getpostman.com/view/36189383/2sAXjGdEjC).

### Handling Responses

- Review the **JSON responses** or **error messages** returned by the API to verify correct behavior.
- Ensure you are checking both successful responses and error codes to understand the application's behavior.

### Debugging

- **Check the logs** for detailed information if you encounter any issues.
- Logs are managed using the **Logback framework**, and you can find them in the `app.log` file or configured log destinations.

## Best Practices

### Data Validation

- Ensure that all required fields are present and valid before making API requests.
- Validate input data on both client and server sides to prevent malformed data from being processed.

### Error Handling

- Implement **client-side error handling** to manage API errors gracefully.
- Provide meaningful error messages to users to help them understand and rectify issues.

### Database Integrity

- Maintain **data integrity** by following the foreign key constraints and cascading rules defined in the database schema.
- Regularly review and test database operations to ensure referential integrity and consistency.

## Submission

### GitHub Repository

- The completed codebase is available in the [GitHub repository](https://github.com/dimagisihilel/Pos-System-API), including the entire API code, database scripts, and this documentation.

### README

- This `README.md` file provides an overview of the project, setup instructions, and links to the API documentation.
- For more detailed information on API endpoints, refer to the documentation provided in the repository and the Postman Collection.
