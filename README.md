# storytel-backend
Backend coding challenge


## Running the project

- Make sure you have Java 8 or higher and Maven installed on your machine.
- Run the project using `mvn spring-boot:run`
- Alternatively you can run the project in your IDE by right-clicking on the `MessageboardApplication` 
  class and selecting "Run as Java Application".
- Once the application is running, you can test the API endpoints using a tool like Postman or 
  cURL.

### Create a user

To be able to post messages, you will need to create a user for yourself.
You can do so by sending a POST request to the endpoint  http://localhost:8080/api/messages with 
a JSON payload like this:

```json
{
  "name": "YourName"
}
```

Remember the id it gives you, you will need it to do further operations!
If you forget it, you can always check it by calling the GET endpoint 
http://localhost:8080/api/authors.

Once you have a username, you're good to go and explore the messages, post your own, and so on :)

### Reading messages

### Posting and editing

#### Request format

To do this kind of requests that necessitate an author, the needed payload is a `MessageRequest`,
which contains a `Message` and an `Author`.

#### Posting a new message

To create a new message, you can send a POST message request to http://localhost:8080/api/messages with a JSON payload like this:

```json
{
  "author": {
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
  "author": {
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
    "author": {
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

To delete a message that you've posted, you only need to know its id, and pass your author data 
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