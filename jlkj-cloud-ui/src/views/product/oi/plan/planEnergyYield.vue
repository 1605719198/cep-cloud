<template>
  <div class="app-container">
    <el-form :model="query" ref="query" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="计划年份" prop="planYear">
        <el-date-picker v-model="query.planYear"
                        type="year"
                        placeholder="计划年份"
                        value-format="yyyy" />
      </el-form-item>
      <el-form-item label="计划月份"
                    prop="planMonth">
        <el-date-picker v-model="query.planMonth"
                        type="month"
                        placeholder="计划月份"
                        format="MM"
                        value-format="M" />
      </el-form-item>
      <el-form-item>
        <el-button size="mini"
                   type="primary"
                   icon="el-icon-search"
                   @click="handleQuery">搜索</el-button>
        <el-button size="mini"
                   icon="el-icon-refresh-left"
                   @click="handleEmpty">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="table.loading" :data="tableData" stripe @sort-change="handleSort">
      <el-table-column type="index"
                       label="序号"
                       :index="indexMethod"
                       align="center"
                       sortable />
      <template v-for="(column,index)  in columns">
        <el-table-column v-if="column.customStyle"
                         :key="index"
                         :prop="column.prop"
                         :label="column.label"
                         :type="column.type"
                         :width="column.width"
                         :min-width="column.minWidth"
                         :sortable="column.sortable">
          <template slot-scope="scope">
            <div v-html="column.customStyle(scope.row,scope.$index,scope)"></div>
          </template>
        </el-table-column>
        <el-table-column v-else
                         :key="index"
                         :prop="column.prop"
                         :label="column.label"
                         :type="column.type"
                         :width="column.width"
                         :min-width="column.minWidth"
                         :sortable="column.sortable"
                         :formatter="column.formatter" />
      </template>
    </el-table>

    <pagination
      v-show="page.total>0"
      :total="page.total"
      :page.sync="page.current"
      :limit.sync="page.size"
      @pagination="handleQuery"
    />

  </div>
</template>

<script>
import { req } from "@/api/production/oi/common";
import planYieldEdit from "./planYieldEdit";

export default {
  components: {
    planYieldEdit
  },
  name: "planEnergyYield",
  data () {
    return {
      showSearch: true,
      openDialog: { open: false, type: '', title: '', width: '500px', data: {}, },
      page: { size: 10, current: 1, total: 0, order: "planDate", orderby: "desc", },
      query: { planYear: '', planMonth: '', },
      queryYear: '', queryMonth: '',
      table: { border: true, loading: true, },
      columns: [
        // { prop: "serial_no", label: "序号", sortable: true, minWidth: "150px" },
        { prop: "factory_name", label: "工序名称", sortable: true, minWidth: "150px" },
        { prop: "factory_area", label: "作业区", sortable: true, minWidth: "150px" },
        { prop: "planDate", label: "计划日期", sortable: true, minWidth: "150px" },
        { prop: "plan_value", label: "计划产量", sortable: true, minWidth: "150px" },
        { prop: "unit", label: "单位", sortable: true, minWidth: "150px" },
        { prop: "memo", label: "成本中心", sortable: true, minWidth: "150px" },
        { prop: "create_date", label: "新增时间", sortable: true, minWidth: "150px" },
        { prop: "create_emp_no", label: "新增人员", sortable: true, minWidth: "150px" },
        { prop: "update_date", label: "修改时间", sortable: true, minWidth: "150px" },
        { prop: "update_emp_no", label: "修改人员", sortable: true, minWidth: "150px" },
      ],
      tableData: [],
    }
  },
  created () {
    this.onLoad();
  },
  methods: {
    // 序号设置
    indexMethod (index) {
      index = (index + 1) + (this.page.current - 1) * this.page.size
    },
    //查询
    handleQuery () {
      this.onLoad();
    },
    onLoad () {
      this.table.loading = true;//加载状态
      let data = { ...this.page, ...this.query };
      req('get', 'listEnergyOutputPlans', data).then(res => {
        this.table.loading = false;
        this.tableData = res.data.records;//表格数据
        this.page.total = res.data.total;
      }, error => {
        this.table.loading = false;
        window.console.log(error);
      });
    },
    // 排序
    handleSort (column) {
      if (column.order === null) {
        //默认
        this.page.order = "planDate";
        this.page.orderby = "desc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.onLoad();
    },
    // 清空
    handleEmpty () {
      this.queryYear = '';
      this.queryMonth = '';
      this.$refs["query"].resetFields();
      this.onLoad();
    },
  }
}
</script>

<style scoped>
</style>
