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
      <el-form-item label="报支类别编码" prop="itemNo">
        <el-input
          v-model="queryParams.itemNo"
          placeholder="请输入报支类别编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报支类别名称" prop="itemName">
        <el-input
          v-model="queryParams.itemName"
          placeholder="请输入报支类别名称"
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
          @click="openSelectPop"
        >选取集团报支类别</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['finance:itemComp:edit']"
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
          v-hasPermi="['finance:itemComp:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="itemCompList"
              @selection-change="handleSelectionChange"
              @row-dblclick="rowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="报支类别编码" align="center" prop="itemNo" />
      <el-table-column label="报支类别名称" align="center" prop="itemName" />
      <el-table-column label="贷方会计科目" align="center" prop="crAcctCode" />
      <el-table-column label="资金项目" align="center" prop="otherType" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:itemComp:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:itemComp:remove']"
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

    <!-- 添加或修改报支类别公司设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="报支类别代码" prop="itemNo">
          <el-input v-model="form.itemNo" placeholder="请输入报支类别代码" class="input"/>
        </el-form-item>
        <el-form-item label="报支类别名称" prop="itemName">
          <el-input v-model="form.itemName" placeholder="请输入报支类别名称" class="input1"/>
        </el-form-item>
        <el-form-item label="贷方会计科目" prop="crAcctCode">
          <el-input v-model="form.crAcctCode" class="input">
            <el-button slot="append" icon="el-icon-search" @click="inputAcctName"
            ></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="核算项目一" prop="calTypeCodea">
          <el-input v-model="form.calTypeCodea" :disabled="true" class="input"/>
        </el-form-item>
        <el-form-item label="核算项目二" prop="calTypeCodeb">
          <el-input v-model="form.calTypeCodeb" :disabled="true" class="input"/>
        </el-form-item>
        <el-form-item label="贷方到期天数" prop="dueDay">
          <el-input v-model="form.dueDay" placeholder="请输入贷方到期天数" class="input"/>
        </el-form-item>
        <el-form-item label="资金项目" prop="otherType">
          <el-select v-model="form.otherType" placeholder="请选择" class="input">
            <el-option
              v-for="dict in dict.type.aa_calcode_rule"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
      <acctcodeCorpPop ref="selectAcctCodeCorpPop" @ok="getAcctCodeCorpPop"/>
    </el-dialog>
    <select-item ref="selectItem" :companyId="this.queryParams.companyId" @ok="handleQuery" ></select-item>
  </div>
</template>

<script>
import { listItemComp, getItemComp, delItemComp, addItemComp, updateItemComp } from "@/api/finance/ap/itemComp";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import selectItem from "@/views/finance/ap/item/selectItem";
import acctcodeCorpPop from "@/views/finance/aa/acctcodeCorpPop";

export default {
  name: "ItemComp",
  dicts: ['aa_calcode_rule'],
  components: { selectItem,acctcodeCorpPop },
  data() {
    return {
      // 会计公司下拉选单
      companyList: [],
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
      // 报支类别公司设置表格数据
      itemCompList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: 'J00',
        itemNo: null,
        itemName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getCompanyList();
    this.getList();
  },
  methods: {
    /** 打开集团报支类别弹窗 */
    openSelectPop() {
      this.$refs.selectItem.show();
    },
    /** 查询报支类别公司设置列表 */
    getList() {
      this.loading = true;
      listItemComp(this.queryParams).then(response => {
        this.itemCompList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
      });
    },
    getAcctCodeCorpPop(val){
      console.log(val);
      this.form.acctId= val[0].acctId
      this.form.crAcctCode= val[0].acctName
      this.form.calTypeCodea = val[0].calTypeCodea;
      this.form.calTypeCodeb = val[0].calTypeCodeb;
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
        itemNo: null,
        itemName: null,
        crAcctCode: null,
        otherType: null,
        status: null,
        dueDay: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateName: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 会计科目点击事件 */
    inputAcctName() {
      this.$refs.selectAcctCodeCorpPop.show();
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
      this.$emit('change', 'second', row.id,row.itemNo,row.itemName);
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
      this.title = "添加报支类别公司设置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getItemComp(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报支类别公司设置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateItemComp(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addItemComp(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除报支类别公司设置编号为"' + ids + '"的数据项？').then(function() {
        return delItemComp(ids);
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
  width: 28%;
}
.input1 {
  width: 70%;
}
</style>
