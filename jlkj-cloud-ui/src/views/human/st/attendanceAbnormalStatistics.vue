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
      <el-form-item label="日期区间" prop="norOndutyBegin">
        <el-date-picker
          v-model="queryParams.norOndutyBegin"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="~"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="dateFormat"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="处理情况" prop="disposeId">
        <el-select v-model="queryParams.disposeId">
          <el-option
            v-for="dict in attendenceOptions.DisposeStatus"
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
          v-hasPermi="['human:attendanceAbnormalStatistics:export']"
        >导出</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="attendanceAbnormalStatisticsList" >
      <el-table-column label="序号" align="center" prop="num" width="60"/>
      <el-table-column label="姓名" align="center" prop="empName" />
      <el-table-column label="工号" align="center" prop="empNo" />
      <el-table-column label="岗位名称" align="center" prop="postName" />
      <el-table-column label="生产异常原因" align="center" prop="excReaId" >
        <template v-slot="scope">
          <dict-tag-human :options="attendenceOptions.AbnormalReason" :value="scope.row.excReaId"/>
        </template>
      </el-table-column>
      <el-table-column label="出勤证明原因细分" align="center" prop="proveReason" >
        <template v-slot="scope">
          <dict-tag-human :options="attendenceOptions.ProveReason" :value="scope.row.proveReason"/>
        </template>
      </el-table-column>
      <el-table-column label="正常出勤时段开始" align="center" prop="norOndutyBegin" />
      <el-table-column label="正常出勤时段结束" align="center" prop="norOndutyEnd" />
      <el-table-column label="实际出勤时段开始" align="center" prop="slotCardOnduty" />
      <el-table-column label="实际出勤时段结束" align="center" prop="slotCardOffduty" />
      <el-table-column label="处理情况" align="center" prop="disposeId" >
        <template v-slot="scope">
          <dict-tag-human :options="attendenceOptions.DisposeStatus" :value="scope.row.disposeId"/>
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
import { listAttendanceAbnormalStatistics, } from "@/api/human/st/attendanceAbnormalStatistics";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getAttendenceOptions} from "@/api/human/hd/attendenceBasis";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";

export default {
  name: "AttendanceAbnormalStatistics",
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
        optionsType:['DisposeStatus','AbnormalReason','ProveReason']
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
      // 出勤异常统计表格数据
      attendanceAbnormalStatisticsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
        disposeId: '',
        norOndutyBegin: null,
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
    //查询绩效选单
    getDisc(){
      getAttendenceOptions(this.attendenceOptionType).then(response=> {
        this.attendenceOptions=response.data;
      })
    },
    /** 日期查询范围变更*/
    dateFormat(val){
      if(val==null){
        this.queryParams.date1=null
        this.queryParams.date2=null
        this.queryParams.norOndutyBegin=null
        return
      }
      this.queryParams.date1 =val[0];
      this.queryParams.date2 =val[1];
    },
    /** 查询出勤异常统计列表 */
    getList() {
      this.loading = true;
      listAttendanceAbnormalStatistics(this.queryParams).then(response => {
        this.attendanceAbnormalStatisticsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        empId: null,
        empName: null,
        empNo: null,
        postId: null,
        postName: null,
        turnTypeId: null,
        turnTypeName: null,
        classId: null,
        className: null,
        excReaId: null,
        proveReaId: null,
        proveReason: null,
        status: null,
        norOndutyBegin: null,
        norOndutyEnd: null,
        slotCardOnduty: null,
        slotCardOffduty: null,
        description: null,
        chiefempnoId: null,
        chiefempnoNo: null,
        chiefempnoName: null,
        chiefempnoPostId: null,
        chiefempnoPostname: null,
        disposeId: null,
        auditType: null,
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
      window.open('http://10.32.157.51:9205/ureport/preview?_u=file:出勤异常表.ureport.xml&compId='+this.queryParams.compId+'&date1='+this.queryParams.date1+'&date2='+this.queryParams.date2+'&disposeId='+this.queryParams.disposeId, '_blank');
    }
  }
};
</script>
