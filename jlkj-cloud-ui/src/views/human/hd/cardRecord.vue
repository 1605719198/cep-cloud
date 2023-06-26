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
      <el-form-item label="刷卡日期" prop="cardTime">
        <el-date-picker
          v-model="queryParams.cardTime"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="~"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="dateFormat">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

    <el-table v-loading="loading" :data="recordList">
      <el-table-column label="工号" align="center" prop="empNo" />
      <el-table-column label="姓名" align="center" prop="empName" />
      <el-table-column label="刷卡时间" align="center" prop="cardTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.cardTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="刷卡卡钟编码" align="center" prop="macCode" />
      <el-table-column label="刷卡卡钟名称" align="center" prop="macName" />
      <el-table-column label="公司" align="center" prop="companyName" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <select-user ref="select" @ok="getJobNumber"/>
  </div>
</template>

<script>
import { listCardRecord } from "@/api/human/hd/cardRecord";
import { selectCompany } from "@/api/human/hp/deptMaintenance";
import selectUser from "@/views/components/human/selectUser/selectUser";

export default {
  name: "cardRecord",
  dicts: ['sys_classtype'],
  components:{selectUser},
  data() {
    return {
      //公司列表
      companyList:[],
      //用户公司别
      userCompId: this.$store.state.user.userInfo.compId,
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
      // 刷卡记录表格数据
      recordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
        empNo: null,
        cardTime: null,
        date1:null,
        date2:null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getCompanyList();
    // this.getList();
  },
  methods: {
    /** 查询公司列表 */
    getCompanyList(){
      selectCompany().then(response =>{
        this.companyList = response.data
        }
      )
    },
    /** 查询刷卡记录列表 */
    getList() {
      this.loading = true;listCardRecord(this.queryParams).then(response => {
        this.recordList = response.rows;
        this.total = response.total;
        this.loading = false;
      });

    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 日期查询范围变更*/
    dateFormat(val){
      if(val==null){
        this.queryParams.date1=null
        this.queryParams.date2=null
        this.queryParams.cardTime=null
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
        empNo: null,
        cardTime: null,
        macCode: null,
        createDate: null,
        checkTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery(e) {
        if(this.judgeQuery()){
          this.queryParams.pageNum = 1;
          this.getList();
        }
    },
    /** 查询条件判定 */
    judgeQuery(){
      if(this.queryParams.empNo===null||this.queryParams.empNo===''){
        this.$modal.msgError("请输入工号")
        return false;
      }else{
        return true;
      }
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.date1 = null;
      this.queryParams.date2 = null;
      this.recordList = [];
      this.total = 0;
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(empId,userName,compId) {
      this.queryParams.empNo = empId;
    },
  }
};
</script>
<style scoped>
/deep/.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
