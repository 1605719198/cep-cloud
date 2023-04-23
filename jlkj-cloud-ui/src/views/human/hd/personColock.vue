<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" >
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
      <el-form-item  prop="type">
        <el-select v-model="colockType" placeholder="请选择卡钟设定类型" clearable>
          <el-option
            v-for="dict in attendenceOptions.ColockType"
            :key="dict.dicNo"
            :label="dict.dicName"
            :value="dict.dicNo"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="" prop="empId" v-if="this.colockType==2">
        <treeselect v-model="queryParams.deptId" :options="deptOptions" :show-count="true" placeholder="请选择机构"  style="width: 200px;"/>
      </el-form-item>
      <el-form-item label="" prop="empId" v-if="this.colockType==1">
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
          v-hasPermi="['human:personColock:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleExport"
          v-hasPermi="['human:personColock:export']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['human:personColock:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ColockList" @selection-change="handleSelectionChange" height="67vh" v-if="this.colockType!=null">
      <el-table-column label="工号" align="center" prop="empId" v-if="this.colockType==1"/>
      <el-table-column label="机构id" align="center" prop="deptId" v-else-if="this.colockType==2" />
      <el-table-column label="一级机构" align="center" prop="firstDeptName" />
      <el-table-column label="生效日期" align="center" prop="effectDate" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.effectDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否刷卡" align="center" prop="checkcard" >
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.checkcard"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" >
        <template v-slot="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
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
            v-hasPermi="['human:personColock:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-error"
            @click="obsolete(scope.row)"
            v-hasPermi="['human:personColock:obsolete']"
          >作废</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:personColock:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-if="this.colockType!=null"
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改人员卡钟对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-row :gutter="20">
          <el-col :span="10" style="display: flex;flex-direction: row" v-if="this.form.id==null">
            <el-form-item  prop="type" label="卡钟类型" style="width: 260px;">
              <el-select v-model="formcolockType" placeholder="请选择卡钟设定类型" clearable>
                <el-option
                  v-for="dict in attendenceOptions.ColockType"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="" prop="deptId" v-if="this.formcolockType==2" label-width="10px">
              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择机构" :normalizer="normalizer" @select="deptChange" style="width: 200px;"/>
            </el-form-item>
            <el-form-item label="" prop="empId" v-if="this.formcolockType==1" label-width="10px" style="width: 200px;" >
              <el-input maxlength="20"  v-model="form.empId" placeholder="请选择员工工号" disabled>
                <el-button slot="append" icon="el-icon-search" @click="inputClick()"></el-button>
              </el-input>
            </el-form-item>
          </el-col>

          <el-col :span="10" style="display: flex;flex-direction: row" v-if="this.form.id!=null">
            <el-form-item  prop="type" label="卡钟类型" style="width: 260px;" >
              <dict-tag-human-base :options="attendenceOptions.ColockType" :value="formcolockType"/>
            </el-form-item>
            <el-form-item label="机构ID"   v-if="this.formcolockType==2"  style="width: 200px;" >
              {{form.deptId}}
            </el-form-item>
            <el-form-item label="员工工号"  v-if="this.formcolockType==1"  style="width: 200px;" >
              {{form.empId}}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="是否刷卡" prop="checkcard">
              <el-radio-group v-model="form.checkcard"   >
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="生效日期" prop="effectDate">
              <el-date-picker clearable
                              v-model="form.effectDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择生效日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="10" style="display: flex;flex-direction: row">
            <el-form-item  prop="type" label="一级单位" style="width: 240px;">
              {{this.form.firstDeptName}}
            </el-form-item>
          </el-col>
        </el-row>
        <div v-if="this.form.checkcard=='Y'">
          <el-divider content-position="center" >刷卡地点清单</el-divider>
        </div>
        <div v-if="this.form.checkcard=='Y'">
          <el-checkbox-group v-model="checkList">
            <el-checkbox v-for="(clock,index) in clockworkList " :label="clock.code">{{ clock.name }}</el-checkbox>
          </el-checkbox-group>
        </div>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <select-user ref="select" @ok="getJobNumber"/>
  </div>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { listPersonColock, getPersonColock, delPersonColock, addPersonColock, updatePersonColock } from "@/api/human/hd/personColock";
