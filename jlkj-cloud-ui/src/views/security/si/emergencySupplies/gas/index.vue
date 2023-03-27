<template>
  <div class="avue-crud el-card__body"
       style="width: 98%;border: 0">
    <div class="avue-crud__search search"
         style="border: 0">
      <el-row>
        <el-form :model="query"
                 ref="query"
                 :inline="true">
          <el-form-item prop="material_name"
                        style="margin-right:20px;">
            <el-input v-model="query.material_name"
                      placeholder="名称"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button @click="handleQuery"
                       type="primary"
                       icon="el-icon-search"
                       size="mini">查 询</el-button>
            <el-button @click="handleReset"
                       icon="el-icon-refresh-left"
                       size="mini">重置</el-button>

          </el-form-item>
        </el-form>
      </el-row>
      <el-row style="margin-right: 20px;">
        <el-button type="primary"
                   size="mini"
                   icon="el-icon-plus"
                           style="margin: 5px 0;"
                   @click="handleAdd('add')">新增
        </el-button>
        <!-- <el-button size="mini"
                   icon="el-icon-delete"
                   @click="handleBatchesDelete">批量删除
        </el-button> -->
        <!-- <el-button size="mini"
                   icon="el-icon-s-promotion"
                   @click="handleDerive">导出
        </el-button> -->
      </el-row>
    </div>
    <div>
      <el-table height="66vh"
                size="small"
                v-loading="table.loading"
                :data="tableData"
                stripe
                @sort-change="handleSort">
        <el-table-column type="selection"
                         width="55"
                         align="center" />
        <el-table-column type="index"
                         label="序号"
                         width="80"
                         align="center" />
        <el-table-column label="名称"
                         sortable
                         minWidth="150"
                         align="left"
                         prop="material_name" />
        <el-table-column label="型号"
                         sortable
                         minWidth="150"
                         align="left"
                         prop="specification" />
        <el-table-column label="数量"
                         sortable
                         minWidth="150"
                         align="left"
                         prop="amount" />
        <el-table-column label="负责人"
                         sortable
                         minWidth="150"
                         align="left"
                         prop="charge_person" />
        <el-table-column label="备注"
                         sortable
                         minWidth="150"
                         align="left"
                         prop="remark" />
        <el-table-column label="创建人"
                         sortable
                         minWidth="150"
                         align="left"
                         prop="create_user" />
        <el-table-column label="创建时间"
                         sortable
                         minWidth="150"
                         align="left"
                         prop="create_time" />
        <el-table-column fixed="right"
                         label="操作"
                         width="180"
                         align="center">
          <template slot-scope="scope">
            <el-button size="mini"
                       plain
                       icon="el-icon-edit"
                       type="primary"
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
      <div style="margin-top: 10px; float: right;padding: 0;"
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
          <Add v-if="dialog.type==='add'"
               @submitSave="submitSave"
               @close="dialog.visible=false" />
          <Edit v-if="dialog.type==='edit'"
                :data="dialog.data"
                @submitSave="submitSave"
                @close="dialog.visible=false" />
        </template>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import { emergencyreliefmaterialsList, emergencyreliefmaterialsDelete } from "@/api/security/si/emergencySupplies/gas";
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
        width: '',
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
        material_name: ''
      },
      table: {
        border: true,
        loading: true,
      },
      tableData: [],
    }
  },
  created () {
    this.onLoad();
  },
  methods: {
    //载入数据
    onLoad () {
      this.table.loading = true;//加载状态
      emergencyreliefmaterialsList(this.page, this.query).then(res => {
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
    //查询
    handleQuery () {
      this.page.current = 1;
      this.onLoad();
    },
    // 重置
    handleReset () {
      this.page.current = 1
      this.$refs["query"].resetFields();
      this.handleQuery()
    },

    //新增
    handleAdd (type) {
      this.dialog.type = type
      this.dialog.title = '新增气防站应急救援物资'
      this.dialog.visible = true;
    },
    //批量删除
    handleBatchesDelete () { },
    //导出
    handleDerive () { },
    //修改
    handleEdit (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '编辑气防站应急救援物资'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    // 删除
    handleDelete (index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        emergencyreliefmaterialsDelete({ id: row.id }).then(res => {
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
  justify-content: space-between;
}
::v-deep.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
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

  .el-dialog__wrapper {
      ::v-deep.el-form-item {
      margin-bottom: 10px;
  }
  }
</style>

