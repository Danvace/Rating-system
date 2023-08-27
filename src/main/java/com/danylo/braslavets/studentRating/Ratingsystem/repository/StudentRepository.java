package com.danylo.braslavets.studentRating.Ratingsystem.repository;

import com.danylo.braslavets.studentRating.Ratingsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByGroupId(Long groupId);

    Optional<Student> findByEmail(String email);
}
