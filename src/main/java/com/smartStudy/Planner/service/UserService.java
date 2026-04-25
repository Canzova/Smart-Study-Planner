package com.smartStudy.Planner.service;

import com.smartStudy.Planner.dto.UserLoginDtoRequest;
import com.smartStudy.Planner.dto.UserLoginDtoResponse;
import jakarta.validation.Valid;

public interface UserService {
    UserLoginDtoResponse saveUser(@Valid UserLoginDtoRequest userDto);
}
