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
      <el-form-item label="考核年月" prop="meritMonth">
        <el-date-picker
          v-model="queryParams.meritMonth"
          value-format="yyyy-MM"
          type="monthrange"
          range-separator="~"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="dateFormat">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="考评类别" prop="meritType">
        <el-select v-model="queryParams.meritType">
          <el-option
            v-for="dict in performanceOptions.MeritType"
            :key="dict.dicNo"
            :label="dict.dicName"
            :value="dict.dicNo"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="一级单位" prop="dept">
        <el-input v-model="queryParams.dept" disabled>
          <el-button slot="append" icon="el-icon-search" @click="openOrgPop"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button
        type="warning"
        plain
        icon="el-icon-download"
        size="mini"
        @click="handleExport"
        v-hasPermi="['human:deptEvaluationQuery:export']"
      >导出</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="deptEvaluationQueryList" >
      <el-table-column label="序号" align="center" prop="num" width="60"/>
      <el-table-column label="一级单位" align="center" prop="dept" />
      <el-table-column label="考评状态" align="center" prop="meritStatus" >
        <template v-slot="scope">
          <dict-tag-human :options="performanceOptions.MeritStatus" :value="scope.row.meritStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="人数" align="center" prop="people" />
      <el-table-column label="比例%" align="center" prop="proportion" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <select-org ref="selectOrg" @ok="getOrg"/>
  </div>
</template>

<script>
import { listDeptEvaluationQuery } from "@/api/human/pa/deptEvaluationQuery";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getPerformanceOptions} from "@/api/human/pa/basis";
import selectOrg from "@/views/components/human/selectUser/selectOrgPerson";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";

export default {
  name: "DeptEvaluationQuery",
  components: { selectOrg,DictTagHuman },
  data() {
    return {
      //绩效选单选项列表
      performanceOptions: [],
      //绩效选单类型查询
      performanceOptionType:{
        id:'',
        optionsType:['MeritStatus','MeritType']
      },
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
      // 个人绩效主档表格数据
      deptEvaluationQueryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dept: null,
        compId: this.$store.state.user.userInfo.compId,
        meritMonth: null,
        meritType: null,
        month1:null,
        month2:null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        meritStatus: [
          { required: true, message: "考评状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getDisc();
    this.getCompanyList()
  },
  methods: {
    /** 查询个人绩效主档列表 */
    getList() {
      this.loading = true;
      listDeptEvaluationQuery(this.queryParams).then(response => {
        this.deptEvaluationQueryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //查询绩效选单
    getDisc(){
      getPerformanceOptions(this.performanceOptionType).then(response=> {
        this.performanceOptions=response.data;
      })
    },
    /** 单位按钮操作 */
    openOrgPop() {
      this.$refs.selectOrg.show();
    },
    /** 获取单位 */
    getOrg(val,val1,deptName) {
      this.queryParams.dept = val
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
        this.queryParams.month1=null
        this.queryParams.month2=null
        this.queryParams.meritMonth=null
        return
      }
      this.queryParams.month1 =val[0];
      this.queryParams.month2 =val[1];
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        meritType: null,
        meritMonth: null,
        empId: null,
        deptType: null,
        dept: null,
        postId: null,
        isTeam: null,
        teamRatio: null,
        teamScore: null,
        isPoint: null,
        pointRatio: null,
        pointScore: null,
        isFix: null,
        fixRatio: null,
        fixScore: null,
        fixId: null,
        isKpi: null,
        kpiRatio: null,
        kpiScore: null,
        selfAppr: null,
        admAppr: null,
        pdAdjust: null,
        pbAdjust: null,
        otherAdjust: null,
        score: null,
        selfScore: null,
        admScore: null,
        rank: null,
        meritStatus: null,
        creator: null,
        creatorId: null,
        createDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      if (this.queryParams.meritMonth != null) {
      this.queryParams.pageNum = 1;
      this.getList();
    } else {
      this.$modal.msgError("考核年月不能为空");
    }
      },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/deptEvaluationQuery/export', {
        ...this.queryParams
      }, `deptEvaluationQuery_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
