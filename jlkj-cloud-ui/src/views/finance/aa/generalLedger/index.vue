<template>
  <div class="app-container">
    <el-form :model="queryParams"
             :rules="rulesQuery" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="87px">
      <el-form-item label="期初导入">
        <el-form-item label="会计公司" prop="companyId">
          <el-select v-model="queryParams.companyId" filterable placeholder="请输入会计公司">
            <el-option
              v-for="item in companyList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="moreQuery">更多条件</el-button>
        <el-button type="primary" size="mini" @click="handleExport">导出</el-button>
      </el-form-item>
    </el-form>

    <!--    v-if="detailIf"-->
    <el-form ref="formDetail" :model="formDetail" :rules="rulesDetail" v-if="detailIf" :key="tableKey">

        <el-table v-loading="loading"
                  v-for="(item,index) in formDetail.detailList"
                  :key="index"
                  :data="item.list"
                  :cell-style="{'text-align':'center'}">
          <el-table-column :label="'会计科目为'+item.acctCode1">
          <el-table-column label="会计周期" prop="acctPeriod" align="center" />
          <el-table-column label="核算项目" prop="calName" align="center" v-if="calNameIf"/>
          <el-table-column label="期初金额" prop="bgnAmt" align="center" v-if="bgnAmtIf"/>
          <el-table-column label="期初" align="center" v-if="bgnQtyIf" >
            <el-table-column label="数量" prop="bgnQty" align="center" />
            <el-table-column label="金额" prop="bgnAmt" align="center" />
          </el-table-column>
          <el-table-column label="借方金额" prop="drAmt" align="center" v-if="bgnAmtIf" />
          <el-table-column label="借方" align="center" v-if="bgnQtyIf" >
            <el-table-column label="数量" prop="drQty" align="center" />
            <el-table-column label="金额" prop="drAmt" align="center"/>
          </el-table-column>
          <el-table-column label="贷方金额" prop="crAmt" align="center" v-if="bgnAmtIf" />
          <el-table-column label="贷方" align="center" v-if="bgnQtyIf" >
            <el-table-column label="数量" prop="crQty" align="center" />
            <el-table-column label="金额" prop="crAmt" align="center" />
          </el-table-column>
          <el-table-column label="期末金额" prop="endOfPeriodAmt" align="center" v-if="bgnAmtIf" />
          <el-table-column label="期末" align="center" v-if="bgnQtyIf" >
            <el-table-column label="数量" prop="endOfPeriodQty" align="center" />
            <el-table-column label="金额" prop="endOfPeriodAmt" align="center" />
          </el-table-column>
          </el-table-column>
        </el-table>
    </el-form>
    <!--    v-if="numberDetailIf"-->
    <el-form ref="formNumberDetail" :model="formNumberDetail" :rules="rulesNumberDetail" v-if="numberDetailIf" :key="tableKey2">
      <el-form-item label="核算项目为主" prop="">
        <el-table
                  border
                  v-for="(item,index) in formNumberDetail.detailList"
                  :key="index"
                  :data="item.list"
        >
          <el-table-column :label="'核算项目为'+item.acctCode1">
          <el-table-column label="会计周期" prop="acctPeriod" align="center"/>
          <el-table-column label="会计科目" prop="acctName" align="center" />
          <el-table-column label="核算项目" prop="calName" align="center" v-if="calNameIf02" />
          <el-table-column label="期初金额" prop="bgnAmt" align="center" v-if="bgnAmtIf02" />
          <el-table-column label="期初" align="center" v-if="bgnQtyIf02" >
            <el-table-column label="数量" prop="bgnQty" align="center"/>
            <el-table-column label="金额" prop="bgnAmt" align="center" />
          </el-table-column>
          <el-table-column label="借方金额" prop="drAmt" align="center" v-if="bgnAmtIf02" />
          <el-table-column label="借方" align="center" v-if="bgnQtyIf02" >
            <el-table-column label="数量" prop="drQty" align="center" />
            <el-table-column label="金额" prop="drAmt" align="center"/>
          </el-table-column>
          <el-table-column label="贷方金额" prop="crAmt" align="center" v-if="bgnAmtIf02" />
          <el-table-column label="贷方" align="center" v-if="bgnQtyIf02" >
            <el-table-column label="数量" prop="crQty" align="center"/>
            <el-table-column label="金额" prop="crAmt" align="center"/>
          </el-table-column>
          <el-table-column label="期末金额" prop="endOfPeriodAmt" align="center" v-if="bgnAmtIf02" />
          <el-table-column label="期末" align="center" v-if="bgnQtyIf02" >
            <el-table-column label="数量" prop="endOfPeriodQty" align="center" />
            <el-table-column label="金额" prop="endOfPeriodAmt" align="center"/>
          </el-table-column>
          </el-table-column>
        </el-table>
      </el-form-item>
    </el-form>
      <el-pagination
        v-if="detailIf"
    background
    :total="total"
    :current-page="queryParams.pageNum"
    :page-sizes="[10, 20, 30, 50]"
    :page-size="queryParams.pageSize"
    layout="total, sizes, prev, pager, next, jumper"
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
    style="float: right; position: relative;height: 25px; margin-bottom: 10px;margin-top: 15px; padding: 10px 20px !important;">
  </el-pagination>
    <el-pagination
      v-if="numberDetailIf"
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
    <!-- 更多条件查询弹窗 -->
    <selectMoreConditions ref="selectMoreConditions" @ok="getVoucherNo"/>

  </div>
