package com.example.educationmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.common.SystemException;
import com.example.educationmanagementsystem.domain.Faculty;
import com.example.educationmanagementsystem.domain.Taro;
import com.example.educationmanagementsystem.dto.TaroDto;
import com.example.educationmanagementsystem.mapper.FacultyMapper;
import com.example.educationmanagementsystem.mapper.TaroMapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.example.educationmanagementsystem.service.TaroService;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TaroServiceImpl implements TaroService{

    @Resource
    private TaroMapper taroMapper;

    @Resource
    private FacultyMapper facultyMapper;

    @Override
    public void addTaro(TaroDto taroDto) {
        Taro taro = convertToTaro(taroDto);
        if (taroMapper.selectById(taro.getTaroId()) != null) {
            throw new SystemException(400, "该教研室id已存在");
        }
        taroMapper.insert(taro);
    }

    @Override
    public void updateTaro(Integer id, TaroDto taroDto) {
        Taro taro = convertToTaro(taroDto);
        taro.setTaroId(id);
        taroMapper.updateById(taro);
    }

    @Override
    public void deleteTaro(Integer id) {
        taroMapper.unboundId(id);
        taroMapper.deleteById(id);
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        taroMapper.unboundIds(ids);
        taroMapper.deleteBatchIds(ids);
    }

    @Override
    public IPage<TaroDto> getTaroInfo(Integer pageNum, Integer pageSize, Integer TaroId, String TaroName, String affiliate_faculty_name) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        MPJLambdaWrapper<Taro> wrappers = new MPJLambdaWrapper<>(Taro.class);
        if (TaroId != null) {
            wrappers.like(Taro::getTaroId,TaroId);
        }
        if (StringUtils.hasText(TaroName)) {
            wrappers.like(Taro::getTaroName,TaroName);
        }
        if (StringUtils.hasText(affiliate_faculty_name)) {
            wrappers.innerJoin(Faculty.class, on -> on.eq(Taro::getAffiliateFacultyId, Faculty::getFacultyId));
            wrappers.like(Faculty::getFacultyName, affiliate_faculty_name);
        }
        Page<Taro> taroPage = taroMapper.selectJoinPage(new Page<>(pageNum, pageSize), Taro.class, wrappers);
        if (taroPage == null){
            return null;
        }
        IPage<TaroDto> taroDtoIPage = taroPage.convert(this::toTaroDto);
        return taroDtoIPage;
    }

    @Override
    public Boolean getTaroCheck(String facultyName) {
        return facultyMapper.selectOneAidByFacultyName(facultyName) != null;
    }

    private Taro convertToTaro(TaroDto taroDto) {
        Taro taro = new Taro();
        taro.setTaroId(taroDto.getId());
        taro.setTaroName(taroDto.getName());
        Integer aid = taroDto.getAid();
        if (StringUtils.hasText(taroDto.getFacultyName())){
            aid = facultyMapper.selectOneAidByFacultyName(taroDto.getFacultyName());
        }
        if (aid == null) {
            return null;
        }
        taro.setAffiliateFacultyId(aid);
        return taro;
    }

    private TaroDto toTaroDto(Taro taro) {
        TaroDto taroDto = new TaroDto();
        taroDto.setId(taro.getTaroId());
        taroDto.setName(taro.getTaroName());
        taroDto.setAid(taro.getAffiliateFacultyId());
        String getName = facultyMapper.selectOneFacultyNameByAid(taro.getAffiliateFacultyId());
        taroDto.setFacultyName(getName);
        return taroDto;
    }
}
