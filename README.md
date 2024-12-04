
# Order Management API

## Overview

The **Order Management API** is a mini-project built using **Spring Boot**. It demonstrates an effective implementation of RESTful APIs for managing customers and their orders. The project includes key features such as logging with **Aspect-Oriented Programming (AOP)**, unit testing using **Mockito, MockMvc, and JUnit**, and interactive API documentation with **Swagger UI**.


![logo](https://media.licdn.com/dms/image/v2/D4D12AQFscCu_T0xB3A/article-cover_image-shrink_600_2000/article-cover_image-shrink_600_2000/0/1688794846091?e=2147483647&v=beta&t=UAzceqpsA588kvnVbHm01O35qL8lnK6eYus5DTDKR8M)
---

## Features

1. **Entity Relationship**:  
   - `Customer`: Represents customer information.  
   - `Order`: Represents order details.  
   - **Relationship**: One-to-Many relationship between `Customer` and `Order`.

2. **Endpoints**:  
   RESTful endpoints for CRUD operations:
   - **GET**: Fetch customers, orders, or specific details.
   - **POST**: Add new customers or orders.
   - **PUT**: Update existing customer or order details.
   - **DELETE**: Remove customers or orders.

3. **Database**:  
   - **MySQL**: Used for persistent storage of data.  

4. **Unit Testing**:  
   - **Mockito**: For mocking dependencies.  
   - **MockMvc**: For testing HTTP requests.  
   - **JUnit**: For writing and running test cases.

5. **AOP Logging**:  
   - Logs API hits with **execution time** and **timestamp** using AOP for better traceability and performance analysis.
   - All the logs are stored in the **logs.txt** file in the logs package.

6. **Swagger UI**:  
   - Provides an interactive API documentation interface for easy exploration and testing of the endpoints.

---

## Technologies Used

- **Spring Boot**  
- **MySQL**  
- **JUnit**, **Mockito**, **MockMvc**  
- **AOP** (Aspect-Oriented Programming)  
- **Swagger UI**  

---

## Getting Started

### Prerequisites
- **Java** (JDK **17** or higher)
- **Maven** (for dependency management)
- **MySQL** (for database)

### Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/Mainakcris7/order_management_api.git
   ```
2. Navigate to the project directory:
   ```bash
   cd order_management_api
   ```
3. Configure the database connection:
   - Update the `application.properties` file in the `src/main/resources` directory with your MySQL database credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/<db_name>
     spring.datasource.username=<your_username>
     spring.datasource.password=<your_password>
     ```

4. Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. Access the Swagger UI documentation at:
   ```
   http://localhost:8080/docs.html
   ```

---

## API Endpoints

### Customer API Endpoints
1. **GET** `/customers`: Fetch all customers.  
2. **GET** `/customers/{id}`: Fetch a customer by ID.  
3. **GET** `/customers/name/{name}`: Fetch customers whose names contain the specified string.  
4. **GET** `/customers/email/{email}`: Fetch a customer by email.  
5. **PUT** `/customers`: Update a customer and append new orders (specify the ID).  
6. **POST** `/customers`: Save a new customer with orders.  
7. **DELETE** `/customers/{id}`: Delete a customer by ID.  

### Order API Endpoints

1. **GET** `/orders`: Fetch all orders.  
2. **GET** `/orders/{id}`: Fetch an order by ID.  
3. **GET** `/orders/name/{name}`: Fetch orders whose names contain the specified string.  
4. **GET** `/orders/price/{price}`: Fetch orders with the exact specified price.  
5. **GET** `/orders/price`:  
   - Fetch orders with flexible price filtering:  
     - **gte**: Greater than or equal to the specified price.  
     - **lte**: Less than or equal to the specified price.  
     - **gte & lte**: Between the specified price range.  
6. **GET** `/orders/price/between/{minPrice}/{maxPrice}`: Fetch orders between the specified price range.  
7. **GET** `/orders/ordered_at/{orderedAt}`: Fetch orders placed at a specific datetime.  
8. **GET** `/orders/ordered_between/{before}/{after}`: Fetch orders placed between two specified timestamps.  
9. **GET** `/orders/ordered_at`:  
   - Fetch orders with flexible datetime filtering:  
     - **before**: Orders placed before the specified datetime.  
     - **after**: Orders placed after the specified datetime.  
     - **before & after**: Orders placed between the specified timestamps.  
10. **PUT** `/orders`: Update an existing order (specify the ID).  
11. **POST** `/orders`: Save a new order.  
12. **DELETE** `/orders/{id}`: Delete an order by ID.  

---

## Unit Testing

Unit tests ensure the reliability of the API. Run the test cases using:
```bash
mvn test
```

---

## Contributing

Contributions are welcome! Feel free to raise issues or submit pull requests.

---

## Contact

Feel free to drop an email at [mainakcr72002@gmail.com](mainakcr72002@gmail.com) for any queries.