<!--
  蒸汽产量实绩
-->
<template>
  <div class="avue-crud el-card__body" style="width: 98%;border: 0">
    <div class="avue-crud__search" style="border: 0">
      <el-form>
        <el-row :gutter="20">
          <el-col :span="4">
            <el-form-item prop="">
              <el-date-picker v-model="query.daterange" type="daterange" value-format="yyyy-MM-dd" clearable
                              start-placeholder="开始日期" range-separator="至" end-placeholder="结束日期"/>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item prop="">
              <el-button v-hasPermi="['getProductionSteamPerformanceList']"
                         size="medium" type="primary" icon="el-icon-search" @click="handleQuery">搜 索
              </el-button>
              <el-button size="medium" type="default" icon="el-icon-refresh-left" @click="handleEmpty">重 置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div>
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
        <div style="margin-top: 10px;right: 0;padding:25px 0 20px 20px ;" class="avue-crud__pagination">
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
    </div>
  </div>
</template>

<script>
  import {getProductionSteamPerformanceList} from "@/api/production/oi/actual/electricity/ProductionSteamYieldPerformanceApi";
  import {getDicts} from '@/api/system/dict/data'

  export default {
    name: "CokeCoefficientRecord",
    data() {
      return {
        page: {
          pageSize: 20,
          currentPage: 1,
          total: 1,
          order: "e_count_date",
          orderby: "desc",
          layout: "total, sizes, prev, pager, next, jumper",
        },
        classTypeOptions: [],
        shiftOptions: [],
        query: {
          daterange: '',
          classType: '',
          shift: ''
        },
        table: {
          border: true,
          loading: false,
        },
        columns: [
          // { label: '编号', prop: "编号", sortable: true, minWidth: '100px' },
          {label: '时间', prop: "e_count_date", sortable: true, minWidth: '80px'},
          {label: '类型', prop: "energy_code_name", sortable: true, minWidth: '80px'},
          {label: '锅炉号', prop: "boiler_no", sortable: true, minWidth: '80px'},
          {label: '产量(m³)', prop: "qty", sortable: true, minWidth: '150px'},
        ],
        tableData: [],
      }
    },
    created() {
      this.handleQuery();
    },
    methods: {
      //查询
      handleQuery() {
        let params = {
          // shift: this.query.shift,
          // classes: this.query.classType,
          start_date: this.query.daterange === null || this.query.daterange[0] === "" ? "" : this.query.daterange[0],
          end_date: this.query.daterange === null || this.query.daterange[1] === "" ? "" : this.query.daterange[1],

          size: this.page.pageSize,
          current: this.page.currentPage,
          // order: "id",
          // orderby: "asc",
          order: this.page.order,
          orderby: this.page.orderby,
        }
        this.table.loading = true;
        getProductionSteamPerformanceList(params).then(res => {
          this.table.loading = false;
          if (res.code === 200) {
            this.tableData = res.data.records;
            this.page.total = res.data.total;
          }
        }, err => {
          this.table.loading = false;
          window.console.log(err);
        });

      },
      // 排序
      handleSort(column) {
        // console.log(column, "column")
        if (column.order === null) {
          //默认
          this.page.order = "stat_time";
          this.page.orderby = "desc";
        } else {
          //选中项
          this.page.order = column.prop;
          this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
        }
        this.handleQuery();
      },
      // 清空
      handleEmpty() {
        this.query = {
          daterange: '',
          classType: '',
          shift: ''
        }
        this.handleQuery();
      },

      // 分页-每页多少条
      handleSizeChange(val) {
        this.page.pageSize = val;
        this.handleQuery();
      },
      // 分页-当前页
      handleCurrentChange(val) {
        this.page.currentPage = val;
        this.handleQuery();
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
    },
    mounted() {
      this.handleQuery();
    }
  }
</script>

<style scoped>
</style>
