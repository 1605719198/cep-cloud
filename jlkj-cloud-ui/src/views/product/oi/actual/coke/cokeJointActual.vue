<!--
  接焦实绩
-->
<template>
  <div class="avue-crud el-card__body" style="width: 98%; border: 0">
    <div class="avue-crud__search" style="border: 0">
      <el-row>
        <el-col :span="20">
          <el-form :model="query" ref="query" :inline="true">
            <el-form-item prop="timeArr">
              <el-date-picker
                v-model="query.timeArr"
                type="daterange"
                range-separator="至"
                start-placeholder="时间开始"
                end-placeholder="结束时间"
                value-format="yyyy-MM-dd"
              />
            </el-form-item>
            <!-- 操作按钮 -->
            <el-form-item>
              <el-button
                @click="handleQuery"
                type="primary"
                icon="el-icon-search"
                size="medium"
              >搜 索</el-button
              >
              <el-button
                @click="handleReset"
                icon="el-icon-refresh-left"
                size="medium"
              >重 置</el-button
              >
              <el-button
                v-hasPermi="['productionCollectionCdqJarList']"
                @click="handleReport"
                icon="el-icon-download"
                type="warning"
                size="medium"
              >导 出</el-button
              >
            </el-form-item>
          </el-form>
        </el-col>
        <!-- <el-col :span="4">
                    <div style="text-align: right;">
                        <el-button type="primary"
                                   size="medium"
                                   icon="el-icon-plus"
                                   @click="handleAdd('add')">新增
                        </el-button>
                    </div>
                </el-col> -->
      </el-row>
    </div>
    <div>
      <el-form>
        <el-table
          height="67vh"
          size="small"
          :data="tableData"
          stripe
          @sort-change="handleSort"
          v-loading="table.loading"
        >
          <el-table-column
            v-for="column in columns"
            :prop="column.prop"
            :label="column.label"
            :sortable="column.sortable"
            :width="column.width"
            :min-width="column.minWidth"
            :type="column.type"
            :align="column.align"
            :formatter="column.formatter"
            :key="column.prop"
          />
          <!-- <el-table-column fixed="right"
                                     label="操作"
                                     width="180"
                                     align="center">
                        <template slot-scope="scope">
                            <el-button size="mini"
                                       plain
                                       icon="el-icon-delete"
                                       type="danger"
                                       @click="handleDelete(scope.$index, scope.row)">删除
                            </el-button>
                        </template>
                    </el-table-column> -->
        </el-table>
        <div
          style="margin-top: 10px; right: 0; padding: 0px 0 20px 20px"
          class="avue-crud__pagination"
        >
          <el-pagination
            background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            layout="total, sizes, prev, pager, next, jumper"
            :current-page="page.currentPage"
            :page-sizes="[20, 50, 100, 200]"
            :page-size="page.pageSize"
            :total="page.total"
          >
          </el-pagination>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { getProductionCollectionCdqJarList } from "@/api/production/oi/actual/coke/cokeJointActual";
import {downloadoffice} from "@/utils/util";

export default {
  components: {
    // Add,
  },
  name: "",
  data() {
    return {
      page: {
        size: 20,
        current: 1,
        total: 0,
        order: "collection_time",
        orderby: "desc",
      },
      query: {
        timeArr: null,
      },
      table: {
        border: true,
        loading: false,
      },
      columns: [
        {
          label: "序号",
          type: "index",
          width: "100",
          align: "center"
        },
        {
          label: "采集时间",
          prop: "collection_time",
          sortable: true,
          minWidth: "150px",
        },
        {
          label: "过程值(t)",
          prop: "process_weight",
          sortable: true,
          minWidth: "150px",
        },
        {
          label: "罐车(t)",
          prop: "tank_weight",
          sortable: true,
          minWidth: "150px",
        },
        {
          label: "焦(t)",
          prop: "coke_weight",
          sortable: true,
          minWidth: "150px",
        },
      ],
      tableData: [],
      dialog: {
        visible: false,
        type: "",
        title: "",
        width: "60%",
        data: {},
      },
    };
  },
  created() {
    // this.onLoad();
  },
  methods: {
    //载入数据
    onLoad() {
      if (this.query.timeArr && this.query.timeArr[0]) {
        this.query.start_time = this.query.timeArr[0] + " " + "00:00:00";
        this.query.end_time = this.query.timeArr[1] + " " + "23:59:59";
      } else {
        this.query.start_time = "";
        this.query.end_time = "";
      }
      this.table.loading = true; //加载状态
      getProductionCollectionCdqJarList(this.page, this.query).then(
        (res) => {
          this.table.loading = false;
          let data = res.data.data; //表格相关数据
          this.page.total = data.total; //数据总数
          this.tableData = data.records; //表格数据
        },
        (error) => {
          this.table.loading = false;
          window.console.log(error);
        }
      );
    },
    // 分页-每页多少条
    handleSizeChange(val) {
      this.page.size = val;
      this.onLoad();
    },
    // 分页-当前页
    handleCurrentChange(val) {
      this.page.current = val;
      this.onLoad();
    },
    // 排序
    handleSort(column) {
      // console.log(column, "column")
      if (column.order === null) {
        //默认
        this.page.order = "collection_time";
        this.page.orderby = "desc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === "ascending" ? "asc" : "desc";
      }
      this.onLoad();
    },
    //查询
    handleQuery() {
      this.page.current = 1;
      this.onLoad();
    },
    // 重置
    handleReset() {
      this.page.current = 1;
      this.$refs["query"].resetFields();
      this.handleQuery();
    },
    //新增
    handleAdd(type) {
      this.dialog.type = type;
      this.dialog.title = "新增";
      this.dialog.data = {};
      this.dialog.visible = true;
    },
    // 删除
    // handleDelete(index, row) {
    //     this.$confirm('是否确认删除数据项?', '警告', {
    //         confirmButtonText: '确定',
    //         cancelButtonText: '取消',
    //         type: 'warning',
    //     })
    //         .then(() => {
    //             deleteProductionOutputPerformanceManualBake({
    //                 id: row.id,
    //             }).then(
    //                 (res) => {
    //                     if (res.data.code === '0') {
    //                         this.onLoad()
    //                         this.$message({
    //                             type: 'success',
    //                             message: res.data.msg,
    //                         })
    //                     }
    //                 },
    //                 (error) => {
    //                     window.console.log(error)
    //                 }
    //             )
    //         })
    //         .catch(() => {
    //             this.$message({ type: 'info', message: '已取消删除' })
    //         })
    // },
    //提供给子类调用父类的方法
    submitSave() {
      this.dialog.visible = false;
      this.dialog.data = {};
      this.handleQuery();
    },
    /**
     * 导出按钮操作
     */
    handleReport() {
      if (this.query.timeArr && this.query.timeArr[0]) {
        this.query.start_time = this.query.timeArr[0] + " " + "00:00:00";
        this.query.end_time = this.query.timeArr[1] + " " + "23:59:59";
      } else {
        this.query.start_time = "";
        this.query.end_time = "";
      }
      downloadoffice('/api/neplatform/production/productionCollectionCdqJar/export', {
        ...this.query
      }, `干熄焦报表${new Date().getTime()}.xlsx`)
    },
  },
};
</script>

<style scoped>
.main {
  /* height: 88vh; */
  background-color: #fff;
}
.el-card__body {
  padding: 20px 10px;
}
</style>
