<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px"
             :rules="rules3"
    >
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司" ref="selectComp">
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
          placeholder="请输入员工工号"
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
          v-hasPermi="['human:personalSalary:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport(1)"
          v-hasPermi="['human:personalSalary:importSalary']"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-search"
          size="mini"
          @click="importQuery(1)"
          v-hasPermi="['human:personalSalary:importSalary']"
        >导入查询
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-printer"
          size="mini"
          @click="handleExport(1)"
          v-hasPermi="['human:personalSalary:importSalary']"
        >错误日志
        </el-button>
      </el-col>


      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport(2)"
          v-hasPermi="['human:personalSalary:importBank']"
        >银行账号导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-search"
          size="mini"
          @click="importQuery(2)"
          v-hasPermi="['human:personalSalary:importBank']"
        >银行账号导入查询
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-printer"
          size="mini"
          @click="handleExport(2)"
          v-hasPermi="['human:personalSalary:importBank']"
        >银行账号错误日志
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="personalSalaryList" @selection-change="handleSelectionChange" height="67vh">
      <el-table-column label="姓名" align="center" prop="empName"/>
      <el-table-column label="薪酬核定原因" align="center" prop="payCheckRea"/>
      <el-table-column label="生效日期" align="center" prop="effectDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effectDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="主岗位" align="center" prop="mainPostName" width="280"/>
      <el-table-column label="职位等级" align="center" prop="postLevel"/>
      <el-table-column label="止薪日期" align="center" prop="endPayDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endPayDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="输入人" align="center" prop="creator"/>
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-show="scope.row.isNew==='0'"
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleView(scope.row)"
            v-hasPermi="['human:personalSalary:query']"
          >详情
          </el-button>
          <el-button
            v-show="scope.row.isNew==='1'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:personalSalary:edit']"
          >修改
          </el-button>
          <el-button
            v-show="scope.row.isNew==='1'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEndPay(scope.row)"
            v-hasPermi="['human:personalSalary:edit']"
          >止薪
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

    <!-- 添加或修改薪资核定对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1400px" append-to-body class="customDialogStyle">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" label-position="left">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="姓名" prop="empName">
              <el-input v-model="form.empName" placeholder="" disabled style="width: 150px;">
                <el-button slot="append" icon="el-icon-search" @click="inputClick()" v-if="form.id===null"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="主岗位" prop="mainPostName">
              {{ form.mainPostName }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="职位等级" prop="postLevel">
              {{ form.postLevel }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="生效日期" prop="effectDate">
              <el-date-picker :clearable="false"
                              style="width: 150px;"
                              v-model="form.effectDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="薪酬核定原因" prop="payCheckReaId">
              <el-select v-model="form.payCheckReaId" placeholder="请选择" style="width: 150px;" ref="select1">
                <el-option
                  v-for="dict in salaryOptions.PayCheckRea"
                  :key="dict.id"
                  :label="dict.dicName"
                  :value="dict.id.toString()"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="公司名称" prop="compName">
              {{ form.compName }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="止薪日期" prop="endPayDate">
              <el-date-picker disabled
                              style="width: 150px;"
                              v-model="form.endPayDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否扣税" prop="istax">
              <el-radio-group v-model="form.istax">
                <el-radio
                  v-for="dict in salaryOptions.Hsyn"
                  :key="dict.id"
                  :label="dict.dicNo"
                >{{ dict.dicName }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="绩效类别" prop="achievementType">
              <el-select v-model="form.achievementType" placeholder="请选择绩效类别" style="width: 150px;">
                <el-option
                  v-for="dict in salaryOptions.AchievementType"
                  :key="dict.id"
                  :label="dict.dicName"
                  :value="dict.id.toString()"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="定薪原因说明" prop="payCheckDesc">
          <el-input v-model="form.payCheckDesc" type="textarea" placeholder="请输入内容" show-word-limit maxlength="1000"/>
        </el-form-item>

        <div style="margin-bottom: 10px;">
          薪资组成清单
        </div>
        <el-table
          :data="detailList"

          style="width: 100%;"
          :row-style="rowStyle"
          :show-header="false"
        >
          <el-table-column prop="payProName" label="第一列" width="180">
            <template v-slot="scope">
              {{ scope.row.serialNo }}{{ scope.row.payProName }}
            </template>
          </el-table-column>
          <el-table-column prop="twoTitle" label="第二列" width="180">
            <template v-slot="scope">
              <div v-show="scope.row.twoTitle!=='hide'">
                {{ scope.row.twoTitle }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="thrTitle" label="第三列">
            <template v-slot="scope">
              <el-radio-group v-model="scope.row.thrIdSta" v-show="scope.row.thrIsLov==='1'">
                <el-radio
                  v-for="dict in salaryOptions.Hsyn"
                  :key="dict.id"
                  :label="dict.dicNo"
                >{{ dict.dicName }}
                </el-radio>
              </el-radio-group>
              <div v-show="scope.row.thrIsLov==='0'">
                -
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="fourTitle" label="第四列"/>
          <el-table-column prop="fivTitle" label="第五列">
            <template v-slot="scope">
              <el-select v-model="scope.row.fivIdSta" placeholder="请选择" style="width: 100%"
                         v-show="scope.row.fivIsLov==='1'&&scope.row.thrIdSta==='1'" v-if="refresh"
              >
                <el-option
                  v-for="dict in payFormationOption[scope.row.lovConId]"
                  :key="dict.uuid"
                  :label="dict.standard"
                  :value="dict.uuid"
                />
              </el-select>
              <el-input v-model="scope.row.fivSta" placeholder="请输入" maxlength="50"
                        v-show="scope.row.fivIsLov==='0'&&scope.row.thrIdSta==='1'"
              />
            </template>
          </el-table-column>
          <el-table-column prop="sixTitle" label="第六列"></el-table-column>
          <el-table-column prop="sevTitle" label="第七列">
            <template v-slot="scope">
              <el-input v-model="scope.row.sevSta" placeholder="请输入" maxlength="50"
                        v-show="scope.row.thrIsLov==='0'"
              />
            </template>
          </el-table-column>
        </el-table>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="工资支付银行" prop="salBankId">
              <el-select v-model="form.salBankId" placeholder="请选择" style="width: 250px;" ref="select2">
                <el-option
                  v-for="dict in salaryBankList"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.uuid"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="工资支付账号" prop="salBankNo">
              <el-input v-model="form.salBankNo" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="输入人" prop="creator">
              {{ form.creatorNo }}-{{ form.creator }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="奖金支付银行" prop="bonBankId">
              <el-select v-model="form.bonBankId" placeholder="请选择" style="width: 250px;" ref="select3">
                <el-option
                  v-for="dict in salaryBankList"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.uuid"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="奖金支付账号" prop="bonBankNo">
              <el-input v-model="form.bonBankNo" placeholder="请输入"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="输入日期" prop="createDate">
              {{ form.createDate }}
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(0)">确定</el-button>
        <el-button type="primary" @click="submitForm(1)">核准定薪</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>

    <!-- 薪资核定详情对话框 -->
    <el-dialog :title="title" :visible.sync="openView" width="1400px" append-to-body class="customDialogStyle">
      <el-form ref="formView" :model="form" :rules="(ifEndPay===true)? rules2:null" label-width="100px"
               label-position="left"
      >
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="姓名" prop="empName">
              {{ form.empName }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="主岗位" prop="mainPostName">
              {{ form.mainPostName }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="职位等级" prop="postLevel">
              {{ form.postLevel }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="生效日期" prop="effectDate">
              {{ form.effectDate }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="薪酬核定原因" prop="payCheckReaId">
              <dict-tag-human :options="salaryOptions.PayCheckRea" :value="form.payCheckReaId" isId="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="公司名称" prop="compName">
              {{ form.compName }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="止薪日期" prop="endPayDate" v-if="!ifEndPay">
              {{ form.endPayDate }}
            </el-form-item>
            <el-form-item label="止薪日期" prop="endPayDate" v-if="ifEndPay">
              <el-date-picker
                style="width: 150px;"
                v-model="form.endPayDate"
                type="date"
                value-format="yyyy-MM-dd"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否扣税" prop="istax">
              <dict-tag-human :options="salaryOptions.Hsyn" :value="form.istax"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="绩效类别" prop="achievementType">
              <dict-tag-human :options="salaryOptions.AchievementType" :value="form.achievementType" isId="true"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="定薪原因说明" prop="payCheckDesc">
          {{ form.payCheckDesc }}
        </el-form-item>

        <div>
          薪资组成清单
        </div>
        <el-table
          :data="detailList"

          style="width: 100%;"
          :row-style="rowStyle"
          :show-header="false"
        >
          <el-table-column prop="payProName" label="第一列" width="180">
            <template v-slot="scope">
              {{ scope.row.serialNo }}{{ scope.row.payProName }}
            </template>
          </el-table-column>
          <el-table-column prop="twoTitle" label="第二列" width="180">
            <template v-slot="scope">
              <div v-show="scope.row.twoTitle!=='hide'">
                {{ scope.row.twoTitle }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="thrTitle" label="第三列">
            <template v-slot="scope">
              <el-radio-group v-model="scope.row.thrIdSta" v-show="scope.row.thrIsLov==='1'" disabled>
                <el-radio
                  v-for="dict in salaryOptions.Hsyn"
                  :key="dict.id"
                  :label="dict.dicNo"
                >{{ dict.dicName }}
                </el-radio>
              </el-radio-group>
              <div v-show="scope.row.thrIsLov==='0'">
                -
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="fourTitle" label="第四列"></el-table-column>
          <el-table-column prop="fivTitle" label="第五列">
            <template v-slot="scope">
              <el-select v-model="scope.row.fivIdSta" style="width: 100%"
                         v-show="scope.row.fivIsLov==='1'&&scope.row.thrIdSta==='1'" v-if="refresh" placeholder=""
                         disabled
              >
                <el-option
                  v-for="dict in payFormationOption[scope.row.lovConId]"
                  :key="dict.uuid"
                  :label="dict.standard"
                  :value="dict.uuid"
                />
              </el-select>
              <el-input v-model="scope.row.fivSta" maxlength="50" disabled
                        v-show="scope.row.fivIsLov==='0'&&scope.row.thrIdSta==='1'" placeholder=""
              />
            </template>
          </el-table-column>
          <el-table-column prop="sixTitle" label="第六列"></el-table-column>
          <el-table-column prop="sevTitle" label="第七列">
            <template v-slot="scope">
              <el-input v-model="scope.row.sevSta" maxlength="50" disabled
                        v-show="scope.row.thrIsLov==='0'"
              />
            </template>
          </el-table-column>
        </el-table>

        <el-row :gutter="20" style="margin-top: 30px;">
          <el-col :span="8">
            <el-form-item label="工资支付银行" prop="salBankId">
              <dict-tag-human :options="salaryBankList" :value="form.salBankId" idString="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="工资支付账号" prop="salBankNo">
              {{ form.salBankNo }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="输入人" prop="creator">
              {{ form.creatorNo }}-{{ form.creator }}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="奖金支付银行" prop="bonBankId">
              <dict-tag-human :options="salaryBankList" :value="form.bonBankId" idString="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="奖金支付账号" prop="bonBankNo">
              {{ form.bonBankNo }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="输入日期" prop="createDate">
              {{ form.createDate }}
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(0)" v-show="ifEndPay">确定</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>
    <select-user ref="select" @ok="getJobNumber"/>
    <!-- 薪资核定资料导入对话框 -->
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

    <!-- 薪资核定资料导入对话框 -->
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
            {{ upload.checkBoxMsg }}
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
import { getToken } from '@/utils/auth'
import {
  addPersonalSalary,
  delPersonalSalary,
  getPayFormation,
  getPayParamStandard,
  getPersonalSalary,
  getSalaryBank,
  listPersonalSalary,
  setEndPayDate,
  updatePersonalSalary
} from '@/api/human/hs/personalSalary'
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getSalaryOptions } from '@/api/human/hs/salaryBasis'
import selectUser from '@/views/components/human/selectUser/selectUser'
import DictTagHuman from '@/views/components/human/dictTag/humanBaseInfo'
import { queryImportNote } from '@/api/human/hs/importNote'

export default {
  name: 'PersonalSalary',
  components: { selectUser, DictTagHuman },
  data() {
    return {
      // 薪资核定资料导入参数
      upload: {
        // 是否显示弹出层
        open: false,
        // 弹出层标题
        title: '',
        // 多选框提示信息
        checkBoxMsg: '',
        // 是否禁用上传
        isUploading: false,
        // 是否新增
        updateSupport: 0,
        // 公司别
        compId: null,
        // 设置上传的请求头部
        headers: { Authorization: 'Bearer ' + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + '/human/personalSalary/importData'
      },
      //是否止薪
      ifEndPay: false,
      //下拉选单刷新
      refresh: true,
      //请求待响应数
      sendNumber: 0,
      //登录用户信息
      user: {},
      //公司数据
      companyList: [],
      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['PayCheckRea', 'AchievementType', 'Hsyn'],
        compId: null
      },
      //薪资选单选项列表
      salaryOptions: [],
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
      // 薪资核定表格数据
      personalSalaryList: [],
      // 公司银行数据
      salaryBankList: [],
      // 公司薪酬项目列表
      payFormationList: [],
      // 公司薪酬项目明细
      payFormationDetail: [],
      detailList: [],
      // 薪酬项目下拉选单
      payFormationOption: {},
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
        payType: '1'
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
        ]
      },
      //止薪表单校验
      rules2: {
        endPayDate: [
          { required: true, message: '止薪日期不能为空', trigger: 'change' }
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
    // 查询公司银行选单，薪酬项目
    getCompData(compId) {
      getSalaryBank(compId).then(response => {
        this.salaryBankList = response
      })
      getPayFormation(compId).then(response => {
        this.payFormationList = response
        this.setPayFormationDetail()
      })
    },
    //设置公司级薪资项目明细
    setPayFormationDetail(e) {
      this.payFormationList.forEach((value) => {
        let ifEnd = value.ifEnd
        let twoTitle = null
        let thrIsLov = null
        let thrSta = null
        let thrIdSta = null
        let fourTitle = null
        let fivIsLov = null
        let fivSta = null
        let fivIdSta = null
        let sixTitle = null
        let sevIsLov = null
        let sevSta = null
        let sevIdSta = null
        if (ifEnd === '1') {
          if (value.payProCode === '00') {
            twoTitle = '核定薪等'
            fivIsLov = '1'
            thrIsLov = '0'
            thrIdSta = '1'
            sixTitle = '基薪标准'
            sevIsLov = '0'
          } else {
            twoTitle = '是否享受'
            thrIsLov = '1'
            fivIsLov = value.isLov
          }
          fourTitle = value.staCon
          if (fivIsLov === '1') {
            let param = {
              compId: this.queryParams.compId,
              payParaId: value.defaultValue
            }
            if (!this.payFormationOption[value.lovConId]) {
              this.sendNumber++
              getPayParamStandard(param).then(response => {
                this.payFormationOption[value.lovConId] = response
                this.sendNumber--
                if (this.sendNumber === 0) {
                  this.refresh = false
                  this.refresh = true
                }
              })
            }
          }
        } else {
          twoTitle = 'hide'
        }
        let detail = {
          id: null,
          salaryId: null,
          payCheckRea: value.id,
          payProName: value.payProName,
          ifEnd,
          twoTitle,
          thrIsLov,
          thrSta,
          thrIdSta,
          fourTitle,
          fivIsLov,
          fivSta,
          fivIdSta,
          sixTitle,
          sevIsLov,
          sevSta,
          sevIdSta,
          serialNo: value.parents,
          postFlag: null,
          postEffectDate: null,
          msgId: null,
          istax: null,
          isEmpPro: null,
          lovConId: value.lovConId,
          isSalaryBasis: (value.payProCode === '00') ? '1' : '0',
          compId: null,
          empId: null,
          versionNo: null,
          isCheck: null,
          creator: null,
          creatorId: null,
          creatorNo: null,
          createDate: null
        }
        this.payFormationDetail.push(detail)
      })

    },
    //设置页面薪资项目明细列表
    setDetailList() {
      this.detailList = []
      this.payFormationDetail.forEach((value) => {
        this.detailList.push(value)
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openView = false
      this.reset()
    },
    //初始化数据
    initData() {
      this.user.empNo = this.$store.state.user.userInfo.userName
      this.user.empId = this.$store.state.user.userInfo.userId
      this.user.empName = this.$store.state.user.userInfo.nickName
      this.user.compId = this.$store.state.user.userInfo.compId
      this.queryParams.compId = this.user.compId
    },
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
    },
    //设置表单数据
    setForm() {
      if (this.form.id === null) {
        this.form.compId = this.queryParams.compId
        this.form.versionNo = 1
      }
      if (this.form.payCheckReaId) {
        this.form.payCheckRea = this.$refs.select1.selectedLabel
      }
      if (this.form.salBankId) {
        this.form.salBank = this.$refs.select2.selectedLabel
      }
      if (this.form.bonBankId) {
        this.form.bonBank = this.$refs.select3.selectedLabel
      }
      this.form.detailList = []
      this.detailList.forEach((value) => {
        this.form.detailList.push(value)
      })
      this.form.isCheck = '0'
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
    /** 查询薪资核定列表 */
    getList() {
      this.$refs['queryForm'].validate(valid => {
        if (valid) {
          this.loading = true
          listPersonalSalary(this.queryParams).then(response => {
            this.personalSalaryList = response.rows
            this.total = response.total
            this.loading = false
          })
        }
      })
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        payCheckReaId: null,
        payCheckRea: null,
        effectDate: null,
        mainPostName: null,
        postLevel: null,
        endPayDate: null,
        payCheckDesc: null,
        salBank: null,
        salBankId: null,
        salBankNo: null,
        bonBank: null,
        bonBankId: null,
        bonBankNo: null,
        compId: null,
        compName: null,
        postId: null,
        postFlag: null,
        postEffectDate: null,
        msgld: null,
        istax: null,
        empId: null,
        empNo: null,
        empName: null,
        versionNo: null,
        isCheck: null,
        achievementType: null,
        creator: null,
        creatorId: null,
        creatorNo: null,
        createDate: null,
        detailList: []
      }
      this.resetForm('form')
      this.resetForm('formView')
      this.ifEndPay = false
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.personalSalaryList = [];
      this.total = 0;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.setDetailList()
      this.open = true
      this.title = '添加薪资核定'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getPersonalSalary(id).then(response => {
        this.form = response.data
        this.detailList = this.form.detailList
        if (this.detailList.length === 0) {
          this.setDetailList()
        }
        this.open = true
        this.title = '修改薪资核定'
      })
    },
    /** 止薪操作 */
    handleEndPay(row) {
      this.reset()
      this.ifEndPay = true
      const id = row.id || this.ids
      getPersonalSalary(id).then(response => {
        this.form = response.data
        this.detailList = this.form.detailList
        this.openView = true
        this.title = '修改薪资核定止薪日期'
      })
    },
    /** 查看薪资核定详情 */
    handleView(row) {
      this.reset()
      const id = row.id || this.ids
      getPersonalSalary(id).then(response => {
        this.form = response.data
        this.detailList = this.form.detailList
        this.openView = true
        this.title = '薪资核定详情'
      })
    },
    /** 提交按钮 */
    submitForm(e) {
      if (!this.ifEndPay) {
        this.$refs['form'].validate(valid => {
          if (valid) {
            this.setForm()
            this.form.isCheck = e
            if (this.form.id != null) {
              updatePersonalSalary(this.form).then(response => {
                this.$modal.msgSuccess('修改成功')
                this.open = false
                this.getList()
              })
            } else {
              addPersonalSalary(this.form).then(response => {
                this.$modal.msgSuccess('新增成功')
                this.open = false
                this.getList()
              })
            }
          }
        })
      } else {
        this.$refs['formView'].validate(valid => {
          if (valid) {
            setEndPayDate(this.form).then(response => {
              this.$modal.msgSuccess('止薪成功')
              this.openView = false
              this.getList()
            })
          }
        })

      }
    },
    /** 导出按钮操作 */
    handleExport(e) {
      let payType
      let xlsxName
      if (e === 1) {
        payType = 1
        xlsxName = `薪资核定导入错误信息.xlsx`
      } else {
        payType = 3
        xlsxName = `银行账号导入错误信息.xlsx`
      }
      let param = {
        payType,
        compId: this.queryParams.compId
      }
      this.download('human/importError/export', {
        ...param
      }, xlsxName)
    },
    /** 导入按钮操作 */
    handleImport(e) {
      if (e === 1) {
        this.upload.title = '薪资核定导入'
        this.upload.checkBoxMsg = '是否新增'
        this.upload.url = process.env.VUE_APP_BASE_API + '/human/personalSalary/importData'
      } else {
        this.upload.title = '银行账号导入'
        this.upload.checkBoxMsg = '只修改最新一笔'
        this.upload.url = process.env.VUE_APP_BASE_API + '/human/personalSalary/importData/bankNo'
      }
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate(e) {
      if (e === 1) {
        this.download('human/personalSalary/importTemplate', {}, `薪资核定导入模板.xlsx`)
      } else {
        this.download('human/personalSalary/importTemplate/bankNo', {}, `银行账号导入模板.xlsx`)
      }
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
    submitFileForm(e) {
      this.upload.compId = this.queryParams.compId
      this.$nextTick(() => {
        this.$refs.upload.submit()
      })
    },
    //导入查询
    importQuery(e) {
      let payType = (e === 1) ? 1 : 3
      let param = {
        payType,
        compId: this.queryParams.compId
      }
      queryImportNote(param).then(response => {
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
    },
    //表格行样式
    rowStyle({ row, rowIndex }) {
      return {
        height: '60px'
      }
    }
  }
}
</script>
