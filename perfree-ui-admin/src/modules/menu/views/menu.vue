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
    <el-dialog v-model="open" :title="title" width="800px" draggable>
      <el-form
          ref="ruleFormRef"
          :model="addForm"
          :rules="addRule"
          label-width="120px"
          class="demo-ruleForm"
          status-icon
      >
        <el-form-item label="父级菜单" prop="parentId">
          <el-tree-select
              v-model="addForm.partentId"
              :data="tableData"
              :props="treeSelectProps"
              check-strictly
              :render-after-expand="false"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="菜单类型" prop="type">
          <el-radio-group v-model="addForm.type">
            <el-radio :label="0">目录</el-radio>
            <el-radio :label="1">菜单</el-radio>
            <el-radio :label="2">按钮</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-row>
          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" >
            <el-form-item label="菜单名称" prop="name">
              <el-input v-model="addForm.name" placeholder="请输入菜单名称"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" >
            <el-form-item label="菜单分类" prop="type">
              <el-radio-group v-model="addForm.type">
                <el-radio :label="0">前台</el-radio>
                <el-radio :label="1">后台</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" >
            <el-form-item label="菜单图标" prop="icon">
              <el-icon-picker v-model="addForm.icon"></el-icon-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" >
            <el-form-item label="菜单排序" prop="seq">
              <el-input-number
                  v-model="addForm.seq"
                  :min="0"
                  :max="9999"
                  controls-position="right"
                  placeholder="菜单排序"
              />
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" >
            <el-form-item label="菜单地址" prop="path">
              <el-input v-model="addForm.path"  placeholder="请输入菜单地址"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" >
            <el-form-item label="所属模块" prop="module">
              <el-input v-model="addForm.module"  placeholder="请输入所属模块"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" >
            <el-form-item label="组件名称" prop="componentName">
              <el-input v-model="addForm.componentName"  placeholder="请输入组件名称"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" >
            <el-form-item label="组件路径" prop="component">
              <el-input v-model="addForm.component" placeholder="请输入组件路径"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" >
            <el-form-item label="权限标识" prop="perms">
              <el-input v-model="addForm.perms" placeholder="请输入权限标识"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" >
            <el-form-item label="打开方式" prop="target">
              <el-radio-group v-model="addForm.target">
                <el-radio :label="0">本页</el-radio>
                <el-radio :label="1">新窗口</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" >
            <el-form-item label="是否外链" prop="isFrame">
              <el-radio-group v-model="addForm.isFrame">
                <el-radio :label="0">是</el-radio>
                <el-radio :label="1">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" >
            <el-form-item label="菜单状态" prop="status">
              <el-radio-group v-model="addForm.status">
                <el-radio :label="0">开启</el-radio>
                <el-radio :label="1">关闭</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
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
import ElIconPicker from "@/core/components/el-icon-picker.vue";

const searchForm = reactive({
  // currentPage: 1,
  // pageSize: 20,
  // total: 21,
  name: '',
});

const treeSelectProps = reactive({
  children: 'children',
  label: 'name',
  value: 'id',
})

const addForm = reactive({
  seq: 0,
  icon: '',
  type: 0,
  parentId: '',
  name: '',
  path: '',
  module: '',
  componentName: '',
  component: '',
  perms: '',
  target: 0,
  isFrame: 1,
  status: 0
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
  console.log(row)
  title.value = "添加菜单";
  open.value = true;
  if (row) {
    addForm.parentId = row.id;
  }
}

initList();
</script>
