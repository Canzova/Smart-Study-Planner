package com.smartStudy.Planner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDtoRequest {

    private Long id;

    @NotBlank(message = "User Name cannot be empty.")
    @Size(min = 3, max = 20, message = "User Name must be in between 3 to 20 characters.")
    private String userName;

    //@NotNull(message = "Email cannot be null")
    @Email(message = "Email should be in correct format")
    private String email;

    @NotBlank(message = "Password cannot be empty.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
