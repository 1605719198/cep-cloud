<template>
  <div>
    <el-table v-loading="loading" :data="expenseBillsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="发票类型" align="center" prop="billType" show-overflow-tooltip>
        <template v-slot="scope">
          <dict-tag :options="dict.type.ao_bill_type" :value="scope.row.billType"/>
        </template>
      </el-table-column>
      <el-table-column label="收款方" align="center" prop="saleName" />
      <el-table-column label="发票代码" align="center" prop="billNo" />
      <el-table-column label="发票号码" align="center" prop="billName" />
      <el-table-column label="含税总额" align="center" prop="totalAmt" />
      <el-table-column label="开票日期" align="center" prop="billDate" width="180"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="openSelectPop(scope.row.id)"
            v-hasPermi="['ao:expenseBills:query']"
          >详情</el-button>
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

    <!-- 添加或修改我的发票对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="发票介质" prop="billMedium">
              <el-select v-model="form.billMedium" placeholder="请选择发票介质" style="width:240px">
                <el-option
                  v-for="dict in dict.type.ao_bill_medium"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发票代码" prop="billNo">
              <el-input v-model="form.billNo" placeholder="请输入发票代码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="发票号码" prop="billName">
              <el-input v-model="form.billName" placeholder="请输入发票号码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发票类型" prop="billType">
              <el-select v-model="form.billType" placeholder="请选择发票类型" style="width:240px">
                <el-option
                  v-for="dict in dict.type.ao_bill_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="购方名称" prop="purchaseName">
              <el-input v-model="form.purchaseName" placeholder="请输入购方名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="购方纳税人识别号" prop="purchaseTaxid">
              <el-input v-model="form.purchaseTaxid" placeholder="请输入购方纳税人识别号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="购方地址电话" prop="purchaseAddress">
              <el-input v-model="form.purchaseAddress" placeholder="请输入购方地址电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="购方银行账号" prop="purchaseBank">
              <el-input v-model="form.purchaseBank" placeholder="请输入购方银行账号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="销方名称" prop="saleName">
              <el-input v-model="form.saleName" placeholder="请输入销方名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销方纳税人识别号" prop="saleTaxid">
              <el-input v-model="form.saleTaxid" placeholder="请输入销方纳税人识别号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="销方地址电话" prop="saleAddress">
              <el-input v-model="form.saleAddress" placeholder="请输入销方地址电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销方银行账号" prop="saleBank">
              <el-input v-model="form.saleBank" placeholder="请输入销方银行账号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="合计金额" prop="saleAmt">
              <el-input v-model="form.saleAmt" placeholder="请输入合计金额" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合计税额" prop="taxAmt">
              <el-input v-model="form.taxAmt" placeholder="请输入合计税额" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="价税合计" prop="totalAmt">
              <el-input v-model="form.totalAmt" placeholder="请输入价税合计" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开票类型" prop="plusOrMinus">
              <el-select v-model="form.plusOrMinus" placeholder="请选择开票类型" style="width:240px">
                <el-option
                  v-for="dict in dict.type.ao_plus_minus"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开票人" prop="billUser">
              <el-input v-model="form.billUser" placeholder="请输入开票人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开票日期" prop="billDate">
              <el-date-picker clearable
                              v-model="form.billDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择开票日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="币别" prop="crcyCode">
              <el-input v-model="form.crcyCode" placeholder="请输入币别" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="关联单号" prop="linkNo">
              <el-input v-model="form.linkNo" placeholder="请输入关联单号" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <select-bills ref="selectBills"></select-bills>
  </div>
</template>

<script>
import { listBills, getBills, delBills, addBills, updateBills } from "@/api/finance/ap/bills";
import { selectCompanyList } from "@/api/finance/aa/companyGroup";
import  selectBills  from "@/views/finance/ao/expenseBills/selectBills";
export default {
  name: "ExpenseBills",
  dicts: ['ao_bill_type','ao_bill_medium','ao_plus_minus'],
  components: { selectBills },
  data() {
    // 额度验证，保存小数点后两位
    const validateAmt = (rule, value, callback) => {
      const pattern = /^[0-9-]+(\.[0-9]{1,2})?$/
      if (value) {
        if (!pattern.test(value)) {
          callback(new Error("请输入小数点≤2位的数据"))
        }
      }
      if (value > 100000000) {
        callback(new Error("数据限额最大值为1亿，请联系管理员修改！"))
      }
      callback()
    }
    return {
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
      // 是否显示弹出层
      open: false,
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
      rules: {
        billName: [
          { required: true, message: "发票号码不能为空", trigger: "blur" }
        ],
        saleAmt: [
          {required: true,message: "请输入合计金额", trigger: "blur"},
          { validator: validateAmt, trigger: 'blur' }
        ],
      }
    };
  },
  created() {
    this.getCompanyList();
    this.getList();
  },
  methods: {
    /** 初始化方法 */
    init(obj) {
      // 根据传递过来的值进行查询
      this.queryParams = obj
      this.getList()
    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
      });
    },
    /** 打开集团报支类别弹窗 */
    openSelectPop(id) {
      this.$refs.selectBills.show(id);
    },
    /** 查询我的发票列表 */
    getList() {
      this.loading = true;
      this.queryParams.status = '1';
      listBills(this.queryParams).then(response => {
        this.expenseBillsList = response.rows;
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
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.form.companyId = this.queryParams.companyId
      this.title = "添加我的发票";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBills(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改我的发票";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBills(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBills(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('确认要删除吗？').then(function() {
        return delBills(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/ap/bills/export', {
        ...this.queryParams
      }, `bills_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
