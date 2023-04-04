<!--
  配煤实绩的详情
-->
<template>
  <div class="avue-crud">
    <div>
      <div class="title">
        <label>基本信息</label>
      </div>
      <el-table :data="tableData"
                stripe
                style="width: 100%"
                v-loading="table.loading">
        <el-table-column v-for="column in columns"
                         :prop="column.prop"
                         :label="column.label"
                         :sortable="column.sortable"
                         :width="column.width"
                         :min-width="column.minWidth"
                         :formatter="column.formatter"
                         :type="column.type"
                         :key="column.prop" />
      </el-table>
      <div style="margin-top: 10px;right: 0;padding: 0;"
           class="avue-crud__pagination">
        <el-pagination background
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="page.currentPage"
                       :page-sizes="[20, 50, 100, 200]"
                       :page-size="page.pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="page.total">
        </el-pagination>
      </div>
    </div>
    <div class="el-dialog__footer"
         style="padding:10px 0 25px 0;">
            <span slot="footer"
                  class="dialog-footer">
                <el-button @click="cancel">取 消</el-button>
            </span>
    </div>
  </div>
</template>

<script>
import { getConfigureCoalSpeciesDetail } from '@/api/production/oi/actual/coal/ConfigureCoalSpeciesPerformanceApi'
import moment from 'moment'

export default {
  name: 'coalBlendingActualDetail',
  props: ['dataEdit'],
  data() {
    return {
      table: {
        loading: false,
      },
      page: {
        currentPage: 1,
        pageSize: 20,
        total: 0,
      },
      tableData: [],
      columns: [
        { label: '编号', type: 'index', minWidth: '80px' },
        {
          label: '实际配煤开始时间',
          prop: 'start_time',
          sortable: true,
          minWidth: '130px',
        },
        {
          label: '实际配煤结束时间',
          prop: 'end_time',
          sortable: true,
          minWidth: '130px',
        },
        {
          label: '配煤时长(min)',
          prop: 'duration',
          sortable: true,
          minWidth: '110px',
        },
        {
          label: '煤种',
          prop: 'material_name',
          sortable: true,
          minWidth: '80px',
        },
        {
          label: '小煤种',
          prop: 'material_small_name',
          sortable: true,
          minWidth: '110px',
        },
        {
          label: '仓号',
          prop: 'bunker_number',
          sortable: true,
          minWidth: '80px',
        },
        {
          label: '实际重量(t)',
          prop: 'material_weight',
          sortable: true,
          minWidth: '100px',
          formatter(row) {
            return row.material_weight / 1000
          },
        },
        // { label: '配煤前料位', prop: "before_material_level", sortable: true, minWidth: '150px' },
        // { label: '配煤后料位', prop: "after_material_level", sortable: true, minWidth: '150px' },
        // { label: '配煤前存量(t)', prop: "before_stock", sortable: true, minWidth: '130px', formatter(row){
        //     return row.before_stock / 1000 ;
        //   } },
        // { label: '配煤后存量(t)', prop: "after_stock", sortable: true, minWidth: '130px', formatter(row){
        //   return row.after_stock / 1000 ;
        //   } },
      ],
    }
  },
  mounted() {
    this.handlerGetDetail()
  },
  methods: {
    handlerGetDetail() {
      let mainId = this.dataEdit.id
      console.log('main_id === ' + mainId)
      let params = {
        mainId: mainId,
        current: this.page.currentPage,
        size: this.page.pageSize,
        order: 'create_time',
        orderby: 'asc',
      }
      this.table.loading = true
      getConfigureCoalSpeciesDetail(params).then(
        (response) => {
          this.table.loading = false
          this.tableData = response.data.records
          this.page.total = response.data.total
        },
        (error) => {
          this.table.loading = false
          window.alert(error)
        }
      )
    },
    cancel() {
      this.$emit('close')
    },
    dateFormat(str, type = 'date') {
      if (str === null || str === undefined || str === '') {
        return ''
      } else {
        if (type === 'date') {
          return moment(str).format('YYYY-MM-DD')
        } else if (type === 'time') {
          return moment(str).format('HH:mm:ss')
        } else {
          return moment(str).format('YYYY-MM-DD HH:mm:ss')
        }
      }
    },
    formatterShiftName(a) {
      switch (a) {
        case '01':
          return '常白班'
        case '2201':
          return '白班'
        case '2202':
          return '夜班'
        case '00':
          return '休息'
        default:
          return a
      }
    },
    handleSizeChange(val) {
      this.page.pageSize = val
      this.handlerGetDetail()
    },
    handleCurrentChange(val) {
      this.page.currentPage = val
      this.handlerGetDetail()
    },
  },
}
</script>

<style scoped>
.title {
  border-left: 10px solid #409eff;
  margin: 20px 0;
}

.title label {
  margin-left: 15px;
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  font-weight: 700;
  text-decoration: none;
}

.content {
  padding-left: 25px;
  line-height: 35px;
  font-size: 14px;
}

.content label {
  float: left;
  text-align: right;
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  font-weight: 700;
  font-style: normal;
  color: rgb(153, 153, 153);
  line-height: 34px;
}

.content .el-col div {
  margin-left: 105px;
  font-family: 微软雅黑;
  font-style: normal;
  text-align: left;
  font-size: 15px;
}

.tableContent {
  border: 1px solid #ebeef5;
  border-bottom: unset;
}
</style>
