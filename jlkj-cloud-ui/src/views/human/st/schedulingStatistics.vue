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
      <el-form-item label="轮班方式" prop="shiftmodeId">
        <el-select v-model="queryParams.shiftmodeId" placeholder="请选择轮班方式" style="width: 100%;" >
          <el-option
            v-for="dict in shiftModeList"
            :key="dict.id"
            :label="dict.turnTypeName"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="班别" prop="classId">
        <el-select v-model="queryParams.classId" placeholder="请选择班别" style="width: 100%">
          <el-option
            v-for="dict in shiftClassList"
            :key="dict.id"
            :label="dict.className"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="开始时间" prop="startDate">
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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['human:schedulingStatistics:export']"
        >导出</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="schedulingStatisticsList" >
      <el-table-column label="序号" align="center" prop="num" width="60"/>
      <el-table-column label="轮班方式" align="center" prop="turnTypeName" />
      <el-table-column label="班别" align="center" prop="className" />
      <el-table-column label="开始时间" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="周期设定" align="center" prop="rule" />
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
import { listSchedulingStatistics } from "@/api/human/st/schedulingStatistics";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {listShiftMode} from "@/api/human/hd/shiftMode";
import {listShiftClass} from "@/api/human/hd/shiftClass";

export default {
  name: "SchedulingStatistics",
  data() {
    return {
      //公司列表
      companyList:[],
      //轮班方式列表
      shiftModeList:[],
      //班别列表
      shiftClassList:[],
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
      // 年度排班统计表格数据
      schedulingStatisticsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
        shiftmodeId: '',
        classId: '',
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
  watch:{
    'queryParams.compId':{
      deep:true,
      immediate:true,
      handler:function( newV){
        this.getShiftList();
      }
    },
    'queryParams.shiftmodeId':{
      deep:true,
      immediate:false,
      handler:function(newV){
        if(newV!=null){
          this.getShiftClass();
        }else{
          this.shiftClassList=[];
        }
      }
    }
  },
  created() {
    this.getCompanyList();
  },
  methods: {
    /** 查询年度排班统计列表 */
    getList() {
      this.loading = true;
      listSchedulingStatistics(this.queryParams).then(response => {
        this.schedulingStatisticsList = response.rows;
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
    //查询轮班方式
    getShiftList(){
      listShiftMode(this.queryParams).then(response => {
        this.shiftModeList = response.rows
      })
    },
    //查询班别
    getShiftClass(){
      var queryParams = {
        shiftmodeId:this.queryParams.shiftmodeId
      }
      listShiftClass(queryParams).then(response => {
        this.shiftClassList = response.rows;
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
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        shiftmodeId: null,
        turnTypeName: null,
        classId: null,
        className: null,
        startDate: null,
        endDate: null,
        rule: null,
        versionNo: null,
        isNew: null,
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
      window.open('http://10.32.157.51:9205/ureport/preview?_u=file:年度排班统计表.ureport.xml&compId='+this.queryParams.compId+'&date1='+this.queryParams.date1+'&date2='+this.queryParams.date2+'&shiftmodeId='+this.queryParams.shiftmodeId+'&classId='+this.queryParams.classId, '_blank');
    }
  }
};
</script>
