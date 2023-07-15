<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="日期" prop="ratedate">
        <el-date-picker clearable
          v-model="queryParams.ratedate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="汇率类别" prop="ratecate">
        <el-select v-model="queryParams.ratecate" placeholder="请选择">
          <el-option
            v-for="dict in dict.type.aa_rate_cate"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="交易市场" prop="ratespot">
        <el-select v-model="queryParams.ratespot" placeholder="请选择">
          <el-option
            v-for="dict in dict.type.aa_rate_spot"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="货币中文名称" prop="ratecrcy">
        <el-select v-model="queryParams.ratecrcy" placeholder="请选择">
          <el-option
            v-for="dict in dict.type.aa_crcy_abbr"
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
          v-hasPermi="['finance:rate:add']"
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
          v-hasPermi="['finance:rate:edit']"
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
          v-hasPermi="['finance:rate:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="rateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="日期" align="center" prop="ratedate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ratedate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="汇率类别" align="center" prop="ratecate" />
      <el-table-column label="交易市场" align="center" prop="ratespot" />
      <el-table-column label="币别" align="center" prop="ratecrcy" />
      <el-table-column label="汇率" align="center" prop="ratevalue" />
      <el-table-column label="资料来源" align="center" prop="ratesrc" />
      <el-table-column label="异动人" align="center" prop="updateBy" />
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
            v-hasPermi="['finance:rate:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:rate:remove']"
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

    <!-- 添加或修改利率汇率管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="日期" prop="ratedate">
              <el-date-picker clearable
                              v-model="form.ratedate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="汇率类别" prop="ratecate">
              <el-select v-model="form.ratecate" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.aa_rate_cate"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="交易市场" prop="ratespot">
              <el-select v-model="form.ratespot" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.aa_rate_spot"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="币别" prop="ratecrcy">
              <el-select v-model="form.ratecrcy" placeholder="请选择" filterable >
                <el-option
                  v-for="dict in ratecrcyList"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="汇率" prop="ratevalue">
              <el-input v-model="form.ratevalue" placeholder="请输入汇率" />
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
import { listRate, getRate, delRate, addRate, updateRate } from "@/api/finance/aa/rate";
import {selectCrcy} from "@/api/finance/aa/crcy";

export default {
  name: "Rate",
  dicts: ['aa_rate_cate','aa_rate_spot','aa_crcy_abbr'],
  data() {
    return {
      ratecrcyList:[],
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
      // 利率汇率管理表格数据
      rateList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ratedate: null,
        ratecate: null,
        ratespot: null,
        ratecrcy: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        ratedate: [
          {required: true, trigger: 'blur',message: "日期不能为空"},
        ],
        ratecate: [
          {required: true, trigger: 'blur',message: "汇率类别不能为空"},
        ],
        ratespot: [
          {required: true, trigger: 'blur',message: "交易市场不能为空"},
        ],
        ratecrcy: [
          {required: true, trigger: 'blur',message: "币别不能为空"},
        ],
        ratevalue: [
          {required: true, trigger: 'blur',message: "汇率不能为空"},
        ],
      }
    };
  },
  created() {
    this.getCompanyList();
    this.getList();
  },
  methods: {
    /** 查询利率汇率管理列表 */
    getList() {
      this.loading = true;
      listRate(this.queryParams).then(response => {
        this.rateList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getCompanyList() {
      selectCrcy().then(response => {
        this.ratecrcyList = response;
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
        ratedate: null,
        ratecate: null,
        ratespot: null,
        ratecrcy: null,
        ratevalue: null,
        ratesrc: null,
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
      this.title = "添加利率汇率管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getRate(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改利率汇率管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRate(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRate(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除利率汇率管理编号为"' + ids + '"的数据项？').then(function() {
        return delRate(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>
