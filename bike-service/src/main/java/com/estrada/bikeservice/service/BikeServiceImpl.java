package com.estrada.bikeservice.service;

import com.estrada.bikeservice.entity.Bike;
import com.estrada.bikeservice.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BikeServiceImpl implements BikeService {
   @Autowired
   private BikeRepository bikeRepository;


    @Override
    public List<Bike> getAllBikes() {
        return bikeRepository.findAll();
    }

    @Override
    public Bike getBikeById(int id) {
        return bikeRepository.findById(id).orElse(null) ;
    }


    @Override
    public Bike createBike(Bike bike) {
        return bikeRepository.save(bike);
    }

    //Buscar el id del service Usuario
    @Override
    public List<Bike> byUserId(int id) {
        return bikeRepository.findByUserId(id);
    }
}
