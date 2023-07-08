<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="公司别" prop="compId">
            <el-select v-model="queryParams.compId" placeholder="请选择公司">
              <el-option
                v-for="dict in companyName"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="考核年月" prop="meritMonth">
            <el-date-picker
              v-model="queryParams.meritMonth"
              type="monthrange"
              value-format="yyyy-MM"
              range-separator="至"
              start-placeholder="开始月份"
              end-placeholder="结束月份"
              @change="dateFormat">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="考评类别" prop="meritType">
            <el-select v-model="queryParams.meritType" placeholder="考评类别" style="width: 100%">
              <el-option
                v-for="dict in performanceOptions.MeritType"
                :key="dict.dicNo"
                :label="dict.dicName"
                :value="dict.dicNo"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="一级单位">
            <el-input v-model="queryParams.dept" disabled style="width: 87%">
              <el-button slot="append" icon="el-icon-search" @click="openOrgPop"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item label="考核主管">
            <el-input v-model="queryParams.admEmpId" disabled style="width: 87%">
              <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item label="人员类别" prop="deptType">
            <el-select v-model="queryParams.deptType" placeholder="人员类别" style="width: 100%">
              <el-option
                v-for="dict in baseInfoData.HP020"
                :key="dict.dicNo"
                :label="dict.dicNo + '-' + dict.dicName"
                :value="dict.dicNo"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="考评状态" prop="meritStatus">
            <el-select v-model="queryParams.meritStatus" placeholder="考评状态">
              <el-option
                v-for="dict in performanceOptions.MeritStatus"
                :key="dict.dicNo"
                :label="dict.dicNo + '-' + dict.dicName"
                :value="dict.dicNo"
              ></el-option>
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
              v-hasPermi="['human:evaluationListQuery:export']"
            >导出</el-button>
          </el-form-item>
        </el-form>

        <el-table v-loading="loading" :data="evaluationList">
          <el-table-column type="index" label="序号" width="50" align="center"/>
          <el-table-column label="考评年月" align="center" prop="meritMonth"/>
          <el-table-column label="考评类别" align="center" prop="meritType">
            <template v-slot="scope">
              <dict-tag-human :options="performanceOptions.MeritType" :value="scope.row.meritType"/>
            </template>
          </el-table-column>
          <el-table-column label="单位" align="center" prop="dept"/>
          <el-table-column label="职工编号/姓名" align="center" prop="empId" :formatter="userFormat"/>
          <el-table-column label="职称" align="center" prop="jobTitleId">
            <template v-slot="scope">
              <dict-tag-human :options="humanJobTitle" :value="scope.row.jobTitleId"/>
            </template>
          </el-table-column>
          <el-table-column label="人员类别" align="center" prop="deptType">
            <template v-slot="scope">
              <dict-tag-human :options="baseInfoData.HP020" :value="scope.row.deptType"/>
            </template>
          </el-table-column>
          <el-table-column label="考绩分数" align="center" prop="score"/>
          <el-table-column label="等第" align="center" prop="grade"/>
          <el-table-column label="考评状态" align="center" prop="meritStatus">
            <template v-slot="scope">
              <dict-tag-human :options="performanceOptions.MeritStatus" :value="scope.row.meritStatus"/>
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
        <select-user ref="select" @ok="getEmpId"/>
        <select-org ref="selectOrg" @ok="getOrg"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import selectUser from "@/views/components/human/selectUser/selectUser";
import selectOrg from "@/views/components/human/selectUser/selectOrgPerson";
import {getPerformanceOptions} from "@/api/human/pa/basis";
import {listEvaluation} from "@/api/human/pa/personPerformance";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";
import {getBaseInfo} from "@/api/human/hm/baseInfo";
import {listJobTitleidname} from "@/api/human/hp/jobTitle";
import {queryAllUser} from "@/api/system/user";

export default {
  name: "EvaluationListQuery",
  components: { selectUser,selectOrg,DictTagHuman },
  data() {
    return {
      // 遮罩层
      loading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        meritType: null,
        dept: null,
        admEmpId: null,
        deptType: null,
        meritStatus: null
      },
      // 考评清单表格数据
      evaluationList: [],
      // 公司别下拉选单
      companyName: [],
      //绩效选单选项列表
      performanceOptions:{},
      //绩效选单类型查询
      performanceOptionType:{
        id:'',
        optionsType:[
          'MeritType',
          'MeritStatus']
      },
      //选单列表
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'HP020'
        ]
      },
      //选单数据
      baseInfoData: [],
      //职位名称id列表
      humanJobTitle:[],
    };
  },
  created() {
    this.initData();
    this.getDisc();
    selectCompany().then(res => {
      this.companyName = res.data
    })
  },
  beforeMount(){
    /** 装载人员信息 */
    queryAllUser().then(response => {
      this.resUserDiction = response.rows;
    })
  },
  methods: {
    // 人员字典翻译
    userFormat(row, column) {
      /** UserDictOnlyName 只显示姓名 */
      // return this.UserDictOnlyName(this.resUserDiction, row.createBy);
      /** UserDictFullName 显示工号_姓名 */
      return this.UserDictFullName(this.resUserDiction, row.empId);
    },
    // 人员字典翻译
    userFormat1(data) {
      /** UserDictFullName 显示工号_姓名 */
      return this.UserDictFullName(this.resUserDiction, data);
    },
    getDisc(){
      getPerformanceOptions(this.performanceOptionType).then(response=> {
        this.performanceOptions=response.data;
      })
    },
    //初始化数据
    initData(){
      listJobTitleidname(this.queryParams.compId).then(response =>{
        this.humanJobTitle = response.rows;
      })
      getBaseInfo(this.baseInfo).then(response => {
        this.baseInfoData = response.data;
      });
      this.queryParams.compId = this.$store.state.user.userInfo.compId;
    },
    /** 查询考评清单列表 */
    getList() {
      this.loading = true;
      listEvaluation(this.queryParams).then(response => {
        this.evaluationList = response.data.rows;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 获取员工姓名点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getEmpId(val) {
      this.queryParams.admEmpId = this.userFormat1(val)
    },
    /** 获取一级单位点击事件 */
    openOrgPop() {
      this.$refs.selectOrg.show();
    },
    /** 获取部门 */
    getOrg(deptCode) {
      this.queryParams.dept = deptCode
    },
    dateFormat(picker) {
      this.queryParams.startTime=picker[0]
      this.queryParams.endTime=picker[1]
    },
    /** 导出按钮操作 */
    handleExport() {
      const exportParams = {
        compId: this.queryParams.compId,
        meritType: this.queryParams.meritType,
        dept: this.queryParams.dept,
        admEmpId: this.queryParams.admEmpId,
        deptType: this.queryParams.deptType,
        meritStatus: this.queryParams.meritStatus,
        startTime: this.queryParams.startTime,
        endTime: this.queryParams.endTime
      }
      this.download('human/personPerformance/export', {
        ...exportParams
      }, `personPerformance_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scoped>
</style>
