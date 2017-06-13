# Spring Boot API

This is a simple API showing how to properly create and document an API. 

## Tech Stack

This project is built with:

- Java 8 -> Language
- Spring Boot -> Main Framework
- MySql -> Main Database
- H2(In Memory DB) -> Test Database
- Logback -> Logging Framework
- ElasticSearch -> NoSQL Database used to store Logs asynchronously

## Requirements

To run this project you'll need to download the following:

- [Java](https://www.java.com/en/download/)
- [Gradle](https://gradle.org/install)
- [Docker](https://docs.docker.com/engine/installation/)

And if you want to run it stand alone you'll need to download [MySQL](https://www.mysql.com/downloads/) and configure the `application.properties` configuration file to point to the right db configuration(port, host, dbName, dbUser and dbPass)

## Running the Project With Docker

After having all the requirements, the first step is to generate the docker Image. To generate the docker image, go to the root of the project and run 

- `$ ./gradlew buildDocker`

Then you'll need to run the `docker.compose.yml` file that's on the directory `src/main/docker` through the following command: 

- `$ cd src/main/docker/`
- `$ docker-compose up`

After that you'll be able to start.

### Testing

By default, gradle run all tests when you build it. But you can use the following command to run it: 

- `$ gradle test`

You can also use [JUnit](http://junit.org/junit4/) if you prefer. 

### Api Documentation (Swagger)

- If you access the root URL (` http://localhost:8080/ `) you'll be redirected to the API Documentation
- To access the api documentation directly, run the application and access : ` http://localhost:8080/swagger-ui.html `

- Through this documentation, you'll be able to make requests, as you'll see on the next topic.

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


### Accessing the Logs(Elasticsearch)

Through an Async Appender used on the spring Logback solution(Appender better defined on the file `src/main/resources/logback.xml`), we send the logs to Elasticsearch.The logs coming from the main api are defined with the index name `logs-YYYY-MM-DD`.
By default(defined on the docker-compose.yml file), we can see the elasticsearch through the following links:

- See all indexes available: `http://localhost:9200/_cat/indices?v`

- See all record from the main api logs : `http://localhost:9200/logs-2017-06-13/_search?pretty=true&q=*:*&sort=@timestamp:desc`. You can also sort and filter through whatever field you have available. For better understand Elasticsearch take a look at the [Elasticsearch Documentation](https://www.elastic.co/guide/en/elasticsearch/reference/5.0/index.html).  Don't forget to change the `YYYY-MM-DD` on the Url path according to the current day.


## Commands to run without Docker (and through the terminal)


- To build the project run from the root folder of the project the following: `$ gradle build`
- To run the project do : `$ java -jar build/libs/user-crud-0.1.0.jar`. Be aware that the jar name is defined inside the gradle.build script and might be able to change.
- To run the tests: `$ gradle test`

## Access the api

After having the project running, you can access the api through [curl](https://curl.haxx.se/download.html) or any other http handler you prefer. 

It runs by default on the `http://localhost:8080/`.


## Things to improve/do

- Improve the logs: Instead of only having Elasticsearch populated with the logs, we could use the ELK(Elasticsearch, Logstash and Kibana) solution to provide a better visualization of logs, and perhaps metrics.

- Populate Elasticsearch with posts as well and then point the user to get data from Elasticsearch and use MySql only to Write. I believe this approach improves the performance quite a lot.

- Use UUID instead of Long as the main ID of all models.
