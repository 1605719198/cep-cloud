<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
            <el-select v-model="queryParams.type" :popper-append-to-body="false" @change="changeType">
              <el-option
                v-for="dict in baseInfoData.CancellationPersonType"
                :key="dict.dicNo"
                :label="dict.dicName"
                :value="dict.dicNo"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-if="this.type === 'org'">
            <el-input v-model="queryParams.orgId" :disabled="true">
              <el-button slot="append" icon="el-icon-search" @click="openOrgPop"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item v-else-if="this.type === 'user'">
            <el-input v-model="queryParams.empNo" :disabled="true">
              <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item v-else>
            <el-select v-model="queryParams.clockWorkId" :popper-append-to-body="false" @change="changeType">
              <el-option
                v-for="dict in baseInfoData.CancellationPersonType"
                :key="dict.dicNo"
                :label="dict.dicName"
                :value="dict.dicNo"
              ></el-option>
            </el-select>
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
              <dict-tag-human :options="baseInfoData.CancellationPersonType" :value="scope.row.type"/>
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
              <el-col :span="12">
                <el-form-item label="员工编码" prop="empNo">
                  <el-input v-model="form.empNo" placeholder="请输入员工编码" />
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
        <select-user ref="select" @ok="getJobNumber"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listCancellationPerson, addCancellationPerson } from "@/api/human/hd/cancellationPerson";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getBaseInfo} from "@/api/human/hm/baseInfo";
import selectOrgPerson from "@/views/components/human/selectUser/selectOrgPerson";
import selectUser from "@/views/components/human/selectUser/selectUser";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";

export default {
  name: "CancellationPerson",
  components: {selectUser,selectOrgPerson,DictTagHuman},
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
        orgId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 公司别数据
      companyName: [],
      // 选单数据
      baseInfoData: [],
      baseInfo: {
        baseInfoList: [
          'CancellationPersonType']
      },
      type: 'org',
      tableColumns: [],
    };
  },
  created() {
    selectCompany().then(res => {
      this.companyName = res.data
    })
    getBaseInfo(this.baseInfo).then(response => {
      this.baseInfoData = response.data
    });
  },
  methods: {
    /** 查询人事注销记录列表 */
    getList() {
      this.loading = true;
      listCancellationPerson(this.queryParams).then(response => {
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
      this.handleQuery();
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
    changeType(val) {
      if (val === 'org'){
        this.type = 'org'
      } else if (val === 'user'){
        this.type = 'user'
      } else {
        this.type = 'mac'
      }
    },
    openOrgPop() {
      this.$refs.selectOrg.show();
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getOrg(deptCode, deptId) {
      this.queryParams.orgId = deptCode
      this.form.departmentId = deptId
      this.getList();
    },
    /** 获取工号 */
    getJobNumber(val) {
      this.queryParams.empNo = val
      this.getList();
    },
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
