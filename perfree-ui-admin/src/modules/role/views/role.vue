<template>
  <div class="page">
    <div class="search-box">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline" ref="searchFormRef">
        <el-form-item label="角色名称">
          <el-input v-model="searchForm.name" placeholder="请输入角色名称" clearable/>
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
        <el-table-column prop="name" label="角色名称" min-width="150" />
        <el-table-column prop="code" label="角色编码" min-width="150" />
        <el-table-column prop="description" label="描述" min-width="240" />
        <el-table-column prop="createTime" label="创建时间" min-width="120" >
          <template v-slot="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="240" fixed="right">
          <template v-slot="scope">
            <el-button size="small" type="primary" link :icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
            <el-button size="small" type="primary" link :icon="Filter" @click="handleRoleMenu(scope.row)">菜单权限</el-button>
            <el-popconfirm
                confirm-button-text="确认"
                cancel-button-text="取消"
                :icon="InfoFilled"
                icon-color="#626AEF"
                title="确定要删除该菜单吗?"
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

    <el-dialog v-model="roleMenuOpen" :title="title" width="800px" draggable>
      <el-form
          ref="menuFormRef"
          :model="menuForm"
          label-width="120px"
          class="demo-ruleForm"
          status-icon
      >
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="menuForm.name" disabled />
        </el-form-item>
        <el-form-item label="角色编码" prop="code">
          <el-input v-model="menuForm.code" disabled />
        </el-form-item>
        <el-form-item label="菜单权限" prop="code">
          <el-checkbox v-model="menuForm.expand" label="展开/折叠" @change="expandAllTree"/>
          <el-checkbox v-model="menuForm.selectAll" label="全选/全不选" @change="selectAllTree"/>
          <div style="width: 100%;border: 1px solid rgb(228 231 237);padding: 5px;">
            <el-tree
                :props="defaultProps"
                :data="menuData"
                node-key="id"
                show-checkbox
                ref="menuTree"
                :check-strictly="true"
                v-loading="menuTreeLoading"
            />
          </div>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
              <el-button type="primary" @click="submitMenuForm">确 定</el-button>
              <el-button @click="roleMenuOpen = false">取 消</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
 
</template>

<script setup>
import {assignRoleMenu, getRoleMenus, page} from "@/modules/role/scripts/api/role";
import {ref} from "vue";
import {Delete, Edit, Filter, InfoFilled, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {handleTree, parseTime} from "@/core/utils/perfree";
import {list} from "@/modules/role/scripts/api/menu";
import {ElMessage} from "element-plus";

let menuData = ref([]);

const defaultProps = {
  children: 'children',
  label: 'name'
}

const searchForm = ref({
  pageNo: 1,
  pageSize: 20,
  total: 0,
  name: ''
})
const menuForm = ref({
  id: '',
  name: '',
  code: '',
  expand: false,
  selectAll: false
});

const menuTree = ref();
const searchFormRef = ref();
let roleMenuOpen = ref(false);
let title = ref('');
let tableData = ref([]);
let loading = ref(false);
let menuTreeLoading = ref(false);

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
 * 菜单权限
 */
function handleRoleMenu(row) {
  roleMenuOpen.value = true;
  menuForm.value.name = row.name;
  menuForm.value.code = row.code;
  menuForm.value.id = row.id;
  title.value = '菜单权限';
  menuTreeLoading.value = true;
  list({}).then((res) => {
    menuData.value = handleTree(res.data, "id", "parentId",'children', '-1');
    getRoleMenus(row.id).then((d) => {
      menuTree.value.setCheckedKeys(d.data);
      menuTreeLoading.value = false;
    })
  })
}

/**
 * 展开/收缩节点
 */
function expandAllTree() {
  if (menuForm.value.expand) {
    Object.values(menuTree.value.store.nodesMap).forEach((v) => v.expand())
  } else {
    Object.values(menuTree.value.store.nodesMap).forEach((v) => v.collapse())
  }
}

/**
 * 选中所有节点
 */
function selectAllTree() {
  if (menuForm.value.selectAll) {
    Object.values(menuTree.value.store.nodesMap).forEach((v) => {
      v.checked = true;
    })
  } else {
    Object.values(menuTree.value.store.nodesMap).forEach((v) => {
      v.checked = false;
    })
  }
}

/**
 * 提交菜单权限
 */
function submitMenuForm() {
  let param = {
    menuIds: [...menuTree.value.getCheckedKeys(), ...menuTree.value.getHalfCheckedKeys()],
    roleId: menuForm.value.id
  };
  assignRoleMenu(param).then((res) => {
    if (res.code === 200 && res.data) {
      ElMessage.success('操作成功');
      roleMenuOpen.value = false;
    } else {
      ElMessage.error(res.msg);
    }
  })
}

initList();
</script>
