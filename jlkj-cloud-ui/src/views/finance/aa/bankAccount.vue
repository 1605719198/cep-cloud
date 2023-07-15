<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择">
          <el-option
            v-for="item in companyList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="银行代码" prop="bankId">
        <el-input
          v-model="queryParams.bankId"
          placeholder="请输入银行代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="银行名称" prop="bankName">
        <el-input
          v-model="queryParams.bankName"
          placeholder="请输入银行名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['finance:account:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['finance:account:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['finance:account:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="accountList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="银行代码" align="center" prop="bankId" />
      <el-table-column label="银行名称" align="center" prop="bankName" />
      <el-table-column label="账户类别" align="center" prop="acctType" />
      <el-table-column label="账号" align="center" prop="remitAcctno" />
      <el-table-column label="账户名称" align="center" prop="acctTypeName" />
      <el-table-column label="状态" align="center" prop="status">
        <template v-slot="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:account:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:account:remove']"
          >删除</el-button>
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

    <!-- 添加或修改往来银行对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司" prop="compId">
              <el-select v-model="form.compId" placeholder="请选择">
                <el-option
                  v-for="item in companyList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="银行代码" prop="bankId">
              <el-input v-model="form.bankId" placeholder="请输入银行代码" class="input"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="金融机构" prop="branchNo">
              <el-input v-model="form.branchNo"  class="input">
                <el-button slot="append" icon="el-icon-search" @click="inputBankName"
                ></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账户类别" prop="acctType">
              <el-select v-model="form.acctType" placeholder="请选择" class="input">
                <el-option
                  v-for="dict in dict.type.aa_bank_account_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="银行名称" prop="bankName">
          <el-input v-model="form.bankName" placeholder="请输入银行名称" class="input1"/>
        </el-form-item>

        <el-form-item label="银行账号" prop="remitAcctno">
          <el-input v-model="form.remitAcctno" placeholder="请输入银行名称" class="input2"/>
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="会计科目" prop="acctCode">
              <el-input v-model="form.acctCode" class="input">
                <el-button slot="append" icon="el-icon-search" @click="inputAcctName"
                ></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="核算项目一类别" prop="calTypeCodea">
              <el-input v-model="form.calTypeCodea" class="input"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="核算项目二类别" prop="calTypeCodeb">
              <el-input v-model="form.calTypeCodeb" class="input"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联络电话" prop="phoneNo">
              <el-input v-model="form.phoneNo" placeholder="请输入联络电话" class="input"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="联络地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入联络地址" class="input1"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" class="input1"/>
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="新增人" prop="createName">
              {{form.createName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="新增日期" prop="createTime">
              {{ parseTime(form.createTime, '{y}-{m}-{d}') }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="异动人" prop="updateName">
              {{form.updateName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="异动日期" prop="updateTime">
              {{ parseTime(form.updateTime, '{y}-{m}-{d}') }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
      <acctcodeCorpPop ref="selectAcctCodeCorpPop" @ok="getAcctCodeCorpPop"/>
      <bankPop ref="selectBankPop" @ok="getBankPop"/>
    </el-dialog>
  </div>
</template>

<script>
import {
  listAccount,
  getAccount,
  delAccount,
  addAccount,
  updateAccount,
  changeBankAccount
} from "@/api/finance/aa/bankAccount";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import acctcodeCorpPop from "@/views/finance/aa/acctcodeCorpPop";
import bankPop from "@/views/finance/aa/bank/bankPop";

export default {
  name: "BankAccount",
  dicts: ['aa_bank_account_type'],
  components: {
    acctcodeCorpPop,bankPop
  },
  data() {
    return {
      // 会计公司下拉选单
      companyList:[],
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
      // 往来银行表格数据
      accountList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        bankId: null,
        bankName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        compId: [
          { required: true, message: "公司不能为空", trigger: "blur" },
        ],
        branchNo: [
          { required: true, message: "金融机构不能为空", trigger: "blur" },
        ],
        acctType: [
          { required: true, message: "账户类别不能为空", trigger: "blur" },
        ],
        bankName: [
          { required: true, message: "银行名称不能为空", trigger: "blur" },
          {max: 100 ,trigger: 'blur',message: "最大不超过100个字符"}
        ],
      }
    };
  },
  created() {
    this.getCompanyList();
    this.getList();
  },
  methods: {
    /** 查询往来银行列表 */
    getList() {
      this.loading = true;
      listAccount(this.queryParams).then(response => {
        this.accountList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
      });
    },
    getAcctCodeCorpPop(val){
      this.form.acctId= val[0].acctId
      this.form.acctCode= val[0].acctName
      this.form.calTypeCodea = val[0].calTypeCodea;
      this.form.calTypeCodeb = val[0].calTypeCodeb;
    },
    getBankPop(val){
      this.form.branchNo= val[0].bankchnName
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
        compId: null,
        bankId: null,
        bankName: null,
        branchNo: null,
        acctType: null,
        acctTypeName: null,
        remitAcctno: null,
        acctId: null,
        acctCode: null,
        calTypeIda: null,
        calTypeCodea: null,
        calTypeIdb: null,
        calTypeCodeb: null,
        calTypeIdc: null,
        calTypeCodec: null,
        calTypeIdd: null,
        calTypeCoded: null,
        phoneNo: null,
        address: null,
        status: null,
        remark: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateName: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 会计科目点击事件 */
    inputAcctName() {
      this.$refs.selectAcctCodeCorpPop.show();
    },
    /** 会计科目点击事件 */
    inputBankName() {
      this.$refs.selectBankPop.show();
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
      this.title = "添加往来银行";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAccount(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改往来银行";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAccount(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAccount(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除往来银行编号为"' + ids + '"的数据项？').then(function() {
        return delAccount(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.bankName + '"用户吗？').then(function () {
        return changeBankAccount(row.id, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function () {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
  }
};
</script>

<style scoped>
.input {
  width: 83%;
}
.input1 {
  width: 93%;
}
.input2 {
  width: 50%;
}
</style>
