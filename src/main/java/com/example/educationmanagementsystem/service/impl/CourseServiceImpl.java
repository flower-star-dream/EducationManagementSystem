package com.example.educationmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.common.SystemException;
import com.example.educationmanagementsystem.domain.Taro;
import com.example.educationmanagementsystem.domain.Course;
import com.example.educationmanagementsystem.dto.CourseDto;
import com.example.educationmanagementsystem.mapper.TaroMapper;
import com.example.educationmanagementsystem.mapper.CourseMapper;
import com.example.educationmanagementsystem.service.CourseService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Resource
    private CourseMapper courseMapper;

    @Resource
    private TaroMapper taroMapper;

    @Override
    public void addCourse(CourseDto courseDto) {
        Course course = convertToCourse(courseDto);
        if (courseMapper.selectById(course.getCourseId()) != null) {
            throw new SystemException(400, "该课程id已存在");
        }
        courseMapper.insert(course);
    }

    @Override
    public void updateCourse(Integer id, CourseDto courseDto) {
        Course course = convertToCourse(courseDto);
        course.setCourseId(id);
        courseMapper.updateById(course);
    }

    @Override
    public void deleteCourse(Integer id) {
        int count = courseMapper.selectBoundId(id);
        if (count > 0) {
            throw new SystemException(400, "该教学计划已启用，不可删除");
        }
        courseMapper.deleteById(id);
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        int count = courseMapper.selectBoundIds(ids);
        if (count > 0) {
            throw new SystemException(400, "该教学计划已启用，不可删除");
        }
        courseMapper.deleteBatchIds(ids);
    }

    @Override
    public IPage<CourseDto> getCourseInfo(Integer pageNum, Integer pageSize, Integer courseId, String courseName, String affiliate_taro_name) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        MPJLambdaWrapper<Course> wrappers = new MPJLambdaWrapper<>(Course.class);
        if (courseId != null) {
            wrappers.like(Course::getCourseId,courseId);
        }
        if (StringUtils.hasText(courseName)) {
            wrappers.like(Course::getCourseName,courseName);
        }
        if (StringUtils.hasText(affiliate_taro_name)) {
            wrappers.innerJoin(Taro.class, on -> on.eq(Course::getAffiliateTaroId, Taro::getTaroId));
            wrappers.like(Taro::getTaroName, affiliate_taro_name);
        }
        Page<Course> coursePage = courseMapper.selectJoinPage(new Page<>(pageNum, pageSize), Course.class, wrappers);
        if (coursePage == null){
            return null;
        }
        IPage<CourseDto> courseDtoIPage = coursePage.convert(this::toCourseDto);
        return courseDtoIPage;
    }

    @Override
    public Boolean getCourseCheck(String taroName) {
        return taroMapper.selectOneAidByTaroName(taroName) != null;
    }

    private Course convertToCourse(CourseDto courseDto) {
        Course course = new Course();
        course.setCourseId(courseDto.getId());
        course.setCourseName(courseDto.getName());
        course.setCourseCredit(courseDto.getCredit());
        course.setCourseTime(courseDto.getTime());
        course.setCourseRoom(courseDto.getRoom());
        Integer aid = courseDto.getAid();
        if (StringUtils.hasText(courseDto.getTaroName())){
            aid = taroMapper.selectOneAidByTaroName(courseDto.getTaroName());
        }
        if (aid == null) {
            return null;
        }
        course.setAffiliateTaroId(aid);
        return course;
    }

    private CourseDto toCourseDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getCourseId());
        courseDto.setName(course.getCourseName());
        courseDto.setCredit(course.getCourseCredit());
        courseDto.setTime(course.getCourseTime());
        courseDto.setRoom(course.getCourseRoom());
        courseDto.setAid(course.getAffiliateTaroId());
        String getName = taroMapper.selectOneTaroNameByAid(course.getAffiliateTaroId());
        courseDto.setTaroName(getName);
        return courseDto;
    }
}
