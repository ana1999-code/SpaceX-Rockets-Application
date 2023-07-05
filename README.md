# SpaceX Rockets App
This is a web application that allows users to view and interact with SpaceX rockets data. The application has two profiles: sx-rockets-app and rockets-app, each serving different functionalities.

## Getting Started
To run the application locally, you need to have Docker installed on your machine.

1. Clone the repository:
```bash
git clone https://github.com/ana1999-code/SpaceX-Rockets-Application.git
cd SpaceX-Rockets-Application
```
2. Run the following command to start the application and PostgreSQL database using Docker Compose:
```bash
docker-compose up -d
```
3. Apply database migrations using Flyway:
```bash
mvn flyway:migrate -Psx-rockets-app
```
Once the application and database are up and running, you can access the endpoints through your web browser or API testing tool like Postman or Swagger.
### Profile: sx-rocket-app
_Run the following command in terminal:_
```bash
mvn clean spring-boot:run -Psx-rocket-app
 ```
This profile uses WebClient to consume the /rockets endpoint from https://api.spacexdata.com/v3/rockets. You can access the following endpoints:

- __GET /rockets__: Get a list of all rockets from the SpaceX API.
_Request Parameters:_
__saveToDatabase (optional)__: Set to true if you want to store the fetched rockets in the local database.

- __GET /rockets/{rocketId}__: Get a specific rocket by ID from the SpaceX API.

### Profile: rocket-app (Default Profile)
_Run the following command in terminal:_  
```bash
mvn clean spring-boot:run -Procket-app
 ```

This profile retrieves rockets from the local database. It provides the following endpoints:

- __GET /rockets__: Get a list of all rockets from the local database.
_Query Parameters:_
__sort__: Sort the rockets based on fields (e.g., costPerLaunch, height, diameter, firstFlight).
__filter__: Filter the rockets based on fields (e.g., costPerLaunch, height, diameter, firstFlight).

- __GET /rockets/{rocketId}__: Get a specific rocket by ID from the local database.
- __POST /rockets__: Add a new rocket to the local database.
_Request Body:_ Rocket data in JSON format.
- __DELETE /rockets/{rocketId}__: Delete a rocket by ID from the local database.

## Technology Stack
- Java
- Spring Boot
- WebClient (for sx-rockets-app)
- PostgreSQL
- Docker
- Maven
- Flyway
- Swagger

## API Documentation
Swagger documentation is available at /swagger-ui.html for both profiles. You can explore the endpoints using Swagger UI.

## Acknowledgments
Special thanks to Elon Musk for making space exploration a reality.
