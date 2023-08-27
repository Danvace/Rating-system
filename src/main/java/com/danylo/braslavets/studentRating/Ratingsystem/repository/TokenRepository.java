package com.danylo.braslavets.studentRating.Ratingsystem.repository;

import com.danylo.braslavets.studentRating.Ratingsystem.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String jwt);

    @Query(value = """
            select t from Token t inner join Student u\s
            on t.student.id = u.id\s
            where u.id = :id and (t.expired = false or t.revoked = false)\s
            """)
    List<Token> findAllValidTokenByUser(Long id);

}
