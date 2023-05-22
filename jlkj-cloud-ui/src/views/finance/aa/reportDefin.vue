<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="报表定义" name="first">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="公司" prop="companyId">
            <el-select v-model="queryParams.companyId" placeholder="请选择公司">
              <el-option
                v-for="item in companyList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="报表代号" prop="reportNo">
            <el-input
              v-model="queryParams.reportNo"
              placeholder="请输入报表代号"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="报表名称" prop="reportName">
            <el-input
              v-model="queryParams.reportName"
              placeholder="请输入报表名称"
              clearable
              @keyup.enter.native="handleQuery"
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
              v-hasPermi="['finance:reportDefin:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['finance:reportDefin:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['finance:reportDefin:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['finance:reportDefin:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="reportDefinList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="报表代号" align="center" prop="reportNo" />
          <el-table-column label="报表名称" prop="reportName" show-overflow-tooltip align="center" >
            <template slot-scope="scope">
              <el-button type="text" @click="gotoTab2(scope.row)" >{{scope.row.reportName}}</el-button>
            </template>
          </el-table-column>
          <el-table-column label="表尾" align="center" prop="reportTail" />
          <el-table-column label="ureport名称" align="center" prop="ureportName" />
          <el-table-column label="报表形式" align="center" prop="reportType" />
          <el-table-column label="异动人" align="center" prop="updateName" />
          <el-table-column label="异动日期" align="center" prop="updateTime" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['finance:reportDefin:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['finance:reportDefin:remove']"
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
      </el-tab-pane>
      <el-tab-pane label="纵向印表项目" name="second">
        <project-formula ref="projectFormula"  @gotoContentTab="gotoTab3"></project-formula>
      </el-tab-pane>
      <el-tab-pane label="项目公式" name="third">
        <project-content ref="projectContent" ></project-content>
      </el-tab-pane>
    </el-tabs>


    <!-- 添加或修改集团管理-报表定义对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-form-item label="公司" prop="companyId">
          <el-select v-model="form.companyId" placeholder="请选择公司">
            <el-option
              v-for="item in companyList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="报表代号" prop="reportNo">
          <el-input v-model="form.reportNo" placeholder="请输入报表代号" style="width: 217px"  maxlength="8" show-word-limit/>
        </el-form-item>
        <el-form-item label="报表名称" prop="reportName">
          <el-input v-model="form.reportName" placeholder="请输入报表名称" maxlength="100" show-word-limit/>
        </el-form-item>
        <el-form-item label="表尾" prop="reportTail">
          <el-input v-model="form.reportTail" placeholder="请输入报表表尾" maxlength="200" show-word-limit/>
        </el-form-item>
        <el-form-item label="ureport名称" prop="ureportName">
          <el-input v-model="form.ureportName" placeholder="请输入ureport名称" maxlength="100" show-word-limit/>
        </el-form-item>
        <el-form-item label="报表形式" prop="reportType">
          <el-select v-model="form.reportType" placeholder="请选择">
            <el-option
              v-for="dict in dict.type.aa_report_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>

        </el-form-item>
        <el-form-item label="新增人" prop="createName" align="left">
          {{form.createName}}
        </el-form-item>
        <el-form-item label="新增时间" prop="createTime" align="left">
          {{form.createTime}}
        </el-form-item>
        <el-form-item label="异动人" prop="updateName" align="left">
          {{form.updateName}}
        </el-form-item>
        <el-form-item label="异动时间" prop="updateTime" align="left">
          {{form.updateTime}}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listReportDefin, getReportDefin, delReportDefin, addReportDefin, updateReportDefin } from "@/api/finance/aa/reportDefin";
import { selectCompanyList } from "@/api/finance/aa/companyGroup";
import projectFormula from "@/views/finance/aa/projectFormula";
import projectContent from "@/views/finance/aa/projectContent";
export default {
  name: "ReportDefin",
  dicts: ['aa_report_type'],
  components: {projectFormula,projectContent},
  data() {
    return {
      // tab名称（默认选中第一个标签页first）
      activeName: 'first',
      // 会计公司下拉选单
      companyList: [],
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
      // 集团管理-报表定义表格数据
      reportDefinList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        reportNo: null,
        reportName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companyId: [
          { required: true, message: "公司不能为空", trigger: "blur" }
        ],
        reportNo: [
          { required: true, message: "报表代号不能为空", trigger: "blur" }
        ],
        reportName: [
          { required: true, message: "报表名称不能为空", trigger: "blur" }
        ],
        reportTail: [
          { required: true, message: "表尾不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getCompanyList();
    this.getList();
  },
  methods: {
    /** tab 切换事件 */
    handleClick(tab, event) {
      this.activeName = tab.name
    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
      });
    },
    gotoTab2(row) {
      const obj = {}
      obj.companyId = row.companyId
      obj.reportId = row.reportId
      obj.reportNo = row.reportNo
      obj.reportName = row.reportName
      this.activeName = "second"
      this.$nextTick(() => {
        this.$refs.projectFormula.init(obj)
      })
    },
    gotoTab3(row,reportName) {
      const obj = {}
      obj.companyId = row.companyId
      obj.reportId = row.reportId
      obj.reportNo = row.reportNo
      obj.reportName = reportName
      obj.itemCode = row.itemCode
      obj.itemName = row.itemName
      this.activeName = "third"
      this.$nextTick(() => {
        this.$refs.projectContent.init(obj)
      })
    },
    /** 查询集团管理-报表定义列表 */
    getList() {
      this.loading = true;
      listReportDefin(this.queryParams).then(response => {
        this.reportDefinList = response.rows;
        this.total = response.total;
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
        reportId: null,
        companyId: null,
        reportNo: null,
        reportName: null,
        reportTail: null,
        ureportName: null,
        reportType: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateName: null,
        updateTime: null
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
      this.ids = selection.map(item => item.reportId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加集团管理-报表定义";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const reportId = row.reportId || this.ids
      getReportDefin(reportId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改集团管理-报表定义";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.reportId != null) {
            updateReportDefin(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReportDefin(this.form).then(response => {
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
      const reportIds = row.reportId || this.ids;
      this.$modal.confirm('确认要删除吗？').then(function() {
        return delReportDefin(reportIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/reportDefin/export', {
        ...this.queryParams
      }, `reportDefin_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
