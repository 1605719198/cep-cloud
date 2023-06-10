<template xmlns:el-col="http://www.w3.org/1999/html">
  <el-dialog title="查询条件" :visible.sync="visible" width="1250px"
             top="5vh" append-to-body
             class="customDialogStyle"
             custom-class="saveAsDialog">
    <el-form :model="queryParams" ref="queryForm" size="small"
             :inline="true" label-width="106px">
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>

        <el-row>
          <el-col   :span="12">
            <el-form-item label="会计公司" prop="companyId">
              <el-select v-model="queryParams.companyId"
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
          <el-col   :span="12">
            <el-form-item label="凭证类型" prop="voucherType">
              <el-select v-model="queryParams.voucherType"
                         filterable placeholder="请输入凭证类型">
                <el-option
                  v-for="item in voucherTypeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  @keyup.enter.native="handleQuery">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col   :span="12">
            <el-form-item label="凭证号码" prop="voucherNo">
              <el-input
                v-model="queryParams.voucherNoStart"
                placeholder="请输入凭证号码"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            ~
            <el-form-item>
              <el-input
                v-model="queryParams.voucherNoEnd"
                placeholder="请输入凭证号码"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
          </el-col>
          <el-col   :span="12">
            <el-form-item label="凭证状态" prop="status">
              <el-select v-model="queryParams.status"
                         filterable placeholder="请输入凭证状态">
                <el-option
                  v-for="dict in dict.type.aa_voucher_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                  @keyup.enter.native="handleQuery">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col   :span="12">
            <el-form-item label="凭证日期" prop="voucherDate">
              <el-date-picker
                v-model="queryParams.voucherDate"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="yyyy-MM-dd"
                format="yyyy-MM-dd"
                @change="dutyDateChange">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col   :span="12">
            <el-form-item label="系统别" prop="apid">
              <el-select v-model="queryParams.apid"
                         filterable placeholder="请输入系统别">
                <el-option
                  v-for="dict in dict.type.aa_source_sys"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                  @keyup.enter.native="handleQuery">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col   :span="12">
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
          <el-col   :span="12">
            <el-form-item label="新增人员" prop="createBy">
              <el-input v-model="queryParams.createBy"  placeholder="请输入新增人员">

              </el-input>
            </el-form-item>
          </el-col>
          <el-col   :span="12">
            <el-form-item label="核算项目一" prop="calNameaStart">
              <el-input v-model="queryParams.calNameaStart"  placeholder="请输入核算项目一">
                <el-button slot="append" icon="el-icon-search" @click="inputCalNamea"
                ></el-button>
              </el-input>
            </el-form-item>
            ~
            <el-form-item  prop="calNameaEnd">
              <el-input v-model="queryParams.calNameaEnd"  placeholder="请输入核算项目一">
                <el-button slot="append" icon="el-icon-search" @click="inputCalNamea1"
                ></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col   :span="12">
            <el-form-item label="经办人姓名" prop="pastuserName">
              <el-input v-model="queryParams.pastuserName" placeholder="请输入经办人姓名"/>
            </el-form-item>
          </el-col>
          <el-col   :span="12">
            <el-form-item label="核算项目二" prop="calNamebStart">
              <el-input v-model="queryParams.calNamebStart"  placeholder="请输入核算项目二">
                <el-button slot="append" icon="el-icon-search" @click="inputCalNameb"
                ></el-button>
              </el-input>
            </el-form-item>
            ~
            <el-form-item prop="calNamebEnd">
              <el-input v-model="queryParams.calNamebEnd"  placeholder="请输入核算项目二">
                <el-button slot="append" icon="el-icon-search" @click="inputCalNameb1"
                ></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col   :span="12">
            <el-form-item label="分录摘要" prop="srlDesc">
              <el-input v-model="queryParams.srlDesc"  placeholder="请输入分录摘要">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col   :span="12">
            <el-form-item label="金额" prop="qtyFrnamt">
              <el-input v-model="queryParams.qtyFrnamtStart"  placeholder="请输入金额">

              </el-input>
            </el-form-item>
            ~
            <el-form-item>
              <el-input v-model="queryParams.qtyFrnamtEnd"  placeholder="请输入金额">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>


    </el-form>
    <el-row>
      <el-form>
        <el-form-item label="查询结果">
          <el-table @row-click="clickRow" ref="table"
                    :data="baseList"
                    height="360px">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column label="凭证号" align="center" prop="voucherNo" />
            <el-table-column label="凭证日期" align="center" prop="voucherDate"
            />
            <el-table-column label="系统别" align="center" prop="apid" >
              <template slot-scope="scope">
                <dict-tag :options="dict.type.aa_source_sys" :value="scope.row.apid"/>
              </template>
            </el-table-column>
            <el-table-column label="摘要" align="center" prop="srlDesc" />
            <el-table-column label="借方金额" align="center" prop="drAmt" />
            <el-table-column label="贷方金额" align="center" prop="crAmt" />
            <el-table-column label="制作人" align="center" prop="pastuserName" />
            <el-table-column label="状态" align="center" prop="status" >
              <template slot-scope="scope">
                <dict-tag :options="dict.type.aa_voucher_status" :value="scope.row.status"/>
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
    </el-row>
    <calTypePOP ref="selectPOP" @pop="getCalTypePOP"/>
    <calTypePOP ref="selectPOP2" @pop="getCalTypePOP2"/>
    <calTypePOP ref="selectPOP3" @pop="getCalTypePOP3"/>
    <calTypePOP ref="selectPOP4" @pop="getCalTypePOP4"/>
    <acctcodeCorpPop ref="selectAcctCodeCorpPop" :companyId="this.queryParams.companyId" @ok="getAcctCodeCorpPop"/>
    <acctcodeCorpPop ref="selectAcctCodeCorpPop1":companyId="this.queryParams.companyId" @ok="getAcctCodeCorpPop1"/>
  </el-dialog>
