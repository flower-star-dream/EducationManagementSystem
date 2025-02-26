package com.example.educationmanagementsystem.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.dto.MajorDto;

import java.util.List;

public interface MajorService {
    /**
     * 新建专业
     * @param MajorDto
     */
    void addMajor(MajorDto MajorDto);

    /**
     * 更新专业
     * @param id
     * @param MajorDto
     */
    void updateMajor(Integer id,MajorDto MajorDto);

    /**
     * 删除专业
     * @param id
     */
    void deleteMajor(Integer id);

    /**
     * 批量删除专业ID列表
     * @param ids
     */
    void batchDelete(List<Integer> ids);

    /**
     * 查询专业
     * @Param pageNum
     * @Param pageSize
     * @param MajorId
     * @param MajorName
     * @param affiliate_faculty_name
     */
    IPage<MajorDto> getMajorInfo(Integer pageNum, Integer pageSize, Integer MajorId, String MajorName, String affiliate_faculty_name);


   /**
     * 查询专业是否存在
     * @param facultyName
     * @return Boolean getMajorCheck(String facultyName);
    */
    Boolean getMajorCheck(String facultyName);
}
