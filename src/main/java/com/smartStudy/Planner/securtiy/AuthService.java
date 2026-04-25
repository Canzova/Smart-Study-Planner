package com.smartStudy.Planner.securtiy;

import com.smartStudy.Planner.dto.Role;
import com.smartStudy.Planner.dto.UserLoginDtoRequest;
import com.smartStudy.Planner.dto.UserLoginDtoResponse;
import com.smartStudy.Planner.entity.User;
import com.smartStudy.Planner.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;

    public UserLoginDtoRequest saveUser(@Valid UserLoginDtoRequest userDto) {
        // Step 1 : Check if this user is already registered
        if(userRepository.existsByUsername(userDto.getUserName())){throw new RuntimeException("User Already Exists");}

        // Step 2 : Get the user
        User user = modelMapper.map(userDto,User.class);

        // Step 3 : Encrypt the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Give a role to the user
        user.getRoles().add(Role.GUEST);

        // Step 4 : Save the user
        user = userRepository.save(user);

        return modelMapper.map(user,UserLoginDtoRequest.class);
    }

    public UserLoginDtoResponse loginUser(@Valid UserLoginDtoRequest userDto) {

        // Step 2 : get the user
        User user = modelMapper.map(userDto,User.class);

        // Step 2 : Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        // Step 3 : If credentials are correct we will get the user
        user = (User) authentication.getPrincipal();

        // Step 4 : Get the jwt token form user
        String jwtToken = authUtil.generateJwtToke(user);

        return new UserLoginDtoResponse(user.getUsername(), jwtToken);
    }
}
