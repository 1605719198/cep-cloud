<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true"
             v-show="showSearch" label-width="87px">
      <el-form-item label="申请单号" prop="applyId">
        <el-input
          v-model="queryParams.applyId"
          placeholder="请输入申请单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="往来公司名称" prop="manufacturerName" label-width="96px">
        <el-input
          v-model="queryParams.manufacturerName"
          placeholder="请输入往来公司名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="法定代表人" prop="owner">
        <el-input
          v-model="queryParams.owner"
          placeholder="请输入法定代表人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
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
          v-hasPermi="['gp:add:add']"
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
          v-hasPermi="['gp:add:edit']"
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
          v-hasPermi="['gp:add:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="addList" @selection-change="handleSelectionChange"  @row-dblclick="rowClick">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="申请单号" align="center" prop="applyId" />
      <el-table-column label="往来公司名称" align="center" prop="manufacturerName" />
      <el-table-column label="法定代表人" align="center" prop="owner" />
      <el-table-column label="联络人" align="center" prop="contact" />
      <el-table-column label="电话" align="center" prop="tel" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['gp:add:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gp:add:remove']"
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


    <!-- 添加或修改厂商增户申请对话框 -->
    <el-dialog :title="title" :visible.sync="open"
               class="customDialogStyle"
               custom-class="saveAsDialog"
               append-to-body  width="900px">
      <el-form ref="form" :model="form" :rules="rules" label-width="106px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="申请单号" prop="applyId">
              <el-input v-model="form.applyId" placeholder="自动生成" disabled style="width: 220px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="往来公司名称" prop="manufacturerName">
              <el-input v-model="form.manufacturerName" placeholder="请输入往来公司名称"  style="width: 220px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="法定代表人" prop="owner">
              <el-input v-model="form.owner" placeholder="请输入法定代表人" style="width: 220px" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联络人" prop="contact">
              <el-input v-model="form.contact" placeholder="请输入联络人" style="width: 220px" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="tel">
              <el-input v-model="form.tel" placeholder="请输入电话"  style="width: 220px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="传真" prop="fax">
              <el-input v-model="form.fax" placeholder="请输入传真" style="width: 220px" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮政区号" prop="billPtcode">
              <el-input v-model="form.billPtcode" placeholder="请输入邮政区号" style="width: 220px" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="财务维护人员" prop="auditor">
              <el-input v-model="form.auditor" placeholder="请输入财务维护人员" style="width: 220px" />
