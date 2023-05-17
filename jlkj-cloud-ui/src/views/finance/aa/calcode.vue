<template>
  <div>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司" prop="companyId">
        <el-select v-model="queryParams.companyId" filterable placeholder="请选择公司">
          <el-option
            v-for="item in companyList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="核算项目类别代码" prop="calTypeCode" label-width="130px">
        <el-input
          v-model="queryParams.calTypeCode"
          placeholder="请输入核算项目类别代码"
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
          v-hasPermi="['finance:calType:add']"
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
          v-hasPermi="['finance:calType:edit']"
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
          v-hasPermi="['finance:calType:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['finance:calType:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="calcodeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="核算项目" align="center" prop="calCode" />
      <el-table-column label="核算项目名称" align="center" prop="calName" />
      <el-table-column label="状态" align="center" key="status" >
        <template v-slot="scope">
          <el-switch
            v-model="scope.row.status"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>


      <el-table-column label="异动人" align="center" prop="updateName" />
      <el-table-column label="异动时间" align="center" prop="updateTime" width="180">
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
            v-hasPermi="['finance:calType:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:calType:remove']"
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

    <!-- 添加或修改核算项目-内容维护对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="490px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px" align="center">
        <el-form-item label="公司" prop="companyId" >
          <el-select v-model="form.companyId" disabled placeholder="请选择公司" style="width:300px">
            <el-option
              v-for="item in companyList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="核算项目类别" prop="calTypeCode">
          <el-input v-model="form.calTypeCode" placeholder="请输入核算项目类别" disabled style="width: 300px"/>
        </el-form-item>
        <el-form-item label="核算项目代码" prop="calCode">
          <el-input v-model="form.calCode" placeholder="请输入核算项目代码" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="核算项目名称" prop="calName">
          <el-input v-model="form.calName" placeholder="请输入核算项目名称" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="状态" align="left">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
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
import { listCalcode, getCalcode, delCalcode, addCalcode, updateCalcode,changeUserStatus }
  from "@/api/finance/aa/calcode";
import { selectCompanyList } from "@/api/finance/aa/companyGroup";
export default {
  name: "Calcode",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 会计公司下拉选单
      companyList: [],
      // 父组件传值
      parCompanyId:null,
      parCalTypeId:null,
      parCalTypeCode: null,
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
      // 核算项目-内容维护表格数据
      calcodeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        calTypeCode: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companyId: [
          { required: true, message: "公司不能为空", trigger: "blur" }
        ],
        calTypeCode: [
          { required: true, message: "核算项目类别不能为空", trigger: "blur" }
        ],
        calCode: [
          { required: true, message: "核算项目代码不能为空", trigger: "blur" },
          { max: 36,message: '长度不可超过36个字符', trigger: 'blur'}
        ],
        calName: [
          { required: true, message: "核算项目名称不能为空", trigger: "blur" },
          { max: 60,message: '长度不可超过60个字符', trigger: 'blur'}
        ],
      }
    };
  },
  created() {
    this.getCompanyList();
    this.getList();
  },
  methods: {
    /** 初始化方法 */
    init(obj) {
      // 根据传递过来的值进行查询
      this.queryParams.companyId = obj.companyId
      this.queryParams.calTypeCode = obj.calTypeCode
      this.parCompanyId = obj.companyId
      this.parCalTypeId = obj.calTypeId
      this.parCalTypeCode = obj.calTypeCode
      this.getList()
    },

    /** 查询核算项目-内容维护列表 */
    getList() {
      this.loading = true;
      listCalcode(this.queryParams).then(response => {
        this.calcodeList = response.rows;
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
        id: null,
        companyId: null,
        calTypeId: null,
        calTypeCode: null,
        calCode: null,
        calName: null,
        status: "0",
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加核算项目-内容维护";
      this.form.companyId = this.parCompanyId
      this.form.calTypeId = this.parCalTypeId
      this.form.calTypeCode = this.parCalTypeCode;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCalcode(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改核算项目-内容维护";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCalcode(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCalcode(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除核算项目-内容维护编号为"' + ids + '"的数据项？').then(function() {
        return delCalcode(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/calcode/export', {
        ...this.queryParams
      }, `calcode_${new Date().getTime()}.xlsx`)
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要' + text + '吗？').then(function () {
        return changeUserStatus(row.id, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function () {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
  }
};
</script>
