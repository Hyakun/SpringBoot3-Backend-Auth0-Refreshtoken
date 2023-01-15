package com.example.demo.datamodels;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterieRepository extends JpaRepository<Materie, Integer> {
    Optional<Materie> findBynumeMaterie(String numeMaterie);

    Optional<Materie> findById(Integer id);

    @Transactional
    int deleteById(int Id);
}
