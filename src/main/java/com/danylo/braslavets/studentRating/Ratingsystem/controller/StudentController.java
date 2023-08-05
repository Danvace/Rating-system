package com.danylo.braslavets.studentRating.Ratingsystem.controller;

import com.danylo.braslavets.studentRating.Ratingsystem.model.Student;
import com.danylo.braslavets.studentRating.Ratingsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping(path = "/group/{groupId}")
    public ResponseEntity<List<Student>> getStudentsByGroupId(@PathVariable Long groupId) {
        return ResponseEntity.ok(studentService.getStudentsByGroupId(groupId));
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    @PutMapping
    public ResponseEntity<Student> putStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.putStudent(student));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentBy(id);
    }

    @DeleteMapping(path = "/admin/deleteAll")
    public void deleteAllStudents() {
        studentService.deleteAllStudents();
    }

}
