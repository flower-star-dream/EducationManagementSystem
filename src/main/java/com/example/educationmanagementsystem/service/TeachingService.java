package com.example.educationmanagementsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.dto.TeachStuDto;
import com.example.educationmanagementsystem.dto.TeachingDto;

import java.util.List;

public interface TeachingService {
      /**
     * 新建教学内容
     * @param teachingDto
     */
    void addTeaching(TeachingDto teachingDto);

    /**
     * 更新教学内容
     * @param tid
     * @param cid
     * @param teachingDto
     */
    void updateTeaching(String tid, Integer cid,TeachingDto teachingDto);

    /**
     * 删除教学内容
     * @param tid
     * @param cid
     */
    void deleteTeaching(String tid, Integer cid);

    /**
     * 批量删除教学内容ID列表
     * @param tids
     * @param cids
     */
    void batchDelete(List<String> tids, List<Integer> cids);

    /**
     * 查询教学内容
     * @Param pageNum
     * @Param pageSize
     * @param tName
     * @param cName
     * @param teachingType
     */
    IPage<TeachingDto> getTeachingInfo(Integer pageNum, Integer pageSize, String tid, Integer cid, String tName, String cName, String teachingType);

    IPage<TeachingDto> getTeachingInfo2(Integer pageNum, Integer pageSize, String tid, Integer cid, String cName, String teachingType);

    Boolean getTeachingCheckTeacher(String tName);

    Boolean getTeachingCheckCourse(String cName);
}
