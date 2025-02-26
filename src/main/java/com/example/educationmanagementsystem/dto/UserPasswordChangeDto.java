package com.example.educationmanagementsystem.dto;

import lombok.Data;

@Data
public class UserPasswordChangeDto {
    private Integer userId;
    private String oldPassword;
    private String newPassword;
}
