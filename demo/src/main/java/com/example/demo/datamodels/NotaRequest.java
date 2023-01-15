package com.example.demo.datamodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotaRequest {
    private Integer nota;
    private Integer idUser;
    private Integer idMaterie;
}
