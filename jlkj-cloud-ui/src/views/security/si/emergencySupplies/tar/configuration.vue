<template>
  <div>
    <div class="avue-crud__search  search">
      <el-row>
        <el-form :model="query"
                 ref="query">
        </el-form>
      </el-row>
        <el-button type="primary"
                   size="mini"
                   icon="el-icon-plus"
                           style="margin: 5px 0;"
                   @click="handleAdd('add')">新增
        </el-button>
    </div>
    <div>
      <el-table height="70vh"
                size="small"
                v-loading="table.loading"
                :data="tableData"
                stripe
                @sort-change="handleSort">
        <el-table-column label="工段名称"
                         sortable
                         minWidth="150"
                         align="center"
                         prop="silo_name" />
        <el-table-column fixed="right"
                         label="操作"
                         width="180"
                         align="center">
          <template slot-scope="scope">
            <el-button size="mini"
                       plain
                       icon="el-icon-edit"
                       type="text"
                       @click="handleEdit('edit',scope.$index, scope.row)"> 编辑
            </el-button>
            <el-button size="mini"
                       plain
                       icon="el-icon-delete"
                       type="text"
                       @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 10px; float: right"
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
    <div v-if="dialog.visible">
      <el-dialog :title="dialog.title"
                 :visible.sync="dialog.visible"
                 :width="dialog.width"
                 class="customDialogStyle"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false">
        <template>
          <Update v-if="dialog.type === 'add' || dialog.type === 'edit'"
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
import { listSilos, deleteSilos } from "@/api/material/mr/parameter/stockMaintain";
import Update from "./update.vue"
export default {
  components: {
    Update
  },
  data () {
    return {
      dialog: {
        visible: false,
        type: '',
        title: '',
        width: '30%',
        data: {},
      },
      page: {
        size: 20,
        current: 1,
        total: 1,
        order: "create_time",
        orderby: "asc",
      },
      query: {
        targetItemTypeId: 0
      },
      table: {
        border: true,
        // loading: true,
      },
      tableData: [{ name: '123' }],
    }
  },
  created () {
    // this.onLoad();
  },
  methods: {
    //载入数据
    onLoad () {
      this.table.loading = true;//加载状态
      listSilos(this.page, this.query).then(res => {
        this.table.loading = false;
        let data = res.data.data;//表格相关数据
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
        this.page.orderby = "asc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.onLoad();
    },

    //新增
    handleAdd (type) {
      this.dialog.type = type
      this.dialog.title = '新增煤焦车间工段'
      this.dialog.data = {}
      this.dialog.visible = true;
    },
    //修改
    handleEdit (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '编辑煤焦车间工段'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    // 删除
    handleDelete (index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        deleteSilos({ id: row.id }).then(res => {
          this.onLoad();
          this.$message({ type: 'success', message: res.data.msg });
        }, error => {
          window.console.log(error);
        });
      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    //提供给子类调用的方法
    submitSave () {
      this.dialog.visible = false;
      this.dialog.data = {};
      this.onLoad();
    },
  }
}
</script>

<style scoped lang="scss">
.search {
  width: 100%;
  display: flex;
}
.el-dialog__wrapper {
  ::v-deep.el-dialog {
        .el-dialog__header {
        background-color: whitesmoke;
        border-radius: 10px 10px 0 0;
        padding: 10px 20px 10px;
    }
  }
  }
  .el-dialog__wrapper {
    ::v-deep.el-dialog {
      border-radius: 10px;
    }
  }
  .el-dialog__wrapper {
  ::v-deep.el-dialog {
        .el-dialog__body {
        padding: 10px 40px 75px 40px;
    }
  }
  }

  .customDialogStyle .el-dialog__body {
      padding: 10px 40px 40px;
  }

  .el-dialog__wrapper {
      ::v-deep.el-form-item {
      margin-bottom: 10px;
  }
  }
</style>

