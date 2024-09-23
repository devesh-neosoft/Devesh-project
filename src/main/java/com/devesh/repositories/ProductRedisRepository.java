package com.devesh.repositories;

import com.devesh.entities.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRedisRepository {

    public static final String HASH_KEY = "product";
    private static final Logger log = LoggerFactory.getLogger(ProductRedisRepository.class);

    @Autowired
    private RedisTemplate redisTemplate;


    public Product save(Product product){
        redisTemplate.opsForHash().put(HASH_KEY,product.getId(),product);
        return product;
    }

    public List<Product> findAll(){
        return redisTemplate.opsForHash().values(HASH_KEY);
    }


    public Product findStudentById(int id){
        log.info("Called findStudentById() from DB");
        return (Product) redisTemplate.opsForHash().get(HASH_KEY,id);
    }

    public String deleteStudentById(int id){
        redisTemplate.opsForHash().delete(HASH_KEY,id);
        return " Product deleted";
    }


}
