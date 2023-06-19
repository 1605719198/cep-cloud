<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别">
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="参数类别" prop="paramTypeId">
        <el-select v-model="queryParams.paramTypeId" placeholder="请选择参数类别" clearable>
          <el-option
            v-for="dict in salaryOptions.SalaryFormulaParamType"
            :key="dict.dicNo"
            :label="dict.dicName"
            :value="dict.dicNo"
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
          v-hasPermi="['human:payFormulaParam:save']"
        >保存</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="handleCopy"
          v-hasPermi="['human:payFormulaParam:copy']"
        >复制</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="payFormulaParamList" height="67vh">
      <el-table-column label="包含" align="center" prop="isCheck" width="50">
        <template v-slot="scope">
          <el-checkbox v-model="scope.row.isCheck" true-label="1" false-label="0"></el-checkbox>
        </template>
      </el-table-column>
      <el-table-column label="参数代码" align="center" prop="paramCode" />
      <el-table-column label="参数名称" align="center" prop="paramName" />
      <el-table-column label="参数类别" align="center" prop="paramTypeId">
        <template slot-scope="scope">
          <dict-tag-human-base :options="salaryOptions.SalaryFormulaParamType" :value="scope.row.paramTypeId"/>
        </template>
      </el-table-column>
      <el-table-column label="输入人" align="center" prop="creator" />
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
    </el-table>

    <!-- 计算参数复制对话框 -->
    <el-dialog title="公司计算参数复制" :visible.sync="open" width="500px" append-to-body class="customDialogStyle" v-if="open">
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="来源公司" prop="compId">
          <el-select :popper-append-to-body="false" v-model="form.compId" placeholder="请选择来源公司" clearable>
            <el-option
              v-for="dict in companyList"
              :key="dict.compId"
              :label="dict.companyName"
              :value="dict.compId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="目标公司" prop="compIdTo">
          <el-select :popper-append-to-body="false" v-model="form.compIdTo" placeholder="请选择目标公司" clearable>
            <el-option
              v-for="dict in companyList"
              :key="dict.compId"
              :label="dict.companyName"
              :value="dict.compId"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormcopy">确 定</el-button>
        <el-button @click="cancelCopy">取 消</el-button>
      </div>
    </el-dialog>

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
import '@/assets/styles/humanStyles.scss'
import { listPayFormulaParam, savePayFormulaParam, copyPayFormulaParam } from "@/api/human/hs/payFormulaParam";
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getSalaryOptions } from "@/api/human/hs/salaryBasis";
import DictTagHumanBase from '@/views/components/human/dictTag/humanBaseInfo'
export default {
  name: "PayFormulaParam",
  dicts: ['sys_normal_disable'],
  components: {DictTagHumanBase},
  data() {
    return {
      //登录人信息
      user: {},
      //公司数据
      companyList: [],
      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['SalaryFormulaParamType'],
        compId:null,
      },
      //薪资选单选项列表
      salaryOptions: [],
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
      // 公司薪资计算参数表格数据
      payFormulaParamList: [],
      //多选框选中数据
      multipleList:[],
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        paramTypeId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        compId: [
          { required: true, message: '来源公司不能为空', trigger: 'change' }
        ],
        compIdTo: [
          { required: true, message: '目标公司不能为空', trigger: 'change' }
        ]
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
    this.getDisc();
    this.getCompanyList()
    this.initData();
  },
  methods: {
    //重置复制表单
    resetForm(){
      this.form={
      compIdTo: null,
      compId: null,
      }
    },
    //保存按钮点击事件
    handleSave(){
      this.multipleList = [];
      this.payFormulaParamList.forEach((value)=>{
        if(value.isCheck==='1'){
          this.multipleList.push(value)
        }
      })
      var data={
        compId: this.queryParams.compId,
        payTableFormulaParamList:this.multipleList
      }
      if(this.multipleList.length===0){
        this.$modal.msgError('请至少选择一条数据')
      }else{
        savePayFormulaParam(data).then( response=>{
          this.$modal.msgSuccess('保存成功')
          this.getList()
        })
      }
    },
    //复制按钮点击事件
    handleCopy(){
      this.resetForm();
      this.open = true;
    },
    //复制确认
    submitFormcopy(){
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.compId == this.form.compIdTo) {
            this.$modal.msgError('请选择两个不同的公司')
          } else {
            copyPayFormulaParam(this.form).then( response =>{
              this.$modal.msgSuccess('复制成功')
              this.open = false;
              this.getList()
            })
          }
        }
      })
    },
    //取消确认
    cancelCopy(){
      this.open = false;
    },
    //查询薪资选单
    getDisc(){
      getSalaryOptions(this.salaryOptionType).then(response=>{
        this.salaryOptions = response.data;
      })
    },
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
    /** 查询公司薪资计算参数列表 */
    getList() {
      this.loading = true;
      listPayFormulaParam(this.queryParams).then(response => {
        this.payFormulaParamList = response.rows;
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
      this.queryParams.compId = this.user.compId;
      this.queryParams.paramTypeId = null;
      this.handleQuery();
    },
  }
};
</script>
