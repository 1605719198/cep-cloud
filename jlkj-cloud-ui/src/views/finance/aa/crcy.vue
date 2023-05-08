<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="97px">
      <el-form-item label="货币代码" prop="crcyCode">
        <el-input
          v-model="queryParams.crcyCode"
          placeholder="请输入货币代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="货币简称" prop="crcyAbbr">

        <el-select v-model="queryParams.crcyAbbr" placeholder="请选择">
          <el-option
            v-for="dict in dict.type.aa_crcy_abbr"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="货币中文名称" prop="crcyLocalName">

        <el-select v-model="queryParams.crcyLocalName" placeholder="请选择">
          <el-option
            v-for="dict in dict.type.aa_crcy_localname"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value">

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
          v-hasPermi="['finance:crcy:add']"
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
          v-hasPermi="['finance:crcy:edit']"
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
          v-hasPermi="['finance:crcy:remove']"
        >删除</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="crcyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="货币代码" align="center" prop="crcyCode" />
      <el-table-column label="货币英文全名" align="center" prop="crcyEngName" />
      <el-table-column label="货币中文名称" align="center" prop="crcyLocalName" />
      <el-table-column label="货币符号" align="center" prop="crcySymbol" />
      <el-table-column label="图档" align="center" prop="crcyPath" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.crcyPath" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="默认货币" align="center" prop="defaultCrcy" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:crcy:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:crcy:remove']"
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

    <!-- 添加或修改币别管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="货币代码" prop="crcyCode">
              <el-input v-model="form.crcyCode" placeholder="请输入货币代码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="货币英文全名" prop="crcyEngName">
              <el-input v-model="form.crcyEngName" placeholder="请输入货币英文全名" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="货币中文名称" prop="crcyLocalName">
              <el-input v-model="form.crcyLocalName" placeholder="请输入货币中文名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="货币符号" prop="crcySymbol">
              <el-input v-model="form.crcySymbol" placeholder="请输入货币符号" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="图档" prop="crcyPath">
<!--          <el-upload-->
<!--            class="avatar-uploader"-->
<!--            action="http://localhost:80/finance/ipms/crcy"-->
<!--            :show-file-list="false"-->
<!--            :on-success="handleAvatarSuccess"-->
<!--            :before-upload="beforeAvatarUpload">-->
<!--            <img v-if="imageUrl" :src="imageUrl" class="avatar">-->
<!--            <i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
<!--          </el-upload>-->
          <image-upload v-model="form.crcyPath">
            class="avatar-uploader"
            action="http://10.32.15.160:80/finance/ipms/crcy"
<!--            :show-file-list="false"-->
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </image-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCrcy, getCrcy, delCrcy, addCrcy, updateCrcy } from "@/api/finance/aa/crcy";
import crcyPath from "@/api/finance/aa/crcy";

export default {
  name: "Crcy",
  dicts: ['aa_crcy_abbr','aa_crcy_localname'],
  components: {crcyPath},
  data() {
    return {
      imageUrl: '',
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
      // 总条数
      total: 0,
      // 币别管理表格数据
      crcyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        crcyCode: null,
        crcyAbbr: null,
        crcyLocalName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        crcyCode: [
          {required: true, trigger: 'blur',message: "货币代码不能为空"},
          {max: 5 ,trigger: 'blur',message: "最大不超过5个字符"}
        ],
        crcyEngName: [
          {required: true, trigger: 'blur',message: "货币英文全名不能为空"},
          {max: 60 ,trigger: 'blur',message: "最大不超过60个字符"}
        ],
        crcyLocalName: [
          {required: true, trigger: 'blur',message: "货币中文名称不能为空"},
          {max: 60 ,trigger: 'blur',message: "最大不超过60个字符"}
        ],
        crcySymbol: [
          {max: 10 ,trigger: 'blur',message: "最大不超过10个字符"}
        ],
        startDate: [
          {required: true, trigger: 'blur',message: "开始日期不能为空"},
        ],
        endDate: [
          {required: true, trigger: 'blur',message: "结束日期不能为空"},
        ]
      },
    };
  },
  created() {
   this.getList()
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!');
      }
      return isLt2M;
    },

    /** 查询币别管理列表 */
    getList() {
      this.loading = true;
      listCrcy(this.queryParams).then(response => {
        this.crcyList = response.rows;
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
        crcyCode: null,
        crcyAbbr: null,
        crcyEngName: null,
        crcyLocalName: null,
        crcySymbol: null,
        crcyPath: null,
        defaultCrcy: null,
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
      this.title = "新增币别管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      console.log("row.id",row,id)
      getCrcy(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改币别管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCrcy(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCrcy(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除币别管理编号为"' + ids + '"的数据项？').then(function() {
        return delCrcy(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>
