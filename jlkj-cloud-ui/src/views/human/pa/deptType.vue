<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="公司别" prop="compId">
            <el-select v-model="queryParams.compId" placeholder="请选择公司">
              <el-option
                v-for="dict in companyName"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="生效年月" prop="effcMonth">
            <el-date-picker
              v-model="queryParams.effcMonth"
              value-format="yyyy-MM"
              type="month">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="人员类别" prop="deptType">
            <el-select v-model="queryParams.deptType" placeholder="人员类别" style="width: 100%">
              <el-option
                v-for="dict in baseInfoData.HP020"
                :key="dict.dicNo"
                :label="dict.dicNo + '-' + dict.dicName"
                :value="dict.dicNo"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="20">
          <el-col :span="5">
            <pagination
              v-show="deptTypeTotal>0"
              :total="deptTypeTotal"
              :page.sync="queryParams.pageNum1"
              :limit.sync="queryParams.pageSize1"
              @pagination="getList"
            />
            <el-table v-loading="loading" :data="deptTypeList" border @row-click="queryDeptTypeDetail">
              <el-table-column label="人员类别" align="center" prop="deptType">
                <template v-slot="scope">
                  <dict-tag-human :options="baseInfoData.HP020" :value="scope.row.deptType"/>
                </template>
              </el-table-column>
              <el-table-column label="生效年月" align="center" prop="effcMonth" />
            </el-table>
          </el-col>
          <el-col :span="19">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button
                  type="primary"
                  plain
                  icon="el-icon-plus"
                  size="mini"
                  @click="handleAdd"
                  v-hasPermi="['human:deptType:add']"
                >新增</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button
                  type="success"
                  plain
                  icon="el-icon-edit"
                  size="mini"
                  @click="handleUpdate"
                  v-hasPermi="['human:deptType:edit']"
                >修改</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button
                  type="danger"
                  plain
                  icon="el-icon-delete"
                  size="mini"
                  @click="handleDelete"
                  v-hasPermi="['human:deptType:remove']"
                >删除</el-button>
              </el-col>
              <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
            </el-row>
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="人员类别" prop="deptType">
                    <el-select v-model="form.deptType" :disabled="changeDeptType" placeholder="人员类别">
                      <el-option
                        v-for="dict in baseInfoData.HP020"
                        :key="dict.dicNo"
                        :label="dict.dicNo + '-' + dict.dicName"
                        :value="dict.dicNo"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="生效年月" prop="effcMonth">
                    <el-date-picker
                      v-model="form.effcMonth"
                      value-format="yyyy-MM"
                      type="month">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="团队绩效" prop="teamRatio">
                    <el-radio v-model="form.isTeam" label="0">是</el-radio>
                    <el-radio v-model="form.isTeam" label="1">否</el-radio>
                    <span style="font-size: 14px;color: #606266">比例</span><el-input v-model="form.teamRatio" size="mini" style="width: 50px;margin-left: 5px"/><span style="margin-left: 5px;color: #606266">%</span>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="重点工作" prop="pointRatio">
                    <el-radio v-model="form.isPoint" label="0">是</el-radio>
                    <el-radio v-model="form.isPoint" label="1">否</el-radio>
                    <span style="font-size: 14px;color: #606266">比例</span><el-input v-model="form.pointRatio" size="mini" style="width: 50px;margin-left: 5px"/><span style="margin-left: 5px;color: #606266">%</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="固定考评项目" prop="fixRatio">
                    <el-radio v-model="form.isFix" label="0" @input="changeIsFix">是</el-radio>
                    <el-radio v-model="form.isFix" label="1" @input="changeIsFixNo">否</el-radio>
                    <span style="font-size: 14px;color: #606266">比例</span><el-input v-model="form.fixRatio" size="mini" style="width: 50px;margin-left: 5px"/><span style="margin-left: 5px;color: #606266">%</span>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="是否自评" prop="kpiSelf">
                    <el-radio v-model="form.kpiSelf" label="0">是</el-radio>
                    <el-radio v-model="form.kpiSelf" label="1">否</el-radio>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="KPI" prop="kpiRatio">
                    <el-radio v-model="form.isKpi" label="0">是</el-radio>
                    <el-radio v-model="form.isKpi" label="1">否</el-radio>
                    <span style="font-size: 14px;color: #606266">比例</span><el-input v-model="form.kpiRatio" size="mini" style="width: 50px;margin-left: 5px"/><span style="margin-left: 5px;color: #606266">%</span>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="上报级数" prop="upLevel">
                    <el-select v-model="form.upLevel">
                      <el-option
                        v-for="dict in payTableData.Digit"
                        :key="dict.dicName"
                        :label="dict.dicName"
                        :value="dict.dicName"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="人事审核" prop="isHr">
                    <el-radio v-model="form.isHr" label="0">是</el-radio>
                    <el-radio v-model="form.isHr" label="1">否</el-radio>
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
              <el-row>
                <el-col :span="16">
                  <span>固定考评项目</span>
                </el-col>
                <el-col :span="8">
                  <el-button type="primary" plain icon="el-icon-plus" size="mini" style="float: right;margin-bottom: 5px" :disabled="isFix" @click="handleEvaluation">添加考评项目</el-button>
                </el-col>
              </el-row>
            </el-form>
            <el-table v-loading="deptTypeLoading" :data="deptTypeDetailList" border>
              <el-table-column label="编号" width="55" align="center" prop="srlNo"/>
              <el-table-column label="考评项目" align="center" prop="item"/>
              <el-table-column label="考评内容说明" align="center" prop="dataDept"/>
              <el-table-column label="权重%" align="center" prop="weight"/>
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template v-slot="scope">
                  <span style="color: #ff0000">{{ "——" }}</span>
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
          </el-col>
        </el-row>

        <!-- 添加考评项目对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="450px" append-to-body>
          <el-form ref="evaluationForm" :model="evaluationForm" :rules="rules" label-width="100px">
            <el-form-item label="编号" prop="srlNo">
              <el-input-number v-model="evaluationForm.srlNo" controls-position="right" :min="1" />
            </el-form-item>
            <el-form-item label="考评项目" prop="item">
              <el-input v-model="evaluationForm.item" placeholder="请输入指标项目" style="width: 90%"/>
            </el-form-item>
            <el-form-item label="考评内容说明" prop="dataDept">
              <el-input v-model="evaluationForm.dataDept" type="textarea" style="width: 90%"/>
            </el-form-item>
            <el-form-item label="权重" prop="weight">
              <el-input v-model="evaluationForm.weight" placeholder="请输入权重" style="width: 60%"><span slot="suffix">%</span></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm" v-hasPermi="['human:deptType:addDetail']">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import {
  listDeptType,
  delDeptType,
  addDeptType,
  listDeptTypeDetail, addDeptTypeDetail, updateDeptType
} from "@/api/human/pa/deptType";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getBaseInfo} from "@/api/human/hm/baseInfo";
import {isIntegerNotMust} from "@/utils/jlkj";
import {getSalaryOptions} from "@/api/human/hs/salaryBasis";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";

