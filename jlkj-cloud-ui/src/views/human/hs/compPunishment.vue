<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别" >
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleSave"
          v-hasPermi="['human:compPunishment:save']"
        >保存</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="compPunishmentList" @selection-change="handleSelectionChange" height="67vh">
      <el-table-column label="包含" align="center" prop="ifChecked" width="50">
        <template v-slot="scope">
          <el-checkbox v-model="scope.row.ifChecked" true-label="1" false-label="0"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column label="专业奖罚项目编码" align="center" prop="payProCode" />
      <el-table-column label="专业奖罚项目名称" align="center" prop="payProName" />
      <el-table-column label="说明" align="center" prop="salaryDescribe" />
      <el-table-column label="排序序号" align="center" prop="srino" width="100" >
        <template v-slot="scope">
          <el-input type="number" v-model="scope.row.srino" placeholder=""></el-input>
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
import { listCompPunishment,  saveCompPunishment } from "@/api/human/hs/compPunishment";
import { selectCompany } from '@/api/human/hp/deptMaintenance'

export default {
  name: "CompPunishment",
  data() {
    return {
      //登录人信息
      user: {},
      //公司数据
      companyList: [],
      // 遮罩层
      loading: true,
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
      // 各公司专业奖罚明细项目维护表格数据
      compPunishmentList: [],
      //选中数据
      multipleList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
      },
      // 表单校验
      rules: {

      }
    };
  },
  watch: {
    'queryParams.compId':{
      handler(val) {
        this.getList();
      },
      deep: true,
      immediate: false,
    },
  },
  created() {
    this.getCompanyList()
    this.initData();
  },
  methods: {
    //获取公司列表
    getCompanyList() {
      selectCompany().then(response => {
        this.companyList = response.data
      })
    },
    //初始化数据
    initData(){
      this.user.empNo = this.$store.state.user.userInfo.userName;
      this.user.empId = this.$store.state.user.userInfo.userId;
      this.user.empName = this.$store.state.user.userInfo.nickName;
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId
    },
    /** 查询各公司专业奖罚明细项目维护列表 */
    getList() {
      this.loading = true;
      listCompPunishment(this.queryParams).then(response => {
        this.compPunishmentList = response.rows;
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.uuid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 保存按钮操作 */
    handleSave() {
      this.multipleList = [];
      this.compPunishmentList.forEach((value)=>{
        if(value.ifChecked==='1'){
          value.compId = this.queryParams.compId;
          this.multipleList.push(value)
        }
      })
      if(this.multipleList.length===0){
        this.$modal.msgError('请至少选择一条数据')
      }else{
        saveCompPunishment(this.multipleList).then( response=>{
          this.$modal.msgSuccess('保存成功')
          this.getList();
        })
      }
    },

  }
};
</script>
