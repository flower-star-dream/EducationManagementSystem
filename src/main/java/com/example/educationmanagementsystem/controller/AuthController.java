package com.example.educationmanagementsystem.controller;


import com.example.educationmanagementsystem.common.PlainResult;
import com.example.educationmanagementsystem.dto.CheckRequest;
import com.example.educationmanagementsystem.dto.LoginRequestDto;
import com.example.educationmanagementsystem.dto.LoginResponseDto;
import com.example.educationmanagementsystem.dto.RegisterDto;
import com.example.educationmanagementsystem.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public PlainResult<String> register(@RequestBody RegisterDto registerDto) {
        userService.register(registerDto);
        return PlainResult.success("注册成功");
    }

    @PostMapping("/login")
    public PlainResult<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = userService.login(loginRequestDto);
        return PlainResult.success(loginResponseDto);
    }

    @PostMapping("/checkAffiliateId")
    public PlainResult<Boolean> checkAffiliateId(@RequestBody CheckRequest checkRequest) {
        Boolean bool = userService.checkAffiliateId(checkRequest.getUsertype(), checkRequest.getAffiliateId());
        return PlainResult.success(bool);
    }
}


