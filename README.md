# Spring Boot Java Base Project
This project is a base project with the following utilities:
- RESTFul controller
- Spring JPA with Postgres
- WebClient as HTTP client

# Testing end to end
We set up an end-to-end testing using:
- SpringBootTests framework with a random port
- WireMock server as HTTP mock server
- RestAssured as HTTP client and assertion
- ObjectMapper as JSON normalizer and assertion

## How end to end tests works
The following is what happens:
- Spring boot starts the project in a random port
- A WireMock server is started
- By each test:
  - JSON mocks are injected in the WireMock server before each test run
  - A RESTFul call is made using RestAssured to the Spring Boot controller
  - An assert is performed against the status response code
  - The JSON response is extracted
  - The JSON response is normalized, alongside with its expected response
  - The actual and expected JSON are compared
  - JSON mocks are reset