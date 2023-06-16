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
      <el-form-item label="年份" prop="year">
        <el-date-picker
          v-model="queryParams.yearObj"
          type="year"
          :clearable="false"
          placeholder="选择年">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="员工编码" prop="empNo">
        <el-input maxlength="32"  v-model="queryParams.empNo" placeholder="请输入员工工号" style="width: 200px;">
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
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="multiple"
          @click="handleSave"
          v-hasPermi="['human:yearAmt:save']"
        >保存</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['human:yearAmt:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['human:yearAmt:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['human:yearAmt:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-form ref="form" :model="form" :rules="rules" >
    <el-table v-loading="loading" :data="form.yearAmtList" @selection-change="handleSelectionChange" height="67vh" :row-class-name="addIndex" @row-click="addLine">
      <el-form-item>
        <el-table-column type="selection" width="55" align="center" />
      </el-form-item>
        <el-table-column label="年份" align="center" prop="year" />
      <el-table-column label="工号/姓名" align="center" width="300">
        <template slot-scope="scope">
          <div style="display: flex;flex-direction: row;align-items: center">
            <el-form-item :prop="'yearAmtList.' + scope.$index + '.empNo'" :rules="rules.empNo">
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
      <el-table-column label="是否全年缴税" align="center" prop="ifFullTax">
        <template slot-scope="scope">
          <el-form-item :prop="'yearAmtList.' + scope.$index + '.ifFullTax'" :rules="rules.ifFullTax">
            <el-radio-group v-model="scope.row.ifFullTax" >
              <el-radio
                v-for="dict in salaryOptions.HsYN"
                :key="dict.id"
                :label="dict.dicNo"
              >{{ dict.dicName }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column label="全年累计是否大于6万" align="center" prop="ifIncomeGreaterSix">
        <template slot-scope="scope">
          <el-form-item :prop="'yearAmtList.' + scope.$index + '.ifIncomeGreaterSix'" :rules="rules.ifIncomeGreaterSix">
            <el-radio-group v-model="scope.row.ifIncomeGreaterSix" >
              <el-radio
                v-for="dict in salaryOptions.HsYN"
                :key="dict.id"
                :label="dict.dicNo"
              >{{ dict.dicName }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column label="累计收入" align="center" prop="sumIncome" >
        <template slot-scope="scope">
          <el-form-item :prop="'yearAmtList.' + scope.$index + '.sumIncome'" :rules="rules.sumIncome">
            <el-input  v-model="scope.row.sumIncome" placeholder="" style="width: 150px;"  type="number"/>
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column label="输入人" align="center" prop="creator" />
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
    <!-- 年收入维护资料导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="450px" append-to-body class="customDialogStyle">
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
          <!--          <div class="el-upload__tip" slot="tip">-->
          <!--            <el-checkbox v-model="upload.updateSupport"/>-->
          <!--            是否更新已经存在的用户数据-->
          <!--          </div>-->
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                   @click="importTemplate"
          >下载模板
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
import '@/assets/styles/humanStyles.scss';
import { getToken } from '@/utils/auth'
import { listYearAmt,  delYearAmt,  saveYearAmt } from "@/api/human/hs/yearAmt";
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getSalaryOptions, getSalaryDeepOptions } from "@/api/human/hs/salaryBasis";
import selectUser from "@/views/components/human/selectUser/selectUser";
export default {
  name: "YearAmt",
  components: {selectUser},
  data() {
    return {
      // 年收入维护资料导入参数
      upload: {
        // 是否显示弹出层（年收入维护资料导入）
        open: false,
        // 弹出层标题（年收入维护资料导入）
        title: '',
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: 'Bearer ' + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + '/human/yearAmt/importData'
      },
      //登录用户信息
      user:{},
      //公司数据
      companyList: [],
      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['HsYN'],
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
      // 年收入维护表格数据
      yearAmtList: [],
      //表单参数
      form:{yearAmtList: []},
      // 返回员工信息index
      index:null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        empNo: null,
        yearObj: new Date(),
        year: new Date().getFullYear(),
      },
      //选中项数据
      multipleSelection: [],
      // 年份
      // 表单校验
      rules: {
        empNo:[
          {required: true, message: '请选择员工', trigger: 'change'},
        ],
        ifFullTax:[
          {required: true, message: '请选择', trigger: 'change'},
        ],
        ifIncomeGreaterSix:[
          {required: true, message: '请选择', trigger: 'change'},
        ],
        sumIncome:[
          {required: true, message: '请输入累计收入', trigger: 'change'},
        ],
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
    'queryParams.yearObj':{
      handler(val) {
        if(typeof val=='object'&&val!=null){
          this.queryParams.year=val.getFullYear()
        }
        this.getList();
      },
      deep: true,
      immediate: false,
    }
  },
  created() {
    this.initData();
    this.getCompanyList();
    this.getDisc();
  },
  methods: {
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
      this.queryParams.compId = this.user.compId;
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
    getJobNumber(userNo, userName,compId,userId) {
      if(this.index!=null){
        this.form.yearAmtList[this.index].empNo = userNo;
        this.form.yearAmtList[this.index].empName = userName;
        this.form.yearAmtList[this.index].empId = userId;
      }else{
        this.queryParams.empNo = userNo;
      }
      this.index = null;
    },
    /** 保存按钮操作 */
    handleSave(row) {
      this.form.yearAmtList = this.multipleSelection;
      this.$nextTick(()=>{
        this.$refs["form"].validate(valid => {
          if (valid) {
            saveYearAmt(this.form.yearAmtList).then( response=>{
              this.$modal.msgSuccess('保存成功')
              this.getList()
            })
          }
        });
      })
    },
    //添加行index
    addIndex({ row, rowIndex }) {
      row.index = rowIndex
    },
    /** 查询年收入维护列表 */
    getList() {
      this.loading = true;
      listYearAmt(this.queryParams).then(response => {
        this.form.yearAmtList = response.rows;
        this.total = response.total;
        this.loading = false;
        if(this.form.yearAmtList.length==0){
          this.addLine();
        }
      });
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      if (!row||this.form.yearAmtList.length === row.index + 1) {
        const newLine = {
          id: null,
          compId: this.queryParams.compId,
          empId: null,
          empNo: null,
          empName: null,
          year: this.queryParams.year,
          ifFullTax: null,
          ifIncomeGreaterSix: null,
          sumIncome: null,
          creator: null,
          creatorId: null,
          creatorNo: null,
          createDate: null,
        }
        this.form.yearAmtList.push(newLine)
      }
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.year = new Date().getFullYear();
      this.queryParams.yearObj = new Date();
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
      this.multipleSelection = selection;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除所选中的数据项？').then(function() {
        return delYearAmt(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/yearAmt/export', {
        ...this.queryParams
      }, `yearAmt_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = '年收入维护资料导入'
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('human/yearAmt/importTemplate', {}, `yearAmt_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$alert('<div style=\'overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;\'>' + response.msg + '</div>', '导入结果', { dangerouslyUseHTMLString: true })
      this.getList()
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit()
    },
  }
};
</script>
<style scoped>
/deep/.el-form-item__error {
  position: relative;
}
</style>
