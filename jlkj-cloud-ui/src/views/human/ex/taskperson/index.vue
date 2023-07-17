<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="工号">
            <el-input v-model="queryParams.userCode" placeholder="请输入工号" disabled>
              <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['human:taskperson:add']"
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
          v-hasPermi="['human:taskperson:edit']"
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
          v-hasPermi="['human:taskperson:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['human:taskperson:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taskpersonList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="考试代码" align="center" prop="examCode"/>
      <el-table-column label="考试人员" align="center" prop="examPerson"/>
      <el-table-column label="试题编号" align="center" prop="taskCode"/>
      <el-table-column label="开始时间" align="center" prop="startTime"/>
      <el-table-column label="结束时间" align="center" prop="endTime"/>
      <el-table-column label="状态" align="center" prop="status"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:taskperson:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:taskperson:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize" @pagination="getList"/>

    <!-- 添加或修改考试人员对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="750px" append-to-body>
      <el-form ref="dataForm" :key="key" :model="dataForm" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="考试代码" prop="examCode">
              <el-input v-model="dataForm.examCode" placeholder="请输入考试代码"/>
            </el-form-item>
            <el-form-item label="开始时间" prop="startTime">
              <el-date-picker v-model="dataForm.startTime" clearable type="datetime"
                              placeholder="开始时间"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="试题编号" prop="taskCode">
              <el-input v-model="dataForm.taskCode" placeholder="请输入试题编号"/>
            </el-form-item>
            <el-form-item label="结束时间" prop="endTime">
              <el-date-picker v-model="dataForm.endTime" clearable type="datetime"
                              placeholder="结束时间"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="人员工号" prop="userCode">
              <el-input v-model="dataForm.userCode" placeholder="请输入工号" :disabled="true">
                <el-button slot="append" icon="el-icon-search" @click="inputClick2"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="dataForm.status" size="mini">
                <el-radio-button label="0">待开考</el-radio-button>
                <el-radio-button label="1">考试中</el-radio-button>
                <el-radio-button label="2">已交卷</el-radio-button>
              </el-radio-group>
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
    <select-user ref="select2" @getMore="getMore"/>
  </div>
</template>

<script>
import {
  addTaskperson,
  delTaskperson,
  exportTaskperson,
  getTaskperson,
  listTaskperson,
  updateTaskperson
} from "@/api/human/ex/taskperson";
import selectUser from "@/views/components/human/selectUser/selectUser";
import '@/assets/styles/humanStyles.scss';

export default {
  name: "Taskperson",
  components: {selectUser},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      key: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 考试人员表格数据
      taskpersonList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 考试代码字典
      examCodeOptions: [],
      // 考试人员字典
      examPersonOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },

      // 表单参数
      dataForm: {
        userCode: undefined,
        status: "待开考"
      },
      updatePop: true,
      // 表单校验
      rules: {
        examCode: [
          {required: true, message: "考试代码不能为空", trigger: "blur"}
        ],
        userCode: [
          {required: true, message: "人员工号不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询考试人员列表 */
    getList() {
      this.loading = true;
      listTaskperson(this.queryParams).then(response => {
        this.taskpersonList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 工号点击事件（查询条件） */
    inputClick() {
      this.$refs.select.show();
    },
    /** 工号点击事件（查询条件） */
    inputClick2() {
      const queryParam = {
        isMore: true,
        compID: '',
        keywords: '',
        pageNum: 1,
        pageSize: 10
      }
      this.$refs.select2.show(queryParam);
    },
    /** 获取工号 （查询条件）*/
    getJobNumber(val, userName) {
      this.queryParams.userCode = val;
    },
    /** 获取工号 callback 函数 （新增、修改画面） */
    getMore(val) {
      let users = '';
      if (val != null) {
        val.forEach((item, index, val) => {
          users += item.empNo + ',';
        })
      }
      this.dataForm.userCode = users.substring(0, users.lastIndexOf(','));
      console.log(this.dataForm)
      this.key = Math.random();
    },
    // 考试代码字典翻译
    examCodeFormat(row, column) {
      return this.selectDictLabel(this.examCodeOptions, row.examCode);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.dataForm = {
        examCode: null,
        examPerson: null
      };
      this.resetForm("dataForm");
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
      this.ids = selection.map(item => item.examCode)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加考试人员";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const examCode = row.examCode || this.ids
      getTaskperson(examCode).then(response => {
        this.dataForm = response.data;
        this.open = true;
        this.title = "修改考试人员";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          if (this.dataForm.examCode != null) {
            updateTaskperson(this.dataForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTaskperson(this.dataForm).then(response => {
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
      const examCodes = row.examCode || this.ids;
      this.$confirm('是否确认删除考试人员编号为"' + examCodes + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delTaskperson(examCodes);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有考试人员数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.exportLoading = true;
        return exportTaskperson(queryParams);
      }).then(response => {
        this.download(response.msg);
        this.exportLoading = false;
      }).catch(() => {
      });
    }
  }
};
</script>
