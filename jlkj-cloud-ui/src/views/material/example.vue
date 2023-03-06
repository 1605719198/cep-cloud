<template>
  <div class="app-container">
    <el-form :model="query" ref="query" :inline="true"  label-width="68px">
      <el-form-item prop="queryDate" label="日期">
        <el-date-picker v-model="query.queryDate" type="daterange" format="yyyy-MM-dd"
                        value-format="yyyy-MM-dd" start-placeholder="开始日期" range-separator="至" end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item prop="carrierNo" label="车号">
        <el-input v-model="query.carrierNo" placeholder="车号" />
      </el-form-item>
      <el-form-item prop="carrier_type" label="运输方式">
        <el-select class="customSelectStyle" :popper-append-to-body="false" v-model="query.carrier_type"
                   clearable placeholder="运输方式">
          <el-option v-for="item in shippingOptions" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="toDesc" label="客户名称">
        <el-input v-model="query.toDesc" placeholder="客户名称" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" v-hasPermi="['cokesalewriteoffreal_list']" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="table.loading" :data="tableData" @sort-change="handleSort" stripe height="67vh">
      <el-table-column label="序号" width="55" type="index" align="left" />
      <el-table-column label="发货单号" sortable minWidth="150" align="left" prop="plan_list_no" />
      <el-table-column label="运输方式" sortable minWidth="150" align="left" prop="carrier_type"
                       :formatter="shippingMethodFormatter" />
      <el-table-column label="车号" sortable minWidth="150" align="left" prop="carrier_no" />
      <el-table-column label="料号" sortable minWidth="150" align="left" prop="mtrl_no" />
      <!-- <el-table-column label="储位代码"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="from_code" /> -->
      <el-table-column label="客户编号" sortable minWidth="150" align="left" prop="to_code" />
      <el-table-column label="客户名称" sortable minWidth="260" align="left" prop="to_desc" />
      <el-table-column label="合同号" sortable minWidth="150" align="left" prop="pchr_no" />
      <el-table-column label="是否过磅" sortable minWidth="150" align="left" prop="is_load"
                       :formatter="isLoadFormatter" />
      <!-- <el-table-column label="过磅点"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="load_station" /> -->
      <el-table-column label="申请单位名称" sortable minWidth="150" align="left" prop="issue_dept_name" />
      <el-table-column label="申请人姓名" sortable minWidth="150" align="left" prop="issue_emp_name" />
      <el-table-column label="申请日期" sortable minWidth="150" align="left" prop="issue_date" />
      <el-table-column label="磅单号" sortable minWidth="150" align="left" prop="wgt_list_no" />
      <el-table-column label="净重(t)" sortable minWidth="150" align="left" prop="net_wgt">
        <template slot-scope="scope">
          {{ (scope.row.net_wgt / 1000).toFixed(3) }}
        </template>
      </el-table-column>
      <el-table-column label="销账时间" sortable minWidth="150" align="left" prop="close_time" />
    </el-table>

    <pagination v-show="page.total > 0"
                   background
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   layout="total, sizes, prev, pager, next, jumper"
                   :current-page="page.current"
                   :page-sizes="[20, 50, 100, 200]"
                   :page-size="page.size"
                   :total="page.total">
    </pagination>
  </div>
</template>
