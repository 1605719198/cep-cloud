<!--卸车实绩-->
<template>
  <div class="app-container">
    <el-form :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="起始日期" prop="unloadingStart">
        <el-date-picker v-model="query.unloadingStart"
                        type="date"
                        size="small"
                        placeholder="选择卸车起始日期"
                        value-format="yyyy-MM-dd"
        style="width: 150px;"/>
      </el-form-item>
      <el-form-item label="结束日期" prop="unloadingEnd">
        <el-date-picker v-model="query.unloadingEnd"
                        type="date"
                        size="small"
                        placeholder="选择卸车结束日期"
                        value-format="yyyy-MM-dd"
                        style="width: 150px;"/>
      </el-form-item>
      <el-form-item label="班别" prop="classType">
        <el-select v-model="query.classType"
                   placeholder="选择班别"
                   clearable
                   size="small"
                   style="width: 120px;">
          <el-option v-for="item in classTypeOptions"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="班次" prop="shift">
        <el-select v-model="query.shift"
                   placeholder="班次"
                   clearable
                   size="small"
                   style="width: 120px;">
          <el-option v-for="item in shiftOptions"
                     :key="item.value"
                     :label="item.label"
                     :value="item.label">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="运输方式" prop="transportModel">
        <el-select v-model="query.transportModel"
                   placeholder="选择运输方式"
                   clearable
                   size="small"
                   style="width: 120px;">
          <el-option v-for="item in transportModelOption"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="query.status"
                   placeholder="状态"
                   clearable
                   size="small"
                   style="width: 120px;">
          <el-option v-for="item in status"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
          </el-option>
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

    <el-table :data="tableData" stripe @sort-change="handleSort" v-loading="table.loading">
      <template v-for="column in columns">
        <el-table-column :prop="column.prop"
                         :label="column.label"
                         :sortable="column.sortable"
                         :width="column.width"
                         :min-width="column.minWidth"
                         :type="column.type"
                         :formatter="column.formatter"
                         :key="column.prop" />
      </template>
      <el-table-column fixed="right"
                       label="操作"
                       width="100px">
        <template slot-scope="scope">
          <el-button size="mini"
                     type="text"
                     icon="el-icon-info"
                     @click="handleDetail(scope.row)">详情
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

      <div v-if="detailBox">
        <el-dialog title="卸车实绩详情"
                   :visible.sync="detailBox"
                   width="850px"
                   append-to-body
                   :destroy-on-close="true"
                   :close-on-click-modal="false">
          <unloadActualDetail :dataEdit="dataEdit"
                              @close="detailBox = false" />
        </el-dialog>
      </div>
  </div>
</template>

<script>

import unloadActualDetail from "./unloadActualDetail";
import { getUnloadList } from "@/api/production/oi/actual/coal/UnloadActualApi";
import { getDicts } from '@/api/system/dict/data'
import moment from "moment";

