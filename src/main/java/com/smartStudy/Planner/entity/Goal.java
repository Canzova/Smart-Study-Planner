package com.smartStudy.Planner.entity;

import com.smartStudy.Planner.constants.PRIORITY;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer goalId;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private PRIORITY priority;
    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
