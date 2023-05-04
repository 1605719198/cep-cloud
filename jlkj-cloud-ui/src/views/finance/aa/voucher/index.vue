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
        <el-form-item label="" prop="voucherNo">
          <el-input
            v-model="queryParams.voucherNo"
            placeholder="请输入凭证号码"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button type="primary" size="mini" @click="headFirstQuery">首笔</el-button>
        <el-button type="primary" size="mini" @click="headFrontQuery">前笔</el-button>
        <el-button type="primary" size="mini" @click="headOrderQuery">次笔</el-button>
        <el-button type="primary" size="mini" @click="headLastQuery">末笔</el-button>
        <el-button type="primary" size="mini" @click="moreQuery">更多条件</el-button>
      </el-form-item>
      <el-row :gutter="10" class="mb8">
        <el-form-item label="凭证主档" prop="">
          <el-button type="primary" size="mini" @click="handleCreateBlankQuery">创建空白</el-button>
          <el-button type="primary" size="mini" @click="submitForm">保存</el-button>
          <el-button type="primary" size="mini" @click="handleDeleteForm">删除</el-button>
          <el-button type="primary" size="mini" @click="handleCopyQuery">复制</el-button>
          <el-button type="primary" size="mini" @click="handleHongChongQuery">红冲</el-button>
          <el-button type="primary" size="mini" @click="handleConfirmQuery">确认</el-button>
          <el-button type="primary" size="mini" @click="handleCancellationConfirmQuery">取消确认</el-button>
          <el-button type="primary" size="mini" @click="handleCrossQuery">过账</el-button>
          <el-button type="primary" size="mini" @click="handleRebellionQuery">反过账</el-button>
          <el-button type="primary" size="mini" @click="outsideQuery" v-if="outsideQueryIf">外币</el-button>
          <el-button type="primary" size="mini" @click="standardQuery" v-if="standardQueryIf">本位</el-button>
          <el-button type="primary" size="mini" @click="printQuery">打印</el-button>
          <el-button type="primary" size="mini" @click="cashFlowQuery">现金流量</el-button>
        </el-form-item>
        <el-form ref="form" :model="form" :rules="rules">
          <el-row>
            <el-col :span="5">
              <el-form-item label="凭证类型" prop="voucherType">
                <el-select v-model="form.voucherType" filterable placeholder="请输入凭证类型">
                  <el-option
                    v-for="item in voucherTypeList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="凭证日期" prop="voucherDate">
                <el-date-picker clearable
                                v-model="form.voucherDate"
                                type="date"
                                value-format="yyyy-MM-dd"
                                placeholder="请选择凭证日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="经办人姓名" prop="pastuserName">
                <el-input v-model="form.pastuserName" placeholder="请输入经办人姓名"/>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="附件张数" prop="srlnoCount">
                <el-input v-model="form.srlnoCount" placeholder="请输入凭单张数"/>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="业务单据编号" prop="billNo">
                <el-button size="mini" @click="billNoClick" type="text">{{ form.billNo }}</el-button>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="系统别" prop="apid">
                <dict-tag :options="dict.type.aa_source_sys" :value="form.apid"/>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="状态" prop="status">
                <dict-tag :options="dict.type.aa_voucher_status" :value="form.status"/>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="新增人姓名" prop="createBy">
                {{ form.createBy }}
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="新增人日期" prop="createDate">
                {{ form.createDate }}
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="借方金额" prop="drAmt">
                {{ form.drAmt }}
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="修改人" prop="updateBy">
                {{ form.updateBy }}
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="修改日期" prop="updateDate">
                {{ form.updateDate }}
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="出纳人员" prop="moneyuserName">
                {{ form.moneyuserName }}
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="确认人" prop="potstuserName">
                {{ form.potstuserName }}
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="贷方金额" prop="crAmt">
                {{ form.crAmt }}
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-row>
    </el-form>
    <el-form ref="formDetail" :model="formDetail"  :rules="rulesDetail">
      <el-form-item label="凭证分录" prop="">
        <el-table v-loading="loading" :data="formDetail.detailList"
                  :row-class-name="rowTCapitalDetailIndex"
                  @row-dblclick="rowClick"
                  @selection-change="handleSelectionChange">
          <el-form-item  >
          <el-table-column label="序号" header-align="center" align="center" type="index" width="55px"/>
          </el-form-item>
            <el-table-column label="摘要" align="center" prop="srlDesc">
            <template slot-scope="scope">
              <el-form-item
                :prop="'detailList.' + scope.$index + '.srlDesc'" :rules="rulesDetail.srlDesc">
              <el-input v-model="scope.row.srlDesc" placeholder="请输入摘要"/>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="会计科目" align="center" prop="acctName">
            <template slot-scope="scope">
              <el-form-item
                :prop="'detailList.' + scope.$index + '.acctName'" :rules="rulesDetail.acctName">
              <el-input v-model="scope.row.acctName" placeholder="请输入会计科目">
                <el-button slot="append" icon="el-icon-search" @click="inputAcctName"
                ></el-button>
              </el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="核算项目一名称" align="center" prop="calNamea">
            <template slot-scope="scope">
              <el-input v-model="scope.row.calNamea" placeholder="请输入核算项目一名称">
                <el-button slot="append" icon="el-icon-search" @click="inputCalNamea"
                ></el-button>
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="核算项目二名称" align="center" prop="calNameb">
            <template slot-scope="scope">
              <el-input v-model="scope.row.calNameb" placeholder="请输入核算项目二名称">
                <el-button slot="append" icon="el-icon-search" @click="inputCalNameb"
                ></el-button>
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="数量单位/币别" align="center" prop="crcyUnit" v-if="standardQueryIf">
            <template slot-scope="scope">
              <el-input v-model="scope.row.crcyUnit" placeholder="请输入数量单位/币别"/>
            </template>
          </el-table-column>
          <el-table-column label="数量/外币金额" align="center" prop="qtyFrnamt" v-if="standardQueryIf">
            <template slot-scope="scope">
              <el-input v-model="scope.row.qtyFrnamt" placeholder="请输入数量/外币金额"/>
            </template>
          </el-table-column>
          <el-table-column label="借方" align="center" prop="ntamtD">
            <template slot-scope="scope">
              <el-form-item
                :prop="'detailList.' + scope.$index + '.ntamtD'" :rules="rulesDetail.ntamtD">
              <el-input v-model="scope.row.ntamtD" placeholder="请输入借方":disabled="ntamtDDisabled"/>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="贷方" align="center" prop="ntamtC">
            <template slot-scope="scope">
              <el-form-item
                :prop="'detailList.' + scope.$index + '.ntamtC'" :rules="rulesDetail.ntamtC">
              <el-input v-model="scope.row.ntamtC" placeholder="请输入贷方" :disabled="ntamtCDisabled"/>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="核算项目三名称" align="center" prop="calNamec">
            <template slot-scope="scope">
              <el-input v-model="scope.row.calNamec" placeholder="请输入核算项目三名称">
                <el-button slot="append" icon="el-icon-search" @click="inputCalNamec"
                ></el-button>
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="核算项目四名称" align="center" prop="calNamed">
            <template slot-scope="scope">
              <el-input v-model="scope.row.calNamed" placeholder="请输入核算项目四名称">
                <el-button slot="append" icon="el-icon-search" @click="inputCalNamed"
                ></el-button>
              </el-input>
            </template>
          </el-table-column>


          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleAddDetailList(scope.row)"
                v-hasPermi="['aa:detail:edit']"
              >插入
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['aa:detail:remove']"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
    </el-form>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 更多条件查询弹窗 -->
    <selectVoucher ref="selectVoucher" @ok="getVoucherNo"/>
  </div>
