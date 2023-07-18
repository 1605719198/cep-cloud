<template>
  <!-- 授权用户 -->
  <el-dialog title="我的发票" :visible.sync="visible" width="1080px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" :inline="true">
          <el-form-item label="发票类别" prop="acctCode">
            <el-select v-model="queryParams.billType" clearable placeholder="请选择发票类型" style="width:240px">
              <el-option
                v-for="dict in dict.type.ao_bill_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发票日期" prop="acctName">
            <el-date-picker
              clearable
              v-model="billDates"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="baseList" @selection-change="handleSelectionChange"
                height="360px">
        <el-table-column type="selection" width="55" ></el-table-column>
        <el-table-column label="发票类型" align="center" prop="billType" show-overflow-tooltip>
          <template v-slot="scope">
            <dict-tag :options="dict.type.ao_bill_type" :value="scope.row.billType"/>
          </template>
        </el-table-column>
        <el-table-column label="发票号码" prop="billName" :show-overflow-tooltip="true"/>
        <el-table-column label="发票日期" prop="billDate" :show-overflow-tooltip="true"/>
        <el-table-column label="纳税人识别号" prop="saleTaxid" :show-overflow-tooltip="true"/>
        <el-table-column label="税前记账金额" prop="saleAmt" :show-overflow-tooltip="true"/>
        <el-table-column label="税额" prop="taxAmt" :show-overflow-tooltip="true"/>
        <el-table-column label="税后记账金额" prop="totalAmt" :show-overflow-tooltip="true"/>
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
      <el-button  @click="reset">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listExpenseBills } from "@/api/finance/ao/expenseBills";
export default {
  dicts: ['ao_bill_type'],
  props: ['companyId'],
  data() {
    return {
      // 开票日期区间
      billDates: [],
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
        billType: null,
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
      this.queryParams.status = '0';
      this.queryParams.params = {"beginDate":this.billDates?.[0],"endDate":this.billDates?.[1]};
      listExpenseBills(this.queryParams).then(response => {
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
      this.billDates = null;
      this.queryParams.billType = null;
      this.handleQuery();
    },
    reset() {
      this.visible = false;
      this.resetForm("queryForm");
      this.billDates = null;
      this.queryParams.billType = null;
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
