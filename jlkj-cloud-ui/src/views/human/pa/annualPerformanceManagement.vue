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
          <el-form-item label="考核年" prop="year">
            <el-date-picker
              v-model="queryParams.year"
              type="year"
              value-format="yyyy"
              placeholder="选择年">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="员工编码">
            <el-input v-model="queryParams.empName" disabled style="width: 87%">
              <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item label="组织机构">
            <el-input v-model="queryParams.dept" disabled style="width: 87%">
              <el-button slot="append" icon="el-icon-search" @click="openOrgPop"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['human:annualPerformanceManagement:import']"
            >导入</el-button>
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
              v-hasPermi="['human:annualPerformanceManagement:add']"
            >新增
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-right"
              size="mini"
              @click="handleSend"
              v-if="showSend"
              v-hasPermi="['human:annualPerformanceManagement:send']"
            >呈送</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-edit"
              size="mini"
              v-if="showCheck"
              @click="handleFinalCheck"
              v-hasPermi="['human:annualPerformanceManagement:check']"
            >复核
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="yearPerformanceList">
          <el-table-column type="index" label="序号" width="50" align="center"/>
          <el-table-column label="考评年度" align="center" prop="year"/>
          <el-table-column label="职工编号/姓名" align="center" prop="empId" :formatter="userFormat"/>
          <el-table-column label="单位" align="center" prop="dept"/>
          <el-table-column label="年度绩效" align="center" prop="yearScore"/>
          <el-table-column label="年度等第" align="center" prop="grade">
            <template v-slot="scope">
              <dict-tag-human :options="performanceOptions.Grade" :value="scope.row.grade"/>
            </template>
          </el-table-column>
          <el-table-column label="输入人" align="center" prop="creator"/>
          <el-table-column label="输入日期" align="center" prop="createDate">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="复核人" align="center" prop="admEmpId"/>
          <el-table-column label="状态" align="center" prop="status">
            <template v-slot="scope">
              <dict-tag-human :options="performanceOptions.Status" :value="scope.row.status"/>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template v-slot="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit-outline"
                :disabled="scope.row.status !== '1'"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['human:annualPerformanceManagement:edit']"
              >编辑</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-document-checked"
                :disabled="scope.row.status !== '2'"
                @click="handleCheck(scope.row)"
                v-hasPermi="['human:annualPerformanceManagement:check']"
              >复核</el-button>
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

        <!-- 添加年度绩效对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="职工编号">
                  <el-input v-model="form.empName" placeholder="请输入职工编号" :disabled="true">
                    <el-button slot="append" icon="el-icon-search" @click="inputClick1"></el-button>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="单位" prop="dept">
                  {{form.dept}}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="考评年度" prop="year">
                  <el-date-picker
                    v-model="form.year"
                    value-format="yyyy"
                    type="year"
                    style="width: 100%"
                    placeholder="选择年">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="年度绩效成绩" prop="yearScore">
                  <el-input v-model="form.yearScore" placeholder="请输入年度绩效成绩" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="年度绩效等第" prop="grade">
                  <el-select v-model="form.grade" style="width: 100%">
                    <el-option
                      v-for="dict in performanceOptions.Grade"
                      :key="dict.dicNo"
                      :label="dict.dicNo + '-' + dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>

        <!-- 修改年度绩效对话框 -->
        <el-dialog :title="title1" :visible.sync="open1" width="650px" append-to-body>
          <el-form ref="form1" :model="form1" :rules="rules" label-width="100px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="职工编号">
                  <el-input v-model="form1.empId" placeholder="请输入职工编号" :disabled="true">
                    <el-button slot="append" icon="el-icon-search" @click="inputClick1"></el-button>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="单位" prop="dept">
                  {{form1.dept}}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="考评年度" prop="year">
                  <el-date-picker
                    v-model="form1.year"
                    :disabled="showYear"
                    value-format="yyyy"
                    type="year"
                    style="width: 100%"
                    placeholder="选择年">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="年度绩效成绩" prop="yearScore">
                  <el-input v-model="form1.yearScore" placeholder="请输入年度绩效成绩" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="年度绩效等第" prop="grade">
                  <el-select v-model="form1.grade" style="width: 100%">
                    <el-option
                      v-for="dict in performanceOptions.Grade"
                      :key="dict.dicNo"
                      :label="dict.dicNo + '-' + dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="状态" prop="status">
                  <dict-tag-human :options="performanceOptions.Status" :value="form1.status"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="输入人" prop="creator">
                  {{form1.creator}}
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="输入日期" prop="createDate">
                  {{form1.createDate}}
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm1">确 定</el-button>
            <el-button @click="cancel1">取 消</el-button>
          </div>
        </el-dialog>

        <!-- 年度绩效导入对话框 -->
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
              <div class="el-upload__tip" slot="tip">
                <el-checkbox v-model="upload.updateSupport"/>
                是否更新已经存在的年度绩效数据
              </div>
              <span>仅允许导入xls、xlsx格式文件。</span>
              <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                       @click="importTemplate">下载年度绩效导入模板
              </el-link>
            </div>
          </el-upload>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitFileForm">确 定</el-button>
            <el-button @click="upload.open = false">取 消</el-button>
          </div>
        </el-dialog>
        <select-user ref="select" @ok="getEmpId"/>
        <select-user ref="select1" @ok="getEmpId1"/>
        <select-org ref="selectOrg" @ok="getOrg"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import selectUser from "@/views/components/human/selectUser/selectUser";
