<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="86px">
      <el-form-item label="公司" prop="companyId">
        <el-select v-model="queryParams.companyId" filterable placeholder="请输入会计公司">
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
      <el-form-item label="厂商编码" prop="manufacturer">
        <el-input
          v-model="queryParams.manufacturer"
          placeholder="请输入厂商编码"
          clearable
          disabled
          @keyup.enter.native="handleQuery"
        >
          <el-button slot="append" icon="el-icon-search" @click="inputQueryClick"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="到期日" prop="dueDate">
        <el-date-picker
          v-model="queryParams.dueDate"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          format="yyyy-MM-dd"
          @change="dutyDateChange">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ao_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="支出类别" prop="itemNo"  v-if="hideIf">
        <el-select v-model="queryParams.itemNo" filterable placeholder="请选择支出类别"  style="width:275px">
          <el-option
            v-for="item in itemNoList"
            :key="item.value"
            :label="item.value+'_'+item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="申请人" prop="createName"  v-if="hideIf">
        <el-input

          v-model="queryParams.createName"
          placeholder="请输入申请人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请人部门" prop="createDeptName"  v-if="hideIf">
        <el-input
          v-model="queryParams.createDeptName"
          placeholder="请输入申请人部门"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业务员" prop="salesmanName"  v-if="hideIf">
        <el-input
          v-model="queryParams.salesmanName"
          placeholder="请输入业务员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button type="primary" icon="el-icon-circle-plus-outline" size="mini" v-if="moreConditionsIf" @click="handleMoreConditions">更多条件</el-button>
        <el-button type="primary" icon="el-icon-circle-close" size="mini" v-if="hideIf" @click="handleHideQuery">隐藏</el-button>
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
          v-hasPermi="['ap:loan:add']"
        >新增</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['ap:loan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          disabled
          @click="handleExport"
          v-hasPermi="['ap:loan:export']"
        >抛账</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="loanList"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="公司" align="center" prop="companyId">
        <template slot-scope="scope">
          <budget-tag :options="companyList" :value="scope.row.companyId"/>
        </template>
      </el-table-column>
      <el-table-column label="预支单号" align="center" prop="billNo" />
      <el-table-column label="受款厂商" align="center" prop="manufacturer" :show-overflow-tooltip='true'>
        <template slot-scope="scope">
          <budget-tag :options="manufacturerList" :value="scope.row.manufacturer" />
        </template>
      </el-table-column>

      <el-table-column label="申请人" align="center" prop="createName" />
      <el-table-column label="业务员" align="center" prop="salesmanName" />
      <el-table-column label="预付金额" align="center" prop="totalAmt" />
      <el-table-column label="付款日期" align="center" prop="dueDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="付款金额" align="center" prop="paidAmt" />
      <el-table-column label="状态" align="center" prop="status" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ao_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ap:loan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ap:loan:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleProcess(scope.row)"
            v-hasPermi="['ap:loan:edit']"
          >审核</el-button>
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

    <!-- 添加或修改预付申请主档对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" >
        <el-row>
          <el-col :span="8">
            <el-form-item label="公司" prop="companyId" label-width="96px">
              <el-select v-model="form.companyId" filterable placeholder="请输入会计公司">
                <el-option
                  v-for="item in companyList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  @click.native="changeCompanyName(item)">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="预支单号" prop="billNo" label-width="96px">
              <span style="text-align: center;display:block;width: 121px">{{ form.billNo }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="申请日期" prop="createDay" label-width="96px">
              <span style="text-align: center;display:block;width: 121px">{{ parseTime(form.createDay, '{y}-{m}-{d}') }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="支出类别" prop="itemNo" label-width="96px">
              <el-select v-model="form.itemNo" filterable placeholder="请选择支出类别"  style="width:217px">
                <el-option
                  v-for="item in itemNoList"
                  :key="item.value"
                  :label="item.value+'_'+item.label"
                  @click.native="changeName(item)"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="厂商编码" prop="manufacturer" label-width="96px">
              <el-input disabled v-model="form.manufacturer" placeholder="请输入厂商编码" style="width: 217px" >
                <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="期望付款方式" prop="payType" label-width="96px">
              <el-select v-model="form.payType" placeholder="请选择期望付款方式" clearable>
                <el-option
                  v-for="dict in dict.type.ao_repayment_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="签核会计" prop="accountName" label-width="96px">
              <el-input v-model="form.accountName" placeholder="请输入签核会计姓名"  style="width: 217px"disabled>
                <el-button slot="append" icon="el-icon-search" @click="inputClickPersonnel()"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="案由摘要" prop="billDesc"  label-width="96px">
              <el-input v-model="form.billDesc" placeholder="请输入摘要"  style="width: 217px" maxlength="200" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="币别" prop="crcyunit"  label-width="96px">
              <el-select v-model="form.crcyunit" placeholder="请选择币别" clearable>
                <el-option
                  v-for="dict in dict.type.gp_currency_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                  @click.native="changeCrcyUnit(dict)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="经办人" prop="createName" label-width="96px">
              {{form.createName}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="经办人部门" prop="createDeptName"  label-width="96px">
              {{form.createDeptName}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="会计确认人" prop="updateName"  label-width="96px">
              {{form.updateName}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="会计确认日期" prop="accountDate" l label-width="96px">
              <span style="text-align: center;display:block;width: 121px">{{ parseTime(form.accountDate, '{y}-{m}-{d}') }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="状态" prop="status" label-width="96px">
              <dict-tag :options="dict.type.ao_status" :value="form.status"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="付款凭证号" prop="payVoucherNo"  label-width="96px">
              <span style="text-align: center;display:block;width: 121px">
                <el-button size="mini" @click="voucherNoClick(form.payVoucherNo)" type="text">{{ form.payVoucherNo }}
                </el-button></span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="付款日期" prop="payVoucherDate"  label-width="96px">
              <span style="text-align: center;display:block;width: 121px">{{ parseTime(form.payVoucherDate, '{y}-{m}-{d}') }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="center">预付申请明细档信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddFinanceApLoanDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteFinanceApLoanDetail">删除</el-button>
          </el-col>
        </el-row>

        <el-table :data="form.financeApLoanDetailList"
                  :row-class-name="rowFinanceApLoanDetailIndex"
                  @selection-change="handleFinanceApLoanDetailSelectionChange"
                  ref="financeApLoanDetailList"
        :key="indexKey">
          <el-table-column type="selection" width="50" align="center"
         />
          <el-table-column label="序号" align="center" prop="index" width="50"/>

          <el-table-column label="费用细项" prop="detailNo" width="150" :render-header="addRedStar">
            <template slot-scope="scope">
              <el-form-item
                :prop="'financeApLoanDetailList.' + scope.$index + '.detailNo'" :rules="rulesLoanDetailList.detailNo">
                <el-select v-model="scope.row.detailNo" filterable placeholder="请输入费用细项">
                  <el-option
                    v-for="item in detailNoList"
                    :key="item.value+item.label"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="原始凭证类型" prop="invoiceType" width="150" :render-header="addRedStar">
            <template slot-scope="scope">
              <el-form-item
                :prop="'financeApLoanDetailList.' + scope.$index + '.invoiceType'" :rules="rulesLoanDetailList.invoiceType">
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
          <el-table-column label="金额" prop="totalAmt" width="150" :render-header="addRedStar">
            <template slot-scope="scope">
              <el-form-item
                :prop="'financeApLoanDetailList.' + scope.$index + '.totalAmt'"  :rules="rulesLoanDetailList.totalAmt">
                <el-input v-model="scope.row.totalAmt" placeholder="请输入金额"
                                 oninput="if(value<0)value=0" type="number"
                                 @input="changNum(scope.row)"   style="width: 131px;"  />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="记账币金额" prop="amt" width="150" :render-header="addRedStar">
          </el-table-column>
          <el-table-column label="合同号码" prop="contract" width="150" :render-header="addRedStar">
            <template slot-scope="scope">
              <el-form-item
                :prop="'financeApLoanDetailList.' + scope.$index + '.contract'" :rules="rulesLoanDetailList.contract">
              <el-input v-model="scope.row.contract" placeholder="请输入合同号码" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="预算项目" prop="budget" width="150" :render-header="addRedStar">
            <template slot-scope="scope">
              <el-form-item
                :prop="'financeApLoanDetailList.' + scope.$index + '.budget'" :rules="rulesLoanDetailList.budget">

              <el-input v-model="scope.row.budget" placeholder="请输入预算项目" />
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="摘要说明" prop="srlDesc" width="150" :render-header="addRedStar">
            <template slot-scope="scope">
              <el-form-item
                :prop="'financeApLoanDetailList.' + scope.$index + '.srlDesc'" :rules="rulesLoanDetailList.srlDesc">

              <el-input v-model="scope.row.srlDesc" placeholder="请输入摘要说明"  maxlength="200" show-word-limit/>
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
    <selectManufacturer ref="select" @ok="getJobNumber"/>
    <selectManufacturer ref="selectQuery" @ok="getQueryJobNumber"/>
    <select-user ref="selectUser" @ok="getJobUserNumber"/>
  </div>
</template>

<script>
import { listLoan, getLoan, delLoan, addLoan, updateLoan } from "@/api/finance/ap/loan";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import budgetTag from "@/views/finance/ao/budget/budgetTag";
import selectManufacturer from "@/views/components/finance/selectManufacturer";
import selectUser from "@/views/components/human/selectUser/selectUser";
import {selectManufacturerList} from "@/api/finance/gp/base1";

import { getItemNoList } from "@/api/finance/ap/itemComp";
import { selectDetailNoList } from "@/api/finance/ap/itemDetailCompany";
import { selecCrcyunitList } from "@/api/finance/aa/rate";
import {parseTime} from "@/utils/jlkj";
export default {
  name: "loan",
  dicts: ['ao_status','ao_repayment_type','gp_currency_type','ao_voucher_type'],
  components: {selectManufacturer, budgetTag,selectUser},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedFinanceApLoanDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 预付申请主档表格数据
      loanList: [],


      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        billNo: null,
        manufacturer: null,
        dueDate: null,
        status: '00',
        startDate: null,
        endDate: null,
        queryParams: null,
        itemNo: null,
      },
      // 查询参数
      queryParam: {
        pageNum: 1,
        pageSize: 10,
        itemNo: null,
        companyId: null,
        ratedate: null,
        startDate: null,
        endDate: null,
        ratecrcy: null,
        createName: null,
        createDeptName: null,
        salesmanName: null,
      },
      // 会计公司下拉选单
      companyList: [],
      // 厂商编码匹配汉字
      manufacturerList: [],
      // 费用细项
      detailNoList: [],
      // 表单参数
      form: {
        financeApLoanDetailList: []
      },

      // 表单校验
      rules: {
        companyId: [
          {required: true, message: '请输入公司', trigger: 'blur'}
        ],
        itemNo: [
          {required: true, message: '请输入支出类别', trigger: 'blur'}
        ],
        manufacturer: [
          {required: true, message: '请输入厂商编码', trigger: 'blur'}
        ],
        accountName: [
          {required: true, message: '请输入签核会计', trigger: 'blur'}
        ],
        billDesc: [
          {required: true, message: '请输入案由摘要', trigger: 'blur'},
          {max: 200, message: '最大长度200个字符', trigger: 'blur'}
        ],
      },
      // 表格规则
      rulesLoanDetailList:{
        detailNo: [
          {required: true, message: '费用细项不能为空', trigger: 'blur'}
        ],
        invoiceType: [
          {required: true, message: '凭证类型不能为空', trigger: 'blur'}
        ],
        totalAmt: [
          {required: true, message: '金额不能为空', trigger: 'blur'}
        ],
        contract: [
          {required: true, message: '合同编号不能为空', trigger: 'blur'},
          {max: 50, message: '最大长度50个字符', trigger: 'blur'}
        ],
        budget: [
          {required: true, message: '预算项目不能为空', trigger: 'blur'},
          {max: 50, message: '最大长度50个字符', trigger: 'blur'}
        ],
        srlDesc: [
          {required: true, message: '请输入摘要', trigger: 'blur'},
          {max: 200, message: '最大长度200个字符', trigger: 'blur'}
        ],
      },
      // 更多条件
      moreConditionsIf:true,
      //隐藏
      hideIf:false,
      // 支付类别下拉选单
      itemNoList: [],
      // 公司下拉选单名称
      companyIdName:'',
      indexKey:0,
      //汇率
      rateValue:null,
      // 查询公司下拉选单名称
      queryCompanyId:null,
    };
  },
  created() {
    this.getCompanyList()

  },
  methods: {
    /** 支出类别下拉选*/
    changeName(val){
      if (!!this.companyIdName){
        for (let t=0;t<this.form.financeApLoanDetailList.length;t++){
          this.form.financeApLoanDetailList[t].detailNo = ''
          this.indexKey+=1;
        }
        this.queryParam.itemNo =val.value
        this.queryParam.companyId =  this.companyIdName
        selectDetailNoList(this.queryParam).then(response => {
          this.detailNoList = response;
        });
      }else {
        this.$modal.msgError("请先选择公司别");
      }
    },
    changNum(row) {
      row.amt = (row.totalAmt * this.rateValue).toFixed(2);
    },
    /** 币别*/
    changeCrcyUnit(val){
      if (!!this.form.crcyunit){
        let now = new Date();
        let year = now.getFullYear();
        let month = now.getMonth() + 1;
        let day = now.getDate();
        this.queryParam.endDate=  year + "-" + month + "-" + day;
        this.queryParam.startDate=year + "-" + month +'-01'
        this.queryParam.ratecrcy =this.form.crcyunit
        selecCrcyunitList(this.queryParam).then(response => {
       this.rateValue=  response.data.ratevalue
          if(!!this.rateValue){
            for (let t=0;t<this.form.financeApLoanDetailList.length;t++){
              this.form.financeApLoanDetailList[t].amt = (this.form.financeApLoanDetailList[t].totalAmt * this.rateValue).toFixed(2)
              this.indexKey+=1;
            }
          }else {
            this.$modal.msgError("请维护当月汇率信息");
          }

        });
      }else {
        this.$modal.msgError("请先选择币别");
      }
    },
    /** 公司下拉选单*/
    changeCompanyName(val){
   this.companyIdName = val.value
    },
    /** 表头标记红星*/
    addRedStar(h, {column }){
      return[
        h("span",""+ column.label),
        h("span", {style: "color: red"},"*"),
      ];
    },
    voucherNoClick(row) {
      this.$router.push({
        name: 'Voucher', query: {
          voucherNo: row,
          companyId: this.form.companyId
        }
      });
    },
    /** 签核会计*/
    inputClickPersonnel(){
      this.$refs.selectUser.show();
    },
    getJobUserNumber(val, userName,compId) {
      this.form.accountBy = val;
      this.form.accountName = userName;
    },
    /** 厂商弹窗*/
    getJobNumber(val,userName) {
      this.form.manufacturer = val
    },
    inputClick(){
      this.$refs.select.show();
    },
    /** 厂商查询 */
    getQueryJobNumber(val,userName) {
      this.queryParams.manufacturer = val
    },
    inputQueryClick(){
      this.$refs.selectQuery.show();
    },
    /** 公司别下拉选单 */
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
        this.queryParams.companyId = this.companyList[0].value
        this.queryCompanyId = this.companyList[0].value
        this.getManufacturerList()
        this.getItemNoList()
      });
    },
    /** 厂商编码 */
    getManufacturerList() {
      selectManufacturerList(this.queryParams.companyId).then(response => {
        this.manufacturerList = response;
        this.getList();
      });
    },
    /** 报支类别 */
    getItemNoList() {
      getItemNoList(this.queryParams.companyId).then(response => {
        this.itemNoList = response;
      });
    },
    //查询日期切换事件
    dutyDateChange(val) {
      if (val!=null){
        this.queryParams.startDate = val[0]
        this.queryParams.endDate = val[1]
      }else {
        this.queryParams.startDate = ''
        this.queryParams.endDate = ''
      }
    },
    /**更多条件 */
    handleMoreConditions(){
      this.moreConditionsIf = false
      this.hideIf= true
    },
    /**隐藏 */
    handleHideQuery(){
      this.moreConditionsIf = true
      this.hideIf= false
    },
    /** 查询预付申请主档列表 */
    getList() {
      this.loading = true;
      listLoan(this.queryParams).then(response => {
        if (response.rows[0]!=null){
          this.loanList = response.rows;
          this.total = response.total;
        }else {
          this.loanList = [];
          this.total = 0;
        }

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
        manufacturer: null,
        itemNo: null,
        payType: null,
        crcyunit: 'CNY',
        accountBy: null,
        accountName: null,
        billDesc: null,
        dueDate: null,
        status: null,
        salesman: null,
        salesmanName: null,
        createBy: null,
        createName: null,
        createTime:null,
        createDay:null,
        createDept: null,
        createDeptName: null,
        updateBy: null,
        updateName: null,
        updateTime: null,
        accountDate: null,
       payVoucherNo: null,
       payVoucherDate: null,

      };
      this.form.financeApLoanDetailList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams={
        pageNum: 1,
        pageSize: 10,
        companyId: this.queryCompanyId,
        billNo: null,
        manufacturer: null,
        dueDate: null,
        status: '00',
        startDate: null,
        endDate: null,
        queryParams: null,
        itemNo: null,
      },
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    //审核点击跳转
    handleProcess(row, column, e) {
        this.$emit('change', 'second', row.billNo);

    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getItemNoList()
      let now = new Date();
      let year = now.getFullYear();
      let month = now.getMonth() + 1;
      let day = now.getDate();
      this.form.createDay =  year + "-" + month + "-" + day;
      this.changeCrcyUnit(this.getDicts("gp_currency_type"))
      this.open = true;
      this.title = "添加预付申请主档";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getItemNoList()
      const id = row.id || this.ids
      getLoan(id).then(response => {
        this.form = response.data;
        this.form.createDay = this.form.createTime
        this.companyIdName =  this.form.companyId
        this.changeCrcyUnit(this.getDicts("gp_currency_type"))
        this.open = true;
        this.title = "修改预付申请主档";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.exchangeRate=this.rateValue
          if (this.form.financeApLoanDetailList.length>0){
            if (this.form.id != null) {
              if (this.form.status=="00"){
                updateLoan(this.form).then(response => {
                  this.$modal.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                });
              }else {
                this.$modal.msgError("当前状态不允许修改");
              }
            } else {
              addLoan(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }else {
            this.$modal.msgError("细项信息至少保留一笔");
          }

        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除预付申请主档编号为"' + ids + '"的数据项？').then(function() {
        return delLoan(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 预付申请明细档序号 */
    rowFinanceApLoanDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 预付申请明细档添加按钮操作 */
    handleAddFinanceApLoanDetail() {
      let obj = {};
      obj.itemNo = "";
      obj.detailNo = "";
      obj.exchangeRate = "";
      obj.invoiceType = "";
      obj.netAmt = "";
      obj.taxAmt = "";
      obj.totalAmt = "";
      obj.contract = "";
      obj.budget = "";
      obj.srlDesc = "";
      obj.status = "";
      this.form.financeApLoanDetailList.push(obj);
    },
    /** 预付申请明细档删除按钮操作 */
    handleDeleteFinanceApLoanDetail() {
      if (this.checkedFinanceApLoanDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的预付申请明细档数据");
      } else {
        const financeApLoanDetailList =    this.form.financeApLoanDetailList
        const checkedFinanceApLoanDetail = this.checkedFinanceApLoanDetail;
        this.form.financeApLoanDetailList = financeApLoanDetailList.filter(function(item) {
          return checkedFinanceApLoanDetail.indexOf(item.index) == -1
        });
        /*     const checkedFinanceApLoanDetail = this.checkedFinanceApLoanDetail;
             checkedFinanceApLoanDetail.forEach(item => {
               console.log(item-1);
               delete this.form.financeApLoanDetailList.splice(item-1, 1);
             })*/
/*        this.$nextTick(()=>{
          this.$refs.form.financeApLoanDetailList.resetField();
        })*/
         console.log(this.form.financeApLoanDetailList);
        this.indexKey+=1;
      }
    },
    /** 复选框选中数据 */
    handleFinanceApLoanDetailSelectionChange(selection) {
      this.checkedFinanceApLoanDetail = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ap/loan/export', {
        ...this.queryParams
      }, `loan_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
