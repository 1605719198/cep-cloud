<template>
  <div>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司" prop="companyId">
        <el-select v-model="queryParams.companyId" filterable placeholder="请选择公司">
          <el-option
            v-for="item in companyList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="核算项目类别" prop="calTypeCode" label-width="130px">
        <el-input
          v-model="queryParams.calTypeCode"
          placeholder="请输入核算项目类别"
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
          v-hasPermi="['finance:sysRule:add']"
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
          v-hasPermi="['finance:sysRule:edit']"
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
          v-hasPermi="['finance:sysRule:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['finance:sysRule:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sysRuleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="系统别" align="center" prop="sys"  width="60"/>
      <el-table-column label="公用class" align="center" prop="className" />
      <el-table-column label="语法字串" align="center" prop="sqlString"  width="600"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:sysRule:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:sysRule:remove']"
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

    <!-- 添加或修改核算项目-系统设定对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px" align="left">
        <el-form-item label="公司" prop="companyId" >
          <el-select v-model="form.companyId" disabled placeholder="请选择公司" style="width:300px">
            <el-option
              v-for="item in companyList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="核算项目类别代码" prop="calTypeCode">
          <el-input v-model="form.calTypeCode" placeholder="请输入核算项目类别代码" disabled style="width: 300px"/>
        </el-form-item>
        <el-form-item label="系统别" prop="sys">
          <el-input v-model="form.sys" placeholder="请输入系统别" clearable style="width: 300px"/>
        </el-form-item>
        <el-form-item label="公用class" prop="className">
          <el-input v-model="form.className" placeholder="请输入公用class" clearable style="width: 600px" maxlength="100" show-word-limit/>
        </el-form-item>
        <el-form-item label="语法字串" prop="sqlString">
          <el-input v-model="form.sqlString" placeholder="请输入语法字串" type="textarea" clearable style="width: 600px" maxlength="300" show-word-limit/>
        </el-form-item>
        <el-form-item label="数据库字串" prop="sqlStringDb">
          <el-input v-model="form.sqlStringDb" placeholder="请输入数据库字串" type="textarea" clearable style="width: 600px" maxlength="600" show-word-limit/>
        </el-form-item>
        <el-form-item label="新增人" prop="createName" align="left">
          {{form.createName}}
        </el-form-item>
        <el-form-item label="新增时间" prop="createTime" align="left">
          {{form.createTime}}
        </el-form-item>
        <el-form-item label="异动人" prop="updateName" align="left">
          {{form.updateName}}
        </el-form-item>
        <el-form-item label="异动时间" prop="updateTime" align="left">
          {{form.updateTime}}
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
import { listSysRule, getSysRule, delSysRule, addSysRule, updateSysRule } from "@/api/finance/aa/sysRule";
import { selectCompanyList } from "@/api/finance/aa/companyGroup";
export default {
  name: "SysRule",
  data() {
    return {
      // 父组件传值
      parCompanyId:null,
      parCalTypeId:null,
      parCalTypeCode: null,
      // 会计公司下拉选单
      companyList: [],
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
      // 核算项目-系统设定表格数据
      sysRuleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        calTypeCode: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companyId: [
          { required: true, message: "公司不能为空", trigger: "blur" }
        ],
        calTypeCode: [
          { required: true, message: "核算项目类别代码不能为空", trigger: "blur" },
          { max: 36,message: '长度不可超过36个字符', trigger: 'blur'}
        ],
        sys: [
          { required: true, message: "系统别不能为空", trigger: "blur" },
          { max: 36,message: '长度不可超过36个字符', trigger: 'blur'}
        ],
        className: [
          { required: true, message: "公用程式不能为空", trigger: "blur" }
        ],
        sqlString: [
          { required: true, message: "语法字串不能为空", trigger: "blur" }
        ],
        sqlStringDb: [
          { required: true, message: "数据库字串不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this. getCompanyList();
    this.getList();
  },
  methods: {
    /** 初始化方法 */
    init(obj) {
      // 根据传递过来的值进行查询
      this.queryParams.companyId = obj.companyId
      this.queryParams.calTypeCode = obj.calTypeCode
      this.parCompanyId = obj.companyId
      this.parCalTypeId = obj.calTypeId
      this.parCalTypeCode = obj.calTypeCode
      this.getList()
    },
    /** 查询核算项目-系统设定列表 */
    getList() {
      this.loading = true;
      listSysRule(this.queryParams).then(response => {
        this.sysRuleList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
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
        calTypeId: null,
        calTypeCode: null,
        sys: null,
        className: null,
        sqlString: null,
        sqlStringDb: null,
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
      this.title = "添加核算项目-系统设定";
      this.form.companyId = this.parCompanyId
      this.form.calTypeId = this.parCalTypeId
      this.form.calTypeCode = this.parCalTypeCode;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSysRule(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改核算项目-系统设定";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSysRule(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSysRule(this.form).then(response => {
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
      this.$modal.confirm('确认要删除么？').then(function() {
        return delSysRule(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/sysRule/export', {
        ...this.queryParams
      }, `sysRule_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
