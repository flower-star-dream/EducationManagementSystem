<template>
  <div class="home">
    <!-- 搜索 -->
    <div class="search-bar">
      <el-form inline size="small">
        <el-form-item label="教师号">
          <el-input v-model="tid" placeholder="请输入教师号" clearable></el-input>
        </el-form-item>
        <el-form-item label="教师姓名">
          <el-input v-model="tName" placeholder="请输入教师姓名" clearable></el-input>
        </el-form-item>
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
    <!-- 按钮 -->
    <div class="button-bar">
      <el-button type="primary" @click="add" size="small">新增教学计划</el-button>
      <el-popconfirm title="确认删除?" @confirm="deleteBatch">
        <template #reference>
          <el-button type="danger" size="small">批量删除</el-button>
        </template>
      </el-popconfirm>
    </div>

    <!-- 数据字段 -->
    <el-table :data="tableData" stripe border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="tid" label="教师号" disabled sortable></el-table-column>
      <el-table-column prop="teacherName" label="教师姓名" sortable></el-table-column>
      <el-table-column prop="cid" label="课程号" disabled sortable></el-table-column>
      <el-table-column prop="courseName" label="课程名称" sortable></el-table-column>
      <el-table-column prop="teachingType" label="上课方式"></el-table-column>
      <el-table-column prop="status" label="状态">
        <template v-slot="scope">
          <el-tag v-if="scope.row.status === 1" type="info">未结课</el-tag>
          <el-tag v-else-if="scope.row.status === 2" type="danger">成绩未全部录入</el-tag>
          <el-tag v-else type="success">已结课</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">修改</el-button>
          <el-popconfirm title="确认删除?" @confirm="handleDelete(scope.row.tid, scope.row.cid)">
            <template #reference>
              <el-button type="danger" size="small">删除</el-button>
            </template>
          </el-popconfirm>
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
    <!-- 修改院系信息对话框 -->
    <el-dialog v-model="editVisible" :title="form.tid&&form.cid ? '修改教学计划信息': '新增教学计划信息'" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="教师号">
          <template v-if="form.tid">
            <el-input class="form-input" v-model="form.originaltid" disabled></el-input>
          </template>
          <template v-else>
            <el-input class="form-input" v-model="form.originaltid"></el-input>
          </template>
        </el-form-item>
        <el-form-item label="教师姓名">
          <template v-if="form.teacherName">
            <el-input class="form-input" v-model="form.originaltName" disabled></el-input>
          </template>
          <template v-else>
            <el-input class="form-input" v-model="form.originaltName"></el-input>
          </template>
        </el-form-item>
        <el-form-item label="课程号">
          <template v-if="form.cid">
            <el-input class="form-input" v-model="form.originalcid" disabled></el-input>
          </template>
          <template v-else>
            <el-input class="form-input" v-model="form.originalcid"></el-input>
          </template>
        </el-form-item>
        <el-form-item label="课程名称">
          <template v-if="form.courseName">
            <el-input class="form-input" v-model="form.originalcName" disabled></el-input>
          </template>
          <template v-else>
            <el-input class="form-input" v-model="form.originalcName"></el-input>
          </template>
        </el-form-item>
        <el-form-item label="上课方式">
          <el-input class="form-input" v-model="form.teachingType"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editVisible = false">取消</el-button>
          <el-button type="primary" @click="save">确定</el-button>
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
import { getTeachingInfoApI, batchDeleteTeachingApI, deleteTeachingApI, updateTeachingApI, saveTeachingApI, getTeachingCheckTeacherApI, getTeachingCheckCourseApI} from '@/api/teaching';
import {useUserStore} from "@/stores/userStore.js";

// 组件的逻辑部分
const tid = ref('');
const cid = ref('');
const tName = ref('');
const cName = ref('');
const originaltName = ref('');
const originalcName = ref('');
const teachingType = ref('');
const tableData = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);
const editVisible = ref(false);
const requiredFields = {
  teachingType: '上课方式'
};
const form = reactive({
  tid: '',
  cid: '',
  teacherName: '',
  courseName: '',
  teachingType: '',
  originaltid: '',
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
      tid: tid.value,
      cid: parseInt(cid.value, 10),
      tName: tName.value,
      cName: cName.value,
      teachingType: teachingType.value
    };
    if (isNaN(params.cid)) {
      params.cid = null;
    }
    const res = await getTeachingInfoApI(params);
    if (res && res.data && res.data.code === 200) {
      tableData.value = res.data.data.records;
      total.value = res.data.data.total;
    } else {
      ElMessage.error('加载数据失败');
    }
};

