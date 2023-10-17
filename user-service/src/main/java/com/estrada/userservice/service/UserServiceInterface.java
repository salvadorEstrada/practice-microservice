package com.estrada.userservice.service;

import com.estrada.userservice.comunicationmodel.Bike;
import com.estrada.userservice.comunicationmodel.Car;
import com.estrada.userservice.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Map;

public interface UserServiceInterface {
    public List<UserEntity> getAllUsers();

    public UserEntity getUserById(int id);

    public UserEntity createUser(UserEntity user);

    //Comunicacion entre servicios por resttemplate
    public List<Car> listCar(int userId);

    public List<Bike> listBike(int userId);


    //Para el cliente faign crear Car
    public Car createCar(int userId,Car car);

    //Para el cliente faign crear Bike
    Bike createBike(int userId,Bike bike);

    //Leer todo los vehiculos asociados a un usuario

    Map<String, Object> getUsersAndVehicles(int userId);

}
