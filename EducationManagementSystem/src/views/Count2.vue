<template>
  <div class="home">
    <h1 style="margin:0 auto;font-size: 50px;text-align: center">课程数据统计</h1>
      <br/>
    <div id="select">
      <div class="select-container">
        <span class="select-label">请选择要查看的课程：</span>
        <select v-model="selected" class="custom-select">
          <option v-for="option in options" :value="option.value">
            {{ option.text }}
          </option>
        </select>
      </div>
      <span class="selected-course">选择要查看的课程的课程号为: {{ selected }}</span>
    </div>
    <el-row :gutter="20">
      <el-col :span="6" v-for="item in cards" :key="item.title">
        <el-card class="box-card">
          <div slot="header" class="clearfix">{{ item.title }}</div>
          <div class="text item">
            <template v-if="item.data.type"><el-tag :type="item.data.type">{{ item.data.text }}</el-tag></template>
            <template v-else>
              <span>{{ item.data }}</span>
            </template>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { ElMessage } from "element-plus"
import { searchAPI, count2API } from '@/api/count.js';
import {useUserStore} from "@/stores/userStore.js";

const userStore = useUserStore();
const user = ref({});
user.value = userStore.userInfo;

const cards = ref([
  { title: '修习该课程学生数', data: 0},
  { title: '该课程平均成绩', data: 0},
  { title: '该课程挂科人数', data: 0},
  { title: '该课程结课状态', data: '未知'}
]);

const options = ref([
  {text: '未选择', value: '--请选择--' }
])
const selected = ref('--请选择--');

onMounted(async () => {
  try {
    const res0 = await searchAPI(user.value.affiliateId);
    if (res0.data.code === 200) {
      for (let i = 0; i < res0.data.data.count; i++) {
        options.value.push({ text: res0.data.data.courseNames[i], value: res0.data.data.courseIds[i]});
      }
    } else {
      ElMessage.error(res0.data.msg);
    }
  } catch (err) {
    ElMessage.error('请求失败，请检查网络连接')
  }
});
watch(selected, async (choice) => {
  if (choice !== '--请选择--') {
    try {
      const res = await count2API(choice);
      if (res.data.code === 200) {
        cards.value[0].data = res.data.data.studentCount;
        cards.value[1].data = res.data.data.averageScore;
        cards.value[2].data = res.data.data.failCount;
        switch (res.data.data.status){
          case 1:
            cards.value[3].data = { type: 'info', text: '未结课' };
            break;
          case 2:
            cards.value[3].data = { type: 'danger', text: '成绩未全部录入' };
            break;
          default:
            cards.value[3].data = { type: 'success', text: '已结课' };
        }
      } else {
        ElMessage.error(res.data.msg)
      }
    } catch (err) {
      ElMessage.error('请求失败，请检查网络连接')
    }
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

#select {
  font-family: 'Arial', sans-serif;
  color: #333;
  margin: 20px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.select-container {
  display: flex;
  align-items: center;
}

.select-label {
  font-size: 16px;
  font-weight: bold;
  margin-right: 10px;
  white-space: nowrap;
}

.custom-select {
  padding: 10px 20px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: white;
  -webkit-appearance: none; /* 移除默认的下拉箭头 */
  -moz-appearance: none;
  width: 200px;
  background-image: url('data:image/svg+xml;utf8,<svg fill="black" height="30" viewBox="0 0 24 24" width="30" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/><path d="M0 0h24v24H0z" fill="none"/></svg>'); /* 使用SVG图标作为下拉箭头 */
  background-repeat: no-repeat;
  background-position: right 10px center; /* 调整箭头位置 */
  cursor: pointer;
  outline: none; /* 移除焦点时的轮廓 */
  transition: border-color 0.3s ease; /* 平滑过渡边框颜色 */
  flex-grow: 1; /* 让下拉框占据剩余空间 */
}

.custom-select:focus {
  border-color: #007bff; /* 焦点时的边框颜色 */
  box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25); /* 焦点时的阴影 */
}

.custom-select:hover {
  border-color: #007bff; /* 鼠标悬停时的边框颜色 */
}

.selected-course {
  font-size: 16px;
  font-weight: normal;
  margin-top: 10px;
  color: #666;
}

</style>
