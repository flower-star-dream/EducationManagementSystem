package com.example.educationmanagementsystem.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.dto.FacultyDto;
import java.util.List;

public interface FacultyService {

    /**
     * 新建院系
     * @param facultyDto
     */
    void addFaculty(FacultyDto facultyDto);

    /**
     * 更新院系
     * @param id
     * @param facultyDto/**
         * 1. 校验院系名称是否重复
         * 2. 保存院系信息
     */
    void updateFaculty(Integer id, FacultyDto facultyDto);

    /**
     * 删除院系
     * @param id
     * @return 无返回值
     */
    void deleteFaculty(Integer id);

    /**
     * 批量删除院系
     *
     * @param ids 要删除的院系ID列表
     * @return 无返回值
     */
    void batchDelete(List<Integer> ids);

    /**
     * 查询院系
     * @param facultyName
     * @return
     */
    IPage<FacultyDto> getFacultyInfo(Integer pageNum, Integer pageSize, Integer facultyId, String facultyName);
}
