<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" :rules="rules" v-show="showSearch" label-width="80px">
          <el-form-item label="公司别" prop="compId">
            <el-select v-model="queryParams.compId" @change="changeCompId">
              <el-option
                v-for="dict in companyName"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="薪资类别" prop="payType">
            <el-select v-model="queryParams.payType" @change="changePayType">
              <el-option
                v-for="dict in salaryAddData.payType"
                :key="dict.dicNo"
                :label="dict.dicName"
                :value="dict.dicNo"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="员工编号" prop="empId">
            <el-input
              v-model="queryParams.empId"
              placeholder="请输入员工编号"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="薪资时间" prop="date">
            <el-date-picker
              v-model="queryParams.date"
              value-format="yyyy-MM"
              type="month"
              placeholder="选择月">
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
              @click="handleSave"
              :disabled="payTypeSwitch"
              v-hasPermi="['human:salaryAdd:add']"
            >保存</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="payTypeSwitch"
              @click="handleDelete"
              v-hasPermi="['human:salaryAdd:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['human:salaryAdd:import']"
            >导入
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-search"
              size="mini"
              @click="handleQueryStatus"
            >查询导入状态</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-download"
              size="mini"
              @click="downloadErrorList"
            >下载导入错误清单</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-download"
              size="mini"
              @click="importTemplate"
            >下载追补扣导入模板</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport1"
              v-hasPermi="['human:salaryAdd:import']"
            >专项附加追补导入</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-form ref="form" :model="form" :rules="rules1">
          <el-table v-loading="loading" :data="form.salaryAddList" :row-class-name="tableRowClassName" @row-click="addLine" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="工号/姓名" align="center" prop="empId">
              <template v-slot="scope">
                <el-form-item>
                  <el-input v-model="scope.row.empId" placeholder="请输入工号" style="width: 70%" disabled>
                    <el-button slot="append" icon="el-icon-search" @click="inputClick()"></el-button>
                  </el-input>
                  {{scope.row.userName}}
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="薪资表项目" align="center" prop="payTabCode">
              <template v-slot="scope">
                <el-form-item>
                  <el-select v-model="scope.row.payTabCode" placeholder="请选择">
                    <el-option
                      v-for="dict in payTypeList"
                      :key="dict.uuid"
                      :label="dict.payProName"
                      :value="dict.payProId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="金额" align="center" prop="mon">
              <template v-slot="scope">
                <el-form-item :prop="'salaryAddList.' + scope.$index + '.mon'" :rules="rules1.mon">
                  <el-input v-model="scope.row.mon" placeholder="请输入金额"/>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="备注" align="center" prop="comm">
              <template v-slot="scope">
                <el-form-item>
                  <el-input v-model="scope.row.comm" placeholder="请输入备注"/>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column label="薪资类别" align="center" prop="payType" />
            <el-table-column label="输入人" align="center" prop="creator" />
            <el-table-column label="输入日期" align="center" prop="createDate" width="180">
              <template v-slot="scope">
                <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
          </el-table>
        </el-form>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
        <select-user ref="select" @ok="getJobNumber"/>

        <!-- 薪资项目追补导入对话框 -->
        <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
          <el-upload
            ref="upload"
            :limit="1"
            accept=".xlsx, .xls"
            :headers="upload.headers"
            :action="upload.url + '?updateSupport=' + upload.updateSupport + '?compId=' + queryParams.compId + '?date=' + queryParams.date"
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
            </div>
          </el-upload>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitFileForm">确 定</el-button>
            <el-button @click="upload.open = false">取 消</el-button>
          </div>
        </el-dialog>

        <!-- 专项附加导入对话框 -->
        <el-dialog :title="upload1.title" :visible.sync="upload1.open" width="400px" append-to-body>
          <el-upload
            ref="upload1"
            :limit="1"
            accept=".xlsx, .xls"
            :headers="upload1.headers"
            :action="upload1.url + '?updateSupport=' + upload1.updateSupport + '?compId=' + queryParams.compId + '?date=' + queryParams.date"
            :disabled="upload1.isUploading"
            :on-progress="handleFileUploadProgress1"
            :on-success="handleFileSuccess1"
            :auto-upload="false"
            drag
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip text-center" slot="tip">
              <div class="el-upload__tip" slot="tip">
                <el-checkbox v-model="upload1.updateSupport"/>
                是否更新已经存在的用户数据
              </div>
              <span>仅允许导入xls、xlsx格式文件。</span>
            </div>
          </el-upload>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitFileForm1">确 定</el-button>
            <el-button @click="upload1.open = false">取 消</el-button>
          </div>
        </el-dialog>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {listSalaryAdd, delSalaryAdd, addSalaryAdd, queryStatus} from "@/api/human/hs/salaryAdd";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getSalaryOptions} from "@/api/human/hs/salaryBasis";
import selectUser from "@/views/components/human/selectUser/selectUser";
import {listPayFormation} from "@/api/human/hs/payFormation";
import {getToken} from "@/utils/auth";

