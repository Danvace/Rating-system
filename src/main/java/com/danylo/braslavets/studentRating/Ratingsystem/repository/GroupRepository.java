package com.danylo.braslavets.studentRating.Ratingsystem.repository;

import com.danylo.braslavets.studentRating.Ratingsystem.model.Group;
import com.danylo.braslavets.studentRating.Ratingsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}
