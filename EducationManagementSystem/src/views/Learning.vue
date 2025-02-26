<template>
  <div class="home">
    <!-- 搜索 -->
    <div class="search-bar">
      <el-form inline size="small">
        <el-form-item label="学号" v-if="user.usertype!='学生'">
          <el-input v-model="sid" placeholder="请输入学号" clearable></el-input>
        </el-form-item>
        <el-form-item label="学生姓名" v-if="user.usertype!='学生'">
          <el-input v-model="sName" placeholder="请输入学生姓名" clearable></el-input>
        </el-form-item>
        <el-form-item label="课程号">
          <el-input v-model="cid" placeholder="请输入课程号" clearable></el-input>
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input v-model="cName" placeholder="请输入课程名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="课程类型">
          <el-input v-model="learningType" placeholder="请输入课程类型" clearable></el-input>
        </el-form-item>
        <el-form-item label="成绩">
          <el-input v-model="score" placeholder="请输入成绩" clearable></el-input>
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
    <div class="button-bar" v-if="user.usertype!='学生'">
      <el-button type="primary" @click="add" size="small">新增成绩</el-button>
      <el-popconfirm title="确认删除?" @confirm="deleteBatch">
        <template #reference>
          <el-button type="danger" size="small">批量删除</el-button>
        </template>
      </el-popconfirm>
    </div>

    <!-- 数据字段 -->
    <el-table :data="tableData" stripe border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="sid" label="学号" disabled sortable></el-table-column>
      <el-table-column prop="studentName" label="学生姓名" sortable></el-table-column>
      <el-table-column prop="cid" label="课程号" disabled sortable></el-table-column>
      <el-table-column prop="courseName" label="课程名称" sortable></el-table-column>
      <el-table-column prop="learningType" label="课程类型"></el-table-column>
      <el-table-column prop="score" label="成绩" sortable>
        <template v-slot="scope">
          <el-tag v-if="scope.row.score === -1" type="info">未结课或成绩未录入</el-tag>
          <el-tag v-else-if="scope.row.score<60" type="danger">{{scope.row.score}}</el-tag>
          <el-tag v-else-if="scope.row.score<80" type="warning">{{scope.row.score}}</el-tag>
          <el-tag v-else type="success">{{scope.row.score}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)" v-if="user.usertype=='学生'">查看</el-button>
          <el-button size="small" @click="handleEdit(scope.row)" v-if="user.usertype!='学生'">修改</el-button>
          <el-popconfirm title="确认删除?" @confirm="handleDelete(scope.row.sid, scope.row.cid)" v-if="user.usertype!='学生'">
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
    <!-- 修改成绩信息对话框 -->
    <el-dialog v-model="editVisible" v-if="user.usertype!='学生'" :title="form.sid&&form.cid ? '修改成绩信息': '新增成绩信息'" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="学号">
          <template v-if="form.sid">
            <el-input class="form-input" v-model="form.originalsid" disabled></el-input>
          </template>
          <template v-else>
            <el-input class="form-input" v-model="form.originalsid"></el-input>
          </template>
        </el-form-item>
        <el-form-item label="学生姓名">
          <template v-if="form.studentName">
            <el-input class="form-input" v-model="form.originalsName" disabled></el-input>
          </template>
          <template v-else>
            <el-input class="form-input" v-model="form.originalsName"></el-input>
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
        <el-form-item label="课程类型">
          <template v-if="form.learningType">
            <el-input class="form-input" v-model="form.originallearningType" disabled></el-input>
          </template>
          <template v-else>
            <el-input class="form-input" v-model="form.originallearningType"></el-input>
          </template>
        </el-form-item>
        <el-form-item label="成绩">
          <el-input class="form-input" v-model="form.score"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editVisible = false">取消</el-button>
          <el-button type="primary" @click="save">确定</el-button>
        </span>
      </template>
    </el-dialog>
    <!--查看信息-->
    <el-dialog v-model="editVisible" v-if="user.usertype=='学生'" title="成绩信息" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="学号">
          <el-input class="form-input" v-model="form.sid" disabled></el-input>
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input class="form-input" v-model="form.studentName" disabled></el-input>
        </el-form-item>
        <el-form-item label="课程号">
          <el-input class="form-input" v-model="form.cid" disabled></el-input>
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input class="form-input" v-model="form.courseName" disabled></el-input>
        </el-form-item>
        <el-form-item label="课程类型">
          <el-input class="form-input" v-model="form.learningType" disabled></el-input>
        </el-form-item>
        <el-form-item label="成绩">
          <el-input class="form-input" v-model="form.score" disabled></el-input>
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
import { getLearningInfoApI, batchDeleteLearningApI, deleteLearningApI, updateLearningApI, saveLearningApI, getLearningCheckStudentApI, getLearningCheckCourseApI} from '@/api/learning';
import {useUserStore} from "@/stores/userStore.js";

