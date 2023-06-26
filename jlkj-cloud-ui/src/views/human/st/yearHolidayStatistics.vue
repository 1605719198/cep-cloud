<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司" :popper-append-to-body="false">
          <el-option
            v-for="dict in companyList"
            :key="dict.deptCode"
            :label="dict.companyName"
            :value="dict.deptCode"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="年度" prop="year">
        <el-date-picker
          v-model="queryParams.year"
          type="year"
          value-format="yyyy"
          placeholder="选择年">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['human:yearHolidayStatistics:export']"
        >导出</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="yearHolidayStatisticsList" >
      <el-table-column label="年度" align="center" prop="year" />
      <el-table-column label="工号" align="center" prop="empNo" />
      <el-table-column label="姓名" align="center" prop="empName" />
      <el-table-column label="岗位名称" align="center" prop="postName" />
      <el-table-column label="本年度可休年休假天数" align="center" prop="totalDays" />
      <el-table-column label="上年度剩余可休年休假天数" align="center" prop="curYearDays" />
      <el-table-column label="累计可休年休假天数" align="center" prop="preYearDays" />
      <el-table-column label="本年度已休年休假天数" align="center" prop="restDays" />
      <el-table-column label="本年度已放弃年休假天数" align="center" prop="giveupDays" />
      <el-table-column label="可结转到下一年度年休假天数" align="center" prop="gotoDays" />
      <el-table-column label="需付未休年休假加班费天数" align="center" prop="overtimePay" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listYearHolidayStatistics } from "@/api/human/st/yearHolidayStatistics";
import {selectCompany} from "@/api/human/hp/deptMaintenance";

export default {
  name: "YearHolidayStatistics",
  dicts: ['sys_classtype'],
  data() {
    return {
      //公司列表
      companyList:[],
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
      // 年休假统计表格数据
      yearHolidayStatisticsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
        year: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getCompanyList()
  },
  methods: {
    /** 查询公司列表 */
    getCompanyList(){
      selectCompany().then(response =>{
          this.companyList = response.data
        }
      )
    },
    /** 查询年休假统计列表 */
    getList() {
      this.loading = true;
      listYearHolidayStatistics(this.queryParams).then(response => {
        this.yearHolidayStatisticsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        year: null,
        empNo: null,
        empId: null,
        empName: null,
        postName: null,
        postId: null,
        totalDays: null,
        curYearDays: null,
        preYearDays: null,
        restDays: null,
        giveupDays: null,
        gotoDays: null,
        overtimePay: null,
        creator: null,
        creatorId: null,
        createDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/yearHolidayStatistics/export', {
        ...this.queryParams
      }, `yearHolidayStatistics_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
