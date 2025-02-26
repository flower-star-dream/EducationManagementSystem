package com.example.educationmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.common.PlainResult;
import com.example.educationmanagementsystem.dto.TeachStuDto;
import com.example.educationmanagementsystem.service.TeachStuService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teachStu")
public class TeachStuController {

    @Resource
    private TeachStuService teachStuService;

    @GetMapping("/search")
    public PlainResult<IPage<TeachStuDto>> getTeachingInfo(@RequestParam(required = false) Integer pageNum,
                                                           @RequestParam(required = false) Integer pageSize,
                                                           @RequestParam(required = false) String tid,
                                                           @RequestParam(required = false) String sid,
                                                           @RequestParam(required = false) Integer cid,
                                                           @RequestParam(required = false) String sName,
                                                           @RequestParam(required = false) String cName,
                                                           @RequestParam(required = false) String className){
        IPage<TeachStuDto> teachStu = teachStuService.getTeachStuInfo(pageNum, pageSize, tid, sid, cid, sName, cName, className);
        return PlainResult.success(teachStu);
    }
}
