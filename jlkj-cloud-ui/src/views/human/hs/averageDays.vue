<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别" ref="selectCompany">
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
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
          v-hasPermi="['human:averageDays:add']"
        >新增</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="averageDaysList" @selection-change="handleSelectionChange" height="67vh">
      <el-table-column label="公司" align="center" prop="compId">
        {{this.companyName}}
      </el-table-column>
      <el-table-column label="生效日期" align="center" prop="effectDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effectDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="月平均天数" align="center" prop="avgDayNum" />
      <el-table-column label="版本号" align="center" prop="versionNo" />
      <el-table-column label="输入人" align="center" prop="creator" />
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            :disabled="new Date(scope.row.effectDate)<new Date()"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:averageDays:edit']"
          >修改</el-button>
          <el-button
            :disabled="new Date(scope.row.effectDate)<new Date()"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:averageDays:remove']"
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

    <!-- 添加或修改各公司平均天数设定对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公司别" prop="compId">
              {{this.form.compName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="月平均天数" prop="avgDayNum">
              <el-input v-model="form.avgDayNum" placeholder="请输入月平均天数" type="number" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="版本号" prop="versionNo">
              <el-input v-model="form.versionNo" placeholder="" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生效日期" prop="effectDate">
              <el-date-picker clearable
                              style="width: 100%;"
                              v-model="form.effectDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择生效日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="备注" prop="comm">
              <el-input v-model="form.comm" type="textarea" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="输入人" prop="creator">
              {{form.creatorNo}}-{{form.creator}}
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
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getDateTime } from '@/api/human/hd/ahumanUtils'
import { listAverageDays, getAverageDays, delAverageDays, addAverageDays, updateAverageDays } from "@/api/human/hs/averageDays";

export default {
  name: "AverageDays",
  dicts: ['sys_normal_disable'],
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
      // 各公司平均天数设定表格数据
      averageDaysList: [],
      //公司数据
      companyList: [],
      //公司名称
      companyName: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      //登录人信息
      user: {},
    };
  },
  watch: {
    'queryParams.compId':{
      deep:true,
      immediate:false,
      handler:function(newV,preV){
        this.getList();
      }
    },
  },
  created() {
    this.initData();
    this.getCompanyList();
  },
  methods: {
    //初始化数据
    initData(){
      this.user.empNo = this.$store.state.user.userInfo.userName;
      this.user.empId = this.$store.state.user.userInfo.userId;
      this.user.empName = this.$store.state.user.userInfo.nickName;
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId
    },
    //获取公司列表
    getCompanyList() {
      selectCompany().then(response => {
        this.companyList = response.data
      })
    },
    //表单值设置
    setForm(e){
      this.form.creator = this.user.empName;
      this.form.creatorNo = this.user.empNo;
      this.form.creatorId = this.user.empId;
      this.form.createDate = getDateTime(0);
      if(e===0){
        this.form.compId = this.queryParams.compId;
        this.form.compName = this.$refs.selectCompany.selectedLabel;
      }
    },
    /** 查询各公司平均天数设定列表 */
    getList() {
      this.loading = true;
      listAverageDays(this.queryParams).then(response => {
        this.averageDaysList = response.rows;
        this.companyName = this.$refs.selectCompany.selectedLabel
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
        compName: null,
        effectDate: null,
        avgDayNum: null,
        versionNo: null,
        comm: null,
        resvAttr1: null,
        resvAttr2: null,
        creatorId: null,
        creatorNo: null,
        creator: null,
        createDate: null
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
      this.setForm(0);
      this.open = true;
      this.title = "添加各公司平均天数设定";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const uuid = row.uuid || this.ids
      getAverageDays(uuid).then(response => {
        this.form = response.data;
        this.form.compName = this.$refs.selectCompany.selectedLabel;
        this.open = true;
        this.title = "修改各公司平均天数设定";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uuid != null) {
            this.setForm();
            updateAverageDays(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAverageDays(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除所选数据项？').then(function() {
        return delAverageDays(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>
