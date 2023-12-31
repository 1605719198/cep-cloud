<template>
  <div class="app-container">
    <el-form :model="query" ref="query" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="计划年份" prop="planYear">
        <el-date-picker v-model="query.planYear"
                        size="small"
                        type="year"
                        placeholder="计划年份"
                        value-format="yyyy"
                        @change="changeYear" />
      </el-form-item>
      <el-form-item label="计划月份" prop="planMonth">
        <el-date-picker v-model="query.planMonth"
                        size="small"
                        type="month"
                        placeholder="计划月份"
                        format="MM"
                        value-format="M" />
      </el-form-item>
      <el-form-item>
        <el-button size="mini"
                   type="primary"
                   icon="el-icon-search"
                   @click="handleQuery">搜索</el-button>
        <el-button size="mini"
                   icon="el-icon-refresh-left"
                   @click="handleEmpty">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-button type="primary"
                 size="mini"
                 plain
                 icon="el-icon-plus"
                 @click="handleOpenWindow('add')">新增
      </el-button>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="handleQuery"></right-toolbar>
    </el-row>

    <el-table v-loading="table.loading" :data="tableData" stripe @sort-change="handleSort" height="72vh">
      <el-table-column prop="plan_year"
                       label="年度"
                       min-width="80px"
                       :formatter="formatterYear" />
      <el-table-column v-if="queryYear"
                       prop="plan_month"
                       label="月份"
                       min-width="80px"
                       :formatter="formatterMonth" />
      <el-table-column v-if="queryMonth"
                       prop="plan_date"
                       label="日"
                       min-width="80px" />
      <template v-if="!queryYear && !queryMonth">
        <el-table-column prop="target_item_id"
                         label="指标项目"
                         min-width="150px"
                         :formatter="formatterTargetItemName" />
        <el-table-column prop="measures_and_goals"
                         label="措施及完成目标"
                         min-width="150px" />
        <el-table-column prop="responsible_person"
                         label="责任人"
                         min-width="150px" />
        <el-table-column prop="target_item_value"
                         label="计划量（kwh）"
                         min-width="150px" />
      </template>
      <template v-if="queryYear"
                v-for="(column,index)  in columns">
        <el-table-column v-if="column.customStyle"
                         :key="index"
                         :prop="column.prop"
                         :label="column.label"
                         :type="column.type"
                         :width="column.width"
                         :min-width="column.minWidth"
                         :sortable="column.sortable">
          <template slot-scope="scope">
            <div v-html="column.customStyle(scope.row,scope.$index,scope)"></div>
          </template>
        </el-table-column>
        <el-table-column v-else
                         :key="index"
                         :prop="column.prop"
                         :label="column.label"
                         :type="column.type"
                         :width="column.width"
                         :min-width="column.minWidth"
                         :sortable="column.sortable"
                         :formatter="column.formatter" />
      </template>
      <el-table-column v-if="columnsType"
                       label="操作"
                       width="180px">
        <template slot-scope="scope">
          <el-button size="mini"
                     icon="el-icon-edit"
                     type="text"
                     v-if="queryYear && !queryMonth"
                     @click="handleOpenWindow('edit',scope.$index, scope.row)">修改
          </el-button>
          <el-button size="mini"
                     icon="el-icon-delete"
                     type="text"
                     v-if="!queryYear"
                     @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="page.total>0"
      :total="page.total"
      :page.sync="page.current"
      :limit.sync="page.size"
      @pagination="handleQuery"
    />


    <div v-if="openDialog.open">
      <el-dialog :title="openDialog.title"
                 :visible.sync="openDialog.open"
                 :width="openDialog.width"
                 class="customDialogStyle"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false">
        <template v-if="openDialog.type === 'add' || openDialog.type === 'edit'">
          <planElectricityAdd :type="openDialog.type"
                              :data="openDialog.data"
                              :columnsList="columnsList"
                              :rulesList="rulesList"
                              :query="query"
                              @submitSave="submitSave"
                              @close="openDialog.open=false" />
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { req } from "@/api/production/oi/common";
import { getProductionTargetItem } from "@/api/production/oi/plan";
import planElectricityAdd from "./planElectricityAdd";

