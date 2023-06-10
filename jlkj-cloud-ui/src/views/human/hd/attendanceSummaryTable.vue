<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" :rules="rules" v-show="showSearch" label-width="68px">
          <el-form-item label="组织机构" prop="compName">
            <el-input v-model="queryParams.compName" :disabled="true">
              <el-button slot="append" icon="el-icon-search" @click="openOrgPop"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-radio v-model="queryParams.includingSubsidiaries" :label="true" @click.native.prevent="change(queryParams.includingSubsidiaries)">包含子公司</el-radio>
          </el-form-item>
          <el-form-item prop="type">
            <el-select v-model="queryParams.type">
              <el-option
                v-for="dict in baseInfoData.MonthQuarter"
                :key="dict.dicNo"
                :label="dict.dicName"
                :value="dict.dicNo"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="日期" prop="date" v-if="this.queryParams.type === 'month'">
            <el-date-picker
              v-model="queryParams.date"
              value-format="yyyy-MM"
              type="month"
              placeholder="选择月">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="日期" v-else>
            <quarter @getQuarter="getQuarter" />
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
              size="mini"
              @click="handleCancelScheduling"
              v-hasPermi="['human:attendanceGather:cancelScheduling']"
            >取消排班</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              size="mini"
              :disabled="isDisabled"
              @click="handleOppositeCancelScheduling"
              v-hasPermi="['human:attendanceGather:oppositeCancelScheduling']"
            >反（取消排班）</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              size="mini"
              :disabled="isDisabled"
              @click="handleConfirm"
              v-hasPermi="['human:attendanceGather:confirm']"
            >确认</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              size="mini"
              :disabled="isDisabledConfirm"
              @click="handleCounterConfirmation"
              v-hasPermi="['human:attendanceGather:counterConfirmation']"
            >反确认（考勤）</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              size="mini"
              :disabled="isDisabledConfirm"
              @click="handleExport"
              v-hasPermi="['human:attendanceGather:export']"
            >导出
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              size="mini"
              @click="handleImport"
              v-hasPermi="['human:attendanceGather:import']"
            >导入
<!--              :disabled="isDisabledImport"-->
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              size="mini"
              @click="handleSumByQuarterYear"
              v-hasPermi="['human:attendanceGather:sumByQuarterYear']"
            >按季度/年度统计
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              size="mini"
              @click="handleConfirmSalaryCalculation"
              v-hasPermi="['human:attendanceGather:confirmSalaryCalculation']"
            >确认（可薪资计算）</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              size="mini"
              :disabled="isDisabledCounterConfirmationSalaryCalculation"
              @click="handleCounterConfirmationSalaryCalculation"
              v-hasPermi="['human:attendanceGather:counterConfirmationSalaryCalculation']"
            >反确认（薪资计算）</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="attendanceGatherList">
          <el-table-column label="工号" align="center" prop="empNo" />
          <el-table-column label="姓名" align="center" prop="empName" />
          <el-table-column label="岗位" align="center" prop="postName" />
          <el-table-column label="迟到次数" align="center" prop="lateNum" />
          <el-table-column label="早退次数" align="center" prop="leaNum" />
          <el-table-column label="大夜班次数" align="center" prop="bigNig" />
          <el-table-column label="小夜班次数" align="center" prop="smaNig" />
          <el-table-column label="大小夜班次数" align="center" prop="bigSmaNig" />
          <el-table-column label="请假天数" align="center">
            <el-table-column label="病假" align="center" prop="dueAttDuty" />
            <el-table-column label="工作假" align="center" prop="dueAttDuty" />
            <el-table-column label="事假" align="center" prop="dueAttDuty" />
            <el-table-column label="婚假" align="center" prop="dueAttDuty" />
            <el-table-column label="产假" align="center" prop="dueAttDuty" />
            <el-table-column label="丧假" align="center" prop="dueAttDuty" />
            <el-table-column label="探亲假" align="center" prop="dueAttDuty" />
            <el-table-column label="公假" align="center" prop="dueAttDuty" />
            <el-table-column label="年休假" align="center" prop="dueAttDuty" />
            <el-table-column label="护理假" align="center" prop="dueAttDuty" />
            <el-table-column label="合计" align="center" prop="holDuty" />
          </el-table-column>
          <el-table-column label="加班小时数" align="center">
            <el-table-column label="延时加班" align="center" prop="overTimeHou" />
            <el-table-column label="休息日加班" align="center" prop="overTimeHou" />
            <el-table-column label="法定假加班" align="center" prop="overTimeHou" />
            <el-table-column label="合计" align="center" prop="overTimeHou" />
          </el-table-column>
          <el-table-column label="月初未上岗天数" align="center" prop="befEntDuty" />
          <el-table-column label="月末未上岗天数" align="center" prop="aftEntDuty" />
          <el-table-column label="存班小时数" align="center" prop="savHou" />
          <el-table-column label="旷职天数" align="center" prop="truDuty" />
          <el-table-column label="应出勤天数" align="center" prop="dueAttDuty" />
          <el-table-column label="实际出勤天数" align="center" prop="actAttDuty" />
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
        <p style="color: red">{{this.tips}}</p>
        <p style="color: red">{{this.tips1}}</p>
        <p style="color: red">{{this.tips2}}</p>
        <p style="color: red">{{this.tips3}}</p>
        <select-user ref="select" @ok="getJobNumber"/>
        <select-org-person ref="selectOrg" @ok="getOrg"/>
      </el-col>
    </el-row>

    <!-- 出勤汇总导入对话框 -->
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
import {
  listAttendance,
  cancelScheduling,
  oppositeCancelScheduling,
  confirm,
  counterConfirmationSalaryCalculation, counterConfirmation, confirmSalaryCalculation
} from "@/api/human/hd/attendanceGather";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import selectUser from "@/views/components/human/selectUser/selectUser";
import selectOrgPerson from "@/views/components/human/selectUser/selectOrgPerson";
import quarter from "@/views/components/human/quarter/quarter";
import {getBaseInfo} from "@/api/human/hm/baseInfo";
import {getToken} from "@/utils/auth";
import '@/assets/styles/humanStyles.scss';

