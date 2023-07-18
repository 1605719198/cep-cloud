<template>
  <div class="app-container">
    <el-form :model="queryParams"
             :rules="rulesQuery" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="会计公司" prop="companyId">
        <el-select v-model="queryParams.companyId" filterable placeholder="请输入会计公司">
          <el-option
            v-for="item in companyList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年月" prop="yarMonth">
        <el-date-picker clearable
                        v-model="queryParams.yarMonth"
                        type="month"
                        value-format="yyyy-MM"
                        placeholder="请选择年月"
                        @change="dutyDateChange">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="部门编码" prop="deptN0Name">
        <el-input

          v-model="queryParams.deptN0Name"
          placeholder="请输入部门编码"
          clearable
          @keyup.enter.native="handleQuery"
        >
          <el-button slot="append" icon="el-icon-search" @click="deptNoButton"
          ></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="预算类别" prop="budgetType">
        <el-select v-model="queryParams.budgetType" placeholder="请选择预算类别" clearable>
          <el-option
            v-for="dict in dict.type.ao_budget_type"
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
          v-hasPermi="['ao:budget:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['ao:budget:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['ao:budget:remove']"
        >删除
        </el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['ao:budget:import']"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ao:budget:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="budgetList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="公司" align="center" prop="companyId">
        <template slot-scope="scope">
          <budget-tag :options="companyList" :value="scope.row.companyId"/>
        </template>
      </el-table-column>
      <el-table-column label="年度" align="center" prop="year" width="180">
      </el-table-column>
      <el-table-column label="部门" align="center" prop="deptNo">

        <template slot-scope="scope">
          <budget-tag :options="deptNoList" :value="scope.row.deptNo"/>
        </template>
      </el-table-column>
      <el-table-column label="预算类别" align="center" prop="budgetType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ao_budget_type" :value="scope.row.budgetType"/>
        </template>
      </el-table-column>
      <el-table-column label="预算总值" align="center" prop="janBudgetYear">

      </el-table-column>
      <el-table-column :label="queryParams.MonthQueryParams+'月预算'" align="center" prop="janBudget"/>
      <el-table-column label="上月预算值" align="center" prop="janBudgetLastMonth"/>
      <el-table-column label="下月预算值" align="center" prop="janBudgetNextMonth"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ao:budget:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ao:budget:remove']"
          >删除
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

    <!-- 添加或修改预算设置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司" prop="companyId">
              <budget-tag  v-if="companyIdTagIF" :options="companyList" :value="form.companyId"/>
              <el-select v-if="companyIdIF" v-model="form.companyId" filterable placeholder="请输入会计公司">
                <el-option
                  v-for="item in companyList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年度" prop="year">
              <span v-if="yearSpanIF" >{{ form.year }}</span>
              <el-date-picker clearable
                              v-if="yearIF"
                              v-model="form.year"
                              type="year"
                              value-format="yyyy"
                              placeholder="请选择年度"
                              @change="dutyYearChange":picker-options="dutyYearChange1" >

              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门编码" prop="deptN0Name">
              <span v-if="deptN0NameTagIF" >{{ form.deptN0Name }}</span>
