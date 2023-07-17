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
      <el-form-item label="班次开始时间" prop="extraWorkBegin" label-width="100px">
        <el-date-picker
          v-model="queryParams.extraWorkBegin"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="~"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="dateFormat"
        >
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
          v-hasPermi="['human:holidayOvertimeStatistics:export']"
        >导出</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="holidayOvertimeStatisticsList" >
      <el-table-column label="序号" align="center" prop="num" width="60"/>
      <el-table-column label="工号" align="center" prop="empNo" />
      <el-table-column label="姓名" align="center" prop="empName" />
      <el-table-column label="岗位全称" align="center" prop="postFullName" />
      <el-table-column label="法定假日期" align="center" prop="legalHolDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.legalHolDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="轮班方式" align="center" prop="turnTypeName" />
      <el-table-column label="班别" align="center" prop="className" />
      <el-table-column label="生效日期" align="center" prop="effectDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effectDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="班次开始时间" align="center" prop="extraWorkBegin" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.extraWorkBegin, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="班次结束时间" align="center" prop="extraWorkEnd" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.extraWorkEnd, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="加班时数" align="center" prop="extraWorkHours" />
      <el-table-column label="刷卡时间" align="center" prop="slotCardTime" width="180">
        <template slot-scope="scope">
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
  </div>
</template>

<script>
import { listHolidayOvertimeStatistics } from "@/api/human/st/holidayOvertimeStatistics";
import {selectCompany} from "@/api/human/hp/deptMaintenance";

export default {
  name: "HolidayOvertimeStatistics",
  data() {
    return {
      //轮班方式列表
      shiftModeList:[],
      //班别列表
      shiftClassList:[],
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
      // 倒班人员法定假日加班统计表格数据
      holidayOvertimeStatisticsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
        extraWorkBegin: null,
        date1:'',
        date2:'',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getCompanyList();
  },
  methods: {
    /** 查询倒班人员法定假日加班统计列表 */
    getList() {
      this.loading = true;
      listHolidayOvertimeStatistics(this.queryParams).then(response => {
        this.holidayOvertimeStatisticsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询公司列表 */
    getCompanyList(){
      selectCompany().then(response =>{
          this.companyList = response.data
        }
      )
    },
    /** 日期查询范围变更*/
    dateFormat(val){
      if(val==null){
        this.queryParams.date1=null
        this.queryParams.date2=null
        this.queryParams.extraWorkBegin=null
        return
      }
      this.queryParams.date1 =val[0];
      this.queryParams.date2 =val[1];
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        empId: null,
        empNo: null,
        empName: null,
        postFullName: null,
        postId: null,
        legalHolDate: null,
        turnTypeId: null,
        turnTypeName: null,
        classId: null,
        className: null,
        effectDate: null,
        extraWorkBegin: null,
        extraWorkEnd: null,
        extraWorkHours: null,
        slotCardTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      if(this.queryParams.extraWorkBegin===null||this.queryParams.extraWorkBegin===''){
        this.$modal.msgError("请输入日期")
      }else{
        this.queryParams.pageNum = 1;
        this.getList();
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      window.open('http://10.32.157.51:9205/ureport/preview?_u=file:倒班人员法定假日加班统计表.ureport.xml&compId='+this.queryParams.compId+'&date1='+this.queryParams.date1+'&date2='+this.queryParams.date2, '_blank');
    }
  }
};
</script>
