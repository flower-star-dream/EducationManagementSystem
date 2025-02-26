package com.example.educationmanagementsystem.dto;

import lombok.Data;

@Data
public class UserInfoDto {
    private Integer id;
    private String username;
    private String password;
    private String usertype;
    private String affiliateId;
    private String affiliateName;
}
