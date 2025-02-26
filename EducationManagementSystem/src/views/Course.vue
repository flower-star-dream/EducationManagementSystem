<template>
  <div class="home">
    <!-- 搜索 -->
    <div class="search-bar">
      <el-form inline size="small">
        <el-form-item label="课程编号">
          <el-input v-model="id" placeholder="请输入课程编号" clearable></el-input>
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input v-model="name" placeholder="请输入课程名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="所属教研室名称">
          <el-input v-model="taroName" placeholder="请输入所属教研室名称" clearable></el-input>
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
      <el-button type="primary" @click="add" size="small">新增课程</el-button>
      <el-popconfirm title="确认删除?" @confirm="deleteBatch">
        <template #reference>
          <el-button type="danger" size="small">批量删除</el-button>
        </template>
      </el-popconfirm>
    </div>

    <!-- 数据字段 -->
    <el-table :data="tableData" stripe border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="课程编号" sortable></el-table-column>
      <el-table-column prop="name" label="课程名称"></el-table-column>
      <el-table-column prop="credit" label="课程学分"></el-table-column>
      <el-table-column prop="time" label="课程学时"></el-table-column>
      <el-table-column prop="room" label="教室"></el-table-column>
      <el-table-column prop="taroName" label="所属教研室"></el-table-column>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">修改</el-button>
          <el-popconfirm title="确认删除?" @confirm="handleDelete(scope.row.id)">
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
    <el-dialog v-model="editVisible" :title="form.id ? '修改课程信息': '新增课程信息'" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="课程编号">
          <template v-if="form.id">
            <el-input class="form-input" v-model="form.originalid" disabled></el-input>
          </template>
          <template v-else>
            <el-input class="form-input" v-model="form.originalid"></el-input>
          </template>
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input class="form-input" v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="课程学分">
          <el-input class="form-input" v-model="form.credit"></el-input>
        </el-form-item>
        <el-form-item label="课程学时">
          <el-input class="form-input" v-model="form.time"></el-input>
        </el-form-item>
        <el-form-item label="教室">
          <el-input class="form-input" v-model="form.room"></el-input>
        </el-form-item>
        <el-form-item label="所属教研室名称">
          <el-input class="form-input" v-model="form.taroName"></el-input>
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
import { getCourseInfoApI, batchDeleteCourseApI, deleteCourseApI, updateCourseApI, saveCourseApI, getCourseCheckApI } from '@/api/course';

// 组件的逻辑部分
const name = ref('');
const originalid = ref('');
const id = ref('');
const taroName = ref('');
const tableData = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);
const editVisible = ref(false);
const requiredFields = {
  name: '课程名称',
  credit: '学分',
  time: '学时',
  room: '教室',
  taroName: '所属教研室名称'
};
const form = reactive({
  originalid: '',
  id: '',
  name: '',
  credit: '',
  time: '',
  room: '',
  taroName: ''
});
const selectedIds = ref([]);

// 异步加载数据
const load = async () => {
    const params = {
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      courseId: parseInt(id.value, 10),
      courseName: name.value,
      taroName: taroName.value
    };
  if (isNaN(params.courseId)) {
    params.courseId = null;
  }
  const res = await getCourseInfoApI(params);
  if (res && res.data && res.data.code === 200) {
    tableData.value = res.data.data.records;
    total.value = res.data.data.total;
  } else {
    ElMessage.error('加载数据失败');
  }
};

// 清空搜索条件
const clear = () => {
  originalid.value = '';
  id.value = '';
  name.value = '';
  taroName.value = '';
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


// 删除选中的课程
const handleDelete = async (id) => {
  try{
    const res = await deleteCourseApI(id);
    if (res.data.code === 200) {
      ElMessage.success('删除成功');
      await load();
    } else {
      if (res.data.code == 400) {
        ElMessage.error(res.data);
      } else {
        ElMessage.error('删除失败');
      }
    }
  }catch (error) {
    if (error.response.data.code == 400){
      ElMessage.error(error.response.data);
    }else {
      ElMessage.error('删除失败');
    }
  }
};

// 处理表格行选中事件
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(row => row.id);
};

// 批量删除课程
const deleteBatch = async () => {
  // 处理批量删除逻辑
  if (!selectedIds.value.length) {
    ElMessage.warning('请选择数据！');
    return;
  }
  try {
    const response = await batchDeleteCourseApI(selectedIds.value);
    if (response.data.code === 200) {
      ElMessage.success('删除成功');
      await load();
    } else {
      ElMessage.error(response.data.message);
    }
  } catch (error) {
    if (error.response.data.code == 400){
      ElMessage.error(error.response.data);
    }else {
      ElMessage.error('批量删除失败');
    }
  }
};


// 打开新增课程对话框
const add = () => {
  editVisible.value = true;
  Object.keys(form).forEach(key => {
    form[key] = '';
  });
};

// 处理保存新课程信息
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
  // 更新逻辑
  if (form.id) {
    try {
      const bool = await getCourseCheckApI(form.taroName);
      if (!(bool.data.data)) {
        ElMessage.error('该教研室不存在');
        return;
      }
      const res = await updateCourseApI(form);
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
    form.id = form.originalid;
    if (!form.id) {
      ElMessage.error('课程id不能为空');
      return;
    }
    try {
      const bool = await getCourseCheckApI(form.taroName);
      if (!(bool.data.data)) {
        ElMessage.error('该教研室不存在');
        return;
      }
      const res = await saveCourseApI(form);
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

// 处理修改教研室信息
const handleEdit = (row) => {
  // 设置表单数据
  form.id = row.id;
  form.name = row.name;
  form.credit = row.credit;
  form.time = row.time;
  form.room = row.room;
  form.taroName = row.taroName;
  form.originalid = row.id; // 设置originalid为当前行的id
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
