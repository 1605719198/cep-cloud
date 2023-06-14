<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="会计公司代码" prop="companyNo">
        <el-input
          v-model="queryParams.companyNo"
          placeholder="请输入会计公司代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="会计公司简称" prop="companySname">
        <el-input
          v-model="queryParams.companySname"
          placeholder="请输入会计公司简称"
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
          v-hasPermi="['finance:companyGroup:add']"
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
          v-hasPermi="['finance:companyGroup:edit']"
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
          v-hasPermi="['finance:companyGroup:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="companyGroupList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="会计公司代码" align="center" prop="companyNo" />
      <el-table-column label="会计公司简称" align="center" prop="companySname" />
      <el-table-column label="会计公司全称" align="center" prop="companyName" />
      <el-table-column label="默认币别编码" align="center" prop="crcyCode" />
      <el-table-column label="对应人事公司" align="center" prop="deptCode" />
      <el-table-column label="对应厂商编码NO" align="center" prop="manufacturerNo" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:companyGroup:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:companyGroup:remove']"
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

    <!-- 添加或修改集团管理-会计公司维护对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-form-item label="会计公司代码" prop="companyNo">
          <el-input v-model="form.companyNo" placeholder="请输入会计公司代码" maxlength="32" show-word-limit style="width:160px"/>
        </el-form-item>
        <el-form-item label="会计公司简称" prop="companySname">
          <el-input v-model="form.companySname" placeholder="请输入会计公司简称" maxlength="10" show-word-limit style="width:160px"/>
        </el-form-item>
        <el-form-item label="会计公司全称" prop="companyName">
          <el-input v-model="form.companyName" placeholder="请输入会计公司全称"  maxlength="200" show-word-limit style="width:400px"/>
        </el-form-item>
        <el-form-item label="默认币别" prop="crcyCode">
          <el-input v-model="form.crcyCode" placeholder="请输入默认币别编码" style="width:400px"/>
        </el-form-item>
        <el-form-item label="对应人事公司" prop="deptCode">
          <el-input v-model="form.deptCode" placeholder="请输入对应人事公司" style="width:400px"/>
        </el-form-item>
        <el-form-item label="对应厂商" prop="manufacturerNo">
          <el-input v-model="form.manufacturerNo" placeholder="请输入对应厂商编码NO" disabled style="width:400px">
            <el-button slot="append" icon="el-icon-search" @click="inputClick"></el-button>
          </el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="新增人" prop="createName" align="left">
              {{form.createName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="新增时间" prop="createTime" align="left">
              {{form.createTime}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="异动人" prop="updateName" align="left">
              {{form.updateName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="异动时间" prop="updateTime" align="left">
              {{form.updateTime}}
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer" align="center">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  <selectManufacturer ref="select" @ok="getJobNumber"/>
  </div>
</template>

<script>
import { listCompanyGroup, getCompanyGroup, delCompanyGroup, addCompanyGroup, updateCompanyGroup } from "@/api/finance/aa/companyGroup";
import selectManufacturer from "@/views/components/finance/selectManufacturer";
export default {
  name: "CompanyGroup",
  components: {selectManufacturer},
  data() {
    return {
      // 遮罩层
      selectManufacturer:false,
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
      // 集团管理-会计公司维护表格数据
      companyGroupList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyNo: null,
        companySname: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companyNo: [
          { required: true, message: "会计公司代码不能为空", trigger: "blur" },
          { max: 32,message: '长度不可超过32个字符', trigger: 'blur'}
        ],
        companySname: [
          { required: true, message: "会计公司简称不能为空", trigger: "blur" },
          { max: 10,message: '长度不可超过5个字符', trigger: 'blur'}
        ],
        companyName: [
          { required: true, message: "会计公司全称不能为空", trigger: "blur" },
          { max: 36,message: '长度不可超过200个字符', trigger: 'blur'}
        ],
        crcyCode: [
          { required: true, message: "默认币别不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询集团管理-会计公司维护列表 */
    getList() {
      this.loading = true;
      listCompanyGroup(this.queryParams).then(response => {
        this.companyGroupList = response.rows;
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
        companyNo: null,
        companySname: null,
        companyName: null,
        crcyId: null,
        crcyCode: null,
        deptCode: null,
        manufacturerId: null,
        manufacturerNo: null,
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
      this.title = "添加集团管理-会计公司维护";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCompanyGroup(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改集团管理-会计公司维护";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCompanyGroup(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCompanyGroup(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除集团管理-会计公司维护编号为"' + ids + '"的数据项？').then(function() {
        return delCompanyGroup(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/companyGroup/export', {
        ...this.queryParams
      }, `companyGroup_${new Date().getTime()}.xlsx`)
    },
    inputClick(){
      this.selectManufacturer = true
      this.$refs.select.show();
    },
    getJobNumber(val,userName) {
      this.form.manufacturerNo = val
    },
  }
};
</script>
