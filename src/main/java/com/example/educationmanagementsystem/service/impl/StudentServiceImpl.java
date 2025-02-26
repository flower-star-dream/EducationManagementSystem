package com.example.educationmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.common.SystemException;
import com.example.educationmanagementsystem.domain.Class;
import com.example.educationmanagementsystem.domain.Student;
import com.example.educationmanagementsystem.domain.Teacher;
import com.example.educationmanagementsystem.dto.StudentDto;
import com.example.educationmanagementsystem.dto.TeacherDto;
import com.example.educationmanagementsystem.mapper.ClassMapper;
import com.example.educationmanagementsystem.mapper.LearningMapper;
import com.example.educationmanagementsystem.mapper.StudentMapper;
import com.example.educationmanagementsystem.service.StudentService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    @Resource
    private ClassMapper classMapper;

    @Resource
    private LearningMapper learningMapper;

    @Override
    public void addStudent(StudentDto studentDto) {
        Student student = convertToStudent(studentDto);
        if (studentMapper.selectById(student.getStudentId()) != null) {
            throw new SystemException(400, "该学生id已存在");
        }
        studentMapper.insert(student);
    }

    @Override
    public void updateStudent(String id, StudentDto studentDto) {
        Student student = convertToStudent(studentDto);
        student.setStudentId(id);
        studentMapper.updateById(student);
    }

    @Override
    public void deleteStudent(String id) {
        learningMapper.deleteBySid(id);
        studentMapper.deleteById(id);
    }

    @Override
    public void batchDelete(List<String> ids) {
        learningMapper.deleteBatchSids(ids);
        studentMapper.deleteBatchIds(ids);
    }

    @Override
    public IPage<StudentDto> getStudentInfo(Integer pageNum, Integer pageSize, String studentId, String studentName, String studentGender, Integer studentAge, String affiliate_class_name) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        MPJLambdaWrapper<Student> wrappers = new MPJLambdaWrapper<>(Student.class);
        if (StringUtils.hasText(studentId)) {
            wrappers.like(Student::getStudentId,studentId);
        }
        if (StringUtils.hasText(studentName)) {
            wrappers.like(Student::getStudentName,studentName);
        }
        if (StringUtils.hasText(studentGender)) {
            wrappers.eq(Student::getStudentGender,studentGender);
        }
        if (studentAge != null) {
            wrappers.like(Student::getStudentAge,studentAge);
        }
        if (StringUtils.hasText(affiliate_class_name)) {
            wrappers.innerJoin(Class.class, on -> on.eq(Student::getAffiliateClassId, Class::getClassId));
            wrappers.like(Class::getClassName, affiliate_class_name);
        }
        Page<Student> studentPage = studentMapper.selectJoinPage(new Page<>(pageNum, pageSize), Student.class, wrappers);
        if (studentPage == null){
            return null;
        }
        IPage<StudentDto> studentDtoIPage = studentPage.convert(this::toStudentDto);
        return studentDtoIPage;
    }

    @Override
    public StudentDto getOneStudentInfo(String studentId) {
        Student student = studentMapper.selectById(studentId);
        if (student == null){
            return null;
        }
        return toStudentDto(student);
    }

    @Override
    public Boolean getStudentCheck(String className) {
        return classMapper.selectOneAidByClassName(className) != null;
    }

    private Student convertToStudent(StudentDto studentDto) {
        Student student = new Student();
        student.setStudentId(studentDto.getId());
        student.setStudentName(studentDto.getName());
        student.setStudentGender(studentDto.getGender());
        student.setStudentAge(studentDto.getAge());
        student.setStudentPhone(studentDto.getPhone());
        student.setStudentBirthdate(studentDto.getBirthdate());
        Integer aid = studentDto.getAid();
        if (StringUtils.hasText(studentDto.getClassName())){
            aid = classMapper.selectOneAidByClassName(studentDto.getClassName());
        }
        if (aid == null) {
            return null;
        }
        student.setAffiliateClassId(aid);
        return student;
    }

    private StudentDto toStudentDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getStudentId());
        studentDto.setName(student.getStudentName());
        studentDto.setGender(student.getStudentGender());
        studentDto.setAge(student.getStudentAge());
        studentDto.setPhone(student.getStudentPhone());
        studentDto.setBirthdate(student.getStudentBirthdate());
        studentDto.setAid(student.getAffiliateClassId());
        String getName = classMapper.selectOneClassNameByAid(student.getAffiliateClassId());
        studentDto.setClassName(getName);
        return studentDto;
    }
}
