package com.devesh.controller;

import com.devesh.dto.StudentDto;
import com.devesh.entities.Student;
import com.devesh.model.Customer;
import com.devesh.service.StudentService;
import com.devesh.utilities.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/")
@EnableCaching
public class StudentController {

    @Autowired
    private StudentService studentService;



    @PostMapping("CommitStudent")
    public ResponseEntity<GenericResponse<String>> createStudentForQuery(@RequestBody List<Customer> studentEvent){
        return studentService.setStudentForConsumer(studentEvent);
    }


    @PostMapping("createStudent")
    public ResponseEntity<GenericResponse<StudentDto>> createStudent(@RequestBody StudentDto userDto){
        return studentService.createStudent(userDto);
    }

    @PostMapping("saveStudent")
    public ResponseEntity<GenericResponse<StudentDto>> saveStudent(@RequestBody Student student){
        return studentService.saveStudent(student);
    }



    @PostMapping("updateStudent")
    public ResponseEntity<GenericResponse<Void>> updateStudent(@RequestBody StudentDto userDto) {
        return studentService.updateStudent(userDto, userDto.getId());
    }

    @PostMapping("getSingleStudent/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.studentService.getStudentById(id));
    }


    @PostMapping("getAllStudent")
    public ResponseEntity<GenericResponse<List<StudentDto>>> getAllStudents(){
        return studentService.getAllStudent();
    }

    @PostMapping("deleteStudent")
    public ResponseEntity<GenericResponse<Void>> deleteStudent(@RequestParam Integer id) {
        return studentService.deleteStudent(id);
    }

}
