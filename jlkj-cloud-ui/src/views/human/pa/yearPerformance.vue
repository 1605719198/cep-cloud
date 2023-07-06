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
      <el-form-item label="年度" prop="year">
        <el-date-picker
          v-model="queryParams.year"
          type="year"
          value-format="yyyy"
          placeholder="选择年">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="组织机构" prop="dept">
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
            v-hasPermi="['human:yearPerformance:export']"
          >导出</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="yearPerformanceList">
      <el-table-column label="序号" align="center" prop="num" width="60"/>
      <el-table-column label="一级单位" align="center" prop="dept" />
      <el-table-column label="年度" align="center" prop="year" />
      <el-table-column label="状态" align="center" prop="status" >
      <template v-slot="scope">
        <dict-tag-human :options="performanceOptions.Status" :value="scope.row.status"/>
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
import { listYearPerformance } from "@/api/human/pa/yearPerformance";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getPerformanceOptions} from "@/api/human/pa/basis";
import selectOrg from "@/views/components/human/selectUser/selectOrgPerson";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";
import {queryNewPostNameAndChangeDetail} from "@/api/human/hm/employeeTurnover";

export default {
  name: "YearPerformance",
  components: { selectOrg,DictTagHuman },
  data() {
    return {
      //绩效选单选项列表
      performanceOptions: [],
      //绩效选单类型查询
      performanceOptionType:{
        id:'',
        optionsType:['Status']
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
      // 年度绩效主档表格数据
      yearPerformanceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
        dept: null,
        year: null,
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
    /** 查询年度绩效主档列表 */
    getList() {
      this.loading = true;
      listYearPerformance(this.queryParams).then(response => {
        this.yearPerformanceList = response.rows;
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
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        dept: null,
        postId: null,
        year: null,
        empId: null,
        yearScore: null,
        rank: null,
        admScore: null,
        admEmpId: null,
        score: null,
        status: null,
        creator: null,
        creatorId: null,
        creatorNo: null,
        createDate: null
      };
      this.resetForm("form");
    },
    /** 获取单位 */
    getJobNumber() {
        queryNewPostNameAndChangeDetail(this.table.yearPerformanceList).then(res => {
          this.table.yearPerformanceList.dept = res.data
        })
    },
    /** 获取单位 */
    getOrg(val,val1,deptName) {
      this.queryParams.dept = val
    },
    /** 单位按钮操作 */
    openOrgPop() {
      this.$refs.selectOrg.show();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      if (this.queryParams.year != null) {
      this.queryParams.pageNum = 1;
      this.getList();
    } else {
      this.$modal.msgError("年度不能为空");
    }},
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/yearPerformance/export', {
        ...this.queryParams
      }, `yearPerformance_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
