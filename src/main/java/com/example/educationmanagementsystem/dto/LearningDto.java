package com.example.educationmanagementsystem.dto;

import lombok.Data;

@Data
public class LearningDto {
    private String sid;
    private String studentName;
    private Integer cid;
    private String courseName;
    private String learningType;
    private Integer score;
}
