package com.example.educationmanagementsystem.mapper;

import com.example.educationmanagementsystem.domain.Faculty;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author JAM
* @description 针对表【faculty】的数据库操作Mapper
* @createDate 2024-11-12 12:17:52
* @Entity com.example.educationmanagementsystem.domain.Faculty
*/
@Mapper
public interface FacultyMapper extends MPJBaseMapper<Faculty> {
    Integer selectOneAidByFacultyName(@Param("facultyName") String facultyName);
    String selectOneFacultyNameByAid(@Param("affiliateFacultyId") Integer affiliateFacultyId);

    void unboundId(@Param("facultyId") Integer id);

    void unboundIds(@Param("facultyIds") List<Integer> ids);
}