<!--              <treeselect v-model="form.auditor"
                          class="treeselect-main"
                          :options="deptOptions"
                          :show-count="true"
                          placeholder="请选择财务维护人员"
                          style="width: 220px"
              />-->
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经办人" prop="createUserName">
              <el-input v-model="form.createUserName" placeholder="当前登录人" disabled style="width: 220px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电子邮件" prop="email">
              <el-input v-model="form.email" placeholder="请输入电子邮件" style="width: 220px" />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="送货地址邮编" prop="sendPtcode">
              <el-input v-model="form.sendPtcode" placeholder="请输入送货地址邮编"  style="width: 220px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否集团中采" prop="purchaseType">
              <el-select v-model="form.purchaseType"  placeholder="是否集团中采"  style="width: 220px">
                <el-option
                  v-for="dict in dict.type.gp_add_purchasetype"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="送货地址" prop="sendAddress">
              <el-input v-model="form.sendAddress" placeholder="请输入送货地址"  style="width: 220px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发票地址" prop="billAddress">
              <el-input v-model="form.billAddress" placeholder="请输入发票地址" style="width: 220px" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="说明" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入说明"  type="textarea"  />
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
import { listAdd, getAdd, delAdd, addAdd, updateAdd } from "@/api/finance/gp/add";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {deptTreeSelect,getAvatorByUserName} from "@/api/system/user";
export default {
  components: {Treeselect},
  dicts: ['gp_add_purchasetype'],
  name: "add",
  data() {
    return {
      // 部门树选项
      deptOptions: undefined,
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
      // 厂商增户申请表格数据
      addList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        applyId: null,
        compId: null,
        owner: null,
        createTime: null,
        startDate: null,
        endDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        manufacturerName: [
          {required: true, message: '请输入往来公司名称', trigger: 'blur'},
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的往来公司名称", trigger: "blur"},
          {max: 30, message: '最大长度为30个字符', trigger: 'blur'}],
        owner: [
          {required: true, message: '请输入法人代表', trigger: 'blur'},
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的法定代表人", trigger: "blur"},
          {max: 30, message: '最大长度为30个字符', trigger: 'blur'}],
        contact :[
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的联络人", trigger: "blur"},
          {max: 30, message: '最大长度30个字符', trigger: 'blur'}],
        tel:[
          { pattern:/^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/, message: "请输入正确的电话号码", trigger: "blur"},
          {max: 20, message: '最大长度20个字符', trigger: 'blur'}],
        fax: [
          { pattern:/^(?:\d{3,4}-)?\d{7,8}(?:-\d{1,6})?$/, message: "请输入正确的传真号码", trigger: "blur"},
          {max: 30, message: '最大长度为30个字符', trigger: 'blur'}],
        billPtcode: [
          { pattern:/^[0-9]*$/, message: "请输入正确的邮政区号", trigger: "blur"},
          {max: 20, message: '最大长度为20个字符', trigger: 'blur'}],
        auditor:[
          {required: true, message: '请输入财务维护人员', trigger: 'blur'},
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的财务维护人员", trigger: "blur"},
          {max: 30, message: '最大长度30个字符', trigger: 'blur'}],
        email :[
          { pattern:/^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/, message: "请输入正确的电子邮件", trigger: "blur"},
          {max: 50, message: '最大长度50个字符', trigger: 'blur'}],
        sendPtcode:[
          { pattern:/^[0-9]*$/, message: "请输入正确的电子邮件", trigger: "blur"},
          {max: 20, message: '最大长度20个字符', trigger: 'blur'}],
        sendAddress:[
          {max: 200, message: '最大长度200个字符', trigger: 'blur'}],
        billAddress:[
          {max: 200, message: '最大长度200个字符', trigger: 'blur'}],
        remark:[
          {max: 300, message: '最大长度300个字符', trigger: 'blur'}],
      }
    };
  },
  created() {
    this.getList();
    this.getDeptTree()
  },
  methods: {
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
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
      console.log(val);
      if (val!=null){
        this.queryParams.startDate = val[0]
        this.queryParams.endDate = val[1]
      }else {
        this.queryParams.startDate = ''
        this.queryParams.endDate = ''
      }

    },
    /** 查询厂商增户申请列表 */
    getList() {
      this.loading = true;
      listAdd(this.queryParams).then(response => {
        this.addList = response.rows;
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
        owner: null,
        contact: null,
        tel: null,
        fax: null,
        auditor: null,
        status: null,
        billAddress: null,
        billPtcode: null,
        sendAddress: null,
        email: null,
        sendPtcode: null,
        purchaseType: null,
        updateDate: null,
        updateUser: null,
        createUser: null,
        createTime: null,
        updateTime: null,
        updateUserName: null,
        createUserName: null
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
      this.title = "添加厂商增户申请";
      // 获取当前登录用户名称
      this.form.createUserName = this.$store.state.user.name
      getAvatorByUserName(this.$store.state.user.name).then( response => {
        this.form.createUserName = response.data.nickName
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const applyId = row.applyId || this.ids
      getAdd(applyId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改厂商增户申请";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.applyId != null) {
            updateAdd(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAdd(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除厂商增户申请编号为"' + applyIds + '"的数据项？').then(function() {
        return delAdd(applyIds);

      }).then(() => {
        this.getList();

        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('gp/add/export', {
        ...this.queryParams
      }, `add_${new Date().getTime()}.xlsx`)
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



