<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px"
             :rules="rules"
    >
      <el-form-item label="公司" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司">
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="员工工号" prop="empNo">
        <el-input v-model="queryParams.empNo" placeholder="请输入员工工号" clearable @keyup.enter.native="handleQuery">
          <el-button slot="append" icon="el-icon-search" @click="inputClick()"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="输入日期" prop="salaryDate">
        <el-date-picker
          :clearable="false"
          v-model="queryParams.salaryDate"
          type="month"
          placeholder="请选择输入日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="salaryResultList" height="67vh">
      <el-table-column label="工号" align="center" prop="empNo"/>
      <el-table-column label="姓名" align="center" prop="empName"/>
      <el-table-column label="薪资年月" align="center" prop="yearmonth">
        <template slot-scope="scope">
          {{ scope.row.year }}-{{ scope.row.month }}
        </template>
      </el-table-column>
      <el-table-column label="组织机构" align="center" prop="orgName"/>
      <el-table-column label="应发金额" align="center" prop="result01"/>
      <el-table-column label="应扣金额" align="center" prop="result02"/>
      <el-table-column label="实发金额" align="center" prop="result"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleInfo(scope.row)"
            v-hasPermi="['human:salaryResult:query']"
          >查看详情
          </el-button>
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

    <!-- 查看薪资计算结果详情对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body class="customDialogStyle">
      <el-form ref="form" :model="form" label-width="110px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="工号" prop="empNo">
              {{ form.empNo }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="姓名" prop="empName">
              {{ form.empName }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="所在组织机构" prop="orgName">
              {{ form.orgName }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="应发金额" prop="result01">
              {{ form.result01 }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="应扣金额" prop="result02">
              {{ form.result02 }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="薪资卡实发金额" prop="result">
              {{ form.result }}
            </el-form-item>
          </el-col>
        </el-row>

        <div class="table1">
          <div class="table1_module ">
            <div class="table1_header">
              应发明细
            </div>
            <div class="table1_column ">
              <div v-for="(item,index) in this.form.list01" class=" table1_row">
                <div class="table1_child table1_child_left">
                  {{ item.payTableName }}
                </div>
                <div class="table1_child">
                  {{ item.result }}
                </div>
              </div>
            </div>
          </div>
          <div class="table1_module ">
            <div class="table1_header">
              应扣明细
            </div>
            <div class="table1_column ">
              <div v-for="(item,index) in this.form.list02" class=" table1_row">
                <div class="table1_child table1_child_left">
                  {{ item.payTableName }}
                </div>
                <div class="table1_child">
                  {{ item.result }}
                </div>
              </div>
            </div>
          </div>
          <div class="table1_module ">
            <div class="table1_header">
              企业支付明细
            </div>
            <div class="table1_column ">
              <div v-for="(item,index) in this.form.list03" class=" table1_row">
                <div class="table1_child table1_child_left">
                  {{ item.payTableName }}
                </div>
                <div class="table1_child">
                  {{ item.result }}
                </div>
              </div>
            </div>
          </div>
          <div class="table1_module ">
            <div class="table1_header">
              计算参数明细
            </div>
            <div class="table1_column ">
              <div v-for="(item,index) in this.form.list04" class=" table1_row">
                <div class="table1_child table1_child_left">
                  {{ item.payTableName }}
                </div>
                <div class="table1_child">
                  {{ item.result }}
                </div>
              </div>
            </div>
          </div>
        </div>
        <div style="padding:10px 0">
          本月追补扣项目说明
        </div>
        <div class="table2">
          <div class="table2_header">
            <div class="table2_header_item">
              追补扣项目
            </div>
            <div class="table2_header_item">
              金额
            </div>
            <div class="table2_header_item">
              说明
            </div>
          </div>
          <div class="table2_child" v-for="(item,index) in this.form.list01">
            <div class="table2_child_item">
              {{ item.payTableName }}
            </div>
            <div class="table2_child_item">
              {{ item.result }}
            </div>
            <div class="table2_child_item">
              {{ item.comm }}
            </div>
          </div>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <select-user ref="select" @ok="getJobNumber"/>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss'
import { listSalaryResult, getSalaryResult } from '@/api/human/hs/salaryResult'
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import selectUser from '@/views/components/human/selectUser/selectUser'

export default {
  name: 'SalaryResult',
  components: { selectUser },
  data() {
    return {
      //登录用户信息
      user: {},
      //公司数据
      companyList: [],
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
      // 薪资计算结果表格数据
      salaryResultList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        empNo: null,
        salaryDate: new Date(),
        month: null,
        year: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        empNo: [
          { required: true, message: '请输入员工工号', trigger: 'change' }
        ],
        salaryDate: [
          { required: true, message: '生效日期不能为空', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.initData()
    this.getCompanyList()
  },
  methods: {
    //获取公司列表
    getCompanyList() {
      selectCompany().then(response => {
        this.companyList = response.data
      })
    },
    //初始化数据
    initData() {
      this.user.empNo = this.$store.state.user.userInfo.userName
      this.user.empId = this.$store.state.user.userInfo.userId
      this.user.empName = this.$store.state.user.userInfo.nickName
      this.user.compId = this.$store.state.user.userInfo.compId
      this.queryParams.compId = this.$store.state.user.userInfo.compId
      this.queryParams.empNo = this.$store.state.user.userInfo.userName
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
    /** 查询薪资计算结果列表 */
    getList() {
      this.$refs['queryForm'].validate(valid => {
        if (valid) {
          this.queryParams.year = this.queryParams.salaryDate.getFullYear()
          this.queryParams.month = String(this.queryParams.salaryDate.getMonth() + 1)
          this.loading = true
          listSalaryResult(this.queryParams).then(response => {
            this.salaryResultList = response.rows
            this.total = response.total
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
        compId: null,
        empId: null,
        empNo: null,
        empName: null,
        orgName: null,
        year: null,
        month: null,
        result01: null,
        result02: null,
        result: null,
        list01: [],
        list02: [],
        list03: [],
        list04: [],
        list00: []
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
      this.handleQuery()
      this.salaryResultList = [];
      this.total = 0;
    },
    /** 查看详情按钮操作 */
    handleInfo(row) {
      this.reset()
      const id = row.id || this.ids
      getSalaryResult(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改薪资计算结果'
      })
    }
  }
}
</script>
<style scoped>
.table1 {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 100%;
}

.table1_module {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 25%;
  border-style: solid;
  border-width: 1px;
  border-color: #000000;
}

.table1_header {
  display: flex;
  flex-direction: row;
  justify-content: center;
  border-style: solid;
  border-color: #000000;
  border-width: 0 0 1px 0;
  width: 100%;
  padding: 5px 0;
  background-color: #f3f3f3;
}

.table1_column {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.table1_row {
  display: flex;
  flex-direction: row;
  align-items: center;
  border-style: solid;
  border-color: #000000;
  border-width: 1px 0 0 0;
  width: 100%;
}

.table1_child {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  height: 30px;
  width: 50%;
  padding: 5px 0;
}

.table1_child_left {
  border-style: solid;
  border-color: #000000;
  border-width: 0 1px 0 0;
}

.table2 {
  display: flex;
  flex-direction: column;
  justify-content: start;
  width: 100%;
}

.table2_header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 100%;
  background-color: #f3f3f3;
}

.table2_header_item {
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 100%;
  border-style: solid;
  border-color: #000000;
  border-width: 1px;
  padding: 5px 0;
  height: 30px;
}

.table2_child {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 100%;
}

.table2_child_item {
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 100%;
  border-style: solid;
  border-color: #000000;
  border-width: 0 1px 1px 1px;
  padding: 5px 0;
  height: 30px;
}
</style>
