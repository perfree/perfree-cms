<template>
  <div class="page">
    <div class="search-box">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="菜单名称">
          <el-input v-model="searchForm.name" placeholder="请输入菜单名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="initList" :icon="Search">查询</el-button>
          <el-button :icon="Refresh">重置</el-button>
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
        <el-table-column prop="name" label="菜单名称" width="240" />
        <el-table-column prop="icon" label="图标" width="180">
          <template #default="scope">
            <el-icon v-if="scope.row.icon"><component :is="scope.row.icon" /></el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="seq" label="排序"  width="80"/>
        <el-table-column prop="module" label="模块" min-width="150"/>
        <el-table-column prop="component" label="组件路径" min-width="150"/>
        <el-table-column prop="componentName" label="组件名" min-width="150"/>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="scope">
            <el-tag class="ml-2" type="success" v-if="scope.row.status === 0">开启</el-tag>
            <el-tag class="ml-2" type="danger" v-else>关闭</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="240" fixed="right">
          <template v-slot="scope">
            <el-button size="small" type="primary" link :icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
            <el-button size="small" type="primary" link :icon="Plus" @click="handleAdd(scope.row)" >新增</el-button>
            <el-button size="small" type="primary" link :icon="Delete" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

<!--      <el-pagination-->
<!--          v-model:current-page="searchForm.currentPage"-->
<!--          v-model:page-size="searchForm.pageSize"-->
<!--          :page-sizes="[10, 20, 30, 50]"-->
<!--          layout="total,sizes,prev, pager, next, jumper"-->
<!--          background-->
<!--          small-->
<!--          :total="searchForm.total"-->
<!--      />-->
    </div>

    <!-- 新增/修改 -->
    <el-dialog v-model="open" :title="title">
      <el-form
          ref="ruleFormRef"
          :model="addForm"
          :rules="addRule"
          label-width="120px"
          class="demo-ruleForm"
          status-icon
      >
        <el-form-item label="Activity name" prop="name">
          <el-input v-model="addForm.name" />
        </el-form-item>
        <el-form-item label="Activity zone" prop="region">
          <el-select v-model="addForm.region" placeholder="Activity zone">
            <el-option label="Zone one" value="shanghai" />
            <el-option label="Zone two" value="beijing" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
              <el-button type="primary" @click="submitForm">确 定</el-button>
              <el-button @click="open = false">取 消</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
 
</template>

<script setup>
import {
  Search,
  Plus,
  Refresh,
  Delete,
  Edit,
} from '@element-plus/icons-vue'
import {ref,reactive} from "vue";
import {list} from "@/modules/menu/scripts/api/menu";
import {handleTree} from "@/core/utils/perfree";

const searchForm = reactive({
  // currentPage: 1,
  // pageSize: 20,
  // total: 21,
  name: '',
});

const addForm = reactive({
  name: '',
  region: ''
});

const addRule = reactive({
  name: [
    { required: true, message: 'Please input Activity name', trigger: 'blur' },
    { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
  ],
  region: [
    {
      required: true,
      message: 'Please select Activity zone',
      trigger: 'change',
    },
  ],
});

let loading = ref(false);
let tableData = ref([]);
let open = ref(false);
let title = ref("");

/**
 * 初始化列表
 */
function initList() {
  loading.value = true;
  list(searchForm).then((res) => {
    tableData.value = handleTree(res.data, "id", "parentId",'children', '-1');
    loading.value = false;
  });
}

/**
 * 新增
 */
function handleAdd(row) {
  title.value = "添加菜单";
  open.value = true;
}

initList();
</script>
