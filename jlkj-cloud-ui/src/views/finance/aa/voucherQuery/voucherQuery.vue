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
              <el-button slot="append" icon="el-icon-search" @click="inputAcctName"
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
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSelectCoder">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
    <calTypePOP ref="selectPOP" @pop="getCalTypePOP"/>
    <calTypePOP ref="selectPOP2" @pop="getCalTypePOP2"/>
    <calTypePOP ref="selectPOP3" @pop="getCalTypePOP3"/>
    <calTypePOP ref="selectPOP4" @pop="getCalTypePOP4"/>
  </el-dialog>
</template>

<script>
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {selectVoucherTypeList} from "@/api/finance/aa/voucherType";
import {listVoucherDetailSelect} from "@/api/finance/aa/voucher";
import calTypePOP from "@/views/components/finance/calTypePOP";
export default {
  name: "voucherQuery",
  dicts: ['aa_source_sys', 'aa_voucher_status'],
  components: {
    calTypePOP
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
        qtyFrnamtEnd:null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getCompanyList();
    this.getVoucherTypeList();

  },
  methods: {
    /** 项目1起始点击事件 */
    inputCalNamea(val) {
      this.$refs.selectPOP.show( this.queryParams);

    },
    /** 项目1结束点击事件 */
    inputCalNamea1(val) {

      this.$refs.selectPOP2.show( this.queryParams);

    },
    /** 项目2起始点击事件 */
    inputCalNameb(val) {
      this.$refs.selectPOP3.show( this.queryParams);
    },
    /** 项目2结束点击事件 */
    inputCalNameb1(val) {
      this.$refs.selectPOP4.show( this.queryParams);
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
    /** 项目2起始回调*/
    getCalTypePOP3(val){
      this.$set( this.queryParams, 'calNamebStart', val.calNo);
      this.$set( this.queryParams, 'alCodebStart', val.calNo);
    },
    /** 项目2截止回调*/
    getCalTypePOP4(val){
      this.$set( this.queryParams, 'calNamebEnd', val.calNo);
      this.$set( this.queryParams, 'calCodebEnd', val.calNo);
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
      this.$emit("ok",this.queryParams);
      this.visible = false;
    },
    /** 会计科目点击事件 */
    inputAcctName(){

      /*    this.selectManufacturer = true
          this.$refs.select.show();*/
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