</template>

<script>
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {selectVoucherTypeList} from "@/api/finance/aa/voucherType";
import selectMoreConditions from "@/views/finance/aa/generalLedger/selectMoreConditions";
import {listCalNumberDetailIfSteel, listDetailIfSteel} from "@/api/finance/aa/generalLedger";

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
        detailList: []
      },
      formNumberDetail: {
        detailList: []
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
      rulesNumberDetail: {},
      rulesCalDetail: {},
      rulesCalNumberDetail: {},
      voucherType: null,
      ntamtDDisabled: false,
      ntamtCDisabled: false,
      indexRow: '',
      detailIf: false,
      calNameIf: false,
      bgnAmtIf: false,
      bgnQtyIf: false,
      numberDetailIf: false,
      calNameIf02: false,
      bgnAmtIf02: false,
      bgnQtyIf02: false,
      numberDetailIf02: false,

      calDetailIf: false,
      calNumberDetailIf: false,
      calNameaIf: false,
      calNamebIf: false,
      calNamecIf: false,
      calNamedIf: false,

      numbertotal:0,
      totalVal:'10',
      fundsList1:[],
      fundsList2:[],
      tableKey:0,
      tableKey2:1
    };
  },
  created() {
    this.getCompanyList()
  },
  methods: {
    //分页方法
    handleSizeChange(val) {
      this.totalVal = val
      this.getListDetailList()
    },
    handleCurrentChange(val) {
      this.numbertotal = val
      this.getListDetailList()
    },
    //分页方法
    handleSizeCodeChange(val) {
      this.totalVal = val
      this.getList()
    },
    handleCurrentCodeChange(val) {
      this.numbertotal=  val
      this.getList()
    },
    getList(){
      listCalNumberDetailIfSteel(this.queryParams).then(response => {
        this.formNumberDetail.detailList=response
        this.fundsList1 =response;
        this.formNumberDetail.detailList = this.fundsList1.slice(0,this.totalVal)
        this.total =response.length;
        let number = Math.ceil(this.total / this.totalVal);
        if (this.numbertotal <= number && this.numbertotal !==0){
          if (this.numbertotal===1){
            this.formNumberDetail.detailList =  response.slice(0, this.totalVal)
          }else if (this.total>this.totalVal*this.numbertotal){
            this.fundsList1 = response;
            this.fundsList2 = this.fundsList1.slice(this.totalVal*(this.numbertotal-1), this.totalVal*this.numbertotal)
            this.formNumberDetail.detailList = this.fundsList2
            this.fundsList2=[]
          }else if ( number === this.numbertotal){
            this.fundsList2 = this.fundsList1.slice(this.totalVal*(this.numbertotal-1),this.total)
            this.formNumberDetail.detailList = this.fundsList2
            this.fundsList2=[]
          }
        }
      })
    },
    /** 查询凭证维护-明细列表 */
    getListDetailList() {
      this.loading = false;
      listDetailIfSteel(this.queryParams).then(response => {
          this.formDetail.detailList=response
        this.fundsList1 =response;
        this.formDetail.detailList = this.fundsList1.slice(0,this.totalVal)
        this.total =response.length;
        let number = Math.ceil(this.total / this.totalVal);
        if (this.numbertotal <= number && this.numbertotal !==0){
          if (this.numbertotal===1){
            this.formDetail.detailList =  response.slice(0, this.totalVal)
          }else if (this.total>this.totalVal*this.numbertotal){
            this.fundsList1 = response;
            this.fundsList2 = this.fundsList1.slice(this.totalVal*(this.numbertotal-1), this.totalVal*this.numbertotal)
            this.formDetail.detailList = this.fundsList2
            this.fundsList2=[]
          }else if ( number === this.numbertotal){
            this.fundsList2 = this.fundsList1.slice(this.totalVal*(this.numbertotal-1),this.total)
            this.formDetail.detailList = this.fundsList2
            this.fundsList2=[]
          }
        }
      });
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
      this.detailIf = false
      this.calNameIf = false
      this.bgnAmtIf = false
      this.bgnQtyIf = false
      this.numberDetailIf = false
      this.calNameIf02=false,
      this.bgnAmtIf02= false,
        this.bgnQtyIf02=false,
      this.queryParams = val
      if ( val.accountingItems =='Y'){
        this.calNameIf = true
      }  if (  val.isNoNumber =='Y'){
        this.bgnQtyIf = true

      }  if ( val.isNoNumber =='N') {
        this.bgnAmtIf = true


      }
      if (val.mainItems =='01'){
        this.detailIf = true

        listDetailIfSteel(this.queryParams).then(response => {
          this.formDetail.detailList=response
          this.fundsList1 =response;
          this.formDetail.detailList = this.fundsList1.slice(0,this.totalVal)
          this.total =response.length;
          let number = Math.ceil(this.total / this.totalVal);
          if (this.numbertotal <= number && this.numbertotal !==0){
            if (this.numbertotal===1){
              this.formDetail.detailList =  response.slice(0, this.totalVal)
            }else if (this.total>this.totalVal*this.numbertotal){
              this.fundsList1 = response;
              this.fundsList2 = this.fundsList1.slice(this.totalVal*(this.numbertotal-1), this.totalVal*this.numbertotal)
              this.formDetail.detailList = this.fundsList2
              this.fundsList2=[]
            }else if ( number === this.numbertotal){
              this.fundsList2 = this.fundsList1.slice(this.totalVal*(this.numbertotal-1),this.total)
              this.formDetail.detailList = this.fundsList2
              this.fundsList2=[]
            }
          }
          this.loading = false;
        })
      }
       if (  val.accountingItems =='Y'){
        this.calNameIf02 = true
      }
     else if (  val.isNoNumber =='Y'){
        this.bgnQtyIf02 = true
      }
      if (  val.isNoNumber =='N') {
        this.bgnAmtIf02 = true
      }
      this.tableKey+=1;
      if (val.mainItems =='02'){
        this.numberDetailIf = true
        listCalNumberDetailIfSteel(this.queryParams).then(response => {
          this.formNumberDetail.detailList=response
          this.fundsList1 =response;
          this.formNumberDetail.detailList = this.fundsList1.slice(0,this.totalVal)
          this.total =response.length;
          let number = Math.ceil(this.total / this.totalVal);
          if (this.numbertotal <= number && this.numbertotal !==0){
            if (this.numbertotal===1){
              this.formNumberDetail.detailList =  response.slice(0, this.totalVal)
            }else if (this.total>this.totalVal*this.numbertotal){
              this.fundsList1 = response;
              this.fundsList2 = this.fundsList1.slice(this.totalVal*(this.numbertotal-1), this.totalVal*this.numbertotal)
              this.formNumberDetail.detailList = this.fundsList2
              this.fundsList2=[]
            }else if ( number === this.numbertotal){
              this.fundsList2 = this.fundsList1.slice(this.totalVal*(this.numbertotal-1),this.total)
              this.formNumberDetail.detailList = this.fundsList2
              this.fundsList2=[]
            }
          }
        })
      }
      this.tableKey2+=1;
    },
    moreQuery() {
      this.$refs.selectMoreConditions.show(this.queryParams);
    },

  }
};
</script>
