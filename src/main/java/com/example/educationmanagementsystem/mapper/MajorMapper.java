package com.example.educationmanagementsystem.mapper;

import com.example.educationmanagementsystem.domain.Major;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author JAM
* @description 针对表【major】的数据库操作Mapper
* @createDate 2024-11-12 12:17:52
* @Entity com.example.educationmanagementsystem.domain.Major
*/
@Mapper
public interface MajorMapper extends MPJBaseMapper<Major> {
    Integer selectOneAidByMajorName(@Param("majorName") String majorName);

    String selectOneMajorNameByAid(@Param("affiliateMajorId") Integer affiliateMajorId);

    void unboundId(@Param("majorId") Integer id);

    void unboundIds(@Param("majorIds") List<Integer> ids);
}




