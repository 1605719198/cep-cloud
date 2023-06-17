<template>
  <div class="app-container">
    <el-form :model="queryParams"
             :rules="rulesQuery" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="87px">
      <el-form-item label="科目余额">
        <el-form-item label="会计公司" prop="companyId">
          <el-select v-model="queryParams.companyId" filterable placeholder="请输入会计公司">
            <el-option
              v-for="item in companyList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="moreQuery">更多条件</el-button>
        <el-button type="primary" size="mini" @click="handleExport">导出</el-button>
      </el-form-item>
    </el-form>
    <el-form ref="formDetail" :model="formCalNumberDetail" :rules="rulesDetail" v-if="detailIf" :key="tableKey">
      <el-form-item label="科目余额表" prop="">
        <el-table v-loading="loading"
                  show-summary
                  :data="formCalNumberDetail.detailList"
                  :cell-style="{'text-align':'center'}"
               >
              <el-table-column label="会计科目" prop="acctCode" align="center" />
              <el-table-column  label="会计科目名称"  prop="acctName" align="center" />
              <el-table-column label="期初余额"  align="center">
                <el-table-column label="借方" prop="bgnAmtStraight"align="center"/>
                <el-table-column label="贷方" prop="bgnAmtBurden"align="center"/>
              </el-table-column>
              <el-table-column label="本期发生额" align="center">
                <el-table-column label="借方" prop="drAmt"align="center"/>
                <el-table-column label="贷方" prop="crAmt"align="center"/>
              </el-table-column>
              <el-table-column label="本年累计发生额" align="center">
                <el-table-column label="借方" prop="yearDrAmt"align="center"/>
                <el-table-column label="贷方" prop="yearCrAmt"align="center"/>
              </el-table-column>
              <el-table-column label="期末余额" align="center">
                <el-table-column label="借方" prop="endingBalanceDr"align="center"/>
                <el-table-column label="贷方" prop="endingBalanceCr"align="center"/>
              </el-table-column>
        </el-table>
      </el-form-item>
    </el-form>
    <el-form ref="formNumberDetail" :model="formCalNumberDetail" :rules="rulesNumberDetail"  v-if="numberDetailIf" :key="tableKey2">
      <el-form-item label="科目余额数量表" prop="">
        <el-table v-loading="loading"
                  show-summary
                  border
                  :data="formCalNumberDetail.detailList"
                 >
          <el-table-column label="会计科目" prop="acctCode" align="center" />
          <el-table-column  label="会计科目名称"  prop="acctName" align="center" />
          <el-table-column label="期初余额"  align="center">
            <el-table-column label="借方金额" prop="bgnAmtStraight"align="center"/>
            <el-table-column label="贷方金额" prop="bgnAmtBurden"align="center"/>
            <el-table-column label="借方数量" prop="bgnQtyStraight"align="center"/>
            <el-table-column label="贷方数量" prop="bgnQtyBurden"align="center"/>
          </el-table-column>
          <el-table-column label="本期发生额" align="center">
            <el-table-column label="借方金额" prop="drAmt"align="center"/>
            <el-table-column label="贷方金额" prop="crAmt"align="center"/>
            <el-table-column label="借方数量" prop="drQty"align="center"/>
            <el-table-column label="贷方数量" prop="crQty"align="center"/>
          </el-table-column>
          <el-table-column label="本年累计发生额" align="center">
            <el-table-column label="借方金额" prop="yearDrAmt"align="center"/>
            <el-table-column label="贷方金额" prop="yearCrAmt"align="center"/>
            <el-table-column label="借方数量" prop="yearDrQty"align="center"/>
            <el-table-column label="贷方数量" prop="yearCrQty"align="center"/>
          </el-table-column>
          <el-table-column label="期末余额" align="center">
            <el-table-column label="借方金额" prop="endingBalanceDr"align="center"/>
            <el-table-column label="贷方金额" prop="endingBalanceCr"align="center"/>
            <el-table-column label="借方数量" prop="endingBalanceQtyDr"align="center"/>
            <el-table-column label="贷方数量" prop="endingBalanceQtyCr"align="center"/>
          </el-table-column>
        </el-table>
      </el-form-item>
    </el-form>
    <el-form ref="formCalDetail" :model="formCalNumberDetail" :rules="rulesCalDetail"  v-if="calDetailIf":key="tableKey2">
      <el-form-item label="核算项目余额表" prop="">
        <el-table v-loading="loading"
                  show-summary
                  :data="formCalNumberDetail.detailList"
                  :cell-style="{'text-align':'center'}"
                >
          <el-table-column label="会计科目"prop="acctCode" align="center"/>
          <el-table-column  label="会计科目名称" prop="acctName" align="center" />
          <el-table-column  label="核算项目一" prop="calNamea" align="center" v-if="calNameaIf" />
          <el-table-column  label="核算项目二" prop="calNameb" align="center" v-if="calNamebIf" />
          <el-table-column  label="核算项目三" prop="calNamec" align="center"v-if="calNamecIf" />
          <el-table-column  label="核算项目四" prop="calNamed" align="center" v-if="calNamedIf" />
          <el-table-column label="初期余额"  align="center">
            <el-table-column label="借方" prop="bgnAmtStraight"align="center"/>
            <el-table-column label="贷方" prop="bgnAmtBurden"align="center"/>
          </el-table-column>
          <el-table-column label="本期发生额" align="center">
            <el-table-column label="借方" prop="drAmt"align="center"/>
            <el-table-column label="贷方" prop="crAmt"align="center"/>
          </el-table-column>
          <el-table-column label="本年累计发生额" align="center">
            <el-table-column label="借方" prop="yearDrAmt"align="center"/>
            <el-table-column label="贷方" prop="yearCrAmt"align="center"/>
          </el-table-column>
          <el-table-column label="期末余额" align="center">
            <el-table-column label="借方" prop="endingBalanceDr"align="center"/>
            <el-table-column label="贷方" prop="endingBalanceCr"align="center"/>
          </el-table-column>
        </el-table>
      </el-form-item>
    </el-form>
    <el-form ref="formCalDetail" :model="formCalNumberDetail" :rules="rulesCalNumberDetail"  v-if="calNumberDetailIf" :key="tableKey4">
      <el-form-item label="核算项目数量金额余额表" prop="">
        <el-table v-loading="loading"
                  show-summary
                  :data="formCalNumberDetail.detailList">
          <el-table-column label="会计科目"prop="acctCode" align="center" />
          <el-table-column  label="会计科目名称" prop="acctName" align="center" />
          <el-table-column  label="核算项目一" prop="calNamea" align="center" v-if="calNameaIf" />
          <el-table-column  label="核算项目二" prop="calNameb" align="center" v-if="calNamebIf" />
          <el-table-column  label="核算项目三" prop="calNamec" align="center" v-if="calNamecIf" />
          <el-table-column  label="核算项目四" prop="calNamed" align="center"v-if="calNamedIf" />
          <el-table-column label="期初余额"  align="center">
            <el-table-column label="借方金额" prop="bgnAmtStraight"align="center"/>
            <el-table-column label="贷方金额" prop="bgnAmtBurden"align="center"/>
            <el-table-column label="借方数量" prop="bgnQtyStraight"align="center"/>
            <el-table-column label="贷方数量" prop="bgnQtyBurden"align="center"/>
          </el-table-column>
          <el-table-column label="本期发生额" align="center">
            <el-table-column label="借方金额" prop="drAmt"align="center"/>
            <el-table-column label="贷方金额" prop="crAmt"align="center"/>
            <el-table-column label="借方数量" prop="drQty"align="center"/>
            <el-table-column label="贷方数量" prop="crQty"align="center"/>
          </el-table-column>
          <el-table-column label="本年累计发生额" align="center">
            <el-table-column label="借方金额" prop="yearDrAmt"align="center"/>
            <el-table-column label="贷方金额" prop="yearCrAmt"align="center"/>
            <el-table-column label="借方数量" prop="yearDrQty"align="center"/>
            <el-table-column label="贷方数量" prop="yearCrQty"align="center"/>
          </el-table-column>
          <el-table-column label="期末余额" align="center">
            <el-table-column label="借方金额" prop="endingBalanceDr"align="center"/>
            <el-table-column label="贷方金额" prop="endingBalanceCr"align="center"/>
            <el-table-column label="借方数量" prop="endingBalanceQtyDr"align="center"/>
            <el-table-column label="贷方数量" prop="endingBalanceQtyCr"align="center"/>
          </el-table-column>
        </el-table>
      </el-form-item>
    </el-form>
    <el-pagination
      background
      :total="total"
      :current-page="queryParams.pageNum"
      :page-sizes="[10, 20, 30, 50]"
      :page-size="queryParams.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeCodeChange"
      @current-change="handleCurrentCodeChange"
      style="float: right; position: relative;height: 25px; margin-bottom: 10px;margin-top: 15px; padding: 10px 20px !important;">
    </el-pagination>
