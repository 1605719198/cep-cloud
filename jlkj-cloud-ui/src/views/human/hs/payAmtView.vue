<template>
  <!-- 查看项目详情 -->
  <el-dialog title="薪资应付详情" :visible.sync="visible" width="1050px" :close-on-click-modal="false">
    <el-form ref="form" :model="form" label-width="130px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="年月" prop="yyMonth">
            {{form.yyMonth}}
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
            <dict-tag-human-basis :options="attendenceOptions.operTimeType" :value="form.operTime"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="申请说明" prop="sendReason">
            {{form.sendReason}}
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
            :on-preview="downloadFile"
            :file-list="fileList">
          </el-upload>
        </el-form-item>
      </el-row>

      <el-divider content-position="center">薪资应付明细</el-divider>
      <el-table :data="humanHsAmtDetailList" show-summary ref="humanHsAmtDetail">
        <el-table-column label="银行" prop="salBank" width="180">
          <template slot-scope="scope">
            {{scope.row.salBank}}
          </template>
        </el-table-column>
        <el-table-column label="应发金额" prop="amtPayable" width="120">
          <template slot-scope="scope">
            {{scope.row.amtPayable}}
          </template>
        </el-table-column>
        <el-table-column label="实发金额" prop="amtNet" width="120">
          <template slot-scope="scope">
            {{scope.row.amtNet}}
          </template>
        </el-table-column>
        <el-table-column label="缓发金额" prop="amtDeferred" width="120">
          <template slot-scope="scope">
            {{scope.row.amtDeferred}}
          </template>
        </el-table-column>
        <el-table-column label="福利(餐补)" prop="amtBenefitMeal" width="120">
          <template slot-scope="scope">
            {{scope.row.amtBenefitMeal}}
          </template>
        </el-table-column>
        <el-table-column label="福利(住房补贴)" prop="amtBenefitHouse" width="120">
          <template slot-scope="scope">
            {{scope.row.amtBenefitHouse}}
          </template>
        </el-table-column>
        <el-table-column label="培训奖金" prop="amtTraining" width="120">
          <template slot-scope="scope">
            {{scope.row.amtTraining}}
          </template>
        </el-table-column>
      </el-table>
    </el-form>
  </el-dialog>
</template>

<script>
import { getPayAmt } from "@/api/human/hs/payAmt";
import { getSalaryOptions } from "@/api/human/hs/salaryBasis";
import {getToken} from "@/utils/auth";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import DictTagHumanBasis from "@/views/components/human/dictTag/humanBaseInfo";
export default {
  name: "payAmtView",
  components: { DictTagHumanBasis},
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
      form: {},
      // 薪资应付明细表格数据
      humanHsAmtDetailList: [],
      visible: false,
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
    }
  },
  created() {
    this.getCompanyList();
  },
  methods: {
    /** 数据初始化 */
    init(val,compId) {
      this.reset()
      this.compId = compId;
      getPayAmt(val).then(response => {
        this.form = response.data;
        this.humanHsAmtDetailList = response.data.humanHsAmtDetailList;
        this.open = true;
        this.visible = true
        // 附件清单
        if (response.data.fileUrl.length > 0) {
          this.fileIdList = response.data.fileUrl.split(",")
          this.fileList = response.data.fileList;
        }
      });
      this.getDisc();
    },
    //获取公司列表
    getCompanyList(){
      selectCompany().then(response=>{
        this.companyList = response.data
      })
    },
    /** 查询数据字典 */
    getDisc() {
      this.attendenceOptionType.compId = this.compId;
      getSalaryOptions(this.attendenceOptionType).then(response => {
        this.attendenceOptions = response.data
      })
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
      this.humanHsAmtDetailList = [];
      this.resetForm("form");
    },
    // 附件相关
    // 附件下载
    downloadFile(file) {
      window.open(process.env.VUE_APP_BASE_API + '/file/file/download/' + file.id);
    },
  }
}
</script>

<style scoped>

</style>
