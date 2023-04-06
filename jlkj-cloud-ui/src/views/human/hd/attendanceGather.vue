<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="公司" prop="compId">
            <el-select v-model="queryParams.compId" placeholder="请选择公司">
              <el-option
                v-for="dict in companyName"
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
          <el-form-item label="日期" prop="shiftDate">
            <el-date-picker
              v-model="queryParams.shiftDate"
              value-format="yyyy-MM-dd"
              type="date"
              placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

        <el-table v-loading="loading" :data="attendanceGatherList">
          <el-table-column label="工号" align="center" prop="empNo" />
          <el-table-column label="姓名" align="center" prop="empName" />
          <el-table-column label="岗位" align="center" prop="postName" />
          <el-table-column label="迟到次数" align="center" prop="lateNum" />
          <el-table-column label="早退次数" align="center" prop="leaNum" />
          <el-table-column label="大夜班次数" align="center" prop="bigNig" />
          <el-table-column label="小夜班次数" align="center" prop="smaNig" />
          <el-table-column label="大小夜班次数" align="center" prop="bigSmaNig" />
          <el-table-column label="请假天数" align="center">
            <el-table-column label="病假" align="center" prop="dueAttDuty" />
            <el-table-column label="工作假" align="center" prop="dueAttDuty" />
            <el-table-column label="事假" align="center" prop="dueAttDuty" />
            <el-table-column label="婚假" align="center" prop="dueAttDuty" />
            <el-table-column label="产假" align="center" prop="dueAttDuty" />
            <el-table-column label="丧假" align="center" prop="dueAttDuty" />
            <el-table-column label="探亲假" align="center" prop="dueAttDuty" />
            <el-table-column label="公假" align="center" prop="dueAttDuty" />
            <el-table-column label="年休假" align="center" prop="dueAttDuty" />
            <el-table-column label="护理假" align="center" prop="dueAttDuty" />
            <el-table-column label="合计" align="center" prop="holDuty" />
          </el-table-column>
          <el-table-column label="加班小时数" align="center">
            <el-table-column label="延时加班" align="center" prop="overTimeHou" />
            <el-table-column label="休息日加班" align="center" prop="overTimeHou" />
            <el-table-column label="法定假加班" align="center" prop="overTimeHou" />
            <el-table-column label="合计" align="center" prop="overTimeHou" />
          </el-table-column>
          <el-table-column label="月初未上岗天数" align="center" prop="befEntDuty" />
          <el-table-column label="月末未上岗天数" align="center" prop="aftEntDuty" />
          <el-table-column label="存班小时数" align="center" prop="savHou" />
          <el-table-column label="旷职天数" align="center" prop="truDuty" />
          <el-table-column label="应出勤天数" align="center" prop="dueAttDuty" />
          <el-table-column label="实际出勤天数" align="center" prop="actAttDuty" />
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
import { listAttendanceGather } from "@/api/human/hd/attendanceGather";
import {selectCompany} from "@/api/human/hp/deptMaintenance";

export default {
  name: "AttendanceGather",
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
      // 月出勤统计汇总表格数据
      attendanceGatherList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        empNo: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 公司别数据
      companyName: [],
    };
  },
  created() {
    selectCompany().then(res => {
      this.companyName = res.data
    })
  },
  methods: {
    /** 查询月出勤统计汇总列表 */
    getList() {
      this.loading = true;
      listAttendanceGather(this.queryParams).then(response => {
        this.attendanceGatherList = response.rows;
        this.total = response.total;
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
  }
};
</script>
