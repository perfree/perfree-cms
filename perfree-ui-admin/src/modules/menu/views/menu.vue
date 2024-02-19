<template>
  <div class="page">
    <div class="search-box">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline" ref="searchFormRef">
        <el-form-item label="菜单名称">
          <el-input v-model="searchForm.name" placeholder="请输入菜单名称" clearable/>
        </el-form-item>

        <el-form-item label="菜单分类">
          <el-select v-model="searchForm.type"  placeholder="请选择菜单分类" clearable>
            <el-option :key="0" label="前台" :value="0" />
            <el-option :key="1" label="后台" :value="1" />
          </el-select>
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
        <el-table-column prop="name" label="菜单名称" width="240" />
        <el-table-column prop="icon" label="图标" width="180">
          <template #default="scope">
            <el-icon v-if="scope.row.icon"><component :is="scope.row.icon" /></el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="seq" label="排序"  width="80"/>
        <el-table-column prop="module" label="所属模块" min-width="150"/>
        <el-table-column prop="path" label="菜单地址" min-width="150"/>
        <el-table-column prop="component" label="组件路径" min-width="150"/>
        <el-table-column prop="componentName" label="组件名" min-width="150"/>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="scope">
            <el-tag class="ml-2" type="success" v-if="scope.row.status === 0">开启</el-tag>
            <el-tag class="ml-2" type="danger" v-else>关闭</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="菜单分类" width="80">
          <template #default="scope">
            <el-tag class="ml-2" type="success" v-if="scope.row.type === 0">前台</el-tag>
            <el-tag class="ml-2" type="info" v-else>后台</el-tag>
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

    </div>

    <!-- 新增/修改 -->
    <el-dialog v-model="open" :title="title" width="800px" draggable>
      <el-form
          ref="ruleFormRef"
          :model="addForm"
          :rules="addRule"
          label-width="100px"
          class="demo-ruleForm"
          status-icon
      >
        <el-form-item label="父级菜单" prop="parentId">
          <el-tree-select
              v-model="addForm.parentId"
              :data="treeData"
              :props="treeSelectProps"
              check-strictly
              :render-after-expand="false"
              style="width: 100%"
              clearable
          />
        </el-form-item>
        <el-form-item label="菜单类型" prop="menuType">
          <el-radio-group v-model="addForm.menuType">
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
          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" v-if="addForm.menuType === 0 || addForm.menuType === 1">
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

          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" v-if="addForm.menuType === 1">
            <el-form-item label="菜单地址" prop="path">
              <el-input v-model="addForm.path"  placeholder="请输入菜单地址"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" v-if="addForm.menuType === 1">
            <el-form-item label="所属模块" prop="module">
              <el-input v-model="addForm.module"  placeholder="请输入所属模块"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" v-if="addForm.menuType === 1">
            <el-form-item label="组件名称" prop="componentName">
              <el-input v-model="addForm.componentName"  placeholder="请输入组件名称"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" v-if="addForm.menuType === 1">
            <el-form-item label="组件路径" prop="component">
              <el-input v-model="addForm.component" placeholder="请输入组件路径"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" v-if="addForm.menuType === 1 || addForm.menuType === 2">
            <el-form-item label="权限标识" prop="perms">
              <el-input v-model="addForm.perms" placeholder="请输入权限标识"/>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12"  v-if="addForm.menuType === 1">
            <el-form-item label="打开方式" prop="target">
              <el-radio-group v-model="addForm.target">
                <el-radio :label="0">本页</el-radio>
                <el-radio :label="1">新窗口</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12"  v-if="addForm.menuType === 1">
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
              <el-button @click="cancelAdd">取 消</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
 
</template>

<script setup>
import {Delete, Edit, Plus, Refresh, Search,} from '@element-plus/icons-vue'
import {reactive, ref} from "vue";
import {addOrUpdate, del, get, list} from "@/modules/menu/scripts/api/menu";
import {handleTree} from "@/core/utils/perfree";
import ElIconPicker from "@/core/components/el-icon-picker.vue";
import {ElMessage, ElMessageBox} from "element-plus";

const searchForm = ref({
  name: '',
  type: ''
});

const treeSelectProps = reactive({
  children: 'children',
  label: 'name',
  value: 'id',
})

let addForm = ref({
  seq: 0,
  icon: '',
  type: 0,
  parentId: '-1',
  name: '',
  path: '',
  module: '',
  componentName: '',
  component: '',
  perms: '',
  target: 0,
  isFrame: 1,
  status: 0,
  menuType: 0
});

const addRule = reactive({
  name: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
  seq: [{ required: true, message: '请输入排序', trigger: 'blur' }],
  menuType: [{ required: true, message: '请选择菜单分类', trigger: 'blur' }],
  status: [{ required: true, message: '请选择菜单状态', trigger: 'blur' }],
  target: [{ required: true, message: '请选择菜单打开方式', trigger: 'blur' }],
  isFrame: [{ required: true, message: '请选择菜单是否为外链', trigger: 'blur' }],
  path: [{ required: true, message: '请输入菜单地址', trigger: 'blur' }],
  type: [{ required: true, message: '请选择菜单类型', trigger: 'blur' }],
});

const searchFormRef = ref();
const ruleFormRef = ref();
let loading = ref(false);
let tableData = ref([]);
let open = ref(false);
let title = ref("");
let treeData = ref([]);

/**
 * 初始化列表
 */
function initList() {
  loading.value = true;
  list(searchForm.value).then((res) => {
    tableData.value = handleTree(res.data, "id", "parentId",'children', '-1');
    treeData.value = [{id: '-1', name: '主类目', children: tableData.value}];
    loading.value = false;
  });
}

/**
 * 新增
 */
function handleAdd(row) {
  title.value = "添加菜单";
  open.value = true;
  if (row && row.id) {
    addForm.value.parentId = row.id;
    addForm.value.type = row.type;
  }
}

/**
 * 取消
 */
function cancelAdd() {
  open.value = false;
  resetForm();
}

/**
 * 重置form
 */
function resetForm() {
  addForm.value = {
    seq: 0,
    icon: '',
    type: 0,
    parentId: '-1',
    name: '',
    path: '',
    module: '',
    componentName: '',
    component: '',
    perms: '',
    target: 0,
    isFrame: 1,
    status: 0,
    menuType: 0
  }
  ruleFormRef.value.resetFields();
}

/**
 * 更新
 * @param row
 */
function handleUpdate(row) {
  get(row.id).then((res) => {
    addForm.value = res.data;
    title.value = "修改菜单";
    open.value = true;
  })
}

/**
 * 提交
 */
function submitForm() {
  ruleFormRef.value.validate(valid => {
    if (valid) {
      if (!addForm.value.parentId) {
        addForm.value.parentId = '-1';
      }
      addOrUpdate(addForm.value).then((res) => {
        if (res.code === 200) {
          ElMessage.success('操作成功');
          open.value = false;
          resetForm();
          initList();
        } else {
          ElMessage.error(res.msg);
        }
      })
    }
  })
}

/**
 * 删除
 * @param row
 */
function handleDelete(row) {
  ElMessageBox.confirm('确定要删除[' + row.name + ']吗？', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    del(row.id).then((res) => {
      if (res.code === 200 && res.data) {
        ElMessage.success('删除成功');
        initList();
      } else {
        ElMessage.error(res.msg);
      }
    });
  }).catch(() => {})
}

/**
 * 重置搜索表单
 */
function resetSearchForm() {
  searchForm.value = {
    name: '',
    type: ''
  }
  searchFormRef.value.resetFields();
}

initList();
</script>
