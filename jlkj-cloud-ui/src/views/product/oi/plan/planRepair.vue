<template>
  <div class="app-container">
    <el-form :model="query" ref="query" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="计划日期" prop="planDate">
            <el-date-picker v-model="planDate"
                            type="daterange"
                            start-placeholder="计划开始日期"
                            range-separator="-"
                            end-placeholder="计划结束日期" />
          </el-form-item>
          <el-form-item label="部门" prop="departmentId">
          <selectDeptUserTree v-model="query.departmentId"
                              :selectForm="deptForm"
                              :isSelectNode="true"
                              :isOnlyDept="true"
                              :isClearable="true"
                              :isFilterable="true"
                              placeholder="请选择部门"
                              @change="getDept" />
          </el-form-item>
          <el-form-item label="设备" prop="equipmentName">
            <el-input readonly="readonly"
                      v-model="query.equipmentName"
                      @click.native="treeOpen=true"
                      placeholder="请选择设备" />
          </el-form-item>
          <el-form-item label="维修内容" prop="repairContent">
            <el-input type="text"
                      :rows="5"
                      placeholder="请输入维修内容"
                      v-model="query.repairContent" />
          </el-form-item>
          <el-form-item label="完成状态"
                        prop="planState">
            <el-select v-model="query.planState"
                       placeholder="计划完成状态"
                       clearable>
              <el-option v-for="item in selectStates"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value" />
            </el-select>
          </el-form-item>
          <div class="el-form-item__content"
               style="margin-left: 0px;">
            <el-button size="mini"
                       type="primary"
                       icon="el-icon-search"
                       @click="handleQuery">搜索</el-button>
            <el-button size="mini"
                       icon="el-icon-refresh-left"
                       @click="handleEmpty">重置</el-button>
          </div>
    </el-form>

    <div class="el-form-item__content">
      <el-button type="primary"
                 size="mini"
                 plain
                 icon="el-icon-plus"
                 @click="handleOpenWindow('add')">新增
      </el-button>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="handleQuery"></right-toolbar>
    </div>

    <el-table v-loading="table.loading" :data="tableData" stripe @sort-change="handleSort">
      <template v-for="(column, index) in columns">
        <el-table-column v-if="column.customStyle"
                         :prop="column.prop"
                         :label="column.label"
                         :type="column.type"
                         :key="index"
                         :width="column.width"
                         :min-width="column.minWidth"
                         :sortable="column.sortable">
          <template slot-scope="scope">
            <div v-html="column.customStyle(scope.row,scope.$index,scope)"></div>
          </template>
        </el-table-column>
        <el-table-column v-else
                         :prop="column.prop"
                         :label="column.label"
                         :type="column.type"
                         :key="index"
                         :width="column.width"
                         :min-width="column.minWidth"
                         :sortable="column.sortable"
                         :formatter="column.formatter" />
      </template>
      <el-table-column fixed="right"
                       label="操作"
                       width="350px">
        <template slot-scope="scope">

          <template v-if="scope.row.plan_state === '已完成'">
            <el-button size="mini"
                       icon="el-icon-info"
                       type="text"
                       @click="handleOpenWindow('info',scope.$index, scope.row)">详情
            </el-button>
            <el-button size="mini"
                       icon="el-icon-edit"
                       type="text"
                       disabled>修改
            </el-button>
            <el-button size="mini"
                       icon="el-icon-delete"
                       type="text"
                       disabled>删除
            </el-button>
            <el-button size="mini"
                       icon="el-icon-success"
                       type="text"
                       disabled>完成
            </el-button>
          </template>
          <template v-else>
            <el-button size="mini"
                       icon="el-icon-info"
                       type="text"
                       @click="handleOpenWindow('info',scope.$index, scope.row)"> 详情
            </el-button>
            <el-button size="mini"
                       icon="el-icon-edit"
                       type="text"
                       @click="handleOpenWindow('edit',scope.$index, scope.row)"> 修改
            </el-button>
            <el-button size="mini"
                       icon="el-icon-delete"
                       type="text"
                       @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
            <el-button size="mini"
                       icon="el-icon-success"
                       type="text"
                       @click="handleOpenWindow('over',scope.$index, scope.row)">完成
            </el-button>
          </template>
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

    <div v-if="openDialog.open">
      <el-dialog :title="openDialog.title"
                 :visible.sync="openDialog.open"
                 :width="openDialog.width"
                 class="customDialogStyle"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false">
        <template v-if="openDialog.type === 'add' || openDialog.type === 'edit'">
          <planRepairEdit :data="openDialog.data"
                          :type="openDialog.type"
                          @submitSave="submitSave"
                          @close="openDialog.open=false" />
        </template>
        <template v-if="openDialog.type === 'info'">
          <planRepairInfo :data="openDialog.data"
                          :type="openDialog.type"
                          @close="openDialog.open=false" />
        </template>
        <template v-if="openDialog.type === 'over'">
          <planRepairOver :data="openDialog.data"
                          :type="openDialog.type"
                          @submitSave="submitSave"
                          @close="openDialog.open=false" />
        </template>
      </el-dialog>
    </div>
    <div v-if="treeOpen">
      <equipment-selection v-if="treeOpen"
                           :userDialogVisible="treeOpen"
                           @close-dialog="treeOpen = false"
                           :isMultiple="false"
                           :height="500"
                           @single-select="handleNodeClick" />
    </div>
  </div>
