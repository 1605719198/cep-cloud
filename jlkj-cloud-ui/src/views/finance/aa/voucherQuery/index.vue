<template>
  <div class="app-container">
    <el-form :model="queryParams"
             :rules="rulesQuery" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="87px">
      <el-form-item label="凭证维护">
        <el-form-item label="会计公司" prop="companyId">
          <el-select v-model="queryParams.companyId" filterable placeholder="请输入会计公司">
            <el-option
              v-for="item in companyList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              @keyup.enter.native="handleQuery">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="凭证日期" prop="voucherDate">
          <el-date-picker
            v-model="queryParams.voucherDate"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @change="dutyDateChange">
          </el-date-picker>

        </el-form-item>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button type="primary" size="mini" @click="moreQuery">更多条件</el-button>
        <el-button type="primary" size="mini" @click="handleExport">导出</el-button>
        <el-button type="primary" size="mini" @click="printQuery">批量打印</el-button>
        <el-button type="primary" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-form ref="formDetail" :model="formDetail"  :rules="rulesDetail">
      <el-form-item label="凭证分录" prop="">
        <el-table v-loading="loading" :data="formDetail.detailList"
                  @row-dblclick="rowClick"
                  @selection-change="handleSelectionChange">

          <el-table-column label="凭证号" align="center" prop="voucherNo"   :show-overflow-tooltip='true'>
            <template slot-scope="scope">
              <el-button size="mini" @click="voucherNoClick(scope.row)" type="text">{{scope.row.voucherNo}}</el-button>
            </template>
          </el-table-column>
          <el-table-column label="凭证日期" align="center" prop="voucherDate"
          />
          <el-table-column label="系统别" align="center" prop="apid" >
            <template slot-scope="scope">
              <dict-tag :options="dict.type.aa_source_sys" :value="scope.row.apid"/>
            </template>
          </el-table-column>
          <el-table-column label="摘要" align="center" prop="srlDesc"  :show-overflow-tooltip='true'/>
          <el-table-column label="科目代码" align="center" prop="acctCode"/>
          <el-table-column label="科目名称" align="center" prop="acctName"  :show-overflow-tooltip='true'/>
          <el-table-column label="单位/币别" align="center" prop="crcyUnit"/>
          <el-table-column label="原币金额" align="center" prop="qtyFrnamt"/>
          <el-table-column label="借方金额" align="center" prop="drAmt"/>
          <el-table-column label="贷方金额" align="center" prop="crAmt"/>
          <el-table-column label="制单人" align="center" prop="pastuserName"/>
          <el-table-column label="经办人" align="center" prop="pastuserName"/>
          <el-table-column label="状态" align="center" prop="status" >
            <template slot-scope="scope">
              <dict-tag :options="dict.type.aa_voucher_status" :value="scope.row.status"/>
            </template>
          </el-table-column>
            <el-table-column label="附件数" align="center" prop="srlnoCount" />
          <el-table-column label="打印次数" align="center" prop="printCount" />
        </el-table>
      </el-form-item>
    </el-form>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getListDetailList"
    />
    <!-- 更多条件查询弹窗 -->
    <voucherQuery ref="selectVoucher" @ok="getVoucherNo"/>

  </div>
</template>

<script>
import {listHeadVoucher, } from "@/api/finance/aa/voucher";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {selectVoucherTypeList} from "@/api/finance/aa/voucherType";
import {listDetail} from "@/api/finance/aa/voucherdetail";
import {listVoucherDetailSelect} from "@/api/finance/aa/voucher";
import voucherQuery from "@/views/finance/aa/voucherQuery/voucherQuery";

