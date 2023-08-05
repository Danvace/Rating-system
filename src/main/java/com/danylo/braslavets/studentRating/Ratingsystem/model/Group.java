package com.danylo.braslavets.studentRating.Ratingsystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "student_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;
    @OneToOne
    private Student groupLeader;

    @JsonManagedReference
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Student> students;

    public void addStudent(Student student) {
        students.add(student);
        student.setGroup(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.setGroup(null);
    }

    public double groupRating() {
        int sum = 0;
        for (Student student : students) {
            sum += student.getRating();
        }
        return (double) sum / students.size();
    }


}
