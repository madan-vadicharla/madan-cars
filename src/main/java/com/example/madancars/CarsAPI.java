package com.example.madancars;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j

@RestController
@RequestMapping("/carsapi/v1")
public class CarsAPI {

    private final CarService carService;

    private final DatamuseConsumerService datamuseConsumerService;

    @GetMapping("/cars")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Car>> all() {
        return ResponseEntity.ok().body( carService.findAll() );
    }

    @GetMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Car> retrieve(@PathVariable Long id) {
        Car car = carService.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));

        String moreData = datamuseConsumerService.relatedWords(car.getModel());
        car.setDatamuse(moreData);

        return ResponseEntity.ok().body(car);
    }

    @PostMapping("/cars")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Car> add(@RequestBody Car car) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.save(car));
    }

    @PutMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Car> update(@PathVariable Long id, @RequestBody Car car) {
        Car carToUpdate = carService.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));

        carToUpdate.setMake(car.getMake());
        carToUpdate.setModel(car.getModel());
        carToUpdate.setColour(car.getColour());
        carToUpdate.setYear(car.getYear());

        Car updatedCar = carService.save(carToUpdate);
        return ResponseEntity.ok(updatedCar);
    }

    @DeleteMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity remove(@PathVariable Long id) {
        carService.deleteById(id);
        return ResponseEntity.accepted().build();
    }
}