</template>

<script>
import { req } from "@/api/production/oi/common";
import planRepairEdit from "./planRepairEdit";
import planRepairInfo from "./planRepairInfo";
import planRepairOver from "./planRepairOver";
import selectDeptUserTree from "@/views/components/human/selectDeptUserTree";

export default {
  components: {
    planRepairEdit,
    planRepairInfo,
    planRepairOver,
    selectDeptUserTree
  },
  name: "planRepair",
  data () {
    return {
      showSearch: true,
      openDialog: { open: false, type: '', title: '', width: '500px', data: {}, },
      page: { size: 10, current: 1, total: 0, order: "create_time", orderby: "desc", },
      query: {
        startDate: '', endDate: '', departmentId: '', equipmentNumber: '', equipmentName: '', repairContent: '', planState: ''
      },
      table: { border: true, loading: true, },
      tableData: [],
      planDate: [],
      // 下拉
      selectEquipment: [{ value: '1', label: '设备1' }, { value: '2', label: '设备2' },],
      selectStates: [{ value: -1, label: '全部' }, { value: -0, label: '未完成' }, { value: 1, label: '已完成' }],
      deptForm: { value: '', label: '' },

      repairTypeList: ['', '大修', '中修', '小修', '临修', '设备消缺', '工艺技改', '零星修缮', '环保项目'],
      columns: [
        // {label: '编号', prop: "repairPlanNumber", sortable: true, type: 'year', minWidth: '80px'},
        { label: '部门 ', prop: "organization_name", sortable: true, minWidth: '180px' },
        {
          label: '维修类型 ', prop: "repair_type_id", sortable: true, minWidth: '100px', formatter (row) {
            return ['', '大修', '中修', '小修', '临修', '设备消缺', '工艺技改', '零星修缮', '环保项目'][row.repair_type_id];
          }
        },
        { label: '设备名称 ', prop: "equipment_name", sortable: true, minWidth: '100px' },
        { label: '计划执行状态 ', prop: "plan_state", sortable: true, minWidth: '130px' },
        {
          label: '是否影响产量 ',
          prop: "is_affect_output",
          sortable: true,
          minWidth: '130px',
          customStyle: function (row) {
            let color = row.is_affect_output === 1 ? "#409EFF" : "#E6A23C";
            let str = row.is_affect_output === 1 ? "影响" : "未影响";
            return "<li style=\"float: left;font-size: 23px;color:" + color + "\"></li><div style=\"float: left;margin-left: -10px;font-size: 14px;color:#000\">" + str + "</div>"

          },
        },
        { label: '计划开始 ', prop: "plan_start_date", sortable: true, minWidth: '100px' },
        { label: '计划结束 ', prop: "plan_end_date", sortable: true, minWidth: '100px' },
        { label: '预提醒天数 ', prop: "remind_days", sortable: true, minWidth: '120px' },
        { label: '实际开始时间 ', prop: "real_start_date", sortable: true, minWidth: '130px' },
        { label: '实际结束时间 ', prop: "real_end_date", sortable: true, minWidth: '130px' },

        { label: '完成操作人 ', prop: "complete_user_name", sortable: true, minWidth: '120px' },
        { label: '主体责任人 ', prop: "person_main_liable_name", sortable: true, minWidth: '120px' },
        { label: '专业责任人 ', prop: "person_profession_liable_name", sortable: true, minWidth: '120px' },
      ],
      treeOpen: false,

    }
  },
  computed: {
  },
  created () {
    this.onLoad();
  },
  methods: {
    //载入数据
    onLoad () {
      this.table.loading = true;//加载状态
      let data = { ...this.page, ...this.query };
      if (this.query.planState === '') {
        data.planState = -1;
      }
      req('get', 'RepairPlan/list', data).then(res => {
        console.log(res);
        this.table.loading = false;
        this.tableData = res.data.records;//表格数据
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
    handleQuery () {
      this.page.current = 1;
      this.onLoad();
    },
    // 清空
    handleEmpty () {
      this.planDate = [];
      this.query = {
        startDate: '', endDate: '', departmentId: '', equipmentNumber: '', repairContent: '', planState: ''
      };
      this.deptForm = { value: '', label: '' };
      this.onLoad();
    },
    // 打开窗口
    handleOpenWindow (type, index, row) {
      let title = '维修计划';
      this.openDialog.type = type;
      this.openDialog.open = true;
      this.openDialog.data = row;
      if (type === 'add') {
        this.openDialog.title = '新增' + title;
        this.openDialog.width = '750px';
        this.openDialog.data = {};
      } else if (type === 'edit') {
        this.openDialog.title = '编辑' + title;
        this.openDialog.width = '750px';
      } else if (type === 'info') {
        this.openDialog.title = '查看' + title;
        this.openDialog.width = '850px';
      } else if (type === 'over') {
        this.openDialog.title = '完成' + title;
        this.openDialog.width = '850px';
      } else {
        this.openDialog.open = false;
      }
    },
    // 删除
    handleDelete (index, row) {
      this.$confirm('此操作将删除[' + row.organization_name + ']的[' + this.repairTypeList[row.repair_type_id] + ']计划, 是否继续?', '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        req('post', 'RepairPlan/delete', {
          id: row.id,
          deleteUserId: this.$store.state.user.userInfo.userName,
          deleteUserName: this.$store.state.user.userInfo.nickName,
        }).then(res => {
          if (res.code == 200) {
            this.$message({
              type: "success", message: "操作成功！", duration: 1000,
              onClose: () => {
                this.onLoad();
              }
            });
          }
        }, error => {
          window.console.log(error);
        });
      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    //提供给子类调用的方法
    submitSave () {
      this.openDialog.open = false;
      this.openDialog.data = {};
      this.onLoad();
    },
    getDept (obj) {
      this.query.departmentId = obj.value;
    },
    handleNodeClick (data) {
      // console.log(data)
      this.query.equipmentNumber = data.id;
      this.query.equipmentName = data.equipmentName;
      this.treeOpen = false;
    },
  },
  // 侦听器
  watch: {
    planDate (newValue) {
      if (newValue.length > 0) {
        this.query.startDate = this.$moment(newValue[0]).format('YYYY-MM-DD');
        this.query.endDate = this.$moment(newValue[1]).format('YYYY-MM-DD');
      } else {
        this.query.startDate = "";
        this.query.endDate = "";
      }
    },
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
