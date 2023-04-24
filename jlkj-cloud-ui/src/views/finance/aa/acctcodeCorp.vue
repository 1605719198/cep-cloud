<template>
  <div class="app-container">
  <div>
    会计科目设定
  </div>
    <div>
      <el-select v-model="queryParams.companyId" filterable placeholder="请输入核算项目一">
        <el-option
          v-for="item in companyList"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
  </div>

    <el-row :gutter="20">
      <!--会计科目-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input placeholder="搜索会计科目"
                    v-model="filterText"
                    clearable
                    style="margin-bottom: 20px">
          </el-input>
        </div>

        <div class="el-form-item el-form-item--small">
          <el-button style="width: 160px" size="medium" type="primary"> 会计科目</el-button>
        </div>
        <div class="head-container" style="height: 81vh;width: 100%;">
          <el-scrollbar style="height: 100%;">
            <el-tree
              :data="codeOptions"
              :props="defaultProps"
              :expand-on-click-node="true"
              :filter-node-method="filterNode"
              ref="tree"
              node-key="id"
              :default-expand-all="false"
              :highlight-current="true"
              @node-click="handleNodeClick"
            />
          </el-scrollbar>
        </div>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
          <el-form-item label="科目名称" prop="acctName">
            <el-input
              v-model="queryParams.acctName"
              placeholder="请输入科目名称"
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
              @click="openSelectCode"
              v-hasPermi="['finance:acctcodeCorp:add']"
            >添加科目</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['finance:acctcodeCorp:edit']"
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
              v-hasPermi="['finance:acctcodeCorp:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['finance:acctcodeCorp:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="acctcodeCorpList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="会计科目" align="center" prop="acctCode" width="80"/>
          <el-table-column label="会计科目中文名称" align="center" prop="acctName" />
          <el-table-column label="核算项目一" align="center" prop="calTypeCodea" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ transCalTypeName(scope.row.calTypeCodea, '') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="核算项目二" align="center" prop="calTypeCodeb" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ transCalTypeName(scope.row.calTypeCodeb, '') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="核算项目三" align="center" prop="calTypeCodec" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ transCalTypeName(scope.row.calTypeCodec, '') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="核算项目四" align="center" prop="calTypeCoded" show-overflow-tooltip>
            <template v-slot="scope">
              <span>{{ transCalTypeName(scope.row.calTypeCoded, '') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="是否传票性科目" align="center" prop="isVoucher" show-overflow-tooltip>
            <template v-slot="scope">
              <dict-tag :options="dict.type.aa_yes_no" :value="scope.row.isVoucher"/>
            </template>
          </el-table-column>
          <el-table-column label="是否数量金额辅助核算" width="150" align="center" prop="isUnit" show-overflow-tooltip>
            <template v-slot="scope">
              <dict-tag :options="dict.type.aa_yes_no" :value="scope.row.isUnit"/>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" key="disabledCode" >
            <template v-slot="scope">
              <el-switch
                v-model="scope.row.disabledCode"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-value="Y"
                inactive-value="N"
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
                v-hasPermi="['finance:acctcodeCorp:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['finance:acctcodeCorp:remove']"
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
      </el-col>
    </el-row>

    <!-- 添加或修改会计科目-公司级对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="850px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px" >
        <el-divider></el-divider>
        <div style="text-align: center;padding: 20px 0 0px 0;position:relative;top:-35px">
          <div style="padding-bottom: 15px">集团级信息</div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="会计科目编号" prop="acctCode" align="left">
                {{form.acctCode}}
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="会计科目中文名称" prop="acctName" align="left">
                {{form.acctName}}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="传票性科目" prop="isVoucher"  align="left">
                <dict-tag :options="dict.type.aa_yes_no" :value="form.isVoucher"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="借贷方" prop="drOrCr" align="left">
                <dict-tag :options="dict.type.aa_drcr" :value="form.drOrCr"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="现金科目" prop="isCash" align="left">
                <dict-tag :options="dict.type.aa_yes_no" :value="form.isCash"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="级别" prop="level" align="left">
                {{form.level}}
              </el-form-item>
            </el-col>
          </el-row>
        </div>

        <el-divider></el-divider>
        <div style="text-align: center;position:relative;top:-15px">公司级信息</div>
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司" prop="companyId">
              <el-input v-model="form.companyId" placeholder="请输入会计公司id" disabled/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="核算项目一" prop="calTypeCodea">
              <el-select v-model="form.calTypeCodea" filterable placeholder="请输入核算项目一">
                <el-option
                  v-for="item in calTypeList"
                  :key="item.value"
                  :label="item.value+'_'+item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="核算项目二" prop="calTypeCodeb">
              <el-select v-model="form.calTypeCodeb" filterable placeholder="请输入核算项目二">
                <el-option
                  v-for="item in calTypeList"
                  :key="item.value"
                  :label="item.value+'_'+item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="核算项目三" prop="calTypeCodec">
              <el-select v-model="form.calTypeCodec" filterable placeholder="请输入核算项目三">
                <el-option
                  v-for="item in calTypeList"
                  :key="item.value"
                  :label="item.value+'_'+item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="核算项目四" prop="calTypeCoded">
              <el-select v-model="form.calTypeCoded" filterable placeholder="请输入核算项目四">
                <el-option
                  v-for="item in calTypeList"
                  :key="item.value"
                  :label="item.value+'_'+item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="到期日核算" prop="isDueDate"  align="left">
              <el-radio-group v-model="form.isDueDate">
                <el-radio v-for="dict in dict.type.aa_yes_no"
                          :key="dict.value"
                          :label="dict.value">{{dict.label}}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数量金额辅助核算" prop="isUnit"  align="left">
              <el-radio-group v-model="form.isUnit">
                <el-radio v-for="dict in dict.type.aa_yes_no"
                          :key="dict.value"
                          :label="dict.value">{{dict.label}}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="外币核算" prop="isFrnCrcy"  align="left">
              <el-radio-group v-model="form.isFrnCrcy">
                <el-radio v-for="dict in dict.type.aa_yes_no"
                          :key="dict.value"
                          :label="dict.value">{{dict.label}}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="机器专用" prop="isPrvlg"  align="left">
              <el-radio-group v-model="form.isPrvlg">
                <el-radio v-for="dict in dict.type.aa_yes_no"
                          :key="dict.value"
                          :label="dict.value">{{dict.label}}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="状态"  align="left">
              <el-radio-group v-model="form.disabledCode">
                <el-radio
                  v-for="dict in dict.type.aa_disabled_code"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="禁用日期" prop="disabledDate"  align="left">
              <span>{{ parseTime(form.disabledDate, '{y}-{m}-{d}') }}</span>
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
    <select-acctcode-group ref="select" :parentAcctCode="parentAcctCode" :parentAcctName="parentAcctName" :companyId="queryParams.companyId" @ok="handleQuery" />
  </div>
</template>

<script>
import { listAcctcodeCorp, getAcctcodeCorp, delAcctcodeCorp, addAcctcodeCorp, updateAcctcodeCorp,codeTreeSelect,changeStatus } from "@/api/finance/aa/acctcodeCorp";
import { selectCompanyList } from "@/api/finance/aa/companyGroup";
import { selectCalTypeList } from "@/api/finance/aa/calType";
import selectAcctcodeGroup from "./selectAcctcodeGroup";
import { parseTime } from "@/utils/jlkj";
export default {
  name: "AcctcodeCorp",
  dicts: ['aa_yes_no','aa_disabled_code','aa_drcr'],
  components: { selectAcctcodeGroup },
  data() {
    return {
      // 会计科目树选项
      codeOptions: undefined,
      // 上层会计科目代码
      parentAcctCode: undefined,
      // 上层会计科目名称
      parentAcctName: undefined,

      // 树节点过滤
      filterText: '',
      // 会计公司下拉选单
      companyList: [],
      // 核算项目类别选单
      calTypeList: [],
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
      // 会计科目-公司级表格数据
      acctcodeCorpList: [],
      // 弹出层标题
      title: "",
      // 是否显示修改弹出层
      open: false,
      // 是否显示添加弹出层
      openAdd: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: 'J00',
        acctName: null,
        updateName: null,
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        acctId: [
          { required: true, message: "会计科目主键不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getCompanyList();
    this.getCalTypeList();
    this.getList();
    this.getCodeTree();
  },
  methods: {
    /** 查询会计科目下拉树结构 */
    getCodeTree() {
      codeTreeSelect({companyId: this.queryParams.companyId}).then(response => {
        this.codeOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.acctCode = data.id;
      this.parentAcctCode = data.id;
      this.parentAcctName = data.name;
      this.handleQuery();
    },

    /** 打开授权用户表弹窗 */
    openSelectCode() {
      this.$refs.select.show();
    },

    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
      });
    },

    getCalTypeList() {
      selectCalTypeList().then(response => {
        this.calTypeList = response;
      });
    },
    /** 查询会计科目-公司级列表 */
    getList() {
      this.loading = true;
      listAcctcodeCorp(this.queryParams).then(response => {
        this.acctcodeCorpList = response.rows;
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
        acctId: null,
        companyId: null,
        groupAcctId: null,
        acctCode: null,
        acctName: null,
        parentId: null,
        parentAcctCode: null,
        isVoucher: null,
        drOrCr: null,
        isCash: null,
        level: null,
        ancestors: null,
        isUnit: null,
        calTypeIda: null,
        calTypeCodea: null,
        calTypeIdb: null,
        calTypeCodeb: null,
        calTypeIdc: null,
        calTypeCodec: null,
        calTypeIdd: null,
        calTypeCoded: null,
        isDueDate: null,
        isLqdt: null,
        isFrnCrcy: null,
        isPrvlg: null,
        disabledCode: null,
        disabledDate: null,
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
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.parentAcctCode = undefined;
      this.parentAcctName = undefined;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.groupAcctId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加会计科目-公司级";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const groupAcctId = row.groupAcctId || this.ids
      getAcctcodeCorp(groupAcctId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改会计科目-公司级";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.groupAcctId != null) {
            updateAcctcodeCorp(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.getCodeTree();
            });
          } else {
            addAcctcodeCorp(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.getCodeTree();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const groupAcctIds = row.groupAcctId || this.ids;
      this.$modal.confirm('确认要删除吗？').then(function() {
        return delAcctcodeCorp(groupAcctIds);
      }).then(() => {
        this.getList();
        this.getCodeTree();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/acctcodeCorp/export', {
        ...this.queryParams
      }, `acctcodeCorp_${new Date().getTime()}.xlsx`)
    },
    /** 核算项目转中文 */
    transCalTypeName(calType,delimiter) {
      if(calType == '') return '';
      if(delimiter!=''){
        return calType + delimiter + this.calTypeList.find(item => item.value == calType).label;
      }
      return this.calTypeList.find(item => item.value == calType).label;
    },
    // 状态修改
    handleStatusChange(row) {
      debugger
      console.log("row====>",row)
      let text = row.disabledCode === "Y" ? "启用" : "停用";
      this.$modal.confirm('确认要' + text + '吗？').then(function () {
        return changeStatus(row.groupAcctId, row.disabledCode);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function () {
        row.disabledCode = row.disabledCode === "0" ? "1" : "0";
      });
    },
  },
  watch: {
    filterText (val) {
      this.$refs.tree.filter(val);
    },
    'queryParams.companyId' (val) {
      this.queryParams.acctCode = null;
      this.parentAcctCode = null;
      this.parentAcctName = null;
      this.getList();
      this.getCodeTree();
    }
  },
};
</script>
<style>
/* el-divider 修改高度&虚线效果 */
.el-divider--horizontal{
  margin: 0 0;
  padding: 0 0;
  background: 0 0;
  top: -28px;
  border-top: 1.5px solid #99a9bf;
}
</style>
