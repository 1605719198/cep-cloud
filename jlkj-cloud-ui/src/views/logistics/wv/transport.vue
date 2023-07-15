<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="货物名称" prop="goodsName">
        <el-input v-model="queryParams.goodsName"
                  type="text"
                  clearable
                  placeholder="请输入货物名称" />
      </el-form-item>
      <el-form-item label="起址" prop="addressFromName">
        <el-input v-model="queryParams.addressFromName"
                  type="text"
                  clearable
                  placeholder="请输入起址" />
      </el-form-item>
      <el-form-item label="迄址" prop="addressToName">
        <el-input v-model="queryParams.addressToName"
                  type="text"
                  clearable
                  placeholder="请输入迄址" />
      </el-form-item>
      <el-form-item label="车牌号" prop="carNo">
        <el-input v-model="queryParams.carNo"
                  type="text"
                  clearable
                  placeholder="请输入车牌号" />
      </el-form-item>
      <el-form-item label="日期">
        <el-date-picker v-model="dateRange"
                        type="daterange"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        @change="dateChange"
                        value-format="yyyy-MM-dd"
                        format="yyyy-MM-dd" />
      </el-form-item>
      <el-form-item>
        <el-button size="mini" type="primary" @click="handleQuery" icon="el-icon-search">搜索</el-button>
        <el-button size="mini" icon="el-icon-refresh-left" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="tableData" :default-sort="{ prop: 'planDay', order: 'descending' }">
      <el-table-column type="index"
                       label="序号"
                       :index="indexMethod"
                       align="center" />
      <template v-for="column in columns">
        <el-table-column :fixed="column.fixed"
                         :prop="column.prop"
                         :label="column.label"
                         :sortable="column.sortable"
                         :width="column.width">
        </el-table-column>
      </template>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.page"
      :limit.sync="queryParams.limit"
      @pagination="handleQuery"
    />
  </div>
</template>

<script>
import { queryInfo } from "@/api/logistics/wv/transport.js";
export default {
  name: "transport",
  components: {},
  data () {
    return {
      // 显示搜索条件
      showSearch: true,
      // 遮罩层
      loading: false,
      // 表格数据
      tableData: [],
      // 日期区间
      dateRange: [],
      // 查询参数
      queryParams: {
        // 车牌号
        carNo: undefined,
        // 迄址
        addressToName: undefined,
        // 起址
        addressFromName: undefined,
        // 货物名称
        goodsName: undefined,
        //当前页
        pageNum: 1,
        //每页记录数
        pageSize: 10,
        // 日期区间
        dateRange: [],

      },
      // 总条数
      total: 0,
      //表格字段
      columns: [
        { label: "履运单号", prop: "carExecuteNo", fixed: false },
        { label: "起址", prop: "addressFromName", fixed: false },
        { label: "迄址", prop: "addressToName", fixed: false },
        { label: "货物名称", prop: "goodsName", fixed: false },
        { label: "数量", prop: "completePoundNum" },
        { label: "台时", prop: "completeMachineHourNum" },
        { label: "车行", prop: "venderName" },
        { label: "车型", prop: "carTypeName" },
        { label: "车号", prop: "carNo" },
        { label: "作业内容", prop: "workTypeName" },
        { label: "称重计划单号", prop: "planPoundListNo" },
      ],
    };
  },
  created () {
    this.handleQuery();
  },
  methods: {
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 选择日期区间
    dateChange (value) {
      this.queryParams.dateRange = value;
    },

    //查询
    handleQuery () {
      queryInfo(this.queryParams).then((res) => {
        this.tableData = res.data.records;
        this.total = res.data.total;
        this.queryParams.pageSize = res.data.size;
        this.queryParams.pageNum = res.data.current;
      });
    },
    // 导出操作
    handleExport () {
      alert("功能正在开发");
    },
    // 序号设置
    indexMethod (index) {
      index = index + 1 + (this.queryParams.pageNum - 1) * this.queryParams.pageSize;
      return index;
    },
  },
};
</script>

<style scoped>
.plan_header {
  background-color: #f9f9f9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #e9e9e9 solid;
}

.plan_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold;
}

.plan_main {
  height: 86vh;
  background-color: #fff;
}
</style>
  <style>
.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}

/* 弹窗圆角 */
.customDialogStyle .el-dialog {
  border-radius: 8px;
}

/* 标题样式 */
.customDialogStyle .el-dialog__header {
  background-color: rgb(245, 245, 245);
  border-radius: 8px 8px 0 0;
  padding: 10px 20px 10px;
}

/* 标题文字样式 */
.customDialogStyle .el-dialog__title {
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  letter-spacing: normal;
  font-size: 14px;
  font-weight: 700;
  font-style: normal;
  text-align: left;
  line-height: 20px;
  border-width: 0;
  color: rgb(102, 102, 102);
}

/* 关闭符号位置调整 */
.customDialogStyle .el-dialog__headerbtn {
  top: 12px;
}

.customDialogStyle .el-dialog__body {
  padding: 15px 40px;
}
</style>
