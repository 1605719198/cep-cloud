<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司" prop="compId">
        <el-select :popper-append-to-body="false" v-model="queryParams.compId" placeholder="请选择公司别" clearable ref="selectCompany">
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="年月" prop="yyMonth">
        <el-date-picker
          v-model="queryParams.yyMonth"
          type="month"
          value-format="yyyy-MM"
          placeholder="选择年月">
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
          v-hasPermi="['human:payAmt:add']"
        >新增</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="payAmtList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />

      <el-table-column label="年月" align="center" prop="yyMonth" />
      <el-table-column label="申请日期" align="center" prop="sendDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sendDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="传票日期" align="center" prop="vchrDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.vchrDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="传票号码" align="center" prop="vchrno" />
      <el-table-column label="审批状态" align="center" prop="status" >
        <template v-slot="scope"> {{ statusType(scope.row.status) }} </template>
      </el-table-column>
      <el-table-column label="作业时点" align="center" >
        <template v-slot="scope">
          <dict-tag-human :options="attendenceOptions.operTimeType" :value="scope.row.operTime"/>
        </template>
      </el-table-column>
      <el-table-column label="申请人" align="center" prop="sendEmpNo" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleView(scope.row)"
            v-hasPermi="['human:payAmt:query']"
          >详情</el-button>
          <el-button  v-if="1==scope.row.status"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:payAmt:edit']"
          >修改</el-button>
          <el-button  v-if="1==scope.row.status"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:payAmt:remove']"
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

    <!-- 添加或修改薪资应付作业对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="850px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="年月" prop="yyMonth">
              <el-date-picker
                v-model="form.yyMonth"
                type="month"
                value-format="yyyy-MM"
                placeholder="选择年月">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="公司">
              <el-select v-model="form.compId" disabled>
                <el-option
                  v-for="dict in companyList"
                  :key="dict.compId"
                  :label="dict.companyName"
                  :value="dict.compId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作业时点" prop="operTime">
              <el-select v-model="form.operTime" placeholder="请选择作业时点" clearable>
                <el-option
                  v-for="dict in attendenceOptions.operTimeType"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                />
              </el-select>

            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="申请说明" prop="sendReason">
              <el-input v-model="form.sendReason" type="textarea" placeholder="请输入内容" maxlength="500" show-word-limit/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="传票号"> {{form.vchrno}} </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="申请人"> {{form.sendEmpNo}} </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="状态">  {{ statusType(form.status) }} </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="传票日期" prop="vchrDate">
              {{form.vchrDate}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="申请日期" prop="sendDate">{{form.sendDate}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="附件" prop="percentCurr">
            <el-upload
              multiple
              ref="imageUpload"
              :action="uploadUrl"
              :headers="headers"
              :show-file-list="true"
              :on-success="handleUploadSuccess"
              :before-upload="handleBeforeUpload"
              :on-error="handleUploadError"
              :on-preview="downloadFile"
              :on-remove="handleDeleteImg"
              :file-list="fileList">
              <el-button size="small" type="primary">点击上传</el-button>
              <span slot="tip" style="margin-left: 5px">（附件大小不可超过{{this.fileSize}} MB!）</span>
            </el-upload>
          </el-form-item>
        </el-row>
        <el-divider content-position="center">薪资应付明细</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="2">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddHumanHsAmtDetail">汇总</el-button>
          </el-col>

        </el-row>
        <el-table :data="humanHsAmtDetailList"
                  show-summary
                  :row-class-name="rowHumanHsAmtDetailIndex"
                  @selection-change="handleHumanHsAmtDetailSelectionChange"
                  ref="humanHsAmtDetail">
          <el-table-column label="银行" prop="salBank" align="center" width="400">
            <template slot-scope="scope">
              <el-input v-model="scope.row.salBankId" />
            </template>
          </el-table-column>
          <el-table-column label="应付金额" prop="amtPayable" align="center" width="400">
            <template slot-scope="scope">
              <el-input v-model="scope.row.amtPayable"  />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <pay-amt-view v-if="viewopen" ref="payAmtView"></pay-amt-view>
  </div>
</template>

<script>
import { listPayAmt, getPayAmt, delPayAmt, addPayAmt, updatePayAmt,getListPayAmtDetail } from "@/api/human/hs/payAmt";
import { getSalaryOptions } from "@/api/human/hs/salaryBasis";
import {getToken} from "@/utils/auth";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";
import payAmtView from "@/views/human/hs/payAmtView";
export default {
  name: "PayAmt",
  components: { DictTagHuman ,payAmtView},
  props: {
    // 上传文件大小限制
    fileSize: {
      type: Number,
      default: 50
    },
  },
  computed: {
    statusType() {
      return val => {
        switch (val) {
          case '1': return '建立'
          case '2': return '已呈送'
          case '3': return '已核准'
        }
      }
    }
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedHumanHsAmtDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 薪资应付作业表格数据
      payAmtList: [],
      // 薪资应付明细表格数据
      humanHsAmtDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: 'J00',
        yyMonth: null,
        operTime: null,
        sendEmpNo: null,
        sendDate: null,
        sendReason: null,
        status: null,
        vchrno: null,
        vchrDate: null,
        createName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        yyMonth: [
          { required: true, message: "年月不能为空", trigger: "blur" }
        ],
        operTime: [
          { required: true, message: "作用时点不能为空", trigger: "blur" }
        ],
        sendReason: [
          { required: true, message: "申请说明不能为空", trigger: "blur" }
        ],
      },
      // 查看详情弹框
      viewopen: false,
      //出勤选单类型查询
      attendenceOptionType: {
        id: '',
        optionsType: ['operTimeType']
      },
      // 公司列表
      companyList:[],
      //出勤选单选项列表
      attendenceOptions: {},
      // 附件相关
      number: 0,
      uploadList: [],
      fileIdList: [],
      fileList: [],
      // 上传的服务器地址
      baseUrl: process.env.VUE_APP_BASE_API,
      uploadUrl: process.env.VUE_APP_BASE_API + '/file/file/upload',
      headers: {
        Authorization: "Bearer " + getToken(),
      },

    };
  },
  created() {
    this.getCompanyList();
    //假别类型查询
    this.getList();
    this.getDisc();
  },
  methods: {
    //获取公司列表
    getCompanyList(){
      selectCompany().then(response=>{
        this.companyList = response.data
      })
    },
    /** 查询数据字典 */
    getDisc() {
      this.attendenceOptionType.compId = this.queryParams.compId;
      getSalaryOptions(this.attendenceOptionType).then(response => {
        this.attendenceOptions = response.data
      })
    },
    /** 详情按钮操作 */
    handleView(row) {
      this.viewopen = true
      const id = row.id || this.ids
      this.$nextTick(() => {
        this.$refs.payAmtView.init(id,this.queryParams.compId)
      })
    },
    /** 查询薪资应付作业列表 */
    getList() {
      this.loading = true;
      listPayAmt(this.queryParams).then(response => {
        this.payAmtList = response.rows;
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
        compId: null,
        yyMonth: null,
        operTime: null,
        sendEmpNo: null,
        sendDate: null,
        sendReason: null,
        fileUrl: null,
        status: null,
        vchrno: null,
        vchrDate: null,
        createBy: null,
        createName: null,
        createTime: null
      };
      this.fileList = [];
      this.humanHsAmtDetailList = [];
      this.resultList = [];
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
      this.title = "添加薪资应付作业";
      this.form.compId = this.queryParams.compId;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPayAmt(id).then(response => {
        this.form = response.data;
        this.humanHsAmtDetailList = response.data.humanHsAmtDetailList;
        this.open = true;
        this.title = "修改薪资应付作业";
        // 附件清单
        if (response.data.fileUrl.length > 0) {
          this.fileIdList = response.data.fileUrl.split(",")
          this.fileList = response.data.fileList;
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.humanHsAmtDetailList = this.humanHsAmtDetailList;
          if (this.form.id != null) {
            this.form.fileUrl = this.fileIdList.toString()
            updatePayAmt(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.fileUrl = this.fileIdList.toString()
            addPayAmt(this.form).then(response => {
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
      this.$modal.confirm('确认要删除吗？').then(function() {
        return delPayAmt(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 薪资应付明细序号 */
    rowHumanHsAmtDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 薪资应付明细添加按钮操作 */
    handleAddHumanHsAmtDetail() {
      //查询薪资计算明细 汇总list

      getListPayAmtDetail(this.form).then(response => {
        debugger
        this.humanHsAmtDetailList = response;
      });
      // //查询
      // let obj = {};
      // obj.compId = "";
      // obj.amtPayable = "";
      // obj.amtNet = "";
      // obj.amtDeferred = "";
      // obj.amtBenefitMeal = "";
      // obj.amtBenefitHouse = "";
      // obj.amtTraining = "";
      // obj.amtA = "";
      // obj.amtB = "";
      // obj.amtC = "";
      // obj.salBank = "";
      // obj.salBankId = "";
      // obj.salBankNo = "";
      // obj.bonBank = "";
      // obj.bonBankId = "";
      // obj.bonBankNo = "";
      // obj.createName = "";
      // this.humanHsAmtDetailList.push(obj);
    },
    /** 复选框选中数据 */
    handleHumanHsAmtDetailSelectionChange(selection) {
      this.checkedHumanHsAmtDetail = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/payAmt/export', {
        ...this.queryParams
      }, `payAmt_${new Date().getTime()}.xlsx`)
    },

    // 附件相关
    // 上传附件成功回调
    handleUploadSuccess(res, file) {
      if (res.code === 200) {
        this.fileIdList.push(res.data.id)
        this.uploadList.push({name: res.data.fileName, id: res.data.id});
        this.uploadedSuccessfully();
      } else {
        this.number--;
        this.$modal.closeLoading();
        this.$modal.msgError(res.msg);
        this.$refs.imageUpload.handleRemove(file);
        this.uploadedSuccessfully();
      }
    },
    // 上传附件结束处理
    uploadedSuccessfully() {
      if (this.number > 0 && this.uploadList.length === this.number) {
        this.fileList = this.fileList.concat(this.uploadList);
        this.uploadList = [];
        this.number = 0;
        this.$modal.closeLoading();
      }
    },
    // 上传前loading加载
    handleBeforeUpload(file) {
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize;
        if (!isLt) {
          this.$modal.msgError(`上传文件大小不能超过 ${this.fileSize} MB!`);
          return false;
        }
      }
      this.$modal.loading("正在上传文件，请稍候...");
      this.number++;
    },
    // 上传失败
    handleUploadError() {
      this.$modal.msgError("上传文件失败，请重试");
      this.$modal.closeLoading();
    },
    // 附件下载
    downloadFile(file) {
      window.open(process.env.VUE_APP_BASE_API + '/file/file/download/' + file.id);
    },
    // 删除上传附件
    handleDeleteImg(file) {
      const findex = this.fileList.map(f => f.name).indexOf(file.name);
      if (findex > -1) {
        this.fileList.splice(findex, 1);
        this.fileIdList.splice(this.fileIdList.indexOf(file.id), 1)
      }
    },
  }
};
</script>
