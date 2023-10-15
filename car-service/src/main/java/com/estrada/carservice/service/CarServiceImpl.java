package com.estrada.carservice.service;

import com.estrada.carservice.entity.Car;
import com.estrada.carservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
   @Autowired
   private CarRepository carRepository;


    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car getCarById(int id) {
        return carRepository.findById(id).orElse(null) ;
    }


    @Override
    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    //Buscar el id del service Usuario
    @Override
    public List<Car> byUserId(int id) {
        return carRepository.findByUserId(id);
    }
}
