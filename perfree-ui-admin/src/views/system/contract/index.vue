<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="合同编号" prop="contractID">
        <el-input
          v-model="queryParams.contractID"
          placeholder="请输入合同编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户名称" prop="clientNice">
        <el-input
          v-model="queryParams.clientNice"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="search" @click="handleQuery">搜索</el-button>
        <el-button icon="refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="plus"
          @click="handleAdd"
        >添加合同</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="delete"
          :disabled="multiple"
          @click="handleDelete"
        >批量删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="contractList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="合同编号" align="center" prop="contractID" />
      <el-table-column label="客户名称" align="center" prop="userId">
        <template #default="{ row }">
          <span v-if="row.clientUser">{{ row.clientUser.clientNice }}</span>
          <span v-else=""></span>
        </template>
      </el-table-column>
      <el-table-column label="手机号码" align="center" prop="userId">
        <template #default="{ row }">
          <span v-if="row.clientUser">{{ row.clientUser.clientTell }}</span>
          <span v-else=""></span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="contractTime" width="180">
        <template #default="{ row }">
          <span>{{ parseTime(row.contractTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="附件" align="center" prop="contractText">
        <template #default="{ row }">
          <a :href="serverUrl + '/common/download?fileName=' + row.contractText + '&delete=false'" style="color: var(--el-color-primary);" target="_blank" download>{{ row.contractText }}</a>
        </template>
      </el-table-column>
      <el-table-column label="价格" align="center" prop="carId" width="180">
        <template #default="{ row }">
          <span v-if="row.car">{{ row.car.carPrice }}</span>
          <span v-else=""></span>
        </template>
      </el-table-column>
      <el-table-column label="车辆名称" align="center" prop="carId" width="180">
        <template #default="{ row }">
          <span v-if="row.car">{{ row.car.carName }}</span>
          <span v-else=""></span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" >修改</el-button>
          <el-button link type="primary" icon="lock" @click="handleUpdateStatus(scope.row, '排队')" v-if="scope.row.status === '签订'">排队</el-button>
          <el-button link type="primary" icon="unlock" @click="handleUpdateStatus(scope.row, '发货')" v-if="scope.row.status == '排队'">发货</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page.sync="queryParams.pageNum"
      v-model:limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改合同信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户" prop="userId">
          <el-select v-model="form.userId" class="m-2" placeholder="请选择" style="width: 400px">
              <el-option v-for="item in clientUsers" :key="item.clientId" :label="item.clientNice" :value="item.clientId" />
            </el-select>
        </el-form-item>

        <el-form-item label="车辆" prop="carId">
          <el-select v-model="form.carId" class="m-2" placeholder="请选择" style="width: 400px">
              <el-option v-for="item in cars" :key="item.carId" :label="item.carName" :value="item.carId" />
            </el-select>
        </el-form-item>

        <el-form-item label="附件" prop="contractText">
          <el-upload
            class="upload-box"
            drag
            :headers="uploadHeaders"
            :limit="1"
            :action="uploadFileUrl"
            :accept="'.doc,.docx,.pdf'"
            :file-list="form.fileList"
            multiple
            :on-success="handleSuccess"
            :on-remove="handleRemove"
          >
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
              点击或将文件拖拽至此处上传
            </div>
            <template #tip>
              <div class="el-upload__tip">
                支持word/pdf格式
              </div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listContract, getContract, delContract, addContract, updateContract } from "@/api/system/contract";
import { getAll } from "@/api/system/car";
import { getAllClientUser } from "@/api/system/clientUser";
import { getToken } from "@/utils/auth";
export default {
  name: "Contract",
  data() {
    return {
      uploadFileUrl: ref(import.meta.env.VITE_APP_BASE_API + "/common/upload"),
      serverUrl: ref(import.meta.env.VITE_APP_BASE_API),
      uploadHeaders:{"Authorization": getToken()},
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      cars: [],
      clientUsers:[],
      // 合同信息表格数据
      contractList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contractID: null,
        clientNice: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "请选择客户", trigger: "blur" }
        ],
        carId: [
          { required: true, message: "请选择车辆", trigger: "blur" }
        ],
        contractText: [
          { required: true, message: "合同内容不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getCars();
    this.getClientUsers();
  },
  methods: {
    /** 查询合同信息列表 */
    getList() {
      this.loading = true;
      listContract(this.queryParams).then(response => {
        this.contractList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getCars() {
      getAll().then(response => {
        if(response.code === 200) {
          this.cars = response.data;
        }
      });
    },
    getClientUsers() {
      getAllClientUser().then(response => {
        if(response.code === 200) {
          this.clientUsers = response.data;
        }
      });
    },
    handleUpdateStatus(row, status) {
      let contract = {status: status, contractID: row.contractID};
      updateContract(contract).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        userId: null,
        carId: null,
        contractText: null,
        fileList: []
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.contractID)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加合同信息";
    },
    handleSuccess(response, file, fileList) {
      if(response.code === 200) {
        this.form.contractText = response.fileName;
      }
    },
    handleRemove(file, fileList) {
      this.form.contractText = null;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const contractID = row.contractID || this.ids
      getContract(contractID).then(response => {
        this.form = response.data;
        if(this.form.contractText) {
          this.form.fileList = [{name: this.form.contractText.split("/").pop() ,url: this.form.contractText}];
        }
        this.open = true;
        this.title = "修改合同信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.contractID != null) {
            updateContract(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addContract(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const contractIDs = row.contractID || this.ids;
      this.$modal.confirm('是否确认删除合同信息编号为"' + contractIDs + '"的数据项？').then(function() {
        return delContract(contractIDs);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/contract/export', {
        ...this.queryParams
      }, `contract_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style>
.el-upload {
    --el-upload-dragger-padding-horizontal: 10px;
    --el-upload-dragger-padding-vertical: 10px;
}
.upload-box{
  width: 240px;
}
</style>