package com.danylo.braslavets.studentRating.Ratingsystem.model;

import com.danylo.braslavets.studentRating.Ratingsystem.exception.StudentNotFoundException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "student_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Group name should not be null")
    @NotEmpty(message = "Group name should not be empty")
    private String groupName;

    @OneToOne
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Student groupLeader;

    @JsonManagedReference
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Student> students;

    public void addStudent(Student student) {
        students.add(student);
        student.setGroup(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.setGroup(null);
    }

    public Student getStudentById(Long id) throws StudentNotFoundException {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException(
                        String.format("There is no student with id = %s in group %s",
                                id,
                                groupName)));
    }

    public double groupRating() {
        int sum = 0;
        for (Student student : students) {
            sum += student.getRating();
        }
        return (double) sum / students.size();
    }


}
