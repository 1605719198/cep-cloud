<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司" >
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="年" prop="year">
        <el-date-picker
                        :clearable="false"
                        v-model="queryParams.year"
                        type="year"
                        value-format="yyyy"
                        placeholder="请选择年"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          :disabled="multiple"
          @click="handleSave"
          v-hasPermi="['human:salaryExplain:save']"
        >保存
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['human:salaryExplain:remove']"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="salaryExplainList" @selection-change="handleSelectionChange"
              :row-class-name="addIndex" stripe
    >
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="对应薪资月份" align="center" prop="yearMonth"/>
      <el-table-column label="拟定工资到账日" align="center" prop="salaryDate" width="180">
        <template slot-scope="scope">
          <el-date-picker clearable
                          style="width: 85%"
                          v-model="scope.row.salaryDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择"
          >
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column label="工资备注" align="center" prop="salaryRemarks">
        <template v-slot="scope">
          <el-input v-model="scope.row.salaryRemarks" placeholder="请输入" style="width: 85%"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="拟定奖金到账日" align="center" prop="bounsDate" width="180">
        <template slot-scope="scope">
          <el-date-picker clearable
                          style="width: 85%"
                          v-model="scope.row.bounsDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择"
          >
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column label="奖金备注" align="center" prop="bounsRemarks">
        <template v-slot="scope">
          <el-input v-model="scope.row.bounsRemarks" placeholder="请输入" style="width: 85%"></el-input>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="false"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

  </div>
</template>

<script>
import {
  listSalaryExplain,
  delSalaryExplain,
  saveSalaryExplain,
} from '@/api/human/hs/salaryExplain'
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getDateTime } from '../../../api/human/hd/ahumanUtils'

export default {
  name: 'SalaryExplain',
  dicts: ['sys_normal_disable'],
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
      // 各公司薪资发放说明表格数据
      salaryExplainList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        compId: null,
        year: null
      },
      // 表单参数
      form: {},
      //选中项数据
      multipleSelection: [],
      // 表单校验
      rules: {
        salaryDate: [
          { required: true, message: '拟定工资到账日不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {

  },
  created() {
    this.getCompanyList()
    this.initData()
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
      this.queryParams.compId = this.user.compId
      this.queryParams.year = new Date().getFullYear().toString();
    },
    /** 查询各公司薪资发放说明列表 */
    getList() {
      this.loading = true
      listSalaryExplain(this.queryParams).then(response => {
        this.salaryExplainList = response.rows
        this.total = response.total
        if (this.total !== 12) {
        } else {
          this.loading = false
        }
      })
    },
    //添加行index
    addIndex({ row, rowIndex }) {
      row.index = rowIndex
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
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.uuid)
      this.multipleSelection = selection
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 保存按钮操作 */
    handleSave() {
      saveSalaryExplain(this.multipleSelection).then(response=>{
        this.$modal.msgSuccess('保存成功')
        this.getList();
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uuids = this.ids
      //是否可以保存
      let couldDelete = false;
      uuids.forEach((value)=>{
        if(value!==null){
          couldDelete = true;
        }
      })
      if(couldDelete===true){
        this.$modal.confirm('是否确认删除所选数据项？').then(function() {
          return delSalaryExplain(uuids)
        }).then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        }).catch(() => {
        })
      }else{
        this.$modal.msgError('所选中项中皆未保存')
      }

    }
  }
}
</script>
