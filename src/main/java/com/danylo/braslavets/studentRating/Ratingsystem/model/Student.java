package com.danylo.braslavets.studentRating.Ratingsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
public class Student implements Comparable<Student> {

    @NotEmpty
    private String studentName;

    @Min(0)
    @Max(100)
    private int ukrainianLanguage;

    @Min(0)
    @Max(100)
    private int basicsOfCircuitTechnology;

    @Min(0)
    @Max(100)
    private int matAnal;

    @Min(0)
    @Max(100)
    private int courseWork;

    @Min(0)
    @Max(100)
    private int discreteMath;

    @Min(0)
    @Max(100)
    private int algorithmAndProgramming;

    @Min(0)
    @Max(100)
    private int english;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public double getRating() {
        return (this.ukrainianLanguage * 3
                + this.basicsOfCircuitTechnology * 5
                + this.matAnal * 5
                + this.courseWork * 2
                + this.discreteMath * 6
                + this.algorithmAndProgramming * 6
                + this.english * 3) / 30.0;
    }

    @Override
    public int compareTo(Student o) {
        return Double.compare(o.getRating(), this.getRating());
    }
}
