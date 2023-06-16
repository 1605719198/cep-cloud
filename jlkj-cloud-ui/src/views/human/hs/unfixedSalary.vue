<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px"
             :rules="rules"
    >
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司" ref="selectComp">
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="核定类别" prop="groupType">
        <el-radio-group v-model="queryParams.groupType">
          <el-radio
            v-for="dict in salaryOptions.UnfixedType"
            :key="dict.dicNo"
            :label="dict.dicNo"
          >{{ dict.dicName }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="员工编号" prop="empNo">
        <el-input v-model="queryParams.empNo" placeholder="" clearable @keyup.enter.native="handleQuery"
                  style="width: 150px;"
        >
          <el-button slot="append" icon="el-icon-search" @click="inputClick()"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="异动日期" prop="effectDate">
        <el-date-picker
          v-model="effectDateRange"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="handleSave"
          v-hasPermi="['human:unfixedSalary:save']"
        >保存
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['human:unfixedSalary:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="unfixedSalaryList" @selection-change="handleSelectionChange"
              ref="multipleTable" height="67vh"
    >
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="工号" align="center" prop="empNo"/>
      <el-table-column label="姓名" align="center" prop="empName"/>
      <el-table-column label="异动日期" align="center" prop="effectDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effectDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="输入人" align="center" prop="creator"/>
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <select-user ref="select" @ok="getJobNumber"/>
  </div>
</template>

<script>
import { listUnfixedSalary, saveUnfixedSalary } from '@/api/human/hs/unfixedSalary'
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import selectUser from '@/views/components/human/selectUser/selectUser'
import { getSalaryOptions } from '@/api/human/hs/salaryBasis'

export default {
  name: 'UnfixedSalary',
  components: { selectUser },
  data() {
    return {
      //异动日期查询条件范围
      effectDateRange: null,
      //登录用户信息
      user: {},
      //公司数据
      companyList: [],
      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['UnfixedType'],
        compId: null
      },
      //薪资选单选项列表
      salaryOptions: [],
      //选中数据
      multipleSelection: [],
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
      // 总条数
      total: 0,
      // 未定薪人员挑选作业表格数据
      unfixedSalaryList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        groupType: null,
        compId: null,
        effectDate: null,
        startDate: null,
        endDate: null,
        empNo: null,
        list: []
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        compId: [
          { required: true, message: '请选择公司别', trigger: 'change' }
        ],
        groupType: [
          { required: true, message: '请选择核定类别', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.initData()
    this.getDisc()
    this.getCompanyList()
  },
  watch: {
    'queryParams.compId': {
      deep: true,
      immediate: false,
      handler: function(newV, preV) {
        if (this.queryParams.groupType) {
          this.getList()
        }
      }
    },
    'queryParams.groupType': {
      deep: true,
      immediate: false,
      handler: function(newV, preV) {
        this.getList()
      }
    }
  },
  methods: {
    //初始化数据
    initData() {
      this.user.empNo = this.$store.state.user.userInfo.userName
      this.user.empId = this.$store.state.user.userInfo.userId
      this.user.empName = this.$store.state.user.userInfo.nickName
      this.user.compId = this.$store.state.user.userInfo.compId
      this.queryParams.compId = this.user.compId
    },
    //查询薪资选单
    getDisc() {
      getSalaryOptions(this.salaryOptionType).then(response => {
        this.salaryOptions = response.data
      })
    },
    //获取公司列表
    getCompanyList() {
      selectCompany().then(response => {
        this.companyList = response.data
      })
    },
    /** 人员选单事件 */
    inputClick() {
      var queryParams = {
        compID: this.queryParams.compId,
        pageNum: 1,
        pageSize: 10
      }
      this.$refs.select.show(queryParams)
    },
    /** 获取工号 */
    getJobNumber(empNo) {
      this.queryParams.empNo = empNo
    },
    /** 查询未定薪人员挑选作业列表 */
    getList() {
      this.$refs['queryForm'].validate(valid => {
        if (valid) {
          if (this.effectDateRange) {
            this.queryParams.startDate = this.effectDateRange[0]
            this.queryParams.endDate = this.effectDateRange[1]
          } else {
            this.queryParams.startDate = null
            this.queryParams.endDate = null
          }
          this.loading = true
          listUnfixedSalary(this.queryParams).then(response => {
            this.unfixedSalaryList = response.rows
            this.$nextTick(() => {
              this.toggleSelection(response.rows)
            })
            this.total = response.total
            this.loading = false
          }, error => {
            this.loading = false
          })
        }
      })

    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        groupType: null,
        typeId: null,
        compId: null,
        effectDate: null,
        empNo: null,
        creator: null,
        creatorNo: null,
        creatorId: null,
        createDate: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.$nextTick(() => {
        this.resetForm('queryForm')
      })
      this.unfixedSalaryList = []
      this.total = 0
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.multipleSelection = selection
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    //添加多选状态
    toggleSelection(rows) {
      rows.forEach(row => {
        if (row.isCheck === '1') {
          this.$refs.multipleTable.toggleRowSelection(row, true)
        }
      })
    },
    /** 保存按钮操作 */
    handleSave() {
      this.queryParams.list = this.multipleSelection
      saveUnfixedSalary(this.queryParams).then(response => {
        this.$modal.msgSuccess('保存成功')
        this.getList()
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/unfixedSalary/export', {
        ...this.queryParams
      }, `"暂未定薪人员数据"_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
