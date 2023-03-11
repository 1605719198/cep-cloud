<template>
    <div class="avue-crud">
        <el-table height="67vh"
                  border
                  size="small"
                  v-loading="table.loading"
                  :data="tableData"
                  stripe
                  @sort-change="handleSort">
            <template v-if="!table.loading">
                <template v-if="type==='inStorage'">
                    <el-table-column label="物料名称"
                                     sortable
                                     minWidth="150"
                                     align="left"
                                     prop="goods_name" />
                    <el-table-column label="车号"
                                     sortable
                                     minWidth="150"
                                     align="left"
                                     prop="car_no" />
                    <el-table-column label="称重日期"
                                     sortable
                                     minWidth="150"
                                     align="left"
                                     prop="pound_date" />
                    <el-table-column label="产出量(t)"
                                     sortable
                                     minWidth="150"
                                     align="left"
                                     prop="complete_pound_num">
                        <template slot-scope="scope">
                            {{scope.row.complete_pound_num / 1000}}
                        </template>
                    </el-table-column>
                </template>
                <template v-if="type==='outStorage'">
                    <el-table-column label="类型"
                                     sortable
                                     minWidth="150"
                                     align="left"
                                     prop="type" />
                    <el-table-column label="物料名称"
                                     sortable
                                     minWidth="150"
                                     align="left"
                                     prop="materials_name" />
                    <el-table-column label="客户名称"
                                     sortable
                                     minWidth="150"
                                     align="left"
                                     prop="client_name" />
                    <el-table-column label="车牌号"
                                     sortable
                                     minWidth="150"
                                     align="left"
                                     prop="vehicle_number" />
                    <el-table-column label="装车日期"
                                     sortable
                                     minWidth="150"
                                     align="left"
                                     prop="loading_time" />
                    <el-table-column label="出库量(t)"
                                     sortable
                                     minWidth="150"
                                     align="left"
                                     prop="net_wgt">
                        <template slot-scope="scope">
                            {{scope.row.net_wgt / 1000}}
                        </template>
                    </el-table-column>
                </template>
            </template>
        </el-table>
        <div style="margin-top: 10px;right: 0"
             class="avue-crud__pagination">
            <pagination v-show="page.total > 0"
                           background
                           @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
                           layout="total, sizes, prev, pager, next, jumper"
                           :current-page="page.current"
                           :page-sizes="[20, 50, 100, 200]"
                           :page-size="page.size"
                           :total="page.total">
            </pagination>
        </div>
    </div>
</template>

<script>
import {
    getCokePutStorageList,
    getCokeOutStorageList,
} from '@/api/material/mr/cokeInventory/index'

export default {
    props: ['data', 'type'],
    data() {
        return {
            page: {
                size: 20,
                current: 1,
                total: 1,
                order: 'pound_date',
                orderby: 'asc',
            },
            query: { materialsId: this.data.materials_id },
            table: { border: true, loading: true },
            tableData: [],
        }
    },
    created() {
        if (this.type === 'inStorage') {
            this.page.order = 'pound_date'
        } else if (this.type === 'outStorage') {
            this.page.order = 'loading_time'
        }
        this.onLoad()
    },
    methods: {
        // 载入数据
        onLoad() {
            this.table.loading = true //加载状态
            if (this.type === 'inStorage') {
                getCokePutStorageList(this.page, this.query).then(
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
            } else {
                getCokeOutStorageList(this.page, this.query).then(
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
            }
        },
        // 排序
        handleSort(column) {
            if (column.order === null) {
                this.page.order = 'pound_date'
                this.page.orderby = 'desc'
            } else {
                this.page.order = column.prop
                this.page.orderby =
                    column.order === 'ascending' ? 'asc' : 'desc'
            }
            this.onLoad()
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
    },
    watch: {
        type(val) {
            if (val === 'inStorage') {
                this.page = {
                    size: 20,
                    current: 1,
                    total: 1,
                    order: 'pound_date',
                    orderby: 'desc',
                }
            } else {
                this.page = {
                    size: 20,
                    current: 1,
                    total: 1,
                    order: 'loading_time',
                    orderby: 'desc',
                }
            }
            this.onLoad()
        },
    },
}
</script>

<style>
</style>
