package com.example.educationmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.common.PlainResult;
import com.example.educationmanagementsystem.dto.BatchDeleteRequest;
import com.example.educationmanagementsystem.dto.TeachingDto;
import com.example.educationmanagementsystem.service.TeachingService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teaching")
public class TeachingController {
    @Resource
    private TeachingService teachingService;

    @GetMapping("/search")
    public PlainResult<IPage<TeachingDto>> getTeachingInfo(@RequestParam(required = false) Integer pageNum,
                                                     @RequestParam(required = false) Integer pageSize,
                                                     @RequestParam(required = false) String tid,
                                                     @RequestParam(required = false) Integer cid,
                                                     @RequestParam(required = false) String tName,
                                                     @RequestParam(required = false) String cName,
                                                     @RequestParam(required = false) String teachingType){
        IPage<TeachingDto> teaching = teachingService.getTeachingInfo(pageNum, pageSize, tid, cid, tName, cName, teachingType);
        return PlainResult.success(teaching);
    }

    @GetMapping("/search2")
    public PlainResult<IPage<TeachingDto>> getTeachingInfo2(@RequestParam(required = false) Integer pageNum,
                                                     @RequestParam(required = false) Integer pageSize,
                                                     @RequestParam(required = false) String tid,
                                                     @RequestParam(required = false) Integer cid,
                                                     @RequestParam(required = false) String cName,
                                                     @RequestParam(required = false) String teachingType){
        IPage<TeachingDto> teaching = teachingService.getTeachingInfo2(pageNum, pageSize, tid, cid, cName, teachingType);
        return PlainResult.success(teaching);
    }

    @PostMapping
    public PlainResult<String> addTeaching(@RequestBody TeachingDto teachingDto){
        teachingService.addTeaching(teachingDto);
        return PlainResult.success("success");
    }

    @PutMapping("/{tid}/{cid}")
    public PlainResult<String> updateTeaching(@PathVariable String tid, @PathVariable Integer cid, @RequestBody TeachingDto teachingDto){
        teachingService.updateTeaching(tid, cid, teachingDto);
        return PlainResult.success("success");
    }

    @DeleteMapping("/{tid}/{cid}")
    public PlainResult<String> deleteTeaching(@PathVariable String tid, @PathVariable Integer cid){
        teachingService.deleteTeaching(tid, cid);
        return PlainResult.success("success");
    }

    @PostMapping("/deleteBatch")
    public PlainResult<String> deleteBatch(@RequestBody BatchDeleteRequest request){
        teachingService.batchDelete(request.getTids(), request.getCids());
        return PlainResult.success("success");
    }

    @GetMapping("/checkTeacher")
    public PlainResult<Boolean> getTeachingCheckTeacher(@RequestParam String tName){
        Boolean bool = teachingService.getTeachingCheckTeacher(tName);
        return PlainResult.success(bool);
    }

    @GetMapping("/checkCourse")
    public PlainResult<Boolean> getTeachingCheckCourse(@RequestParam String cName){
        Boolean bool = teachingService.getTeachingCheckCourse(cName);
        return PlainResult.success(bool);
    }
}
