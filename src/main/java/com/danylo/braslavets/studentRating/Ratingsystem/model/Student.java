package com.danylo.braslavets.studentRating.Ratingsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Student implements Comparable<Student> {

    public static final int MAX_MARK = 100;
    public static final int MIN_MARK = 0;

    @NotEmpty(message = "Student name should not be empty")
    @NotNull(message = "Student name should not be null")
    private String studentName;

    @ManyToOne
    @JsonBackReference
    private Group group;

    @Min(value = MIN_MARK, message = "Mark should not be less than 0")
    @Max(value = MAX_MARK, message = "Mark should not be greater than 100")
    private int ukrainianLanguage;

    @Min(value = MIN_MARK, message = "Mark should not be less than 0")
    @Max(value = MAX_MARK, message = "Mark should not be greater than 100")
    private int basicsOfCircuitTechnology;

    @Min(value = MIN_MARK, message = "Mark should not be less than 0")
    @Max(value = MAX_MARK, message = "Mark should not be greater than 100")
    private int matAnal;

    @Min(value = MIN_MARK, message = "Mark should not be less than 0")
    @Max(value = MAX_MARK, message = "Mark should not be greater than 100")
    private int courseWork;

    @Min(value = MIN_MARK, message = "Mark should not be less than 0")
    @Max(value = MAX_MARK, message = "Mark should not be greater than 100")
    private int discreteMath;

    @Min(value = MIN_MARK, message = "Mark should not be less than 0")
    @Max(value = MAX_MARK, message = "Mark should not be greater than 100")
    private int algorithmAndProgramming;

    @Min(value = MIN_MARK, message = "Mark should not be less than 0")
    @Max(value = MAX_MARK, message = "Mark should not be greater than 100")
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
    public int compareTo(final Student o) {
        return Double.compare(o.getRating(), this.getRating());
    }

}
