<!--
  产量异常台账
-->
<template>
  <div class="avue-crud el-card__body"
       style="width: 98%;border: 0">
    <!-- 操作 -->
    <div class="avue-crud__search"
         style="border: 0">
      <el-form :inline="true">
        <el-row>
            <el-form-item label="日期">
              <el-date-picker v-model="query.daterange"
                              type="daterange"
                              unlink-panels
                              range-separator="至"
                              start-placeholder="开始日期"
                              end-placeholder="结束日期"
                              value-format="yyyy-MM-dd" />
              <!-- 年-月-日 -->
              <!-- unlink-panels ————>左右独立选择年月日 -->
            </el-form-item>

            <el-form-item prop="department_name" label="主体单位">
              <el-input v-model="query.department_name"
                        placeholder="主体单位">
              </el-input>
            </el-form-item>

            <div class="el-form-item__content"
                 style="margin-left: 0px;">
              <el-button v-hasPermi="['getProductionAbnormalLedger']"
                         size="mini"
                         type="primary"
                         icon="el-icon-search"
                         @click="handleQuery">搜 索</el-button>
              <el-button size="mini"
                         type="default"
                         icon="el-icon-refresh"
                         @click="handleEmpty">重 置</el-button>
            </div>
        </el-row>
        <div class="el-form-item__content">
          <el-button v-hasPermi="['addProductionAbnormalLedger']"
                     type="primary"
                     size="mini"
                     plain
                     icon="el-icon-plus"
                     @click="handleOpenWindow('add')">新增
          </el-button>
        </div>
      </el-form>
    </div>
    <!-- 内容 -->
    <div>
      <el-form>
        <el-table height="65vh"
                  size="small"
                  :data="tableData"
                  stripe
                  :default-sort="{prop: 'number', order: 'descending'}"
                  :cell-style="changeTableCellStyle">
          <template v-for="column in columns">
            <el-table-column :prop="column.prop"
                             :type="column.type"
                             :label="column.label"
                             :sortable="column.sortable"
                             :width="column.width"
                             :min-width="column.minWidth"
                             :key="column.prop"
                             show-overflow-tooltip />
          </template>
          <!-- fixed="right" -->
          <el-table-column align="center"
                           label="操作"
                           width="180px">
            <template slot-scope="scope">
              <el-button v-hasPermi="['updateProductionAbnormalLedger']"
                         size="mini"
                         icon="el-icon-edit"
                         type="text"
                         @click="handleOpenWindow('edit',scope.$index, scope.row)">编辑
              </el-button>
              <el-button v-hasPermi="['delProductionAbnormalLedger']"
                         v-if="scope.row.measures_implementation === '未完成'"
                         size="mini"
                         icon="el-icon-delete"
                         type="text"
                         @click="handleDelete(scope.$index, scope.row)">删除</el-button>

              <el-button v-hasPermi="['delProductionAbnormalLedger']"
                         v-if="scope.row.measures_implementation === '已完成'"
                         size="mini"
                         icon="el-icon-delete"
                         type="text"
                         disabled>删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px;float: right;padding:25px 0 20px 20px ;"
             class="avue-crud__pagination">
          <el-pagination background
                         @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"
                         :current-page="page.current"
                         :page-sizes="[20, 50, 100, 200]"
                         :page-size="page.size"
                         layout="total, sizes, prev, pager, next, jumper"
                         :total="page.total">
          </el-pagination>
        </div>
      </el-form>
    </div>
    <!-- 弹窗 -->
    <div v-if="openDialog.open">
      <el-dialog :title="openDialog.title"
                 :visible.sync="openDialog.open"
                 :width="openDialog.width"
                 class="customDialogStyle"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false">
        <template v-if="openDialog.type === 'add' || openDialog.type === 'edit'">
          <AccidentReportEditor :type="openDialog.type"
                                :data="openDialog.data"
                                @submitSave="submitSave"
                                @close="openDialog.open=false" />
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import AccidentReportEditor from "./AccidentReportEditor";
import { delYa, selYa } from '@/api/production/oi/YA.js';

