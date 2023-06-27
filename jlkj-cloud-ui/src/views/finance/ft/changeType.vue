<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="公司别" prop="companyId">
        <el-input
          v-model="queryParams.companyId"
          placeholder="请输入公司别"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="变动方式名称" prop="changeName">
        <el-input
          v-model="queryParams.changeName"
          placeholder="请输入变动方式名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="变动方式代码" prop="changeWay">
        <el-input
          v-model="queryParams.changeWay"
          placeholder="请输入变动方式代码"
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
          v-hasPermi="['finance:changeType:add']"
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
          v-hasPermi="['finance:changeType:edit']"
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
          v-hasPermi="['finance:changeType:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="changeTypeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="变动方式名称" align="center" prop="changeName" />
      <el-table-column label="变动方式代码" align="center" prop="changeWay" />
      <el-table-column label="单据性质" align="center" prop="billNature" />
      <el-table-column label="账务规则代码" align="center" prop="acctLogic" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:changeType:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:changeType:remove']"
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

    <!-- 添加或修改固定资产变动方式对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司别" prop="companyId">
              <el-select v-model="form.companyId" filterable placeholder="请选择公司">
                <el-option
                  v-for="item in companyList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="变动方式名称" prop="changeName">
              <el-input v-model="form.changeName" placeholder="请输入变动方式名称" class="input"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="变动方式代码" prop="changeWay">
              <el-input v-model="form.changeWay" placeholder="请输入变动方式代码" class="input"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单据性质" prop="billNature">
              <el-select v-model="form.billNature" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.ft_bill_nature"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="账务规则代码" prop="acctLogic">
              <el-input v-model="form.acctLogic" placeholder="请输入账务规则代码" class="input"/>
            </el-form-item>
          </el-col>
          <el-col :span="12"/>
        </el-row>

        <el-table v-loading="loading" :data="acctList">
          <el-table-column label="项次" align="center" prop="changeName" />
          <el-table-column label="借/贷" align="center" prop="dr_cr" />
          <el-table-column label="会计科目代码" align="center" prop="acctCode" />
          <el-table-column label="核算项目一" align="center" prop="calTypeCodea" />
          <el-table-column label="核算项目二" align="center" prop="calTypeCodeb" />
          <el-table-column label="金额栏位" align="center" prop="amt_type" />
        </el-table>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listChangeType, getChangeType, delChangeType, addChangeType, updateChangeType } from "@/api/finance/ft/changeType";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";

export default {
  name: "ChangeType",
  dicts: ['ft_bill_nature'],
  data() {
    return {
      // 会计公司下拉选单
      companyList:[],
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
      // 固定资产变动方式表格数据
      changeTypeList: [],
      acctList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        changeWay: null,
        changeName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companyId: [
          { required: true, message: "公司别不能为空", trigger: "blur" },
        ],
        changeName: [
          { required: true, message: "变动方式名称不能为空", trigger: "blur" },
          {max: 60 ,trigger: 'blur',message: "最大不超过60个字符"}
        ],
        changeWay: [
          { required: true, message: "变动方式代码不能为空", trigger: "blur" },
          {max: 10 ,trigger: 'blur',message: "最大不超过10个字符"}
        ],
      }
    };
  },
  created() {
    this.getCompanyList();
    this.getList();
  },
  methods: {
    /** 查询固定资产变动方式列表 */
    getList() {
      this.loading = true;
      listChangeType(this.queryParams).then(response => {
        this.changeTypeList = response.rows;
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
        uuid: null,
        companyId: null,
        changeWay: null,
        changeName: null,
        billNature: null,
        acctLogic: null,
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
      this.ids = selection.map(item => item.uuid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加变动方式";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const uuid = row.uuid || this.ids
      getChangeType(uuid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改变动方式";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uuid != null) {
            updateChangeType(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addChangeType(this.form).then(response => {
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
      const uuids = row.uuid || this.ids;
      this.$modal.confirm('是否确认删除固定资产变动方式编号为"' + uuids + '"的数据项？').then(function() {
        return delChangeType(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>
