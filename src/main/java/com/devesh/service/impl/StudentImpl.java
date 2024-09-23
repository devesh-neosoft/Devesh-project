package com.devesh.service.impl;

import com.devesh.dto.StudentDto;
import com.devesh.entities.Address;
import com.devesh.entities.Student;
import com.devesh.exception.CodeException;
import com.devesh.exception.ErrorCode;
import com.devesh.exception.ResourceNotFoundException;
import com.devesh.model.Customer;
import com.devesh.repositories.StudentRepository;
import com.devesh.service.StudentService;
import com.devesh.utilities.AppConstants;
import com.devesh.utilities.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentImpl implements StudentService {


    @Autowired
    public StudentRepository studentRepository;

    @Autowired
    public ModelMapper modelMapper;
    @Autowired
    public AddressServiceImpl addressService;

    @Autowired
    private KafkaTemplate<String, Customer> kafkaTemplate;

    @Override
    public ResponseEntity<GenericResponse<String>> setStudentForConsumer(List<Customer> student) {

        if (!student.isEmpty()) {
            for (Customer s : student) {
                kafkaTemplate.send(AppConstants.TOPIC, s);
                log.info("************Msg published to Kafka topic***************");
            }
        }
        return ResponseEntity.ok(new GenericResponse<>("Customer Created Successfully for Consumer", true, HttpStatus.OK.value(), "Done"));
    }


    @Override
    public ResponseEntity<GenericResponse<StudentDto>> createStudent(StudentDto userDto) {
        Student student = dtoToUser(userDto);
        Student save = studentRepository.save(student);
        return ResponseEntity.ok(new GenericResponse<>("Student Created Successfully", true, HttpStatus.OK.value(), userToDto(save)));
    }


    @Transactional(propagation = Propagation.REQUIRED,timeout = 5,rollbackFor = {CodeException.class})
    @Override
    public ResponseEntity<GenericResponse<StudentDto>> saveStudent(Student student) {
        try {

//            Thread.sleep(6000);

            Student savedStudent = studentRepository.save(student);

            Address address = new Address();
            address.setId(1);
            address.setName("Krishna");
            addressService.addAddress(address);

            if (student.getName().isEmpty() || student.getName().length() < 3) {
                throw new CodeException("Address is too short", ErrorCode.COMMON);
            }
            return ResponseEntity.ok(
                    new GenericResponse<>("Student Saved Successfully", true, HttpStatus.OK.value(), userToDto(savedStudent)));
        } catch (CodeException e) {
            log.error("Got CodeException {}", e.getMessage());
            return ResponseEntity.ok(
                    new GenericResponse<>( e.getMessage(), false, HttpStatus.OK.value(),null));
        }catch (Exception e) {
            log.error("Got Exception {}", e.getMessage());
            return ResponseEntity.ok(
                    new GenericResponse<>( e.getMessage(), false, HttpStatus.OK.value(),null));
        }
    }



    @Override
    public ResponseEntity<GenericResponse<Void>> updateStudent(StudentDto userDto, Integer userId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        student.setName(userDto.getName().trim());
        student.setEmail(userDto.getEmail().trim());
        student.setPassword(userDto.getPassword().trim());
        student.setAbout(userDto.getAbout().trim());


        studentRepository.save(student);
        return ResponseEntity.ok(new GenericResponse<>("User Updated Successfully", true, HttpStatus.OK.value(), null));
    }



    @Override
    public StudentDto getStudentById(Integer userId) {
        Student user = studentRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return userToDto(user);
    }

    @Override
    public ResponseEntity<GenericResponse<List<StudentDto>>> getAllStudent() {
        List<StudentDto> collect;
        try {
            List<Student> students = studentRepository.findAll();
            if (!CollectionUtils.isEmpty(students)) {
                collect = students.stream().map(this::userToDto).collect(Collectors.toList());
            } else {
                throw new CodeException("User Not found", ErrorCode.COMMON);
            }
            return ResponseEntity.ok(
                    new GenericResponse<>("Users Fetched Successfully", true, HttpStatus.OK.value(), collect)
            );
        } catch (CodeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new GenericResponse<>(e.getMessage(), false, ErrorCode.COMMON.getCode(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>("Internal Server Error", false, HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
        }
    }



    @Override
    public ResponseEntity<GenericResponse<Void>> deleteStudent(Integer userId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        studentRepository.delete(student);
        return ResponseEntity.ok(
                new GenericResponse<>("User Deleted Successfully", true, HttpStatus.OK.value(), null)
        );
    }




    public Student dtoToUser(StudentDto userDto) {
        return modelMapper.map(userDto, Student.class);
    }

    private StudentDto userToDto(Student user) {
        return modelMapper.map(user, StudentDto.class);
    }

}
