package com.example.educationmanagementsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    /**
     * 用户注册
     * @param registerDto
     */
    void register(RegisterDto registerDto);


    /**
     * 用户登录
     * @param loginRequestDto
     * @return
     */
    LoginResponseDto login(LoginRequestDto loginRequestDto);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    UserInfoDto getUserByUsername(String username);

    /**
     * 用户搜索
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    IPage<UserInfoDto> searchUser(Integer pageNum, Integer pageSize, String name);

    /**
     * 更新用户信息
     * @param userInfoDto
     */
    LoginResponseDto updateUser(Integer id, UserInfoDto userInfoDto);

    /**
     * 删除用户
     * @param id
     */
    void deleteUser(Integer id);

    /**
     * 批量删除用户
     * @param ids
     */
    void batchDelete(List<Integer> ids);

    /**
     * 更新用户密码
     * @param userPasswordChangeDto
     */
    void updateUserPassword(UserPasswordChangeDto userPasswordChangeDto);

    /**
     * 验证学号/教师号是否存在
     * @param usertype
     * @param affiliateId
     * @return
     */
    Boolean checkAffiliateId(String usertype, String affiliateId);
}
