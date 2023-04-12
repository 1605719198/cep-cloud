<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="96px">
      <el-form-item label="申请单号" prop="applyId">
        <el-input
          v-model="queryParams.applyId"
          placeholder="请输入申请单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="往来公司名称" prop="manufacturerName">
        <el-input
          v-model="queryParams.manufacturerName"
          placeholder="请输入往来公司名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上传资料类型" prop="dataType">
        <el-select v-model="queryParams.dataType"  placeholder="请选择上传资料类型">
          <el-option
            v-for="dict in dict.type.finance_gp_datatype"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
          v-hasPermi="['gp:addfile:add']"
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
          v-hasPermi="['gp:addfile:edit']"
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
          v-hasPermi="['gp:addfile:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-s-promotion"
          size="mini"
          @click="handleExport"
          v-hasPermi="['gp:addfile:export']"
        >送出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="addfileList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="申请单号" align="center" prop="applyId" />
      <el-table-column label="申请状态" align="center" prop="status" />
      <el-table-column label="往来公司名称" align="center" prop="manufacturerName" />
      <el-table-column label="上传资料类型" align="center" prop="dataType" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.finance_gp_datatype" :value="scope.row.dataType"/>
        </template>
      </el-table-column>
      <el-table-column label="上传资料名称" align="center" prop="fileName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['gp:addfile:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gp:addfile:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="downloadFile(scope.row)"
          >下载
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="previewImgFile(scope.row)"

          >预览
          </el-button>
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
    <!-- 添加或修改厂商增户证照资料对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px"  class="customDialogStyle" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="106px">

        <el-form-item label="上传资料类型" prop="dataType">
          <el-select v-model="form.dataType"  placeholder="请选择上传资料类型"  style="width: 220px">
            <el-option
              v-for="dict in dict.type.finance_gp_datatype"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>

        </el-form-item>

        <el-form-item label="申请单号" prop="applyId">
          <el-input v-model="form.applyId" disabled placeholder="请输入申请单号" style="width: 220px" />
        </el-form-item>
        <el-form-item label="文件类型" prop="filetype">
          <el-input v-model="form.filetype"disabled tplaceholder="请输入内容" style="width: 220px" />
        </el-form-item>
        <el-form-item label="文件名" prop="fileName">
          <el-input v-model="form.fileName" disabled placeholder="请输入内容" style="width: 220px" />
        </el-form-item>

          <el-form-item label="文件上传" prop="fileUrl">
            <el-upload
              class="upload-demo"
              :headers="headers"
              ref="FileUpload"
              :action="uploadFileurl"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleDeleteImg"
              :on-success="handleUploadSuccess"
              :before-upload="handleBeforeUpload"
              :on-error="handleUploadError"
              :on-exceed="handleExceed"
              multiple
              :limit="1"
              :file-list="fileUrlList">
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>

          </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogVisibleImage">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
    <!--删除调用刷新 -->

  </div>
</template>

<script>
import { listAddfile, getAddfile, delAddfile, addAddfile, updateAddfile } from "@/api/finance/gp/addfile";
import {getToken} from "@/utils/auth";


