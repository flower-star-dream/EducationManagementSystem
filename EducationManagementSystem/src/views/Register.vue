<template>
  <div class="login-container">
    <el-form ref="formRef" :model="form" :rules="rules" class="login-page">
      <h2 class="title" style="margin-bottom: 20px">用户注册</h2>
      <el-form-item prop="username">
        <el-input v-model="form.username" placeholder="请输入用户名" clearable>
          <template #prefix>
            <el-icon class="el-input__icon"><User/></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="form.password" placeholder="请输入密码" clearable show-password>
          <template #prefix>
            <el-icon class="el-input__icon"><Lock/></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="confirm">
        <el-input v-model="form.confirm" placeholder="请再次确认密码" clearable show-password>
          <template #prefix>
            <el-icon class="el-input__icon"><Lock/></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="usertype">
        <el-radio v-model="form.usertype" label="学生">学生</el-radio>
        <el-radio v-model="form.usertype" label="教师">教师</el-radio>
        <el-radio v-model="form.usertype" label="管理员">管理员</el-radio>
      </el-form-item>
      <el-form-item prop="authorize" v-if="form.usertype==='教师'">
        <el-input v-model="form.authorize" placeholder="请输入教师注册码" clearable show-password>
          <template #prefix>
            <el-icon class="el-input__icon"><Lock/></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="authorize" v-if="form.usertype==='管理员'">
        <el-input v-model="form.authorize" placeholder="请输入管理员注册码" clearable show-password>
          <template #prefix>
            <el-icon class="el-input__icon"><Lock/></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="affiliateId" v-if="form.usertype==='教师'">
        <el-input v-model="form.affiliateId" placeholder="请绑定教师号" clearable>
        </el-input>
      </el-form-item>
      <el-form-item prop="affiliateId" v-if=" form.usertype==='学生'">
        <el-input v-model="form.affiliateId" placeholder="请绑定学号" clearable>
        </el-input>
      </el-form-item>
      <el-form-item>
        <div style="display: flex">
          <el-input v-model="form.validCode" style="width: 45%;" placeholder="请输入验证码"></el-input>
          <ValidCode @input="createValidCode" style="width: 50%"/>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width: 100%" @click="register">注 册</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="text" @click="$router.push('/login')">前往登录 >></el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus';
import ValidCode from "../components/ValidCode.vue";
import { User, Lock } from '@element-plus/icons-vue';
import {useRouter} from "vue-router";
import { registerAPI, checkAffiliateIdAPI} from '@/api/auth.js';

const form = reactive({
  username: '',
  password: '',
  confirm: '',
  usertype: '学生',
  authorize: '',
  affiliateId: '',
  validCode: '',
});

const router = useRouter()
const validCode = ref('');

const rules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 13, message: '长度要求为2到13位', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ],
  confirm: [
    { required: true, message: '请确认密码', trigger: 'blur' }
  ],
  authorize: [
    { required: true, message: '请输入注册码', trigger: 'blur' }
  ]
});

const createValidCode = (data) => {
  validCode.value = data;
};

const register = async () => {
  if (!form.validCode) {
    ElMessage.error("请填写验证码");
    return;
  }
  if (form.validCode !== validCode.value) {
    ElMessage.error("验证码错误");
    return;
  }
  if (form.password !== form.confirm) {
    ElMessage.error("两次密码输入不一致");
    return;
  }
  if ((form.usertype === '管理员' || form.usertype === '教师') && (form.authorize !== "teacher" && form.authorize !== "admin")) {
    ElMessage.error("请输入正确的注册码");
    return;
  }
  const bool = await checkAffiliateIdAPI(form.usertype, form.affiliateId);
  if (!bool.data.data) {
    ElMessage.error("学号/教师号不存在");
    return;
  }
  try {
    if (form.usertype === '管理员') {
      form.affiliateId = null;
    }
    const res = await registerAPI(form);
    if (res.data.code === 200) {
      ElMessage.success("注册成功");
      router.push("/login");
    } else {
      ElMessage.error(res.data.message);
    }
  } catch (error) {
    // 这里处理错误，可能是网络错误，或者是后端返回的错误状态码
    console.error('注册失败:', error.response.data);
    ElMessage.error(error.response.data);
  }
};
</script>

<style scoped>
.login-container {
  position: fixed;
  width: 100%;
  height: 100%;
  background-size: contain;
}
.login-page {
  border-radius: 5px;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
</style>
