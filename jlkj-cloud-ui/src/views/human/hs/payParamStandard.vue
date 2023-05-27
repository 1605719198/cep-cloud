<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" :rules="rules" v-show="showSearch" label-width="68px">
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
          <el-form-item label="薪资参数" prop="payParaId">
            <el-select v-model="queryParams.payParaId" placeholder="请选择薪资参数">
              <el-option
                v-for="dict in payParamStandard.SalaryParameters"
                :key="dict.dicNo"
                :label="dict.dicName"
                :value="dict.dicNo"
              ></el-option>
            </el-select>
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
              @click="handleSave"
              v-hasPermi="['human:payParamStandard:add']"
            >保存</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="status"
              @click="handleCopy"
              v-hasPermi="['human:payParamStandard:copy']"
            >复制</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="payParamStandardForm.payParamStandardList" :row-class-name="tableRowClassName" @row-click="addLine" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="编码" align="center" prop="standardCode">
            <template v-slot="scope">
              <el-input v-model="scope.row.standardCode" placeholder="请输入"/>
            </template>
          </el-table-column>
          <el-table-column label="标准" align="center" prop="standard">
            <template v-slot="scope">
              <el-input v-model="scope.row.standard" placeholder="请输入"/>
            </template>
          </el-table-column>
          <el-table-column label="说明" align="center" prop="description">
            <template v-slot="scope">
              <el-input v-model="scope.row.description" placeholder="请输入"/>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" prop="status">
            <template v-slot="scope">
              <el-select v-model="scope.row.status" placeholder="请选择">
                <el-option
                  v-for="dict in payParamStandard.status"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="输入人" align="center" prop="creator" />
          <el-table-column label="输入日期" align="center" prop="createDate" width="180">
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

        <!-- 各公司薪资参数设定复制对话框 -->
        <el-dialog title="请选择目标公司" :visible.sync="open" width="500px" append-to-body>
          <el-form ref="form" :model="form" label-width="150px">
            <el-form-item label="复制到" prop="compIdTo">
              <el-select v-model="form.compIdTo" placeholder="请选择公司">
                <el-option
                  v-for="dict in companyName"
                  :key="dict.compId"
                  :label="dict.companyName"
                  :value="dict.compId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitFormCopy">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listPayParamStandard, addPayParamStandard, copyPayParamStandard  } from "@/api/human/hs/payParamStandard";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getSalaryOptions} from "@/api/human/hs/salaryBasis";

export default {
  name: "PayParamStandard",
  data() {
    return {
      // 遮罩层
      loading: false,
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
      // 公司薪资参数设定表格数据
      payParamStandardForm: {
        payParamStandardList: []
      },
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //薪资选单类型查询
      salaryOptionType: {
        id: '19',
        optionsType: [
          'SalaryParameters',
          'status'
        ],
        compId:'J00',
      },
      payParamStandard: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        payParaId: '1',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        compId: [
          { required: false, message: "公司不能为空", trigger: "blur" }
        ],
        payParaId: [
          { required: false, message: "薪资参数不能为空", trigger: "blur" }
        ],
      },
      // 公司别下拉选单
      companyName: [],
      //登录人信息
      user: {},
      index: 0,
      status: true
    };
  },
  created() {
    this.initData();
    selectCompany().then(res => {
      this.companyName = res.data
    })
    getSalaryOptions(this.salaryOptionType).then(response =>{
      this.payParamStandard = response.data
    })
  },
  methods: {
    //初始化数据
    initData(){
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId
    },
    /** 查询公司薪资参数设定列表 */
    getList() {
      this.loading = true;
      listPayParamStandard(this.queryParams).then(response => {
        this.payParamStandardForm.payParamStandardList = response.data.rows;
        if (this.payParamStandardForm.payParamStandardList.length > 0) {
          this.status = false
        } else {
          this.payParamStandardForm.payParamStandardList = [
            {
              standardCode: undefined
            }
          ]
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
        compIdTo: null
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
      this.ids = selection.map(item => item.uuid)
      this.multiple = !selection.length
    },
    /** 保存按钮操作 */
    handleSave() {
      this.$refs['queryForm'].validate(valid => {
        this.payParamStandardForm.payParamStandardList.map(value => {
          value.compId = this.queryParams.compId
          value.payParaId = this.queryParams.payParaId
        })
        addPayParamStandard(this.payParamStandardForm).then(res => {
          this.$modal.msgSuccess("新增成功");
          this.getList();
        })
      })
    },
    //复制按钮点击事件
    handleCopy(){
      this.resetForm();
      this.open = true;
    },
    //复制确认
    submitFormCopy(){
      this.$refs['form'].validate(valid => {
        if (valid) {
            this.payParamStandardForm.payParamStandardList.map(value => {
              value.compId = this.form.compIdTo
              value.payParaId = this.queryParams.payParaId
            })
          copyPayParamStandard(this.payParamStandardForm).then( response =>{
              this.$modal.msgSuccess('复制成功')
              this.open = false;
              this.queryParams.compId = this.form.compIdTo
              this.getList()
            })
        }
      })
    },
    tableRowClassName({row, rowIndex}) {
      // 把每一行的索引放到row里
      row.index = rowIndex
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      if (this.payParamStandardForm.payParamStandardList.length === row.index + 1) {
        const newLine = {
          standardCode: undefined
        }
        this.index++
        this.payParamStandardForm.payParamStandardList.push(newLine)
      }
    },
  }
};
</script>