// 组件的逻辑部分
const sid = ref('');
const cid = ref('');
const sName = ref('');
const cName = ref('');
const originalsName = ref('');
const originalcName = ref('');
const learningType = ref('');
const originallearningType = ref('');
const score = ref('');
const tableData = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);
const editVisible = ref(false);
const requiredFields = {
  learningType: '课程类型',
  score: '成绩'
};
const form = reactive({
  sid: '',
  cid: '',
  studentName: '',
  courseName: '',
  learningType: '',
  score: '',
  originalsid: '',
  originalcid: '',
  originalsName: '',
  originalcName: '',
  originallearningType: ''
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
      affiliateId: user.value.usertype != "管理员" ? user.value.affiliateId : null,
      sid: sid.value,
      cid: parseInt(cid.value, 10),
      sName: sName.value,
      cName: cName.value,
      learningType: learningType.value,
      score: parseInt(score.value, 10),
      usertype: user.value.usertype
    };
  if (isNaN(params.cid)) {
    params.cid = null;
  }
  if (isNaN(params.score)){
    params.score = null;
  }
  const res = await getLearningInfoApI(params);
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
  originalsName.value = '';
  originalcName.value = '';
  learningType.value = '';
  originallearningType.value = '';
  score.value = '';
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


// 删除选中的成绩
const handleDelete = async (sid, cid) => {
  const res = await deleteLearningApI(sid, cid);
  if (res.data.code === 200) {
    ElMessage.success('删除成功');
  } else {
    ElMessage.error('删除失败');
  }
  await load();
};

// 处理表格行选中事件
const handleSelectionChange = (selection) => {
  selectedsIds.value = selection.map(row => row.sid);
  selectedcIds.value = selection.map(row => row.cid);
};

// 批量删除成绩
const deleteBatch = async () => {
  // 处理批量删除逻辑
  if (!selectedsIds.value.length) {
    ElMessage.warning('请选择数据！');
    return;
  }
  try {
    const response = await batchDeleteLearningApI(selectedsIds.value, selectedcIds.value);
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


// 打开新增成绩对话框
const add = () => {
  editVisible.value = true;
  Object.keys(form).forEach(key => {
    form[key] = '';
  });
};

// 处理保存新成绩信息
function validateForm(form, requiredFields) {
  for (const field in requiredFields) {
    if (!form[field]) {
      console.log(form);
      ElMessage.error(`${requiredFields[field]}不能为空`);
      return false;
    }
  }
  return true;
}
const save = async () => {
  form.learningType = form.originallearningType;
  form.studentName = form.originalsName;
  form.courseName = form.originalcName;
  if (!validateForm(form, requiredFields)) {
    return;
  }
  if (form.learningType != '必修课' && form.learningType != '选修课') {
      ElMessage.error(`上课类型只能为必修课, 选修课二者其一`);
      return false;
  }
  if (-1 > parseInt(form.score,10) || parseInt(form.score,10) > 100) {
      ElMessage.error(`成绩取值只能为-1到100`);
      return false;
  }
  // 更新逻辑
  if (form.sid && form.cid) {
    try {
      const res = await updateLearningApI(form);
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
    form.sid = form.originalsid;
    form.cid = form.originalcid;
    if (!form.sid) {
      ElMessage.error('学生id不能为空');
      return;
    }
    if (!form.cid) {
      ElMessage.error('课程id不能为空');
      return;
    }
    try {
      const bool = await getLearningCheckStudentApI(form.studentName);
      if (!(bool.data.data)) {
        ElMessage.error('该学生与学生号不一致');
        return;
      }
      const bool2 = await getLearningCheckCourseApI(form.courseName);
      if (!(bool2.data.data)) {
        ElMessage.error('该课程与课程号不一致');
        return;
      }
      const res = await saveLearningApI(form);
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
  form.sid = row.sid;
  form.cid = row.cid;
  form.studentName = row.studentName;
  form.courseName = row.courseName;
  form.originalsid = row.sid;
  form.originalcid = row.cid;
  form.originalsName = row.studentName;
  form.originalcName = row.courseName;
  form.learningType = row.learningType;
  form.originallearningType = row.learningType;
  form.score = row.score;
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
