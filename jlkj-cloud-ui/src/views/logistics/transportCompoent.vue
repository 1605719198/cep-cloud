<template>
        <div class="plan_main" style="height: 60vh;">
            <div>
              <el-form>
                <el-table
                  height="50vh"
                  size="small"
                  :data="tableData"
                  stripe
                  width="100%"
                  :default-sort="{ prop: 'planDay', order: 'descending' }"
                >
                  <el-table-column
                    type="index"
                    label="序号"
                    :index="indexMethod"
                    align="center"
                  />
                  <template v-for="column in columns">
                    <el-table-column
                      :fixed="column.fixed"
                      :prop="column.prop"
                      :label="column.label"
                      :sortable="column.sortable"
                      :width="column.width"
                    >
                    </el-table-column>
                  </template>
                </el-table>
                <!--               分页 -->
                <div
                  style="margin-top: 5px; left: 392px"
                  class="avue-crud__pagination"
                >
                  <el-row>
                    <el-col :span="7">
                     <el-pagination
                    background
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="queryParams.pageNum"
                    :page-sizes="[20, 50, 100, 200]"
                    :page-size="queryParams.pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total"
                  >
                  </el-pagination>
                    </el-col>
                  </el-row>
                </div>
              </el-form>
            </div>
          </div>
</template>

<script>
import { queryTransportActual } from "@/api/logistics/temporary.js";
export default {
  name: "transportCompoent",
  components: {},
  props: ["id"],
  data() {
    return {
      // 表格数据
      tableData: [],
      // 日期区间
      dateRange: [],
      // 查询参数
      queryParams: {
        // 货物名称
        id: 2,
        //当前页
        pageNum: 1,
        //每页记录数
        pageSize: 20,
      },
      // 总条数
      total: 0,
      //表格字段
      columns: [
        { label: "履运单号", prop: "carExecuteNo", fixed: false },
        { label: "数量", prop: "completePoundNum" },
        { label: "台时", prop: "completeMachineHourNum" },
        { label: "车行", prop: "venderName" },
        { label: "车型", prop: "carTypeName" },
        { label: "车号", prop: "carNo" },
        { label: "称重计划单号", prop: "planPoundListNo" },
      ],
    };
  },

  created() {
    this.handleQuery();
    console.log(this.id);
  },
  watch: {
    id: {
      handler(newName) {
        this.handleQuery();
      },
    },
  },
  methods: {
    //分页改变
    handleSizeChange(val) {
      this.queryParams.pageSize = val;
      this.handleQuery();
    },
    //页码改变
    handleCurrentChange(val) {
      this.queryParams.pageNum = val;
      this.handleQuery();
    },
    //查询
    handleQuery() {
      queryTransportActual(this.id).then((res) => {
        console.log(this.id);
        this.tableData = res.data.data.records;
        this.total = res.data.data.total;
        this.queryParams.pageSize = res.data.data.size;
        this.queryParams.pageNum = res.data.data.current;
      });
    },

    //序号设置
    indexMethod(index) {
      index =
        index + 1 + (this.queryParams.pageNum - 1) * this.queryParams.pageSize;
      return index;
    },
  },
};
</script>

<style scoped>
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
  height: 60vh;
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
