<template>
  <div class="home">
    <h1 style="margin:0 auto;font-size: 50px;text-align: center">基础数据统计</h1>
      <br/>
    <el-row :gutter="20">
      <el-col :span="8" v-for="item in cards" :key="item.title">
        <el-card class="box-card">
          <div slot="header" class="clearfix">{{ item.title }}</div>
          <div class="text item">
              <span>{{ item.data }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from "element-plus"
import { count3API } from '@/api/count.js';
import {useUserStore} from "@/stores/userStore.js";

const userStore = useUserStore();
const user = ref({});
user.value = userStore.userInfo;

const cards = ref([
  { title: '平均绩点', data: 0},
  { title: '不及格科目数', data: 0},
  { title: '共修习课程数', data: 0}
]);

onMounted(async () => {
  try {
    const res = await count3API(user.value.affiliateId);
    if (res.data.code === 200) {
      cards.value[0].data = res.data.data.averageGPA;
      cards.value[1].data = res.data.data.failCount;
      cards.value[2].data = res.data.data.count;
    } else {
      ElMessage.error(res.data.msg);
    }
  } catch (err) {
    ElMessage.error('请求失败，请检查网络连接')
  }
});
</script>

<style scoped>
.home {
  width: 100%;
}

.box-card {
  width: 80%;
  margin-bottom: 25px;
  margin-left: 50px;
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


</style>
