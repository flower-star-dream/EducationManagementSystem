package com.example.educationmanagementsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.dto.StudentDto;

import java.util.List;

public interface StudentService {
    /**
     * 添加学生信息
     * @param studentDto
     */
    void addStudent(StudentDto studentDto);

    /**
     * 更新学生信息
     * @param id
     * @param studentDto
     */
    void updateStudent(String id, StudentDto studentDto);

    /**
     * 删除学生信息
     * @param id
     */
    void deleteStudent(String id);

    /**
     * 批量删除学生ID列表
     * @param ids
     */
    void batchDelete(List<String> ids);

    /**
     * 查询所有学生信息
     * @Param pageNum
     * @Param pageSize
     * @Param studentId
     * @Param studentName
     * @Param studentGender
     * @Param studentAge
     * @Param affiliate_class_name
     * @return IPage<StudentDto> getStudentInfo(Integer pageNum, Integer pageSize, String studentId, String studentName, String studentGender, Integer studentAge, String affiliate_class_name);
     */
    IPage<StudentDto> getStudentInfo(Integer pageNum, Integer pageSize, String studentId, String studentName, String studentGender, Integer studentAge, String affiliate_class_name);

    /**
     * 查询班级是否存在
     * @param className
     * @return Boolean getStudentCheck(String className);
     */
    Boolean getStudentCheck(String className);

    /**
     * 查询单个学生信息
     * @param studentId
     * @return StudentDto getOneStudentInfo(String studentId);
     */
    StudentDto getOneStudentInfo(String studentId);
}
