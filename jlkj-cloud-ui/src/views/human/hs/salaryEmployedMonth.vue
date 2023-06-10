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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-hasPermi="['human:salaryEmployedMonth:add']" icon="el-icon-edit" type="primary" size="mini" plain :disabled="multiple" @click="handleSave">保存</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['human:salaryEmployedMonth:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-form ref="form" :model="form" >
    <el-table v-loading="loading" :row-class-name="addIndex" :data="form.salaryEmployedMonthList" @selection-change="handleSelectionChange" @row-click="addLine">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工号/姓名" align="center" width="300">
        <template slot-scope="scope">
          <div style="display: flex;flex-direction: row;align-items: center">
            <el-form-item :prop="'salaryEmployedMonthList.' + scope.$index + '.empNo'">
              <el-input maxlength="32"  v-model="scope.row.empNo" placeholder="员工工号" style="width: 150px;" disabled>
                <el-button slot="append" icon="el-icon-search" @click="inputClick(scope.row)"></el-button>
              </el-input>
            </el-form-item>
            <el-form-item>
              <div style="width: 80px;">{{scope.row.empName}}</div>
            </el-form-item>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="主岗位" align="center" prop="post" />
      <el-table-column label="受雇月" align="center" prop="entDate" width="180">
        <template slot-scope="scope">
          <el-date-picker
            v-model="scope.row.entDate"
            value-format="yyyy-MM"
            type="month"
            placeholder="选择日期">
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column label="薪资月" align="center" prop="salaryDate" width="180">
        <template slot-scope="scope">
          <el-date-picker
            v-model="scope.row.salaryDate"
            value-format="yyyy-MM"
            type="month"
            placeholder="选择日期">
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column label="简要说明" align="center" prop="descript" >
        <template v-slot="scope">
          <el-input v-model="scope.row.descript" placeholder="请输入内容"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" >
        <template v-slot="scope">
          <el-radio-group v-model="scope.row.status" >
            <el-radio
              v-for="dict in salaryOptions.status"
              :key="dict.dicNo"
              :label="dict.dicNo"
            >{{dict.dicName}}</el-radio>
          </el-radio-group>
        </template>
      </el-table-column>
      <el-table-column label="输入人" align="center" prop="creator" />
<!--      <el-table-column label="输入人工号" align="center" prop="creatorNo" />-->
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
    </el-table>
    </el-form>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <select-user ref="select" @ok="getJobNumber"/>

    <!-- 员工受雇月维护资料导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                   @click="importTemplate">下载模板
          </el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSalaryEmployedMonth, getSalaryEmployedMonth, addSalaryEmployedMonth, } from "@/api/human/hs/salaryEmployedMonth";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import selectUser from "@/views/components/human/selectUser/selectUser";
import {getSalaryOptions} from "@/api/human/hs/salaryBasis";
import {getDateTime} from "@/api/human/hd/ahumanUtils";
import {getToken} from "@/utils/auth";
import {queryNewPostNameAndChangeDetail} from "@/api/human/hm/employeeTurnover";
import '@/assets/styles/humanStyles.scss';

export default {
  name: "SalaryEmployedMonth",
  dicts: ['sys_classtype'],
  components:{selectUser},
  data() {
    return {
      //薪资选单选项列表
      salaryOptions: [],
      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['status'],
        compId:null,
      },
      // 返回员工信息index
      index:null,
      //公司列表
      companyList:[],
      multipleSelection: [],
      //登录人姓名
      nickName: null,
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
      // 员工受雇月维护表格数据
      salaryEmployedMonthList: [],
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
      },
      // 员工受雇月维护资料导入参数
      upload: {
        // 是否显示弹出层（员工受雇月维护资料导入）
        open: false,
        // 弹出层标题（员工受雇月维护资料导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/human/salaryEmployedMonth/importData"
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        empNo:[
          {required: true, message: '请选择员工', trigger: 'change'},
        ],
      }
    };
  },
  created() {
    this.initData();
    this.getCompanyList();
    this.getDisc();
  },
  methods: {
    /** 查询公司列表 */
    getCompanyList(){
      selectCompany().then(response =>{
          this.companyList = response.data
        }
      )
    },
    /** 查询员工受雇月维护列表 */
    getList() {
      this.loading = true;
      listSalaryEmployedMonth(this.queryParams).then(response => {
        this.form.salaryEmployedMonthList = response.rows;
        this.addLine();
        this.total = response.total;
        this.loading = false;
      });
    },
    //初始化数据
    initData(){
      this.userEmpId= this.$store.state.user.name;
      this.empName = this.$store.state.user.userInfo.userName;
      this.nickName= this.$store.state.user.userInfo.nickName;
      this.logincompId= this.$store.state.user.userInfo.compId;
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        empId: null,
        empNo: null,
        post: null,
        entDate: null,
        salaryDate: null,
        descript: null,
        status: null,
        creator: null,
        creatorId: null,
        creatorNo: null,
        createDate: null
      };
      this.resetForm("form");
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "员工受雇月维护资料导入";
      this.upload.open = true;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSalaryEmployedMonth(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSalaryEmployedMonth(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multipleSelection = selection;
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    //查询薪资选单
    getDisc(){
      this.salaryOptionType.compId = null
      getSalaryOptions(this.salaryOptionType).then(response=>{
        this.salaryOptions = response.data;
      })
    },
    /** 保存按钮 */
    handleSave() {
      addSalaryEmployedMonth(this.multipleSelection).then(res => {
        this.$modal.msgSuccess("保存成功");
        this.getList();
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSalaryEmployedMonth(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工受雇月维护";
      });
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
    addIndex({row, rowIndex}) {
      row.index = rowIndex
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      if (!row||this.form.salaryEmployedMonthList.length == row.index + 1) {
        const newLine = {
          uuid: null,
          creator: this.nickName,
          creatorId: this.$store.state.user.name,
          createDate: getDateTime(1),
          status: "1",
          empName: null,
          compId: this.queryParams.compId
        }
        this.form.salaryEmployedMonthList.push(newLine)
      }
    },
    /** 人员选单事件 */
    inputClick(row) {
      if(row){
        this.index = row.index;
      }
      var queryParams={
        compID:this.queryParams.compId,
        pageNum: 1,
        pageSize: 10,
      }
      this.$refs.select.show(queryParams);
    },
    /** 获取工号 */
    getJobNumber(empId,userName,compId) {
      if(this.index!=null){
        this.form.salaryEmployedMonthList[this.index].empNo = empId;
        this.form.salaryEmployedMonthList[this.index].empName = userName;
        queryNewPostNameAndChangeDetail(this.form.salaryEmployedMonthList[this.index]).then(res => {
          this.form.salaryEmployedMonthList[this.index].post = res.data.list1[0].newPostName
          this.index =null;
        })
      }else{
        this.queryParams.empNo = empId;
        this.index =null;
      }
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('human/salaryEmployedMonth/importTemplate', {}, `salaryEmployedMonth_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
  }
};
</script>
