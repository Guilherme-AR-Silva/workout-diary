package com.workoutdiary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutRequestDTO {

    @NotBlank(message = "Nome is required")
    private String nome;

    private String descricao;

    @NotBlank(message = "Grupo muscular is required")
    private String grupoMuscular;

    @NotNull(message = "Carga is required")
    private Integer carga;

    private String observacao;
}