export default {
  name: "DeptType",
  components: {DictTagHuman },
  data() {
    return {
      // 遮罩层
      loading: false,
      // 明细档遮罩层
      deptTypeLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 人员类别考评项目设定主档表格总条数
      deptTypeTotal: 0,
      // 人员类别考评项目设定主档表格数据
      deptTypeList: [],
      // KPI指标明细档表格数据
      deptTypeDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pageNum1: 1,
        pageSize1: 10,
        compId: null,
        effcMonth: null,
      },
      // 明细表单参数
      form: {
        creator: null
      },
      // 考评项目表单参数
      evaluationForm: {},
      // 表单校验
      rules: {
        teamRatio:[
          { required: false, validator: isIntegerNotMust, trigger: "blur" }
        ],
        pointRatio:[
          { required: false, validator: isIntegerNotMust, trigger: "blur" }
        ],
        fixRatio:[
          { required: false, validator: isIntegerNotMust, trigger: "blur" }
        ],
        kpiRatio:[
          { required: false, validator: isIntegerNotMust, trigger: "blur" }
        ]
      },
      // 公司别下拉选单
      companyName: [],
      //登录人信息
      user: {},
      //选单数据
      baseInfoData: [],
      baseInfo: {
        baseInfoList: ['HP020']
      },
      isFix: true,
      changeDeptType: false,
      //薪资选单类型查询
      salaryOptionType: {
        id: '19',
        optionsType: [
          'Digit'],
        compId:null,
      },
      payTableData: [],
    };
  },
  created() {
    this.initData();
    selectCompany().then(res => {
      this.companyName = res.data
    })
    getBaseInfo(this.baseInfo).then(response => {
      this.baseInfoData = response.data
    });
    getSalaryOptions(this.salaryOptionType).then(response =>{
      this.payTableData = response.data
    })
  },
  methods: {
    //初始化数据
    initData(){
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId
    },
    /** 查询人员类别考评项目设定主档列表 */
    getList() {
      this.loading = true;
      listDeptType(this.queryParams).then(response => {
        this.deptTypeList = response.data.rows;
        this.loading = false;
      });
    },
    getDetailList() {
      this.deptTypeLoading = true;
      listDeptTypeDetail(this.form).then(response => {
        this.deptTypeDetailList = response.data.rows;
        this.deptTypeLoading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.evaluationForm = {
        item: null,
        dataDept: null,
        weight: null
      };
      this.resetForm("evaluationForm");
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
      this.form.compId = this.queryParams.compId
      addDeptType(this.form).then(res =>{
        this.queryParams.effcMonth = this.form.effcMonth
        this.queryParams.deptType = this.form.deptType
        this.getList();
        this.$modal.msgSuccess("新增成功");
      })
    },
    /** 添加考评项目按钮操作 */
    handleEvaluation() {
      if (this.form.creator === null) {
        this.$modal.msgSuccess("请点击左侧列表选择所要操作的数据");
      } else {
        this.reset();
        this.open = true;
        this.title = "添加考评项目";
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["evaluationForm"].validate(valid => {
        if (valid) {
          this.evaluationForm.deptTypeId = this.form.id
          this.evaluationForm.compId = this.form.compId
          addDeptTypeDetail(this.evaluationForm).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getDetailList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete() {
      delDeptType(this.form).then(res => {
        this.form = {}
        this.changeDeptType = false
        this.getDetailList();
        this.getList();
        this.$modal.msgSuccess("删除成功");
    })
    },
    /** 查询人员类别考评项目设定明细档表格 */
    queryDeptTypeDetail(row) {
      this.form = row
      if (this.form.isFix === '0') {
        this.changeIsFix();
      }
      this.changeDeptType = true
      this.getDetailList();
    },
    changeIsFix() {
      this.isFix = false
    },
    changeIsFixNo() {
      this.isFix = true
    },
    handleUpdate() {
      updateDeptType(this.form).then(res =>{
        this.queryParams.effcMonth = this.form.effcMonth
        this.queryParams.deptType = this.form.deptType
        this.getList();
        this.$modal.msgSuccess("修改成功");
      })
    }
  }
};
</script>
