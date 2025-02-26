package com.example.educationmanagementsystem.mapper;

import com.example.educationmanagementsystem.domain.Class;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.educationmanagementsystem.domain.Faculty;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author JAM
* @description 针对表【class】的数据库操作Mapper
* @createDate 2024-11-12 12:17:52
* @Entity com.example.educationmanagementsystem.domain.Class
*/
public interface ClassMapper extends MPJBaseMapper<Class> {
    Integer selectOneAidByClassName(@Param("className") String className);

    String selectOneClassNameByAid(@Param("AffiliateClassId") Integer AffiliateClassId);

    void unboundId(@Param("classId") Integer id);

    void unboundIds(@Param("classIds") List<Integer> ids);
}




