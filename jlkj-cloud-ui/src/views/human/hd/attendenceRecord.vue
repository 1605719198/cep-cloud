<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" :rules="rules" v-show="showSearch" label-width="80px">
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
          <el-form-item label="刷卡日期" prop="workOvertimeDate">
            <el-date-picker
              v-model="queryParams.workOvertimeDate"
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

        <el-table v-loading="loading" :data="attendenceRecordList">
          <el-table-column label="工号" align="center" prop="empNo" />
          <el-table-column label="姓名" align="center" prop="empName" />
          <el-table-column label="岗位" align="center" prop="postName" />
          <el-table-column label="刷卡日期" align="center" prop="slotCardDate" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.slotCardDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="刷卡时间" align="center" prop="slotCardTime" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.slotCardTime, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="上/下班" align="center" prop="onOffDuty" />
          <el-table-column label="星期" align="center" prop="week" />
          <el-table-column label="刷卡钟名称" align="center" prop="clockName" />
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
import selectUser from "@/views/components/human/selectUser/selectUser";
import { listAttendenceRecord } from "@/api/human/hd/attendenceRecord";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {validateNumber} from "@/utils/jlkj";

export default {
  name: "AttendenceRecord",
  components: {selectUser},
  data() {
    return {
      //公司数据
      companyList:[],
      // 遮罩层
      loading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 员工出勤有效记录资料表格数据
      attendenceRecordList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: 'J00',
        empNo: null,
        workOvertimeDate: '',
        startTime: '',
        endTime: ''
      },
      rules: {
        empNo: [
          { required: true, validator: validateNumber, trigger: "blur" },
          { max: 6, message: '工号长度必须为6位数字', trigger: 'blur' }
        ],
        workOvertimeDate: [
          { required: true, message: "刷卡日期不能为空", trigger: "blur" }
        ],
      }
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
    /** 查询员工出勤有效记录资料列表 */
    getList() {
      this.loading = true;
      listAttendenceRecord(this.queryParams).then(response => {
        this.attendenceRecordList = response.data.rows;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.getList();
        }
      });
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.empNo = undefined
      this.queryParams.workOvertimeDate = undefined
      this.handleQuery();
    },
    dateFormat(picker) {
      this.queryParams.startTime=picker[0]
      this.queryParams.endTime=picker[1]
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
