# Ticket Pricing System

This project is a ticket pricing system implemented in Java using Spring Boot, Maven, and JUnit 5. It consists of three projects:

- **baseprice**: Returns the base price of a ticket, which is set to 10 EUR.
- **taxrate**: Returns the tax rate applied to the ticket price, which is set to 21%.
- **draftprice**: Performs the calculation of the final ticket price based on various parameters.

## Technologies and Frameworks

- Java 17
- Spring Boot (version 3.0.6)
- Maven (version 3.9.0)
- JUnit 5 (version 5.9.2)
- Lombok (version 1.18.26)

## How to Start

1. Clone the repository from Git.
2. Build each project by running the following command inside the respective project folder:
```
mvn clean package
```
3. Start each project by running the following command from the `target` folder inside each project:
- For **baseprice**:

  ```
  java -jar baseprice-0.0.1-SNAPSHOT.jar
  ```

- For **taxrate**:

  ```
  java -jar taxrate-0.0.1-SNAPSHOT.jar
  ```

- For **draftprice**:

  ```
  java -jar draftprice-0.0.1-SNAPSHOT.jar
  ```

## How to Try

Once all three applications are up and running, you can execute requests using Postman.

1. Send a POST request to the URL `localhost:8080/draftPrice`.
2. In the body section of the request, include the following JSON:

```json
{
    "passengers": [
        {
            "isChild": false,
            "bagsCount": 2
        },
        {
            "isChild": true,
            "bagsCount": 1
        }
    ],
    "date": "2023-05-22",
    "busTerminalName": "Vilnius"
}
```
The JSON includes the following parameters:

- `passengers`: An array containing information about each passenger (child status and number of bags).
- `date`: The date in the format "yyyy-MM-dd" to calculate the price for a specific date.
- `busTerminalName`: The name of the target route. Currently, only "Vilnius" is acceptable.

3. After sending the request, you should receive a response like the following:
```
{
    "data": {
        "adultsTotalPrice": 12.10,
        "adultsBagsTotalPrice": 7.26,
        "childrenTotalPrice": 6.05,
        "childrenBagsTotalPrice": 3.63,
        "totalPrice": 29.04
    },
    "message": "Draft price is successfully calculated.",
    "status": 200
}
```
In case of an error, such as providing an unrecognized `busTerminalName`, you will receive an error response:
```
{
    "data": null,
    "message": "Cannot get base price: Given bus terminal name (Riga) is not recognized.",
    "status": 400
}
```

