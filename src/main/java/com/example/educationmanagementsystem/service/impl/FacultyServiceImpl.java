package com.example.educationmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.EducationManagementSystemApplication;
import com.example.educationmanagementsystem.common.SystemException;
import com.example.educationmanagementsystem.domain.Faculty;
import com.example.educationmanagementsystem.dto.FacultyDto;
import com.example.educationmanagementsystem.mapper.FacultyMapper;
import com.example.educationmanagementsystem.service.FacultyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Resource
    private FacultyMapper facultyMapper;

    @Override
    public void addFaculty(FacultyDto facultyDto) {
        Faculty faculty = convertToFaculty(facultyDto);
        if (facultyMapper.selectById(faculty.getFacultyId()) != null) {
            throw new SystemException(400, "该学院id已存在");
        }
        facultyMapper.insert(faculty);
    }

    @Override
    public void updateFaculty(Integer id,FacultyDto facultyDto) {
        Faculty faculty = convertToFaculty(facultyDto);
        faculty.setFacultyId(id);
        facultyMapper.updateById(faculty);
    }

    @Override
    public void deleteFaculty(Integer id) {
        facultyMapper.unboundId(id);
        facultyMapper.deleteById(id);
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        facultyMapper.unboundIds(ids);
        facultyMapper.deleteBatchIds(ids);
    }

    @Override
    public IPage<FacultyDto> getFacultyInfo(Integer pageNum, Integer pageSize, Integer id, String name){
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        LambdaQueryWrapper<Faculty> wrappers = Wrappers.lambdaQuery();
        if (id != null) {
            wrappers.like(Faculty::getFacultyId,id);
        }
        if (StringUtils.hasText(name)) {
            wrappers.like(Faculty::getFacultyName,name);
        }
        Page<Faculty> facultyPage = facultyMapper.selectPage(new Page<>(pageNum, pageSize), wrappers);
        if (facultyPage == null) {
            return null;
        }
        return facultyPage.convert(this::toFacultyDto);
    }

    private FacultyDto toFacultyDto(Faculty faculty) {
        FacultyDto facultyDto = new FacultyDto();
        facultyDto.setId(faculty.getFacultyId());
        facultyDto.setName(faculty.getFacultyName());
        facultyDto.setAddress(faculty.getFacultyAddress());
        return facultyDto;
    }

    private Faculty convertToFaculty(FacultyDto facultyDto) {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(facultyDto.getId());
        faculty.setFacultyName(facultyDto.getName());
        faculty.setFacultyAddress(facultyDto.getAddress());
        return faculty;
    }
}
