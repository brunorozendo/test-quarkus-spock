# User CRUD Application

A simple Quarkus application that provides CRUD operations for a User entity.

## Technologies Used

- Quarkus 3.21.3
- Hibernate ORM with Panache
- RESTEasy Reactive
- H2 Database
- Gradle
- Spock Framework for testing

## Project Structure

```
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           ├── Application.java
│   │           ├── controller
│   │           │   └── UserController.java
│   │           ├── model
│   │           │   └── User.java
│   │           ├── repository
│   │           │   └── UserRepository.java
│   │           └── service
│   │               └── UserService.java
│   └── resources
│       └── application.properties
└── test
    └── groovy
        └── com
            └── example
                ├── controller
                │   └── UserControllerSpec.groovy
                └── service
                    └── UserServiceSpec.groovy
```

## Building the Application

```bash
./gradlew build
```

## Running the Application

```bash
./gradlew quarkusDev
```

The application will start on port 8080.

## API Endpoints

- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create a new user
- `PUT /api/users/{id}` - Update an existing user
- `DELETE /api/users/{id}` - Delete a user

## Curl Commands

### Get All Users
```bash
curl -X GET http://localhost:8080/api/users -H "Accept: application/json"
```

Example response:
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "1234567890"
  },
  {
    "id": 2,
    "name": "Jane Doe",
    "email": "jane@example.com",
    "phone": "0987654321"
  }
]
```

### Get User by ID
```bash
curl -X GET http://localhost:8080/api/users/1 -H "Accept: application/json"
```

Example response:
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "1234567890"
}
```

### Create a New User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "1234567890"
  }'
```

Example response:
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "1234567890"
}
```

### Update an Existing User
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "name": "John Updated",
    "email": "john.updated@example.com",
    "phone": "9876543210"
  }'
```

Example response:
```json
{
  "id": 1,
  "name": "John Updated",
  "email": "john.updated@example.com",
  "phone": "9876543210"
}
```

### Delete a User
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

This endpoint returns no content (HTTP 204) on successful deletion.

## H2 Console

The H2 console is enabled and can be accessed at:

```
http://localhost:8080/h2-console
```

Use the following credentials:
- JDBC URL: `jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1`
- Username: `sa`
- Password: `password`

## Running Tests

```bash
./gradlew test
```

This will run both the JUnit tests and Spock tests.

## Sample User JSON

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "1234567890"
}
```
