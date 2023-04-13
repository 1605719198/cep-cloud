<!--
  出炉实绩测温记录
-->
<template>
  <div class="avue-crud el-card__body" style="width: 98%;border: 0">
    <div class="avue-crud__search" style="border: 0">
      <el-form :inline="true">
        <el-row :gutter="20">
            <div class="el-form-item el-form-item--small">
              <label class="el-form-item__label">日期</label>
              <div class="el-form-item__content">
                <el-date-picker v-model="query.daterange" type="daterange" range-separator="至" start-placeholder="开始日期"
                  end-placeholder="结束日期" value-format="yyyy-MM-dd" />
              </div>
            </div>
            <div class="el-form-item el-form-item--small">
              <label class="el-form-item__label">班别</label>
              <div class="el-form-item__content">
                <el-select v-model="query.class_name" placeholder="选择班别" clearable>
                  <el-option v-for="item in classTypeOptions" :key="item.value" :label="item.label" :value="item.label">
                  </el-option>
                </el-select>
              </div>
            </div>
            <div class="el-form-item el-form-item--small">
              <label class="el-form-item__label">班次</label>
              <div class="el-form-item__content">
                <el-select v-model="query.shift_name" placeholder="班次" clearable>
                  <el-option v-for="item in shiftOptions" :key="item.value" :label="item.label" :value="item.value">
                  </el-option>
                </el-select>
              </div>
            </div>
            <div class="el-form-item__content" style="margin-left: 0px;">
              <el-button v-hasPermi="['getProductionOutputPerformanceTemperaturePage']" size="mini" type="primary"
                icon="el-icon-search" @click="handleQuery">搜 索
              </el-button>
              <el-button size="mini" type="default" icon="el-icon-refresh-left" @click="handleEmpty">重 置
              </el-button>
            </div>
        </el-row>
      </el-form>
    </div>
    <div>
      <el-form>
        <el-table height="66vh" size="small" :data="tableData" stripe @sort-change="handleSort" v-loading="table.loading">

          <template v-for="column in columns">
            <el-table-column :prop="column.prop" :label="column.label" :sortable="column.sortable" :width="column.width"
              :min-width="column.minWidth" :key="column.prop" :formatter="column.formatter" />
          </template>
        </el-table>

        <div style="margin-top: 10px;float: right;padding:25px 0 20px 20px ;" class="avue-crud__pagination">
          <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
            layout="total, sizes, prev, pager, next, jumper" :current-page="page.currentPage"
            :page-sizes="[20, 50, 100, 200]" :page-size="page.pageSize" :total="page.total">
          </el-pagination>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { sel } from "@/api/production/oi/OT.js";
import { getDicts } from '@/api/system/dict/data'

export default {
  name: "OutActualTemparetureRecord",
  computed: {
  },

  data() {
    return {
      page: {
        total: 1,
        pageSize: 20,
        currentPage: 1,
        order: "measure_time",
        orderby: "desc",
      },
      currentPage: 0,
      total: 0,
      classTypeOptions: [],
      shiftOptions: [],
      query: {
        daterange: '',
        class_name: '',
        shift_name: '',
        measure_time: '',
      },
      table: {
        border: true,
        loading: true
      },
      columns: [
        { label: '测温日期', prop: "measure_time", sortable: true, minWidth: '150px' },
        { label: '班别', prop: "class_name", sortable: true, minWidth: '80px' },
        {
          label: '班次', prop: "shift_name", sortable: true, minWidth: '80px', formatter(row) {
            switch (row.shift_name) {
              case "01":
                return "常白班";
              case "2201":
                return "白班";
              case "2202":
                return "夜班";
              case "00":
                return "休息";
              default:
                return row.shift_name;
            }
          }
        },
        {
          label: '炉号', prop: "coke_oven_number", sortable: true, minWidth: '80px', formatter(row) {
            return row.coke_oven_number + '#';
          }
        },
        {
          label: '测温类型', prop: "measure_type", sortable: true, minWidth: '80px', formatter(row) {
            switch (row.measure_type) {
              case 1:
                return "焦侧";
              case 2:
                return "机侧";
              default:
                return ''
            }
          }
        },
        { label: '标准温度℃', prop: "standard_temp", sortable: true, minWidth: '80px' },
        // {
        //   label: '高/低', prop: "high", sortable: true, minWidth: '80px', formatter (row) {
        //     return row.high + '/' + row.low;
        //   }
        // },
        { label: '最高温度℃', prop: "maximum_temp", sortable: true, minWidth: '80px' },
        { label: '最低温度℃', prop: "minimum_temp", sortable: true, minWidth: '80px' },
        { label: '平均温度℃', prop: "average_temp", sortable: true, minWidth: '80px' },
      ],
      tableData: [],
    }
  },
  mounted() {
    getDicts("sys_classtype").then(response => {
      this.classTypeOptions = response.data.map((i)=>{
        return { value: i.dictLabel, label:  i.dictLabel };
      });
    });
    getDicts("sys_shift_no").then(response => {
      this.shiftOptions = response.data.map((i)=>{
        return { value: i.dictValue, label:  i.dictLabel };
      });
    });
    this.handleQuery();
  },
  methods: {
    handleQuery() {
      let query = this.query
      let page = this.page;
      let starDate = query.daterange === "" ? "" : query.daterange[0] + " 00:00:00";
      let endDate = query.daterange === "" ? "" : query.daterange[1] + " 23:59:59";
      let params = {
        "className": query.class_name,
        "shiftName": query.shift_name,
        "startTime": starDate,
        "endTime": endDate,
        "size": page.pageSize,
        "current": page.currentPage,
        // "order": "measure_time",
        // "orderby": "desc",
        "order": page.order,
        "orderby": page.orderby,
      }
      this.table.loading = true;
      sel(params).then(res => {
        this.table.loading = false;
        this.tableData = res.data.records;
        this.page.total = res.data.total;
        if (this.tableData.length === 0) {
          this.$message({ type: "warning", message: "暂无数据" });
        }
      }, error => {
        window.console.log(error);
      })
    },
    //查询
    // handleQuery () {
    //     let query = this.query
    //     let page = this.page;
    //     let params = {
    //     "class_name": query.class_name,
    //     "shift_name": query.shift_name,
    //     "start_time": query.daterange[0] === '' ? '' : this.query.daterange[0],
    //     "end_time": query.daterange[1] === '' ? '' : this.query.daterange[1],
    //     "size": page.pageSize,
    //     "current": page.currentPage,
    //     "order": page.order,
    //     "orderby": page.orderby,
    //     "measure_time":query.measure_time
    //   }
    //   sel(params).then(res => {
    //       console.log(res)
    //       this.tableData = res.data.data.records;
    //       this.page.total = res.data.data.total;
    //       if (this.tableData.length === 0) {
    //         this.$message({ type: "warning", message: "暂无数据" });
    //       }
    //     }, error => {
    //       window.console.log(error);
    //     })
    // },
    // 清空
    handleEmpty() {
      this.query = {
        daterange: '',
        class_name: '',
        shift_name: '',
        measure_time: '',
      }
      this.handleQuery();
    },
    // 排序
    handleSort (column) {
      // console.log(column, "column")
      if (column.order === null) {
        //默认
        this.page.order = "measure_time";
        this.page.orderby = "desc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.handleQuery();
    },
    //查
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
    // submitSave (msg) {
    //   this.openDialog.open = false;
    //   this.openDialog.data = {};
    //   this.onLoad();
    // },
  }
}
</script>

<style scoped>

</style>
