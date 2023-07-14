<!--
  推焦计划
-->
<template>
  <div class="avue-crud el-card__body"
       style="width: 98%;border: 0">
    <div class="avue-crud__search"
         style="border: 0">
      <el-row>
        <el-col :span="20">
          <el-form :model="query"
                   ref="query"
                   :inline="true">
            <el-form-item prop="timeArr">
              <el-date-picker v-model="query.timeArr"
                              type="daterange"
                              range-separator="至"
                              start-placeholder="计划推焦时间开始"
                              end-placeholder="计划推焦时间结束"
                              value-format="yyyy-MM-dd" />
            </el-form-item>
            <el-form-item prop="furnaceArea">
              <el-select v-model="query.furnaceArea"
                         placeholder="炉区"
                         clearable>
                <el-option v-for="item in furnaceBlockOptions"
                           :key="item.value"
                           :label="item.label"
                           :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item prop="class_name">
              <el-select v-model="query.class_name"
                         placeholder="班组"
                         clearable>
                <el-option v-for="item in classTypeOptions"
                           :key="item.value"
                           :label="item.label"
                           :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <!-- 操作按钮 -->
            <el-form-item>
              <el-button @click="handleQuery"
                         type="primary"
                         icon="el-icon-search"
                         size="medium">搜 索</el-button>
              <el-button @click="handleReset"
                         icon="el-icon-refresh-left"
                         size="medium">重 置</el-button>
            </el-form-item>
          </el-form>
        </el-col>
        <!-- <el-col :span="4">
            <div style="text-align: right;">
                <el-button type="primary"
                           size="medium"
                           icon="el-icon-plus"
                           @click="handleAdd('add')">新增
                </el-button>
            </div>
        </el-col> -->
      </el-row>
    </div>
    <div>
      <el-form>
        <el-table height="67vh"
                  size="small"
                  :data="tableData"
                  stripe
                  @sort-change="handleSort"
                  v-loading="table.loading">
          <el-table-column v-for="column in columns"
                           :prop="column.prop"
                           :label="column.label"
                           :sortable="column.sortable"
                           :width="column.width"
                           :min-width="column.minWidth"
                           :type="column.type"
                           :formatter="column.formatter"
                           :key="column.prop" />
          <!-- <el-table-column fixed="right"
                           label="操作"
                           width="180"
                           align="center">
              <template slot-scope="scope">
                  <el-button size="mini"
                             plain
                             icon="el-icon-delete"
                             type="danger"
                             @click="handleDelete(scope.$index, scope.row)">删除
                  </el-button>
              </template>
          </el-table-column> -->
        </el-table>
        <div style="margin-top: 10px;right: 0;padding:0px 0 20px 20px ;"
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
import moment from 'moment'
import { getDicts } from '@/api/system/dict/data'
import { getProductionOutputPerformancePlanPage } from '@/api/production/oi/actual/coke/cokePushingPlan'
export default {
  components: {
    // Add,
  },
  name: '',
  data() {
    return {
      page: {
        size: 20,
        current: 1,
        total: 0,
        order: 'plan_focus_time',
        orderby: 'desc',
      },
      classTypeOptions: [],
      shiftOptions: [],
      furnaceBlockOptions: [
        {
          value: 0,
          label: '1#',
        },
        {
          value: 1,
          label: '2#',
        },
      ],
      query: {
        furnaceArea: '',
        furnace_area: null,
        class_name: null,
        timeArr: null,
      },
      table: {
        border: true,
        loading: true,
      },
      columns: [
        {
          label: '炉区',
          prop: 'furnace_area',
          sortable: true,
          minWidth: '150px',
          formatter: (row) => {
            return row.furnace_area == 0 ? '1#炉区' : '2#炉区'
          },
        },
        {
          label: '炉数',
          prop: 'released_num',
          sortable: true,
          minWidth: '150px',
        },
        {
          label: '班组',
          prop: 'class_name',
          sortable: true,
          minWidth: '150px',
        },
        {
          label: '炉号',
          prop: 'furnace_num',
          sortable: true,
          minWidth: '150px',
        },
        {
          label: '上次装煤时间',
          prop: 'last_coal_load_time',
          sortable: true,
          minWidth: '150px',
        },
        {
          label: '计划结焦时间',
          prop: 'plan_coking_time',
          sortable: true,
          minWidth: '150px',
        },
        {
          label: '计划推焦时间',
          prop: 'plan_focus_time',
          sortable: true,
          minWidth: '150px',
        },
        {
          label: '状态',
          prop: 'status',
          sortable: true,
          minWidth: '150px',
          // formatter(row) {
          //     return row.status ? '已推' : '未推'
          // },
        },
      ],
      tableData: [],
      dialog: {
        visible: false,
        type: '',
        title: '',
        width: '60%',
        data: {},
      },
    }
  },
  mounted() {
    getDicts('sys_classtype').then((response) => {
      this.classTypeOptions = response.data.data.map((i) => {
        return { value: i.dictLabel, label: i.dictLabel }
      })
    })
    // getDicts('sys_shift_no').then((response) => {
    //     this.shiftOptions = response.data.data.map((i) => {
    //         return { value: i.dictValue, label: i.dictLabel }
    //     })
    // })
    this.onLoad()
  },
  methods: {
    //载入数据
    onLoad() {
      if (this.query.timeArr && this.query.timeArr[0]) {
        this.query.start_time = this.query.timeArr[0] + ' ' + '00:00:00'
        this.query.end_time = this.query.timeArr[1] + ' ' + '23:59:59'
      } else {
        this.query.start_time = ''
        this.query.end_time = ''
      }
      //炉区
      if (this.query.furnaceArea === '') {
        this.query.furnace_area = -1
      } else {
        this.query.furnace_area = this.query.furnaceArea
      }
      this.table.loading = true //加载状态
      getProductionOutputPerformancePlanPage(this.page, this.query).then(
        (res) => {
          this.table.loading = false
          let data = res.data.data //表格相关数据
          this.page.total = data.total //数据总数
          this.tableData = data.records //表格数据
        },
        (error) => {
          this.table.loading = false
          window.console.log(error)
        }
      )
    },
    // 分页-每页多少条
    handleSizeChange(val) {
      this.page.size = val
      this.onLoad()
    },
    // 分页-当前页
    handleCurrentChange(val) {
      this.page.current = val
      this.onLoad()
    },
    // 排序
    handleSort(column) {
      // console.log(column, "column")
      if (column.order === null) {
        //默认
        this.page.order = 'plan_focus_time'
        this.page.orderby = 'desc'
      } else {
        //选中项
        this.page.order = column.prop
        this.page.orderby =
          column.order === 'ascending' ? 'asc' : 'desc'
      }
      this.onLoad()
    },
    //查询
    handleQuery() {
      this.page.current = 1
      this.onLoad()
    },
    // 重置
    handleReset() {
      this.page.current = 1
      this.$refs['query'].resetFields()
      this.handleQuery()
    },
    //新增
    handleAdd(type) {
      this.dialog.type = type
      this.dialog.title = '新增'
      this.dialog.data = {}
      this.dialog.visible = true
    },
    // 删除
    // handleDelete(index, row) {
    //     this.$confirm('是否确认删除数据项?', '警告', {
    //         confirmButtonText: '确定',
    //         cancelButtonText: '取消',
    //         type: 'warning',
    //     })
    //         .then(() => {
    //             deleteProductionOutputPerformanceManualBake({
    //                 id: row.id,
    //             }).then(
    //                 (res) => {
    //                     if (res.data.code === '0') {
    //                         this.onLoad()
    //                         this.$message({
    //                             type: 'success',
    //                             message: res.data.msg,
    //                         })
    //                     }
    //                 },
    //                 (error) => {
    //                     window.console.log(error)
    //                 }
    //             )
    //         })
    //         .catch(() => {
    //             this.$message({ type: 'info', message: '已取消删除' })
    //         })
    // },
    //提供给子类调用父类的方法
    submitSave() {
      this.dialog.visible = false
      this.dialog.data = {}
      this.handleQuery()
    },
  },
}
</script>

<style scoped>
</style>
