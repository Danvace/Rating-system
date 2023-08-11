package com.danylo.braslavets.studentRating.Ratingsystem.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GroupNotFoundException extends NotFoundException {
    public GroupNotFoundException(final String message) {
        super(message);
    }
}
