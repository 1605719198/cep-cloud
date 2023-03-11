<template>
  <div class="app-container">
    <el-form :model="query" ref="query" :inline="true"  label-width="68px">
      <el-form-item prop="unloading" label="卸车日期">
        <el-date-picker v-model="query.unloading"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="卸车开始日期"
                        end-placeholder="卸车结束日期"
                        value-format="yyyy-MM-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item prop="class_name" label="班别">
        <el-select :popper-append-to-body="false"
                   v-model="query.class_name"
                   clearable
                   placeholder="选择班别">
          <el-option v-for="item in classOptions"
                     :key="item.id"
                     :label="item.name"
                     :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
<!--      <el-form-item prop="shift_name" label="班次">-->
<!--        <el-select :popper-append-to-body="false"-->
<!--                   v-model="query.shift_name"-->
<!--                   clearable-->
<!--                   placeholder="选择班次">-->
<!--          <el-option v-for="item in shiftOptions"-->
<!--                     :key="item.value"-->
<!--                     :label="item.label"-->
<!--                     :value="item.value">-->
<!--          </el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item prop="shipping_method" label="运输方式">
        <el-select :popper-append-to-body="false"
                   v-model="query.shipping_method"
                   clearable
                   placeholder="选择运输方式">
          <el-option v-for="item in shippingOptions"
                     :key="item.id"
                     :label="item.name"
                     :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="is_done" label="完成状态">
        <el-select :popper-append-to-body="false"
                   v-model="is_done"
                   clearable
                   placeholder="选择完成状态">
          <el-option v-for="item in statusOptions"
                     :key="item.id"
                     :label="item.name"
                     :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
