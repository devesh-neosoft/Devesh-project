package com.devesh.controller;

import com.devesh.entities.Product;
import com.devesh.repositories.ProductRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@EnableCaching
@RequestMapping("product")
public class ProductRedisController {

    @Autowired
    private ProductRedisRepository redisRepository;

    @PostMapping("saveProduct")
    public Product save(@RequestBody Product product){
        return redisRepository.save(product);
    }

    @PostMapping("getAllProduct")
    public List<Product> save(){
        return redisRepository.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id", value = "product")
    public Product findById(@PathVariable int id){
        return redisRepository.findStudentById(id);
    }

    @PostMapping("/{id}")
    public String deleteById(@PathVariable int id){
        return redisRepository.deleteStudentById(id);
    }
}
