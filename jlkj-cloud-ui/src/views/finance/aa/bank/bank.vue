<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="总行" prop="bankNo">
        <el-select v-model="queryParams.branchNo" placeholder="请选择">
          <el-option
            v-for="dict in dict.type.aa_bank_bankno"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value">
          </el-option>
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
          v-hasPermi="['finance:bank:add']"
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
          v-hasPermi="['finance:bank:edit']"
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
          v-hasPermi="['finance:bank:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bankList"
              @selection-change="handleSelectionChange"
              @row-dblclick="rowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="总行代码" align="center" prop="bankNo" />
      <el-table-column label="总行中文简称" align="center" prop="bankChnabbr" />
      <el-table-column label="总行中文全名" align="center" prop="bankchnName" />
      <el-table-column label="总行统一编号" align="center" prop="bankuniformNo" />
      <el-table-column label="代码来源" align="center" prop="banknoSrc" />
      <el-table-column label="总行国别" align="center" prop="countryId" />
      <el-table-column label="银行型态" align="center" prop="bankType" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:bank:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:bank:remove']"
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

    <!-- 添加或修改金融机构管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="总行代码" prop="bankNo">
              <el-input v-model="form.bankNo" placeholder="请输入总行代码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="总行简称" prop="bankChnabbr">
              <el-input v-model="form.bankChnabbr" placeholder="请输入总行简称" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="总行中文全名" prop="bankchnName">
          <el-input v-model="form.bankchnName" placeholder="请输入总行中文全名" />
        </el-form-item>
        <el-form-item label="总行英文简称" prop="bankEngabbr">
          <el-input v-model="form.bankEngabbr" placeholder="请输入总行英文简称" />
        </el-form-item>
        <el-form-item label="总行英文全名" prop="bankengName">
          <el-input v-model="form.bankengName" placeholder="请输入总行英文全名" />
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="总行统一编号" prop="bankuniformNo">
              <el-input v-model="form.bankuniformNo" placeholder="请输入总行统一编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="代码来源" prop="banknoSrc">
              <el-select v-model="form.banknoSrc" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.aa_bank_sour"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="总行国别" prop="countryId">
              <el-select v-model="form.countryId" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.aa_bank_countries"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="银行型态" prop="bankType">
              <el-select v-model="form.bankType" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.aa_bank_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="新增人" prop="createName">
              {{form.createName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="新增日期" prop="createTime">
              {{ parseTime(form.createTime, '{y}-{m}-{d}') }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="异动人" prop="updateName">
              {{form.updateName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="异动日期" prop="updateTime">
              {{ parseTime(form.updateTime, '{y}-{m}-{d}') }}
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
import { listBank, getBank, delBank, addBank, updateBank } from "@/api/finance/aa/bank";

export default {
  name: "Bank",
  dicts: ['aa_bank_bankno','aa_bank_sour','aa_bank_countries','aa_bank_type'],
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
      // 金融机构管理表格数据
      bankList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bankNo: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bankNo: [
          { required: true, message: "总行代码不能为空", trigger: "blur" }
        ],
        countryId: [
          { required: true, message: "总行国别不能为空", trigger: "change" }
        ],
        bankType: [
          { required: true, message: "银行型态不能为空", trigger: "change" }
        ],
        banknoSrc: [
          { required: true, message: "代码来源不能为空", trigger: "change" }
        ],
        bankuniformNo: [
          { required: true, message: "总行统一编号不能为空", trigger: "blur" }
        ],
        bankchnName: [
          { required: true, message: "总行中文全名不能为空", trigger: "blur" }
        ],
        bankChnabbr: [
          { required: true, message: "总行中文简称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询金融机构管理列表 */
    getList() {
      this.loading = true;
      listBank(this.queryParams).then(response => {
        this.bankList = response.rows;
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
        bankNo: null,
        countryId: null,
        bankType: null,
        banknoSrc: null,
        bankuniformNo: null,
        bankchnName: null,
        bankengName: null,
        bankChnabbr: null,
        bankEngabbr: null,
        status: null,
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
    rowClick(row, column, e) {

      this.$emit('change', 'second', row.id,row.bankNo,row.bankchnName);
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
      this.title = "添加金融机构管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBank(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改金融机构管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBank(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBank(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除金融机构管理编号为"' + ids + '"的数据项？').then(function() {
        return delBank(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>
