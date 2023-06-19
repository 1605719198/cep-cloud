<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px" :rules="rules3" >
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别">
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="员工工号" prop="empNo">
        <el-input
          v-model="queryParams.empNo"
          placeholder="请输入"
        >
          <el-button slot="append" icon="el-icon-search" @click="inputClick()"></el-button>
        </el-input>
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
          v-hasPermi="['human:socialSecurityBasis:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['human:socialSecurityBasis:import']"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-search"
          size="mini"
          @click="importQuery"
          v-hasPermi="['human:socialSecurityBasis:import']"
        >导入查询
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-printer"
          size="mini"
          @click="handleExport"
          v-hasPermi="['human:personalSalary:import']"
        >错误日志
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="socialSecurityBasisList" height="67vh">
      <el-table-column label="姓名" align="center" prop="empName"/>
      <el-table-column label="社保核定原因" align="center" prop="insureRea"/>
      <el-table-column label="生效日期" align="center" prop="effectDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effectDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="主岗位名称" align="center" prop="mainPostName" width="280"/>
      <el-table-column label="职位等级" align="center" prop="posLevel"/>
      <el-table-column label="输入人" align="center" prop="creator"/>
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleView(scope.row)"
            v-hasPermi="['human:socialSecurityBasis:query']"
          >详情
          </el-button>
          <el-button
            v-if="scope.row.isNew!='0'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:socialSecurityBasis:edit']"
          >修改
          </el-button>
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

    <!-- 添加或修改社保公积金标准核定对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1600px" append-to-body class="customDialogStyle">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" label-position="left">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="姓名" prop="empName">
              <el-input v-model="form.empName" placeholder="" disabled>
                <el-button slot="append" icon="el-icon-search" @click="inputClick()" v-if="form.id===null"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="生效日期" prop="effectDate">
              <el-date-picker clearable
                              style="width: 100%"
                              v-model="form.effectDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择生效日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="社保核定原因" prop="insureReaId">
              <el-select v-model="form.insureReaId" placeholder="请选择" ref="select1">
                <el-option
                  v-for="dict in salaryOptions.InsureRea"
                  :key="dict.id"
                  :label="dict.dicName"
                  :value="dict.id.toString()"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="社保编号" prop="insureNo">
              <el-input v-model="form.insureNo" placeholder="请输入社保编号"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="主岗位" prop="mainPostName">
              {{ form.mainPostName }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="户籍所在地" prop="reg">
              {{ form.reg }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="参加工作日期" prop="joinWorkDate">
              {{ form.joinWorkDate }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="医保编号" prop="medicalInsuranceNo">
              <el-input v-model="form.medicalInsuranceNo" placeholder="请输入医保编号"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="出生日期" prop="birthDate">
              {{ form.birthDate }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="最高学历" prop="lastExper">
              <dict-tag-human :options="baseInfoData.qualification" :value="form.lastExper"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="户口性质" prop="regFea">
              <dict-tag-human :options="baseInfoData.natureOfAccount" :value="form.regFea"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="公司保险手册编号" prop="compInsureBookNo" label-width="125px">
              <el-input v-model="form.compInsureBookNo" placeholder="请输入编号"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="民族" prop="nation">
              <dict-tag-human :options="baseInfoData.nation" :value="form.nation"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="离职日期" prop="leaveEffectDate">
              {{ form.leaveEffectDate }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="长期工作地" prop="workAddr">
              {{ form.workAddr }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="住房公积金账号" prop="houseAccount" label-width="125px">
              <el-input v-model="form.houseAccount" placeholder="请输入账号"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="证件号码" prop="cerNo">
              {{ form.cerNo }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="职位等级" prop="posLevel">
              {{ form.posLevel }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="职工身份" prop="empIde">
              {{ form.empIde }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="社保缴费地类别" prop="payAreaTypeId" label-width="125px">
              <el-select v-model="form.payAreaTypeId" placeholder="请选择" ref="select2">
                <el-option
                  v-for="dict in salaryOptions.PayAreaType"
                  :key="dict.id"
                  :label="dict.dicName"
                  :value="dict.id.toString()"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="现居住地" prop="nowAddr">
              {{ form.nowAddr }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="用工性质" prop="empFea">
              {{ form.empFea }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="基本工资" prop="basicPay">
              {{ form.basicPay }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="联系电话" prop="mobPhone">
              {{ form.mobPhone }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="detailBody ">
        <div
          class="detailChild detailChildMax"
          v-for="(items,index) in this.socialSecurityDetail"
        >
          <div class="detailChild detailChildWidth1"
          >
            <div style="width: 110px;">{{ items.payProName }}</div>
            <div>{{ items.twoTitle }}</div>
            <div>
              <el-radio-group v-model="items.thrIdSta">
                <el-radio
                  v-for="dict in salaryOptions.HsYN"
                  :key="dict.id"
                  :label="dict.dicNo"
                >{{ dict.dicName }}
                </el-radio>
              </el-radio-group>
            </div>
          </div>
          <div class="detailChild detailChildWidth2"
               v-show="items.thrIdSta==='1'"
          >
            <div style="width: 130px;">
              {{ items.fourTitle }}
            </div>
            <div>
              <el-input v-model="items.fivSta" class="inputWidth" maxlength="100" type="number"></el-input>
            </div>
            <div>
              {{ items.sixTitle }}
            </div>
            <div>
              <el-select v-model="items.sevIdSta" placeholder="请选择缴费地" class="inputWidth">
                <el-option
                  v-for="dict in salaryOptions.SocialSecurity"
                  :key="dict.id"
                  :label="dict.dicName"
                  :value="dict.id.toString()"
                />
              </el-select>
            </div>
            <div>{{ items.eigTitle }}</div>
            <div>
              <el-input v-model="items.ninSta" class="inputWidth" disabled></el-input>
            </div>
            <div>{{ items.tenTitle }}</div>
            <div>
              <el-input v-model="items.eleSta" class="inputWidth" disabled></el-input>
            </div>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 社保公积金标准核定详情对话框 -->
    <el-dialog :title="title" :visible.sync="openView" width="1600px" append-to-body class="customDialogStyle">
      <el-form ref="formView" :model="form" label-width="100px" label-position="left">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="姓名" prop="empName">
              {{ form.empName }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="生效日期" prop="effectDate">
              {{ form.effectDate }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="社保核定原因" prop="insureRea">
              {{ form.insureRea }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="社保编号" prop="insureNo">
              {{ form.insureNo }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="主岗位" prop="mainPostName">
              {{ form.mainPostName }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="户籍所在地" prop="reg">
              {{ form.reg }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="参加工作日期" prop="joinWorkDate">
              {{ form.joinWorkDate }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="医保编号" prop="medicalInsuranceNo">
              {{ form.medicalInsuranceNo }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="出生日期" prop="birthDate">
              {{ form.birthDate }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="最高学历" prop="lastExper">
              <dict-tag-human :options="baseInfoData.qualification" :value="form.lastExper"/>
              <dict-tag></dict-tag>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="户口性质" prop="regFea">
              <dict-tag-human :options="baseInfoData.natureOfAccount" :value="form.regFea"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="公司保险手册编号" prop="compInsureBookNo" label-width="125px">
              {{ form.compInsureBookNo }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="民族" prop="nation">
              <dict-tag-human :options="baseInfoData.nation" :value="form.nation"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="离职日期" prop="leaveEffectDate">
              {{ form.leaveEffectDate }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="长期工作地" prop="workAddr">
              {{ form.workAddr }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="住房公积金账号" prop="houseAccount" label-width="125px">
              {{ form.houseAccount }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="证件号码" prop="cerNo">
              {{ form.cerNo }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="职位等级" prop="posLevel">
              {{ form.posLevel }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="职工身份" prop="empIde">
              {{ form.empIde }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="社保缴费地类别" prop="payAreaTypeId" label-width="125px">
              {{ form.payAreaType }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="现居住地" prop="nowAddr">
              {{ form.nowAddr }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="用工性质" prop="empFea">
              {{ form.empFea }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="基本工资" prop="basicPay">
              {{ form.basicPay }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="联系电话" prop="mobPhone">
              {{ form.mobPhone }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="detailBody ">
        <div
          class="detailChild detailChildMax"
          v-for="(items,index) in this.socialSecurityDetail"
        >
          <div class="detailChild detailChildWidth1"
          >
            <div style="width: 110px;">{{ items.payProName }}</div>
            <div>{{ items.twoTitle }}</div>
            <div>
              <el-radio-group v-model="items.thrIdSta" disabled>
                <el-radio
                  v-for="dict in salaryOptions.HsYN"
                  :key="dict.id"
                  :label="dict.dicNo"
                >{{ dict.dicName }}
                </el-radio>
              </el-radio-group>
            </div>
          </div>
          <div class="detailChild detailChildWidth2"
               v-show="items.thrIdSta==='1'"
          >
            <div style="width: 130px;">
              {{ items.fourTitle }}
            </div>
            <div>
              <el-input v-model="items.fivSta" class="inputWidth" maxlength="100" type="number" disabled></el-input>
            </div>
            <div>
              {{ items.sixTitle }}
            </div>
            <div>
              <el-select v-model="items.sevIdSta" placeholder="请选择缴费地" class="inputWidth" disabled>
                <el-option
                  v-for="dict in salaryOptions.SocialSecurity"
                  :key="dict.id"
                  :label="dict.dicName"
                  :value="dict.id.toString()"
                />
              </el-select>
            </div>
            <div>{{ items.eigTitle }}</div>
            <div>
              <el-input v-model="items.ninSta" class="inputWidth" disabled></el-input>
            </div>
            <div>{{ items.tenTitle }}</div>
            <div>
              <el-input v-model="items.eleSta" class="inputWidth" disabled></el-input>
            </div>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <select-user ref="select" @ok="getJobNumber"/>
    <!-- 社保公积金核定资料导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="450px" append-to-body class="customDialogStyle">
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport+ '&compId=' + upload.compId"
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
            <el-checkbox v-model="upload.updateSupport"/>
            是否新增版本
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                   @click="importTemplate"
          >下载模板
          </el-link>
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
import '@/assets/styles/humanStyles.scss'
import {
  listSocialSecurityBasis,
  getSocialSecurityBasis,
  delSocialSecurityBasis,
  addSocialSecurityBasis,
  updateSocialSecurityBasis
} from '@/api/human/hs/socialSecurityBasis'
import { getToken } from '@/utils/auth'
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getBaseInfo } from '@/api/human/hm/baseInfo'
import { getSalaryOptions, getSalaryDeepOptions } from '@/api/human/hs/salaryBasis'
import selectUser from '@/views/components/human/selectUser/selectUser'
import DictTagHuman from '@/views/components/human/dictTag/humanBaseInfo'
import { socialSecurity } from '@/api/human/hs/socialSecurity'
import { queryImportNote } from '@/api/human/hs/importNote'

export default {
  name: 'SocialSecurityBasis',
  components: { selectUser, DictTagHuman },
  data() {
    return {
      // 社保公积金资料导入参数
      upload: {
        // 是否显示弹出层（年收入维护资料导入）
        open: false,
        // 弹出层标题（年收入维护资料导入）
        title: '',
        // 是否禁用上传
        isUploading: false,
        // 是否新增
        updateSupport: 0,
        // 公司别
        compId: null,
        // 设置上传的请求头部
        headers: { Authorization: 'Bearer ' + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + '/human/socialSecurityBasis/importData'
      },
      //登录用户信息
      user: {},
      //公司数据
      companyList: [],
      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['SocialSecurity', 'InsureRea', 'PayAreaType', 'HsYN'],
        compId: null
      },
      //薪资选单选项列表
      salaryOptions: [],
      //选单列表
      baseInfo: {
        uuid: '',
        baseInfoList: ['nation', 'natureOfAccount', 'qualification']
      },
      //选单数据
      baseInfoData: [],
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
      // 社保公积金标准核定表格数据
      socialSecurityBasisList: [],
      // 社保公积金列表数据
      socialSecurityList: [],
      // 社保公积金核定明细列表数据
      socialSecurityDetail: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      openView: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        empNo: null,
        payType: '2'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        empName: [
          { required: true, message: '请选择员工', trigger: 'change' }
        ],
        effectDate: [
          { required: true, message: '生效日期不能为空', trigger: 'change' }
        ],
        insureNo: [
          { min: 3, max: 100, message: '长度在3到100个字符', trigger: 'blur' }
        ],
        medicalInsuranceNo: [
          { min: 3, max: 50, message: '长度在3到50个字符', trigger: 'blur' }
        ],
        compInsureBookNo: [
          { min: 3, max: 50, message: '长度在3到50个字符', trigger: 'blur' }
        ],
        houseAccount: [
          { min: 3, max: 50, message: '长度在3到50个字符', trigger: 'blur' }
        ]
      },
      //查询表单校验
      rules3: {
        empNo: [
          { required: true, message: '请输入员工工号', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.initData()
    this.getCompanyList()
    this.getDisc()
  },
  watch: {

  },
  methods: {
    //查询薪资选单
    getDisc() {
      getSalaryOptions(this.salaryOptionType).then(response => {
        this.salaryOptions = response.data
      })
    },
    //获取公司列表
    getCompanyList() {
      selectCompany().then(response => {
        this.companyList = response.data
      })
      getBaseInfo(this.baseInfo).then(response => {
        this.baseInfoData = response.data
      })
    },
    //初始化数据
    initData() {
      this.user.empNo = this.$store.state.user.userInfo.userName
      this.user.empId = this.$store.state.user.userInfo.userId
      this.user.empName = this.$store.state.user.userInfo.nickName
      this.user.compId = this.$store.state.user.userInfo.compId
      this.queryParams.compId = this.user.compId
    },
    //设置表单数据
    setForm(e) {
      if (e === 0) {
        this.form.compId = this.queryParams.compId
      }
      if (this.form.insureReaId) {
        this.form.insureRea = this.$refs.select1.selectedLabel
      }
      if (this.form.payAreaTypeId) {
        this.form.payAreaType = this.$refs.select2.selectedLabel
      }
    },
    //校验保险基数
    judge() {
      let errorMsg = null
      let stop = false
      this.socialSecurityDetail.forEach((value) => {
        if (value.thrIdSta === '1') {
          if (value.fivSta && stop !== true) {
            if (stop !== true && (parseInt(value.fivSta) > parseInt(value.eleSta) || parseInt(value.fivSta) < parseInt(value.ninSta))) {
              errorMsg = value.payProName + '基数大小不符合要求'
              stop = true
            }
          } else if (!value.fivSta && stop !== true) {
            errorMsg = value.payProName + '基数不可为空'
            stop = true
          }
        }
      })
      if (stop === true) {
        this.$modal.msgError(errorMsg)
        return false
      } else {
        return true
      }

    },
    //获取社保公积金项目
    getSocialSecurityList(e) {
      socialSecurity(this.queryParams.compId).then(response => {
        this.socialSecurityList = response.data
        this.setSocialSecurityDetail(e)
      })
    },
    setSocialSecurityDetail(e) {
      this.socialSecurityList.forEach((value) => {
        let detail = {
          id: null,
          socialSecurityId: null,
          payProId: value.salaryProjectId,
          payProName: value.payProName,
          payProCode: value.payProCode,
          twoTitle: '是否缴纳',
          thrIsLov: '0',
          thrSta: null,
          thrIdSta: '1',
          fourTitle: value.payProName + '基数',
          fivIsLov: '0',
          fivSta: '',
          fivIdSta: null,
          sixTitle: '缴费地',
          sevIsLov: '1',
          sevSta: null,
          sevIdSta: '',
          eigTitle: '该险种最低基数',
          ninIsLov: '0',
          ninSta: value.baseLl,
          ninIdSta: null,
          tenTitle: '该险种最高基数',
          eleIsLov: '0',
          eleSta: value.baseUcl,
          eleIdSta: null,
          seriNo: '',
          creator: null,
          creatorId: null,
          creatorNo: null,
          createDate: null
        }
        this.socialSecurityDetail.push(detail)
      })
      if (e === 1) {
        this.socialSecurityDetail.forEach((values, indexs) => {
          this.form.detailList.forEach((value, index) => {
            if (value.payProId === values.payProId) {
              this.socialSecurityDetail[indexs] = value
              this.form.detailList.splice(index, 1)
            }
          })
        })
      }
    },
    /** 查询社保公积金标准核定列表 */
    getList() {
      this.$refs['queryForm'].validate(valid => {
        if (valid) {
          this.loading = true
          listSocialSecurityBasis(this.queryParams).then(response => {
            this.socialSecurityBasisList = response.rows
            this.total = response.total
            this.loading = false
          })
        }
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openView = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        insureRea: null,
        insureReaId: null,
        insureNo: null,
        effectDate: null,
        mainPostName: null,
        posLevel: null,
        birthDate: null,
        lastExper: null,
        joinWorkDate: null,
        nation: null,
        reg: null,
        regFea: null,
        cerNo: null,
        entCorDate: null,
        nowAddr: null,
        mobPhone: null,
        basicPay: null,
        leaveEffectDate: null,
        empIde: null,
        workAddr: null,
        empFea: null,
        payAreaType: null,
        payAreaTypeId: null,
        compId: null,
        empName: null,
        empNo: null,
        empId: null,
        versionNo: null,
        msgid: null,
        medicalInsuranceNo: null,
        compInsureBookNo: null,
        houseAccount: null,
        creator: null,
        creatorId: null,
        creatorNo: null,
        createDate: null,
        detailList: null
      }
      this.resetForm('form')
      this.socialSecurityList = []
      this.socialSecurityDetail = []
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.socialSecurityBasisList = []
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.socialSecurityBasisList = [];
      this.total = 0;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.getSocialSecurityList()
      this.open = true
      this.title = '添加社保公积金标准核定'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getSocialSecurityBasis(id).then(response => {
        this.form = response.data
        this.getSocialSecurityList(1)
        this.open = true
        this.title = '修改社保公积金标准核定'
      })
    },
    /** 详情按钮操作*/
    handleView(row) {
      this.reset()
      const id = row.id || this.ids
      getSocialSecurityBasis(id).then(response => {
        this.form = response.data
        this.getSocialSecurityList(1)
        this.socialSecurityDetail.forEach((values, indexs) => {
          this.form.detailList.forEach((value, index) => {
            if (value.payProId === values.payProId) {
              values = value
              this.socialSecurityDetail[indexs] = value
              this.form.detailList.splice(index, 1)
            }
          })
        })
        this.openView = true
        this.title = '社保公积金标准核定详情'
      })
    },
    /** 提交按钮 */
    submitForm() {
      let judge = this.judge()
      if (judge) {
        this.$refs['form'].validate(valid => {
          if (valid) {
            this.setForm()
            this.form.detailList = this.socialSecurityDetail
            if (this.form.id != null) {
              updateSocialSecurityBasis(this.form).then(response => {
                this.$modal.msgSuccess('修改成功')
                this.open = false
                this.getList()
              }, error => {

              })
            } else {
              this.setForm(0)
              addSocialSecurityBasis(this.form).then(response => {
                this.$modal.msgSuccess('新增成功')
                this.open = false
                this.getList()
              })
            }
          }
        })
      }
    },
    /** 人员选单事件 */
    inputClick() {
      var queryParams = {
        compID: this.queryParams.compId,
        pageNum: 1,
        pageSize: 10
      }
      this.$refs.select.show(queryParams)
    },
    /** 获取工号 */
    getJobNumber(empNo, empName, compId, id) {
      if (this.open === true) {
        this.form.empNo = empNo
        this.form.empId = id
        this.form.empName = empName
      } else {
        this.queryParams.empNo = empNo
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/importError/export', {
        ...this.queryParams
      }, `社保公积金导入错误信息.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = '社保公积金导入'
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('human/socialSecurityBasis/importTemplate', {}, `社保导入模板.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$alert('<div style=\'overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;\'>' + response.msg + '</div>', '导入结果', { dangerouslyUseHTMLString: true })
      this.getList()
    },
    // 提交上传文件
    submitFileForm() {
      this.upload.compId = this.queryParams.compId
      this.$nextTick(() => {
        this.$refs.upload.submit()
      })
    },
    //导入查询
    importQuery() {
      queryImportNote(this.queryParams).then(response => {
        let result = response.data
        if (result) {
          if (result.status === '1') {
            this.$modal.msgWarning('最新一笔导入时间为' + result.createDate + '的数据正在导入中')
          } else if (result.status === '2') {
            this.$modal.msgError('最新一笔导入时间为' + result.createDate + '的数据导入失败')
          } else if (result.status === '3') {
            this.$modal.msgSuccess('最新一笔导入时间为' + result.createDate + '的数据已经导入完成')
          }
        } else {
          this.$modal.msgWarning('未查询到导入记录！')
        }
      })
    }
  }
}
</script>
<style scoped>
.detailBody {
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: start;
  width: 100%
}

.detailChild {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin-top: 3px;
  height: 40px;
}

.detailChildMax {
  width: 100%;
}

.detailChildWidth1 {
  width: 20%;
}

.detailChildWidth2 {
  width: 75%;
}

.inputWidth {
  width: 130px;
}
</style>
