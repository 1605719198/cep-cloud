<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="companyId">
        <el-select v-model="queryParams.companyId" filterable placeholder="请选择公司">
          <el-option
            v-for="item in companyList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="选单名称" prop="dictName">
        <el-input
          v-model="queryParams.dictName"
          placeholder="请输入选单名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="选项名称" prop="dictLabel">
        <el-input
          v-model="queryParams.dictLabel"
          placeholder="请输入选项名称"
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
          v-hasPermi="['finance:dictData:add']"
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
          v-hasPermi="['finance:dictData:edit']"
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
          v-hasPermi="['finance:dictData:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['finance:dictData:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-close"
          size="mini"
          @click="handleClose"
        >关闭</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dictDataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="选项名称" align="center" prop="dictLabel" />
      <el-table-column label="选项值" align="center" prop="dictCode" />
      <el-table-column label="排序" align="center" prop="seq" />
      <el-table-column label="栏位1" align="center" prop="bakOne" />
      <el-table-column label="栏位2" align="center" prop="bakTwo" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:dictData:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:dictData:remove']"
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

    <!-- 添加或修改选单内容设定对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="公司别" prop="companyId">
<!--          <el-input v-model="form.companyId" placeholder="请输入公司别" />-->
          {{form.companyId}}
        </el-form-item>
        <el-form-item label="选单" prop="dictId">
<!--          <el-input v-model="form.dictId" placeholder="请输入选单" />-->
          {{form.dictId}}
        </el-form-item>
        <el-form-item label="选项名称" prop="dictLabel">
          <el-input v-model="form.dictLabel" placeholder="请输入选项名称" />
        </el-form-item>
        <el-form-item label="选项编码" prop="dictCode">
          <el-input v-model="form.dictCode" placeholder="请输入选项编码" />
        </el-form-item>
        <el-form-item label="栏位1" prop="bakOne">
          <el-input v-model="form.bakOne" placeholder="请输入栏位1" />
        </el-form-item>
        <el-form-item label="栏位2" prop="bakTwo">
          <el-input v-model="form.bakTwo" placeholder="请输入栏位2" />
        </el-form-item>
        <el-form-item label="显示排序" prop="seq">
          <el-input-number v-model="form.seq" controls-position="right" :min="1" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="dictRemark">
          <el-input v-model="form.dictRemark" placeholder="请输入备注" />
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
import { listDictData, getDictData, delDictData, addDictData, updateDictData } from "@/api/finance/ft/dictData";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";

export default {
  name: "DictData",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 会计公司下拉选单
      companyList:[],
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
      // 选单内容设定表格数据
      dictDataList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        dictName: null,
        dictLabel: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        dictLabel: [
          { required: true, message: "选项名称不能为空", trigger: "blur" },
          {max: 100 ,trigger: 'blur',message: "最大不超过100个字符"}
        ],
        dictCode: [
          { required: true, message: "选项编码不能为空", trigger: "blur" },
          {max: 50 ,trigger: 'blur',message: "最大不超过50个字符"}
        ],
        dictRemark: [
          {max: 200 ,trigger: 'blur',message: "最大不超过200个字符"}
        ],
      }
    };
  },
  created() {
    this.queryParams.companyId = this.$route.params.companyId;
    this.queryParams.dictName = this.$route.params.dictName;
    console.log(this.queryParams);
    this.getCompanyList();
    this.getList();
  },
  methods: {
    /** 查询选单内容设定列表 */
    getList() {
      this.loading = true;
      listDictData(this.queryParams).then(response => {
        this.dictDataList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
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
        dictId: null,
        companyId: null,
        dictType: null,
        dictName: null,
        dictCode: null,
        dictLabel: null,
        bakOne: null,
        bakTwo: null,
        status: "0",
        seq: null,
        dictRemark: null,
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
    /** 返回按钮操作 */
    handleClose() {
      const obj = { path: "/finance/ft/dictType" };
      this.$tab.closeOpenPage(obj);
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.uuid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加选项内容";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const uuid = row.uuid || this.ids
      getDictData(uuid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改选项内容";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uuid != null) {
            updateDictData(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDictData(this.form).then(response => {
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
      const uuids = row.uuid || this.ids;
      this.$modal.confirm('是否确认删除选单内容设定编号为"' + uuids + '"的数据项？').then(function() {
        return delDictData(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/dictData/export', {
        ...this.queryParams
      }, `dictData_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
