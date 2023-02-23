<template>
  <el-row style="padding:0 10px">
    <div class="energy_main"
         style="text-align: center;width: auto;">
      <el-form :model="queryParams"
               ref="queryForm"
               :inline="true"
               v-show="showSearch"
               style="text-align: left;padding-top: 20px;margin: 0 13px 0 20px">
        <el-form-item label="">
          <el-select v-model="queryParams.engyIdStart"
                     clearable
                     :popper-append-to-body="false"
                     placeholder="请选择能源代码"
                     style="width: 185.84px;">
            <el-option v-for="item in optionsEngyIdStart"
                       :key="item"
                       :label="item"
                       :value="item">
            </el-option>
          </el-select>
          ~
          <el-select v-model="queryParams.engyIdEnd"
                     clearable
                     :popper-append-to-body="false"
                     placeholder="请选择能源代码"
                     style="width: 185.84px;padding-left: 0.5%;">
            <el-option v-for="item in optionsEngyIdEnd"
                       :key="item"
                       :label="item"
                       :value="item">
            </el-option>
          </el-select>
          <el-button v-hasPermi="['code_query']"
                     type="primary"
                     size="mini"
                     icon="el-icon-search"
                     @click="handleQuery"
                     style="margin-left: 20px">搜 索</el-button>
          <el-button size="mini"
                     type="default"
                     icon="el-icon-refresh-left"
                     @click="handleEmpty">重 置</el-button>
        </el-form-item>

        <div class="el-form-item__content"
             style="float: right">
          <el-button v-hasPermi="['code_add']"
                     type="primary"
                     plain
                     size="mini"
                     icon="el-icon-plus"
                     @click="handleAdd">新增</el-button>
          <el-button v-hasPermi="['code_delete']"
                     type="danger"
                     size="mini"
                     icon="el-icon-delete"
                     :disabled="openIsDisabled"
                     @click="handleDelete">删除</el-button>
        </div>
      </el-form>

      <el-table v-loading="loading"
                stripe
                height="68.1vh"
                :data="tableData"
                tooltip-effect="dark"
                @selection-change="handleSelectionChange"
                style="margin: 0 13px 0 20px;width: auto;">
        <el-table-column type="selection"
                         width="55"
                         align="center" />
        <el-table-column label="能源缩写"
                         align="center"
                         prop="engyAc" />
        <el-table-column label="能源代码"
                         align="center"
                         prop="engyId" />
        <el-table-column label="能源名称"
                         align="center"
                         prop="engyName" />
        <el-table-column label="计量单位"
                         align="center"
                         prop="engyUnit" />
        <el-table-column label="热值系数"
                         align="center"
                         prop="calValue" />
        <el-table-column label="热值系数单位"
                         align="center"
                         prop="calUnit" />
        <el-table-column label="来源方式"
                         align="center"
                         prop="srcType" />
        <el-table-column label="能源种类"
                         align="center"
                         prop="engyType" />
        <el-table-column label="抛帐系统"
                         align="center"
                         prop="acctSys" />
        <el-table-column label="建立人员"
                         align="center"
                         prop="createEmpNo" />
        <el-table-column label="建立日期"
                         align="center"
                         prop="createTime" />
        <el-table-column label="操作"
                         align="center"
                         class-name="small-padding fixed-width"
                         width="200">
          <template slot-scope="scope">
            <el-button v-hasPermi="['code_update']"
                       size="mini"
                       plain
                       icon="el-icon-edit"
                       type="primary"
                       @click="handleUpdate(scope.row)">修改</el-button>
            <el-button v-hasPermi="['code_delete']"
                       size="mini"
                       plain
                       icon="el-icon-delete"
                       type="danger"
                       @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div style="margin: 8px 13px 15px 15px"
           class="avue-crud__pagination">
        <el-pagination background
                       :total="total"
                       :current-page="queryParams.pageNum"
                       :page-sizes="[20, 50, 100, 200]"
                       :page-size="queryParams.pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange">
        </el-pagination>
      </div>

      <!-- 添加或修改能源代码对话框 -->
      <el-dialog :title="title"
                 v-if="open"
                 :visible.sync="open"
                 width="500px"
                 :close-on-click-modal="false"
                 class="customDialogStyle"
                 :destroy-on-close="true"
                 append-to-body>
        <el-form ref="form"
                 :model="form"
                 status-icon
                 :rules="rules"
                 label-width="80px">
          <el-form-item label="能源代码"
                        prop="engyId">
            <el-input v-model="form.engyId"
                      :disabled="engyInput" />
            <span class="el-text"> *</span>
          </el-form-item>
          <el-form-item label="计量单位"
                        prop="engyUnit">
            <el-input v-model="form.engyUnit"
                      :disabled="engyInput" />
            <span class="el-text"> *</span>
          </el-form-item>
          <el-form-item label="热值单位"
                        prop="calUnit">
            <el-input v-model="form.calUnit" />
            <span class="el-text"> *</span>
          </el-form-item>
          <el-form-item label="能源种类"
                        prop="engyType">
            <el-select v-model="form.engyType"
                       :popper-append-to-body="false"
                       placeholder="请选择"
                       :disabled="engyInput">
              <el-option v-for="dict in optionsEngyType"
                         :key="dict.dictValue"
                         :label="dict.dictLabel"
                         :value="dict.dictValue">
              </el-option>
            </el-select>
            <span class="el-text"> *</span>
          </el-form-item>
          <el-form-item label="来源方式"
                        prop="srcType">
            <el-radio-group v-model="form.srcType">
              <el-radio v-for="dict in optionsSrcType"
                        :key="dict.dictValue"
                        :label="dict.dictValue">{{dict.dictLabel}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="能源缩写"
                        prop="engyAc">
            <el-input v-model="form.engyAc" />
          </el-form-item>
          <el-form-item label="热值系数"
                        prop="calValue">
            <el-input v-model="form.calValue" />
            <span class="el-text"> *</span>
          </el-form-item>
          <el-form-item label="能源名称"
                        prop="engyName">
            <el-input v-model="form.engyName"
                      :disabled="engyInput" />
            <span class="el-text"> *</span>
          </el-form-item>
          <el-form-item label="抛帐系统"
                        prop="acctSys">
            <el-radio-group v-model="form.acctSys">
              <el-radio v-for="dict in optionsAcctSys"
                        :key="dict.dictValue"
                        :label="dict.dictLabel">{{dict.dictLabel}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div slot="footer"
             class="el-dialog__footer">
          <el-button @click="cancel('form')">取 消</el-button>
          <el-button type="primary"
                     @click="submitForm('form')"
                     :loading="states">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </el-row>
</template>

<script>

import { addInfo, delInfo, updateInfo, queryInfo, getInfo, queryEngyIds } from "@/api/energy/ee/energyCodeMaintenance";
import { mapGetters } from "vuex";


export default {
  name: "energyCodeMaintenance",
  data () {
    var checkA = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('能源代码栏位为空，请重新输入！'));
      }
      setTimeout(() => {
        if (value.length > 8) {
          callback(new Error('能源代码参数长度为 8 ，请重新输入！'));
        } else {
          callback();
        }
      }, 500);
    };
    var checkB = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('计量单位栏位为空，请重新输入！'));
      }
      setTimeout(() => {
        if (value.length > 8) {
          callback(new Error('计量单位参数长度为 8 ，请重新输入！'));
        } else {
          callback();
        }
      }, 500);
    };
    var checkC = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('热值单位栏位为空，请重新输入！'));
      }
      setTimeout(() => {
        if (value.length > 6) {
          callback(new Error('热值单位参数长度为 6 ，请重新输入！'));
        } else {
          callback();
        }
      }, 500);
    };
    var checkD = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('能源种类栏位为空，请重新输入！'));
      }
      setTimeout(() => {
        if (value == 'G000' || value == 'Y000') {
          callback(new Error('能源种类应不包括（‘G000’，‘Y000’）'));
        } else {
          callback();
        }
      }, 500);
    };
    var checkE = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('热值系数栏位为空，请重新输入！'));
      } else {
        if (isNaN(value)) {
          callback(new Error('请输入数字值'));
        } else {
          if (value.length > 7) {
            callback(new Error('热值系数参数长度为 7 ，请重新输入！'));
          } else {
            callback();
          }
        }
      }
    };
    var checkF = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('能源名称栏位为空，请重新输入！'));
      }
      setTimeout(() => {
        if (value.length > 30) {
          callback(new Error('能源名称参数长度为 30 ，请重新输入！'));
        } else {
          callback();
        }
      }, 500);
    };
    var checkG = (rule, value, callback) => {
      if (value != null) {
        if (value.length > 10) {
          callback(new Error('能源缩写参数长度为 10 ，请重新输入！'));
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
      open: false,
      // 总条数
      total: 0,
      // 表单参数
      form: {},
      // 表格数据
      tableData: [],
      optionsEngyIdStart: [],
      optionsEngyIdEnd: [],
      states: false,
      // 表单校验
      rules: {
        engyId: [
          { validator: checkA, trigger: 'blur' }
        ],
        engyUnit: [
          { validator: checkB, trigger: 'blur' }
        ],
        calUnit: [
          { validator: checkC, trigger: 'blur' }
        ],
        engyType: [
          { validator: checkD, trigger: 'blur' }
        ],
        calValue: [
          { validator: checkE, trigger: 'blur' }
        ],
        engyName: [
          { validator: checkF, trigger: 'blur' }
        ],
        engyAc: [
          { validator: checkG, trigger: 'blur' }
        ]
      },
      optionsEngyType: [],
      optionsSrcType: [],
      optionsAcctSys: [],
      // 弹出层标题
      title: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        engyIdStart: undefined,
        engyIdEnd: undefined,
      },
      openIsDisabled: '',
      engyInput: ''
    }
  },
  created () {
    this.getList();
    this.getDicts("engy_engy_type").then(response => {
      this.optionsEngyType = response.data.data;
    })
    this.getDicts("engy_acct_sys").then(response => {
      this.optionsAcctSys = response.data.data;
    })
    this.getDicts("engy_src_type").then(response => {
      this.optionsSrcType = response.data.data;
    })
    queryEngyIds().then(response => {
      this.optionsEngyIdStart = response.data.data;
      this.optionsEngyIdEnd = response.data.data;
      this.loading = false
    })
  },
  computed: {
    ...mapGetters(["userInfo"])
  },
  methods: {
    // 分页数据
    handleSizeChange (val) {
      this.queryParams.pageSize = val
      this.getList()
    },
    handleCurrentChange (val) {
      this.queryParams.pageNum = val
      this.getList()
    },
    //获取数据刷新页面
    getList () {
      queryInfo(this.queryParams).then(response => {
        this.tableData = response.data.data.list
        this.total = response.data.data.total
        this.loading = false
      })
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
      if (this.ids.length != 0) {
        this.openIsDisabled = false
      } else {
        this.openIsDisabled = true
      }
    },
    // 取消按钮
    cancel (formName) {
      this.$refs[formName].resetFields();
      this.open = false;
      this.reset();
    },
    /** 新增按钮操作 */
    handleAdd () {
      this.engyInput = false;
      this.reset();
      this.open = true;
      this.title = "新增能源代码";
    },
    // 表单重置
    reset () {
      this.form = {
        engyId: null,
        engyName: null,
        engyAc: null,
        engyUnit: null,
        calValue: null,
        calUnit: null,
        engyType: null,
        srcType: null,
        acctSys: null,
        createEmpNo: null,
        createTime: null,
        updateEmpNo: null,
        updateTime: null
      };
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.loading = true
      queryInfo(this.queryParams).then(response => {
        this.tableData = response.data.data.list
        this.total = response.data.data.total
        this.loading = false
      })
    },
    // 清空
    handleEmpty () {
      this.queryParams = {
        pageNum: 1,
        pageSize: 20,
        engyIdStart: undefined,
        engyIdEnd: undefined,
      }
      this.getList();
    },
    /** 修改按钮操作 */
    handleUpdate (row) {
      this.reset();
      const id = row.id || this.ids
      this.engyInput = true;
      getInfo(id).then(response => {
        this.form = response.data.data[0];
        this.open = true;
        this.title = "修改能源代码";
      });
    },
    /** 删除按钮操作 */
    handleDelete (row) {
      const ids = row.id || this.ids;
      this.$confirm('此操作将永久删除数据记录，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {//点击确定，执行then方法
        //调用删除的方法
        delInfo(ids).then(response => {
          this.$message({
            type: 'success',
            message: response.data.msg
          });
          this.getList();
        })
      })
    },
    /** 提交按钮 */
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.states = true;
          this.form.createEmpNo = this.userInfo.userName;
          if (this.form.id != null) {
            updateInfo(this.form).then(response => {
              this.states = false;
              this.$message({
                type: 'success',
                message: response.data.msg
              });
              this.open = false;
              this.handleQuery();
              queryEngyIds().then(response => {
                this.optionsEngyIdStart = response.data.data;
                this.optionsEngyIdEnd = response.data.data;
                this.loading = false
              })
            }).catch(() => {
              this.states = false;
            });
          } else {
            addInfo(this.form).then(response => {
              this.states = false;
              console.log(response.data);
              if (response.data.code == '0') {
                this.$message({
                  type: 'success',
                  message: response.data.msg
                });
              }
              this.open = false;
              this.handleQuery();
              queryEngyIds().then(response => {
                this.optionsEngyIdStart = response.data.data;
                this.optionsEngyIdEnd = response.data.data;
                this.loading = false
              })
            }).catch(() => {
              this.states = false;
            });
          }
        } else {
          return false;
        }
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.customDialogStyle ::v-deep .el-dialog__body {
  padding: 15px 40px 0 40px;
}

.energy_main {
  height: 86vh;
  background-color: #fff;
}
::v-deep .el-tabs--border-card > .el-tabs__header {
  background-color: #ffffff;
}

::v-deep .el-tabs--border-card > .el-tabs__header .el-tabs__item {
  width: 45%;
  text-align: center;
}
::v-deep .el-table th,
.el-table tr {
  background-color: #fafafa;
  color: rgba(0, 0, 0, 0.85);
  font-size: 12px;
}
::v-deep .el-form-item__label {
  white-space: nowrap;
}
::v-deep .el-tabs--border-card {
  height: 825px;
}
::v-deep .el-form-item__content {
  white-space: nowrap;
}
::v-deep .el-text {
  color: red;
}
::v-deep .el-select {
  width: 100%;
}
.mb8.el-row {
  margin-right: 100px;
}
/*下拉框最后一个显示不完全*/
::v-deep.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
::v-deep.el-form-item--feedback {
  width: 92%;
}
</style>

