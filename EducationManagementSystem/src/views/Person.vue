<template>
  <div>
    <el-card class="card">
      <h2 class="card-title">个人信息</h2>
      <el-form :model="form" ref="formRef" label-width="100px">
        <el-form-item label="用户名">
          <el-input class="input" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="权限">
          <span class="role">{{ form.usertype }}</span>
        </el-form-item>
        <el-form-item :label="form.usertype === '教师' ? '教师号' : form.usertype === '学生' ? '学生号' : ''">
          <span v-if="form.usertype === '教师' || form.usertype === '学生'" class="affiliateId">{{ form.affiliateId }}</span>
          <span v-if="form.usertype === '管理员'" class="affiliateId">管理员无身份绑定</span>
        </el-form-item>
      </el-form>
      <el-form :model="form2" ref="formRef" label-width="100px" v-if="form.usertype === '教师'">
        <el-form-item prop="name" label="教师姓名">
          <span class="name">{{ form2.name }}</span>
        </el-form-item>
        <el-form-item prop="gender" label="教师性别">
          <span class="gender">{{ form2.gender }}</span>
        </el-form-item>
        <el-form-item prop="age" label="教师年龄">
          <span class="age">{{ form2.age }}</span>
        </el-form-item>
        <el-form-item prop="phone" label="教师电话">
          <el-input class="input" v-model="form2.phone"></el-input>
        </el-form-item>
        <el-form-item prop="title" label="教师职称">
         <span class="title">{{ form2.title }}</span>
        </el-form-item>
        <el-form-item prop="degree" label="教师学历">
          <span class="degree">{{ form2.degree }}</span>
        </el-form-item>
        <el-form-item prop="birthdate" label="出生日期">
          <span class="birthdate">{{ form2.birthdate }}</span>
        </el-form-item>
        <el-form-item prop="taroName" label="所属教研室">
          <span class="taroName">{{ form2.taroName }}</span>
        </el-form-item>
      </el-form>
      <el-form :model="form2" ref="formRef" label-width="100px" v-if="form.usertype === '学生'">
        <el-form-item prop="name" label="学生姓名">
          <span class="name">{{ form2.name }}</span>
        </el-form-item>
        <el-form-item prop="gender" label="学生性别">
          <span class="gender">{{ form2.gender }}</span>
        </el-form-item>
        <el-form-item prop="age" label="学生年龄">
          <span class="age">{{ form2.age }}</span>
        </el-form-item>
        <el-form-item prop="phone" label="学生电话">
          <el-input class="input" v-model="form2.phone"></el-input>
        </el-form-item>
        <el-form-item prop="birthdate" label="出生日期">
          <span class="birthdate">{{ form2.birthdate }}</span>
        </el-form-item>
        <el-form-item prop="className" label="所属班级">
          <span class="taroName">{{ form2.className }}</span>
        </el-form-item>
      </el-form>
      <div class="button-container">
        <el-button type="primary" @click="update">保存</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import {ElMessage, ElTableColumn} from 'element-plus';
import { useUserStore } from '@/stores/userStore.js';
import { updateUserAPI } from '@/api/user.js';
import { updateTeacherApI } from '@/api/teacher';
import { updateStudentApI } from '@/api/student';

const form = ref({});
const form2 = ref({});

const userStore = useUserStore();
form.value = userStore.userInfo;
if (form.value.usertype === '教师') {
  form2.value = userStore.teacherInfo;
}else if(form.value.usertype === '学生'){
  form2.value = userStore.studentInfo;
}

const update = async () => {
  try {
    const res = await updateUserAPI(form.value);
    if (res.data.code === 200) {
      // 刷新token
      localStorage.setItem('token', res.data.data.token);
    } else {
      ElMessage.error(res.data.message);
    }

    let res2 = null;
    if (form.value.usertype === '教师') {
      res2 = await updateTeacherApI(form2.value);
    }else if (form.value.usertype === '学生') {
      res2 = await updateStudentApI(form2.value);
    }
    if (res.data.code === 200 && (res2 === null || res2.data.code === 200)) {
      ElMessage.success('更新用户信息成功');
      // 刷新用户信息
      await userStore.getUserInfo();
      if (form.value.usertype === '教师') {
        await userStore.getTeacherInfo();
      }else if (form.value.usertype === '学生') {
        await userStore.getStudentInfo();
      }
    } else {
      ElMessage.error(res.data.message);
    }
  } catch (error) {
    ElMessage.error(error.response.data.message);
  }
};
</script>

<style scoped>
.card {
  width: 150%;
  margin-left: 120px;
  margin-top: 40px;
}

.card-title {
  padding: 30px;
}

.input {
  width: 80%;
}

.textarea {
  width: 80%;
}

.button-container {
  text-align: center;
}

</style>
