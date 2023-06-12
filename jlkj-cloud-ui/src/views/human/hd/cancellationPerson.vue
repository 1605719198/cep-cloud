<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" :rules="rules" v-show="showSearch" label-width="68px">
          <el-form-item label="公司别" prop="companyId">
            <el-select v-model="queryParams.companyId" placeholder="请选择公司" :popper-append-to-body="false">
              <el-option
                v-for="dict in companyName"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="type">
            <el-select v-model="queryParams.type" :popper-append-to-body="false" @change="resetType">
              <el-option
                v-for="dict in attendenceOptions.CancellationPersonType"
                :key="dict.dicNo"
                :label="dict.dicName"
                :value="dict.dicNo"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-if="this.queryParams.type === 'org'" prop="orgId">
            <el-input v-model="queryParams.orgId" :disabled="true">
              <el-button slot="append" icon="el-icon-search" @click="openOrgPop"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item v-else-if="this.queryParams.type === 'user'" prop="empNo">
            <el-input v-model="queryParams.empNo" :disabled="true">
              <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item v-else prop="clockWorkId">
            <el-input v-model="queryParams.clockWorkId" :disabled="true">
              <el-button slot="append" icon="el-icon-search" @click="openMacPop"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item prop="checkStartDate">
            <el-date-picker
              v-model="queryParams.checkStartDate"
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
              v-hasPermi="['human:cancellationPerson:add']"
            >新增</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="cancellationPersonList">
          <el-table-column label="类别" align="center" prop="type" >
            <template v-slot="scope">
              <dict-tag-human :options="attendenceOptions.CancellationPersonType" :value="scope.row.type"/>
            </template>
          </el-table-column>
          <el-table-column
            v-for="item in tableColumns" :key="item.key"
            :prop="item.key"
            align='center'
            :label="item.name">
          </el-table-column>
          <el-table-column label="注销原因" align="center" prop="reason" />
          <el-table-column label="注销开始时间" align="center" prop="checkStartDate" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.checkStartDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="注销结束时间" align="center" prop="checkEndDate" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.checkEndDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作人" align="center" prop="creator" />
          <el-table-column label="操作日期" align="center" prop="createDate" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
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

        <!-- 添加或修改人事注销记录对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-row>
              <el-col :span="24">
                <el-form-item label="类别" prop="type">
                  <el-radio-group v-model="form.type">
                    <el-radio :label="'user'">员工编码</el-radio>
                    <el-radio :label="'org'">组织机构</el-radio>
                    <el-radio :label="'mac'">刷卡钟</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="19">
                <el-form-item label="员工编码" v-if="this.form.type === 'user'">
                  <el-input v-model="form.empNo" :disabled="true">
                    <el-button slot="append" icon="el-icon-search" @click="inputClick1"></el-button>
                  </el-input>
                </el-form-item>
                <el-form-item label="组织机构" v-else-if="this.form.type === 'org'">
                  <el-input v-model="form.orgId" :disabled="true">
                    <el-button slot="append" icon="el-icon-search" @click="openOrgPop1"></el-button>
                  </el-input>
                </el-form-item>
                <el-form-item label="刷卡钟" v-else>
                  <el-input v-model="form.clockWorkId" :disabled="true">
                    <el-button slot="append" icon="el-icon-search" @click="openMacPop1"></el-button>
                  </el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="注销时间" prop="workOvertimeDate">
                  <el-date-picker
                    v-model="form.workOvertimeDate"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    type="datetimerange"
                    range-separator="~"
                    start-placeholder="注销开始时间"
                    end-placeholder="注销结束时间"
                    :default-time="['08:00:00', '17:00:00']"
                    @change="dateFormat1"
                    class="picker-width">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="注销原因" prop="reason">
                  <el-input v-model="form.reason" placeholder="请输入注销原因" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="输入人" prop="creator">
                  {{form.creator}}
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="输入日期" prop="createDate">
                  {{form.createDate}}
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>
        <select-org-person ref="selectOrg" @ok="getOrg"/>
        <select-org-person ref="selectOrg1" @ok="getOrg1"/>
        <select-user ref="select" @ok="getJobNumber"/>
        <select-user ref="select1" @ok="getJobNumber1"/>
        <select-clock ref="selectMac" @ok="getMac"></select-clock>
        <select-clock ref="selectMac1" @ok="getMac1"></select-clock>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listCancellationPerson, addCancellationPerson } from "@/api/human/hd/cancellationPerson";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import selectOrgPerson from "@/views/components/human/selectUser/selectOrgPerson";
