package com.example.educationmanagementsystem.mapper;

import com.example.educationmanagementsystem.domain.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author JAM
* @description 针对表【course】的数据库操作Mapper
* @createDate 2024-11-12 12:17:52
* @Entity com.example.educationmanagementsystem.domain.Course
*/
public interface CourseMapper extends MPJBaseMapper<Course> {

    Integer selectOneCidByCourseName(@Param("courseName") String courseName);

    String selectOneCourseNameByCid(@Param("courseId") Integer courseId);

    int selectBoundId(@Param("courseId") Integer courseId);

    int selectBoundIds(@Param("courseIds") List<Integer> ids);
}




