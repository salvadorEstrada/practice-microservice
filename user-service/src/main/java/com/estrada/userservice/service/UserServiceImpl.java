package com.estrada.userservice.service;

import com.estrada.userservice.comunicationmodel.Bike;
import com.estrada.userservice.comunicationmodel.Car;
import com.estrada.userservice.config.RestTemplateConfig;
import com.estrada.userservice.entity.UserEntity;
import com.estrada.userservice.feignclient.BikeFeignClient;
import com.estrada.userservice.feignclient.CarFeignClient;
import com.estrada.userservice.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserServiceInterface{
   @Autowired
   private UserEntityRepository userEntityRepository;

    @Autowired
    private RestTemplateConfig restTemplateConfig;
    //private RestTemplate restTemplate;

    //inyectar al cliente faign
    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private BikeFeignClient bikeFeignClient;

    @Override
    public List<UserEntity> getAllUsers() {
        return userEntityRepository.findAll();
    }

    @Override
    public UserEntity getUserById(int id) {
        return userEntityRepository.findById(id).orElse(null) ;
    }


    @Override
    public UserEntity createUser(UserEntity user) {
        return userEntityRepository.save(user);
    }



   @Override
    public List<Car> listCar(int userId){
        List<Car> cars = restTemplateConfig.restTemplate().getForObject("http://car-service/car/byuser/"+userId,List.class);
       return  cars;
    }

    @Override
    public List<Bike> listBike(int userId){
        List<Bike> bikes = restTemplateConfig.restTemplate().getForObject("http://bike-service/bike/byuser/"+userId,List.class);
        return  bikes;
    }

    //Para el cliente faign
    @Override
    public Car createCar(int userId,Car car){
        car.setUserId(userId);
        Car newCar = carFeignClient.createUser(car);
        return newCar;
    }

    //Para el cliente faign
    @Override
    public Bike createBike(int userId, Bike bike) {
        bike.setUserId(userId);
        Bike newBike = bikeFeignClient.createBike(bike);
        return newBike;
    }

    @Override
    public Map<String, Object> getUsersAndVehicles(int userId){
        Map<String, Object> getUserVehicles = new HashMap<>();
        UserEntity user = userEntityRepository.findById(userId).orElse(null);
        if(user==null){
            getUserVehicles.put("Mensaje","No existe el usuario");
            return getUserVehicles;
        }
        getUserVehicles.put("User", user);

        List<Car> listCars = carFeignClient.getCars(userId);
        if(listCars.isEmpty())
            getUserVehicles.put("Cars", "Usuario sin coches asociados");
        else
            getUserVehicles.put("Cars", listCars);

        List<Bike> listBikes = bikeFeignClient.getBikes(userId);
        if(listBikes.isEmpty())
            getUserVehicles.put("Bikes", "Usuario sin motos asociadas");
        else
            getUserVehicles.put("Bikes", listBikes);

        return getUserVehicles;
    }


}
