package com.estrada.userservice.feignclient;

import com.estrada.userservice.comunicationmodel.Bike;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="bike-service")
@RequestMapping("/bike")
public interface BikeFeignClient {

    @PostMapping()
    Bike createBike(@RequestBody Bike bike);

    @GetMapping("/byuser/{userId}")
    List<Bike> getBikes(@PathVariable("userId") int userId);

}
