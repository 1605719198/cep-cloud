<template>
  <div class="app-container">
    <el-form :model="queryParams"
             :rules="rulesQuery"ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="会计公司" prop="companyId">
        <el-select v-model="queryParams.companyId" filterable placeholder="请输入会计公司">
          <el-option
            v-for="item in companyList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年月" prop="yarMonth">
        <el-date-picker clearable
                        v-model="queryParams.yarMonth"
                        type="month"
                        value-format="yyyy-MM"
                        placeholder="请选择年月"
        @change="dutyDateChange">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="部门编码" prop="deptNo">
        <el-input
          v-model="queryParams.deptNo"
          placeholder="请输入部门编码"
          clearable
          @keyup.enter.native="handleQuery"
        >
          <el-button slot="append" icon="el-icon-search" @click="deptNoButton"
          ></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="预算类别" prop="budgetType">
        <el-select v-model="queryParams.budgetType" placeholder="请选择预算类别" clearable>
          <el-option
            v-for="dict in dict.type.ao_budget_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
          v-hasPermi="['ao:budget:add']"
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
          v-hasPermi="['ao:budget:edit']"
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
          v-hasPermi="['ao:budget:remove']"
        >删除</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['ao:budget:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ao:budget:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="budgetList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="公司" align="center" prop="companyId" >
        <template slot-scope="scope">
          <budget-tag :options="companyList" :value="scope.row.companyId" />
        </template>
      </el-table-column>
      <el-table-column label="年度" align="center" prop="year" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.year, '{y}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="部门" align="center" prop="deptNo" >

      </el-table-column>
      <el-table-column label="预算类别" align="center" prop="budgetType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ao_budget_type" :value="scope.row.budgetType"/>
        </template>
      </el-table-column>
      <el-table-column label="预算总值" align="center" prop="janBudgetYear" />
      <el-table-column :label="queryParams.MonthQueryParams+'月预算'" align="center" prop="janBudget" />
      <el-table-column label="上月预算值" align="center" prop="janBudgetLastMonth" />
      <el-table-column label="下月预算值" align="center" prop="janBudgetNextMonth" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ao:budget:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ao:budget:remove']"
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

    <!-- 添加或修改预算设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="年度" prop="year">
          <el-date-picker clearable
            v-model="form.year"
            type="year"
            value-format="yyyy"
            placeholder="请选择年度">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="部门编码" prop="deptNo">
          <el-input v-model="form.deptNo" placeholder="请输入部门编码" >
            <el-button slot="append" icon="el-icon-search" @click="deptNoButton"
            ></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="预算类别" prop="budgetType">
          <el-select v-model="form.budgetType" placeholder="请选择预算类别">
            <el-option
              v-for="dict in dict.type.ao_budget_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年月" prop="yarMonth">
          <el-date-picker clearable
            v-model="form.yarMonth"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="预算值" prop="janBudget">
          <el-input v-model="form.janBudget" placeholder="请输入预算值" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" class="customDialogStyle"  append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
    <selectOrgPerson ref="selectOrgPerson" @ok="getOrgPerson"/>
  </div>
</template>

<script>
import { listBudget, getBudget, delBudget, addBudget, updateBudget } from "@/api/finance/ao/budget";
import selectOrgPerson from "@/views/components/human/selectUser/selectOrgPerson";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {getToken} from "@/utils/auth";
import budgetTag from "./budgetTag";
export default {
  name: "Budget",
  components: {selectOrgPerson,budgetTag},
  dicts: ['ao_budget_type'],
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
      // 预算设置表格数据
      budgetList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        deptNo: null,
        budgetType: null,
        yarMonth: null,
        MonthQueryParams:'当'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 会计公司下拉选单
      companyList: [],
      // 导入参数
      upload: {
        // 是否显示弹出层（探亲假资料导入）
        open: false,
        // 弹出层标题（探亲假资料导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/finance/budget/importData"
      },
      // 搜索框校验
      rulesQuery: {
        yarMonth: [
          {required: true, message: '请输入年月', trigger: 'blur'}
        ],

      },
    };
  },
  created() {
    this.getCompanyList();
    this.getList();

  },
  methods: {
    //查询日期切换事件
    dutyDateChange(val) {

      this.queryParams.MonthQueryParams=val.substring(5,7)
    },
    /** 公司别下拉选单 */
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
      });
    },
    /** 部门编码点击 */
    deptNoButton(){
      this.$refs.selectOrgPerson.show();
    },
    getOrgPerson(node,name,id,val){
      console.log(node,name,id,val);
    },
    /** 查询预算设置列表 */
    getList() {
      this.loading = true;
      listBudget(this.queryParams).then(response => {
        this.budgetList = response.rows;
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
        year: null,
        deptNo: null,
        budgetType: null,
        yarMonth: null,
        janBudget: null,
        status: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        updateName: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.getList();
        }
      });
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.resetForm("queryForm");
          this.handleQuery();
        }
      });
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
      this.title = "添加预算设置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBudget(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改预算设置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBudget(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBudget(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除预算设置编号为"' + ids + '"的数据项？').then(function() {
        return delBudget(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/budget/export', {
        ...this.queryParams
      }, `budget_${new Date().getTime()}.xlsx`)
    },

    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "预算数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('finance/budget/importTemplate', {

      }, `budget_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      console.log(response+"上传成功");
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
            this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
              response.msg + "</div>", "导入结果",
              { dangerouslyUseHTMLString: true });

    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }

  }
};
</script>
