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
      <el-form-item label="工号" prop="empNo">
        <el-input v-model="queryParams.empNo" placeholder="请输入工号" :disabled="true">
          <el-button slot="append" icon="el-icon-search" @click="inputClick" clearable></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="组织机构" prop="postName">
        <el-input v-model="queryParams.postName" disabled>
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
          v-hasPermi="['human:saveTimeStatistics:export']"
        >导出</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="saveTimeStatisticsList" >
      <el-table-column label="序号" align="center" prop="num" width="60"/>
      <el-table-column label="姓名" align="center" prop="empName" />
      <el-table-column label="工号" align="center" prop="empNo" />
      <el-table-column label="岗位名称" align="center" prop="postName" />
      <el-table-column label="加班小时数" align="center" prop="overtimeHours" />
      <el-table-column label="存班小时数" align="center" prop="saveHours" />
      <el-table-column label="补休小时数" align="center" prop="compHours" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <select-user ref="select" @ok="getJobNumber"/>
    <select-org ref="selectOrg" @ok="getOrg"/>
  </div>
</template>

<script>
import { listSaveTimeStatistics, } from "@/api/human/st/saveTimeStatistics";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import selectUser from "@/views/components/human/selectUser/selectUser";
import selectOrg from "@/views/components/human/selectUser/selectOrgPerson";

export default {
  name: "SaveTimeStatistics",
  components:{selectUser,selectOrg},
  data() {
    return {
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
      // 存班统计表格数据
      saveTimeStatisticsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
        empNo: '',
        postName: '',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
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
    /** 单位按钮操作 */
    openOrgPop() {
      this.$refs.selectOrg.show();
    },
    /** 获取单位 */
    getOrg(val,val1,deptName) {
      this.queryParams.postName = val
    },
    /** 查询存班统计列表 */
    getList() {
      this.loading = true;
      listSaveTimeStatistics(this.queryParams).then(response => {
        this.saveTimeStatisticsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        year: null,
        month: null,
        empId: null,
        empName: null,
        empNo: null,
        postId: null,
        postName: null,
        overtimeDays: null,
        overtimeHours: null,
        saveDays: null,
        saveHours: null,
        compDays: null,
        compHours: null,
        creator: null,
        creatorId: null,
        createDate: null
      };
      this.resetForm("form");
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(empId,userName,compId) {
      this.queryParams.empNo = empId;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 导出按钮操作 */
    handleExport() {
      window.open('http://10.32.157.51:9205/ureport/preview?_u=file:存班统计表.ureport.xml&compId='+this.queryParams.compId+'&empNo='+this.queryParams.empNo+'&postName='+this.queryParams.postName, '_blank');
    }
  }
};
</script>
