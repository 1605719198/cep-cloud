<template xmlns="http://www.w3.org/1999/html">
  <div class="app-container">
    <el-form :model="queryParams"
             :rules="rulesQuery" ref="queryForm" size="small" :inline="true"
             v-show="showSearch" label-width="78px">
      <el-form-item label="自动结转">
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
        <el-form-item label="结转类型" prop="carry">
          <el-select v-model="queryParams.carry" filterable placeholder="请输入结转类型">
            <el-option
              v-for="dict in dict.type.carryover_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
              @keyup.enter.native="handleQuery">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="会计周期" prop="acctPeriod">
          <el-date-picker
            v-model="queryParams.acctPeriod"
            size="mini"
            type="month"
            value-format="yyyy-MM"
            format="yyyy-MM"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-form>
      <el-form-item label="结转规则" prop="">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleExecute"
              v-hasPermi="['aa:record:add']"
            >执行
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-edit"
              size="mini"
              @click="handleCancel"
              v-hasPermi="['aa:record:edit']"
            >取消
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>

          <el-table-column label="结转规则代码" align="center" prop="rulesNo"/>
          <el-table-column label="结转规则名称" align="center" prop="rulesName"/>
          <el-table-column label="凭证号码" align="center" prop="executeVoucherNo">
            <template slot-scope="scope">
              <el-button size="mini" @click="voucherNoClick(scope.row)" type="text">{{ scope.row.executeVoucherNo }}
              </el-button>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" prop="status">
          </el-table-column>
          <el-table-column label="执行人" align="center" prop="createBy"/>
          <el-table-column label="执行时间" align="center" prop="createTime">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
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
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {
  listRecord,
  getRecord,
  delRecord,
  addRecord,
  updateRecord,
  listRecordCompanyId,
  addExecute,
  updateCancel
} from "@/api/finance/aa/record";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";

export default {
  name: "Record",
  dicts: ['carryover_type', 'execution_status'],
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
      // 自动结转规则-执行记录表格数据
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
        carry: null,
        rulesNo: null,
        acctPeriod: null,
        executeVoucherNo: null,
        cancelVoucherNo: null,
        status: null,
      },
      // 表单参数
      form: {
        recordList: []
      },
      // 表单校验
      rules: {},
      // 搜索框校验
      rulesQuery: {
        companyId: [
          {required: true, message: '请输入公司', trigger: 'blur'}
        ],
        carry: [
          {required: true, message: '请输入结转类型', trigger: 'blur'}
        ],
        acctPeriod: [
          {required: true, message: '请输入会计周期', trigger: 'blur'}
        ],
      },
      companyList: [],
      recordListSelect: []
    };
  },
  created() {
    this.queryParams.acctPeriod = new Date(new Date() - 30 * 24 * 3600 * 1000)

    this.getCompanyList()

  },
  methods: {
    voucherNoClick(row) {
      this.$router.push({
        name: 'Voucher', query: {
          voucherNo: row.executeVoucherNo,
          companyId: row.companyId
        }
      });
    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
        this.queryParams.companyId = this.companyList[0].value
        this.getList();
      });
    },
    /** 查询自动结转规则-执行记录列表 */
    getList() {
      this.loading = true;
      if (this.queryParams.carry === 'A' || this.queryParams.carry === null) {
        this.queryParams.carry = 'A'
        listRecord(this.queryParams).then(response => {
          this.recordList = response.rows;
          this.recordList = this.recordList.map(item => {
            switch (item.status) {
              case null :
                item.status = '未执行';
                break;
              case 'Y' :
                item.status = '已执行';
                break;
              case 'N' :
                item.status = '已取消';
                break;
            }
            return item;
          })
          this.total = response.total;
          this.loading = false;
        });
      } else if (this.queryParams.carry === 'B') {
        listRecordCompanyId(this.queryParams).then(response => {
          this.recordList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      }

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
        carry: null,
        rulesNo: null,
        acctPeriod: null,
        executeVoucherNo: null,
        cancelVoucherNo: null,
        status: null,
        createBy: null,
        createTime: null,
        createName: null
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.recordListSelect = selection
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },

    /** 执行按钮操作 */
    handleExecute() {
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.form.recordList = this.recordListSelect
          this.form.recordList = this.recordListSelect
          this.form.companyId = this.queryParams.companyId
          this.form.carry = this.queryParams.carry
          this.form.acctPeriod = this.queryParams.acctPeriod
          if (this.form.recordList.length === 0) {
            this.$modal.msgError("请选择数据");
            return;
          }
          if (this.form.recordList.length > 1) {
            this.$modal.msgError("只能选择一笔数据");
            return;
          }
          this.form.executeVoucherNo = this.recordListSelect[0].executeVoucherNo
          this.form.cancelVoucherNo = this.recordListSelect[0].cancelVoucherNo
          if (this.form.executeVoucherNo) {
            this.$modal.msgError("不能执行有凭证号的数据");
            return;
          }
          if (this.form.cancelVoucherNo) {
            this.form.cancelVoucherNo = ''
          }
          this.form.id = this.recordListSelect[0].id
          this.form.rulesNo = this.recordListSelect[0].rulesNo
          this.form.rulesName = this.recordListSelect[0].rulesName
          this.form.remark = this.recordListSelect[0].remark
          this.form.statusA = this.recordListSelect[0].statusA
              addExecute(this.form).then(response => {
                this.$modal.msgSuccess("执行成功");
                this.open = false;
                this.getList();
              });
        }
      });

    },
    /** 取消按钮操作 */
    handleCancel() {
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.form.recordList = this.recordListSelect
          this.form.companyId = this.queryParams.companyId
          this.form.carry = this.queryParams.carry
          this.form.acctPeriod = this.queryParams.acctPeriod
          if (this.form.recordList.length === 0) {
            this.$modal.msgError("请选择数据");
            return;
          }
          if (this.form.recordList.length > 1) {
            this.$modal.msgError("只能选择一笔数据");
            return;
          }
          this.form.id = this.recordListSelect[0].id
          this.form.rulesNo = this.recordListSelect[0].rulesNo
          this.form.rulesName = this.recordListSelect[0].rulesName
          this.form.remark = this.recordListSelect[0].remark
          this.form.executeVoucherNo = this.recordListSelect[0].executeVoucherNo
          if (!this.form.executeVoucherNo) {
            this.$modal.msgError("不能取消凭证号为空的数据");
            return;
          }
          updateCancel(this.form).then(response => {
            this.$modal.msgSuccess("取消成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRecord(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改自动结转规则-执行记录";
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
      this.$modal.confirm('是否确认删除自动结转规则-执行记录编号为"' + ids + '"的数据项？').then(function () {
        return delRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
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
