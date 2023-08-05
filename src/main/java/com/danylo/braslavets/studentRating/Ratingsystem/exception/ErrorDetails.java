package com.danylo.braslavets.studentRating.Ratingsystem.exception;

import org.springframework.http.HttpStatus;


public record ErrorDetails(String message,
                           HttpStatus status) {
}

