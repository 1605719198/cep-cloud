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
                   style="width: 215px;">
          <el-option v-for="item in optionsEngyIdEnd"
                     :key="item"
                     :label="item"
                     :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-hasPermi="['code_query']" type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
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
          v-hasPermi="['code_add']"
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
          v-hasPermi="['code_delete']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading"
              height="67vh"
              stripe
              :data="tableData"
              tooltip-effect="dark"
              @selection-change="handleSelectionChange"
              style="margin: 0 0px 0 00px;width: auto;">
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
      <el-table-column label="来源方式" align="center" prop="srcType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.engy_src_type" :value="scope.row.srcType" />
        </template>
      </el-table-column>
      <el-table-column label="能源种类" align="center" prop="engyType" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.engy_engy_type" :value="scope.row.engyType" />
        </template>
      </el-table-column>
      <el-table-column label="抛帐系统" align="center" prop="acctSys" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.engy_acct_sys" :value="scope.row.acctSys" />
        </template>
      </el-table-column>
      <el-table-column label="建立人员"
                       align="center"
                       prop="createEmpNo" />
      <el-table-column label="建立日期"
                       align="center"
                       width="160"
                       prop="createTime" />
      <el-table-column label="操作"
                       align="center"
                       class-name="small-padding fixed-width"
                       width="200">
        <template slot-scope="scope">
          <el-button v-hasPermi="['code_update']"
                     size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleUpdate(scope.row)">修改</el-button>
          <el-button v-hasPermi="['code_delete']"
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

    <!-- 添加或修改能源代码对话框 -->
    <el-dialog :title="title"
               v-if="open"
               :visible.sync="open"
               width="500px"
               append-to-body>
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="80px">
        <el-form-item label="能源代码"
                      prop="engyId">
          <el-input v-model="form.engyId"
                    :disabled="engyInput" />

        </el-form-item>
        <el-form-item label="计量单位"
                      prop="engyUnit">
          <el-input v-model="form.engyUnit"
                    :disabled="engyInput" />

        </el-form-item>
        <el-form-item label="热值单位"
                      prop="calUnit">
          <el-input v-model="form.calUnit" />

        </el-form-item>
        <el-form-item label="能源种类"
                      prop="engyType">
          <el-select v-model="form.engyType"
                     :popper-append-to-body="false"
                     placeholder="请选择"
                     :disabled="engyInput">
            <el-option v-for="dict in dict.type.engy_engy_type"
                       :key="dict.value"
                       :label="dict.label"
                       :value="dict.value">
            </el-option>
          </el-select>

        </el-form-item>
        <el-form-item label="来源方式"
                      prop="srcType">
          <el-radio-group v-model="form.srcType">
            <el-radio v-for="dict in dict.type.engy_src_type"
                      :key="dict.value"
                      :label="dict.value">{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="能源缩写"
                      prop="engyAc">
          <el-input v-model="form.engyAc" />
        </el-form-item>
        <el-form-item label="热值系数"
                      prop="calValue">
          <el-input v-model="form.calValue" />

        </el-form-item>
        <el-form-item label="能源名称"
                      prop="engyName">
          <el-input v-model="form.engyName"
                    :disabled="engyInput" />

        </el-form-item>
        <el-form-item label="抛帐系统"
                      prop="acctSys">
          <el-radio-group v-model="form.acctSys">
            <el-radio v-for="dict in dict.type.engy_acct_sys"
                      :key="dict.value"
                      :label="dict.value">{{dict.label}}</el-radio>
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
</template>

<script>

import { addInfo, delInfo, updateInfo, queryInfo, getInfo, queryEngyIds } from "@/api/energy/ee/energyCodeMaintenance";


export default {
  name: "energyCodeMaintenance",
  dicts: ['engy_engy_type','engy_acct_sys','engy_src_type'],
  data () {
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
          { required: true, message: "能源代码不能为空", trigger: "blur" }
        ],
        engyUnit: [
          { required: true, message: "计量单位不能为空", trigger: "blur" }
        ],
        calUnit: [
          { required: true, message: "热值单位不能为空", trigger: "blur" }
        ],
        engyType: [
          { required: true, message: "能源种类不能为空", trigger: "blur" }
        ],
        calValue: [
          { required: true, message: "热值系数不能为空", trigger: "blur" }
        ],
        engyName: [
          { required: true, message: "能源名称不能为空", trigger: "blur" }
        ],
        engyAc: [
          { required: true, message: "能源缩写不能为空", trigger: "blur" }
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
    queryEngyIds().then(response => {
      this.optionsEngyIdStart = response.data;
      this.optionsEngyIdEnd = response.data;
      this.loading = false
    })
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
      this.engyInput = true;
      getInfo(id).then(response => {
        this.form = response.data[0];
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
          this.form.createEmpNo = '姓名';
          if (this.form.id != null) {
            updateInfo(this.form).then(response => {
              this.states = false;
              // this.$message.success(response.msg)
              this.$modal.msgSuccess(response.msg)
              this.open = false;
              this.handleQuery();
              queryEngyIds().then(response => {
                this.optionsEngyIdStart = response.data;
                this.optionsEngyIdEnd = response.data;
                this.loading = false
              })
            }).catch(() => {
              this.states = false;
            });
          } else {
            addInfo(this.form).then(response => {
              this.states = false;
              if (response.code == '0') {
                this.$modal.msgSuccess(response.msg)
              }
              this.open = false;
              this.handleQuery();
              queryEngyIds().then(response => {
                this.optionsEngyIdStart = response.data;
                this.optionsEngyIdEnd = response.data;
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
