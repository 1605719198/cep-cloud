<template>
  <div class="app-container">

    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="核算项目类别" name="first">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="公司" prop="companyId">
            <el-input
              v-model="queryParams.companyId"
              placeholder="请输入公司"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="核算项目类别" prop="calTypeCode" label-width="100px">
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
              v-hasPermi="['finance:calType:add']"
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
              v-hasPermi="['finance:calType:edit']"
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
              v-hasPermi="['finance:calType:remove']"
            >删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="calTypeList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="核算项目类别代码" align="center" prop="calTypeCode" />
          <el-table-column label="核算项目类别名称" align="center" prop="calTypeName" />
          <el-table-column label="核算项目规则" align="center" prop="calRule">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.aa_calcode_rule" :value="scope.row.calRule"/>
            </template>
          </el-table-column>
          <el-table-column label="异动人" align="center" prop="updateName" />
          <el-table-column label="异动日期" align="center" prop="updateTime" :formatter="dateFormat"></el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['finance:calType:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['finance:calType:remove']"
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
      </el-tab-pane>
      <el-tab-pane label="核算项目资料" name="second">
        <cal-code ref="calCode" ></cal-code>
      </el-tab-pane>
      <el-tab-pane label="系统设定" name="third">
        <sys-rule ref="sysRule" ></sys-rule>
      </el-tab-pane>
    </el-tabs>

    <!-- 添加或修改核算项目-类别维护对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="490px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px" align="center">
        <el-form-item label="公司" prop="companyId">
          <el-input v-model="form.companyId" placeholder="请输入公司" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="核算项目类别代码" prop="calTypeCode">
          <el-input v-model="form.calTypeCode" placeholder="请输入核算项目类别代码" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="核算项目类别名称" prop="calTypeName">
          <el-input v-model="form.calTypeName" placeholder="请输入核算项目类别名称"style="width: 300px" />
        </el-form-item>
        <el-form-item label="核算项目规则" prop="calRule">
          <el-select v-model="form.calRule" placeholder="请选择" style="width: 300px">
            <el-option
              v-for="dict in dict.type.aa_calcode_rule"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
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
import { listCalType, getCalType, delCalType, addCalType, updateCalType } from "@/api/finance/aa/calType";
import calCode from "@/views/finance/aa/calcode";
import sysRule from "@/views/finance/aa/sysRule";
import {parseTime} from "@/utils/jlkj";

export default {
  name: "CalType",
  dicts: ['aa_calcode_rule'],
  components: {calCode,sysRule},
  data() {
    return {
      // tab名称（默认选中第一个标签页first）
      activeName: 'first',
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
      // 核算项目-类别维护表格数据
      calTypeList: [],
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
        calTypeName: [
          { required: true, message: "核算项目类别名称不能为空", trigger: "blur" },
          { max: 150,message: '长度不可超过150个字符', trigger: 'blur'}
        ],
        calRule: [
          { required: true, message: "核算项目规则不能为空", trigger: "blur" }
        ],

      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    /** tab 切换事件 */
    handleClick(tab, event) {
      this.activeName = tab.name
    },
    /** 查询核算项目-类别维护列表 */
    getList() {
      this.loading = true;
      listCalType(this.queryParams).then(response => {
        this.calTypeList = response.rows;
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
        calTypeCode: null,
        calTypeName: null,
        calRule: null,
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
      this.title = "添加核算项目-类别维护";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCalType(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改核算项目-类别维护";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCalType(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCalType(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除核算项目-类别维护编号为"' + ids + '"的数据项？').then(function() {
        return delCalType(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/calType/export', {
        ...this.queryParams
      }, `calType_${new Date().getTime()}.xlsx`)
    },
    dateFormat(row,column){
      var date = row[column.property];
      if(date === undefined){
        return "";
      }
      return parseTime(date,'{y}-{m}-{d}');
    }
  }
};
</script>
