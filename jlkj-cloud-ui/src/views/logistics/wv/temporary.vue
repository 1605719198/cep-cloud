<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="临时用车申请单号" prop="applyNo">
        <el-input v-model="queryParams.applyNo"
                  type="text"
                  clearable
                  placeholder="请输入临时用车申请单号" />
      </el-form-item>
      <el-form-item>
        <el-button size="mini" type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button size="mini" icon="el-icon-refresh-left" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="5">计划台时总量：{{
          this.sumData.planMachineHourNumSum
        }}</el-col>
      <el-col :span="5">剩余台时总量：{{
          this.sumData.planSurplusMachineHourNumSum
        }}</el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="handleQuery"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tableData" :default-sort="{ prop: 'planDay', order: 'descending' }">
      <template v-for="column in columns">
        <el-table-column :fixed="column.fixed"
                         :prop="column.prop"
                         :label="column.label"
                         :sortable="column.sortable"
                         :width="column.width">
        </el-table-column>
      </template>
      <el-table-column label="操作"
                       fixed="right"
                       width="130"
                       header-align="center"
                       align="center">
        <template slot-scope="scope">
          <el-button size="mini"
                     plain
                     type="text"
                     @click="handleCheckView(scope.row)">
            查看运输实绩
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.page"
      :limit.sync="queryParams.limit"
      @pagination="handleQuery"
    />

    <!--               查看运输实际 -->
    <div v-if="openPlus">
      <el-dialog title="查看运输实际"
                 :visible.sync="openPlus"
                 width="900px"
                 :close-on-click-modal="false"
                 class="customDialogStyle"
                 append-to-body>
        <transportCompoent :id="logicId"></transportCompoent>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import { queryTempDayData } from "@/api/logistics/wv/temporary";
import transportCompoent from "@/views/logistics/wv/transportCompoent";
export default {
  name: "temporary",
  components: { transportCompoent },
  data () {
    return {
      // 显示搜索条件
      showSearch: true,
      CostCenterCostVisible: false,
      openText: false,
      // 遮罩层
      loading: false,
      openPlus: false,
      //台时量总和
      sumData: {
        planMachineHourNumSum: 0,
        planSurplusMachineHourNumSum: 0,
      },
      // 总条数
      total: 0,
      logicId: "",
      // 查询参数
      queryParams: {
        //临时用车申请单号
        applyNo: undefined,
        //当前页
        page: 1,
        //每页记录数
        limit: 10,
      },
      //表格数据
      tableData: [],
      //表格字段
      columns: [
        // {label: '计划年月', prop: "planMonth", sortable: true, type: 'month', minWidth: '80px'},
        {
          label: "申请单号",
          prop: "applyNo",
          sortable: true,
          width: "150",
          fixed: true,
        },
        {
          label: "起始地点",
          prop: "addressFromName",
          sortable: true,
          width: "140",
          fixed: true,
        },
        {
          label: "到达地点",
          prop: "addressToName",
          sortable: true,
          width: "140",
          fixed: true,
        },
        {
          label: "货物名称",
          prop: "goodsName",
          sortable: true,
          width: "120",
          fixed: true,
        },
        {
          label: "用车单位",
          prop: "departmentName",
          sortable: true,
          width: "120",
        },
        {
          label: "申请台时量",
          prop: "planMachineHourNum",
          sortable: true,
          width: "110",
        },
        { label: "申请车数", prop: "carNum", sortable: true },
        { label: "用车事由", prop: "reasonName", sortable: true, width: '120' },
        { label: "联系电话", prop: "phoneNo", sortable: true, width: '100' },
        { label: "车型", prop: "transportToolName", sortable: true },
        { label: "作业内容", prop: "workTypeName", sortable: true },
        { label: "成本中心", prop: "costBudgName", sortable: true },
        // {label: '称重计划单号', prop: "planPoundListNo", sortable: true,width: '160'},
        { label: "备注", prop: "memo", sortable: true },
      ],
    };
  },
  created () {
    this.getDataList()
  },
  methods: {
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    //查询数据
    getDataList () {
      this.loading = true;
      queryTempDayData(this.queryParams).then((response) => {
        this.tableData = response.data.dataList;
        this.sumData = response.data.sumData;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    //点击搜索查询
    handleQuery () {
      this.getDataList();
    },
    //导出操作
    handleExport () {
      alert("功能正在开发");
    },
    handleCheckView (row) {
      this.openPlus = true;
      this.logicId = row.logicId;
    },
  },
};
</script>

<style scoped>
/*.avue-crud .el-input {*/
/*  width: auto !important;*/
/*}*/
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
.customDialogStyle {
  animation: anim-open 1s ease;
}
@keyframes anim-open {
  0% {
    height: 0;
    overflow: hidden;
  }
  100% {
    height: 100vh;
    overflow: hidden;
  }
}
</style>
