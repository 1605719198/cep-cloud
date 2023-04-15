<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="总行" prop="bankNo">
        <el-select v-model="queryParams.bankNo" placeholder="请选择">
          <el-option
            v-for="dict in dict.type.aa_bank_bankno"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="分行" prop="branchNo">
        <el-select v-model="queryParams.branchNo" placeholder="请选择">
          <el-option
            v-for="dict in dict.type.aa_branch_branchno"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value">
          </el-option>
        </el-select>
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
          v-hasPermi="['finance:branch:add']"
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
          v-hasPermi="['finance:branch:edit']"
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
          v-hasPermi="['finance:branch:remove']"
        >删除</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['finance:branch:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="branchList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="分行代码" align="center" prop="branchNo" />
      <el-table-column label="分行中文全名" align="center" prop="branchchnname" />
      <el-table-column label="分行中文简称" align="center" prop="branchchnabbr" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:branch:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:branch:remove']"
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

    <!-- 添加或修改金融机构管理分行对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="总行代码" prop="bankNo">
              <el-input v-model="form.bankNo" placeholder="请输入总行代码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="总行简称" prop="bankChnabbr">
              <el-input v-model="form.bankChnabbr" placeholder="请输入总行简称" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="分行代码" prop="branchNo">
              <el-input v-model="form.branchNo" placeholder="请输入分行代码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分行中文简称" prop="branchchnabbr">
              <el-input v-model="form.branchchnabbr" placeholder="请输入分行中文简称" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="分行中文全名" prop="branchchnname">
          <el-input v-model="form.branchchnname" placeholder="请输入分行中文全名" />
        </el-form-item>
        <el-form-item label="分行英文简称" prop="branchengabbr">
          <el-input v-model="form.branchengabbr" placeholder="请输入分行英文简称" />
        </el-form-item>
        <el-form-item label="分行英文全名" prop="branchengname">
          <el-input v-model="form.branchengname" placeholder="请输入分行英文全名" />
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
import { listBranch, getBranch, delBranch, addBranch, updateBranch } from "@/api/finance/aa/branch";

export default {
  name: "Branch",
  dicts: ['aa_bank_bankno','aa_branch_branchno'],
  data() {
    return {
      // 遮罩层
      loading: false,
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
      // 金融机构管理分行表格数据
      branchList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bankNo: null,
        branchNo: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bankNo: [
          { required: true, message: "总行代码不能为空", trigger: "blur" }
        ],
        bankChnabbr: [
          { required: true, message: "总行简称不能为空", trigger: "blur" }
        ],
        branchNo: [
          { required: true, message: "分行代码不能为空", trigger: "blur" },
          {max: 5 ,trigger: 'blur',message: "最大不超过5个字符"}
        ],
        branchchnabbr: [
          {max: 60 ,trigger: 'blur',message: "最大不超过60个字符"}
        ],
        branchchnname: [
          {max: 100 ,trigger: 'blur',message: "最大不超过100个字符"}
        ],
        branchengabbr: [
          { required: true, message: "分行英文简称不能为空", trigger: "blur" },
          {max: 60 ,trigger: 'blur',message: "最大不超过60个字符"}
        ],
        branchengname: [
          { required: true, message: "分行英文全名不能为空", trigger: "blur" },
          {max: 100 ,trigger: 'blur',message: "最大不超过100个字符"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询金融机构管理分行列表 */
    getList() {
      this.loading = true;
      listBranch(this.queryParams).then(response => {
        this.branchList = response.rows;
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
        mainId: null,
        bankNo: null,
        branchNo: null,
        branchchnname: null,
        branchengname: null,
        branchchnabbr: null,
        branchengabbr: null,
        status: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateName: null,
        updateTime: null
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
      this.title = "添加金融机构管理分行";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBranch(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改金融机构管理分行";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBranch(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBranch(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除金融机构管理分行编号为"' + ids + '"的数据项？').then(function() {
        return delBranch(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    // /** 导出按钮操作 */
    // handleExport() {
    //   this.download('finance/branch/export', {
    //     ...this.queryParams
    //   }, `branch_${new Date().getTime()}.xlsx`)
    // }
  }
};
</script>
