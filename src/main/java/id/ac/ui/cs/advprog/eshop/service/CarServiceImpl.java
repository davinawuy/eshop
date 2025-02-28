package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private CarRepositoryInterface carRepository;

    @Autowired
    public CarServiceImpl(CarRepositoryInterface carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car create(Car car) {
        // TODO Auto-generated method stub
        carRepository.create(car);
        return car;
    }

    @Override
    public List<Car> findAll() {
        Iterator<Car> carIterator = carRepository.findAll();
        List<Car> allCar = new ArrayList<>();
        carIterator.forEachRemaining(allCar::add);
        return allCar;
    }

    @Override
    public Car findById(String Id) {
        Car car = carRepository.findById(Id);
        return car;
    }

    @Override
    public void update(String Id, Car car) {
        // TODO Auto-generated method stub
        carRepository.update(Id, car);
    }

    @Override
    public void deleteCarById(String Id) {
        // TODO Auto-generated method stub
        carRepository.delete(Id);
    }

}