export default {
  name: "Addfile",
  dicts: ['finance_gp_datatype'],

  data() {
    return {
      dialogVisibleImage: false,
      dialogImageUrl:'',
      fromApplyid:'',
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
      // 厂商增户证照资料表格数据
      addfileList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        uuid: null,
        compId: null,
        applyId: null,
        applyType: null,
        filetype: null,
        filebase64: null,
        fileUrl: null,
        dataType: null,
        manufacturerName:null,

      },
      uploadList: [],

      baseUrl: process.env.VUE_APP_BASE_API,
      // 上传的文件服务器地址
      uploadFileurl:'',
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      download: '',
      views:'',
      fileUrlList: [],
      imageList: [],
      fileUrlId:'',
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        dataType:[
          {required: true, message: '请输入上传资料类型', trigger: 'blur'},],
      }
    };
  },
  props: ['applyId'],
  mounted() {
    this.$watch('applyId', function (newVal) {
      this.queryParams.applyId = newVal;
      this.fromApplyid = newVal
      this.getList();
    })
  },
  created() {
    this.uploadFileurl = process.env.VUE_APP_BASE_API + '/file/file/upload', // 上传的图片服务器地址
     this.download = process.env.VUE_APP_BASE_API + '/file/file/download/';
    this.views = process.env.VUE_APP_BASE_API + '/file/file/view/';
    this.getList();
  },
  methods: {

    // 分页数据
    handleSizeChange(val) {
      this.queryParams.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.queryParams.pageNum = val
      this.getList()
    },
    downloadFile(row) {
      window.open(this.download + row.uuid);

    },
    //图片预览
    previewImgFile(row) {
      window.open(this.views + row.uuid);
    },
    /** 查询厂商增户证照资料列表 */
    getList() {
      this.loading = true;
      listAddfile(this.queryParams).then(response => {
        this.addfileList = response.rows;
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
        uuid: null,
        compId: null,
        applyId: null,
        applyType: null,
        filetype: null,
        filebase64: null,
        fileUrl: null,
        dataType: null,
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
      this.ids = selection.map(item => item.uuid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      if(this.queryParams.applyId!=null){
        this.open = true;
      }
      this.imageList = []
      this.fileUrlList = []
      this.form.applyId = this.fromApplyid
      this.title = "添加厂商增户证照资料";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.imageList = []
      this.fileUrlList = []
      const uuid = row.uuid || this.ids
      console.log(row);
      getAddfile(uuid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改厂商增户证照资料";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {

        this.form.fileUrl = this.imageList.toString()
        if (valid) {
          if (this.form.uuid != null) {
            console.log(this.form);
            updateAddfile(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.uuid = this.fileUrlId
            addAddfile(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除厂商增户证照资料编号为"' + uuids + '"的数据项？').then(function() {
        return delAddfile(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

    /** 导出按钮操作 */
    handleExport() {
      // this.download('gp/addfile/export', {
      //   ...this.queryParams
      // }, `addfile_${new Date().getTime()}.xlsx`)
    },
    // 预览
    handlePictureCardPreview(file) {

      this.dialogImageUrl = file.url;

    },
    // 删除图片
    handleDeleteImg(file) {
      this.form.filetype = ''
      this.form.fileName = ''
      this.form.fileUrl = ''
      const findex = this.fileUrlList.map(f => f.name).indexOf(file.name);
      if (findex > -1) {
        this.fileUrlList.splice(findex, 1);
        this.imageList.splice(findex, 1)
        this.$emit("input", this.listToString(this.fileUrlList));
      }
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${ file.name }？`);
    },

    // 上传成功回调
    handleUploadSuccess(res, file) {
      console.log(res);
      if (res.code === 200) {
        this.imageList.push( res.data.url)
        this.fileUrlId = res.data.id
        console.log(res.data.id);
        console.log(this.imageList);
        this.uploadList.push({name: res.data.url, url: this.baseUrl + res.data.url});
        this.uploadedSuccessfully();
      } else {
        this.number--;
        this.$modal.closeLoading();
        this.$modal.msgError(res.msg);
        this.$refs.FileUpload.handleRemove(file);
        this.uploadedSuccessfully();
      }
    },
    //上传成功
    uploadSuccess(info) {
      let appendixInfo = {
        file_name: info.data.fileName,
        appendix_id: info.data.id
      };
      this.appendixData.push(appendixInfo);
      this.data.file_list = this.appendixData;
      this.$message({message: '上传成功', type: 'success'});
    },
    // 上传前loading加载
    handleBeforeUpload(file) {
      let obj = {};
      obj = Object.assign({}, file.name.split('.').splice(-1))
      this.form.filetype = obj[0]
      this.form.fileName = file.name

    },
    // 上传失败
    handleUploadError() {
      this.$modal.msgError("上传文件失败，请重试");
      this.$modal.closeLoading();
    },
    // 文件个数超出
    handleExceed() {
      this.$modal.msgError(`上传文件数量不能超过1个!`);
    },
    // 上传结束处理
    uploadedSuccessfully() {
      if (this.number > 0 && this.uploadList.length === this.number) {
        this.fileUrlList = this.fileUrlList.concat(this.uploadList);
        this.uploadList = [];
        this.number = 0;
        this.$emit("input", this.listToString(this.fileUrlList));
        this.$modal.closeLoading();
      }
    },
    // 对象转成指定字符串分隔
    listToString(list, separator) {
      let strs = "";
      separator = separator || ",";
      for (let i in list) {
        if (list[i].url) {
          strs += list[i].url.replace(this.baseUrl, "") + separator;
        }
      }
      return strs != '' ? strs.substr(0, strs.length - 1) : '';
    },

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
