<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别">
          <el-option
            v-for="dict in companyList"
            :key="dict.deptCode"
            :label="dict.companyName"
            :value="dict.deptCode"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="工号" prop="empNo">
        <el-input v-model="queryParams.empNo" placeholder="请输入工号">
          <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="日期" prop="queryDate">
        <el-date-picker
          v-model="queryParams.queryDate"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="~"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          align="right"
          unlink-panels
          :picker-options="pickerOptions"
          @change="dateFormat"
        >
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
          icon="el-icon-coffee-cup"
          size="mini"
          @click="handleBindProcess"
        >绑定流程
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="cancelHolidayList" @selection-change="handleSelectionChange" height="67vh">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="工号" align="center" prop="empNo"/>
      <el-table-column label="请假时间" align="center" width="350">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.oriStartDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          ~
          <span>{{ parseTime(scope.row.oriEndDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请假天数" align="center" prop="oriLeaveDays"/>
      <el-table-column label="请假类别" align="center" prop="leaTypeId">
        <template v-slot="scope">
          <dict-tag-human-basis :options="attendenceOptions.HD001" :value="scope.row.leaTypeId"/>
        </template>
      </el-table-column>
      <el-table-column label="请假状态" align="center" prop="leaveStatus">
        <template v-slot="scope">
          <el-tag :type="approveTypeTag(scope.row.leaveStatus)">{{ commentType(scope.row.leaveStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="销假状态" align="center" prop="status">
        <template v-slot="scope">
          <el-tag :type="approveTypeTag(scope.row.status)" v-if="scope.row.status">{{
              commentType(scope.row.status)
            }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="!scope.row.status||scope.row.status === '0'"
                     size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleUpdate(scope.row)"
                     v-hasPermi="['human:cancelHoliday:edit']"
          >销假
          </el-button>
          <el-button v-if="!scope.row.status||scope.row.status === '0'"
                     :disabled="!scope.row.id"
                     size="mini"
                     type="text"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.row)"
                     v-hasPermi="['human:cancelHoliday:remove']"
          >作废
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            v-if="!scope.row.status||scope.row.status === '0'"
            :disabled="!scope.row.id"
            @click="handleSend(scope.row)"
          >送审
          </el-button>
          <el-button v-if="scope.row.status&&scope.row.status!=='0'"
                     size="mini"
                     type="text"
                     icon="el-icon-info"
                     @click="handleDetails(scope.row)"
          >详情
          </el-button>
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

    <!-- 添加或修改员工销假对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1500px" append-to-body class="customDialogStyle">
      <el-tabs :value="procData.processed === true ? 'approval' : 'form'" style="height:500px;overflow: auto">
        <el-tab-pane label="表单信息" name="form">
          <el-form ref="form" :model="form" :rules="rules" label-width="200px">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="人员编码" prop="empNo">
                  <span>{{ form.empNo }}-{{ form.empName }} {{ form.postName }}</span>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="原请假开始时间" prop="oriStartDate">
                  {{ form.oriStartDate }}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="原请假结束时间" prop="oriEndDate">
                  {{ form.oriEndDate }}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="原请假天数" prop="oriLeaveDays">
                  {{ form.oriLeaveDays }}
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="实际请假开始时间" prop="startDate">
                  <el-date-picker clearable
                                  style="width: 100%"
                                  v-model="form.startDate"
                                  value-format="yyyy-MM-dd HH:mm:ss"
                                  type="datetime"
                                  placeholder="请选择请假开始时间"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="实际请假结束时间" prop="endDate">
                  <el-date-picker clearable
                                  style="width: 100%"
                                  v-model="form.endDate"
                                  value-format="yyyy-MM-dd HH:mm:ss"
                                  type="datetime"
                                  placeholder="请选择请假结束时间"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="实际请假天数" prop="leaveDays">
                  <el-input v-model="form.leaveDays" placeholder="" disabled/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="请假类别" prop="leaTypeId">
                  <el-select v-model="form.leaTypeId" placeholder="请选择请假类别" disabled style="width: 100%;">
                    <el-option
                      v-for="dict in attendenceOptions.HD001"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否包括节假日" prop="isContainHoliday">
                  <el-radio-group v-model="form.isContainHoliday" disabled>
                    <el-radio
                      v-for="dict in dict.type.sys_yes_no"
                      :key="dict.value"
                      :label="dict.value"
                    >{{ dict.label }}
                    </el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否全销" prop="isAll">
                  <el-radio-group v-model="form.isAll">
                    <el-radio
                      v-for="dict in dict.type.sys_yes_no"
                      :key="dict.value"
                      :label="dict.value"
                    >{{ dict.label }}
                    </el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="本月累计天数" prop="monthDays">
                  <el-input v-model="form.monthDays" placeholder="" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="本年累计天数" prop="yearDays">
                  <el-input v-model="form.yearDays" placeholder="" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="剩余可请假天数" prop="remainingDays">
                  <el-input v-model="form.remainingDays" placeholder="" disabled/>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="辅助说明" prop="description">
                  <el-input v-model="form.description" placeholder="请输入辅助说明" type="textarea" show-word-limit
                            maxlength="500"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="输入日期" prop="createDate">
                  {{ form.createDate }}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="输入人员" prop="creator">
                  <span v-show="form.creator">{{ form.creatorId }}-{{ form.creator }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="批标状态" prop="status">
                  <dict-tag-human-basis :options="attendenceOptions.FlowStatus" :value="form.status"/>
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
        <el-button type="primary" @click="cancelHolidayProcess">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 显示员工销假详情对话框 -->
    <el-dialog :title="title" :visible.sync="openDetail" width="1500px" append-to-body class="customDialogStyle">
      <el-tabs :value="procData.processed === true ? 'approval' : 'form'" style="height:500px;overflow: auto">
        <el-tab-pane label="表单信息" name="form">
          <el-form ref="form" :model="form" :rules="rules" label-width="200px">
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="人员编码" prop="empNo">
                  <span>{{ form.empNo }}-{{ form.empName }} {{ form.postName }}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="原请假开始时间" prop="oriStartDate">
                  {{ form.oriStartDate }}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="原请假结束时间" prop="oriEndDate">
                  {{ form.oriEndDate }}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="原请假天数" prop="oriLeaveDays">
                  {{ form.oriLeaveDays }}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="实际请假开始时间" prop="startDate">
                  {{ form.startDate }}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="实际请假结束时间" prop="endDate">
                  {{ form.endDate }}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="实际请假天数" prop="leaveDays">
                  {{ form.leaveDays }}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="请假类别" prop="leaTypeId">
                  <el-select v-model="form.leaTypeId" placeholder="请选择请假类别" disabled style="width: 100%;">
                    <el-option
                      v-for="dict in attendenceOptions.HD001"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否包括节假日" prop="isContainHoliday">
                  <el-radio-group v-model="form.isContainHoliday" disabled>
                    <el-radio
                      v-for="dict in dict.type.sys_yes_no"
                      :key="dict.value"
                      :label="dict.value"
                    >{{ dict.label }}
                    </el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否全销" prop="isAll">
                  <el-radio-group v-model="form.isAll" disabled>
                    <el-radio
                      v-for="dict in dict.type.sys_yes_no"
                      :key="dict.value"
                      :label="dict.value"
                    >{{ dict.label }}
                    </el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="本月累计天数" prop="monthDays">
                  <el-input v-model="form.monthDays" placeholder="" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="本年累计天数" prop="yearDays">
                  <el-input v-model="form.yearDays" placeholder="" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="剩余可请假天数" prop="remainingDays">
                  <el-input v-model="form.remainingDays" placeholder="" disabled/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="24">
                <el-form-item label="辅助说明" prop="description">
                  {{form.description}}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="输入日期" prop="createDate">
                  {{ form.createDate }}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="输入人员" prop="creator">
                  <span v-show="form.creator">{{ form.creatorId }}-{{ form.creator }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="批标状态" prop="status">
                  <dict-tag-human-basis :options="attendenceOptions.FlowStatus" :value="form.status"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="流程信息" name="approval" v-if="openDetail">
          <flow-detail :procData="procData" @reject="reject"></flow-detail>
        </el-tab-pane>
      </el-tabs>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--选择流程-->
    <select-deploy ref="deployProcess" @check="bindProcess"></select-deploy>
    <!--选择用户-->
    <select-user ref="select" @ok="getJobNumber"/>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss'
import {
  listCancelHoliday,
  getCancelHoliday,
  delCancelHoliday,
  addCancelHoliday,
  updateCancelHoliday
} from '@/api/human/hd/cancelHoliday'
import {
  delPersonHoliday,
  updatePersonHoliday
} from '@/api/human/hd/personHoliday'
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getAttendenceOptions } from '@/api/human/hd/attendenceBasis'
import DictTagHumanBasis from '@/views/components/human/dictTag/humanBaseInfo'
import selectUser from '@/views/components/human/selectUser/selectUser'
import { getDateTime } from '@/api/human/hd/ahumanUtils'
import { yearHoliday, updateYearHolidayByEmp } from '@/api/human/hd/yearHoliday'
import { homeLeaveHoliday, updateHolidayByEmp } from '@/api/human/hd/homeLeaveHoliday'
import { getCompHolidaySetting } from '@/api/human/hd/holidaysetting'
import { getShiftCodeByPerson } from '@/api/human/hd/shiftCode'
import { addOverTime } from '@/api/human/hd/overTime'
import { startProcess, startProcessOverride } from '@/api/workflow/process'
import flowDetail from '@/views/components/flowable/detail'
import { getFromByInsId, getTaskByFormId, saveInstanceForm } from '@/api/workflow/insform'
import selectDeploy from '@/views/components/flowable/deploy'

export default {
  name: 'CancelHoliday',
  dicts: ['sys_yes_no'],
  components: { DictTagHumanBasis, selectUser, flowDetail, selectDeploy },
  data() {
    return {
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['FlowStatus', 'HD001']
      },
      //出勤选单选项列表
      attendenceOptions: {},
      //公司列表
      companyList: [],
      //登录人信息
      user: {},
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
      // 员工销假表格数据
      cancelHolidayList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否显示详情弹出层
      openDetail: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        empId: null,
        queryDate: null,
        startDate: null,
        endDate: null
      },
      // 表单参数
      form: {},
      // 表单之前是否全销
      preIsAll:null,
      // 表单校验
      rules: {
      },
      //年休探亲假数据处理参数
      holidayYeaHomeData: {
        enough: true,
        number: 0
      },
      //员工轮班方式
      shiftMode: null,
      //班次数据
      shiftCodeData: null,
      //请假跨天数
      conDays: null,
      //所选假别参数设定
      holidaySetting: {},
      //回退数据id
      deletId: null,
      //是否回退删除原数据
      ifDelete: false,
      //全销作废
      isAllDelete: false,
      //是否获取原请假统计数据
      ifPreData: false,
      //原请假统计数据
      preData: null,
      //请假统计数据（按年月分类）
      statisticsData: null,
      //请假错误信息存取
      errorData: {
        ifError: null,
        errorMsg: null
      },
      // 流程相关数据
      procData: {
        // 流程部署id
        procDefId: undefined,
        // 流程实例id
        procInsId: undefined,
        // 是否待办人
        processed: false
      },
      // 判断页面是跳转还是直接进入
      taskFlag: undefined,
      // table选择数据
      selectionList: [],
      // 日期选择器快捷选项
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      }
    }
  },
  computed: {
    commentType() {
      return val => {
        switch (val) {
          case '0':
            return '未送审'
          case '1':
            return '审核中'
          case '2':
            return '通过'
          case '3':
            return '驳回'
        }
      }
    },
    approveTypeTag() {
      return val => {
        switch (val) {
          case '0':
            return 'info'
          case '1':
            return 'primary'
          case '2':
            return 'success'
          case '3':
            return 'danger'
        }
      }
    }
  },
  created() {
    this.initTaskParam()
    this.initData()
    this.getCompanyList()
    this.getDisc()
    this.getList()
  },
  methods: {
    //销假流程
    cancelHolidayProcess() {
      //如果为全销
      if (this.form.isAll === 'Y') {
        this.submitForm(0)
        console.log('全销假')
      } else {
        if(this.form.startDate&&this.form.endDate){
          // 请假结束日期需大于请假开始日期
          if (new Date(this.form.endDate).getTime() < new Date(this.form.startDate).getTime()) {
            this.$modal.msgError('实际请假结束日期需大于实际请假开始日期')
          } else if (new Date(this.form.startDate) < new Date(this.form.oriStartDate) || new Date(this.form.endDate) > new Date(this.form.oriEndDate)) {
            this.$modal.msgError('实际请假时间段应在原请假时间段内')
          } else {
            this.getLeaHoliday(this.form.startDate, this.form.endDate)
          }
        }else{
          this.$modal.msgError("请选择实际请假时间段")
        }
      }
    },
    //获取请假假期设定数据
    getLeaHoliday(dateString1, dateString2) {
      var params = {
        holidayTypeCode: this.form.leaTypeId,
        compId: this.form.compId
      }
      getCompHolidaySetting(params).then(response => {
        if (response.data) {
          this.holidaySetting = response.data
          this.form.isContainHoliday = this.holidaySetting.isIncHol
          this.getShiftMode(dateString1, dateString2)
        } else {
          this.$modal.msgError('该公司下未找到该请假类别数据')
        }
      })
    },
    //获取轮班方式和班次数据
    getShiftMode(dateString1, dateString2) {
      //请假开始结束时间
      let startDate = new Date(dateString1)
      let endDate = new Date(dateString2)
      let preDate = new Date((startDate.getTime() - 24 * 3600 * 1000))
      let params = {
        empId: this.form.empNo,
        startDate: getDateTime(1, preDate),
        endDate: getDateTime(1, endDate),
        compId: this.form.compId
      }
      getShiftCodeByPerson(params).then(response => {
        this.shiftCodeData = response.rows
        console.log('员工排班数据')
        this.shiftCodeData.forEach((value) => {
          console.log('时间：' + value.createDate + ' 编码：' + value.shiftCode)
        })
        let days = (new Date(getDateTime(1, endDate)) - new Date(getDateTime(1, startDate))) / (24 * 60 * 60 * 1000)
        //请假跨天数
        let conDays = Math.floor(days) + 1
        this.conDays = conDays
        if (response.total !== (conDays + 1)) {
          this.$modal.msgError('请假时间段内有未排班天数')
        } else {
          this.computeWorkTime(dateString1, dateString2)
        }
      })
    },
    //计算请假工时
    computeWorkTime(dateString1, dateString2) {
      if (this.ifPreData) {
        this.preData = {
          minutes: 0,
          shifts: 0,
          yearData: [],
          monthData: []
        }
      } else {
        this.statisticsData = {
          minutes: 0,
          shifts: 0,
          yearData: [],
          monthData: []
        }
      }
      //请假过程是否有问题
      this.errorData.ifError = false
      //员工请假分钟数
      let personMinute = 0
      //请假班次(天数)
      let conDay = 0
      //一班次时间工作分钟数
      let oneTurnMinute
      //请假开始结束时间
      let startDate = new Date(dateString1)
      let endDate = new Date(dateString2)
      //第一天最后一天请假小时分钟
      let personHour1 = parseInt(String(startDate.getHours()).padStart(2, '0'))
      let personMin1 = parseInt(String(startDate.getMinutes()).padStart(2, '0'))
      let personHour2 = parseInt(String(endDate.getHours()).padStart(2, '0'))
      let personMin2 = parseInt(String(endDate.getMinutes()).padStart(2, '0'))
      //请假前一天与倒数第二天时间
      let firstDate = new Date((startDate.getTime() - 24 * 3600 * 1000))
      let lastDate = new Date((endDate.getTime() - 24 * 3600 * 1000))
      //请假前一天，倒数第二天差值数据,第一天，最后一天班次数据
      let firstData = null, lastData = null, startData = null, endData = null
      let firstMinute = 0, lastMinute = 0, startMinute = 0, endMinute = 0
      //最小请假分钟数
      let leastMinute = 0
      this.shiftCodeData.forEach((value) => {
        //工作开始，持续，结束时分
        let startHour = parseInt(value.startHour)
        let startMin = parseInt(value.startMin)
        let conHour = parseInt(value.conHour)
        let conMin = parseInt(value.conMin)
        let endHour = parseInt(value.endHour)
        let endMin = parseInt(value.endMin)
        let conMinute = conHour * 60 + conMin
        if (conMinute !== 0) {
          if (value.createDate === getDateTime(1, startDate)) {
            startData = value
          } else if (value.createDate === getDateTime(1, endDate)) {
            endData = value
          } else if (value.createDate === getDateTime(1, firstDate)) {
            if (this.ifOverDay(JSON.stringify(value))) {
              firstData = value
            }
          } else if (value.createDate === getDateTime(1, lastDate)) {
            personMinute += conMinute
            conDay += 1
            this.computeData(value.createDate, conMinute, 1, 1)
            if (this.ifOverDay(JSON.stringify(value))) {
              lastData = value
            }
          } else {
            personMinute += conMinute
            conDay += 1
            this.computeData(value.createDate, conMinute, 1, 1)
          }
        }
      })
      //前一天请假时间计算
      if (firstData != null) {
        let firstStartHour, firstStartMin
        if (this.compareTime(personHour1, personMin1, firstData.endHour, firstData.endMin) > 0) {
          firstStartHour = firstData.endHour
          firstStartMin = firstData.endMin
        } else {
          firstStartHour = personHour1
          firstStartMin = personMin1
        }
        let obj = {
          startHour: parseInt(firstStartHour) + 24,
          startMin: firstStartMin,
          endHour: parseInt(firstData.endHour) + 24,
          endMin: firstData.endMin,
          shiftCodeData: firstData
        }
        firstMinute = this.computeDayMinute(JSON.stringify(obj))
        console.log('请假前一天请假时间分钟数：' + firstMinute)
        personMinute += firstMinute
        if (firstData.description === 'N') {
          leastMinute = this.holidaySetting.minUnitDay
        } else {
          leastMinute = (parseInt(firstData.conHour) * 60 + parseInt(firstData.conMin)) * this.holidaySetting.minUnitTurns
        }
        let firstDay = firstMinute / (parseInt(firstData.conHour) * 60 + parseInt(firstData.conMin))
        conDay += firstDay
        this.computeData(firstData.createDate, firstMinute, firstDay, 1)
        if (firstMinute % leastMinute !== 0) {
          this.errorData.ifError = true
          this.errorData.errorMsg = ('前一天请假时间不合规范')
          console.log('前一天请假时间不合规范')
        } else {
          console.log('前一天请假时间符合规范')
          console.log('前一天请假班次：' + firstDay)
        }
      }
      //第一天请假时间计算
      if (startData != null) {
        //工作开始，持续，结束时分
        let startHour = parseInt(startData.startHour)
        let startMin = parseInt(startData.startMin)
        let conHour = parseInt(startData.conHour)
        let conMin = parseInt(startData.conMin)
        let endHour = parseInt(startData.endHour)
        let endMin = parseInt(startData.endMin)
        //第一天开始小时分钟
        let startStartHour, startStartMin
        if (this.compareTime(startData.startHour, startData.startMin, personHour1, personMin1) > 0) {
          startStartHour = startData.startHour
          startStartMin = startData.startMin
        } else {
          startStartHour = personHour1
          startStartMin = personMin1
        }
        //如果不止一天
        if (this.conDays !== 1) {
          //不跨天
          if (!this.ifOverDay(JSON.stringify(startData))) {
            if (this.compareTime(startStartHour, startStartMin, startData.endHour, startData.endMin) >= 0) {
              startMinute = 0
            } else {
              let obj = {
                startHour: startStartHour,
                startMin: startStartMin,
                endHour: startData.endHour,
                endMin: startData.endMin,
                shiftCodeData: startData
              }
              startMinute = this.computeDayMinute(JSON.stringify(obj))
            }
          } else {
            //跨天
            let obj = {
              startHour: startStartHour,
              startMin: startStartMin,
              endHour: parseInt(startData.endHour) + 24,
              endMin: parseInt(startData.endMin),
              shiftCodeData: startData
            }
            startMinute = this.computeDayMinute(JSON.stringify(obj))
          }
          console.log('第一天请假时间分钟数：' + startMinute)
          personMinute += startMinute
        } else {
          //不跨天
          if (!this.ifOverDay(JSON.stringify(startData))) {
            if (this.compareTime(personHour2, personMin2, startData.startHour, startData.startMin) < 0 || this.compareTime(personHour1, personMin1, startData.endHour, startData.endMin) > 0) {
              startMinute = 0
              this.errorData.errorMsg = ('请假时长为0')
              this.errorData.ifError = true
            } else {
              if (this.compareTime(personHour2, personMin2, startData.endHour, startData.endMin) > 0) {
                personHour2 = startData.endHour
                personMin2 = startData.endMin
              }
              if (this.compareTime(personHour1, personMin1, startData.startHour, startData.startMin) < 0) {
                personHour1 = startData.startHour
                personMin1 = startData.startMin
              }
              let obj = {
                startHour: personHour1,
                startMin: personMin1,
                endHour: personHour2,
                endMin: personMin2,
                shiftCodeData: startData
              }
              startMinute = this.computeDayMinute(JSON.stringify(obj))
              console.log('第一天请假时间分钟数：' + startMinute)
              personMinute += startMinute
            }
          } else {
            //跨天
            if (this.compareTime(personHour2, personMin2, startData.startHour, startData.startMin) < 0) {
              startMinute = 0
              this.errorData.ifError = true
            } else {
              if (this.compareTime(personHour1, personMin1, startData.startHour, startData.startMin) < 0) {
                personHour1 = startData.startHour
                personMin1 = startData.startMin
              }
              let obj = {
                startHour: personHour1,
                startMin: personMin1,
                endHour: personHour2,
                endMin: personMin2,
                shiftCodeData: startData
              }
              startMinute = this.computeDayMinute(JSON.stringify(obj))
              console.log('第一天请假时间分钟数：' + startMinute)
              personMinute += startMinute
            }
          }
        }
        if (startData.description === 'N') {
          leastMinute = this.holidaySetting.minUnitDay
        } else {
          leastMinute = (parseInt(startData.conHour) * 60 + parseInt(startData.conMin)) * this.holidaySetting.minUnitTurns
        }
        let startDay = startMinute / (parseInt(startData.conHour) * 60 + parseInt(startData.conMin))
        conDay += startDay
        this.computeData(startData.createDate, startMinute, startDay, 1)
        if (startMinute % leastMinute !== 0) {
          this.errorData.ifError = true
          this.errorData.errorMsg = '第一天请假时间不合规范'
          console.log('第一天请假时间不合规范')
        } else {
          console.log('第一天请假时间符合规范')
          console.log('第一天请假班次：' + startDay)
        }
      }
      if (lastData !== null) {
        if (this.compareTime(personHour2, personMin2, lastData.endHour, lastData.endMin) < 0) {
          let lastStartHour = personHour2
          let lastStartMin = personMin2
          let lastEndHour = lastData.endHour
          let lastEndMin = lastData.endMin
          let obj = {
            startHour: parseInt(lastStartHour) + 24,
            startMin: lastStartMin,
            endHour: parseInt(lastEndHour) + 24,
            endMin: lastEndMin,
            shiftCodeData: lastData
          }
          //倒数第二天实际请假差值时间
          lastMinute = this.computeDayMinute(JSON.stringify(obj))
          let realLastMinute = (parseInt(lastData.conHour) * 60 + parseInt(lastData.conMin)) - lastMinute
          console.log('倒数第二天请假分钟为：' + realLastMinute)
          personMinute -= lastMinute
          if (lastData.description === 'N') {
            leastMinute = this.holidaySetting.minUnitDay
          } else {
            leastMinute = (parseInt(lastData.conHour) * 60 + parseInt(lastData.conMin)) * this.holidaySetting.minUnitTurns
          }
          let lastDay = lastMinute / (parseInt(lastData.conHour) * 60 + parseInt(lastData.conMin))
          conDay -= lastDay
          this.computeData(lastData.createDate, (0 - lastMinute), (0 - lastDay), 1)
          if (realLastMinute % leastMinute !== 0) {
            this.errorData.ifError = true
            this.errorData.errorMsg = '倒数第二天请假时间不合规范'
            console.log('倒数第二天请假时间不合规范')
          } else {
            console.log('倒数第二天请假时间符合规范')
            console.log('倒数第二天请假班次：' + (1 - lastDay))
          }
        }
      }

      //最后一天请假时间计算
      if (endData !== null) {
        if (this.conDays === 2 && this.ifOverDay(JSON.stringify(startData))) {
          lastData = startData
        }
        //工作开始，持续，结束时分
        let startHour = parseInt(endData.startHour)
        let startMin = parseInt(endData.startMin)
        let conHour = parseInt(endData.conHour)
        let conMin = parseInt(endData.conMin)
        let endHour = parseInt(endData.endHour)
        let endMin = parseInt(endData.endMin)
        //最后一天开始结束小时分钟
        let endStartHour, endStartMin, endEndHour, endEndMin
        if ((this.compareTime(personHour2, personMin2, endData.startHour, endData.startMin) > 0) && (!lastData || this.compareTime(personHour2, personMin2, lastData.endHour, lastData.endMin) > 0)) {
          endStartHour = endData.startHour
          endStartMin = endData.startMin
          if (this.compareTime(personHour2, personMin2, endData.endHour, endData.endMin) > 0 && !this.ifOverDay(JSON.stringify(endData))) {
            endEndHour = endData.endHour
            endEndMin = endData.endMin
          } else {
            endEndHour = personHour2
            endEndMin = personMin2
          }
          let obj = {
            startHour: endStartHour,
            startMin: endStartMin,
            endHour: endEndHour,
            endMin: endEndMin,
            shiftCodeData: endData
          }
          endMinute = this.computeDayMinute(JSON.stringify(obj))
          console.log('最后一天请假分钟为：' + endMinute)
          personMinute += endMinute
          if (endData.description === 'N') {
            leastMinute = this.holidaySetting.minUnitDay
          } else {
            leastMinute = (parseInt(endData.conHour) * 60 + parseInt(endData.conMin)) * this.holidaySetting.minUnitTurns
          }
          let endDay = endMinute / (parseInt(endData.conHour) * 60 + parseInt(endData.conMin))
          conDay += endDay
          this.computeData(endData.createDate, endMinute, endDay, 1)
          if (endMinute % leastMinute !== 0) {
            this.errorData.ifError = true
            this.errorData.errorMsg = '最后一天请假时间不合规范'
            console.log('最后一天请假时间不合规范')
          } else {
            console.log('最后一天请假时间符合规范')
            console.log('最后一天请假班次：' + endDay)
          }
        } else {
          endMinute = 0
          console.log('最后一天请假分钟为：' + endMinute)
          if (endData.description === 'N') {
            leastMinute = this.holidaySetting.minUnitDay
          } else {
            leastMinute = (parseInt(endData.conHour) * 60 + parseInt(endData.conMin)) * this.holidaySetting.minUnitTurns
          }
          let endDay = endMinute / (parseInt(endData.conHour) * 60 + parseInt(endData.conMin))
          conDay += endDay
          this.computeData(endData.createDate, endMinute, endDay, 1)
          if (endMinute % leastMinute !== 0) {
            this.errorData.ifError = true
            this.errorData.errorMsg = '最后一天请假时间不合规范'
            console.log('最后一天请假时间不合规范')
          } else {
            console.log('最后一天请假时间符合规范')
            console.log('最后一天请假班次：' + endDay)
          }
        }
      }
      console.log('员工请假总分钟数：' + personMinute)
      console.log('员工请假总天/班次数：' + conDay)
      if (!this.ifPreData) {
        console.log('统计数据为：' + JSON.stringify(this.statisticsData))
        if (this.errorData.ifError === false && personMinute !== 0) {
          console.log('请假时间符合要求')
          this.form.leaveShifts = conDay
          this.form.leaveDays = conDay
          this.form.leaveHours = personMinute / 60
          //提交前假别数据处理
          this.preSubmit()
          return true
        } else {
          console.log('请假时间不符合要求')
          this.$modal.msgError(this.errorData.errorMsg)
          this.form.leaveShifts = null
          return false
        }
      } else {
        console.log('原请假统计数据为' + JSON.stringify(this.preData))
        this.ifPreData = false
        if (this.ifDelete) {
          delCancelHoliday(this.deletId).then(response => {
            console.log('主档数据删除成功')
            this.getList()
            this.$modal.msgSuccess('删除成功')
            setTimeout(()=>{
              this.getShiftMode(this.form.oriStartDate,this.form.oriEndDate)
            },1000)
          })
        }
      }
    },
    //请假时间计算完毕后处理
    preSubmit() {
      this.disposePreData()
      let param = {
        empNo: this.form.empNo,
        compId: this.form.compId,
        companyId: this.form.compId
      }
      if (this.form.leaTypeId === '07') {
        //探亲假情况
        homeLeaveHoliday(param).then(response => {
          this.homeLeaveHoliday = response.data
          this.disposeYearHomeHoliday(1)
        })
      } else if (this.form.leaTypeId === '09') {
        //年休假情况
        yearHoliday(param).then(response => {
          this.yearHoliday = response.data
          this.disposeYearHomeHoliday(2)
        })
      } else {
        console.log('修改后的统计数据为：' + JSON.stringify(this.statisticsData))
        this.form.remainingDays = null
        this.form.yearDays = null
        this.submitForm(1)
      }
    },
    //年休假探亲假数据处理
    disposeYearHomeHoliday(e) {
      //假足够
      let enough = true
      //假别年数据条数足够
      let number = 0
      if (e === 1) {
        //探亲假
        for (let i = 0; i < this.homeLeaveHoliday.length; i++) {
          console.log(this.homeLeaveHoliday[i].year + '探亲假共有' + this.homeLeaveHoliday[i].dueHomeDays + '天已休' + this.homeLeaveHoliday[i].restHomeDays + '天')
          if (parseInt(this.homeLeaveHoliday[i].year) === new Date().getFullYear()) {
            this.form.remainingDays = this.homeLeaveHoliday[i].preHomeDays
            this.form.yearDays = this.homeLeaveHoliday[i].restHomeDays
          }
          for (let j = 0; j < this.statisticsData.yearData.length; j++) {
            if (this.homeLeaveHoliday[i].year === this.statisticsData.yearData[j].year) {
              if (this.statisticsData.yearData[j].shifts > this.homeLeaveHoliday[i].preHomeDays) {
                enough = false
                break
              } else {
                this.statisticsData.yearData[j].restHomeDays = this.statisticsData.yearData[j].shifts
                this.statisticsData.yearData[j].preHomeDays = this.statisticsData.yearData[j].shifts
                this.statisticsData.yearData[j].restHomeNums += 1
                if (!this.form.id) {
                  if (parseInt(this.statisticsData.yearData[j].year) === new Date().getFullYear()) {
                    this.form.remainingDays -= this.statisticsData.yearData[j].shifts
                    this.form.yearDays += this.statisticsData.yearData[j].shifts
                  }
                }
              }
              number++
            }
          }
        }
      } else {
        //年休假
        for (let i = 0; i < this.yearHoliday.length; i++) {
          console.log(this.yearHoliday[i].year + '年休假共有' + this.yearHoliday[i].preYearDays + '天已休' + this.yearHoliday[i].restDays + '天')
          if (parseInt(this.yearHoliday[i].year) === new Date().getFullYear()) {
            this.form.remainingDays = this.yearHoliday[i].preYearDays - this.yearHoliday[i].restDays
            this.form.yearDays = this.yearHoliday[i].restDays
          }
          for (let j = 0; j < this.statisticsData.yearData.length; j++) {
            if (this.yearHoliday[i].year === this.statisticsData.yearData[j].year) {
              if (this.yearHoliday[i].restDays + this.statisticsData.yearData[j].shifts > this.yearHoliday[i].preYearDays) {
                enough = false
                break
              } else {
                this.statisticsData.yearData[j].restDays = this.statisticsData.yearData[j].shifts
                if (parseInt(this.statisticsData.yearData[j].year) === new Date().getFullYear()) {
                  if (!this.form.id) {
                    this.form.remainingDays -= this.statisticsData.yearData[j].shifts
                    this.form.yearDays += this.statisticsData.yearData[j].shifts
                  }
                }
              }
              number++
            }
          }
        }
      }
      this.holidayYeaHomeData.enough = enough
      this.holidayYeaHomeData.number = number
      if (e === 1) {
        console.log('修改后的统计数据为：' + JSON.stringify(this.statisticsData))
        if (!this.holidayYeaHomeData.enough) {
          this.$modal.msgError('探亲假天数不足')
        } else if (this.statisticsData.yearData.length !== this.holidayYeaHomeData.number) {
          this.$modal.msgError('探亲假数据缺失，无法请假')
        } else {
          this.statisticsData.yearData.forEach((value) => {
            let param = {
              empNo: this.form.empNo,
              year: value.year,
              restHomeDays: value.restHomeDays,
              preHomeDays: value.preHomeDays,
              restHomeNums: value.restHomeNums
            }
            if (this.form.id) {
              if (parseInt(value.year) === new Date().getFullYear()) {
                this.form.remainingDays -= value.shifts
                this.form.yearDays += value.shifts
              }
            }
            // 修改探亲假数据
            updateHolidayByEmp(param).then(response => {
            })
          })
          this.submitForm(1)
        }
      } else {
        console.log('修改后的统计数据为：' + JSON.stringify(this.statisticsData))
        if (!this.holidayYeaHomeData.enough) {
          this.$modal.msgError('年休假天数不足')
        } else if (this.statisticsData.yearData.length !== this.holidayYeaHomeData.number) {
          this.$modal.msgError('年休假数据缺失，无法请假')
        } else {
          this.statisticsData.yearData.forEach((value) => {
            let param = {
              empNo: this.form.empNo,
              year: value.year,
              restDays: value.restDays
            }
            if (this.form.id) {
              if (parseInt(value.year) === new Date().getFullYear()) {
                this.form.remainingDays -= value.restDays
                this.form.yearDays += value.restDays
              }
            }
            //修改年休假数据
            updateYearHolidayByEmp(param).then(response => {
            })
          })
          this.submitForm(1)
        }
      }
    },
    //原数据计算处理
    disposePreData() {
      if(this.preIsAll !== 'Y' || this.form.isAll !=='N'){
        this.preData.yearData.forEach((value) => {
          this.computeData(value.year, value.minutes, value.shifts, -1)
        })
        this.preData.monthData.forEach((value) => {
          this.computeData(value.yearMonth, value.minutes, value.shifts, -2)
        })
      }
    },
    //计算某班次是否跨天
    ifOverDay(json) {
      let obj = {}
      //参数对象转化
      if (json != null) {
        obj = JSON.parse(json)
      }
      let restMin = 0
      for (let i = 1; i <= obj.restCount; i++) {
        if (i === 1) {
          restMin += parseInt(obj.restConMin)
        } else {
          restMin += parseInt(obj['restConMin' + i])
        }
      }
      let hour = (parseInt(obj.conHour) * 60 + parseInt(obj.conMin) + restMin) / 60
      return (parseInt(obj.endHour) < parseInt(obj.startHour)) || (hour - 24 >= 0)
    },
    //计算某天请假时间
    computeDayMinute(json) {
      let obj = {}
      //参数对象转化
      if (json != null) {
        obj = JSON.parse(json)
      }
      if (this.compareTime(obj.shiftCodeData.conHour, obj.shiftCodeData.conMin, 0, 0) !== 0) {
        //跨天数据转化
        if (this.ifOverDay(JSON.stringify(obj.shiftCodeData))) {
          obj.shiftCodeData.endHour = parseInt(obj.shiftCodeData.endHour) + 24
          if (parseInt(obj.shiftCodeData.restStartHour) <= parseInt(obj.shiftCodeData.startHour)) {
            obj.shiftCodeData.restStartHour = parseInt(obj.shiftCodeData.restStartHour) + 24
          }
          if (parseInt(obj.shiftCodeData.restEndHour) <= parseInt(obj.shiftCodeData.startHour)) {
            obj.shiftCodeData.restEndHour = parseInt(obj.shiftCodeData.restEndHour) + 24
          }
          if (parseInt(obj.shiftCodeData.restStartHour2) <= parseInt(obj.shiftCodeData.startHour)) {
            obj.shiftCodeData.restStartHour2 = parseInt(obj.shiftCodeData.restStartHour2) + 24
          }
          if (parseInt(obj.shiftCodeData.restEndHour2) <= parseInt(obj.shiftCodeData.startHour)) {
            obj.shiftCodeData.restEndHour2 = parseInt(obj.shiftCodeData.restEndHour2) + 24
          }
          if (obj.shiftCodeData.restStartHour3 <= obj.shiftCodeData.startHour) {
            obj.shiftCodeData.restStartHour3 = parseInt(obj.shiftCodeData.restStartHour3) + 24
          }
          if (obj.shiftCodeData.restEndHour3 <= obj.shiftCodeData.startHour) {
            obj.shiftCodeData.restEndHour3 = parseInt(obj.shiftCodeData.restEndHour3) + 24
          }
        }
        //请假开始结束时间
        let startHour = obj.startHour
        let startMin = obj.startMin
        let endHour = obj.endHour
        let endMin = obj.endMin
        let shiftCodeData = obj.shiftCodeData
        //请假当天总时间分钟数
        let personConMinute = this.compareTime(endHour, endMin, startHour, startMin)
        let stop = false
        for (let i = 1; i <= obj.shiftCodeData.restCount; i++) {
          if (!stop) {
            let restStartHour, restStartMin, restEndHour, restEndMin, restConMin
            if (i === 1) {
              restStartHour = parseInt(shiftCodeData.restStartHour)
              restStartMin = parseInt(shiftCodeData.restStartMin)
              restEndHour = parseInt(shiftCodeData.restEndHour)
              restEndMin = parseInt(shiftCodeData.restEndMin)
              restConMin = parseInt(shiftCodeData.restConMin)
            } else {
              restStartHour = parseInt(shiftCodeData['restStartHour' + i])
              restStartMin = parseInt(shiftCodeData['restStartMin' + i])
              restEndHour = parseInt(shiftCodeData['restEndHour' + i])
              restEndMin = parseInt(shiftCodeData['restEndMin' + i])
              restConMin = parseInt(shiftCodeData['restConMin' + i])
            }
            //1：start，1：end，2：start，2：end
            let condition1 = this.compareTime(startHour, startMin, restStartHour, restStartMin)
            let condition2 = this.compareTime(startHour, startMin, restEndHour, restEndMin)
            let condition3 = this.compareTime(endHour, endMin, restStartHour, restStartMin)
            let condition4 = this.compareTime(endHour, endMin, restEndHour, restEndMin)
            if (condition1 >= 0 && condition4 <= 0) {
              //请假时间段在休息时间内
              personConMinute = 0
              stop = true
            } else if (condition1 <= 0 && condition3 >= 0 && condition4 <= 0) {
              //请假时间段左外右内
              personConMinute -= condition3
              stop = true
            } else if (condition3 <= 0) {
              //请假时间段在休息时间左侧
              stop = true
            } else if (condition1 <= 0 && condition4 >= 0) {
              //请假时间跨住休息时间
              personConMinute -= restConMin
            } else if (condition2 <= 0 && condition4 >= 0 && condition1 >= 0) {
              //请假时间左内右外
              personConMinute += condition2
            } else if (condition2 >= 0) {
              //请假时间段在休息时间左侧
            }
          }
        }
        return personConMinute
      } else {
        return 0
      }
    },
    //工时班次总年月统计方法
    computeData(date, minutes, shifts, e) {
      //年数据处理
      const year = date.substring(0, 4)
      let data
      if (this.ifPreData) {
        data = this.preData
      } else {
        data = this.statisticsData
      }
      let existYear = false
      let yearIndex = data.yearData.length
      for (let i = 0; i < yearIndex; i++) {
        if (data.yearData[i].year === year) {
          existYear = true
          yearIndex = i
          break
        }
      }
      if (!existYear) {
        data.yearData.push({
          year: year,
          minutes: 0,
          shifts: 0,
          restDays: 0,
          restHomeDays: 0,
          preHomeDays: 0,
          restHomeNums: 0
        })
      }
      //月数据处理
      let yearMonth
      let month
      let existMonth
      let monthIndex
      if (e !== -1) {
        yearMonth = date.substring(0, 7)
        month = date.substring(5, 7)
        existMonth = false
        monthIndex = data.monthData.length
        for (let i = 0; i < monthIndex; i++) {
          if (data.monthData[i].yearMonth === yearMonth) {
            existMonth = true
            monthIndex = i
            break
          }
        }
        if (!existMonth) {
          data.monthData.push({
            yearMonth: yearMonth,
            year: year,
            month: month,
            minutes: 0,
            shifts: 0
          })
        }
      }
      if (e > 0) {
        data.minutes += parseFloat(minutes)
        data.shifts += parseFloat(shifts)
        data.yearData[yearIndex].minutes += parseFloat(minutes)
        data.yearData[yearIndex].shifts += parseFloat(shifts)
        data.monthData[monthIndex].minutes += parseFloat(minutes)
        data.monthData[monthIndex].shifts += parseFloat(shifts)
      } else if (e === -1) {
        data.minutes -= parseFloat(minutes)
        data.shifts -= parseFloat(shifts)
        data.yearData[yearIndex].minutes -= parseFloat(minutes)
        data.yearData[yearIndex].shifts -= parseFloat(shifts)
        data.yearData[yearIndex].restDays -= parseFloat(shifts)
        data.yearData[yearIndex].restHomeDays -= parseFloat(shifts)
        data.yearData[yearIndex].preHomeDays -= parseFloat(shifts)
        data.yearData[yearIndex].restHomeNums -= 1
      } else if (e === -2) {
        data.monthData[monthIndex].minutes -= parseFloat(minutes)
        data.monthData[monthIndex].shifts -= parseFloat(shifts)
      }
    },
    /** 保存月度数据(加班汇总表新增) */
    saveMonthData(e) {
      if (e > 0) {
        this.statisticsData.monthData.forEach((value) => {
          value.holOveType = this.form.leaTypeId
          value.isHolType = 1
          value.dutyNum = value.shifts
          value.hourNum = value.minutes / 60
          value.empNo = this.form.empNo
          value.compId = this.form.compId
          value.dataFrom = 0
        })
        addOverTime(this.statisticsData.monthData).then(response => {
          console.log('加班汇总数据更新成功')
        })
      } else {
        this.preData.monthData.forEach((value) => {
          value.holOveType = this.form.leaTypeId
          value.isHolType = 1
          value.dutyNum =  0-value.shifts
          value.hourNum =  0-value.minutes / 60
          value.empNo = this.form.empNo
          value.compId = this.form.compId
          value.dataFrom = 0
        })
        if (e === -1 || (e === 0 && this.form.leaveHours !== 0)) {
          if (e === 0) {
            if (this.form.leaveHours !== 0) {
              this.form.leaveShifts = 0
              this.form.leaveHours = 0
              this.form.leaveDays = 0
            }
          } else if (e === -1) {
            delCancelHoliday(this.deletId).then(response => {
              console.log('主档数据删除成功')
              this.getList()
              this.$modal.msgSuccess('删除成功')
            })
          }
          addOverTime(this.preData.monthData).then(response => {
            console.log('加班汇总数据回退成功')
          })
          if (this.form.leaTypeId === '07') {
            this.preData.yearData.forEach((value) => {
              let param = {
                empNo: this.form.empNo,
                year: value.year,
                restHomeDays: 0 - value.shifts,
                preHomeDays:  0 - value.shifts,
                restHomeNums: -1
              }
              // 修改探亲假数据
              updateHolidayByEmp(param).then(response => {
                console.log('探亲假数据回退成功')
              })
            })
          } else if (this.form.leaTypeId === '09') {
            this.preData.yearData.forEach((value) => {
              let param = {
                empNo: this.form.empNo,
                year: value.year,
                restDays: 0-value.shifts
              }
              //修改年休假数据
              updateYearHolidayByEmp(param).then(response => {
                console.log('年休假数据回退成功')
              })
            })
          }
        }
      }
    },
    /** 时间大小比较 */
    compareTime(hour1, min1, hour2, min2) {
      var time1 = parseInt(hour1) * 60 + parseInt(min1)
      var time2 = parseInt(hour2) * 60 + parseInt(min2)
      return time1 - time2
    },
    /** 查询员工销假列表 */
    getList() {
      this.loading = true
      listCancelHoliday(this.queryParams).then(response => {
        this.cancelHolidayList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openDetail = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        personHolidayId: null,
        compId: null,
        empNo: null,
        empId: null,
        empName: null,
        postName: null,
        postId: null,
        orgParentId: null,
        orgId: null,
        startDate: null,
        endDate: null,
        leaveDays: null,
        oriStartDate: null,
        oriEndDate: null,
        oriLeaveDays: null,
        leaTypeId: null,
        leaveShifts: null,
        leaveHours: null,
        morLeaveHours: null,
        morLeaveShifts: null,
        status: null,
        isAll: null,
        description: null,
        isContainHoliday: null,
        remainingDays: null,
        monthDays: null,
        monthHours: null,
        yearDays: null,
        yearHours: null,
        creator: null,
        creatorId: null,
        createDate: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
      this.selectionList = selection
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加员工销假'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      if (row.id) {
        getCancelHoliday(row.id).then(response => {
          this.form = response.data
          this.preIsAll = this.form.isAll;
          this.ifPreData = true
          if (this.form.id&&this.form.isAll!=='Y') {
            this.getLeaHoliday(this.form.startDate, this.form.endDate)
          } else {
            this.getLeaHoliday(this.form.oriStartDate, this.form.oriEndDate)
          }
          this.open = true
          this.title = '修改员工销假'
        })
      } else {
        this.form = row
        this.ifPreData = true
        if (this.form.id) {
          this.getLeaHoliday(this.form.startDate, this.form.endDate)
        } else {
          this.getLeaHoliday(this.form.oriStartDate, this.form.oriEndDate)
        }
        this.open = true
        this.title = '修改员工销假'
      }
    },
    /** 提交按钮 */
    submitForm(e) {
      this.saveMonthData(e)
      if(!this.isAllDelete&&!this.ifDelete){
        if (this.form.id != null) {
          updateCancelHoliday(this.form).then(response => {
            this.$modal.msgSuccess('修改成功')
            this.open = false
            this.getList()
          })
        } else {
          this.form.status = '0'
          addCancelHoliday(this.form).then(response => {
            this.$modal.msgSuccess('新增成功')
            this.open = false
            this.getList()
          })
        }
      }else{
        this.isAllDelete = false;
        this.ifDelete = false;
      }
    },
    /** 作废按钮操作 */
    handleDelete(row) {
      this.handleDeleteData(row)
      // this.$modal.confirm('是否确认删除该数据项？').then(function() {
      //   return this.handleDeleteData(ids)
      // }).then(() => {
      //   this.getList()
      //   this.$modal.msgSuccess('删除成功')
      // }).catch(() => {
      // })
    },
    /** 作废数据处理 */
    handleDeleteData(row) {
      getCancelHoliday(row.id).then(response => {
        this.reset()
        this.form = response.data
        if(row.isAll!=='Y'){
          this.ifPreData = true
          this.ifDelete = true
          this.deletId = row.id
          this.getLeaHoliday(this.form.startDate,this.form.endDate)
        }else{
          this.preData = {
            minutes: 0,
            shifts: 0,
            yearData: [],
            monthData: []
          }
          this.isAllDelete = true;
          this.getLeaHoliday(row.oriStartDate,row.oriEndDate)
          delCancelHoliday(row.id).then(response => {
            console.log('主档数据删除成功')
            this.getList()
            this.$modal.msgSuccess('删除成功')
          })
        }
      })
    },
    /** 详情按钮操作 */
    handleDetails(row) {
      this.reset()
      const id = row.id || this.ids
      getCancelHoliday(id).then(response => {
        this.form = response.data
        this.openDetail = true
        this.title = '员工销假记录详情'
      })
    },
    /** 初始化数据 */
    initData() {
      this.user.empNo = this.$store.state.user.name
      this.user.empName = this.$store.state.user.userInfo.nickName
      this.user.compId = this.$store.state.user.userInfo.compId
      this.queryParams.compId = this.user.compId
    },
    /** 查询公司列表 */
    getCompanyList() {
      selectCompany().then(response => {
          this.companyList = response.data
        }
      )
    },
    /** 查询出勤字典 */
    getDisc() {
      getAttendenceOptions(this.attendenceOptionType).then(response => {
        this.attendenceOptions = response.data
      })
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show()
    },
    /** 获取工号 */
    getJobNumber(empNo, userName, compId) {
      if (this.open === true) {
        this.form.empNo = empNo
        this.form.empName = userName
        this.form.compId = compId
      } else {
        this.queryParams.empNo = empNo
      }
    },
    /** 日期范围转换 */
    dateFormat(picker) {
      if (picker != null && picker !== '') {
        this.queryParams.startDate = picker[0]
        this.queryParams.endDate = picker[1]
      }
    },
    /** 送审 */
    handleSend(row) {
      this.reset()
      const id = row.id || this.ids
      getCancelHoliday(id).then(response => {
        this.form = response.data
        this.form.compId = this.queryParams.compId
      }).then(() => {
        const variables = this.form
        // 设定流程变量参数 -- 天数
        variables.days = this.form.leaveShifts
        // 启动流程并将表单数据加入流程变量
        startProcessOverride(JSON.stringify(variables)).then(res => {
          if (res.code == 200) {
            this.$modal.msgSuccess(res.msg)
            this.$tab.closeOpenPage({
              path: '/work/own'
            })
            this.form.status = res.data
            // 流程启动更新状态为 1 => 进行中
            updateCancelHoliday(this.form).then(res1 => {
              this.getList()
            })
          }
        })
      })
    },
    /** 绑定流程 */
    handleBindProcess() {
      if (this.selectionList.length === 0) {
        this.$modal.msgWarning('请选择表单数据！')
      } else {
        this.$refs.deployProcess.init()
      }
    },
    /** 绑定流程子传父回调函数 */
    bindProcess(val) {
      const formList = []
      const obj = {
        formId: undefined,
        deployId: undefined,
        routerPath: undefined
      }
      // 只有未送审和被驳回的表单才能重新绑定流程
      this.selectionList.forEach(item => {
        if (item.status === "0" || item.status === "3") {
          obj.formId = item.id
          obj.deployId = val
          obj.routerPath = this.$route.path
          formList.push(obj)
        }
      })
      /** 绑定表单流程 */
      saveInstanceForm(formList).then(res => {
        this.$modal.msgSuccess(res.msg)
      })
    },
    /** 初始化流程参数 */
    initTaskParam() {
      // taskFlag === 'skip' 赋值
      if (this.$route.query.taskFlag === 'skip') {
        // 是否通过跳转进入页面
        this.taskFlag = this.$route.query && this.$route.query.taskFlag
        // 流程部署id
        this.procData.procDefId = this.$route.query && this.$route.query.procDefId
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
      updateCancelHoliday(this.form).then(res1 => {
        this.getList()
      })
    },
    /** 审批通过且最后节点执行方法 */
    complete(val) {
      this.reset()
      this.form = val.formData
      // 任务被拒绝后更改状态为 2 => 审批通过且流程结束
      this.form.status = val.type
      updateCancelHoliday(this.form).then(res1 => {
        this.getList()
      })
    },
    // 我的流程跳转至画面，自动打开dialog
    openDialog() {
      // 切换当前状态为审核状态，不允许修改数据
      // 根据流程实例id查询form表单id
      const params = { insId: this.procData.procInsId }
      getFromByInsId(params).then(res => {
        // 根据id查询form表单数据
        getCancelHoliday(res.data.formId).then(res1 => {
          this.form = res1.data
          this.form.compId = this.queryParams.compId
          this.procData.formData = this.form
          this.openDetail = true
          this.title = '查看流程详情'
        })
      })
    }
  }
}
</script>
