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
          v-hasPermi="['human:travelStatistics:export']"
        >导出</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="travelStatisticsList" >
      <el-table-column label="序号" align="center" prop="num" width="60"/>
      <el-table-column label="工号" align="center" prop="empNo" />
      <el-table-column label="姓名" align="center" prop="empName" />
      <el-table-column label="部门名称" align="center" prop="deptName" />
      <el-table-column label="出差类别" align="center" prop="travelTpye" >
        <template v-slot="scope">
          <dict-tag-human :options="attendenceOptions.TravelType" :value="scope.row.travelTpye"/>
        </template>
      </el-table-column>
      <el-table-column label="出差开始日期" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出差结束日期" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出差地点" align="center" prop="travelAbroad" >
        <template v-slot="scope">
          <dict-tag-human :options="attendenceOptions.TravelAbroad" :value="scope.row.travelAbroad"/>
        </template>
      </el-table-column>
      <el-table-column label="成本中心" align="center" prop="costCenter" />
      <el-table-column label="出差事由" align="center" prop="travelReason" >
        <template v-slot="scope">
          <dict-tag-human :options="attendenceOptions.TravelReason" :value="scope.row.travelReason"/>
        </template>
      </el-table-column>
      <el-table-column label="出差单号" align="center" prop="travelNo" />
      <el-table-column label="报支单号" align="center" prop="billNo" />
      <el-table-column label="申请单状态" align="center" prop="status" >
        <template v-slot="scope">
          <dict-tag-human :options="attendenceOptions.FlowStatus" :value="scope.row.status"/>
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
import { listTravelStatistics } from "@/api/human/st/travelStatistics";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getAttendenceOptions} from "@/api/human/hd/attendenceBasis";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";

export default {
  name: "TravelStatistics",
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
        optionsType:['FlowStatus','TravelType','TravelReason','TravelAbroad']
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
      // 出差统计表格数据
      travelStatisticsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
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
    /** 查询出差统计列表 */
    getList() {
      this.loading = true;
      listTravelStatistics(this.queryParams).then(response => {
        this.travelStatisticsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
    // 表单重置
    reset() {
      this.form = {
        id: null,
        travelNo: null,
        travelTpye: null,
        travelReason: null,
        compId: null,
        empId: null,
        empNo: null,
        empName: null,
        deptName: null,
        status: null,
        agentId: null,
        agentNo: null,
        agentName: null,
        costCenter: null,
        contract: null,
        startDate: null,
        startTime: null,
        endDate: null,
        endTime: null,
        realStartDate: null,
        realStartTime: null,
        realEndDate: null,
        realEndTime: null,
        reasonDesc: null,
        billNo: null,
        isEngineer: null,
        creator: null,
        creatorId: null,
        createDate: null,
        resvAttr1: null,
        resvAttr2: null,
        resvAttr3: null,
        resvAttr4: null
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
      window.open('http://10.32.157.51:9205/ureport/preview?_u=file:出差统计表.ureport.xml&compId='+this.queryParams.compId+'&date1='+this.queryParams.date1+'&date2='+this.queryParams.date2+'&status='+this.queryParams.status, '_blank');
    }
  }
};
</script>
