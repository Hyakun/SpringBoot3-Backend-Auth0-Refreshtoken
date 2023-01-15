package com.example.demo.datamodels;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface NotaRepository extends JpaRepository<Nota, Integer> {
    List<Nota> findAllByUser(User user);
    List<Nota> findAllByMaterie(Materie materie);
}
