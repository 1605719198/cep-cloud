<template>
  <div class="app-container">
    <el-form :model="queryParams"
             ref="queryForm"
             :inline="true"
             status-icon
             :rules="rulesMonth"
             v-show="showSearch"
             label-width="68px">
      <el-form-item label="能源代码" prop="engyId">
        <el-select v-model="queryParams.engyId" placeholder="请选择能源代码" clearable size="small" :popper-append-to-body="false">
          <el-option v-for="item in optionsEngyId"
                     :key="item"
                     :label="item"
                     :value="item">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="计量月份" prop="dateYM">
        <el-date-picker clearable
                        v-model="queryParams.dateYM"
                        type="month"
                        placeholder="选择能源计量月份"
                        size="small">
        </el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button v-hasPermi="['solid_liquid_query']"
                   type="primary"
                   size="mini"
                   icon="el-icon-search" @click="handleQuery('queryForm')">搜索</el-button>
        <el-button v-hasPermi="['solid_liquid_query']"
                   size="mini"
                   type="default"
                   icon="el-icon-upload" @click="handleQueryPlus">进阶查询</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-hasPermi="['solid_liquid_query']"
                   type="primary"
                   plain
                   size="mini"
                   icon="el-icon-plus"
                   @click="handleUpdate">更新资料</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['solid_liquid_query']"
                   type="info"
                   size="mini"
                   icon="el-icon-s-promotion"
                   @click="handleSendEMS">抛送EMS</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading"
              height="67vh"
              stripe
              :data="tableData"
              tooltip-effect="dark"
              @selection-change="handleSelectionChange">
      <el-table-column label="能源代码"
                       align="center"
                       prop="engyId" />
      <el-table-column label="能源名称"
                       align="center"
                       prop="engyName" />
      <el-table-column label="能源量类型"
                       align="center"
                       prop="engyClass" />
      <el-table-column label="成本中心"
                       align="center"
                       prop="costCenter" />
      <el-table-column label="能源计量日期"
                       align="center"
                       prop="engyDate" />
      <el-table-column label="能源量"
                       align="center"
                       prop="engyValue" />
      <el-table-column label="计量单位"
                       align="center"
                       prop="engyUnit" />
      <el-table-column label="能源种类"
                       align="center"
                       prop="engyType" />
      <el-table-column label="来源系统"
                       align="center"
                       prop="engySource" />
    </el-table>



    <pagination background
                :total="total"
                :current-page="queryParams.pageNum"
                :page-sizes="[20, 50, 100, 200]"
                :page-size="queryParams.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange">
    </pagination>

    <!-- 能源计量月份对话框 -->
    <el-dialog :title="title"
               :visible.sync="openMonth"
               width="500px"
               :close-on-click-modal="false"
               class="customDialogStyle"
               append-to-body>
      <el-form ref="form"
               :model="form"
               status-icon
               :rules="rulesMonth"
               label-width="100px">
        <el-form-item label="能源计量月份"
                      prop="dateYM">
          <el-date-picker v-model="form.dateYM"
                          type="month"
                          placeholder="选择月">
          </el-date-picker>

        </el-form-item>
      </el-form>
      <div slot="footer"
           class="el-dialog__footer">
        <el-button @click="cancel('form')">取 消</el-button>
        <el-button type="primary"
                   @click="submitFormMonth('form')"
                   :loading="states">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 进阶查询对话框 -->
    <el-dialog :title="title"
               :visible.sync="openPlus"
               width="600px"
               :close-on-click-modal="false"
               class="customDialogStyle"
               append-to-body>
      <el-form ref="formPlus"
               :model="formPlus"
               label-width="105px"
               status-icon
               :rules="rulesPlus">
        <el-form-item label="能源计量日期"
                      prop="engyDateStart">
          <el-date-picker v-model="formPlus.engyDateStart"
                          type="date"
                          placeholder="选择日期">
          </el-date-picker>

          ~
          <el-date-picker v-model="formPlus.engyDateEnd"
                          type="date"
                          placeholder="选择日期">
          </el-date-picker>

        </el-form-item>
        <el-form-item label="能源代码">
          <el-select v-model="formPlus.engyIdStart"
                     :popper-append-to-body="false"
                     style="width: 200px;">
            <el-option v-for="item in optionsEngyIdStart"
                       :key="item"
                       :label="item"
                       :value="item">
            </el-option>
          </el-select>
          <span class="el-text">&nbsp;&nbsp;</span>
          ~
          <el-select v-model="formPlus.engyIdEnd"
                     :popper-append-to-body="false"
                     style="width: 200px;">
            <el-option v-for="item in optionsEngyIdEnd"
                       :key="item"
                       :label="item"
                       :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="成本中心"
                      prop="costCenterStart">
          <el-select v-model="formPlus.costCenterStart"
                     style="width: 200px;">
            <el-option v-for="item in optionsCostCenterStart"
                       :key="item.value"
                       :label="item.label"
                       :value="item.value">
            </el-option>
          </el-select>
          <span class="el-text">&nbsp;&nbsp;</span>
          ~
          <el-select v-model="formPlus.costCenterEnd"
                     style="width: 200px;">
            <el-option v-for="item in optionsCostCenterEnd"
                       :key="item.value"
                       :label="item.label"
                       :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer"
           class="el-dialog__footer">
        <el-button @click="cancel('formPlus')">取 消</el-button>
        <el-button type="primary"
                   @click="submitFormPlus('formPlus')"
                   :loading="states">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
