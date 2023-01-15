package com.example.demo.datamodels;

import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_nota")
public class Nota {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer nota;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "materie_id", referencedColumnName = "id")
    private Materie materie;

}
