<template>
  <div>
    <el-card class="card">
      <el-form
        ref="formRef"
        :model="form"
        status-icon
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="老密码" prop="currentPassword">
          <el-input
            v-model="form.currentPassword"
            type="password"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input
            v-model="form.newPassword"
            type="password"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input
            v-model="form.confirmPassword"
            type="password"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm" class="button">提交</el-button>
          <el-button @click="resetForm" class="button">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/userStore.js';
import { updateUserPasswordAPI } from '@/api/user.js';

const router = useRouter();

const form = reactive({
  currentPassword: '',
  newPassword: '',
  confirmPassword: '',
  storedPassword: '',
  userId: 0,
});

const validateCurrentPassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入老密码'));
  } else {
    callback();
  }
};

const validateNewPassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入新密码'));
  } else {
    callback();
  }
};

const validateConfirmPassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'));
  } else if (value !== form.newPassword) {
    callback(new Error('两次输入密码不匹配'));
  } else {
    callback();
  }
};

const rules = reactive({
  currentPassword: [{ validator: validateCurrentPassword, trigger: 'blur', required: true }],
  newPassword: [{ validator: validateNewPassword, trigger: 'blur', required: true }],
  confirmPassword: [{ validator: validateConfirmPassword, trigger: 'blur', required: true }],
});

const formRef = ref(null);

const user = useUserStore().userInfo;
form.userId = user.id;

const submitForm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const res = await updateUserPasswordAPI({
          userId: form.userId,
          oldPassword: form.currentPassword,
          newPassword: form.newPassword,
        });
        if (res.data.code === 200) {
          // 刷新用户信息
          useUserStore().clearUserInfo();
          router.push('/login');
          ElMessage.success('更新用户密码成功');
        } else {
          ElMessage.error(res.data.message);
        }
      } catch (error) {
        ElMessage.error(error.response.data.message);
      }
    }
  });
};

const resetForm = () => {
  formRef.value.resetFields();
};
</script>

<style scoped>
.card {
  width: 100%;
  margin-left: 120px;
  margin-top: 40px;
}

.button {
  text-align: center;
}

</style>
