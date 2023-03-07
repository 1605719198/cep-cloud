<template>
  <div class="avue-crud">
    <el-table height="67vh"
              border
              size="small"
              v-loading="table.loading"
              :data="tableData"
              stripe
              @sort-change="handleSort">
      <el-table-column label="变更前（kg）"
                       minWidth="150"
                       align="left"
                       prop="before_inventory" />
      <el-table-column label="变更后（kg）"
                       minWidth="150"
                       align="left"
                       prop="after_inventory" />
      <el-table-column label="变更人"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="modify_user_name" />
      <el-table-column label="变更时间"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="modify_time" />
      <el-table-column label="备注"
                       minWidth="150"
                       align="left"
                       prop="remark" />
    </el-table>
    <div style="margin-top: 10px;right: 0"
         class="avue-crud__pagination">
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
import { materialsCokeStockHistoryList } from "@/api/material/mr/cokeInventory/index";
export default {
  //接受爷爷传过来的
  inject: ["row"],
  data () {
    return {
      page: {
        size: 20,
        current: 1,
        total: 1,
        order: "modify_time",
        orderby: "desc",
      },
      table: {
        border: true,
        // loading: true,
      },
      tableData: [],
    }
  },
  created () {
    this.onLoad()
  },
  methods: {
    //载入数据
    onLoad () {
      this.table.loading = true;//加载状态
      materialsCokeStockHistoryList(this.page, this.row.data.id).then(res => {
        this.table.loading = false;
        let data = res.data;//表格相关数据
        this.page.total = data.total;//数据总数
        this.tableData = data.records;//表格数据
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
    handleSort (column) {
      // console.log(column, "column")
      if (column.order === null) {
        //默认
        this.page.order = "modify_time";
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
