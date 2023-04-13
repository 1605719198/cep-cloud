<!--
  出炉实绩
-->
<template>
    <div class="avue-crud el-card__body"
         style="width: 98%;border: 0">
        <div class="avue-crud__search"
             style="border: 0">
            <el-form :inline="true">
              <div class="el-form-item el-form-item--small">
                <label class="el-form-item__label">日期</label>
                  <div class="el-form-item__content">
                      <el-date-picker v-model="query.daterange"
                                      type="daterange"
                                      range-separator="至"
                                      start-placeholder="开始日期"
                                      end-placeholder="结束日期"
                                      value-format="yyyy-MM-dd" />
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
              <el-form-item prop="cokeOvenNumber"
                            label="炉号">
                  <el-select :popper-append-to-body="false"
                             v-model="query.cokeOvenNumber"
                             clearable
                             filterable
                             placeholder="选择炉号">
                      <el-option v-for="i in 16"
                                 :key="i"
                                 :label="i+'号'"
                                 :value="i" />
                  </el-select>
              </el-form-item>
              <el-form-item prop="carbonizationChamberNumber"
                            label="炭化室号">
                  <el-select :popper-append-to-body="false"
                             v-model="query.carbonizationChamberNumber"
                             clearable
                             filterable
                             placeholder="选择炭化室号">
                      <el-option v-for="i in 17"
                                 :key="i"
                                 :label="i+'号'"
                                 :value="i" />
                  </el-select>
              </el-form-item>

              <div class="el-form-item__content"
                   style="margin-left: 0px;">
                  <el-button v-hasPermi="['listOutputPerformance']"
                             size="mini"
                             type="primary"
                             icon="el-icon-search"
                             @click="handleQuery">搜索
                  </el-button>
                  <el-button size="mini"
                             type="default"
                             icon="el-icon-refresh-left"
                             @click="handleEmpty">重置
                  </el-button>
              </div>
            </el-form>
            <el-button v-hasPermi="['listOutputPerformance']"
                       icon="el-icon-plus"
                       size="mini"
                       plain
                       type="primary"
                       style="margin: 5px 0;"
                       @click="handleAdd('add')">新增
            </el-button>

        </div>
        <div>
            <el-form>
                <el-table height="67vh"
                          size="small"
                          :data="tableData"
                          stripe
                          @sort-change="handleSort"
                          :default-sort="{prop: 'number', order: 'descending'}"
                          v-loading="table.loading">
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
                    <el-table-column v-for="column in columns"
                                     :prop="column.prop"
                                     :label="column.label"
                                     :sortable="column.sortable"
                                     :width="column.width"
                                     :min-width="column.minWidth"
                                     :type="column.type"
                                     :formatter="column.formatter"
                                     :key="column.prop" />
                    <el-table-column fixed="right"
                                     label="操作"
                                     width="180"
                                     align="center">
                        <template slot-scope="scope">
                            <el-button size="mini"
                                       icon="el-icon-delete"
                                       type="text"
                                       @click="handleDelete(scope.$index, scope.row)">删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div style="margin-top: 10px;float: right;padding:0px 0 20px 20px ;"
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
                         @close="dialog.visible=false" />
                </template>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import Add from './outFurnaceActualAdd'
import {
    getOutputPerformanceList,
    getProductionOutputPerformanceManualBakePage,
    deleteProductionOutputPerformanceManualBake,
} from '@/api/production/oi/actual/coke/OutputPerformanceApi'
import moment from 'moment'
import { getDicts } from '@/api/system/dict/data'

