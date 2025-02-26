<template>
  <div class="home">
    <h1 style="margin:0 auto;text-align: center">系统数据统计</h1>
      <br/>
    <el-row :gutter="20">
      <el-col :span="6" v-for="item in cards" :key="item.title">
        <el-card class="box-card">
          <div slot="header" class="clearfix">{{ item.title }}</div>
          <div class="text item">
            <svg class="icon" aria-hidden="true">
              <use :xlink:href="item.icon" style="width: 100px"></use>
            </svg>
            <span class="text">{{ item.data }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="12">
      <el-col :span="8" v-for="(chart, index) in charts" :key="chart.series.title">
        <el-card class="box-card">
          <div slot="header" class="clearfix">{{ chart.series.title }}</div>
          <div ref="chartContainer" :id="'chart-' + index" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'
import { ElMessage } from "element-plus"
import { countAPI } from '@/api/count.js';

const cards = ref([
  { title: '开设学院数', data: 0, icon: '#icon-faculty2' },
  { title: '开设专业数', data: 0, icon: '#icon-major2' },
  { title: '开设教研室数', data: 0, icon: '#icon-taro2' },
  { title: '开设班级数', data: 0, icon: '#icon-class2' },
  { title: '全校教师数', data: 0, icon: '#icon-teacher2' },
  { title: '全校学生数', data: 0, icon: '#icon-student2' },
  { title: '系统用户数', data: 0, icon: '#icon-user2' },
  { title: '管理员数', data: 0, icon: '#icon-admin'}
])

const maleFemaleRatio = ref({ male: 0, female: 0 });
const roleRatio = ref({ admin: 0, teacher: 0, student: 0 });
const facultyRatio = ref({ faculty:[], name:[], count:0 });

// 初始化扇形图的容器
const charts = ref([
  {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      bottom: 'bottom',
      data: ['男','女']
    },
    series: {
        title: '学生性别比例统计',
        type: 'pie',
        radius: '55%',
        roseType: 'angle',
        data: [
          { value: maleFemaleRatio.value.male, name:'男'},
          { value: maleFemaleRatio.value.female, name:'女'}
        ],
        itemStyle: {
          emphasis: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        label: {
         normal: {
             show: true,
             position: 'outside' // 可以设置为 'outside' 来显示在扇区外部
         }
        }
    }
  },
  {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      bottom: 'bottom',
      data: ['学生','教师','管理员']
    },
    series: {
        title: '已注册用户权限占比统计',
        type: 'pie',
        radius: '55%',
        roseType: 'angle',
        data: [
          { value: roleRatio.value.admin, name:'管理员'},
          { value: roleRatio.value.teacher, name:'教师'},
          { value: roleRatio.value.student, name:'学生'}
        ],
        itemStyle: {
          emphasis: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        label: {
         normal: {
             show: true,
             position: 'outside' // 可以设置为 'outside' 来显示在扇区外部
         }
        }
    }
  },
  {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} : {c} ({d}%)'
    },
    series: {
        title: '各学院学生人数统计',
        type: 'pie',
        radius: '55%',
        roseType: 'angle',
        data: [],
        itemStyle: {
          emphasis: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        label: {
         normal: {
             show: true,
             position: 'outside'
         }
        }
    }
  }
]);

const chartInstances = ref([]); // 存储每个图表实例

const initCharts = () => {
  charts.value.forEach((chart, index) => {
    const chartInstance = echarts.init(document.getElementById('chart-' + index));
    chartInstances.value[index] = chartInstance; // 存储图表实例
    chartInstance.setOption(chart);
    return chartInstance;
  });
}

onMounted(async () => {
  try {
    const res = await countAPI();
    if (res.data.code === 200) {
      cards.value[0].data = res.data.data.facultyCount-1;
      cards.value[1].data = res.data.data.majorCount-1;
      cards.value[2].data = res.data.data.taroCount-1;
      cards.value[3].data = res.data.data.classCount-1;
      cards.value[4].data = res.data.data.teacherCount-1;
      cards.value[5].data = res.data.data.studentCount;
      cards.value[6].data = res.data.data.userCount;
      cards.value[7].data = res.data.data.adminCount;
      maleFemaleRatio.value.male = res.data.data.maleCount;
      maleFemaleRatio.value.female = res.data.data.femaleCount;
      roleRatio.value.admin = res.data.data.adminCount;
      roleRatio.value.teacher = res.data.data.userTeacherCount;
      roleRatio.value.student = res.data.data.userStudentCount;
      facultyRatio.value.count = res.data.data.facultyCount;
      for (var i = 0; i < facultyRatio.value.count; i++) {
        facultyRatio.value.faculty.push(res.data.data.studentCountsByFaculty[i]);
        if (i == facultyRatio.value.count - 1){
          facultyRatio.value.name.push('未分配学生数');
        } else {
          facultyRatio.value.name.push(res.data.data.facultyNames[i]);
        }
      }
      charts.value[0].series.data = [
        { value: maleFemaleRatio.value.male, name: '男' },
        { value: maleFemaleRatio.value.female, name: '女' }
      ];
      charts.value[1].series.data = [
        { value: roleRatio.value.admin, name: '管理员' },
        { value: roleRatio.value.teacher, name: '教师' },
        { value: roleRatio.value.student, name: '学生' }
      ];
      charts.value[2].series.data = [];
      for (let i = 0; i< facultyRatio.value.count; i++) {
        charts.value[2].series.data.push({ value: facultyRatio.value.faculty[i], name: facultyRatio.value.name[i] });
      }

      initCharts();
      window.addEventListener('resize', () => {
        chartInstances.value.forEach(instance => instance.resize());
      });
    } else {
      ElMessage.error(res.data.msg)
    }
  } catch (err) {
    ElMessage.error('请求失败，请检查网络连接')
  }
})
</script>

<style scoped>
.home {
  width: 100%;
}

.box-card {
  width: 80%;
  margin-bottom: 25px;
  margin-left: 30px;
}

.clearfix {
  text-align: center;
  font-size: 15px;
}

.text {
  text-align: center;
  font-size: 24px;
  font-weight: 700;
  vertical-align: super;
}

.chart-container {
  width: 100%;
  height: 250px;
  margin: auto;
}

.icon {
  width: 50px;
  height: 50px;
  padding-top: 5px;
  padding-right: 10px;
}
</style>
