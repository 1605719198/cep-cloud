<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="companyId">
        <el-select v-model="queryParams.companyId" filterable placeholder="请选择公司">
          <el-option
            v-for="item in companyList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="申请单号" prop="billNo">
        <el-input
          v-model="queryParams.billNo"
          placeholder="请输入申请单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请人" prop="applyBy">
        <el-input v-model="queryParams.applyBy"  placeholder="请输入申请人" clearable @keyup.enter.native="handleQuery">
          <el-button slot="append" icon="el-icon-search" @click="inputClick()"></el-button>
        </el-input>
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
          v-hasPermi="['finance:loanApply:add']"
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
          v-hasPermi="['finance:loanApply:edit']"
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
          v-hasPermi="['finance:loanApply:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="loanApplyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="申请单号" align="center" prop="billNo" />
      <el-table-column label="支出类别" align="center" prop="itemNo" />
      <el-table-column label="申请人" align="center" prop="applyBy" />
      <el-table-column label="经办人" align="center" prop="createName" />
      <el-table-column label="经办人部门" align="center" prop="createDeptName" />
      <el-table-column label="借支总额" align="center" prop="totalAmt" />
      <el-table-column label="状态" align="center" prop="status" show-overflow-tooltip>
        <template v-slot="scope">
          <dict-tag :options="dict.type.ao_loan_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:loanApply:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:loanApply:remove']"
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

    <!-- 添加或修改借支申请主档对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="850px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" >
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司别" prop="companyId" label-width="130px">
              <el-select v-model="form.companyId" disabled style="width:275px">
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
            <el-form-item label="申请单号" prop="billNo" label-width="130px">
              {{form.billNo}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="支出类别" prop="itemNo" label-width="130px">
              <el-select v-model="form.itemNo" filterable placeholder="请选择支出类别"  style="width:275px">
                <el-option
                  v-for="item in itemNoList"
                  :key="item.value"
                  :label="item.value+'_'+item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请人" prop="applyName" label-width="130px">
              <el-input v-model="form.applyName" placeholder="请输入申请人姓名" >
                 <el-button slot="append" icon="el-icon-search" @click="inputClick('apply')"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="签核会计" prop="accountName" label-width="130px">
              <el-input v-model="form.accountName" placeholder="请输入签核会计姓名" >
                <el-button slot="append" icon="el-icon-search" @click="inputClick('account')"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="案由摘要" prop="billDesc" label-width="130px">
              <el-input v-model="form.billDesc" placeholder="请输入摘要" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="经办人" prop="createName" label-width="130px">
              {{form.createName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经办人部门" prop="createDeptName" label-width="130px">
              {{form.createDeptName}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="申请日期" prop="accountDate" label-width="130px">
              {{form.createTime}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="到期日" prop="dueDate" label-width="130px">
              {{form.dueDate}}
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="center" style="margin-top: 10px">借支细项信息维护</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddFinanceAoLoanDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteFinanceAoLoanDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="form.financeAoLoanDetailList" :row-class-name="rowFinanceAoLoanDetailIndex" @selection-change="handleFinanceAoLoanDetailSelectionChange" ref="financeAoLoanDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="原始凭证类型" prop="invoiceType" width="150" :render-header="addRedstar">
            <template slot-scope="scope">
              <el-form-item :prop="'financeAoLoanDetailList.' + scope.$index + '.invoiceType'" :rules="rules.invoiceType">
                <el-select v-model="scope.row.invoiceType" placeholder="请选择">
                  <el-option
                    v-for="dict in dict.type.ao_voucher_type"
                    :key="dict.value"
                    :label="dict.value+'_'+dict.label"
                    :value="dict.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>

          <el-table-column label="币别" prop="crcyCode" width="150" :render-header="addRedstar">
            <template slot-scope="scope">
              <el-form-item :prop="'financeAoLoanDetailList.' + scope.$index + '.crcyCode'" :rules="rules.crcyCode">
               <el-input v-model="scope.row.crcyCode" placeholder="请输入币别" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="金额" prop="netAmt" width="150" :render-header="addRedstar">
            <template slot-scope="scope">
              <el-form-item :prop="'financeAoLoanDetailList.' + scope.$index + '.netAmt'" :rules="rules.netAmt">
                <el-input v-model="scope.row.netAmt" type="number" placeholder="请输入税前金额" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="记账币金额" prop="totalAmt" width="150">
            <template slot-scope="scope">
              <el-form-item>
                {{scope.row.totalAmt}}
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="摘要说明" prop="srlDesc" width="150" :render-header="addRedstar">
            <template slot-scope="scope">
            <el-form-item :prop="'financeAoLoanDetailList.' + scope.$index + '.srlDesc'" :rules="rules.srlDesc">
              <el-input v-model="scope.row.srlDesc" placeholder="请输入摘要说明" />
            </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <select-user ref="select" @ok="getJobNumber"/>
  </div>
</template>

<script>
import { listLoanApply, getLoanApply, delLoanApply, addLoanApply, updateLoanApply } from "@/api/finance/ao/loanApply";
import { getItemNoList } from "@/api/finance/ao/reimbItemComp";
import { selectCompanyList } from "@/api/finance/aa/companyGroup";
import selectUser from "@/views/components/human/selectUser/selectUser";
import Treeselect from "@riophae/vue-treeselect";
import log from "../../../monitor/job/log";
export default {
  name: "LoanApply",
  dicts: ['ao_loan_status','ao_voucher_type'],
  components: {selectUser},
  data() {
    return {
      // 会计公司下拉选单
      companyList: [],
      // 支付类别下拉选单
      itemNoList: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedFinanceAoLoanDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 借支申请主档表格数据
      loanApplyList: [],
      // 报支管理-借支申请明细档表格数据
      financeAoLoanDetailList: [],
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
        applyBy: null,
        createBy: null,
      },
      // 表单参数
      form: { financeAoLoanDetailList: [],},
      // 表单校验
      rules: {
        itemNo: [
          { required: true, message: "支出类别不能为空", trigger: "blur" }
        ],
        applyName: [
          { required: true, message: "申请人不能为空", trigger: "blur" }
        ],
        accountName: [
          { required: true, message: "签核会计不能为空", trigger: "blur" }
        ],
        billDesc: [
          { required: true, message: "案由摘不能为空", trigger: "blur" }
        ],
        invoiceType: [
          { required: true, message: "凭证类型不能为空", trigger: "blur" }
        ],
        crcyCode: [
          { required: true, message: "币别不能为空", trigger: "blur" }
        ],
        netAmt: [
          { required: true, message: "金额不能为空", trigger: "blur" }

        ],
        srlDesc: [
          { required: true, message: "摘要不能为空", trigger: "blur" }
        ],

      }
    };
  },
  created() {
    this.getCompanyList()
    this.getList();
  },
  methods: {
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
      });
    },
    getItemNoList() {
      getItemNoList(this.queryParams.companyId).then(response => {
        this.itemNoList = response;
      });
    },
    /** 人员选单事件 */
    inputClick(src) {
      this.src = src;
      var queryParams={
        compID:this.queryParams.companyId,
        keywords: '',
        pageNum: 1,
        pageSize: 10,
      }
      this.$refs.select.show(queryParams);
    },
    /** 获取工号 */
    getJobNumber(val, userName,compId) {
      if(this.open===true){
        if(this.src=='account'){
          this.form.accountBy = val;
          this.form.accountName = userName;
        }else{
          this.form.applyBy = val;
          this.form.applyName = userName;
        }
      }else{
        this.queryParams.applyBy = val;
      }
    },
    /** 查询借支申请主档列表 */
    getList() {
      this.loading = true;
      listLoanApply(this.queryParams).then(response => {
        this.loanApplyList = response.rows;
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
        itemNo: null,
        payType: null,
        applyBy: null,
        applyName: null,
        accountBy: null,
        accountName: null,
        billDesc: null,
        dueDate: null,
        accountDate: null,
        payDate: null,
        arrangeNo: null,
        voucherNo: null,
        status: null,
        createBy: null,
        createName: null,
        createTime: null,
        createDept: null,
        createDeptName: null,
        updateBy: null,
        updateName: null,
        updateTime: null
      };
      this.form.financeAoLoanDetailList = [];
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
      this.getItemNoList()
      this.open = true;
      this.title = "添加借支申请主档";
      this.form.companyId = this.queryParams.companyId
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getItemNoList()
      const id = row.id || this.ids
      getLoanApply(id).then(response => {
        this.form = response.data;
       // this.financeAoLoanDetailList = response.data.financeAoLoanDetailList;
        this.open = true;
        this.title = "修改借支申请主档";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
         // this.form.financeAoLoanDetailList = this.financeAoLoanDetailList;
          if (this.form.id != null) {
            updateLoanApply(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLoanApply(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除借支申请主档编号为"' + ids + '"的数据项？').then(function() {
        return delLoanApply(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 报支管理-借支申请明细档序号 */
    rowFinanceAoLoanDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 报支管理-借支申请明细档添加按钮操作 */
    handleAddFinanceAoLoanDetail() {
      let obj = {};
      obj.seqNo = "";
      obj.itemNo = "";
      obj.detailNo = "";
      obj.crcyCode = "";
      obj.exchangeRate = "";
      obj.invoiceType = "";
      obj.netAmt = "";
      obj.taxAmt = "";
      obj.totalAmt = "";
      obj.srlDesc = "";
      obj.status = "";
      obj.createName = "";
      obj.updateName = "";
      this.form.financeAoLoanDetailList.push(obj);
    },
    /** 报支管理-借支申请明细档删除按钮操作 */
    handleDeleteFinanceAoLoanDetail() {
      if (this.checkedFinanceAoLoanDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的报支管理-借支申请明细档数据");
      } else {
        const financeAoLoanDetailList = this.form.financeAoLoanDetailList;
        const checkedFinanceAoLoanDetail = this.checkedFinanceAoLoanDetail;
        this.form.financeAoLoanDetailList = financeAoLoanDetailList.filter(function(item) {
          return checkedFinanceAoLoanDetail.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleFinanceAoLoanDetailSelectionChange(selection) {
      this.checkedFinanceAoLoanDetail = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/loanApply/export', {
        ...this.queryParams
      }, `loanApply_${new Date().getTime()}.xlsx`)
    },
    /** 表头标记红星*/
    addRedstar(h, {column }){
      return[
        h("span",""+ column.label),
        h("span", {style: "color: red"},"*"),
      ];
    },
  }
};
</script>
