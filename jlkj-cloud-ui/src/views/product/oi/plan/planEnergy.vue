<template>
  <div class="app-container">
    <el-form :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="计划年份" prop="planYear" >
        <el-date-picker v-model="query.planYear"
                        type="year"
                        size="small"
                        placeholder="计划年份"
                        value-format="yyyy"
        style="width: 200px"/>
      </el-form-item>
      <el-form-item label="计划月份" prop="planMonth">
        <el-date-picker v-model="query.planMonth"
                        type="month"
                        size="small"
                        placeholder="计划月份"
                        value-format="MM"
                        format="M"
                        style="width: 200px"/>
      </el-form-item>
      <el-form-item label="能源介质" prop="energyCode">
        <el-select v-model="query.energyCode"
                   placeholder="选择能源介质"
                   v-loading="table.selectLoading"
                   size="small"
                   clearable
                   style="width: 200px">
          <el-option v-for="item in options"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="类型" prop="types">
        <el-select v-model="query.types" placeholder="选择类型" clearable size="small" style="width: 200px">
          <el-option label="耗用量" value="耗用量" />
          <el-option label="耗产量" value="耗产量" />
          <el-option label="回收量" value="回收量" />
        </el-select>
      </el-form-item>
      <el-form-item label="作业区" prop="factoryArea">
        <el-select v-model="query.factoryArea" placeholder="选择作业区" clearable size="small">
          <el-option label="焦化厂_炼焦作业区" value="焦化厂_炼焦作业区" />
          <el-option label="焦化厂_化产作业区" value="焦化厂_化产作业区" />
          <el-option label="焦化厂_备煤作业区" value="焦化厂_备煤作业区" />
        </el-select>
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

    <el-table :data="tableData" stripe @sort-change="handleSort" v-loading="table.tableLoading">
      <template v-for="column in columns">
        <el-table-column :prop="column.prop"
                         :label="column.label"
                         :sortable="column.sortable"
                         :width="column.width"
                         :min-width="column.minWidth"
                         :key="column.prop" />
      </template>
    </el-table>

    <pagination
      v-show="page.total>0"
      :total="page.total"
      :page.sync="page.current"
      :limit.sync="page.size"
      @pagination="handleQuery"
    />

  </div>
</template>

<script>
import { getEnergyCodeList, listMonthEnergyPlans } from "@/api/production/oi/plan";
import { selectEnergyTree } from "@/api/energy/ee/energyTree";

export default {
  name: "planEnergy",
  data () {
    return {
      showSearch: true,
      dataEdit: {},
      page: { size: 10, current: 1, total: 0, order: "planDate", orderby: "desc" },
      query: { planYear: '', planMonth: '', energyCode: '', types: '', factoryArea: '' },
      table: { border: true, selectLoading: false, tableLoading: false },
      columns: [
        { label: '日期', prop: "planDate", sortable: true, type: 'year', minWidth: '100px' },
        { label: '能源介质', prop: "energy_name", sortable: true, minWidth: '150px' },
        { label: '类型', prop: "types", sortable: true, minWidth: '150px' },
        { label: '工序名称', prop: "factory_name", sortable: true, minWidth: '150px' },
        { label: '作业区', prop: "factory_area", sortable: true, minWidth: '150px' },
        { label: '计划值', prop: "plan_value", sortable: true, minWidth: '150px' },
        { label: '单位', prop: "unit", sortable: true, minWidth: '150px' },
        { label: '备注', prop: "memo", sortable: true, minWidth: '150px' },
        { label: '日计划公式', prop: "memoa", sortable: true, minWidth: '150px' },
        { label: '行业先进值', prop: "advanced_value", sortable: true, minWidth: '150px' },
        { label: '内控指标值', prop: "laws_value", sortable: true, minWidth: '150px' },
        { label: '新增时间', prop: "create_date", sortable: true, minWidth: '150px' },
        { label: '新增人员', prop: "create_emp_no", sortable: true, minWidth: '150px' },
        { label: '修改时间', prop: "update_date", sortable: true, minWidth: '150px' },
        { label: '修改人员', prop: "update_emp_no", sortable: true, minWidth: '150px' },
      ],
      tableData: [],
      options: [],
    }
  },
  mounted () {
    this.getEnergyCode();
    this.handleQuery();
  },
  methods: {
    //查询
    handleQuery () {
      // this.page.current = 1;
      let params = {
        planYear: this.query.planYear === null ? "" : this.query.planYear,
        planMonth: this.query.planMonth === null ? "" : this.query.planMonth,
        energyCode: this.query.energyCode === null ? "" : this.query.energyCode,
        types: this.query.types === null ? "" : this.query.types,
        factoryArea: this.query.factoryArea === null ? "" : this.query.factoryArea,
        ...this.page
      };
      this.table.tableLoading = true;
      listMonthEnergyPlans(params).then(response => {
        this.table.tableLoading = false;
        this.tableData = response.data.records;
        this.page.total = response.data.total;
      }, error => {
        this.table.tableLoading = false;
        window.console.log(error);
      })
    },
    getEnergyCode () {
      this.table.selectLoading = true;
      // getEnergyCodeList().then(response => {
      //   this.table.selectLoading = false;
      //   let dataArray = response.data.data;
      //   for (let i = 0; i < dataArray.length; i++) {
      //     this.options.push({ "value": dataArray[i].engyId, "label": dataArray[i].engyName });
      //   }
      // }, error => {
      //   this.table.selectLoading = false;
      //   window.console.log(error);
      // })
      selectEnergyTree().then(response => {
        this.table.selectLoading = false;
        let dataArray = response;
        for (let i = 0; i < dataArray.length; i++) {
          if (dataArray[i].column4.length === 6 && dataArray[i].serial_no) {
            this.options.push({ "value": dataArray[i].node_name, "label": dataArray[i].node_name });
          }

        }
      }, error => {
        this.table.selectLoading = false;
        window.console.log(error);
      })
    },
    // 清空
    handleEmpty () {
      this.query = {
        planYear: '',
        planMonth: '',
        energyCode: '',
      };
      this.handleQuery();
    },
    // 排序
    handleSort (column) {
      if (column.order === null) {
        //默认
        this.page.order = "planDate";
        this.page.orderby = "desc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.handleQuery();
    },
  }
}
</script>

<style scoped>
</style>
