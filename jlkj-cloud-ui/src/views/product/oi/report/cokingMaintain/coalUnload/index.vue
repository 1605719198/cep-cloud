<template>
    <div class="avue-crud el-card__body"
         style="width: 98%;border: 0;">
        <div class="avue-crud__search"
             style="border: 0">
            <el-row>
                <el-col :span="20">
                    <!-- 表单筛选 -->
                    <el-form :model="query"
                             ref="query"
                             :inline="true">
                        <el-form-item prop="timeArr">
                            <el-date-picker v-model="query.timeArr"
                                            type="daterange"
                                            range-separator="至"
                                            start-placeholder="统计开始日期"
                                            end-placeholder="统计结束日期"
                                            value-format="yyyy-MM-dd">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item prop="coal_way">
                            <el-select :popper-append-to-body="false"
                                       class="customSelectStyle"
                                       v-model="query.coal_way"
                                       clearable
                                       filterable
                                       placeholder="来煤方式">
                                <el-option v-for="item in coalSourceMethodOptions"
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
                                       size="medium">查 询</el-button>
                            <el-button @click="handleReset"
                                       icon="el-icon-refresh-left"
                                       size="medium">重 置</el-button>
                        </el-form-item>
                    </el-form>
                </el-col>
                <el-col :span="4">
                    <div style="text-align: right;margin:0 10px 20px 0;">
                        <el-button type="primary"
                                   size="medium"
                                   icon="el-icon-plus"
                                   @click="handleAdd('add')">新增
                        </el-button>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div>
            <el-table height="73vh"
                      size="small"
                      v-loading="table.loading"
                      :data="tableData"
                      stripe
                      @sort-change="handleSort">
                <!-- <el-table-column label="序号"
                                 type="index"
                                 width="55"
                                 align="center" /> -->
                <el-table-column label="统计日期"
                                 sortable
                                 minWidth="150"
                                 align="left"
                                 prop="statistics_date" />
                <el-table-column label="来煤方式"
                                 sortable
                                 minWidth="150"
                                 align="left"
                                 prop="coal_way" />
                <el-table-column label="来煤车数"
                                 sortable
                                 minWidth="150"
                                 align="left"
                                 prop="coal_num" />
                <el-table-column label="库存(KG)"
                                 sortable
                                 minWidth="150"
                                 align="left"
                                 prop="stock" />
                <el-table-column label="卸煤-螺旋卸"
                                 sortable
                                 minWidth="150"
                                 align="left"
                                 prop="unloading_coal_screw" />
                <el-table-column label="卸煤-分卸"
                                 sortable
                                 minWidth="150"
                                 align="left"
                                 prop="unloading_coal_part" />
                <el-table-column label="待卸-螺旋卸"
                                 sortable
                                 minWidth="150"
                                 align="left"
                                 prop="unloaded_screw" />
                <el-table-column label="待卸-分卸"
                                 sortable
                                 minWidth="150"
                                 align="left"
                                 prop="unloaded_part" />
                <el-table-column fixed="right"
                                 label="操作"
                                 width="180"
                                 align="center">
                    <template slot-scope="scope">
                        <el-button size="mini"
                                   plain
                                   icon="el-icon-edit"
                                   type="success"
                                   @click="handleEdit('edit',scope.$index, scope.row)">修改
                        </el-button>
                        <el-button size="mini"
                                   plain
                                   icon="el-icon-delete"
                                   type="danger"
                                   @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="margin-top: 10px;right: 0;padding: 0;"
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
                    <Update v-if="dialog.type === 'add' || dialog.type === 'edit'"
                            :type="dialog.type"
                            :data="dialog.data"
                            :coalSourceMethodOptions="coalSourceMethodOptions"
                            @submitSave="submitSave"
                            @close="dialog.visible=false" />
                </template>
            </el-dialog>
        </div>
    </div>
</template>

  <script>
import { getDicts } from '@/api/system/dict/data'
import {
    productionReportUnloadingList,
    productionReportUnloadingDelete,
} from '@/api/production/oi/cokingMaintain'
import Update from './update'
import { mapGetters } from 'vuex'

export default {
    components: {
        Update,
    },
    data() {
        return {
            dialog: {
                visible: false,
                type: '',
                title: '',
                width: '40%',
                data: {},
            },
            page: {
                size: 20,
                current: 1,
                total: 1,
                order: 'statistics_date',
                orderby: 'desc',
            },
            query: {
                timeArr: null,
                coal_way: null,
            },
            table: {
                border: true,
                loading: false,
            },
            coalSourceMethodOptions: [],
            tableData: [],
        }
    },
    computed: {
        ...mapGetters(['userInfo']),
    },
    created() {
        this.getComeCoalWay()
        this.onLoad()
    },
    methods: {
        //获取来煤方式下拉列表
        getComeCoalWay() {
            getDicts('report_coal_way').then((response) => {
                this.coalSourceMethodOptions = response.data.data.map((i) => {
                    return { value: i.dictValue, label: i.dictLabel }
                })
            })
        },
        //载入数据
        onLoad() {
            if (this.query.timeArr && this.query.timeArr[0]) {
                this.query.start_time = this.query.timeArr[0]
                this.query.end_time = this.query.timeArr[1]
            } else {
                this.query.start_time = ''
                this.query.end_time = ''
            }
            this.table.loading = true //加载状态
            productionReportUnloadingList(this.page, this.query).then(
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
                this.page.order = 'statistics_date'
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
            this.handleQuery()
        },
        //新增
        handleAdd(type) {
            this.dialog.type = type
            this.dialog.title = '新增煤场卸车情况'
            this.dialog.data = {}
            this.dialog.visible = true
        },
        //修改
        handleEdit(type, index, row) {
            this.dialog.type = type
            this.dialog.title = '编辑煤场卸车情况'
            this.dialog.data = row
            // console.log(row, "row")
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
                    productionReportUnloadingDelete({
                        id: row.id,
                        delete_user_id: this.userInfo.userId,
                        delete_user_name: this.userInfo.userName,
                    }).then(
                        (res) => {
                            this.onLoad()
                            this.$message({
                                type: 'success',
                                message: res.data.msg,
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
.el-button--success {
    background-color: #b3e19d;
    color: white;
    border: white;
}
</style>

