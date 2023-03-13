# Message Board API Documentation

This API provides the functionality to manage a public message board, allowing users to create, 
read, update, and delete messages.

## Overview

### Architecture

This RESTful API service is built using the Spring Boot framework, which provides a lightweight 
and easy-to-use platform for building web applications in Java.

The service follows a layered architecture pattern, with the following layers:

- Controller layer: This layer receives incoming HTTP requests and sends back HTTP responses. It 
handles the input validation, and calls the appropriate service layer methods to process the requests.

- Service layer: This layer contains the business logic of the application. It handles the 
database interactions through a repository interface, and performs any necessary data processing and transformations.

- Repository layer: This layer is responsible for the data access and persistence. It uses a 
CrudRepository interface provided by Spring Data JPA to perform the basic CRUD operations on a database.

- Model layer: This layer contains the data model and entities used by the application. It 
defines the data structures that are stored and manipulated by the service, such as messages and 
  users.

### Endpoints
The following endpoints are available in this API:

- GET /api/messages: Returns a list of all messages stored in the database.

- POST /api/messages: Creates a new message in the database. The request body should contain the 
user and text fields.

- PUT /api/messages/{id}: Updates an existing message with the specified id in the database. The 
request body should contain the user and text fields.

- DELETE /api/messages/{id}: Deletes the message with the specified id from the database. The 
request body should contain the user.

- GET /api/users: Returns a list of all users stored in the database.

- POST /api/users: Creates a new user in the database. The request body should contain the
username.

### Authentication and Authorization

The user is passed in the body of the request and compared to the initial user who wrote the 
target message to edit or delete. At the moment it is not perfect before the person making the 
request is passing the user in the body.

### Improvements
Here are some suggestions for improving the Message Board API service:

- Add authentication and authorization: To restrict access to the API and provide better 
  security, we can add an authentication and authorization mechanism. For example, we can use 
  Spring  Security to implement OAuth2 authentication with JWT tokens.

- Implement pagination: If the number of messages in the database grows too large, it may become 
  inefficient to return all messages in a single response. We can implement pagination to limit the number of messages returned in each response and allow clients to navigate through the pages.

- Improve error handling: Currently, the API returns generic error responses for all types of 
  errors. We can improve the error handling by providing more specific error messages and status codes for different types of errors. For example, we can return a 400 Bad Request status code with a message explaining the validation errors if the input data is invalid.

- Deployment: Currently, this project is only configured to be run locally with Maven. It would 
  need some edits to be deployed in the cloud (for example with AWS or GCP). Some specific 
  configurations would be needed, for example.

- Health checks: If we deploy this service, it would be interesting to have some health checks 
  available to get information on the service status

- Logging and monitoring

## Running the project

- Make sure you have Java 8 or higher and Maven installed on your machine.
- Run the project using `mvn spring-boot:run`
- Alternatively you can run the project in your IDE by right-clicking on the `MessageboardApplication` 
  class and selecting "Run as Java Application".
- Once the application is running, you can test the API endpoints using a tool like Postman or 
  cURL.

## Calling the APIs

### User API

To be able to post messages, you will need to create a user for yourself.
You can do so by sending a POST request to the endpoint  http://localhost:8080/api/users with 
a JSON payload like this:

```json
{
  "name": "YourName"
}
```

Remember the id it gives you, you will need it to do further operations!
If you forget it, you can always check it by calling the GET endpoint 
http://localhost:8080/api/users.

Once you have a username, you're good to go and explore the messages, post your own, and so on :)

### Message APIs

#### Reading messages

To read all the messages existing on the message board, simply send a GET request to the 
endpoint http://localhost:8080/api/messages.

The service gets started with welcome messages from the Admin, and you should expect a response 
that looks like this:

```json
[
    {
        "id": 1,
        "content": "Welcome to the Forum!",
        "user": {
            "id": 1,
            "name": "Admin"
        },
        "createdAt": "2023-01-19T14:43:33.792944",
        "updatedAt": "2023-01-19T14:43:33.792944"
    },
    {
        "id": 2,
        "content": "You can write about anything you want, including writing a short presentation about you. Don't be shy!",
        "user": {
            "id": 1,
            "name": "Admin"
        },
        "createdAt": "2023-01-19T14:43:33.792944",
        "updatedAt": "2023-01-19T14:43:33.792944"
    }
]
```

#### Request format for posting and editing

To do this kind of requests that necessitate an user, the needed payload is a `MessageRequest`,
which contains a `Message` and an `User`.

#### Posting a new message

To create a new message, you can send a POST message request to http://localhost:8080/api/messages with a JSON payload like this:

```json
{
  "user": {
    "id": 2,
    "name": "YourName"
  },
  "message":{
    "content": "Hello world!"
  }
}
```

You should receive a JSON response with the newly created message, including its ID and 
   timestamp:

```json
{
  "id": 4,
  "content": "Hello world!",
  "user": {
    "id": 2,
    "name": "YourName"
  },
  "createdAt": "2023-03-11T16:38:51.426542",
  "updatedAt": "2023-03-11T16:38:51.426569"
}
```

#### Editing one of your messages

To edit a message that you've posted, you can send a PUT message request to 
http://localhost:8080/api/messages/{id} with a JSON payload like this:

```json
{
    "user": {
        "id": 2,
        "name": "YourName"
    },
    "message":{
    "content": "Bye bye"
    }
}
```

You should receive a JSON response with the newly updated message, including its ID and timestamp.

#### Deleting one of your messages

To delete a message that you've posted, you only need to know its id, and pass your user data 
in the body.
For example: you can send a DEL message request to http://localhost:8080/api/messages/{id} with 
a JSON payload like this:

```json
{
  "id": 2,
  "name": "YourName"
}
```

You should receive an empty response with a 200 OK code.