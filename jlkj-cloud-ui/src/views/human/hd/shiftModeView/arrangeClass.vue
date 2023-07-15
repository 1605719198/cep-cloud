<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" >
      <el-form-item label="年份" prop="classYear" >
        <el-date-picker
          :clearable="false"
          v-model="queryParams.classYear"
          type="year"
          placeholder="选择年份">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="轮班方式" prop="shiftmodeId">
        <el-select v-model="queryParams.shiftmodeId" placeholder="请选择轮班方式" ref="select1" >
          <el-option
            v-for="dict in modeList"
            :key="dict.id"
            :label="dict.turnTypeName"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="班别" prop="shiftmodeId">
        <el-select v-model="queryParams.classId" placeholder="请选择班别" ref="select2"  >
          <el-option
            v-for="dict in classList"
            :key="dict.id"
            :label="dict.className"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="显示星期" prop="showWeekend">
        <el-radio-group v-model="showWeekend">
          <el-radio
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.value"
          >{{dict.label}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>
    <div v-show="showClassTable">
      <el-divider></el-divider>
      <el-form :model="cycleSetData" ref="cycleSetForm" size="small" :inline="true"   >
        <el-form-item label="排班区间" prop="interval">
          <el-date-picker
            v-model="cycleSetData.interval"
            type="daterange"
            value-format="yyyy-MM-dd"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="周期设定" prop="cycleSetString">
          <el-input v-model="cycleSetData.cycleString" placeholder="请输入周期设定(逗号分隔)" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-tickets" size="mini" @click="setCycleData">排班</el-button>
        </el-form-item>
      </el-form>

      <el-table
        v-loading="loading"
        v-if="refresh"
        :data="tableData"
        :span-method="objectSpanMethod"
        border
        :cell-style="cellStyle"
        class="tableBox"
        style="width: 100%"
      >
        <el-table-column
          prop="month"
          align="center"
          label="月/日"
          width="60"
        >
        </el-table-column>
        <el-table-column
          align="center"
          v-for="(itemss,index) in daysHead"
          :prop="itemss.name"
          width="50"
          :label="itemss.value"
        >
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import { listShiftCode } from "@/api/human/hd/shiftCode";
import { listShiftClass} from "@/api/human/hd/shiftClass";
import { getDateTime } from "@/api/human/hd/ahumanUtils"
import { addArrangeClassMaster } from '@/api/human/hd/arrangeClassMaster'
import { listArrangeClass } from '@/api/human/hd/arrangeClass'
export default {
  name: 'arrangeClass',
  dicts: ['sys_yes_no'],
  data() {
    return {
      //遮罩层
      loading: false,
      //table刷新
      refresh:true,
      //是否显示排班表
      showClassTable:true,
      //登录人工号
      userEmpId: this.$store.state.user.name,
      //登录人姓名
      nickName: this.$store.state.user.userInfo.nickName,
      //登录人公司
      logincompId: this.$store.state.user.userInfo.compId,
      //公司别
      compId:null,
      cycleSetData:{
        //排班周期区间
        interval :null,
        //排班周期开始月日
        startMonth: null,
        startDay: null,
        //排班周期持续时间
        days: null,
        //周期设定字符串
        cycleString: null,
        //周期设定数组
        cycleArray: [],
      },
      // 显示搜索条件
      showSearch: true,
      //是否显示星期
      showWeekend:'Y',
      //31天表头
      daysHead: [],
      //年份选项
      allYear:[],
      //表格数据
      tableData: [],
      //排班数据
      arrangeClassList: [],
      //周数据
      weekendList: [] ,
      //轮班方式数据
      modeList:[],
      //班别列表数据
      classList:[],
      //班次列表数据
      shiftCodeList:[],
      //排班明细数据
      detailList: [],
      //大月份
      bigMonth:[1, 3, 5, 7, 8, 10, 12],
      //表单参数
      form:{},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        shiftmodeId: null,
        classId: null,
        classYear: null,
        arrShiDate: null,
      },
    }
  },
  watch: {
    showWeekend(val) {
      this.tableData =[];
      this.setTableData()
    },
    'queryParams.shiftmodeId':{
      handler(newItem,preItem){
        if(newItem){
          this.getShiftClass();
        }
      },
      immediate:false,
    },

  },
  methods: {
    //表格单元样式调整
    cellStyle({row,column,rowIndex, columnIndex}){
      for(var i in row){
        const re = /^day.*/;
        const rsCheck = re.test(i);
        if(rsCheck){
          if(row[i]==='00'&&column.property===i){
            return "color:red"
          }
        }
      }
    },
    //排班按钮点击事件
    setCycleData(){
      this.loading = true;
      this.resetData(1);
      this.setCycleDate();
    },
    //排班数据预处理
    setCycleDate(){
      var judge = 1;
      var classArray = [];
      this.shiftCodeList.forEach(value => {
        classArray.push(value.shiftCode)
      })
      var strings = this.cycleSetData.cycleString;
      var arr = [];
      if(strings){
        arr  = strings.split(',')
        arr.forEach(value => {
          if(!(classArray.indexOf(value)!==-1)){
            if(judge===1){
              this.resetData(0)
              this.$modal.msgError("请检查班次输入项是否正确")
              this.loading = false;
              judge=0
            }
          }
        })
      }else{
        this.resetData(0)
        this.$modal.msgError("请输入周期设定")
        this.loading = false;
        judge = 0;
      }
      if(judge===1){
        this.cycleSetData.cycleArray = arr
        var interVal = this.cycleSetData.interval
        if(interVal){
          var Date1 = new Date(interVal[0])
          var Date2 = new Date(interVal[1])
          var days = null
          if(Date1.getFullYear()!==Date2.getFullYear()){
            this.$modal.msgError("排班日期不允许跨年");
            this.loading = false;
            this.resetData(0)
          }else if(Date1.getFullYear()!==this.queryParams.classYear.getFullYear()){
            this.$modal.msgError('请排'+this.queryParams.classYear.getFullYear()+'年的班');
            this.loading = false;
            this.resetData(0)
          }else if(Date1<new Date()){
            this.$modal.msgError("排班开始日期不能小于当前日期")
            this.loading = false;
            this.resetData(0)
          } else {
            days=(Date2 - Date1)/(24*60*60*1000)+1;
            var startMonth = String(Date1.getMonth() + 1).padStart(2, '0');
            var startDay = String(Date1.getDate()).padStart(2, '0');
            this.cycleSetData.startMonth = startMonth;
            this.cycleSetData.startDay = startDay;
            this.cycleSetData.days = days;
            this.setFinalCycle();
            this.setTableData();
            this.saveClassdata();
          }
        }else{
          this.$modal.msgWarning("请选择排班区间")
        }
      }

    },
    //排班操作
    setFinalCycle(){
      //大月
      var bigMonth = this.bigMonth
      //年份
      var year = this.queryParams.classYear.getFullYear()
      //排班周期记录参数
      var k = 1
      //起始月
      var startMonth = parseInt(this.cycleSetData.startMonth)
      //起始日
      var startDay = parseInt(this.cycleSetData.startDay)
      //排班天数
      var cycleNumber = parseInt(this.cycleSetData.days)
      //已排班天数
      var haveCycleNumber = 0;
      //周排班配置
      var cycleClass = this.cycleSetData.cycleArray
      //排班配置周期个数
      var CycleDays = cycleClass.length
      for (var i = startMonth; i < 13; i++) {
        var Class = {
          order: null,
          month: null,
          type: 'class'
        }
        Class.order = i
        Class.month = i + '月'
        var monthDays ;
        if (bigMonth.indexOf(i) !== -1) {
          monthDays = 32;
        } else if (i === 2) {
          var o = (year % 4 === 0) ? 1 : 0;
          monthDays = 29+o;
        } else {
          monthDays = 31
        }
        for (var j = startDay; j < monthDays; j++) {
          startDay = 1;
          var t = ((haveCycleNumber+1) % CycleDays === 0) ? CycleDays : (haveCycleNumber+1) % CycleDays
          Class['day' + j] = cycleClass[t-1]
          this.arrangeClassList[i-1]['day' + j] = cycleClass[t-1]
          haveCycleNumber++
          if(haveCycleNumber===cycleNumber){
            if(Object.keys(Class).length!==3){
              // this.arrangeClassList.push(Class);
            }
            return true;
          }
        }
        // if(Object.keys(Class).length!=3){
        //   this.arrangeClassList.push(Class);
        // }
      }
    },
    //存储排班数据
    saveClassdata(){
      this.resetForm();
      this.form.compId = this.queryParams.compId;
      this.form.shiftmodeId = this.queryParams.shiftmodeId;
      this.form.turnTypeName = this.$refs.select1.selectedLabel;
      this.form.classId = this.queryParams.classId;
      this.form.className = this.$refs.select2.selectedLabel;
      this.form.startDate = this.cycleSetData.interval[0]
      this.form.endDate = this.cycleSetData.interval[1]
      this.form.rule = JSON.stringify(this.cycleSetData.cycleArray)
      this.form.versionNo = 1;
      this.form.isNew = '1';
      this.form.creator = this.nickName;
      this.form.creatorId = this.userEmpId;
      this.form.createDate = getDateTime(1);
      this.saveClassdataDetail();
      if(this.form.id==null){
        addArrangeClassMaster(this.form).then(response=>{
          this.$modal.msgSuccess("排班成功");
          this.loading = false;
        })
      }
    },
    //存储排班明细
    saveClassdataDetail(){
      var detailList = [];
      this.arrangeClassList.forEach((value,index,array)=>{
        for(var i=1;i<32;i++){
          var arrangeDetail = {
            id: null,
            compId: null,
            arrangeClassMasterId: null,
            shiftmodeId: null,
            classId: null,
            shiftId: null,
            shiftCode: null,
            arrShiDate: null,
            shiftDesc: null,
            creator: null,
            creatorId: null,
            createDate: null,
          }
          if(value['day'+i]&&value['day'+i]!='-'){
            var month = ((index+1)<10)? '0'+(index+1):(index+1);
            var day = (i<10)? '0'+i:i;
            var year = this.queryParams.classYear.getFullYear();
            var arrangeDate = year +'-'+month+'-'+day;
            arrangeDetail.shiftCode = value['day'+i]
            arrangeDetail.arrShiDate = year+'-'+month+'-'+day
            this.shiftCodeList.forEach((value,index,array) => {
              if(value.shiftCode === arrangeDetail.shiftCode){
                arrangeDetail.shiftId = value.id;
                arrangeDetail.shiftDesc = '说明'+'：'+value.description+'。班次开始时间：'+value.startHour+':'+value.startMin+'-班次结束时间：'+value.endHour+':'+value.endMin;
              }
            })
            detailList.push(arrangeDetail)
          }
        }
      })
      this.form.detailList = detailList
    },
    //获取排班详情列表
    getDetailList(){
      this.queryParams.arrShiDate = getDateTime(1,this.queryParams.classYear);
      listArrangeClass(this.queryParams).then(response=>{
        this.detailList = response.rows;
        this.arrangeClassList=[];
        this.weekendList = [];
        this.setInitialArray();
        this.detailList.forEach((value => {
          var month = value.arrShiDate.substring(5,7)
          var day = value.arrShiDate.substring(8,10)
          this.arrangeClassList[parseInt(month)-1][('day'+parseInt(day))] = value.shiftCode;
        }))
        if(this.detailList.length ===0){
          this.resetData(0)
          this.$modal.msgWarning("未查询到数据")
        }else{
          this.tableData = [];
          this.setTableData();
          this.loading = false
          this.$modal.msgSuccess("查询成功")
        }
      })
    },
    init(modeData) {
      if(this.queryParams.compId!==modeData.compId){
        this.queryParams.shiftmodeId =null;
        this.queryParams.classId =null;
        this.queryParams.classYear =null;
        this.queryParams.arrShiDate =null;
      }
      this.modeList = modeData.modeList;
      this.queryParams.compId = modeData.compId;
      this.setInitialData();
    },
    //获取班别班次数据
    getShiftClass(){
      this.queryParams.classId = null;
      listShiftClass(this.queryParams).then(response => {
        this.classList = response.rows
      });
      listShiftCode(this.queryParams).then(response => {
        this.shiftCodeList = response.rows;
      })
    },
    //设置初始数据
    setInitialData() {
      this.daysHead = [];
      this.queryParams.classYear = new Date();
      for (var i = 1; i < 32; i++) {
        var obj = {
          name: 'day' + i,
          value: JSON.stringify(i)
        }
        this.daysHead.push(obj)
      }
      this.resetData(0);
    },
    //设置初始排班数组和星期数组
    setInitialArray(){
      //大月
      var bigMonth = this.bigMonth;
      //年份
      var year = this.queryParams.classYear.getFullYear()
      //当年1号星期
      var k = new Date(this.queryParams.classYear.getFullYear()+'-01-01').getDay();
      //周配置
      var Week = ['日','一', '二', '三', '四', '五', '六']
      for (var i = 1; i <  13; i++) {
        var Class = {
          order: null,
          month: null,
          type: 'class'
        }
        Class.order = i
        Class.month = i + '月'
        var Weekend = {
          order: null,
          month: null,
          type: 'weekend'
        }
        Weekend.order = i
        Weekend.month = i + '月'
        var monthDays ;
        if (bigMonth.indexOf(i) !== -1) {
          monthDays = 32;
        } else if (i == 2) {
          var o = ((year%400 ===0)||(year % 4 === 0 && year%100 !== 0)) ? 1 : 0;
          monthDays = 29+o;
        } else {
          monthDays = 31
        }
        for (var j = 1; j < monthDays; j++) {
          var t = k % 7
          k++
          Class['day' + j] = '-';
          Weekend['day' + j] = Week[(t)]
        }
        this.arrangeClassList.push(Class)
        this.weekendList.push(Weekend)
      }
    },
    setTableData() {
      for (var i = 0; i < 12; i++) {
        if(this.showWeekend=='Y'){
          this.tableData.push(this.weekendList[i])
        }
        this.tableData.push(this.arrangeClassList[i])
      }
    },
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if(this.showWeekend=='Y'){
        if (columnIndex === 0) {
          if (rowIndex % 2 === 0) {
            return {
              rowspan: 2,
              colspan: 1
            }
          } else {
            return {
              rowspan: 0,
              colspan: 0
            }
          }
        }
      }
    },
    //重置表单
    resetData(e){
      this.tableData = [];
      this.arrangeClassList = [];
      this.weekendList = [];
      this.setInitialArray();
      if(e===0){
        this.setTableData()
      }
    },
    /** 搜索按钮操作 */
    handleQuery() {
      if(this.queryParams.shiftmodeId==null){
        this.$modal.msgWarning("清先选择轮班方式")
      }else if(this.queryParams.classId==null){
        this.$modal.msgWarning("请先选择班别")
      }else{
        // this.resetData(1)
        this.showClassTable = true;
        this.getDetailList();
      }
    },
    // 表单重置
    resetForm() {
      this.form = {
        id: null,
        compId: null,
        shiftmodeId: null,
        turnTypeName: null,
        classId: null,
        className: null,
        startDate: null,
        endDate: null,
        rule: null,
        versionNo: null,
        isNew: null,
        creator: null,
        creatorId: null,
        createDate: null,
        detailList:[],
      };
    },
    /**改变公司别 */
    changeCompId(val,list){
      this.queryParams.compId = val;
      this.queryParams.shiftmodeId =null;
      this.modeList = list;
      this.queryParams.classId = null;
    }
  }
}
</script>
<style lang="scss" scoped>
.tableBox {
  th {
    padding: 0 !important;
    height: 10px;
    line-height: 10px;

  }

  td {
    padding: 0 !important;
    height: 10px;
    line-height: 10px;
  }
}
</style>
