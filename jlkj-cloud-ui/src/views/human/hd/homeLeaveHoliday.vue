<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="companyId">
        <el-select v-model="queryParams.companyId" placeholder="请选择公司别" :popper-append-to-body="false">
          <el-option
            v-for="dict in companyList"
            :key="dict.deptCode"
            :label="dict.companyName"
            :value="dict.deptCode"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="工号" prop="empNo">
        <el-input v-model="queryParams.empNo" placeholder="请输入工号" :disabled="true">
          <el-button slot="append" icon="el-icon-search" @click="inputClick" clearable></el-button>
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
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['human:homeLeaveHoliday:import']"
        >导入
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="holidayList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="年度" align="center" prop="year" />
      <el-table-column label="工号" align="center" prop="empNo" />
      <el-table-column label="姓名" align="center" prop="empName" />
      <el-table-column label="岗位名称" align="center" prop="postName" />
      <el-table-column label="本年度可休探亲假天数" align="center" prop="dueHomeDays" />
      <el-table-column label="本年度年休假冲抵探亲假天数" align="center" prop="offHomeDays" />
      <el-table-column label="已休探亲假天数" align="center" prop="restHomeDays" />
      <el-table-column label="已休反探亲假次数" align="center" prop="restFhomeNums" />
      <el-table-column label="已休反探亲假天数" align="center" prop="restFhomeDays" />
      <el-table-column label="剩余探亲假天数" align="center" prop="preHomeDays" />
      <el-table-column label="已休次数" align="center" prop="restHomeNums" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.year === '2023'"
                     size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleUpdate(scope.row)"
          >修改</el-button>

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
    <select-user ref="select" @ok="getJobNumber"/>

    <!-- 添加或修改探亲假天数设定对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="200px">

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="人员编码" prop="empNo">
              <el-input v-model="form.empNo" placeholder="请输入人员编码" maxlength="10" :disabled="this.form.id!=null" class="inputInner"/>{{form.empName}}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="岗位" prop="postName">
              <el-input v-model="form.postName" placeholder="请输入岗位" maxlength="200" :disabled="this.form.id!=null"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="年度" prop="year">
              <el-input v-model="form.year" placeholder="请输入年度" maxlength="10" :disabled="this.form.id!=null"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="可休探亲假天数" prop="dueHomeDays">
              <el-input v-model="form.dueHomeDays" placeholder="请输入可休探亲假天数" type="number"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="本年度年休假冲抵探亲假天数" prop="offHomeDays">
              <el-input v-model="form.offHomeDays" placeholder="请输入本年度年休假冲抵探亲假天数" type="number" :disabled="this.form.id!=null"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="已休探亲假天数" prop="restHomeDays">
              <el-input v-model="form.restHomeDays" placeholder="请输入已休探亲假天数" type="number" :disabled="this.form.id!=null"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="已休探亲假次数" prop="restHomeNums">
              <el-input v-model="form.restHomeNums" placeholder="请输入已休探亲假次数" type="number" :disabled="this.form.id!=null"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="已休反探亲假天数" prop="restFhomeDays">
              <el-input v-model="form.restFhomeDays" placeholder="请输入已休反探亲假天数" type="number" :disabled="this.form.id!=null"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="已休反探亲假次数" prop="restFhomeDays">
              <el-input v-model="form.restFhomeDays" placeholder="请输入已休反探亲假次数" type="number" :disabled="this.form.id!=null"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="剩余探亲假天数" prop="preHomeDays">
              <el-input v-model="preHomeDays" placeholder="请输入剩余探亲假天数" type="number" :disabled="this.form.id!=null"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="输入人" prop="creator">
              {{ form.creatorId }} {{form.creator}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="输入日期" prop="createDate">
              {{form.createDate}}
            </el-form-item>
          </el-col>
        </el-row>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 探亲假资料导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
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

  </div>
</template>

<script>
import { listHoliday, getHoliday, delHoliday, addHoliday, updateHoliday } from "@/api/human/hd/homeLeaveHoliday";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import selectUser from "@/views/components/human/selectUser/selectUser";
import {getDateTime} from "@/api/human/hd/ahumanUtils";
import {getToken} from "@/utils/auth";

export default {
  name: "Holiday",
  dicts: ['sys_classtype'],
  components:{selectUser},
  data() {
    return {
      //年度
      year:2023,
      //用户公司别
      userCompId: this.$store.state.user.userInfo.compId,
      //公司列表
      companyList:[],
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
      // 探亲假天数设定表格数据
      holidayList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: this.$store.state.user.userInfo.compId,
        empNo: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        dueHomeDays:[
          {required:true,message:'不能为空',trigger: "change"}
        ]
      },
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
        url: process.env.VUE_APP_BASE_API + "/human/homeLeaveHoliday/importData"
      },
    };
  },
  computed:{
    preHomeDays(){
      return  parseInt(this.form.dueHomeDays) -  parseInt(this.form.restHomeDays);
    }
  },
  created() {
    this.getCompanyList();
  },
  methods: {
    /** 查询公司列表 */
    getCompanyList(){
      selectCompany().then(response =>{
          this.companyList = response.data
        }
      )
    },
    /** 查询探亲假天数设定列表 */
    getList() {
      this.loading = true;
      listHoliday(this.queryParams).then(response => {
        this.holidayList = response.rows;
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
        year: null,
        empNo: null,
        empId: null,
        empName: null,
        postName: null,
        postId: null,
        dueHomeDays: null,
        offHomeDays: null,
        restHomeDays: null,
        restFhomeNums: null,
        restFhomeDays: null,
        preHomeDays: null,
        restHomeNums: null,
        creator: null,
        creatorId: null,
        createDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery(e) {
      if(e===0){
        this.queryParams.pageNum = 1;
        this.getList();
      }else{
        if(this.judgeQuery()){
          this.queryParams.pageNum = 1;
          this.getList();
        }
      }
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "探亲假资料导入";
      this.upload.open = true;
    },
    /** 查询条件判定 */
    judgeQuery(){
      if(this.queryParams.empNo===null||this.queryParams.empNo===''){
        this.$modal.msgError("请输入工号")
        return false;
      }else{
        return true;
      }
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery(0);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getHoliday(id).then(response => {
        this.form = response.data;
        this.form.creator = this.$store.state.user.userInfo.nickName ;
        this.form.createDate = getDateTime(1) ;
        this.form.creatorId = this.$store.state.user.name;
        this.open = true;
        this.title = "修改探亲假天数设定";
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.form.preHomeDays=this.preHomeDays
            updateHoliday(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.preHomeDays=this.preHomeDays
            addHoliday(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 工号点击事件 */
    inputClick() {
      this.$refs.select.show();
    },
    /** 获取工号 */
    getJobNumber(empId,userName,compId) {
      this.queryParams.empNo = empId;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('human/homeLeaveHoliday/importTemplate', {}, `homeLeaveHoliday_template_${new Date().getTime()}.xlsx`)
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
  }
};
</script>
<style scoped>
.inputInner {
  width: 70%;
}
/deep/.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>

