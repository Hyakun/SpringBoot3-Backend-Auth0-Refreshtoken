package com.example.demo.datamodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetailsRequest {
    private String nrTelefon;
    private String adresa;
    private String cnp;
    private String numeMama;
    private String numeTata;
    private String nrTelefonMama;
    private String nrTelefonTata;

}
