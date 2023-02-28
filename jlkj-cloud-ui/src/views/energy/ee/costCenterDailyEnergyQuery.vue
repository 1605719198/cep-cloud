<template>
  <div class="app-container">
    <el-form :ref="query"
             :model="query"
             status-icon
             :rules="rules"
             :inline="true"
             label-width="102px">
      <el-form-item label="计量日期" prop="eCountDateStart">
        <el-date-picker style="width: 215px!important;"
                        v-model="query.eCountDateStart"
                        type="date"
                        placeholder="选择能源计量日期">
        </el-date-picker>
        <span class="el-text"> *</span>
        ~
        <el-date-picker style="width: 215px!important;"
                        v-model="query.eCountDateEnd"
                        type="date"
                        placeholder="选择能源计量日期">
        </el-date-picker>
        <span class="el-text"> *</span>
      </el-form-item>

      <el-form-item>
        <el-button v-hasPermi="['consume_output_query']"
                   type="primary"
                   size="mini"
                   icon="el-icon-search"
                   @click="handleQuery('query')">搜 索</el-button>
        <el-button size="mini"
                   type="default"
                   icon="el-icon-refresh-left"
                   @click="handleEmpty('query')">重 置</el-button>
        <el-button type="text"
                   icon="el-icon-info"
                   @click="handleAdvanceFilter">高级筛选</el-button>
      </el-form-item>
      <el-drawer class="el-icon-info"
                 title="高级筛选"
                 :append-to-body="true"
                 :before-close="handleClose"
                 :visible.sync="drawer"
                 direction="rtl"
                 custom-class="demo-drawer"
                 :wrapperClosable="true">
        <div class="demo-drawer__content">
          <el-form ref="query"
                   :model="query"
                   status-icon
                   :rules="rules"
                   label-width="120px">
            <el-row :gutter="20">
              <el-col :span="24"
                      style="margin-bottom: 0;">
                <div class="el-form-item__content">
                  <el-row>
                    <el-col style="display: flex;">
                      <el-form-item label="能源计量日期"
                                    prop="eCountDateStart">
                        <el-date-picker style="width: 83%!important;"
                                        v-model="query.eCountDateStart"
                                        type="date"
                                        placeholder="选择日期">
                        </el-date-picker>
                        <span class="el-text"> *</span>
                      </el-form-item>
                      <div style="display: flex;margin-left: -21px;">
                        <div>~</div>
                        <el-form-item label=""
                                      prop="eCountDateEnd"
                                      label-width="0">
                          <el-date-picker style="width: 83%!important;"
                                          v-model="query.eCountDateEnd"
                                          type="date"
                                          placeholder="选择日期">
                          </el-date-picker>
                          <span class="el-text"> *</span>
                        </el-form-item>
                      </div>
                    </el-col>
                  </el-row>
                  <el-form-item label="成本中心"
                                prop="costCenterA">
                    <el-select v-model="query.costCenterA"
                               :popper-append-to-body="false"
                               placeholder="请选择"
                               style="width: 40%!important;">
                      <el-option v-for="item in optionsCostCenterA"
                                 :key="item.value"
                                 :label="item.label"
                                 :value="item.value">
                      </el-option>
                    </el-select>
                    &nbsp;、
                    <el-select v-model="query.costCenterB"
                               :popper-append-to-body="false"
                               placeholder="请选择"
                               style="width: 40%!important;">
                      <el-option v-for="item in optionsCostCenterB"
                                 :key="item.value"
                                 :label="item.label"
                                 :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item label="能源代码">
                    <el-select v-model="query.engyIdStart"
                               :popper-append-to-body="false"
                               placeholder="请选择"
                               style="width: 40%!important;">
                      <el-option v-for="item in optionsEngyIdStart"
                                 :key="item"
                                 :label="item"
                                 :value="item">
                      </el-option>
                    </el-select>
                    &nbsp;&nbsp;~
                    <el-select v-model="query.engyIdEnd"
                               :popper-append-to-body="false"
                               placeholder="请选择"
                               style="width: 40%!important;">
                      <el-option v-for="item in optionsEngyIdEnd"
                                 :key="item"
                                 :label="item"
                                 :value="item">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </div>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24"
                      style="margin-bottom: 0;">
                <el-form-item label="排序方式"
                              prop="orderByNo">
                  <el-select v-model="query.orderByNo"
                             :popper-append-to-body="false"
                             placeholder="请选择"
                             style="width: 40%!important;">
                    <el-option v-for="item in optionsOrderByNo"
                               :key="item.value"
                               :label="item.label"
                               :value="item.value">
                    </el-option>
                  </el-select>
                  <span class="el-text"> *</span>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div class="demo-drawer__footer"
               style="text-align: center">
            <el-button v-hasPermi="['consume_output_query']"
                       type="primary"
                       size="mini"
                       icon="el-icon-search"
                       @click="handleQuery('query')">搜 索</el-button>
            <el-button size="mini"
                       type="default"
                       icon="el-icon-refresh-left"
                       @click="handleReset('query')">重 置</el-button>
          </div>
        </div>
      </el-drawer>
    </el-form>

    <el-form style="margin:0px 20px 0px 20px ;">
      <el-table size="small"
                :data="tableData"
                stripe
                :default-sort="{prop: 'proConQty', order: 'descending'}">
        <template v-for="column in columns">
          <el-table-column :key="column.prop"
                           :prop="column.prop"
                           :label="column.label"
                           :sortable="column.sortable"
                           :width="column.width"
                           :min-width="column.minWidth" />
        </template>
      </el-table>

      <div>
        <p> 能源产耗总量：{{ message }}</p>
      </div>

      <div style="margin: 8px 0px 15px"
           class="avue-crud__pagination">
        <pagination background
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="page.currentPage"
                    :page-sizes="[20, 50, 100, 200]"
                    :page-size="page.pageSize"
                    :layout="page.layout"
                    :total="page.total">
        </pagination>
      </div>
    </el-form>


  </div>
