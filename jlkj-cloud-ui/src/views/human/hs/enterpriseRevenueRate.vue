<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司" :popper-append-to-body="false">
          <el-option
            v-for="dict in companyList"
            :key="dict.deptCode"
            :label="dict.companyName"
            :value="dict.deptCode"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="年月" prop="month">
        <el-date-picker
          v-model="queryParams.month"
          type="month"
          value-format="yyyy-MM"
          placeholder="选择月">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['human:enterpriseRevenueRate:add']"
        >新增</el-button>
      </el-form-item>
    </el-form>


    <el-table v-loading="loading" :data="enterpriseRevenueRateList" >
      <el-table-column label="年月" align="center" prop="month" />
      <el-table-column label="企业效益系数" align="center" prop="enterpriseRevenueRate" />
      <el-table-column label="输入人" align="center" prop="creator" />
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column   label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button v-show="scope.row.id==uuis"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:enterpriseRevenueRate:edit']"
          >修改</el-button>
          <el-button v-show="scope.row.id==uuis"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:enterpriseRevenueRate:remove']"
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

    <!-- 添加或修改企业效益系数设定对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="年月" prop="month" label-width="100px" >
          <el-date-picker
            :disabled="form.id!=null"
            v-model="form.month"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="企业效益系数" prop="enterpriseRevenueRate" label-width="100px">
          <el-input v-model="form.enterpriseRevenueRate" placeholder="请输入企业效益系数" />
        </el-form-item>
        <el-form-item label="输入人" prop="creator">
          {{form.creator}}
        </el-form-item>
        <el-form-item label="输入日期" prop="createDate">
          {{form.createDate}}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listEnterpriseRevenueRate, getEnterpriseRevenueRate, delEnterpriseRevenueRate, addEnterpriseRevenueRate, updateEnterpriseRevenueRate } from "@/api/human/hs/enterpriseRevenueRate";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getDateTime} from "@/api/human/hd/ahumanUtils";

export default {
  name: "EnterpriseRevenueRate",
  dicts: ['sys_classtype'],
  data() {
    return {
      //公司列表
      companyList:[],
      //
      uuis:'',
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
      // 企业效益系数设定表格数据
      enterpriseRevenueRateList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
        month: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
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
    /** 查询企业效益系数设定列表 */
    getList() {
      this.loading = true;
      listEnterpriseRevenueRate(this.queryParams).then(response => {
        this.enterpriseRevenueRateList = response.rows;
        this.uuis=response.rows[0].id
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
        month: null,
        enterpriseRevenueRate: null,
        creator: null,
        creatorId: null,
        creatorNo: null,
        createDate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form.compId = this.queryParams.compId;
      this.form.creator = this.$store.state.user.userInfo.nickName ;
      this.form.createDate = getDateTime(1) ;
      this.form.creatorId = this.$store.state.user.name;
      this.open = true;
      this.title = "添加企业效益系数设定";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getEnterpriseRevenueRate(id).then(response => {
        this.form = response.data;
        this.form.creator = this.$store.state.user.userInfo.nickName ;
        this.form.createDate = getDateTime(1) ;
        this.form.creatorId = this.$store.state.user.name;
        this.open = true;
        this.title = "修改企业效益系数设定";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateEnterpriseRevenueRate(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addEnterpriseRevenueRate(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除此数据项？').then(function() {
        return delEnterpriseRevenueRate(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
  }
};
</script>
