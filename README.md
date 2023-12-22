


How to start up the project:
go to root folder and run the following command:
mvn spring-boot:run


Move public elevator:
{url}/api/v1/publicelevators/{id}

json body example:
{
    "weight":"70",
    "floor":"-1",
    "keycode":"12345"
}

Move freight elevator:
{url}/api/v1/freightelevators/{id}

json body example:
{
"weight":"70",
"floor":"-1"
}