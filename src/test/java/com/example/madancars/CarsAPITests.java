package com.example.madancars;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CarsAPITests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    private JacksonTester<Car> jsonHelper;

    private Car car = newCar(1L, "1", "Ferrari", 1990);
    private List<Car> cars = Arrays.asList( car );

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void testAll() throws Exception {

        given( carService.findAll() ).willReturn(cars);

        this.mockMvc.perform( get("/carsapi/v1/cars") )
                .andExpect( status().isOk() )
                .andExpect( content().json("[{'id': 1,'make': '1','model': 'Ferrari','year': 1990}]") );
    }

    @Test
    public void testRetrieve() throws Exception {

        given( carService.findById(any(Long.class)) ).willReturn(Optional.of(car));

        this.mockMvc.perform( get("/carsapi/v1/cars/1") )
                .andExpect( status().isOk() )
                .andExpect( content().json("{'id': 1,'make': '1','model': 'Ferrari','year': 1990}") );
    }

    @Test
    public void testRetrieveUnknown() throws Exception {

        given( carService.findById(any(Long.class)) ).willThrow(CarNotFoundException.class);

        this.mockMvc.perform( get("/carsapi/v1/cars/2") )
                .andExpect( status().isNotFound() );
    }

    @Test
    public void testAdd() throws Exception {

        given( carService.save( any(Car.class) ) ).willReturn(car);

        this.mockMvc.perform( post("/carsapi/v1/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content( jsonHelper.write(car).getJson() ) )
                .andExpect( status().isCreated() )
                .andExpect( content().json("{'id': 1,'make': '1','model': 'Ferrari','year': 1990}") );
    }

    @Test
    public void testRemove() throws Exception {

        doNothing().when(carService).deleteById( any(Long.class) );

        this.mockMvc.perform( delete("/carsapi/v1/cars/1") )
                .andExpect( status().isAccepted() );
    }

    private Car newCar(long id, String make, String model, int year) {
        Car car = new Car();
        car.setId(id);
        car.setMake(make);
        car.setModel(model);
        car.setYear(year);
        return car;
    }
}