import selectOrg from "@/views/components/human/selectUser/selectOrgPerson";
import {getPerformanceOptions} from "@/api/human/pa/basis";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";
import {queryAllUser} from "@/api/system/user";
import {
  addYearPerformance, finalCheckYearPerformance,
  listYearPerformanceManagement,
  sendYearPerformance,
  updateYearPerformance
} from "@/api/human/pa/yearPerformance";
import {queryInfo} from "@/api/human/hm/personnelBasicInfo";
import {getToken} from "@/utils/auth";

export default {
  name: "AnnualPerformanceManagement",
  components: { selectUser,selectOrg,DictTagHuman },
  data() {
    return {
      // 遮罩层
      loading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        year: null,
        dept: null,
        empId: null,
        empName: null
      },
      // 年度绩效主档表格数据
      yearPerformanceList: [],
      // 公司别下拉选单
      companyName: [],
      //绩效选单选项列表
      performanceOptions:{},
      //绩效选单类型查询
      performanceOptionType:{
        id:'',
        optionsType:[
          'MeritType',
          'Status',
          'Grade']
      },
      // 弹出层标题
      title: "",
      // 是否显示编辑弹出层
      open: false,
      // 编辑弹出层标题
      title1: "",
      // 是否显示弹出层
      open1: false,
      // 表单参数
      form: {},
      // 修改表单参数
      form1: {},
      // 表单校验
      rules: {},
      // 呈送表单
      sendForm: {},
      // 是否显示呈送按钮
      showSend: false,
      // 是否显示复核按钮
      showCheck: false,
      // 是否可编辑年度
      showYear: false,
      sendCount: 0,
      // 年度绩效导入参数
      upload: {
        // 是否显示弹出层（年度绩效导入）
        open: false,
        // 弹出层标题（年度绩效导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/human/yearPerformance/importData"
      },
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
    // 人员字典翻译
    userFormat1(data) {
      /** UserDictFullName 显示工号_姓名 */
      return this.UserDictFullName(this.resUserDiction, data);
    },
    getDisc(){
      getPerformanceOptions(this.performanceOptionType).then(response=> {
        this.performanceOptions=response.data;
      })
    },
    //初始化数据
    initData(){
      this.queryParams.compId = this.$store.state.user.userInfo.compId;
    },
    /** 查询年度绩效列表 */
    getList() {
      this.loading = true;
      this.sendCount = 0
      listYearPerformanceManagement(this.queryParams).then(response => {
        this.yearPerformanceList = response.data.rows;
        for (const item of this.yearPerformanceList) {
          if (item.status === '1'){
            this.sendCount++
          } else {
            this.sendCount--
          }
        }
        this.total = response.data.total;
        if (this.sendCount === this.total){
          this.showSend = true
          this.showCheck = false
        } else {
          this.showSend = false
          this.showCheck = true
        }
        this.loading = false;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        empId: null,
        dept: null,
        year: new Date().getFullYear().toString(),
        yearScore: null,
        grade: null
      };
      this.resetForm("form");
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增窗口";
    },
    /** 编辑按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.form1 = row
      this.open1 = true;
      this.title1 = "编辑窗口";
    },
    /** 复核按钮操作 */
    handleCheck(row) {
      this.reset();
      this.form1 = row
      this.showYear = true
      this.open1 = true;
      this.title1 = "复核窗口";
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 获取员工姓名点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取员工姓名点击事件 */
    inputClick1() {
      this.$refs.select1.show();
    },
    /** 获取工号 */
    getEmpId(val) {
      this.queryParams.empName = this.userFormat1(val)
      this.queryParams.empId = val
    },
    /** 获取工号 */
    getEmpId1(val) {
      this.form.empName = this.userFormat1(val)
      this.form.empId = val
      this.form.empNo = val
      queryInfo(this.form).then(res => {
        this.form.dept = res.data[0].departmentName
      })
    },
    /** 获取一级单位点击事件 */
    openOrgPop() {
      this.$refs.selectOrg.show();
    },
    /** 获取部门 */
    getOrg(deptCode) {
      this.queryParams.dept = deptCode
    },
    /** 提交按钮 */
    submitForm() {
      this.form.compId = this.queryParams.compId;
      addYearPerformance(this.form).then(response => {
        this.$modal.msgSuccess("新增成功");
        this.open = false;
        this.getList();
      });
    },
    /** 编辑提交按钮 */
    submitForm1() {
      updateYearPerformance(this.form1).then(response => {
        this.$modal.msgSuccess("编辑成功");
        this.open1 = false;
        this.getList();
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮
    cancel1() {
      this.open1 = false;
      this.reset();
    },
    /** 呈送按钮 */
    handleSend() {
      this.sendForm = {}
      this.sendForm.yearPerformanceList = this.yearPerformanceList
      sendYearPerformance(this.sendForm).then(res =>{
        this.getList();
        this.$modal.msgSuccess("呈送成功");
      })
    },
    /** 最终复核按钮 */
    handleFinalCheck() {
      this.sendForm = {}
      this.sendForm.yearPerformanceList = this.yearPerformanceList
      finalCheckYearPerformance(this.sendForm).then(res =>{
        this.getList();
        this.$modal.msgSuccess("复核成功");
      })
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "年度绩效导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('human/yearPerformance/importTemplate', {}, `yearPerformance_template_${new Date().getTime()}.xlsx`)
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
    }
  }
};
</script>
<style scoped>
</style>
