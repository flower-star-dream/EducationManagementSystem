package com.example.educationmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.common.PlainResult;
import com.example.educationmanagementsystem.dto.CourseDto;
import com.example.educationmanagementsystem.service.CourseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Resource
    private CourseService courseService;

    @GetMapping("/search")
    public PlainResult<IPage<CourseDto>> getCourseInfo(@RequestParam(required = false) Integer pageNum,
                                                     @RequestParam(required = false) Integer pageSize,
                                                     @RequestParam(required = false) Integer courseId,
                                                     @RequestParam(required = false) String courseName,
                                                     @RequestParam(required = false) String taroName){
        IPage<CourseDto> course = courseService.getCourseInfo(pageNum, pageSize, courseId, courseName, taroName);
        return PlainResult.success(course);
    }

    @PostMapping
    public PlainResult<String> addCourse(@RequestBody CourseDto courseDto){
        courseService.addCourse(courseDto);
        return PlainResult.success("success");
    }

    @PutMapping("/{id}")
    public PlainResult<String> updateCourse(@PathVariable Integer id, @RequestBody CourseDto courseDto){
        courseService.updateCourse(id, courseDto);
        return PlainResult.success("success");
    }

    @DeleteMapping("/{id}")
    public PlainResult<String> deleteCourse(@PathVariable Integer id){
        courseService.deleteCourse(id);
        return PlainResult.success("success");
    }

    @PostMapping("/deleteBatch")
    public PlainResult<String> deleteBatch(@RequestBody List<Integer> ids){
        courseService.batchDelete(ids);
        return PlainResult.success("success");
    }

    @GetMapping("/check")
    public PlainResult<Boolean> getCourseInfo(@RequestParam String taroName){
        Boolean bool = courseService.getCourseCheck(taroName);
        return PlainResult.success(bool);
    }
}
