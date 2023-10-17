package com.estrada.userservice.feignclient;

import com.estrada.userservice.comunicationmodel.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="car-service", url = "http://localhost:8002")
@RequestMapping("/car")
public interface CarFeignClient {

    @PostMapping()
    Car createUser(@RequestBody Car car);

    @GetMapping("/byuser/{userId}")
    List<Car> getCars(@PathVariable("userId") int userId);
}
