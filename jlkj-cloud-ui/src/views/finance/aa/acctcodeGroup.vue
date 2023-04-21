<template>
  <div class="app-container">
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
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
          <el-form-item label="会计科目名称" prop="groupAcctName">
            <el-input
              v-model="queryParams.groupAcctName"
              placeholder="请输入会计科目名称"
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
              v-hasPermi="['finance:acctcodeGroup:add']"
            >添加科目</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['finance:acctcodeGroup:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="acctcodeGroupList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="会计科目" align="center" prop="groupAcctCode" />
          <el-table-column label="会计科目中文名称" align="center" prop="groupAcctName" />
          <el-table-column label="余额方向" align="center" prop="drOrCr" show-overflow-tooltip>
            <template v-slot="scope">
              <dict-tag :options="dict.type.aa_drcr" :value="scope.row.drOrCr"/>
            </template>
          </el-table-column>
          <el-table-column label="现金科目" align="center" prop="isCash" show-overflow-tooltip>
            <template v-slot="scope">
              <dict-tag :options="dict.type.aa_yes_no" :value="scope.row.isCash"/>
            </template>
          </el-table-column>
          <el-table-column label="是否传票性科目" align="center" prop="isVoucher" show-overflow-tooltip>
            <template v-slot="scope">
              <dict-tag :options="dict.type.aa_yes_no" :value="scope.row.isVoucher"/>
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
                v-hasPermi="['finance:acctcodeGroup:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['finance:acctcodeGroup:remove']"
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


    <!-- 添加或修改会计科目-集团级对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="850px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="上层会计科目"  align="left">
              {{parentAcctCode}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上层会计科目名称" align="left">
              {{parentAcctName}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="会计科目编号" prop="groupAcctCode"  align="left">
              <el-input v-model="form.groupAcctCode" placeholder="请输入会计科目编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="会计科目中文名称" prop="groupAcctName"  align="left">
              <el-input v-model="form.groupAcctName" placeholder="请输入会计科目中文名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="传票性科目" prop="isVoucher"  align="left">
              <el-radio-group v-model="form.isVoucher">
                <el-radio v-for="dict in dict.type.aa_yes_no"
                  :key="dict.value"
                  :label="dict.value">{{dict.label}}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="余额方向" prop="drOrCr" align="left">
              <el-radio-group v-model="form.drOrCr">
                <el-radio v-for="dict in dict.type.aa_drcr"
                          :key="dict.value"
                          :label="dict.value">{{dict.label}}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="现金科目" prop="isCash"  align="left">
              <el-radio-group v-model="form.isCash">
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
            <el-form-item label="作废日期" prop="disabledDate"  align="left">
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
  </div>
</template>

<script>
import { listAcctcodeGroup, getAcctcodeGroup, delAcctcodeGroupByCode, addAcctcodeGroup, updateAcctcodeGroup,codeTreeSelect,changeStatus } from "@/api/finance/aa/acctcodeGroup";
import {parseTime} from "@/utils/jlkj";
export default {
  name: "AcctcodeGroup",
  dicts: ['aa_yes_no','aa_disabled_code','aa_drcr'],
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
      // 会计科目-集团级表格数据
      acctcodeGroupList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        groupAcctName: null,
        groupAcctCode: null
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        groupAcctCode: [
          { required: true, message: "会计科目不能为空", trigger: "blur" },
          { max: 20,message: '长度不可超过20个字符', trigger: 'blur'}
        ],
        groupAcctName: [
          { required: true, message: "会计科目名称不能为空", trigger: "blur" },
          { max: 50,message: '长度不可超过50个字符', trigger: 'blur'}
        ],
        isVoucher: [
          { required: true, message: "传票性科目不能为空", trigger: "blur" }
        ],
        drOrCr: [
          { required: true, message: "余额方向不能为空", trigger: "blur" }
        ],
        isCash: [
          { required: true, message: "现金科目不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getCodeTree();
  },
  methods: {
    /** 查询会计科目下拉树结构 */
    getCodeTree() {
      codeTreeSelect().then(response => {
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
      this.queryParams.groupAcctCode = data.id;
      this.parentAcctCode = data.id;
      this.parentAcctName = data.name;
      this.handleQuery();
    },

    /** 查询会计科目-集团级列表 */
    getList() {
      this.loading = true;
      listAcctcodeGroup(this.queryParams).then(response => {
        this.acctcodeGroupList = response.rows;
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
        groupAcctId: null,
        groupAcctCode: null,
        groupAcctName: null,
        parentId: null,
        parentAcctCode: this.parentAcctCode,
        isVoucher: null,
        drOrCr: null,
        isCash: null,
        level: null,
        disabledCode: 'Y',
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
      this.title = "添加会计科目-集团级";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const groupAcctId = row.groupAcctId || this.ids
      getAcctcodeGroup(groupAcctId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改会计科目-集团级";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.groupAcctId != null) {
            updateAcctcodeGroup(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.getCodeTree();
            });
          } else {
            addAcctcodeGroup(this.form).then(response => {
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
    handleDeleteBak(row) {
      const groupAcctIds = row.groupAcctId || this.ids;
      this.$modal.confirm('是否确认删除？').then(function() {
        return delAcctcodeGroup(groupAcctIds);
      }).then(() => {
        this.getList();
        this.getCodeTree();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const groupAcctCode = row.groupAcctCode;
      this.$modal.confirm('是否确认删除？').then(function() {
        return delAcctcodeGroupByCode(groupAcctCode);
      }).then(() => {
        this.getList();
        this.getCodeTree();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/acctcodeGroup/export', {
        ...this.queryParams
      }, `acctcodeGroup_${new Date().getTime()}.xlsx`)
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.disabledCode === "0" ? "启用" : "停用";
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
    }
  },
};
</script>
