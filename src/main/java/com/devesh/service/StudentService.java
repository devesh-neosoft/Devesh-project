package com.devesh.service;

import com.devesh.dto.StudentDto;
import com.devesh.entities.Student;
import com.devesh.model.Customer;
import com.devesh.utilities.GenericResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {

    ResponseEntity<GenericResponse<StudentDto>> createStudent(StudentDto userDto);

    ResponseEntity<GenericResponse<StudentDto>> saveStudent(Student student);

    ResponseEntity<GenericResponse<Void>> updateStudent(StudentDto userDto, Integer userId);

    StudentDto getStudentById(Integer userId);

    ResponseEntity<GenericResponse<List<StudentDto>>> getAllStudent();

    ResponseEntity<GenericResponse<Void>> deleteStudent(Integer userId);

    ResponseEntity<GenericResponse<String>> setStudentForConsumer(List<Customer> studentEvent);
}
