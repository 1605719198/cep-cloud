<template>
  <el-dialog title="金融管理" :visible.sync="visible" width="1080px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item label="总行" prop="bankNo">
        <el-input
          v-model="queryParams.bankNo"
          placeholder="总行代码"
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
      <el-table @row-click="clickRow" ref="table" :data="bankList" @selection-change="handleSelectionChange"
                height="360px">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="总行代码" prop="bankNo" :show-overflow-tooltip="true"/>
        <el-table-column label="总行中文简称" prop="bankChnabbr" :show-overflow-tooltip="true"/>
        <el-table-column label="总行中文全名" prop="bankchnName" :show-overflow-tooltip="true"/>
        <el-table-column label="总行统一编号" prop="bankuniformNo" :show-overflow-tooltip="true"/>
        <el-table-column label="代码来源" prop="banknoSrc" :show-overflow-tooltip="true"/>
        <el-table-column label="总行国别" prop="countryId" :show-overflow-tooltip="true"/>
        <el-table-column label="银行型态" prop="bankType" :show-overflow-tooltip="true"/>
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
import {listBank} from "@/api/finance/aa/bank";

export default {

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
      bankList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleId: 1,
        bankNo: null,
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
    /** 查询金融机构管理列表 */
    getList() {
      this.loading = true;

      listBank(this.queryParams).then(response => {
        this.bankList = response.rows;
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
    /** 选择金融机构 */
    handleSelectUser() {
      if (this.projectIds.length === 0) {
        this.$modal.msgError("请选择要分配的总行代码");
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


