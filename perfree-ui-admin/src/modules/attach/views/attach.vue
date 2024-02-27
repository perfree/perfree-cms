<template>
  <div class="page">
    <div class="search-box">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline" ref="searchFormRef">
        <el-form-item label="附件名称">
          <el-input v-model="searchForm.name" placeholder="请输入附件名称" clearable/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="initList" :icon="Search">查询</el-button>
          <el-button :icon="Refresh" @click="resetSearchForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button :icon="UploadFilled" type="primary" plain @click="handleAdd">上传附件</el-button>
      </el-col>
      <div class="right-tool">
        <el-button :icon="Refresh" circle @click="initList"/>
      </div>
    </el-row>

    <div class="table-box">

      <el-table :data="tableData" style="width: 100%;height:100%;" row-key="id" v-loading="loading">
        <el-table-column prop="name" label="附件名称" min-width="150" />
        <el-table-column prop="path" label="附件路径" min-width="150" />
        <el-table-column prop="desc" label="附件描述" min-width="240" />
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
          label-width="80px"
          status-icon
          :rules="addRule"
      >
        <el-form-item label="存储策略" prop="name">
          <el-select v-model="addForm.attachConfigId" placeholder="请选择存储策略" >
            <el-option :key="3" :label="'本地磁盘'" :value="3" />
            <el-option :key="1" :label="'S3对象存储'" :value="1" />
          </el-select>
        </el-form-item>

        <el-form-item label="附件描述" prop="name">
          <el-upload
              class="upload-demo"
              drag
              :headers="headers"
              :action="serverBaseUrl + '/api/attach/upload'"
              multiple
              style="width: 100%"
              :data="{attachConfigId: addForm.attachConfigId}"
          >
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
              Drop file here or <em>click to upload</em>
            </div>
            <template #tip>
              <div class="el-upload__tip">
                jpg/png files with a size less than 500kb
              </div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
 
</template>

<script setup>
import {Delete, Edit, Download, UploadFilled, Refresh, Search } from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import {parseTime} from "@/core/utils/perfree";
import {page} from "@/modules/attach/scripts/api/attach";
import axios_config from "@/core/api/axios_config";
import {CONSTANTS} from "@/core/utils/constants";

let token_info = localStorage.getItem(CONSTANTS.STORAGE_TOKEN);
let serverBaseUrl = axios_config.baseURL;
let tableData = ref([]);
let loading = ref(false);
const searchFormRef = ref();
let open = ref(false);
let title = ref('');
let  headers = {
  Authorization: "Bearer " + JSON.parse(token_info).accessToken,
};
const addFormRef = ref();

const addForm = ref({
  attachConfigId: '',
});
const addRule = reactive({
  attachConfigId: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
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
  title.value = '上传附件';
  open.value = true;
}

initList();
</script>
