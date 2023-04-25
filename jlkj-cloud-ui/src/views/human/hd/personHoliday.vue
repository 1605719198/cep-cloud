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
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请假结束时间" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
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
<!--            v-hasPermi="['human:personHoliday:details']"-->
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
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="200px">

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="工号" prop="empNo">
              <el-input v-model="queryParams.empNo" placeholder="请输入工号" :disabled="true" class="inputInner">
                <el-button slot="append" icon="el-icon-search" @click="inputClick" clearable></el-button>
              </el-input>
              {{ form.empName }}{{ form.postname }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="请假开始时间" prop="startDate">
              <el-date-picker clearable
                              v-model="form.startDate"
                              type="datetime"
                              placeholder="请选择请假开始时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请假结束时间" prop="endDate">
              <el-date-picker clearable
                              v-model="form.endDate"
                              type="datetime"
                              placeholder="请选择请假结束时间">
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
              <el-input v-model="leaveShifts" placeholder="请输入请假天数" type="number"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="是否包括节假日" prop="isContainHoliday">
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
              <el-input v-model="form.remainingDays" placeholder="请输入剩余可请假天数" type="number"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="本月累计天数" prop="monthDays">
              <el-input v-model="form.monthDays" placeholder="请输入本月累计天数" type="number" :disabled="this.form.id!=null"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="本年累计天数" prop="yearDays">
              <el-input v-model="form.yearDays" placeholder="请输入本年累计天数" type="number" :disabled="this.form.id!=null"/>
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
      </div>
    </el-dialog>

    <!-- 详情员工请假记录对话框 -->
    <el-dialog :title="title" :visible.sync="opencreate" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="200px">

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="工号" prop="empNo">
              <el-input v-model="queryParams.empNo" placeholder="请输入工号" :disabled="true" class="inputInner">
                <el-button slot="append" icon="el-icon-search" @click="inputClick" clearable></el-button>
              </el-input>
              {{ form.empName }}{{ form.postname }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="请假开始时间" prop="startDate">
              {{form.startDate}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请假结束时间" prop="endDate">
              {{form.endDate}}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="请假类别" prop="leaTypeId">
                {{form.leaTypeId}}
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
import {
  listPersonHoliday,
  getPersonHoliday,
  delPersonHoliday,
  addPersonHoliday,
  updatePersonHoliday
} from "@/api/human/hd/personHoliday";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getAttendenceOptions} from '@/api/human/hd/attendenceBasis'
import DictTagHumanBasis from "@/views/components/human/dictTag/humanBaseInfo"
import selectUser from "@/views/components/human/selectUser/selectUser";
import {getDateTime} from "@/api/human/hd/ahumanutils";
import {delComptime} from "@/api/human/hd/comptime";
import {queryNewPostNameAndChangeDetail} from "@/api/human/hm/employeeTurnover";
import {yearHoliday} from "@/api/human/hd/yearHoliday";
import {homeLeaveHoliday} from "@/api/human/hd/homeLeaveHoliday";

export default {
  name: "PersonHoliday",
  dicts: ['sys_yes_no', 'sys_classtype'],
  components: {DictTagHumanBasis, selectUser},
  data() {
    return {
      //员工年休假数组
      yearHoliday:[],
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
      //用户公司别
      userCompId: this.$store.state.user.userInfo.compId,
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
      title: "",
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
        queryDate: '',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        totalDays: [
          {required: true, message: '不能为空', trigger: "change"}
        ]
      }
    };
  },
  computed:{
    leaveShifts(){
      var startDate = new Date(this.form.startDate);
      var endDate = new Date(this.form.endDate)
      var result = (endDate-startDate) / (1 * 24 * 60 * 60 * 1000);

      return result;
    },
    remainingDays(){
      return parseInt(this.form.gotoDays) - parseInt(this.form.leaveShifts);
      return parseInt(this.form.preHomeDays) - parseInt(this.form.leaveShifts);
    },
    monthDays(){

    },
    yearDays(){

    }
  },
  created() {
    this.getCompanyList();
    this.getList();
    this.getDisc();
    this.getYearHolidayList();
    this.getHomeLeaveHolidayList();
  },
  methods: {
    /** 查询出勤字典 */
    getDisc() {
      getAttendenceOptions(this.attendenceOptionType).then(response => {
        this.attendenceOptions = response.data;
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
      this.loading = true;
      listPersonHoliday(this.queryParams).then(response => {
        this.personHolidayList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询员工年休假记录 */
    getYearHolidayList() {
      var year={
        year: 2023,
        empNo: null,
        compId: null,
      }
      yearHoliday(year).then(response => {
        this.yearHoliday = response.rows;
        console.log(JSON.stringify(response.rows))
      });
    },
    /** 查询员工探亲假记录 */
    getHomeLeaveHolidayList() {
      var home={
        empNo: null,
        compId: null,
      }
      homeLeaveHoliday(home).then(response => {
        this.homeLeaveHoliday = response.rows;
        console.log(JSON.stringify(response.rows))
      });
    },
    /** 查询条件判定 */
    judgeQuery() {
      if (this.queryParams.empNo === null || this.queryParams.empNo === '') {
        this.$modal.msgError("请输入工号")
        return false;
      } else {
        return true;
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
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery(e) {
      if (e === 0 || this.judgeQuery()) {
        this.queryParams.pageNum = 1;
        this.getList();
      }
    },
    dateFormat(picker) {
      if (picker != null && picker != "") {
        this.queryParams.startDate = picker[0]
        this.queryParams.endDate = picker[1]
      }
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form.creator = this.nickName;
      this.form.creatorId = this.$store.state.user.name;
      this.form.createDate = getDateTime(1)
      this.open = true;
      this.title = "添加员工请假记录";
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.form.leaveShifts=this.leaveShifts
            updatePersonHoliday(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.leaveShifts=this.leaveShifts
            addPersonHoliday(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 详情按钮操作 */
    handleDetails(row) {
      this.reset();
      const id = row.id || this.ids
      getPersonHoliday(id).then(response => {
        this.form = response.data;
        this.form.creator = this.$store.state.user.userInfo.nickName;
        this.form.createDate = getDateTime(1);
        this.form.creatorId = this.$store.state.user.name;
        this.opencreate = true;
        this.title = "员工请假记录详情";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPersonHoliday(id).then(response => {
        this.form = response.data;
        this.form.creator = this.$store.state.user.userInfo.nickName;
        this.form.createDate = getDateTime(1);
        this.form.creatorId = this.$store.state.user.name;
        this.open = true;
        this.title = "修改员工请假记录";
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除员工请假记录编号为"' + ids + '"的数据项？').then(function () {
        return delPersonHoliday(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(empId, userName, compId) {
      this.queryParams.empNo = empId;
      this.form.empNo = empId
      this.form.empName = userName
      this.getList();
      queryNewPostNameAndChangeDetail(this.form).then(res => {
        this.form.postName = res.data.list1[0].newPostName
      })
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
