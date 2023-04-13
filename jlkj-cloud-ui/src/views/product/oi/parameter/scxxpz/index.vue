<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-button type="primary" plain size="mini" icon="el-icon-plus" @click="handleAdd('add')">新增</el-button>
    </el-row>

    <el-table v-loading="table.loading" :data="tableData" stripe @sort-change="handleSort">
      <el-table-column label="序号" type="index" width="55" align="center" />
      <el-table-column label="焦炭等级" sortable minWidth="150" align="left" prop="materials_name" />
      <el-table-column label="熄焦方式" sortable minWidth="150" align="left" prop="quenching_method"
                       :formatter="quenchingMethodFormatter" />
      <el-table-column label="单孔理论重量(kg)" sortable minWidth="150" align="left" prop="single_hole_weight" />
      <el-table-column label="理论单耗" sortable minWidth="150" align="left" prop="single_consumption" />
      <el-table-column fixed="right" label="操作" width="180" align="center">
        <template slot-scope="scope">
          <el-button size="mini" icon="el-icon-edit" type="text"
                     @click="handleEdit('edit', scope.$index, scope.row)">修改
          </el-button>
          <el-button size="mini" icon="el-icon-delete" type="text"
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

    <!-- 弹窗 -->
    <div v-if="dialog.visible">
      <el-dialog :title="dialog.title" :visible.sync="dialog.visible" :width="dialog.width" class="customDialogStyle"
        append-to-body :destroy-on-close="true" :close-on-click-modal="false">
        <template>
          <Update v-if="dialog.type === 'add' || dialog.type === 'edit'" :type="dialog.type" :data="dialog.data"
            @submitSave="submitSave" @close="dialog.visible = false" />
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { getProductionInformationConfigurationPage, deleteProductionInformationConfiguration } from "@/api/production/oi/parameter";
import Update from "./update"

export default {
  components: {
    Update
  },
  data() {
    return {
      dialog: {
        visible: false,
        type: '',
        title: '',
        width: '40%',
        data: {},
      },
      page: {
        size: 10,
        current: 1,
        total: 1,
        order: "create_time",
        orderby: "desc",
      },
      query: {},
      table: {
        border: true,
        loading: false,
      },
      tableData: [],
    }
  },
  computed: {
  },
  created() {
    this.onLoad();
  },
  methods: {
    //载入数据
    onLoad() {
      this.table.loading = true;//加载状态
      getProductionInformationConfigurationPage(this.page, this.query).then(res => {
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
      // console.log(this.query.issueDate)
      this.onLoad();
    },
    // 重置
    handleReset() {
      this.page.current = 1
      this.$refs["query"].resetFields();
      this.handleQuery()
    },
    //新增
    handleAdd(type) {
      this.dialog.type = type
      this.dialog.title = '新增生产信息配置'
      this.dialog.data = {};
      this.dialog.visible = true;
    },
    //修改
    handleEdit(type, index, row) {
      this.dialog.type = type
      this.dialog.title = '编辑生产信息配置'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    // 删除
    handleDelete(index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        deleteProductionInformationConfiguration({
          id: row.id,
          delete_user_id: this.$store.getters.userInfo.userId,
          delete_user_name: this.$store.getters.userInfo.nickName,
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
    //格式化熄焦方式
    quenchingMethodFormatter(row) {
      switch (row.quenching_method) {
        case "1":
          return "干熄";
        case "2":
          return "湿熄";
        default:
          return row.quenching_method;
      }
    },
    //提供给子类调用的方法
    submitSave() {
      this.dialog.visible = false;
      this.dialog.data = {};
      this.onLoad();
    },
  }
}
</script>

<style scoped lang="scss">
.el-button--success {
  background-color: #b3e19d;
  color: white;
  border: white;
}
</style>

