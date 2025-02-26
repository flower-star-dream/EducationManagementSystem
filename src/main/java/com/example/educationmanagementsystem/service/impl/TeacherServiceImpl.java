package com.example.educationmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.common.SystemException;
import com.example.educationmanagementsystem.domain.Taro;
import com.example.educationmanagementsystem.domain.Teacher;
import com.example.educationmanagementsystem.dto.TeacherDto;
import com.example.educationmanagementsystem.mapper.TaroMapper;
import com.example.educationmanagementsystem.mapper.TeacherMapper;
import com.example.educationmanagementsystem.service.TeacherService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private TaroMapper taroMapper;

    @Override
    public void addTeacher(TeacherDto teacherDto) {
        Teacher teacher = convertToTeacher(teacherDto);
        if (teacherMapper.selectById(teacher.getTeacherId()) != null) {
            throw new SystemException(400, "该教师id已存在");
        }
        teacherMapper.insert(teacher);
    }

    @Override
    public void updateTeacher(String id, TeacherDto teacherDto) {
        Teacher teacher = convertToTeacher(teacherDto);
        teacher.setTeacherId(id);
        teacherMapper.updateById(teacher);
    }

    @Override
    public void deleteTeacher(String id) {
        teacherMapper.unboundId(id);
        teacherMapper.deleteById(id);
    }

    @Override
    public void batchDelete(List<String> ids) {
        teacherMapper.unboundIds(ids);
        teacherMapper.deleteBatchIds(ids);
    }

    @Override
    public IPage<TeacherDto> getTeacherInfo(Integer pageNum, Integer pageSize, String teacherId, String teacherName, String teacherGender, Integer teacherAge, String affiliate_taro_name) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        MPJLambdaWrapper<Teacher> wrappers = new MPJLambdaWrapper<>(Teacher.class);
        if (StringUtils.hasText(teacherId)) {
            wrappers.like(Teacher::getTeacherId,teacherId);
        }
        if (StringUtils.hasText(teacherName)) {
            wrappers.like(Teacher::getTeacherName,teacherName);
        }
        if (StringUtils.hasText(teacherGender)) {
            wrappers.eq(Teacher::getTeacherGender,teacherGender);
        }
        if (teacherAge != null) {
            wrappers.like(Teacher::getTeacherAge,teacherAge);
        }
        if (StringUtils.hasText(affiliate_taro_name)) {
            wrappers.innerJoin(Taro.class, on -> on.eq(Teacher::getAffiliateTaroId, Taro::getTaroId));
            wrappers.like(Taro::getTaroName, affiliate_taro_name);
        }
        Page<Teacher> teacherPage = teacherMapper.selectJoinPage(new Page<>(pageNum, pageSize), Teacher.class, wrappers);
        if (teacherPage == null){
            return null;
        }
        IPage<TeacherDto> teacherDtoIPage = teacherPage.convert(this::toTeacherDto);
        return teacherDtoIPage;
    }

    @Override
    public TeacherDto getOneTeacherInfo(String teacherId) {
        Teacher teacher = teacherMapper.selectById(teacherId);
        if (teacher == null){
            return null;
        }
        return toTeacherDto(teacher);
    }

    @Override
    public Boolean getTeacherCheck(String taroName) {
        return taroMapper.selectOneAidByTaroName(taroName) != null;
    }

    private Teacher convertToTeacher(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        teacher.setTeacherId(teacherDto.getId());
        teacher.setTeacherName(teacherDto.getName());
        teacher.setTeacherGender(teacherDto.getGender());
        teacher.setTeacherAge(teacherDto.getAge());
        teacher.setTeacherPhone(teacherDto.getPhone());
        teacher.setTeacherTitle(teacherDto.getTitle());
        teacher.setTeacherDegree(teacherDto.getDegree());
        teacher.setTeacherBirthdate(teacherDto.getBirthdate());
        Integer aid = teacherDto.getAid();
        if (StringUtils.hasText(teacherDto.getTaroName())){
            aid = taroMapper.selectOneAidByTaroName(teacherDto.getTaroName());
        }
        if (aid == null) {
            return null;
        }
        teacher.setAffiliateTaroId(aid);
        return teacher;
    }

    private TeacherDto toTeacherDto(Teacher teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacher.getTeacherId());
        teacherDto.setName(teacher.getTeacherName());
        teacherDto.setGender(teacher.getTeacherGender());
        teacherDto.setAge(teacher.getTeacherAge());
        teacherDto.setPhone(teacher.getTeacherPhone());
        teacherDto.setBirthdate(teacher.getTeacherBirthdate());
        teacherDto.setTitle(teacher.getTeacherTitle());
        teacherDto.setDegree(teacher.getTeacherDegree());
        teacherDto.setAid(teacher.getAffiliateTaroId());
        String getName = taroMapper.selectOneTaroNameByAid(teacher.getAffiliateTaroId());
        teacherDto.setTaroName(getName);
        return teacherDto;
    }
}
