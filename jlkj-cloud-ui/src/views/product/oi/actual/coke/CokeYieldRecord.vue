<!--
  焦炭产量记录
-->
<template>
  <div class="avue-crud el-card__body"
       style="width: 98%;border: 0">
    <div class="avue-crud__search"
         style="border: 0">
      <el-form :inline="true">
        <el-row :gutter="20">
            <div class="el-form-item el-form-item--small">
              <label class="el-form-item__label">起始日期</label>
              <div class="el-form-item__content">
                <el-date-picker v-model="query.startTime"
                                type="date"
                                placeholder="选择起始日期"
                                value-format="yyyy-MM-dd"/>
              </div>
            </div>
            <div class="el-form-item el-form-item--small">
              <label class="el-form-item__label">结束日期</label>
              <div class="el-form-item__content">
                <el-date-picker v-model="query.end_time"
                                type="date"
                                placeholder="选择结束日期"
                                value-format="yyyy-MM-dd"/>
              </div>
            </div>
            <div class="el-form-item el-form-item--small">
              <label class="el-form-item__label">班别</label>
              <div class="el-form-item__content">
                <el-select v-model="query.className"
                           placeholder="选择班别"
                           clearable>
                  <el-option v-for="item in classTypeOptions"
                             :key="item.value"
                             :label="item.label"
                             :value="item.value">
                  </el-option>
                </el-select>
              </div>
            </div>
            <div class="el-form-item el-form-item--small">
              <label class="el-form-item__label">班次</label>
              <div class="el-form-item__content">
                <el-select v-model="query.shiftName"
                           placeholder="班次"
                           clearable>
                  <el-option v-for="item in shiftOptions"
                             :key="item.value"
                             :label="item.label"
                             :value="item.value">
                  </el-option>
                </el-select>
              </div>
            </div>
            <div class="el-form-item__content"
                 style="margin-left: 0px;">
              <el-button v-hasPermi="['getCokeItemYieldList']"
                         size="mini"
                         type="primary"
                         icon="el-icon-search"
                         @click="handleQuery">搜 索
              </el-button>
              <el-button size="mini"
                         type="default"
                         icon="el-icon-refresh-left"
                         @click="handleEmpty">重 置
              </el-button>
            </div>
        </el-row>
      </el-form>
    </div>
    <div>
      <el-form>
        <el-table height="66vh" size="small" :data="tableData" stripe v-loading="table.loading"
                  @sort-change="handleSort">
          <template v-for="column in columns">
            <el-table-column :prop="column.prop"
                             :label="column.label"
                             :sortable="column.sortable"
                             :width="column.width"
                             :min-width="column.minWidth"
                             :type="column.type"
                             :formatter="column.formatter"
                             :key="column.prop"/>
          </template>
        </el-table>
        <div style="margin-top: 10px;float: right;padding:25px 0 20px 20px ;"
             class="avue-crud__pagination">
          <el-pagination background
                         @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"
                         layout="total, sizes, prev, pager, next, jumper"
                         :current-page="page.currentPage"
                         :page-sizes="[20, 50, 100, 200]"
                         :page-size="page.pageSize"
                         :total="page.total">
          </el-pagination>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
  import {sel} from "@/api/production/oi/CY.js";
  import { getDicts } from '@/api/system/dict/data'

  export default {
    name: "CokeYieldRecord",
    computed: {
    },
    data() {
      return {
        page: {
          pageSize: 20,
          currentPage: 1,
          total: 1,
          order: "class_start_time",
          orderby: "desc"
        },
        currentPage: 0,
        total: 0,
        classTypeOptions: [],
        shiftOptions: [],
        query: {
          startTime: '',
          endTime: '',
          className: '',
          shiftName: ''
        },
        table: {
          border: true,
          loading: true
        },
        columns: [
          {label: '编号', type: "index", minWidth: '100px'},
          {label: '班别', prop: "class_name", sortable: true, minWidth: '80px'},
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
          {label: '班组开始时间', prop: "class_start_time", sortable: true, minWidth: '100px'},
          {label: '班组结束时间', prop: "class_end_time", sortable: true, minWidth: '100px'},
          {label: '产品名称', prop: "material_name", sortable: true, minWidth: '100px'},
          {
            label: '产出量(t)', prop: "material_weight", sortable: true, minWidth: '50px',
            formatter(row) {
              return row.material_weight / 1000
            }
          },
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
      //查询
      handleQuery() {
        let query = this.query;
        let page = this.page;
        let params = {
          "shiftName": query.shiftName,
          "className": query.className,
          "startTime": query.startTime === "" ? "" : query.startTime,
          "endTime": query.endTime === "" ? "" : query.endTime,
          "size": page.pageSize,
          "current": page.currentPage,
          "order": this.page.order,
          "orderby": this.page.orderby
        }
        this.table.loading = true;
        sel(params).then(response => {
          this.table.loading = false;
          this.tableData = response.data.records;
          this.page.total = response.data.total;
        }, error => {
          this.table.loading = false;
          window.console.log(error);
        })
      },
      // 清空
      handleEmpty() {
        this.query = {
          startTime: '',
          endTime: '',
          className: '',
          shift: '',
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
      // 排序
      handleSort(column) {
        if (column.order === null) {
          //默认
          this.page.order = "class_start_time";
          this.page.orderby = "desc";
        } else {
          //选中项
          this.page.order = column.prop;
          this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
        }
        this.handleQuery();
      },
      //提供给子类调用父类的方法
      // submitAdd (data) {
      //   console.log(JSON.stringify(data))
      //   this.addBox = false;
      //   this.editBox = false;
      //   this.$message({
      //     type: "success",
      //     message: "操作成功!"
      //   });
      // },
    }
  }
</script>

<style scoped>
</style>
