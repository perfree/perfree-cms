<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderCode">
        <el-input
          v-model="queryParams.orderCode"
          placeholder="请输入订单编号"
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
        >新增订单</el-button>
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

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单编号" align="center" prop="orderCode" />
      <el-table-column label="客户名称" align="center" prop="clientId"> 
        <template #default="{ row }">
          <span v-if="row.clientUser">{{ row.clientUser.clientNice }}</span>
          <span v-else=""></span>
        </template>
      </el-table-column>

      <el-table-column label="手机号码" align="center" prop="clientId"> 
        <template #default="{ row }">
          <span v-if="row.clientUser">{{ row.clientUser.clientTell }}</span>
          <span v-else=""></span>
        </template>
      </el-table-column>

      <el-table-column label="订单时间" align="center" prop="orderTime" width="180">
       <template #default="{ row }">
          <span>{{ parseTime(row.orderTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="车辆名称" align="center" prop="carId"> 
        <template #default="{ row }">
          <span v-if="row.car">{{ row.car.carName }}</span>
          <span v-else=""></span>
        </template>
      </el-table-column>

      <el-table-column label="颜色" align="center" prop="carId"> 
        <template #default="{ row }">
          <span v-if="row.car">{{ row.car.carColor }}</span>
          <span v-else=""></span>
        </template>
      </el-table-column>

      <el-table-column label="价格" align="center" prop="carId"> 
        <template #default="{ row }">
          <span v-if="row.car">{{ row.car.carPrice }}</span>
          <span v-else=""></span>
        </template>
      </el-table-column>

      <el-table-column label="车型" align="center" prop="carId"> 
        <template #default="{ row }">
          <span v-if="row.car">{{ row.car.carType }}</span>
          <span v-else=""></span>
        </template>
      </el-table-column>

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

    <!-- 添加或修改订单信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单车辆" prop="carId">
          <el-select v-model="form.carId" class="m-2" placeholder="请选择" style="width: 400px">
              <el-option v-for="item in cars" :key="item.carId" :label="item.carName" :value="item.carId" />
            </el-select>
        </el-form-item>

        <el-form-item label="订单客户" prop="clientId">
          <el-select v-model="form.clientId" class="m-2" placeholder="请选择" style="width: 400px">
              <el-option v-for="item in clientUsers" :key="item.clientId" :label="item.clientNice" :value="item.clientId" />
            </el-select>
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
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/system/order";
import { getAll } from "@/api/system/car";
import { getAllClientUser } from "@/api/system/clientUser";
export default {
  name: "Order",
  data() {
    return {
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
      // 订单信息表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      cars: [],
      clientUsers:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderCode: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        carId: [
          { required: true, message: "车辆ID不能为空", trigger: "blur" }
        ],
        clientId: [
          { required: true, message: "用户ID不能为空", trigger: "blur" }
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
    /** 查询订单信息列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        carId: null,
        clientId: null
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
      this.ids = selection.map(item => item.orderCode)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const orderCode = row.orderCode || this.ids
      getOrder(orderCode).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orderCode != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
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
      const orderCodes = row.orderCode || this.ids;
      this.$modal.confirm('是否确认删除订单信息编号为"' + orderCodes + '"的数据项？').then(function() {
        return delOrder(orderCodes);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