export default {
  name: "SalaryAdd",
  components: {selectUser},
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 薪资类别为全部时禁用
      payTypeSwitch: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        year: null,
        month: null,
        payType: null,
        compId: null,
        empId: null,
        date: null
      },
      // 表单参数
      form: {
        // 薪资项目金额追补扣表格数据
        salaryAddList: [
          {
            empId: undefined,
            mon: 0
          }
        ],
      },
      // 表单校验
      rules: {
        compId: [
          { required: true, message: "公司别不能为空", trigger: "blur" }
        ],
        date: [
          { required: true, message: "薪资时间不能为空", trigger: "blur" }
        ]
      },
      rules1: {
        mon: [
          { pattern: /^\d+$|^\d+[.]?\d+$/, message: "请输入数字", trigger: "change"},
        ]
      },
      // 公司别数据
      companyName: [],
      salaryAddData: [],
      //薪资选单类型查询
      salaryOptionType: {
        id: '19',
        optionsType: [
          'payType'],
        compId:null,
      },
      //登录人信息
      user: {},
      rowIndex: 0,
      index: 0,
      payTypeList: [],
      // 薪资项目追补导入参数
      upload: {
        // 是否显示弹出层（薪资项目追补资料导入）
        open: false,
        // 弹出层标题（薪资项目追补资料导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/human/salaryAdd/importData"
      },
      upload1: {
        // 是否显示弹出层（专项附加资料导入）
        open: false,
        // 弹出层标题（专项附加资料导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/human/salaryAdd/importData1"
      },
    };
  },
  created() {
    this.initData();
    selectCompany().then(res => {
      this.companyName = res.data
    })
    getSalaryOptions(this.salaryOptionType).then(response =>{
      this.salaryAddData = response.data
    })
  },
  methods: {
    //初始化数据
    initData(){
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId
      listPayFormation(this.queryParams).then(response => {
        this.payTypeList = response.data.rows;
      });
    },
    /** 查询薪资项目金额追补扣列表 */
    getList() {
      this.loading = true;
      if (this.queryParams.date != null) {
        this.queryParams.year = this.queryParams.date.substr(0,4)
        this.queryParams.month = this.queryParams.date.substr(5,2)
      }
      listSalaryAdd(this.queryParams).then(response => {
        this.form.salaryAddList = response.data.rows;
        if (this.form.salaryAddList.length === 0) {
          this.form.salaryAddList = [
            {
              empId: undefined,
              mon: 0
            }
          ]
        }
        this.total = response.data.total;
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
        year: null,
        month: null,
        payTableId: null,
        payTabCode: null,
        payTabName: null,
        mon: null,
        comm: null,
        payType: null,
        compId: null,
        empId: null,
        creator: null,
        creatorId: null,
        createDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.getList();
        }
      });
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
    },
    /** 保存按钮操作 */
    handleSave() {
      this.$refs['queryForm'].validate(valid => {
        this.form.compId = this.queryParams.compId
        this.form.payType = this.queryParams.payType
        if (this.queryParams.date != null) {
          this.form.year = this.queryParams.date.substr(0,4)
          this.form.month = this.queryParams.date.substr(5,2)
        }
        this.form.salaryAddList.map(value => {
          value.compId = this.queryParams.compId
          value.payType = this.queryParams.payType
          value.year = this.queryParams.date.substr(0,4)
          value.month = this.queryParams.date.substr(5,2)
        })
        addSalaryAdd(this.form).then(res => {
          this.$modal.msgSuccess("新增成功");
          this.getList();
        })
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除薪资项目金额追补扣编号为"' + ids + '"的数据项？').then(function() {
        return delSalaryAdd(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    tableRowClassName({row, rowIndex}) {
      // 把每一行的索引放到row里
      row.index = rowIndex
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      this.rowIndex = row.index
      if (this.form.salaryAddList.length === row.index + 1) {
        const newLine = {
          empId: undefined,
          mon: 0
        }
        this.index++
        this.form.salaryAddList.push(newLine)
      }
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(val,userName) {
      this.form.salaryAddList[this.rowIndex].empId = val
      this.form.salaryAddList[this.rowIndex].userName = userName
    },
    changePayType(val) {
      if (val === '6') {
        this.payTypeSwitch = true
      } else {
        this.payTypeSwitch = false
      }
    },
    changeCompId() {
      listPayFormation(this.queryParams).then(response => {
        this.payTypeList = response.data.rows;
      });
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "薪资项目追补导入";
      this.upload.open = true;
    },
    /** 导入按钮操作 */
    handleImport1() {
      this.upload.title = "专项附加导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('human/salaryAdd/importTemplate', {}, `salaryAdd_template_${new Date().getTime()}.xlsx`)
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
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    // 文件上传中处理
    handleFileUploadProgress1(event, file, fileList) {
      this.upload1.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess1(response, file, fileList) {
      this.upload1.open = false;
      this.upload1.isUploading = false;
      this.$refs.upload1.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
      this.getList();
    },
    // 提交上传文件
    submitFileForm1() {
      this.$refs.upload1.submit();
    },
    handleQueryStatus() {
      if (this.queryParams.date != null) {
        this.queryParams.month = this.queryParams.date.substr(6,1)
      }
      queryStatus(this.queryParams).then(res =>{
        if (res.data[0].status === '1'){
          this.$modal.msgWarning("导入中");
        } else if (res.data[0].status === '2') {
          this.$modal.msgError("导入失败");
        } else {
          this.$modal.msgSuccess("导入成功");
        }
      })
    },
    /** 下载导入错误清单 */
    downloadErrorList(){
      this.download('human/salaryAdd/export', {
        ...this.queryParams
      }, `salaryAddError_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
