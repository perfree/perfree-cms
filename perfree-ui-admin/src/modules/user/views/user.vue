<template>
  <div class="page">
    <div class="search-box">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline" ref="searchFormRef">
        <el-form-item label="用户名称">
          <el-input v-model="searchForm.name" placeholder="请输入用户名称" clearable/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="initList" :icon="Search">查询</el-button>
          <el-button :icon="Refresh" @click="resetSearchForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button :icon="Plus" type="primary" plain @click="handleAdd">新增</el-button>
      </el-col>
      <div class="right-tool">
        <el-button :icon="Refresh" circle @click="initList"/>
      </div>
    </el-row>

    <div class="table-box">

      <el-table :data="tableData" style="width: 100%;height:100%;" row-key="id" v-loading="loading">
        <el-table-column label="序号" min-width="80" type="index" />
        <el-table-column prop="userName" label="用户名称" min-width="150" />
        <el-table-column prop="account" label="账号" min-width="150" />
        <el-table-column prop="status" label="状态" min-width="100">
          <template #default="scope">
            <el-tag class="ml-2" type="success" v-if="scope.row.status === 0">开启</el-tag>
            <el-tag class="ml-2" type="danger" v-else>关闭</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" min-width="150" />
        <el-table-column prop="website" label="网站" min-width="150" />
        <el-table-column prop="createTime" label="创建时间" min-width="120" >
          <template v-slot="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="240" fixed="right">
          <template v-slot="scope">
            <el-button size="small" type="primary" link :icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
            <el-popconfirm
                confirm-button-text="确认"
                cancel-button-text="取消"
                :icon="InfoFilled"
                icon-color="#626AEF"
                title="确定要删除该角色吗?"
                @confirm="handleDelete(scope.row)"
            >
              <template #reference>
                <el-button size="small" type="primary" link :icon="Delete" >删除</el-button>
              </template>
            </el-popconfirm>

          </template>
        </el-table-column>
      </el-table>

            <el-pagination
                v-model:current-page="searchForm.pageNo"
                v-model:page-size="searchForm.pageSize"
                :page-sizes="[10, 20, 30, 50]"
                layout="total,sizes,prev, pager, next, jumper"
                background
                small
                @change="initList"
                :total="searchForm.total"
            />
    </div>

    <el-dialog v-model="open" :title="title" width="600px" draggable>
      <el-form
          ref="addFormRef"
          :model="addForm"
          label-width="120px"
          status-icon
          :rules="addRule"
      >
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="addForm.name" placeholder="请输入角色名称" />
        </el-form-item>

        <el-form-item label="角色编码" prop="code">
          <el-input v-model="addForm.code" placeholder="请输入角色编码" />
        </el-form-item>

        <el-form-item label="描述" prop="description">
          <el-input v-model="addForm.description" placeholder="请输入角色描述"  :autosize="{ minRows: 3, maxRows: 6 }" type="textarea"/>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
              <el-button type="primary" @click="submitAddForm">确 定</el-button>
              <el-button @click="open = false; resetAddForm()">取 消</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
 
</template>

<script setup>
import {addOrUpdate, assignRoleMenu, del, getRole, getRoleMenus, page} from "@/modules/user/scripts/api/user";
import {reactive, ref} from "vue";
import {Delete, Edit, Filter, InfoFilled, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {handleTree, parseTime} from "@/core/utils/perfree";
import {ElMessage} from "element-plus";


const searchForm = ref({
  pageNo: 1,
  pageSize: 20,
  total: 0,
  name: ''
})
const addForm = ref({
  id: '',
  name: '',
  code: '',
  description: ''
});
const addRule = reactive({
  name: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
  code: [{ required: true, message: '请输入角色编码', trigger: 'blur' }],
});

const searchFormRef = ref();
const addFormRef = ref();
let open = ref(false);
let title = ref('');
let tableData = ref([]);
let loading = ref(false);

/**
 * 添加提交
 */
function submitAddForm() {
  addFormRef.value.validate(valid => {
    if (valid) {
      addOrUpdate(addForm.value).then((res) => {
        if (res.code === 200) {
          ElMessage.success('操作成功');
          open.value = false;
          resetAddForm();
          initList();
        } else {
          ElMessage.error(res.msg);
        }
      })
    }
  })
}

/**
 * 新增
 */
function handleAdd() {
  title.value = '添加角色';
  open.value = true;
}

/**
 * 修改
 */
function handleUpdate(row) {
  title.value = '修改角色';
  open.value = true;
  getRole(row.id).then((res) => {
    addForm.value = res.data;
  })
}

/**
 * 删除
 * @param row
 */
function handleDelete(row) {
  del(row.id).then((res) => {
    if (res.code === 200 && res.data) {
      ElMessage.success('删除成功');
      initList();
    } else {
      ElMessage.error(res.msg);
    }
  });
}

/**
 * 加载列表
 */
function initList() {
  loading.value = true;
  page(searchForm.value).then((res) => {
    tableData.value = res.data.list;
    searchForm.value.total = res.data.total;
    loading.value = false;
  })
}

/**
 * 重置搜索表单
 */
function resetSearchForm() {
  searchForm.value = {
    name: ''
  }
  searchFormRef.value.resetFields();
}

/**
 * 重置添加表单
 */
function resetAddForm() {
  addForm.value = {
    id: '',
    name: '',
    code: '',
    description: ''
  }
  addFormRef.value.resetFields();
}

initList();
</script>
