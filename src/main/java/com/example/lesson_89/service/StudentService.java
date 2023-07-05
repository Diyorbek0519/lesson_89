package com.example.lesson_89.service;

import com.example.lesson_89.dto.StudentDTO;
import com.example.lesson_89.entitiy.StudentEntity;
import com.example.lesson_89.exceptions.AppBadRequestException;
import com.example.lesson_89.exceptions.ItemNotFoundException;
import com.example.lesson_89.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    public StudentDTO add(StudentDTO dto) {
        check(dto); // validate inputs

        StudentEntity entity = new StudentEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setAge(dto.getAge());
        entity.setCreatedDate(LocalDateTime.now());
        studentRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public List<StudentDTO> addAll(List<StudentDTO> list) {
        for (StudentDTO dto : list) {
            StudentEntity entity = new StudentEntity();
            entity.setName(dto.getName());
            entity.setSurname(dto.getSurname());
            entity.setPhone(dto.getPhone());
            entity.setAge(dto.getAge());
            entity.setCreatedDate(LocalDateTime.now());
            studentRepository.save(entity);
            dto.setId(entity.getId());
        }
        return list;
    }

    public List<StudentDTO> getAll() {
        Iterable<StudentEntity> iterable = studentRepository.findAll();
        List<StudentDTO> dtoList = new LinkedList<>();
        iterable.forEach(entity -> {
            StudentDTO dto =toDTO(entity);
            dtoList.add(dto);
        });
        return dtoList;
    }

    public StudentDTO getById(Integer id) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()) {
            throw  new ItemNotFoundException("Student not found");
        }
        StudentEntity entity = optional.get();
        return toDTO(entity);

//        Optional<StudentEntity> optional = studentRepository.findById(id);
//        return optional.map(entity -> toDTO(entity)).orElseThrow(() -> {
//            throw new ItemNotFoundException("Student not found");
//        });

    }
    public StudentDTO getByPhone(String phone) {
        Optional<StudentEntity> optional = studentRepository.getByPhone(phone);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Student not found");
        }
        StudentEntity entity = optional.get();
        return toDTO(entity);
    }

    public Boolean delete(Integer id) {
        return false;
    }

    public Boolean update(Integer id, StudentDTO student) {
        check(student);
        return null;
    }
    public StudentDTO toDTO(StudentEntity entity){
        StudentDTO dto = new StudentDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setAge(entity.getAge());
        dto.setPhone(entity.getPhone());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    private void check(StudentDTO student) {
        if (student.getName() == null || student.getName().isBlank()) {
            throw new AppBadRequestException("Name qani?");
        }
        if (student.getSurname() == null || student.getSurname().isBlank()) {
            throw new AppBadRequestException("Surname qani?");
        }
    }
}
