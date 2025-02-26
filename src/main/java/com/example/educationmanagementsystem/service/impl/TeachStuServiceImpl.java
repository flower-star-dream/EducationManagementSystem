package com.example.educationmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.dto.TeachStuDto;
import com.example.educationmanagementsystem.mapper.*;
import com.example.educationmanagementsystem.service.TeachStuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TeachStuServiceImpl implements TeachStuService {
    @Resource
    private TeachingMapper teachingMapper;

    @Override
    public IPage<TeachStuDto> getTeachStuInfo(Integer pageNum, Integer pageSize, String tid, String sid, Integer cid, String sName, String cName, String className) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        int offset = (pageNum - 1) * pageSize;
        IPage<TeachStuDto> teachStuDtoIPage = teachingMapper.selectJoinPage2(new Page<>(pageNum, pageSize), tid, sid, cid, sName, cName, className, offset);
        if (teachStuDtoIPage == null){
            return null;
        }
        return teachStuDtoIPage;
    }

    
}
