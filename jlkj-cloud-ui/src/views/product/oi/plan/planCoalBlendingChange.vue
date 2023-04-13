<template>
  <div class="avue-crud el-card__body">
    <div>
      <el-form>
        <el-table height="55vh" size="small" v-loading="table.loading" :data="tableData" stripe
                  @sort-change="handleSort" style="width: 100%">
          <template v-for="(column,index)  in columns">
            <el-table-column v-if="column.customStyle" :key="index"
                             :prop="column.prop" :label="column.label" :type="column.type"
                             :width="column.width" :min-width="column.minWidth" :sortable="column.sortable">
              <template slot-scope="scope">
                <div v-html="column.customStyle(scope.row,scope.$index,scope)"></div>
              </template>
            </el-table-column>
            <el-table-column v-else :key="index"
                             :prop="column.prop" :label="column.label" :type="column.type"
                             :width="column.width" :min-width="column.minWidth" :sortable="column.sortable"
                             :formatter="column.formatter"/>
          </template>
          <el-table-column fixed="right" label="操作" width="120px">
            <template slot-scope="scope">
              <el-button size="mini" plain icon="el-icon-success" type="warning"
                         @click="handleConfirm(scope.row)"> 切换
              </el-button>

            </template>
          </el-table-column>
        </el-table>
        <div class="el-dialog__footer">
        <span slot="footer" class="dialog-footer" style="margin-top:10px">
          <el-button @click="cancel">取 消</el-button>
        </span>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {req} from "@/api/production/oi/common";
import planCoalBlendingEdit from "./planCoalBlendingEdit";
import planCoalBlendingInfo from "./planCoalBlendingInfo";
import {mapGetters} from "vuex";
import {listMaterialsBoxJ} from "@/api/material/mr/parameter/materialCode";

export default {
  components: {
    planCoalBlendingEdit,
    planCoalBlendingInfo,
  },
  name: "planCoalBlending",
  data() {
    return {
      openDialog: {open: false, type: '', title: '', width: '500px', data: {},},
      page: {size: 10000, current: 1, total: 1, order: "create_time", orderby: "desc",},
      query: {plan_start_time: '', plan_end_time: '', plan_state: 2, materialId: '', planNumber: ''},
      table: {border: true, loading: true,},
      columns: [
        {label: '计划编号', prop: "plan_number", sortable: true, type: 'year', minWidth: '100px'},
        {label: '焦炭等级', prop: "material_name", sortable: true, minWidth: '150px'},
        {label: '计划开始时间', prop: "plan_start_time", sortable: true, minWidth: '150px'},
        {label: '计划状态', prop: "plan_state", sortable: true, minWidth: '100px'},
      ],
      tableData: [],
    }
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  created() {
    this.onLoad();
  },
  methods: {
    //载入数据
    onLoad() {
      this.table.loading = true;//加载状态
      let data = {...this.page, ...this.query};
      req('get', 'listProductionCfgCokePlans', data).then(res => {
        this.table.loading = false;
        this.tableData = res.data.data.records;//表格数据
        this.page.total = res.data.data.total;

      }, error => {
        this.table.loading = false;
        window.console.log(error);
      });
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
    handleConfirm(row) {
      this.$confirm('是否切换到【' + row.plan_number + '】的配煤计划，切换配煤计划后将无法修改, 是否继续?', '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        req('post', 'changeProductionPlanCfgCokeConfirm', {
          id: row.id, receive_user_id: this.$store.getters.userInfo.userId, receive_user_name: this.userInfo.userName,
        }).then(res => {
          if (res.code == 200) {
            this.$message({
              type: "success", message: "操作成功！", duration: 1000,
              onClose: () => {
                this.$emit('submitSave')
              }
            });
          }
        }, error => {
          window.console.log(error);
        });
      });      },
    cancel() {
      this.$emit('close')
    }


  },
}
</script>

<style scoped>

</style>
