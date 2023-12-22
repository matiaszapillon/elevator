
# Elevator challenge

## How to start up the project:
* Install JDK 17 (Define JAVA_HOME + Add bin folder to the path)
* Install Maven 3.6.3+ (Add bin folder to the path)
* go to root folder and run the following command: **mvn spring-boot:run**

## How to use it
Basically in order to use the elevators there are 2 available endpoints (PUT methods). Those are:
**{url}/api/v1/publicelevators/{id}** and **{url}/api/v1/freightelevators/{id}**
Given that the database is refreshed and initialized everytime we restart the application, the elevators ids are 1 for both cases. 
[In case we want to confirm those values we can do a GET to {url}/api/v1/publicelevators and we will get all the available elevators in the building.]

So, if we wanted to use the public elevator to the floor 50 we should send a request indicating the floor, the weight and, since this floor is a restricted one, the keycard (harcoded to 12345)
json body example:
{
"weight":"70",
"floor":"50",
"keycode":"12345"
}

## Database (H2):
http://localhost:8080/h2-console

#### Credentials:
- jdbc url: jdbc:h2:mem:elevatordb
- username: sa
- password: password
