<!--
  上煤记录
-->
<template>
  <div class="avue-crud el-card__body ">
    <div>
      <el-table height="65vh"
                size="small"
                v-loading="table.loading"
                :data="tableData"
                stripe
                @sort-change="handleSort">
        <el-table-column label="序号"
                         type="index"
                         width="55"
                         align="center" />
        <el-table-column label="班次"
                         sortable
                         minWidth="150"
                         align="left"
                         prop="shift_name"
                         :formatter="shiftFormatter" />
        <el-table-column label="班别"
                         sortable
                         minWidth="150"
                         align="left"
                         prop="class_name" />
        <el-table-column label="配煤计划编号"
                         minWidth="300"
                         align="left"
                         prop="coal_blending_plan_number" />
        <el-table-column label="焦炭等级名称"
                         minWidth="150"
                         align="left"
                         prop="coal_level_materials_name" />
        <el-table-column label="储煤塔号"
                         minWidth="150"
                         align="left"
                         prop="coal_tower_number" />
        <el-table-column label="开始时间"
                         minWidth="150"
                         align="left"
                         prop="start_time" />
        <el-table-column label="结束时间"
                         minWidth="150"
                         align="left"
                         prop="end_time" />
        <el-table-column label="配煤重量(kg)"
                         minWidth="150"
                         align="left"
                         prop="coal_weight" />
        <el-table-column label="配煤前重量(kg)"
                         minWidth="150"
                         align="left"
                         prop="coal_weight_befor" />
        <el-table-column label="配煤后重量(kg)"
                         minWidth="150"
                         align="left"
                         prop="coal_weight_after" />
<!--        <el-table-column label="配煤前料位(m)"-->
<!--                         minWidth="150"-->
<!--                         align="left"-->
<!--                         prop="coal_material_level_befor" />-->
<!--        <el-table-column label="配煤后料位(m)"-->
<!--                         minWidth="150"-->
<!--                         align="left"-->
<!--                         prop="coal_material_level_after" />-->
        <el-table-column label="数据来源"
                         minWidth="150"
                         align="left"
                         prop="data_sources"
                         :formatter="sourcesFormatter" />
        <el-table-column label="创建人"
                         minWidth="150"
                         align="left"
                         prop="create_user_name" />
        <el-table-column label="创建时间"
                         minWidth="150"
                         align="left"
                         prop="create_time"
                         sortable />
        <el-table-column label="上班时间"
                         minWidth="150"
                         align="left"
                         prop="shift_start_time"
                         sortable />
        <el-table-column label="下班时间"
                         minWidth="150"
                         align="left"
                         prop="shift_end_time"
                         sortable />
        <el-table-column fixed="right"
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
            <el-button size="mini"
                       plain
                       type="info"
                       icon="el-icon-info"
                       @click="handleView('view',scope.$index, scope.row)">详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 10px;text-align: right;padding: 25px 0px 20px 20px;"
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
    <div class="el-dialog__footer"
         style="padding:10px 0 0 0;">
      <span slot="footer"
            class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </span>
    </div>
    <!-- 弹窗 -->
    <div v-if="dialog.visible">
      <el-dialog :title="dialog.title"
                 :visible.sync="dialog.visible"
                 :width="dialog.width"
                 class="customDialogStyle"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false">
        <template>
          <Views v-if="dialog.type === 'view'"
                 :type="dialog.type"
                 :data="dialog.data"
                 @submitSave="submitSave"
                 @close="dialog.visible=false" />
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import Views from "./coalBlending"
import { getTowerBlendDetailList, delManualConfigCoal } from "@/api/production/oi/handCoalBlending";
export default {
  components: {
    Views
  },
  props: {
    type: {
      type: String
    },
    data: {
      type: Object
    }
  },
  data () {
    return {
      dialog: {
        visible: false,
        type: '',
        title: '',
        width: '60%',
        data: {},
      },
      page: {
        size: 20,
        current: 1,
        total: 0,
        order: "create_time",
        orderby: "desc",
      },
      table: {
        border: true,
        loading: false,
      },
      tableData: [{}],
    }
  },
  created () {
    this.onLoad();
  },
  computed: {
  },
  methods: {
    //载入数据
    onLoad () {
      let query = {
        tower: this.data.coal_tower_number
      }
      this.table.loading = true;//加载状态
      getTowerBlendDetailList(this.page, query).then(res => {
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
        this.page.order = "create_time";
        this.page.orderby = "desc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.onLoad();
    },
    //详情
    handleView (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '详情'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    // 删除
    handleDelete (index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        delManualConfigCoal({
          id: row.id,
          user_id: this.$store.getters.userInfo.userId,
          user_name: this.$store.getters.userInfo.nickName,
        }).then(res => {
          if (res.code === 200) {
            this.onLoad();
            this.$message({ type: 'success', message: res.msg });
            this.$emit('sumRefresh')
          }
        }, error => {
          window.console.log(error);
        });
      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    shiftFormatter (row) {
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
    },
    sourcesFormatter (row) {
      if (row.data_sources == '0') {
        return '手工填报'
      } else {
        return '数据采集'
      }
    },
    //提供给子类调用的方法
    submitSave () {
      this.dialog.visible = false;
      this.dialog.data = {};
      this.onLoad();
    },
    cancel () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped lang="scss">
</style>