<!--    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getListDetailList"
    />-->
    <!-- 更多条件查询弹窗 -->
    <selectMoreConditions ref="selectMoreConditions" @ok="getVoucherNo"/>

  </div>
</template>

<script>
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {selectVoucherTypeList} from "@/api/finance/aa/voucherType";
import selectMoreConditions from "@/views/finance/aa/accountBalanceTonSteel/selectMoreConditions";
import {listDetailIfSteel} from "@/api/finance/aa/accountBalanceTonSteel";
export default {
  name: "Voucher",
  components: {
    selectMoreConditions
  },
  dicts: ['aa_source_sys', 'aa_voucher_status'],
  data() {
    return {
      //本位按钮
      standardQueryIf: false,
      //外币按钮
      outsideQueryIf: false,
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
      // 凭证维护-主表格数据
      voucherList: [],
      // 凭证维护-凭证类型
      voucherTypeList: [],
      //凭证分录表格
      detailList: [],
      // 子表选中数据
      checkedTCapitalDetail: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 会计公司下拉选单
      companyList: [],
      // 表单参数
      form: {},
      // 表单参数
      formDetail: {
        detailList: [
        ]
      },
      formNumberDetail:{
        detailList: [
         ]
      },
      formCalDetail:{
        detailList: [
         ]
      },
      formCalNumberDetail:{
        detailList: [
         ]
      },
      // 表单校验
      rules: {
        voucherType: [
          {required: true, message: '请输入类别', trigger: 'blur'}
        ],
      },
      // 搜索框校验
      rulesQuery: {
        companyId: [
          {required: true, message: '请输入公司', trigger: 'blur'}
        ],

      },
      rulesDetail: {},
      rulesNumberDetail:{},
      rulesCalDetail:{},
      rulesCalNumberDetail:{},
      voucherType: null,
      ntamtDDisabled: false,
      ntamtCDisabled: false,
      indexRow: '',
      detailIf: false,
      numberDetailIf: false,
      calDetailIf: false,
      calNumberDetailIf: false,
      calNameaIf: false,
      calNamebIf: false,
      calNamecIf: false,
      calNamedIf: false,
      tableKey:0,
      tableKey2:1,
      tableKey3:3,
      tableKey4:4,
      numbertotal:0,
      totalVal:'10',
      fundsList1:[],
      fundsList2:[],
    };
  },
  created() {
    this.getCompanyList()
  },
  methods: {
    //分页方法
    handleSizeCodeChange(val) {
      this.totalVal = val
      this.getListDetailList()
    },
    handleCurrentCodeChange(val) {
      this.numbertotal=  val
      this.getListDetailList()
    },
    /** 查询凭证维护-明细列表 */
    getListDetailList() {
      listDetailIfSteel(this.queryParams).then(response => {
        this.formCalNumberDetail.detailList = response;
        this.fundsList1 =response;
        this.formCalNumberDetail.detailList = this.fundsList1.slice(0,this.totalVal)
        this.total =response.length;
        let number = Math.ceil(this.total / this.totalVal);
        if (this.numbertotal <= number && this.numbertotal !==0){
          if (this.numbertotal===1){
            this.formCalNumberDetail.detailList =  response.slice(0, this.totalVal)
          }else if (this.total>this.totalVal*this.numbertotal){
            this.fundsList1 = response;
            this.fundsList2 = this.fundsList1.slice(this.totalVal*(this.numbertotal-1), this.totalVal*this.numbertotal)
            this.formCalNumberDetail.detailList = this.fundsList2
            this.fundsList2=[]
          }else if ( number === this.numbertotal){
            this.fundsList2 = this.fundsList1.slice(this.totalVal*(this.numbertotal-1),this.total)
            this.formCalNumberDetail.detailList = this.fundsList2
            this.fundsList2=[]
          }
        }
      })
      this.loading=false
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
    handleExport() {
    },
    getVoucherNo(val) {
      this.numberDetailIf = false
      this.detailIf= false
      this.calDetailIf=false
      this.calNumberDetailIf= false
      this.calNameaIf= false
      this.calNamebIf= false
      this.calNamecIf= false
      this.calNamedIf= false
      this.queryParams=val
      this.loading = true;

      if(val.reportType=='1'&& val.isNoNumber=='N'){
        this.detailIf=true
        this.getListDetailList()
      }
      this.tableKey+=1;
      if(val.reportType=='1'&& val.isNoNumber=='Y'){
        this.numberDetailIf=true
        this.getListDetailList()

      }
      this.tableKey2+=1;
      if(val.reportType!='1'&& val.isNoNumber=='N'){
        this.calDetailIf=true
        this.getListDetailList()
      }
      this.tableKey3+=1;
      if(val.reportType!='1'&& val.isNoNumber=='Y'){
        this.calNumberDetailIf=true
     this.getListDetailList()
      }
      this.tableKey4+=1;
      if (val.reportType=='2'){
        this.calNameaIf= true
      }
      if (val.reportType=='3'){
        this.calNameaIf= true
        this.calNamebIf= true
      }
      if (val.reportType=='4'){
        this.calNameaIf= true
        this.calNamebIf= true
        this.calNamecIf= true
      }
      if (val.reportType=='5'){
        this.calNameaIf= true
        this.calNamebIf= true
        this.calNamecIf= true
        this.calNamedIf= true
      }
      this.tableKey2+=1;
    },
    moreQuery() {
      this.$refs.selectMoreConditions.show(this.queryParams);
    },

  }
};
</script>
