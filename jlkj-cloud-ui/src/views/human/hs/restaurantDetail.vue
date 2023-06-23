<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px" :rules="rules">
      <el-form-item label="刷卡时间" prop="slotCardTimes">
        <el-date-picker
          v-model="queryParams.slotCardTimes"
          type="daterange"
          value-format="yyyy-MM-dd"
          range-separator="至"
          :clearable =false
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="restaurantDetailList" height="67vh">
      <el-table-column label="工号" align="center" prop="empNo" />
      <el-table-column label="姓名" align="center" prop="empName" />
      <el-table-column label="刷卡时间" align="center" prop="slotCardTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.slotCardTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="消费金额" align="center" prop="money" />
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
import { listRestaurantDetail } from "@/api/human/hs/restaurantDetail";

export default {
  name: "RestaurantDetail",
  data() {
    return {
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
      // 餐饮消费明细表格数据
      restaurantDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empNo: this.$store.state.user.userInfo.userName,
        slotCardTimes: null,
        startDate: null,
        endDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        slotCardTimes: [
          { required: true, message: '请选择时间范围', trigger: 'change' }
        ]
      }
    };
  },
  created() {
  },
  methods: {
    /** 查询餐饮消费明细列表 */
    getList() {
      this.$refs['queryForm'].validate(valid => {
        if (valid) {
          this.queryParams.startDate = this.queryParams.slotCardTimes[0];
          this.queryParams.endDate = this.queryParams.slotCardTimes[1];
          this.loading = true;
          listRestaurantDetail(this.queryParams).then(response => {
            this.restaurantDetailList = response.data.rows;
            this.total = response.data.total;
            this.loading = false;
          });
        }
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.slotCardTimes = null;
      this.total = 0;
      this.restaurantDetailList= [];
    },
  }
};
</script>
