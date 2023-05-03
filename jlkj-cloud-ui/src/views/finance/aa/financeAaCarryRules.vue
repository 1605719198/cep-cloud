<template>
  <div>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="结转代码" prop="rulesNo">
        <el-input
          v-model="queryParams.rulesNo"
          placeholder="请输入结转代码"
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
          v-hasPermi="['finance:financeAaCarryRules:add']"
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
          v-hasPermi="['finance:financeAaCarryRules:edit']"
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
          v-hasPermi="['finance:financeAaCarryRules:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['finance:financeAaCarryRules:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="financeAaCarryRulesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="转出会计科目" align="center" prop="acctNameOut" />
      <el-table-column label="结转方式" align="center" prop="carryType" />
      <el-table-column label="转入会计科目" align="center" prop="acctNameIn" />
      <el-table-column label="结转方向" align="center" prop="drcr" />
      <el-table-column label="凭证摘要" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:financeAaCarryRules:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:financeAaCarryRules:remove']"
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

    <!-- 添加或修改集团管理-自动结转规则-规则设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="结转代码" prop="rulesNo">
              <el-input v-model="form.rulesNo" placeholder="请输入结转代码" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结转名称" align="left">
              {{parRulesName}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="转出会计科目" prop="accountCodeOut">
              <el-input v-model="form.acctNameOut" placeholder="请选择转出会计科目" :disabled="true">
                <el-button slot="append" icon="el-icon-search" @click="accountCodeClick('codeOutClick')"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结转方式" prop="carryType">
              <el-select v-model="form.carryType" placeholder="请选择结转方式" style="width: 240px">
                <el-option
                  v-for="dict in dict.type.aa_carry_type"
                  :key="dict.value"
                  :label="dict.value+'_'+dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="转入会计科目" prop="accountCodeIn">
              <el-input v-model="form.acctNameIn" placeholder="请选择转入会计科目" :disabled="true">
                <el-button slot="append" icon="el-icon-search" @click="accountCodeClick('codeInClick')"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
              <el-form-item label="结转方向" prop="drcr" align="left">
                <el-radio-group v-model="form.drcr">
                  <el-radio v-for="dict in dict.type.aa_drcr"
                            :key="dict.value"
                            :label="dict.value">{{dict.label}}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="凭证摘要" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入凭证摘要" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="新增人" prop="createName" align="left">
              {{form.createName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="新增时间" prop="createTime" align="left">
              {{form.createTime}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="异动人" prop="updateName" align="left">
              {{form.updateName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="异动时间" prop="updateTime" align="left">
              {{form.updateTime}}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <acctcode-group-pop ref="acctcodeGroupPop" @ok="getAccountCodeData"></acctcode-group-pop>
  </div>
</template>

<script>
import { listFinanceAaCarryRules, getFinanceAaCarryRules, delFinanceAaCarryRules, addFinanceAaCarryRules, updateFinanceAaCarryRules } from "@/api/finance/aa/financeAaCarryRules";
import acctcodeGroupPop from "@/views/finance/aa//acctcodeGroupPop";

export default {
  name: "FinanceAaCarryRules",
  dicts: ['aa_carry_type','aa_drcr'],
  components: {acctcodeGroupPop},
  data() {
    return {
      // 父组件传值
      parRulesNo: null,
      parRulesName: null,
      // 会计科目弹窗来源
      tagSrc: null,
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
      // 集团管理-自动结转规则-规则设置表格数据
      financeAaCarryRulesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rulesNo: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        rulesNo: [
          { required: true, message: "结转代码不能为空", trigger: "blur" }
        ],
        accountCodeOut: [
          { required: true, message: "转出会计科目不能为空", trigger: "blur" }
        ],
        carryType: [
          { required: true, message: "结转方式不能为空", trigger: "blur" }
        ],
        accountCodeIn: [
          { required: true, message: "转入会计科不能为空", trigger: "blur" }
        ],
        drcr: [
          { required: true, message: "结转方式不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 初始化方法 */
    init(obj) {
      // 根据传递过来的值进行查询
      this.queryParams.rulesNo = obj.rulesNo
      this.parRulesNo = obj.rulesNo
      this.parRulesName = obj.rulesName
      this.getList()
    },
    /** 获取转出会计科目 */
    getAccountCodeData(val) {
      if (this.tagSrc === 'codeOutClick') {
        this.form.accountIdOut = val[0].groupAcctId
        this.form.accountCodeOut = val[0].groupAcctCode
        this.form.acctNameOut = val[0].groupAcctName
      }else if(this.tagSrc === 'codeInClick') {
        this.form.accountIdIn = val[0].groupAcctId
        this.form.accountCodeIn = val[0].groupAcctCode
        this.form.acctNameIn = val[0].groupAcctName
      }
    },
    /** 会计科目点击事件 */
    accountCodeClick(val) {
      this.tagSrc = val
      this.isSingleProject = true
      this.$refs.acctcodeGroupPop.show(this.isSingleProject);
    },

    /** 查询集团管理-自动结转规则-规则设置列表 */
    getList() {
      this.loading = true;
      listFinanceAaCarryRules(this.queryParams).then(response => {
        this.financeAaCarryRulesList = response.rows;
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
        rulesId: null,
        rulesNo: null,
        accountIdOut: null,
        accountCodeOut: null,
        carryType: null,
        accountIdIn: null,
        accountCodeIn: null,
        drcr: null,
        remark: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateName: null,
        updateTime: null,
        acctNameOut: null,
        acctNameIn: null
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
      this.title = "添加集团管理-自动结转规则-规则设置";
      this.form.rulesNo = this.parRulesNo;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFinanceAaCarryRules(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改集团管理-自动结转规则-规则设置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateFinanceAaCarryRules(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFinanceAaCarryRules(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除集团管理-自动结转规则-规则设置编号为"' + ids + '"的数据项？').then(function() {
        return delFinanceAaCarryRules(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/financeAaCarryRules/export', {
        ...this.queryParams
      }, `financeAaCarryRules_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
