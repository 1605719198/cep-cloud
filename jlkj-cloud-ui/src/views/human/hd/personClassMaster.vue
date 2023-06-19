<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" >
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别" ref="selectCompany" >
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item  prop="type">
        <el-select v-model="personClassType" placeholder="请选择出勤身份类型" >
          <el-option
            v-for="dict in attendenceOptions.PersonClassType"
            :key="dict.dicNo"
            :label="dict.dicName"
            :value="dict.dicNo"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="" prop="empId" v-if="this.personClassType==='2'">
        <treeselect v-model="queryParams.deptId" :options="deptOptions" :show-count="true" placeholder="请选择机构"  style="width: 200px;"/>
      </el-form-item>
      <el-form-item label="" prop="empId" v-if="this.personClassType==='1'">
        <el-input maxlength="32"  v-model="queryParams.empId" placeholder="请输入员工工号" style="width: 200px;">
          <el-button slot="append" icon="el-icon-search" @click="inputClick()"></el-button>
        </el-input>
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
          @click="handleAdd"
          v-hasPermi="['human:personClassMaster:add']"
        >排班</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="classList" @selection-change="handleSelectionChange" height="67vh" v-if="this.personClassType!==''">
      <el-table-column label="员工工号" align="center" prop="empId" v-if="this.personClassType==='1'"/>
      <el-table-column label="机构id" align="center" prop="deptId" v-else-if="this.personClassType==='2'" />
      <el-table-column label="一级机构" align="center" prop="firstDeptName" />
      <el-table-column label="轮班方式" align="center" prop="shiftmodeName" width="180" />
      <el-table-column label="班别" align="center" prop="className" />
      <el-table-column label="开始日期" align="center" prop="startDate" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束日期" align="center" prop="endDate" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" >
        <template v-slot="scope">
          <dict-tag-human-base :options="attendenceOptions.PersonClassStatus" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="输入人" align="center" prop="creator" />
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:personClassMaster:edit']"
          >调班</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-if="this.personClassType!=null"
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改出勤身份设定对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body class="customDialogStyle">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" label-position="left" >
        <el-row :gutter="20" style="margin-bottom: 10px;">
          <el-button
            plain
            type="primary"
            size="mini"
            @click="submitForm"
            style="margin-right: 10px"
          >排班</el-button>
          <el-button
            plain
            v-if="this.form.id !=null"
            type="primary"
            size="mini"
            @click="shiftChange"
            style="margin-right: 10px"
          >调班</el-button>
          <el-button
            plain
            type="primary"
            size="mini"
            @click="runShiftView"
            style="margin-right: 10px"
          >轮班方式/班次/班别查看</el-button>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item prop="compId" label="公司别" >
              {{form.companyName}}
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="this.form.id!=null">
            <el-form-item label="机构ID" prop="deptId" v-if="this.formpersonClassType==='2'" >
              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择机构" :normalizer="normalizer" @select="deptChange" />
            </el-form-item>
            <el-form-item label="员工工号" prop="empId" v-if="this.formpersonClassType==='1'">
              <el-input maxlength="20"  v-model="form.empId" placeholder="请选择员工工号" disabled>
                <el-button slot="append" icon="el-icon-search" @click="inputClick()"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20" v-if="this.form.id==null">
          <el-col :span="12">
            <el-form-item  prop="type" label="出勤身份类型" >
              <el-select v-model="formpersonClassType" placeholder="请选择出勤身份类型" style="width: 100%"  clearable>
                <el-option
                  v-for="dict in attendenceOptions.PersonClassType"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="机构ID" prop="deptId" v-if="this.formpersonClassType==='2'">
              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择机构" :normalizer="normalizer" @select="deptChange" />
            </el-form-item>
            <el-form-item label="员工工号" prop="empId" v-if="this.formpersonClassType==='1'">
              <el-input maxlength="20"  v-model="form.empId" placeholder="请选择员工工号" disabled>
                <el-button slot="append" icon="el-icon-search" @click="inputClick()"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="轮班方式" prop="shiftmodeId">
              <el-select v-model="form.shiftmodeId" placeholder="请选择轮班方式" style="width: 100%;" >
                <el-option
                  v-for="dict in shiftModeList"
                  :key="dict.id"
                  :label="dict.turnTypeName"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="班别" prop="classId">
              <el-select v-model="form.classId" placeholder="请选择班别" style="width: 100%">
                <el-option
                  v-for="dict in shiftClassList"
                  :key="dict.id"
                  :label="dict.className"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="排班开始日期" prop="startDate">
              <el-date-picker clearable
                              style="width: 100%"
                              v-model="form.startDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="排班开始日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排版结束日期" prop="endDate">
              <el-date-picker clearable
                              style="width: 100%"
                              v-model="form.endDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="排班结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="输入日期" prop="createDate">
              {{form.createDate}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="输入人" prop="creator">
              {{form.creatorId}}-{{form.creator}}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <div v-if="this.showCalendar">
        <div>
          <el-divider content-position="center" >调班页面</el-divider>
          <label>月份 </label>
          <el-date-picker
            v-model="queryParams.queryMonth"
            :clearable="false"
            type="month"
            value-format="yyyy-MM-dd"
            placeholder="选择月"
          >
          </el-date-picker>

          <human-calendar style="width: 100%;" :value="this.queryParams.queryMonth">
            <template slot="dateCell" slot-scope="{date, data}" align="center" >
              <div style="width: 100%;display: flex;flex-direction: column;justify-content: center;align-items: center;" v-if="shiftChangeObj!=null&&data.type==='current-month'">

                <div style="width: 100%;display: flex;flex-direction: row;align-items: center;margin-bottom: 1px;">
                  <div style="width: 47%;background-color: #d7d7d7;margin-right: 0px;height:25px;;">
                    日期
                  </div>
                  <div style="width: 47%;display: flex;flex-direction: row;justify-content: center;align-items: center;background-color: #f7f6f6;height:25px;;">
                    {{ data.day.split('-').slice(2).join('-') }}
                  </div>
                </div>
                <div style="width: 100%;display: flex;flex-direction: row;align-items: center;margin-bottom: 1px;">
                  <div style="width: 47%;background-color: #d7d7d7;margin-right: 0px;height:25px;;">
                    班次一
                  </div>
                  <div style="width: 47%;display: flex;flex-direction: row;justify-content: center;align-items: center;background-color: #e6e6e6;height:25px;;">
                    {{shiftChangeObj[data.day].firShiftCode}}
                  </div>
                </div>
                <div style="width: 100%;display: flex;flex-direction: row;align-items: center;margin-bottom: 1px;">
                  <div style="width: 47%;background-color: #d7d7d7;margin-right: 0px;height:25px;;">
                    班次二
                  </div>
                  <div style="width: 47%;display: flex;flex-direction: row;justify-content: center;align-items: center;background-color: #f7f6f6;height:25px;;">
                    <input v-model="shiftChangeObj[data.day].secShiftCode" style="width: 80%;height: 18px;"></input>
                  </div>
                </div>
                <div style="width: 100%;display: flex;flex-direction: row;align-items: center;margin-bottom: 1px;">
                  <div style="width: 47%;background-color: #d7d7d7;margin-right: 0px;height:25px;;">
                    连班
                  </div>
                  <div style="width: 47%;display: flex;flex-direction: row;justify-content: center;align-items: center;background-color: #e6e6e6;height:25px;;">
                    <select v-model="shiftChangeObj[data.day].isJoinShift" style="width: 95%">
                      <option v-for="(options,id) in attendenceOptions.JoinShift" :key="options.dicNo" :value="options.dicNo">
                        {{options.dicName}}
                      </option>
                    </select>
                  </div>
                </div>
              </div>
            </template>
          </human-calendar>

        </div>
      </div>
    </el-dialog>
    <select-user ref="select" @ok="getJobNumber"/>
  </div>
</template>

<script>
import humanCalendar from '@/views/components/human/humanCalendar/src/main'
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { listShiftClass } from "@/api/human/hd/shiftClass";
import { listShiftMode  } from '@/api/human/hd/shiftMode'
import { listOrgClassMaster, getOrgClassMaster, delOrgClassMaster, addOrgClassMaster, updateOrgClassMaster } from "@/api/human/hd/orgClassMaster";
import { listPersonClassMaster, getPersonClassMaster, delPersonClassMaster, addPersonClassMaster, updatePersonClassMaster } from "@/api/human/hd/personClassMaster";
import { listPersonClassDetail, changePersonClassDetail, addPersonClassDetail } from "@/api/human/hd/personClassDetail";
import { treeselect,selectCompany,queryFirstdeptByPerson,queryFirstdeptByDept } from "@/api/human/hp/deptMaintenance";
import DictTagHumanBase from "@/views/components/human/dictTag/humanBaseInfo"
import { getAttendenceOptions } from "@/api/human/hd/attendenceBasis";
import { getDateTime } from "@/api/human/hd/ahumanUtils";
import selectUser from "@/views/components/human/selectUser/selectUser";
import '@/assets/styles/humanStyles.scss';
export default {
  name: "OrgPersonClass",
  dicts: ['sys_normal_disable','sys_yes_no'],
  components: {Treeselect,DictTagHumanBase,selectUser,humanCalendar},
  data() {
    return {
      //调班详情数据对象
      shiftChangeObj:{},
      //是否显示调班日历
      showCalendar: false,
      //出勤选单类型查询
      attendenceOptionType:{
        id:'',
        optionsType:['PersonClassType','PersonClassStatus','PersonClassRemark', 'JoinShift']
      },
      //出勤选单选项列表
      attendenceOptions:{},
      //form卡钟类别
      formpersonClassType:null,
      //一级机构信息
      firstDept:{},
      //机构id/员工工号
      empId:null,
      //出勤身份类别
      personClassType:'1',
      //公司选项
      compId:null,
      //公司数据
      companyList:[],
      //登录人信息
      user: {},
      // 遮罩层
      loading: false,
      //部门选单树数据
      deptOptions: [],
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
      // 出勤身份表格数据
      classList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        empId: null,
        deptId: null,
        queryMonth: new Date(),
      },
      //部门选单查询参数
      deptParams:{
        compId:null,
        ifCompany:0,
      },
      // 表单参数
      form: {},
      formDetail: {},
      // 表单校验
      rules: {
        empId: [
          { required: true, message: '员工工号不可为空', trigger: 'blur' },
        ],
      },
      //选单数据
      humanOptions: [],
      //轮班方式列表
      shiftModeList:[],
      //班别列表
      shiftClassList:[],
    };
  },
  watch:{
    personClassType(val){
      this.queryParams.empId=null
        this.queryParams.deptId=null
    },
    'queryParams.compId':{
      deep:true,
      immediate:false,
      handler:function( newV){
        this.getTreeselect();
        this.getShiftList();
      }
    },
    'form.shiftmodeId':{
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
    this.initData();
    this.init();
    this.getCompanyList();
    this.getDisc()
  },
  methods: {
    //初始化数据
    initData() {
      this.user.empNo = this.$store.state.user.name
      this.user.empName = this.$store.state.user.userInfo.nickName
      this.user.compId = this.$store.state.user.userInfo.compId
      this.queryParams.compId = this.user.compId
    },
    //调班数据对象初始化
    init(){
      //大月
      var bigMonth = [1, 3, 5, 7, 8, 10, 12];
      //年份
      var year = '2023'
      for (var i = 1; i <  13; i++) {
        var monthDays ;
        if (bigMonth.indexOf(i) !== -1) {
          monthDays = 32;
        } else if (i === 2) {
          var o = ((year%400 ===0)||(year % 4 === 0 && year%100 !== 0)) ? 1 : 0;
          monthDays = 29+o;
        } else {
          monthDays = 31
        }
        for (var j = 1; j < monthDays; j++) {
          var month = (i<10)? '0'+i:i
          var day = (j<10)? '0'+j:j
          var date = year+'-'+month+'-'+day;
          this.shiftChangeObj[date]={
            id:null,
            firShiftId:null,
            secShiftId:null,
            firShiftCode:null,
            secShiftCode:null,
            isJoinShift:null,
          }
        }
      }
    },
    //调班操作
    shiftChange(){
      var shiftChange = [];
      for(var i in this.shiftChangeObj){
        var obj = this.shiftChangeObj[i]
        if(obj.firShiftCode!=null||obj.secShiftCode!=null|obj.isJoinShift!=null){
          if(obj.id==null){
            obj.compId = this.form.compId;
            obj.personClassMasterId = this.form.id;
            if(this.form.empId!= null){
              obj.classType = 0;
              obj.empId = this.form.empId;
            }else{
              obj.classType = 1;
              obj.deptId = this.form.deptId;
            }
            obj.arrShiDate = i;
          }
          obj.creator = this.user.empName;
          obj.creatorId = this.user.empNo;
          obj.createDate = getDateTime(1);
          shiftChange.push(obj)
        }
      }
      var detailObj = {};
      detailObj.shiftChangeList=shiftChange
      changePersonClassDetail(detailObj).then(response=>{
        this.$modal.msgSuccess("调班成功")
      })
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
        shiftmodeId:this.form.shiftmodeId
      }
      listShiftClass(queryParams).then(response => {
        this.shiftClassList = response.rows;
      });
    },
    //获取出勤字典
    getDisc(){
      getAttendenceOptions(this.attendenceOptionType).then(response=> {
        this.attendenceOptions=response.data;
      })
    },
    /** 人员选单事件 */
    inputClick() {
      var queryParams={
        compID:this.queryParams.compId,
        pageNum: 1,
        pageSize: 10,
      }
      this.$refs.select.show(queryParams);
    },
    /** 获取工号 */
    getJobNumber(val, userName,compId) {
      if(this.open===true){
        this.form.empId = val;
      }else{
        this.queryParams.empId = val;
      }
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      this.deptParams.compId = this.queryParams.compId;
      treeselect(this.deptParams).then(response => {
        this.deptOptions = response.data;
      });
    },
    /** 上级部门切换事件 */
    deptChange(val) {
      queryFirstdeptByDept(val.id).then(response =>{
        this.checkList=[];
        this.form.firstDeptId = response.data.firstDeptId;
        this.form.firstDeptName = response.data.firstDeptName;
      })
    },
    //获取公司列表
    getCompanyList(){
      selectCompany().then(response=>{
        this.companyList = response.data
      })
    },
    //设置表单值
    setForm(e){
      this.form.creator = this.user.empName;
      this.form.creatorId = this.user.empNo;
      this.form.createDate = getDateTime(1);
      this.form.compId = this.queryParams.compId;
      if(e==0){
        this.form.status = 1;
        this.form.remark = 0;
      }
    },
    /** 转换部门数据结构 */
    normalizer(node) {
      return {
        id: node.id,
        label: node.label,
        label2: node.label2,
        children: node.children
      };
    },
    /** 查询人员数据 */
    getList(e){
      if(this.personClassType==="1"){
        if(!this.queryParams.empId){
          if(!e){
            this.$modal.msgWarning("请输入工号");
          }
        }else{
          this.getListPerspn();
        }
      }else if(this.personClassType==="2"){
        if(!this.queryParams.deptId){
          if(!e){
            this.$modal.msgWarning("请选择部门");
          }
        }else{
          this.getListOrg();
        }
      }
    },
    /** 查询人员排班列表 */
    getListPerspn() {
      this.loading = true;
      listPersonClassMaster(this.queryParams).then(response => {
        this.classList = response.rows;
        this.total = response.total;
        this.loading = false;
        if(this.total===0){
          this.$modal.msgWarning("未查询到数据")
        }
      });
    },
    /** 查询组织机构排班列表 */
    getListOrg() {
      this.loading = true;
      listOrgClassMaster(this.queryParams).then(response => {
        this.classList = response.rows;
        this.total = response.total;
        this.loading = false;
        if(this.total===0){
          this.$modal.msgWarning("未查询到数据")
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.showCalendar = false;
      this.form = {
        id: null,
        compId: this.queryParams.compId,
        companyName: this.$refs.selectCompany.selectedLabel,
        deptId:null,
        empId: null,
        orgId: null,
        shiftmodeId: null,
        classId: null,
        startDate: null,
        endDate: null,
        remark: null,
        status: null,
        creator: null,
        creatorId: null,
        createDate: null,
      };
      this.resetForm("form");
      this.formpersonClassType = this.personClassType;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.compId = this.user.compId;
      this.queryParams.empId = null;
      this.queryParams.deptId = null;
      this.personClassType = '1';
      this.empId = null;
      this.classList = [];
      this.total = 0;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    // 轮班方式，班次，班别查看
    runShiftView(){
      this.$router.push("/human/hd/hd01/shiftMode")
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.setForm(0);
      this.title = "添加出勤身份设定";
      this.open = true;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id
      if(this.personClassType==="1"){
        getPersonClassMaster(id).then(response => {
          this.form = response.data;
          var params={
            personClassMasterId:this.form.id
          }
          listPersonClassDetail(params).then(response=>{
            response.rows.forEach((value,index,array)=>{
              this.shiftChangeObj[value.arrShiDate] = value;
            })
            this.open = true;
            this.showCalendar = true;
          })
          this.form.companyName = this.$refs.selectCompany.selectedLabel;
          this.title = "人员调班";
        });
      }else{
        getOrgClassMaster(id).then(response => {
          this.form = response.data;
          var params={
            personClassMasterId:this.form.id
          }
          listPersonClassDetail(params).then(response=>{
            response.rows.forEach((value,index,array)=>{
              this.shiftChangeObj[value.arrShiDate] = value;
            })
            this.open = true;
            this.showCalendar = true;
          })
          this.form.companyName = this.$refs.selectCompany.selectedLabel;
          this.title = "组织机构调班";
          this.open = true;
        });
      }
    },
    /** 提交按钮 */
    submitForm(){
      if(this.formpersonClassType==='1'){
        this.submitFormPerson();
      }else if(this.formpersonClassType==='2'){
        this.submitFormDept();
      }
    },
    /** 提交按钮(人员) */
    submitFormPerson() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePersonClassMaster(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList(1);
            });
          } else {
            addPersonClassMaster(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList(1);
            });
          }
        }
      });
    },
    /** 提交按钮(部门) */
    submitFormDept() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOrgClassMaster(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList(1);
            });
          } else {
            addOrgClassMaster(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList(1);
            });
          }
        }
      });
    },
    /** 删除按钮 */
    handleDelete(row){
      if(this.personClassType==='1'){
        this.handleDeletePerson(row)
      }else if(this.personClassType==='2'){
        this.handleDeleteDept(row)
      }
    },
    /** 删除人员卡钟按钮操作 */
    handleDeletePerson(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除人员卡钟编号为"' + ids + '"的数据项？').then(function() {
        return delPersonClassMaster(ids);
      }).then(() => {
        this.getList(1);
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    handleDeleteDept(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除部门卡钟编号为"' + ids + '"的数据项？').then(function() {
        return delOrgClassMaster(ids);
      }).then(() => {
        this.getList(1);
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>
<style scoped>
</style>
