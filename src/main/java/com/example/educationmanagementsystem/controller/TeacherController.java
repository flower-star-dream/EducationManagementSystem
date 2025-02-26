package com.example.educationmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.common.PlainResult;
import com.example.educationmanagementsystem.dto.TeacherDto;
import com.example.educationmanagementsystem.service.TeacherService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    @GetMapping("/search")
    public PlainResult<IPage<TeacherDto>> getTeacherInfo(@RequestParam(required = false) Integer pageNum,
                                                     @RequestParam(required = false) Integer pageSize,
                                                     @RequestParam(required = false) String teacherId,
                                                     @RequestParam(required = false) String teacherName,
                                                     @RequestParam(required = false) String teacherGender,
                                                     @RequestParam(required = false) Integer teacherAge,
                                                     @RequestParam(required = false) String taroName){
        IPage<TeacherDto> teacher = teacherService.getTeacherInfo(pageNum, pageSize, teacherId, teacherName, teacherGender, teacherAge, taroName);
        return PlainResult.success(teacher);
    }

    @GetMapping("/searchOne")
    public PlainResult<TeacherDto> getOneTeacherInfo(@RequestParam String teacherId){
        TeacherDto teacher = teacherService.getOneTeacherInfo(teacherId);
        return PlainResult.success(teacher);
    }

    @PostMapping
    public PlainResult<String> addTeacher(@RequestBody TeacherDto teacherDto){
        teacherService.addTeacher(teacherDto);
        return PlainResult.success("success");
    }

    @PutMapping("/update/{id}")
    public PlainResult<String> updateTeacher(@PathVariable String id, @RequestBody TeacherDto teacherDto){
        teacherService.updateTeacher(id, teacherDto);
        return PlainResult.success("success");
    }

    @DeleteMapping("/delete/{id}")
    public PlainResult<String> deleteTeacher(@PathVariable String id){
        teacherService.deleteTeacher(id);
        return PlainResult.success("success");
    }

    @PostMapping("/deleteBatch")
    public PlainResult<String> deleteBatch(@RequestBody List<String> ids){
        teacherService.batchDelete(ids);
        return PlainResult.success("success");
    }

    @GetMapping("/check")
    public PlainResult<Boolean> getTeacherInfo(@RequestParam String taroName){
        Boolean bool = teacherService.getTeacherCheck(taroName);
        return PlainResult.success(bool);
    }
}
