v-hasPermi="['manufacturer_queryOne']"<template>
  <div>
    <el-row>
      <el-col :span="24"
              style="padding:0 10px">

        <!--        table主体-->
        <div class="plan_main">
          <div class="avue-crud el-card__body"
               style="width: 98%;border: 0">
            <!--           条件搜索 -->
            <div class="avue-crud__search"
                 style="border: 0">
              <el-form :model="queryParams"
                       ref="queryForm"
                       size="small"
                       :inline="true"
                       v-show="showSearch"
                       label-width="100px">
                <el-row :gutter="20">
                  <el-col :span="3">
                    <div class="el-form-item el-form-item--small">
                      <div class="el-form-item__content">
                        <el-date-picker value-format="yyyy"
                                        format="yyyy"
                                        v-model="queryParams.fiscalYearDate"
                                        @change="accountPeriodDateChange"
                                        type="year"
                                        placeholder="选择日期"></el-date-picker>
                      </div>
                    </div>
                  </el-col>
                  <el-col :span="3">
                    <div class="el-form-item el-form-item--small">
                      <div class="el-form-item__content">
                        <el-input v-model="queryParams.costCenter"
                                  placeholder="请输入成本中心编码"
                                  clearable />
                      </div>
                    </div>
                  </el-col>
                  <el-col :span="3">
                    <div class="el-form-item el-form-item--small">
                      <div class="el-form-item__content">
                        <el-input v-model="queryParams.costCenterDesc"
                                  placeholder="请输入成本中心名称"
                                  clearable />
                      </div>
                    </div>
                  </el-col>
                  <el-col :span="10">
                    <div class="el-form-item__content"
                         style="margin-left: 0px;">
                      <el-button v-hasPermi="['costCenter_queryAll']"
                                 type="primary"
                                 size="mini"
                                 @click="handleQuery"
                                 icon="el-icon-search">搜索</el-button>
                      <el-button size="mini"
                                 @click="resetQuery"
                                 icon="el-icon-refresh-left">重置</el-button>
                    </div>
                  </el-col>
                  <el-col :span="5">
                    <div class="el-form-item__content"
                         style="float: right">
                      <el-button v-hasPermi="['costCenter_doAdd']"
                                 type="primary"
                                 plain
                                 size="mini"
                                 icon="el-icon-plus"
                                 @click="AddleUpdate">新增</el-button>
                    </div>
                  </el-col>
                </el-row>
              </el-form>
            </div>
            <!--            表单数据-->
            <div>
              <el-form>
                <el-table height="67vh"
                          size="small"
                          stripe
                          v-loading="loading"
                          :data="financetestList"
                          :header-cell-style="{background:'#FAFAFA'}">
                  <el-table-column label="年度"
                                   sortable
                                   align="center"
                                   prop="fiscalYear" />
                  <el-table-column label="成本中心"
                                   sortable
                                   align="center"
                                   prop="costCenter" />
                  <el-table-column label="成本中心名称"
                                   sortable
                                   align="center"
                                   prop="costCenterDesc" />
                  <el-table-column label="成本中心形态属性"
                                   sortable
                                   align="center"
                                   prop="ccType" />
                  <el-table-column label="操作"
                                   align="center"
                                   class-name="small-padding fixed-width">
                    <template slot-scope="scope">
                      <el-button v-hasPermi="['costCenter_queryOne']"
                                 icon="el-icon-info"
                                 type="text"
                                 size="mini"
                                 @click="handleDetails(scope.row)">详情</el-button>
                      <el-button v-hasPermi="['costCenter_doEdit']"
                                 size="mini"
                                 icon="el-icon-edit"
                                 type="text"
                                 @click="handleUpdate(scope.row)">编辑</el-button>
                      <el-button v-hasPermi="['costCenter_delete']"
                                 size="mini"
                                 type="text"
                                 icon="el-icon-delete"
                                 @click="handleDelete(scope.row)">删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <!--                分页-->
                <div style="margin-top: 10px;right: 0"
                     class="avue-crud__pagination">
                  <el-pagination background
                                 :total="total"
                                 :current-page="queryParams.pageNum"
                                 :page-sizes="[20, 50, 100, 200]"
                                 :page-size="queryParams.pageSize"
                                 layout="total, sizes, prev, pager, next, jumper"
                                 @size-change="handleSizeChange"
                                 @current-change="handleCurrentChange"
                                 style="float: right;">
                  </el-pagination>
                </div>
              </el-form>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <!-- 添加或修改参数配置对话框 -->
    <CostCenterCost @getLists="getListHandle"
                    :visible.sync="CostCenterCostVisible"
                    v-if="CostCenterCostVisible"
                    ref="addOrUpdate"></CostCenterCost>
  </div>

</template>

<script>
import { listFinancetest, delFinancetest } from "@/api/finance/ac/financetest";

import CostCenterCost from './CostCenterCost'

