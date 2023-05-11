<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别" :popper-append-to-body="false">
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
          @change="dateFormat">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

    <el-table v-loading="loading" :data="cancelHolidayList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />

      <el-table-column label="工号" align="center" prop="empNo" />
      <el-table-column label="实际请假开始日期" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际请假结束日期" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="代理人" align="center" prop="agent" />
      <el-table-column label="实际请假天数" align="center" prop="leaveDays" />
      <el-table-column label="请假类别" align="center" prop="leaTypeId">
        <template v-slot="scope">
          <dict-tag-human-basis :options="attendenceOptions.HD001" :value="scope.row.leaTypeId"/>
        </template>
      </el-table-column>
      <el-table-column label="请假状态" align="center" prop="status1"/>
      <el-table-column label="销假状态" align="center" prop="status">
        <template v-slot="scope">
          <dict-tag-human-basis :options="attendenceOptions.HolidayStatus" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button v-if="scope.row.status === '01'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:cancelHoliday:edit']"
          >修改</el-button>
          <el-button v-if="scope.row.status === '01'"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:cancelHoliday:remove']"
          >作废</el-button>
          <el-button v-if="scope.row.status !== '01'"
                     size="mini"
                     type="text"
                     icon="el-icon-details"
                     @click="handleDetails(scope.row)"
          >详情</el-button>
          <el-button v-if="scope.row.status === '03'"
                     size="mini"
                     type="text"
                     icon="el-icon-other"
                     @click="handleWithdraw(scope.row)"
                     v-hasPermi="['human:cancelHoliday:withdraw']"
          >撤回</el-button>
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

    <!-- 修改员工销假对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="200px">

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="工号" prop="empNo">
              <el-input v-model="form.empNo" placeholder="请输入工号" :disabled="true" class="inputInner">
                <el-button slot="append" icon="el-icon-search" @click="inputClick" clearable></el-button>
              </el-input>
              {{ form.empName }}:{{ form.postName }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="原始请假开始时间" prop="oriStartDate">
              {{ form.oriStartDate }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="原始请假结束时间" prop="oriEndDate">
              {{ form.oriEndDate }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="原始请假天数" prop="oriLeaveDays">
              {{ form.oriLeaveDays }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="实际请假开始时间" prop="startDate">
              <el-date-picker clearable
                              v-model="form.startDate"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              type="datetime"
                              placeholder="请选择实际请假开始时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="实际请假结束时间" prop="endDate">
              <el-date-picker clearable
                              v-model="form.endDate"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              type="datetime"
                              placeholder="请选择实际请假结束时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="实际请假天数" prop="leaveDays">
              <el-input v-model="leaveDays" placeholder="请输入实际请假天数" :disabled="this.form.id!=null"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
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
          <el-col :span="8">
            <el-form-item label="是否包括节假日" prop="isContainHoliday">
              <el-radio-group v-model="form.isContainHoliday">
                <el-radio
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
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
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="本月累计天数" prop="monthDays">
              <el-input v-model="form.monthDays" placeholder="请输入本月累计天数" :disabled="this.form.id!=null"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="本年累计天数" prop="yearDays">
              <el-input v-model="form.yearDays" placeholder="请输入本年累计天数" :disabled="this.form.id!=null"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="剩余可请假天数" prop="remainingDays">
              <el-input v-model="form.remainingDays" placeholder="请输入剩余可请假天数" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="24">
        <el-form-item label="辅助说明" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="输入人" prop="creator">
              {{ form.creatorId }} {{ form.creator }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="输入日期" prop="createDate">
              {{ form.createDate }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="审批状态" prop="status" :disabled="this.form.id!=null">
              <template v-slot="scope">
                <dict-tag-human-basis :options="attendenceOptions.HolidayStatus" :value="form.status"/>
              </template>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
        <el-button @click="submit">呈 送</el-button>
      </div>
    </el-dialog>

    <!-- 详情员工请假记录对话框 -->
    <el-dialog :title="title" :visible.sync="opencreate" width="1200px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="200px">

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="工号" prop="empNo">
              <el-input v-model="form.empNo" placeholder="请输入工号" :disabled="true" class="inputInner">
                <el-button slot="append" icon="el-icon-search" @click="inputClick" clearable></el-button>
              </el-input>
              {{ form.empName }}:{{ form.postName }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="原始请假开始时间" prop="oriStartDate">
              {{ form.oriStartDate }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="原始请假结束时间" prop="oriEndDate">
              {{ form.oriEndDate }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="原始请假天数" prop="oriLeaveDays">
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
              <template v-slot="scope">
                <dict-tag-human-basis :options="attendenceOptions.HD001" :value="form.leaTypeId"/>
              </template>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否包括节假日" prop="isContainHoliday">
              {{ form.isContainHoliday }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否全销" prop="isAll">
              {{ form.isAll }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="本月累计天数" prop="monthDays">
              {{ form.monthDays }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="本年累计天数" prop="yearDays">
              {{ form.yearDays }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="剩余可请假天数" prop="remainingDays">
              {{ form.remainingDays }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="辅助说明" prop="description">
              <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="输入人" prop="creator">
              {{ form.creatorId }} {{ form.creator }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="输入日期" prop="createDate">
              {{ form.createDate }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="审批状态" prop="status" :disabled="this.form.id!=null">
              <template v-slot="scope">
                <dict-tag-human-basis :options="attendenceOptions.HolidayStatus" :value="form.status"/>
              </template>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {
  addCancelHoliday,
  delCancelHoliday,
  getCancelHoliday,
  listCancelHoliday,
  updateCancelHoliday,
  withdrawCancelHoliday
} from "@/api/human/hd/cancelHoliday";
import {queryNewPostNameAndChangeDetail} from "@/api/human/hm/employeeTurnover";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getAttendenceOptions} from '@/api/human/hd/attendenceBasis'
import DictTagHumanBasis from "@/views/components/human/dictTag/humanBaseInfo";
import selectUser from "@/views/components/human/selectUser/selectUser";
import {getDateTime} from "@/api/human/hd/ahumanUtils";


export default {
  name: "CancelHoliday",
  dicts: ['sys_yes_no'],
  components: {DictTagHumanBasis, selectUser},
  data() {
    return {
      //公司列表
      companyList: [],
      //用户公司别
      userCompId: this.$store.state.user.userInfo.compId,
      // 遮罩层
      loading: true,
      // 是否显示详情弹出层
      opencreate: false,
      //状态
      status: '01',
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['HolidayStatus', 'HD001']
      },
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
      title: "",
      //出勤选单选项列表
      attendenceOptions: {},
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        empNo: null,
        startDate: '',
        endDate: '',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  computed:{
    leaveDays(){
        var startDate = new Date(this.form.startDate);
        var endDate = new Date(this.form.endDate)
        return (endDate - startDate) / (1 * 24 * 60 * 60 * 1000);
    }
  },
  created() {
    this.getCompanyList();
    this.getDisc();
    this.getList();
  },
  methods: {
    /** 查询员工销假列表 */
    getList() {
      this.loading = true;
      listCancelHoliday(this.queryParams).then(response => {
        this.cancelHolidayList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询公司列表 */
    getCompanyList() {
      selectCompany().then(response => {
          this.companyList = response.data
        }
      )
    },
    //日期范围转换
    dateFormat(picker) {
      if (picker != null && picker != "") {
        this.queryParams.startDate = picker[0]
        this.queryParams.endDate = picker[1]
      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 呈送按钮
    submit() {
       this.open = false;
       this.form.status = "03";
       this.submitForm()
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
      };
      this.resetForm("form");
    },
    /** 查询出勤字典 */
    getDisc() {
      getAttendenceOptions(this.attendenceOptionType).then(response => {
        this.attendenceOptions = response.data;
      })
    },
    /** 查询条件判定 */
    judgeQuery(){
      if(this.queryParams.empNo===null||this.queryParams.empNo===''){
        this.$modal.msgError("请输入工号")
        return false;
      }else{
        return true;
      }
    },
    /** 搜索按钮操作 */
    handleQuery(e) {
      if(e===0){
        this.queryParams.pageNum = 1;
        this.getList();
      }else{
        if(this.judgeQuery()){
          this.queryParams.pageNum = 1;
          this.getList();
        }
      }
    },
    /** 详情按钮操作 */
    handleDetails(row) {
      this.reset();
      const id = row.id || this.ids
      getCancelHoliday(id).then(response => {
        this.form = response.data;
        this.opencreate = true;
        this.title = "员工销假记录详情";
      });
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.startDate = null
      this.queryParams.endDate = null
      this.handleQuery(0);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCancelHoliday(id).then(response => {
        this.form = response.data;
        this.form.creator = this.$store.state.user.userInfo.nickName ;
        this.form.createDate = getDateTime(1) ;
        this.form.creatorId = this.$store.state.user.name;
        this.open = true;
        this.title = "修改员工销假";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if(this.form.isAll === '否'){
          this.form.leaveDays = this.leaveDays
          } else{
            this.form.leaveDays = this.leaveDays = 0
          }
          if (this.form.id != null) {
            updateCancelHoliday(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCancelHoliday(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 作废按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认作废此员工销假的数据项？').then(function() {
        return delCancelHoliday(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("作废成功");
      }).catch(() => {});
    },
    /** 撤回按钮操作 */
    handleWithdraw(row) {
      withdrawCancelHoliday(row.id).then(res => {
        this.$modal.msgSuccess("撤回成功");
        this.getList();
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/cancelHoliday/export', {
        ...this.queryParams
      }, `cancelHoliday_${new Date().getTime()}.xlsx`)
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(empId, userName, compId) {
      if (this.open == true) {
        this.form.empNo = empId
        this.form.empName = userName
        queryNewPostNameAndChangeDetail(this.form).then(res => {
          this.form.postName = res.data.list1[0].newPostName
        })
      } else {
        this.queryParams.empNo = empId;
        this.getList();
      }
    }
  }
};
</script>
<style scoped>
.inputInner {
  width: 30%;
}
/deep/.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
