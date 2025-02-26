package com.example.educationmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.common.PlainResult;
import com.example.educationmanagementsystem.dto.LearnTeacherDto;
import com.example.educationmanagementsystem.service.LearnTeacherService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/learnTeacher")
public class LearnTeacherController {

    @Resource
    private LearnTeacherService learnTeacherService;

    @GetMapping("/search")
    public PlainResult<IPage<LearnTeacherDto>> getTeachingInfo(@RequestParam(required = false) Integer pageNum,
                                                               @RequestParam(required = false) Integer pageSize,
                                                               @RequestParam(required = false) String sid,
                                                               @RequestParam(required = false) Integer cid,
                                                               @RequestParam(required = false) String tName,
                                                               @RequestParam(required = false) String cName,
                                                               @RequestParam(required = false) String learningType){
        IPage<LearnTeacherDto> learnTeacher = learnTeacherService.getLearnTeacherInfo(pageNum, pageSize, sid, cid, tName, cName, learningType);
        return PlainResult.success(learnTeacher);
    }
}
