package com.example.educationmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.common.PlainResult;
import com.example.educationmanagementsystem.dto.MajorDto;
import com.example.educationmanagementsystem.service.MajorService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/major")
public class MajorController {
    @Resource
    private MajorService majorService;

    @GetMapping("/search")
    public PlainResult<IPage<MajorDto>> getMajorInfo(@RequestParam(required = false) Integer pageNum,
                                                     @RequestParam(required = false) Integer pageSize,
                                                     @RequestParam(required = false) Integer majorId,
                                                     @RequestParam(required = false) String majorName,
                                                     @RequestParam(required = false) String facultyName){
        IPage<MajorDto> major = majorService.getMajorInfo(pageNum, pageSize, majorId, majorName, facultyName);
        return PlainResult.success(major);
    }

    @PostMapping
    public PlainResult<String> addMajor(@RequestBody MajorDto majorDto){
        majorService.addMajor(majorDto);
        return PlainResult.success("success");
    }

    @PutMapping("/{id}")
    public PlainResult<String> updateMajor(@PathVariable Integer id, @RequestBody MajorDto majorDto){
        majorService.updateMajor(id, majorDto);
        return PlainResult.success("success");
    }

    @DeleteMapping("/{id}")
    public PlainResult<String> deleteMajor(@PathVariable Integer id){
        majorService.deleteMajor(id);
        return PlainResult.success("success");
    }

    @PostMapping("/deleteBatch")
    public PlainResult<String> deleteBatch(@RequestBody List<Integer> ids){
        majorService.batchDelete(ids);
        return PlainResult.success("success");
    }

    @GetMapping("/check")
    public PlainResult<Boolean> getMajorInfo(@RequestParam String facultyName){
        Boolean bool = majorService.getMajorCheck(facultyName);
        return PlainResult.success(bool);
    }
}
