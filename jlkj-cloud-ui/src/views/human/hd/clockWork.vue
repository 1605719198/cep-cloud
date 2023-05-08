<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" >
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别" size="small" ref="selectComp">
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
          @click="handleAdd"
          v-hasPermi="['human:clockwork:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="clockworkList" @selection-change="handleSelectionChange" height="67vh">
      <el-table-column label="刷卡钟编码" align="center" prop="code" width="120" sortable/>
      <el-table-column label="刷卡钟名称" align="center" prop="name" width="180" sortable/>
      <el-table-column label="一级机构" align="center" prop="firstDeptName" show-overflow-tooltip width="300" sortable/>
      <el-table-column label="备注" align="center" prop="note" show-overflow-tooltip sortable/>
      <el-table-column label="输入人" align="center" prop="creator" width="120" sortable/>
      <el-table-column label="输入日期" align="center" prop="createDate" width="180" sortable>
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:clockwork:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:clockwork:remove']"
          >删除</el-button>
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

    <!-- 添加或修改公司卡钟设定对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body class="customDialogStyle">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司别" prop="compId">
              {{form.compName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="一级机构" prop="leader">
              <el-input   v-model="form.deptName"   disabled>
                <el-button slot="append" icon="el-icon-search" @click="inputClick()"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="刷卡钟编码" prop="code">
              <el-input v-model="form.code" placeholder="请输入编码" maxlength="10" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="刷卡钟名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入名称" maxlength="200" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="note" >
          <el-input v-model="form.note" type="textarea" show-word-limit :autosize="{ minRows: 2, maxRows: 6 }" placeholder="请输入内容" maxlength="500" />
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="输入人" prop="creator">
              {{form.creator}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="输入日期" prop="createDate" >
              {{form.createDate}}
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <select-dept ref="select" @ok="getJobNumber"></select-dept>
  </div>
</template>

<script>
import { getDateTime } from "@/api/human/hd/ahumanUtils"
import { selectCompany } from "@/api/human/hp/deptMaintenance";
import { getAvatorByUserName} from "@/api/system/user";
import { listClockwork, getClockwork, delClockwork, addClockwork, updateClockwork } from "@/api/human/hd/clockwork";
import selectDept from "@/views/components/human/selectView/selectDept";
import DictTagHuman from '@/views/components/human/dictTag/humanBaseInfo'
export default {
  name: "Clockwork",
  components: {selectDept,DictTagHuman},
  data() {
    return {
      //公司数据
      companyList:[],
      //一级部门选单部门号
      firstDeptId:'',
      //登录人信息
      user: {
        empNo: null,
        empName: null,
        compId: null
      },
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
      // 公司卡钟设定表格数据
      clockworkList: [],
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
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        compId: [
          { required: true, message: '请选择公司别', trigger: 'change' },
        ],
        code: [
          { required: true, message: '请输入刷卡钟编码', trigger: 'blur' },
        ],
        name: [
          { required: true, message: '请输入刷卡钟名称', trigger: 'blur' },
        ],
        firstDeptName:[
          { required: true, message: '请选择一级单位', trigger: 'change' },
        ]
      }
    };
  },
  watch: {
    'queryParams.compId'(val) {
      this.getList();
    }
  },
  created() {
    this.getCompanyList();
    this.initData();
  },
  methods: {
    //获取公司列表
    getCompanyList(){
      selectCompany().then(response=>{
        this.companyList = response.data
      })
    },
    /** 一级部门选单事件 */
    inputClick() {
      if(this.form.compId!=null){
        this.$refs.select.show(this.form.compId);
      }else{
        this.$modal.msgWarning("请先选择公司别");
      }
    },
    /** 点选获取部门信息 */
    getJobNumber(val) {
      var firstDeptId = [];
      var firstDeptName = '' ;
      val.forEach((value,index,array)=>{
        firstDeptId.push(value.deptId);
        if(firstDeptName==''){
          firstDeptName+=(value.deptName)
        }else{
          firstDeptName+=(','+value.deptName)
        }
        this.form.firstDeptName = firstDeptName;
        if(this.form.firstDeptName.length>10){
          this.form.deptName = this.form.firstDeptName.substring(0,10)+'...'
        }else{
          this.form.deptName = this.form.firstDeptName
        }
        this.form.firstDeptId = firstDeptId.toString()
      })
    },
    //初始化数据
    initData() {
      this.user.empNo = this.$store.state.user.name
      this.user.empName = this.$store.state.user.userInfo.nickName
      this.user.compId = this.$store.state.user.userInfo.compId
      this.queryParams.compId = this.user.compId
    },
    //配置表单数据
    setForm(e){
        this.form.creator = this.user.empName;
        this.form.creatorId = this.user.empNo;
        this.form.createDate = getDateTime(1);
        this.form.compName = this.$refs.selectComp.selectedLabel;
        this.form.compId = this.queryParams.compId;
        if(e==1){
          if(this.form.firstDeptName.length>10){
            this.form.deptName = this.form.firstDeptName.substring(0,10)+'...'
          }else{
            this.form.deptName = this.form.firstDeptName
          }

        }
    },
    /** 查询公司卡钟设定列表 */
    getList() {
      this.loading = true;
      listClockwork(this.queryParams).then(response => {
        this.clockworkList = response.rows;
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
        code: null,
        name: null,
        compId: null,
        compName: null,
        firstDeptId: null,
        firstDeptName: null,
        deptName: null,
        note: null,
        creator: null,
        creatorId: null,
        createDate: null
      };
      this.resetForm("form");
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.setForm(0)
      this.open = true;
      this.title = "添加公司卡钟设定";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getClockwork(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.setForm(1)
        this.title = "修改公司卡钟设定";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateClockwork(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addClockwork(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除公司卡钟编码为"' + row.code + '"的数据项？').then(function() {
        return delClockwork(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>
<style scoped>
.maxWidth{
  width: 100%;
}
</style>
