<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="报支类别名称" prop="itemName">
        <el-input
          v-model="queryParams.itemName"
          placeholder="请输入报支类别名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报支类别代码" prop="itemNo">
        <el-input
          v-model="queryParams.itemNo"
          placeholder="请输入报支类别代码"
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
          v-hasPermi="['finance:item:add']"
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
          v-hasPermi="['finance:item:edit']"
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
          v-hasPermi="['finance:item:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="itemList"
              @selection-change="handleSelectionChange"
              @row-dblclick="rowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="报支类别编码" align="center" prop="itemNo" />
      <el-table-column label="报支类别名称" align="center" prop="itemName" />
      <el-table-column label="资金项目" align="center" prop="otherType" />
      <el-table-column label="贷方会计科目" align="center" prop="crAcctCode" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:item:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:item:remove']"
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

    <!-- 添加或修改报支类别集团设置对话框 -->
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
  </div>
</template>

<script>
import { listItem, getItem, delItem, addItem, updateItem } from "@/api/finance/ap/item";
import acctcodeCorpPop from "@/views/finance/aa/acctcodeCorpPop";


export default {
  name: "Item",
  dicts: ['aa_calcode_rule'],
  components: {
    acctcodeCorpPop
  },
  data() {
    return {
      // dueDay:1,
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
      // 报支类别集团设置表格数据
      itemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemNo: null,
        itemName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        itemNo: [
          { required: true, message: "报支类别代码不能为空", trigger: "blur" },
          {max: 10 ,trigger: 'blur',message: "最大不超过10个字符"}
        ],
        itemName: [
          { required: true, message: "报支类别名称不能为空", trigger: "blur" },
          {max: 100 ,trigger: 'blur',message: "最大不超过100个字符"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // handleChange(value) {
    // },
    /** 查询报支类别集团设置列表 */
    getList() {
      this.loading = true;
      listItem(this.queryParams).then(response => {
        this.itemList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getAcctCodeCorpPop(val){
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
      this.title = "新增报支类别";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getItem(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报支类别";
      });
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除报支类别集团设置编号为"' + ids + '"的数据项？').then(function() {
        return delItem(ids);
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