export default {
  name: "CostCenter",
  components: {
    CostCenterCost
  },
  dicts: ['cost_costtype'],
  data () {
    return {
      //添加按键熟悉
      AddVisible: false,
      //编辑按键熟悉
      UpdateVisible: false,
      visible: false,
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      //添加-编辑
      CostCenterCostVisible: false,
      // 总条数
      total: 0,
      // 测试表格数据
      financetestList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: true,
      // 查询参数
      queryParams: {
        // 总条数
        total: 0,
        pageNum: 1,
        pageSize: 10,
        fiscalYear: '',
        fiscalYearDate: '',
        costCenter: null,
        costCenterDesc: null,
        ccType: null,
        compId: 'J00'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created () {
    this.getList();
  },
  methods: {
    // 年度日期切换
    accountPeriodDateChange (val) {
      this.accountPeriodSub = val
      this.queryParams.fiscalYear = val
    },
    // 分页数据
    handleSizeChange (val) {
      this.queryParams.pageSize = val
      this.getList()
    },
    handleCurrentChange (val) {
      this.queryParams.pageNum = val
      this.getList()
    },
    /** 查询测试列表 */
    getList () {
      this.loading = false;

      listFinancetest(this.queryParams).then(response => {
        if (response.data.data == null) {
          this.queryParams.fiscalYearDate = new Date()
          this.financetestList = []
          this.total = 0;
          this.costAccount = true
        } else {
          this.financetestList = response.data.data.list;
          this.queryParams.fiscalYearDate = new Date(response.data.data.list[0].fiscalYear.substring(0, 4))

          this.financetestList = this.financetestList.map(item => {
            switch (item.ccType) {
              case 'A': item.ccType = 'A-生产管理性成本中心'; break;
              case 'P': item.ccType = 'P-生产性成本中心'; break;
              case 'R': item.ccType = 'R-销管研训成本中心'; break;
              case 'S': item.ccType = 'S-服务性成本中心'; break;
              case 'T': item.ccType = 'T-统计性成本中心'; break;
              case 'V': item.ccType = 'V-资本支出性成本中心'; break;

            }
            return item;
          })
          this.total = response.data.data.total;
          this.loading = false;
        }
      });

    },

    // 取消按钮
    cancel () {
      this.CostCenterCostVisible = false
      this.reset();
    },
    // 表单重置
    reset () {
      this.form = {};
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.queryParams = {
        total: 0,
        pageNum: 1,
        pageSize: 10,
        fiscalYear: '',
        fiscalYearDate: '',
        costCenter: null,
        costCenterDesc: null,
        ccType: null,
        compId: 'J00'
      }
      listFinancetest(this.queryParams).then(response => {
        if (response.data.data == null) {
          this.queryParams.fiscalYearDate = new Date()
          this.financetestList = []
          this.total = 0;
          this.costAccount = true
        } else {
          this.financetestList = response.data.data.list;
          this.queryParams.fiscalYearDate = new Date(response.data.data.list[0].fiscalYear.substring(0, 4))
          this.financetestList = this.financetestList.map(item => {
            switch (item.ccType) {
              case 'A': item.ccType = 'A-生产管理性成本中心'; break;
              case 'P': item.ccType = 'P-生产性成本中心'; break;
              case 'R': item.ccType = 'R-销管研训成本中心'; break;
              case 'S': item.ccType = 'S-服务性成本中心'; break;
              case 'T': item.ccType = 'T-统计性成本中心'; break;
              case 'V': item.ccType = 'V-资本支出性成本中心'; break;
            }
            return item;
          })
          this.total = response.data.data.total;
          this.loading = false;
        }

      });

    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd () {
      this.reset();
      this.open = true;
      this.title = "添加测试";
    },
    /** 添加按钮操作 */
    AddleUpdate () {
      this.CostCenterCostVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.AddPage(this.dict.type.cost_costType)
      })

    },
    /** 详情按钮操作 */
    handleDetails (row) {
      this.CostCenterCostVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.Details(row.costCenter, row.id, this.dict.type.cost_costType)
      })
    },
    /** 修改按钮操作 */
    handleUpdate (row) {
      this.CostCenterCostVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.Update(row.costCenter, row.id, this.dict.type.cost_costType)
      })
    },

    /** 删除按钮操作 */
    handleDelete (row) {
      const id = row.id || this.ids;
      this.$confirm('此操作将永久删除数据记录，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {//点击确定，执行then方法
        //调用删除的方法
        delFinancetest(id).then(response => {
          if (response.data.code === "0") {
            this.getList();
          }
        })

      })
    },
    getListHandle () {
      this.queryParams.fiscalYear = ''
      this.getList()

    }
  }
};
</script>
<style scoped>
.link-type {
  color: #337ab7;
  cursor: pointer;
}

/*.avue-crud .el-date-editor.el-input {*/
/*  width: auto !important;*/
/*}*/
.plan_header {
  background-color: #f9f9f9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #e9e9e9 solid;
}

.plan_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold;
}

.plan_main {
  height: 86vh;
  background-color: #fff;
}
</style>
<style>
.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}

/* 弹窗圆角 */
.customDialogStyle .el-dialog {
  border-radius: 8px;
}

/* 标题样式 */
.customDialogStyle .el-dialog__header {
  background-color: rgb(245, 245, 245);
  border-radius: 8px 8px 0 0;
  padding: 10px 20px 10px;
}

/* 标题文字样式 */
.customDialogStyle .el-dialog__title {
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  letter-spacing: normal;
  font-size: 14px;
  font-weight: 700;
  font-style: normal;
  text-align: left;
  line-height: 20px;
  border-width: 0;
  color: rgb(102, 102, 102);
}

/* 关闭符号位置调整 */
.customDialogStyle .el-dialog__headerbtn {
  top: 12px;
}

.customDialogStyle .el-dialog__body {
  padding: 15px 40px;
}
</style>
