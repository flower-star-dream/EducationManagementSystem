<template>
  <div class="header">
    <div class="logo">
      <img src="../assets/icon/school.png" class="icon" alt="" />
      <span>教学管理系统</span>
    </div>
    <div>
      <el-dropdown>
        <span class="el-dropdown-link">
          {{ user.username }}
          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="exit">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { ArrowDown } from '@element-plus/icons-vue';
import { useUserStore } from '@/stores/userStore.js';

const user = ref({});
const router = useRouter();
const userStore = useUserStore();
user.value = userStore.userInfo;

const exit = () => {
  userStore.clearUserInfo();
  router.push('/login');
  ElMessage.success('退出系统成功');
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #f5f5f5;
  border-bottom: 1px solid #eaeaea;
}

.logo {
  display: flex;
  align-items: center;
}

.icon {
  width: 40px;
  height: 40px;
  margin-right: 10px;
}

.el-dropdown-link {
  display: flex;
  align-items: center;
  cursor: pointer;
}
</style>
