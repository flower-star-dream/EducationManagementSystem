package com.example.educationmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.common.SystemException;
import com.example.educationmanagementsystem.domain.*;
import com.example.educationmanagementsystem.domain.Learning;
import com.example.educationmanagementsystem.dto.LearningDto;
import com.example.educationmanagementsystem.dto.LearningDto;
import com.example.educationmanagementsystem.mapper.CourseMapper;
import com.example.educationmanagementsystem.mapper.LearningMapper;
import com.example.educationmanagementsystem.mapper.StudentMapper;
import com.example.educationmanagementsystem.mapper.TaroMapper;
import com.example.educationmanagementsystem.service.LearningService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class LearningServiceImpl implements LearningService {

    @Resource
    private LearningMapper learningMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private CourseMapper courseMapper;
    
    
    @Override
    public void addLearning(LearningDto learningDto) {
        Learning learning = convertToLearning(learningDto);
        if (learningMapper.selectOne(new QueryWrapper<Learning>().eq("student_id", learning.getStudentId()).eq("course_id", learning.getCourseId())) != null) {
            throw new SystemException(400, "该学生已选修该课程");
        }
        learningMapper.insert(learning);
    }

    @Override
    public void updateLearning(String sid, Integer cid, LearningDto learningDto) {
        Learning learning = convertToLearning(learningDto);
        learning.setStudentId(sid);
        learning.setCourseId(cid);
        learningMapper.updateById(learning);
    }
    
    @Override
    public void deleteLearning(String sid, Integer cid) {
        learningMapper.deleteById(sid,cid);
    }

    @Override
    public void batchDelete(List<String> sids, List<Integer> cids) {
        learningMapper.deleteBatchIds(sids, cids);
    }
    
    @Override
    public IPage<LearningDto> getLearningInfo(Integer pageNum, Integer pageSize, String affiliateId, String sid, Integer cid, String sName, String cName, String learningType, Integer score, String usertype) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        MPJLambdaWrapper<Learning> wrappers = new MPJLambdaWrapper<>(Learning.class);
        if (StringUtils.hasText(sid)) {
            wrappers.like(Learning::getStudentId, sid);
        }
        if (cid != null) {
            wrappers.like(Learning::getCourseId, cid);
        }
        // 教师权限id关联查询
        if (StringUtils.hasText(affiliateId) && "教师".equals(usertype)) {
            wrappers.innerJoin(Course.class, on -> on.eq(Learning::getCourseId, Course::getCourseId))// 课程表关联成绩表
                    .innerJoin(Teaching.class, on -> on.eq(Course::getCourseId, Teaching::getCourseId)); // 授课表关联课程表
            wrappers.eq(Teaching::getTeacherId, affiliateId);
        }else if (StringUtils.hasText(affiliateId)) { // 学生权限id关联查询
            wrappers.innerJoin(Student.class, on -> on.eq(Learning::getStudentId, Student::getStudentId)); // 学生表关联成绩表
            wrappers.eq(Student::getStudentId, affiliateId);
        }

        if (StringUtils.hasText(sName) && StringUtils.hasText(cName)) {
            if (!StringUtils.hasText(affiliateId)) {
                wrappers.innerJoin(Student.class, on -> on.eq(Learning::getStudentId, Student::getStudentId)) // 学生表关联成绩表
                    .innerJoin(Course.class, on -> on.eq(Learning::getCourseId, Course::getCourseId)); // 课程表关联成绩表
            }else {
                if ("教师".equals(usertype)) {
                    wrappers.innerJoin(Student.class, on -> on.eq(Learning::getStudentId, Student::getStudentId)); // 学生表关联成绩表
                }else {
                    wrappers.innerJoin(Course.class, on -> on.eq(Learning::getCourseId, Course::getCourseId)); // 课程表关联成绩表
                }
            }
            wrappers.like(Student::getStudentName, sName);
            wrappers.like(Course::getCourseName, cName);
        } else if (StringUtils.hasText(sName)) {
            if (!StringUtils.hasText(affiliateId)) {
                wrappers.innerJoin(Student.class, on -> on.eq(Learning::getStudentId, Student::getStudentId)); // 学生关联成绩表
            }else {
                if ("教师".equals(usertype)) {
                    wrappers.innerJoin(Student.class, on -> on.eq(Learning::getStudentId, Student::getStudentId)); // 学生表关联成绩表
                }else {
                    wrappers.innerJoin(Course.class, on -> on.eq(Learning::getCourseId, Course::getCourseId)); // 课程表关联成绩表
                }
            }
            wrappers.like(Student::getStudentName, sName);
        } else if (StringUtils.hasText(cName)) {
            if (!StringUtils.hasText(affiliateId)) {
                wrappers.innerJoin(Course.class, on -> on.eq(Learning::getCourseId, Course::getCourseId)); // 课程表关联成绩表
            }else {
                if ("学生".equals(usertype)) {
                    wrappers.innerJoin(Course.class, on -> on.eq(Learning::getCourseId, Course::getCourseId)); // 课程表关联成绩表
                }
            }
            wrappers.like(Course::getCourseName, cName);
        }

        if (score != null) {
            wrappers.like(Learning::getScore, score);
        }

        if (StringUtils.hasText(learningType)){
            wrappers.like(Learning::getLearningType, learningType);
        }

        Page<Learning> learningPage = learningMapper.selectJoinPage(new Page<>(pageNum, pageSize), Learning.class, wrappers);
        if (learningPage == null){
            return null;
        }
        IPage<LearningDto> learningDtoIPage = learningPage.convert(this::toLearningDto);
        return learningDtoIPage;
    }

    private Learning convertToLearning(LearningDto learningDto) {
        Learning learning = new Learning();
        learning.setStudentId(learningDto.getSid());
        learning.setCourseId(learningDto.getCid());
        learning.setLearningType(learningDto.getLearningType());
        learning.setScore(learningDto.getScore());
        return learning;
    }

    private LearningDto toLearningDto(Learning learning) {
        LearningDto learningDto = new LearningDto();
        learningDto.setSid(learning.getStudentId());
        learningDto.setCid(learning.getCourseId());
        String getStudentName = studentMapper.selectOneStudentNameBySid(learning.getStudentId());
        learningDto.setStudentName(getStudentName);
        String getCourseName = courseMapper.selectOneCourseNameByCid(learning.getCourseId());
        learningDto.setCourseName(getCourseName);
        learningDto.setLearningType(learning.getLearningType());
        learningDto.setScore(learning.getScore());
        return learningDto;
    }

    @Override
    public Boolean getLearningCheckStudent(String sName){
        return studentMapper.selectOneSidByStudentName(sName) != null;
    }
    @Override
    public Boolean getLearningCheckCourse(String cName){
        return courseMapper.selectOneCidByCourseName(cName) != null;
    }
}
