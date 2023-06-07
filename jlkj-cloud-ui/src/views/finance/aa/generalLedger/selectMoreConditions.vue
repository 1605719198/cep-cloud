<template >
  <el-dialog title="查询条件" :visible.sync="visible" width="800px"
             top="5vh" append-to-body
             class="customDialogStyle"
             custom-class="saveAsDialog">
    <el-form :model="queryParams" ref="queryForm" size="small"
             :rules="rulesQuery"
             :inline="true" label-width="189px">
      <el-row>
        <el-col   :span="24">
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
        <el-col   :span="24">
          <el-form-item label="会计期间" prop="acctPeriod">
            <el-date-picker
              v-model="queryParams.acctPeriod"
              value-format="yyyy-MM"
              format="yyyy-MM"
              type="monthrange"
              range-separator="至"
              start-placeholder="开始月份"
              end-placeholder="结束月份"
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


        <el-col   :span="24">
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
        <el-col   :span="24" >
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
        <el-col   :span="24">
          <el-form-item label="显示主项" prop="mainItems">
            <el-radio-group v-model="queryParams.mainItems">
              <el-radio
                v-for="dict in dict.type.aa_main_items"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col   :span="24">
          <el-form-item label="显示核算项目" prop="accountingItems">
            <el-radio-group v-model="queryParams.accountingItems">
              <el-radio
                v-for="dict in dict.type.aa_yes_no"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col   :span="24">
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
        <el-col   :span="24">
          <el-form-item label="无发生额不显示" prop="amountNotDisplayed">
            <el-radio-group v-model="queryParams.amountNotDisplayed">
              <el-radio
                v-for="dict in dict.type.aa_yes_no"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col   :span="24">
          <el-form-item label="余额为零且无发生额不显示" prop="balanceZero">
            <el-radio-group v-model="queryParams.balanceZero">
              <el-radio
                v-for="dict in dict.type.aa_yes_no"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col   :span="24">
          <el-form-item label="是否显示本期合计" prop="currentPeriod">
            <el-radio-group v-model="queryParams.currentPeriod">
              <el-radio
                v-for="dict in dict.type.aa_yes_no"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col   :span="24">
          <el-form-item label="是否显示本年累计" prop="yearAdd">
            <el-radio-group v-model="queryParams.yearAdd">
              <el-radio
                v-for="dict in dict.type.aa_yes_no"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
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
  dicts: ['account_balance_type', 'account_level','aa_yes_no','aa_main_items'],
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
        status:null,
        startDate: null,
        endDate: null,
        acctNameStart:null,
        acctNameEnd:null,
        calNameaStart:null,
        calNameaEnd:null,
        qtyFrnamtStart:null,
        qtyFrnamtEnd:null,
        reportType:null,
        acctPeriodStart:null,
        acctPeriodEnd:null,
        accountLevel:null,
        dominantTerm:'Y',
        calculate:'Y',
        unpostedVoucher:'N',
        amountNotDisplayed:'N',
        balanceZero:'N',
        isNoCurrentPeriodAdd:'Y',
        isNoThisYearAdd:'Y',
        mainItems:'01',
        accountingItems:'Y',
        isNoNumber:'N',
        currentPeriod:'N',
        yearAdd:'N',
        calTypeCode:null,
        sys:''
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 搜索框校验
      rulesQuery: {
        companyId: [
          {required: true, message: '请输入公司', trigger: 'blur'}
        ],
        acctPeriod: [
          {required: true, message: '请输入会计期间', trigger: 'blur'}
        ],
      },
      // 核算项目类别选单
      calTypeList: [],

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
      /*    this.selectManufacturer = true
          this.$refs.select.show();*/
    },
    /** 会计科目点击事件 */
    inputAcctName1(){
      this.$refs.selectAcctCodeCorpPop1.show();
      /*    this.selectManufacturer = true
          this.$refs.select.show();*/
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
      this.reset();
    },
    // 表单重置

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {

      this.resetForm("queryForm");
      this.queryParams.endDate=new Date()
      this.handleQuery();
    },
    clickRow(row) {
      this.$emit("ok",row);
      this.visible = false;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {

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
