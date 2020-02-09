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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class CarsAPITests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private DatamuseConsumerService datamuseConsumerService;

    private JacksonTester<Car> jsonHelper;

    private Car car = newCar(1L, "Ferrari", "LaFerrari", "red", 2018);
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
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].make", is("Ferrari")))
                .andExpect(jsonPath("$.[0].model", is("LaFerrari")))
                .andExpect(jsonPath("$.[0].colour", is("red")))
                .andExpect(jsonPath("$.[0].year", is(2018)));
    }

    @Test
    public void testRetrieve() throws Exception {

        given( carService.findById(any(Long.class)) ).willReturn(Optional.of(car));
        given( datamuseConsumerService.relatedWords(any(String.class)) ).willReturn("some,text,from,datamuse");

        this.mockMvc.perform( get("/carsapi/v1/cars/1") )
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.make", is("Ferrari")))
                .andExpect(jsonPath("$.model", is("LaFerrari")))
                .andExpect(jsonPath("$.colour", is("red")))
                .andExpect(jsonPath("$.year", is(2018)))
                .andExpect(jsonPath("$.datamuse", is("some,text,from,datamuse")));
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
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.make", is("Ferrari")))
                .andExpect(jsonPath("$.model", is("LaFerrari")))
                .andExpect(jsonPath("$.colour", is("red")))
                .andExpect(jsonPath("$.year", is(2018)));
    }

    @Test
    public void testUpdate() throws Exception {
        given( carService.findById(any(Long.class)) ).willReturn(Optional.of(car));

        Car updatedCar = new Car();
        updatedCar.setId(car.getId());
        updatedCar.setMake(car.getMake());
        updatedCar.setModel("newmodel");
        updatedCar.setColour(car.getColour());
        updatedCar.setYear(car.getYear());

        given( carService.save( any(Car.class) ) ).willReturn(car);

        this.mockMvc.perform( put("/carsapi/v1/cars/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content( jsonHelper.write(updatedCar).getJson() ) )
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.make", is("Ferrari")))
                .andExpect(jsonPath("$.model", is("newmodel")))
                .andExpect(jsonPath("$.colour", is("red")))
                .andExpect(jsonPath("$.year", is(2018)));
    }

    @Test
    public void testUpdateUnknown() throws Exception {

        given( carService.findById(any(Long.class)) ).willThrow(CarNotFoundException.class);

        this.mockMvc.perform( put("/carsapi/v1/cars/2") )
                .andExpect( status().isBadRequest() );
    }

    @Test
    public void testRemove() throws Exception {

        doNothing().when(carService).deleteById( any(Long.class) );

        this.mockMvc.perform( delete("/carsapi/v1/cars/1") )
                .andExpect( status().isAccepted() );
    }

    private Car newCar(long id, String make, String model, String colour, int year) {
        Car car = new Car();
        car.setId(id);
        car.setMake(make);
        car.setModel(model);
        car.setColour(colour);
        car.setYear(year);
        return car;
    }
}
