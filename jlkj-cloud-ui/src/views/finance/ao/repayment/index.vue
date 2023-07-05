<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="companyId">
        <el-input
          v-model="queryParams.companyId"
          placeholder="请输入公司别"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请单号" prop="billNo">
        <el-input
          v-model="queryParams.billNo"
          placeholder="请输入申请单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="借款人" prop="loanBy">
        <el-input
          v-model="queryParams.loanBy"
          placeholder="请输入借款人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="还款日期" prop="payDate">
        <el-date-picker clearable
          v-model="queryParams.payDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择还款日期">
        </el-date-picker>
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
          @click="openSelectPop"
          v-hasPermi="['finance:repayment:add']"
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
          v-hasPermi="['finance:repayment:edit']"
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
          v-hasPermi="['finance:repayment:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['finance:repayment:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="repaymentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="申请单号" align="center" prop="billNo" />
      <el-table-column label="借支单id" align="center" prop="loanId" />
      <el-table-column label="借款人姓名" align="center" prop="loanName" />
      <el-table-column label="本次还款金额" align="center" prop="amt" />
      <el-table-column label="状态" align="center" prop="status" >
        <template v-slot="scope"> {{ statusName(scope.row.status) }} </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:repayment:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:repayment:remove']"
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

    <!-- 添加或修改还款单资料档对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="公司别" prop="companyId">
          <el-input v-model="form.companyId" placeholder="请输入公司别" />
        </el-form-item>
        <el-form-item label="申请单号" prop="billNo">
          <el-input v-model="form.billNo" placeholder="请输入申请单号" />
        </el-form-item>
        <el-form-item label="借支单id" prop="loanId">
          <el-input v-model="form.loanId" placeholder="请输入借支单id" />
        </el-form-item>
        <el-form-item label="借款人" prop="loanBy">
          <el-input v-model="form.loanBy" placeholder="请输入借款人" />
        </el-form-item>
        <el-form-item label="借款人姓名" prop="loanName">
          <el-input v-model="form.loanName" placeholder="请输入借款人姓名" />
        </el-form-item>
        <el-form-item label="本次还款金额" prop="amt">
          <el-input v-model="form.amt" placeholder="请输入本次还款金额" />
        </el-form-item>
        <el-form-item label="还款日期" prop="payDate">
          <el-date-picker clearable
            v-model="form.payDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择还款日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="传票号码" prop="voucherNo">
          <el-input v-model="form.voucherNo" placeholder="请输入传票号码" />
        </el-form-item>
        <el-form-item label="新增人名称" prop="createName">
          <el-input v-model="form.createName" placeholder="请输入新增人名称" />
        </el-form-item>
        <el-form-item label="异动名称" prop="updateName">
          <el-input v-model="form.updateName" placeholder="请输入异动名称" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <select-loan-apply ref="selectLoanApply" :companyId="this.queryParams.companyId" @ok="handleQuery" ></select-loan-apply>
  </div>
</template>

<script>
import { listRepayment, getRepayment, delRepayment, addRepayment, updateRepayment } from "@/api/finance/ao/repayment";
import selectLoanApply from "@/views/finance/ao/repayment/selectLoanApply";
export default {
  name: "Repayment",
  components: { selectLoanApply },
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
      // 还款单资料档表格数据
      repaymentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: 'J00',
        billNo: null,
        loanBy: null,
        payDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    /** 打开集团报支类别弹窗 */
    openSelectPop() {
      this.$refs.selectLoanApply.show();
    },

    /** 查询还款单资料档列表 */
    getList() {
      this.loading = true;
      listRepayment(this.queryParams).then(response => {
        this.repaymentList = response.rows;
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
        billNo: null,
        loanId: null,
        payType: null,
        loanBy: null,
        loanName: null,
        amt: null,
        payDate: null,
        voucherNo: null,
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
      this.title = "添加还款单资料档";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRepayment(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改还款单资料档";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRepayment(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRepayment(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除还款单资料档编号为"' + ids + '"的数据项？').then(function() {
        return delRepayment(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/repayment/export', {
        ...this.queryParams
      }, `repayment_${new Date().getTime()}.xlsx`)
    }
  },
  computed: {
    statusName() {
      return val => {
        switch (val) {
          case '00': return '新增'
          case '10': return '会计已确认'
          case '20': return '出纳已确认'
        }
      }
    }
  },
};
</script>
