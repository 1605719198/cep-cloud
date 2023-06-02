<template>
  <el-dialog title="设置细项" :visible.sync="openmain" width="1500px" custom-class="details_class" append-to-body>
    <div>
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="130px">
        <el-form-item label="报支类别编码">
          {{queryParams.itemNo}}
        </el-form-item>
        <el-form-item label="报支类别名称">
          {{queryParams.itemName}}
        </el-form-item>
        <el-form-item label="状态">
          {{queryParams.statusMain=='0' ? '启用':'停用'}}
        </el-form-item>
      </el-form>

      <el-table v-loading="loading" :data="reimbDetailList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="细项编码" align="center" prop="detailNo" />
        <el-table-column label="细项名称" align="center" prop="detailName" />
        <el-table-column label="费用类别" align="center" prop="expenseType" show-overflow-tooltip>
          <template v-slot="scope">
            {{scope.row.expenseType+'_'}}<dict-tag :options="dict.type.ao_expense_type" :value="scope.row.expenseType" style="display: inline"/>
          </template>
        </el-table-column>
        <el-table-column label="借方会计科目" align="center" prop="drAcctName" />
        <el-table-column label="贷方会计科目" align="center" prop="crAcctName" />
        <el-table-column label="状态" align="center" key="status" >
          <template v-slot="scope">
            <el-switch
              v-model="scope.row.status"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="0"
              inactive-value="1"
              @change="handleStatusChange(scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['finance:reimbDetail:edit']"
            >修改</el-button>
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

      <!-- 添加或修改报支管理-报支类别集团设定明细档对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="800px" custom-class="details_class" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="130px">
          <el-divider>报支类别信息</el-divider>
          <el-row>
            <el-col :span="8">
              <el-form-item label="报支类别编码" prop="acctCode" align="left">
                {{queryParams.itemNo}}
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="报支类别名称" prop="acctName" align="left">
                {{queryParams.itemName}}
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="状态" prop="acctName" align="left">
                {{queryParams.statusMain=='0' ? '启用':'停用'}}
              </el-form-item>
            </el-col>
          </el-row>
          <el-divider>细项信息</el-divider>
          <el-row>
            <el-col :span="12">
              <el-form-item label="细项编码" prop="detailNo">
                <el-input v-model="form.detailNo" placeholder="请输入细项编码" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="细项名称" prop="detailName">
                <el-input v-model="form.detailName" placeholder="请输入细项名称" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="费用类别" prop="expenseType">
                <el-select v-model="form.expenseType" placeholder="请输入费用类别" style="width: 250px">
                  <el-option
                    v-for="dict in dict.type.ao_expense_type"
                    :key="dict.value"
                    :label="dict.value+'_'+dict.label"
                    :value="dict.value"
                  ></el-option>
                </el-select>

              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="状态" >
                <el-switch
                  v-model="form.status"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  active-value="0"
                  inactive-value="1"
                  @change="handleStatusChange(form)"
                ></el-switch>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="借方科目" prop="drAcctCode">
                <el-input v-model="this.form.drAcctName" placeholder="请选择借方科目" :disabled="true">
                  <el-button slot="append" icon="el-icon-search" @click="accountCodeClick('drCodeClick')"></el-button>
                </el-input>
              </el-form-item>

            </el-col>
            <el-col :span="12">
              <el-form-item label="贷方科目" prop="crAcctCode">
                <el-input v-model="this.form.crAcctName" placeholder="请选择贷方科目" :disabled="true">
                  <el-button slot="append" icon="el-icon-search" @click="accountCodeClick('crCodeClick')"></el-button>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="借方核算项目一" prop="drAcctCode">
                <el-input v-model="this.form.drCalTypeName1" :disabled="true"></el-input>
              </el-form-item>

            </el-col>
            <el-col :span="12">
              <el-form-item label="贷方核算项目一" prop="crAcctCode">
                <el-input v-model="this.form.crCalTypeName1":disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="借方核算项目二" prop="drAcctCode">
                <el-input v-model="this.form.drCalTypeName2" :disabled="true"></el-input>
              </el-form-item>

            </el-col>
            <el-col :span="12">
              <el-form-item label="贷方核算项目二" prop="crAcctCode">
                <el-input v-model="this.form.crCalTypeName2"  :disabled="true"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="预算类别" prop="budgetType">
                <el-select v-model="form.budgetType" placeholder="请输入预算类别" style="width: 250px">
                  <el-option
                    v-for="dict in dict.type.ao_budget_type"
                    :key="dict.value"
                    :label="dict.value+'_'+dict.label"
                    :value="dict.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="成本科目" prop="costCode">
                <el-input v-model="form.costCode" placeholder="请输入成本科目" />
              </el-form-item>

            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="细项说明" prop="otherDesc">
                <el-input v-model="form.otherDesc" placeholder="请输入细项说明" />
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
    <div slot="footer" class="dialog-footer" align="center">
      <el-button @click="cancelMain">关 闭</el-button>
    </div>
    <acctcode-corp-pop ref="acctcodeCorpPop" @ok="getAccountCodeData" :companyId="this.queryParams.companyId"></acctcode-corp-pop>
  </el-dialog>
