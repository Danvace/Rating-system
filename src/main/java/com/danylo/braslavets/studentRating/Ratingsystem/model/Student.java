package com.danylo.braslavets.studentRating.Ratingsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Comparable<Student>, UserDetails {

    public static final int MAX_MARK = 100;
    public static final int MIN_MARK = 0;

    @Email(message = "bad format")
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