export default {
  name: "AttendanceSummaryTable",
  components: {selectUser,selectOrgPerson,quarter},
  data() {
    return {
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
      // 月出勤统计汇总表格数据
      attendanceGatherList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: 'J00',
        compName: '吉林建龙钢铁有限责任公司',
        empNo: null,
        includingSubsidiaries: false,
        type: 'month',
        date: '',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        date: [
          { required: true, message: "日期不能为空", trigger: "blur" }
        ],
      },
      // 出勤汇总导入参数
      upload: {
        // 是否显示弹出层（出勤汇总导入）
        open: false,
        // 弹出层标题（出勤汇总导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/human/attendanceGather/importData"
      },
      // 公司别数据
      companyName: [],
      // 选单数据
      baseInfoData: [],
      baseInfo: {
        baseInfoList: [
          'MonthQuarter']
      },
      tips: '温馨提示：',
      tips1: '1、【取消排班】按钮：对某月取消排班后，这个月所有员工的排班就被控制为不能调整了',
      tips2: '2、【确认】按钮：操作前，需确保员工考勤都已经完成（含异常处理、各流程审批等），对某月确认后，表示这个月所有员工考勤已经被锁定。后面如果有员工考勤有问题，只能通过导入方式修正！',
      tips3: '3、【确认（可薪资计算）】按钮：操作前，需确保这个月考勤汇总已经全部修正完成。一旦该按钮被点后，就不能再导入修正汇总表了！“确认（可薪资计算）” 后方可以计算工资。',
      isDisabled: true,
      isDisabledConfirm: true,
      isDisabledImport: true,
      isDisabledCounterConfirmationSalaryCalculation: true
    };
  },
  created() {
    selectCompany().then(res => {
      this.companyName = res.data
    })
    getBaseInfo(this.baseInfo).then(response => {
      this.baseInfoData = response.data
    });
  },
  methods: {
    /** 查询月出勤统计汇总列表 */
    getList() {
      this.loading = true;
      listAttendance(this.queryParams).then(response => {
        this.attendanceGatherList = response.rows;
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(val) {
      this.queryParams.empNo = val
    },
    openOrgPop() {
      this.$refs.selectOrg.show();
    },
    /** 获取工号 */
    getOrg(deptCode, deptId, deptName) {
      this.queryParams.compId = deptCode
      this.queryParams.compName = deptName
      this.form.departmentId = deptId
      this.getList();
    },
    //获取季度子组件传回的数据
    getQuarter(val){
      this.queryParams.date = val
    },
    change(value) {
      this.queryParams.includingSubsidiaries = value !== true;
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "出勤汇总导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('human/attendanceGather/importTemplate', {}, `attendanceSummary_template_${new Date().getTime()}.xlsx`)
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
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/attendanceGather/export', {
        ...this.queryParams
      }, `attendance_${new Date().getTime()}.xlsx`)
      this.isDisabledImport = false
    },
    /** 取消排班按钮操作 */
    handleCancelScheduling() {
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          cancelScheduling(this.queryParams).then(response => {
            this.isDisabled = false
            this.$modal.msgSuccess("取消排班成功");
          });
        }
      });
    },
    /** 反（取消排班）按钮操作 */
    handleOppositeCancelScheduling() {
      oppositeCancelScheduling(this.queryParams).then(response => {
        this.isDisabled = true
        this.$modal.msgSuccess("反（取消排班）成功");
      });
    },
    /** 确认按钮操作 */
    handleConfirm() {
      confirm(this.queryParams).then(response => {
        this.isDisabledConfirm = false
        this.$modal.msgSuccess("确认成功");
      });
    },
    /** 反确认（考勤）按钮操作 */
    handleCounterConfirmation() {
      counterConfirmation(this.queryParams).then(response => {
        this.isDisabledConfirm = true
        this.$modal.msgSuccess("反确认（考勤）成功");
      });
    },
    /** 确认（可薪资计算）按钮操作 */
    handleConfirmSalaryCalculation() {
      confirmSalaryCalculation(this.queryParams).then(response => {
        this.isDisabledCounterConfirmationSalaryCalculation = false
        this.$modal.msgSuccess("确认（可薪资计算）成功");
      });
    },
    /** 反确认（可薪资计算）按钮操作 */
    handleCounterConfirmationSalaryCalculation() {
      counterConfirmationSalaryCalculation(this.queryParams).then(response => {
        this.isDisabledCounterConfirmationSalaryCalculation = true
        this.$modal.msgSuccess("反确认（可薪资计算）成功");
      });
    }
  }
};
</script>
<style scoped>
</style>
