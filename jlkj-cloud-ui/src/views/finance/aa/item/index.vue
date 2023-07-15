<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="122px">

      <el-form-item label="现金流量名称" prop="cashFlowName">
        <el-input
          disabled
          v-model="queryParams.cashFlowName"
          placeholder="请输入现金流量名称"
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
          v-hasPermi="['aa:item:add']"
        >保存</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAddTCapitalDetail"
          v-hasPermi="['aa:item:Add']"
        >添加</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-form ref="form" :model="form" :rules="rules">
    <el-table v-loading="loading" :data="form.itemList"
              @selection-change="handleSelectionChange"
              :row-class-name="rowTCapitalDetailIndex" ref="tCapitalDetail">
      <el-form-item  >
      <el-table-column type="selection" width="55" align="center" />
      </el-form-item>
      <el-table-column label="加减项" align="center" prop="operator">
        <template slot-scope="scope">
          <el-form-item :prop="'itemList.' + scope.$index + '.operator'" :rules="rules.operator">
            <el-select v-model="scope.row.operator" placeholder="请选择加减项">
              <el-option
                v-for="dict in dict.type.aa_additionorsubtraction"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column label="会计科目编号 " align="center" prop="acctCode" >
        <template slot-scope="scope">
          <el-form-item :prop="'itemList.' + scope.$index + '.acctCode'" :rules="rules.acctCode">
            <el-input v-model="scope.row.acctCode" placeholder="请输入会计科目编号"/>
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column label="取金方式" align="center" prop="quedataway">
        <template slot-scope="scope">
          <el-form-item :prop="'itemList.' + scope.$index + '.quedataway'" :rules="rules.quedataway">
          <el-select v-model="scope.row.quedataway" placeholder="请选择取金方式">
            <el-option
              v-for="dict in dict.type.aa_withdrawalmethod"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column label="新增人" align="center" prop="createBy" />
      <el-table-column label="新增时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="异动人" align="center" prop="updateBy" />
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
            v-hasPermi="['aa:item:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['aa:item:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    </el-form>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />


  </div>
</template>

<script>
import { listItem, getItem, delItem, addItem, updateItem } from "@/api/finance/aa/item";


export default {
  name: "Item",
  dicts: ['aa_withdrawalmethod', 'aa_additionorsubtraction'],
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
      // 现金流量代码-对应会计科目表格数据
      itemList: [],
      // 子表选中数据
      checkedTCapitalDetail: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      codeList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cashFlowCode: null,
        cashFlowName:null
      },
      // 表单参数
      form: { itemList: []},
      // 表单校验
      rules: {
        acctCode: [
          {required: true, message: '请输入会计科目', trigger: 'blur'},
          {max: 20, message: '最大长度20个字符', trigger: 'blur'}
        ],
        operator: [
          {required: true, message: '请输入加减项', trigger: 'blur'},
        ],
        quedataway: [
          {required: true, message: '请输入取金方式', trigger: 'blur'},
        ],
      }
    };
  },
  props: ['cashFlowCode','cashFlowName','id'],
  mounted() {
    this.$watch('cashFlowCode', function (newVal) {
      this.queryParams.cashFlowCode = newVal;
      this.form.cashFlowCode = newVal
      this.getList();
    })
    this.$watch('id', function (newVal) {
      this.queryParams.cashFlowId = newVal;
      this.form.cashFlowId = newVal
    })
    this.$watch('cashFlowName', function (newVal) {
      this.queryParams.cashFlowName = newVal;
      this.getList();
    })
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询现金流量代码-对应会计科目列表 */
    getList() {
      this.loading = true;
      listItem(this.queryParams).then(response => {
        this.form.itemList = response.rows;
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
        companyId: null,
        cashFlowCode: null,
        cashFlowId: null,
        acctCode: null,
        operator: null,
        quedataway: null,
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
      this.checkedTCapitalDetail = selection.map(item => item.index)
      this.codeList = selection
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      if (this.codeList.length == 0) {
        this.$modal.msgError("请至少勾选一笔数据");
      }
 ;      for (let i = 0; i < this.codeList.length; i++) {
        this.codeList[i].cashFlowId = this.form.cashFlowId
        this.codeList[i].cashFlowCode = this.form.cashFlowCode

        this.form.codeList = this.codeList
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          addItem(this.form).then(response => {
            this.$modal.msgSuccess("保存成功");
            this.open = false;
            this.getList();

          });
        }
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      updateItem(row).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      });
  /*    this.reset();
      const id = row.id || this.ids
      getItem(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改现金流量代码-对应会计科目";
      });*/
    },
    /** 细项维护序号 */
    rowTCapitalDetailIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    /** 细项维护添加按钮操作 */
    handleAddTCapitalDetail() {
      let obj = {};
      obj.cashFlowCode = "";
      obj.cashFlowName = "";
      obj.isunabl = "";
      obj.inorout = "";
      obj.quedataway = "";
      this.form.itemList.push(obj);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateItem(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addItem(this.form).then(response => {
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
      if (row.id == null||row.id==''||row.id==undefined){
        if (this.checkedTCapitalDetail.length == 0) {
          this.$modal.msgError("请先选择要删除的会计科目数据");
        } else {
          const tCodeList = this.form.itemList;
          const checkedTCapitalDetail = this.checkedTCapitalDetail;
          this.form.itemList = tCodeList.filter(function (item) {
            return checkedTCapitalDetail.indexOf(item.index) == -1
          });
        }
      }else {
        const ids = row.id || this.ids;
        this.$modal.confirm('是否确认删除现金流量代码-对应会计科目编号为"' + row.acctCode + '"的数据项？').then(function () {
          return delItem(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      /*this.download('aa/item/export', {
        ...this.queryParams
      }, `item_${new Date().getTime()}.xlsx`)*/
    }
  }
};
</script>
