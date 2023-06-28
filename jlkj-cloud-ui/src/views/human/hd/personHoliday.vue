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
          <el-button v-if="scope.row.status !== '01'"
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
                              style="width: 100%"
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
                              style="width: 100%"
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
              <el-select v-model="form.leaTypeId" placeholder="请选择请假类别">
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
              <el-input v-model="form.remainingDays" type="number" disabled/>
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
      //请假结束日期需大于请假开始日期
      if(this.form.endDate.getTime()>this.form.startDate){
        this.form.compId = this.queryParams.compId;
        this.getLeaHoliday()
      }else{
        this.$modal.msgError("请假结束日期需大于请假开始日期")
      }
    },
    //获取请假假期设定数据
    getLeaHoliday() {
      var params = {
        holidayTypeCode: this.form.leaTypeId,
        compId: this.form.compId
      }
      getCompHolidaySetting(params).then(response => {
        if(response.data){
          this.holidaySetting = response.data
          this.form.isContainHoliday = this.holidaySetting.isIncHol
          this.getShiftMode()
        }else{
          this.$modal.msgError("该公司下未找到该请假类别数据")
        }
      })
    },
    //获取轮班方式和班次数据
    getShiftMode() {
      var params = {
        compId: this.form.compId,
        empId: this.form.empNo
      }
      getClassMasterByPerson(params).then(response => {
        if(response.data){
          this.shiftMode = response.data
          //如果轮班方式是常白班：01
          var cbMode = '01'
          if (this.shiftMode.shiftmodeName === cbMode) {
            //班次：常白班
            var cbCode = '01'
            var shiftCode = {
              shiftmodeId: this.shiftMode.shiftmodeId,
              shiftCode: cbCode
            }
            queryShiftCode(shiftCode).then(response => {
              this.shiftCodeData = response.data
              this.getHolidayTable()
            })
          } else {
            // 如果轮班方式为非常白班
            //请假开始结束时间
            let startDate = this.form.startDate
            let endDate = this.form.endDate
            let preDate = new Date((startDate.getTime() - 24 * 3600 * 1000))
            // let nextDate = endDate
            // nextDate = new Date(nextDate.setDate(nextDate.getDate()+1))
            let params = {
              empId: this.form.empNo,
              startDate: getDateTime(1, preDate),
              endDate: getDateTime(1, endDate),
              compId: this.form.compId
            }
            getShiftCodeByPerson(params).then(response => {
              this.shiftCodeData = response.rows
              console.log('员工排班数据')
              this.shiftCodeData.forEach((value)=>{
                console.log('时间：'+value.createDate+'编码：'+value.shiftCode)
              })
              let days = (new Date(getDateTime(1, endDate)) - new Date(getDateTime(1, startDate))) / (24 * 60 * 60 * 1000)
              //请假跨天数
              let conDays = Math.floor(days) + 1
              console.log('请假跨天数：' + conDays)
              this.conDays = conDays
              if (response.total !== (conDays + 1)) {
                this.$modal.msgError('请假时间段内有未排班天数')
              } else {
                this.computeWorkTime(2)
              }
            })
          }
        }else{
          this.$modal.msgError("该员工在请假期间未排班")
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
        this.computeWorkTime(1)
      })
    },
    //计算请假工时
    computeWorkTime(e) {
      //请假过程是否有问题
      this.errorData.ifError = false
      //员工请假分钟数
      let personMinute = 0
      //请假班次(天数)
      let conDay = 0;
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
          console.log("常白班请假未跨天")
          if(this.holidayTable[0].dateType!=="1"&&this.form.isContainHoliday==='N'){
            console.log("请假失败，请假当天非工作日")
            this.errorData.ifError = false
            this.errorData.errorMsg = "请假失败，请假当天非工作日";
          }else{
            if (this.compareTime(startHour, startMin, personHour1, personMin1) > 0) {
              personHour1 = startHour
              personMin1 = startMin
            }
            if (this.compareTime(personHour2, personMin2, endHour, endMin) > 0) {
              personHour2 = endHour
              personMin2 = endMin
            }
            //请假当天分钟数
            let personConMinute;
            if(this.compareTime(personHour2,personMin2,personHour1,personMin1)>0){
              let obj = {
                startHour: personHour1,
                startMin: personMin1,
                endHour: personHour2,
                endMin: personMin2,
                shiftCodeData: this.shiftCodeData
              }
              personConMinute = this.computeDayMinute(JSON.stringify(obj))
            }else{
              personConMinute = 0;
            }
            console.log('请假当天实际分钟数：' + personConMinute)
            personMinute = personConMinute
          }
        } else {
          console.log("常白班请假已跨天")
          this.holidayTable.forEach((value) => {
            if (this.errorData.ifError !== true) {
              //每日请假分钟数
              let personConMinute;
              if (value.holDay === getDateTime(1, startDate)){
                if(value.dateType==="1"||this.form.isContainHoliday==='Y'){
                  if (this.compareTime(startHour, startMin, personHour1, personMin1) > 0) {
                    personHour1 = startHour
                    personMin1 = startMin
                  }
                  if(this.compareTime(this.shiftCodeData.endHour,this.shiftCodeData.endMin,personHour1,personMin1)>0){
                    let obj = {
                      startHour: personHour1,
                      startMin: personMin1,
                      endHour: this.shiftCodeData.endHour,
                      endMin: this.shiftCodeData.endMin,
                      shiftCodeData: this.shiftCodeData
                    }
                    personConMinute = this.computeDayMinute(JSON.stringify(obj));
                  }else{
                    personConMinute = 0;
                  }
                }else{
                  personConMinute = 0;
                }
              } else if (value.holDay === getDateTime(1, endDate)) {
                if(value.dateType==="1"||this.form.isContainHoliday==='Y'){
                  if (this.compareTime(personHour2, personMin2, endHour, endMin) > 0) {
                    personHour2 = endHour
                    personMin2 = endMin
                  }
                  if(this.compareTime(personHour2,personMin2,this.shiftCodeData.startHour,this.shiftCodeData.startMin)>0){
                    let obj = {
                      startHour: this.shiftCodeData.startHour,
                      startMin: this.shiftCodeData.startMin,
                      endHour: personHour2,
                      endMin: personMin2,
                      shiftCodeData: this.shiftCodeData
                    }
                    personConMinute = this.computeDayMinute(JSON.stringify(obj));
                  }else{
                    personConMinute = 0;
                  }
                }else{
                  personConMinute = 0;
                }
              } else {
                if(value.dateType==="1"||this.form.isContainHoliday==='Y'){
                  personConMinute = workMinuteDay;
                }else{
                  personConMinute =0;
                }
              }
              personMinute += personConMinute;
            }
          })
        }
        if (personMinute % leastMinute !== 0 || personMinute===0) {
          this.errorData.ifError = true
          this.errorData.errorMsg = "请假失败，请假时长不满足要求";
        }else{
          conDay =  parseInt(personMinute) / parseInt(oneTurnMinute) * 100 / 100
        }
      } else {
        //倒班计算
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
          if(conMinute!==0){
            if (value.createDate === getDateTime(1, startDate)) {
              startData = value
            } else if (value.createDate === getDateTime(1, endDate)) {
              endData = value
            } else if (value.createDate === getDateTime(1, firstDate)) {
              if (this.ifOverDay(JSON.stringify(value))) {
                firstData = value
              }
            } else if (value.createDate === getDateTime(1, lastDate)) {
              personMinute += conMinute;
              conDay +=1;
              if (this.ifOverDay(JSON.stringify(value))) {
                lastData = value
              }
            } else {
              personMinute += conMinute
              conDay +=1;
            }
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
          firstMinute = this.computeDayMinute(JSON.stringify(obj))
          console.log('请假前一天请假时间分钟数：'+firstMinute)
          personMinute +=firstMinute;
          leastMinute = (parseInt(firstData.conHour)*60+parseInt(firstData.conMin))*this.holidaySetting.minUnitTurns
          if(firstMinute%leastMinute!==0){
            this.errorData.ifError = true;
            this.errorData.errorMsg=("前一天请假时间不合规范");
            console.log('前一天请假时间不合规范')
          }else{
            let firstDay = firstMinute/(parseInt(firstData.conHour)*60+parseInt(firstData.conMin));
            conDay += firstDay
            console.log('前一天请假时间符合规范')
            console.log('前一天请假班次：'+firstDay)
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
              if (!this.ifOverDay(JSON.stringify(startData))) {
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
                  startMinute = this.computeDayMinute(JSON.stringify(obj))
                }
              } else {
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
            }else{
              //不跨天
              if (!this.ifOverDay(JSON.stringify(startData))) {
                if(this.compareTime(personHour2,personMin2,startData.startHour,startData.startMin)<0||this.compareTime(personHour1,personMin1,startData.endHour,startData.endMin)>0){
                  startMinute = 0;
                  this.errorData.errorMsg=("请假时长为0");
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
                  startMinute = this.computeDayMinute(JSON.stringify(obj))
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
                  startMinute = this.computeDayMinute(JSON.stringify(obj))
                  console.log('第一天请假时间分钟数：'+startMinute);
                  personMinute += startMinute
                }

              }
            }
            leastMinute = (parseInt(startData.conHour) * 60 + parseInt(startData.conMin)) * this.holidaySetting.minUnitTurns
            if (startMinute % leastMinute !== 0) {
              this.errorData.ifError = true
              this.errorData.errorMsg = '第一天请假时间不合规范';
              console.log('第一天请假时间不合规范')
            } else {
              let startDay =startMinute/(parseInt(startData.conHour) * 60 + parseInt(startData.conMin))
              conDay += startDay
              console.log('第一天请假时间符合规范')
              console.log('第一天请假班次：'+startDay)
            }
          }
          //最后一天请假时间计算
          if (endData != null) {
            if (this.conDays === 2&&this.ifOverDay(JSON.stringify(startData))) {
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
              if(this.compareTime(personHour2,personMin2,endData.endHour,endData.endMin)>0&&!this.ifOverDay(JSON.stringify(endData))){
                endEndHour = endData.endHour;
                endEndMin = endData.endMin;
              }else{
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
              leastMinute = (parseInt(endData.conHour) * 60 + parseInt(endData.conMin)) * this.holidaySetting.minUnitTurns
              if (endMinute % leastMinute !== 0) {
                this.errorData.ifError = true
                this.errorData.errorMsg = '最后一天请假时间不合规范';
                console.log('最后一天请假时间不合规范')
              } else {
                let endDay =endMinute/(parseInt(endData.conHour) * 60 + parseInt(endData.conMin))
                conDay += endDay
                console.log('最后一天请假时间符合规范')
                console.log('最后一天请假班次：'+endDay)
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
              //倒数第二天实际请假差值时间
              lastMinute = this.computeDayMinute(JSON.stringify(obj))
              let realLastMinute = (parseInt(lastData.conHour) * 60 + parseInt(lastData.conMin))-lastMinute
              console.log('倒数第二天请假分钟为：' + realLastMinute)
              personMinute -= lastMinute
              leastMinute = (parseInt(lastData.conHour) * 60 + parseInt(lastData.conMin)) * this.holidaySetting.minUnitTurns
              if (realLastMinute % leastMinute !== 0) {
                this.errorData.ifError = true
                this.errorData.errorMsg = '倒数第二天请假时间不合规范';
                console.log('倒数第二天请假时间不合规范')
              } else {
                let lastDay = lastMinute/(parseInt(lastData.conHour) * 60 + parseInt(lastData.conMin))
                conDay -= lastDay
                console.log('倒数第二天请假时间符合规范')
                console.log('倒数第二天请假班次：'+(1-lastDay))
              }
            } else {
              endMinute = 0
              console.log('最后一天请假分钟为：' + endMinute)
              leastMinute = (parseInt(endData.conHour) * 60 + parseInt(endData.conMin)) * this.holidaySetting.minUnitTurns
              if (endMinute % leastMinute !== 0) {
                this.errorData.ifError = true
                this.errorData.errorMsg = '最后一天请假时间不合规范';
                console.log('最后一天请假时间不合规范')
              } else {
                let endDay =endMinute/(parseInt(endData.conHour) * 60 + parseInt(endData.conMin))
                conDay += endDay
                console.log('最后一天请假时间符合规范')
                console.log('最后一天请假班次：'+endDay)
              }
            }
          }

      }
      console.log('员工请假总分钟数：' + personMinute)
      console.log('员工请假总天/班次数：' + conDay)
      if (this.errorData.ifError === false&&personMinute!==0) {
        console.log('请假时间符合要求')
        this.form.leaveShifts = conDay
        let param = {
          year: this.form.startDate.getFullYear(),
          empNo: this.form.empNo,
          compId: this.form.compId,
          companyid: this.form.compId,
        }
        if(this.form.leaTypeId==='07'){
          homeLeaveHoliday(param).then(response => {
            this.homeLeaveHoliday = response
            alert(JSON.stringify(response))
          })
        }else if(this.form.leaTypeId==='09'){
          yearHoliday(param).then(response => {
            this.yearHoliday = response.data
            //总可休天数
            let preYearDays = this.yearHoliday.preYearDays
            this.form.remainingDays = preYearDays;
            //已休天数
            let restDays = this.yearHoliday.restDays;
            this.form.yearDays = this.yearHoliday.restDays;
            if((restDays+this.form.leaveShifts)>preYearDays){
              this.$modal.msgError("年休假剩余可休时长不足！")
            }
          })
        }else {
          this.$modal.msgSuccess('请假成功')
        }
        return true
      } else {
        console.log('请假时间不符合要求')
        this.$modal.msgError(this.errorData.errorMsg);
        this.form.leaveShifts = null;
        return false
      }
    },
    //计算某班次是否跨天
    ifOverDay(json){
      let obj = {}
      //参数对象转化
      if (json != null) {
        obj = JSON.parse(json)
      }
      let restMin = 0;
      for(let i=1; i<=obj.restCount; i++ ){
        if(i===1){
          restMin += parseInt(obj.restConMin)
        }else{
          restMin += parseInt(obj['restConMin'+i])
        }
      }
      let hour = (parseInt(obj.conHour)*60+parseInt(obj.conMin)+restMin)/60
      return (parseInt(obj.endHour)<parseInt(obj.startHour))||(hour - 24 >= 0)
    },
    //计算某天请假时间
    computeDayMinute(json) {
      let obj = {}
      //参数对象转化
      if (json != null) {
        obj = JSON.parse(json)
      }
      if(this.compareTime(obj.shiftCodeData.conHour,obj.shiftCodeData.conMin,0,0)!==0){
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
      }else{
        return 0;
      }
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
      if (e === 0) {
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
          this.personHolidayProcess();
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
      if (this.open === true) {
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
  width: 32%;
}

</style>
