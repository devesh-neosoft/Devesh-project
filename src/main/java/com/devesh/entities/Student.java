package com.devesh.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "user_name", nullable = false, length = 100)
    private String name;
    private String email;
    private String password;
    private String about;

}
