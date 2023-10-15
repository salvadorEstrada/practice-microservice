package com.estrada.userservice.service;

import com.estrada.userservice.comunicationmodel.Bike;
import com.estrada.userservice.comunicationmodel.Car;
import com.estrada.userservice.entity.UserEntity;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface UserServiceInterface {
    public List<UserEntity> getAllUsers();

    public UserEntity getUserById(int id);

    public UserEntity createUser(UserEntity user);

    //Comunicacion entre servicios por resttemplate
    public List<Car> listCar(int userId);

    public List<Bike> listBike(int userId);

}
