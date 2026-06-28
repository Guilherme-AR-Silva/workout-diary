package com.workoutdiary.controller;

import com.workoutdiary.dto.WorkoutRequestDTO;
import com.workoutdiary.dto.WorkoutResponseDTO;
import com.workoutdiary.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints de gerenciamento de treinos.
 * Base URL: /ws/workout
 * Todos os endpoints exigem autenticação JWT.
 */
@RestController
@RequestMapping("/ws/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    /**
     * Cria um novo treino associado ao usuário autenticado.
     */
    @PostMapping
    public ResponseEntity<WorkoutResponseDTO> create(@Valid @RequestBody WorkoutRequestDTO request) {
        WorkoutResponseDTO response = workoutService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Lista todos os treinos do usuário autenticado.
     */
    @GetMapping
    public ResponseEntity<List<WorkoutResponseDTO>> findAll() {
        List<WorkoutResponseDTO> workouts = workoutService.findAllByCurrentUser();
        return ResponseEntity.ok(workouts);
    }

    /**
     * Busca um treino por ID, verificando que pertence ao usuário autenticado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutResponseDTO> findById(@PathVariable Long id) {
        WorkoutResponseDTO response = workoutService.findById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Atualiza um treino existente do usuário autenticado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody WorkoutRequestDTO request
    ) {
        WorkoutResponseDTO response = workoutService.update(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Exclui um treino do usuário autenticado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workoutService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
