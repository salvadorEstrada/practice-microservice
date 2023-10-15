package com.estrada.bikeservice.controller;

import com.estrada.bikeservice.entity.Bike;
import com.estrada.bikeservice.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike")
public class BikeController {

    @Autowired
    private BikeService bikeService;

    @GetMapping()
    public ResponseEntity<?> listBike(){
        List<Bike> lisBikes= bikeService.getAllBikes();
        if(lisBikes.isEmpty()){
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(lisBikes);
    }

    @GetMapping("/{bikeId}")
    public ResponseEntity<?> getBikeById(@PathVariable int bikeId){
        Bike getBike = bikeService.getBikeById(bikeId);
        if(getBike==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(getBike);
    }

    @PostMapping()
    public ResponseEntity<?> createBike(@RequestBody Bike bike){
        Bike newBike = bikeService.createBike(bike);
        return  new ResponseEntity<>(newBike, HttpStatus.CREATED);
    }


    @GetMapping("/byuser/{userId}")
    public ResponseEntity<?> getUserId(@PathVariable int userId){
        List<Bike> bikes = bikeService.byUserId(userId);
        if(bikes.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bikes);
    }

}
