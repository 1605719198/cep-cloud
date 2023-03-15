<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="职称代号" prop="jobTitleNo">
        <el-input
          maxlength="10"
          v-model="queryParams.jobTitleNo"
          placeholder="请输入职称代号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态码" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态码" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
          @click="handleAdd"
          v-hasPermi="['human:jobTitle:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['human:jobTitle:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jobTitleList" @selection-change="handleSelectionChange" height="67vh">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="职称代号" align="center" prop="jobTitleNo" />
      <el-table-column label="中文职称说明" align="center" prop="jobTitleName" />
      <el-table-column label="英文职称说明" align="center" prop="jobTitleNameEng" />
      <el-table-column label="申请加班" align="center" prop="overTime">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.human_hp_overtime" :value="scope.row.overTime"/>
        </template>
      </el-table-column>
      <el-table-column label="人员类别" align="center" prop="kind">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.human_hp_kind" :value="scope.row.kind"/>
        </template>
      </el-table-column>
      <el-table-column label="人员层级" align="center" prop="titleLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.human_hp_titlelevel" :value="scope.row.titleLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="状态码" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="现有人数" align="center" prop="number" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:jobTitle:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:jobTitle:remove']"
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

    <!-- 添加或修改职位名称数据维护对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="职称代号" prop="jobTitleNo">
              <el-input v-model="form.jobTitleNo" placeholder="请输入职称代号" maxlength="10"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="职称中文说明" prop="jobTitleName">
              <el-input v-model="form.jobTitleName" placeholder="请输入中文职称说明" maxlength="50"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="职称英文说明" prop="jobTitleNameEng">
              <el-input v-model="form.jobTitleNameEng" placeholder="请输入职称英文说明" maxlength="100" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="人员层级" prop="titleLevel">
              <el-select v-model="form.titleLevel" placeholder="请选择人员层级" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.human_hp_titlelevel"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="申请加班" prop="overTime">
              <el-select v-model="form.overTime" placeholder="请选择申请加班" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.human_hp_overtime"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="人员类别" prop="kind">
              <el-select v-model="form.kind" placeholder="请选择人员类别" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.human_hp_kind"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="状态码" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="现有人数" prop="number" >
              <el-input v-model="form.number" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="维护人" prop="creator" >
              <el-input v-model="form.creator" placeholder="请输入维护人" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="维护日期" prop="createDate" >
              <el-date-picker clearable disabled
                              style="width: 100%"
                              v-model="form.createDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择维护日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJobTitle, getJobTitle, delJobTitle, addJobTitle, updateJobTitle } from "@/api/human/hp/jobTitle";
import {deptTreeSelect, getAvatorByUserName, getUser} from "@/api/system/user";
export default {
  name: "JobTitle",
  dicts: ['human_hp_overtime', 'human_hp_kind', 'human_hp_titlelevel', 'sys_normal_disable'],
  data() {
    return {
      //登录人姓名
      nickName: undefined,
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
      // 职位名称数据维护表格数据
      jobTitleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobTitleNo: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        jobTitleNo: [
          { required: true, message: '请输入职称代码', trigger: 'blur' },
        ],
        jobTitleName: [
          { required: true, message: '请输入职称中文说明', trigger: 'blur' },
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getName();
  },
  methods: {
    /** 获取当前日期 */
    getDate(e){
      var today = new Date(); //日期
      var DD = String(today.getDate()).padStart(2, '0'); // 获取日
      var MM = String(today.getMonth() + 1).padStart(2, '0'); //获取月份，1 月为 0
      var yyyy = today.getFullYear(); // 获取年
      var hh = String(today.getHours()).padStart(2, '0'); //获取当前小时数(0-23)
      var mm = String(today.getMinutes()).padStart(2, '0'); //获取当前分钟数(0-59)
      var ss = String(today.getSeconds()).padStart(2, '0'); //获取当前秒数(0-59)
      var time =yyyy + '-' + MM + '-' + DD+' '+hh+':'+mm+':'+ss;
      var date = yyyy + '-' + MM + '-' + DD;
      if(e==0){
        return time;
      }else {
        return date;
      }
    },
    /** 查询职位名称数据维护列表 */
    getList() {
      this.loading = true;
      listJobTitle(this.queryParams).then(response => {
        this.jobTitleList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //获得姓名
    getName(){
      getAvatorByUserName(this.$store.state.user.name).then( response => {
        this.nickName=response.data.nickName
      })
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
        jobTitleNo: null,
        jobTitleName: null,
        jobTitleNameEng: null,
        overTime: null,
        kind: null,
        titleLevel: null,
        status: '0',
        creator: this.nickName,
        createDate: this.getDate(1),
        number:null,
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
      this.title = "添加职位名称数据维护";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getJobTitle(id).then(response => {
        this.form = response.data;
        this.form.creator = this.nickName;
        this.form.createDate = this.getDate(1);
        this.open = true;
        this.title = "修改职位名称数据维护";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateJobTitle(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addJobTitle(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除职位名称数据维护编号为"' + ids + '"的数据项？').then(function() {
        return delJobTitle(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/jobTitle/export', {
        ...this.queryParams
      }, `jobTitle_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
