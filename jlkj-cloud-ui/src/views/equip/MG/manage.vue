<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="二级编号/IP" prop="mgmp3No">
        <el-input v-model="queryParams.mgmp3No" type="text" clearable placeholder="请输入二级编号/IP" />
      </el-form-item>
      <el-form-item label="产线编号" prop="mgma1No">
        <el-input v-model="queryParams.mgma1No" type="text" clearable placeholder="请输入产线编号" />
      </el-form-item>
      <el-form-item label="日期" prop="datetimeRange">
        <el-date-picker
          v-model="datetimeRange"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="dateTimeChange"
          value-format="yyyy-MM-dd"
          format="yyyy-MM-dd" />
      </el-form-item>
      <el-form-item label="停机类型" prop="mgmp3Stus">
        <el-select v-model="queryParams.mgmp3Stus"
                   placeholder="请选择停机类型">
          <el-option v-for="item in options"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button size="mini" type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button size="mini" icon="el-icon-refresh-left" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="tableData" stripe :default-sort="{prop: 'mgmp3no', order: 'descending'}">
      <el-table-column type="index" label="序号" :index="indexMethod" align="center" />
      <template v-for="column in columns">
        <el-table-column :key="column.prop"
                         :fixed="column.fixed"
                         :prop="column.prop"
                         :label="column.label"
                         :sortable="column.sortable"
                         :width="column.width"
                         :align="column.align">
        </el-table-column>
      </template>
      <el-table-column prop="mgmp3Stus" sortable label="停机类型" align="center">
        <template slot-scope="scope">
          <dict-tag :options="options" :value="scope.row.mgmp3Stus" />
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.page"
      :limit.sync="queryParams.limit"
      @pagination="handleQuery"
    />
  </div>
</template>

<script>
import { queryDataByParams } from "@/api/equip/MG/manage"
export default {
  name: "manage",
  components: {},
  dicts: ["equipment_record_state"],
  data () {
    return {
      // 显示搜索条件
      showSearch: true,
      options: [
        { value: '1', label: '重启', raw: { listClass: '' } },
        { value: '0', label: '停机', raw: { listClass: '' } }
      ],
      // 遮罩层
      loading: false,
      // 表格数据
      tableData: [],
      // 表格字段
      columns: [
        { label: '二级编号/IP', prop: "mgmp3No", sortable: true, align: "center", fixed: false },
        { label: '产线编号', prop: "mgma1No", sortable: true, align: "center", fixed: false },
        { label: '接收日期', prop: "receiveDate", sortable: true, align: "center", fixed: false },
      ],
      // 总条数
      total: 0,
      // 日期区间
      datetimeRange: [],
      // 查询参数
      queryParams: {
        // 当前页
        page: 1,
        // 每页记录数
        limit: 10,
        // 二级编号/IP
        mgmp3No: undefined,
        // 产线编号
        mgma1No: undefined,
        // 停机类型
        mgmp3Stus: undefined,
        // 开始日期
        startDate: undefined,
        // 结束日期
        endDate: undefined
      }
    }
  },
  created () {
    this.handleQuery()
  },
  methods: {
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm("queryForm")
      this.datetimeRange = [];
      this.handleQuery();
    },
    //分页改变
    handleSizeChange (val) {
      this.queryParams.limit = val;
      this.handleQuery();
    },
    //页码改变
    handleCurrentChange (val) {
      this.queryParams.page = val;
      this.handleQuery();
    },
    // 日期change
    dateTimeChange (e) {
      if (e === null) {
        this.queryParams.startDate = e
        this.queryParams.endDate = e
      } else {
        this.queryParams.startDate = e[0]
        this.queryParams.endDate = e[1]
      }
    },
    // 查询方法
    handleQuery () {
      queryDataByParams(this.queryParams).then(response => {
        this.tableData = response.data.list
        this.total = response.data.total
      })
    },
    handleExport () {

    },
    // 序号设置
    indexMethod (index) {
      index = (index + 1) + (this.queryParams.page - 1) * this.queryParams.limit
      return index
    },
  }
}
</script>

<style scoped>
/*.avue-crud .el-date-editor.el-input {*/
/*  width: auto !important;*/
/*}*/
.plan_header {
  background-color: #f9f9f9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #e9e9e9 solid;
}

.plan_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold;
}

.plan_main {
  height: 86vh;
  background-color: #fff;
}
</style>
  <style>
.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}

/* 弹窗圆角 */
.customDialogStyle .el-dialog {
  border-radius: 8px;
}

/* 标题样式 */
.customDialogStyle .el-dialog__header {
  background-color: rgb(245, 245, 245);
  border-radius: 8px 8px 0 0;
  padding: 10px 20px 10px;
}

/* 标题文字样式 */
.customDialogStyle .el-dialog__title {
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  letter-spacing: normal;
  font-size: 14px;
  font-weight: 700;
  font-style: normal;
  text-align: left;
  line-height: 20px;
  border-width: 0;
  color: rgb(102, 102, 102);
}

/* 关闭符号位置调整 */
.customDialogStyle .el-dialog__headerbtn {
  top: 12px;
}

.customDialogStyle .el-dialog__body {
  padding: 15px 40px;
}
</style>
