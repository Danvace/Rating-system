package com.danylo.braslavets.studentRating.Ratingsystem.service;

import com.danylo.braslavets.studentRating.Ratingsystem.exception.StudentNotFoundException;
import com.danylo.braslavets.studentRating.Ratingsystem.model.Student;
import com.danylo.braslavets.studentRating.Ratingsystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> getStudents() {
        List<Student> allStudents = studentRepository.findAll();
        Collections.sort(allStudents);
        return allStudents;
    }

    public Student addStudent(final Student student) {
        return studentRepository.save(student);
    }

    public Student putStudent(final Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(final Long id) throws StudentNotFoundException {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(
                        String.format("There is no student with id = %s", id)));
    }

    public void deleteStudentBy(final Long id) throws StudentNotFoundException {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentNotFoundException(String.format("There is no student with id = %s", id));
        }
        studentRepository.deleteById(id);
    }

    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }

    public List<Student> getStudentsByGroupId(Long groupId) {
        return studentRepository.findByGroupId(groupId);
    }
}
