<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" :rules="rules" label-width="78px">
          <el-form-item label="公司别" prop="companyId">
            <el-select v-model="queryParams.companyId" placeholder="请选择公司" :popper-append-to-body="false">
              <el-option
                v-for="dict in companyName"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="注销时间" prop="workOvertimeDate">
            <el-date-picker
              v-model="queryParams.workOvertimeDate"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetimerange"
              range-separator="~"
              start-placeholder="注销开始时间"
              end-placeholder="注销结束时间"
              :default-time="['08:00:00', '08:00:00']"
              @change="dateFormat">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="注销原因" prop="reason">
            <el-input v-model="queryParams.reason" placeholder="请输入注销原因" style="width: 600px"/>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              size="mini"
              @click="screenBasedLogout"
              v-hasPermi="['human:cancellationPerson:add']"
            >基于画面注销</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              size="mini"
              @click="handleImport"
              v-hasPermi="['human:cancellationPerson:import']"
            >基于表格注销</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              size="mini"
              @click="importTemplate"
            >下载注销模板</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="cancellationPersonList">
          <el-table-column label="工号" align="center" prop="empNo" />
          <el-table-column label="姓名" align="center" prop="empName" />
          <el-table-column align="center">
            <template slot="header" slot-scope="scope">
              <el-button
                type="primary"
                plain
                size="mini"
                @click="inputClick"
              >添加员工</el-button>
            </template>
            <template v-slot="scope">
              {{ "——" }}
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

        <select-cancellation-user ref="select" @ok="getJobNumber"/>
      </el-col>
    </el-row>

    <!-- 基于表格注销对话框 -->
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
            是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
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
  addBatchCancellationPerson,
  listCancellationPerson,
} from "@/api/human/hd/cancellationPerson";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import selectCancellationUser from "@/views/components/human/selectUser/selectCancellationUser";
import {getToken} from "@/utils/auth";
import '@/assets/styles/humanStyles.scss';

export default {
  name: "BatchCancellationPerson",
  components: {selectCancellationUser},
  data() {
    return {
      // 遮罩层
      loading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 人事注销记录表格数据
      cancellationPersonList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: 'J00',
        workOvertimeDate: null,
        reason: null,
        userInfo: []
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        workOvertimeDate: [
          { required: true, message: "注销时间不能为空", trigger: "blur" }
        ],
        reason: [
          { required: true, message: "注销原因不能为空", trigger: "blur" }
        ],
      },
      // 公司别数据
      companyName: [],
      // 基于表格注销参数
      upload: {
        // 是否显示弹出层（基于表格注销）
        open: false,
        // 弹出层标题（基于表格注销）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/human/cancellationPerson/importData"
      },
    };
  },
  created() {
    selectCompany().then(res => {
      this.companyName = res.data
    })
  },
  methods: {
    /** 查询人事注销记录列表 */
    getList() {
      this.loading = true;
      listCancellationPerson(this.queryParams).then(response => {
        this.cancellationPersonList = response.data.rows;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    dateFormat(picker) {
      this.queryParams.startTime=picker[0]
      this.queryParams.endTime=picker[1]
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(val) {
      this.cancellationPersonList = val
      this.queryParams.userInfo = val
    },
    /** 基于画面注销按钮操作 */
    screenBasedLogout() {
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          addBatchCancellationPerson(this.queryParams).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 基于表格注销按钮操作 */
    handleImport() {
      this.upload.title = "基于表格注销";
      this.upload.open = true;
    },
    /** 下载注销模板操作 */
    importTemplate() {
      this.download('human/cancellationPerson/importTemplate', {}, `cancellationPerson_template_${new Date().getTime()}.xlsx`)
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
<style scoped>
/deep/.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
.picker-width{
  width: 100%;
}
</style>
