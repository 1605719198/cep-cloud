<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-input
          v-model="queryParams.compId"
          placeholder="请输入公司别"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工号ID" prop="empId">
        <el-input
          v-model="queryParams.empId"
          placeholder="请输入工号ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实际请假开始日期" prop="startDate">
        <el-date-picker clearable
          v-model="queryParams.startDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择实际请假开始日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="实际请假结束日期" prop="endDate">
        <el-date-picker clearable
          v-model="queryParams.endDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择实际请假结束日期">
        </el-date-picker>
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
          v-hasPermi="['human:cancelHoliday:add']"
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
          v-hasPermi="['human:cancelHoliday:edit']"
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
          v-hasPermi="['human:cancelHoliday:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['human:cancelHoliday:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="cancelHolidayList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="工号" align="center" prop="empNo" />
      <el-table-column label="实际请假开始日期" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际请假结束日期" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际请假天数" align="center" prop="leaveDays" />
      <el-table-column label="请假类别" align="center" prop="leaTypeId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:cancelHoliday:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:cancelHoliday:remove']"
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

    <!-- 添加或修改员工销假对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="请假记录id" prop="personHolidayId">
          <el-input v-model="form.personHolidayId" placeholder="请输入请假记录id" />
        </el-form-item>
        <el-form-item label="公司别" prop="compId">
          <el-input v-model="form.compId" placeholder="请输入公司别" />
        </el-form-item>
        <el-form-item label="工号" prop="empNo">
          <el-input v-model="form.empNo" placeholder="请输入工号" />
        </el-form-item>
        <el-form-item label="工号ID" prop="empId">
          <el-input v-model="form.empId" placeholder="请输入工号ID" />
        </el-form-item>
        <el-form-item label="姓名" prop="empName">
          <el-input v-model="form.empName" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="岗位名称" prop="postName">
          <el-input v-model="form.postName" placeholder="请输入岗位名称" />
        </el-form-item>
        <el-form-item label="岗位ID" prop="postId">
          <el-input v-model="form.postId" placeholder="请输入岗位ID" />
        </el-form-item>
        <el-form-item label="一级组织机构ID" prop="orgParentId">
          <el-input v-model="form.orgParentId" placeholder="请输入一级组织机构ID" />
        </el-form-item>
        <el-form-item label="二级组织机构id" prop="orgId">
          <el-input v-model="form.orgId" placeholder="请输入二级组织机构id" />
        </el-form-item>
        <el-form-item label="实际请假开始日期" prop="startDate">
          <el-date-picker clearable
            v-model="form.startDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择实际请假开始日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实际请假结束日期" prop="endDate">
          <el-date-picker clearable
            v-model="form.endDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择实际请假结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实际请假天数" prop="leaveDays">
          <el-input v-model="form.leaveDays" placeholder="请输入实际请假天数" />
        </el-form-item>
        <el-form-item label="原始请假开始时间" prop="oriStartDate">
          <el-date-picker clearable
            v-model="form.oriStartDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择原始请假开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="原始请假结束时间" prop="oriEndDate">
          <el-date-picker clearable
            v-model="form.oriEndDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择原始请假结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="原始请假天数" prop="oriLeaveDays">
          <el-input v-model="form.oriLeaveDays" placeholder="请输入原始请假天数" />
        </el-form-item>
        <el-form-item label="请假类别" prop="leaTypeId">
          <el-input v-model="form.leaTypeId" placeholder="请输入请假类别" />
        </el-form-item>
        <el-form-item label="本次请假天数" prop="leaveShifts">
          <el-input v-model="form.leaveShifts" placeholder="请输入本次请假天数" />
        </el-form-item>
        <el-form-item label="本次请假时数" prop="leaveHours">
          <el-input v-model="form.leaveHours" placeholder="请输入本次请假时数" />
        </el-form-item>
        <el-form-item label="本次请假时数" prop="morLeaveHours">
          <el-input v-model="form.morLeaveHours" placeholder="请输入本次请假时数" />
        </el-form-item>
        <el-form-item label="本次请假天数" prop="morLeaveShifts">
          <el-input v-model="form.morLeaveShifts" placeholder="请输入本次请假天数" />
        </el-form-item>
        <el-form-item label="是否全销" prop="isAll">
          <el-input v-model="form.isAll" placeholder="请输入是否全销" />
        </el-form-item>
        <el-form-item label="辅助说明" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="是否包括节假日" prop="isContainHoliday">
          <el-radio-group v-model="form.isContainHoliday">
            <el-radio
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="剩余可请假天数" prop="remainingDays">
          <el-input v-model="form.remainingDays" placeholder="请输入剩余可请假天数" />
        </el-form-item>
        <el-form-item label="本月累计天数" prop="monthDays">
          <el-input v-model="form.monthDays" placeholder="请输入本月累计天数" />
        </el-form-item>
        <el-form-item label="本月累计小时数" prop="monthHours">
          <el-input v-model="form.monthHours" placeholder="请输入本月累计小时数" />
        </el-form-item>
        <el-form-item label="本年累计天数" prop="yearDays">
          <el-input v-model="form.yearDays" placeholder="请输入本年累计天数" />
        </el-form-item>
        <el-form-item label="本年累计小时数" prop="yearHours">
          <el-input v-model="form.yearHours" placeholder="请输入本年累计小时数" />
        </el-form-item>
        <el-form-item label="输入人" prop="creator">
          <el-input v-model="form.creator" placeholder="请输入输入人" />
        </el-form-item>
        <el-form-item label="输入人ID" prop="creatorId">
          <el-input v-model="form.creatorId" placeholder="请输入输入人ID" />
        </el-form-item>
        <el-form-item label="输入人日期" prop="createDate">
          <el-date-picker clearable
            v-model="form.createDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择输入人日期">
          </el-date-picker>
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
import { listCancelHoliday, getCancelHoliday, delCancelHoliday, addCancelHoliday, updateCancelHoliday } from "@/api/human/hd/cancelHoliday";

export default {
  name: "CancelHoliday",
  dicts: ['sys_yes_no'],
  data() {
    return {
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
      // 员工销假表格数据
      cancelHolidayList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        empId: null,
        startDate: null,
        endDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询员工销假列表 */
    getList() {
      this.loading = true;
      listCancelHoliday(this.queryParams).then(response => {
        this.cancelHolidayList = response.rows;
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
        id: null,
        personHolidayId: null,
        compId: null,
        empNo: null,
        empId: null,
        empName: null,
        postName: null,
        postId: null,
        orgParentId: null,
        orgId: null,
        startDate: null,
        endDate: null,
        leaveDays: null,
        oriStartDate: null,
        oriEndDate: null,
        oriLeaveDays: null,
        leaTypeId: null,
        leaveShifts: null,
        leaveHours: null,
        morLeaveHours: null,
        morLeaveShifts: null,
        status: null,
        isAll: null,
        description: null,
        isContainHoliday: null,
        remainingDays: null,
        monthDays: null,
        monthHours: null,
        yearDays: null,
        yearHours: null,
        creator: null,
        creatorId: null,
        createDate: null
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
      this.title = "添加员工销假";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCancelHoliday(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工销假";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCancelHoliday(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCancelHoliday(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除员工销假编号为"' + ids + '"的数据项？').then(function() {
        return delCancelHoliday(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/cancelHoliday/export', {
        ...this.queryParams
      }, `cancelHoliday_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
