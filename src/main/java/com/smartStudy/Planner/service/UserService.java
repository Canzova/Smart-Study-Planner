package com.smartStudy.Planner.service;

import com.smartStudy.Planner.dto.UserDto;
import jakarta.validation.Valid;

public interface UserService {
    UserDto saveUser(@Valid UserDto userDto);
}
