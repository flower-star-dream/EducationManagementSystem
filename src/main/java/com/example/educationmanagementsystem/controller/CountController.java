package com.example.educationmanagementsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.educationmanagementsystem.common.PlainResult;
import com.example.educationmanagementsystem.domain.*;
import com.example.educationmanagementsystem.dto.CalcGPADto;
import com.example.educationmanagementsystem.dto.LearnTeacherDto;
import com.example.educationmanagementsystem.dto.TeachingDto;
import com.example.educationmanagementsystem.mapper.*;
import com.github.yulichang.query.MPJQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/count")
public class CountController {

    @Resource
    private FacultyMapper facultyMapper;

    @Resource
    private MajorMapper majorMapper;

    @Resource
    private TaroMapper taroMapper;

    @Resource
    private ClassMapper classMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private LearningMapper learningMapper;

    @Resource
    private TeachingMapper teachingMapper;

    @GetMapping("/stats")
    public PlainResult<Map<String, Object>> getStatistics() {
        // 查询学院数
        long facultyCount = facultyMapper.selectCount(new QueryWrapper<>());

        // 查询专业数
        long majorCount = majorMapper.selectCount(new QueryWrapper<>());

        // 查询教研室数
        long taroCount = taroMapper.selectCount(new QueryWrapper<>());

        // 查询班级数
        long classCount = classMapper.selectCount(new QueryWrapper<>());

        // 查询教师数
        long teacherCount = teacherMapper.selectCount(new QueryWrapper<>());

        // 查询学生数
        long studentCount = studentMapper.selectCount(new QueryWrapper<>());

        // 查询用户数
        long userCount = userMapper.selectCount(new QueryWrapper<>());

        // 查询管理员数
        long adminCount = userMapper.selectCount(new QueryWrapper<User>().eq("usertype", "管理员"));

        long userTeacherCount = userMapper.selectCount(new QueryWrapper<User>().eq("usertype", "教师"));

        long userStudentCount = userMapper.selectCount(new QueryWrapper<User>().eq("usertype", "学生"));

        // 查询学生男生数
        long maleCount = studentMapper.selectCount(new QueryWrapper<Student>().eq("student_gender", "男"));
        // 查询学生女生数
        long femaleCount = studentMapper.selectCount(new QueryWrapper<Student>().eq("student_gender", "女"));

        // 查询各学院学生人数
        List<Long> studentCountsByFaculty = new ArrayList<>();
        List<String> facultyNames = new ArrayList<>();
        for(int i=0;i<facultyCount;i++){
            int fid = i+1;
            if (i == facultyCount - 1){
                fid = -1;
            }
            long count = facultyMapper.selectJoinCount(new MPJQueryWrapper<Faculty>()
                    .innerJoin("major m on m.affiliate_faculty_id = faculty_id")
                    .innerJoin("class c on c.affiliate_major_id = m.major_id")
                    .innerJoin("student s on s.affiliate_class_id = c.class_id")
                    .eq("faculty_id", fid));
            studentCountsByFaculty.add(count);
            facultyNames.add(facultyMapper.selectById(fid).getFacultyName());
        }

        // 构造返回的统计数据
        Map<String, Object> statsMap = new HashMap<>();
        statsMap.put("facultyCount", facultyCount);
        statsMap.put("majorCount", majorCount);
        statsMap.put("taroCount", taroCount);
        statsMap.put("classCount", classCount);
        statsMap.put("teacherCount", teacherCount);
        statsMap.put("studentCount", studentCount);
        statsMap.put("userCount", userCount);
        statsMap.put("adminCount", adminCount);
        statsMap.put("maleCount", maleCount);
        statsMap.put("femaleCount", femaleCount);
        statsMap.put("userTeacherCount", userTeacherCount);
        statsMap.put("userStudentCount", userStudentCount);
        statsMap.put("studentCountsByFaculty", studentCountsByFaculty);
        statsMap.put("facultyNames", facultyNames);

        return PlainResult.success(statsMap);
    }

