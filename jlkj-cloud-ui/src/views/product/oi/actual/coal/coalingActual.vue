<!--
  上煤实绩
-->
<template>
  <div class="avue-crud el-card__body"
       style="width: 98%;border: 0">
    <div class="avue-crud__search"
         style="border: 0">
      <el-form :model="query"
               :inline="true"
               ref="query">
        <el-row :gutter="20">
          <!-- <el-col :span="3">
  <div class="el-form-item el-form-item--small">
    <div class="el-form-item__content">
      <el-date-picker v-model="query.startTime"
                      type="date"
                      value-format="yyyy-MM-dd"
                      placeholder="选择起始日期" />
    </div>
  </div>
</el-col>
<el-col :span="3">
  <div class="el-form-item el-form-item--small">
    <div class="el-form-item__content">
      <el-date-picker v-model="query.endTime"
                      type="date"
                      value-format="yyyy-MM-dd"
                      placeholder="选择结束日期" />
    </div>
  </div>
</el-col>
<el-col :span="3">
  <div class="el-form-item el-form-item--small">
    <div class="el-form-item__content">
      <el-select v-model="query.classType"
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
</el-col>

<el-col :span="3">
  <div class="el-form-item el-form-item--small">
    <div class="el-form-item__content">
      <el-select v-model="query.shift"
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
</el-col> -->
            <el-form-item prop="shiftName" label="班次">
              <el-select placeholder="选择班次"
                         v-model="query.shiftName"
                         clearable
                         :popper-append-to-body="false"
                         class="customSelectStyle">
                <el-option v-for="item in shiftOptions"
                           :key="item.value"
                           :label="item.label"
                           :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="className" label="班别">
              <el-select placeholder="选择班别"
                         v-model="query.className"
                         clearable
                         :popper-append-to-body="false"
                         class="customSelectStyle">
                <el-option v-for="item in classTypeOptions"
                           :key="item.value"
                           :label="item.label"
                           :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="cokeMaterialsCode" label="料号名称">
              <el-select :popper-append-to-body="false"
                         class="customSelectStyle"
                         v-model="query.cokeMaterialsCode"
                         clearable
                         filterable
                         placeholder="选择煤的料号名称">
                <el-option v-for="item in gradeOptions"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>

            <div class="el-form-item__content"
                 style="margin-left: 0px;">
              <el-button v-hasPermi="['listMaterialLoadingPerformance']"
                         size="mini"
                         type="primary"
                         icon="el-icon-search"
                         @click="handleQuery">搜 索</el-button>
              <el-button size="mini"
                         type="default"
                         icon="el-icon-refresh-left"
                         @click="handleEmpty">重 置</el-button>
            </div>
          <!--          <el-col :span="2">-->
          <!--            <div class="el-form-item__content">-->
          <!--              <el-button v-hasPermi="['listMaterialLoadingPerformance']"-->
          <!--                         size="medium"-->
          <!--                         type="primary"-->
          <!--                         icon="el-icon-search"-->
          <!--                         @click="jump">上传ERP</el-button>-->

          <!--            </div>-->
          <!--          </el-col>-->
        </el-row>
      </el-form>
    </div>
    <div>
      <el-form>
        <el-table height="66vh"
                  size="small"
                  :data="tableData"
                  stripe
                  v-loading="table.loading"
                  @sort-change="handleSort">
          <!-- <template v-for="column in columns">
  <el-table-column :prop="column.prop"
                   :label="column.label"
                   :sortable="column.sortable"
                   :width="column.width"
                   :min-width="column.minWidth"
                   :type="column.type"
                   :formatter="column.formatter"
                   :key="column.prop" />
