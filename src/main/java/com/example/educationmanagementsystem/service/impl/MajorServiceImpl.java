package com.example.educationmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.common.SystemException;
import com.example.educationmanagementsystem.domain.Faculty;
import com.example.educationmanagementsystem.domain.Major;
import com.example.educationmanagementsystem.dto.MajorDto;
import com.example.educationmanagementsystem.mapper.FacultyMapper;
import com.example.educationmanagementsystem.mapper.MajorMapper;
import com.example.educationmanagementsystem.service.MajorService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {
    @Resource
    private MajorMapper majorMapper;

    @Resource
    private FacultyMapper facultyMapper;

    @Override
    public void addMajor(MajorDto majorDto) {
        Major major = convertToMajor(majorDto);
        if (majorMapper.selectById(major.getMajorId()) != null) {
            throw new SystemException(400, "该专业id已存在");
        }
        majorMapper.insert(major);
    }

    @Override
    public void updateMajor(Integer id, MajorDto majorDto) {
        Major major = convertToMajor(majorDto);
        major.setMajorId(id);
        majorMapper.updateById(major);
    }

    @Override
    public void deleteMajor(Integer id) {
        majorMapper.unboundId(id);
        majorMapper.deleteById(id);
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        majorMapper.unboundIds(ids);
        majorMapper.deleteBatchIds(ids);
    }

    @Override
    public IPage<MajorDto> getMajorInfo(Integer pageNum, Integer pageSize, Integer majorId, String majorName, String affiliate_faculty_name) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        MPJLambdaWrapper<Major> wrappers = new MPJLambdaWrapper<>(Major.class);
        if (majorId != null) {
            wrappers.like(Major::getMajorId,majorId);
        }
        if (StringUtils.hasText(majorName)) {
            wrappers.like(Major::getMajorName,majorName);
        }
        if (StringUtils.hasText(affiliate_faculty_name)) {
            wrappers.innerJoin(Faculty.class, on -> on.eq(Major::getAffiliateFacultyId, Faculty::getFacultyId));
            wrappers.like(Faculty::getFacultyName, affiliate_faculty_name);
        }
        Page<Major> majorPage = majorMapper.selectJoinPage(new Page<>(pageNum, pageSize), Major.class, wrappers);
        if (majorPage == null){
            return null;
        }
        IPage<MajorDto> majorDtoIPage = majorPage.convert(this::toMajorDto);
        return majorDtoIPage;
    }

    @Override
    public Boolean getMajorCheck(String facultyName) {
        return facultyMapper.selectOneAidByFacultyName(facultyName) != null;
    }

    private Major convertToMajor(MajorDto majorDto) {
        Major major = new Major();
        major.setMajorId(majorDto.getId());
        major.setMajorName(majorDto.getName());
        Integer aid = majorDto.getAid();
        if (StringUtils.hasText(majorDto.getFacultyName())){
            aid = facultyMapper.selectOneAidByFacultyName(majorDto.getFacultyName());
        }
        if (aid == null) {
            return null;
        }
        major.setAffiliateFacultyId(aid);
        return major;
    }

    private MajorDto toMajorDto(Major major) {
        MajorDto majorDto = new MajorDto();
        majorDto.setId(major.getMajorId());
        majorDto.setName(major.getMajorName());
        majorDto.setAid(major.getAffiliateFacultyId());
        String getName = facultyMapper.selectOneFacultyNameByAid(major.getAffiliateFacultyId());
        majorDto.setFacultyName(getName);
        return majorDto;
    }
}
