<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
          <el-form-item label="指标年月" prop="effcMonth">
            <el-date-picker
              v-model="queryParams.effcMonth"
              value-format="yyyy-MM"
              type="month">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="组织机构" prop="dept">
            <el-input v-model="queryParams.dept" disabled>
              <el-button slot="append" icon="el-icon-search" @click="openOrgPop1"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="20">
          <el-col :span="5">
            <el-table v-loading="loading" :data="deptKpiList" border @row-click="queryKpiDetail">
              <el-table-column type="index" label="序号" width="55" align="center" />
              <el-table-column label="生效年月" align="center" prop="effcMonth" />
              <el-table-column label="一级单位" align="center" prop="dept" />
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
                  v-hasPermi="['human:deptKpi:add']"
                >新增</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button
                  type="danger"
                  plain
                  icon="el-icon-delete"
                  size="mini"
                  @click="handleDelete"
                  v-hasPermi="['human:deptKpi:remove']"
                >删除</el-button>
              </el-col>
              <el-col :span="1.5">
                <el-button
                  type="success"
                  plain
                  icon="el-icon-circle-check"
                  size="mini"
                  :disabled="form.status==null"
                  @click="handleConfirm"
                  v-hasPermi="['human:deptKpi:confirm']"
                >确认</el-button>
              </el-col>
              <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
            </el-row>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="组织机构" prop="dept">
                    <el-input v-model="form.dept" disabled style="width: 70%">
                      <el-button slot="append" icon="el-icon-search" @click="openOrgPop2"></el-button>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="指标年月" prop="effcMonth">
                    <el-date-picker
                      v-model="form.effcMonth"
                      value-format="yyyy-MM"
                      type="month">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="状态" prop="status">
                    {{form.status}}
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="输入人" prop="creator">
                    {{form.creator}}
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="输入日期" prop="createDate">
                    {{form.createDate}}
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-button type="primary" plain icon="el-icon-plus" size="mini" style="float: right" :disabled="form.dept==null" @click="handleEvaluation">添加考评项目</el-button>
                </el-col>
              </el-row>
            </el-form>
            <el-table :data="deptKpiDetailList" border>
              <el-table-column label="编码" width="55" align="center" prop="num"/>
              <el-table-column label="指标项目" align="center" prop="item"/>
              <el-table-column label="数据提供单位" align="center" prop="dataDept"/>
              <el-table-column label="权重%" align="center" prop="weight"/>
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template v-slot="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="handleDeleteDetail(scope.row)"
                    v-hasPermi="['human:deptKpi:removeDetail']"
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
          </el-col>
        </el-row>

        <!-- 添加考评项目对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="450px" append-to-body>
          <el-form ref="evaluationForm" :model="evaluationForm" :rules="rules" label-width="100px">
            <el-form-item label="编码" prop="num">
              <el-input-number v-model="evaluationForm.num" controls-position="right" :min="1" />
            </el-form-item>
            <el-form-item label="指标项目" prop="item">
              <el-input v-model="evaluationForm.item" placeholder="请输入指标项目" style="width: 90%"/>
            </el-form-item>
            <el-form-item label="数据提供单位" prop="dataDept">
              <el-input v-model="evaluationForm.dataDept" disabled style="width: 90%">
                <el-button slot="append" icon="el-icon-search" @click="openOrgPop"></el-button>
              </el-input>
            </el-form-item>
            <el-form-item label="权重" prop="weight">
              <el-input v-model="evaluationForm.weight" placeholder="请输入权重" style="width: 60%"><span slot="suffix">%</span></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm" v-hasPermi="['human:deptKpi:addDetail']">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>
        <select-org ref="selectOrg" @ok="getOrg"/>
        <select-org ref="selectOrg1" @ok="getOrg1"/>
        <select-org ref="selectOrg2" @ok="getOrg2"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import {
  listDeptKpi,
  delDeptKpi,
  addDeptKpi,
  listDeptKpiDetail,
  delDeptKpiDetail, addDeptKpiDetail, updateDeptKpiStatus
} from "@/api/human/pa/deptKpi";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import selectOrg from "@/views/components/human/selectUser/selectOrgPerson";