    @GetMapping("/stats2")
    public PlainResult<Map<String, Object>> getStatistics2(@RequestParam Integer choice) {
        // 查询修习该课程学生数
        long studentCount = learningMapper.selectJoinCount(new MPJQueryWrapper<Learning>()
                .innerJoin("course c on c.course_id = t.course_id")
                .eq("c.course_id", choice));

        // 查询课程平均成绩
        double averageScore = learningMapper.selectAverage(choice);

        // 查询挂科人数
        long failCount= learningMapper.selectCount(new QueryWrapper<Learning>().eq("course_id", choice).gt("score", 0).lt("score", 60));

        // 查询课程结课状态
        Long count = learningMapper.selectCount(new QueryWrapper<Learning>().eq("course_id",choice).eq("score", -1));
        Long count2 = learningMapper.selectCount(new QueryWrapper<Learning>().eq("course_id",choice));
        int status;
        if (count2 == 0 || count == count2){
            status = 1;
        } else if(count != count2 && count != 0){
            status = 2;
        } else {
            status = 0;
        }


        // 构造返回的统计数据
        Map<String, Object> statsMap = new HashMap<>();
        statsMap.put("studentCount", studentCount);
        statsMap.put("averageScore", averageScore);
        statsMap.put("failCount", failCount);
        statsMap.put("status", status);

        return PlainResult.success(statsMap);
    }

    @GetMapping("/search")
    public PlainResult<Map<String, Object>> search(@RequestParam String tid) {
        // 查询课程数
        long count = teachingMapper.selectJoinCount(new MPJQueryWrapper<Teaching>()
                .innerJoin("course c on c.course_id = t.course_id")
                .eq("t.teacher_id", tid));

        IPage<TeachingDto> teachingDtoIPage = teachingMapper.selectJoinPage3(new Page<>(1, 10), tid, null, null, null);
        List<String> courseNames = new ArrayList<>();
        List<Integer> courseIds = new ArrayList<>();
        if (teachingDtoIPage != null && teachingDtoIPage.getRecords() != null) {
            for (TeachingDto teachingDto : teachingDtoIPage.getRecords()) {
                courseNames.add(teachingDto.getCourseName());
                courseIds.add(teachingDto.getCid());
            }
        }

        Map<String, Object> statsMap = new HashMap<>();
        statsMap.put("count", count);
        statsMap.put("courseNames", courseNames);
        statsMap.put("courseIds", courseIds);

        return PlainResult.success(statsMap);
    }

    @GetMapping("/stats3")
    public PlainResult<Map<String, Object>> getStatistics3(@RequestParam String sid) {
        // 查询课程数
        long count = learningMapper.selectJoinCount(new MPJQueryWrapper<Learning>().eq("student_id", sid));

        // 查询挂科数
        long failCount= learningMapper.selectCount(new QueryWrapper<Learning>().eq("student_id", sid).gt("score", 0).lt("score", 60));

        IPage<CalcGPADto> calcGPADtoIPage = learningMapper.selectScore(new Page<>(1, 10), sid);
        double averageGPA = 0;
        List<Double> gpas = new ArrayList<>();
        List<Integer> credits = new ArrayList<>();
        int sum = 0;
        if (calcGPADtoIPage != null && calcGPADtoIPage.getRecords() != null) {
            for (CalcGPADto calcGPADto : calcGPADtoIPage.getRecords()) {
                if (calcGPADto.getScore() == -1) {
                    continue;
                }
                double gpa = calcGPADto.getScore() >= 60 ? (calcGPADto.getScore() - 50) / 10 : 0;
                gpas.add(gpa);
                credits.add(calcGPADto.getCredit());
            }
        }
        if (gpas.size() > 0) {
            for (int i = 0; i < gpas.size(); i++) { // 计算平均绩点
                averageGPA += credits.get(i) * gpas.get(i);
                sum += credits.get(i);
            }
        }
        averageGPA = averageGPA / sum;

        Map<String, Object> statsMap = new HashMap<>();
        statsMap.put("averageGPA", averageGPA);
        statsMap.put("failCount", failCount);
        statsMap.put("count", count);

        return PlainResult.success(statsMap);
    }

}
