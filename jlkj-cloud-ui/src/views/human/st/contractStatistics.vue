<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司" :popper-append-to-body="false">
          <el-option
            v-for="dict in companyList"
            :key="dict.deptCode"
            :label="dict.companyName"
            :value="dict.deptCode"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="岗位类别" prop="postTypeId">
        <el-select v-model="queryParams.postTypeId">
          <el-option
            v-for="dict in baseInfoData.post_type_id"
            :key="dict.uuid"
            :label="dict.dicName"
            :value="dict.uuid"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="岗位序列" prop="postSequenceId">
        <el-select v-model="queryParams.postSequenceId">
          <el-option
            v-for="dict in baseInfoData.HP003"
            :key="dict.uuid"
            :label="dict.dicName"
            :value="dict.uuid"
          />
        </el-select>
      </el-form-item>
      <el-row :gutter="10" class="mb8">
        <el-form-item label="合同到期日期" prop="endDate" label-width="100px">
          <el-date-picker
            v-model="queryParams.endDate"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="~"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @change="dateFormat"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="职工来源" prop="sourceOfEmployees">
          <el-select v-model="queryParams.sourceOfEmployees">
            <el-option
              v-for="dict in baseInfoData.employeeSource"
              :key="dict.uuid"
              :label="dict.dicName"
              :value="dict.uuid"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="劳动合同关系" prop="relStatus" label-width="100px">
          <el-select v-model="queryParams.relStatus">
            <el-option
              v-for="dict in baseInfoData.RelationStatus"
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
            v-hasPermi="['human:contractStatistics:export']"
          >导出</el-button>
        </el-form-item>
      </el-row>
    </el-form>

    <el-table v-loading="loading" :data="contractStatisticsList">
      <el-table-column label="序号" align="center" prop="num" width="60"/>
      <el-table-column label="工号" align="center" prop="jobNumber" />
      <el-table-column label="姓名" align="center" prop="fullName" />
      <el-table-column label="岗位名称" align="center" prop="postName" />
      <el-table-column label="签订合同日期" align="center" prop="signDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.signDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="合同起始日期" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="合同期限" align="center" prop="conPeriId" />
      <el-table-column label="合同到期日期" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="版本号" align="center" prop="versionNo" />
      <el-table-column label="输入人" align="center" prop="creatorId" />
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
import { listContractStatistics } from "@/api/human/st/contractStatistics";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getBaseInfo} from "@/api/human/hm/baseInfo";

export default {
  name: "ContractStatistics",
  data() {
    return {
      //公司列表
      companyList:[],
      //员工选单选项列表
      baseInfoData: {},
      //员工选单类型查询
      baseInfo: {
        baseInfoList: ['post_type_id','HP003','RelationStatus','employeeSource']
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
      // 劳动合同统计分析表格数据
      contractStatisticsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
        relStatus: '',
        endDate: null,
        sourceOfEmployees: '',
        postTypeId: '',
        postSequenceId: '',
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
    this.getCompanyList();
  },
  methods: {
    /** 查询公司列表 */
    getCompanyList(){
      selectCompany().then(response =>{
          this.companyList = response.data
        }
      )
    },
    /** 查询劳动合同统计分析列表 */
    getList() {
      this.loading = true;
      listContractStatistics(this.queryParams).then(response => {
        this.contractStatisticsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 日期查询范围变更*/
    dateFormat(val){
      if(val==null){
        this.queryParams.date1=null
        this.queryParams.date2=null
        this.queryParams.endDate=null
        return
      }
      this.queryParams.date1 =val[0];
      this.queryParams.date2 =val[1];
    },
    //查询员工基础配置选单
    getDisc(){
      getBaseInfo(this.baseInfo).then(response => {
        this.baseInfoData = response.data
      });
    },
    // 表单重置
    reset() {
      this.form = {
        compId: null,
        relStatus: null,
        signDate: null,
        startDate: null,
        conPeriId: null,
        endDate: null,
        versionNo: null,
        creatorId: null,
        createDate: null,
        jobNumber: null,
        fullName: null,
        postId: null,
        postName: null,
        sourceOfEmployees: null,
        postTypeId: null,
        postSequenceId: null,
        orgId: null,
        ancestors: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      if(this.queryParams.endDate===null||this.queryParams.endDate===''){
        this.$modal.msgError("请输入日期")
      }else{
        this.queryParams.pageNum = 1;
        this.getList();
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      window.open('http://10.32.157.51:9205/ureport/preview?_u=file:劳动合同统计表.ureport.xml&compId='+this.queryParams.compId+'&date1='+this.queryParams.date1+'&date2='+this.queryParams.date2+'&relStatus='+this.queryParams.relStatus+'&sourceOfEmployees='+this.queryParams.sourceOfEmployees+'&postTypeId='+this.queryParams.postTypeId+'&postSequenceId='+this.queryParams.postSequenceId, '_blank');
    }
  }
};
</script>
