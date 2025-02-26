<template>
  <div>
    <el-menu
        style="width: 200px; min-height: calc(100vh - 50px);"
        :default-active="path"
        router
        background-color="#30333c"
        text-color="#fff"
    >
      <el-menu-item index="/count" v-if="user.usertype === '管理员'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icondashboard "></use>
        </svg>
        <span>数据统计</span>
      </el-menu-item>
      <el-menu-item index="/count2" v-if="user.usertype === '教师'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icondashboard "></use>
        </svg>
        <span>数据统计</span>
      </el-menu-item>
      <el-menu-item index="/count3" v-if="user.usertype === '学生'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icondashboard "></use>
        </svg>
        <span>数据统计</span>
      </el-menu-item>
      <el-sub-menu index="1"  v-if="user.usertype === '管理员' || user.usertype === '教师'">
        <template #title>
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-manage "></use>
          </svg>
          <span>数据管理</span>
        </template>
        <el-menu-item index="/faculty" v-if="user.usertype === '管理员'">
          <svg class="icon" aria-hidden="true" >
            <use xlink:href="#icon-faculty "></use>
          </svg>
          <span>院系管理</span>
        </el-menu-item>
        <el-menu-item index="/taro" v-if="user.usertype === '管理员'">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-taro "></use>
          </svg>
          <span>教研室管理</span>
        </el-menu-item>
        <el-menu-item index="/major" v-if="user.usertype === '管理员'">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-major "></use>
          </svg>
          <span>专业管理</span>
        </el-menu-item>
        <el-menu-item index="/class" v-if="user.usertype === '管理员'">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-class "></use>
          </svg>
          <span>班级管理</span>
        </el-menu-item>
        <el-menu-item index="/course" v-if="user.usertype === '管理员'">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-course "></use>
          </svg>
          <span>课程管理</span>
        </el-menu-item>
        <el-menu-item index="/student" v-if="user.usertype === '管理员'">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-student "></use>
          </svg>
          <span>学生管理</span>
        </el-menu-item>
        <el-menu-item index="/teacher" v-if="user.usertype === '管理员'">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-teacher "></use>
          </svg>
          <span>教师管理</span>
        </el-menu-item>
        <el-menu-item index="/teaching" v-if="user.usertype === '管理员'">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-teaching "></use>
          </svg>
          <span>课程安排</span>
        </el-menu-item>
        <el-menu-item index="/learning">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-learning "></use>
          </svg>
          <span>成绩管理</span>
        </el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="2"  v-if="user.usertype !== '管理员'">
        <template #title>
          <el-icon>
            <grid/>
          </el-icon>
          <span>信息查询</span>
        </template>
        <el-menu-item index="/teaching2" v-if="user.usertype === '教师'">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-teaching"></use>
          </svg>
          <span>教授课程</span>
        </el-menu-item>
        <el-menu-item index="/teachStu" v-if="user.usertype === '教师'">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-student"></use>
          </svg>
          <span>授课学生</span>
        </el-menu-item>
        <el-menu-item index="/learnTeacher" v-if="user.usertype === '学生'">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-teaching"></use>
          </svg>
          <span>课程查询</span>
        </el-menu-item>
        <el-menu-item index="/learning" v-if="user.usertype === '学生'">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-learning"></use>
          </svg>
          <span>成绩查询</span>
        </el-menu-item>
      </el-sub-menu>

      <el-menu-item index="/user" v-if="user.usertype === '管理员'">
        <svg class="icon" aria-hidden="true">
          <use xlink:href="#icon-user "></use>
        </svg>
        <span>账号管理</span>
      </el-menu-item>

      <el-sub-menu index="3" text-color="#fff">
        <template #title>
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-mingpian "></use>
          </svg>
          <span>个人信息</span>
        </template>
        <el-menu-item index="/person" style="font-color: white">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-a-bianji1 "></use>
          </svg>
          <span>修改个人信息</span>
        </el-menu-item>
        <el-menu-item index="/password">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-mima"></use>
          </svg>
          <span>修改密码</span>
        </el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import { useUserStore } from '@/stores/userStore.js';

const user = ref({});
const route = useRoute();
const path = ref(route.path);
const userStore = useUserStore();
user.value = userStore.userInfo;

</script>

<style scoped>
.icon {
  width: 30px;
  height: 30px;
  padding-top: 5px;
  padding-right: 10px;
}
</style>