export default {
  name: "Voucher",
  components: {
    voucherQuery
  },
  dicts: ['aa_source_sys', 'aa_voucher_status'],
  data() {
    return {
      //本位按钮
      standardQueryIf:false,
      //外币按钮
      outsideQueryIf:false,
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
        companyId: "",
        voucherDate: "",
        voucherNo: "",
        printCount: "",
        startDate: "",
        endDate: "",
        voucherType:"",
        status:"",
        apid:"",
        acctName:"",
        createBy:"",
        calNamea:"",
        calNameb:"",
        pastuserName:"",
        srlDesc:"",
        qtyFrnamt:"",
        voucherNoStart:"",
        voucherNoEnd:"",
        acctNameStart:"",
        acctNameEnd:"",
        calNameaStart:"",
        calNameaEnd:"",
        calNamebStart:"",
        calNamebEnd:"",
        qtyFrnamtStart:"",
        qtyFrnamtEnd:""
      },
      // 会计公司下拉选单
      companyList: [],
      // 表单参数
      form: {

      },
      // 表单参数
      formDetail: {
        detailList:[]
      },
      // 表单校验
      rules: {
        voucherType: [
          {required: true, message: '请输入类别', trigger: 'blur'}
        ],
        voucherDate: [
          {required: true, message: '请输入日期', trigger: 'blur'}
        ],
        pastuserName: [
          {required: true, message: '请输入经办人', trigger: 'blur'}
        ],
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
      rulesDetail: {
        srlDesc: [
          {required: true, message: '请输入摘要', trigger: 'blur'}
        ],
        acctName: [
          {required: true, message: '请输入会计科目', trigger: 'blur'}
        ],

      },
      voucherType:null,
      ntamtDDisabled:false,
      ntamtCDisabled:false,
      indexRow:''
    };
  },
  created() {
    this.getCompanyList();
    this.getVoucherTypeList();
    this.getListDetailList()
  },
  methods: {
    voucherNoClick(row){
      this.$router.push({ name: 'Voucher' ,query:{ voucherNo: row.voucherNo,
          companyId:  row.companyId}});
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
    headOrderQuery(){},

    moreQuery() {
      this.selectManufacturer = true
      this.$refs.selectVoucher.show(this.queryParams);
    },
    /** 更多条件弹窗*/
    getVoucherNo(val) {
      console.log(val.status);
      this.queryParams=val
      this.getListDetailList()
    },
    /** 查询凭证维护-明细列表 */
    getListDetailList() {
      this.loading = true;
      listVoucherDetailSelect(this.queryParams).then(response => {
          this.formDetail.detailList = response.rows;
            for (let t = 0;t<response.rows.length;t++){
              if (this.formDetail.detailList[t].drcr==='C'){
                this.formDetail.detailList[t].ntamtC=this.formDetail.detailList[t].ntamt
              }
              if (this.formDetail.detailList[t].drcr==='D'){
                this.formDetail.detailList[t].ntamtD=this.formDetail.detailList[t].ntamt
              }
            }
          this.total = response.total;
          this.loading = false;
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

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.$refs["queryForm"].validate(valid => {
        if (valid) {

          this.getListDetailList()
        }
      });

    },
    headQuery() {
      this.queryParams.pageNum = 1;
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          listHeadVoucher(this.queryParams).then(response => {

          });
        }
      });

    },
    /** 打印按钮操作 */
    printQuery(){
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams={
        pageNum: 1,
        pageSize: 10,
        companyId: "",
        voucherDate: "",
        voucherNo: "",
        printCount: "",
        startDate: "",
        endDate: "",
        voucherType:"",
        status:"",
        apid:"",
        acctName:"",
        createBy:"",
        calNamea:"",
        calNameb:"",
        pastuserName:"",
        srlDesc:"",
        qtyFrnamt:"",
        voucherNoStart:"",
        voucherNoEnd:"",
        acctNameStart:"",
        acctNameEnd:"",
        calNameaStart:"",
        calNameaEnd:"",
        calNamebStart:"",
        calNamebEnd:"",
        qtyFrnamtStart:"",
        qtyFrnamtEnd:""
      },
      this.getListDetailList()

    },
    rowClick(row, column, e) {
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.checkedTCapitalDetail = selection.map(item => item.index)
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/voucher/export', {
        ...this.queryParams
      }, `voucher_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