export default {
  name: "YieldExceptionAccount",
  //组件注册
  components: {
    AccidentReportEditor,
  },
  // 注册属性
  props: {},

  computed: {
  },
  data () {
    return {
      page: {
        size: 20,
        current: 1,
        total: 1,
        order: "happen_time",
        orderby: "desc",
        department_name: '',
        happen_time: ''
      },
      //弹窗
      openDialog: {
        open: false,
        type: "",
        title: "",
        width: '800px',
        data: {}
      },
      //搜索

      query: {
        happen_time: '',
        daterange: [],
        planStartTime: '',
        planEndTime: '',
        departmentName: '',
      },
      table: {
        border: true
      },
      columns: [
        { label: '序号', type: 'index', sortable: true, minWidth: '80px' },
        { label: '主体单位', prop: "department_name", sortable: false, minWidth: '100px' },
        { label: '发生时间', prop: "happen_time", sortable: false, minWidth: '150px' },
        { label: '事故名称', prop: "accident_name", sortable: false, minWidth: '120px' },
        { label: '防范措施描述', prop: "preventive_measures", sortable: false, minWidth: '170px' },
        { label: '计划完成时间', prop: "measures_completion_time", sortable: false, minWidth: '100px' },
        { label: '措施落实情况', prop: "measures_implementation", sortable: false, minWidth: '100px' },
        { label: '专业责任人', prop: "professional_responsible_person", sortable: false, minWidth: '100px' },
        { label: '跟踪责任人', prop: "track_responsible_person", sortable: false, minWidth: '100px' },
      ],
      tableData: [],
      form: {},
      currentPage: 0,
      total: 0,
    }
  },
  // created 在模板渲染成html前调用，即通常初始化某些属性值，然后再渲染成视图
  created () {
    this.onLoad();
  },
  methods: {
    //如果数据库返回的参数和接口的参数不一致的写法
    //首页打印——————查询接口
    //接口参数如下
    // {
    //   planStartTime
    //   planEndTime
    //   departmentName
    //   size
    //   current
    //   order
    //   orderBy
    // }
    onLoad () {
      selYa({
        size: this.page.size,
        current: this.page.current,
        order: this.page.order,
        orderby: this.page.orderby,
        departmentName: this.page.department_name,
        planStartTime: this.page.planStartTime,
        planEndTime: this.page.planEndTime
      }).then(res => {
        // console.log(res)
        this.tableData = res.data.records;
        this.page.total = res.data.total;
        if (this.tableData.length === 0) {
          this.$message({ type: "warning", message: "暂无数据" });
        }
      }, error => {
        window.console.log(error);
      })
    },
    //查询功能————查询接口
    handleQuery () {
      let query = this.query
      let page = this.page;
      let params = {
        "departmentName": query.department_name,
        "planStartTime": query.daterange[0] === '' ? '' : this.query.daterange[0],
        "planEndTime": query.daterange[1] === '' ? '' : this.query.daterange[1],
        "size": page.size,
        "current": page.current,
        "order": page.order,
        "orderby": page.orderby,
        "happenTime": query.happen_time
      }
      selYa(params).then(res => {
        console.log(res)
        this.tableData = res.data.data.records;
        this.page.total = res.data.data.total;
        if (this.tableData.length === 0) {
          this.$message({ type: "warning", message: "暂无数据" });
        }
      }, error => {
        window.console.log(error);
      })
    },
    // 重置
    handleEmpty () {
      this.query = {
        departmentName: '',
        daterange: '',
      }
      this.onLoad();
    },
    //不知道干嘛的
    changeTableCellStyle ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 9) {
        return 'color:#00A0FF'
      }
    },
    //打开弹窗
    handleOpenWindow (type, index, row) {
      let title = '指标项';
      this.openDialog.open = true;
      this.openDialog.type = type;
      this.openDialog.width = '600px';
      this.openDialog.data = row;
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
    //点击弹窗（弃）
    // handleOpenWindow (type, index, row) {
    //   let title = "产量异常台账"
    //   this.openDialog.open = true;
    //   this.openDialog.data = row;
    //   switch (tag) {
    //     case "add":
    //       this.openDialog.type = tag;
    //       this.openDialog.title = "新增" + title;
    //       this.openDialog.data = {};
    //       Object.defineProperty(this.openDialog.data, "type", {
    //         value: tag
    //       });

    //       break
    //     case "edit":
    //       this.openDialog.type = tag;
    //       this.openDialog.title = "编辑" + title;
    //       Object.defineProperty(this.openDialog.data, "type", {
    //         value: tag
    //       });
    //       break
    //     default:
    //       this.openDialog.open = false;
    //       this.openDialog.data = {};
    //       Object.defineProperty(this.openDialog.data, "type", {
    //         value: tag
    //       });
    //   }
    // },
    //删除
    handleDelete (index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        delYa({
          id: row.id,
          delete_user_id: this.$store.getters.userInfo.userId,
          delete_user_name: this.$store.getters.userInfo.nickName,
        }).then(res => {
          this.onLoad();
          this.$message({ type: 'success', message: "删除成功" });
        }, error => {
          window.console.log(error);
        });
      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    // 分页-每页多少条
    handleSizeChange (val) {
      // console.log(`每页 ${val} 条`);
      this.page.size = val;
      this.handleQuery();
    },
    // 分页-当前页
    handleCurrentChange (val) {
      // console.log(`当前页: ${val}`);
      this.page.current = val;
      this.handleQuery();
    },
    //提供给子类调用的方法
    submitSave (msg) {
      this.openDialog.open = false;
      this.openDialog.data = {};
      this.onLoad();
    },
  }
}
</script>

<!-- tooltip文字提示样式 -->
<style>
.el-tooltip__popper {
  font-size: 14px;
  max-width: 50%;
}
</style>
