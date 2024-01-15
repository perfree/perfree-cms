<template>
  <div class="page">
    <div class="search-box">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="Approved by">
          <el-input v-model="searchForm.user" placeholder="Approved by" clearable />
        </el-form-item>
        <el-form-item label="Activity zone">
          <el-select
              v-model="searchForm.region"
              placeholder="Activity zone"
              clearable
          >
            <el-option label="Zone one" value="shanghai" />
            <el-option label="Zone two" value="beijing" />
          </el-select>
        </el-form-item>
        <el-form-item label="Activity time">
          <el-date-picker
              v-model="searchForm.date"
              type="date"
              placeholder="Pick a date"
              clearable
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">Query</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="table-box">
      <el-table :data="tableData" style="width: 100%;height:100%;">
        <el-table-column prop="date" label="Date" width="180" />
        <el-table-column prop="name" label="Name" width="180" />
        <el-table-column prop="address" label="Address" />
      </el-table>

      <el-pagination
          v-model:current-page="pageParam.currentPage"
          v-model:page-size="pageParam.pageSize"
          :page-sizes="[10, 20, 30, 50]"
          layout="total,sizes,prev, pager, next, jumper"
          background
          small
          :total="pageParam.total"
      />
    </div>
  </div>
 
</template>

<script setup>
import {ref,reactive} from "vue";

const searchForm = reactive({
  user: '',
  region: '',
  date: '',
})

const pageParam = ref({
  currentPage: 1,
  pageSize: 20,
  total: 21
})

const tableData = [
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-02',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
]

function  onSubmit () {
  console.log('submit!')
}
</script>
