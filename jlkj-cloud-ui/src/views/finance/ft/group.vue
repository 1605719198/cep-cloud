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
      <el-form-item label="资产大类" prop="assetGroupNo">
        <el-select v-model="queryParams.assetGroupNo" filterable placeholder="请选择公司">
          <el-option
            v-for="item in assetGroupList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
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
          v-hasPermi="['finance:group:add']"
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
          v-hasPermi="['finance:group:edit']"
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
          v-hasPermi="['finance:group:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="groupList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="资产大类编码" align="center" prop="assetGroupNo" />
      <el-table-column label="资产大类名称" align="center" prop="assetGroupName" />
      <el-table-column label="数量单位" align="center" prop="unit" />
      <el-table-column label="耐用年限（月）" align="center" prop="usableMonth" />
      <el-table-column label="残值率" align="center" prop="scrapRate" />
      <el-table-column label="折旧方法" align="center" prop="deprMethodName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:group:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:group:remove']"
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

    <!-- 添加或修改资产大类基本资料对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司别" prop="companyId">
              <el-select v-model="form.companyId" filterable placeholder="请选择公司">
                <el-option
                  v-for="item in companyList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产大类" prop="assetGroupNo">
              <el-select v-model="form.assetGroupNo" filterable placeholder="请选择公司">
                <el-option
                  v-for="item in assetGroupList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="数量单位" prop="unit">
              <el-input v-model="form.unit" placeholder="请输入数量单位" class="input"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="耐用年限" prop="usableMonth">
              <el-input v-model="form.usableMonth" placeholder="请输入耐用年限（月数）" class="input"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="残值率" prop="scrapRate">
              <el-input v-model="form.scrapRate" placeholder="请输入残值率" class="input"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="折旧方法" prop="deprMethodNo">
              <el-select v-model="form.deprMethodNo" filterable placeholder="请选择公司">
                <el-option
                  v-for="item in deprMethodList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="备注" prop="remark" class="input1">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="是否计提折旧" prop="deprType">
              <el-select v-model="form.deprType" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.ft_depr_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="固定资产科目" prop="assetAccount">
              <el-input v-model="form.assetAccount" placeholder="请输入固定资产科目" class="input"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="累计折旧科目" prop="deprAccount">
              <el-input v-model="form.deprAccount" placeholder="请输入累计折旧科目" class="input"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产清理科目" prop="clearAccount">
              <el-input v-model="form.clearAccount" placeholder="请输入资产清理科目" class="input"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="折旧费用科目" prop="expenseAccount">
              <el-input v-model="form.expenseAccount" placeholder="请输入折旧费用科目" class="input"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="减值准备科目" prop="reserveAccount">
              <el-input v-model="form.reserveAccount" placeholder="请输入减值准备科目" class="input"/>
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
import { listGroup, getGroup, delGroup, addGroup, updateGroup } from "@/api/finance/ft/group";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {selectAssetGroupList} from "@/api/finance/aa/assetCategory";
import {selectDeprMethodList} from "@/api/finance/aa/deprMethod";

export default {
  name: "Group",
  dicts: ['ft_depr_type'],
  data() {
    return {
      // 折旧方法下拉选单
      deprMethodList:[],
      // 资产大类下拉选单
      assetGroupList:[],
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
      // 资产大类基本资料表格数据
      groupList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        assetGroupNo: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companyId: [
          { required: true, message: "公司别不能为空", trigger: "blur" },
        ],
        assetGroupNo: [
          { required: true, message: "资产大类不能为空", trigger: "blur" },
        ],
        unit: [
          { required: true, message: "数量单位不能为空", trigger: "blur" },
          {max: 50 ,trigger: 'blur',message: "最大不超过50个字符"}
        ],
        usableMonth: [
          { required: true, message: "耐用年限不能为空", trigger: "blur" },
        ],
        remark: [
          {max: 100 ,trigger: 'blur',message: "最大不超过100个字符"}
        ],
      }
    };
  },
  created() {
    this.getDeprMethodList();
    this.getAssetGroupList();
    this.getCompanyList();
    this.getList();
  },
  methods: {
    /** 查询资产大类基本资料列表 */
    getList() {
      this.loading = true;
      listGroup(this.queryParams).then(response => {
        this.groupList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
      });
    },
    getAssetGroupList() {
      selectAssetGroupList().then(response => {
        this.assetGroupList = response;
      });
    },
    getDeprMethodList() {
      selectDeprMethodList().then(response => {
        this.deprMethodList = response;
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
        assetGroupNo: null,
        assetGroupName: null,
        deprMethodNo: null,
        deprMethodName: null,
        unit: null,
        usableMonth: null,
        scrapRate: null,
        remark: null,
        deprType: null,
        assetAccount: null,
        deprAccount: null,
        clearAccount: null,
        expenseAccount: null,
        reserveAccount: null,
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
      this.title = "添加资产大类基本资料";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getGroup(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改资产大类基本资料";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateGroup(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGroup(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除资产大类基本资料编号为"' + ids + '"的数据项？').then(function() {
        return delGroup(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>

<style scoped>
.input {
  width: 83%;
}
.input1 {
  width: 43%;
}
</style>
