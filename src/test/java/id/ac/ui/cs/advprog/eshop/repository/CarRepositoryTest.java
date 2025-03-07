package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarRepositoryTest {

    @InjectMocks
    CarRepository carRepository;

    @Test
    void testCreateAndFind() {
        Car car = new Car();
        car.setId("fixed-id-123");
        car.setName("Toyota");
        car.setColor("Red");
        car.setQuantity(10);

        carRepository.create(car);

        Iterator<Car> iterator = carRepository.findAll();
        assertTrue(iterator.hasNext());
        Car savedCar = iterator.next();
        assertEquals(car.getId(), savedCar.getId());
        assertEquals(car.getName(), savedCar.getName());
        assertEquals(car.getColor(), savedCar.getColor());
        assertEquals(car.getQuantity(), savedCar.getQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Car> iterator = carRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneCar() {
        Car car1 = new Car();
        car1.setId("id1");
        car1.setName("Honda");
        car1.setColor("Blue");
        car1.setQuantity(5);
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setId("id2");
        car2.setName("Ford");
        car2.setColor("Black");
        car2.setQuantity(8);
        carRepository.create(car2);

        Iterator<Car> iterator = carRepository.findAll();
        assertTrue(iterator.hasNext());
        Car savedCar1 = iterator.next();
        assertEquals(car1.getId(), savedCar1.getId());

        Car savedCar2 = iterator.next();
        assertEquals(car2.getId(), savedCar2.getId());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindByIdExisting() {
        Car car = new Car();
        car.setId("car-123");
        car.setName("Nissan");
        car.setColor("White");
        car.setQuantity(7);
        carRepository.create(car);

        Car found = carRepository.findById("car-123");
        assertNotNull(found);
        assertEquals(car.getName(), found.getName());
        assertEquals(car.getColor(), found.getColor());
        assertEquals(car.getQuantity(), found.getQuantity());
    }

    @Test
    void testFindByIdNotFound() {
        Car found = carRepository.findById("non-existent-id");
        assertNull(found);
    }

    @Test
    void testUpdateCarPositive() {
        Car car = new Car();
        car.setId("update-test-id");
        car.setName("Old Name");
        car.setColor("Green");
        car.setQuantity(3);
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setName("New Name");
        updatedCar.setColor("Yellow");
        updatedCar.setQuantity(6);

        Car result = carRepository.update("update-test-id", updatedCar);
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("Yellow", result.getColor());
        assertEquals(6, result.getQuantity());

        Car foundCar = carRepository.findById("update-test-id");
        assertNotNull(foundCar);
        assertEquals("New Name", foundCar.getName());
        assertEquals("Yellow", foundCar.getColor());
        assertEquals(6, foundCar.getQuantity());
    }

    @Test
    void testUpdateCarNegative() {
        Car updatedCar = new Car();
        updatedCar.setName("Doesn't Matter");
        updatedCar.setColor("Silver");
        updatedCar.setQuantity(0);

        Car result = carRepository.update("non-existent-id", updatedCar);
        assertNull(result);
    }

    @Test
    void testDeleteCarPositive() {
        Car car = new Car();
        car.setId("delete-id");
        car.setName("Car to Delete");
        car.setColor("Purple");
        car.setQuantity(4);
        carRepository.create(car);

        carRepository.delete("delete-id");

        Car found = carRepository.findById("delete-id");
        assertNull(found);
    }

    @Test
    void testDeleteCarNegative() {
        Car car = new Car();
        car.setId("existing-id");
        car.setName("Existing Car");
        car.setColor("Orange");
        car.setQuantity(2);
        carRepository.create(car);

        carRepository.delete("non-existent-id");

        Car found = carRepository.findById("existing-id");
        assertNotNull(found);
    }
}
