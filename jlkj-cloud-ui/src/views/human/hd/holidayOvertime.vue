<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :rules="rules" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="公司" prop="compId">
            <el-select v-model="queryParams.compId" placeholder="请选择公司">
              <el-option
                v-for="dict in companyList"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="工号" prop="empNo">
            <el-input v-model="queryParams.empNo" placeholder="请输入工号" :disabled="true">
              <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item label="日期" prop="legalHolDate">
            <el-date-picker
              v-model="queryParams.legalHolDate"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="~"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              @change="dateFormat">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

        <el-table v-loading="loading" :data="holidayOvertimeList">
          <el-table-column label="岗位" align="center" prop="postFullName" />
          <el-table-column label="工号" align="center" prop="empNo" />
          <el-table-column label="姓名" align="center" prop="empName" />
          <el-table-column label="法定假日期" align="center" prop="legalHolDate" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.legalHolDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="轮班方式" align="center" prop="turnTypeName" />
          <el-table-column label="班别" align="center" prop="className" />
          <el-table-column label="生效日期" align="center" prop="effectDate" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.effectDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="班次开始时间" align="center" prop="extraWorkBegin" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.extraWorkBegin, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="班次结束时间" align="center" prop="extraWorkEnd" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.extraWorkEnd, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="加班时数" align="center" prop="extraWorkHours" />
          <el-table-column label="刷卡时间" align="center" prop="slotCardTime" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.slotCardTime, '{y}-{m}-{d}') }}</span>
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
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listHolidayOvertime } from "@/api/human/hd/holidayOvertime";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {validateNumber} from "@/utils/jlkj";
import selectUser from "@/views/components/human/selectUser/selectUser";

export default {
  name: "HolidayOvertime",
  components: {selectUser},
  data() {
    return {
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
      // 倒班人员法定假日加班资料表格数据
      holidayOvertimeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: 'J00',
        empNo: null,
        legalHolDate: '',
        startTime: '',
        endTime: ''
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        empNo: [
          { required: true, validator: validateNumber, trigger: "blur" },
          { max: 6, message: '工号长度必须为6位数字', trigger: 'blur' }
        ],
        legalHolDate: [
          { required: true, message: "日期不能为空", trigger: "blur" }
        ]
      },
      //公司数据
      companyList:[],
    };
  },
  created() {
    this.getCompanyList();
  },
  methods: {
    //获取公司列表
    getCompanyList(){
      selectCompany().then(response=>{
        this.companyList = response.data
      })
    },
    /** 查询倒班人员法定假日加班资料列表 */
    getList() {
      this.loading = true;
      listHolidayOvertime(this.queryParams).then(response => {
        this.holidayOvertimeList = response.data.rows;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(val) {
      this.queryParams.empNo = val
    },
    dateFormat(picker) {
      this.queryParams.startTime=picker[0]
      this.queryParams.endTime=picker[1]
    },
  }
};
</script>
