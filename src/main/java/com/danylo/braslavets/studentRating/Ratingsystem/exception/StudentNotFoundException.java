package com.danylo.braslavets.studentRating.Ratingsystem.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StudentNotFoundException extends NotFoundException {
    public StudentNotFoundException(final String message) {
        super(message);
    }

}
