<template>
  <div class="app-container">
    <el-form :model="queryParams"
             :rules="rulesQuery" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="87px">
      <el-form-item label="明细账查询">
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
    <el-form ref="formDetail" :model="formDetail" :rules="rulesDetail" :key="tableKey">
      <el-form-item prop="列表" label="列表" >
      <el-table
        border
        v-for="(item,index) in formDetail.detailList"
        :key="index"
        :data="item.list"
        :cell-style="{'text-align':'center'}">
        <el-table-column :label="'会计科目：'+item.acctCode1">
          <el-table-column label="核算项目一" prop="calNamea" align="center"v-if="calNameaIf"/>
          <el-table-column label="核算项目二" prop="calNameb" align="center" v-if="calNamebIf"/>
        <el-table-column  label="核算项目三" prop="calNamec" align="center" v-if="calNamecIf"/>
        <el-table-column  label="核算项目四" prop="calNamed" align="center" v-if="calNamedIf" />
        <el-table-column  label="凭证日期" prop="voucherDate" align="center"  >
        </el-table-column>
        </el-table-column>
        <el-table-column :label="'期初数量/外币：'+item.bgnQty">
        <el-table-column label="凭证号" align="center" prop="voucherNo" :show-overflow-tooltip='true'>
          <template slot-scope="scope">
            <el-button size="mini" @click="voucherNoClick(scope.row)" type="text">{{ scope.row.voucherNo }}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="系统别" align="center" prop="apid" >
          <template slot-scope="scope">
            <dict-tag :options="dict.type.aa_source_sys" :value="scope.row.apid"/>
          </template>

        </el-table-column>
        <el-table-column label="摘要" align="center" prop="srlDesc" />
        <el-table-column label="单位/币别" align="center" prop="crcyUnit"/>
        <el-table-column label="借方数量/外币金额" align="center" prop="qtyFrnamtD" v-if="qtyFrnamtIf"/>
        <el-table-column label="借方金额" align="center" prop="ntamtD"/>
        <el-table-column label="贷方数量/外币金额" align="center" prop="qtyFrnamtC" v-if="qtyFrnamtIf"/>
        </el-table-column>
        <el-table-column :label="'期初余额：'+item.bgnAmt">
        <el-table-column label="贷方金额" align="center" prop="ntamtC"/>
          <el-table-column label="借贷方向" align="center" prop="drcr">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.aa_drcr" :value="scope.row.drcr"/>
            </template>
          </el-table-column>
        <el-table-column label="余额数量/外币金额" align="center" prop="qtyFrnamtBalance" v-if="qtyFrnamtIf"/>
        <el-table-column label="余额" align="center" prop="balance"/>
        </el-table-column>
      </el-table>
      </el-form-item>
    </el-form>
    <el-pagination

      background
      :total="total"
      :current-page="queryParams.pageNum"
      :page-sizes="pageSizesList"
      :page-size="queryParams.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      style="float: right; position: relative;height: 25px; margin-bottom: 10px;margin-top: 15px; padding: 10px 20px !important;">
    </el-pagination>

    <!-- 更多条件查询弹窗 -->
    <selectMoreConditions ref="selectMoreConditions" @ok="getVoucherNo"/>

  </div>
</template>

<script>
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {selectVoucherTypeList} from "@/api/finance/aa/voucherType";
import selectMoreConditions from "@/views/finance/aa/detailSteel/selectMoreConditions";
import {listDetailSteelSteel} from "@/api/finance/aa/detailSteel";

export default {
  name: "Voucher",
  components: {
    selectMoreConditions
  },
  dicts: ['aa_source_sys', 'aa_voucher_status','aa_drcr'],
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
      //分页数组
      pageSizesList:[],
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
        pageSize: 1,
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
      totalVal:'1',
      fundsList1:[],
      fundsList2:[],
      tableKey:0,
      qtyFrnamtIf:false
    };
  },
  created() {
    this.getCompanyList()
  },
  methods: {
    voucherNoClick(row) {
      this.$router.push({
        name: 'Voucher', query: {
          voucherNo: row.voucherNo,
          companyId: row.companyId
        }
      });
    },
    //分页方法
    handleSizeChange(val) {
      this.totalVal = val
      this.getListDetailList()
    },
    handleCurrentChange(val) {
      this.numbertotal = val
      this.getListDetailList()
    },

    getListDetailList(){
      listDetailSteelSteel(this.queryParams).then(response => {
        console.log(response);
        this.pageSizesList=[1]
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
      this.calNameaIf=false
      this.calNamebIf=false
      this.calNamecIf=false
      this.calNamedIf=false
      this.qtyFrnamtIf = false
      console.log(val.accountingItems);
      if (val.accountingItems =='1'){
        this.calNameaIf=true
      }
      if (val.accountingItems =='2'){
        this.calNameaIf=true
        this.calNamebIf=true
      }
      if (val.accountingItems =='3'){
        this.calNameaIf=true
        this.calNamebIf=true
        this.calNamecIf=true
      }
      if (val.accountingItems =='4'){
        this.calNameaIf=true
        this.calNamebIf=true
        this.calNamecIf=true
        this.calNamedIf=true
      }
      if (val.isNoNumber =='Y'){
        this.qtyFrnamtIf=true
      }
        this.queryParams = val
      this.getListDetailList();
      this.tableKey+=1;

    },
    moreQuery() {
      this.$refs.selectMoreConditions.show(this.queryParams);
    },
  }
};
</script>
