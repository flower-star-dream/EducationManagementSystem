<template>
  <div class="home">
    <!-- 搜索 -->
    <div class="search-bar">
      <el-form inline size="small">
        <el-form-item label="学号">
          <el-input v-model="sid" placeholder="请输入学号" clearable></el-input>
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input v-model="sName" placeholder="请输入学生姓名" clearable></el-input>
        </el-form-item>
        <el-form-item label="课程号">
          <el-input v-model="cid" placeholder="请输入课程号" clearable></el-input>
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input v-model="cName" placeholder="请输入课程名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="班级">
          <el-input v-model="className" placeholder="请输入学生所在班级" clearable></el-input>
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
      <el-table-column prop="sid" label="学号" disabled sortable></el-table-column>
      <el-table-column prop="studentName" label="学生姓名" sortable></el-table-column>
      <el-table-column prop="gender" label="性别"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column prop="className" label="班级"></el-table-column>
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
    <el-dialog v-model="editVisible" title="授课学生信息" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="课程号">
          <el-input class="form-input" v-model="form.cid" disabled></el-input>
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input class="form-input" v-model="form.courseName" disabled></el-input>
        </el-form-item>
        <el-form-item label="学号">
          <el-input class="form-input" v-model="form.sid" disabled></el-input>
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input class="form-input" v-model="form.studentName" disabled></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input class="form-input" v-model="form.gender" disabled></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input class="form-input" v-model="form.phone" disabled></el-input>
        </el-form-item>
        <el-form-item label="班级">
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
import { getTeachStuInfoApI} from '@/api/teachStu';
import {useUserStore} from "@/stores/userStore.js";

// 组件的逻辑部分
const sid = ref('');
const cid = ref('');
const sName = ref('');
const cName = ref('');
const className = ref('');

const tableData = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);
const editVisible = ref(false);
const form = reactive({
  cid: '',
  courseName: '',
  teachingType: '',
  credit: '',
  time: '',
  room: '',
  sid: '',
  studentName: '',
  gender: '',
  phone: '',
  className: ''
});
const selectedsIds = ref([]);
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
      sid: sid.value,
      cid: parseInt(cid.value, 10),
      sName: sName.value,
      cName: cName.value,
      className: className.value
    };
    if (isNaN(params.cid)) {
      params.cid = null;
    }
    const res = await getTeachStuInfoApI(params);
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
  sName.value = '';
  cName.value = '';
  className.value = '';
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
  selectedsIds.value = selection.map(row => row.sid);
  selectedcIds.value = selection.map(row => row.cid);
};

// 查看信息
const handleEdit = (row) => {
  form.cid = row.cid;
  form.courseName = row.courseName;
  form.sid = row.sid;
  form.studentName = row.studentName;
  form.gender = row.gender;
  form.phone = row.phone;
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
