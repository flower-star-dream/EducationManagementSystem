package com.example.educationmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.common.PlainResult;
import com.example.educationmanagementsystem.dto.BatchDeleteRequest;
import com.example.educationmanagementsystem.dto.LearningDto;
import com.example.educationmanagementsystem.service.LearningService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learning")
public class LearningController {
    @Resource
    private LearningService learningService;

    @GetMapping("/search")
    public PlainResult<IPage<LearningDto>> getLearningInfo(@RequestParam(required = false) Integer pageNum,
                                                     @RequestParam(required = false) Integer pageSize,
                                                     @RequestParam(required = false) String affiliateId,
                                                     @RequestParam(required = false) String sid,
                                                     @RequestParam(required = false) Integer cid,
                                                     @RequestParam(required = false) String sName,
                                                     @RequestParam(required = false) String cName,
                                                     @RequestParam(required = false) String learningType,
                                                     @RequestParam(required = false) Integer score,
                                                     @RequestParam(required = false) String usertype){
        IPage<LearningDto> learning = learningService.getLearningInfo(pageNum, pageSize, affiliateId, sid, cid, sName, cName, learningType, score, usertype);
        return PlainResult.success(learning);
    }

    @PostMapping
    public PlainResult<String> addLearning(@RequestBody LearningDto learningDto){
        learningService.addLearning(learningDto);
        return PlainResult.success("success");
    }

    @PutMapping("/{sid}/{cid}")
    public PlainResult<String> updateLearning(@PathVariable String sid, @PathVariable Integer cid, @RequestBody LearningDto learningDto){
        learningService.updateLearning(sid, cid, learningDto);
        return PlainResult.success("success");
    }

    @DeleteMapping("/{sid}/{cid}")
    public PlainResult<String> deleteLearning(@PathVariable String sid, @PathVariable Integer cid){
        learningService.deleteLearning(sid, cid);
        return PlainResult.success("success");
    }

    @PostMapping("/deleteBatch")
    public PlainResult<String> deleteBatch(@RequestBody BatchDeleteRequest request){
        learningService.batchDelete(request.getSids(), request.getCids());
        return PlainResult.success("success");
    }

    @GetMapping("/checkStudent")
    public PlainResult<Boolean> getLearningCheckStudent(@RequestParam String sName){
        Boolean bool = learningService.getLearningCheckStudent(sName);
        return PlainResult.success(bool);
    }

    @GetMapping("/checkCourse")
    public PlainResult<Boolean> getLearningCheckCourse(@RequestParam String cName){
        Boolean bool = learningService.getLearningCheckCourse(cName);
        return PlainResult.success(bool);
    }
}
