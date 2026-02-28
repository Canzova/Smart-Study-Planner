package com.smartStudy.Planner.dto;

import com.smartStudy.Planner.constants.PRIORITY;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class GoalDto {

    private Integer goalId;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 20, message = "Title has to be in between 3 to 20 characters.")
    private String title;

//    @NotBlank(message = "Description cannot be blank")
    @Size(max = 30, message = "Description cannot be more than 30 characters")
    private String description;

    @NotNull(message = "Priority cannot be null")
    private PRIORITY priority;

    @NotNull(message = "Deadline cannot be null")
    private LocalDate deadline;

}
