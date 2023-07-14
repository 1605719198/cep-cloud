<template>
  <div class="avue-crud el-card__body" style="width: 98%;border: 0">
    <div class="avue-crud__search" style="border: 0">
      <el-form :model="query" ref="query" :inline="true">
            <el-form-item label="计划编号" prop="planNumber">
              <el-input type="text" placeholder="计划编号" size="small" v-model="query.planNumber" clearable/>
            </el-form-item>
            <el-form-item label="计划日期" prop="planDate">
              <el-date-picker v-model="planDate" type="daterange" size="small" start-placeholder="计划开始日期" range-separator="-"
                              end-placeholder="计划结束日期"/>
            </el-form-item>
            <el-form-item label="状态" prop="planState">
              <el-select v-model="planState" size="small" placeholder="选择状态" clearable>
                <el-option v-for="item in selectStates" :key="item.value" :label="item.label"
                           :value="item.value"/>
              </el-select>
            </el-form-item>
            <el-form-item label="焦炭等级" prop="materialId">
              <el-select v-model="query.materialId" size="small" placeholder="选择焦炭等级" clearable>
                <el-option v-for="item in selectCokeType" :key="item.id" :label="item.materials_name" :value="item.id"/>
              </el-select>
            </el-form-item>

            <div class="el-form-item__content" style="margin-left: 0px;">
              <el-button v-hasPermi="['listProductionCfgCokePlans']"
                         size="mini" type="primary" icon="el-icon-search" @click="handleQuery">搜 索
              </el-button>
              <el-button size="mini" type="default" icon="el-icon-refresh-left" @click="handleEmpty">重 置
              </el-button>
            </div>
            <el-row class="mb8">
              <el-button v-hasPermi="['addProductionPlanCfgCoke']"
                         type="primary" size="mini" plain icon="el-icon-plus" @click="handleOpenWindow('add')">新增
              </el-button>
              <el-button v-hasPermi="['addProductionPlanCfgCoke']" type="primary" size="mini"
                         @click="handleOpenWindow('changePlanCoal')">手动切配煤计划
              </el-button>
            </el-row>
      </el-form>
    </div>
    <div>
      <el-form>
        <el-table height="65vh" size="small" v-loading="table.loading" :data="tableData" stripe
                  @sort-change="handleSort">
          <template v-for="(column,index)  in columns">
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
                             :formatter="column.formatter"/>
          </template>
          <el-table-column fixed="right" label="操作" width="360px">
            <template slot-scope="scope">
              <template v-if="scope.row.plan_state === '未确认'">
                <el-button v-hasPermi="['listProductionCfgCokePlans']"
                           size="mini" icon="el-icon-info" type="text"
                           @click="handleOpenWindow('info',scope.$index, scope.row)"> 详情
                </el-button>
                <el-button v-hasPermi="['editProductionPlanCfgCoke']"
                           size="mini" icon="el-icon-edit" type="text"
                           @click="handleOpenWindow('edit',scope.$index, scope.row)"> 修改
                </el-button>
                <el-button v-hasPermi="['delProductionPlanCfgCoke']"
                           size="mini" icon="el-icon-delete" type="text"
                           @click="handleDelete(scope.$index, scope.row)">删除
                </el-button>
                <el-button v-hasPermi="['editProductionPlanCfgCoke']"
                           size="mini" icon="el-icon-success" type="text"
                           @click="handleConfirm(scope.$index, scope.row)"> 确认
                </el-button>
              </template>
              <template v-else>
                <el-button v-hasPermi="['listProductionCfgCokePlans']"
                           size="mini" icon="el-icon-info" type="text"
                           @click="handleOpenWindow('info',scope.$index, scope.row)">详情
                </el-button>
                <el-button v-hasPermi="['editProductionPlanCfgCoke']"
                           size="mini" icon="el-icon-edit" type="text" disabled>修改
                </el-button>
                <el-button v-hasPermi="['delProductionPlanCfgCoke']"
                           size="mini" icon="el-icon-delete" type="text" disabled>删除
                </el-button>
                <el-button v-hasPermi="['editProductionPlanCfgCoke']"
                           size="mini" icon="el-icon-success" type="text" disabled>已确认
                </el-button>
              </template>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px;float: right;padding:25px 0 20px 20px ;" class="avue-crud__pagination">
          <el-pagination background
                         @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"
                         layout="total, sizes, prev, pager, next, jumper"
                         :current-page="page.current"
                         :page-sizes="[20, 50, 100, 200]"
                         :page-size="page.size"
                         :total="page.total">
          </el-pagination>
        </div>
      </el-form>
    </div>
    <div v-if="openDialog.open">
      <el-dialog :title="openDialog.title" :visible.sync="openDialog.open" :width="openDialog.width"
                 class="customDialogStyle" append-to-body :destroy-on-close="true" :close-on-click-modal="false">
        <template v-if="openDialog.type === 'add' || openDialog.type === 'edit'">
          <planCoalBlendingEdit :data="openDialog.data" @submitSave="submitSave" @close="openDialog.open=false"/>
        </template>
        <template v-if="openDialog.type === 'info'">
          <planCoalBlendingInfo :data="openDialog.data" @close="openDialog.open=false"/>
        </template>
        <template v-if="openDialog.type === 'changePlanCoal'">
          <planCoalBlendingChange :data="openDialog.data" @submitSave="submitSave" @close="openDialog.open=false"/>
        </template>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import {req} from "@/api/production/oi/common";
import planCoalBlendingEdit from "./planCoalBlendingEdit";
import planCoalBlendingInfo from "./planCoalBlendingInfo";
import planCoalBlendingChange from "./planCoalBlendingChange";
import {listMaterialsBoxJ} from "@/api/material/mr/parameter/materialCode";

