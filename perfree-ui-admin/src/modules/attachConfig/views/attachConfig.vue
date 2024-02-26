<template>
  <div class="page">
    <div class="search-box">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline" ref="searchFormRef">
        <el-form-item label="配置名称">
          <el-input v-model="searchForm.name" placeholder="请输入配置名称" clearable/>
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
        <el-table-column prop="name" label="配置名称" min-width="150" />
        <el-table-column prop="path" label="存储器" min-width="150" />
        <el-table-column prop="desc" label="备注" min-width="240" />
        <el-table-column prop="desc" label="默认配置" min-width="240" />
        <el-table-column prop="createTime" label="创建时间" min-width="120" >
          <template v-slot="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="240" fixed="right">
          <template v-slot="scope">
            <el-button size="small" type="primary" link :icon="Download" @click="handleDelete(scope.row)">下载</el-button>
            <el-button size="small" type="primary" link :icon="Edit" @click="handleUpdate(scope.row)">修改</el-button>
            <el-button size="small" type="primary" link :icon="Delete" @click="handleDelete(scope.row)">删除</el-button>

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
        <el-form-item label="名称" prop="name">
          <el-input v-model="addForm.name" placeholder="请输入名称" />
        </el-form-item>

        <el-form-item label="备注" prop="name">
          <el-input v-model="addForm.name" placeholder="请输入备注" />
        </el-form-item>

        <el-form-item label="存储器" prop="name">
          <el-select v-model="addForm.storage" placeholder="请选择存储器" >
            <el-option :key="0" :label="'本地磁盘'" :value="0" />
            <el-option :key="1" :label="'S3对象存储'" :value="1" />
          </el-select>
        </el-form-item>

        <el-form-item label="存储路径" prop="name" v-if="addForm.storage === 0">
          <el-input v-model="addForm.name" placeholder="请输入存储路径" />
        </el-form-item>

        <el-form-item label="节点地址" prop="name" v-if="addForm.storage === 1">
          <el-input v-model="addForm.name" placeholder="请输入节点地址" />
        </el-form-item>

        <el-form-item label="存储bucket" prop="name" v-if="addForm.storage === 1">
          <el-input v-model="addForm.name" placeholder="请输入存储bucket" />
        </el-form-item>

        <el-form-item label="accessKey" prop="name" v-if="addForm.storage === 1">
          <el-input v-model="addForm.name" placeholder="请输入accessKey" />
        </el-form-item>

        <el-form-item label="accessSecret" prop="name" v-if="addForm.storage === 1">
          <el-input v-model="addForm.name" placeholder="请输入accessSecret" />
        </el-form-item>

        <el-form-item label="访问域名" prop="name" v-if="addForm.storage === 1">
          <el-input v-model="addForm.name" placeholder="请输入访问域名" />
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
import {Delete, Edit, Download, Plus, Refresh, Search, Tools} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import {parseTime} from "@/core/utils/perfree";
import {page} from "@/modules/attach/scripts/api/attach";

let tableData = ref([]);
let loading = ref(false);
const searchFormRef = ref();
let open = ref(false);
let title = ref('');
const addFormRef = ref();

const addForm = ref({
  id: '',
  name: '',
  code: '',
  storage: undefined,
  description: ''
});
const addRule = reactive({
  name: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
  code: [{ required: true, message: '请输入角色编码', trigger: 'blur' }],
});


const searchForm = ref({
  pageNo: 1,
  pageSize: 20,
  total: 0,
  name: ''
})

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
 * 新增
 */
function handleAdd() {
  title.value = '新增配置';
  open.value = true;
}

initList();
</script>