export default {
  name: "DeptKpi",
  components: { selectOrg },
  data() {
    return {
      // 遮罩层
      loading: false,
      // 明细档遮罩层
      deptKpiLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // KPI指标主档表格数据
      deptKpiList: [],
      // KPI指标明细档表格数据
      deptKpiDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        dept: null,
        effcMonth: null,
      },
      // 明细表单参数
      form: {
        dept: null
      },
      // 考评项目表单参数
      evaluationForm: {},
      // 确认按钮参数
      confirmForm: {},
      // 表单校验
      rules: {
      },
      // 公司别下拉选单
      companyName: [],
      //登录人信息
      user: {},
    };
  },
  created() {
    this.initData();
    selectCompany().then(res => {
      this.companyName = res.data
    })
  },
  methods: {
    //初始化数据
    initData(){
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId
    },
    /** 查询KPI指标主档列表 */
    getList() {
      this.loading = true;
      listDeptKpi(this.queryParams).then(response => {
        this.deptKpiList = response.data.rows;
        this.loading = false;
      });
    },
    getDetailList() {
      this.deptKpiLoading = true;
      listDeptKpiDetail(this.form).then(response => {
        this.deptKpiDetailList = response.data.rows;
        this.deptKpiLoading = false;
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
      addDeptKpi(this.form).then(res =>{
        this.queryParams.effcMonth = this.form.effcMonth
        this.getList();
        this.$modal.msgSuccess("新增成功");
      })
    },
    /** 添加考评项目按钮操作 */
    handleEvaluation() {
      this.reset();
      this.open = true;
      this.title = "添加考评项目";
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["evaluationForm"].validate(valid => {
        if (valid) {
          this.evaluationForm.kpiId = this.form.id
          this.evaluationForm.compId = this.form.compId
          this.evaluationForm.dept = this.form.dept
          this.evaluationForm.effcMonth = this.form.effcMonth
          addDeptKpiDetail(this.evaluationForm).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getDetailList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete() {
      const id = this.form.id
      this.$modal.confirm('是否确认删除KPI指标明细档编号为"' + id + '"的数据项？').then(function() {
        return delDeptKpi(id);
      }).then(() => {
        this.form = {}
        this.getDetailList();
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 删除明细档按钮操作 */
    handleDeleteDetail(row) {
      const detailId = row.id
      this.$modal.confirm('是否确认删除KPI指标明细档编号为"' + detailId + '"的数据项？').then(function() {
        return delDeptKpiDetail(detailId);
      }).then(() => {
        this.getDetailList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    openOrgPop() {
      this.$refs.selectOrg.show();
    },
    openOrgPop1() {
      this.$refs.selectOrg1.show();
    },
    openOrgPop2() {
      this.$refs.selectOrg2.show();
    },
    /** 获取工号 */
    getOrg(val,val1,deptName) {
      this.evaluationForm.dataDept = val
    },
    /** 获取工号 */
    getOrg1(val,val1,deptName) {
      this.queryParams.dept = val
    },
    /** 获取工号 */
    getOrg2(val,val1,deptName) {
      this.form.dept = val
    },
    /** 查询KPI指标明细档表格 */
    queryKpiDetail(row) {
      this.form = row
      if (row.status === '1'){
        this.form.status = '已录入'
      } else if (row.status === '2'){
        this.form.status = '已确认'
      }
      this.getDetailList();
    },
    /** 单位KPI指标主档确认按钮 */
    handleConfirm() {
      this.confirmForm.deptKpiDetailList = this.deptKpiDetailList
      this.confirmForm.id = this.form.id
      updateDeptKpiStatus(this.confirmForm).then(res =>{
        this.form.status = "已确认"
        this.$modal.msgSuccess("确认成功");
      })
    }
  }
};
</script>
