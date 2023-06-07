<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" :rules="rules" label-width="68px">
          <el-form-item label="公司" prop="compId">
          <el-select v-model="queryParams.compId" placeholder="请选择公司">
            <el-option
              v-for="dict in companyName"
              :key="dict.compId"
              :label="dict.companyName"
              :value="dict.compId"
            ></el-option>
          </el-select>
        </el-form-item>
          <el-form-item label="工号" prop="empNo">
            <el-input v-model="queryParams.empNo" placeholder="请输入工号" :disabled="true">
              <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item label="日期" prop="slotCardOnduty">
            <el-date-picker
              v-model="queryParams.slotCardOnduty"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="~"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              @change="dateFormat">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

        <el-table v-loading="loading" :data="attendanceAbnormalList">
          <el-table-column label="工号" align="center" prop="empNo"/>
          <el-table-column label="姓名" align="center" prop="empName"/>
          <el-table-column label="正常出勤时段" align="center" prop="norOndutyBegin" width="300"/>
          <el-table-column label="实际出勤时段" align="center" prop="slotCardOnduty" width="300"/>
          <el-table-column label="出勤证明原因" align="center" prop="proveReason" >
            <template v-slot="scope">
              <dict-tag-human :options="attendenceOptions.ProveReason" :value="scope.row.proveReason"/>
            </template>
          </el-table-column>
          <el-table-column label="辅助说明" align="center" prop="description" />
          <el-table-column label="审批状态" align="center" prop="status">
            <template v-slot="scope">
              <dict-tag-human-basis :options="attendenceOptions.HolidayStatus" :value="scope.row.status"/>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template v-slot="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                :disabled="scope.row.status!=='01'"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['human:attendanceAbnormal:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-right"
                @click="handleSend(scope.row)"
                v-hasPermi="['human:attendanceAbnormal:edit']"
              >送审</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-back"
                @click="handleRecall(scope.row)"
                v-hasPermi="['human:attendanceAbnormal:edit']"
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

        <!-- 修改出勤异常对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="120px">
            <el-row>
              <el-col :span="9">
                <el-form-item label="申请人" prop="empNo">
                  {{form.empNo}}
                </el-form-item>
              </el-col>
              <el-col :span="9">
                <el-form-item label="姓名" prop="empName">
                  {{form.empName}}
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="岗位" prop="postName">
                  {{form.postName}}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="9">
                <el-form-item label="正常出勤时段" prop="norOndutyBegin">
                  {{form.norOndutyBegin}}~{{form.norOndutyEnd}}
                </el-form-item>
              </el-col>
              <el-col :span="9">
                <el-form-item label="实际出勤时段" prop="slotCardOnduty">
                  {{form.slotCardOnduty}}~{{form.slotCardOffduty}}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="9">
                <el-form-item label="出勤证明原因">
                  <el-select v-model="form.proveReason" style="width: 100%">
                    <el-option
                      v-for="dict in attendenceOptions.ProveReason"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="9">
                <el-form-item label="辅助说明" prop="description">
                  <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="9">
                <el-form-item label="审批状态" prop="status">
                  {{form.status}}
                </el-form-item>
              </el-col>
              <el-col :span="9">
                <el-form-item label="输入人" prop="creator">
                  {{form.creator}}
                </el-form-item>
              </el-col>
              <el-col :span="6">
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
        <select-user ref="select" @ok="getJobNumber"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listAttendanceAbnormal, getAttendanceAbnormal, updateAttendanceAbnormal, recallAttendanceAbnormal, sendAttendanceAbnormal } from "@/api/human/hd/attendanceAbnormal";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import selectUser from "@/views/components/human/selectUser/selectUser";
import {getBaseInfo} from "@/api/human/hm/baseInfo";
import {validateNumber} from "@/utils/jlkj";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";
import {getAttendenceOptions} from "@/api/human/hd/attendenceBasis";
import DictTagHumanBasis from "@/views/components/human/dictTag/humanBaseInfo";

export default {
  name: "AttendanceAbnormal",
  components: {selectUser,DictTagHuman,DictTagHumanBasis},
  data() {
    return {
      // 遮罩层
      loading: false,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 出勤异常表格数据
      attendanceAbnormalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
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
      // 表单参数
      form: {},
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
        optionsType: ['HolidayStatus','ProveReason']
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
      listAttendanceAbnormal(this.queryParams).then(response => {
        this.attendanceAbnormalList = response.data.rows;
        for (let i = 0; i < this.attendanceAbnormalList.length; i++) {
          this.attendanceAbnormalList[i].slotCardOnduty = this.attendanceAbnormalList[i].slotCardOnduty + '~' + this.attendanceAbnormalList[i].slotCardOffduty
          this.attendanceAbnormalList[i].norOndutyBegin = this.attendanceAbnormalList[i].norOndutyBegin + '~' + this.attendanceAbnormalList[i].norOndutyEnd
        }
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
        compId: null,
        empId: null,
        empName: null,
        empNo: null,
        postId: null,
        postName: null,
        turnTypeId: null,
        turnTypeName: null,
        classId: null,
        className: null,
        excReaId: null,
        proveReaId: null,
        proveReason: null,
        status: null,
        norOndutyBegin: null,
        norOndutyEnd: null,
        slotCardOnduty: null,
        slotCardOffduty: null,
        description: null,
        disposeId: null,
        auditType: null,
        creator: null,
        creatorId: null,
        createDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.getList();
        }
      });
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.empNo = undefined
      this.queryParams.slotCardOnduty = undefined
      this.handleQuery();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id
      getAttendanceAbnormal(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改出勤异常申请资料";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
            updateAttendanceAbnormal(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
        }
      });
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
    dateFormat(picker) {
      this.queryParams.startTime=picker[0]
      this.queryParams.endTime=picker[1]
    },
    handleSend(row) {
      sendAttendanceAbnormal(row).then(res => {
        this.$modal.msgSuccess("送审成功");
        this.getList();
      })
    },
    handleRecall(row) {
      recallAttendanceAbnormal(row).then(res => {
        this.$modal.msgSuccess("撤回成功");
        this.getList();
      })
    }
  }
};
</script>
