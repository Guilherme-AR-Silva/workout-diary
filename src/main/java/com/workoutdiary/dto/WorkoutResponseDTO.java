package com.workoutdiary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String grupoMuscular;
    private Integer carga;
    private String observacao;
    private LocalDateTime dataCriacao;
}
