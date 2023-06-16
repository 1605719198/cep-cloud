<template >
  <el-dialog title="查询条件" :visible.sync="visible" width="800px"
             top="5vh" append-to-body
             class="customDialogStyle"
             custom-class="saveAsDialog">
    <el-form :model="queryParams" ref="queryForm" size="small"
             :rules="rulesQuery"
             :inline="true" label-width="189px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="公司" prop="companyId">
            <el-select v-model="queryParams.companyId"
                       disabled
                       filterable placeholder="请输入会计公司">
              <el-option
                v-for="item in companyList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
                @keyup.enter.native="handleQuery">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="只印有交易资料" prop="transactionalData">
            <el-radio-group v-model="queryParams.transactionalData">
              <el-radio
                v-for="dict in dict.type.aa_yes_no"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="是否含未过账凭证" prop="unpostedVoucher">
            <el-radio-group v-model="queryParams.unpostedVoucher">
              <el-radio
                v-for="dict in dict.type.aa_yes_no"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="凭证日期" prop="voucherDate">
            <el-date-picker
              v-model="queryParams.voucherDate"
              value-format="yyyy-MM-dd"
              format="yyyy-MM-dd"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              @change="dutyDateChange">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col   :span="24">
          <el-form-item label="科目级别" prop="accountLevel">
            <el-select v-model="queryParams.accountLevel"
                       filterable placeholder="请输入科目级别">
              <el-option
                v-for="dict in dict.type.account_level"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
                @keyup.enter.native="handleQuery">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col  :span="24">
          <el-form-item label="会计科目" prop="acctNameStart">
            <el-input v-model="queryParams.acctNameStart"  placeholder="请输入会计科目">
              <el-button slot="append" icon="el-icon-search" @click="inputAcctName"
              ></el-button>
            </el-input>
          </el-form-item>
          ~
          <el-form-item prop="acctNameEnd">
            <el-input v-model="queryParams.acctNameEnd"  placeholder="请输入会计科目">
              <el-button slot="append" icon="el-icon-search" @click="inputAcctName1"
              ></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col   :span="24" >
          <el-form-item label="核算项目类别" prop="calTypeCode">
            <el-select v-model="queryParams.calTypeCode"
                       filterable placeholder="请输入核算项目类别">
              <el-option
                v-for="item in calTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col  :span="24">
          <el-form-item label="核算项目" prop="calNameaStart" >
            <el-input v-model="queryParams.calNameaStart"  placeholder="请输入核算项目">
              <el-button slot="append" icon="el-icon-search" @click="inputCalNamea"
              ></el-button>
            </el-input>
          </el-form-item>
          ~
          <el-form-item  prop="calNameaEnd">
            <el-input v-model="queryParams.calNameaEnd"  placeholder="请输入核算项目">
              <el-button slot="append" icon="el-icon-search" @click="inputCalNamea1"
              ></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col   :span="24" >
          <el-form-item label="凭证号码" prop="voucherNoStart" >
            <el-input v-model="queryParams.voucherNoStart"  placeholder="请输入凭证号码"/>
          </el-form-item>
          ~
          <el-form-item  prop="calNameaEnd">
            <el-input v-model="queryParams.voucherNoEnd"  placeholder="请输入凭证号码"/>
          </el-form-item>
        </el-col>
        <el-col   :span="24">
          <el-form-item label="借贷方" prop="debitOrCredit">
            <el-radio-group v-model="queryParams.debitOrCredit">
              <el-radio
                v-for="dict in dict.type.aa_drcr_add"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col   :span="24">
          <el-form-item label="显示核算项目" prop="accountingItems">
            <el-select v-model="queryParams.accountingItems"
                       filterable placeholder="请输入核算项目">
              <el-option
                v-for="dict in dict.type.aa_accounting_items"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
                @click.native="changReportType(dict)"
                @keyup.enter.native="handleQuery">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col   :span="24" >
          <el-form-item label="金额" prop="amountStart" >
            <el-input v-model="queryParams.amountStart"  placeholder="请输入金额">
            </el-input>
          </el-form-item>
          ~
          <el-form-item  prop="amountEnd">
            <el-input v-model="queryParams.amountEnd"  placeholder="请输入金额">
            </el-input>
          </el-form-item>
        </el-col>

        <el-col   :span="24" >
          <el-form-item label="数量或外币" prop="qtyFrnamtStart" >
            <el-input v-model="queryParams.qtyFrnamtStart"  placeholder="请输入数量或外币">
            </el-input>
          </el-form-item>
          ~
          <el-form-item  prop="amountNoEnd">
            <el-input v-model="queryParams.qtyFrnamtEnd"  placeholder="请输入数量或外币">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col   :span="24">
          <el-form-item label="是否显示数量" prop="isNoNumber">
            <el-radio-group v-model="queryParams.isNoNumber">
              <el-radio
                v-for="dict in dict.type.aa_yes_no"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSelectCoder">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
    <calTypeSteelPOP ref="selectPOP" @pop="getCalTypePOP"/>
    <calTypeSteelPOP ref="selectPOP2" @pop="getCalTypePOP2"/>

    <acctcodeCorpPop ref="selectAcctCodeCorpPop":companyId="this.queryParams.companyId" @ok="getAcctCodeCorpPop"/>
    <acctcodeCorpPop ref="selectAcctCodeCorpPop1":companyId="this.queryParams.companyId" @ok="getAcctCodeCorpPop1"/>
  </el-dialog>
