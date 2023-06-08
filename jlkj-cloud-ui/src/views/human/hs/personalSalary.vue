<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司ID" clearable>
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="员工工号" prop="empNo">
        <el-input
          v-model="queryParams.empNo"
          placeholder="请输入员工工号"
        >
          <el-button slot="append" icon="el-icon-search" @click="inputClick()"></el-button>
        </el-input>
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
          v-hasPermi="['human:personalSalary:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="personalSalaryList" @selection-change="handleSelectionChange">
      <el-table-column label="姓名" align="center" prop="empName" />
      <el-table-column label="薪酬核定原因" align="center" prop="payCheckRea" />
      <el-table-column label="生效日期" align="center" prop="effectDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effectDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="主岗位" align="center" prop="mainPostName" />
      <el-table-column label="职位等级" align="center" prop="postLevel" />
      <el-table-column label="止薪日期" align="center" prop="endPayDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endPayDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="输入人" align="center" prop="creator" />
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:personalSalary:query']"
          >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:personalSalary:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:personalSalary:remove']"
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

    <!-- 添加或修改薪资核定对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1400px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" label-position="left">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="姓名" prop="empName">
              <el-input v-model="form.empName" placeholder="" disabled style="width: 150px;">
                <el-button slot="append" icon="el-icon-search" @click="inputClick()" ></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="主岗位" prop="mainPostName">
              {{form.mainPostName}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="职位等级" prop="postLevel">
              {{form.postLevel}}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="生效日期" prop="effectDate">
              <el-date-picker clearable
                              style="width: 150px;"
                              v-model="form.effectDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="薪酬核定原因" prop="payCheckReaId">
              <el-select v-model="form.payCheckReaId" placeholder="请选择" style="width: 150px;" ref="select1">
                <el-option
                  v-for="dict in salaryOptions.PayCheckRea"
                  :key="dict.id"
                  :label="dict.dicName"
                  :value="dict.id.toString()"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="公司名称" prop="compName">
              {{form.compName}}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="止薪日期" prop="endPayDate">
              <el-date-picker disabled
                              style="width: 150px;"
                              v-model="form.endPayDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否扣税" prop="istax">
              <el-radio-group v-model="form.istax">
                <el-radio
                  v-for="dict in salaryOptions.SalaryYN"
                  :key="dict.id"
                  :label="dict.id.toString()"
                >{{dict.dicName}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="绩效类别" prop="achievementType">
              <el-select v-model="form.achievementType" placeholder="请选择绩效类别" style="width: 150px;">
                <el-option
                  v-for="dict in salaryOptions.AchievementType"
                  :key="dict.id"
                  :label="dict.dicName"
                  :value="dict.id.toString()"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="定薪原因说明" prop="payCheckDesc">
          <el-input v-model="form.payCheckDesc" type="textarea" placeholder="请输入内容" show-word-limit maxlength="1000" />
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="工资支付银行" prop="salBankId">
              <el-select v-model="form.salBankId" placeholder="请选择" style="width: 250px;" ref="select2">
                <el-option
                  v-for="dict in salaryOptions.AchievementType"
                  :key="dict.id"
                  :label="dict.dicName"
                  :value="dict.id.toString()"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="工资支付账号" prop="salBankNo">
              <el-input v-model="form.salBankNo" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="输入人" prop="creator">
              {{form.creator}}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="奖金支付银行" prop="bonBankId">
              <el-select v-model="form.bonBankId" placeholder="请选择" style="width: 250px;" ref="select3">
                <el-option
                  v-for="dict in salaryOptions.AchievementType"
                  :key="dict.id"
                  :label="dict.dicName"
                  :value="dict.id.toString()"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="奖金支付账号" prop="bonBankNo">
              <el-input v-model="form.bonBankNo" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
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
  </div>
</template>

<script>
import { listPersonalSalary, getPersonalSalary, delPersonalSalary, addPersonalSalary, updatePersonalSalary } from "@/api/human/hs/personalSalary";
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getSalaryOptions, getSalaryDeepOptions } from '@/api/human/hs/salaryBasis'
import selectUser from '@/views/components/human/selectUser/selectUser'
import DictTagHumanBase from '@/views/components/human/dictTag/humanBaseInfo'
import '@/assets/styles/humanStyles.scss';

export default {
  name: "PersonalSalary",
  components: { selectUser, DictTagHumanBase },
  data() {
    return {
      //登录用户信息
      user: {},
      //公司数据
      companyList: [],
      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['PayCheckRea', 'AchievementType', 'SalaryYN'],
        compId: null
      },
      //薪资选单选项列表
      salaryOptions: [],
      // 遮罩层
      loading: true,
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
      // 薪资核定表格数据
      personalSalaryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openView: false,
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
        empName: [
          { required: true, message: '请选择员工', trigger: 'change' }
        ],
        effectDate: [
          { required: true, message: '生效日期不能为空', trigger: 'change' }
        ],
      }
    };
  },
  created() {
    this.initData()
    this.getCompanyList()
    this.getDisc();
    this.getList();
  },
  watch: {
    'queryParams.compId': {
      deep: true,
      immediate: false,
      handler: function(newV, preV) {
        this.getList()
      }
    }
  },
  methods: {
    //查询薪资选单
    getDisc() {
      getSalaryOptions(this.salaryOptionType).then(response => {
        this.salaryOptions = response.data
      })
    },
    //获取公司列表
    getCompanyList() {
      selectCompany().then(response => {
        this.companyList = response.data
      })
    },
    //初始化数据
    initData() {
      this.user.empNo = this.$store.state.user.userInfo.userName
      this.user.empId = this.$store.state.user.userInfo.userId
      this.user.empName = this.$store.state.user.userInfo.nickName
      this.user.compId = this.$store.state.user.userInfo.compId
      this.queryParams.compId = this.user.compId
    },
    //设置表单数据
    setForm(e) {
      if(e===0){
        this.form.compId = this.queryParams.compId;
        this.form.versionNo = 0;
      }
      if (this.form.payCheckReaId) {
        this.form.payCheckRea = this.$refs.select1.selectedLabel
      }
      if (this.form.salBankId) {
        this.form.salBank = this.$refs.select2.selectedLabel
      }
      if (this.form.bonBankId) {
        this.form.bonBank = this.$refs.select3.selectedLabel
      }
    },
    /** 人员选单事件 */
    inputClick() {
      var queryParams = {
        compID: this.queryParams.compId,
        pageNum: 1,
        pageSize: 10
      }
      this.$refs.select.show(queryParams)
    },
    /** 获取工号 */
    getJobNumber(empNo, empName, compId, id) {
      if (this.open == true) {
        this.form.empNo = empNo
        this.form.empId = id
        this.form.empName = empName
      } else {
        this.queryParams.empNo = empNo
      }
    },
    /** 查询薪资核定列表 */
    getList() {
      this.loading = true;
      listPersonalSalary(this.queryParams).then(response => {
        this.personalSalaryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.openView = false
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        payCheckReaId: null,
        payCheckRea: null,
        effectDate: null,
        mainPostName: null,
        postLevel: null,
        endPayDate: null,
        payCheckDesc: null,
        salBank: null,
        salBankId: null,
        salBankNo: null,
        bonBank: null,
        bonBankId: null,
        bonBankNo: null,
        compId: null,
        compName: null,
        postId: null,
        postFlag: null,
        postEffectDate: null,
        msgld: null,
        istax: null,
        empId: null,
        empNo: null,
        empName: null,
        versionNo: null,
        isCheck: null,
        achievementType: null,
        creator: null,
        creatorId: null,
        creatorNo: null,
        createDate: null,
        detailList: null,
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加薪资核定";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPersonalSalary(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改薪资核定";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePersonalSalary(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.setForm(0)
            addPersonalSalary(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除薪资核定编号为"' + ids + '"的数据项？').then(function() {
        return delPersonalSalary(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>
