package com.smartStudy.Planner.service;

import com.smartStudy.Planner.dto.GoalDto;
import com.smartStudy.Planner.entity.Goal;
import com.smartStudy.Planner.entity.User;
import com.smartStudy.Planner.repositories.GoalRepository;
import com.smartStudy.Planner.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService{

    private final ModelMapper modelMapper;
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public GoalDto saveGoal(GoalDto goal, Long userId) {
        // Step 1 : Convert this dto into entity
        Goal goalEntity = modelMapper.map(goal, Goal.class);

        // Check if this user exists in to the db
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));

        goalEntity.setUser(user);  // Save user into goal as well

        // This user has been taken out from db so it is in persistence state
        user.getGoals().add(goalEntity);   // Now you dont need to save the user, also you do not need to save the goal

        // Because when jpa will try to save user it will see that user has a goal and due to cascading firstly it will save goal and then save user

        goalEntity = goalRepository.save(goalEntity); // Saving explicitly just to get the id
        return modelMapper.map(goalEntity,GoalDto.class);

    }

}
