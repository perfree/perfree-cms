<template>
  <div class="page">
    <div class="search-box">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline" ref="searchFormRef">
        <el-form-item label="插件名称">
          <el-input v-model="searchForm.name" placeholder="请输入插件名称" clearable/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="initList" :icon="Search">查询</el-button>
          <el-button :icon="Refresh" @click="resetSearchForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
      </el-col>
      <div class="right-tool">
        <el-button :icon="Refresh" circle @click="initList"/>
      </div>
    </el-row>

    <div class="table-box">

      <el-table :data="tableData" style="width: 100%;height:100%;" row-key="id" v-loading="loading">
        <el-table-column prop="name" label="插件名称" min-width="150" />
        <el-table-column prop="path" label="安装路径" min-width="150" />
        <el-table-column prop="desc" label="描述" min-width="240" />
        <el-table-column prop="version" label="插件版本" min-width="120" />
        <el-table-column prop="author" label="插件作者" min-width="120" />
        <el-table-column prop="status" label="插件状态" min-width="120" >
          <template #default="scope">
            <el-tag class="ml-2" type="danger" v-if="scope.row.status === 0">禁用</el-tag>
            <el-tag class="ml-2" type="success" v-else>启用</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="安装时间" min-width="120" >
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
                <el-button size="small" type="primary" link :icon="Delete" >卸载</el-button>
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
  </div>
 
</template>

<script setup>
import {Delete, Edit, Filter, InfoFilled, Plus, Refresh, Search} from "@element-plus/icons-vue";
import {parseTime} from "@/core/utils/perfree";
import {ref} from "vue";
import {page} from "@/modules/plugins/scripts/api/plugins";

let tableData = ref([]);

const searchForm = ref({
  pageNo: 1,
  pageSize: 20,
  total: 0,
  name: ''
});

let loading = ref(false);

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

initList();
</script>
