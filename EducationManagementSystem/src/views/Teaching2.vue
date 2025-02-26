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
        <el-form-item label="上课方式">
          <el-input v-model="teachingType" placeholder="请输入上课方式" clearable></el-input>
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
      <el-table-column prop="tid" label="教师号" disabled sortable></el-table-column>
      <el-table-column prop="teacherName" label="教师姓名" sortable></el-table-column>
      <el-table-column prop="cid" label="课程号" disabled sortable></el-table-column>
      <el-table-column prop="courseName" label="课程名称" sortable></el-table-column>
      <el-table-column prop="teachingType" label="上课方式"></el-table-column>
      <el-table-column prop="learningType" label="课程类型"></el-table-column>
      <el-table-column prop="credit" label="学分"></el-table-column>
      <el-table-column prop="time" label="学时"></el-table-column>
      <el-table-column prop="room" label="上课地点"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template v-slot="scope">
          <el-tag v-if="scope.row.status === 1" type="info">未结课</el-tag>
          <el-tag v-else-if="scope.row.status === 2" type="danger">成绩未全部录入</el-tag>
          <el-tag v-else type="success">已结课</el-tag>
        </template>
      </el-table-column>
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
    <el-dialog v-model="editVisible" title="教学计划信息" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="教师号">
          <el-input class="form-input" v-model="form.tid" disabled></el-input>
        </el-form-item>
        <el-form-item label="教师姓名">
          <el-input class="form-input" v-model="form.teacherName" disabled></el-input>
        </el-form-item>
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
import {getTeachingInfoApI2} from '@/api/teaching';
import {useUserStore} from "@/stores/userStore.js";

// 组件的逻辑部分
const cid = ref('');
const cName = ref('');
const originalcName = ref('');
const teachingType = ref('');
const tableData = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);
const editVisible = ref(false);
const form = reactive({
  tid: '',
  cid: '',
  teacherName: '',
  courseName: '',
  teachingType: '',
  learningType: '',
  credit: '',
  time: '',
  room: '',
  originalcid: '',
  originaltName: '',
  originalcName: '',
});
const selectedtIds = ref([]);
const selectedcIds = ref([]);
const userStore = useUserStore();
const user = ref({});
user.value = userStore.userInfo;

// 异步加载数据
const load = async () => {
    const params = {
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      tid: user.value.affiliateId,
      cid: parseInt(cid.value, 10),
      cName: cName.value,
      teachingType: teachingType.value
    };
    if (isNaN(params.cid)) {
      params.cid = null;
    }
    console.log(params);
    const res = await getTeachingInfoApI2(params);
    if (res && res.data && res.data.code === 200) {
      tableData.value = res.data.data.records;
      total.value = res.data.data.total;
      console.log(tableData.value);
    } else {
      ElMessage.error('加载数据失败');
    }
};

// 清空搜索条件
const clear = () => {
  cid.value = '';
  cName.value = '';
  originalcName.value = '';
  teachingType.value = '';
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
  selectedtIds.value = selection.map(row => row.tid);
  selectedcIds.value = selection.map(row => row.cid);
};

// 处理修改学院信息
const handleEdit = (row) => {
  // 设置表单数据
  form.tid = row.tid;
  form.cid = row.cid;
  form.courseName = row.courseName;
  form.teacherName = row.teacherName;
  form.originalcid = row.cid;
  form.originalcName = row.courseName;
  form.teachingType = row.teachingType;
  form.learningType = row.learningType;
  form.credit = row.credit;
  form.time = row.time;
  form.room = row.room;
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
