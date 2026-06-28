package com.workoutdiary.service;

import com.workoutdiary.dto.WorkoutRequestDTO;
import com.workoutdiary.dto.WorkoutResponseDTO;
import com.workoutdiary.entity.User;
import com.workoutdiary.entity.Workout;
import com.workoutdiary.exception.ResourceNotFoundException;
import com.workoutdiary.exception.UnauthorizedResourceAccessException;
import com.workoutdiary.repository.WorkoutRepository;
import com.workoutdiary.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final SecurityUtils securityUtils;

    @Transactional
    public WorkoutResponseDTO create(WorkoutRequestDTO request) {
        User currentUser = securityUtils.getCurrentUser();

        Workout workout = Workout.builder()
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .grupoMuscular(request.getGrupoMuscular())
                .carga(request.getCarga())
                .observacao(request.getObservacao())
                .user(currentUser)
                .build();

        Workout saved = workoutRepository.save(workout);
        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<WorkoutResponseDTO> findAllByCurrentUser() {
        User currentUser = securityUtils.getCurrentUser();
        return workoutRepository.findByUser(currentUser)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public WorkoutResponseDTO findById(Long id) {
        User currentUser = securityUtils.getCurrentUser();
        Workout workout = findWorkoutOrThrow(id);
        validateOwnership(workout, currentUser);
        return toResponseDTO(workout);
    }

    @Transactional
    public WorkoutResponseDTO update(Long id, WorkoutRequestDTO request) {
        User currentUser = securityUtils.getCurrentUser();
        Workout workout = findWorkoutOrThrow(id);
        validateOwnership(workout, currentUser);

        workout.setNome(request.getNome());
        workout.setDescricao(request.getDescricao());
        workout.setGrupoMuscular(request.getGrupoMuscular());
        workout.setCarga(request.getCarga());
        workout.setObservacao(request.getObservacao());

        Workout updated = workoutRepository.save(workout);
        return toResponseDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        User currentUser = securityUtils.getCurrentUser();
        Workout workout = findWorkoutOrThrow(id);
        validateOwnership(workout, currentUser);
        workoutRepository.delete(workout);
    }

    private Workout findWorkoutOrThrow(Long id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found"));
    }

    private void validateOwnership(Workout workout, User currentUser) {
        if (!workout.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedResourceAccessException("Access denied to this workout");
        }
    }

    private WorkoutResponseDTO toResponseDTO(Workout workout) {
        return WorkoutResponseDTO.builder()
                .id(workout.getId())
                .nome(workout.getNome())
                .descricao(workout.getDescricao())
                .grupoMuscular(workout.getGrupoMuscular())
                .carga(workout.getCarga())
                .observacao(workout.getObservacao())
                .dataCriacao(workout.getDataCriacao())
                .build();
    }
}