// 清空搜索条件
const clear = () => {
  tid.value = '';
  cid.value = '';
  tName.value = '';
  cName.value = '';
  originaltName.value = '';
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


// 删除选中的教学计划
const handleDelete = async (tid, cid) => {
  const res = await deleteTeachingApI(tid, cid);
  if (res.data.code === 200) {
    ElMessage.success('删除成功');
  } else {
    ElMessage.error('删除失败');
  }
  await load();
};

// 处理表格行选中事件
const handleSelectionChange = (selection) => {
  selectedtIds.value = selection.map(row => row.tid);
  selectedcIds.value = selection.map(row => row.cid);
};

// 批量删除教学计划
const deleteBatch = async () => {
  // 处理批量删除逻辑
  if (!selectedtIds.value.length) {
    ElMessage.warning('请选择数据！');
    return;
  }
  try {
    const response = await batchDeleteTeachingApI(selectedtIds.value, selectedcIds.value);
    if (response.data.code === 200) {
      ElMessage.success('删除成功');
      await load();
    } else {
      ElMessage.error(response.data.message);
    }
  } catch (error) {
    ElMessage.error('批量删除失败');
  }
};


// 打开新增教学计划对话框
const add = () => {
  editVisible.value = true;
  Object.keys(form).forEach(key => {
    form[key] = '';
  });
};

// 处理保存新教学计划信息
function validateForm(form, requiredFields) {
  for (const field in requiredFields) {
    if (!form[field]) {
      ElMessage.error(`${requiredFields[field]}不能为空`);
      return false;
    }
  }
  return true;
}
const save = async () => {
  if (!validateForm(form, requiredFields)) {
    return;
  }
  if (form.teachingType != '线上教学' && form.teachingType != '线下教学' && form.teachingType != '实验课') {
      ElMessage.error(`上课类型只能为线上教学, 线下教学, 实验课三者其一`);
      return false;
  }
  // 更新逻辑
  if (form.tid && form.cid) {
    try {
      const res = await updateTeachingApI(form);
      if (res.data.code === 200) {
        ElMessage.success('更新成功');
        await load();
      } else {
        ElMessage.error(res.data.message);
      }
    } catch (error) {
      ElMessage.error(error.response.data.message);
    }
    editVisible.value = false;
  } else {
    form.tid = form.originaltid;
    form.cid = form.originalcid;
    form.teacherName = form.originaltName;
    form.courseName = form.originalcName;
    if (!form.tid) {
      ElMessage.error('教师id不能为空');
      return;
    }
    if (!form.cid) {
      ElMessage.error('课程id不能为空');
      return;
    }
    try {
      const bool = await getTeachingCheckTeacherApI(form.teacherName);
      if (!(bool.data.data)) {
        ElMessage.error('该教师与教师号不一致');
        return;
      }
      const bool2 = await getTeachingCheckCourseApI(form.courseName);
      if (!(bool2.data.data)) {
        ElMessage.error('该课程与课程号不一致');
        return;
      }
      const res = await saveTeachingApI(form);
      if (res.data.code === 200) {
        ElMessage.success('新增成功');
        await load();
      } else {
        ElMessage.error(res.data.message);
      }
    } catch (error) {
      ElMessage.error(error.response.data.message);
    }
    await load();
    editVisible.value = false;
  }
};

// 处理修改学院信息
const handleEdit = (row) => {
  // 设置表单数据
  form.tid = row.tid;
  form.cid = row.cid;
  form.teacherName = row.teacherName;
  form.courseName = row.courseName;
  form.originaltid = row.tid;
  form.originalcid = row.cid;
  form.originaltName = row.teacherName;
  form.originalcName = row.courseName;
  form.teachingType = row.teachingType;
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
