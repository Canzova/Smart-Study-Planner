package com.smartStudy.Planner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDtoResponse {
//    private Integer userId;
    private String username;
    private String jwtToken;

}
