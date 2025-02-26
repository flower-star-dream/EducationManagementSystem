<template>
  <div class="home">
    <!-- 搜索 -->
    <div class="search-bar">
      <el-form inline size="small">
        <el-form-item label="课程号">
          <el-input v-model="cid" placeholder="请输入课程号" clearable></el-input>
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input v-model="cName" placeholder="请输入课程名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="教师姓名">
          <el-input v-model="tName" placeholder="请输入教师姓名" clearable></el-input>
        </el-form-item>
        <el-form-item label="课程类型">
          <el-input v-model="learningType" placeholder="请输入课程类型" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="load" size="small">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="danger" @click="clear">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 数据字段 -->
    <el-table :data="tableData" stripe border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="cid" label="课程号" disabled sortable></el-table-column>
      <el-table-column prop="courseName" label="课程名称" sortable></el-table-column>
      <el-table-column prop="teachingType" label="上课方式"></el-table-column>
      <el-table-column prop="learningType" label="课程类型"></el-table-column>
      <el-table-column prop="credit" label="学分"></el-table-column>
      <el-table-column prop="time" label="学时"></el-table-column>
      <el-table-column prop="room" label="上课地点"></el-table-column>
      <el-table-column prop="teacherName" label="授课教师姓名" sortable></el-table-column>
      <el-table-column prop="gender" label="性别"></el-table-column>
      <el-table-column prop="phone" label="电话" ></el-table-column>
      <el-table-column prop="title" label="职称" ></el-table-column>
      <el-table-column prop="className" label="授课班级" class="pre-line"></el-table-column>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination-bar">
      <el-pagination
        v-model:currentPage="currentPage"
        :page-sizes="[5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      ></el-pagination>
    </div>
    <!--查看信息-->
    <el-dialog v-model="editVisible" title="课程信息" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="课程号">
          <el-input class="form-input" v-model="form.cid" disabled></el-input>
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input class="form-input" v-model="form.courseName" disabled></el-input>
        </el-form-item>
        <el-form-item label="上课方式">
          <el-input class="form-input" v-model="form.teachingType" disabled></el-input>
        </el-form-item>
        <el-form-item label="课程类型">
          <el-input class="form-input" v-model="form.learningType" disabled></el-input>
        </el-form-item>
        <el-form-item label="学分">
          <el-input class="form-input" v-model="form.credit" disabled></el-input>
        </el-form-item>
        <el-form-item label="学时">
          <el-input class="form-input" v-model="form.time" disabled></el-input>
        </el-form-item>
        <el-form-item label="上课地点">
          <el-input class="form-input" v-model="form.room" disabled></el-input>
        </el-form-item>
        <el-form-item label="授课教师姓名">
          <el-input class="form-input" v-model="form.teacherName" disabled></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input class="form-input" v-model="form.gender" disabled></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input class="form-input" v-model="form.phone" disabled></el-input>
        </el-form-item>
        <el-form-item label="职称">
          <el-input class="form-input" v-model="form.title" disabled></el-input>
        </el-form-item>
        <el-form-item label="授课班级">
          <el-input class="form-input" v-model="form.className" disabled></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button style="background:red;color:white;" @click="editVisible = false">退出</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import {
  ref,
  reactive,
  onMounted,
  computed,
} from 'vue';
import {
  ElButton,
  ElForm,
  ElFormItem,
  ElInput,
  ElTag,
  ElTable,
  ElTableColumn,
  ElPagination,
  ElDialog,
  ElPopconfirm,
  ElMessage,
} from 'element-plus';
import { getLearnTeacherInfoApI} from '@/api/learnTeacher';
import {useUserStore} from "@/stores/userStore.js";

// 组件的逻辑部分
const sid = ref('');
const cid = ref('');
const tName = ref('');
const cName = ref('');
const learningType = ref('');

const tableData = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);
const editVisible = ref(false);
const form = reactive({
  cid: '',
  courseName: '',
  teachingType: '',
  learningType: '',
  credit: '',
  time: '',
  room: '',
  sid: '',
  teacherName: '',
  gender: '',
  phone: '',
  title: '',
  className: ''
});
const selectedcIds = ref([]);
const userStore = useUserStore();
const user = ref({});
user.value = userStore.userInfo;

// 异步加载数据
const load = async () => {
    const params = {
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      sid: user.value.affiliateId,
      cid: parseInt(cid.value, 10),
      tName: tName.value,
      cName: cName.value,
      learningType: learningType.value
    };
    if (isNaN(params.cid)) {
      params.cid = null;
    }
    const res = await getLearnTeacherInfoApI(params);
    if (res && res.data && res.data.code === 200) {
      tableData.value = res.data.data.records;
      total.value = res.data.data.total;
    } else {
      ElMessage.error('加载数据失败');
    }
};

// 清空搜索条件
const clear = () => {
  sid.value = '';
  cid.value = '';
  tName.value = '';
  cName.value = '';
  learningType.value = '';
  currentPage.value = 1;
  load();
};

// 处理分页大小改变事件
const handleSizeChange = (val) => {
  pageSize.value = val;
  load(); // 重新加载数据
};

// 处理当前页改变事件
const handleCurrentChange = (val) => {
  currentPage.value = val;
  load(); // 重新加载数据
};

// 处理表格行选中事件
const handleSelectionChange = (selection) => {
  selectedcIds.value = selection.map(row => row.cid);
};

// 查看信息
const handleEdit = (row) => {
  form.cid = row.cid;
  form.courseName = row.courseName;
  form.teachingType = row.teachingType;
  form.learningType = row.learningType;
  form.credit = row.credit;
  form.time = row.time;
  form.room = row.room;
  form.sid = row.sid;
  form.teacherName = row.teacherName;
  form.gender = row.gender;
  form.phone = row.phone;
  form.title = row.title;
  form.className = row.className;
  editVisible.value = true;
};

// 组件加载时初始化数据
onMounted(() => {
  load(); // 初始化加载数据
});

</script>
<style scoped>
.home {
  width: 100%;
  padding: 10px;
}

.search-bar {
  margin: 10px 0;
}

.search-bar .el-form-item {
  margin-right: 10px;
}

.button-bar {
  margin: 10px 0;
}

.pagination-bar {
  margin: 10px 0;
}

.form-input {
  width: 80%;
}

.dialog-footer {
  text-align: right;
}

</style>
