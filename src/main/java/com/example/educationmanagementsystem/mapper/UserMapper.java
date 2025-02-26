package com.example.educationmanagementsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.educationmanagementsystem.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User findOneByUsername(@Param("username") String username);

    User findOneByTypeAid(@Param("usertype") String usertype, @Param("affiliateId") String affiliateId);
}




