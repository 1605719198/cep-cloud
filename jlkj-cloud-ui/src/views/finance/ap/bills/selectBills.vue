<template>
  <!-- 发票详情对话框 -->
  <el-dialog title="发票详情" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form ref="form" :model="form" :rules="rules" label-width="140px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="发票介质">
            <dict-tag :options="dict.type.ao_bill_medium" :value="form.billMedium"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发票代码" >{{ form.billNo }}</el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="发票号码" >
            {{ form.billName }}
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发票类型" >
            <dict-tag :options="dict.type.ao_bill_type" :value="form.billType"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="购方名称">
            {{ form.purchaseName }}
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="购方纳税人识别号">
            {{ form.purchaseTaxid }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="购方地址电话" >
            {{ form.purchaseAddress }}
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="购方银行账号" >
            {{ form.purchaseBank }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="销方名称">
            {{ form.saleName }}
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="销方纳税人识别号">
            {{ form.saleTaxid }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="销方地址电话" >
            {{ form.saleAddress }}
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="销方银行账号" >
            {{ form.saleBank }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="合计金额" >
            {{ form.saleAmt }}
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="合计税额" >
            {{ form.taxAmt }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="价税合计">
            {{ form.totalAmt }}
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="开票类型" >
            <dict-tag :options="dict.type.ao_plus_minus" :value="form.plusOrMinus"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="开票人" >
            {{ form.billUser }}
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="开票日期">
            {{ form.billDate }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="币别" >
            {{ form.crcyCode }}
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="关联单号">
            {{ form.linkNo }}
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer" align="center">
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listBills, getBills } from "@/api/finance/ap/bills";
;
import { selectCompanyList } from "@/api/finance/aa/companyGroup";
export default {
  dicts: ['ao_bill_type','ao_bill_medium','ao_plus_minus'],
  data() {
    return {
      // 遮罩层
      visible: false,
      // 会计公司下拉选单
      companyList: [],
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
      // 我的发票表格数据
      expenseBillsList: [],
      // 弹出层标题
      title: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: 'J00',
        billDate: null,
        saleName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getCompanyList();
    this.getList();
  },
  methods: {
    // 显示弹框
    show(id) {
      this.reset();
      getBills(id).then(response => {
        this.form = response.data;
        this.visible = true;
      });
    },

    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
      });
    },
    /** 查询我的发票列表 */
    getList() {
      this.loading = true;
      listBills(this.queryParams).then(response => {
        this.expenseBillsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.visible = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        companyId: null,
        crcyCode: null,
        billMedium: null,
        billNo: null,
        billName: null,
        billType: null,
        purchaseName: null,
        purchaseTaxid: null,
        purchaseAddress: null,
        purchaseBank: null,
        saleName: null,
        saleTaxid: null,
        saleAddress: null,
        saleBank: null,
        saleAmt: null,
        taxAmt: null,
        totalAmt: null,
        plusOrMinus: null,
        billUser: null,
        billDate: null,
        status: null,
        linkNo: null,
        createBy: null,
        createName: null,
        createTime: null
      };
      this.resetForm("form");
    },

    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

  }
};
</script>
