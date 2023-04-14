<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm"
             :rules="rulesQuery"  size="small" :inline="true" v-show="showSearch" label-width="97px">
      <el-form-item label="公司" prop="companyId">
        <el-input
          v-model="queryParams.companyId"
          placeholder="请输入公司"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参考数字代码" prop="parameterKey">
        <el-input
          v-model="queryParams.parameterKey"
          placeholder="请输入参考数字代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参数名称" prop="parameterDesc">
        <el-input
          v-model="queryParams.parameterDesc"
          placeholder="请输入参数名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['aa:para:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['aa:para:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['aa:para:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="paraList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="参考数字代码" align="center" prop="parameterKey" />
      <el-table-column label="参数说明" align="center" prop="parameterDesc" />
      <el-table-column label="参数值" align="center" prop="parameterValue" />
      <el-table-column label="新增人" align="center" prop="createBy" />
      <el-table-column label="新增日期" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['aa:para:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['aa:para:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改会计系统参数维护对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px"
               class="customDialogStyle"
               custom-class="saveAsDialog"
               append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="106px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司" prop="companyId">
              <el-input v-model="form.companyId" placeholder="请输入公司" style="width: 194px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="参考数字代码" prop="parameterKey">
              <el-input v-model="form.parameterKey" placeholder="请输入参考数字代码"  style="width:194px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="参数说明" prop="parameterDesc">
              <el-input v-model="form.parameterDesc" placeholder="请输入参数说明" style="width: 194px" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="参数值" prop="parameterValue">
              <el-input v-model="form.parameterValue" placeholder="请输入参数值" style="width: 194px" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPara, getPara, delPara, addPara, updatePara } from "@/api/finance/aa/para";

export default {
  name: "Para",
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
      // 会计系统参数维护表格数据
      paraList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        parameterKey: null,
        parameterDesc: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companyId: [
          {required: true, message: '请输入公司', trigger: 'blur'}
        ],
        parameterKey: [
          {required: true, message: '请输入参考数字代码', trigger: 'blur'},
          {max: 32, message: '最大长度32个字符', trigger: 'blur'}
        ],
        parameterDesc: [
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的参数说明", trigger: "blur"},
          {required: true, message: '请输入参数说明', trigger: 'blur'},
          {max: 150, message: '最大长度150个字符', trigger: 'blur'}
        ],
        parameterValue: [
          {required: true, message: '请输入参数值', trigger: 'blur'},
          {max: 20, message: '最大长度20个字符', trigger: 'blur'}
        ],
      },
      // 表单校验
      rulesQuery:{
        companyId: [
          {required: true, message: '请输入公司', trigger: 'blur'}
        ]
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询会计系统参数维护列表 */
    getList() {
      this.loading = true;
      listPara(this.queryParams).then(response => {
        this.paraList = response.rows;
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
        id: null,
        companyId: null,
        parameterKey: null,
        parameterDesc: null,
        parameterValue: null,
        status: null,
        updateBy: null,
        createBy: null,
        createTime: null,
        updateTime: null,
        updateName: null,
        createName: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加会计系统参数维护";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPara(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改会计系统参数维护";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePara(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPara(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除会计系统参数维护编号为"' + ids + '"的数据项？').then(function() {
        return delPara(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('aa/para/export', {
        ...this.queryParams
      }, `para_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style scoped>
.link-type {
  color: #337ab7;
  cursor: pointer;
}

/*.avue-crud .el-date-editor.el-input {*/
/*  width: auto !important;*/
/*}*/
.plan_header {
  background-color: #f9f9f9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #e9e9e9 solid;
}

.plan_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold;
}

.plan_main {
  height: 86vh;
  background-color: #fff;
}
</style>
<style>
.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}

/* 弹窗圆角 */
.customDialogStyle .el-dialog {
  border-radius: 8px;
}

/* 标题样式 */
.customDialogStyle .el-dialog__header {
  background-color: rgb(245, 245, 245);
  border-radius: 8px 8px 0 0;
  padding: 10px 20px 10px;
}

/* 标题文字样式 */
.customDialogStyle .el-dialog__title {
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  letter-spacing: normal;
  font-size: 14px;
  font-weight: 700;
  font-style: normal;
  text-align: left;
  line-height: 20px;
  border-width: 0;
  color: rgb(102, 102, 102);
}

/* 关闭符号位置调整 */
.customDialogStyle .el-dialog__headerbtn {
  top: 12px;
}

.customDialogStyle .el-dialog__body {
  padding: 15px 40px;
}
</style>
