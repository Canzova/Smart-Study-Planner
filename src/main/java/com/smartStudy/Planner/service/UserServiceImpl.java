package com.smartStudy.Planner.service;

import com.smartStudy.Planner.dto.UserDto;
import com.smartStudy.Planner.entity.User;
import com.smartStudy.Planner.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public UserDto saveUser(UserDto userDto) {
        // Step 1 : Get the entity out of this dto
        User user = modelMapper.map(userDto, User.class);

        // Step 2 : Save it into db
        User SavedUser = userRepository.save(user);

        // Step 3 : Convert back into dto and return
        return modelMapper.map(SavedUser,UserDto.class);
    }

}
