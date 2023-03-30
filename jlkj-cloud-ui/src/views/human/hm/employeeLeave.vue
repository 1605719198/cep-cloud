<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="公司">
            <el-select v-model="queryParams.compId" placeholder="请选择公司">
              <el-option
                v-for="dict in companyName"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="工号">
            <el-input
              v-model="queryParams.empNo"
              placeholder="请输入工号"
              disabled
            >
              <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button size="mini"
                       type="primary"
                       @click="getList"
                       icon="el-icon-search">搜 索</el-button>
            <el-button size="mini"
                       type="default"
                       @click="handleEmpty"
                       icon="el-icon-refresh-left">重 置</el-button>
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
              v-hasPermi="['human:employeeLeave:add']"
            >新增</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="employeeLeaveList">
          <el-table-column label="工号" align="center" prop="empNo" />
          <el-table-column label="姓名" align="center" prop="empName" />
          <el-table-column label="离职生效日期" align="center" prop="leaveEffectDate" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.leaveEffectDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="离职类别" align="center" prop="leaveTypeId" >
            <template v-slot="scope">
              <dict-tag-human :options="baseInfoData.ResignationCategory" :value="scope.row.leaveTypeId"/>
            </template>
          </el-table-column>
          <el-table-column label="输入人" align="center" prop="creator" />
          <el-table-column label="输入日期" align="center" prop="createDate" width="180">
            <template v-slot="scope">
              <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template v-slot="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['human:employeeLeave:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['human:employeeLeave:remove']"
              >删除</el-button>
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

        <!-- 添加或修改人员离职信息对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="850px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="110px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="工号" prop="empNo">
                  <el-input v-model="form.empNo" placeholder="请输入工号" disabled>
                    <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="姓名" prop="empName">
                  <el-input v-model="form.empName" placeholder="请输入姓名" disabled/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="离职前主岗位" prop="leaveMainPost">
                  <el-input v-model="form.leaveMainPost" placeholder="请输入离职前主岗位" disabled/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="入企日期" prop="enterDate">
                  <el-date-picker clearable
                                  v-model="form.enterDate"
                                  type="date"
                                  class="aa"
                                  disabled
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择入企日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="离职生效日期" prop="leaveEffectDate">
                  <el-date-picker clearable
                                  v-model="form.leaveEffectDate"
                                  type="date"
                                  class="aa"
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择离职生效日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="离职类别" prop="leaveTypeId">
                  <el-select v-model="form.leaveTypeId" placeholder="请选择" style="width: 100%">
                    <el-option
                      v-for="dict in baseInfoData.ResignationCategory"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="离职原因" prop="leaveReason">
                  <el-select v-model="form.leaveReason" placeholder="请选择" style="width: 131px" @change="changeLeaveReason">
                    <el-option
                      v-for="dict in baseInfoData.ReasonForResignation"
                      :key="dict.uuid"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item prop="leaveReasonDetail">
                  <el-select v-model="form.leaveReasonDetail" style="width: 158px;margin-left: -65px">
                    <el-option
                      v-for="dict in leaveReasonDetail"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="止薪日期" prop="endPayDate">
                  <el-date-picker clearable
                                  v-model="form.endPayDate"
                                  type="date"
                                  class="aa"
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择止薪日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="输入人" prop="creator">{{form.creator}}</el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="输入日期" prop="createDate">{{ parseTime(form.createDate, '{y}-{m}-{d}') }}</el-form-item>
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
import {getBaseInfo, getDegreeMajorSpecialization} from "@/api/human/hm/baseInfo";
import selectUser from "@/views/components/human/selectUser/selectUser";
import {listEmployeeLeave, getEmployeeLeave, addEmployeeLeave, updateEmployeeLeave, delEmployeeLeave} from "@/api/human/hm/employeeLeave";
import {queryNewPostNameAndChangeDetail} from "@/api/human/hm/employeeTurnover";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";
import {selectCompany} from "@/api/human/hp/deptMaintenance";

export default {
  name: "employeeLeave",
  dicts: ['comp_id'],
  components: {selectUser, DictTagHuman},
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 人员离职信息表格数据
      employeeLeaveList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        empNo: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        leaveEffectDate: [
          { required: true, message: "离职生效日期不能为空", trigger: "blur" }
        ],
        leaveTypeId: [
          { required: true, message: "离职类别不能为空", trigger: "blur" }
        ],
        leaveReason: [
          { required: true, message: "离职原因不能为空", trigger: "blur" }
        ],
      },
      //选单数据
      baseInfoData: [],
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'ResignationCategory',
          'ReasonForResignation']
      },
      //离职原因细分
      leaveReasonDetail: [],
      companyName: []
    };
  },
  created() {
    getBaseInfo(this.baseInfo).then(response => {
      this.baseInfoData = response.data
    });
    selectCompany().then(res => {
      this.companyName = res.data
    })
  },
  methods: {
    /** 查询人员离职信息列表 */
    getList() {
      this.loading = true;
      listEmployeeLeave(this.queryParams).then(response => {
        this.employeeLeaveList = response.data.rows;
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
        empNo: null,
        empName: null,
        leaveMainPost: null,
        enterDate: null,
        leaveEffectDate: new Date(),
        endPayDate: new Date(),
        leaveTypeId: null,
        leaveReason: null,
        leaveReasonDetail: null,
        creator: null,
        createDate: new Date(),
      };
      this.resetForm("form");
    },
    /** 重置按钮操作 */
    handleEmpty() {
      this.queryParams = {
        compId: undefined,
        empNo: undefined
      }
      this.getList()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加员工离职资料";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const uuid = row.uuid
      getEmployeeLeave(uuid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工离职资料";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uuid != null) {
            updateEmployeeLeave(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEmployeeLeave(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uuids = row.uuid;
      const empNo = row.empNo;
      this.$modal.confirm('是否确认删除工号为"' + empNo + '"的数据项？').then(function() {
        return delEmployeeLeave(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    changeLeaveReason(val) {
      const selectedItem = this.baseInfoData.ReasonForResignation.find((item) => {
        return item.dicNo === val
      })
      this.baseInfo.uuid = selectedItem.uuid
      this.leaveReasonDetail = []
      getDegreeMajorSpecialization(this.baseInfo).then(response => {
        this.leaveReasonDetail = response.data
      });
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(val, userName) {
      this.queryParams.empNo = val
      this.form.empNo = val
      this.form.empName = userName
      this.getList();
      queryNewPostNameAndChangeDetail(this.form).then(res => {
        this.form.leaveMainPost = res.data.list1[0].newPostName
        this.form.enterDate = res.data.list[0].effectDate
      })
    },
  }
};
</script>
<style scoped>
.aa {
  width: 295px;
}
</style>
