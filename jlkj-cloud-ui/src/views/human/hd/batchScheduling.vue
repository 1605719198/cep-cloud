<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别"  size="small">
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="轮班方式" prop="shiftmodeId">
        <el-select v-model="queryParams.shiftmodeId" placeholder="请选择轮班方式" clearable style="width: 200px">
          <el-option
            v-for="dict in shiftModeList"
            :key="dict.id"
            :label="dict.turnTypeName"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="班别" prop="classId">
        <el-select v-model="queryParams.classId" placeholder="请选择班别" clearable style="width: 200px">
          <el-option
            v-for="dict in shiftClassList"
            :key="dict.id"
            :label="dict.className"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="排班日期" prop="interval">
        <el-date-picker
          v-model="queryParams.interval"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 200px">
          <el-option
            v-for="dict in attendenceOptions.PersonClassStatus"
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
          @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          size="mini"
          @click="setScheduling"
        >排班
        </el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          size="mini"-->
<!--          @click="showShift"-->
<!--        >轮班方式/班次/班别查看-->
<!--        </el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="batchSchedule" :data="batchScheduleList" @selection-change="handleSelectionChange" :row-class-name="addIndex"
              height="67vh" size="small"
    >
      <el-table-column type="selection" width="100" align="center"/>
      <el-table-column label="工号" align="center" prop="empId">
        <template v-slot="scope">
          <el-input maxlength="32"  v-model="scope.row.empId" placeholder="请选择员工" disabled>
            <el-button slot="append" icon="el-icon-search" @click="inputClick(scope.row.index)"></el-button>
          </el-input>
        </template>
      </el-table-column>
      <el-table-column label="姓名" align="center" prop="nickName"/>
      <el-table-column label="轮班方式" align="center"  prop="shiftmodeName"/>
      <el-table-column label="班别" align="center"  prop="className"/>
      <el-table-column label="排班开始日期" align="center"  prop="startDate"/>
      <el-table-column label="排班结束日期" align="center" prop="endDate"/>
      <el-table-column label="输入人" align="center"  prop="creator"/>
      <el-table-column label="输入日期" align="center"  prop="createDate"/>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <select-user ref="select" @ok="getJobNumber"/>
    <!-- 批量排班资料导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body class="customDialogStyle">
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
import { getToken } from '@/utils/auth'
import { listPersonClassMaster,  batchAddPersonClassMaster } from "@/api/human/hd/personClassMaster";
import { listShiftClass } from '@/api/human/hd/shiftClass'
import { listShiftMode } from '@/api/human/hd/shiftMode'
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getAttendenceOptions } from '@/api/human/hd/attendenceBasis'
import { getDateTime } from '@/api/human/hd/ahumanUtils'
import selectUser from "@/views/components/human/selectUser/selectUser";
export default {
  name: 'batchScheduling',
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
        url: process.env.VUE_APP_BASE_API + '/human/personClassMaster/importData'
      },
      //公司选项
      compId: null,
      //公司数据
      companyList: [],
      //登录人工号
      userEmpId: this.$store.state.user.name,
      //登录人姓名
      nickName: this.$store.state.user.userInfo.nickName,
      //登录人公司
      logincompId: this.$store.state.user.userInfo.compId,
      //批量排班列表
      batchScheduleList: [],
      //多选框选中数据
      multipleSelection: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      //表格序列
      index: 0,
      form:{},
      //人员序号
      selectIndex: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        status:null,
        interval:null,
        remark: 2,
        empId:null,
        compId: this.$store.state.user.userInfo.compId,
        shiftmodeId: null,
        classId: null,
      },
      // 表单校验
      rules: {},
      //轮班方式列表
      shiftModeList: [],
      //班别列表
      shiftClassList: [],
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['PersonClassStatus']
      },
      //出勤选单选项列表
      attendenceOptions: {}
    }
  },
  created() {
    this.getCompanyList()
    this.getDisc()
  },
  watch:{
    'queryParams.compId':{
      deep:true,
      immediate:true,
      handler:function( newV){
        this.queryParams.shiftmodeId = null;
        this.queryParams.classId = null;
        this.getDisc()
        this.getShiftList(0)
      }
    },
    'queryParams.shiftmodeId':{
      deep:true,
      immediate:false,
      handler:function( newV){
        this.queryParams.classId = null;
        this.getDisc()
        this.getShiftList(1)
      }
    },
  },
  methods: {
    //轮班方式/班次/班别查看
    showShift() {
      // alert(JSON.stringify(this.ids))
    },
    //排班
    setScheduling() {
      if(this.queryParams.interval!==null&&this.queryParams.shiftmodeId!==null&&this.queryParams.classId!==null){
        let stsrtDate = getDateTime(1,this.queryParams.interval[0])
        let endDate = getDateTime(1,this.queryParams.interval[1])
        this.multipleSelection.forEach((value)=>{
          value.shiftmodeId = this.queryParams.shiftmodeId;
          value.classId = this.queryParams.classId;
          value.startDate = stsrtDate;
          value.endDate = endDate;
        })
        batchAddPersonClassMaster(this.multipleSelection).then(response=>{
          this.$modal.msgSuccess('排班完成');
          this.getList();
        })
      }else{
        this.$modal.msgWarning('请选择排班必需的数据')
      }

    },
    //新增按钮操作
    handleAdd() {
      this.batchScheduleList.push({
        id: null,
        empId: null,
        shiftmodeId: null,
        classId: null,
        startDate: null,
        endDate: null,
        status:1,
        remark: 2,
        creator: null,
        creatorId: null,
        createDate: null
      })
    },
    //查询操作
    getList() {
      this.loading = true;
      listPersonClassMaster(this.queryParams).then(response => {
        this.batchScheduleList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //获取公司列表
    getCompanyList() {
      selectCompany().then(response => {
        this.companyList = response.data
      })
    },
    //查询轮班方式及班别
    getShiftList(e) {
      if(e===0){
        listShiftMode(this.queryParams).then(response => {
          this.shiftModeList = response.rows
        })
      }else if(e===1){
        listShiftClass(this.queryParams).then(response => {
          this.shiftClassList = response.rows
        })
      }
    },
    //获取出勤字典
    getDisc() {
      getAttendenceOptions(this.attendenceOptionType).then(response => {
        this.attendenceOptions = response.data;
      })
    },
    //搜索操作
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.batchScheduleList = [];
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multipleSelection = selection
    },
    /** 人员选单事件 */
    inputClick(index) {
      this.selectIndex = index;
      let param = {
        compId: this.queryParams.compId,
      }
      this.$refs.select.show(param);
    },
    /** 获取工号 */
    getJobNumber(empId, userName,compId) {
      this.batchScheduleList[this.selectIndex].empId = empId;
      this.batchScheduleList[this.selectIndex].nickName = userName;
      this.batchScheduleList[this.selectIndex].compId = compId;
    },
    //添加行index
    addIndex({ row, rowIndex }) {
      row.index = rowIndex
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = '批量排班资料导入'
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('human/personClassMaster/importTemplate', {}, `personClassMaster_template_${new Date().getTime()}.xlsx`)
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
}
</script>

<style scoped>

</style>
