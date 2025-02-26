package com.example.educationmanagementsystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.domain.Learning;
import com.example.educationmanagementsystem.dto.CalcGPADto;
import com.example.educationmanagementsystem.dto.LearnTeacherDto;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author JAM
* @description 针对表【learning】的数据库操作Mapper
* @createDate 2024-11-12 12:17:52
* @Entity com.example.educationmanagementsystem.domain.Learning
*/
public interface LearningMapper extends MPJBaseMapper<Learning> {

    void deleteById(@Param("sid") String sid, @Param("cid") Integer cid);

    void deleteBatchIds(@Param("sids") List<String> sids,@Param("cids") List<Integer> cids);

    int updateById(@Param("learning") Learning learning);

    <T> IPage<LearnTeacherDto> selectJoinPage2(Page<T> tPage, @Param("sid") String sid, @Param("cid") Integer cid,
                                               @Param("tName") String tName, @Param("cName") String cName,
                                               @Param("learningType") String learningType, @Param("offset") int offset);

    String selectClassNameByCourseIdTId(@Param("cid") Integer cid, @Param("tid") String tid);

    void deleteBySid(@Param("sid") String sid);

    void deleteBatchSids(@Param("sids") List<String> ids);

    double selectAverage(@Param("cid") Integer choice);

    <T> IPage<CalcGPADto> selectScore(Page<T> tPage, @Param("sid") String sid);
}




