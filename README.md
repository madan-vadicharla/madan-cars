# Instructions to run the application

To run tests:
```console
$ mvn clean test
```

To build/package the application into local target folder:
```console
$ mvn clean package
```

Run the application as follows:
```console
$ mvn spring-boot:run
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

Thank you :)
