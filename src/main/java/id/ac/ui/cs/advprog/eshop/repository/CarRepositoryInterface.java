package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import java.util.Iterator;

public interface CarRepositoryInterface {
    Car create(Car car);
    Iterator<Car> findAll();
    Car findById(String carId);
    Car update(String carId, Car updatedCar);
    void delete(String carId);
}
