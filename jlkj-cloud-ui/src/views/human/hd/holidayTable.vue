<template>
  <div class="app-container" v-loading="loading">
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
      <el-form-item label="月" prop="month">
        <el-date-picker
          v-model="queryParams.queryMonth"
          :clearable="false"
          type="month"
          value-format="yyyy-MM-dd"
          placeholder="选择月"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="setYearData">生成全年的数据</el-button>
      </el-form-item>
    </el-form>
    <human-calendar style="width: 100%;" :value="this.queryParams.queryMonth" >
      <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
      <template slot="dateCell" slot-scope="{date, data}" align="center">
        <p style="width: 100%;height:100%;display: flex;justify-content: center;align-items: center; padding-bottom: 30px;" @click="selectData(data)" >
          <span style="margin-top: 2px">{{ data.day.split('-').slice(2).join('-') }}</span>
          <span
                style="display: flex;justify-content: center;align-items: center; flex-direction: row"
                v-show="data.type==='current-month'"
          ><<dict-tag-human-base :options="attendenceOptions.dateType"
                                 :value="+holidayTableList[parseInt(data.day.substring(5,7))-1]['day'+parseInt(data.day.substring(8,10))]"
          />></span>
        </p>
      </template>
    </human-calendar>

    <!-- 日期类别选择对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="250px" append-to-body >
      <div style="display: flex;flex-direction: column; align-items: center">
        <div :class="headClass">
          日期类别
        </div>
        <div v-if="render" v-for="o in attendenceOptions.dateType" :key="o.dicNo" :class="unionClass[o.dicNo-1]" @click="selectChange(o.dicNo,selectDatas)" @mouseover="mouseOver(2,o.dicNo-1)" @mouseleave="mouseOver(1,o.dicNo-1)" >
          {{ o.dicName }}
        </div>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import {
  listHolidayTable,
  getHolidayTable,
  delHolidayTable,
  addHolidayTable,
  updateHolidayTable,
  addYearHolidayTable
} from '@/api/human/hd/holidayTable'
import { getDateTime } from '@/api/human/hd/ahumanutils'
import { getAttendenceOptions } from '@/api/human/hd/attendenceBasis'
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import humanCalendar from '@/views/components/human/humanCalendar/src/main'
import DictTagHumanBase from '@/views/components/human/dictTag/humanBaseInfo'

