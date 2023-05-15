<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="基本资料" name="first">
        <el-form :model="queryParams" ref="queryForm"  size="small" :inline="true" v-show="showSearch" label-width="68px">

          <el-form-item label="公司" prop="companyId">
            <el-select v-model="queryParams.companyId" filterable placeholder="请选择公司">
              <el-option
                v-for="item in companyList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="结转代码" prop="rulesNo">
            <el-input
              v-model="queryParams.rulesNo"
              placeholder="请输入结转代码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="结转名称" prop="rulesName">
            <el-input
              v-model="queryParams.rulesName"
              placeholder="请输入结转名称"
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
              v-hasPermi="['finance:financeAaCarryBaseCorp:add']"
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
              v-hasPermi="['finance:financeAaCarryBaseCorp:edit']"
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
              v-hasPermi="['finance:financeAaCarryBaseCorp:remove']"
            >删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="financeAaCarryBaseCorpList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="结转代码" align="center" prop="rulesNo" />
          <el-table-column label="结转名称" width="160" prop="rulesName" show-overflow-tooltip>
            <template slot-scope="scope">
              <el-button type="text" @click="handleCarryRules(scope.row)">{{scope.row.rulesName}}</el-button>
            </template>
          </el-table-column>


          <el-table-column label="凭证类别" align="center" prop="voucherType" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ transVoucherName(scope.row.voucherType, '_') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="凭证状态" align="center" prop="voucherStatus" show-overflow-tooltip>
            <template v-slot="scope">
              <dict-tag :options="dict.type.aa_voucher_status" :value="scope.row.voucherStatus"/>
            </template>
          </el-table-column>
          <el-table-column label="说明" align="center" prop="remark" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['finance:financeAaCarryBaseCorp:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['finance:financeAaCarryBaseCorp:remove']"
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
      </el-tab-pane>
      <el-tab-pane label="结转规则" name="second">
        <finance-aa-carry-rules-corp ref="financeAaCarryRulesCorp" ></finance-aa-carry-rules-corp>
      </el-tab-pane>
    </el-tabs>


    <!-- 添加或修改自动结转规则-基本资料-集团对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司" prop="companyId" >
              <el-select v-model="form.companyId" filterable placeholder="请选择公司" style="width:205px">
                <el-option
                  v-for="item in companyList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="结转代码" prop="rulesNo" >
              <el-input v-model="form.rulesNo" placeholder="请输入结转代码" maxlength="10" show-word-limit/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结转名称" prop="rulesName">
              <el-input v-model="form.rulesName" placeholder="请输入结转名称" maxlength="100" show-word-limit/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="凭证类别" prop="voucherType">
              <el-select v-model="form.voucherType" filterable placeholder="请选择凭证类别">
                <el-option
                  v-for="item in voucherTypeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="凭证状态"  prop="voucherStatus" align="left">
              <el-radio-group v-model="form.voucherStatus">
                <el-radio
                  v-for="dict in dict.type.aa_voucher_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="说明" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入说明" maxlength="200" show-word-limit/>
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
  </div>
</template>

<script>
import { listFinanceAaCarryBaseCorp, getFinanceAaCarryBaseCorp, delFinanceAaCarryBaseCorp, addFinanceAaCarryBaseCorp, updateFinanceAaCarryBaseCorp } from "@/api/finance/aa/financeAaCarryBaseCorp";
import { selectVoucherTypeList } from "@/api/finance/aa/voucherType";
import financeAaCarryRulesCorp from "@/views/finance/aa/financeAaCarryRulesCorp";
import { selectCompanyList } from "@/api/finance/aa/companyGroup";
export default {
  name: "FinanceAaCarryBaseCorp",
  dicts: ['aa_voucher_status'],
  components: {financeAaCarryRulesCorp},
  data() {
    return {
      // tab名称（默认选中第一个标签页first）
      activeName: 'first',
      // 凭证类别下拉选单
      voucherTypeList: [],
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
      // 自动结转规则-基本资料-集团表格数据
      financeAaCarryBaseCorpList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId:'J00',
        rulesNo: null,
        rulesName: null,
      },
      // 表单参数
      form: {},
      queryRules: {
        rulesNo: [
          { required: true, message: "结转代码不能为空", trigger: "blur" },
          { max: 32,message: '长度不可超过10个字符', trigger: 'blur'}
        ],
      },
      // 表单校验
      rules: {
        companyId: [
          { required: true, message: "公司不能为空", trigger: "blur" }
        ],
        rulesNo: [
          { required: true, message: "结转代码不能为空", trigger: "blur" },
          { max: 32,message: '长度不可超过10个字符', trigger: 'blur'}
        ],
        rulesName: [
          { required: true, message: "结转名称不能为空", trigger: "blur" },
          { max: 100,message: '长度不可超过100个字符', trigger: 'blur'}
        ],
        voucherType: [
          { required: true, message: "凭证类别不能为空", trigger: "blur" }
        ],
        voucherStatus: [
          { required: true, message: "凭证状态不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getCompanyList();
    this.getVoucherTypeList();
    this.getList();
  },
  methods: {
    /** tab 切换事件 */
    handleClick(tab, event) {
      this.activeName = tab.name
    },
    /** 跳转到结转规则页签*/
    handleCarryRules(row) {
      this.activeName = "second"
      const obj = {}
      obj.companyId = row.companyId
      obj.rulesNo = row.rulesNo
      obj.rulesName = row.rulesName

      this.$nextTick(() => {
        this.$refs.financeAaCarryRulesCorp.init(obj)
      })
    },
    getVoucherTypeList() {
      selectVoucherTypeList().then(response => {
        this.voucherTypeList = response;
      });
    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
      });
    },
    /** 查询自动结转规则-基本资料-集团列表 */
    getList() {
      this.loading = true;
      listFinanceAaCarryBaseCorp(this.queryParams).then(response => {
        this.financeAaCarryBaseCorpList = response.rows;
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
        rulesNo: null,
        rulesName: null,
        voucherType: null,
        voucherStatus: null,
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
    /** 搜索按钮操作 */
    handleQuery() {
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.queryParams.pageNum = 1;
          this.getList();
        }
      });
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
      this.title = "添加自动结转规则-基本资料-集团";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFinanceAaCarryBaseCorp(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改自动结转规则-基本资料-集团";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateFinanceAaCarryBaseCorp(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFinanceAaCarryBaseCorp(this.form).then(response => {
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
        return delFinanceAaCarryBaseCorp(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/financeAaCarryBaseCorp/export', {
        ...this.queryParams
      }, `financeAaCarryBaseCorp_${new Date().getTime()}.xlsx`)
    },
    /** 核算项目转中文 */
    transVoucherName(voucherType,delimiter) {
      if(voucherType == '') return '';
      if(delimiter!=''){
        return voucherType + delimiter + this.voucherTypeList.find(item => item.value == voucherType).label;
      }
      return this.voucherTypeList.find(item => item.value == voucherType).label;
    },
  }
};
</script>
