<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="报支类别编码" prop="itemNo">
        <el-input
          v-model="queryParams.itemNo"
          placeholder="请输入报支类别编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="细项编码" prop="detailNo">
        <el-input
          v-model="queryParams.detailNo"
          placeholder="请输入细项编码"
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
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['finance:itemDetailCompany:edit']"
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
          v-hasPermi="['finance:itemDetailCompany:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="itemDetailCompanyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="细项编码" align="center" prop="detailNo" />
      <el-table-column label="细项名称" align="center" prop="detailName" />
      <el-table-column label="费用类别" align="center" prop="expenseType" />
      <el-table-column label="借方会计科目" align="center" prop="drAcctCode" />
      <el-table-column label="贷方会计科目" align="center" prop="crAcctCode" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:itemDetailCompany:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:itemDetailCompany:remove']"
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

    <!-- 添加或修改报支类别公司细项对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="报支类别编码" prop="itemNo">
          <el-input v-model="form.itemNo" placeholder="请输入报支类别编码" />
        </el-form-item>
        <el-form-item label="细项编码" prop="detailNo">
          <el-input v-model="form.detailNo" placeholder="请输入细项编码" />
        </el-form-item>
        <el-form-item label="细项名称" prop="detailName">
          <el-input v-model="form.detailName" placeholder="请输入细项名称" />
        </el-form-item>
        <el-form-item label="借方会计科目" prop="drAcctCode">
          <el-input v-model="form.drAcctCode" placeholder="请输入借方会计科目" />
        </el-form-item>
        <el-form-item label="贷方会计科目" prop="crAcctCode">
          <el-input v-model="form.crAcctCode" placeholder="请输入贷方会计科目" />
        </el-form-item>
        <el-form-item label="成本科目" prop="costCode">
          <el-input v-model="form.costCode" placeholder="请输入成本科目" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listItemDetailCompany, getItemDetailCompany, delItemDetailCompany, addItemDetailCompany, updateItemDetailCompany } from "@/api/finance/ap/itemDetailCompany";

export default {
  name: "ItemDetailCompany",
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
      // 报支类别公司细项表格数据
      itemDetailCompanyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemNo: null,
        detailNo: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询报支类别公司细项列表 */
    getList() {
      this.loading = true;
      listItemDetailCompany(this.queryParams).then(response => {
        this.itemDetailCompanyList = response.rows;
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
        detailNo: null,
        detailName: null,
        expenseType: null,
        drAcctCode: null,
        crAcctCode: null,
        budgetType: null,
        costCode: null,
        status: null,
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
      this.title = "添加报支类别公司细项";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getItemDetailCompany(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报支类别公司细项";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateItemDetailCompany(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addItemDetailCompany(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除报支类别公司细项编号为"' + ids + '"的数据项？').then(function() {
        return delItemDetailCompany(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>
