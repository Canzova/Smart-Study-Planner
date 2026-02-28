package com.smartStudy.Planner.service;

import com.smartStudy.Planner.dto.GoalDto;
import jakarta.validation.Valid;

public interface GoalService {
    GoalDto saveGoal(@Valid GoalDto goal, Long userId);
}
