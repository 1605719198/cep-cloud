<template>
  <div class="app-container">
    <el-form :model="query" ref="query" :inline="true"  label-width="68px">
      <el-form-item prop="createDate" label="到货日期">
        <el-date-picker v-model="query.createDate"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="到货开始日期"
                        end-placeholder="到货结束日期"
                        value-format="yyyy-MM-dd">
        </el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" v-hasPermi="['listMaterialsStorage']" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="table.loading" :data="tableData" @sort-change="handleSort" height="67vh">
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
      <el-table-column label="到货日期"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="recv_date" />
      <el-table-column label="车号"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="carrier_no" />
      <el-table-column label="储位说明"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="stg_name" />
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
      <el-table-column label="榜单号"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="wgt_list_no" />
      <el-table-column label="检斤实绩时间"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="load_date_time" />
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
import { listMaterialsStorage } from "@/api/material/mr/storeInformation/storeInformation";
export default {
  data () {
    return {
      page: {
        size: 20,
        current: 1,
        total: 1,
        order: "recv_date",
        orderby: "desc",
      },
      query: {
        createDate: null
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
      if (this.query.createDate && this.query.createDate[0]) {
        this.query.createDateStart = this.query.createDate[0]
        this.query.createDateEnd = this.query.createDate[1]
      } else {
        this.query.createDateStart = ''
        this.query.createDateEnd = ''
      }
      this.table.loading = true;//加载状态
      listMaterialsStorage(this.page, this.query).then(res => {
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
        this.page.order = "recv_date";
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
      // console.log(this.query.value1)
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
    }
  }
}
</script>

<style scoped lang="scss">
.el-card__body {
  padding: 10px 20px 20px 10px;
}
.pagination {
  white-space: nowrap;
  padding: 25px 0px 20px 0px;
  color: #303133;
  font-weight: 700;
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
</style>

