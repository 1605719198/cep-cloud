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
                        label="名称"
                        style="margin-right:20px;">
            <el-input v-model="query.material_name"
                      placeholder="名称"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button v-hasPermi="['workshopacc_list']"
                       @click="handleQuery"
                       type="primary"
                       icon="el-icon-search"
                       size="mini">搜索</el-button>
            <el-button @click="handleReset"
                       icon="el-icon-refresh-left"
                       size="mini">重置</el-button>

          </el-form-item>
        </el-form>
      </el-row>
      <el-row style="margin-right: 0px;">
        <el-button v-hasPermi="['workshopacc_insert']"
                   type="primary"
                   size="mini"
                   icon="el-icon-plus"
                           style="margin: 5px 0;"
                   @click="handleAdd('add')">新增
        </el-button>
        <el-button v-hasPermi="['workshopacc_sectionlist']"
                   size="mini"
                   icon="el-icon-s-platform"
                   @click="handleConfig('configuration')">工段配置
        </el-button>
      </el-row>
    </div>
    <div>
      <el-table ref="tableName"
                height="66vh"
                size="small"
                v-loading="table.loading"
                :data="tableData"
                stripe
                @sort-change="handleSort">
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
        <el-table-column label="工段"
                         minWidth="150"
                         align="center">
          <el-table-column v-for="(item,index) in workshopList"
                           :key="index"
                           :label="item.section_name"
                           minWidth="150"
                           align="left"
                           :prop="item.prop">
            <template slot-scope="scope">
              <el-tooltip effect="dark"
                          content="编辑"
                          placement="right-end">
                <el-link v-if="scope.row[item.prop]"
                         type="primary"
                         :underline="false"
                         @click="handleEdit('edit',scope.$index, scope.row,scope.row[item.prop].split('_')[0])">{{parseFloat(scope.row[item.prop].split('_')[1])}}</el-link>
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column fixed="right"
                         label="操作"
                         width="180"
                         align="center">
          <template slot-scope="scope">
            <el-button v-hasPermi="['workshopacc_delete']"
                       size="mini"
                       plain
                       icon="el-icon-delete"
                       type="text"
                       @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 10px; float: right;padding: 25px 0px 20px 20px;"
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
          <AddEdit v-if="dialog.type === 'add' || dialog.type === 'edit'"
                   :type="dialog.type"
                   :data="dialog.data"
                   :workshop_name="workshop_name"
                   @submitSave="submitSave"
                   @close="dialog.visible=false" />
          <Configuration v-if=" dialog.type === 'configuration'"
                         :workshop_name="workshop_name"
                         @submitSave="submitSave"
                         @close="dialog.visible=false" />
        </template>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import { workshopaccList, workshopaccSectionlist, workshopaccInfo, workshopaccBatchDelete } from "@/api/security/si/emergencySupplies/workshop";
import AddEdit from "./addEdit.vue";
import Configuration from "./configuration"
export default {
  components: {
    AddEdit,
    Configuration
  },
  //祖父组件注入方法（子组件调用）
  provide () {
    return {
      fatherMethod: this.onLoad
    }
  },
  props: {
    workshop_name: {
      type: String,
      required: true
    }
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
        order: "material_name",
        orderby: "asc",
      },
      query: {
        material_name: '',
        // workshop_name: '发电车间'
      },
      table: {
        border: true,
        loading: true,
      },
      tableData: [],
      //工段
      workshopList: []
    }
  },
  watch: {
    workshop_name: {
      handler () {
        this.onLoad();
      },
      deep: true,
      immediate: true
    }
  },
  created () {
    this.onLoad();
  },
  methods: {
    //载入数据
    onLoad () {
      this.table.loading = true;//加载状态
      //获取动态列
      workshopaccSectionlist({ workshop_name: this.workshop_name }).then(res => {
        // console.log(res)
        this.workshopList = res.data.data
        // this.workshopList = [
        //   {
        //     section_name: '工段1',
        //     prop: 'material_name'
        //   },
        //   {
        //     section_name: '工段2',
        //     prop: 'specification'
        //   }
        // ]
        this.query.workshop_name = this.workshop_name
        //获取数据
        workshopaccList(this.page, this.query).then(res => {
          this.table.loading = false;
          let data = res.data.data;//表格相关数据
          this.page.total = data.total;//数据总数
          this.tableData = data.records;//表格数据
          //el-table进行重载
          this.$nextTick(() => {
            this.$refs.tableName.doLayout();
          });
        }, error => {
          this.table.loading = false;
          window.console.log(error);
        });
      })
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
        this.page.order = "material_name";
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
      this.dialog.title = `新增${this.workshop_name}明细台账`
      this.dialog.data = {}
      this.dialog.visible = true;
    },
    //修改
    handleEdit (type, index, row, workshopId,) {
      workshopaccInfo({ id: workshopId }).then(res => {
        console.log(res)
        this.dialog.type = type
        this.dialog.title = `编辑${this.workshop_name}明细台账`
        this.dialog.data = { ...res.data.data, id: workshopId }
        this.dialog.visible = true
      })

    },
    //工段配置
    handleConfig (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '配置工段'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    // 删除
    handleDelete (index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        let param = {
          material_name: row.material_name,
          specification: row.specification,
          workshop_name: row.workshop_name,
        }
        workshopaccBatchDelete(param).then(res => {
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

