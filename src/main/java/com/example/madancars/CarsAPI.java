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

    @GetMapping("/cars")
    ResponseEntity<List<Car>> all() {
        return ResponseEntity.ok().body( carService.findAll() );
    }

    @GetMapping("/cars/{id}")
    ResponseEntity<Car> retrieve(@PathVariable Long id) throws CarNotFoundException {
        Car car = carService.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
        return ResponseEntity.ok().body(car);
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> add(@RequestBody Car car) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.save(car));
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity remove(@PathVariable Long id) {
        carService.deleteById(id);
        return ResponseEntity.accepted().build();
    }
}
