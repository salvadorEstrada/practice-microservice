package com.estrada.userservice.service;

import com.estrada.userservice.comunicationmodel.Bike;
import com.estrada.userservice.comunicationmodel.Car;
import com.estrada.userservice.config.RestTemplateConfig;
import com.estrada.userservice.entity.UserEntity;
import com.estrada.userservice.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements UserServiceInterface{
   @Autowired
   private UserEntityRepository userEntityRepository;

    @Autowired
    private RestTemplateConfig restTemplateConfig;
    //private RestTemplate restTemplate;

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
        List<Car> cars = restTemplateConfig.restTemplate().getForObject("http://localhost:8002/car/byuser/"+userId,List.class);
       return  cars;
    }

    @Override
    public List<Bike> listBike(int userId){
        List<Bike> bikes = restTemplateConfig.restTemplate().getForObject("http://localhost:8003/bike/byuser/"+userId,List.class);
        return  bikes;
    }
}
