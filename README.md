# Instructions to run the application
Please run following from [this project directory](./).

To run tests:
```console
$ ./mvnw clean test
```

To build/package the application into local target folder:
```console
$ ./mvnw clean package
```

Run the application as follows:
```console
$ ./mvnw spring-boot:run
```

Open following url in your browser and try out various api calls!

* [http://localhost:8080/carsapi/swagger-ui.html](http://localhost:8080/carsapi/swagger-ui.html)

or
```console
curl -X POST "http://localhost:8080/carsapi/v1/cars" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"colour\": \"red\", \"make\": \"Ferrari\", \"model\": \"LaFerrari\", \"year\": 2018}"

curl -X GET "http://localhost:8080/carsapi/v1/cars" -H "accept: */*"

curl -X GET "http://localhost:8080/carsapi/v1/cars/1" -H "accept: */*"

curl -X DELETE "http://localhost:8080/carsapi/v1/cars/1" -H "accept: */*"
```

Other application links of interest:

* [http://localhost:8080/carsapi/h2](http://localhost:8080/carsapi/h2)
* [http://localhost:8080/carsapi/v1/api-docs](http://localhost:8080/carsapi/h2)

Thank you :)
