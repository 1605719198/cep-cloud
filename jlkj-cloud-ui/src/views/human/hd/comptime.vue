<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="公司" prop="compId">
            <el-select v-model="queryParams.compId" placeholder="请选择公司">
              <el-option
                v-for="dict in companyName"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="工号" prop="empNo">
            <el-input v-model="queryParams.empNo" placeholder="请输入工号" :disabled="true">
              <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item label="日期" prop="startDate">
            <el-date-picker
              v-model="queryParams.startDate"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="~"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              @change="dateFormat">
            </el-date-picker>
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
              v-hasPermi="['human:comptime:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['human:comptime:import']"
            >导入
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="comptimeList">
          <el-table-column label="工号" align="center" prop="empNo" />
          <el-table-column label="姓名" align="center" prop="empName" />
          <el-table-column label="补休开始日期" align="center" prop="startDate" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="补休结束日期" align="center" prop="endDate" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="补休小时数" align="center" prop="compHours" />
          <el-table-column label="审核状态" align="center" prop="status" />
          <el-table-column label="输入人" align="center" prop="creator" />
          <el-table-column label="输入日期" align="center" prop="createDate" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
            <template v-slot="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['human:comptime:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-right"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['human:comptime:edit']"
              >送出</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-back"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['human:comptime:edit']"
              >撤回</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['human:comptime:remove']"
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

        <!-- 添加或修改补休记录对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="110px">
            <el-row>
              <el-col :span="8">
                <el-form-item label="工号" prop="empNo">
                  <el-input v-model="form.empNo" placeholder="请输入工号" :disabled="true">
                    <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="申请人姓名" prop="empName">
                  {{form.empName}}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="申请人岗位名称" prop="postName">
                  {{form.postName}}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="16">
                <el-form-item label="补休开始日期" prop="workOvertimeDate">
                  <el-date-picker
                    v-model="form.workOvertimeDate"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    type="datetimerange"
                    range-separator="~"
                    start-placeholder="补休开始时间"
                    end-placeholder="补休结束时间"
                    :default-time="['08:00:00', '17:00:00']"
                    @change="dateFormat1">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="补休小时数" prop="compHours">
                  <el-input v-model="form.compHours" placeholder="请输入补休小时数" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="存班小时数" prop="saveHours">
                  {{form.saveHours}}
                </el-form-item>
              </el-col>
              <el-col :span="16">
                <el-form-item label="辅助说明" prop="description">
                  <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="审核状态" prop="status">
                  {{form.status}}
                </el-form-item>
              </el-col>
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
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>
        <select-user ref="select" @ok="getJobNumber"/>
      </el-col>
    </el-row>
    <!-- 补休资料导入对话框 -->
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
import { listComptime, getComptime, delComptime, addComptime, updateComptime } from "@/api/human/hd/comptime";
import {getToken} from "@/utils/auth";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getBaseInfo} from "@/api/human/hm/baseInfo";
import {queryNewPostNameAndChangeDetail} from "@/api/human/hm/employeeTurnover";
import selectUser from "@/views/components/human/selectUser/selectUser";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";
import {validateNumber} from "@/utils/jlkj";

export default {
  name: "Comptime",
  components: {selectUser,DictTagHuman},
  data() {
    return {
      // 遮罩层
      loading: false,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 补休记录表格数据
      comptimeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: 'J00',
        empNo: null,
        startDate: '',
        startTime: '',
        endTime: ''
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        empNo: [
          { required: true, validator: validateNumber, trigger: "blur" },
          { max: 6, message: '工号长度必须为6位数字', trigger: 'blur' }
        ],
        workOvertimeDate: [
          { required: true, message: "补休日期不能为空", trigger: "blur" }
        ],
        compHours: [
          { required: true, message: "补休小时数不能为空", trigger: "blur" }
        ],
      },
      // 公司别数据
      companyName: [],
      // 选单数据
      baseInfoData: [],
      baseInfo: {
        baseInfoList: [
          'OvertimeType',
          'OvertimeReason']
      },
      // 补休资料导入参数
      upload: {
        // 是否显示弹出层（补休资料导入）
        open: false,
        // 弹出层标题（补休资料导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/human/comptime/importData"
      },
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
    /** 查询补休记录列表 */
    getList() {
      this.loading = true;
      listComptime(this.queryParams).then(response => {
        this.comptimeList = response.data.rows;
        this.total = response.data.total;
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
        empId: null,
        empName: null,
        empNo: null,
        postId: null,
        postName: null,
        startDate: null,
        endDate: null,
        saveHours: '0',
        compHours: '0',
        description: null,
        status: null,
        creator: null,
        creatorId: null,
        createDate: null,
        resvAttr1: null,
        resvAttr2: null,
        resvAttr3: null,
        resvAttr4: null
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增补休资料";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id
      getComptime(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改补休资料";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateComptime(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.compId = this.queryParams.compId
            addComptime(this.form).then(response => {
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
      const id = row.id;
      this.$modal.confirm('是否确认删除补休记录编号为"' + id + '"的数据项？').then(function() {
        return delComptime(id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(val, userName) {
      this.queryParams.empNo = val
      this.form.empNo = val
      this.form.empName = userName
      this.getList();
      queryNewPostNameAndChangeDetail(this.form).then(res => {
        this.form.postName = res.data.list1[0].newPostName
      })
    },
    dateFormat(picker) {
      this.queryParams.startTime=picker[0]
      this.queryParams.endTime=picker[1]
    },
    dateFormat1(picker) {
      this.form.startTime=picker[0]
      this.form.endTime=picker[1]
      if (this.form.startTime.substring(11, 13) === '08') {
        this.form.compHours = '8'
      } else {
        this.form.compHours = this.form.endTime.substring(11, 13) - this.form.startTime.substring(11, 13)
      }
    },
    /**是否显示按钮 */
    isShow(status) {
      if (status === '未送出') {
        return false
      } else {
        return true
      }
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "补休资料导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('human/comptime/importTemplate', {}, `comptime_template_${new Date().getTime()}.xlsx`)
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
