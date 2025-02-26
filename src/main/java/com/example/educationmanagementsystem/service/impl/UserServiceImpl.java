package com.example.educationmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.common.SystemException;
import com.example.educationmanagementsystem.domain.Student;
import com.example.educationmanagementsystem.dto.*;
import com.example.educationmanagementsystem.domain.User;
import com.example.educationmanagementsystem.mapper.StudentMapper;
import com.example.educationmanagementsystem.mapper.TeacherMapper;
import com.example.educationmanagementsystem.mapper.UserMapper;
import com.example.educationmanagementsystem.service.UserService;
import com.example.educationmanagementsystem.util.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public void register(RegisterDto registerDto) {
        User originUser = userMapper.findOneByUsername(registerDto.getUsername());
        if (originUser != null) {
            throw new SystemException(400, "用户名已存在");
        }
        User originUser2 = userMapper.findOneByTypeAid(registerDto.getUsertype(),registerDto.getAffiliateId());
        if (originUser2 != null) {
            throw new SystemException(400, "该id已绑定"+registerDto.getUsertype()+"类用户");
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setUsertype(registerDto.getUsertype());
        user.setAffiliateId(registerDto.getAffiliateId());
        userMapper.insert(user);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken
                        (loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken(jwtUtil.generateToken(loginRequestDto.getUsername()));
        return loginResponseDto;
    }

    @Override
    public UserInfoDto getUserByUsername(String username) {
        User user = userMapper.findOneByUsername(username);
        if (user == null) {
            return null;
        }
        return toUserInfoDto(user);
    }

    private UserInfoDto toUserInfoDto(User user) {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(user.getId());
        userInfoDto.setUsername(user.getUsername());
        userInfoDto.setUsertype(user.getUsertype());
        userInfoDto.setAffiliateId(user.getAffiliateId());
        if (StringUtils.hasText(user.getAffiliateId())) {
            if (user.getUsertype().equals("教师")) {
                userInfoDto.setAffiliateName(teacherMapper.selectOneTeacherNameByTid(user.getAffiliateId()));
            }else if (user.getUsertype().equals("学生")) {
                userInfoDto.setAffiliateName(studentMapper.selectOneStudentNameBySid(user.getAffiliateId()));
            }
        }
        return userInfoDto;
    }

    @Override
    public LoginResponseDto updateUser(Integer id, UserInfoDto userInfoDto) {
        User user = toUser(id, userInfoDto);
        userMapper.updateById(user);
        LoginResponseDto updateToken = new LoginResponseDto();
        updateToken.setToken(jwtUtil.generateToken(user.getUsername()));
        return updateToken;
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteById(id);

    }

    @Override
    public void batchDelete(List<Integer> ids) {
        userMapper.deleteBatchIds(ids);
    }

    private User toUser(Integer id, UserInfoDto userInfoDto) {
        User user = new User();
        user.setId(id);
        if (userInfoDto.getUsername().length() != 0) {
            user.setUsername(userInfoDto.getUsername());
        }
        if (userInfoDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        }
        return user;
    }

    @Override
    public void updateUserPassword(UserPasswordChangeDto userPasswordChangeDto) {
        if (userPasswordChangeDto.getUserId() == null) {
            throw new SystemException(400, "用户ID不能为空");
        }
        if (!StringUtils.hasText(userPasswordChangeDto.getOldPassword())) {
            throw new SystemException(400, "旧密码不能为空");
        }
        if (!StringUtils.hasText(userPasswordChangeDto.getNewPassword())) {
            throw new SystemException(400, "新密码不能为空");
        }
        if (Objects.equals(userPasswordChangeDto.getOldPassword(), userPasswordChangeDto.getNewPassword())) {
            throw new SystemException(400, "新密码不能与旧密码相同");
        }
        User user = userMapper.selectById(userPasswordChangeDto.getUserId());
        if (user == null) {
            throw new SystemException(400, "用户不存在");
        }
        if (!passwordEncoder.matches(userPasswordChangeDto.getOldPassword(), user.getPassword())) {
            throw new SystemException(500, "旧密码错误");
        }
        user.setPassword(passwordEncoder.encode(userPasswordChangeDto.getNewPassword()));
        userMapper.updateById(user);
    }

    @Override
    public IPage<UserInfoDto> searchUser(Integer pageNum, Integer pageSize, String name) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        LambdaQueryWrapper<User> wrappers = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            wrappers.like(User::getUsername, name);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrappers);
        if (userPage == null) {
            return null;
        }
        return userPage.convert(this::toUserInfoDto);
    }

    @Override
    public Boolean checkAffiliateId(String usertype, String affiliateId) {
        if ("教师".equals(usertype)) {
            return teacherMapper.selectById(affiliateId) != null;
        }else if ("学生".equals(usertype)) {
            return studentMapper.selectById(affiliateId) != null;
        }else if ("管理员".equals(usertype)) {
            return true;
        }else {
            return false;
        }
    }

}
