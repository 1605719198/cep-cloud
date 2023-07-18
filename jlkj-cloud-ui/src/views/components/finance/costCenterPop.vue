<template>
  <!-- 授权用户 -->
  <el-dialog title="选择成本中心" :visible.sync="visible" width="1080px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" :inline="true">
          <el-form-item label="厂别" prop="factory">
            <el-input
              style="width:116px"
              v-model="queryParams.factory"
              placeholder="请输入厂别"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="编码" prop="costCenter">
            <el-input
              style="width:180px"
              v-model="queryParams.costCenter"
              placeholder="请输入成本中心编码"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
      <el-form-item label="名称" prop="costCenterDesc">
        <el-input
          v-model="queryParams.costCenterDesc"
          placeholder="请输入成本中心名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年度" prop="fiscalYear">
        <el-date-picker
          style="width:100px"
          v-model="queryParams.fiscalYear"
          type="year"
          value-format="yyyy"
          >
        </el-date-picker>
      </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="baseList" @selection-change="handleSelectionChange" height="360px">
        <el-table-column type="selection" width="55" align="left" ></el-table-column>
        <el-table-column label="厂别" prop="factory" align="left" />
        <el-table-column label="成本中心" prop="costCenter" :show-overflow-tooltip="true" align="left" />
        <el-table-column label="中文说明" prop="costCenterDesc" align="left" width="300"/>
        <el-table-column label="年度" prop="fiscalYear" align="left" width="80" />
        <el-table-column label="属性"prop="ccType" align="left" width="200">
          <template v-slot="scope">
            <dict-tag :options="dict.type.cost_cctype_name" :value="scope.row.ccType"/>
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="stusCode" align="left" >
          <template v-slot="scope">
            <dict-tag :options="dict.type.cost_stuscode_name" :value="scope.row.stusCode"/>
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
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSelectUser">确 定</el-button>
      <el-button @click="reset">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { selectCenterList } from "@/api/finance/ao/repayment";
export default {
  dicts: ['cost_cctype_name','cost_stuscode_name'],
  props: ['companyId'],
  data() {
    return {
      // 判断选择单笔还是多笔
      isSingle: true,
      // 遮罩层
      visible: false,
      // 选中数组值
      selecteds: [],
      // 总条数
      total: 0,
      // 弹窗数据
      baseList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleId: 1,
        companyId: null,
        factory: null,
        costCenter: null,
        costCenterDesc: null,
        fiscalYear: `${new Date().getFullYear()}`.slice(0, 4)
      }
    };
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
      this.selecteds = selection;
    },

    // 查询表数据
    getList() {
      this.loading = true;
      this.queryParams.companyId = this.companyId;
      selectCenterList(this.queryParams).then(response => {
        this.baseList = response.rows;
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
    /** 取消按钮 */
    reset() {
      this.visible = false;
      this.resetForm("queryForm");
    },
    /** 数据挑选 */
    handleSelectUser() {
      if (this.selecteds.length === 0) {
        this.$modal.msgError("请至少选择一行");
        return;
      }
      if (this.selecteds.length > 1 && this.isSingle === true) {
        this.$modal.msgError("只能选择一笔数据");
        return;
      }
      this.visible = false;
      this.$emit("ok",this.selecteds);
    },
  }
};
</script>
<style scoped>
</style>
