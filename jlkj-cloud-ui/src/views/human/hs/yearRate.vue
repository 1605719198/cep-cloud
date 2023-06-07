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
      <el-form-item label="年份" prop="year">
        <el-date-picker
          v-model="queryParams.year"
          type="year"
          value-format="yyyy"
          placeholder="选择年">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-hasPermi="['human:yearRate:add']" type="primary" size="mini" plain @click="handleSave">保存</el-button>
      </el-form-item>
    </el-form>


    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row :gutter="20">
        <el-col :span="8">
      <el-form-item label="年份" prop="year" >
        <el-date-picker
          v-model="form.year"
          type="year"
          value-format="yyyy"
          placeholder="选择年">
        </el-date-picker>
      </el-form-item>
        </el-col>
        <el-col :span="4">
      <el-form-item label="年度奖金比例" prop="bonRate" label-width="100px">
        <el-input v-model="form.bonRate" placeholder="请输入年度奖金比例" />
      </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="8">
      <el-form-item label="输入人" prop="creator">
        {{form.creator}}
      </el-form-item>
        </el-col>
        <el-col :span="4">
      <el-form-item label="输入日期" prop="createDate">
        {{form.createDate}}
      </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-table v-loading="loading" :data="yearRateList" @selection-change="handleSelectionChange">
      <el-table-column label="年份" align="center" prop="year" />
      <el-table-column label="年度奖金比例" align="center" prop="bonRate" />
      <el-table-column label="输入人" align="center" prop="creator" />
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
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
  </div>
</template>

<script>
import { listYearRate, addYearRate, } from "@/api/human/hs/yearRate";
import {selectCompany} from "@/api/human/hp/deptMaintenance";

export default {
  name: "YearRate",
  data() {
    return {
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
      // 年度奖金比例设定表格数据
      yearRateList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
        year: null,
      },
      // 表单参数
      form: {
        year:null,
        bonRate:null,
        compId:null,
        creator: null,
        createDate: null
      },
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
    /** 查询年度奖金比例设定列表 */
    getList() {
      this.loading = true;
      listYearRate(this.queryParams).then(response => {
        this.yearRateList = response.rows;
        this.form = response.rows[0];
        this.total = response.total;
        this.loading = false;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        compId: null,
        year: null,
        bonRate: null,
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
    /** 保存按钮 */
    handleSave() {
      this.form.compId = this.queryParams.compId;
      this.$refs["form"].validate(valid => {
        if (valid) {
            addYearRate(this.form).then(response => {
              this.$modal.msgSuccess("保存成功");
              this.open = false;
              this.getList();
            });
        }
      });
    },
  }
};
</script>
