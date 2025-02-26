package com.example.educationmanagementsystem.mapper;

import com.example.educationmanagementsystem.domain.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author JAM
* @description 针对表【teacher】的数据库操作Mapper
* @createDate 2024-11-12 12:17:52
* @Entity com.example.educationmanagementsystem.domain.Teacher
*/
public interface TeacherMapper extends MPJBaseMapper<Teacher> {

    String selectOneTeacherNameByTid(@Param("teacherId") String teacherId);

    String selectOneTidByTeacherName(@Param("teacherName")String teacherName);

    void unboundId(@Param("teacherId") String id);

    void unboundIds(@Param("teacherIds") List<String> ids);
}




