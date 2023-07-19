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
            <el-button v-hasPermi="['human:employeeEncourage:list']"
                       size="mini"
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
              v-hasPermi="['human:employeeEncourage:add']"
            >新增</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="employeeEncourageList">
          <el-table-column label="工号" align="center" prop="empNo" />
          <el-table-column label="姓名" align="center" prop="empName" />
          <el-table-column label="岗位" align="center" prop="postFullName" />
          <el-table-column label="奖惩类别" align="center" prop="encourageType">
            <template v-slot="scope">
              <dict-tag-human :options="baseInfoData.EncourageType" :value="scope.row.encourageType"/>
            </template>
          </el-table-column>
          <el-table-column label="输入人" align="center" prop="creator" />
          <el-table-column label="输入时间" align="center" prop="createDate" width="180">
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
                v-hasPermi="['human:employeeEncourage:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['human:employeeEncourage:remove']"
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

        <!-- 添加或修改人员奖惩信息对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-row>
              <el-col :span="8">
                <el-form-item label="工号" prop="empNo">
                  <el-input v-model="form.empNo" placeholder="请输入工号" disabled>
                    <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="姓名" prop="empName">
                  <el-input v-model="form.empName" placeholder="请输入姓名" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="职位等级" prop="postLevel">
                  <el-input v-model="form.postLevel" placeholder="请输入职位等级" disabled/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="16">
                <el-form-item label="岗位" prop="postFullName">
                  <el-input v-model="form.postFullName" placeholder="请输入岗位" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="生效日期" prop="effectDate">
                  <el-date-picker clearable
                                  v-model="form.effectDate"
                                  type="date"
                                  class="aa"
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择生效日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="奖惩类别" prop="encourageType">
                  <el-select v-model="form.encourageType">
                    <el-option
                      v-for="dict in baseInfoData.EncourageType"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="16">
                <el-form-item label="奖惩依据" prop="encourageAccording">
                  <el-input v-model="form.encourageAccording" placeholder="请输入奖惩依据" :maxlength="500" show-word-limit/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="奖惩原因" prop="encourageReason">
                  <el-input v-model="form.encourageReason" type="textarea" placeholder="请输入内容" :maxlength="500" show-word-limit/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="公文文号" prop="docNo">
                  <el-input v-model="form.docNo" type="textarea" placeholder="请输入公文文号"  :maxlength="200" show-word-limit/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="备注" prop="remark">
                  <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" :maxlength="500" show-word-limit/>
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
import { listEmployeeEncourage, getEmployeeEncourage, delEmployeeEncourage, addEmployeeEncourage, updateEmployeeEncourage } from "@/api/human/hm/employeeEncourage";
import selectUser from "@/views/components/human/selectUser/selectUser";
import {queryNewPostNameAndChangeDetail} from "@/api/human/hm/employeeTurnover";
import {getBaseInfo} from "@/api/human/hm/baseInfo";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import '@/assets/styles/humanStyles.scss';

export default {
  name: "employeeEncourage",
  components: {selectUser,DictTagHuman},
  data() {
    return {
      // 遮罩层
      loading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 人员奖惩信息表格数据
      employeeEncourageList: [],
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
        effectDate: [
          { required: true, message: "生效日期不能为空", trigger: "blur" }
        ],
        encourageType: [
          { required: true, message: "奖惩类别不能为空", trigger: "blur" }
        ],
        encourageAccording: [
          { required: true, message: "奖惩依据不能为空", trigger: "blur" }
        ],
        encourageReason: [
          { required: true, message: "奖惩原因不能为空", trigger: "blur" }
        ],
      },
      //选单数据
      baseInfoData: [],
      baseInfo: {
        baseInfoList: [
          'EncourageType']
      },
      companyName: []
    };
  },
  created() {
    this.queryParams.compId = this.$store.state.user.userInfo.compId
    getBaseInfo(this.baseInfo).then(response => {
      this.baseInfoData = response.data
    });
    selectCompany().then(res => {
      this.companyName = res.data
    })
  },
  methods: {
    /** 查询人员奖惩信息列表 */
    getList() {
      this.loading = true;
      listEmployeeEncourage(this.queryParams).then(response => {
        this.employeeEncourageList = response.data.rows;
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
        uuid: null,
        compId: null,
        empId: null,
        empName: null,
        empNo: null,
        postId: null,
        postFullName: null,
        effectDate: null,
        postLevel: null,
        encourageType: null,
        encourageAccording: null,
        encourageReason: null,
        docNo: null,
        remark: null,
        creator: null,
        creatorId: null,
        createDate: null,
        alternateField1: null,
        alternateField2: null
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
      this.title = "新增员工奖惩资料";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const uuid = row.uuid;
      getEmployeeEncourage(uuid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工奖惩资料";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uuid != null) {
            updateEmployeeEncourage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.compId = this.queryParams.compId
            addEmployeeEncourage(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除人员奖惩信息编号为"' + uuids + '"的数据项？').then(function() {
        return delEmployeeEncourage(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
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
        this.form.postFullName = res.data.list1[0].newPostName
        this.form.postLevel = res.data.list[0].postLevel
      })
    },
  }
};
</script>
<style scoped>
.aa {
  width: 193px;
}
</style>

