package com.example.educationmanagementsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.dto.CourseDto;

import java.util.List;

public interface CourseService {
    /**
     * 新建课程
     * @param courseDto
     */
    void addCourse(CourseDto courseDto);

    /**
     * 更新课程
     * @param id
     * @param courseDto
     */
    void updateCourse(Integer id, CourseDto courseDto);

    /**
     * 删除课程
     * @param id
     */
    void deleteCourse(Integer id);

    /**
     * 批量删除课程ID列表
     * @param ids
     * */
    void batchDelete(List<Integer> ids);

    /**
     * 查询课程
     * @Param pageNum
     * @Param pageSize
     * @param courseId
     * @param courseName
     * @param affiliate_faculty_name
     */
    IPage<CourseDto> getCourseInfo(Integer pageNum, Integer pageSize, Integer courseId, String courseName, String affiliate_faculty_name);

    /**
     * 查询教研室是否存在
     * @param taroName
     * @return Boolean getCourseCheck(String taroName);
    */
    Boolean getCourseCheck(String taroName);

}
