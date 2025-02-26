package com.example.educationmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.common.SystemException;
import com.example.educationmanagementsystem.domain.*;
import com.example.educationmanagementsystem.dto.TeachStuDto;
import com.example.educationmanagementsystem.dto.TeachingDto;
import com.example.educationmanagementsystem.mapper.CourseMapper;
import com.example.educationmanagementsystem.mapper.LearningMapper;
import com.example.educationmanagementsystem.mapper.TeachingMapper;
import com.example.educationmanagementsystem.mapper.TeacherMapper;
import com.example.educationmanagementsystem.service.TeachingService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TeachingServiceImpl implements TeachingService {
    @Resource
    private TeachingMapper teachingMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private LearningMapper learningMapper;


    @Override
    public void addTeaching(TeachingDto teachingDto) {
        Teaching teaching = convertToTeaching(teachingDto);
        if (teachingMapper.selectOne(new QueryWrapper<Teaching>().eq("teacher_id", teaching.getTeacherId()).eq("course_id", teaching.getCourseId())) != null) {
            throw new SystemException(400, "该教师已安排该课程");
        }
        teachingMapper.insert(teaching);
    }

    @Override
    public void updateTeaching(String tid, Integer cid, TeachingDto teachingDto) {
        Teaching teaching = convertToTeaching(teachingDto);
        teaching.setTeacherId(tid);
        teaching.setCourseId(cid);
        teachingMapper.updateById(teaching);
    }

    @Override
    public void deleteTeaching(String tid, Integer cid) {
        teachingMapper.deleteById(tid,cid);
    }

    @Override
    public void batchDelete(List<String> tids, List<Integer> cids) {
        teachingMapper.deleteBatchIds(tids, cids);
    }
    
    @Override
    public IPage<TeachingDto> getTeachingInfo(Integer pageNum, Integer pageSize, String tid, Integer cid, String tName, String cName, String teachingType) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        MPJLambdaWrapper<Teaching> wrappers = new MPJLambdaWrapper<>(Teaching.class);
        wrappers.innerJoin(Teacher.class, on -> on.eq(Teaching::getTeacherId, Teacher::getTeacherId))
                    .innerJoin(Course.class, on -> on.eq(Teaching::getCourseId, Course::getCourseId));

        if (StringUtils.hasText(tid)) {
            wrappers.like(Teaching::getTeacherId, tid);
        }

        if (cid != null) {
            wrappers.like(Teaching::getCourseId, cid);
        }

        if (StringUtils.hasText(tName) && StringUtils.hasText(cName)) {
            wrappers.like(Teacher::getTeacherName, tName);
            wrappers.like(Course::getCourseName, cName);
        }else if (StringUtils.hasText(tName)) {
            wrappers.like(Teacher::getTeacherName, tName);
        }else if (StringUtils.hasText(cName)) {
            wrappers.like(Course::getCourseName, cName);
        }

        if (StringUtils.hasText(teachingType)){
            wrappers.like(Teaching::getTeachingType, teachingType);
        }

        Page<Teaching> teachingPage = teachingMapper.selectJoinPage(new Page<>(pageNum, pageSize), Teaching.class, wrappers);
        if (teachingPage == null){
            return null;
        }
        IPage<TeachingDto> teachingDtoIPage = teachingPage.convert(this::toTeachingDto);
        return teachingDtoIPage;
    }

    @Override
    public IPage<TeachingDto> getTeachingInfo2(Integer pageNum, Integer pageSize, String tid, Integer cid, String cName, String teachingType) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        IPage<TeachingDto> teachingDtoIPage = teachingMapper.selectJoinPage3(new Page<>(pageNum, pageSize), tid, cid, cName, teachingType);
        if (teachingDtoIPage == null){
            return null;
        }
        if (teachingDtoIPage.getRecords() != null) {
            for (TeachingDto teachingDto : teachingDtoIPage.getRecords()) {
                int status;
                Long count = learningMapper.selectCount(new QueryWrapper<Learning>().eq("course_id", teachingDto.getCid()).eq("score", -1));
                Long count2 = learningMapper.selectCount(new QueryWrapper<Learning>().eq("course_id", teachingDto.getCid()));
                if (count2 == 0 || count == count2) {
                    status = 1;
                } else if (count != count2 && count != 0) {
                    status = 2;
                } else {
                    status = 0;
                }
                teachingDto.setStatus(status);
            }
        }
        return teachingDtoIPage;
    }

    
    private Teaching convertToTeaching(TeachingDto teachingDto) {
        Teaching teaching = new Teaching();
        teaching.setTeacherId(teachingDto.getTid());
        teaching.setCourseId(teachingDto.getCid());
        teaching.setTeachingType(teachingDto.getTeachingType());
        return teaching;
    }

    private TeachingDto toTeachingDto(Teaching teaching) {
        TeachingDto teachingDto = new TeachingDto();
        teachingDto.setTid(teaching.getTeacherId());
        teachingDto.setCid(teaching.getCourseId());
        String getTeacherName = teacherMapper.selectOneTeacherNameByTid(teaching.getTeacherId());
        teachingDto.setTeacherName(getTeacherName);
        String getCourseName = courseMapper.selectOneCourseNameByCid(teaching.getCourseId());
        teachingDto.setCourseName(getCourseName);
        teachingDto.setTeachingType(teaching.getTeachingType());
        Long count = learningMapper.selectCount(new QueryWrapper<Learning>().eq("course_id",teaching.getCourseId()).eq("score", -1));
        Long count2 = learningMapper.selectCount(new QueryWrapper<Learning>().eq("course_id",teaching.getCourseId()));
        int status;
        if (count2 == 0 || count == count2){
            status = 1;
        } else if(count != count2 && count != 0){
            status = 2;
        } else {
            status = 0;
        }
        teachingDto.setStatus(status);
        return teachingDto;
    }

    @Override
    public Boolean getTeachingCheckTeacher(String tName){
        return teacherMapper.selectOneTidByTeacherName(tName) != null;
    }
    @Override
    public Boolean getTeachingCheckCourse(String cName){
        return courseMapper.selectOneCidByCourseName(cName) != null;
    }
    
}