</template> -->
          <el-table-column label="序号"
                           type="index"
                           width="55"
                           align="center" />
          <el-table-column label="班次"
                           sortable
                           minWidth="150"
                           align="left"
                           prop="shift_name"
                           :formatter="shiftFormatter" />
          <el-table-column label="班别"
                           sortable
                           minWidth="150"
                           align="left"
                           prop="class_name" />
          <el-table-column label="配煤计划编号"
                           sortable
                           minWidth="150"
                           align="left"
                           prop="plan_number" />
          <el-table-column label="焦炭等级名称"
                           minWidth="150"
                           align="left"
                           prop="coal_level_materials_name" />
          <el-table-column label="配煤仓号"
                           minWidth="150"
                           align="left"
                           prop="coke_warehouse_number" />
          <el-table-column label="煤的料号名称"
                           minWidth="150"
                           align="left"
                           prop="coke_materials_name" />
          <el-table-column label="小煤种名称"
                           minWidth="150"
                           align="left"
                           prop="coke_materials_small_name" />
          <el-table-column label="上煤重量(t)"
                           minWidth="150"
                           align="left"
                           prop="coke_weight">
            <template slot-scope="scope">
              {{ (scope.row.coke_weight / 1000).toFixed(3) }}
            </template>
          </el-table-column>
          <el-table-column label="创建人姓名"
                           minWidth="150"
                           align="left"
                           prop="create_user_name" />
          <el-table-column label="创建时间"
                           minWidth="150"
                           align="left"
                           prop="create_time"
                           sortable />
        </el-table>
        <div style="margin-top: 10px;float: right;padding:25px 0 20px 20px ;"
             class="avue-crud__pagination">
          <!-- <el-pagination background
               @size-change="handleSizeChange"
               @current-change="handleCurrentChange"
               layout="total, sizes, prev, pager, next, jumper"
               :current-page="page.currentPage"
               :page-sizes="[20, 50, 100, 200]"
               :page-size="page.pageSize"
               :total="page.total">
</el-pagination> -->
          <el-pagination v-show="page.total > 0"
                         background
                         @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"
                         layout="total, sizes, prev, pager, next, jumper"
                         :current-page="page.current"
                         :page-sizes="[20, 50, 100, 200]"
                         :page-size="page.size"
                         :total="page.total">
          </el-pagination>
        </div>
      </el-form>
    </div>
    <el-dialog :visible.sync="dialogVisible"
               title="手动上传ERP"
               width="600px"
               :destroy-on-close="true"
               :close-on-click-modal="false"
               append-to-body>
      <CoalingActualERPEditor @close="dialogVisible = false" />
    </el-dialog>
  </div>
</template>