<!--              <budget-tag  v-if="deptN0NameTagIF" :options="companyList" :value="form.companyId"/>-->
              <el-input v-if="deptN0NameIF" v-model="form.deptN0Name" placeholder="请输入部门编码" style="width:  218px;">
                <el-button slot="append" icon="el-icon-search" @click="deptNoButtonForm"
                ></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="预算类别" prop="budgetType">
              <dict-tag v-if="budgetTypeTagIF" :options="dict.type.ao_budget_type" :value="form.budgetType"/>
              <el-select v-if="budgetTypeIF" v-model="form.budgetType" placeholder="请选择预算类别">
                <el-option
                  v-for="dict in dict.type.ao_budget_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="center">预算明细</el-divider>
        <el-row >
          <el-col :span="6">
            <el-form-item label="1月" prop="januaryJanBudget">
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="2月" prop="februaryJanBudget">
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="3月" prop="marchJanBudget"></el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="4月" prop="aprilJanBudget">
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <span v-if="JanuaryJanBudgetSpanIF" style="text-align: center;display:block;width: 121px">{{ form.januaryJanBudget }}</span>
            <el-input v-model="form.januaryJanBudget" placeholder="请输入1月预算值"
                      style="width: 145px" v-if="JanuaryJanBudgetIF"
                      oninput="if(value<0)value=0" type="number"/>
          </el-col>
          <el-col :span="6">
            <span v-if="FebruaryJanBudgetSpanIF" style="text-align: center;display:block;width: 121px">{{ form.februaryJanBudget }}</span>
            <el-input v-model="form.februaryJanBudget" placeholder="请输入2月预算值"
                      style="width: 145px" v-if="FebruaryJanBudgetIF"
                      oninput="if(value<0)value=0" type="number"/>
          </el-col>
          <el-col :span="6">
            <span v-if="MarchJanBudgetSpanIF" style="text-align: center;display:block;width: 121px">{{ form.marchJanBudget }}</span>
            <el-input v-model="form.marchJanBudget" placeholder="请输入3月预算值"
                      style="width: 145px" v-if="MarchJanBudgetIF"
                      oninput="if(value<0)value=0" type="number"/>
          </el-col>
          <el-col :span="6">
            <span v-if="AprilJanBudgetSpanIF" style="text-align: center;display:block;width: 121px">{{ form.aprilJanBudget }}</span>
            <el-input v-model="form.aprilJanBudget" placeholder="请输入4月预算值"
                      style="width: 145px" v-if="AprilJanBudgetIF"
                      oninput="if(value<0)value=0" type="number"/>
          </el-col>
          <el-col :span="6">
            <el-form-item label="5月" prop="mayJanBudget"/>
          </el-col>
          <el-col :span="6">
            <el-form-item label="6月" prop="juneJanBudget"/>
          </el-col>
          <el-col :span="6">
            <el-form-item label="7月" prop="julyJanBudget"/>
          </el-col>
          <el-col :span="6">
            <el-form-item label="8月" prop="augustJanBudget"/>
          </el-col>
          <el-col :span="6">
            <span v-if="MayJanBudgetSpanIF" style="text-align: center;display:block;width: 121px">{{ form.mayJanBudget}}</span>
            <el-input v-model="form.mayJanBudget" placeholder="请输入5月预算值"
                      style="width: 145px" v-if="MayJanBudgetIF"
                      oninput="if(value<0)value=0" type="number"/>
          </el-col>
          <el-col :span="6">
            <span v-if="JuneJanBudgetSpanIF" style="text-align: center;display:block;width: 121px">{{ form.juneJanBudget}}</span>
            <el-input v-model="form.juneJanBudget" placeholder="请输入6月预算值"
                      style="width: 145px" v-if="JuneJanBudgetIF"
                      oninput="if(value<0)value=0" type="number"/>
          </el-col>
          <el-col :span="6">
            <span v-if="JulyJanBudgetSpanIF" style="text-align: center;display:block;width: 121px">{{ form.julyJanBudget }}</span>
            <el-input v-model="form.julyJanBudget" placeholder="请输入7月预算值"
                      style="width: 145px" v-if="JulyJanBudgetIF"
                      oninput="if(value<0)value=0" type="number"/>
          </el-col>
          <el-col :span="6">
            <span v-if="AugustJanBudgetSpanIF" style="text-align: center;display:block;width: 121px">{{ form.augustJanBudget }}</span>
            <el-input v-model="form.augustJanBudget" placeholder="请输入8月预算值"
                      style="width: 145px" v-if="AugustJanBudgetIF"
                      oninput="if(value<0)value=0" type="number"/>
          </el-col>
          <el-col :span="6">
            <el-form-item label="9月" prop="septemberJanBudget"/>
          </el-col>
          <el-col :span="6">
            <el-form-item label="10月" prop="octoberJanBudget"/>
          </el-col>
          <el-col :span="6">
            <el-form-item label="11月" prop="novemberJanBudget"/>
          </el-col>
          <el-col :span="6">
            <el-form-item label="12月" prop="decemberJanBudget"/>
          </el-col>
          <el-col :span="6">
            <span v-if="SeptemberJanBudgetSpanIF" style="text-align: center;display:block;width: 121px">{{ form.septemberJanBudget }}</span>
            <el-input v-model="form.septemberJanBudget" placeholder="请输入9月预算值"
                      style="width: 145px" v-if="SeptemberJanBudgetIF"
                      oninput="if(value<0)value=0" type="number"/>
          </el-col>
          <el-col :span="6">
            <span v-if="OctoberJanBudgetSpanIF" style="text-align: center;display:block;width: 121px">{{ form.octoberJanBudget }}</span>

            <el-input v-model="form.octoberJanBudget" placeholder="请输入10月预算值"
                      style="width: 145px" v-if="OctoberJanBudgetIF"
                      oninput="if(value<0)value=0" type="number"/>
          </el-col>
          <el-col :span="6">
            <span v-if="NovemberJanBudgetSpanIF" style="text-align: center;display:block;width: 121px">{{ form.novemberJanBudget }}</span>
            <el-input v-model="form.novemberJanBudget" placeholder="请输入11月预算值" style="width: 145px" v-if="NovemberJanBudgetIF">
              <el-input v-model="form.novemberJanBudget" placeholder="请输入11月预算值"
                        style="width: 145px" v-if="NovemberJanBudgetIF"
                        oninput="if(value<0)value=0" type="number"/>
            </el-input>
          </el-col>
          <el-col :span="6">
            <span v-if="DecemberJanBudgetSpanIF" style="text-align: center;display:block;width: 121px">{{ form.decemberJanBudget }}</span>
            <el-input v-model="form.decemberJanBudget" placeholder="请输入12月预算值"
                      style="width: 145px" v-if="DecemberJanBudgetIF"
                      oninput="if(value<0)value=0" type="number"/>

          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" class="customDialogStyle" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
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
            是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                   @click="importTemplate">下载模板
          </el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
    <selectOrgPerson ref="selectOrgPerson" @ok="getOrgPerson"/>
    <selectOrgPerson ref="selectOrgPersonForm" @ok="getOrgPersonForm"/>
  </div>
