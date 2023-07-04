<template>
  <el-dialog title="账务规则代码" :visible.sync="visible" width="1080px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="账务代码" prop="accountItemCode">
        <el-input
          v-model="queryParams.accountItemCode"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账务名称" prop="accountItemName">
        <el-input
          v-model="queryParams.accountItemName"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="financetestList" @selection-change="handleSelectionChange"
                height="360px">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="账务代码" prop="accountItemCode" :show-overflow-tooltip="true"/>
        <el-table-column label="账务名称" prop="accountItemName" :show-overflow-tooltip="true"/>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSelectUser">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {listFinancetest} from "@/api/finance/ip/accountingcode";

export default {
  name: "accountingCodePop",
  data() {
    return {

      // 判断选择单笔还是多笔
      isSingle: true,
      // 遮罩层
      visible: false,
      // 选中数组值
      projectIds: [],
      // 总条数
      total: 0,
      // 弹窗数据
      financetestList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleId: 1,
        accountItemCode: null,
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 显示弹框
    show(val) {
      this.getList();
      this.visible = true;
      this.isSingle = val
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.projectIds = selection;
    },
    /** 查询账务代码列表 */
    getList() {
      this.loading = true;

      listFinancetest(this.queryParams).then(response => {
        this.financetestList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
    /** 选择账务代码 */
    handleSelectUser() {
      if (this.projectIds.length === 0) {
        this.$modal.msgError("请选择要分配的账务代码");
        return;
      }
      if (this.projectIds.length > 1 && this.isSingle === true) {
        this.$modal.msgError("只能选择一笔数据");
        return;
      }
      this.visible = false;
      this.$emit("ok",this.projectIds);
    },
  }
};
</script>


