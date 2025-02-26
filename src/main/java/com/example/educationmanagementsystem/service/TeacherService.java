package com.example.educationmanagementsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.dto.TeacherDto;

import java.util.List;

public interface TeacherService {
    /**
     * 添加教师信息
     * @param teacherDto
     */
    void addTeacher(TeacherDto teacherDto);

    /**
     * 更新教师信息
     * @param id
     * @param teacherDto
     */
    void updateTeacher(String id, TeacherDto teacherDto);

    /**
     * 删除教师信息
     * @param id
     */
    void deleteTeacher(String id);

    /**
     * 批量删除教师ID列表
     * @param ids
     */
    void batchDelete(List<String> ids);

    /**
     * 查询所有教师信息
     * @Param pageNum
     * @Param pageSize
     * @Param teacherId
     * @Param teacherName
     * @Param teacherGender
     * @Param teacherAge
     * @Param affiliate_class_name
     * @return IPage<TeacherDto> getTeacherInfo(Integer pageNum, Integer pageSize, String teacherId, String teacherName, String teacherGender, Integer teacherAge, String affiliate_class_name);
     */
    IPage<TeacherDto> getTeacherInfo(Integer pageNum, Integer pageSize, String teacherId, String teacherName, String teacherGender, Integer teacherAge, String affiliate_class_name);

    /**
     * 查询班级是否存在
     * @param className
     * @return Boolean getTeacherCheck(String className);
     */
    Boolean getTeacherCheck(String className);

    /**
     * 查询一个教师信息
     * @param teacherId
     * @return TeacherDto getOneTeacherInfo(String teacherId);
     */
    TeacherDto getOneTeacherInfo(String teacherId);
}
