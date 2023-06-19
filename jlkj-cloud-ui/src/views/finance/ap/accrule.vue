<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司" prop="companyId">
        <el-select v-model="queryParams.companyId" placeholder="请选择">
          <el-option
            v-for="item in companyList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="付款方式" prop="payMethod">
        <el-input
          v-model="queryParams.payMethod"
          placeholder="请输入付款方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="付款名称" prop="payMethodName">
        <el-input
          v-model="queryParams.payMethodName"
          placeholder="请输入付款名称"
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
          v-hasPermi="['finance:accrule:add']"
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
          v-hasPermi="['finance:accrule:edit']"
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
          v-hasPermi="['finance:accrule:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="accruleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="付款方式" align="center" prop="payMethod" />
      <el-table-column label="付款名称" align="center" prop="payMethodName" />
      <el-table-column label="会计科目" align="center" prop="acctName" />
      <el-table-column label="核算项目一类别" align="center" prop="calTypeCodea" />
      <el-table-column label="核算项目二类别" align="center" prop="calTypeCodeb" />
      <el-table-column label="是否生成应付单" align="center" prop="isGen" />
      <el-table-column label="凭证类型" align="center" prop="voucherType" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:accrule:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:accrule:remove']"
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

    <!-- 添加或修改付款抛账规则维护对话框 -->
    <el-dialog :title="title" :visible.sync="open" :key="key" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司" prop="companyId">
              <el-select v-model="form.companyId" placeholder="请选择">
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
            <el-form-item label="付款方式" prop="payMethod">
              <el-input v-model="form.payMethod" placeholder="请输入付款方式" class="input"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="付款名称" prop="payMethodName">
              <el-input v-model="form.payMethodName" placeholder="请输入付款名称" class="input"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否生成应付单" prop="isGen">
              <el-radio v-model="form.isGen" label="1">是</el-radio>
              <el-radio v-model="form.isGen" label="2">否</el-radio>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="凭证类型" prop="voucherType">
              <el-select v-model="form.voucherType" filterable placeholder="请输入凭证类型">
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
          <el-col :span="12">
            <el-form-item label="会计科目" prop="acctName">
              <el-input v-model="form.acctName" placeholder="请输入会计科目" class="input">
                <el-button slot="append" icon="el-icon-search" @click="inputAcctName"
                ></el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="核算项目一类别" prop="calTypeCodea">
              <el-input v-model="form.calTypeCodea" placeholder="请输入核算项目一类别" class="input"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="核算项目二类别" prop="calTypeCodeb">
              <el-input v-model="form.calTypeCodeb" placeholder="请输入核算项目二类别" class="input"/>
            </el-form-item>
          </el-col>
        </el-row>

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
    </el-dialog>
  </div>
</template>

<script>
import { listAccrule, getAccrule, delAccrule, addAccrule, updateAccrule } from "@/api/finance/ap/accrule";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {selectVoucherTypeList} from "@/api/finance/aa/voucherType";
import acctcodeCorpPop from "@/views/finance/aa/acctcodeCorpPop";


export default {
  name: "Accrule",
  components: {
    acctcodeCorpPop
  },
  data() {
    return {
      // 会计公司下拉选单
      companyList:[],
      // 凭证维护-凭证类型
      voucherTypeList: [],
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
      // 付款抛账规则维护表格数据
      accruleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        payMethod: null,
        payMethodName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companyId: [
          { required: true, message: "公司不能为空", trigger: "blur" }
        ],
        payMethod: [
          { required: true, message: "付款方式不能为空", trigger: "blur" },
          {max: 2 ,trigger: 'blur',message: "最大不超过2个字符"}
        ],
        payMethodName: [
          { required: true, message: "付款名称不能为空", trigger: "blur" },
          {max: 100 ,trigger: 'blur',message: "最大不超过100个字符"}
        ],
        voucherType: [
          { required: true, message: "凭证类型不能为空", trigger: "blur" }
        ],
        acctId: [
          { required: true, message: "会计科目不能为空", trigger: "blur" }
        ],
      },
      key: 0
    };
  },
  created() {
    this.getVoucherTypeList();
    this.getCompanyList();
    this.getList();
  },
  methods: {
    /** 查询付款抛账规则维护列表 */
    getList() {
      this.loading = true;
      listAccrule(this.queryParams).then(response => {
        this.accruleList = response.rows;
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
    getAcctCodeCorpPop(val){
      this.form.acctId= val[0].acctId
      this.form.acctName= val[0].acctName
      this.form.calTypeCodea = val[0].calTypeCodea;
      this.form.calTypeCodeb = val[0].calTypeCodeb;
      this.key = Math.random()
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
        payMethod: null,
        payMethodName: null,
        voucherType: null,
        isGen: null,
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
      this.title = "新增付款抛账规则";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAccrule(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改付款抛账规则";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAccrule(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAccrule(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除付款抛账规则维护编号为"' + ids + '"的数据项？').then(function() {
        return delAccrule(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>

<style scoped>
.input {
  width: 83%;
}
</style>
