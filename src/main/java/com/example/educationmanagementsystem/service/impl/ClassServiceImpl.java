package com.example.educationmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.common.SystemException;
import com.example.educationmanagementsystem.domain.Class;
import com.example.educationmanagementsystem.domain.Major;
import com.example.educationmanagementsystem.dto.ClassDto;
import com.example.educationmanagementsystem.mapper.ClassMapper;
import com.example.educationmanagementsystem.mapper.MajorMapper;
import com.example.educationmanagementsystem.service.ClassService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {
    @Resource
    private ClassMapper classMapper;

    @Resource
    private MajorMapper majorMapper;

    @Override
    public void addClass(ClassDto classDto) {
        Class classvar = convertToClass(classDto);
        if (classMapper.selectById(classvar.getClassId()) != null) {
            throw new SystemException(400, "该班级id已存在");
        }
        classMapper.insert(classvar);
    }

    @Override
    public void updateClass(Integer id, ClassDto classDto) {
        Class classvar = convertToClass(classDto);
        classvar.setClassId(id);
        classMapper.updateById(classvar);
    }

    @Override
    public void deleteClass(Integer id) {
        classMapper.unboundId(id);
        classMapper.deleteById(id);
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        classMapper.unboundIds(ids);
        classMapper.deleteBatchIds(ids);
    }

    @Override
    public IPage<ClassDto> getClassInfo(Integer pageNum, Integer pageSize, Integer id, String name, String affiliate_major_name){
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        MPJLambdaWrapper<Class> wrappers = new MPJLambdaWrapper<>(Class.class);
        if (id != null) {
            wrappers.like(Class::getClassId,id);
        }
        if (StringUtils.hasText(name)) {
            wrappers.like(Class::getClassName,name);
        }
        if (StringUtils.hasText(affiliate_major_name)) {
            wrappers.innerJoin(Major.class, on -> on.eq(Class::getAffiliateMajorId, Major::getMajorId));
            wrappers.like(Major::getMajorName, affiliate_major_name);
        }
        Page<Class> classPage = classMapper.selectJoinPage(new Page<>(pageNum, pageSize), Class.class, wrappers);
        if (classPage == null) {
            return null;
        }
        IPage<ClassDto> classDtoIPage = classPage.convert(this::toClassDto);
        return classDtoIPage;
    }

   
    public Boolean getClassCheck(String majorName) {
        return majorMapper.selectOneAidByMajorName(majorName) != null;
    }
    

    private ClassDto toClassDto(Class classvar) {
        ClassDto classDto = new ClassDto();
        classDto.setId(classvar.getClassId());
        classDto.setName(classvar.getClassName());
        classDto.setAid(classvar.getAffiliateMajorId());
        String getName = majorMapper.selectOneMajorNameByAid(classvar.getAffiliateMajorId());
        classDto.setMajorName(getName);
        return classDto;
    }



    private Class convertToClass(ClassDto classDto) {
        Class classvar = new Class();
        classvar.setClassId(classDto.getId());
        classvar.setClassName(classDto.getName());
        classvar.setAffiliateMajorId(classDto.getAid());
        Integer aid = classDto.getAid();
        if (StringUtils.hasText(classDto.getMajorName())){
            aid = majorMapper.selectOneAidByMajorName(classDto.getMajorName());
        }
        if (aid == null) {
            return null;
        }
        classvar.setAffiliateMajorId(aid);
        return classvar;
    }


}
