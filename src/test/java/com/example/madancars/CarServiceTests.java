package com.example.madancars;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTests {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    private Car car = new Car("make", "model", "colour", 1990);
    private List<Car> cars = Arrays.asList( car );

    @Test
    public void testFindAll() {
        when( carRepository.findAll() ).thenReturn(cars);
        List<Car> result = carService.findAll();
        assertNotNull(result);
    }

    @Test
    public void testFindById() {
        when( carRepository.findById( any(Long.class) ) ).thenReturn( Optional.of(car) );
        Optional<Car> result = carService.findById( 1L );
        assertNotNull(result);
    }

    @Test
    public void testSave() {
        when( carRepository.save( any(Car.class) ) ).thenReturn(car);
        Car result = carService.save( new Car("make", "model", "colour", 1990) );
        assertNotNull(result);
    }

    @Test
    public void testDeleteById() {
        doNothing().when(carRepository).deleteById( any(Long.class) );
        carService.deleteById( 1L );
        verify(carRepository, times(1)).deleteById(eq(1L));
    }

}