export default {
  name: 'HolidayTable',
  dicts: ['sys_normal_disable'],
  components: { humanCalendar, DictTagHumanBase },
  data() {
    return {
      //样式控制
      childClass:'text item child',
      headClass: 'text item head',
      unionClass: [],
      render: true,
      //选中数据
      selectDatas:{},
      //页面刷新
      refreshPage: true,
      //日期类型数组
      dayDataType: [],
      // 节假日设定表格数据
      holidayTableList: [],
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['dateType']
      },
      //出勤选单选项列表
      attendenceOptions: {},
      //公司数据
      companyList: [],
      //登录人工号
      userEmpId: this.$store.state.user.name,
      //登录人姓名
      nickName: this.$store.state.user.userInfo.nickName,
      //登录人公司
      logincompId: this.$store.state.user.userInfo.compId,
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
      // 弹出层标题
      title: '更新日期类别',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
        queryYear: null,
        queryMonth: new Date(),
        year: new Date().getFullYear(),
        month: null
      },
      //大月份
      bigMonth: [1, 3, 5, 7, 8, 10, 12],
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    }
  },
  created() {
    this.init()
    this.getDisc()
    this.getList()
    this.getCompanyList()
  },
  computed: {},
  watch: {
    'queryParams.queryMonth'(val) {
      var nowDate = val
      if (this.queryParams.year != (nowDate.substring(0, 4))) {
        this.init(1)
        this.queryParams.year = nowDate.substring(0, 4)
        this.getList()
      }
    }
  },
  methods: {
    //鼠标悬停
    mouseOver(e,no){
      if(e==1){
        this.unionClass[no] = this.childClass
      }else{
        this.unionClass[no] = this.headClass
      }
      this.render= false;
      this.render= true;
    },
    //弹出类别选择页
    selectData(e) {
      if(e.type==='current-month'){
        this.selectDatas= e;
        this.open = true;
      }
    },
    //页面数据初始化
    init(e) {
      if (e == 1) {
        this.holidayTableList = []
      }
      //大月
      var bigMonth = this.bigMonth
      //年份
      var year = this.queryParams.year
      //当年1号星期
      var k = new Date(this.queryParams.year + '-01-01').getDay()
      for (var i = 1; i < 13; i++) {
        var Class = {
          order: null,
          month: null
        }
        Class.order = i
        Class.month = i + '月'
        var monthDays
        if (bigMonth.indexOf(i) != -1) {
          monthDays = 32
        } else if (i == 2) {
          var o = ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) ? 1 : 0
          monthDays = 29 + o
        } else {
          monthDays = 31
        }
        for (var j = 1; j < monthDays; j++) {
          var t = k % 7
          k++
          Class['day' + j] = null
        }
        this.holidayTableList.push(Class)
      }
    },
    /** 生成全年数据按钮操作 */
    setYearData() {
      //大月
      var bigMonth = this.bigMonth
      //年份
      var year = this.queryParams.year
      //当年1号星期
      var k = new Date(this.queryParams.year + '-01-01').getDay()
      //周配置
      var Week = ['2', '1', '1', '1', '1', '1', '2']
      for (var i = 1; i < 13; i++) {
        var monthDays
        if (bigMonth.indexOf(i) != -1) {
          monthDays = 32
        } else if (i == 2) {
          var o = ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) ? 1 : 0
          monthDays = 29 + o
        } else {
          monthDays = 31
        }
        for (var j = 1; j < monthDays; j++) {
          var t = k % 7
          k++
          this.holidayTableList[parseInt(i) - 1]['day' + parseInt(j)] = Week[t]
        }
      }
      this.loading = true
      var obj = {
        compId: this.queryParams.compId,
        year: this.queryParams.year,
        creator: this.nickName,
        creatorId: this.userEmpId,
        createDate: getDateTime(1)
      }
      addYearHolidayTable(obj).then(response => {
        this.$modal.msgSuccess('全年数据生成成功')
        this.loading = false
      })
    },
    //日期类型改变
    selectChange(e, data) {
      // this.loading = true
      var oneDay = data.day
      var year = oneDay.substring(0, 4)
      var month = oneDay.substring(5, 7)
      var day = oneDay.substring(8, 10)
      var chosenDate = new Date(oneDay)
      var firstDate = new Date(year + '-' + '01-01')
      const days = (chosenDate - firstDate) / (1 * 24 * 60 * 60 * 1000)
      var holidayObj = {
        compId: this.queryParams.compId,
        year: year,
        month: month,
        day: day,
        dateType: e,
        holDay: chosenDate,
        creator: this.nickName,
        creatorId: this.$store.state.user.name,
        createDate: getDateTime(0)
      }
      this.loading = true;
      addHolidayTable(holidayObj).then(response => {
        this.$modal.msgSuccess('新增成功')
        this.holidayTableList[parseInt(oneDay.substring(5,7))-1]['day'+parseInt(oneDay.substring(8,10))] = e;
        this.loading = false;
        this.open = false;
      })
    },
    //获取出勤字典(假别类型查询)
    getDisc() {
      getAttendenceOptions(this.attendenceOptionType).then(response => {
        this.attendenceOptions = response.data;
        this.attendenceOptions.dateType.forEach((value,index,array)=>{
          this.unionClass.push('text item child')
        })
      })
    },
    //获取公司列表
    getCompanyList() {
      selectCompany().then(response => {
        this.companyList = response.data
      })
    },
    /** 查询假日设定列表 */
    getList() {
      this.loading = true
      this.init(1)
      listHolidayTable(this.queryParams).then(response => {
        response.rows.forEach((value, index, array) => {
          this.holidayTableList[parseInt(value.month) - 1]['day' + parseInt(value.day)] = value.dateType
        })
        if (response.total == 0) {
          this.$modal.msgWarning('未查询到数据')
        } else {
          this.$modal.msgSuccess('查询完成')
        }
        this.loading = false
      })
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        year: null,
        month: null,
        day: null,
        dateType: null,
        holDay: null,
        creator: null,
        creatorId: null,
        createDate: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList()
    },
  }
}

</script>
<style>
.is-selected {
  color: #1989FA;
}
.text {
  font-size: 16px;
}

.item {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  height: 35px;
  width: 150px;
  margin-bottom: 1px;
}
.head {
  background-color: #d7d7d7;
}
.child {
  background-color: #f2f2f2;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 480px;
}
</style>
