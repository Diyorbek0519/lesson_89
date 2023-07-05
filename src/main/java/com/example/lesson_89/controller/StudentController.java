package com.example.lesson_89.controller;

import com.example.lesson_89.dto.StudentDTO;
import com.example.lesson_89.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody StudentDTO student) {
        StudentDTO response = studentService.add(student);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create/all")
    public ResponseEntity<?> create(@RequestBody List<StudentDTO> list) {
        return ResponseEntity.ok(studentService.addAll(list));
    }


    @GetMapping("/all")
    public List<StudentDTO> all() {
        return studentService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentService.getById(id));
    }
    @GetMapping(value = "/phone/{phone}")
    public ResponseEntity<?> getByPhone(@PathVariable("phone") String phone){
        return ResponseEntity.ok(studentService.getByPhone(phone));
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        Boolean response = studentService.delete(id);
        if (response) {
            return ResponseEntity.ok("Student deleted");
        }
        return ResponseEntity.badRequest().body("Student Not Found");
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> put(@RequestBody StudentDTO student,
                                 @PathVariable("id") Integer id) {
        studentService.update(id, student);
        return ResponseEntity.ok(true);
    }
}
