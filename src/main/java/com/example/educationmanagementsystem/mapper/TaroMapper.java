package com.example.educationmanagementsystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.domain.Taro;
import com.example.educationmanagementsystem.dto.TaroDto;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author JAM
* @description 针对表【taro】的数据库操作Mapper
* @createDate 2024-11-12 12:17:52
* @Entity com.example.educationmanagementsystem.domain.Taro
*/
public interface TaroMapper extends MPJBaseMapper<Taro> {

    Integer selectOneAidByTaroName(@Param("taroName") String taroName);

    String selectOneTaroNameByAid(@Param("affiliateTaroId") Integer affiliateTaroId);


    void unboundId(@Param("taroId") Integer id);

    void unboundIds(@Param("taroIds") List<Integer> ids);
}




