package com.devesh.repositories;

import com.devesh.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