</template>

<script>
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {selectVoucherTypeList} from "@/api/finance/aa/voucherType";
import {listVoucherDetailSelect} from "@/api/finance/aa/voucher";
import calTypePOP from "@/views/components/finance/calTypePOP";
import acctcodeCorpPop from "@/views/finance/aa/acctcodeCorpPop";
export default {
  name: "selecVoucher",
  dicts: ['aa_source_sys', 'aa_voucher_status'],
  components: {
    calTypePOP,acctcodeCorpPop
  },
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中数组
      manufacturerId: [],
      // 选中数组
      manufacturerChineseName: [],
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
      // 会计公司下拉选单
      companyList: [],
      // 凭证维护-凭证类型
      voucherTypeList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        voucherType:null,
        voucherDate: null,
        voucherNo: null,
        status:null,
        apid:null,
        acctName:null,
        createBy:null,
        calNamea:null,
        calNameb:null,
        pastuserName:null,
        srlDesc:null,
        qtyFrnamt:null,
        voucherNoStart:null,
        voucherNoEnd:null,
        startDate: null,
        endDate: null,
        acctNameStart:null,
        acctNameEnd:null,
        calNameaStart:null,
        calNameaEnd:null,
        calNamebStart:null,
        calNamebEnd:null,
        qtyFrnamtStart:null,
        qtyFrnamtEnd:null,
        calCodec:null,
        calNamec:null,
        calIdc:null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      calNameaStart:""
    };
  },
  created() {
    this.getCompanyList();
    this.getVoucherTypeList();

  },
  methods: {
    /** 会计科目起始点击事件 */
    inputAcctName(){
      this.$refs.selectAcctCodeCorpPop.show();
    },
    /** 会计科目截止点击事件 */
    inputAcctName1(){
      this.$refs.selectAcctCodeCorpPop1.show();
    },
    /** 项目1起始点击事件 */
    inputCalNamea(val) {
      this.queryParams.calTypeCode =''
      this.$refs.selectPOP.show( this.queryParams);

    },
    /** 项目1结束点击事件 */
    inputCalNamea1(val) {

      this.$refs.selectPOP2.show( this.queryParams);

    },
    /** 项目2起始点击事件 */
    inputCalNameb(val) {
      this.queryParams.calTypeCode =''
      this.$refs.selectPOP3.show( this.queryParams);

    },
    /** 项目2结束点击事件 */
    inputCalNameb1(val) {
      this.$refs.selectPOP4.show( this.queryParams);
    },
    getCalTypePOP(val){
      this.$set( this.queryParams, 'calCodeaStart', val.calNo);
      this.$set( this.queryParams, 'calNameaStart', val.calNo);
    },
    getCalTypePOP2(val){
      this.$set( this.queryParams, 'calNameaEnd', val.calNo);
      this.$set( this.queryParams, 'calCodeaEnd', val.calNo);
    },
    getCalTypePOP3(val){
      this.$set( this.queryParams, 'calNamebStart', val.calNo);
      this.$set( this.queryParams, 'alCodebStart', val.calNo);
    },
    getCalTypePOP4(val){
       this.$set( this.queryParams, 'calNamebEnd', val.calNo);
      this.$set( this.queryParams, 'calCodebEnd', val.calNo);
    },
    getAcctCodeCorpPop(val){
      this.queryParams.acctNameStart = val[0].acctCode
      this.$set( this.queryParams, 'acctCodeStart', val[0].acctCode);
      this.$set( this.queryParams, 'groupAcctId', val[0].groupAcctId);
      this.$set( this.queryParams, 'acctNameStart', val[0].acctCode);
      this.$set( this.queryParams, 'acctIdStart', val[0].acctId);;
    },
    getAcctCodeCorpPop1(val){
      this.$set( this.queryParams, 'acctCodeEnd', val[0].acctCode);
      this.$set( this.queryParams, 'groupAcctId', val[0].groupAcctId);
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

    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
      });
    },
    getVoucherTypeList() {
      selectVoucherTypeList().then(response => {
        this.voucherTypeList = response;
      });
    },
    // 显示弹框
    show(queryParams) {
      if(queryParams){
        this.queryParams = queryParams;
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
