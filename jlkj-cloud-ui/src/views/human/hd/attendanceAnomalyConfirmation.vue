<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" :rules="rules" label-width="80px">
          <el-form-item label="公司别" prop="compId">
            <el-select v-model="queryParams.compId" placeholder="请选择公司" :popper-append-to-body="false">
              <el-option
                v-for="dict in companyName"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="员工编码" prop="empNo">
            <el-input v-model="queryParams.empNo" placeholder="请输入工号" :disabled="true">
              <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item prop="slotCardOnduty">
            <el-date-picker
              v-model="queryParams.slotCardOnduty"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="~"
              start-placeholder="注销开始时间"
              end-placeholder="注销结束时间"
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
              size="mini"
              @click="handleConfirm"
              v-hasPermi="['human:attendanceAnomalyConfirmation:confirm']"
            >确认旷工</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="attendanceAbnormalList">
          <el-table-column label="工号" align="center" prop="empNo"/>
          <el-table-column label="姓名" align="center" prop="empName"/>
          <el-table-column label="正常出勤时段" align="center" prop="norOndutyBegin" />
          <el-table-column label="实际出勤时段" align="center" prop="slotCardOnduty" />
          <el-table-column label="出勤证明原因" align="center" prop="proveReason" >
            <template v-slot="scope">
              <dict-tag-human :options="attendenceOptions.ProveReason" :value="scope.row.proveReason"/>
            </template>
          </el-table-column>
          <el-table-column label="辅助说明" align="center" prop="description" />
          <el-table-column label="审批状态" align="center" prop="status" />
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />

        <select-user ref="select" @ok="getJobNumber"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getBaseInfo} from "@/api/human/hm/baseInfo";
import selectUser from "@/views/components/human/selectUser/selectUser";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";
import {validateNumber} from "@/utils/jlkj";
import {listAttendanceAnomalyConfirmationList} from "@/api/human/hd/attendanceAbnormal";
import {addBatchCancellationPerson, confirmAttendanceAnomaly} from "@/api/human/hd/cancellationPerson";
import {getAttendenceOptions} from "@/api/human/hd/attendenceBasis";

export default {
  name: "AttendanceAnomalyConfirmation",
  components: {selectUser,DictTagHuman},
  data() {
    return {
      // 遮罩层
      loading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 人事注销记录表格数据
      attendanceAbnormalList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: 'J00',
        empNo: null,
        slotCardOnduty: '',
        startTime: '',
        endTime: ''
      },
      // 表单校验
      rules: {
        empNo: [
          { required: true, validator: validateNumber, trigger: "blur" },
          { max: 6, message: '工号长度必须为6位数字', trigger: 'blur' }
        ],
        slotCardOnduty: [
          { required: true, message: "日期不能为空", trigger: "blur" }
        ]
      },
      // 公司别数据
      companyName: [],
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['ProveReason']
      },
      //出勤选单选项列表
      attendenceOptions: {},
    };
  },
  created() {
    selectCompany().then(res => {
      this.companyName = res.data
    })
    /** 查询出勤字典 */
    getAttendenceOptions(this.attendenceOptionType).then(response => {
      this.attendenceOptions = response.data;
    })
  },
  methods: {
    /** 查询出勤异常列表 */
    getList() {
      this.loading = true;
      listAttendanceAnomalyConfirmationList(this.queryParams).then(response => {
        this.attendanceAbnormalList = response.data.rows;
        for (let i = 0; i < this.attendanceAbnormalList.length; i++) {
          this.attendanceAbnormalList[i].slotCardOnduty = this.attendanceAbnormalList[i].slotCardOnduty + '~' + this.attendanceAbnormalList[i].slotCardOffduty
          this.attendanceAbnormalList[i].norOndutyBegin = this.attendanceAbnormalList[i].norOndutyBegin + '~' + this.attendanceAbnormalList[i].norOndutyEnd
        }
        this.total = response.data.total;
        this.loading = false;
      });
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
    /** 时间选择器给查询参数赋值 */
    dateFormat(picker) {
      this.queryParams.startTime=picker[0]
      this.queryParams.endTime=picker[1]
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(val) {
      this.queryParams.empNo = val
      this.getList();
    },
    handleConfirm() {
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          confirmAttendanceAnomaly(this.queryParams).then(response => {
            this.$modal.msgSuccess("已确认旷工");
            this.open = false;
            this.getList();
          });
        }
      });
    }
  }
};
</script>
<style scoped>
/deep/.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
.picker-width{
  width: 100%;
}
</style>
