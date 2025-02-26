package com.example.educationmanagementsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.dto.TeachStuDto;

import java.util.List;

public interface TeachStuService {

    /**
     * 查询教学内容
     * @Param pageNum
     * @Param pageSize
     * @param tid
     * @param sid
     * @param cid
     * @param sName
     * @param cName
     * @param className
     */
    IPage<TeachStuDto> getTeachStuInfo(Integer pageNum, Integer pageSize, String tid, String sid, Integer cid, String sName, String cName, String className);

}
