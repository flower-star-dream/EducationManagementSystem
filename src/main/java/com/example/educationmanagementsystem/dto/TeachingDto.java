package com.example.educationmanagementsystem.dto;

import lombok.Data;

@Data
public class TeachingDto {
    private String tid;
    private String teacherName;
    private Integer cid;
    private String courseName;
    private String teachingType;
    private String learningType;
    private Integer credit;
    private Integer time;
    private String room;
    private Integer status;
}
