<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" :rules="rules" label-width="68px">
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
          <el-form-item label="日期" prop="workOvertimeDate">
            <el-date-picker
              v-model="queryParams.workOvertimeDate"
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
              v-hasPermi="['human:overtimeRecord:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['human:overtimeRecord:import']"
            >导入
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="overtimeRecordList">
          <el-table-column label="姓名" align="center" prop="empName" />
          <el-table-column label="工号" align="center" prop="empNo" />
          <el-table-column label="加班开始日期" align="center" prop="startDate" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="加班结束日期" align="center" prop="endDate" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="加班类别" align="center" prop="overtimeTypeId" >
            <template v-slot="scope">
              <dict-tag-human :options="attendenceOptions.OvertimeType" :value="scope.row.overtimeTypeId"/>
            </template>
          </el-table-column>
          <el-table-column label="加班时数" align="center" prop="overtimeHours" />
          <el-table-column label="审核状态" align="center" prop="status" />
          <el-table-column label="输入人" align="center" prop="creator" />
          <el-table-column label="输入日期" align="center" prop="createDate">
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
                :disabled="isShow(scope.row.status)"
                v-hasPermi="['human:overtimeRecord:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-right"
                @click="handleUpdate2(scope.row)"
                v-hasPermi="['human:overtimeRecord:edit']"
              >送出</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-back"
                @click="handleUpdate3(scope.row)"
                v-hasPermi="['human:overtimeRecord:edit']"
              >撤回</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                :disabled="isShow(scope.row.status)"
                v-hasPermi="['human:overtimeRecord:remove']"
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

        <!-- 添加或修改加班记录对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="1500px" append-to-body>
          <el-tabs :value="procData.processed === true ? 'approval' : 'form'" style="height:500px;overflow: auto" >
            <el-tab-pane label="表单信息" name="form">
              <el-form ref="form" :model="form" :rules="rules" :key="key" label-width="110px">
                <el-row>
                  <el-col :span="8">
                    <el-form-item label="工号" prop="empNo">
                      <el-input v-model="form.empNo" placeholder="请输入工号" :disabled="true">
                        <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="姓名" prop="empName">
                      {{form.empName}}
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="岗位" prop="postName">
                      {{form.postName}}
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="16">
                    <el-form-item label="加班日期" prop="workOvertimeDate1">
                      <el-date-picker
                        v-model="form.workOvertimeDate1"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        type="datetimerange"
                        range-separator="~"
                        start-placeholder="加班开始时间"
                        end-placeholder="加班结束时间"
                        :default-time="['08:00:00', '17:00:00']"
                        @change="dateFormat1">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="加班时数" prop="overtimeHours">
                      <el-input v-model="form.overtimeHours" placeholder="请输入加班时数" />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="8">
                    <el-form-item label="加班类别" prop="overtimeTypeId">
                      <el-select v-model="form.overtimeTypeId" style="width: 100%">
                        <el-option
                          v-for="dict in attendenceOptions.OvertimeType"
                          :key="dict.dicNo"
                          :label="dict.dicName"
                          :value="dict.dicNo"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="加班原因" prop="overtimeReaId">
                      <el-select v-model="form.overtimeReaId" style="width: 100%">
                        <el-option
                          v-for="dict in attendenceOptions.OvertimeReason"
                          :key="dict.dicNo"
                          :label="dict.dicName"
                          :value="dict.dicNo"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="辅助说明" prop="description">
                      <el-input v-model="form.description" autosize type="textarea" placeholder="请输入辅助说明" :maxlength="500" show-word-limit/>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="刷卡时间" prop="cardTime">
                      {{form.cardTime}}
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
            </el-tab-pane>
            <el-tab-pane label="流程信息" name="approval" v-if="open">
              <flow-detail :procData="procData" @reject="reject"></flow-detail>
            </el-tab-pane>
          </el-tabs>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm" v-if="form.status !== '审核中'" :disabled="form.cardTime==null">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>
        <select-user ref="select" @ok="getJobNumber"/>
      </el-col>
    </el-row>

    <!-- 加班资料导入对话框 -->
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
import {
  listOvertimeRecord,
  getOvertimeRecord,
  delOvertimeRecord,
  addOvertimeRecord,
  updateOvertimeRecord,
  sendOvertimeRecord
} from "@/api/human/hd/overtimeRecord";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {validateNumber} from "@/utils/jlkj";
import selectUser from "@/views/components/human/selectUser/selectUser";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";
import {queryNewPostNameAndChangeDetail} from "@/api/human/hm/employeeTurnover";
import {getToken} from "@/utils/auth";
import {listOvertimeWorksetting} from "@/api/human/hd/overtimeWorksetting";
import {getAttendenceOptions} from "@/api/human/hd/attendenceBasis";
import {listCardRecord} from "@/api/human/hd/cardRecord";
import '@/assets/styles/humanStyles.scss';
import {getFromByInsId, getTaskByFormId, saveInstanceFormNew} from "@/api/workflow/insform";
import {listDeploy} from "@/api/workflow/deploy";
import {startProcessOverride} from "@/api/workflow/process";
import flowDetail from "@/views/components/flowable/detail";

