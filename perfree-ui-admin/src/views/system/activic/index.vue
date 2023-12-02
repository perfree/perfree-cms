<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="活动名称" prop="activicName">
        <el-input
          v-model="queryParams.activicName"
          placeholder="请输入活动名称"
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
        >添加活动</el-button>
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

    <el-table v-loading="loading" :data="activicList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column fixed label="活动名称" align="center" prop="activicName"  width="200"/>
      <el-table-column label="活动有效期" align="center"  prop="startTime"  width="300">
        <template #default="{ row }">
          <span>{{ parseTime(row.startTime, '{y}-{m}-{d}') }}</span> -
          <span>{{ parseTime(row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动状态" align="center" prop="activicText"  width="100">
        <template #default="{ row }">
          <span v-if="row.status == 1">禁用</span>
          <span v-else="">启用</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="{ row }">
          <span>{{ parseTime(row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createUser"  width="100">
        <template #default="{ row }">
          <span v-if="row.user">{{ row.user.adminName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="关联渠道" align="center" prop="channel"   width="200"/>
      <el-table-column label="活动内容" align="center" prop="activicText" />
     
      <el-table-column fixed="right" label="操作" align="center" class-name="small-padding fixed-width"  width="280">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" >编辑</el-button>
          <el-button link type="primary" icon="lock" @click="handleUpdateStatus(scope.row)" v-if="scope.row.status == 0">禁用</el-button>
          <el-button link type="primary" icon="unlock" @click="handleUpdateStatus(scope.row)" v-if="scope.row.status == 1">启用</el-button>
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

    <!-- 添加或修改活动信息对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="活动名称" prop="activicName">
          <el-input v-model="form.activicName" placeholder="请输入活动名称" />
        </el-form-item>
        <el-form-item label="上传">
          <el-upload
            class="upload-box"
            drag
            :headers="uploadHeaders"
            :limit="1"
            :action="uploadFileUrl"
            :accept="'image/*,audio/mpeg'"
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
                支持jpg,png,mp3等格式
              </div>
            </template>
          </el-upload>
        </el-form-item>
        
        <el-form-item label="活动内容" prop="activicText">
          <el-input v-model="form.activicText" type="textarea" placeholder="请输入内容" :rows="4"/>
        </el-form-item>

        <el-form-item label="有效期">
          <el-date-picker
            v-model="form.dateRange"
            type="daterange"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
          />
        </el-form-item>

        <el-form-item label="状态">
          <el-select v-model="form.status" class="m-2" placeholder="请选择" style="width: 400px">
              <el-option :key="0" :label="'启用'" :value="0"/>
              <el-option :key="1" :label="'禁用'" :value="1"/>
            </el-select>
        </el-form-item>

        <el-form-item label="关联渠道">
          <el-select v-model="form.channels" class="m-2" placeholder="请选择" multiple style="width: 400px">
              <el-option key="小红书" :label="'小红书'" value="小红书"/>
              <el-option key="抖音" :label="'抖音'" value="抖音"/>
              <el-option key="微信广告" :label="'微信广告'" value="微信广告"/>
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
import { listActivic, getActivic, delActivic, addActivic, updateActivic } from "@/api/system/activic";
import { getToken } from "@/utils/auth";
export default {
  name: "Activic",
  data() {
    return {
      // 遮罩层
      uploadFileUrl: ref(import.meta.env.VITE_APP_BASE_API + "/common/upload"),
      loading: true,
      // 选中数组
      ids: [],
      uploadHeaders:{"Authorization": getToken()},
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 活动信息表格数据
      activicList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        activicName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        activicName: [
          { required: true, message: "活动名称不能为空", trigger: "blur" }
        ],
        activicText: [
          { required: true, message: "活动内容不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "活动创建日期不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询活动信息列表 */
    getList() {
      this.loading = true;
      listActivic(this.queryParams).then(response => {
        this.activicList = response.rows;
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
        activicId: null,
        activicName: null,
        activicText: null,
        dateRange: ref(''),
        status: 0,
        channels: null,
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
      this.ids = selection.map(item => item.activicId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加活动信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const activicId = row.activicId || this.ids
      getActivic(activicId).then(response => {
        this.form = response.data;
        this.form.dateRange = [this.form.startTime, this.form.endTime];
        if(this.form.channel) {
          this.form.channels = this.form.channel.split(',');
        }
        if(this.form.attach) {
          this.form.fileList = [{name: this.form.attach.split("/").pop() ,url: this.form.attach}];
        }
        this.open = true;
        this.title = "修改活动信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if(this.form.channels && this.form.channels.length >= 1){
            this.form.channel = this.form.channels.join(',');
          }
          if(this.form.dateRange && this.form.dateRange.length >= 1) {
            const [startTime, endTime] = this.form.dateRange;
            this.form.startTime = startTime;
            this.form.endTime = endTime;
          }
          if (this.form.activicId != null) {
            updateActivic(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addActivic(this.form).then(response => {
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
      const activicIds = row.activicId || this.ids;
      this.$modal.confirm('是否确认删除活动信息编号为"' + activicIds + '"的数据项？').then(function() {
        return delActivic(activicIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/activic/export', {
        ...this.queryParams
      }, `activic_${new Date().getTime()}.xlsx`)
    },
    handleSuccess(response, file, fileList) {
      if(response.code === 200) {
        this.form.attach = response.fileName;
      }
    },
    handleRemove(file, fileList) {
      this.form.attach = null;
    },
    handleUpdateStatus(row) {
      let activic = {status: 0, activicId: row.activicId};
      if(row.status == 0) {
        activic.status = 1;
      } else {
        activic.status = 0;
      }
      
      updateActivic(activic).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      });
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
