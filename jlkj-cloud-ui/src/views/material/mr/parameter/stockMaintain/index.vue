<template>
  <div class="avue-crud el-card__body"
       style="width: 98%;border: 0">
    <div class="avue-crud__search  search"
         style="border: 0">
      <el-row>
        <el-form :model="query"
                 ref="query">
        </el-form>
      </el-row>
      <el-row style="margin-bottom: 25px">
        <el-button v-hasPermi="['saveSilo']"
                   :disabled="tableData.length ? true : false"
                   type="primary"
                   size="medium"
                   icon="el-icon-plus"
                   @click="handleAdd('add')">新增
        </el-button>
      </el-row>
    </div>
    <div>
      <el-table height="69vh"
                size="small"
                v-loading="table.loading"
                :data="tableData"
                stripe
                @sort-change="handleSort">
        <el-table-column type="index"
                         label="序号"
                         width="80"
                         align="center" />
        <el-table-column label="料仓名称"
                         sortable
                         minWidth="150"
                         align="left"
                         prop="silo_name" />
        <el-table-column label="料仓长度(米)"
                         sortable
                         minWidth="150"
                         align="left"
                         prop="silo_length" />
        <el-table-column label="创建人"
                         sortable
                         minWidth="150"
                         align="left"
                         prop="modify_user_name" />
        <el-table-column label="创建时间"
                         sortable
                         minWidth="150"
                         align="left"
                         prop="create_time" />
        <el-table-column label="修改人"
                         sortable
                         minWidth="150px"
                         align="left"
                         prop="modify_user_name" />
        <el-table-column label="修改时间"
                         sortable
                         minWidth="150"
                         align="left"
                         prop="modify_time" />
        <el-table-column fixed="right"
                         label="操作"
                         width="180"
                         align="center">
          <template slot-scope="scope">
            <el-button v-hasPermi="['updateSilo']"
                       size="mini"
                       plain
                       icon="el-icon-edit"
                       type="success"
                       @click="handleEdit('edit',scope.$index, scope.row)"> 修改
            </el-button>
            <el-button v-hasPermi="['deleteSilos']"
                       size="mini"
                       plain
                       icon="el-icon-delete"
                       type="danger"
                       @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 10px;right: 0;padding: 25px 0px 20px 20px;"
           class="avue-crud__pagination">
        <pagination v-show="page.total > 0"
                       background
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       layout="total, sizes, prev, pager, next, jumper"
                       :current-page="page.current"
                       :page-sizes="[20, 50, 100, 200]"
                       :page-size="page.size"
                       :total="page.total">
        </pagination>
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
          <Add v-if="dialog.type === 'add'"
               @submitSave="submitSave"
               @close="dialog.visible=false" />
          <Edit v-if=" dialog.type === 'edit'"
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
import Add from "./add";
import Edit from "./edit"
export default {
  components: {
    Add,
    Edit
  },
  data () {
    return {
      dialog: {
        visible: false,
        type: '',
        title: '',
        width: '40%',
        data: {},
      },
      page: {
        size: 20,
        current: 1,
        total: 1,
        order: "create_time",
        orderby: "desc",
      },
      query: {

      },
      table: {
        border: true,
        loading: true,
      },
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
      listSilos(this.page, this.query).then(res => {
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

    //新增
    handleAdd (type) {
      this.dialog.type = type
      this.dialog.title = '新增料仓'
      this.dialog.visible = true;
    },
    //修改
    handleEdit (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '编辑料仓'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    // 删除
    handleDelete (index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        deleteSilos({
          id: row.id,
          delete_user_id: this.$store.state.user.userInfo.userName,
          delete_user_name: '姓名',
        }).then(res => {
          if (res.data.code == 0) {
            this.$message({ type: 'success', message: res.data.msg });
            this.onLoad();
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
  justify-content: space-between;
}
.el-button--success {
  background-color: #b3e19d;
  color: white;
  border: white;
}
.el-button--success {
  background-color: #b3e19d;
  color: white;
  border: white;
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
      padding: 10px 40px 40px 40px;
    }
  }
}

.customDialogStyle .el-dialog__body {
  padding: 10px 40px 40px;
}
</style>

