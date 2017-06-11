# Spring Boot API

This is a simple API showing how to properly create and document an API 

## Requirements

To run this project you'll need to download the following:

- [Java](https://www.java.com/en/download/)
- [Gradle](https://gradle.org/install)
- [Docker](https://docs.docker.com/engine/installation/)

## Commands to run without Docker (and through the terminal)


- To build the project run from the root folder of the project the following: `$ gradle build`
- To run the project do : `$ java -jar build/libs/user-crud-0.1.0.jar`. Be aware that the jar name is defined inside the gradle.build script and might be able to change.
- To run the tests: `$ gradle test`

## Access the api

After having the project running, you can access the api through [curl](https://curl.haxx.se/download.html) or any other http handler you prefer. 

It runs by default on the `http://localhost:8080/`.

### Endpoints

Currently it contains a basic crud of a User.

#### Adding a User (POST http://localhost:8080/users).

You can add a user doing a Post, here's an example using curl :

- `curl -H "Content-Type: application/json" -X POST -d '{"name":"First User","role":"admin"}' http://localhost:8080/users`
- `curl -H "Content-Type: application/json" -X POST -d '{"name":"Second User","role":"admin"}' http://localhost:8080/users`
- `curl -H "Content-Type: application/json" -X POST -d '{"name":"Third User","role":"marketing"}' http://localhost:8080/users`
- `curl -H "Content-Type: application/json" -X POST -d '{"name":"Fourth User","role":"marketing"}' http://localhost:8080/users`

#### Getting all Users (GET http://localhost:8080/users).

You can get all users doing a Get. It also works with multiple query parameters(examples: `/users?role=admin`, `/users?role=admin,marketing`, `/users?id=1,4&role=admin,marketing`). 

Here are some examples using curl :

- `curl -X GET http://localhost:8080/users`
- `curl -X GET http://localhost:8080/users?role=admin`
- `curl -X GET http://localhost:8080/users?role=admin,marketing`
- `curl -X GET http://localhost:8080/users?id=1,3,4&role=admin,marketing`

#### Updating a User (PUT http://localhost:8080/users/ID_OF_THE_USER).

Here's an example on how to change the property `name` of the user with the `id=1`:

- `curl -H "Content-Type: application/json" -X PUT -d '{"name":"First User"}' http://localhost:8080/users/1`

#### Deleting a User (PUT http://localhost:8080/users/ID_OF_THE_USER).

Here's an example on how to delete the user with the `id=4`:

- `curl -X DELETE http://localhost:8080/users/4`
