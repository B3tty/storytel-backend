# storytel-backend
Backend coding challenge


## Running the project

- Make sure you have Java 8 or higher and Maven installed on your machine.
- Run the project using `mvn spring-boot:run`
- Alternatively you can run the project in your IDE by right-clicking on the `MessageController` 
  class and selecting "Run as Java Application".
- Once the application is running, you can test the API endpoints using a tool like Postman or 
  cURL. For example, to create a new message, you can send a POST messageRequest to 
  http://localhost:8080/api/messages with a JSON payload like this:

```json
{
  "author": "Alice",
  "content": "Hello world!"
}
```

You should receive a JSON response with the newly created message, including its ID and 
   timestamp:

```json
{
  "id": 1163404943818051014,
  "author": "Alice",
  "content": "Hello world!",
  "timestamp": "2023-03-08T08:00:00.000+0000"
}
```