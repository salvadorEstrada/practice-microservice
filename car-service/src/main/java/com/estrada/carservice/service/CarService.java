package com.estrada.carservice.service;

import com.estrada.carservice.entity.Car;


import java.util.List;

public interface CarService {
    public List<Car> getAllCars();

    public Car getCarById(int id);

    public Car createCar(Car car);

    public List<Car> byUserId(int id);
}
