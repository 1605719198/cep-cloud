<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true"
             v-show="showSearch" label-width="96px">
      <el-form-item label="申请单号" prop="applyId">
        <el-input
          v-model="queryParams.applyId"
          placeholder="请输入申请单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="往来公司代码" prop="manufacturerId">
        <el-input v-model="queryParams.manufacturerId" placeholder="请输入厂商编号"   @keyup.enter.native="handleQuery" >
          <el-button slot="append" icon="el-icon-search" @click="inputClick"
          ></el-button>
        </el-input>

      </el-form-item>
      <el-form-item label="新增加人" prop="createUser">
        <el-input
          v-model="queryParams.createUser"
          placeholder="请输入新增加人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="新增加日期" prop="createTime">
        <el-date-picker
          style="width: 220px;"
          v-model="queryParams.createTime"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="dutyDateChange">
        </el-date-picker>

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
          v-hasPermi="['gp:change:add']"
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
          v-hasPermi="['gp:change:edit']"
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
          v-hasPermi="['gp:change:remove']"
        >删除</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="changeList" @selection-change="handleSelectionChange"  @row-dblclick="rowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="申请单号" align="center" prop="applyId" />
      <el-table-column label="往来公司名称" align="center" prop="manufacturerName" />
      <el-table-column label="往来公司代码" align="center" prop="manufacturerId" />
      <el-table-column label="财务维护人员" align="center" prop="auditor" />
      <el-table-column label="新增加人" align="center" prop="createUser" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['gp:change:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gp:change:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-picture-outline"
            @click="handleSkipAddFile(scope.row)"
            v-hasPermi="['gp:addfile:skipAddFile']"
          >证照</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            @click="handleSkipVerify(scope.row)"
            v-hasPermi="['gp:addfile:skipVerify']"
          >审核</el-button>
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

    <!-- 添加或修改厂商异动申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px"
               class="customDialogStyle"
               custom-class="saveAsDialog"
               append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="112px">
        <el-row>
        <el-col :span="12">
          <el-form-item label="申请单号" prop="applyId">
            <el-input v-model="form.applyId" placeholder="自动生成" disabled
                      style="width: 220px"/>
          </el-form-item>
        </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-input v-model="form.status" placeholder="自动生成" disabled
                        style="width: 220px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="厂商编号" prop="manufacturerId">
              <el-input v-model="form.manufacturerId" placeholder="请输入厂商编号"  style="width: 220px" >
                <el-button slot="append" icon="el-icon-search" @click="inputClick"
                ></el-button>
              </el-input>
<!--              <el-popover
                placement="right"
                width="400"
                trigger="focus">
                <FinanceTree  :getTreeNode="getThirdUst" :financeTreeList="financeTreeList"  v-if="productCodeTree" />

                <el-input v-model="form.manufacturerId" placeholder="请输入厂商编号"  style="width: 220px" slot="reference">
                  <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
                </el-input>
              </el-popover>-->
            </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="厂商名称" prop="manufacturerName">
          <el-input v-model="form.manufacturerName" placeholder="请输入厂商名称" style="width: 220px" />
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="财务维护人员" prop="auditor">
          <el-input v-model="form.auditor" placeholder="请输入财务维护人员" style="width: 220px" />
        </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经办人" prop="createUserName">
              <el-input v-model="form.createUserName" placeholder="当前登录人" disabled style="width: 220px"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
        <el-form-item label="说明" prop="remarkAdd">
          <el-input v-model="form.remarkAdd" placeholder="请输入说明" type="textarea"/>
        </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <selectManufacturer ref="select" @ok="getJobNumber"/>
  </div>
</template>

<script>
import selectManufacturer from "@/views/components/finance/selectManufacturer";
import { listChange, getChange, delChange, addChange, updateChange } from "@/api/finance/gp/change";
import FinanceTree from "@/components/finance-manufacturer-tree/FinanceTree";
import {
  getTreeTestNode
} from "@/api/finance/gp/financeaccount";
export default {
  name: "change",

  components: {
    FinanceTree,
    selectManufacturer
  },
  data() {
    return {
      selectManufacturer:false,
      productCodeTree:true,
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
      // 厂商异动申请表格数据
      changeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        manufacturerName: null,
        createUser: null,
        createTime: null,
        updateUserName: null,
        createUserName: null,
        manufacturerId: null,
        startDate: null,
        endDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        manufacturerId:[
          {required: true, message: '请输入厂商编号', trigger: 'blur'},],
        manufacturerName: [
          {required: true, message: '请输入往来公司名称', trigger: 'blur'},
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的往来公司名称", trigger: "blur"},
          {max: 30, message: '最大长度为30个字符', trigger: 'blur'}],
        auditor:[
          {required: true, message: '请输入财务维护人员', trigger: 'blur'},
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的财务维护人员", trigger: "blur"},
          {max: 30, message: '最大长度30个字符', trigger: 'blur'}],
      },
      financeTreeList:''
    };
  },

  mounted() {
    this.getTreeTestNodeList();

    this.getList();
  },
  methods: {
    getTreeTestNodeList(){

      getTreeTestNode().then(response => {

        this.financeTreeList = response.data;
      }).catch(e=>{

      })

    },
    getJobNumber(val,userName) {
          this.queryParams.manufacturerId = val
          this.form.manufacturerId = val
          this.form.manufacturerName = userName
          this.selectManufacturer = false
          this.getList();
    },
    /** 工号点击事件 */
    inputClick(){
      this.selectManufacturer = true
      this.$refs.select.show();
    },

    getThirdUst(data) {
      this.form.manufacturerId = data.nodeNo;
      this.form.manufacturerName = data.nodeName;
      this.productCodeTree=false;
    },
    // 分页数据
    handleSizeChange(val) {
      this.queryParams.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.queryParams.pageNum = val
      this.getList()
    },
    //查询日期切换事件
    dutyDateChange(val) {
      if (val!=null){
        this.queryParams.startDate = val[0]
        this.queryParams.endDate = val[1]
      }else {
        this.queryParams.startDate = ''
        this.queryParams.endDate = ''
      }

    },
    /** 查询厂商异动申请列表 */
    getList() {
      this.loading = true;
      listChange(this.queryParams).then(response => {
        this.changeList = response.rows;
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
        applyId: null,
        compId: null,
        manufacturerName: null,
        auditor: null,
        updateDate: null,
        updateUser: null,
        createUser: null,
        createTime: null,
        updateTime: null,
        updateUserName: null,
        createUserName: null,
        status: null,
        manufacturerId: null,
        remarkAdd: null
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
      this.ids = selection.map(item => item.applyId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    rowClick(row, column, e) {

      this.$emit('change', 'second', row.applyId);
    },
    handleSkipAddFile(row){
      this.$emit('change', 'second', row.applyId);
    },
    handleSkipVerify(row){
      this.$emit('change', 'third', row.applyId);
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      // 获取当前登录用户名称
      this.form.createUserName = this.$store.state.user.name
      this.title = "添加厂商异动申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const applyId = row.applyId || this.ids
      getChange(applyId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改厂商异动申请";
      });
    },
    /** 提交按钮 */
    submitForm() {

      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.applyId != null) {
            updateChange(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addChange(this.form).then(response => {
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
      const applyIds = row.applyId || this.ids;
      this.$modal.confirm('是否确认删除厂商异动申请编号为"' + applyIds + '"的数据项？').then(function() {
        return delChange(applyIds);
      }).then(() => {
        this.getList();

        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('gp/change/export', {
        ...this.queryParams
      }, `change_${new Date().getTime()}.xlsx`)
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

