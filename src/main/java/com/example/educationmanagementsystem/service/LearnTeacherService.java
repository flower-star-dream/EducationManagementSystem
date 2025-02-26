package com.example.educationmanagementsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.dto.LearnTeacherDto;

public interface LearnTeacherService {

    /**
     * 查询上课教师
     * @param pageNum
     * @param pageSize
     * @param sid
     * @param cid
     * @param tName
     * @param cName
     * @param learningType
     */
    IPage<LearnTeacherDto> getLearnTeacherInfo(Integer pageNum, Integer pageSize, String sid, Integer cid, String tName, String cName, String learningType);
}
