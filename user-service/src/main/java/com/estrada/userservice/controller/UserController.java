package com.estrada.userservice.controller;

import com.estrada.userservice.comunicationmodel.Bike;
import com.estrada.userservice.comunicationmodel.Car;
import com.estrada.userservice.entity.UserEntity;
import com.estrada.userservice.feignclient.CarFeignClient;
import com.estrada.userservice.service.UserServiceInterface;
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

    //Comunicacion con el microservicio bike por restTemplate

   @GetMapping("bikes/{userId}")
    public ResponseEntity<?> getBikes(@PathVariable int userId){
        UserEntity user = userServiceInterface.getUserById(userId);
        if(user==null){
            return ResponseEntity.notFound().build();
        }

        List<Bike> listBikes = userServiceInterface.listBike(userId);
        return ResponseEntity.ok(listBikes);
    }

   @PostMapping("/createCar/{userId}")
    public ResponseEntity<?> createCar(@PathVariable int userId,@RequestBody Car car){

        if(userServiceInterface.getUserById(userId)==null)
            return ResponseEntity.notFound().build();

        Car newCar = userServiceInterface.createCar(userId,car);
        return new ResponseEntity<>(newCar, HttpStatus.CREATED);
   }

   @PostMapping("/createbike/{userId}")
    public ResponseEntity<?> createBike(@PathVariable int userId, @RequestBody Bike bike){
       if(userServiceInterface.getUserById(userId)==null)
           return ResponseEntity.notFound().build();

        Bike newBike = userServiceInterface.createBike(userId, bike);
        return new ResponseEntity<>(newBike, HttpStatus.CREATED);
   }

  @GetMapping("/getall/{userId}")
    public ResponseEntity<Map<String, Object>> getUserAllVehicles(@PathVariable int userId){
      Map<String, Object> getVehicles = userServiceInterface.getUsersAndVehicles(userId);
      return ResponseEntity.ok(getVehicles);
  }


}
