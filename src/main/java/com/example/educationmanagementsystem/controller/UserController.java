package com.example.educationmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.common.PlainResult;
import com.example.educationmanagementsystem.dto.LoginResponseDto;
import com.example.educationmanagementsystem.dto.UserInfoDto;
import com.example.educationmanagementsystem.dto.UserPasswordChangeDto;
import com.example.educationmanagementsystem.service.UserService;
import com.example.educationmanagementsystem.util.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private UserService userService;

    @GetMapping
    public PlainResult<UserInfoDto> getUserInfo(@RequestParam String token) {
        String username = jwtUtil.extractUsername(token);
        UserInfoDto userInfoDto = userService.getUserByUsername(username);
        return PlainResult.success(userInfoDto);
    }

    @GetMapping("/search")
    public PlainResult<IPage<UserInfoDto>> searchUser(@RequestParam(required = false) Integer pageNum,
                                                      @RequestParam(required = false) Integer pageSize,
                                                      @RequestParam(required = false) String name) {
        IPage<UserInfoDto> userInfoDtoIPage = userService.searchUser(pageNum, pageSize, name);
        return PlainResult.success(userInfoDtoIPage);

    }

    @PutMapping("/update/{id}")
    public PlainResult<LoginResponseDto> updateUser(@PathVariable Integer id, @RequestBody UserInfoDto userInfoDto) {
        LoginResponseDto updateToken = userService.updateUser(id, userInfoDto);
        return PlainResult.success(updateToken);
    }
    @DeleteMapping("/delete/{id}")
    public PlainResult<String> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return PlainResult.success("success");
    }

    @PostMapping("/deleteBatch")
    public PlainResult<String> deleteBatch(@RequestBody List<Integer> ids) {
        userService.batchDelete(ids);
        return PlainResult.success("success");
    }


    @PostMapping("/password")
    public PlainResult<String> updateUserPassword(@RequestBody UserPasswordChangeDto userPasswordChangeDto) {
        userService.updateUserPassword(userPasswordChangeDto);
        return PlainResult.success("success");
    }

}
