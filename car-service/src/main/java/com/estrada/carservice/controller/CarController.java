package com.estrada.carservice.controller;

import com.estrada.carservice.entity.Car;
import com.estrada.carservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;


    @GetMapping()
    public ResponseEntity<?> listCar(){
        List<Car> lisCars= carService.getAllCars();
        if(lisCars.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lisCars);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<?> getCarById(@PathVariable int carId){
        Car getCar = carService.getCarById(carId);
        if(getCar==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(getCar);
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody Car car){
        Car newCar = carService.createCar(car);
        return  new ResponseEntity<>(newCar, HttpStatus.CREATED);
    }

    //Este es el mismo metodo en el feign client
    @GetMapping("/byuser/{userId}")
    public ResponseEntity<?> getUserId(@PathVariable int userId){
        List<Car> cars = carService.byUserId(userId);
        return ResponseEntity.ok(cars);
    }

}
