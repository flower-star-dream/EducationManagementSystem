<template>
  <div class="home">
    <!-- 搜索 -->
    <div class="search-bar">
      <el-form inline size="small">
        <el-form-item label="学号">
          <el-input v-model="id" placeholder="请输入学号" clearable></el-input>
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input v-model="name" placeholder="请输入学生姓名" clearable></el-input>
        </el-form-item>
        <el-form-item label="学生性别">
          <el-input v-model="gender" placeholder="请输入学生性别" clearable></el-input>
        </el-form-item>
        <el-form-item label="学生年龄">
          <el-input v-model="age" placeholder="请输入学生年龄" clearable></el-input>
        </el-form-item>
        <el-form-item label="班级名称">
          <el-input v-model="className" placeholder="请输入班级名称" clearable></el-input>
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
      <el-button type="primary" @click="add" size="small">新增学生</el-button>
      <el-popconfirm title="确认删除?" @confirm="deleteBatch">
        <template #reference>
          <el-button type="danger" size="small">批量删除</el-button>
        </template>
      </el-popconfirm>
    </div>

    <!-- 数据字段 -->
    <el-table :data="tableData" stripe border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="学号" sortable></el-table-column>
      <el-table-column prop="name" label="学生姓名"></el-table-column>
      <el-table-column prop="gender" label="学生性别"></el-table-column>
      <el-table-column prop="age" label="学生年龄"></el-table-column>
      <el-table-column prop="phone" label="学生电话"></el-table-column>
      <el-table-column prop="birthdate" label="出生日期"></el-table-column>
      <el-table-column prop="className" label="班级"></el-table-column>
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
    <el-dialog v-model="editVisible" :title="form.id ? '修改学生信息': '新增学生信息'" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="学号">
          <template v-if="form.id">
            <el-input class="form-input" v-model="form.originalid" disabled></el-input>
          </template>
          <template v-else>
            <el-input class="form-input" v-model="form.originalid"></el-input>
          </template>
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input class="form-input" v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="学生性别">
          <el-input class="form-input" v-model="form.gender"></el-input>
        </el-form-item>
        <el-form-item label="学生年龄">
          <el-input class="form-input" v-model="form.age"></el-input>
        </el-form-item>
        <el-form-item label="学生电话">
          <el-input class="form-input" v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="出生日期">
          <el-input class="form-input" v-model="form.birthdate"></el-input>
        </el-form-item>
        <el-form-item label="班级名称">
          <el-input class="form-input" v-model="form.className"></el-input>
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
import { getStudentInfoApI, batchDeleteStudentApI, deleteStudentApI, updateStudentApI, saveStudentApI, getStudentCheckApI } from '@/api/student';

// 组件的逻辑部分
const name = ref('');
const originalid = ref('');
const id = ref('');
const gender = ref('');
const age = ref('');
const className = ref('');
const tableData = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);
const editVisible = ref(false);
const requiredFields = {
  name: '学生姓名',
  gender: '学生性别',
  age: '学生年龄',
  className: '班级名称'
};
const form = reactive({
  originalid: '',
  id: '',
  name: '',
  gender: '',
  age: '',
  phone: '',
  birthdate: '',
  className: ''
});
const selectedIds = ref([]);

// 异步加载数据
const load = async () => {
    const params = {
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      studentId: id.value,
      studentName: name.value,
      studentGender: gender.value,
      studentAge: parseInt(age.value, 10),
      className: className.value
    };
  if (isNaN(params.studentAge)) {
    params.studentAge = null;
  }
  const res = await getStudentInfoApI(params);
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
  gender.value = '';
  age.value = '';
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


// 删除选中的学生
const handleDelete = async (id) => {
  const res = await deleteStudentApI(id);
  if (res.data.code === 200) {
    ElMessage.success('删除成功');
  } else {
    ElMessage.error('删除失败');
  }
  await load();
};

// 处理表格行选中事件
const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(row => row.id);
};

// 批量删除学生
const deleteBatch = async () => {
  // 处理批量删除逻辑
  if (!selectedIds.value.length) {
    ElMessage.warning('请选择数据！');
    return;
  }
  try {
    const response = await batchDeleteStudentApI(selectedIds.value);
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


// 打开新增学生对话框
const add = () => {
  editVisible.value = true;
  Object.keys(form).forEach(key => {
    form[key] = '';
  });
};

// 处理保存新学生信息
function validateForm(form, requiredFields) {
  for (const field in requiredFields) {
    if (!form[field]) {
      ElMessage.error(`${requiredFields[field]}不能为空`);
      return false;
    }
  }
  return true;
}
function isValidDate(str) {
    const regex = /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/;
    return regex.test(str);
}

const save = async () => {
  if (!validateForm(form, requiredFields)) {
    return;
  }
  if (!isValidDate(form.birthdate)) {
      ElMessage.error(`生日的格式应当为YYYY-MM-DD`);
      return false;
  }
  if (form.gender !== '男' && form.gender !== '女') {
    ElMessage.error(`性别只能为男或女`);
    return false;
  }
  // 更新逻辑
  if (form.id) {
    try {
      const bool = await getStudentCheckApI(form.className);
      if (!(bool.data.data)) {
        ElMessage.error('该班级不存在');
        return;
      }
      const res = await updateStudentApI(form);
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
      ElMessage.error('学生id不能为空');
      return;
    }
    try {
      const bool = await getStudentCheckApI(form.className);
      if (!(bool.data.data)) {
        ElMessage.error('该班级不存在');
        return;
      }
      const res = await saveStudentApI(form);
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

// 处理修改班级信息
const handleEdit = (row) => {
  // 设置表单数据
  form.id = row.id;
  form.name = row.name;
  form.gender = row.gender;
  form.age = row.age;
  form.phone = row.phone;
  form.birthdate = row.birthdate;
  form.className = row.className;
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
