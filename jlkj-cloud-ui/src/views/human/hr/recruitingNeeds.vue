<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select style="width:100%" v-model="queryParams.compId" placeholder="请选择公司别">
          <el-option
            v-for="dict in companyList"
            :key="dict.deptCode"
            :label="dict.companyName"
            :value="dict.deptCode"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="需求单号" prop="hiringId">
        <el-input
          v-model="queryParams.hiringId"
          placeholder="请输入需求单号"
          clearable
        />
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
          v-hasPermi="['human:recruitingNeeds:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['human:recruitingNeeds:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['human:recruitingNeeds:remove']"
        >删除
        </el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['human:recruitingNeeds:export']"-->
<!--        >导出-->
<!--        </el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recruitingNeedsList" @selection-change="handleSelectionChange" height="67vh">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="需求单号" align="center" prop="hiringId"/>
      <el-table-column label="需求岗位" align="center" prop="upperposiName"/>
      <el-table-column label="隶属机构" align="center" prop="hireDeptName"/>
      <el-table-column label="招聘方式" align="center" prop="hiretype" width="120">
        <template slot-scope="scope">
          <dict-tag-human :options="baseInfoData.HireType" :value="scope.row.hiretype"></dict-tag-human>
        </template>
      </el-table-column>
      <el-table-column label="申请日期" align="center" prop="applyDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请人" align="center" prop="applyNo">
        <template slot-scope="scope">
          <span>{{ userFormat(scope.row.applyNo, 1) }}</span>
        </template>
        userFormat
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:recruitingNeeds:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:recruitingNeeds:remove']"
          >删除
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

    <!-- 添加或修改招聘需求对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1300px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="需求单号" prop="hiringId">
              {{ form.hiringId }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="需求岗位编码" prop="upperposiNo">
              <el-input v-model="form.upperposiNo" placeholder="请选择岗位" disabled>
                <el-button slot="append" icon="el-icon-search" @click="inputPostClick()"
                ></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="隶属机构" prop="hireDeptNo">
              {{ form.hireDeptName }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="定员" prop="planCapacity">
              {{ form.planCapacity }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="现员" prop="nowCapacity">
              {{ form.nowCapacity }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="缺员" prop="needCapacity">
              {{ form.needCapacity }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="center">岗位职责</el-divider>
        <div style="display: flex;flex-direction: row;width: 100%;justify-content: start;">
          <div style="display: flex;flex-direction: row;justify-content: center;align-items: center; width: 150px;">
            <span style="font-size: 18px;">任职资格</span>
          </div>
          <div style="display: flex; flex-direction: column; justify-content: space-between;width: 100%;">
            <div style="display: flex;flex-direction: row;width: 100%;justify-content: start;height: 130px;">
              <div
                style="display: flex;justify-content: center;align-items: center;height: 80px;padding: 10px;width: 100px;"
              >
                <span>基本条件</span>
              </div>
              <div style="width: 100%;">
                <div
                  style="display: flex;flex-direction: row;align-items: center; justify-content: start; height: 27px;width: 100%;"
                >
                  <div style="width: 80px">
                    <span>年龄：</span>
                  </div>
                  <div style="margin-right: 50px;">
                    <span>{{ this.form.minAge }}-{{ this.form.maxAge }}</span>
                  </div>
                  <div style="width: 80px">
                    <span>性别：</span>
                  </div>
                  <div style="margin-right: 50px;">
                    <span><dict-tag :options="dict.type.sys_user_sex" :value="this.form.preGender"/></span>
                  </div>
                  <div style="width: 80px">
                    <span>最低学历：</span>
                  </div>
                  <div style="margin-right: 50px;">
                    <span>{{ this.form.recordId }}</span>
                  </div>
                </div>
                <div
                  style="display: flex;flex-direction: row;align-items: center; justify-content: start; height: 27px;width: 100%;"
                >
                  <div style="width: 80px">
                    <span>职业资格：</span>
                  </div>
                  <div>
                    <span>{{ this.form.proQua }}</span>
                  </div>
                </div>
                <div
                  style="display: flex;flex-direction: row;align-items: center; justify-content: start; height: 27px;width: 100%;"
                >
                  <div style="width: 80px">
                    <span>工作经验：</span>
                  </div>
                  <div>
                    <span>{{ this.form.workExp }}</span>
                  </div>
                </div>
                <div
                  style="display: flex;flex-direction: row;align-items: center; justify-content: start; height: 27px;width: 100%;"
                >
                  <div style="width: 80px">
                    <span>专业要求：</span>
                  </div>
                  <div>
                    <span>{{ this.form.speClaim }}</span>
                  </div>
                </div>
                <div
                  style="display: flex;flex-direction: row;align-items: center; justify-content: start; height: 27px;width: 100%;"
                >
                  <div style="width: 80px">
                    <span>其他要求：</span>
                  </div>
                  <div>
                    <span>{{ this.form.otherClaim }}</span>
                  </div>
                </div>
              </div>
            </div>
            <div style="display: flex;flex-direction: row;width: 100%;justify-content: start;">
              <div
                style="display: flex;justify-content: center;align-items: center;height: 40px;padding: 10px;width: 100px;"
              >
                <span>知识与技能</span>
              </div>
              <div
                style="display: flex;justify-content: start;align-items: center;height: 40px;padding: 10px;width:100%;"
              >
                <span>{{ this.form.knowTec }}</span>
              </div>
            </div>
            <div style="display: flex;flex-direction: row;width: 100%;justify-content: start;">
              <div
                style="display: flex;justify-content: center;align-items: center;height: 40px;padding: 10px;width: 100px;"
              >
                <span>职业化行为</span>
              </div>
              <div
                style="display: flex;justify-content: start;align-items: center;height: 40px;padding: 10px;width:100%;"
              >
                <span>{{ this.form.proAct }}</span>
              </div>
            </div>

          </div>

        </div>
        <el-divider content-position="center">需求资料</el-divider>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="需求理由" prop="hireReason">
              <el-select style="width:100%" v-model="form.hireReason" placeholder="请选择需求理由">
                <el-option
                  v-for="dict in baseInfoData.HiringReason"
                  :key="dict.uuid"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="需求人数" prop="hireCount">
              <el-input v-model.number="form.hireCount" placeholder="需求人数" type="number"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="工作地点" prop="workplace">
              <el-input v-model="form.workplace" placeholder="请输入工作地点" maxlength="100"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="倒班方式" prop="attendid">
              <el-select style="width:100%" v-model="form.attendid" placeholder="请选择倒班方式">
                <el-option
                  v-for="dict in baseInfoData.ShiftMode"
                  :key="dict.uuid"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="招聘方式" prop="hiretype">
              <el-select style="width:100%" v-model="form.hiretype" placeholder="请选择招聘方式">
                <el-option
                  v-for="dict in baseInfoData.HireType"
                  :key="dict.uuid"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="期望到岗日期" prop="wishdate">
              <el-date-picker clearable
                              style="width: 100%"
                              v-model="form.wishdate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择期望到岗日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="申请日期" prop="applyDate">
              {{ form.applyDate }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请人" prop="applyNo">
              <span>{{ userFormat(form.applyNo, 1) }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="修改时间" prop="updateDate">
              {{ form.updateDate }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="修改人" prop="updateNo">
              <span>{{ userFormat(form.updateNo, 1) }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="center">任职资格补充</el-divider>
        <el-row :gutter="20">
          <el-col :span="5">
            <el-form-item label="最小年龄" prop="minage">
              <el-input v-model="form.minage" type="number" style="width: 100px;" @blur="watchAge"/>
              岁
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="最大年龄" prop="maxage">
              <el-input v-model="form.maxage" type="number" style="width: 100px;" @blur="watchAge"/>
              岁
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select style="width:100%" v-model="form.gender" placeholder="请选择性别">
                <el-option
                  v-for="dict in baseInfoData.HrSex"
                  :key="dict.uuid"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="最高学历" prop="mingradute">
              <el-select style="width:100%" v-model="form.mingradute" placeholder="请选择最高学历">
                <el-option
                  v-for="dict in baseInfoData.qualification"
                  :key="dict.uuid"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工作经验" prop="workexperience">
              <el-input v-model="form.workexperience" placeholder="请输入工作经验" maxlength="100"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="专业要求" prop="miliduty">
              <el-input v-model="form.miliduty" placeholder="请输入专业要求" maxlength="100"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="第一学历" prop="firstDegree">
              <el-select style="width:100%" v-model="form.firstDegree" placeholder="请选择第一学历">
                <el-option
                  v-for="dict in baseInfoData.qualification"
                  :key="dict.uuid"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="年度绩效要求" prop="mertyear">
              <el-input v-model="form.mertyear" placeholder="请输入年度绩效要求" maxlength="50"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="职责补充" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入职责补充" maxlength="200"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <select-post ref="selectPost" @ok="getPost"/>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss'
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { queryAllUser } from '@/api/system/user'
import { listPostMaintenance } from '@/api/human/hp/postMaintenance'
import DictTagHuman from '@/views/components/human/dictTag/humanBaseInfo'
import { getBaseInfo } from '@/api/human/hm/baseInfo'
import {
  listRecruitingNeeds,
  getRecruitingNeeds,
  delRecruitingNeeds,
  addRecruitingNeeds,
  updateRecruitingNeeds
} from '@/api/human/hr/recruitingNeeds'
import SelectPost from '@/views/components/human/selectView/hp/selectPost'
import { isIntegerNotMust } from '@/utils/jlkj'

export default {
  name: 'RecruitingNeeds',
  dicts: ['sys_user_sex'],
  components: { DictTagHuman, SelectPost },
  data() {
    return {
      //用户字典
      resUserDiction: [],
      //公司数据
      companyList: [],
      //选单列表
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'ShiftMode',
          'HiringReason',
          'HireType',
          'qualification',
          'HrSex'
        ]
      },
      //选单数据
      baseInfoData: [],
      //登录人信息
      user: {
        empNo: null,
        empName: null,
        compId: null
      },
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
      // 招聘需求表格数据
      recruitingNeedsList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        hiringId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        upperposiNo: [
          { required: true, message: '需求岗位编码不能为空', trigger: 'blur' }
        ],
        hireCount: [
          { type: 'number', min: 1, max: 100000, message: '必须在1到100000之间', trigger: 'blur' },
          { required: false, validator: isIntegerNotMust, trigger: 'blur' }
        ],
        minage: [
          { required: false, validator: isIntegerNotMust, trigger: 'blur' }
        ],
        maxage: [
          { required: false, validator: isIntegerNotMust, trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    'queryParams.compId':{
      deep:true,
      immediate:false,
      handler:function(newV,preV){
        this.getList();
      }
    },
  },
  created() {
    this.initData()
    this.getList()
    this.getCompanyList()
    this.getHumanDisc()
  },
  beforeMount() {
    /** 装载人员信息 */
    queryAllUser().then(response => {
      this.resUserDiction = response.rows
    })
  },
  methods: {
    // 人员字典翻译
    userFormat(row, column) {
      if (column) {
        /** UserDictFullName 显示工号_姓名 */
        return this.UserDictFullName(this.resUserDiction, row)
      } else {
        /** UserDictOnlyName 只显示姓名 */
        return this.UserDictOnlyName(this.resUserDiction, row)
      }
    },
    //年龄监听
    watchAge() {
      if (this.form.maxage !== null && this.form.minage !== null) {
        if (this.form.minage >= this.form.maxage) {
          this.$modal.msgError('最大年龄应大于最小年龄')
          this.form.minage = 1
          this.form.maxage = 99
        }
      }
      if (this.form.minage > 99 || this.form.minage < 1) {
        this.form.minage = 1
      }
      if (this.form.maxage > 99 || this.form.maxage < 1) {
        this.form.maxage = 99
      }
    },
    /** 初始化数据 */
    initData() {
      this.user.empNo = this.$store.state.user.name
      this.user.empName = this.$store.state.user.userInfo.nickName
      this.user.compId = this.$store.state.user.userInfo.compId
      this.queryParams.compId = this.user.compId
    },
    /** 获取人事选单字典 */
    getHumanDisc() {
      getBaseInfo(this.baseInfo).then(response => {
        this.baseInfoData = response.data
      })
    },
    /** 查询公司列表 */
    getCompanyList() {
      selectCompany().then(response => {
          this.companyList = response.data
        }
      )
    },
    /** 查询招聘需求列表 */
    getList() {
      this.loading = true
      listRecruitingNeeds(this.queryParams).then(response => {
        this.recruitingNeedsList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        uuid: null,
        compId: null,
        hiringId: null,
        hireDeptNo: null,
        hireDeptName: null,
        planCapacity: null,
        nowCapacity: null,
        needCapacity: null,
        minAge: null,
        maxAge: null,
        preGender: null,
        recordId: null,
        speClaim: null,
        proQua: null,
        workExp: null,
        otherClaim: null,
        knowTec: null,
        proAct: null,
        upperposiId: null,
        upperposiNo: null,
        upperposiName: null,
        hireReason: null,
        hireCount: null,
        workplace: null,
        attendid: null,
        hiretype: null,
        wishdate: null,
        applyDate: null,
        applyNo: null,
        updateDate: null,
        updateNo: null,
        minage: null,
        maxage: null,
        mingradute: null,
        miliduty: null,
        miliduty2: null,
        remark: null,
        firstDegree: null,
        workexperience: null,
        status: null,
        mertyear: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.uuid)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.form.compId = this.queryParams.compId
      this.open = true
      this.title = '添加招聘需求'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const uuid = row.uuid || this.ids
      getRecruitingNeeds(uuid).then(response => {
        this.form = response.data
        listPostMaintenance({ postCode: response.data.upperposiId }).then(response => {
          if(response.total!==0){
            this.getPost(response.rows[0])
          }
          this.open = true
          this.title = '修改招聘需求'
        })
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.uuid != null) {
            updateRecruitingNeeds(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addRecruitingNeeds(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uuids = row.uuid || this.ids
      this.$modal.confirm('是否确认删除所选择的数据项？').then(function() {
        return delRecruitingNeeds(uuids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/recruitingNeeds/export', {
        ...this.queryParams
      }, `recruitingNeeds_${new Date().getTime()}.xlsx`)
    },
    //岗位弹窗打开事件
    inputPostClick() {
      var compId = this.form.compId
      this.$refs.selectPost.show(compId)
    },
    //岗位弹窗确定事件
    getPost(postData) {
      this.form.upperposiId = postData.postId
      this.form.upperposiNo = postData.postCode
      this.form.upperposiName = postData.postName
      this.form.hireDeptNo = postData.orgId
      this.form.hireDeptName = postData.orgName
      this.form.planCapacity = postData.planCapacity
      this.form.nowCapacity = postData.nowCapacity
      this.form.needCapacity = ((this.form.planCapacity - this.form.nowCapacity) < 0) ? 0 : this.form.planCapacity - this.form.nowCapacity
      this.form.minAge = postData.minAge
      this.form.maxAge = postData.maxAge
      this.form.preGender = postData.gender
      this.form.recordId = postData.recordId
      this.form.speClaim = postData.speClaim
      this.form.proQua = postData.proQua
      this.form.workExp = postData.workExp
      this.form.otherClaim = postData.otherClaim
      this.form.knowTec = postData.knowTec
      this.form.proAct = postData.proAct
      this.form.parentPostId = postData.postId
      this.form.parentPostName = postData.postName
    }
  }
}
</script>
<style scoped>

</style>
