package com.example.educationmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.common.PlainResult;
import com.example.educationmanagementsystem.dto.FacultyDto;
import com.example.educationmanagementsystem.service.FacultyService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    @Resource
    private FacultyService facultyService;

    @GetMapping("/search")
    public PlainResult<IPage<FacultyDto>> getFacultyInfo(@RequestParam(required = false) Integer pageNum,
                                                        @RequestParam(required = false) Integer pageSize,
                                                        @RequestParam(required = false) Integer facultyId,
                                                        @RequestParam(required = false) String facultyName){

        IPage<FacultyDto> faculty = facultyService.getFacultyInfo(pageNum, pageSize, facultyId, facultyName);
        return PlainResult.success(faculty);
    }

    @PostMapping
    public PlainResult<String> addFaculty(@RequestBody FacultyDto facultyDto){
        facultyService.addFaculty(facultyDto);
        return PlainResult.success("success");
    }

    @PutMapping("/{id}")
    public PlainResult<String> updateFaculty(@PathVariable Integer id, @RequestBody FacultyDto facultyDto){
        facultyService.updateFaculty(id, facultyDto);
        return PlainResult.success("success");
    }

    @DeleteMapping("/{id}")
    public PlainResult<String> deleteFaculty(@PathVariable Integer id){
        facultyService.deleteFaculty(id);
        return PlainResult.success("success");
    }

    @PostMapping("/deleteBatch")
    public PlainResult<String> deleteBatch(@RequestBody List<Integer> ids){
        facultyService.batchDelete(ids);
        return PlainResult.success("success");
    }

}
