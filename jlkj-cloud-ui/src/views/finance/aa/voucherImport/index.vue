<template>
  <div class="app-container">
    <el-form :model="queryParams"
             :rules="rulesQuery" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="87px">
      <el-form-item label="凭证导入">
        <el-form-item label="会计公司" prop="companyId">
          <el-select v-model="queryParams.companyId" filterable placeholder="请输入会计公司">
            <el-option
              v-for="item in companyList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
             >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleImport">导入</el-button>
      </el-form-item>
    </el-form>
    <el-form ref="formDetail" :model="formDetail"  :rules="rulesDetail">
      <el-form-item label="凭证明细" prop="">
        <el-table v-loading="loading" :data="formDetail.detailList">
          <el-table-column label="凭证号" align="center" prop="voucherNo"   :show-overflow-tooltip='true'>
            <template slot-scope="scope">
              <el-button size="mini" @click="voucherNoClick(scope.row)" type="text">{{scope.row.voucherNo}}</el-button>
            </template>
          </el-table-column>
          <el-table-column label="凭证日期" align="center" prop="voucherDate"/>
          <el-table-column label="科目代码" align="center" prop="acctCode"/>
          <el-table-column label="科目名称" align="center" prop="acctName"  :show-overflow-tooltip='true'/>
          <el-table-column label="单位/币别" align="center" prop="crcyUnit"/>
          <el-table-column label="原币金额" align="center" prop="qtyFrnamt"/>
          <el-table-column label="借贷方向" align="center" prop="drcr"/>
          <el-table-column label="金额" align="center" prop="ntamt"/>
          <el-table-column label="错误原因" align="center" prop="errorReason"/>
        </el-table>
      </el-form-item>
    </el-form>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getListDetailList"
    />
    <!-- 更多条件查询弹窗 -->

    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" class="customDialogStyle"  append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url  +'/'+queryParams.companyId+'?updateSupport=' + upload.updateSupport"
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
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {selectVoucherTypeList} from "@/api/finance/aa/voucherType";
import {listVoucherDetailSelect} from "@/api/finance/aa/voucher";
import voucherQuery from "@/views/finance/aa/voucherQuery/voucherQuery";
import {getToken} from "@/utils/auth";
export default {
  name: "Voucher",
  components: {
    voucherQuery
  },
  dicts: ['aa_source_sys', 'aa_voucher_status'],
  data() {
    return {
      //本位按钮
      standardQueryIf:false,
      //外币按钮
      outsideQueryIf:false,
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
      // 凭证维护-主表格数据
      voucherList: [],
      // 凭证维护-凭证类型
      voucherTypeList: [],
      //凭证分录表格
      detailList: [],
      // 子表选中数据
      checkedTCapitalDetail: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: "",
        voucherDate: "",
        voucherNo: "",
        printCount: "",
        startDate: "",
        endDate: "",
        voucherType:"",
        status:"",
        apid:"",
        acctName:"",
        createBy:"",
        calNamea:"",
        calNameb:"",
        pastuserName:"",
        srlDesc:"",
        qtyFrnamt:"",
        voucherNoStart:"",
        voucherNoEnd:"",
        acctNameStart:"",
        acctNameEnd:"",
        calNameaStart:"",
        calNameaEnd:"",
        calNamebStart:"",
        calNamebEnd:"",
        qtyFrnamtStart:"",
        qtyFrnamtEnd:""
      },
      // 会计公司下拉选单
      companyList: [],
      // 表单参数
      form: {
      },
      // 表单参数
      formDetail: {
        detailList:[]
      },
      // 表单校验
      rules: {
        voucherType: [
          {required: true, message: '请输入类别', trigger: 'blur'}
        ],
        voucherDate: [
          {required: true, message: '请输入日期', trigger: 'blur'}
        ],
        pastuserName: [
          {required: true, message: '请输入经办人', trigger: 'blur'}
        ],
      },
      // 搜索框校验
      rulesQuery: {
        companyId: [
          {required: true, message: '请输入公司', trigger: 'blur'}
        ],

      },
      rulesDetail: {
      },
      voucherType:null,
      ntamtDDisabled:false,
      ntamtCDisabled:false,
      indexRow:'',
      // 探亲假资料导入参数
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
        url: process.env.VUE_APP_BASE_API + "/finance/voucher/importData"
      },

    };
  },
  created() {

    this.getVoucherTypeList();
    this.getListDetailList()
    this.getCompanyList()
  },
  methods: {
    voucherNoClick(row){
      this.$router.push({ name: 'Voucher' ,query:{ voucherNo: row.voucherNo,
          companyId:  row.companyId}});
    },
    /** 查询凭证维护-明细列表 */
    getListDetailList() {
      this.loading = false;

    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
      });
    },
    getVoucherTypeList() {
      selectVoucherTypeList().then(response => {
        this.voucherTypeList = response;
      });
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "凭证数据导入";
      console.log(this.queryParams.companyId);
      if(!this.queryParams.companyId){
        this.$message.error('公司不能为空');
        return
      }

      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('finance/voucher/importTemplate', {

      }, `voucher_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      console.log(response+"上传成功");
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.formDetail.detailList = response.data
/*      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
        response.msg + "</div>", "导入结果",
        { dangerouslyUseHTMLString: true });*/

    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }

  }
};
</script>
