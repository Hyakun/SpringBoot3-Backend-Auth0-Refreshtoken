package com.example.demo.datamodels;

import com.example.demo.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorCodeRepository extends JpaRepository<ProfessorCode, Integer> {
    Optional<ProfessorCode> findByCode(String Code);

    @Transactional
    int deleteById(int Id);
}
