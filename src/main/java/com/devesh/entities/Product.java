package com.devesh.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("student")
public class Product implements Serializable {

    private int id;
    private String name;
}
