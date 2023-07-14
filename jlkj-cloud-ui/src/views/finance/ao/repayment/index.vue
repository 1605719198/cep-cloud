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
      <el-form-item label="单号" prop="billNo">
        <el-input
          v-model="queryParams.billNo"
          placeholder="请输入借支单号或还款单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请人" prop="createBy">
        <el-input v-model="queryParams.createBy"  placeholder="请输入申请人" clearable @keyup.enter.native="handleQuery">
          <el-button slot="append" icon="el-icon-search" @click="inputClick()"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="还款日期" prop="payDate">
        <el-date-picker
          clearable
          v-model="payDates"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd">
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="repaymentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="借支单号" align="center" >
        <template v-slot="scope"> {{ scope.row.loanNo }} </template>
      </el-table-column>
      <el-table-column label="还款单号" align="center" prop="billNo" />
      <el-table-column label="申请人" align="center" >
        <template v-slot="scope"> {{ scope.row.applyBy+'_' + scope.row.applyName }} </template>
      </el-table-column>
      <el-table-column label="经办人" align="center" >
        <template v-slot="scope"> {{ scope.row.createBy+'_' + scope.row.createName }} </template>
      </el-table-column>
      <el-table-column label="借支总额" align="center">
        <template v-slot="scope"> {{ scope.row.totalAmt }} </template>
      </el-table-column>
      <el-table-column label="本次还款金额" align="center" prop="amt"/>
      <el-table-column label="状态" align="center" prop="status" >
        <template v-slot="scope"> {{ statusName(scope.row.status) }} </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-search"
            @click="handleView(scope.row)"
            v-hasPermi="['finance:repayment:edit']"
          >详情</el-button>
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
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司别" prop="companyId">
              <el-select v-model="form.companyId" disabled style="width:240px">
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
            <el-form-item label="还款单号" prop="billNo">
              {{form.billNo}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="借支单号">  {{ form.loanNo }} </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="借支总额"> {{ form.totalAmt }} </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="本次还款金额" prop="amt">
              <el-input v-if="!isQuery" v-model="form.amt" placeholder="请输入本次还款金额" clearable/>
              <span v-if="isQuery">{{ form.amt }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="已报销金额"> {{ form.reimbAmt }} </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="还款方式" prop="payType">
              <el-select v-if="isQuery===false" v-model="form.payType" placeholder="请选择还款方式" style="width: 240px">
                <el-option
                  v-for="dict in dict.type.ao_repayment_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
              <dict-tag v-if="isQuery" :options="dict.type.ao_repayment_type" :value="form.payType"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="未报销金额"> {{ form.unreimbAmt }} </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="借款人"> {{ form.loanBy+"_"+form.loanName }} </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态"> {{ statusName(form.status) }} </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="还款日期"> {{ form.payDate }} </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="异动时间"> {{ form.updateTime }} </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer" align="center">
        <el-button v-if="isQuery===false" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <select-loan-apply ref="selectLoanApply" @ok="handleQuery" ></select-loan-apply>
    <select-user ref="select" @ok="getJobNumber"/>
  </div>
</template>

<script>
import { qryList, getRepayment, delRepayment, addRepayment, updateRepayment } from "@/api/finance/ao/repayment";
import { selectCompanyList } from "@/api/finance/aa/companyGroup";
import selectUser from "@/views/components/human/selectUser/selectUser";
import selectLoanApply from "@/views/finance/ao/repayment/selectLoanApply";
import {parseTime} from "@/utils/jlkj";
export default {
  name: "Repayment",
  dicts: ['ao_repayment_type'],
  components: { selectLoanApply,selectUser },
  data() {
    // 额度验证，保存小数点后两位
    const validateAmt = (rule, value, callback) => {
      var pattern = /^[0-9]+(\.[0-9]{1,2})?$/
      if (value) {
        if (!pattern.test(value)) {
          callback(new Error("请输入≥0并且小数点≤2位的数据"))
        }
      }
      if (value > 100000000) {
        callback(new Error("数据限额最大值为1亿，请联系管理员修改！"))
      }
      callback()
    }
    return {
      // 会计公司下拉选单
      companyList: [],
      // 还款日期区间
      payDates: [],
      // 查询详情
      isQuery: false,
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
        createBy: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        amt: [
          {required: true,message: "请输入本次还款金额", trigger: "blur"},
          { validator: validateAmt, trigger: 'blur' }
        ]
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

    /** 打开集团报支类别弹窗 */
    openSelectPop() {
      this.$refs.selectLoanApply.show(this.queryParams.companyId);
    },

    /** 人员选单事件 */
    inputClick() {
      const queryParams={
        compID:this.queryParams.companyId,
        keywords: '',
        pageNum: 1,
        pageSize: 10,
      }
      this.$refs.select.show(queryParams);
    },
    /** 获取工号 */
    getJobNumber(val, userName,compId) {
      this.queryParams.createBy = val;
    },


    /** 查询还款单资料档列表 */
    getList() {
      this.loading = true;
      // 处理还款日期区间查询
      // 解析的url为&params[beginDate]=2021-xx-xx&params[endDate]=2021-xx-xx
      // qs.stringify函数在处理Object时，如果value值为undefined（undefined与'undefined'不是同一值），会将对应的key去掉
      this.queryParams.params = {"beginDate":this.payDates?.[0],"endDate":this.payDates?.[1]};
      qryList(this.queryParams).then(response => {
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
        updateTime: null,
        totalAmt: null,
        reimbAmt: null,
        unreimbAmt: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.payDates = null;
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
      this.isQuery = false;
      const id = row.id || this.ids
      getRepayment(id).then(response => {
        this.form = response.data;
        this.form.updateTime =  parseTime(this.form.updateTime,'{y}-{m}-{d} {h}:{i}:{s}');
        this.open = true;
        this.title = "修改还款单资料档";
      });
    },
    /** 详情按钮操作 */
    handleView(row) {
      this.reset();
      this.isQuery = true;
      const id = row.id || this.ids
      getRepayment(id).then(response => {
        this.form = response.data;
        this.form.updateTime =  parseTime(this.form.updateTime,'{y}-{m}-{d} {h}:{i}:{s}');
        this.open = true;
        this.title = "查看还款单资";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            if(this.form.amt > this.form.unreimbAmt){
              this.$modal.msgError("本次还款金额不能大于未报销金额");
              return;
            }
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
      this.$modal.confirm('确认要删除吗？').then(function() {
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