</template>


<script>

import { queryInfo, querySumInfo, queryEngyIds } from "@/api/energy/ee/costCenterDailyEnergyQuery";

export default {
  name: "costCenterDailyEnergyQuery",
  data () {
    var checkA = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('能源计量日期栏位为空，请重新输入！'));
      } else {
        callback();
      }
    };
    var checkB = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('排序方式栏位为空，请重新输入！'));
      } else {
        callback();
      }
    };
    return {
      message: '',
      addBox: false,
      editBox: false,
      delBox: false,
      dataEdit: {},
      // 是否显示抽屉
      drawer: false,
      page: {
        pageSize: 20,
        currentPage: 1,
        total: 0,
        layout: "total, sizes, prev, pager, next, jumper",
      },
      query: {
        pageSize: 20,
        pageNum: 1,
        eCountDateStart: undefined,
        eCountDateEnd: undefined,
        costCenterA: undefined,
        costCenterB: undefined,
        engyIdStart: undefined,
        engyIdEnd: undefined,
        orderByNo: undefined,
      },
      table: {
        border: true
      },
      columns: [
        { label: '能源计量日期', prop: "eCountDate", sortable: true, minWidth: '100px' },
        { label: '产耗类别', prop: "ioTypeName", sortable: true, minWidth: '100px' },
        { label: '成本中心', prop: "costCenter", sortable: true, minWidth: '100px' },
        { label: '能源代码', prop: "engyId", sortable: true, minWidth: '100px' },
        { label: '能源名称', prop: "engyName", sortable: true, minWidth: '100px' },
        { label: '能源产耗量', prop: "proConQty", sortable: true, minWidth: '100px' },
        { label: '计量单位', prop: "engyUnit", sortable: true, minWidth: '80px' },
        { label: '热值系数', prop: "calValue", sortable: true, minWidth: '80px' },
        { label: '热值系数单位', prop: "calUnit", sortable: true, minWidth: '80px' },
      ],
      tableData: [],
      rules: {
        eCountDateStart: [
          { validator: checkA, trigger: 'blur' }
        ],
        orderByNo: [
          { validator: checkB, trigger: 'blur' }
        ],
        eCountDateEnd: [
          { validator: checkA, trigger: 'blur' }
        ]
      },
      optionsCostCenterA: [{
        value: '3',
        label: '3'
      }, {
        value: '4',
        label: '4'
      }],
      optionsCostCenterB: [{
        value: '2',
        label: '2'
      }, {
        value: '1',
        label: '1'
      }],
      optionsEngyIdStart: [],
      optionsEngyIdEnd: [],
      optionsOrderByNo: [{
        value: '1',
        label: '成本中心代号 , 能源计量日期 , 能源代码 , 耗用产出类别代号'
      }, {
        value: '2',
        label: '能源计量日期 , 成本中心代号 , 能源代码 , 耗用产出类别代号'
      }, {
        value: '3',
        label: '能源代码 , 能源计量日期 , 成本中心代号 , 耗用产出类别代号'
      }, {
        value: '4',
        label: '耗用产出类别代号 , 能源代码 , 能源计量日期 , 成本中心代号'
      }],
    }
  },
  created () {
    this.getList()
    queryEngyIds().then(response => {
      this.optionsEngyIdStart = response.data;
      this.optionsEngyIdEnd = response.data;
      this.loading = false
    })
  },
  methods: {
    //查询
    handleQuery (form) {
      if (this.$refs[form] !== undefined) {
        this.$refs[form].validate((valid) => {
          if (valid) {
            this.start = new Date(this.query.eCountDateStart)
            this.end = new Date(this.query.eCountDateEnd)

            if (this.start <= this.end) {
              let query = this.query
              queryInfo(query).then(response => {
                this.tableData = response.data.list
                this.page.total = response.data.total
              })
              querySumInfo(query).then(response => {
                this.message = response.data.sum
              })
            } else {
              this.$message({ type: 'warning', message: '请输入正确的日期区间！' });
            }
          } else {
            return false;
          }
        });
      }
    },
    // 高级筛选关闭
    handleClose (done) {
      console.log(this.query)
      this.query = {};
      console.log(this.query)
      done();
    },
    // 高级筛选弹出
    handleAdvanceFilter () {
      this.drawer = true;
    },
    // 重置
    handleReset (formName) {
      this.$refs[formName].resetFields();
      this.query = {
        //当前页
        pageNum: 1,
        //每页记录数
        pageSize: 20,
      };
    },
    // 清空
    handleEmpty (formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields();
      }
      this.query = {
        pageSize: 20,
        pageNum: 1,
        eCountDateStart: undefined,
        eCountDateEnd: undefined,
        costCenterA: undefined,
        costCenterB: undefined,
        engyIdStart: undefined,
        engyIdEnd: undefined,
        orderByNo: undefined,
      }
    },
    // 新增
    handleAdd () {
      this.dataEdit = {};
      this.addBox = true;
      this.editBox = false;
    },
    // 分页-每页多少条
    handleSizeChange (val) {
      this.query.pageSize = val
      this.getList()
    },
    // 分页-当前页
    handleCurrentChange (val) {
      this.query.pageNum = val
      this.getList()
    },
    //获取数据刷新页面
    getList () {
      queryInfo(this.query).then(response => {
        if (response.data != null) {
          this.tableData = response.data.list
          this.page.total = response.data.total
        }
      })
      querySumInfo(this.query).then(response => {
        if (response.data != null) {
          this.message = response.data.sum
        }
      })
    },
  }
}
</script>

<style scoped>
.energy_main {
  height: 86vh;
  background-color: #fff;
}

/deep/ .el-form-item__label {
  width: 120px;
  text-align: left;
  padding-left: 23px;
}

.el-text {
  color: red;
}

/*下拉框最后一个显示不完全*/
/deep/ .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