// import {autoSendInfo, queryInfo, refreshInfo, queryDropDownInfo} from "@/api/energy/ee/solidLiquidEnergyVolumeDataOperation";
import { queryInfo, queryDropDownInfo } from "@/api/energy/ee/solidLiquidEnergyVolumeDataOperation";
import { mapGetters } from "vuex";
export default {
  name: "solidLiquidEnergyVolumeDataOperation",
  data () {
    var checkA = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('能源计量月份栏位为空，请重新输入！'));
      }
      setTimeout(() => {
        callback();
      }, 500);
    };
    var checkB = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('能源计量日期栏位为空，请重新输入！'));
      }
      setTimeout(() => {
        callback();
      }, 500);
    };
    var checkC = (rule, value, callback) => {
      if (value != null) {
        if (value.length > 10) {
          callback(new Error('成本中心长度为 10 ，请重新输入！'));
        } else {
          callback();
        }
      } else {
        callback();
      }
    };
    return {
      // 显示搜索条件
      showSearch: true,
      // 遮罩层
      loading: false,
      // 是否显示弹出层
      openMonth: false,
      // 是否显示弹出层
      openPlus: false,
      // 总条数
      total: 0,
      //能源代码下拉选单数据
      optionsEngyId: [],
      optionsEngyIdStart: [],
      optionsEngyIdEnd: [],
      // 表格数据
      tableData: [],
      // 表单校验
      rulesMonth: {
        dateYM: [
          { validator: checkA, trigger: 'blur' }
        ],
      },
      // 进阶表单校验
      rulesPlus: {
        engyDateStart: [
          { validator: checkB, trigger: 'blur' }
        ],
        costCenterStart: [
          { validator: checkC, trigger: 'blur' }
        ],
      },
      // 弹出层标题
      title: "",
      // 表单参数
      form: {},
      // 进阶表单参数
      formPlus: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        engyId: undefined,
        dateYM: undefined,
        engyIdStart: undefined,
        engyIdEnd: undefined,
        costCenterStart: undefined,
        engyDateStart: undefined,
        costCenterEnd: undefined,
        engyDateEnd: undefined,
      },
      optionsCostCenterStart: [],
      optionsCostCenterEnd: [],
      states: false,
    }
  },
  created () {
    this.getList();
    this.getDropDownMenu();
  },
  methods: {
    // 分页数据
    handleSizeChange (newSize) {
      this.queryParams.pageSize = newSize
      this.getList()
    },
    handleCurrentChange (newPage) {
      this.queryParams.pageNum = newPage
      this.getList()
    },
    //获取数据刷新页面
    getList () {
      queryInfo(this.queryParams).then(response => {
        this.tableData = response.data.list
        this.total = response.data.total
        this.loading = false
      })
    },
    getDropDownMenu () {
      queryDropDownInfo().then(response => {
        this.optionsEngyId = response.data
        this.optionsEngyIdStart = response.data
        this.optionsEngyIdEnd = response.data
      })
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 取消按钮
    cancel (formName) {
      this.$refs[formName].resetFields();
      this.openMonth = false;
      this.openPlus = false;
      this.reset();
    },
    // 表单重置
    reset () {
      this.form = {
        engyId: null,
        dateYM: null,
      };
      this.formPlus = {
        engyIdStart: null,
        engyIdEnd: null,
        costCenterStart: null,
        engyDateStart: null,
        costCenterEnd: null,
        engyDateEnd: null
      };
    },
    /** 查询按钮操作 */
    handleQuery (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true
          queryInfo(this.queryParams).then(response => {
            this.tableData = response.data.list
            this.total = response.data.total
            this.loading = false
          })
        } else {
          return false;
        }
      });
    },
    /** 更新资料按钮操作 */
    handleUpdate () {
      this.reset();
      this.openMonth = true;
      this.title = "更新资料计量月份";
    },
    /** 抛送EMS按钮操作 */
    handleSendEMS () {
      this.reset();
      this.openMonth = true;
      this.title = "抛送EMS计量月份";
    },
    /** 进阶查询按钮操作 */
    handleQueryPlus () {
      this.reset();
      this.openPlus = true;
      this.title = "进阶查询";
    },
    /** 提交按钮 */
    submitFormMonth (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.openMonth = false;
          //更新资料
          if (this.title == "更新资料计量月份") {
            // refreshInfo(this.queryParams).then(response => {
            //   this.tableData = response.data;
            // })
          }
          //抛送EMS
          if (this.title == "抛送EMS计量月份") {
            // autoSendInfo(this.queryParams).then(response => {
            //   this.tableData = response.data;
            // })
          }
        } else {
          return false;
        }
      });
    },
    /** 提交按钮 */
    submitFormPlus (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.states = true;
          this.form.createEmpNo = this.userInfo.userName;
          this.openPlus = false;
          this.loading = true
          queryInfo(this.queryParams).then(response => {
            this.tableData = response.data.list
            this.total = response.data.total
            this.loading = false
          })
        } else {
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>
.energy_main {
  height: 86vh;
  background-color: #fff;
}

::v-deep .el-table th,
.el-table tr {
  background-color: #fafafa;
  color: rgba(0, 0, 0, 0.85);
  font-size: 12px;
}
::v-deep .el-form-item__label {
  white-space: nowrap;
  text-align: left;
}

::v-deep .el-form-item__content {
  white-space: nowrap;
  padding-right: 10px;
}
::v-deep .el-text {
  color: red;
}
.mb8.el-row {
  margin-right: 100px;
}
/*下拉框最后一个显示不完全*/
::v-deep .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
::v-deep
  .el-date-editor.el-input.el-input--prefix.el-input--suffix.el-date-editor--date {
  width: 200px;
}
</style>
