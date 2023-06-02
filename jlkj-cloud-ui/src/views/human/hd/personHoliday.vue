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
        <el-button @click="personHolidayProcess">请假</el-button>
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
import '@/assets/styles/humanStyles.scss'
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
import { queryShiftCode, getShiftCodeByPerson } from '@/api/human/hd/shiftCode'

export default {
  name: 'PersonHoliday',
  dicts: ['sys_yes_no'],
  components: { DictTagHumanBasis, selectUser },
  data() {
    return {
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
      //请假跨天数
      conDays: null,
      //所选假别参数设定
      holidaySetting: {},
      //公司节假日设定数据
      holidayTable: [],
      //请假错误信息存取
      errorData: {
        ifError: null,
        errorMsg: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        totalDays: [
          { required: true, message: '不能为空', trigger: 'change' }
        ],
        empNo: [
          { required: true, message: '员工工号不能为空', trigger: 'blur' }
        ],
        leaTypeId: [
          { required: true, message: '请选择请假类别', trigger: 'change' }
        ],
        startDate: [
          { required: true, message: '请假开始时间不能为空', trigger: 'change' }
        ],
        endDate: [
          { required: true, message: '请假结束时间不能为空', trigger: 'change' }
        ]
      }
    }
  },
  watch: {},
  created() {
    this.initData()
    this.getCompanyList()
    this.getList()
    this.getDisc()
  },
  methods: {
    //请假流程
    personHolidayProcess() {
      this.form.compId = 'J00'
      this.getLeaHoliday()
    },
    //获取请假假期设定数据
    getLeaHoliday() {
      var params = {
        holidayTypeCode: this.form.leaTypeId,
        compId: this.form.compId
      }
      getCompHolidaySetting(params).then(response => {
        this.holidaySetting = response.data
        console.log('请假假期数据：' + JSON.stringify(response.data))
        this.form.isContainHoliday = this.holidaySetting.isIncHol
        this.getShiftMode()
      })
    },
    //获取轮班方式和班次数据
    getShiftMode() {
      var params = {
        compId: this.form.compId,
        empId: this.form.empNo
      }
      getClassMasterByPerson(params).then(response => {
        this.shiftMode = response.data
        console.log('员工工号为' + this.form.empNo + '的轮班方式编码为:' + this.shiftMode.shiftmodeName)
        //如果轮班方式是常白班：01
        var cbMode = '01'
        if (this.shiftMode.shiftmodeName == cbMode) {
          //班次：常白班
          var cbCode = '01'
          var shiftCode = {
            shiftmodeId: this.shiftMode.shiftmodeId,
            shiftCode: cbCode
          }
          queryShiftCode(shiftCode).then(response => {
            this.shiftCodeData = response.data
            console.log('常白班的班次数据为：' + JSON.stringify(this.shiftCodeData))
            this.getHolidayTable()
          })
        } else {
          // 如果轮班方式为非常白班
          //请假开始结束时间
          let startDate = this.form.startDate
          let endDate = this.form.endDate
          let preDate = new Date((startDate.getTime() - 1 * 24 * 3600 * 1000))
          // let nextDate = endDate
          // nextDate = new Date(nextDate.setDate(nextDate.getDate()+1))
          let params = {
            empId: this.form.empNo,
            startDate: getDateTime(1, preDate),
            endDate: getDateTime(1, endDate),
            compId: this.form.compId
          }
          console.log(JSON.stringify(params))
          getShiftCodeByPerson(params).then(response => {
            this.shiftCodeData = response.rows
            console.log('员工排班数据' + JSON.stringify(response.rows))
            let days = (new Date(getDateTime(1, endDate)) - new Date(getDateTime(1, startDate))) / (1 * 24 * 60 * 60 * 1000)
            //请假跨天数
            let conDays = Math.floor(days) + 1
            console.log('请假跨天数：' + conDays)
            this.conDays = conDays
            if (response.total != (conDays + 1)) {
              this.$modal.msgError('请假时间段内有未排班天数')
            } else {
              this.computeWorkTime(2)
            }
            // if(this.computeWorkTime(2)===false){
            //   this.$modal.msgError('请假时间不符规范')
            // }else{
            //   this.$modal.msgSuccess('请假成功')
            // }
          })
        }
      })
    },
    //获取节假日设定数据
    getHolidayTable() {
      //假日设定:1,全日班，2,休息班，3,法定假日
      const dateType1 = 1
      const dateType2 = 2
      const dateType3 = 3
      let dateType = (this.form.isContainHoliday === 'Y') ? null : dateType1
      var param = {
        compId: this.form.compId,
        startDate: getDateTime(1, this.form.startDate),
        endDate: getDateTime(1, this.form.endDate),
        dateType: dateType
      }
      getHoliday(param).then(response => {
        this.holidayTable = response.rows
        console.log('节假日设定数据' + JSON.stringify(this.holidayTable))
        if (this.computeWorkTime(1) === false) {
          this.$modal.msgError('请假时间不符规范')
        } else {
          this.$modal.msgSuccess('请假成功')
        }
      })
    },
    //计算请假工时
    computeWorkTime(e) {
      //请假是否有问题
      this.errorData.ifError = false
      //员工请假分钟数
      let personMinute = 0
      //一班次时间工作分钟数
      let oneTurnMinute
      //请假开始结束时间
      let startDate = this.form.startDate
      let endDate = this.form.endDate
      //第一天最后一天请假小时分钟
      let personHour1 = parseInt(String(startDate.getHours()).padStart(2, '0'))
      let personMin1 = parseInt(String(startDate.getMinutes()).padStart(2, '0'))
      let personHour2 = parseInt(String(endDate.getHours()).padStart(2, '0'))
      let personMin2 = parseInt(String(endDate.getMinutes()).padStart(2, '0'))
      //常白班逻辑
      if (e === 1) {
        //工作开始结束时间
        let startHour = parseInt(this.shiftCodeData.startHour)
        let startMin = parseInt(this.shiftCodeData.startMin)
        let endHour = parseInt(this.shiftCodeData.endHour)
        let endMin = parseInt(this.shiftCodeData.endMin)
        //常白班一天工作分钟
        let workMinuteDay = parseInt(this.shiftCodeData.conHour) * 60 + parseInt(this.shiftCodeData.conMin)
        oneTurnMinute = workMinuteDay
        //请假最小单位数（小时）
        let leastMinute = parseInt(this.holidaySetting.minUnitDay) * 60
        //请假未跨天
        if (getDateTime(1, startDate) === getDateTime(1, endDate)) {
          if (this.compareTime(startHour, startMin, personHour1, personMin1) > 0) {
            personHour1 = startHour
            personMin1 = startMin
          }
          if (this.compareTime(personHour2, personMin2, endHour, endMin) > 0) {
            personHour2 = endHour
            personMin2 = endMin
          }
          //请假当天总时间分钟数
          let personConMinute = this.compareTime(personHour2, personMin2, personHour1, personMin1)
          let obj = {
            startHour: personHour1,
            startMin: personMin1,
            endHour: personHour2,
            endMin: personMin2,
            shiftCodeData: this.shiftCodeData
          }
          let json = JSON.stringify(obj)
          personConMinute = this.computeDayMinute(json)
          console.log('请假当天实际分钟数：' + personConMinute)
          if (personConMinute % leastMinute !== 0) {
            this.errorData.ifError = true
          } else {
            personMinute = personConMinute
            this.form.leaveShifts = parseInt((personMinute / oneTurnMinute) * 100) / 100
          }
        } else {
          this.holidayTable.forEach((value) => {
            if (this.errorData.ifError !== true) {
              if (value.holDay === getDateTime(1, startDate)) {
                //请假开始时间早于当天工作结束时间
                if (this.compareTime(personHour1, personMin1, endHour, endMin) < 0) {
                  //请假开始时间晚于当天工作开始时间
                  if (this.compareTime(personHour1, personMin1, startHour, startMin) > 0) {
                    //请假第一天总时间分钟数
                    let personConMinute = this.compareTime(endHour, endMin, personHour1, personMin1)
                    console.log('请假第一天总时间为：' + personConMinute)
                    let beforeRest = false
                    for (let i = 1; i <= this.shiftCodeData.restCount; i++) {
                      let restEndHour, restEndMin, restConMin
                      if (i === 1) {
                        restEndHour = parseInt(this.shiftCodeData.restEndHour)
                        restEndMin = parseInt(this.shiftCodeData.restEndMin)
                        restConMin = parseInt(this.shiftCodeData.restConMin)
                      } else {
                        restEndHour = parseInt(this.shiftCodeData['restEndHour' + i])
                        restEndMin = parseInt(this.shiftCodeData['restEndMin' + i])
                        restConMin = parseInt(this.shiftCodeData['restConMin' + i])
                      }
                      if (!beforeRest) {
                        let condition = this.compareTime(restEndHour, restEndMin, personHour1, personMin1)
                        if (condition >= restConMin) {
                          personConMinute -= restConMin
                          beforeRest = true
                        } else if (condition >= 0 && condition < restConMin) {
                          personConMinute -= condition
                          beforeRest = true
                        }
                      } else {
                        personConMinute -= restConMin
                      }
                    }
                    //请假第一天实际工作分钟数
                    console.log('请假第一天实际工作分钟数：' + personConMinute)
                    if (personConMinute % leastMinute !== 0) {
                      this.errorData.ifError = true
                    }
                    personMinute += personConMinute
                  } else {
                    personMinute += workMinuteDay
                  }
                } else {
                  personMinute += 0
                }
              } else if (value.holDay === getDateTime(1, endDate)) {
                //请假结束时间晚于当天工作开始时间
                if (this.compareTime(personHour2, personMin2, startHour, startMin) > 0) {
                  //请假结束时间早于当天工作结束时间
                  if (this.compareTime(personHour2, personMin2, endHour, endMin) < 0) {
                    //请假最后一天总分钟数
                    let personConMinute = this.compareTime(personHour2, personMin2, startHour, startMin)
                    console.log('请假最后一天总时间为：' + personConMinute)
                    let afterRest = false
                    for (let i = this.shiftCodeData.restCount; i >= 1; i--) {
                      let restStartHour, restStartMin, restConMin
                      if (i === 1) {
                        restStartHour = parseInt(this.shiftCodeData.restStartHour)
                        restStartMin = parseInt(this.shiftCodeData.restStartMin)
                        restConMin = parseInt(this.shiftCodeData.restConMin)
                      } else {
                        restStartHour = parseInt(this.shiftCodeData['restStartHour' + i])
                        restStartMin = parseInt(this.shiftCodeData['restStartMin' + i])
                        restConMin = parseInt(this.shiftCodeData['restConMin' + i])
                      }
                      if (!afterRest) {
                        let condition = this.compareTime(personHour2, personMin2, restStartHour, restStartMin)
                        if (condition >= restConMin) {
                          personConMinute -= restConMin
                          console.log('最后一天某阶段分钟长' + personConMinute)
                          afterRest = true
                        } else if (condition >= 0 && condition < restConMin) {
                          personConMinute -= condition
                          console.log('最后一天某阶段分钟长' + personConMinute)
                          afterRest = true
                        }
                      } else {
                        personConMinute -= restConMin
                        console.log('最后一天某阶段分钟长' + personConMinute)
                      }
                    }
                    //请假最后一天实际工作分钟数
                    console.log('请假最后一天实际工作分钟数：' + personConMinute)
                    if (personConMinute % leastMinute !== 0) {
                      this.errorData.ifError = true
                    }
                    personMinute += personConMinute
                  } else {
                    personMinute += workMinuteDay
                  }
                } else {
                  personMinute += 0
                }
              } else {
                personMinute += workMinuteDay
              }
            }
          })
        }
        if (personMinute % leastMinute !== 0) {
          this.errorData.ifError = true
        }
      } else {
        //倒班计算
        //请假前一天与倒数第二天时间
        let firstDate = new Date((startDate.getTime() - 1 * 24 * 3600 * 1000))
        let lastDate = new Date((endDate.getTime() - 1 * 24 * 3600 * 1000))
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
          if (value.createDate === getDateTime(1, startDate)) {
            startData = value
          } else if (value.createDate === getDateTime(1, endDate)) {
            endData = value
          } else if (value.createDate === getDateTime(1, firstDate)) {
            if (endHour <= startHour) {
              firstData = value
            }
          } else if (value.createDate === getDateTime(1, lastDate)) {
            personMinute += conMinute
            if (endHour <= startHour) {
              lastData = value
            }
          } else {
            personMinute += conMinute
          }
        })
        //前一天请假时间计算
        if(firstData!=null){
          let firstStartHour,firstStartMin
          if(this.compareTime(personHour1,personMin1,firstData.endHour,firstData.endMin)>0){
            firstStartHour = firstData.endHour;
            firstStartMin = firstData.endMin;
          }else{
            firstStartHour = personHour1;
            firstStartMin = personMin1
          }
          let obj = {
            startHour:parseInt(firstStartHour)+24,
            startMin:firstStartMin,
            endHour:parseInt(firstData.endHour)+24,
            endMin:firstData.endMin,
            shiftCodeData: firstData
          }
          let json = JSON.stringify(obj)
          firstMinute = this.computeDayMinute(json)
          console.log('请假前一天请假时间分钟数：'+firstMinute)
          personMinute +=firstMinute;
          leastMinute = (parseInt(firstData.conHour)*60+parseInt(firstData.conMin))*this.holidaySetting.minUnitTurns
          if(firstMinute%leastMinute!=0){
            this.errorData.ifError = true;
            console.log('前一天请假时间不合规范')
          }else{
            console.log('前一天请假时间符合规范')
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
            if(this.conDays!==1){
              //不跨天
              if (endHour > startHour) {
                if(this.compareTime(startStartHour,startStartMin,startData.endHour,startData.endMin)>=0){
                  startMinute = 0;
                }else{
                  let obj = {
                    startHour: startStartHour,
                    startMin: startStartMin,
                    endHour: startData.endHour,
                    endMin: startData.endMin,
                    shiftCodeData: startData
                  }
                  let json = JSON.stringify(obj)
                  startMinute = this.computeDayMinute(json)
                }
              } else {
                let obj = {
                  startHour: startStartHour,
                  startMin: startStartMin,
                  endHour: parseInt(startData.endHour) + 24,
                  endMin: parseInt(startData.endMin),
                  shiftCodeData: startData
                }
                let json = JSON.stringify(obj)
                startMinute = this.computeDayMinute(json)
              }
              console.log('第一天请假时间分钟数：' + startMinute)
              personMinute += startMinute
            }else{
              //不跨天
              if (endHour > startHour) {
                if(this.compareTime(personHour2,personMin2,startData.startHour,startData.startMin)<0||this.compareTime(personHour1,personMin1,startData.endHour,startData.endMin)>0){
                  startMinute = 0;
                  this.errorData.ifError = true;
                }else{
                  if(this.compareTime(personHour2,personMin2,startData.endHour,startData.endMin)>0){
                    personHour2 = startData.endHour;
                    personMin2 = startData.endMin;
                  }
                  if(this.compareTime(personHour1,personMin1,startData.startHour,startData.startMin)<0){
                    personHour1 = startData.startHour;
                    personMin1 = startData.startMin;
                  }
                  let obj = {
                    startHour: personHour1,
                    startMin: personMin1,
                    endHour: personHour2,
                    endMin: personMin2,
                    shiftCodeData: startData
                  }
                  let json = JSON.stringify(obj)
                  startMinute = this.computeDayMinute(json)
                  console.log('第一天请假时间分钟数：'+startMinute);
                  personMinute += startMinute
                }
              }else{
                //跨天
                if(this.compareTime(personHour2,personMin2,startData.startHour,startData.startMin)<0){
                  startMinute = 0;
                  this.errorData.ifError = true;
                }else{
                  if(this.compareTime(personHour1,personMin1,startData.startHour,startData.startMin)<0){
                    personHour1 = startData.startHour;
                    personMin1 = startData.startMin;
                  }
                  let obj = {
                    startHour: personHour1,
                    startMin: personMin1,
                    endHour: personHour2,
                    endMin: personMin2,
                    shiftCodeData: startData
                  }
                  let json = JSON.stringify(obj)
                  startMinute = this.computeDayMinute(json)
                  console.log('第一天请假时间分钟数：'+startMinute);
                  personMinute += startMinute
                }

              }
            }
            leastMinute = (parseInt(startData.conHour) * 60 + parseInt(startData.conMin)) * this.holidaySetting.minUnitTurns
            if (startMinute % leastMinute != 0) {
              this.errorData.ifError = true
              console.log('第一天请假时间不合规范')
            } else {
              console.log('第一天请假时间符合规范')
            }
          }
          //最后一天请假时间计算
          if (endData != null) {
            if (this.conDays === 2&&this.compareTime(startData.startHour,startData.startMin,startData.endHour,startData.endMin)>=0) {
              lastData = startData;
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
            if (this.compareTime(personHour2, personMin2, endData.startHour, endData.startMin) > 0) {
              endStartHour = endData.startHour
              endStartMin = endData.startMin
              endEndHour = personHour2
              endEndMin = personMin2
              let obj = {
                startHour: endStartHour,
                startMin: endStartMin,
                endHour: endEndHour,
                endMin: endEndMin,
                shiftCodeData: endData
              }
              let json = JSON.stringify(obj)
              endMinute = this.computeDayMinute(json)
              console.log('最后一天请假分钟为：' + endMinute)
              personMinute += endMinute
              leastMinute = (parseInt(endData.conHour) * 60 + parseInt(endData.conMin)) * this.holidaySetting.minUnitTurns
              if (endMinute % leastMinute !== 0) {
                this.errorData.ifError = true
                console.log('最后一天请假时间不合规范')
              } else {
                console.log('最后一天请假时间符合规范')
              }
            } else if (lastData != null && this.compareTime(personHour2, personMin2, lastData.endHour, lastData.endMin) <= 0) {

              endStartHour = personHour2
              endStartMin = personMin2
              endEndHour = lastData.endHour
              endEndMin = lastData.endMin
              let obj = {
                startHour: parseInt(endStartHour) + 24,
                startMin: endStartMin,
                endHour: parseInt(endEndHour) + 24,
                endMin: endEndMin,
                shiftCodeData: lastData
              }
              let json = JSON.stringify(obj)
              //倒数第二天实际请假时间
              lastMinute = this.computeDayMinute(json)
              console.log('倒数第二天差值请假分钟为：' + lastMinute)
              personMinute -= lastMinute
              leastMinute = (parseInt(lastData.conHour) * 60 + parseInt(lastData.conMin)) * this.holidaySetting.minUnitTurns
              if (((parseInt(lastData.conHour) * 60 + parseInt(lastData.conMin)) - lastMinute) % lastMinute !== 0) {
                this.errorData.ifError = true
                console.log('倒数第二天请假时间不合规范')
              } else {
                console.log('倒数第二天请假时间符合规范')
              }
            } else {
              endMinute = 0
              console.log('最后一天请假分钟为：' + endMinute)
              leastMinute = (parseInt(endData.conHour) * 60 + parseInt(endData.conMin)) * this.holidaySetting.minUnitTurns
              if (endMinute % leastMinute != 0) {
                this.errorData.ifError = true
                console.log('最后一天请假时间不合规范')
              } else {
                console.log('最后一天请假时间符合规范')
              }
            }
          }

      }
      console.log('员工请假总分钟数：' + personMinute)
      if (this.errorData.ifError === false) {
        console.log('请假时间符合要求')
        this.form.leaveShifts = parseInt((personMinute / oneTurnMinute) * 100) / 100
        return true
      } else {
        console.log('请假时间不符合要求')
        return false
      }
    },
    //计算某天请假时间
    computeDayMinute(json) {
      let obj = {}
      //参数对象转化
      if (json != null) {
        obj = JSON.parse(json)
      }
      //跨天数据转化
      if (parseInt(obj.shiftCodeData.endHour) <= parseInt(obj.shiftCodeData.startHour)) {
        obj.shiftCodeData.endHour = parseInt(obj.shiftCodeData.endHour) + 24
        if (parseInt(obj.restStartHour) <= parseInt(obj.shiftCodeData.startHour)) {
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
        if (this.errorData.ifError !== true && stop !== true) {
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
    },
    /** 时间大小比较 */
    compareTime(hour1, min1, hour2, min2) {
      var time1 = parseInt(hour1) * 60 + parseInt(min1)
      var time2 = parseInt(hour2) * 60 + parseInt(min2)
      return time1 - time2
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
      this.form.status = '03'
      this.open = false
      this.submitForm()
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
    getJobNumber(empNo, userName, compId) {
      if (this.open == true) {
        this.form.empNo = empNo
        this.form.empName = userName
        this.form.compId = compId
        queryNewPostNameAndChangeDetail(this.form).then(res => {
          this.form.postname = res.data.list1[0].newPostName
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
