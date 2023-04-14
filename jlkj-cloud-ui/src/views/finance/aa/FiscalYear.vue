<template>
  <div class="app-container">
              <el-form :model="queryParams" ref="queryForm"
                       :rules="rulesQuery"  size="small" :inline="true" v-show="showSearch" label-width="97px">

                <el-form-item label="公司" prop="compId">
                  <el-input v-model="queryParams.compId"
                            placeholder="请输入公司"
                            style="width: 190px"
                            clearable
                            @keyup.enter.native="handleQuery"/>
                </el-form-item>
                <el-form-item label="会计年度" prop="accountPeriodDate">
                  <el-date-picker v-model="queryParams.accountPeriodDate"
                                  type="year"
                                  format="yyyy"
                                  value-format="yyyy"
                                  placeholder="选择年"
                                  :picker-options="pickerOptions"
                                  @change="accountPeriodDateChange">
                  </el-date-picker>
                </el-form-item>
                <el-form-item>
                  <el-button v-hasPermi="['account_period_queryAll']"
                             type="primary"
                             size="mini"
                             @click="handleQuery"
                             icon="el-icon-search">搜索
                  </el-button>
                  <el-button size="mini"
                             type="default"
                             @click="resetQuery"
                             icon="el-icon-refresh-left">重置
                  </el-button>
                  <el-button v-hasPermi="['account_period_queryAll']"
                             style="width: 120px"
                             size="mini"
                             type="warning"
                             icon="el-icon-caret-left"
                             @click="LastYear()">上一年度
                  </el-button>
                  <el-button v-hasPermi="['account_period_queryAll']"
                             style="width: 120px"
                             size="mini"
                             type="warning"
                             @click="NextYear()">下一年度<i class="el-icon-arrow-right el-icon-caret-right"></i></el-button>
                  <el-button v-hasPermi="['account_period_doAdd']"
                             type="primary"
                             size="mini"
                             plain
                             icon="el-icon-plus"
                             @click="AddleUpdate()">新增会计年度
                  </el-button>
                  <el-button v-hasPermi="['account_period_delete']"
                             type="danger"
                             size="mini"
                             @click="handleDelete"
                             icon="el-icon-delete">删除会计年度
                  </el-button>
                </el-form-item>
              </el-form>
            <!--            表单数据-->
                <el-table height="67vh"
                          @selection-change="handleSelectionChange"
                          stripe
                          v-loading="loading"
                          :data="financetestList"
                          :header-cell-style="{background:'#FAFAFA'}">
<!--                  <el-table-column type="selection"
                                   width="55"
                                   align="center"/>-->
                  <el-table-column label="会计周期"
                                   sortable
                                   align="center"
                                   prop="accountPeriod"/>
                  <el-table-column label="起始日期"
                                   sortable
                                   align="center"
                                   prop="startDate"/>
                  <el-table-column label="终止日期"
                                   sortable
                                   align="center"
                                   prop="endDate"/>
                  <el-table-column label="状态"
                                   align="center"
                                   prop="isClosed">
                    <template slot-scope="scope">
                      <dict-tag :options="dict.type.finance_year_status"
                                :value="scope.row.isClosed"/>
                    </template>
                  </el-table-column>
                  <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                    <template slot-scope="scope">
                      <el-button v-hasPermi="['account_period_doEdit']"
                                 size="mini"
                                 plain
                                 icon="el-icon-caret-left"
                                 type="text"
                                 @click="handleDetails(scope.row)">
                        开启
                      </el-button>

                    </template>
                  </el-table-column>
                </el-table>
                <!--                分页-->
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




    <!-- 添加或修改测试对话框 -->
    <fiscalYearCost @getLists="getListHandle"
                    :visible.sync="CostCenterCostVisible"
                    v-if="CostCenterCostVisible"
                    ref="CostCenterCostVisible"></fiscalYearCost>
  </div>
</template>

<script>
import {listFinancetest, delFinancetest, addFinancetest, updatecloseYn} from "@/api/finance/aa/fiscalYear";
import fiscalYearCost from './FiscalYearCost'

