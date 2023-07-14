<!--
  发电量实绩
-->
<template>
  <div class="avue-crud el-card__body" style="width: 98%;border: 0">
    <div class="avue-crud__search" style="border: 0">
      <el-form :inline="true">
        <el-row :gutter="20">
            <el-form-item prop="" label="日期">
              <el-date-picker v-model="query.daterange" type="daterange" value-format="yyyy-MM-dd" clearable
                              start-placeholder="开始日期" range-separator="至" end-placeholder="结束日期"/>
            </el-form-item>
            <el-form-item prop="">
              <el-button v-hasPermi="['getProductionPowerYieldListPerformanceList']"
                         size="mini" type="primary" icon="el-icon-search" @click="handleQuery">搜 索
              </el-button>
              <el-button size="mini" type="default" icon="el-icon-refresh-left" @click="handleEmpty">重 置</el-button>
            </el-form-item>
        </el-row>
      </el-form>
    </div>
    <div>
      <el-form>
        <el-form>
          <el-table height="64vh" size="small" :data="tableData" stripe @sort-change="handleSort"
                    v-loading="table.loading" :default-sort="{prop: 'number', order: 'descending'}">
            <template v-for="column in columns">
              <el-table-column :prop="column.prop"
                               :label="column.label"
                               :sortable="column.sortable"
                               :width="column.width"
                               :formatter="column.formatter"
                               :min-width="column.minWidth"
                               :key="column.prop"/>
            </template>
          </el-table>
          <div style="margin-top: 10px;float: right;padding:25px 0 20px 20px ;" class="avue-crud__pagination">
            <el-pagination background
                           @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
                           :current-page="page.currentPage"
                           :page-sizes="[20, 50, 100, 200]"
                           :page-size="page.pageSize"
                           :layout="page.layout"
                           :total="page.total">
            </el-pagination>
          </div>
        </el-form>
      </el-form>
    </div>
  </div>
</template>

<script>
  import {
    getProductionPowerPerformance,
    getProductionPowerYieldListPerformanceList
  } from "@/api/production/oi/actual/electricity/ProductionPowerPerformance"
  import {getDicts} from '@/api/system/dict/data'

  export default {
    name: "CokeCoefficientRecord",
    data() {
      return {
        page: {
          size: 20,
          current: 1,
          total: 1,
          order: "e_count_date",
          orderby: "desc",
          layout: "total, sizes, prev, pager, next, jumper",
        },
        query: {
          daterange: '',
          classType: '',
          shift: ''
        },
        table: {
          loading: true,
          border: true
        },
        columns: [
          // { label: '编号', prop: "编号", sortable: true, minWidth: '100px' },
          {label: '时间', prop: "e_count_date", sortable: true, minWidth: '80px'},
          {label: '类型', prop: "type", sortable: true, minWidth: '80px'},
          {label: '发电机组', prop: "generator_no", sortable: true, minWidth: '80px'},
          {label: '产量(万Kw·h)', prop: "qty", sortable: true, minWidth: '150px'},
        ],
        tableData: [],
      }
    },
    created() {
      this.getProductionPowerPerformance();
    },
    methods: {
      getProductionPowerPerformance() {
        let query = this.query;
        let page = this.page;
        let param = {
          size: page.size,
          current: page.current,
          // "order": "id",
          // "orderby": "asc",
          order: page.order,
          orderby: page.orderby,
          shift: query.shift,
          classes: query.classType,
          startDate: query.daterange === '' ? '' : query.daterange[0],
          endDate: query.daterange === '' ? '' : query.daterange[1],
        }
        this.table.loading = true;
        getProductionPowerYieldListPerformanceList(param).then(response => {
          this.table.loading = false;
          this.tableData = response.data.records;
          this.page.total = response.data.total;
        }, error => {
          this.table.loading = false;
          window.console.log(error);
        })
      },
      // 排序
      handleSort(column) {
        // console.log(column, "column")
        if (column.order === null) {
          //默认
          this.page.order = "e_count_date";
          this.page.orderby = "desc";
        } else {
          //选中项
          this.page.order = column.prop;
          this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
        }
        this.handleQuery();
      },
      //查询
      handleQuery() {
        this.getProductionPowerPerformance()
      },
      // 清空
      handleEmpty() {
        this.page.current = 1,
          this.query = {
            shift: '',
            classType: '',
            daterange: ''
          }
        this.getProductionPowerPerformance()
      },

      // 分页-每页多少条
      handleSizeChange(val) {
        this.page.size = val;
        this.getProductionPowerPerformance()
      },
      // 分页-当前页
      handleCurrentChange(val) {
        this.page.current = val;
        this.getProductionPowerPerformance()

      },
      //提供给子类调用父类的方法
      submitAdd(data) {
        console.log(JSON.stringify(data))
        this.addBox = false;
        this.editBox = false;
        this.$message({
          type: "success",
          message: "操作成功!"
        });
      },
      shiftFormatter(row) {
        switch (row.shift) {
          case "01":
            return "常白班";
          case "2201":
            return "白班";
          case "2202":
            return "夜班";
          case "00":
            return "休息";
          default:
            return row.shift;
        }
      }
    }
  }
</script>

<style scoped>
</style>