import selectUser from "@/views/components/human/selectUser/selectUser";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";
import {getAttendenceOptions} from "@/api/human/hd/attendenceBasis";
import selectClock from "@/views/components/human/selectView/hd/selectClock";
import '@/assets/styles/humanStyles.scss';

export default {
  name: "CancellationPerson",
  components: {selectUser,selectOrgPerson,DictTagHuman,selectClock},
  data() {
    return {
      // 遮罩层
      loading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 人事注销记录表格数据
      cancellationPersonList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: 'J00',
        checkStartDate: null,
        type: 'org',
        orgId: null,
        empNo: null,
        clockWorkId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        checkStartDate: [
          { required: true, message: "注销时间不能为空", trigger: "blur" }
        ],
      },
      // 公司别数据
      companyName: [],
      tableColumns: [],
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['CancellationPersonType']
      },
      //出勤选单选项列表
      attendenceOptions: {},
    };
  },
  created() {
    selectCompany().then(res => {
      this.companyName = res.data
    })
    getAttendenceOptions(this.attendenceOptionType).then(response => {
      this.attendenceOptions = response.data
    })
  },
  methods: {
    /** 查询人事注销记录列表 */
    getList() {
      this.loading = true;
      listCancellationPerson(this.queryParams).then(response => {
        this.tableColumns = []
        if (response.data.rows.empNo!=='') {
          this.tableColumns.push({ key: 'empNo', name: '工号', align: 'center'})
        } else if (response.data.rows.orgId!=='') {
          this.tableColumns.push({ key: 'orgId', name: '机构', align: 'center'})
        } else if (response.data.rows.clockWorkId!=='') {
          this.tableColumns.push({ key: 'clockWorkId', name: '刷卡钟', align: 'center'})
        }
        this.cancellationPersonList = response.data.rows;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        companyId: null,
        empNo: null,
        empId: null,
        empName: null,
        postName: null,
        postId: null,
        orgName: null,
        orgId: null,
        clockWorkId: null,
        checkStartDate: null,
        checkEndDate: null,
        reason: null,
        type: null,
        creator: null,
        creatorId: null,
        createDate: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加人事注销记录";
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
            addCancellationPerson(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
        }
      });
    },
    dateFormat(picker) {
      this.queryParams.startTime=picker[0]
      this.queryParams.endTime=picker[1]
    },
    dateFormat1(picker) {
      this.form.startTime=picker[0]
      this.form.endTime=picker[1]
    },
    openOrgPop() {
      this.$refs.selectOrg.show();
    },
    openOrgPop1() {
      this.$refs.selectOrg1.show();
    },
    openMacPop() {
      this.$refs.selectMac.show(this.queryParams.companyId);
    },
    openMacPop1() {
      this.$refs.selectMac1.show(this.queryParams.companyId);
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 工号点击事件 */
    inputClick1() {
      this.$refs.select1.show();
    },
    /** 获取工号 */
    getOrg(deptCode) {
      this.queryParams.orgId = deptCode
    },
    getOrg1(deptCode, deptId) {
      this.form.departmentId = deptId
      this.form.orgId = deptCode
    },
    /** 获取刷卡钟 */
    getMac(userIds) {
      this.queryParams.clockWorkId = userIds[0].name
    },
    /** 获取刷卡钟 */
    getMac1(userIds) {
      this.form.clockWorkId = userIds[0].name
    },
    /** 获取工号 */
    getJobNumber(val) {
      this.queryParams.empNo = val
    },
    /** 获取工号 */
    getJobNumber1(val) {
      this.form.empNo = val
    },
    resetType() {
      this.queryParams.orgId = null
      this.queryParams.empNo = null
      this.queryParams.clockWorkId = null
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