import { listPersonColockOrg, getPersonColockOrg, delPersonColockOrg, addPersonColockOrg, updatePersonColockOrg } from "@/api/human/hd/personColockOrg";
import { listPersonColockDetail} from "@/api/human/hd/personColockDetail";
import { treeselect,selectCompany,queryFirstdeptByPerson,queryFirstdeptByDept } from "@/api/human/hp/deptMaintenance";
import DictTagHumanBase from "@/views/components/human/dictTag/humanBaseInfo"
import { getAttendenceOptions } from "@/api/human/hd/attendenceBasis";
import { getDateTime } from "@/api/human/hd/ahumanutils";
import { getCompClockwork } from "@/api/human/hd/clockwork";
import selectUser from "@/views/components/human/selectUser/selectUser";
export default {
  name: "PersonColock",
  dicts: ['sys_normal_disable','sys_yes_no'],
  components: {Treeselect,DictTagHumanBase,selectUser},
  data() {
    return {
      //出勤选单类型查询
      attendenceOptionType:{
        id:'',
        optionsType:['ColockType','HD001']
      },
      //出勤选单选项列表
      attendenceOptions:{},
      //form卡钟类别
      formcolockType:null,
      //公司卡钟数据
      clockworkList:[],
      //多选框数据
      checkList:[],
      //一级机构信息
      firstDept:{},
      //机构id/员工工号
      empId:null,
      //公司选项
      compId:null,
      //公司数据
      companyList:[],
      //卡钟类别
      colockType:'1',
      //登录人工号
      userEmpId: this.$store.state.user.name,
      //登录人姓名
      nickName: this.$store.state.user.userInfo.nickName,
      //登录人公司
      logincompId: this.$store.state.user.userInfo.compId,
      // 遮罩层
      loading: true,
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
      // 人员卡钟表格数据
      ColockList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
        empId: null,
        deptId:null,
      },
      //部门选单查询参数
      deptParams:{
        compId:null,
        ifCompany:0,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        empId: [
          { required: true, message: '员工工号不可为空', trigger: 'blur' },
        ],
        deptId:[
          { required: true, message: '部门不可为空', trigger: 'blur' },
        ],
        checkcard: [
          { required: true, message: '是否打卡不可为空', trigger: 'change' },
        ],
        effectDate: [
          { required: true, message: '请选择生效日期', trigger: 'change' },
        ]
      },
      //选单数据
      humanOptions: [],
    };
  },
  watch:{
    colockType(val){
      this.queryParams.empId=null,
        this.queryParams.deptId=null,
        this.getList();
    },
    'queryParams.compId':{
      deep:true,
      immediate:true,
      handler:function( newV){
        this.getList();
        this.getTreeselect();
      }
    },
  },
  created() {
    this.getDisc();
    this.getCompanyList();
    this.getList();
  },
  methods: {
    //获取出勤字典(假别类型查询)
    getDisc(){
      getAttendenceOptions(this.attendenceOptionType).then(response=> {
        this.attendenceOptions=response.data;
      })
    },
    //获取公司卡钟设定
    getAllcolock(compId){
      getCompClockwork(compId).then(response => {
        this.clockworkList = response.rows;
        this.open = true;
      });
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
      if(this.open == false){
        this.queryParams.empId = val;
        this.getList();
      }else {
        this.form.empId = val;
        queryFirstdeptByPerson(val).then(response =>{
          this.checkList=[];
          this.form.firstDeptId = response.data.firstDeptId;
          this.form.firstDeptName = response.data.firstDeptName;
          this.clockworkList.forEach((value,index,array)=>{
            if (value.firstDeptId){
              var array= value.firstDeptId.split(',')
              array.forEach((values,indexs,arrays)=>{
                if(values==this.form.firstDeptId&&this.form.firstDeptId!=null){
                  this.checkList.push(value.code);
                }
              })
            }
          })
        })
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
        this.clockworkList.forEach((value,index,array)=>{
          if (value.firstDeptId){
            var array= value.firstDeptId.split(',')
            array.forEach((values,indexs,arrays)=>{
              if(values==this.form.firstDeptId&&this.form.firstDeptId!=null){
                this.checkList.push(value.code);
              }
            })
          }
        })
      })
    },
    //获取公司列表
    getCompanyList(){
      selectCompany().then(response=>{
        this.companyList = response.data
      })
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
    getList(){
      if(this.colockType==1){
        this.getListPerspn();
      }else if(this.colockType==2){
        this.getListOrg();
      }
    },
    /** 查询人员卡钟列表 */
    getListPerspn() {
      this.loading = true;
      listPersonColock(this.queryParams).then(response => {
        this.ColockList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询组织机构卡钟列表 */
    getListOrg() {
      this.loading = true;
      listPersonColockOrg(this.queryParams).then(response => {
        this.ColockList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        deptId:null,
        empId: null,
        org_colock_id:null,
        checkcard: null,
        effectDate: null,
        status: null,
        creator: null,
        creatorId: null,
        createDate: null,
        colockList:null,
        firstDeptName: null,
        firstDeptId: null,
      };
      this.resetForm("form");
      this.checkList = [];
      this.formcolockType = this.colockType;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.compId = this.logincompId;
      this.queryParams.empId = null;
      this.queryParams.deptId = null;
      this.colockType = '1';
      this.empId = null;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form.effectDate = getDateTime(1);
      this.title = "添加卡钟";
      this.getAllcolock(this.queryParams.compId);
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      if(this.colockType==1){
        getPersonColock(id).then(response => {
          this.form = response.data;
          this.title = "修改人员卡钟";
          this.getAllcolock(this.queryParams.compId);
        });
        var params={ personColockId:id }
        listPersonColockDetail(params).then(response => {
          response.rows.forEach((value)=>{
            this.checkList.push(value.macId);
          })
        })
      }else{
        getPersonColockOrg(id).then(response => {
          this.form = response.data;
          this.title = "修改部门卡钟";
          this.getAllcolock(this.queryParams.compId);
        });
        var params={ personColockId:id }
        listPersonColockDetail(params).then(response => {
          response.rows.forEach((value)=>{
            this.checkList.push(value.macId);
          })
        })
      }
    },
    //作废按钮操作
    obsolete(row){
      var obj = JSON.parse(JSON.stringify(row))
      var effectDate = new Date(row.effectDate)
      var nowDate = new Date(getDateTime(1))
      if(effectDate.getTime() >nowDate.getTime()){
        this.handleDelete(row);
      }else{
        obj.status = '1';
        obj.colockList = [];
        if(this.colockType==1){
          updatePersonColock(obj).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
        }else if(this.colockType==2){
          updatePersonColockOrg(obj).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
        }
      }
    },
    /** 提交按钮 */
    submitForm(){
      this.form.creatorId = this.$store.state.user.name;
      this.form.creator = this.nickName;
      this.form.createDate = getDateTime(1);
      if(this.form.checkcard=='Y'){
        this.form.colockList = this.checkList;
      }else{
        this.form.colockList = []
      }
      if(this.formcolockType==1){
        this.submitFormPerson();
      }else if(this.formcolockType==2){
        this.submitFormDept();
      }
    },
    /** 提交按钮(人员) */
    submitFormPerson() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePersonColock(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.status = '0',
              this.form.compId = this.queryParams.compId;
            addPersonColock(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
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
            updatePersonColockOrg(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.status = '0',
              this.form.compId = this.queryParams.compId;
            addPersonColockOrg(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮 */
    handleDelete(row){
      if(this.colockType==1){
        this.handleDeletePerson(row)
      }else if(this.colockType==2){
        this.handleDeleteDept(row)
      }
    },
    /** 删除人员卡钟按钮操作 */
    handleDeletePerson(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除人员卡钟编号为"' + ids + '"的数据项？').then(function() {
        return delPersonColock(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    handleDeleteDept(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除部门卡钟编号为"' + ids + '"的数据项？').then(function() {
        return delPersonColockOrg(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      // this.download('human/personColock/export', {
      //   ...this.queryParams
      // }, `personColock_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
