# Alten Hotel Challenge

# Developed by: Gustavo G. Castro (gustavogcastro1992@gmail.com)

# Done:
	- [x] Main microservice
	- [ ] Unit tests
	- [ ] Implement additional features (caching, resilience, etc)
	
# How to compile:

First you need Java SDK 8 installed

1. Using the terminal go to the project directory
2. Use the command 'mvnw clean install' 
3. Run the microservice using 'java -jar .target/alten-challenge-0.0.1-SNAPSHOT.jar'

# How to run:

Use the command 'mvnw spring-boot:run' in the project directory.

# How to use:

This project uses the API First concept. It generates the necessary models and controllers from a YAML file in the OpenAPI standard, located at:

project_dir/src/main/resources/swagger/server/swagger.yaml

And it also generates the documentation automatically, which after the project is executed, can be accessed at:

http://localhost:8080/swagger-ui.html

# Comments:

- To control the situation where two people book a room at the same time, the property 'token' has been created for the room model, and it can be obtained by making the request for the room (GET /rooms). This same token must be used to create the reservation (POST /rooms/{id}/reservations), if the reservation token does not match the room token, the reservation cannot be done.