<!--      <el-form-item prop="material_code" label="物料名称">-->
<!--        <el-select :popper-append-to-body="false"-->
<!--                   class="customSelectStyle"-->
<!--                   v-model="query.material_code"-->
<!--                   clearable-->
<!--                   filterable-->
<!--                   placeholder="选择物料名称">-->
<!--          <el-option v-for="item in materialsOptions"-->
<!--                     :key="item.id"-->
<!--                     :label="item.name"-->
<!--                     :value="item.id">-->
<!--          </el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" v-hasPermi="['listMaterialUnloadingPerformance']" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="table.loading" :data="tableData" @sort-change="handleSort" height="67vh">
      <el-table-column label="序号"
                       sortable
                       width="65"
                       type="index"
                       align="left"/>
      <el-table-column label="检斤计划编号"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="plan_id"/>
      <el-table-column label="检斤计划日期"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="plan_date"/>
      <el-table-column label="供货单位名称"
                       sortable
                       minWidth="260"
                       align="left"
                       prop="supplier_name"/>
      <el-table-column label="运输方式"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="shipping_method"
                       :formatter="shippingMethodFormatter"/>
      <el-table-column label="车号"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="vehicle_number"/>
      <el-table-column label="储位编号"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="storage_maintain_space_name"/>
      <el-table-column label="物料名称"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="material_name"/>
      <el-table-column label="物料重量(t)"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="material_weight">
        <template slot-scope="scope">
          {{ (scope.row.material_weight / 1000).toFixed(3) }}
        </template>
      </el-table-column>
      <el-table-column label="班别"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="class_name"/>
      <el-table-column label="班次"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="shift_name"/>
      <el-table-column label="卸车时间"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="unloading_time"/>
      <el-table-column fixed="right"
                       label="操作"
                       width="180"
                       align="center">
        <template slot-scope="scope">
          <el-button v-hasPermi="['listMaterialUnloadingPerformance']"
                     size="mini"
                     plain
                     icon="el-icon-edit"
                     type="update"
                     @click="handleEdit('edit', scope.$index, scope.row)"> 修改
          </el-button>
          <el-button v-hasPermi="['listMaterialUnloadingPerformance']"
                     size="mini"
                     plain
                     icon="el-icon-view"
                     type="info"
                     @click="handleView('view', scope.$index, scope.row)">详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>

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
          <Edit v-if="dialog.type === 'edit'"
                :data="dialog.data"
                @submitSave="submitSave"
                @close="dialog.visible = false"/>
          <Views v-if="dialog.type === 'view'"
                 :data="dialog.data"
                 @close="dialog.visible = false"/>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  import {listMaterialsBoxM} from "@/api/material/mr/parameter/materialCode"
  import {listMaterialUnloadingPerformance} from "@/api/material/mr/unloadCoal/index";
  import Edit from "./edit"
  import Views from "./view"
  import { getDicts } from '@/api/system/dict/data'

  export default {
    components: {
      Edit,
      Views
    },
    data() {
      return {
        classOptions: [
          {id: '甲', name: '甲'},
          {id: '乙', name: '乙'},
          {id: '丙', name: '丙'}
        ],
        shiftOptions: [
          {value: "常白班", label: "常白班"},
          {value: "白班", label: "白班"},
          {value: "夜班", label: "夜班"},
          {value: "休息", label: "休息"}
        ],
        shippingOptions: [
          {id: "", name: "全部"},
          {id: "T", name: "铁运"},
          {id: "Q", name: "汽运"},
        ],
        statusOptions: [
          {id: '0', name: '全部'},
          {id: '1', name: '未完成'},
          {id: '2', name: '已完成'}
        ],
        dialog: {
          visible: false,
          type: '',
          title: '',
          width: '50%',
          data: {},
        },
        page: {
          size: 20,
          current: 1,
          total: 1,
          order: "plan_date",
          orderby: "desc",
        },
        //完成状态（解决双向绑定回显问题，为空的时候不显示数据）
        is_done: '',
        query: {
          unloading: null,
          is_done: null,
          shipping_method: null,
          class_name: null,
          shift_name: null,
          material_code: null
        },
        materialsOptions: [],
        table: {
          border: true,
          loading: true,
        },
        tableData: [],
      }
    },
    created() {
      getDicts("sys_classtype").then(response => {
        this.classOptions = response.data.map((i)=>{
          return { id: i.dictLabel, name:  i.dictLabel };
        });
      });
      getDicts("sys_shift_no").then(response => {
        this.shiftOptions = response.data.map((i)=>{
          return { value: i.dictValue, label:  i.dictLabel };
        });
      });
      this.getMaterialsOptions()
      this.onLoad();
    },
    methods: {
      //获取煤种
      getMaterialsOptions() {
        const param = {}
        listMaterialsBoxM(param).then((res) => {
          // console.log(res)
          let options = []
          res.data.forEach(item => {
            let i = {
              id: item.materials_code,
              name: item.materials_name
            }
            options.push(i)
          });
          this.materialsOptions = options
        })
      },
      //载入数据
      onLoad() {
        if (this.query.unloading && this.query.unloading[0]) {
          this.query.unloadingStart = this.query.unloading[0]
          this.query.unloadingEnd = this.query.unloading[1]
        } else {
          this.query.unloadingStart = ''
          this.query.unloadingEnd = ''
        }
        //完成状态
        if (this.is_done === '') {
          this.query.is_done = 0
        } else {
          this.query.is_done = this.is_done
        }
        this.table.loading = true;//加载状态
        listMaterialUnloadingPerformance(this.page, this.query).then(res => {
          this.table.loading = false;
          let data = res.data;//表格相关数据
          this.page.total = data.total;//数据总数
          this.tableData = data.records;//表格数据
        }, error => {
          this.table.loading = false;
          window.console.log(error);
        });

      },
      // 分页-每页多少条
      handleSizeChange(val) {
        this.page.size = val;
        this.onLoad();
      },
      // 分页-当前页
      handleCurrentChange(val) {
        this.page.current = val;
        this.onLoad();
      },
      // 排序
      handleSort(column) {
        // console.log(column, "column")
        if (column.order === null) {
          //默认
          this.page.order = "plan_date";
          this.page.orderby = "desc";
        } else {
          //选中项
          this.page.order = column.prop;
          this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
        }
        this.onLoad();
      },
      //查询
      handleQuery() {
        this.page.current = 1;
        // console.log(this.query.issueDate)
        this.onLoad();
      },
      // 重置
      handleReset() {
        this.is_done = ''
        this.page.current = 1
        this.$refs["query"].resetFields();
        this.handleQuery()
      },
      //修改
      handleEdit(type, index, row) {
        this.dialog.type = type
        this.dialog.title = '编辑卸车实绩'
        this.dialog.data = row;
        // console.log(row, "row")
        this.dialog.visible = true;
      },
      //详情
      handleView(type, index, row) {
        this.dialog.type = type
        this.dialog.title = '卸车实绩详情'
        this.dialog.data = row;
        // console.log(row, "row")
        this.dialog.visible = true;
      },
      //提供给子类调用的方法
      submitSave() {
        this.dialog.visible = false;
        this.dialog.data = {};
        this.onLoad();
      },
      //格式化运输方式数据
      shippingMethodFormatter(row) {
        if (row.shipping_method === "T") {
          return "铁运";
        } else if (row.shipping_method === "Q") {
          return "汽运";
        }
      },
    }
  }
</script>

<style scoped lang="scss">
  .el-card__body {
    padding: 10px 20px 20px 10px;
  }

  .pagination {
    white-space: nowrap;
    padding: 25px 0px 20px 0px;
    color: #303133;
    font-weight: 700;
  }

  .main {
    // height: 86vh;
    background-color: #fff;
  }

  .search {
    width: 100%;
    display: flex;
    justify-content: space-between;
  }

  ::v-deep .el-select-dropdown__wrap.el-scrollbar__wrap {
    margin-bottom: 0 !important;
  }

  .el-button--update {
    background-color: #b3e19d;
    color: white;
  }

  .el-button--update:hover {
    background-color: #b3e19d;
    color: white;
    border: #b3e19d;
  }

  .el-button--update:focus {
    background-color: #b3e19d;
    color: white;
    border: #b3e19d;
  }

  .el-button--success {
    background-color: #b3e19d;
    color: white;
    border: white;
  }

  .el-dialog__wrapper {
    ::v-deep .el-dialog {
      .el-dialog__header {
        background-color: whitesmoke;
        border-radius: 10px 10px 0 0;
        padding: 10px 20px 10px;
      }
    }
  }

  .el-dialog__wrapper {
    ::v-deep .el-dialog {
      border-radius: 10px;
    }
  }

  .el-dialog__wrapper {
    ::v-deep .el-dialog {
      .el-dialog__body {
        padding: 10px 40px 40px 40px;
      }
    }
  }

  .customDialogStyle .el-dialog__body {
    padding: 10px 40px 40px;
  }
</style>

