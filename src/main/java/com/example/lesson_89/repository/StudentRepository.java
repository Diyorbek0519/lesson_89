package com.example.lesson_89.repository;

import com.example.lesson_89.dto.StudentDTO;
import com.example.lesson_89.entitiy.StudentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface   StudentRepository  extends CrudRepository<StudentEntity, Integer> {
   Optional<StudentEntity> getByPhone(String phone);
}
