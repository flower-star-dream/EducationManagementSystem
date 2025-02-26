<template>
  <div class="home">
    <!-- 搜索 -->
    <div>
      <el-form inline size="small">
        <el-form-item label="用户名">
          <el-input v-model="searchName" placeholder="请输入用户名" clearable>
            <template #prefix>
              <el-icon class="el-input__icon">
                <search />
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="margin-left: 1%" @click="loadusers" size="small">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="danger" @click="clearSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- 按钮 -->
    <div style="margin: 10px 0;">
      <el-popconfirm title="确认删除?" @confirm="deleteSelectedusers">
        <template #reference>
          <el-button type="danger" size="small">批量删除</el-button>
        </template>
      </el-popconfirm>
    </div>
    <!-- 数据字段 -->
    <el-table :data="users" stripe border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" :selectable="selectable"></el-table-column>
      <el-table-column prop="id" label="用户id" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="usertype" label="用户权限" />
      <el-table-column prop="affiliateId" label="绑定教师号/学号" />
      <el-table-column prop="affiliateName" label="教师/学生姓名" />
      <el-table-column fixed="right" label="操作">
        <template v-slot="scope">
          <el-button size="small" @click="editUser(scope.row)" :disabled="scope.row.usertype === '管理员'">编辑</el-button>
          <el-popconfirm title="确认删除?" @confirm="deleteUser(scope.row.id)" :disabled="scope.row.usertype === '管理员'">
            <template #reference>
              <el-button type="danger" size="small" :disabled="scope.row.usertype === '管理员'">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div>
      <el-pagination
        v-model:currentPage="currentPage"
        :page-sizes="[5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalusers"
        @size-change="handlePageSizeChange"
        @current-change="handleCurrentPageChange"
      ></el-pagination>

      <el-dialog v-model="dialogVisible" title="编辑用户信息" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="用户名">
            <el-input style="width: 80%" v-model="form.username"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input style="width: 80%" v-model="form.password"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="saveUser">确定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { batchDeleteUsersAPI, deleteUserAPI, getUsersAPI, updateUserAPI } from '@/api/user.js';
import {useUserStore} from "@/stores/userStore.js";

const searchName = ref('');

const form = reactive({});
const dialogVisible = ref(false);

const totalusers = ref(10);
const currentPage = ref(1);
const pageSize = ref(10);
const users = ref([]);

const user = reactive({});
const userStore = useUserStore();
user.value = userStore.userInfo;
const selectedUserIds = ref([]);

const handleSelectionChange = (val) => {
  selectedUserIds.value = val.map((v) => v.id);
};

const deleteSelectedusers = async () => {
  if (!selectedUserIds.value.length) {
    ElMessage.warning('请选择数据！');
    return;
  }
  try {
    const response = await batchDeleteUsersAPI(selectedUserIds.value);
    if (response.data.code === 200) {
      ElMessage.success('删除成功');
      await loadusers();
    } else {
      ElMessage.error(response.data.message);
    }
  } catch (error) {
    ElMessage.error('批量删除失败');

  }
};

const loadusers = async () => {
  const params = {
    pageNum: currentPage.value,
    pageSize: pageSize.value,
    name: searchName.value
  };
  const res = await getUsersAPI(params);
  users.value = res.data.data.records;
  totalusers.value = res.data.data.total;
};

const clearSearch = () => {
  searchName.value = '';
  loadusers();
};

const selectable= (row)=> {
  return row.usertype !== '管理员';
}

const deleteUser = async (id) => {
  const res = await deleteUserAPI(id);
  if (res.data.code === 200) {
    ElMessage.success('删除成功');
  } else {
    ElMessage.error('删除失败');
  }
  await loadusers();

};

const saveUser = async () => {
  try {
    const res = await updateUserAPI(form);
    if (res.data.code === 200) {
      ElMessage.success('更新用户信息成功');
      // 刷新用户信息
      await loadusers();
      dialogVisible.value = false
    } else {
      ElMessage.error(res.data.message);
    }
  } catch (error) {
    ElMessage.error(error.response.data.message);
  }
};


const editUser = (row) => {
  Object.assign(form, row);
  dialogVisible.value = true;
};

const handlePageSizeChange = (newSize) => {
  pageSize.value = newSize;
  loadusers();
};

const handleCurrentPageChange = (newPage) => {
  currentPage.value = newPage;
  loadusers();
};

onMounted(() => {
  loadusers();
});
</script>

<style scoped>
.home {
  width: 100%;
  padding: 10px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
}
</style>
