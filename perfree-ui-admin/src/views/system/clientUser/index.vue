<template>
  <div class="app-container">
    <el-tabs v-model="activeName" class="demo-tabs">
    <el-tab-pane label="我的客户" name="我的客户"></el-tab-pane>
    <el-tab-pane label="下级客户" name="下级客户"></el-tab-pane>
  </el-tabs>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户名称" prop="clientNice">
        <el-input
          v-model="queryParams.clientNice"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="clientTell">
        <el-input
          v-model="queryParams.clientTell"
          placeholder="请输入手机号码"
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
        >添加客户</el-button>
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

    <el-table v-loading="loading" :data="clientUserList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="客户名称" align="center" prop="clientNice" />
      <el-table-column label="客户标签" align="center" prop="tag" />
      <el-table-column label="手机号码" align="center" prop="clientTell" />
      <el-table-column label="线索来源" align="center" prop="dataSource" />
      <el-table-column label="意向车型" align="center" prop="carType">
        <template #default="{ row }">
          <span v-if="row.car">{{ row.car.carName }}</span>
          <span v-else="">该车型已删除</span>
        </template>
      </el-table-column>
      <el-table-column label="跟进状态" align="center" prop="status" />
      <el-table-column label="客户地址" align="center" prop="clientAdrress" />
      <el-table-column label="创建日期" align="center" prop="createTime" width="180">
        <template #default="{ row }">
          <span>{{ parseTime(row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="240px">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" >编辑</el-button>
          <el-button link type="primary" icon="Edit"  @click="handleMove(scope.row)">转移</el-button>
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

    <!-- 添加或修改客户信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户名称" prop="clientNice">
          <el-input v-model="form.clientNice" placeholder="请输入客户名称" />
        </el-form-item>
        <el-form-item label="手机号码" prop="clientTell">
          <el-input v-model="form.clientTell" placeholder="请输入客户手机号码" />
        </el-form-item>
        <el-form-item label="客户标签" prop="tag">
          <el-input v-model="form.tag" placeholder="请输入客户标签" />
        </el-form-item>
        <el-form-item label="线索来源" prop="dataSource">
          <el-select v-model="form.dataSource" class="m-2" placeholder="请选择" style="width: 400px">
              <el-option :key="'线下导入'" :label="'线下导入'" value="线下导入"/>
              <el-option :key="'微信朋友圈'" :label="'微信朋友圈'" value="微信朋友圈"/>
              <el-option :key="'微信好友'" :label="'微信好友'" value="微信好友"/>
              <el-option :key="'客户推荐'" :label="'客户推荐'" value="客户推荐"/>
            </el-select>
        </el-form-item>

        <el-form-item label="意向车型" prop="carType">
          <el-select v-model="form.carType" class="m-2" placeholder="请选择" style="width: 400px">
              <el-option v-for="item in cars" :key="item.carId" :label="item.carName" :value="item.carId" />
            </el-select>
        </el-form-item>

        <el-form-item label="跟进状态" prop="status">
          <el-select v-model="form.status" class="m-2" placeholder="请选择" style="width: 400px">
              <el-option :key="'预约试驾'" :label="'预约试驾'" value="预约试驾"/>
              <el-option :key="'有意愿购买'" :label="'有意愿购买'" value="有意愿购买"/>
              <el-option :key="'已确定购买'" :label="'已确定购买'" value="已确定购买"/>
              <el-option :key="'取消试驾'" :label="'取消试驾'" value="取消试驾"/>
              <el-option :key="'没有意愿购买'" :label="'没有意愿购买'" value="没有意愿购买"/>
            </el-select>
        </el-form-item>

        <el-form-item label="客户地址" prop="clientAdrress">
          <el-input v-model="form.clientAdrress" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


     <!-- 转移 -->
     <el-dialog :title="'转移客户'" v-model="moveOpen" width="500px" append-to-body>
      <el-form ref="moveForm" :model="moveForm" :rules="moveRules" label-width="80px">
        <el-form-item label="目标人员" prop="userId">
          <el-select v-model="moveForm.userId" class="m-2" placeholder="请选择" style="width: 400px">
              <el-option v-for="item in users" :key="item.adminID" :label="item.adminName" :value="item.adminID" />
            </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="moveSubmitForm">确 定</el-button>
        <el-button @click="moveCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listClientUser, getClientUser, delClientUser, addClientUser, updateClientUser } from "@/api/system/clientUser";
import { getAll } from "@/api/system/car";
import { getAllUser } from "@/api/system/user";
export default {
  name: "ClientUser",
  data() {
    return {
      // 遮罩层
      loading: true,
      activeName: '我的客户',
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
      // 客户信息表格数据
      clientUserList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      moveOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        clientNice: null,
        clientTell: null,
      },
      // 表单参数
      form: {},
      users: [],
      cars: [],
      moveForm: {},
      moveRules: {
        userId: [
        { required: true, message: "请选择转移人", trigger: "blur" }
        ]
      },
      // 表单校验
      rules: {
        clientNice: [
          { required: true, message: "客户名称不能为空", trigger: "blur" }
        ],
        clientTell: [
          { required: true, message: "手机号码不能为空", trigger: "blur" },
          { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
        ],
        tag: [
          { required: true, message: "客户标签不能为空", trigger: "blur" }
        ],
        carType: [
          { required: true, message: "意向车型不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "跟进状态不能为空", trigger: "change" }
        ],
        dataSource: [
          { required: true, message: "线索来源不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getCars();
    this.getUsers();
  },
  methods: {
    /** 查询客户信息列表 */
    getList() {
      this.loading = true;
      listClientUser(this.queryParams).then(response => {
        this.clientUserList = response.rows;
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
    getUsers() {
      getAllUser().then(response => {
        if(response.code === 200) {
          this.users = response.data;
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    moveCancel() {
      this.moveOpen = false;
      this.moveForm = {
        userId: null,
        clientId: null
      };
      this.resetForm("moveForm");
    },
    // 表单重置
    reset() {
      this.form = {
        clientId: null,
        clientNice: null,
        clientTell: null,
        clientEmail: null,
        clientAdrress: null,
        dataSource: null,
        tag: null,
        carType: null,
        status: null
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
      this.ids = selection.map(item => item.clientId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加客户信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const clientId = row.clientId || this.ids
      getClientUser(clientId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改客户信息1";
      });
    },
    moveSubmitForm() {
      this.$refs["moveForm"].validate(valid => {
        if (valid) {
          updateClientUser(this.moveForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.moveOpen = false;
              this.getList();
          });
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.clientId != null) {
            updateClientUser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addClientUser(this.form).then(response => {
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
      const clientIds = row.clientId || this.ids;
      this.$modal.confirm('是否确认删除客户信息编号为"' + clientIds + '"的数据项？').then(function() {
        return delClientUser(clientIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    handleMove(row) {
      this.moveOpen = true;
      this.moveForm.clientId = row.clientId;
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/clientUser/export', {
        ...this.queryParams
      }, `clientUser_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style>
.el-tabs__nav-wrap::after{
  background-color: #ffffff;
}
</style>