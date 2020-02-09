package com.example.madancars;

public class CarNotFoundException extends RuntimeException {

    CarNotFoundException(Long id) {
        super("Could not find car " + id);
    }
}
