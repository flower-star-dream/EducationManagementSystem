package com.example.educationmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.common.PlainResult;
import com.example.educationmanagementsystem.dto.ClassDto;
import com.example.educationmanagementsystem.service.ClassService;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class")
public class ClassController {

    @Resource
    private ClassService classService;

    @GetMapping("/search")
    public PlainResult<IPage<ClassDto>> getClassInfo(@RequestParam(required = false) Integer pageNum,
                                                     @RequestParam(required = false) Integer pageSize,
                                                     @RequestParam(required = false) Integer classId,
                                                     @RequestParam(required = false) String className,
                                                     @RequestParam(required = false) String majorName){

        IPage<ClassDto> classvar = classService.getClassInfo(pageNum, pageSize, classId, className, majorName);
        return PlainResult.success(classvar);
    }

    @PostMapping
    public PlainResult<String> addClass(@RequestBody ClassDto classDto){
        classService.addClass(classDto);
        return PlainResult.success("success");
    }

    @PutMapping("/{id}")
    public PlainResult<String> updateClass(@PathVariable Integer id, @RequestBody ClassDto classDto){
        classService.updateClass(id, classDto);
        return PlainResult.success("success");
    }
    
    @DeleteMapping("/{id}")
    public PlainResult<String> deleteClass(@PathVariable Integer id){
        classService.deleteClass(id);
        return PlainResult.success("success");
    }

    @PostMapping("/deleteBatch")
    public PlainResult<String> deleteBatch(@RequestBody List<Integer> ids){
        classService.batchDelete(ids);
        return PlainResult.success("success");
    }

    @GetMapping("/check")
    public PlainResult<Boolean> getMajorInfo(@RequestParam String majorName){
        Boolean bool = classService.getClassCheck(majorName);
        return PlainResult.success(bool);
    }
}
