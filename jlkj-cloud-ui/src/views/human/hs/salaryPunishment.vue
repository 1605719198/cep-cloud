<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" :rules="rules" v-show="showSearch" label-width="80px">
          <el-form-item label="公司别" prop="compId">
            <el-select v-model="queryParams.compId" @change="changeCompId">
              <el-option
                v-for="dict in companyName"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="员工编号" prop="empId">
            <el-input
              v-model="queryParams.empId"
              placeholder="请输入员工编号"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="薪资时间" prop="date">
            <el-date-picker
              v-model="queryParams.date"
              value-format="yyyy-MM"
              type="month"
              placeholder="选择月">
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
              @click="handleSave"
              :disabled="payTypeSwitch"
              v-hasPermi="['human:salaryPunishment:add']"
            >保存</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="payTypeSwitch"
              @click="handleDelete"
              v-hasPermi="['human:salaryPunishment:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['human:salaryPunishment:import']"
            >导入
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-search"
              size="mini"
              @click="handleDelete"
            >查询导入状态</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleDelete"
              v-hasPermi="['human:salaryPunishment:export']"
            >下载导入错误清单</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-download"
              size="mini"
              @click="importTemplate"
              v-hasPermi="['human:salaryPunishment:export']"
            >下载专业奖惩导入模板</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-form ref="form" :model="form" :rules="rules1">
          <el-table v-loading="loading" :data="form.salaryPunishmentList" :row-class-name="tableRowClassName" @row-click="addLine" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="工号/姓名" align="center" prop="empId">
              <template v-slot="scope">
                <el-form-item>
                  <el-input v-model="scope.row.empId" placeholder="请输入工号" style="width: 70%" disabled>
                    <el-button slot="append" icon="el-icon-search" @click="inputClick()"></el-button>
                  </el-input>
                  {{scope.row.userName}}
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="专业奖惩编码/名称" align="center" prop="payTabCode">
              <template v-slot="scope">
                <el-form-item>
                  <el-select v-model="scope.row.payTabCode" placeholder="请选择">
                    <el-option
                      v-for="dict in payTypeList"
                      :key="dict.uuid"
                      :label="dict.payProName"
                      :value="dict.payProId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="金额" align="center" prop="mon">
              <template v-slot="scope">
                <el-form-item :prop="'salaryPunishmentList.' + scope.$index + '.mon'" :rules="rules1.mon">
                  <el-input v-model="scope.row.mon" placeholder="请输入金额"/>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="备注" align="center" prop="comm">
              <template v-slot="scope">
                <el-form-item>
                  <el-input v-model="scope.row.comm" placeholder="请输入备注"/>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="输入人" align="center" prop="creator" />
            <el-table-column label="输入日期" align="center" prop="createDate" width="180">
              <template v-slot="scope">
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

        <!-- 专业奖罚导入对话框 -->
        <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
          <el-upload
            ref="upload"
            :limit="1"
            accept=".xlsx, .xls"
            :headers="upload.headers"
            :action="upload.url + '?updateSupport=' + upload.updateSupport + '?compId=' + queryParams.compId + '?date=' + queryParams.date"
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
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listSalaryPunishment, delSalaryPunishment, addSalaryPunishment } from "@/api/human/hs/salaryPunishment";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import selectUser from "@/views/components/human/selectUser/selectUser";
import {listPayFormation} from "@/api/human/hs/payFormation";
import {getToken} from "@/utils/auth";

export default {
  name: "SalaryPunishment",
  components: {selectUser},
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 薪资类别为全部时禁用
      payTypeSwitch: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        year: null,
        month: null,
        compId: null,
        empId: null,
        date: null
      },
      // 表单参数
      form: {
        // 专业奖罚表格数据
        salaryPunishmentList: [
          {
            empId: undefined,
            mon: 0
          }
        ],
      },
      // 表单校验
      rules: {
        compId: [
          { required: true, message: "公司别不能为空", trigger: "blur" }
        ],
        date: [
          { required: true, message: "薪资时间不能为空", trigger: "blur" }
        ]
      },
      rules1: {
        mon: [
          { pattern: /^\d+$|^\d+[.]?\d+$/, message: "请输入数字", trigger: "change"},
        ]
      },
      // 公司别数据
      companyName: [],
      //登录人信息
      user: {},
      rowIndex: 0,
      index: 0,
      payTypeList: [],
      // 专业奖罚导入参数
      upload: {
        // 是否显示弹出层（专业奖罚导入）
        open: false,
        // 弹出层标题（专业奖罚导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/human/salaryPunishment/importData"
      }
    };
  },
  created() {
    this.initData();
    selectCompany().then(res => {
      this.companyName = res.data
    })
  },
  methods: {
    //初始化数据
    initData(){
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId
      listPayFormation(this.queryParams).then(response => {
        this.payTypeList = response.data.rows;
      });
    },
    /** 查询专业奖罚列表 */
    getList() {
      this.loading = true;
      if (this.queryParams.date != null) {
        this.queryParams.year = this.queryParams.date.substr(0,4)
        this.queryParams.month = this.queryParams.date.substr(5,2)
      }
      listSalaryPunishment(this.queryParams).then(response => {
        this.form.salaryPunishmentList = response.data.rows;
        if (this.form.salaryPunishmentList.length === 0) {
          this.form.salaryPunishmentList = [
            {
              empId: undefined,
              mon: 0
            }
          ]
        }
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
        year: null,
        month: null,
        payTableId: null,
        payTabCode: null,
        payTabName: null,
        mon: null,
        comm: null,
        payType: null,
        compId: null,
        empId: null,
        creator: null,
        creatorId: null,
        createDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.getList();
        }
      });
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
    },
    /** 保存按钮操作 */
    handleSave() {
      this.$refs['queryForm'].validate(valid => {
        this.form.compId = this.queryParams.compId
        this.form.payType = this.queryParams.payType
        if (this.queryParams.date != null) {
          this.form.year = this.queryParams.date.substr(0,4)
          this.form.month = this.queryParams.date.substr(5,2)
        }
        this.form.salaryPunishmentList.map(value => {
          value.compId = this.queryParams.compId
          value.payType = this.queryParams.payType
          value.year = this.queryParams.date.substr(0,4)
          value.month = this.queryParams.date.substr(5,2)
        })
        addSalaryPunishment(this.form).then(res => {
          this.$modal.msgSuccess("新增成功");
          this.getList();
        })
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除专业奖罚编号为"' + ids + '"的数据项？').then(function() {
        return delSalaryPunishment(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    tableRowClassName({row, rowIndex}) {
      // 把每一行的索引放到row里
      row.index = rowIndex
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      this.rowIndex = row.index
      if (this.form.salaryPunishmentList.length === row.index + 1) {
        const newLine = {
          empId: undefined,
          mon: 0
        }
        this.index++
        this.form.salaryPunishmentList.push(newLine)
      }
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(val,userName) {
      this.form.salaryPunishmentList[this.rowIndex].empId = val
      this.form.salaryPunishmentList[this.rowIndex].userName = userName
    },
    changePayType(val) {
      if (val === '6') {
        this.payTypeSwitch = true
      } else {
        this.payTypeSwitch = false
      }
    },
    changeCompId() {
      listPayFormation(this.queryParams).then(response => {
        this.payTypeList = response.data.rows;
      });
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "专业奖罚导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('human/salaryPunishment/importTemplate', {}, `salaryPunishment_template_${new Date().getTime()}.xlsx`)
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
