<template>
  <div class="app-container">
    <el-form :model="queryParams"
             :rules="rulesQuery"ref="queryForm" size="small" :inline="true"
             v-show="showSearch" label-width="84px">
     <el-form-item label="期初导入">
       <el-form-item label="会计公司" prop="companyId">
         <el-select v-model="queryParams.companyId" filterable placeholder="请输入会计公司">
           <el-option
             v-for="item in companyList"
             :key="item.value"
             :label="item.label"
             :value="item.value"
             @keyup.enter.native="handleQuery">
           </el-option>
         </el-select>
       </el-form-item>
       <el-form-item label="会计周期" prop="acctPeriod">
         <el-date-picker clearable
                         v-model="queryParams.acctPeriod"
                         size="mini"
                         type="month"
                         value-format="yyyy-MM"
                         format="yyyy-MM"
                         placeholder="请选择会计周期">
         </el-date-picker>
       </el-form-item>
       <el-form-item>
         <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
         <el-button type="primary" size="mini" @click="handleImport">导入</el-button>
       </el-form-item>
     </el-form-item>

    </el-form>
    <el-form>
      <el-form-item  label="期初明细">
        <el-table v-loading="loading" :data="initList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />

          <el-table-column label="会计周期" align="center" prop="acctPeriod" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.acctPeriod, '{y}-{m}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="会计科目编号" align="center" prop="acctCode" />
          <el-table-column label="核算项目一名称" align="center" prop="calNamea" />
          <el-table-column label="核算项目二名称" align="center" prop="calNameb" />
          <el-table-column label="核算项目三名称" align="center" prop="calNamec" />
          <el-table-column label="核算项目四名称" align="center" prop="calNamed" />
          <el-table-column label="期初余额" align="center" prop="bgnAmt" />
          <el-table-column label="期初余数量" align="center" prop="bgnQty" />
          <el-table-column label="错误信息" align="center" prop="errorMessage":show-overflow-tooltip='true' />
        </el-table>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-form-item>
    </el-form>
    <!-- 更多条件查询弹窗 -->

    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" class="customDialogStyle"  append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url +'/'+queryParams.acctPeriod +'/'+queryParams.companyId+'?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listInit, getInit, delInit, addInit, updateInit } from "@/api/finance/aa/init";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {getToken} from "@/utils/auth";
export default {
  name: "Init",
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
      // 初始化记录表格数据
      initList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        acctPeriod: null,
      },
      // 导入参数
      upload: {
        // 是否显示弹出层（探亲假资料导入）
        open: false,
        // 弹出层标题（探亲假资料导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/finance/init/importData"
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 搜索框校验
      rulesQuery: {
        companyId: [
          {required: true, message: '请输入公司', trigger: 'blur'}
        ],
        acctPeriod: [
          {required: true, message: '请输入会计周期', trigger: 'blur'}
        ],
      },
      companyList: [],
    };
  },
  created() {

    this.getCompanyList()
  },
  methods: {
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
        this.queryParams.companyId = this.companyList[0].value
        this.getList();
      });
    },
    /** 查询初始化记录列表 */
    getList() {
      this.loading = true;
      listInit(this.queryParams).then(response => {
        this.initList = response.rows;
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
        acctPeriod: null,
        acctId: null,
        acctCode: null,
        acctName: null,
        calIda: null,
        calCodea: null,
        calNamea: null,
        calIdb: null,
        calCodeb: null,
        calNameb: null,
        calIdc: null,
        calCodec: null,
        calNamec: null,
        calIdd: null,
        calCoded: null,
        calNamed: null,
        bgnAmt: null,
        bgnQty: null,
        createBy: null,
        createTime: null,
        createName: null
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加初始化记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInit(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改初始化记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInit(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除初始化记录编号为"' + ids + '"的数据项？').then(function () {
        return delInit(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/init/export', {
        ...this.queryParams
      }, `init_${new Date().getTime()}.xlsx`)
    },

    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "期初数据导入";
      console.log(this.queryParams.companyId);
      if (!this.queryParams.companyId) {
        this.$message.error('公司不能为空');
        return
      }
      if (!this.queryParams.acctPeriod) {
        this.$message.error('会计周期不能为空');
        return
      }
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('finance/init/importTemplate', {}, `init_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.initList = response.data
      console.log(response.data);

      if (  this.initList.length>0 ){
        this.$modal.msgError("上传文件失败，请重试");
      }else {
        this.$message({message: '上传成功', type: 'success'});
      }
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
/*      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
        response.msg + "</div>", "导入结果",
        {dangerouslyUseHTMLString: true});*/

    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }

};
</script>
