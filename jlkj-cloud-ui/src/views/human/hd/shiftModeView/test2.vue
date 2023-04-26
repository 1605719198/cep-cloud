<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="会计类别" prop="acctType">
        <el-select v-model="queryParams.acctType" placeholder="请选择会计类别" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="费用类别" prop="apprGroup">
        <el-select v-model="queryParams.apprGroup" placeholder="请选择费用类别" clearable>
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
          v-hasPermi="['human:tripAccountRule:add']"
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
          v-hasPermi="['human:tripAccountRule:edit']"
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
          v-hasPermi="['human:tripAccountRule:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tripAccountRuleList" @selection-change="handleSelectionChange" :span-method="spanTable">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="会计类别" align="center" prop="acctType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.acctType"/>
          {{scope.row.acctType}}-{{scope.row.acctDesc}}
        </template>
      </el-table-column>
      <el-table-column label="类别分类" align="center" prop="acctClassify" />

      <el-table-column label="借方会计科目" align="center" prop="acctCoded" >
        <el-table-column label="贷方会计科目" align="center" prop="acctCodec" >
        </el-table-column>
      </el-table-column>

      <el-table-column label="借方户号" align="center" prop="idCoded">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.idCoded"/>
        </template>
      </el-table-column>
      <el-table-column label="借方参号" align="center" prop="refNod">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.refNod"/>
        </template>
      </el-table-column>
      <el-table-column label="借方到期日" align="center" prop="dueDated" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDated, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="贷方户号" align="center" prop="idCodec">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.idCodec"/>
        </template>
      </el-table-column>
      <el-table-column label="贷方参号" align="center" prop="refNoc">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.refNoc"/>
        </template>
      </el-table-column>
      <el-table-column label="贷方到期日" align="center" prop="dueDatec" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDatec, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="输入人" align="center" prop="creator" />
      <el-table-column label="输入人ID" align="center" prop="creatorId" />
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:tripAccountRule:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:tripAccountRule:remove']"
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

    <!-- 添加或修改出差会计科目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="公司别" prop="compId">
          <el-select v-model="form.compId" placeholder="请选择公司别">
            <el-option
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="会计类别" prop="acctType">
          <el-select v-model="form.acctType" placeholder="请选择会计类别">
            <el-option
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="费用类别" prop="apprGroup">
          <el-select v-model="form.apprGroup" placeholder="请选择费用类别">
            <el-option
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="类别说明" prop="acctDesc">
          <el-input v-model="form.acctDesc" placeholder="请输入类别说明" />
        </el-form-item>
        <el-form-item label="类别分类" prop="acctClassify">
          <el-input v-model="form.acctClassify" placeholder="请输入类别分类" />
        </el-form-item>
        <el-form-item label="借方会计科目" prop="acctCoded">
          <el-input v-model="form.acctCoded" placeholder="请输入借方会计科目" />
        </el-form-item>
        <el-form-item label="借方户号" prop="idCoded">
          <el-select v-model="form.idCoded" placeholder="请选择借方户号">
            <el-option
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="借方参号" prop="refNod">
          <el-select v-model="form.refNod" placeholder="请选择借方参号">
            <el-option
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="借方到期日" prop="dueDated">
          <el-date-picker clearable
                          v-model="form.dueDated"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择借方到期日">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="贷方会计科目" prop="acctCodec">
          <el-input v-model="form.acctCodec" placeholder="请输入贷方会计科目" />
        </el-form-item>
        <el-form-item label="贷方户号" prop="idCodec">
          <el-select v-model="form.idCodec" placeholder="请选择贷方户号">
            <el-option
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="贷方参号" prop="refNoc">
          <el-select v-model="form.refNoc" placeholder="请选择贷方参号">
            <el-option
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="贷方到期日" prop="dueDatec">
          <el-date-picker clearable
                          v-model="form.dueDatec"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择贷方到期日">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="输入人" prop="creator">
          <el-input v-model="form.creator" placeholder="请输入输入人" />
        </el-form-item>
        <el-form-item label="输入人ID" prop="creatorId">
          <el-input v-model="form.creatorId" placeholder="请输入输入人ID" />
        </el-form-item>
        <el-form-item label="输入日期" prop="createDate">
          <el-date-picker clearable
                          v-model="form.createDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择输入日期">
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
import { listTripAccountRule, getTripAccountRule, delTripAccountRule, addTripAccountRule, updateTripAccountRule } from "@/api/human/hd/tripAccountRule";

export default {
  name: "TripAccountRule",
  dicts: ['sys_normal_disable'],
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
      // 出差会计科目表格数据
      tripAccountRuleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        acctType: null,
        apprGroup: null,
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
    //表格合并方法
    spanTable(){

    },
    /** 查询出差会计科目列表 */
    getList() {
      this.loading = true;
      listTripAccountRule(this.queryParams).then(response => {
        this.tripAccountRuleList = response.rows;
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
        compId: null,
        acctType: null,
        apprGroup: null,
        acctDesc: null,
        acctClassify: null,
        acctCoded: null,
        idCoded: null,
        refNod: null,
        dueDated: null,
        acctCodec: null,
        idCodec: null,
        refNoc: null,
        dueDatec: null,
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
      this.title = "添加出差会计科目";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTripAccountRule(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改出差会计科目";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTripAccountRule(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTripAccountRule(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除出差会计科目编号为"' + ids + '"的数据项？').then(function() {
        return delTripAccountRule(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>
