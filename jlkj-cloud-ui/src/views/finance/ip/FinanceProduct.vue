<template>
  <div class="app-container">
    <el-form :model="queryParams"
             ref="queryForm"
             size="small"
             :inline="true"
             v-show="showSearch" label-width="85px">

      <el-form-item label="产副品代码" prop="productCode">
        <el-input
          v-model="queryParams.productCode"
          placeholder="请输入产副品代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="中文名称" prop="productCodeDesc">
        <el-input
          v-model="queryParams.productCodeDesc"
          placeholder="请输入产副品中文名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button v-hasPermi="['product_code_queryAll']"
                   type="primary"
                   icon="el-icon-search"
                   size="mini"
                   @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh"
                   size="mini"
                   @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-hasPermi="['product_code_doAdd']"
                   type="primary"
                   plain
                   size="mini"
                   icon="el-icon-plus"
                   @click="AddleUpdate()">新增</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading"
              :data="financetestList"
              stripe>
      <el-table-column label="产副品编码"
                       sortable
                       align="center"
                       prop="productCode" />
      <el-table-column label="产副品名称"
                       sortable
                       align="center"
                       prop="productCodeDesc" />
      <el-table-column label="产副品帐务分类"
                       sortable
                       align="center"
                       prop="productAccountAttri" />
      <el-table-column label="操作"
                       align="center"
                       class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-hasPermi="['product_code_queryOne']"
                     plain
                     icon="el-icon-info"
                     type="info"
                     size="mini"
                     @click="handleDetails(scope.row)">详情</el-button>
          <el-button v-hasPermi="['product_code_doEdit']"
                     size="mini"
                     plain
                     icon="el-icon-edit"
                     type="primary"
                     @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button v-hasPermi="['product_code_delete']"
                     size="mini"
                     plain
                     icon="el-icon-delete"
                     type="danger"
                     @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination background
                :total="total"
                :current-page="queryParams.pageNum"
                :page-sizes="[20, 50, 100, 200]"
                :page-size="queryParams.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                style="float: right;">
    </pagination>
    <!-- 添加或修改参数配置对话框 -->
    <FinanceProductCost @getLists="getListHandle"
                        :visible.sync="CostCenterCostVisible"
                        v-if="CostCenterCostVisible"
                        ref="CostCenterCostVisible"></FinanceProductCost>
  </div>
</template>


<script>
import { listFinancetest, delFinancetest, addFinancetest } from "@/api/finance/ip/financeproduct";

import FinanceProductCost from './FinanceProductCost'

export default {
  name: "FinanceProduct",
  components: {
    FinanceProductCost
  },
  dicts: ['cost_producttype_name'],
  financeattritestList: [],
  data () {
    return {
      //添加按键熟悉
      AddVisible: false,
      //编辑按键熟悉
      UpdateVisible: false,
      visible: false,
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      //添加-编辑
      CostCenterCostVisible: false,
      // 总条数
      total: 0,
      // 测试表格数据
      financetestList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productCode: '',
        productCodeDesc: '',
        ccType: null,
        compId: 'J00'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created () {
    this.getList();
  },
  methods: {
    // 分页数据
    handleSizeChange (val) {
      this.queryParams.pageSize = val
      this.getList()
    },
    handleCurrentChange (val) {
      this.queryParams.pageNum = val
      this.getList()
    },
    /** 查询测试列表 */
    getList () {
      this.loading = false;
      listFinancetest(this.queryParams).then(response => {
        if (response.data.data == null) {
          this.financetestList = []
          this.total = 0;
          this.costAccount = true
        } else {
          this.financetestList = response.data.data.list;
          this.total = response.data.data.total;
          this.loading = false;
        }
      });

    },


    // 表单重置
    reset () {
      this.form = {};
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        productCode: '',
        productCodeDesc: '',
        ccType: null,
        compId: 'J00'
      },
        listFinancetest(this.queryParams).then(response => {
          this.financetestList = response.data.data.list;
          this.total = response.data.data.total;
          this.loading = false;
        });

    },

    /** 新增按钮操作 */
    handleAdd () {
      this.reset();
      this.open = true;
      this.title = "添加测试";
    },
    /** 添加按钮操作 */
    AddleUpdate () {
      this.CostCenterCostVisible = true
      this.$nextTick(() => {
        this.$refs.CostCenterCostVisible.AddPage(this.dict.type.cost_producttype_name)
      })

    },
    /** 详情按钮操作 */
    handleDetails (row) {
      this.CostCenterCostVisible = true
      this.$nextTick(() => {
        this.$refs.CostCenterCostVisible.Details(row.productCode, row.id, this.dict.type.cost_producttype_name)
      })
    },
    /** 修改按钮操作 */
    handleUpdate (row) {
      this.CostCenterCostVisible = true
      this.$nextTick(() => {
        this.$refs.CostCenterCostVisible.Update(row.productCode, row.id, this.dict.type.cost_producttype_name)
      })
    },

    /** 提交按钮 */
    submitForm () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addFinancetest(this.form).then(response => {
            if (response.data.code === "0") {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }

          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete (row) {
      const id = row.id || this.ids;
      this.$confirm('此操作将永久删除数据记录，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {//点击确定，执行then方法
        //调用删除的方法
        delFinancetest(id).then(response => {
          if (response.data.code === "0") {
            this.getList();
          }
        })
      })
    },
    getListHandle () {
      this.getList()
    }
  }
};
</script>

<style scoped>
.link-type {
  color: #337ab7;
  cursor: pointer;
}
/*.avue-crud .el-date-editor.el-input {*/
/*  width: auto !important;*/
/*}*/
.plan_header {
  background-color: #f9f9f9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #e9e9e9 solid;
}

.plan_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold;
}

.plan_main {
  height: 86vh;
  background-color: #fff;
}
</style>
<style>
.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}

/* 弹窗圆角 */
.customDialogStyle .el-dialog {
  border-radius: 8px;
}

/* 标题样式 */
.customDialogStyle .el-dialog__header {
  background-color: rgb(245, 245, 245);
  border-radius: 8px 8px 0 0;
  padding: 10px 20px 10px;
}

/* 标题文字样式 */
.customDialogStyle .el-dialog__title {
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  letter-spacing: normal;
  font-size: 14px;
  font-weight: 700;
  font-style: normal;
  text-align: left;
  line-height: 20px;
  border-width: 0;
  color: rgb(102, 102, 102);
}

/* 关闭符号位置调整 */
.customDialogStyle .el-dialog__headerbtn {
  top: 12px;
}

.customDialogStyle .el-dialog__body {
  padding: 15px 40px;
}
</style>
