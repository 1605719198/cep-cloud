<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
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
      <el-form-item label="使用状态代码" prop="statusCode">
        <el-input
          v-model="queryParams.statusCode"
          placeholder="请输入使用状态代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="使用状态名称" prop="statusName">
        <el-input
          v-model="queryParams.statusName"
          placeholder="请输入使用状态名称"
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
          v-hasPermi="['finance:status:add']"
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
          v-hasPermi="['finance:status:edit']"
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
          v-hasPermi="['finance:status:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="statusList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="使用状态代码" align="center" prop="statusCode" />
      <el-table-column label="使用状态名称" align="center" prop="statusName" />
      <el-table-column label="备注" align="center" prop="statusRemark" />
      <el-table-column label="新增人" align="center" prop="createBy" />
      <el-table-column label="新增日期" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:status:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:status:remove']"
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

    <!-- 添加或修改资产使用状态对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司别" prop="companyId">
              <el-select v-model="form.companyId" filterable placeholder="请选择公司" class="input">
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
            <el-form-item label="使用状态代码" prop="statusCode">
              <el-input v-model="form.statusCode" placeholder="请输入使用状态代码" class="input"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="使用状态名称" prop="statusName">
              <el-input v-model="form.statusName" placeholder="请输入使用状态名称" class="input"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="statusRemark">
              <el-input v-model="form.statusRemark" type="textarea" maxlength="200" show-word-limit class="input"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="addLine"
              v-hasPermi="['finance:status:add']"
            >增行</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="delTableItem"
              v-hasPermi="['finance:status:remove']"
            >删除</el-button>
          </el-col>
        </el-row>
        <el-table v-loading="loadingA" :data="statusListA" :key="key" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="80" align="center" />
          <el-table-column label="资产用途" align="center" prop="assetPurpose">
            <template v-slot="scope">
              <el-select v-model="scope.row.assetPurpose">

              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="财务状况用途代码" align="center" prop="financePurpose" >
            <template v-slot="scope">
              <el-select v-model="scope.row.financePurpose">

              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="是否计提折旧" align="center" prop="isDepr" >
            <template v-slot="scope">
              <el-select v-model="scope.row.isDepr">

              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="费用性质" align="center" prop="expenseType" >
            <template v-slot="scope">
              <el-select v-model="scope.row.expenseType">

              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="备注" align="center" prop="detailRemark" >
            <template v-slot="scope">
              <el-select v-model="scope.row.detailRemark">

              </el-select>
            </template>
          </el-table-column>
        </el-table>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStatus, getStatus, delStatus, addStatus, updateStatus } from "@/api/finance/ft/status";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";

export default {
  name: "Status",
  data() {
    return {
      // 会计公司下拉选单
      companyList:[],
      // 遮罩层
      loading: true,
      loadingA: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      addMultiple: [],
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      statusListA:[
        {
          uuid: undefined,
          financePurpose: undefined,
        }
      ],
      // 资产使用状态表格数据
      statusList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        statusCode: null,
        statusName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companyId: [
          { required: true, message: "公司不能为空", trigger: "blur" },
        ],
        statusCode: [
          { required: true, message: "使用状态代码不能为空", trigger: "blur" },
          {max: 10 ,trigger: 'blur',message: "最大不超过10个字符"}
        ],
        statusName: [
          { required: true, message: "使用状态名称不能为空", trigger: "blur" },
          {max: 60 ,trigger: 'blur',message: "最大不超过60个字符"}
        ],
      },
      index: 0,
      key: undefined
    };
  },
  created() {
    this.getCompanyList();
    this.getList();
  },
  methods: {
    /** 查询资产使用状态列表 */
    getList() {
      this.loading = true;
      listStatus(this.queryParams).then(response => {
        this.statusList = response.rows;
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
        companyId: null,
        statusCode: null,
        statusName: null,
        statusRemark: null,
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
      this.ids = selection.map(item => item.uuid)
      this.single = selection.length!==1
      this.multiple = !selection.length
      this.addMultiple = selection
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine() {
      this.index++
      const newLine = {
        financePurpose: "",
        uuid: this.index
      }
      this.statusListA.push(newLine)
    },
    delTableItem() {
      // 确认删除
      if (this.addMultiple.length > 0) {
        let arrs = [];
        let ids = this.addMultiple.map(val => val.uuid); //拿到选中的数据id,
        this.statusListA.forEach(item => {
          if (!ids.includes(item.uuid)) {
            // 当uuid在employeeTurnoverList中，表示数据被选中，该将其删除，即将不被选中的保留
            arrs.push(item);
          }
        });
        this.statusListA = arrs;
        this.key = Math.random()
      } else {
        this.$message.warning("请选择要删除的数据");
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加资产使用状态";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const uuid = row.uuid || this.ids
      getStatus(uuid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改资产使用状态";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uuid != null) {
            updateStatus(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.statusListA = this.statusListA
            addStatus(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除资产使用状态编号为"' + uuids + '"的数据项？').then(function() {
        return delStatus(uuids);
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
  width: 95%;
}
</style>
