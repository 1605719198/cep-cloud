<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="130px">
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
          @click="openSelectPop"
          v-hasPermi="['finance:reimbItemComp:add']"
        >选取集团报支类别</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['finance:reimbItemComp:edit']"
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
          v-hasPermi="['finance:reimbItemComp:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['finance:reimbItemComp:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="reimbItemCompList" @selection-change="handleSelectionChange">
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
            v-hasPermi="['finance:reimbItemComp:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="openSelectCode(scope.row)"
            v-hasPermi="['finance:reimbItemComp:edit']"
          >设置细项</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:reimbItemComp:remove']"
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

    <!-- 添加或修改报支类别-公司级-设定主档对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-form-item label="报支类别编码" prop="itemNo">
          {{form.itemNo}}
        </el-form-item>
        <el-form-item label="报支类别名称" prop="itemName">
          {{form.itemName}}
        </el-form-item>
        <el-form-item label="贷方会计科目" prop="crAcctCode">
          {{form.crAcctCode}}
        </el-form-item>
        <el-form-item label="备注" prop="otherDesc">
          <el-input v-model="form.otherDesc" placeholder="请输入备注"  type="textarea" maxlength="200" show-word-limit/>
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
    <select-reimb-item ref="selectReimbItem" :companyId="this.queryParams.companyId" @ok="handleQuery" ></select-reimb-item>
    <reimb-item-comp-detail ref="reimbItemCompDetail" ></reimb-item-comp-detail>
  </div>
</template>

<script>
import { listReimbItemComp, getReimbItemComp, delReimbItemComp, addReimbItemComp, updateReimbItemComp, changeStatus } from "@/api/finance/ao/reimbItemComp";
import selectReimbItem from "./selectReimbItem";
import reimbItemCompDetail from "@/views/finance/ao/reimbItemCompDetail";
import { selectCompanyList } from "@/api/finance/aa/companyGroup";
export default {
  name: "ReimbItemComp",
  components: { selectReimbItem,reimbItemCompDetail },
  data() {
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
      // 报支类别-公司级-设定主档表格数据
      reimbItemCompList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: 'J00',
        itemNo: null,
        itemName: null,
        updateName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getCompanyList();
    this.getList();
  },
  methods: {
    /** 打开集团报支类别弹窗 */
    openSelectPop() {
      this.$refs.selectReimbItem.show();
    },
    /** 打开授权用户表弹窗 */
    openSelectCode(row) {
      if (row.status == '1') {
        this.$modal.msgError("禁用状态不可设置细项!");
        return;
      }
      this.$refs.reimbItemCompDetail.show(row.companyId,row.itemNo,row.itemName,row.status);
    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
      });
    },
    /** 查询报支类别-公司级-设定主档列表 */
    getList() {
      this.loading = true;
      listReimbItemComp(this.queryParams).then(response => {
        this.reimbItemCompList = response.rows;
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
        itemNo: null,
        itemName: null,
        crAcctCode: null,
        status: null,
        otherType: null,
        otherDesc: null,
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
      this.title = "添加报支类别-公司级-设定主档";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getReimbItemComp(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报支类别-公司级-设定主档";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReimbItemComp(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReimbItemComp(this.form).then(response => {
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
        return delReimbItemComp(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/reimbItemComp/export', {
        ...this.queryParams
      }, `reimbItemComp_${new Date().getTime()}.xlsx`)
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
