package com.danylo.braslavets.studentRating.Ratingsystem.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {
    public NotFoundException(final String message) {
        super(message);
    }
}
