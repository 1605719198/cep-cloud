<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-button type="primary"
                 plain
                 size="mini"
                 icon="el-icon-plus"
                 @click="handleOpenWindow('add')">新增
      </el-button>
    </el-row>

    <el-table v-loading="table.loading" :data="tableData" stripe>
      <template v-for="(column,index) in columns">
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
                         :formatter="column.formatter" />
      </template>
      <el-table-column fixed="right"
                       label="操作"
                       width="180px">
        <template slot-scope="scope">
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
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="page.total>0"
      :total="page.total"
      :page.sync="page.current"
      :limit.sync="page.size"
      @pagination="onLoad"
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
          <cokeOvenHoleYieldmEdit :type="openDialog.type"
                                  :data="openDialog.data"
                                  @submitSave="submitSave"
                                  @close="openDialog.open=false" />
        </template>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import { listProductionSingleHoleYield, delProductionSingleHoleYield } from "@/api/production/oi/parameter";
import cokeOvenHoleYieldmEdit from "./cokeOvenHoleYieldmEdit";

export default {
  components: {
    cokeOvenHoleYieldmEdit,
  },
  name: "cokeOvenHoleYield",
  data () {
    return {
      openDialog: {
        open: false,
        type: '',
        title: '',
        width: '500px',
        data: {},
      },
      page: {
        size: 20,
        current: 1,
        total: 1,
        order: "create_time",
        orderby: "asc",
      },
      query: {},
      table: {
        border: true,
        loading: true,
      },
      columns: [
        { label: '序号', type: 'index', width: '80px' },
        {
          label: '产量', prop: "yield", sortable: true, minWidth: '150px', formatter (row) {
            return row.yield + '千克';
          }
        },
        { label: '创建人', prop: "create_user_name", sortable: true, minWidth: '100px' },
        { label: '创建时间', prop: "create_time", sortable: true, minWidth: '150px' },
        { label: '修改人', prop: "modify_user_name", sortable: true, minWidth: '100px' },
        { label: '修改时间', prop: "modify_time", sortable: true, minWidth: '150px' },
      ],
      tableData: [],
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
      listProductionSingleHoleYield(this.page, this.query).then(res => {
        this.table.loading = false;
        let data = res.data;//表格相关数据
        this.page.total = data.total;//数据总数
        this.tableData = data.records;//表格数据
      }, error => {
        this.table.loading = false;
        window.console.log(error);
      });

    },
    // 排序
    handleSort (column) {
      if (column.order === null) {
        //默认
        this.page.order = "create_time";
        this.page.orderby = "asc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.onLoad();
    },
    //查询
    handleQuery () {
      this.onLoad();
    },
    // 清空
    handleEmpty () {
      this.query = {}
    },
    // 打开窗口
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
    // 删除
    handleDelete (index, row) {
      this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        delProductionSingleHoleYield({
          id: row.id,
          delete_user_id: this.$store.state.user.userInfo.userName,
          delete_user_name: this.$store.state.user.userInfo.nickName,
        }).then(res => {
          this.onLoad();
          this.$message({ type: 'success', message: res.msg });
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

