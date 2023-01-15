package com.example.demo.datamodels;

import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_studentdetails")
public class StudentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String NrTelefon;
    private String Adresa;
    private String Cnp;
    private String NumeMama;
    private String NumeTata;
    private String NrTelefonMama;
    private String NrTelefonTata;

}
