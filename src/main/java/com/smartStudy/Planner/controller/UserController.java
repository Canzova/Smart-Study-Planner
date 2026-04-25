package com.smartStudy.Planner.controller;

import com.smartStudy.Planner.dto.UserLoginDtoRequest;
import com.smartStudy.Planner.dto.UserLoginDtoResponse;
import com.smartStudy.Planner.securtiy.AuthService;
import com.smartStudy.Planner.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping("/register")
    ResponseEntity<UserLoginDtoRequest> register(@RequestBody @Valid UserLoginDtoRequest userDto) {
        UserLoginDtoRequest savedUserDto = authService.saveUser(userDto);
        return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginDtoResponse> login(@RequestBody @Valid UserLoginDtoRequest userDto) {
        UserLoginDtoResponse loggedInUser = authService.loginUser(userDto);
        return new ResponseEntity<>(loggedInUser,HttpStatus.OK);
    }

}
