package com.workoutdiary.repository;

import com.workoutdiary.entity.User;
import com.workoutdiary.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByUser(User user);
}