export default {
  name: "OvertimeRecord",
  components: {selectUser,DictTagHuman,flowDetail},
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
      // 加班记录表格数据
      overtimeRecordList: [],
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
        workOvertimeDate: '',
        startTime: '',
        endTime: '',
        processName: '人事模块加班流程'
      },
      // 表单参数
      form: {
        overtimeHours: null,
        cardTime: null
      },
      // 表单校验
      rules: {
        empNo: [
          { required: true, validator: validateNumber, trigger: "blur" },
          { max: 6, message: '工号长度必须为6位数字', trigger: 'blur' }
        ],
        workOvertimeDate: [
          { required: true, message: "加班开始日期不能为空", trigger: "blur" }
        ],
        workOvertimeDate1: [
          { required: true, message: "加班开始日期不能为空", trigger: "blur" }
        ],
        overtimeHours: [
          // { pattern: /^(0|-[1-9][0-9]*)$/, message: "加班时数不能小于零", trigger: "blur" },
          {required: true, message: "加班时数不能为空", trigger: "blur" }
        ],
        overtimeTypeId: [
          { required: true, message: "加班类别不能为空", trigger: "blur" }
        ],
        overtimeReaId: [
          { required: true, message: "加班原因不能为空", trigger: "blur" }
        ]
      },
      // 公司别数据
      companyName: [],
      // 加班资料导入参数
      upload: {
        // 是否显示弹出层（加班资料导入）
        open: false,
        // 弹出层标题（加班资料导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/human/overtimeRecord/importData"
      },
      oveUnit: null,
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['OvertimeType','OvertimeReason']
      },
      //出勤选单选项列表
      attendenceOptions: {},
      // 加班时数校验值
      overtimeHours: 0,
      processForm:{},
      // 流程相关数据
      procData: {
        // 流程部署id
        procDefId: undefined,
        // 流程实例id
        procInsId: undefined,
        // 是否待办人
        processed: false,
      },
      key: 0
    };
  },
  created() {
    this.initTaskParam();
    selectCompany().then(res => {
      this.companyName = res.data
    })
    this.getOveUnit();
    getAttendenceOptions(this.attendenceOptionType).then(response => {
      this.attendenceOptions = response.data
    })
  },
  methods: {
    /** 初始化流程参数 */
    initTaskParam() {
      // taskFlag === 'skip' 赋值
      if (this.$route.query.taskFlag === 'skip') {
        // 是否通过跳转进入页面
        this.taskFlag = this.$route.query && this.$route.query.taskFlag
        // 流程部署id
        this.procData.procDefId  = this.$route.query && this.$route.query.procDefId
        // 流程实例id
        this.procData.procInsId = this.$route.query && this.$route.query.procInsId
        // 是否待办任务
        this.procData.processed = this.$route.query && eval(this.$route.query.processed || false)
        // 跳转过来直接打开dialog
        this.openDialog()
      }
    },
    /** 拒绝任务后回调方法 */
    reject(val) {
      this.reset()
      this.form = val.formData
      // 任务被拒绝后更改状态为 3 => 拒绝
      this.form.status = val.type
      sendOvertimeRecord(this.form).then(response => {
        this.$modal.msgSuccess("送出成功");
        this.getList();
      });
    },
    /** 审批通过且最后节点执行方法 */
    complete(val) {
      this.reset()
      this.form = val.formData
      // 任务被拒绝后更改状态为 2 => 审批通过且流程结束
      this.form.status = val.type
      sendOvertimeRecord(this.form).then(response => {
        this.$modal.msgSuccess("送出成功");
        this.getList();
      });
    },
    // 我的流程跳转至画面，自动打开dialog
    openDialog() {
      // 切换当前状态为审核状态，不允许修改数据
      // 根据流程实例id查询form表单id
      const params = {insId:this.procData.procInsId}
      getFromByInsId(params).then(res => {
        // 根据id查询form表单数据
        getOvertimeRecord(res.data.formId).then(res1 => {
          this.form = res1.data;
          this.form.workOvertimeDate1 = res1.data.workOvertimeDate
          listCardRecord(this.form).then(response => {
            this.form.cardTime = response.rows[0].cardTime
          });
          this.form.compId = this.queryParams.compId;
          this.procData.formData = this.form
          this.open = true;
          this.title = "查看流程详情";
        })
      })
    },
    /** 查询加班记录列表 */
    getList() {
      this.loading = true;
      listOvertimeRecord(this.queryParams).then(response => {
        this.overtimeRecordList = response.data.rows;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      this.$router.push({ path: '/human/hd/hd5/overtimeRecord' })
    },
    // 表单重置
    reset() {
      this.procData = {
        procDefId: undefined,
        procInsId: undefined,
        processed: false,
        formData: {}
      }
      this.form = {
        id: null,
        compId: null,
        empId: null,
        empName: null,
        empNo: null,
        postId: null,
        postName: null,
        overtimeTypeId: null,
        overtimeReaId: null,
        overtimeDays: null,
        overtimeHours: '0',
        description: null,
        status: null,
        resultlt: null,
        cardTime: null,
        creator: null,
        creatorId: null,
        createDate: null,
        disposeId: null,
        resvAttr1: null,
        resvAttr2: null,
        resvAttr3: null,
        resvAttr4: null,
        resvAttr5: null,
        workOvertimeDate: null,
        startTime: null,
        endTime: null
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
      this.queryParams.empNo = undefined
      this.queryParams.workOvertimeDate = undefined
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加加班记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id
      getOvertimeRecord(id).then(response => {
        this.form = response.data;
        this.form.workOvertimeDate1 = response.data.workOvertimeDate
        listCardRecord(this.form).then(response => {
          this.form.cardTime = response.rows[0].cardTime
        });
        this.open = true;
        this.title = "修改加班记录";
      });
      // 如果不是从我的流程或待办任务跳转，则带formid查询
      // if (!(this.taskFlag === 'skip')) {
      // 根据表单id获取是否有绑定启动流程
      getTaskByFormId(id).then(res => {
        if (res.data !== undefined) {
          this.procData.procDefId = res.data.deployId
          this.procData.procInsId = res.data.instanceId
          this.procData.formData = this.form
          this.key = Math.random();
        }
        this.open = true;
        this.title = "修改加班记录";
      })
    },
    handleUpdate2(row){
      this.processForm = {
        formId: row.id,
        deployId: undefined,
        routerPath: this.$route.path
      }
      /** 绑定表单流程 */
      listDeploy(this.queryParams).then(response => {
        this.processForm.deployId = response.rows[0].definitionId;
        saveInstanceFormNew(this.processForm).then(res => {
          const variables = row;
          // 设定流程变量参数 -- 天数
          variables.days = this.getTravelDays();
          // 启动流程并将表单数据加入流程变量
          startProcessOverride(JSON.stringify(variables)).then(res => {
            if (res.code === 200) {
              this.form.status = '审核中'
              this.form.id = row.id
              sendOvertimeRecord(this.form).then(response => {
                this.$modal.msgSuccess("送出成功");
                this.getList();
              });
            }
          })
        })
      });
    },
    getTravelDays(){
      if( this.form.startDate && this.form.endDate){
        return Math.floor((new Date(this.form.endDate).getTime()-new Date(this.form.startDate).getTime())/(24*3600*1000)) + 1
      }
      return '';
    },
    /**  取消流程申请 */
    handleUpdate3(row){
      this.$router.push({
        path: '/work/own'
      })
      this.$message({ type: 'error', message: '请在我的流程页面取消此流程！', duration: 8000 });
      //获得form表单
      this.reset();
      const id = row.id
      getOvertimeRecord(id).then(response => {
        this.form = response.data;
        //修改审核状态
        this.form.status = '未送出';
        this.form.startTime = this.form.workOvertimeDate1[0]
        this.form.endTime = this.form.workOvertimeDate1[1]
        updateOvertimeRecord(this.form).then(response => {
          this.$modal.msgSuccess("修改状态成功");
          this.getList();
        });
      });
    },
    getOveUnit() {
      listOvertimeWorksetting(this.queryParams).then(response => {
        this.oveUnit = response.rows[0].oveUnit
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            if (this.form.overtimeHours < this.oveUnit) {
              this.$modal.msgError("加班时数小于加班最小单位");
            } else {
              if (this.form.overtimeHours == this.overtimeHours) {
                this.form.startTime = this.form.workOvertimeDate1[0]
                this.form.endTime = this.form.workOvertimeDate1[1]
                updateOvertimeRecord(this.form).then(response => {
                  this.$modal.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                });
              } else {
                this.$modal.msgError("加班时数计算错误，请重新输入");
              }
            }
          } else {
            if (this.form.overtimeHours < this.oveUnit) {
              this.$modal.msgError("加班时数小于加班最小单位");
            } else {
              if (this.form.overtimeHours == this.overtimeHours){
                this.form.compId = this.queryParams.compId
                addOvertimeRecord(this.form).then(response => {
                  this.$modal.msgSuccess("新增成功");
                  this.open = false;
                  this.form.workOvertimeDate1[0] = this.form.workOvertimeDate1[0].substring(0, 10)
                  this.form.workOvertimeDate1[1] = this.form.workOvertimeDate1[1].substring(0, 10)
                  this.queryParams.workOvertimeDate = this.form.workOvertimeDate1;
                  this.queryParams.startTime = this.form.workOvertimeDate1[0].substring(0, 10)
                  this.queryParams.endTime = this.form.workOvertimeDate1[1].substring(0, 10)
                  this.getList();
                });
              } else {
                this.$modal.msgError("加班时数计算错误，请重新输入");
              }
            }
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      const startDate = row.startDate;
      const endDate = row.endDate;
      this.$modal.confirm('是否确认删除加班日期为"' + startDate + '~' + endDate + '"的数据项？').then(function() {
        return delOvertimeRecord(id);
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
      this.form.date1=picker[0]
      this.form.date2=picker[1]
      if (this.form.startTime.substring(11, 13) === '08' && this.form.endTime.substring(11, 13) === '17') {
        this.overtimeHours = 8
      } else if (this.form.startTime.substring(11, 13) < 12 && this.form.endTime.substring(11, 13) > 13){
        this.overtimeHours = this.form.endTime.substring(11, 13) - this.form.startTime.substring(11, 13) - 1
      } else {
        this.overtimeHours = this.form.endTime.substring(11, 13) - this.form.startTime.substring(11, 13)
      }
      listCardRecord(this.form).then(response => {
        if (response.rows.length===0){
          this.$modal.msgSuccess("加班时间段没有刷卡记录！！");
        } else {
          this.form.cardTime = response.rows[0].cardTime
        }
      });
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
      this.upload.title = "加班资料导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('human/overtimeRecord/importTemplate', {}, `overtimeRecord_template_${new Date().getTime()}.xlsx`)
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