export default {
  components: {
    planElectricityAdd
  },
  name: "planElectricity",
  data () {
    return {
      showSearch: true,
      openDialog: { open: false, type: '', title: '', width: '500px', data: {}, },
      page: { size: 10, current: 1, total: 0, order: "plan_year", orderby: "desc", },
      query: { planYear: '', planMonth: '', },
      queryYear: '', queryMonth: '',
      table: { border: true, loading: true, },
      columnsList: [],
      rulesList: {},
      columnsType: false,
      columns: [],
      tableData: [],
      targetItemNames: {},
    }
  },
  computed: {
  },
  created () {
    getProductionTargetItem({ targetItemTypeId: 2 }).then(response => {
      let columns = [];
      let columnsList = [];
      let rules = {};
      let targetItemNames = {};
      response.data.forEach(function (val) {
        let column = { label: val.targetItemName, prop: val.id, minWidth: '150px' };
        let column2 = {
          id: val.id, value: "0", targetItemName: val.targetItemName,
          calcUnitId: val.calcUnitId, calcUnitName: val.measuringUnit, measures_and_goals: "", responsible_person: "",
        };
        columns.push(column);
        columnsList.push(column2);
        targetItemNames[val.id] = val.targetItemName;
        rules[val.id] = [{ required: true, message: '请输入指标值', trigger: 'blur' },
        { type: 'number', message: '请输入数字', trigger: 'blur', transform: (value) => Number(value) },];
      });
      this.columns = columns;
      this.columnsList = columnsList;
      this.rulesList = rules;
      this.columnsType = true;
      this.targetItemNames = targetItemNames;
      this.onLoad();
    }, error => {
      window.console.log(error);
    });
  },
  methods: {
    //载入数据
    onLoad () {
      this.table.loading = true;//加载状态
      if (this.queryYear !== '' && this.queryMonth === '') {
        let data = { ...this.page, ...this.query };
        req('get', 'listMonthPowerGenerationTargetPlans', data).then(res => {
          this.table.loading = false;
          this.tableData = res.data;//表格数据
        }, error => {
          this.table.loading = false;
          window.console.log(error);
        });
      } else if (this.queryMonth !== '') {
        let data = { ...this.page, ...this.query };
        req('get', 'listDatePowerGenerationTargetPlans', data).then(res => {
          this.table.loading = false;
          this.tableData = res.data;//表格数据
        }, error => {
          this.table.loading = false;
          window.console.log(error);
        });
      } else {
        let data = { ...this.page, ...this.query };
        req('get', 'listYearPowerGenerationTargetPlans', data).then(res => {
          this.table.loading = false;
          this.tableData = res.data;//表格数据
        }, error => {
          this.table.loading = false;
          window.console.log(error);
        });
      }

    },
    // 排序
    handleSort (column) {
      if (column.order === null) {
        //默认
        this.page.order = "plan_year";
        this.page.orderby = "desc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.onLoad();
    },
    //查询
    handleQuery () {
      if (this.query.planYear === '' || this.query.planYear === null) {
        this.queryYear = '';
      } else {
        this.queryYear = this.query.planYear;
      }
      if (this.query.planMonth === '' || this.query.planMonth === null) {
        this.queryMonth = '';
      } else {
        this.queryMonth = this.query.planMonth;
      }
      this.onLoad();
    },
    // 清空
    handleEmpty () {
      this.queryYear = '';
      this.queryMonth = '';
      this.$refs["query"].resetFields();
      this.onLoad();
    },
    // 打开窗口
    handleOpenWindow (type, index, row) {
      let title = '发电年指标计划';
      this.openDialog.open = true;
      this.openDialog.type = type;
      this.openDialog.width = '800px';
      this.openDialog.data = row;
      console.log(this.openDialog.data)
      if (type === 'add') {
        this.openDialog.title = '新增' + title;
        this.openDialog.data = {};
      } else if (type === 'edit') {
        this.openDialog.title = '编辑' + title;
      } else {
        this.openDialog.open = false;
        this.openDialog.data = {};
      }
    },
    // 删除
    handleDelete (index, row) {
      this.$confirm('此操作将删除' + row.plan_year + '年全部发电指标计划, 是否继续?', '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        req('post', 'deleteYearPowerGenerationTargetPlan', {
          planYear: row.plan_year,
          deleteUserId: this.$store.state.user.userInfo.userName,
          deleteUserName: this.$store.state.user.userInfo.nickName,
        }).then(res => {
          this.$message({
            type: "success", message: "删除成功！", duration: 1000,
            onClose: () => {
              this.onLoad();
            }
          });
        }, error => {
          window.console.log(error);
        });
      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    //提供给子类调用的方法
    submitSave () {
      this.openDialog.open = false;
      this.openDialog.data = {};
      this.onLoad();
    },
    formatterYear (row) {
      return this.queryYear !== '' || this.queryMonth ? this.queryYear : row.plan_year;
    },
    formatterMonth (row) {
      return this.queryMonth !== '' ? this.queryMonth : row.plan_month;
    },
    formatterTargetItemName (row) {
      return this.targetItemNames[row.target_item_id];
    },
    changeYear (val) {
      if (val === null) {
        this.query.planMonth = '';
      }
    },
  }
}
</script>

<style scoped>
</style>
