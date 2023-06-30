<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="companyId">
          <el-select v-model="queryParams.companyId" placeholder="请选择公司" :popper-append-to-body="false">
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
          v-hasPermi="['human:homeLeaveStatistics:export']"
        >导出</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="homeLeaveStatisticsList" >
      <el-table-column label="年度" align="center" prop="year" />
      <el-table-column label="工号" align="center" prop="empNo" />
      <el-table-column label="姓名" align="center" prop="empName" />
      <el-table-column label="岗位名称" align="center" prop="postName" />
      <el-table-column label="本年度可休探亲假天数" align="center" prop="dueHomeDays" />
      <el-table-column label="本年度年休假冲抵探亲假天数" align="center" prop="offHomeDays" />
      <el-table-column label="已休探亲假次数" align="center" prop="restHomeNums" />
      <el-table-column label="已休探亲假天数" align="center" prop="restHomeDays" />
      <el-table-column label="已休反探亲假次数" align="center" prop="restFhomeNums" />
      <el-table-column label="已休反探亲假天数" align="center" prop="restFhomeDays" />
      <el-table-column label="剩余探亲假天数" align="center" prop="preHomeDays" />
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
import { listHomeLeaveStatistics } from "@/api/human/st/homeLeaveStatistics";
import {selectCompany} from "@/api/human/hp/deptMaintenance";

export default {
  name: "HomeLeaveStatistics",
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
      // 探亲假统计表格数据
      homeLeaveStatisticsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: this.$store.state.user.userInfo.compId,
        year: '',
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
    /** 查询探亲假统计列表 */
    getList() {
      this.loading = true;
      listHomeLeaveStatistics(this.queryParams).then(response => {
        this.homeLeaveStatisticsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        companyId: null,
        year: null,
        empNo: null,
        empId: null,
        empName: null,
        postName: null,
        postId: null,
        dueHomeDays: null,
        offHomeDays: null,
        restHomeDays: null,
        restFhomeNums: null,
        restFhomeDays: null,
        preHomeDays: null,
        restHomeNums: null,
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
      window.open('http://10.32.157.51:9205/ureport/preview?_u=file:探亲假统计表.ureport.xml&companyId='+this.queryParams.companyId+'&year='+this.queryParams.year, '_blank');
    }
  }
};
</script>