export default {
  name: "fiscalYear",
  components: {
    fiscalYearCost
  },
  dicts: ['finance_year_status'],
  financeattritestList: [],
  data() {
    return {
      // 日期函数
      pickerOptions: {},
      //删除参数
      accountPeriodDelete: '',
      //开启按钮
      opentest: false,
      //编辑按钮
      Updatetest: false,
      //添加按键熟悉
      AddVisible: false,
      //编辑按键熟悉
      UpdateVisible: false,
      visible: false,
      // 遮罩层
      loading: false,
      // 选中数组
      isClosed: [],
      isClosedY: false,
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
      //上一年度
      accountPeriodSub: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        accountYear: "",
        accountPeriod: "",
        ccType: null,
        compId: 'J00',
        accountPeriodDate: ""
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 搜索校验
      rulesQuery: {
        compId: [
          {required: true, message: '请输入公司', trigger: 'blur'}
        ]
      },
    };
  },
  created() {

    this.getList();
  },
  methods: {
    // 年度日期切换
    accountPeriodDateChange(val) {

      this.accountPeriodSub = val
      this.queryParams.accountPeriod = val
    },
    // 分页数据
    handleSizeChange(val) {
      this.queryParams.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.queryParams.pageNum = val
      this.getList()
    },
    /** 查询测试列表 */
    getList() {
      this.loading = false;
      listFinancetest(this.queryParams).then(response => {

        if (response.data.length == 0) {
          this.financetestList = []
          this.queryParams.accountPeriodDate = new Date()
          this.accountPeriodSub = new Date().getFullYear().toString()
          this.total = 0;
        } else {
          this.financetestList = response.data.list;
          this.total = response.data.total;
          this.accountPeriodSub = this.financetestList[0].accountPeriod.substring(0, 4)
          this.queryParams.accountPeriodDate = new Date(this.financetestList[0].accountPeriod.substring(0, 4))
        }
        this.loading = false;
      });
    },
    /** 查询测试列表 */
    getList1() {
      this.loading = false;

      listFinancetest(this.queryParams).then(response => {
        if (response.data.length == 0) {
          this.financetestList = []
          this.total = 0;
        } else {
          this.financetestList = response.data.list;
          this.total = response.data.total;
        }
        this.loading = false;
      });

    },
    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        accountYear: "",
        accountPeriod: "",
        accountPeriodDate: "",
        ccType: null,
        compId: 'J00',
      }
      this.queryParams.accountPeriodDate = new Date()
      this.accountPeriodSub = new Date().getFullYear().toString()
      listFinancetest(this.queryParams).then(response => {
        if (response.data.length == 0) {
          this.financetestList = []
          this.total = 0;
        } else {
          this.financetestList = response.data.list;
          this.queryParams.accountPeriodDate = new Date(this.financetestList[0].accountPeriod.substring(0, 4))
          this.accountPeriodSub = this.financetestList[0].accountPeriod.substring(0, 4)
          this.total = response.data.total;
          this.loading = false;

        }

      });

    },
    /** 添加按钮操作 */
    AddleUpdate() {
      this.CostCenterCostVisible = true
      this.$nextTick(() => {
        this.$refs.CostCenterCostVisible.AddPage()
      })
    },
    /** 上一年度按钮操作 */
    LastYear: function () {
      this.accountPeriodSub--
      this.queryParams.accountPeriod = this.accountPeriodSub.toString();
      this.queryParams.accountPeriodDate = new Date(this.accountPeriodSub.toString());
      this.getList1()
    },
    /** 下一年度按钮操作 */
    NextYear() {
      this.accountPeriodSub++
      this.queryParams.accountPeriod = this.accountPeriodSub.toString();
      this.queryParams.accountPeriodDate = new Date(this.accountPeriodSub.toString());
      this.getList1()
    },
    /** 修改按钮操作 */
    handleSee(row) {
      this.CostCenterCostVisible = true
      this.$nextTick(() => {
        this.$refs.CostCenterCostVisible.See(row.accountPeriod, row.startDate, row.endDate, row.id)
      })
    },
    /** 开启按钮 */
    handleDetails(row) {
      updatecloseYn("N", row.id)
      this.getList();
 /*     for (let i = 0; i < this.isClosed.length; i++) {
        this.isClosed = this.isClosed[i]
        this.id = this.ids[i]
        if (this.isClosed === "N") {
          this.isClosedY = true
          this.$alert('只有会计周期结束，才能开启', {
            confirmButtonText: '确定',
          });
        }
      }

      if (this.isClosedY = false) {
        updatecloseYn("N", this.id)
        this.opentest = false
        this.Updatetest = true
      }*/

    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addFinancetest(this.form).then(response => {
            if (response.data.code === "0") {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }

          });
        }
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.isClosed = selection.map(item => item.isClosed)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },

    /** 删除按钮操作 */
    handleDelete () {

      this.accountPeriodDelete = this.accountPeriodSub.toString()


      this.$confirm('此操作将永久删除数据记录，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {//点击确定，执行then方法
        //调用删除的方法

        delFinancetest(this.accountPeriodSub, this.queryParams.compId).then(response => {
          if (response.data.code === "0") {
            this.getList();
          }
        })
      })

    },
    getListHandle () {
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
