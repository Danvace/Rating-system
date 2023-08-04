package com.danylo.braslavets.studentRating.Ratingsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;
    @OneToOne
    private Student groupLeader;

    @OneToMany
    private List<Student> students;


    public double groupRating(){
        int sum = 0;
        for(Student student:students){
            sum += student.getRating();
        }
        return (double) sum /students.size();
    }


}
