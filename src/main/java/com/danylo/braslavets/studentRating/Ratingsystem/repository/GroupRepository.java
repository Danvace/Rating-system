package com.danylo.braslavets.studentRating.Ratingsystem.repository;

import com.danylo.braslavets.studentRating.Ratingsystem.model.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<StudentGroup, Long> {
}