export default {
  components: {
    unloadActualDetail
  },
  name: "unloadActual",
  mounted () {
    getDicts("sys_classtype").then(response => {
      this.classTypeOptions = response.data.map((i)=>{
        return { value: i.dictLabel, label:  i.dictLabel };
      });
    });
    getDicts("sys_shift_no").then(response => {
      this.shiftOptions = response.data.map((i)=>{
        return { value: i.dictValue, label:  i.dictLabel };
      });
    });
    this.handleQuery()
  },
  data () {
    return {
      showSearch: true,
      dataEdit: {},
      detailBox: false,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      currentPage: 0,
      total: 0,
      classTypeOptions: [],
      shiftOptions: [],
      transportModelOption: [
        { value: "T", label: "铁运" },
        { value: "Q", label: "汽运" },
      ],
      status: [
        { value: 0, label: "全部" },
        { value: 1, label: "未完成" },
        { value: 2, label: "已完成" }
      ],
      query: {
        unloadingStart: '',
        unloadingEnd: '',
        classType: '',
        shift: '',
        transportModel: '',
        status: 0
      },
      table: {
        border: true,
        loading: true
      },
      columns: [
        { label: '编号', type: "index", minWidth: '100px' },
        { label: '检斤计划编号', prop: "plan_id", sortable: true, minWidth: '150px' },
        {
          label: '检斤计划日期', prop: "plan_date", sortable: true, minWidth: '150px', formatter (row) {
            return row.plan_date === null ? "" : moment(row.plan_date).format("YYYY-MM-DD HH:mm:ss");
          }
        },
        { label: '供货单位名称', prop: "supplier_name", sortable: true, minWidth: '150px' },
        {
          label: '运输方式', prop: "shipping_method", sortable: true, minWidth: '100px', formatter (row) {
            if (row.shipping_method === "T") {
              return "铁运";
            } else if (row.shipping_method === "Q") {
              return "汽运";
            } else {
              return row.shipping_method;
            }
          }
        },
        { label: '车号', prop: "vehicle_number", sortable: true, minWidth: '80px' },
        { label: '储位编号', prop: "storage_maintain_space_name", sortable: true, minWidth: '100px' },
        { label: '物料名称', prop: "material_name", sortable: true, minWidth: '150px' },
        { label: '物料重量(t)', prop: "material_weight", sortable: true, minWidth: '100px', formatter (row) { return (row.material_weight / 1000).toFixed(3) } },
        { label: '班别', prop: "class_name", sortable: true, minWidth: '80px' },
        {
          label: '班次', prop: "shift_name", sortable: true, minWidth: '80px', formatter (row) {
            switch (row.shift_name) {
              case "01":
                return "常白班";
              case "2201":
                return "白班";
              case "2202":
                return "夜班";
              case "00":
                return "休息";
              default:
                return row.shift_name;
            }
          }
        },
        { label: '卸车时间', prop: "unloading_time", sortable: true, minWidth: '150px' },
      ],
      tableData: [],
    }
  },
  methods: {
    //查询
    handleQuery () {
      this.page.currentPage = 1;
      let query = this.query;
      let params = {
        shiftName: query.shift,
        className: query.classType,
        unloadingStart: query.unloadingStart === "" ? "" : query.unloadingStart,
        unloadingEnd: query.unloadingEnd === "" ? "" : query.unloadingEnd,
        shippingMethod: query.transportModel === null ? '' : query.transportModel,
        isDone: query.status === null || query.status === '' ? 0 : query.status,
        size: this.page.pageSize,
        current: this.page.currentPage,
        order: "plan_date",
        orderby: "desc"
      }
      console.log(params)
      this.table.loading = true;
      getUnloadList(params).then(response => {
        this.table.loading = false;
        this.tableData = response.data.records;
        this.page.total = response.data.total;
      }, error => {
        this.table.loading = false;
        window.alert(error);
      })
    },
    // 排序
    handleSort (column) {
      if (column.order === null) {
        //默认
        this.page.order = "plan_date";
        this.page.orderby = "desc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.handleQuery();
    },
    // 清空
    handleEmpty () {
      this.query.unloadingStart = '';
      this.query.unloadingEnd = '';
      this.query.classType = '';
      this.query.shift = '';
      this.query.transportModel = '';
      this.query.status = 0;
      this.handleQuery();
    },
    //详情
    handleDetail (row) {
      this.dataEdit = row;
      this.detailBox = true;
    },
    // 分页-每页多少条
    handleSizeChange (val) {
      this.page.pageSize = val;
      this.handleQuery();
    },
    // 分页-当前页
    handleCurrentChange (val) {

      this.page.currentPage = val;
      this.handleQuery();
    },
    //提供给子类调用父类的方法
    submitAdd (data) {
      console.log(JSON.stringify(data))
      this.addBox = false;
      this.editBox = false;
      this.$message({
        type: "success",
        message: "操作成功!"
      });
    },
  }
}
</script>

<style scoped>
</style>