<script>
import { listMaterialsBoxM } from '@/api/material/mr/parameter/materialCode'
import {
  getMaterialLoadingPerformanceList,
  getProductionConveyingCoalActualPage,
} from '@/api/production/oi/actual/coal/MaterialLoadingPerformanceApi'
import CoalingActualERPEditor from '@/views/product/oi/actual/coal/CoalingActualERPEditor'
import moment from 'moment'
import { getDicts } from '@/api/system/dict/data'
export default {
  name: 'coalingActual',
  components: { CoalingActualERPEditor },
  data() {
    return {
      dataEdit: {},
      // page: {
      //   pageSize: 20,
      //   currentPage: 1,
      //   total: 1,
      // },
      // currentPage: 0,
      // total: 0,
      page: {
        size: 20,
        current: 1,
        total: 0,
        order: 'create_time',
        orderby: 'desc',
      },
      classTypeOptions: [],
      shiftOptions: [],
      query: {
        // startTime: '',
        // endTime: '',
        // classType: '',
        // shift: '',
        className: null,
        shiftName: null,
        cokeMaterialsCode: null,
      },
      table: {
        border: true,
        loading: true,
      },
      // columns: [
      //   { label: '编号', type: "index", minWidth: '100px' },
      //   { label: '班别', prop: "class_name", sortable: true, minWidth: '80px' },
      //   {
      //     label: '班次', prop: "shift_name", sortable: true, minWidth: '80px', formatter (row) {
      //       switch (row.shift_name) {
      //         case "01":
      //           return "常白班";
      //         case "2201":
      //           return "白班";
      //         case "2202":
      //           return "夜班";
      //         case "00":
      //           return "休息";
      //         default:
      //           return row.shift_name;
      //       }
      //     }
      //   },
      //   { label: '上煤仓号', prop: "conveying_coal_warehouse_number", sortable: true, minWidth: '100px' },
      //   { label: '堆取料机号', prop: "stacker_reclaimer_number", sortable: true, minWidth: '100px' },
      //   { label: '料仓名称', prop: "silo_name", sortable: true, minWidth: '150px' },
      //   { label: '储位名称', prop: "slot_name", sortable: true, minWidth: '150px' },
      //   {
      //     label: '上煤开始', prop: "start_time", sortable: true, minWidth: '150px', formatter (row) {
      //       return row.start_time === null ? "" : moment(row.start_time).format('YYYY-MM-DD HH:mm:ss');
      //     }
      //   },
      //   {
      //     label: '上煤结束', prop: "end_time", sortable: true, minWidth: '150px', formatter (row) {
      //       return row.end_time === null ? "" : moment(row.end_time).format('YYYY-MM-DD HH:mm:ss');
      //     }
      //   },
      //   { label: '上煤时长(min)', prop: "duration", sortable: true, minWidth: '150px' },
      //   { label: '上煤量(kg)', prop: "material_weight", sortable: true, minWidth: '100px' },
      //   { label: '物料名称', prop: "material_name", sortable: true, minWidth: '100px' },
      //   {
      //     label: '创建时间', prop: "create_time", sortable: true, minWidth: '150px', formatter (row) {
      //       return row.create_time === null ? "" : moment(row.create_time).format('YYYY-MM-DD HH:mm:ss');
      //     }
      //   },

      // ],
      tableData: [],
      dialogVisible: false,
      gradeOptions: [],
    }
  },
  mounted() {
    getDicts('sys_classtype').then((response) => {
      this.classTypeOptions = response.data.map((i) => {
        return { value: i.dictLabel, label: i.dictLabel }
      })
    })
    getDicts('sys_shift_no').then((response) => {
      this.shiftOptions = response.data.map((i) => {
        return { value: i.dictValue, label: i.dictLabel }
      })
    })
    this.getMaterialsOptions()
    this.handleQuery()
  },
  methods: {
    //获取物料代码（煤）
    getMaterialsOptions() {
      const param = {}
      listMaterialsBoxM(param).then((res) => {
        // console.log(res)
        let options = []
        res.forEach((item) => {
          let i = {
            id: item.materials_code,
            name: item.materials_name,
          }
          options.push(i)
        })
        this.gradeOptions = options
      })
    },
    //查询
    handleQuery() {
      // this.page.currentPage = 1;
      // let query = this.query;
      // let page = this.page;
      // let params = {
      //   "shiftName": this.query.shift,
      //   "className": query.classType,
      //   "startTime": query.startTime === "" ? "" : query.startTime,
      //   "endTime": query.endTime === "" ? "" : query.endTime,
      //   "size": page.pageSize,
      //   "current": page.currentPage,
      //   "order": "id",
      //   "orderby": "asc"
      // }
      // this.table.loading = true;
      // getMaterialLoadingPerformanceList(params).then(response => {
      //   this.table.loading = false;
      //   this.tableData = response.data.data.records;
      //   this.page.total = response.data.data.total;
      // }, error => {
      //   this.table.loading = false;
      //   window.console.log(error);
      // })
      this.table.loading = true //加载状态
      getProductionConveyingCoalActualPage(this.page, this.query).then(
        (res) => {
          this.table.loading = false
          let data = res.data //表格相关数据
          this.page.total = data.total //数据总数
          this.tableData = data.records //表格数据
        },
        (error) => {
          this.table.loading = false
          window.console.log(error)
        }
      )
    },
    // 清空
    handleEmpty() {
      // this.query = {
      //   startTime: '',
      //   endTime: '',
      //   classType: '',
      //   shift: '',
      // }
      this.page.current = 1
      this.$refs['query'].resetFields()
      this.handleQuery()
    },

    // 排序
    handleSort(column) {
      // console.log(column, "column")
      if (column.order === null) {
        //默认
        this.page.order = 'create_time'
        this.page.orderby = 'desc'
      } else {
        //选中项
        this.page.order = column.prop
        this.page.orderby =
          column.order === 'ascending' ? 'asc' : 'desc'
      }
      this.handleQuery()
    },

    // // 分页-每页多少条
    // handleSizeChange (val) {
    //   this.page.pageSize = val;
    //   this.handleQuery();
    // },
    // // 分页-当前页
    // handleCurrentChange (val) {
    //   this.page.currentPage = val;
    //   this.handleQuery();
    // },

    // 分页-每页多少条
    handleSizeChange(val) {
      this.page.size = val
      this.handleQuery()
    },
    // 分页-当前页
    handleCurrentChange(val) {
      this.page.current = val
      this.handleQuery()
    },

    jump() {
      // this.$router.push('/production/actual/coal/coalingActualERP')
      this.dialogVisible = true
    },
    shiftFormatter(row) {
      switch (row.shift_name) {
        case '01':
          return '常白班'
        case '2201':
          return '白班'
        case '2202':
          return '夜班'
        case '00':
          return '休息'
        default:
          return row.shift_name
      }
    },
  },
}
</script>

<style scoped>
</style>
