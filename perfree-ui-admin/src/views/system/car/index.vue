<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="车辆名称" prop="carName">
        <el-input
          v-model="queryParams.carName"
          placeholder="请输入车辆名称"
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
        >添加车辆</el-button>
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

    <el-table v-loading="loading" :data="carList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="车辆图片" align="center" prop="carImage" >
        <template #default="{ row }">
          <el-image :src="serverUrl + row.carImage"  style="width: auto; height: 100px"/>
        </template>
      </el-table-column>
      <el-table-column label="车辆名称" align="center" prop="carName" />
      <el-table-column label="车型" align="center" prop="carType" />
      <el-table-column label="颜色" align="center" prop="carColor" />
      <el-table-column label="库存" align="center" prop="carCount" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" >编辑</el-button>
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

    <!-- 添加或修改车辆信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="车辆名称" prop="carName">
          <el-input v-model="form.carName" placeholder="请输入车辆名称" />
        </el-form-item>
        <el-form-item label="车型" prop="carType">
          <el-select v-model="form.carType" class="m-2" placeholder="请选择车型" style="width: 400px">
              <el-option :key="'高等车型'" :label="'高等车型'" value="高等车型"/>
              <el-option :key="'中等车型'" :label="'中等车型'" value="中等车型"/>
              <el-option :key="'低端车型'" :label="'低端车型'" value="低端车型"/>
            </el-select>
        </el-form-item>
       
        <el-form-item label="颜色" prop="carColor">
          <el-input v-model="form.carColor" placeholder="请输入车辆颜色" />
        </el-form-item>
        <el-form-item label="库存" prop="carCount">
          <el-input-number v-model="form.carCount" :min="1" :max="99999999" placeholder="库存"  />
        </el-form-item>

        <el-form-item label="价格(元)" prop="carPrice">
          <el-input-number v-model="form.carPrice" :precision="2" :step="10000" :max="999999999999999999999999" placeholder="价格"/>
        </el-form-item>

        <el-form-item label="车辆图片" prop="carImage">
          <el-upload
            class="upload-box"
            drag
            :headers="uploadHeaders"
            :limit="1"
            :action="uploadFileUrl"
            :accept="'image/*'"
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
                支持jpg,png等图片
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
import { listCar, getCar, delCar, addCar, updateCar } from "@/api/system/car";
import { getToken } from "@/utils/auth";
export default {
  name: "Car",
  data() {
    return {
      serverUrl: ref(import.meta.env.VITE_APP_BASE_API),
      uploadFileUrl: ref(import.meta.env.VITE_APP_BASE_API + "/common/upload"),
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
      // 车辆信息表格数据
      carList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        carName: null,
      },
      cars: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        carName: [
        { required: true, message: "车辆名称不能为空", trigger: "change" }
        ],
        carType: [
          { required: true, message: "车型不能为空", trigger: "change" }
        ],
        carPrice: [
          { required: true, message: "车辆价格不能为空", trigger: "change" }
        ],
        carColor: [
          { required: true, message: "颜色不能为空", trigger: "blur" }
        ],
        carCount: [
          { required: true, message: "库存不能为空", trigger: "change" }
        ],
        carImage: [
          { required: true, message: "车辆图片不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询车辆信息列表 */
    getList() {
      this.loading = true;
      listCar(this.queryParams).then(response => {
        this.carList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        carId: null,
        carType: null,
        carPrice: null,
        carColor: null,
        carCount: null,
        fileList: []
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    handleSuccess(response, file, fileList) {
      if(response.code === 200) {
        this.form.carImage = response.fileName;
      }
    },
    handleRemove(file, fileList) {
      this.form.carImage = null;
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.carId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加车辆信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const carId = row.carId || this.ids
      getCar(carId).then(response => {
        this.form = response.data;
        if(this.form.carImage) {
          this.form.fileList = [{name: this.form.carImage.split("/").pop() ,url: this.form.carImage}];
        }
        this.open = true;
        this.title = "修改车辆信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.carId != null) {
            updateCar(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            console.log(this.form);
            addCar(this.form).then(response => {
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
      const carIds = row.carId || this.ids;
      this.$modal.confirm('是否确认删除车辆信息编号为"' + carIds + '"的数据项？').then(function() {
        return delCar(carIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/car/export', {
        ...this.queryParams
      }, `car_${new Date().getTime()}.xlsx`)
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