</template>

<script>
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {listVoucherDetailSelect} from "@/api/finance/aa/voucher";
import calTypeSteelPOP from "@/views/components/finance/calTypeSteelPOP";
import acctcodeCorpPop from "@/views/finance/aa/acctcodeCorpPop";
import { selectCalTypeList} from "@/api/finance/aa/calType";
export default {
  name: "selectMoreConditions.vue",
  dicts: ['account_level','aa_yes_no','aa_accounting_items','aa_drcr_add'],
  components: {
    calTypeSteelPOP,acctcodeCorpPop
  },
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中数组
      manufacturerId: [],
      // 选中数组
      manufacturerChineseName: [],
      companyList: [],
      // 选中数组
      compId: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 销售管理-厂商基本资料表格数据
      baseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        transactionalData:"Y",
        unpostedVoucher:'Y',
        voucherDate:null,
        startDate: null,
        endDate: null,
        accountLevel:null,
        acctNameStart:null,
        acctNameEnd:null,
        calTypeCode:null,
        calNameaStart:null,
        calNameaEnd:null,
        voucherNoStart:null,
        voucherNoEnd:null,
        debitOrCredit:'A',
        accountingItems:'1',
        amountStart:null,
        amountEnd:null,
        qtyFrnamtStart:null,
        qtyFrnamtEnd:null,
        isNoNumber:'Y'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 搜索框校验
      rulesQuery: {
        companyId: [
          {required: true, message: '请输入公司', trigger: 'blur'}
        ],
        voucherDate: [
          {required: true, message: '请输入凭证日期', trigger: 'blur'}
        ],

      },
      // 核算项目类别选单
      calTypeList: [],
      calTypeCodeIf:false,
    };
  },
  created() {
    this.getCompanyList();
    this.getCalTypeList();

  },
  methods: {

    getCalTypeList() {
      selectCalTypeList().then(response => {
        this.calTypeList = response;
      });
    },
    /** 项目1起始点击事件 */
    inputCalNamea(val) {
      this.$refs.selectPOP.show( this.queryParams);
    },
    /** 项目1结束点击事件 */
    inputCalNamea1(val) {
      this.$refs.selectPOP2.show( this.queryParams);
    },

    /** 项目1起始回调*/
    getCalTypePOP(val){
      this.$set( this.queryParams, 'calCodeaStart', val.calNo);
      this.$set( this.queryParams, 'calNameaStart', val.calNo);
    },
    /** 项目1截止回调*/
    getCalTypePOP2(val){
      this.$set( this.queryParams, 'calNameaEnd', val.calNo);
      this.$set( this.queryParams, 'calCodeaEnd', val.calNo);
    },

    getAcctCodeCorpPop(val){
      this.$set( this.queryParams, 'acctCodeStart', val[0].acctCode);
      this.$set( this.queryParams, 'acctNameStart', val[0].acctCode);
      this.$set( this.queryParams, 'acctIdStart', val[0].acctId);
    },
    getAcctCodeCorpPop1(val){
      this.$set( this.queryParams, 'acctCodeEnd', val[0].acctCode);
      this.$set( this.queryParams, 'acctNameEnd', val[0].acctCode);
      this.$set( this.queryParams, 'acctIdEnd', val[0].acctId);
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
    handleSelectCoder(){
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.$emit("ok",this.queryParams);
          this.visible = false;
        }
      });

    },
    /** 会计科目点击事件 */
    inputAcctName(){
      this.$refs.selectAcctCodeCorpPop.show();

    },
    /** 会计科目点击事件 */
    inputAcctName1(){
      this.$refs.selectAcctCodeCorpPop1.show();

    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
      });
    },

    // 显示弹框
    show(queryParams) {
      if(queryParams){
        this.queryParams.companyId= queryParams.companyId;
      }
      if(!this.queryParams.companyId){
        this.$message.error('公司别不能为空');
        return
      }
      this.visible = true;
    },

    /** 查询销售管理-厂商基本资料列表 */
    getList() {
      this.loading = true;
      listVoucherDetailSelect(this.queryParams).then(response => {
        this.baseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;

    },
    // 表单重置

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    //公司下拉选单掉用方法
    changReportType(val) {
      if (val.value!='1'){
        this.calTypeCodeIf=true
      }else {
        this.calTypeCodeIf=false
      }
    },
  }
}
</script>

<style scoped>
/deep/ .el-input--small .el-input__inner {
  height: 32px;
  line-height: 32px;
  width: 175px;
}
</style>
