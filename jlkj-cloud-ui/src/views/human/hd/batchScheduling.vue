<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别" clearable size="small">
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="轮班方式" prop="shiftmodeId">
        <el-select v-model="queryParams.shiftmodeCode" placeholder="请选择轮班方式" clearable>
          <el-option
            v-for="dict in shiftModeList"
            :key="dict.turnTypeCode"
            :label="dict.turnTypeName"
            :value="dict.turnTypeCode"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="班别" prop="classCode">
        <el-select v-model="queryParams.classCode" placeholder="请选择班别" clearable>
          <el-option
            v-for="dict in shiftClassList"
            :key="dict.classMode"
            :label="dict.className"
            :value="dict.classMode"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="排班日期" prop="interval">
        <el-date-picker
          v-model="interval"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="classId">
        <el-select v-model="form.classId" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in attendenceOptions.PersonClassRemark"
            :key="dict.dicNo"
            :label="dict.dicName"
            :value="dict.dicNo"
          />
        </el-select>
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
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="setScheduling"
        >排班
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="showShift"
        >轮班方式/班次/班别查看
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-form ref="batchScheduleForm" :model="{batchScheduleList}" :rules="rules" v-if="this.batchScheduleList.length!=0">
      <el-table ref="batchSchedule" :data="batchScheduleList" @selection-change="handleSelectionChange"
                highlight-current-row :cell-style="{paddingBottom:'0px'}"
      >
        <el-table-column type="selection" width="100" align="center"/>
        <el-table-column label="工号-姓名" align="center" key="empIdName" prop="empIdName">
          <template v-slot="scope">
            <!--            <el-form-item :prop="'batchScheduleList.'+scope.$index+'.empId'">-->
<!--            <el-form-item prop="empId">-->
<!--              <el-input v-model="scope.row.empId" placeholder="请输入" clearable></el-input>-->
<!--            </el-form-item>-->
            <el-form-item label="" prop="empIdName">
              <el-input maxlength="32"  v-model="batchScheduleList[(scope.row.index)].empIdName" placeholder="请选择员工" disabled>
                <el-button slot="append" icon="el-icon-search" @click="inputClick(scope.row.index)"></el-button>
              </el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="轮班方式" align="center" key="shiftmodeCode" prop="shiftmodeCode"/>
        <el-table-column label="班别" align="center" key="classCode" prop="classCode"/>
        <el-table-column label="排班开始日期" align="center" key="startDate" prop="startDate"/>
        <el-table-column label="排班结束日期" align="center" key="endDate" prop="endDate"/>
        <el-table-column label="输入人" align="center" key="creator" prop="creator"/>
        <el-table-column label="输入日期" align="center" key="createDate" prop="createDate"/>
      </el-table>
    </el-form>
    <select-user ref="select" @ok="getJobNumber"/>
  </div>
</template>

<script>
import { listShiftClass } from '@/api/human/hd/shiftClass'
import { listShiftMode } from '@/api/human/hd/shiftMode'
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getAttendenceOptions } from '@/api/human/hd/attendenceBasis'
import { getDateTime } from '@/api/human/hd/ahumanUtils'
import selectUser from "@/views/components/human/selectUser/selectUser";
export default {
  name: 'batchScheduling',
  components: {selectUser},
  data() {
    return {
      //公司选项
      compId: null,
      //公司数据
      companyList: [],
      //登录人工号
      userEmpId: this.$store.state.user.name,
      //登录人姓名
      nickName: this.$store.state.user.userInfo.nickName,
      //登录人公司
      logincompId: this.$store.state.user.userInfo.compId,
      //批量排班列表
      batchScheduleList: [],
      //排班区间
      interval: null,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      //表格序列
      index: 0,
      //人员序号
      selectIndex: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empId:null,
        compId: this.$store.state.user.userInfo.compId,
        shiftmodeCode: null,
        classCode: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      //轮班方式列表
      shiftModeList: [],
      //班别列表
      shiftClassList: [],
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['PersonClassRemark']
      },
      //出勤选单选项列表
      attendenceOptions: {}
    }
  },
  created() {
    this.getCompanyList()
    this.getDisc()
    this.getShiftList()
  },
  methods: {
    //轮班方式/班次/班别查看
    showShift() {
      // alert(JSON.stringify(this.ids))
    },
    //排班
    setScheduling() {
      alert(JSON.stringify(this.batchScheduleList))
    },
    //新增按钮操作
    handleAdd() {

      this.batchScheduleList.push({
        index: this.index,
        id: null,
        empId: null,
        empIdName: null,
        shiftmodeCode: this.queryParams.shiftmodeCode,
        classCode: this.queryParams.classCode,
        startDate: getDateTime(1,this.interval[0]),
        endDate: getDateTime(1,this.interval[1]),
        creator: this.nickName,
        creatorId: this.empId,
        createDate: getDateTime(1)
      })
      this.index++
    },
    //查询操作
    getList() {

    },
    //获取公司列表
    getCompanyList() {
      selectCompany().then(response => {
        this.companyList = response.data
      })
    },
    //查询轮班方式及班别
    getShiftList() {
      listShiftMode(this.queryParams).then(response => {
        this.shiftModeList = response.rows
      })
      listShiftClass(this.queryParams).then(response => {
        this.shiftClassList = response.rows
      })
    },
    //获取出勤字典
    getDisc() {
      getAttendenceOptions(this.attendenceOptionType).then(response => {
        this.attendenceOptions = response.data;
      })
    },
    //搜索操作
    handleQuery() {

    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
    },
    /** 人员选单事件 */
    inputClick(index) {
      this.selectIndex = index;
      var queryParams={
        // compID:this.queryParams.compId,
        compID:'J01',
        pageNum: 1,
        pageSize: 10,
      }
      this.$refs.select.show(queryParams);
    },
    /** 获取工号 */
    getJobNumber(empId, userName,compId) {
      this.batchScheduleList[this.selectIndex].empId = empId;
      this.batchScheduleList[this.selectIndex].empIdName = empId+'-'+userName;
    },
  }
}
</script>

<style scoped>

</style>
