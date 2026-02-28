package com.smartStudy.Planner.controller;

import com.smartStudy.Planner.dto.GoalDto;
import com.smartStudy.Planner.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goal")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @PostMapping("/set/user/{userId}")
    public ResponseEntity<GoalDto> setGoal(@RequestBody @Valid GoalDto goal, @PathVariable Long userId) {
        GoalDto savedGoal = goalService.saveGoal(goal, userId);
        return ResponseEntity.ok(savedGoal);
    }


}
