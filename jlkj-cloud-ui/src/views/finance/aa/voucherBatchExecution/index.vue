<template>
  <div class="app-container">
    <el-form :model="queryParams"
             :rules="rulesQuery" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="87px">
      <el-form-item label="批量执行">
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
        <el-button type="primary" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="primary" size="mini" @click="moreQuery">更多条件</el-button>
      </el-form-item>
    </el-form>

    <el-form ref="formDetail" :model="formDetail">
      <el-form-item label="凭证清单" prop="">
      </el-form-item>
      <el-form-item>
        <el-row :gutter="10" class="mb8">
          <el-button type="primary" size="mini" @click="handleConfirmQuery">确认</el-button>
          <el-button type="primary" size="mini" @click="handleCancellationConfirmQuery">取消确认</el-button>
          <el-button type="primary" size="mini" @click="handleCrossQuery">过账</el-button>
          <el-button type="primary" size="mini" @click="handleRebellionQuery">反过账</el-button>
          <el-button type="primary" size="mini" @click="handleDeleteForm">删除</el-button>
        </el-row>

        <el-table v-loading="loading" :data="formDetail.detailList"
                  :row-class-name="rowTCapitalDetailIndex"

                  @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="凭证号" align="center" prop="voucherNo" :show-overflow-tooltip='true'>
            <template slot-scope="scope">
              <el-button size="mini" @click="voucherNoClick(scope.row)" type="text">{{ scope.row.voucherNo }}
              </el-button>
            </template>
          </el-table-column>
          <el-table-column label="凭证日期" align="center" prop="voucherDate"
          />
          <el-table-column label="系统别" align="center" prop="apid">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.aa_source_sys" :value="scope.row.apid"/>
            </template>
          </el-table-column>
          <el-table-column label="摘要" align="center" prop="voucherDesc" :show-overflow-tooltip='true'/>
          <el-table-column label="借方金额" align="center" prop="drAmt"/>
          <el-table-column label="贷方金额" align="center" prop="crAmt"/>
          <el-table-column label="制单人" align="center" prop="pastuserName"/>
          <el-table-column label="经办人" align="center" prop="pastuserName"/>
          <el-table-column label="状态" align="center" prop="status">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.aa_voucher_status" :value="scope.row.status"/>
            </template>
          </el-table-column>
          <el-table-column label="附件数" align="center" prop="srlnoCount"/>
          <el-table-column label="打印次数" align="center" prop="printCount"/>

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
import {delVoucher, listHeadVoucher, updateVoucherStatus, updateVoucherCross,listVoucherBatchExecution
} from "@/api/finance/aa/voucherBatchExecution";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {selectVoucherTypeList} from "@/api/finance/aa/voucherType";

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
        companyId: null,
        voucherDate: null,
        voucherNo: null,
        printCount: null,
        calTypeCode: '',
        startDate: "",
        endDate: "",
      },
      // 会计公司下拉选单
      companyList: [],
      // 表单参数
      form: {},
      // 表单参数
      formDetail: {
        detailList: []
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
      voucherType: null,
      ntamtDDisabled: false,
      ntamtCDisabled: false,
      indexRow: '',
      calTypeCodea: '',
      calTypeCodeb: '',
      calTypeCodec: '',
      calTypeCoded: ''
    };
  },
  created() {
    this.init();
  },
  watch: {
    $route() {
      this.init();
    }
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
    //查询日期切换事件
    dutyDateChange(val) {
      if (val != null) {
        this.queryParams.startDate = val[0]
        this.queryParams.endDate = val[1]
      } else {
        this.queryParams.startDate = ''
        this.queryParams.endDate = ''
      }
    },
    init() {
      this.queryParams.voucherNo = this.$route.query.voucherNo
      this.queryParams.companyId = this.$route.query.companyId
      this.getCompanyList();
      this.getVoucherTypeList();
      this.getListDetailList()
    },
    moreQuery() {
      this.selectManufacturer = true
      this.$refs.selectVoucher.show(this.queryParams);
    },
    getVoucherNo(val) {
      this.queryParams.companyId = val.companyId
      this.queryParams.voucherNo = val.voucherNo
      this.voucherType = val.voucherNo.substring(0, 1)
      this.getListDetailList()
    },

    /** 查询凭证维护-明细列表 */
    getListDetailList() {
      this.loading = true;
      listVoucherBatchExecution(this.queryParams).then(response => {
        this.formDetail.detailList = response.rows;
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
        voucherType: null,
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
    /** 序号 */
    rowTCapitalDetailIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.checkedTCapitalDetail = selection.map(item => item.index)
      this.detailList = selection

      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 确认按钮操作 */
    handleConfirmQuery() {
      this.form.detailList =this.detailList;

      if (this.form.detailList.length==0){this.$message.error('至少选择一笔数据确认');return}
        this.form.status = 'Y'
        this.form.postTime = new Date()
        this.form.potstuserName = '1'

      updateVoucherStatus(this.form).then(response => {
          this.$modal.msgSuccess("确认成功");
          this.open = false;
          this.getListDetailList()
        });
    },
    /** 取消确认按钮操作 */
    handleCancellationConfirmQuery() {
      this.form.detailList =this.detailList;
      if (this.form.detailList.length==0){this.$message.error('至少选择一笔数据取消确认');return}
      this.form.status = 'N'
      this.form.potstuserName = ''
      this.form.postTime = new Date(0, 0, 0)
      updateVoucherStatus(this.form).then(response => {
        this.$modal.msgSuccess("取消确认成功");
        this.open = false;
        this.getListDetailList()
      });
    },
    /** 过账按钮操作 */
    handleCrossQuery() {
      this.form.detailList =this.detailList;
      if (this.form.detailList.length==0){
        this.$message.error('至少选择一笔数据过账');
        return
      }
      for(let i =0; i<this.form.detailList.length;i++){
        if (this.form.detailList[i].status != 'Y'){
          this.$message.error('第'+i+1+'笔状态不能过账');
          return;
        }else {
          this.form.detailList[i].status = 'P'
        }
      }
        this.form.postTime = new Date()
        this.form.potstuserName = '1'
      this.form.status = 'P'
        updateVoucherCross(this.form).then(response => {
          this.$modal.msgSuccess("过账成功");
          this.open = false;
          this.getListDetailList()
        });
    },
    /**反过账按钮操作 */
    handleRebellionQuery() {
      this.form.detailList =this.detailList;
        for (let i = 0; i <this.detailList.length; i++) {
          if (this.form.detailList[i].status != 'P'){
            this.$message.error('第'+i+1+'笔状态不能反过账');
            return;
          }else {
            this.form.detailList[i].status = 'N'
          }
        }
      this.form.potstuserName = ''
      this.form.postTime = new Date(0, 0, 0)
      this.form.status = 'N'
        this.form.detailList =this.detailList;
        if (this.form.detailList.length==0){
          this.$message.error('至少选择一笔数据反过账');
          return
        }

        updateVoucherCross(this.form).then(response => {
          this.$modal.msgSuccess("反过账成功");
          this.open = false;
          this.getListDetailList()
        });
    },

    /** 主档删除按钮操作 */
    handleDeleteForm() {
      this.form.detailList =this.detailList;
      for (let i = 0; i <this.detailList.length; i++) {
        if ( this.form.detailList[i].status === 'N') {
          const ids = this.form.detailList[i].voucherNo
          this.$modal.confirm('是否确认删除凭证维护-主编号为"' + this.form.detailList[i].voucherNo + '"的数据项？').then(() => {
            return delVoucher(this.form);
          }).then(() => {
            this.queryParams.voucherNo = ''
            this.getListDetailList();
            this.$modal.msgSuccess("删除成功");
          }).catch(() => {
          });
        } else {
          this.$message.error('凭证号'+this.form.detailList[i].voucherNo+'状态为不允许删除');
          return
        }
      }
    },
  }
};
</script>
