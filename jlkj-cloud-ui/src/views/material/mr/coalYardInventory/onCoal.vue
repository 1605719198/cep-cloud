<template>
  <div>
    <el-table height="70vh" border :header-cell-style="{background:'#f5f7fa',color:'#606266'}" size="small"
              v-loading="table.loading" :data="tableData" stripe @sort-change="handleSort">
      <el-table-column label="物料类别" minWidth="150" align="left" prop="category_name"/>
      <el-table-column label="物料名称" minWidth="150" align="left" prop="material_name"/>
      <el-table-column label="小煤种" minWidth="150" align="left" prop="material_small_name"/>
      <!--      <el-table-column label="物资品名" minWidth="150" align="left" prop="lot_no"/>-->
      <el-table-column label="班组" sortable minWidth="150" align="left" prop="class_name"/>
      <!--      <el-table-column label="供货单位" minWidth="150" align="left" prop="carrier_no"/>-->
      <el-table-column label="配煤开始时间" sortable minWidth="150" align="left" prop="start_time"/>
      <el-table-column label="配煤结束时间" sortable minWidth="150" align="left" prop="end_time"/>
      <!--      <el-table-column label="煤堆号" sortable minWidth="150" align="left" prop="slot_name"/>-->
      <el-table-column label="配煤仓号" sortable minWidth="150" align="left" prop="bunker_number"/>
      <el-table-column label="重量(kg)" sortable minWidth="150" align="left" prop="material_weight"/>
    </el-table>
    <div style="margin-top: 10px;float: right" class="avue-crud__pagination">
      <el-pagination v-show="page.total > 0"
                     background
                     @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     layout="total, sizes, prev, pager, next, jumper"
                     :current-page="page.current"
                     :page-sizes="[20, 50, 100, 200]"
                     :page-size="page.size"
                     :total="page.total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  import {getConveyingCoalList} from '@/api/material/mr/coalYardInventory/index'

  export default {
    props: {data: {type: Object}},
    data() {
      return {
        page: {size: 20, current: 1, total: 1, order: "start_time", orderby: "desc"},
        table: {border: true},
        query: {categoryId: '', materialsId: '', materialsName: '', materials_small_code: ''},
        tableData: [],
      }
    },
    created() {
      this.query.materials_small_code = this.data.materials_small_code;
      // this.query.materialsName = this.data.materials_name;
      // this.query.storageSpacesId = this.data.storage_spaces_id;
      this.onLoad()
    },
    methods: {
      //载入数据
      onLoad() {
        this.table.loading = true;//加载状态
        getConveyingCoalList(this.page, this.query).then(res => {
          let data = res.data;//表格相关数据
          this.page.total = data.total;//数据总数
          this.tableData = data.records;//表格数据
          this.table.loading = false;
        }, error => {
          this.table.loading = false;
          window.console.log(error);
        });
      },
      // 分页-每页多少条
      handleSizeChange (val) {
        this.page.size = val;
        this.onLoad();
      },
      // 分页-当前页
      handleCurrentChange (val) {
        this.page.current = val;
        this.onLoad();
      },
      // 排序
      handleSort(column) {
        // console.log(column, "column")
        if (column.order === null) {
          //默认
          this.page.order = "start_time";
          this.page.orderby = "desc";
        } else {
          //选中项
          this.page.order = column.prop;
          this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
        }
        this.onLoad();
      },
    }
  }
</script>

<style>
</style>
