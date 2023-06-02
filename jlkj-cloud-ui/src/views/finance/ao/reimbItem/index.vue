<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="130px">
      <el-form-item label="报支类别编码" prop="itemNo">
        <el-input
          v-model="queryParams.itemNo"
          placeholder="请输入报支类别编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报支类别名称" prop="itemName">
        <el-input
          v-model="queryParams.itemName"
          placeholder="请输入报支类别名称"
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
          v-hasPermi="['finance:reimbItem:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['finance:reimbItem:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['finance:reimbItem:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="reimbItemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="报支类别编码" align="center" prop="itemNo" />
      <el-table-column label="报支类别名称" align="center" prop="itemName" />
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
            v-hasPermi="['finance:financeAaCarryRulesCorp:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="openSelectCode(scope.row)"
            v-hasPermi="['finance:reimbItem:edit']"
          >设置细项</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:reimbItem:remove']"
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

    <!-- 添加或修改报支管理-报支类别集团设定主档对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-form-item label="报支类别编码" prop="itemNo">
          <el-input v-model="form.itemNo" placeholder="请输入报支类别编码" maxlength="10" show-word-limit :disabled="actionCode=='U'"/>
        </el-form-item>
        <el-form-item label="报支类别名称" prop="itemName">
          <el-input v-model="form.itemName" placeholder="请输入报支类别名称" maxlength="100" show-word-limit/>
        </el-form-item>
        <el-form-item label="贷方会计科目" >
          <el-input v-model="crAcctName" placeholder="请选择贷方会计科目" :disabled="true">
            <el-button slot="append" icon="el-icon-search" @click="accountCodeClick()"></el-button>
          </el-input>
        </el-form-item>



        <el-form-item label="新增人" prop="createName" align="left">
          {{form.createName}}
        </el-form-item>
        <el-form-item label="新增时间" prop="createTime" align="left">
          {{form.createTime}}
        </el-form-item>
        <el-form-item label="异动人" prop="updateName" align="left">
          {{form.updateName}}
        </el-form-item>
        <el-form-item label="异动时间" prop="updateTime" align="left">
          {{form.updateTime}}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <acctcode-group-pop ref="acctcodeGroupPop" @ok="getAccountCodeData"></acctcode-group-pop>
    <reimb-detail ref="reimbDetail" ></reimb-detail>
  </div>
</template>

<script>
import { listReimbItem, getReimbItem, delReimbItem, addReimbItem, updateReimbItem, changeStatus } from "@/api/finance/ao/reimbItem";
import reimbDetail from "@/views/finance/ao/reimbDetail";
import acctcodeGroupPop from "@/views/finance/aa/acctcodeGroupPop";
import {selectAcctNameByCode} from "@/api/finance/aa/acctcodeGroup";
export default {
  name: "ReimbItem",
  components: {acctcodeGroupPop,reimbDetail},
  data() {
    return {
      // 会计科目中文名称
      crAcctName: null,
      // 操作标识 N-新增 U-修改
      actionCode:null,
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
      // 报支管理-报支类别集团设定主档表格数据
      reimbItemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemNo: null,
        itemName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemNo: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
        itemName: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    /** 打开授权用户表弹窗 */
    openSelectCode(row) {
      if (row.status == '1') {
        this.$modal.msgError("禁用状态不可设置细项!");
        return;
      }
      this.$refs.reimbDetail.show(row.itemNo,row.itemName,row.status);
    },

    /** 会计科目点击事件 */
    accountCodeClick(val) {
      this.tagSrc = val
      this.isSingleProject = true
      this.$refs.acctcodeGroupPop.show(this.isSingleProject);
    },

    /** 获取转出会计科目 */
    getAccountCodeData(val) {
      this.form.crAcctCode = val[0].groupAcctCode
      this.crAcctName = val[0].groupAcctName
    },
    /** 查询报支管理-报支类别集团设定主档列表 */
    getList() {
      this.loading = true;
      listReimbItem(this.queryParams).then(response => {
        this.reimbItemList = response.rows;
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
        itemNo: null,
        itemName: null,
        crAcctCode: null,
        status: '0',
        otherType: null,
        otherDesc: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateName: null,
        updateTime: null
      };
      this.crAcctName = null;
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
      this.actionCode = 'N'
      this.title = "报支类别集团设定主档新增";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.actionCode = 'U'
      this.reset();
      selectAcctNameByCode(row.crAcctCode).then(response => {
        this.crAcctName = response;
      });
      const id = row.id || this.ids
      getReimbItem(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "报支类别集团设定主档修改";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReimbItem(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReimbItem(this.form).then(response => {
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
        return delReimbItem(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/reimbItem/export', {
        ...this.queryParams
      }, `reimbItem_${new Date().getTime()}.xlsx`)
    },
    // 状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要' + text + '吗？').then(function () {
        return changeStatus(row.id, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function () {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
  }
};
</script>
