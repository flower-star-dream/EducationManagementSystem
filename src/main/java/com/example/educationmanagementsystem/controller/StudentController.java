package com.example.educationmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.common.PlainResult;
import com.example.educationmanagementsystem.dto.StudentDto;
import com.example.educationmanagementsystem.dto.TeacherDto;
import com.example.educationmanagementsystem.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    @GetMapping("/search")
    public PlainResult<IPage<StudentDto>> getStudentInfo(@RequestParam(required = false) Integer pageNum,
                                                     @RequestParam(required = false) Integer pageSize,
                                                     @RequestParam(required = false) String studentId,
                                                     @RequestParam(required = false) String studentName,
                                                     @RequestParam(required = false) String studentGender,
                                                     @RequestParam(required = false) Integer studentAge,
                                                     @RequestParam(required = false) String className){
        IPage<StudentDto> student = studentService.getStudentInfo(pageNum, pageSize, studentId, studentName, studentGender, studentAge, className);
        return PlainResult.success(student);
    }

    @GetMapping("/searchOne")
    public PlainResult<StudentDto> getOneStudentInfo(@RequestParam String studentId){
        StudentDto student = studentService.getOneStudentInfo(studentId);
        return PlainResult.success(student);
    }

    @PostMapping
    public PlainResult<String> addStudent(@RequestBody StudentDto studentDto){
        studentService.addStudent(studentDto);
        return PlainResult.success("success");
    }

    @PutMapping("/update/{id}")
    public PlainResult<String> updateStudent(@PathVariable String id, @RequestBody StudentDto studentDto){
        studentService.updateStudent(id, studentDto);
        return PlainResult.success("success");
    }

    @DeleteMapping("/delete/{id}")
    public PlainResult<String> deleteStudent(@PathVariable String id){
        studentService.deleteStudent(id);
        return PlainResult.success("success");
    }

    @PostMapping("/deleteBatch")
    public PlainResult<String> deleteBatch(@RequestBody List<String> ids){
        studentService.batchDelete(ids);
        return PlainResult.success("success");
    }

    @GetMapping("/check")
    public PlainResult<Boolean> getStudentInfo(@RequestParam String className){
        Boolean bool = studentService.getStudentCheck(className);
        return PlainResult.success(bool);
    }
}
