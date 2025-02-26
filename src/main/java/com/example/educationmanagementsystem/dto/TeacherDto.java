package com.example.educationmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class TeacherDto {
    private String id;
    private String name;
    private String gender;
    private Integer age;
    private String phone;
    private String title;
    private String degree;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    private Integer aid;
    private String taroName;

}
