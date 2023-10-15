package com.estrada.userservice.controller;

import com.estrada.userservice.comunicationmodel.Bike;
import com.estrada.userservice.comunicationmodel.Car;
import com.estrada.userservice.entity.UserEntity;
import com.estrada.userservice.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> createUser(@RequestBody UserEntity user){
        UserEntity newUser = userServiceInterface.createUser(user);
        return  new ResponseEntity<>(newUser, HttpStatus.CREATED);
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


}
