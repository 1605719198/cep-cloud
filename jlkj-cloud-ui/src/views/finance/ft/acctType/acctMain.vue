<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
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
      <el-form-item label="账务分类代码" prop="acctTypeA">
        <el-input
          v-model="queryParams.acctTypeA"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账务代码" prop="acctDetail">
        <el-input
          v-model="queryParams.acctDetail"
          placeholder="请输入账务代码"
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
          v-hasPermi="['finance:acctMain:add']"
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
          v-hasPermi="['finance:acctMain:edit']"
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
          v-hasPermi="['finance:acctMain:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="acctMainList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="账务代码" align="center" prop="acctDetail" />
      <el-table-column label="账务代码名称" align="center" prop="logicName" />
      <el-table-column label="是否抛AA" align="center" prop="isAa" />
      <el-table-column label="备注说明" align="center" prop="logicRemark" />
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
            v-hasPermi="['finance:acctMain:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:acctMain:remove']"
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

    <!-- 添加或修改账务代码对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司" prop="companyId">
              <dict-tag-a :options="companyList" :value="form.companyId"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账务分类" prop="acctType">
              {{this.acctType}}_{{this.acctName}}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="账务代码" prop="acctDetail">
              {{this.acctType}}_
              <el-input v-model="form.acctDetail" placeholder="请输入账务代码" class="input1"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账务代码名称" prop="logicName">
              <el-input v-model="form.logicName" placeholder="请输入账务代码名称" class="input"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="是否抛AA" prop="isAa">
              <el-radio v-model="form.isAa" label="是">是</el-radio>
              <el-radio v-model="form.isAa" label="否">否</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="logicRemark">
              <el-input v-model="form.logicRemark" type="textarea" maxlength="200" show-word-limit class="input"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="center" direction="horizontal">财务代码明细资料</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="addLine"
              v-hasPermi="['finance:acctMain:add']"
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
              v-hasPermi="['finance:acctMain:remove']"
            >删除</el-button>
          </el-col>
        </el-row>
        <el-table v-loading="loadingA" :data="acctMainListA" :key="key" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="80" align="center" />
          <el-table-column label="借/贷" align="center" prop="drCr">
            <template v-slot="scope">
              <el-select v-model="scope.row.drCr">
<!--                <el-option-->
<!--                  v-for="dict in baseInfoData.ifInCompany"-->
<!--                  :key="dict.dicNo"-->
<!--                  :label="dict.dicName"-->
<!--                  :value="dict.dicNo"-->
<!--                ></el-option>-->
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="会计科目代码" align="center" prop="acctCode" >
            <template v-slot="scope">
              <el-select v-model="scope.row.acctCode">

              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="核算项目一" align="center" prop="calTypeCodea" >
            <template v-slot="scope">
              <el-select v-model="scope.row.calTypeCodea">

              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="核算项目二" align="center" prop="calTypeCodeb" >
            <template v-slot="scope">
              <el-select v-model="scope.row.calTypeCodeb">

              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="金额栏位" align="center" prop="amtType" >
            <template v-slot="scope">
              <el-select v-model="scope.row.amtType">

              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="到期日" align="center" prop="dueDate" >
            <template v-slot="scope">
              <el-select v-model="scope.row.dueDate">

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
import { listAcctMain, getAcctMain, delAcctMain, addAcctMain, updateAcctMain } from "@/api/finance/ft/acctMain";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import dictTagA from "@/views/components/finance/dictTagA";

export default {
  name: "AcctMain",
  components: {dictTagA},
  data() {
    return {
      // 会计公司下拉选单
      companyList:[],
      // 遮罩层
      loading: true,
      // 遮罩层
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
      // 账务代码表格数据
      acctMainListA: [
        {
          uuid: undefined,
          acctCode: undefined
        }
      ],
      // 账务代码表格数据
      acctMainList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        acctType: null,
        acctTypeA: null,
        acctDetail: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        acctDetail: [
          { required: true, message: "账务代码不能为空", trigger: "blur" },
        ],
        logicName: [
          { required: true, message: "账务代码名称不能为空", trigger: "blur" },
          {max: 60 ,trigger: 'blur',message: "最大不超过60个字符"}
        ],
        isAa: [
          { required: true, message: "请选择是否抛AA", trigger: "blur" },
        ],
      },
      index: 0,
      key: undefined
    };
  },
  props: ['applyId','acctType','acctName','companyId'],
  created() {
    this.getCompanyList();
    this.getList();
  },
  methods: {
    /** 查询账务代码列表 */
    getList() {
      this.loading = true;
      listAcctMain(this.queryParams).then(response => {
        this.acctMainList = response.rows;
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
        acctType: null,
        acctLogic: null,
        acctDetail: null,
        logicName: null,
        isAa: null,
        logicRemark: null,
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
        acctCode: "",
        uuid: this.index
      }
      this.acctMainListA.push(newLine)
    },
    delTableItem() {
      // 确认删除
      if (this.addMultiple.length > 0) {
        let arrs = [];
        let ids = this.addMultiple.map(val => val.uuid); //拿到选中的数据id,
        this.acctMainListA.forEach(item => {
          if (!ids.includes(item.uuid)) {
            // 当uuid在employeeTurnoverList中，表示数据被选中，该将其删除，即将不被选中的保留
            arrs.push(item);
          }
        });
        this.acctMainListA = arrs;
        this.key = Math.random()
      } else {
        this.$message.warning("请选择要删除的数据");
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form.companyId = this.queryParams.companyId
      this.open = true;
      this.title = "添加账务代码";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const uuid = row.uuid || this.ids
      getAcctMain(uuid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改账务代码";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uuid != null) {
            updateAcctMain(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.acctType = this.acctType
            this.form.acctLogic = this.acctType  + '_' + this.form.acctDetail
            this.form.acctMainListA = this.acctMainListA
            addAcctMain(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除账务代码编号为"' + uuids + '"的数据项？').then(function() {
        return delAcctMain(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    getVal(applyId,acctType,acctName,companyId) {
      this.queryParams.acctType = acctType
      this.queryParams.acctTypeA = acctType + '_' + acctName
      this.queryParams.companyId = companyId
      this.getList();
    }
  }
};
</script>

<style scoped>
.input {
  width: 97%;
}
.input1 {
  width: 90%;
}
</style>
