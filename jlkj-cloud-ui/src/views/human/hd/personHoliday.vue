<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别" >
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
      <el-form-item label="日期" prop="queryDate">
        <el-date-picker
          v-model="queryParams.queryDate"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="~"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['human:personHoliday:add']"
        >新增
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="personHolidayList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="工号" align="center" prop="empNo"/>
      <el-table-column label="请假类别" align="center" prop="leaTypeId">
        <template v-slot="scope">
          <dict-tag-human-basis :options="attendenceOptions.HD001" :value="scope.row.leaTypeId"/>
        </template>
      </el-table-column>
      <el-table-column label="剩余可请假天数" align="center" prop="remainingDays"/>
      <el-table-column label="请假批示状态" align="center" prop="status">
        <template v-slot="scope">
          <dict-tag-human-basis :options="attendenceOptions.HolidayStatus" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="请假天数" align="center" prop="leaveShifts"/>
      <el-table-column label="请假开始时间" align="center" prop="startDate" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请假结束时间" align="center" prop="endDate" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button v-if="scope.row.status === '01'"
                     size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleUpdate(scope.row)"
                     v-hasPermi="['human:personHoliday:edit']"
          >修改
          </el-button>
          <el-button v-if="scope.row.status === '01'"
                     size="mini"
                     type="text"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.row)"
                     v-hasPermi="['human:personHoliday:remove']"
          >作废
          </el-button>
          <el-button v-if="scope.row.status != '01'"
                     size="mini"
                     type="text"
                     icon="el-icon-details"
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
    <select-user ref="select" @ok="getJobNumber"/>

    <!-- 添加或修改员工请假记录对话框 -->
    <el-dialog :title="title"
               :visible.sync="open"
               width="900px"
               append-to-body
               class="customDialogStyle"
               :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="200px">

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="工号" prop="empNo">
              <el-input v-model="form.empNo" placeholder="请输入工号" :disabled="true" class="inputInner">
                <el-button slot="append" icon="el-icon-search" @click="inputClick" clearable></el-button>
              </el-input>
              <label :hidden="this.form.postname==null">
                {{ form.empName }}：{{ form.postname }}
              </label>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="请假开始时间" prop="startDate">
              <el-date-picker clearable
                              v-model="form.startDate"
                              type="datetime"
                              placeholder="请选择请假开始时间"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请假结束时间" prop="endDate">
              <el-date-picker clearable
                              v-model="form.endDate"
                              type="datetime"
                              placeholder="请选择请假结束时间"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="请假类别" prop="leaTypeId">
              <el-select v-model="form.leaTypeId" placeholder="请选择请假类别" clearable>
                <el-option
                  v-for="dict in attendenceOptions.HD001"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请假天数" prop="leaveShifts">
              <el-input v-model="form.leaveShifts" type="number" disabled/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
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
          <el-col :span="12">
            <el-form-item label="剩余可请假天数" prop="remainingDays">
              <el-input v-model="form.remainingDays" type="number"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="本月累计天数" prop="monthDays">
              <el-input v-model="form.monthDays" type="number" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="本年累计天数" prop="yearDays">
              <el-input v-model="form.yearDays" type="number" disabled/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="(this.form.id!=null)? 12:24">
            <el-form-item label="辅助说明" prop="description">
              <el-input v-model="form.description" type="textarea" placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="this.form.id!=null">
            <el-form-item label="审批状态" prop="status" :disabled="this.form.id!=null">
              <template v-slot="scope">
                <dict-tag-human-basis :options="attendenceOptions.HolidayStatus" :value="form.status"/>
              </template>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="输入人" prop="creator">
              {{ form.creatorId }} {{ form.creator }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="输入日期" prop="createDate">
              {{ form.createDate }}
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
        <el-button @click="submit">呈 送</el-button>
        <el-button @click="computeManhour">计算</el-button>
      </div>
    </el-dialog>

    <!-- 详情员工请假记录对话框 -->
    <el-dialog :title="title" :visible.sync="opencreate" width="900px" append-to-body class="customDialogStyle">
      <el-form ref="form" :model="form" :rules="rules" label-width="200px">

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="工号" prop="empNo">
              <el-input v-model="form.empNo" placeholder="请输入工号" :disabled="true" class="inputInner">
                <el-button slot="append" icon="el-icon-search" @click="inputClick" clearable></el-button>
              </el-input>
              {{ form.empName }}:{{ form.postname }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="请假开始时间" prop="startDate">
              {{ form.startDate }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请假结束时间" prop="endDate">
              {{ form.endDate }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="请假类别" prop="leaTypeId">
              {{ form.leaTypeId }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请假天数" prop="leaveShifts">
              {{ form.leaveShifts }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="是否包括节假日" prop="isContainHoliday" :disabled="this.form.id!=null">
              <el-radio-group v-model="form.isContainHoliday" :disabled="this.form.id!=null">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="剩余可请假天数" prop="remainingDays">
              {{ form.remainingDays }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="本月累计天数" prop="monthDays">
              {{ form.monthDays }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="本年累计天数" prop="yearDays">
              {{ form.yearDays }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="(this.form.id!=null)? 12:24">
            <el-form-item label="辅助说明" prop="description">
              {{ form.description }}
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="this.form.id!=null">
            <el-form-item label="审批状态" prop="status">
              {{ form.status }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="输入人" prop="creator">
              {{ form.creatorId }} {{ form.creator }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="输入日期" prop="createDate">
              {{ form.createDate }}
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
    </el-dialog>


  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import {
  listPersonHoliday,
  getPersonHoliday,
  delPersonHoliday,
  addPersonHoliday,
  updatePersonHoliday
} from '@/api/human/hd/personHoliday'
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getAttendenceOptions } from '@/api/human/hd/attendenceBasis'
import DictTagHumanBasis from '@/views/components/human/dictTag/humanBaseInfo'
import selectUser from '@/views/components/human/selectUser/selectUser'
import { getDateTime } from '@/api/human/hd/ahumanUtils'
import { queryNewPostNameAndChangeDetail } from '@/api/human/hm/employeeTurnover'
import { yearHoliday } from '@/api/human/hd/yearHoliday'
import { homeLeaveHoliday } from '@/api/human/hd/homeLeaveHoliday'
import { getClassMasterByPerson } from '@/api/human/hd/personClassMaster'
import { getCompHolidaySetting } from '@/api/human/hd/holidaysetting'
import { getHoliday } from '@/api/human/hd/holidayTable'
import { queryShiftCode,getShiftCodeByPerson } from '@/api/human/hd/shiftCode'

export default {
  name: 'PersonHoliday',
  dicts: ['sys_yes_no', 'sys_classtype'],
  components: { DictTagHumanBasis, selectUser },
  data() {
    return {
      //状态
      status: '01',
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['HolidayStatus', 'HD001']
      },
      //出勤选单选项列表
      attendenceOptions: {},
      // 遮罩层
      loading: true,
      //公司列表
      companyList: [],
      //登录人信息
      user: {},
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
      // 员工请假记录表格数据
      personHolidayList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否显示详情弹出层
      opencreate: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
        empNo: null,
        startDate: '',
        endDate: '',
        queryDate: ''
      },
      //员工轮班方式
      shiftMode: null,
      //班次数据
      shiftCodeData: null,
      //所选假别参数设定
      holidaySetting: {},
      //公司节假日设定
      holidayTable: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        totalDays: [
          { required: true, message: '不能为空', trigger: 'change' }
        ],
        empNo: [
          { required: true, message: '员工工号不能为空', trigger: 'blur'}
        ],
        leaTypeId: [
          { required: true, message: '请选择请假类别', trigger: 'change'}
        ],
        startDate: [
          { required: true, message: '请假开始时间不能为空', trigger: 'change'}
        ],
        endDate: [
          { required: true, message: '请假结束时间不能为空', trigger: 'change'}
        ],
      }
    }
  },
  watch: {
    'form.empNo': {
      handler(val) {
        if (val) {

        }
      },
      deep: true,
      immediate: false
    },
    'form.leaTypeId': {
      handler(val) {
        if (val) {
          var params = {
            holidayTypeCode: val,
            compId: this.form.compId
          }
          getCompHolidaySetting(params).then(response => {
            if (response.data) {
              this.holidaySetting = response.data
              this.form.isContainHoliday = this.holidaySetting.isIncHol
            }
          })
        }
      },
      deep: true,
      immediate: true
    }
  },
  computed: {
    monthDays() {

    },
    yearDays() {

    }
  },
  created() {
    this.initData()
    this.getCompanyList()
    this.getList()
    this.getDisc()
  },
  methods: {
    /** 工时计算 */
    computeManhour(e) {
      //如果轮班方式是常白班：01
      var cbMode = '01'
      var startDate = this.form.startDate
      var endDate = this.form.endDate
      var days = (endDate - startDate) / (1 * 24 * 60 * 60 * 1000)
      //请假跨天数
      var restDays = Math.floor(days) + 1
      if (this.shiftMode == cbMode) {
        //假日设定:1,全日班，2,休息班，3,法定假日
        var dateType1 = 1
        var dateType2 = 2
        var dateType3 = 3
        var holidayTable = {
          compId: this.form.compId,
          startDate: getDateTime(1, this.form.startDate),
          endDate: getDateTime(1, this.form.endDate)
        }
        getHoliday(holidayTable).then(response => {
            this.holidayTable = response.rows
            //休息天数
            var k = 0
            //总工作分钟数
            var workMinuteAll = 0
            //一天理论工作分钟数
            var workMinuteDay = parseInt(this.shiftCodeData.conHour) * 60 + parseInt(this.shiftCodeData.conMin)
            this.holidayTable.forEach((value, index, array) => {
              //请假时间
              var hour1;
              var min1;
              //工作开始时间
              var hour2 = parseInt(this.shiftCodeData.startHour)
              var min2 = parseInt(this.shiftCodeData.startMin)
              //工作结束时间
              var hour3 = parseInt(this.shiftCodeData.endHour)
              var min3 = parseInt(this.shiftCodeData.endMin)
              //一次休息开始时间
              var hour4 = parseInt(this.shiftCodeData.restStartHour)
              var min4 = parseInt(this.shiftCodeData.restStartMin)
              //一次休息结束时间
              var hour5 = parseInt(this.shiftCodeData.restEndHour)
              var min5 = parseInt(this.shiftCodeData.restEndMin)
              //当天初始工作分钟数
              var workMinute = 0
              //非工作日判定
              if (value.dateType != dateType1) {
                if (this.form.isContainHoliday == 'N') {
                  k++
                } else {
                  workMinute = workMinuteDay
                }
              } else {
                //休息次数为1
                if (this.shiftCodeData.restCount == 1) {
                  //如果在起始日
                  if (value.holDay == getDateTime(1, startDate)) {
                    hour1 = parseInt(String(startDate.getHours()).padStart(2, '0'))
                    min1 = parseInt(String(startDate.getMinutes()).padStart(2, '0'))
                    //分段判定，早于工作开始时间，第一次休息前工作时间内，晚于工作结束时间，第一次休息后工作时间内，第一次休息时间
                    if (this.compareTime(hour1, min1, hour2, min2) <= 0) {
                      workMinute = workMinuteDay
                    } else if (this.compareTime(hour1, min1, hour4, min4) <= 0) {
                      workMinute = (hour4 - hour1) * 60 + (min4 - min1) + (hour3 - hour5) * 60 + (min3 - min5)
                    } else if (this.compareTime(hour1, min1, hour3, min3) >= 0) {
                      workMinute = 0
                    } else if (this.compareTime(hour1, min1, hour5, min5) >= 0) {
                      workMinute = (hour3 - hour1) * 60 + (min3 - min1)
                    } else {
                      workMinute = (hour3 - hour5) * 60 + (min3 - min5)
                    }
                  } else if (value.holDay == getDateTime(1, endDate)) {
                    hour1 = parseInt(String(endDate.getHours()).padStart(2, '0'))
                    min1 = parseInt(String(endDate.getMinutes()).padStart(2, '0'))
                    //分段判定，早于工作开始时间，第一次休息前工作时间内，晚于工作结束时间，第一次休息后工作时间内，第一次休息时间
                    if (this.compareTime(hour1, min1, hour2, min2) <= 0) {
                      workMinute = 0
                    } else if (this.compareTime(hour1, min1, hour4, min4) <= 0) {
                      workMinute = (hour1 - hour2) * 60 + (min1 - min2)
                    } else if (this.compareTime(hour1, min1, hour3, min3) >= 0) {
                      workMinute = workMinuteDay
                    } else if (this.compareTime(hour1, min1, hour5, min5) >= 0) {
                      workMinute = (hour1 - hour5) * 60 + (min1 - min5) + (hour4 - hour2) * 60 + (min4 - min2)
                    } else {
                      workMinute = (hour3 - hour5) * 60 + (min3 - min5)
                    }
                  } else {
                    workMinute = workMinuteDay
                  }
                }
              }
              workMinuteAll += workMinute
            })
            if (k == restDays) {
              this.$modal.msgError('请假时间段为休息时间')
            } else {
              var leaveDays = workMinuteAll / workMinuteDay
              var leastMinute = parseInt(this.holidaySetting.minUnitDay) * 60
              console.log('总请假分钟数：' + workMinuteAll)
              console.log('最小请假分钟数：' + leastMinute)
              console.log('请假天数：' + leaveDays)
              if (workMinuteAll % leastMinute == 0) {

                if (this.form.id != null) {
                  this.setForm()
                  updatePersonHoliday(this.form).then(response => {
                    this.$modal.msgSuccess('修改成功')
                    this.open = false
                    this.getList()
                  })
                } else {
                  this.form.leaveShifts = leaveDays
                  this.$modal.msgSuccess('请假时间符合要求')
                  // addPersonHoliday(this.form).then(response => {
                  //   this.$modal.msgSuccess('新增成功')
                  //   this.open = false
                  //   this.getList()
                  // })
                }
              } else {
                this.$modal.msgError('请假时间不合要求')
              }
            }
          }
        )
      }else{
        //起止请假时间小时分钟
        var startHour = parseInt(String(startDate.getHours()).padStart(2, '0'))
        var startMin = parseInt(String(startDate.getMinutes()).padStart(2, '0'))
        var endHour = parseInt(String(endDate.getHours()).padStart(2, '0'))
        var endMin = parseInt(String(endDate.getMinutes()).padStart(2, '0'))
        var params={
          empId: this.form.empNo,
          startDate: getDateTime(1,startDate),
          endDate: getDateTime(1,endDate),
          compId: this.form.compId,
        }
        getShiftCodeByPerson(params).then(response =>{
          this.shiftCodeData = response.rows;
          //总工作分钟数
          var workMinuteAll = 0;
          //一天理论工作分钟数
          var workMinuteDay = parseInt(this.shiftCodeData[0].conHour) * 60 + parseInt(this.shiftCodeData[0].conMin)
          if(response.total!=restDays){
            this.$modal.msgError('请假时间内有未排班天数')
          }else{
            response.rows.forEach((value)=>{
              //工作时间
              var workMinute = 0;
              //工作开始时间
              var hour1 = parseInt(value.startHour)
              var min1 = parseInt(value.startMin)
              //工作持续时间
              var hour2 = parseInt(value.conHour)
              var min2 = parseInt(value.conMin)
              //工作结束时间
              var hour3 = parseInt(value.endHour)
              var min3 = parseInt(value.endMin)
              //是否跨天
              var overDay = (hour1+hour2+Math.floor((min1+min2)/60))>24? 'Y':'N'
              if(value.restCount==0){
                if (overDay ==='Y'){
                  console.log('跨天：'+value.shiftCode)
                  if(value.createDate===getDateTime(1,startDate)){
                    //分段判定，早于工作时间，工作时间内，晚于工作时间
                    if(this.compareTime(startHour,startMin,hour1,min1)<=0){
                      workMinute = workMinuteDay;
                    }else if(this.compareTime(startHour,startMin,hour3,min3)>=0){
                      workMinute = 0;
                    }else{
                      workMinute = (hour3-startHour)*60+(min3-startMin)
                    }
                  }else if(value.createDate===getDateTime(1,endDate)){
                    if(this.compareTime(endHour,endMin,hour1,min1)<=0){
                      workMinute = 0;
                    }else if(this.compareTime(endHour,endHour,hour3,min3)>=0){
                      workMinute = workMinuteDay;
                    }else{
                      workMinute = (endHour-hour1)*60+(endMin-min1)
                    }
                  }else{
                    workMinute = workMinuteDay;
                  }


                }else{
                  console.log('非跨天:'+value.shiftCode)
                  if(value.createDate===getDateTime(1,startDate)){
                    //分段判定，早于工作时间，工作时间内，晚于工作时间
                    if(this.compareTime(startHour,startMin,hour1,min1)<=0){
                      workMinute = workMinuteDay;
                    }else if(this.compareTime(startHour,startMin,hour3,min3)>=0){
                      workMinute = 0;
                    }else{
                      workMinute = (hour3-startHour)*60+(min3-startMin)
                    }
                  }else if(value.createDate===getDateTime(1,endDate)){
                    if(this.compareTime(endHour,endMin,hour1,min1)<=0){
                      workMinute = 0;
                    }else if(this.compareTime(endHour,endHour,hour3,min3)>=0){
                      workMinute = workMinuteDay;
                    }else{
                      workMinute = (endHour-hour1)*60+(endMin-min1)
                    }
                  }else{
                    workMinute = workMinuteDay;
                  }
                  workMinuteAll +=workMinute;
                }
              }
            })
          }
        })
      }
    },
    /** 时间大小比较 */
    compareTime(hour1, min1, hour2, min2) {
      var time1 = parseInt(hour1) * 60 + parseInt(min1)
      var time2 = parseInt(hour2) * 60 + parseInt(min2)
      return time1 - time2
    },
    /**获取班次信息 */
    getShiftCode(e) {
      var shiftCode = {
        shiftCode: e,
        compId: this.form.compId
      }
      queryShiftCode(shiftCode).then(response => {
        this.shiftCodeData = response.data
      })

    },
    /** 初始化数据 */
    initData() {
      this.user.empNo = this.$store.state.user.name
      this.user.empName = this.$store.state.user.userInfo.nickName
      this.user.compId = this.$store.state.user.userInfo.compId
      this.queryParams.compId = this.user.compId
    },
    /** 表单数据配置 */
    setForm(e) {
      this.form.creator = this.user.empName
      this.form.creatorId = this.user.empNo
      this.form.createDate = getDateTime(1)
      if (e == 0) {
        this.form.status = '01'
        this.form.compId = this.queryParams.compId
      }
    },
    /** 查询出勤字典 */
    getDisc() {
      getAttendenceOptions(this.attendenceOptionType).then(response => {
        this.attendenceOptions = response.data
      })
    },
    /** 查询公司列表 */
    getCompanyList() {
      selectCompany().then(response => {
          this.companyList = response.data
        }
      )
    },
    /** 查询员工请假记录列表 */
    getList() {
      this.loading = true
      listPersonHoliday(this.queryParams).then(response => {
        this.personHolidayList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询员工年休假记录 */
    getYearHolidayList() {
      var year = {
        year: new Date().getFullYear(),
        empNo: null,
        compId: null
      }
      yearHoliday(year).then(response => {
        this.yearHoliday = response.rows
      })
    },
    /** 查询员工探亲假记录 */
    getHomeLeaveHolidayList() {
      var home = {
        year: new Date().getFullYear(),
        empNo: null,
        compId: null
      }
      homeLeaveHoliday(home).then(response => {
        this.homeLeaveHoliday = response.rows
      })
    },
    /** 查询条件判定 */
    judgeQuery() {
      if (this.queryParams.empNo === null || this.queryParams.empNo === '') {
        this.$modal.msgError('请输入工号')
        return false
      } else {
        return true
      }
    },

    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },

    // 呈送按钮
    submit() {
      // this.open = false;
      // this.form.status = "03";
      // this.submitForm()
    },

    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        empNo: null,
        empId: null,
        empName: null,
        postname: null,
        postid: null,
        orgParentId: null,
        orgId: null,
        leaTypeId: null,
        isContainHoliday: null,
        description: null,
        remainingDays: null,
        monthDays: null,
        monthHours: null,
        yearDays: null,
        yearHours: null,
        status: null,
        statusCancel: null,
        leaveHours: null,
        leaveShifts: null,
        startDate: null,
        endDate: null,
        creator: null,
        creatorId: null,
        createDate: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery(e) {
      if (e === 0 || this.judgeQuery()) {
        this.queryParams.pageNum = 1
        this.getList()
      }
    },
    //日期范围转换
    dateFormat(picker) {
      if (picker != null && picker != '') {
        this.queryParams.startDate = picker[0]
        this.queryParams.endDate = picker[1]
      }
    },

    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.queryParams.startDate = null
      this.queryParams.endDate = null
      this.handleQuery(0)
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.setForm(0)
      this.open = true
      this.title = '添加员工请假记录'
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.computeManhour()
        }
      })
    },
    /** 详情按钮操作 */
    handleDetails(row) {
      this.reset()
      const id = row.id || this.ids
      getPersonHoliday(id).then(response => {
        this.form = response.data
        this.opencreate = true
        this.title = '员工请假记录详情'
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getPersonHoliday(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改员工请假记录'
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除员工请假记录编号为"' + ids + '"的数据项？').then(function() {
        return delPersonHoliday(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show()
    },
    /** 获取工号 */
    getJobNumber(empId, userName, compId) {
      if (this.open == true) {
        this.form.empNo = empId
        this.form.empName = userName
        queryNewPostNameAndChangeDetail(this.form).then(res => {
          this.form.postname = res.data.list1[0].newPostName
        })
        var params = {
          compId: compId,
          empId: empId
        }
        getClassMasterByPerson(params).then(response => {
          this.shiftMode = response.data.shiftmodeName
          //如果轮班方式是常白班：01
          var cbMode = '01'
          if (this.shiftMode == cbMode) {
            //班次：常白班
            var cbCode = '01'
            var shiftCode = {
              shiftCode: cbCode,
              compId: this.form.compId
            }
            queryShiftCode(shiftCode).then(response => {
              this.shiftCodeData = response.data
            })
          }else{
            // alert(this.shiftMode)
            //getShiftCodeByPerson

          }

        })
      } else {
        this.queryParams.empNo = empId
        this.getList()
      }
    }
  }
}

</script>

<style scoped>
.inputInner {
  width: 30%;
}

</style>