</template>

<script>
import {
  listVoucher, getVoucher, delVoucher,
  addVoucher, updateVoucher, listHeadVoucher, listFrontVoucher, listOrderVoucher, listLastVoucher,
  updateVoucherStatus,updateVoucherCross} from "@/api/finance/aa/voucher";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {selectVoucherTypeList} from "@/api/finance/aa/voucherType";
import {listDetail} from "@/api/finance/aa/voucherdetail";
import selectVoucher from "@/views/finance/aa/voucher/selectVoucher";

export default {
  name: "Voucher",
  components: {
    selectVoucher
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
        companyId: null,
        voucherDate: null,
        voucherNo: null,
        printCount: null,
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
        voucherNo: [
          {required: true, message: '请输入凭证号', trigger: 'blur'}
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
    };
  },
  created() {
    this.getCompanyList();
    this.getVoucherTypeList();
    this.getList();
    this.getListDetailList()
  },
  methods: {
    moreQuery() {
      this.selectManufacturer = true
      this.$refs.selectVoucher.show();
    },
    getVoucherNo(val) {
      this.queryParams.companyId=val.companyId
      this.queryParams.voucherNo=val.voucherNo
      this.voucherType=val.voucherNo.substring(0,1)
      this.getList();
      this.getListDetailList()

    },
    /** 会计科目点击事件 */
    inputAcctName() {
      /*    this.selectManufacturer = true
          this.$refs.select.show();*/
    },
    /** 项目1点击事件 */
    inputCalNamea() {
      /*    this.selectManufacturer = true
          this.$refs.select.show();*/
    },
    /** 项目2点击事件 */
    inputCalNameb() {
      /*    this.selectManufacturer = true
          this.$refs.select.show();*/
    },
    /** 项目3点击事件 */
    inputCalNamec() {
      /*    this.selectManufacturer = true
          this.$refs.select.show();*/
    },
    /** 项目4点击事件 */
    inputCalNamed() {
      /*    this.selectManufacturer = true
          this.$refs.select.show();*/
    },
    /** 查询凭证维护-明细列表 */
    getListDetailList() {
      this.loading = true;
      if (this.queryParams.voucherNo == null||this.queryParams.voucherNo =='') {
         this.formDetail.detailList=[]
        let item = {
          index: null,
          id: null,
          companyId: null,
          voucherId: null,
          voucherDate: null,
          voucherNo: null,
          srlno: null,
          drcr: null,
          groupAcctId: null,
          acctId: null,
          acctCode: null,
          acctName: null,
          calIda: null,
          calCodea: null,
          calNamea: null,
          calIdb: null,
          calCodeb: null,
          calNameb: null,
          calIdc: null,
          calCodec: null,
          calNamec: null,
          calIdd: null,
          calCoded: null,
          calNamed: null,
          crcyUnit: null,
          qtyFrnamt: null,
          ntamtD:null,
          ntamtC:null,
          ntamt: null,
          srlDesc: null
        };
         this.formDetail.detailList.push(item)
      } else {
        listDetail(this.queryParams).then(response => {
           this.formDetail.detailList = response.rows;
          console.log(this.formDetail.detailList.length);
          if (this.formDetail.detailList.length==0){
            let item = {
              index: null,
              id: null,
              companyId: null,
              voucherId: null,
              voucherDate: null,
              voucherNo: null,
              srlno: null,
              drcr: null,
              groupAcctId: null,
              acctId: null,
              acctCode: null,
              acctName: null,
              calIda: null,
              calCodea: null,
              calNamea: null,
              calIdb: null,
              calCodeb: null,
              calNameb: null,
              calIdc: null,
              calCodec: null,
              calNamec: null,
              calIdd: null,
              calCoded: null,
              calNamed: null,
              crcyUnit: null,
              qtyFrnamt: null,
              ntamtD:null,
              ntamtC:null,
              ntamt: null,
              srlDesc: null
            };
            this.formDetail.detailList.push(item)
          }else {
            for (let t = 0;t<response.rows.length;t++){
              if (this.formDetail.detailList[t].drcr==='C'){
                this.formDetail.detailList[t].ntamtC=this.formDetail.detailList[t].ntamt
              }
              if (this.formDetail.detailList[t].drcr==='D'){
                this.formDetail.detailList[t].ntamtD=this.formDetail.detailList[t].ntamt
              }
            }
          }


          this.total = response.total;
          this.loading = false;
        });
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
    /** 查询凭证维护-主列表 */
    getList() {
      this.loading = true;
      this.outsideQueryIf=true
      listVoucher(this.queryParams).then(response => {
        this.form = response.data;
        if (!!this.form){
          if (!!this.voucherType){
            this.form.voucherType= this.voucherType
          }
          if (this.form.srlnoCount==null){
            this.form.srlnoCount=0
          }
          if(!!this.form.voucherNo ){
            this.form.voucherType =this.form.voucherNo.substring(0,1)
          }
        }else {
          this.reset()
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
        voucherDate: null,
        voucherNo: null,
        tradeNo: null,
        apid: null,
        pgrmid: null,
        voucherDesc: null,
        billNo: null,
        billUrl: null,
        printCount: null,
        srlnoCount: 0,
        voucherType:null,
        pastuserId: null,
        pastuserName: null,
        moneyuserId: null,
        moneyuserName: null,
        potstuserId: null,
        potstuserName: null,
        postDate: null,
        postTime: null,
        drAmt: null,
        crAmt: null,
        status: null,
        createBy: null,
        createDate: null,
        updateBy: null,
        updateDate: null,
        createTime: null,
        updateTime: null,
        createName: null,
        updateName: null
      };
      this.resetForm("form");
    },
    /** 首笔按钮操作 */
    headFirstQuery() {
      listDetail(this.queryParams).then(response => {
         this.formDetail.detailList = response.rows;
        this.total = response.total;
        this.loading = false;
        let list =  this.formDetail.detailList[0]
         this.formDetail.detailList = []
         this.formDetail.detailList.push(list)
      });
    },
    /** 前笔按钮操作 */
    headFrontQuery: function () {
      listFrontVoucher(this.queryParams).then(response => {
        this.form = response.data;
        if(this.form.voucherNo !=null){
          this.form.voucherType =this.form.voucherNo.substring(0,1)
        }
        if (this.form.voucherNo != null) {
          this.queryParams.voucherNo = this.form.voucherNo
          this.queryParams.companyId = this.form.companyId
          this.getListDetailList()
        }else {
          this.queryParams.voucherNo=''
          this.queryParams.companyId=''
          this.getListDetailList()
        }
        this.loading = false;
      });


    },
    /** 次笔按钮操作 */
    headOrderQuery() {
      listOrderVoucher(this.queryParams).then(response => {
        this.form = response.data;
        if(this.form.voucherNo !=null){
          this.form.voucherType =this.form.voucherNo.substring(0,1)
        }
        if (this.form.voucherNo != null) {
          this.queryParams.voucherNo = this.form.voucherNo
          this.queryParams.companyId = this.form.companyId
          this.getListDetailList()
        }else {
          this.queryParams.voucherNo=''
          this.queryParams.companyId=''
          this.getListDetailList()
        }

        this.loading = false;
      });
      this.getListDetailList()
    },
    /** 末笔按钮操作 */
    headLastQuery() {
      let list;
      listDetail(this.queryParams).then(response => {
         this.formDetail.detailList = response.rows;
        this.total = response.total;
        this.loading = false;
        for (let i = 0; i <  this.formDetail.detailList.length; i++) {
          list =  this.formDetail.detailList[i]
        }
         this.formDetail.detailList = []
         this.formDetail.detailList.push(list)
      });

    },
    handleCreateBlankQuery(){
      this.reset()
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        voucherDate: null,
        voucherNo: null,
        printCount: null,
      };
       this.formDetail.detailList = []
      let item = {
        index: null,
        id: null,
        companyId: null,
        voucherId: null,
        voucherDate: null,
        voucherNo: null,
        srlno: null,
        drcr: null,
        groupAcctId: null,
        acctId: null,
        acctCode: null,
        acctName: null,
        calIda: null,
        calCodea: null,
        calNamea: null,
        calIdb: null,
        calCodeb: null,
        calNameb: null,
        calIdc: null,
        calCodec: null,
        calNamec: null,
        calIdd: null,
        calCoded: null,
        calNamed: null,
        crcyUnit: null,
        qtyFrnamt: null,
        ntamtD:null,
        ntamtC:null,
        ntamt: null,
        srlDesc: null
      };
       this.formDetail.detailList.push(item)

    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.getList();
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
      /** 本位按钮  */
    standardQuery(){
        this.outsideQueryIf=true
        this.standardQueryIf=false
      },
    /** 外币按钮  */
    outsideQuery(){
      this.outsideQueryIf=false
      this.standardQueryIf=true
    },
    /** 单号跳转按钮操作 */
    billNoClick(){
      console.log(11111)
    },
    /** 现金流量按钮操作 */
    cashFlowQuery(){

    },
    /** 打印按钮操作 */
    printQuery(){

    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    rowClick(row, column, e) {

    },
    /** 序号 */
    rowTCapitalDetailIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    /** 插入 */
    handleAddDetailList(row) {
      let item = {
        index: row.index + 1,
        id: null,
        companyId: null,
        voucherId: null,
        voucherDate: null,
        voucherNo: null,
        srlno: null,
        drcr: null,
        groupAcctId: null,
        acctId: null,
        acctCode: null,
        acctName: null,
        calIda: null,
        calCodea: null,
        calNamea: null,
        calIdb: null,
        calCodeb: null,
        calNameb: null,
        calIdc: null,
        calCodec: null,
        calNamec: null,
        calIdd: null,
        calCoded: null,
        calNamed: null,
        crcyUnit: null,
        qtyFrnamt: null,
        ntamtD:null,
        ntamtC:null,
        ntamt: null,
        srlDesc: null
      };


       this.formDetail.detailList.splice(row.index, 0, item)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.checkedTCapitalDetail = selection.map(item => item.index)
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加凭证维护-主";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getVoucher(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改凭证维护-主";
      });
    },
    /** 确认按钮操作 */
    handleConfirmQuery(){
      this.form.detailList =  this.formDetail.detailList;

      if (this.form.status==='N'){
        this.form.status='Y'
        this.form.postTime=new Date()
        this.form.potstuserName = '1'
        updateVoucherStatus(this.form).then(response => {
          this.$modal.msgSuccess("确认成功");
          this.open = false;
          this.getList();
          this.getListDetailList()
        });
      }else {
        this.$message.error('当前状态不能确认');
      }
    },
    /** 过账按钮操作 */
    handleCrossQuery(){
      this.form.detailList =  this.formDetail.detailList;
      if (this.form.status==='N'){
        this.form.status='P'
        this.form.postTime = new Date()
        this.form.potstuserName = '1'
        updateVoucherCross(this.form).then(response => {
          this.$modal.msgSuccess("过账成功");
          this.open = false;
          this.getList();
          this.getListDetailList()
        });
      }else {
        this.$message.error('当前状态不能过账');
      }
    },
    /**反过账按钮操作 */
    handleRebellionQuery(){
      if (this.form.status==='P'){
        this.form.status='N'
        this.form.potstuserName = ''
        this.form.postTime=new Date(0,0,0)
        for (let i=0;i<  this.formDetail.detailList.length;i++){
          if (!! this.formDetail.detailList[i].ntamtC){
            this.formDetail.detailList[i].ntamt =  this.formDetail.detailList[i].ntamtC
            this.formDetail.detailList[i].drcr = "C"
          }else if (!! this.formDetail.detailList[i].ntamtD){
            this.formDetail.detailList[i].ntamt =  this.formDetail.detailList[i].ntamtD
            this.formDetail.detailList[i].drcr = "D"
          }
        }
        this.form.detailList =  this.formDetail.detailList;
        updateVoucherCross(this.form).then(response => {
          this.$modal.msgSuccess("反过账成功");
          this.open = false;
          this.getList();
          this.getListDetailList()
        });
      }else {
        this.$message.error('当前状态不能反过账');
      }
    },
    /** 取消确认按钮操作 */
    handleCancellationConfirmQuery(){
      this.form.detailList =  this.formDetail.detailList;
      if (this.form.status==='Y'){
        this.form.status='N'
        this.form.potstuserName = ''
        this.form.postTime=new Date(0,0,0)
        updateVoucherStatus(this.form).then(response => {
          this.$modal.msgSuccess("取消确认成功");
          this.open = false;
          this.getList();
          this.getListDetailList()
        });
      }else {
        this.$message.error('当前状态不能取消确认');
      }
    },
    /** 红冲按钮操作 */
    handleHongChongQuery(){
      if (!! this.form.voucherNo){
        this.form.voucherNo=""
        this.form.id=null
        this.form.companyId=this.queryParams.companyId
        for (let i=0;i<  this.formDetail.detailList.length;i++){
          this.formDetail.detailList[i].qtyFrnamt = 0 -this.formDetail.detailList[i].qtyFrnamt
          this.formDetail.detailList[i].ntamtD = 0 -this.formDetail.detailList[i].ntamtD
          this.formDetail.detailList[i].ntamtC = 0 -this.formDetail.detailList[i].ntamtC
          this.formDetail.detailList[i].srlDesc="红冲凭证号"+ this.formDetail.detailList[i].voucherNo
            if (!! this.formDetail.detailList[i].ntamtC){
            this.formDetail.detailList[i].ntamt =  this.formDetail.detailList[i].ntamtC
            this.formDetail.detailList[i].drcr = "C"
          }else if (!! this.formDetail.detailList[i].ntamtD){
            this.formDetail.detailList[i].ntamt =  this.formDetail.detailList[i].ntamtD
            this.formDetail.detailList[i].drcr = "D"
          }
        }
        this.form.detailList =  this.formDetail.detailList;
        addVoucher(this.form).then(response => {
          this.$modal.msgSuccess("红冲成功");
          this.open = false;
          this.getList();
          this.getListDetailList()
        });

      }else {
        this.$message.error('请查询数据');
      }
    },
    /** 复制删除按钮操作 */
    handleCopyQuery(){
      if (!! this.form.voucherNo){
        this.form.voucherNo=""
        this.form.id=null
        this.form.companyId=this.queryParams.companyId
        this.form.detailList =  this.formDetail.detailList;
        addVoucher(this.form).then(response => {
          this.$modal.msgSuccess("复制成功");
          this.open = false;
          this.getList();
          this.getListDetailList()
        });

      }else {
        this.$message.error('复制数据,不能都为空');
      }

    },
    /** 提交按钮 */
    submitForm() {
      if(!!this.queryParams.companyId){
        for (let i=0;i<  this.formDetail.detailList.length;i++){
        this.formDetail.detailList[i].companyId=this.queryParams.companyId
        if (!! this.formDetail.detailList[i].ntamtC && !! this.formDetail.detailList[i].ntamtD ){
          this.$message.error('借方金额/贷方金额,二者任选一个栏位输入');
          return
        }else if (!! this.formDetail.detailList[i].ntamtC){
          this.formDetail.detailList[i].ntamt =  this.formDetail.detailList[i].ntamtC
          this.formDetail.detailList[i].drcr = "C"
        }else if (!! this.formDetail.detailList[i].ntamtD){
          this.formDetail.detailList[i].ntamt =  this.formDetail.detailList[i].ntamtD
          this.formDetail.detailList[i].drcr = "D"
        }else {
          this.$message.error('借方金额/贷方金额,不能都为空');
          return
        }
      }
      this.$refs["formDetail"].validate(valid => {
        if (valid) {
          this.$refs["form"].validate(valid => {
            if (valid) {
              this.form.companyId=this.queryParams.companyId
              this.form.detailList =  this.formDetail.detailList;
              if (this.form.id != null) {
                updateVoucher(this.form).then(response => {
                  this.$modal.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                  this.getListDetailList()
                });
              } else {
                addVoucher(this.form).then(response => {
                  this.$modal.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                  this.getListDetailList()
                });
              }
            }
          });
        }
      });
      }else {
        this.$message.error('搜索公司不能为空');
      }


    },
    /** 主档删除按钮操作 */
    handleDeleteForm(){

      if (!!this.queryParams.voucherNo){
        if (this.form.status==='N'){
          const ids=this.queryParams.voucherNo
          this.$modal.confirm('是否确认删除凭证维护-主编号为"' + this.queryParams.voucherNo + '"的数据项？').then(() => {
            // delVoucher(this.form);
            this.form.detailList =this.formDetail.detailList;
            return delVoucher(this.form);
          }).then(() => {
            this.queryParams.voucherNo=''
            this.getList();
            this.getListDetailList();
            this.$modal.msgSuccess("删除成功");
          }).catch(() => {});
        }else {
          this.$message.error('当前状态不允许删除');
        }

      }else {
        this.$message.error('凭证号不能为空');
        return
      }

      },
    /** 删除按钮操作 */
    handleDelete(row) {
      if (row.id == null || row.id == '' || row.id == undefined) {
         this.formDetail.detailList.splice(row.index - 1, 1)
      } else {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除凭证维护-主编号为"' + ids + '"的数据项？').then(function () {
          return delVoucher(ids);
        }).then(() => {
          this.getList();
          this.getListDetailList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('aa/voucher/export', {
        ...this.queryParams
      }, `voucher_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