</template>

<script>
import { listReimbDetail, getReimbDetail, delReimbDetail, addReimbDetail, updateReimbDetail } from "@/api/finance/ao/reimbDetail";
import { listReimbItemCompDetail, getReimbItemCompDetail, delReimbItemCompDetail, addReimbItemCompDetail, updateReimbItemCompDetail,changeStatus } from "@/api/finance/ao/reimbItemCompDetail";
import acctcodeCorpPop from "@/views/finance/aa//acctcodeCorpPop";
export default {
  name: "ReimbItemCompDetail",
  dicts: ['ao_expense_type','ao_budget_type'],
  components: {acctcodeCorpPop},
  data() {
    return {
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
      // 报支管理-报支类别集团设定明细档表格数据
      reimbDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示主页面
      openmain: false,
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemNo: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        detailNo: [
          { required: true, message: "细项编码不能为空", trigger: "blur" }
        ],
        detailName: [
          { required: true, message: "细项名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 显示弹框
    show(companyId,itemNo,itemName,status) {
      // this.queryParams.acctCode = this.parentAcctCode;
      this.queryParams.companyId = companyId;
      this.queryParams.itemNo = itemNo;
      this.queryParams.itemName = itemName
      this.queryParams.statusMain = status
      this.getList();
      this.openmain = true;
    },
    /** 会计科目点击事件 */
    accountCodeClick(val) {
      this.tagSrc = val
      this.isSingleProject = true
      this.$refs.acctcodeCorpPop.show(this.isSingleProject);
    },
    /** 获取会计科目 */
    getAccountCodeData(val) {
      console.log("val====>",val)
      if (this.tagSrc === 'drCodeClick') {
        this.form.drAcctCode = val[0].acctCode
        this.form.drAcctName = val[0].acctName
        this.form.drCalTypeName1 = val[0].calTypeName1
        this.form.drCalTypeName2 = val[0].calTypeName2
      }else if(this.tagSrc === 'crCodeClick') {
        this.form.crAcctCode = val[0].acctCode
        this.form.crAcctName = val[0].acctName
        this.form.crCalTypeName1 = val[0].calTypeName1
        this.form.crCalTypeName2 = val[0].calTypeName2
      }
    },
    /** 查询报支管理-报支类别集团设定明细档列表 */
    getList() {
      this.loading = true;
      listReimbItemCompDetail(this.queryParams).then(response => {
        this.reimbDetailList = response.rows;
        console.log("reimbDetailList===>",this.reimbDetailList)
        this.total = response.total;
        this.loading = false;
      });
    },
    cancelMain() {
      this.openmain = false;
      this.reset();
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
        itemNo: null,
        detailNo: null,
        detailName: null,
        expenseType: null,
        drAcctCode: null,
        crAcctCode: null,
        budgetType: null,
        costCode: null,
        status: null,
        otherType: null,
        otherDesc: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateName: null,
        updateTime: null,
        drAcctName: null,
        drCalTypeName1: null,
        drCalTypeName2: null,
        crAcctName: null,
        crCalTypeName1: null,
        crCalTypeName2: null
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
      this.form.itemNo = this.queryParams.itemNo;
      this.title = "新增细项";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getReimbItemCompDetail(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改细项";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReimbItemCompDetail(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReimbDetail(this.form).then(response => {
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
        return delReimbDetail(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/reimbDetail/export', {
        ...this.queryParams
      }, `reimbDetail_${new Date().getTime()}.xlsx`)
    },
    // 状态修改
    handleStatusChange(form) {
      let text = form.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要' + text + '吗？').then(function () {
        return changeStatus(form.id, form.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function () {
        form.status = form.status === "0" ? "1" : "0";
      });
    },
  }
};
</script>
<style scoped>
/deep/ .details_class .el-dialog__body{
  padding: 20px;
  padding-top: 0px;
  padding-bottom: 20px;
}
</style>
