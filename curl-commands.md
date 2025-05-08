# Curl Commands for User API

This file contains curl commands for all endpoints in the User API.

## Get All Users
```bash
curl -X GET http://localhost:8080/api/users -H "Accept: application/json"
```

## Get User by ID
```bash
curl -X GET http://localhost:8080/api/users/1 -H "Accept: application/json"
```

## Create a New User
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

## Update an Existing User
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

## Delete a User
```bash
curl -X DELETE http://localhost:8080/api/users/1
```