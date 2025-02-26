package com.example.educationmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.dto.LearnTeacherDto;
import com.example.educationmanagementsystem.dto.TeachStuDto;
import com.example.educationmanagementsystem.mapper.LearningMapper;
import com.example.educationmanagementsystem.mapper.TeachingMapper;
import com.example.educationmanagementsystem.service.LearnTeacherService;
import com.example.educationmanagementsystem.service.TeachStuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearnTeacherServiceImpl implements LearnTeacherService {
    @Resource
    private LearningMapper learningMapper;

    @Override
    public IPage<LearnTeacherDto> getLearnTeacherInfo(Integer pageNum, Integer pageSize, String sid, Integer cid, String tName, String cName, String learningType) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        int offset = (pageNum - 1) * pageSize;
        IPage<LearnTeacherDto> learnTeacherDtoIPage = learningMapper.selectJoinPage2(new Page<>(pageNum, pageSize), sid, cid, tName, cName, learningType, offset);
        if (learnTeacherDtoIPage == null){
            return null;
        }
        List<LearnTeacherDto> records = learnTeacherDtoIPage.getRecords();
        for (LearnTeacherDto learnTeacherDto : records) {
            String tid = learnTeacherDto.getTid();
            String[] tids = tid.split(", ");
            learnTeacherDto.setClassName(learningMapper.selectClassNameByCourseIdTId(learnTeacherDto.getCid(), tids[0]));
        }
        return learnTeacherDtoIPage;
    }

    
}
