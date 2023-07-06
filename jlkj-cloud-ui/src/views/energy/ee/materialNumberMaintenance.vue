<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="能源代码">
        <el-select v-model="queryParams.engyIdStart"
                   clearable
                   :popper-append-to-body="false"
                   placeholder="请选择能源代码"
                   style="width: 215px;">
          <el-option v-for="item in optionsEngyIdStart"
                     :key="item.value"
                     :label="item.value"
                     :value="item.value">
          </el-option>
        </el-select>
        ~
        <el-select v-model="queryParams.engyIdEnd"
                   clearable
                   :popper-append-to-body="false"
                   placeholder="请选择能源代码"
                   style="width: 215px;">
          <el-option v-for="item in optionsEngyIdEnd"
                     :key="item.value"
                     :label="item.value"
                     :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-hasPermi="['code:material:query']" type="primary" icon="el-icon-search" size="mini"
                   @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="handleEmpty">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['code:material:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="openIsDisabled"
          @click="handleDelete"
          v-hasPermi="['code:material:delete']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading"
              height="67vh"
              stripe
              :data="tableData"
              tooltip-effect="dark"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection"
                       width="55"
                       align="center" />
      <el-table-column label="能源代码"
                       align="center"
                       prop="engyId" />
      <el-table-column label="能源名称"
                       align="center"
                       prop="engyName" />
      <el-table-column label="成本中心"
                       align="center"
                       prop="costCenter" />
      <el-table-column label="能源量类型" align="center" prop="engyClass" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.engy_engy_class" :value="scope.row.engyClass" />
        </template>
      </el-table-column>
      <el-table-column label="来源系统"
                       align="center"
                       prop="engySource" />
      <el-table-column label="料号1"
                       align="center"
                       prop="engyCmp1No" />
      <el-table-column label="料号2"
                       align="center"
                       prop="engyCmp2No" />
      <el-table-column label="料号3"
                       align="center"
                       prop="engyCmp3No" />
      <el-table-column label="料号4"
                       align="center"
                       prop="engyCmp4No" />
      <el-table-column label="料号5"
                       align="center"
                       prop="engyCmp5No" />
      <el-table-column label="料号6"
                       align="center"
                       prop="engyCmp6No" />
      <el-table-column label="料号7"
                       align="center"
                       prop="engyCmp7No" />
      <el-table-column label="料号8"
                       align="center"
                       prop="engyCmp8No" />
      <el-table-column label="料号9"
                       align="center"
                       prop="engyCmp9No" />
      <el-table-column label="料号10"
                       align="center"
                       prop="engyCmp10No" />
      <el-table-column label="操作"
                       align="center"
                       class-name="small-padding fixed-width"
                       width="180px">
        <template slot-scope="scope">
          <el-button v-hasPermi="['code:material:update']"
                     size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleUpdate(scope.row)">修改</el-button>
          <el-button v-hasPermi="['code:material:delete']"
                     size="mini"
                     type="text"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
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

    <!-- 添加或修改料号维护对话框 -->
    <el-dialog :title="title"
               v-if="open"
               :visible.sync="open"
               width="750px"
               :close-on-click-modal="false"
               class="customDialogStyle"
               append-to-body>
      <el-form ref="form"
               :model="form"
               status-icon
               :rules="rules"
               label-width="105px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="能源代码"
                          prop="engyId">
              <el-select v-model="form.engyId"
                         @change="selectEngyId"
                         :popper-append-to-body="false"
                         placeholder="请选择">
                <el-option v-for="(item,index) in optionsEngyId"
                           :key="index"
                           :label="item.value"
                           :value="{value:item.value,label:item.label}">
                </el-option>
              </el-select>

            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="能源名称"
                          prop="engyName">
              <el-input v-model="form.engyName"
                        :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="成本中心"
                          prop="costCenter">
              <el-autocomplete class="inline-input"
                               v-model="form.costCenter"
                               :fetch-suggestions="costCenterSearchAsync"
                               placeholder="成本中心"
                               @select="handleSelectCostCenter"
                               style="width: 90%!important;"></el-autocomplete>

            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="能源量类型"
                          prop="engyClass">
              <el-select v-model="form.engyClass"
                         :popper-append-to-body="false"
                         placeholder="请选择">
                <el-option v-for="dict in optionsEngyClass"
                           :key="dict.dictValue"
                           :label="dict.dictLabel"
                           :value="dict.dictValue">
                </el-option>
              </el-select>

            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="来源系统"
                          prop="engySource">
              <el-select v-model="form.engySource"
                         :popper-append-to-body="false"
                         placeholder="请选择">
                <el-option v-for="dict in optionsEngySource"
                           :key="dict.dictValue"
                           :label="dict.dictLabel"
                           :value="dict.dictValue">
                </el-option>
              </el-select>

            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="料号1"
                          prop="engyCmp1No">
              <el-input v-model="form.engyCmp1No" />

            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="料号2"
                          prop="engyCmp2No">
              <el-input v-model="form.engyCmp2No" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="料号3"
                          prop="engyCmp3No">
              <el-input v-model="form.engyCmp3No" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="料号4"
                          prop="engyCmp4No">
              <el-input v-model="form.engyCmp4No" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="料号5"
                          prop="engyCmp5No">
              <el-input v-model="form.engyCmp5No" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="料号6"
                          prop="engyCmp6No">
              <el-input v-model="form.engyCmp6No" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="料号7"
                          prop="engyCmp7No">
              <el-input v-model="form.engyCmp7No" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="料号8"
                          prop="engyCmp8No">
              <el-input v-model="form.engyCmp8No" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="料号9"
                          prop="engyCmp9No">
              <el-input v-model="form.engyCmp9No" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="料号10"
                          prop="engyCmp10No">
              <el-input v-model="form.engyCmp10No" />
            </el-form-item>
          </el-col>
        </el-row>
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
</template>


<script>
import { queryInfo, addInfo, updateInfo, delInfo, getInfo, querySolidEngyIds } from "@/api/energy/ee/materialNumberMaintenance";
import { listApplyLike } from "@/api/energy/ee/productionLineCostCenter"

export default {
  name: "materialNumberMaintenance",
  dicts: ['engy_engy_class'],
  data () {
    return {
      // 弹出层标题
      title: "",
      // 显示搜索条件
      showSearch: true,
      // 遮罩层
      loading: false,
      // 是否显示弹出层
      open: false,
      // 总条数
      total: 0,
      // 表单校验
      rules: {
        engyId: [
          { required: true, message: "能源代码不能为空", trigger: "blur" }
        ],
        engyClass: [
          { required: true, message: "能源量类型不能为空", trigger: "blur" }
        ],
        engySource: [
          { required: true, message: "来源系统不能为空", trigger: "blur" }
        ],
        engyCmp1No: [
          { required: true, message: "料号1不能为空", trigger: "blur" }
        ],
      },
      optionsEngySource: [],
      optionsEngyClass: [],
      optionsEngyName: [],
      optionsEngyId: [],
      optionsEngyIdStart: [],
      optionsEngyIdEnd: [],
      // 表单参数
      form: {},
      // 表格数据
      tableData: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        engyIdStart: undefined,
        engyIdEnd: undefined,
        costCenter: null
      },
      openIsDisabled: '',
      states: false,
    }
  },
  created () {
    this.getList()
    this.getDicts("engy_engy_class").then(response => {
      this.optionsEngyClass = response.data;
    })
    this.getDicts("engy_engy_source").then(response => {
      this.optionsEngySource = response.data;
    })
    querySolidEngyIds().then(response => {
      this.optionsEngyIdStart = response.data;
      this.optionsEngyIdEnd = response.data;
      this.loading = false
    })
  },
  methods: {
    selectEngyId (data) {
      // 将data对象解构
      const { value, label } = data;
      this.label = label;
      this.value = value;
      this.form.engyName = this.label;
    },
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
    // 表单重置
    reset () {
      this.form = {
        engyId: null,
        engyName: null,
        costCenter: null,
        engyClass: null,
        engySource: null,
        engyCmp1No: null,
        engyCmp2No: null,
        engyCmp3No: null,
        engyCmp4No: null,
        engyCmp5No: null,
        engyCmp6No: null,
        engyCmp7No: null,
        engyCmp8No: null,
        engyCmp9No: null,
        engyCmp10No: null
      };
    },
    /** 新增按钮操作 */
    handleAdd () {
      this.reset();
      this.open = true;
      this.title = "新增料号维护";
      //新增对话框能源代码查询
      querySolidEngyIds().then(response => {
        this.optionsEngyId = response.data;
        this.loading = false
      })
    },
    /** 查询按钮操作 */
    handleQuery () {
      this.loading = true
      queryInfo(this.queryParams).then(response => {
        this.tableData = response.data.list
        this.total = response.data.total
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
      getInfo(id).then(response => {
        this.form = response.data[0];
        this.open = true;
        this.title = "修改料号维护";
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
          this.$modal.msgSuccess(response.msg)
          this.getList();
        })
      })
    },
    /** 提交按钮 */
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.states = true;
          if (this.form.id != null) {
            updateInfo(this.form).then(response => {
              this.states = false;
              this.$modal.msgSuccess(response.msg)
              this.open = false;
              this.handleQuery();
              querySolidEngyIds().then(response => {
                this.optionsEngyIdStart = response.data;
                this.optionsEngyIdEnd = response.data;
                this.loading = false
              })
            }).catch(() => {
              this.states = false;
            });
          } else {
            this.form.engyId = this.form.engyId.value
            addInfo(this.form).then(response => {
              this.states = false;
              this.open = false;
              this.handleQuery();
              querySolidEngyIds().then(response => {
                this.optionsEngyIdStart = response.data;
                this.optionsEngyIdEnd = response.data;
                this.loading = false
              })
              if (response.code == '0') {
                this.$modal.msgSuccess(response.msg)
              }
            }).catch(() => {
              this.states = false;
            });
          }
        } else {
          return false;
        }
      });
    },
    costCenterSearchAsync (queryString, cb) {
      this.loadAacdemyCostCenter(queryString, (data) => {
        let results = queryString ? data.filter(this.costCenterStateFilter(queryString)) : data;
        clearTimeout(this.timeout);
        this.timeout = setTimeout(() => {
          cb(results);
        }, 30);
      })
    },
    costCenterStateFilter (queryString) {
      return (state) => {
        return (state.value.toLowerCase().indexOf(queryString.toLowerCase()) > -1);
      };
    },
    handleSelectCostCenter (item) {
      this.form.costCenter = item.name
    },
    loadAacdemyCostCenter (query, fun) {//后台根据用户输入的内容进行模糊查询
      let costCenter = [];
      this.queryParams.costCenter = this.form.costCenter
      listApplyLike(this.queryParams).then(response => {
        if (response.data.length > 0) {
          response.data.forEach((item) => {
            costCenter.push({
              name: item.costCenter,
              value: item.costCenterDesc
            })
          })
        } else {
          costCenter.push({
            value: "未找到相关结果",
          })
        }
        fun(costCenter);
      }).catch((error) => {
      })
    },
  }
}
</script>


