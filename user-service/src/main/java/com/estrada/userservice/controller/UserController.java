package com.estrada.userservice.controller;

import com.estrada.userservice.comunicationmodel.Bike;
import com.estrada.userservice.comunicationmodel.Car;
import com.estrada.userservice.entity.UserEntity;
import com.estrada.userservice.feignclient.CarFeignClient;
import com.estrada.userservice.service.UserServiceInterface;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceInterface userServiceInterface;



    @GetMapping()
    public ResponseEntity<?> listUsers(){
        List<UserEntity> lisUsers= userServiceInterface.getAllUsers();
        /*if(listUsers().equals(null)){
            return ResponseEntity.noContent().build();
        }*/
        return ResponseEntity.ok(lisUsers);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId){
        UserEntity getUser = userServiceInterface.getUserById(userId);
        if(getUser==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(getUser);
    }

    @PostMapping()
    public ResponseEntity<Car> createUser(@RequestBody UserEntity user){
        UserEntity newUser = userServiceInterface.createUser(user);
        return  new ResponseEntity(newUser, HttpStatus.CREATED);
    }

    //Comunicacion con el microservicio car por restTemplate
    @CircuitBreaker(name="carsCB", fallbackMethod = "fallBackGetCars")
   @GetMapping("cars/{userId}")
    public ResponseEntity<?> getCars(@PathVariable int userId){

        UserEntity user =  userServiceInterface.getUserById(userId);
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        //En otro caso
        List<Car> cars = userServiceInterface.listCar(userId);
        return ResponseEntity.ok(cars);
    }

    @CircuitBreaker(name="carsCB", fallbackMethod = "fallBackSaveCar")
    @PostMapping("/createCar/{userId}")
    public ResponseEntity<?> createCar(@PathVariable int userId,@RequestBody Car car){

        if(userServiceInterface.getUserById(userId)==null)
            return ResponseEntity.notFound().build();

        Car newCar = userServiceInterface.createCar(userId,car);
        return new ResponseEntity<>(newCar, HttpStatus.CREATED);
    }



    //Comunicacion con el microservicio bike por restTemplate
    @CircuitBreaker(name="bikesCB", fallbackMethod = "fallBackGetBikes")
   @GetMapping("bikes/{userId}")
    public ResponseEntity<?> getBikes(@PathVariable int userId){
        UserEntity user = userServiceInterface.getUserById(userId);
        if(user==null){
            return ResponseEntity.notFound().build();
        }

        List<Bike> listBikes = userServiceInterface.listBike(userId);
        return ResponseEntity.ok(listBikes);
    }

    @CircuitBreaker(name="bikesCB", fallbackMethod = "fallBackSaveBike")
   @PostMapping("/createbike/{userId}")
    public ResponseEntity<?> createBike(@PathVariable int userId, @RequestBody Bike bike){
       if(userServiceInterface.getUserById(userId)==null)
           return ResponseEntity.notFound().build();

        Bike newBike = userServiceInterface.createBike(userId, bike);
        return new ResponseEntity<>(newBike, HttpStatus.CREATED);
   }

    @CircuitBreaker(name="allCB", fallbackMethod = "fallBackGetAll")
  @GetMapping("/getall/{userId}")
    public ResponseEntity<Map<String, Object>> getUserAllVehicles(@PathVariable int userId){
      Map<String, Object> getVehicles = userServiceInterface.getUsersAndVehicles(userId);
      return ResponseEntity.ok(getVehicles);
  }

  public ResponseEntity<?> fallBackGetCars(@PathVariable int userId, RuntimeException e){
        return new ResponseEntity("El usuario"+ userId+ "tiene los coches en el taller", HttpStatus.OK);
  }

  public ResponseEntity<?> fallBackSaveCar(@PathVariable int userId, @RequestBody Car car, RuntimeException e){
        return new ResponseEntity("El usuario"+ userId+ "No tiene dinero para coches", HttpStatus.OK);
    }

    public ResponseEntity<?> fallBackGetBikes(@PathVariable int userId, RuntimeException e){
        return new ResponseEntity("El usuario"+ userId+ "tiene las motos en el taller", HttpStatus.OK);
    }

    public ResponseEntity<?> fallBackSaveBike(@PathVariable int userId, @RequestBody Bike bike, RuntimeException e){
        return new ResponseEntity("El usuario"+ userId+ "no tiene dinero para motos", HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> fallBackGetAll(@PathVariable int userId, RuntimeException e){
        return new ResponseEntity("El usuario"+ userId+ "tiene los vehiculos en el taller", HttpStatus.OK);
    }


}
