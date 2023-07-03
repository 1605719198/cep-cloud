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
      <el-form-item label="日期区间" prop="startDate">
        <el-date-picker
          v-model="queryParams.startDate"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="~"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="dateFormat"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="请假类别" prop="leaTypeId">
        <el-select v-model="queryParams.leaTypeId">
          <el-option
            v-for="dict in attendenceOptions.HD001"
            :key="dict.dicNo"
            :label="dict.dicName"
            :value="dict.dicNo"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审批状态" prop="status">
        <el-select v-model="queryParams.status">
          <el-option
            v-for="dict in attendenceOptions.FlowStatus"
            :key="dict.dicNo"
            :label="dict.dicName"
            :value="dict.dicNo"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['human:personHolidayStatistics:export']"
        >导出</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="personHolidayStatisticsList" >
      <el-table-column label="序号" align="center" prop="num" width="60"/>
      <el-table-column label="工号" align="center" prop="empNo" />
      <el-table-column label="姓名" align="center" prop="empName" />
      <el-table-column label="岗位" align="center" prop="postname" />
      <el-table-column label="请假类别" align="center" prop="leaTypeId" >
        <template v-slot="scope">
          <dict-tag-human :options="attendenceOptions.HD001" :value="scope.row.leaTypeId"/>
        </template>
      </el-table-column>
      <el-table-column label="原因" align="center" prop="description" />
      <el-table-column label="请假批示状态" align="center" prop="status" >
        <template v-slot="scope">
          <dict-tag-human :options="attendenceOptions.FlowStatus" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="请假天数" align="center" prop="leaveShifts" />
      <el-table-column label="请假开始时间" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请假结束时间" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="输入人" align="center" prop="creator" />
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
  </div>
</template>

<script>
import { listPersonHolidayStatistics, } from "@/api/human/st/personHolidayStatistics";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getAttendenceOptions} from "@/api/human/hd/attendenceBasis";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";

export default {
  name: "PersonHolidayStatistics",
  components: { DictTagHuman },
  data() {
    return {
      //公司列表
      companyList:[],
      //出勤选单选项列表
      attendenceOptions: {},
      //绩效选单类型查询
      attendenceOptionType:{
        id:'',
        optionsType:['HD001','FlowStatus']
      },
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
      // 员工请假统计表格数据
      personHolidayStatisticsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
        leaTypeId: '',
        status: '',
        startDate: null,
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
    this.getDisc();
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
    /** 查询员工请假统计列表 */
    getList() {
      this.loading = true;
      listPersonHolidayStatistics(this.queryParams).then(response => {
        this.personHolidayStatisticsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        empNo: null,
        empId: null,
        empName: null,
        postname: null,
        postid: null,
        orgParentId: null,
        orgId: null,
        leaTypeId: null,
        isContainHoliday: null,
        description: null,
        remainingDays: null,
        monthDays: null,
        monthHours: null,
        yearDays: null,
        yearHours: null,
        status: null,
        statusCancel: null,
        leaveHours: null,
        leaveShifts: null,
        startDate: null,
        endDate: null,
        creator: null,
        creatorId: null,
        createDate: null
      };
      this.resetForm("form");
    },
    /** 日期查询范围变更*/
    dateFormat(val){
      if(val==null){
        this.queryParams.date1=null
        this.queryParams.date2=null
        this.queryParams.startDate=null
        return
      }
      this.queryParams.date1 =val[0];
      this.queryParams.date2 =val[1];
    },
    //查询绩效选单
    getDisc(){
      getAttendenceOptions(this.attendenceOptionType).then(response=> {
        this.attendenceOptions=response.data;
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 导出按钮操作 */
    handleExport() {
      window.open('http://10.32.157.51:9205/ureport/preview?_u=file:员工请假统计表.ureport.xml&compId='+this.queryParams.compId+'&date1='+this.queryParams.date1+'&date2='+this.queryParams.date2+'&status='+this.queryParams.status+'&leaTypeId='+this.queryParams.leaTypeId, '_blank');
    }
  }
};
</script>