</template>

<script>
import {listBudget, getBudget, delBudget, addBudget, updateBudget} from "@/api/finance/ao/budget";
import selectOrgPerson from "@/views/components/human/selectUser/selectOrgPerson";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {getToken} from "@/utils/auth";
import budgetTag from "./budgetTag";
import {parseTime} from "@/utils/jlkj";

import { selectDeptName } from "@/api/human/hp/deptMaintenance";
export default {
  name: "Budget",
  components: {selectOrgPerson, budgetTag},
  dicts: ['ao_budget_type'],
  data() {
    // 额度验证，保存小数点后两位
    const validateAmt = (rule, value, callback) => {
      const pattern = /^[0-9-]+(\.[0-9]{1,2})?$/
      if (value) {
        if (!pattern.test(value)) {
          callback(new Error("请输入小数点≤2位的数据"))
        }
      }
      if (value > 100000000) {
        callback(new Error("数据限额最大值为1亿，请联系管理员修改！"))
      }
      callback()
    }
    return {

      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 删除数组
      deleteList:[],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 预算设置表格数据
      budgetList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        deptN0Name: null,
        deptNo: null,
        budgetType: null,
        yarMonth: null,
        MonthQueryParams: '当'
      },
      // 表单参数
      form: { id: null,
        companyId: null,
        year: parseTime(new Date(), '{y}') ,
        deptNo: null,
        budgetType: null,
        yarMonth: null,
        janBudget: null,
        status: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        updateName: null,
        januaryJanBudget: "0",
        februaryJanBudget: "0",
        marchJanBudget: "0",
        aprilJanBudget: "0",
        mayJanBudget: "0",
        juneJanBudget: "0",
        julyJanBudget: "0",
        augustJanBudget: "0",
        septemberJanBudget: "0",
        octoberJanBudget: "0",
        novemberJanBudget: "0",
        decemberJanBudget: "0",
        deptN0Name: "",
        deleteList:[]
      },
      // 表单校验
      rules: {
        companyId: [
          {required: true, message: '请输入公司', trigger: 'blur'}
        ],
        year: [
          {required: true, message: '请输入年度', trigger: 'blur'}
        ],
        deptN0Name: [
          {required: true, message: '请输入编码', trigger: 'blur'},
          {max: 64, message: '最大长度64个字符', trigger: 'blur'}
        ],
        budgetType: [
          {required: true, message: '请输入预算类别', trigger: 'blur'}
        ],
     januaryJanBudget: [
       { validator: validateAmt, trigger: 'blur' }
     ],
       februaryJanBudget: [
         { validator: validateAmt, trigger: 'blur' }
       ],
      marchJanBudget: [
        { validator: validateAmt, trigger: 'blur' }
      ],
        aprilJanBudget: [
          { validator: validateAmt, trigger: 'blur' }
        ],
    mayJanBudget: [
          { validator: validateAmt, trigger: 'blur' }
        ],
        juneJanBudget: [
          { validator: validateAmt, trigger: 'blur' }
        ],
        julyJanBudget: [
          { validator: validateAmt, trigger: 'blur' }
        ],
        augustJanBudget: [
          { validator: validateAmt, trigger: 'blur' }
        ],
        septemberJanBudget: [
          { validator: validateAmt, trigger: 'blur' }
        ],
        octoberJanBudget: [
          { validator: validateAmt, trigger: 'blur' }
        ],
        novemberJanBudget: [
          { validator: validateAmt, trigger: 'blur' }
        ],
        decemberJanBudget: [
          { validator: validateAmt, trigger: 'blur' }
        ],

      },
      // 会计公司下拉选单
      companyList: [],
      // 部门下拉选单
      deptNoList: [],
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
        url: process.env.VUE_APP_BASE_API + "/finance/budget/importData"
      },
      // 搜索框校验
      rulesQuery: {
        yarMonth: [
          {required: true, message: '请输入年月', trigger: 'blur'}
        ],
      },
      JanuaryJanBudgetIF: false,
      JanuaryJanBudgetSpanIF: false,
      FebruaryJanBudgetIF: false,
      FebruaryJanBudgetSpanIF: false,
      MarchJanBudgetIF: false,
      MarchJanBudgetSpanIF: false,
      AprilJanBudgetIF: false,
      AprilJanBudgetSpanIF: false,
      MayJanBudgetSpanIF: false,
      MayJanBudgetIF: false,
      JuneJanBudgetIF: false,
      JuneJanBudgetSpanIF: false,
      JulyJanBudgetIF: false,
      JulyJanBudgetSpanIF: false,
      AugustJanBudgetIF: false,
      AugustJanBudgetSpanIF: false,
      SeptemberJanBudgetIF: false,
      SeptemberJanBudgetSpanIF: false,
      OctoberJanBudgetIF: false,
      OctoberJanBudgetSpanIF: false,
      NovemberJanBudgetIF: false,
      NovemberJanBudgetSpanIF: false,
      DecemberJanBudgetIF: false,
      DecemberJanBudgetSpanIF: false,
      id:0,
      dutyYearChange1: {
        disabledDate(time) {
          // 禁止选择以前的时间
          return time.getTime() < Date.now();
        },
      },
      companyIdTagIF:false,
      companyIdIF:false,
      yearSpanIF:false,
      yearIF:false,
      deptN0NameTagIF:false,
      deptN0NameIF:false,
      budgetTypeTagIF:false,
      budgetTypeIF:false,
    };
  },
  created() {
    this.getCompanyList();
    this.getList();

  },
  methods: {
    //查询日期切换事件
    dutyDateChange(val) {
      if (!!val) {
        this.queryParams.MonthQueryParams = val.substring(5, 7)
      } else {
        this.queryParams.MonthQueryParams = '当'
      }

    },
    dutyYearChange(val) {
     this.form= {
       id: null,
       companyId: null,
       year:val,
       deptNo: null,
       budgetType: null,
       yarMonth: null,
       janBudget: null,
       status: null,
       createBy: null,
       createName: null,
       createTime: null,
       updateBy: null,
       updateTime: null,
       updateName: null,
       januaryJanBudget: "0",
       februaryJanBudget: "0",
       marchJanBudget: "0",
       aprilJanBudget: "0",
       mayJanBudget: "0",
       juneJanBudget: "0",
       julyJanBudget: "0",
       augustJanBudget: "0",
       septemberJanBudget: "0",
       octoberJanBudget: "0",
       novemberJanBudget: "0",
       decemberJanBudget: "0",
       deptN0Name: ""
     };
      if (val> parseTime(new Date(), '{y}-{m}').substring(0, 4) ) {
        this.selectInput("01")
      }else {
        this.selectInput(parseTime(new Date(), '{y}-{m}').substring(5, 7) )
      }

    },
    /** 公司别下拉选单 */
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
        this.queryParams.companyId = this.companyList[0].value
        this.getDeptName()
      });
    },
    /** 部门下拉选单 */
    getDeptName() {
      selectDeptName(this.queryParams.companyId).then(response => {
        this.deptNoList = response;
      });
    },
    /** 部门编码点击 */
    deptNoButton() {
      this.$refs.selectOrgPerson.show();
    },
    /** 部门编码点击 */
    deptNoButtonForm() {
      this.$refs.selectOrgPersonForm.show();
    },
    getOrgPerson(node, id, name, val) {
      this.queryParams.deptN0Name = node + "_" + name
      this.queryParams.deptNo = node
    },
    getOrgPersonForm(node, id, name) {
      this.form.deptN0Name = node + "_" + name
      this.form.deptNo = node
    },
    /** 查询预算设置列表 */
    getList() {
      this.loading = true;
      if (this.queryParams.yarMonth==null){
        this.queryParams.yarMonth = parseTime(new Date(), '{y}-{m}')
      }
      if (!this.queryParams.deptN0Name){
        this.queryParams.deptNo= this.queryParams.deptN0Name
      }

      this.queryParams.year=  this.queryParams.yarMonth.substring(0,4)
      listBudget(this.queryParams).then(response => {
        this.budgetList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      this.queryParams.MonthQueryParams = this.queryParams.yarMonth.substring(5,7)
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
        year: parseTime(new Date(), '{y}'),
        deptNo: null,
        budgetType: null,
        yarMonth: null,
        janBudget: null,
        status: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        updateName: null,
        januaryJanBudget: "0",
        februaryJanBudget: "0",
        marchJanBudget: "0",
        aprilJanBudget: "0",
        mayJanBudget: "0",
        juneJanBudget: "0",
        julyJanBudget: "0",
        augustJanBudget: "0",
        septemberJanBudget: "0",
        octoberJanBudget: "0",
        novemberJanBudget: "0",
        decemberJanBudget: "0",
        deptN0Name: ""
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      if(!!this.queryParams.yarMonth){
        this.queryParams.year =  this.queryParams.yarMonth.substring(0,4)
      }
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.getList();
        }
      });
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.resetForm("queryForm");
          if (!this.queryParams.companyId){
            this.queryParams.companyId = this.companyList[0].value
          }
          this.handleQuery();
        }
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.form.deleteList = selection
      this.deleteList= selection

      this.ids = selection.map(item => item.deptNo)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.companyIdTagIF=false;
        this.companyIdIF=true;
        this.yearSpanIF=false;
        this.yearIF=true;
        this.deptN0NameTagIF=false;
        this.deptN0NameIF=true;
        this.budgetTypeTagIF=false;
        this. budgetTypeIF=true;
      this.selectInput(parseTime(new Date(), '{y}-{m}').substring(5, 7) )
      this.title = "添加预算设置";
    },
    selectInput(val) {
      this.JanuaryJanBudgetIF = false;
      this.JanuaryJanBudgetSpanIF = false;
      this.FebruaryJanBudgetIF = false;
      this.FebruaryJanBudgetSpanIF = false;
      this.MarchJanBudgetIF = false;
      this.MarchJanBudgetSpanIF = false;
      this.AprilJanBudgetIF = false;
      this.AprilJanBudgetSpanIF = false;
      this.MayJanBudgetSpanIF = false;
      this.MayJanBudgetIF = false;
      this.JuneJanBudgetIF = false;
      this.JuneJanBudgetSpanIF = false;
      this.JulyJanBudgetIF = false;
      this.JulyJanBudgetSpanIF = false;
      this.AugustJanBudgetIF = false;
      this.AugustJanBudgetSpanIF = false;
      this.SeptemberJanBudgetIF = false;
      this.SeptemberJanBudgetSpanIF = false;
      this.OctoberJanBudgetIF = false;
      this.OctoberJanBudgetSpanIF = false;
      this.NovemberJanBudgetIF = false;
      this.NovemberJanBudgetSpanIF = false;
      this.DecemberJanBudgetIF = false;
      this.DecemberJanBudgetSpanIF = false;
      if (val == '01') {
        this.JanuaryJanBudgetIF = true;
        this.FebruaryJanBudgetIF = true;
        this.MarchJanBudgetIF = true;
        this.AprilJanBudgetIF = true;
        this.MayJanBudgetIF = true;
        this.JuneJanBudgetIF = true
        this.JulyJanBudgetIF = true
        this.AugustJanBudgetIF = true;
        this.SeptemberJanBudgetIF = true;
        this.OctoberJanBudgetIF = true;
        this.NovemberJanBudgetIF = true;
        this.DecemberJanBudgetIF = true;
      } else if (val == '02') {
        this.JanuaryJanBudgetSpanIF = true;
        this.FebruaryJanBudgetIF = true;
        this.MarchJanBudgetIF = true;
        this.AprilJanBudgetIF = true;
        this.MayJanBudgetIF = true;
        this.JuneJanBudgetIF = true
        this.JulyJanBudgetIF = true
        this.AugustJanBudgetIF = true;
        this.SeptemberJanBudgetIF = true;
        this.OctoberJanBudgetIF = true;
        this.NovemberJanBudgetIF = true;
        this.DecemberJanBudgetIF = true;
      } else if (val == '03') {
        this.JanuaryJanBudgetSpanIF = true;
        this.FebruaryJanBudgetSpanIF = true;
        this.MarchJanBudgetIF = true;
        this.AprilJanBudgetIF = true;
        this.MayJanBudgetIF = true;
        this.JuneJanBudgetIF = true
        this.JulyJanBudgetIF = true
        this.AugustJanBudgetIF = true;
        this.SeptemberJanBudgetIF = true;
        this.OctoberJanBudgetIF = true;
        this.NovemberJanBudgetIF = true;
        this.DecemberJanBudgetIF = true;
      } else if (val == '04') {
        this.JanuaryJanBudgetSpanIF = true;
        this.FebruaryJanBudgetSpanIF = true;
        this.MarchJanBudgetSpanIF = true;
        this.AprilJanBudgetIF = true;
        this.MayJanBudgetIF = true;
        this.JuneJanBudgetIF = true
        this.JulyJanBudgetIF = true
        this.AugustJanBudgetIF = true;
        this.SeptemberJanBudgetIF = true;
        this.OctoberJanBudgetIF = true;
        this.NovemberJanBudgetIF = true;
        this.DecemberJanBudgetIF = true;
      } else if (val == '05') {
        this.JanuaryJanBudgetSpanIF = true;
        this.FebruaryJanBudgetSpanIF = true;
        this.MarchJanBudgetSpanIF = true;
        this.AprilJanBudgetSpanIF = true;
        this.MayJanBudgetIF = true;
        this.JuneJanBudgetIF = true
        this.JulyJanBudgetIF = true
        this.AugustJanBudgetIF = true;
        this.SeptemberJanBudgetIF = true;
        this.OctoberJanBudgetIF = true;
        this.NovemberJanBudgetIF = true;
        this.DecemberJanBudgetIF = true;
      } else if (val == '06') {
        this.JanuaryJanBudgetSpanIF = true;
        this.FebruaryJanBudgetSpanIF = true;
        this.MarchJanBudgetSpanIF = true;
        this.AprilJanBudgetSpanIF = true;
        this.MayJanBudgetSpanIF = true;
        this.JuneJanBudgetIF = true
        this.JulyJanBudgetIF = true
        this.AugustJanBudgetIF = true;
        this.SeptemberJanBudgetIF = true;
        this.OctoberJanBudgetIF = true;
        this.NovemberJanBudgetIF = true;
        this.DecemberJanBudgetIF = true;
      } else if (val == '07') {
        this.JanuaryJanBudgetSpanIF = true;
        this.FebruaryJanBudgetSpanIF = true;
        this.MarchJanBudgetSpanIF = true;
        this.AprilJanBudgetSpanIF = true;
        this.MayJanBudgetSpanIF = true;
        this.JuneJanBudgetSpanIF = true
        this.JulyJanBudgetIF = true
        this.AugustJanBudgetIF = true;
        this.SeptemberJanBudgetIF = true;
        this.OctoberJanBudgetIF = true;
        this.NovemberJanBudgetIF = true;
        this.DecemberJanBudgetIF = true;
      } else if (val == '08') {
        this.JanuaryJanBudgetSpanIF = true;
        this.FebruaryJanBudgetSpanIF = true;
        this.MarchJanBudgetSpanIF = true;
        this.AprilJanBudgetSpanIF = true;
        this.MayJanBudgetSpanIF = true;
        this.JuneJanBudgetSpanIF = true
        this.JulyJanBudgetSpanIF = true
        this.AugustJanBudgetIF = true;
        this.SeptemberJanBudgetIF = true;
        this.OctoberJanBudgetIF = true;
        this.NovemberJanBudgetIF = true;
        this.DecemberJanBudgetIF = true;
      } else if (val == '09') {
        this.JanuaryJanBudgetSpanIF = true;
        this.FebruaryJanBudgetSpanIF = true;
        this.MarchJanBudgetSpanIF = true;
        this.AprilJanBudgetSpanIF = true;
        this.MayJanBudgetSpanIF = true;
        this.JuneJanBudgetSpanIF = true
        this.JulyJanBudgetSpanIF = true
        this.AugustJanBudgetSpanIF = true;
        this.SeptemberJanBudgetIF = true;
        this.OctoberJanBudgetIF = true;
        this.NovemberJanBudgetIF = true;
        this.DecemberJanBudgetIF = true;
      } else if (val == '10') {
        this.JanuaryJanBudgetSpanIF = true;
        this.FebruaryJanBudgetSpanIF = true;
        this.MarchJanBudgetSpanIF = true;
        this.AprilJanBudgetSpanIF = true;
        this.MayJanBudgetSpanIF = true;
        this.JuneJanBudgetSpanIF = true
        this.JulyJanBudgetSpanIF = true
        this.AugustJanBudgetSpanIF = true;
        this.SeptemberJanBudgetSpanIF = true;
        this.OctoberJanBudgetIF = true;
        this.NovemberJanBudgetIF = true;
        this.DecemberJanBudgetIF = true;
      } else if (val == '11') {
        this.JanuaryJanBudgetSpanIF = true;
        this.FebruaryJanBudgetSpanIF = true;
        this.MarchJanBudgetSpanIF = true;
        this.AprilJanBudgetSpanIF = true;
        this.MayJanBudgetSpanIF = true;
        this.JuneJanBudgetSpanIF = true
        this.JulyJanBudgetSpanIF = true
        this.AugustJanBudgetSpanIF = true;
        this.SeptemberJanBudgetSpanIF = true;
        this.OctoberJanBudgetSpanIF = true;
        this.NovemberJanBudgetIF = true;
        this.DecemberJanBudgetIF = true;
      } else if (val == '12') {
        this.JanuaryJanBudgetSpanIF = true;
        this.FebruaryJanBudgetSpanIF = true;
        this.MarchJanBudgetSpanIF = true;
        this.AprilJanBudgetSpanIF = true;
        this.MayJanBudgetSpanIF = true;
        this.JuneJanBudgetSpanIF = true
        this.JulyJanBudgetSpanIF = true
        this.AugustJanBudgetSpanIF = true;
        this.SeptemberJanBudgetSpanIF = true;
        this.OctoberJanBudgetSpanIF = true;
        this.NovemberJanBudgetSpanIF = true;
        this.DecemberJanBudgetIF = true;
      }

    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.selectInput(parseTime(new Date(), '{y}-{m}').substring(5, 7) )
      const id = row.id || this.ids
      getBudget(id).then(response => {
        this.form = response.data;
        this.form.deptN0Name =   this.form.deptNo
        this.open = true;
        this.companyIdTagIF=true;
        this.companyIdIF=false;
        this.yearSpanIF=true;
        this.yearIF=false;
        this.deptN0NameTagIF=true;
        this.deptN0NameIF=false;
        this.budgetTypeTagIF=true;
        this. budgetTypeIF=false;
        this.title = "修改预算设置";
      });

    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBudget(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBudget(this.form).then(response => {
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
      if (row.id!=null){
       this.deleteList[0]=row
      }
      const row1 =  this.deleteList
      const ids = row.deptNo || this.ids;
      this.$modal.confirm('是否确认删除预算设置编号为"' + ids + '"的数据项？').then(function () {
        return delBudget(row1);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/budget/export', {
        ...this.queryParams
      }, `budget_${new Date().getTime()}.xlsx`)
    },

    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "预算数据导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('finance/budget/importTemplate', {}, `budget_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
        response.msg + "</div>", "导入结果",
        {dangerouslyUseHTMLString: true});

    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }

  }
};
</script>
