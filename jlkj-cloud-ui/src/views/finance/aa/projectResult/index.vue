<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司代码" prop="companyId">

        <el-select v-model="queryParams.companyId" style="margin-bottom: 20px;    width: 228px;" filterable placeholder="请输入会计公司">
          <el-option
            v-for="item in companyList"
            :key="item.value"
            :label="item.label"
            :value="item.value"

            @keyup.enter.native="handleQuery">
          </el-option>
        </el-select>

      </el-form-item>


      <el-form-item label="会计周期" prop="acctPeriod">
        <el-date-picker clearable
          v-model="queryParams.acctPeriod"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择会计周期">
        </el-date-picker>
      </el-form-item>


        <el-form-item label="是否包含未抛" prop="isPost">
        <template v-slot="scope">
          <dict-tag :options="dict.type.aa_yes_no" :value="queryParams.isPost"/>
        </template>
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
          v-hasPermi="['finance:projectResult:add']"
        >计算</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['finance:projectResult:edit']"
        >预览</el-button>
      </el-col>


      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['finance:projectResult:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="projectResultList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />

      <el-table-column label="公司代码" align="center" prop="companyId" />
      <el-table-column label="报表代号" align="center" prop="reportNo" />
      <el-table-column label="会计周期" align="center" prop="acctPeriod" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.acctPeriod, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="金额实际数" align="center" prop="amt" />
      <el-table-column label="数量实际数" align="center" prop="qty" />
      <el-table-column label="新增人名称" align="center" prop="createName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:projectResult:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:projectResult:remove']"
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

    <!-- 添加或修改印项目计算结果对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="公司代码" prop="companyId">
          <el-input v-model="form.companyId" placeholder="请输入公司代码" />
        </el-form-item>
        <el-form-item label="报表id" prop="reportId">
          <el-input v-model="form.reportId" placeholder="请输入报表id" />
        </el-form-item>
        <el-form-item label="报表代号" prop="reportNo">
          <el-input v-model="form.reportNo" placeholder="请输入报表代号" />
        </el-form-item>
        <el-form-item label="印表项目" prop="itemCode">
          <el-input v-model="form.itemCode" placeholder="请输入印表项目" />
        </el-form-item>
        <el-form-item label="会计周期" prop="acctPeriod">
          <el-date-picker clearable
            v-model="form.acctPeriod"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择会计周期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="金额实际数" prop="amt">
          <el-input v-model="form.amt" placeholder="请输入金额实际数" />
        </el-form-item>
        <el-form-item label="数量实际数" prop="qty">
          <el-input v-model="form.qty" placeholder="请输入数量实际数" />
        </el-form-item>
        <el-form-item label="新增人名称" prop="createName">
          <el-input v-model="form.createName" placeholder="请输入新增人名称" />
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
import { listProjectResult, getProjectResult, delProjectResult, addProjectResult, updateProjectResult } from "@/api/finance/aa/projectResult";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
export default {
  dicts: ['aa_yes_no'],
  name: "ProjectResult",
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
      // 印项目计算结果表格数据
      projectResultList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        reportId: null,
        reportNo: null,
        itemCode: null,
        acctPeriod: null,
        isPost: null,

        createName: null,
      },
      companyList: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getCompanyList()
  },
  methods: {
    /** 查询印项目计算结果列表 */
    getList() {
      this.loading = true;
      listProjectResult(this.queryParams).then(response => {
        this.projectResultList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
        this.queryParams.companyId = this.companyList[0].value
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
        id: null,
        companyId: null,
        reportId: null,
        reportNo: null,
        itemCode: null,
        acctPeriod: null,
        amt: null,
        qty: null,
        createBy: null,
        createName: null,
        createTime: null
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
      this.title = "添加印项目计算结果";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProjectResult(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改印项目计算结果";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProjectResult(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProjectResult(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除印项目计算结果编号为"' + ids + '"的数据项？').then(function() {
        return delProjectResult(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/projectResult/export', {
        ...this.queryParams
      }, `projectResult_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
