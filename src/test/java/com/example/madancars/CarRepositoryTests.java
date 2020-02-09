package com.example.madancars;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CarRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository testee;

    private Long id;

    @BeforeEach
    public void setup() {
        Car car = entityManager.persistAndFlush(new Car("make", "model", "colour", 1990));
        id = car.getId();
    }

    @AfterEach
    public void clear() {
        entityManager.clear();
    }

    @Test
    public void testFindAll() {
        List<?> all = testee.findAll();
        assertEquals(all.size(), 1);
    }

    @Test
    public void testFindById() {
        Optional<Car> car = testee.findById(id);
        assertTrue(car.isPresent());
    }

    @Test
    public void testSave() {
        Car car = testee.save(new Car("make1", "model1", "colour1", 1991));
        assertNotNull(entityManager.find(Car.class, car.getId()));
    }

    @Test
    public void testDeleteById() {
        testee.deleteById( id );
        Optional<Car> car = testee.findById(id);
        assertFalse(car.isPresent());
    }
}
