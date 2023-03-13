<template>
  <div class="app-container">
    <el-form :model="query" ref="query" :inline="true"  label-width="68px">
      <el-form-item prop="queryDate" label="日期">
        <el-date-picker v-model="query.queryDate"
                        type="daterange"
                        format="yyyy-MM-dd"
                        value-format="yyyy-MM-dd"
                        start-placeholder="开始日期"
                        range-separator="至"
                        end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item prop="carrierNo" label="车号">
        <el-input v-model="query.carrierNo"
                  placeholder="车号" />
      </el-form-item>
      <el-form-item prop="carrier_type" label="运输方式">
        <el-select class="customSelectStyle"
                   :popper-append-to-body="false"
                   v-model="query.carrier_type"
                   clearable
                   placeholder="运输方式">
          <el-option v-for="item in shippingOptions"
                     :key="item.id"
                     :label="item.name"
                     :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="toDesc" label="客户名称">
        <el-input v-model="query.toDesc"
                  placeholder="客户名称" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" v-hasPermi="['cokesaledeliverplan_list']" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="table.loading" :data="tableData" @sort-change="handleSort" stripe height="67vh">
      <el-table-column label="序号"
                       width="55"
                       type="index"
                       align="left" />
      <el-table-column label="发货单号"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="plan_list_no" />
      <el-table-column label="运输方式"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="carrier_type"
                       :formatter="shippingMethodFormatter" />
      <el-table-column label="车号"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="carrier_no" />
      <el-table-column label="料号"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="mtrl_no" />
      <!-- <el-table-column label="储位代码"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="from_code" /> -->
      <el-table-column label="客户编号"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="to_code" />
      <el-table-column label="客户名称"
                       sortable
                       minWidth="260"
                       align="left"
                       prop="to_desc" />
      <el-table-column label="合同号"
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
      <!-- <el-table-column label="过磅点"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="load_station" /> -->
      <el-table-column label="开单人单位名称"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="issue_dept_name" />
      <el-table-column label="开单人姓名"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="issue_emp_name" />
      <el-table-column label="申请日期"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="issue_date" />
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
import { listMaterialsCokeSaleDeliverPlan } from "@/api/material/mr/materialsCokeSaleDeliverPlan";

export default {
  data () {
    return {
      shippingOptions: [
        { id: '', name: '全部' },
        { id: 'Q', name: '汽运' },
        { id: 'T', name: '铁运' }
      ],
      page: {
        size: 20,
        current: 1,
        total: 1,
        order: "issue_date",
        orderby: "desc",
      },
      query: {
        startTime: '',
        endTime: '',
        carrierNo: '',
        carrierType: '',
        toDesc: '',
        queryDate: null,
        carrier_type: null
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
      if (this.query.queryDate && this.query.queryDate[0]) {
        this.query.startTime = this.query.queryDate[0]
        this.query.endTime = this.query.queryDate[1]
      } else {
        this.query.startTime = ''
        this.query.endTime = ''
      }
      this.table.loading = true;//加载状态
      listMaterialsCokeSaleDeliverPlan(this.page, this.query).then(res => {
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
      this.onLoad();
    },
    // 重置
    handleReset () {
      this.page.current = 1;
      this.$refs["query"].resetFields();
      this.handleQuery()
    },
    //提供给子类调用的方法
    submitSave () {
      this.dialog.visible = false;
      this.dialog.data = {};
      this.onLoad();
    },
    //格式化运输方式数据
    shippingMethodFormatter (row) {
      if (row.carrier_type === "T") {
        return "铁运";
      } else if (row.carrier_type === "Q") {
        return "汽运";
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
    }
  },
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
</style>

