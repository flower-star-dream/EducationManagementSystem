// 管理用户数据相关

import { defineStore } from 'pinia';
import { ref } from 'vue';
import { userInfoAPI } from '@/api/user.js';
import { getOneTeacherInfoApI } from '@/api/teacher';
import { getOneStudentInfoApI } from "@/api/student.js";

export const useUserStore = defineStore('user', () => {
  // 1. 定义管理用户数据的state
  const userInfo = ref({});
  const teacherInfo = ref({});
  const studentInfo = ref({});
  // 2. 定义获取接口数据的action函数
  const getUserInfo = async () => {
    const token = localStorage.getItem("token")
    const res = await userInfoAPI(token);
    userInfo.value = res.data.data;
  };

  const getTeacherInfo = async () => {
    if (userInfo.value.usertype === '教师') {
      const res = await getOneTeacherInfoApI(userInfo.value.affiliateId);
      teacherInfo.value = res.data.data;
    }else {
      teacherInfo.value = {};
    }
  }

  const getStudentInfo = async () => {
    if (userInfo.value.usertype === '学生') {
      const res = await getOneStudentInfoApI(userInfo.value.affiliateId);
      studentInfo.value = res.data.data;
    }else {
      studentInfo.value = {};
    }
  }

  // 退出时清除用户信息
  const clearUserInfo = () => {
    localStorage.removeItem('token');
    userInfo.value = {};
    teacherInfo.value = {};
    studentInfo.value = {};
  };
  // 3. 以对象的格式把state和action return
  return {
    userInfo, teacherInfo, studentInfo, getTeacherInfo, getStudentInfo, getUserInfo, clearUserInfo,
  };
}, {
  persist: true,
});
