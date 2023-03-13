<template>
  <div class="app-container">
    <el-form :model="query" ref="query" :inline="true" label-width="68px">
      <el-form-item prop="issueDate" label="申请日期">
        <el-date-picker v-model="query.issueDate"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="申请开始日期"
                        end-placeholder="申请结束日期"
                        value-format="yyyy-MM-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" v-hasPermi="['listMaterialsWeighingPlan']" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table height="67vh"
              v-loading="table.loading"
              :data="tableData"
              stripe
              @sort-change="handleSort">
      <el-table-column label="计划单号"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="plan_list_no" />
      <el-table-column label="运输方式"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="carrier_type"
                       :formatter="carrierFormatter" />
      <el-table-column label="编组号"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="lot_no" />
      <el-table-column label="车号"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="carrier_no" />
      <el-table-column label="料号说明"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="mtrl_name" />
      <el-table-column label="来源说明"
                       sortable
                       minWidth="260"
                       align="left"
                       prop="from_desc" />
      <el-table-column label="去向说明"
                       sortable
                       minWidth="260"
                       align="left"
                       prop="to_desc" />
      <el-table-column label="采购合同号"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="pchr_no" />
      <el-table-column label="是否过磅"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="is_load"
                       :formatter="isLoadFormatter" />
      <el-table-column label="过磅点"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="load_station" />
      <el-table-column label="申请单位"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="issue_dept" />
      <el-table-column label="申请人"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="issue_emp_no" />
      <el-table-column label="申请日期"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="issue_date" />
      <el-table-column label="是否回皮"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="is_back"
                       :formatter="isBackFormatter" />
    </el-table>

    <pagination v-show="page.total > 0"
                background
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                layout="total, sizes, prev, pager, next, jumper"
                :current-page="page.current"
                :page-sizes="[20, 50, 100, 200]"
                :page-size="page.size"
                :total="page.total">
    </pagination>
  </div>
</template>


<script>
import { listMaterialsWeighingPlan } from "@/api/material/mr/inspectionCattiesPlan/inspectionCattiesPlan";
export default {
  data () {
    return {
      page: {
        size: 20,
        current: 1,
        total: 1,
        order: "issue_date",
        orderby: "decs",
      },
      query: {
        issueDate: null
      },
      table: {
        border: true,
        loading: true,
      },
      tableData: [],
    }
  },
  created () {
    this.onLoad();
  },
  methods: {
    //载入数据
    onLoad () {
      if (this.query.issueDate && this.query.issueDate[0]) {
        this.query.issueDateStart = this.query.issueDate[0]
        this.query.issueDateEnd = this.query.issueDate[1]
      } else {
        this.query.issueDateStart = ''
        this.query.issueDateEnd = ''
      }
      this.table.loading = true;//加载状态
      listMaterialsWeighingPlan(this.page, this.query).then(res => {
        this.table.loading = false;
        let data = res.data;//表格相关数据
        this.page.total = data.total;//数据总数
        this.tableData = data.records;//表格数据
      }, error => {
        this.table.loading = false;
        window.console.log(error);
      });

    },
    // 分页-每页多少条
    handleSizeChange (val) {
      this.page.size = val;
      this.onLoad();
    },
    // 分页-当前页
    handleCurrentChange (val) {
      this.page.current = val;
      this.onLoad();
    },
    // 排序
    handleSort (column) {
      // console.log(column, "column")
      if (column.order === null) {
        //默认
        this.page.order = "issue_date";
        this.page.orderby = "desc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.onLoad();
    },
    //查询
    handleQuery () {
      this.page.current = 1;
      // console.log(this.query.issueDate)
      this.onLoad();
    },
    // 重置
    handleReset () {
      this.page.current = 1
      this.$refs["query"].resetFields();
      this.handleQuery()
    },
    carrierFormatter (row) {
      switch (row.carrier_type) {
        case "T":
          return "铁运";
        case "Q":
          return "汽运";
        default:
          return row.carrier_type;
      }
    },
    isLoadFormatter (row) {
      switch (row.is_load) {
        case "Y":
          return "是";
        case "N":
          return "否";
        default:
          return row.is_load;
      }
    },
    isBackFormatter (row) {
      switch (row.is_back) {
        case "Y":
          return "是";
        case "N":
          return "否";
        default:
          return row.is_back;
      }
    }
  }
}
</script>

<style scoped lang="scss">
.el-card__body {
  padding: 10px 20px 20px 10px;
}
.main {
  // height: 86vh;
  background-color: #fff;
}
.search {
  width: 100%;
  display: flex;
  justify-content: space-between;
}
::v-deep.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
.pagination {
  white-space: nowrap;
  padding: 25px 0px 20px 0px;
  color: #303133;
  font-weight: 700;
}
</style>

