<template>
  <div class="app-container">
    <el-form
      :rules="rulesQuery" :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="84px">
      <el-form-item label="会计公司" prop="companyId">
        <el-select v-model="queryParams.companyId" filterable placeholder="请输入会计公司">
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
          type="month"
          value-format="yyyy-MM"
          placeholder="请选择会计周期">
        </el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button type="primary"  icon="el-icon-plus" size="mini" @click="handleClosingExecute">执行</el-button>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="会计周期" align="center" prop="acctPeriod" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.acctPeriod, '{y}-{m}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后执行时间" align="center" prop="createTime" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime,  '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="执行人员" align="center" prop="createBy" />
      <el-table-column label="执行状态" align="center" prop="status" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.closing_status"
                    :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="执行信息" align="center" prop="desc"  :show-overflow-tooltip='true' />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['aa:record:remove']"
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

  </div>
</template>
<script>
import { listRecord, getRecord, delRecord, addRecord, updateRecord,addClosingExecute } from "@/api/finance/aa/recordClosing";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
export default {
  name: "Record",
  dicts: ['closing_status'],
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
      // 关账执行记录表格数据
      recordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        acctPeriod: null,
        createName: null,
      },
      // 搜索框校验
      rulesQuery: {
        companyId: [
          {required: true, message: '请输入公司', trigger: 'blur'}
        ],

        acctPeriod: [
          {required: true, message: '请输入会计周期', trigger: 'blur'}
        ],
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      companyList:[],
    };
  },
  created() {
    this.getCompanyList()
    this.queryParams.acctPeriod = new Date(new Date() - 30 * 24 * 3600 * 1000)

  },
  methods: {
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
        this.queryParams.companyId=this.companyList[0].value
        this.getList();
      });
    },
    /** 查询关账执行记录列表 */
    getList() {
      this.loading = true;
      listRecord(this.queryParams).then(response => {
        this.recordList = response.rows;
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
        acctPeriod: null,
        status: null,
        desc: null,
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
    /** 执行按钮操作 */
    handleClosingExecute(){
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.form.companyId = this.queryParams.companyId
          this.form.acctPeriod = this.queryParams.acctPeriod
          addClosingExecute(this.form).then(response => {
            this.$modal.msgSuccess("执行成功");
            this.open = false;
            this.getList();
          });
        }
      });

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
      this.title = "添加关账执行记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRecord(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改关账执行记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRecord(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRecord(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除关账执行记录编号为"' + ids + '"的数据项？').then(function() {
        return delRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('aa/record/export', {
        ...this.queryParams
      }, `record_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
