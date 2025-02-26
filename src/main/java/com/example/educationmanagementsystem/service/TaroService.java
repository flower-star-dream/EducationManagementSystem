package com.example.educationmanagementsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.dto.TaroDto;

import java.util.List;

public interface TaroService{

    /**
     * 新建教研室
     * @param taroDto
     */
    void addTaro(TaroDto taroDto);

    /**
     * 更新教研室
     * @param id
     * @param taroDto
     */
    void updateTaro(Integer id,TaroDto taroDto);

    /**
     * 删除教研室
     * @param id
     */
    void deleteTaro(Integer id);

    /**
     * 批量删除教研室ID列表
     * @param ids
     */
    void batchDelete(List<Integer> ids);

    /**
     * 查询教研室
     * @Param pageNum
     * @Param pageSize
     * @param TaroId
     * @param TaroName
     * @param affiliate_faculty_name
     */
    IPage<TaroDto> getTaroInfo(Integer pageNum, Integer pageSize, Integer TaroId, String TaroName, String affiliate_faculty_name);


   /**
     * 查询教研室是否存在
     * @param facultyName
     * @return Boolean getTaroCheck(String facultyName);
    */
    Boolean getTaroCheck(String facultyName);
}
