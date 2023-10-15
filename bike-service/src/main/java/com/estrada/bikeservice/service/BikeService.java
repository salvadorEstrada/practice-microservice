package com.estrada.bikeservice.service;

import com.estrada.bikeservice.entity.Bike;

import java.util.List;

public interface BikeService {
    public List<Bike> getAllBikes();

    public Bike getBikeById(int id);

    public Bike createBike(Bike car);

    public List<Bike> byUserId(int id);
}