export default {
    components: {
        Add,
    },
    name: 'outFurnaceActual',

    data() {
        return {
            page: {
                pageSize: 20,
                currentPage: 1,
                total: 1,
                order: 'production_date desc,actual_production_time desc',
                orderby: '',
            },
            classTypeOptions: [],
            shiftOptions: [],
            query: {
                daterange: '',
                className: '',
                shiftName: '',
                cokeOvenNumber: '',
                carbonizationChamberNumber: '',
            },
            table: {
                border: true,
                loading: true,
            },
            columns: [
                { label: '编号', type: 'index', minWidth: '100px' },
                {
                    label: '班别',
                    prop: 'class_name',
                    sortable: true,
                    minWidth: '110px',
                },
                {
                    label: '班次',
                    prop: 'shift_name',
                    sortable: true,
                    minWidth: '80px',
                    formatter(row) {
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
                {
                    label: '炉号',
                    prop: 'coke_oven_number',
                    sortable: true,
                    minWidth: '80px',
                },
                {
                    label: '炭化室号',
                    prop: 'carbonization_chamber_number',
                    sortable: true,
                    minWidth: '100px',
                },
                {
                    label: '焦炭类别',
                    prop: 'coke_type',
                    sortable: true,
                    minWidth: '120px',
                    formatter: (row) =>
                        row.coke_type === 1
                            ? '干熄焦'
                            : row.coke_type === 2
                            ? '湿熄焦'
                            : '',
                },
                {
                    label: '焦炭等级',
                    prop: 'materials_name',
                    sortable: true,
                    minWidth: '120px',
                },
                {
                    label: '生产日期',
                    prop: 'production_date',
                    sortable: true,
                    minWidth: '150px',
                    formatter(row) {
                        return row.production_date === null
                            ? ''
                            : moment(row.production_date).format('YYYY-MM-DD')
                    },
                },
                {
                    label: '计划推焦时间',
                    prop: 'planning_production_time',
                    sortable: true,
                    minWidth: '150px',
                },
                {
                    label: '实绩推焦时间',
                    prop: 'actual_production_time',
                    sortable: true,
                    minWidth: '150px',
                },
                {
                    label: '储煤塔号',
                    prop: 'coal_tower_number',
                    sortable: true,
                    minWidth: '150px',
                },
                {
                    label: '推焦电流',
                    prop: 'coke_current',
                    sortable: true,
                    minWidth: '100px',
                },
                {
                    label: '计划装煤时间',
                    prop: 'planning_load_coal_time',
                    sortable: true,
                    minWidth: '150px',
                },
                {
                    label: '实际装煤时间',
                    prop: 'actual_load_coal_time',
                    sortable: true,
                    minWidth: '150px',
                },
                {
                    label: '装煤电流',
                    prop: 'coal_current',
                    sortable: true,
                    minWidth: '100px',
                },
                {
                    label: '计划运转周期',
                    prop: 'planning_run_cycle',
                    sortable: true,
                    minWidth: '150px',
                },
                {
                    label: '实际运转周期',
                    prop: 'actual_run_cycle',
                    sortable: true,
                    minWidth: '150px',
                },
                {
                    label: '单孔产量(t)',
                    prop: 'single_hole_production',
                    sortable: true,
                    minWidth: '110px',
                    formatter(row) {
                        return (row.single_hole_production / 1000).toFixed(3)
                    },
                },
                {
                    label: '装煤量(t)',
                    prop: 'loading_coal',
                    sortable: true,
                    minWidth: '95px',
                    formatter(row) {
                        return (row.loading_coal / 1000).toFixed(2)
                    },
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
            this.classTypeOptions = response.data.map((i) => {
                return { value: i.dictLabel, label: i.dictLabel }
            })
        })
        getDicts('sys_shift_no').then((response) => {
            this.shiftOptions = response.data.map((i) => {
                return { value: i.dictValue, label: i.dictLabel }
            })
        })
        this.handleQuery()
    },
    methods: {
        //查询
        handleQuery() {
            let query = this.query
            let page = this.page
            let starDate = query.daterange === '' ? '' : query.daterange[0]
            let endDate = query.daterange === '' ? '' : query.daterange[1]

            let cokeOvenNumber =
                query.cokeOvenNumber === '' ? 0 : query.cokeOvenNumber
            let carbonizationChamberNumber =
                query.carbonizationChamberNumber === ''
                    ? 0
                    : query.carbonizationChamberNumber
            // let param = {
            //   "shiftName": query.shiftName,
            //   "className": query.className,
            //   "startTime": starDate,
            //   "endTime": endDate,
            //   "size": page.pageSize,
            //   "current": page.currentPage,
            //   "order": "id",
            //   "orderby": "asc"
            // }
            // this.table.loading = true;
            // getOutputPerformanceList(param).then(response => {
            //   this.table.loading = false;
            //   this.tableData = response.data.data.records;
            //   this.page.total = response.data.data.total;
            // }, error => {
            //   this.table.loading = false;
            //   window.console.log(error);
            // })

            let param = {
                shiftName: query.shiftName,
                className: query.className,
                cokeOvenNumber: cokeOvenNumber,
                carbonizationChamberNumber: carbonizationChamberNumber,
                startDate: starDate,
                endDate: endDate,
                size: page.pageSize,
                current: page.currentPage,
                // "order": "production_date",
                // "orderby": "desc"
                order: page.order,
                orderby: page.orderby,
            }
            this.table.loading = true
            getProductionOutputPerformanceManualBakePage(param).then(
                (response) => {
                    this.table.loading = false
                    this.tableData = response.data.records
                    this.page.total = response.data.total
                },
                (error) => {
                    this.table.loading = false
                    window.console.log(error)
                }
            )
        },
        // 排序
        handleSort(column) {
            // console.log(column, "column")
            if (column.order === null) {
                //默认
                this.page.order = 'production_date'
                this.page.orderby = 'desc'
            } else {
                //选中项
                this.page.order = column.prop
                this.page.orderby =
                    column.order === 'ascending' ? 'asc' : 'desc'
            }
            this.handleQuery()
        },
        // 清空
        handleEmpty() {
            this.query = {
                daterange: '',
                className: '',
                shiftName: '',
              cokeOvenNumber: '',
              carbonizationChamberNumber: '',
            }
            this.handleQuery()
        },

        // 分页-每页多少条
        handleSizeChange(val) {
            this.page.pageSize = val
            this.handleQuery()
        },
        // 分页-当前页
        handleCurrentChange(val) {
            this.page.currentPage = val
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
        handleDelete(index, row) {
            this.$confirm('是否确认删除数据项?', '警告', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            })
                .then(() => {
                    deleteProductionOutputPerformanceManualBake({
                        id: row.id,
                    }).then(
                        (res) => {
                            if (res.code === 200) {
                                this.handleQuery()
                                this.$message({
                                    type: 'success',
                                    message: res.msg,
                                })
                            }
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
