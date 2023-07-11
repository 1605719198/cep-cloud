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
          <el-form-item label="员工姓名">
            <el-input v-model="queryParams.empName" disabled>
              <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="20">
          <el-col :span="4">
            <pagination
              v-show="personPerformanceFixTotal>0"
              :total="personPerformanceFixTotal"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              style="margin-top: -12px;margin-bottom: 24px;right: 48px"
              @pagination="getList"
            />
            <el-table v-loading="loading" :data="personPerformanceFixList" border @row-click="getPersonPerformanceFix">
              <el-table-column label="年月" align="center" prop="meritMonth" />
              <el-table-column label="类别" align="center" prop="meritType">
                <template v-slot="scope">
                  <dict-tag-human :options="performanceOptions.MeritType" :value="scope.row.meritType"/>
                </template>
              </el-table-column>
              <el-table-column label="状态" align="center" prop="meritStatus">
                <template v-slot="scope">
                  <dict-tag-human :options="performanceOptions.FixedEvaluationStatus" :value="scope.row.meritStatus"/>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
          <el-col :span="20">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button
                  type="primary"
                  plain
                  icon="el-icon-switch-button"
                  size="mini"
                  @click="handleStart"
                  v-if="showStart"
                  v-hasPermi="['human:fixedProjectEvaluation:start']"
                >启动</el-button>
                <el-button
                  type="primary"
                  plain
                  icon="el-icon-check"
                  size="mini"
                  @click="handleConfirm"
                  v-else
                  v-hasPermi="['human:fixedProjectEvaluation:confirm']"
                >确认</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button
                  type="success"
                  plain
                  icon="el-icon-right"
                  size="mini"
                  @click="handleSend"
                  v-if="showStart"
                  v-hasPermi="['human:fixedProjectEvaluation:send']"
                >呈送</el-button>
              </el-col>
              <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
            </el-row>
            <el-form ref="form" :model="form" :rules="rules" :key="key" label-width="100px">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="员工姓名" prop="empId">
                    {{this.user.empId}}-{{this.user.empName}}
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="所属单位" prop="dept">
                    {{form.dept}}
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="岗位" prop="postId">
                    {{form.postId}}
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="考核年月" prop="meritMonth">
                    <el-date-picker
                      v-model="form.meritMonth"
                      value-format="yyyy-MM"
                      type="month">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="考评类别" prop="meritType">
                    <el-select v-model="form.meritType" placeholder="考评类别">
                      <el-option
                        v-for="dict in performanceOptions.MeritType"
                        :key="dict.dicNo"
                        :label="dict.dicName"
                        :value="dict.dicNo"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="人员类别" prop="deptType">
                    <el-select v-model="form.deptType" placeholder="人员类别">
                      <el-option
                        v-for="dict in baseInfoData.HP020"
                        :key="dict.dicNo"
                        :label="dict.dicName"
                        :value="dict.dicNo"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="输入人" prop="creator">
                    {{form.creator}}
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="输入日期" prop="createDate">
                    {{form.createDate}}
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="主管考评" prop="apprEmp">
                    {{form.apprEmp}}
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="考评时间">
                    <span>{{ parseTime(form.apprTime,  '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="考评状态">
                    <dict-tag-human :options="performanceOptions.ApprStatus" :value="form.apprStatus"/>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="最终复核">
                    {{form.lastEmp}}
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="考评时间">
                    <span>{{ parseTime(form.apprTime1,  '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="考评状态">
                    <dict-tag-human :options="performanceOptions.ApprStatus" :value="form.apprStatus1"/>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
            <el-table v-loading="fixedProjectLoading" :data="fixedProjectList">
              <el-table-column type="index" label="项次" width="55" align="center"/>
              <el-table-column label="人员姓名" align="center" prop="empId" :formatter="userFormat"/>
              <el-table-column label="岗位" align="center" prop="postId"/>
              <el-table-column label="人员类别" align="center" prop="deptType">
                <template v-slot="scope">
                  <dict-tag-human :options="baseInfoData.HP020" :value="scope.row.deptType"/>
                </template>
              </el-table-column>
              <el-table-column label="考评分数" align="center" prop="score"/>
              <el-table-column label="状态" align="center" prop="meritStatus">
                <template v-slot="scope">
                  <dict-tag-human :options="performanceOptions.MeritStatus" :value="scope.row.meritStatus"/>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template v-slot="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit-outline"
                    @click="supervisorEvaluation(scope.row)"
                  >考评
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>

        <!-- 主管考评对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
          <el-form ref="supervisorEvaluationForm" :model="supervisorEvaluationForm" label-width="100px">
            <el-row>
              <el-col :span="6">
                <el-form-item label="员工姓名">
                  {{supervisorEvaluationForm.empName}}
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="考核年月">
                  {{supervisorEvaluationForm.meritMonth}}
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="考评分数">
                  {{supervisorEvaluationForm.admScore}}
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="等第">
                  {{supervisorEvaluationForm.grade}}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="考评意见">
                  <el-input type="textarea" v-model="supervisorEvaluationForm.selfAppr" :autosize="{ minRows: 2, maxRows: 4}" maxlength="400" show-word-limit placeholder="请输入考评意见" :disabled="showSelfAppr"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="复核意见">
                  <el-input type="textarea" v-model="supervisorEvaluationForm.admAppr" :autosize="{ minRows: 2, maxRows: 4}" maxlength="400" show-word-limit placeholder="请输入复核意见" :disabled="showAdmAppr"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <el-table v-loading="keyWorkItemsLoading" :data="keyWorkItemsList" max-height="300px" border>
            <el-table-column label="项次" width="45" align="center" prop="num"/>
            <el-table-column label="考评项目" align="center" prop="item">
              <template v-slot="scope">
                <el-input type="textarea" size="mini" v-model="scope.row.item" disabled placeholder="请输入考评项目"/>
              </template>
            </el-table-column>
            <el-table-column label="考评内容说明" align="center" prop="itemGoal">
              <template v-slot="scope">
                <el-input type="textarea" size="mini" v-model="scope.row.itemGoal" disabled placeholder="请输入考评内容说明"/>
              </template>
            </el-table-column>
            <el-table-column label="权重%" width="75" align="center" prop="weight">
              <template v-slot="scope">
                <el-input size="mini" v-model="scope.row.weight" disabled placeholder="请输入权重"/>
              </template>
            </el-table-column>
            <el-table-column label="主管考评" width="75" align="center" prop="admScore">
              <template v-slot="scope">
                <el-input size="mini" v-model="scope.row.admScore"/>
              </template>
            </el-table-column>
            <el-table-column label="备注" align="center" prop="remark">
              <template v-slot="scope">
                <el-input size="mini" type="textarea" v-model="scope.row.remark"/>
              </template>
            </el-table-column>
          </el-table>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitSupervisorEvaluationForm" v-hasPermi="['human:fixedProjectEvaluation:edit']">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>
        <select-user ref="select" @ok="getEmpId"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import selectUser from "@/views/components/human/selectUser/selectUser";
import {getPerformanceOptions} from "@/api/human/pa/basis";
import {
  confirmPersonalPerformance, confirmPersonalPerformanceFix, listPersonPerformance, listPersonPerformanceDetail,
  listPersonPerformanceFix, listPersonPerformanceSchedule, sendPersonPerformance, sendPersonPerformanceFix,
  startPersonPerformanceFix,
  updateAdmAppr, updateAdmEvaluation
} from "@/api/human/pa/personPerformance";
import {queryInfo} from "@/api/human/hm/personnelBasicInfo";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";
import {getBaseInfo} from "@/api/human/hm/baseInfo";
import {queryAllUser} from "@/api/system/user";

export default {
  name: "FixedProjectEvaluation",
  components: { selectUser,DictTagHuman },
  data() {
    return {
      //用户字典
      resUserDiction: [],
      // 遮罩层
      loading: false,
      // 固定项目考评遮罩层
      fixedProjectLoading: false,
      // 重点工作项遮罩层
      keyWorkItemsLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 个人绩效主档表格总条数
      personPerformanceFixTotal: 0,
      // 个人绩效主档表格数据
      personPerformanceFixList: [],
      // 固定项目考评表格数据
      fixedProjectList: [],
      // 重点工作项表格数据
      keyWorkItemsList: [],
      // 主管考评弹出层标题
      title: "",
      // 是否显示主管考评弹出层
      open: false,
      // 是否显示启动按钮
      showStart: true,
      // 呈送按钮控制
      showSend: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        meritMonth: null,
        meritType: null,
        empName: null,
        empId: null,
        empNo: null
      },
      // 明细表单参数
      form: {},
      // 主管考评表单参数
      supervisorEvaluationForm: {},
      // 表单校验
      rules: {
        meritMonth:[
          { required: true, message: "考核年月不能为空", trigger: ['blur', 'change'] }
        ],
        meritType:[
          { required: true, message: "考评类别不能为空", trigger: ['blur', 'change'] }
        ],
        deptType:[
          { required: true, message: "人员类别不能为空", trigger: ['blur', 'change'] }
        ],
      },
      // 公司别下拉选单
      companyName: [],
      //登录人信息
      user: {},
      //绩效选单选项列表
      performanceOptions:{},
      //绩效选单类型查询
      performanceOptionType:{
        id:'',
        optionsType:[
          'MeritType',
          'MeritStatus',
          'ApprStatus',
          'FixedEvaluationStatus']
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
      postId: 0,
      postName: null,
      //确认表单数据
      confirmForm:{},
      key: undefined,
      showSelfAppr: false,
      showAdmAppr: false,
      sendForm:{}
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
    getDisc(){
      getPerformanceOptions(this.performanceOptionType).then(response=> {
        this.performanceOptions=response.data;
      })
    },
    //初始化数据
    initData(){
      getBaseInfo(this.baseInfo).then(response => {
        this.baseInfoData = response.data;
      });
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.user.empId = this.$store.state.user.userInfo.userName;
      this.user.empName = this.$store.state.user.userInfo.nickName;
      this.queryParams.compId = this.user.compId
    },
    /** 查询固定项目启动档列表 */
    getList() {
      this.loading = true;
      listPersonPerformanceFix(this.queryParams).then(response => {
        this.personPerformanceFixList = response.data.rows;
        this.personPerformanceFixTotal = response.data.total;
        this.loading = false;
      });
    },
    /** 查询固定项目启动档列表 */
    getPersonPerformanceList() {
      this.fixedProjectLoading = true;
      this.queryParams.isFix = '0'
      listPersonPerformance(this.queryParams).then(response => {
        this.fixedProjectList = response.data.rows;
        this.fixedProjectLoading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
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
    /** 主管考评提交按钮 */
    submitSupervisorEvaluationForm() {
      this.supervisorEvaluationForm.keyWorkItemsList = this.keyWorkItemsList
      updateAdmEvaluation(this.supervisorEvaluationForm).then(response => {
        this.$modal.msgSuccess("新增成功");
        this.getPersonPerformanceList();
        this.open = false;
      });
    },
    /** 查询人员类别考评项目设定明细档表格 */
    getPersonPerformanceFix(row) {
      this.form.postId = null
      this.form = row
      this.form.empNo = row.empId
      queryInfo(this.form).then(response => {
        this.form.dept = response.data[0].departmentName
        this.form.postId = response.data[0].postName
        this.postName = response.data[0].postName
        listPersonPerformanceSchedule(this.form).then(res =>{
          this.form.apprEmp = res.data.rows[0].apprEmp
          this.form.apprTime = res.data.rows[0].apprTime
          this.form.apprStatus = res.data.rows[0].apprStatus
          this.form.lastEmp = res.data.rows[1].apprEmp
          this.form.apprTime1 = res.data.rows[1].apprTime
          this.form.apprStatus1 = res.data.rows[1].apprStatus
          if (this.form.apprStatus === '1' && this.form.apprStatus1 === '0'){
            this.showStart = false
          }
          this.getPersonPerformanceList();
          this.key = Math.random()
        })
      })
    },
    /** 获取员工姓名点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getEmpId(val,userName) {
      this.queryParams.empName = val + '-' +userName
      this.queryParams.empId = val
    },
    dateFormat(picker) {
      this.queryParams.startTime=picker[0]
      this.queryParams.endTime=picker[1]
    },
    /** 启动按钮 */
    handleStart() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.empId = this.user.empId
          this.form.compId = this.queryParams.compId
          this.queryParams.empNo = this.user.empId
          queryInfo(this.queryParams).then(response => {
            this.form.dept = response.data[0].departmentId
            this.form.postId = response.data[0].postId
              startPersonPerformanceFix(this.form).then(res =>{
                this.$modal.msgSuccess("启动成功");
              })
          })
        }
      });
    },
    handleConfirm(){
      this.confirmForm.id = this.form.id
      confirmPersonalPerformanceFix(this.confirmForm).then(res =>{
        this.form.apprStatus1 = '1'
        this.key = Math.random()
        this.$modal.msgSuccess("确认成功");
      })
    },
    /** 呈送按钮 */
    handleSend() {
      this.sendForm = {}
      this.sendForm.id = this.form.id
      this.sendForm.fixedProjectList = this.fixedProjectList
      sendPersonPerformanceFix(this.sendForm).then(res =>{
        this.form.apprStatus = '1'
        this.key = Math.random()
        this.$modal.msgSuccess("呈送成功");
      })
    },
    supervisorEvaluation(data) {
      this.supervisorEvaluationForm = data
      this.showSelfAppr = false
      this.showAdmAppr = false
      this.keyWorkItemsLoading = true
      this.supervisorEvaluationForm.empName = this.userFormat(data);
      listPersonPerformanceDetail(this.supervisorEvaluationForm).then(response => {
        this.keyWorkItemsList = response.data.rows
        this.keyWorkItemsLoading = false
      });
      if (this.form.apprStatus === '0'){
        this.showAdmAppr = true
      } else {
        this.showSelfAppr = true
      }
      this.open = true
      this.title = "主管考评";
    },
  }
};
</script>
<style scoped>
</style>
