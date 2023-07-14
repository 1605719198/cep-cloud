<!--
  上煤记录
-->
<template>
  <div class="app-container">
    <!-- 表单筛选 -->
    <el-form :model="query" ref="query" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item prop="timeArr" label="上煤时间">
        <el-date-picker v-model="query.timeArr"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="上煤开始时间"
                        end-placeholder="上煤结束时间"
                        value-format="yyyy-MM-dd"
        size="small">
        </el-date-picker>
      </el-form-item>
      <el-form-item prop="shiftName" label="班次">
        <el-select placeholder="选择班次"
                   v-model="query.shiftName"
                   size="small"
                   style="width: 150px"
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
                   size="small"
                   style="width: 150px"
                   clearable
                   :popper-append-to-body="false"
                   class="customSelectStyle">
          <el-option v-for="item in classOptions"
                     :key="item.id"
                     :label="item.name"
                     :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="cokeMaterialsCode" label="料号名称">
        <el-select :popper-append-to-body="false"
                   class="customSelectStyle"
                   v-model="query.cokeMaterialsCode"
                   clearable
                   filterable
                   size="small"
                   style="width: 150px"
                   placeholder="选择煤的料号名称">
          <el-option v-for="item in gradeOptions"
                     :key="item.id"
                     :label="item.name"
                     :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <!-- 操作按钮 -->
      <el-form-item>
        <el-button @click="handleQuery"
                   type="primary"
                   icon="el-icon-search"
                   size="mini">搜 索</el-button>
        <el-button @click="handleReset"
                   icon="el-icon-refresh-left"
                   size="mini">重 置</el-button>
      </el-form-item>
    </el-form>


    <div class="avue-crud__search"
         style="border: 0">
      <el-row class="mb8">
        <el-button type="primary"
                   size="mini"
                   plain
                   icon="el-icon-plus"
                   @click="handleAdd('add')">新增
        </el-button>
      </el-row>
    </div>
    <div>
      <el-table height="65vh"
                size="small"
                v-loading="table.loading"
                :data="tableData"
                stripe
                @sort-change="handleSort">
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
        <el-table-column label="上煤开始时间"
                         minWidth="150"
                         align="left"
                         prop="start_time" />
        <el-table-column label="上煤结束时间"
                         minWidth="150"
                         align="left"
                         prop="end_time" />
        <el-table-column label="上煤重量(t)"
                         minWidth="150"
                         align="left"
                         prop="coke_weight">
          <template slot-scope="scope">
            {{ (scope.row.coke_weight / 1000).toFixed(3) }}
          </template>
        </el-table-column>
        <!-- <el-table-column label="上煤前重量(kg)"
                 minWidth="150"
                 align="left"
                 prop="coke_weight_befor" /> -->
        <!-- <el-table-column label="上煤后重量(kg)"
                 minWidth="150"
                 align="left"
                 prop="coke_weight_after" /> -->
        <el-table-column label="上煤前料位(m)"
                         minWidth="150"
                         align="left"
                         prop="coke_material_level_befor" />
        <el-table-column label="上煤后料位(m)"
                         minWidth="150"
                         align="left"
                         prop="coke_material_level_after" />
        <el-table-column label="创建人姓名"
                         minWidth="150"
                         align="left"
                         prop="create_user_name" />
        <el-table-column label="创建时间"
                         minWidth="150"
                         align="left"
                         prop="create_time"
                         sortable />
        <el-table-column label="数据来源"
                         minWidth="150"
                         align="left"
                         prop="data_sources"
                         :formatter="sourcesFormatter" />
        <el-table-column fixed="right"
                         label="操作"
                         width="180"
                         align="center">
          <template slot-scope="scope">
            <el-button v-if="scope.row.create_user_id == userId"
                       size="mini"
                       icon="el-icon-delete"
                       type="text"
                       @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 10px;float: right;padding: 25px 0px 20px 20px;"
           class="avue-crud__pagination">
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
    </div>
    <!-- 弹窗 -->
    <div v-if="dialog.visible">
      <el-dialog :title="dialog.title"
                 :visible.sync="dialog.visible"
                 :width="dialog.width"
                 class="customDialogStyle"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false">
        <template>
          <Add v-if="dialog.type === 'add'"
               :type="dialog.type"
               :data="dialog.data"
               @submitSave="submitSave"
               @close="dialog.visible = false" />
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { listMaterialsBoxM } from '@/api/material/mr/parameter/materialCode'
import {
  getProductionConveyingCoalRecordPage,
  deleteProductionConveyingCoalRecord,
} from '@/api/production/oi/upCoalRecord'
import { getDicts } from '@/api/system/dict/data'
import Add from './add.vue'
export default {
  components: {
    Add,
  },
  data() {
    return {
      showSearch: true,
      dialog: {
        visible: false,
        type: '',
        title: '',
        width: '60%',
        data: {},
      },
      page: {
        size: 20,
        current: 1,
        total: 0,
        order: 'create_time',
        orderby: 'desc',
      },
      query: {
        timeArr: null,
        className: null,
        shiftName: null,
        cokeMaterialsCode: null,
      },
      table: {
        border: true,
        loading: false,
      },
      tableData: [],
      classOptions: [],
      shiftOptions: [],
      gradeOptions: [],
      userId: ""
    }
  },
  created() {
    this.userId = this.$store.state.user.userInfo.userName
    getDicts('sys_classtype').then((response) => {
      this.classOptions = response.data.map((i) => {
        return { id: i.dictLabel, name: i.dictLabel }
      })
    })
    getDicts('sys_shift_no').then((response) => {
      this.shiftOptions = response.data.map((i) => {
        return { value: i.dictValue, label: i.dictLabel }
      })
    })
    this.getMaterialsOptions()
    this.onLoad()
  },
  computed: {
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
    //载入数据
    onLoad() {
      if (this.query.timeArr && this.query.timeArr[0]) {
        this.query.startTime = this.query.timeArr[0] + ' ' + '00:00:00'
        this.query.endTime = this.query.timeArr[1] + ' ' + '23:59:59'
      } else {
        this.query.startTime = ''
        this.query.endTime = ''
      }
      this.table.loading = true //加载状态
      getProductionConveyingCoalRecordPage(this.page, this.query).then(
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
        this.page.order = 'create_time'
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
      // console.log(this.query.issueDate)
      this.onLoad()
    },
    // 重置
    handleReset() {
      this.page.current = 1
      this.$refs['query'].resetFields()
      this.deptForm = { value: '', label: '' }
      // console
      this.handleQuery()
    },
    //新增
    handleAdd(type) {
      this.dialog.type = type
      this.dialog.title = '新增上煤记录'
      this.dialog.data = {}
      this.dialog.visible = true
    },
    // 删除
    handleDelete(index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          deleteProductionConveyingCoalRecord({
            id: row.id,
            delete_user_id: this.$store.getters.userInfo.userId,
            delete_user_name: this.$store.getters.userInfo.nickName,
          }).then(
            (res) => {
                this.onLoad()
                this.$message({
                  type: 'success',
                  message: "删除成功",
                })
            },
            (error) => {
              window.console.log(error)
            }
          )
        })
        .catch(() => {
          this.$message({ type: 'info', message: '已取消删除' })
        })
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
    sourcesFormatter(row) {
      if (row.data_sources == '0') {
        return '手工填报'
      } else {
        return '数据采集'
      }
    },
    //提供给子类调用的方法
    submitSave() {
      this.dialog.visible = false
      this.dialog.data = {}
      this.onLoad()
    },
  },
}
</script>

<style scoped lang="scss">
</style>

