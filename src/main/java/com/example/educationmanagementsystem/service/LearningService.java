package com.example.educationmanagementsystem.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.dto.LearningDto;

import java.util.List;

public interface LearningService {
    /**
     * 新建学习内容
     * @param learningDto
     */
    void addLearning(LearningDto learningDto);

    /**
     * 更新学习内容
     * @param sid
     * @param cid
     * @param learningDto
     */
    void updateLearning(String sid, Integer cid,LearningDto learningDto);

    /**
     * 删除学习内容
     * @param sid
     * @param cid
     */
    void deleteLearning(String sid, Integer cid);

    /**
     * 批量删除学习内容ID列表
     * @param sids
     * @param cids
     */
    void batchDelete(List<String> sids, List<Integer> cids);

    /**
     * 查询学习内容
     * @Param pageNum
     * @Param pageSize
     * @Param affiliateId
     * @Param sid
     * @Param cid
     * @param sName
     * @param cName
     * @param learningType
     * @param score
     * @Param usertype
     * @return
     */
    IPage<LearningDto> getLearningInfo(Integer pageNum, Integer pageSize, String affiliateId, String sid, Integer cid, String sName, String cName, String learningType, Integer score, String usertype);

    Boolean getLearningCheckStudent(String sName);

    Boolean getLearningCheckCourse(String cName);
}
