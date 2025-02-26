package com.example.educationmanagementsystem.mapper;

import com.example.educationmanagementsystem.domain.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author JAM
* @description 针对表【student】的数据库操作Mapper
* @createDate 2024-11-12 12:17:52
* @Entity com.example.educationmanagementsystem.domain.Student
*/
public interface StudentMapper extends MPJBaseMapper<Student> {
    String selectOneStudentNameBySid(@Param("StudentId") String StudentId);

    String selectOneSidByStudentName(@Param("studentName") String studentName);
}




