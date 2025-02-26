package com.example.educationmanagementsystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.domain.Learning;
import com.example.educationmanagementsystem.domain.Teaching;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.educationmanagementsystem.dto.TeachStuDto;
import com.example.educationmanagementsystem.dto.TeachingDto;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author JAM
* @description 针对表【teaching】的数据库操作Mapper
* @createDate 2024-11-12 12:17:52
* @Entity com.example.educationmanagementsystem.domain.Teaching
*/
public interface TeachingMapper extends MPJBaseMapper<Teaching> {

    void deleteById(@Param("tid") String tid, @Param("cid") Integer cid);

    void deleteBatchIds(@Param("tids") List<String> tids, @Param("cids") List<Integer> cids);

    int updateById(@Param("teaching") Teaching teaching);

    <T> IPage<TeachStuDto> selectJoinPage2(Page<T> tPage, @Param("tid") String tid, @Param("sid") String sid,
                                          @Param("cid") Integer cid, @Param("sName") String sName, @Param("cName") String cName,
                                          @Param("className") String className,@Param("offset") int offset);

    <T> IPage<TeachingDto> selectJoinPage3(Page<T> tPage, @Param("tid") String tid, @Param("cid") Integer cid,
                                            @Param("cName") String cName, @Param("teachingType") String teachingType);
}




