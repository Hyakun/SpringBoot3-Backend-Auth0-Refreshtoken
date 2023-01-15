package com.example.demo.datamodels;


import com.example.demo.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentDetailsRepository extends JpaRepository<StudentDetails, Integer> {
    Optional<StudentDetails> findByUser(User user);

    @Transactional
    int deleteByUser(User user);
}

