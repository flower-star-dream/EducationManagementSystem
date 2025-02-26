<template>
  <div class="login-container">
    <el-form ref="formRef" :model="form" :rules="rules" class="login-page">
      <h2 class="title" style="margin-bottom: 20px">系统登陆</h2>
      <el-form-item prop="username">
        <el-input v-model="form.username" clearable>
          <template #prefix>
            <el-icon class="el-input__icon"><User /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="form.password" clearable show-password>
          <template #prefix>
            <el-icon class="el-input__icon"><Lock /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item>
        <div style="display: flex">
          <el-input v-model="form.validCode" style="width: 45%;" placeholder="请输入验证码"></el-input>
          <ValidCode id="ValidCode" @input="createValidCode" style="width: 50%" />
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="width: 100%" @click="login">登 录</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="text" @click="$router.push('/register')">前往注册 >></el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { User, Lock } from '@element-plus/icons-vue';
import { useUserStore } from '@/stores/userStore.js';
import ValidCode from '../components/ValidCode.vue';
import { loginAPI } from '@/api/auth.js';

const form = ref({
  username: '',
  password: '',
  validCode: ''
});

const validCode = ref('');

const rules = ref({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
});

const router = useRouter();
const formRef = ref(null);

const createValidCode = (data) => {
  validCode.value = data;
};

const login = async () => {
  try {
    const valid = await formRef.value.validate();
    if (valid) {
      if (!form.value.validCode) {
        ElMessage.error("请填写验证码");
        return;
      }
      if (form.value.validCode !== validCode.value) {
        ElMessage.error("验证码错误");
        document.getElementById("ValidCode").click();
        return;
      }

      const res = await loginAPI(form.value);
      if (res.data.code === 200) {
        ElMessage.success("登录成功");
        localStorage.setItem('token', res.data.data.token);
        const userStore = useUserStore();
        await userStore.getUserInfo();
        await userStore.getTeacherInfo();
        await userStore.getStudentInfo();
        router.push("/");
      } else {
        ElMessage.error(res.data.message);
      }
    }
  } catch (error) {
    console.error(error);
    ElMessage.error("登录失败:"+error.response.data.message)
  }
};

</script>

<style scoped>
.login-container {
  position: fixed;
  width: 100%;
  height: 100vh;
  background-size: contain;
  overflow: hidden;
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