export default {
  components: {
    planCoalBlendingEdit,
    planCoalBlendingInfo,
    planCoalBlendingChange,
  },
  name: "planCoalBlending",
  data() {
    return {
      openDialog: {open: false, type: '', title: '', width: '500px', data: {},},
      page: {size: 20, current: 1, total: 1, order: "create_time", orderby: "desc",},
      query: {planStartTime: '', planEndTime: '', planState: 0, materialId: '', planNumber: ''},
      table: {border: true, loading: true,},
      planState: "", planDate: [],
      selectStates: [
        {value: '1', label: '未确认'},
        {value: '2', label: '已确认'},
        {value: '3', label: '执行中'},
        {value: '4', label: '已完成'},
      ],
      selectCokeType: [],
      columns: [
        {label: '计划编号', prop: "plan_number", sortable: true, type: 'year', minWidth: '100px'},
        // {label: '储煤塔号', prop: "tower_number", sortable: true, minWidth: '150px'},
        {label: '焦炭等级', prop: "material_name", sortable: true, minWidth: '150px'},
        {label: '开始时间', prop: "plan_start_time", sortable: true, minWidth: '150px'},
        {label: '结束时间', prop: "plan_end_time", sortable: true, minWidth: '150px'},
        {label: '计划状态', prop: "plan_state", sortable: true, minWidth: '100px'},
        // {label: '接收人', prop: "receive_user_name", sortable: true, minWidth: '150px'},
        // {label: '接收确认时间', prop: "receive_time", sortable: true, minWidth: '150px'},
        {label: '创建人', prop: "create_user_name", sortable: true, minWidth: '150px'},
        {label: '创建时间', prop: "create_time", sortable: true, minWidth: '150px'},
        {label: '确认人', prop: "receive_user_name", sortable: true, minWidth: '150px'},
        {label: '确认时间', prop: "receive_time", sortable: true, minWidth: '150px'},
      ],
      tableData: [],
    }
  },
  computed: {
  },
  created() {
    listMaterialsBoxJ().then(res => {
      // this.selectCokeType = res.data;//表格数据
      this.selectCokeType = res.data.filter(item => {
        return item.materials_code.substring(0, 5) === '01501';
      });
    }, error => {
      window.console.log(error);
    });
    this.onLoad();
  },
  methods: {
    //载入数据
    onLoad() {
      this.table.loading = true;//加载状态
      let data = {...this.page, ...this.query};
      req('get', 'listProductionCfgCokePlans', data).then(res => {
        this.table.loading = false;
        this.tableData = res.data.records;//表格数据
        this.page.total = res.data.total;

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
    //查询
    handleQuery() {
      this.page.current = 1;
      this.onLoad();
    },
    // 清空
    handleEmpty() {
      this.planDate = [];
      this.query = {planStartTime: '', planEndTime: '', planState: 0,materialId:''};
      this.planState = '';
      this.onLoad();
    },
    // 打开窗口
    handleOpenWindow(type, index, row) {
      let title = '配煤计划';
      this.openDialog.open = true;
      this.openDialog.type = type;
      this.openDialog.width = '1160px';
      this.openDialog.data = row;
      if (type === 'add') {
        this.openDialog.title = '新增' + title;
        this.openDialog.data = {};
      } else if (type === 'edit') {
        this.openDialog.title = '编辑' + title;
      } else if (type === 'info') {
        this.openDialog.title = '查看' + title;
        this.openDialog.width = '850px';
      } else if (type === 'changePlanCoal') {
        this.openDialog.title = '查看' + title;
        this.openDialog.width = '850px';
      } else {
        this.openDialog.open = false;
        this.openDialog.data = {};
      }
    },
    // 删除
    handleDelete(index, row) {
      this.$confirm('此操作将删除计划编号为' + row.plan_number + '的配煤计划, 是否继续?', '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        req('post', 'delProductionPlanCfgCoke', {
          id: row.id,
          deleteUserId: this.$store.getters.userInfo.userId,
          deleteUserName: this.$store.getters.userInfo.userName,
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
        this.$message({type: 'info', message: '已取消删除'});
      });
    },
    //提供给子类调用的方法
    submitSave() {
      this.openDialog.open = false;
      this.openDialog.data = {};
      this.onLoad();
    },
    handleConfirm(index, row) {
      this.$confirm('是否确认计划编号为' + row.plan_number + '的配煤计划，确认后的配煤计划将无法修改, 是否继续?', '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        req('post', 'updateProductionPlanCfgCokeConfirm', {
          id: row.id, receive_user_id: this.$store.getters.userInfo.userId, receive_user_name: this.$store.getters.userInfo.userName,
        }).then(res => {
          this.$message({
            type: "success", message: "确认成功！", duration: 1000,
            onClose: () => {
              this.onLoad();
            }
            });
        }, error => {
          window.console.log(error);
        });
      }).catch(() => {
      });
    },
    changePlanCoal() {

    },


  },
  // 侦听器
  watch: {
    planDate(newValue) {
      if (newValue !== null && newValue.length > 0) {
        this.query.planStartTime = this.$moment(newValue[0]).format('YYYY-MM-DD');
        this.query.planEndTime = this.$moment(newValue[1]).format('YYYY-MM-DD');
      } else {
        this.query.planStartTime = "";
        this.query.planEndTime = "";
      }
    },
    planState(newValue) {
      if (newValue === '') {
        this.query.planState = 0;
      } else {
        this.query.planState = newValue;
      }

    }
  }
}
</script>

<style scoped>
.el-button--success {
  background-color: #b3e19d;
  color: white;
  border: white;
}
</style>
