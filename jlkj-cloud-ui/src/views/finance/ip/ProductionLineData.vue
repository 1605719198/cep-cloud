<template>
  <el-row>
    <el-col :span="24"
            style="padding:0 10px">
      <div class="energy_main">
        <div class="avue-crud el-card__body"
             style="width: 98%;border: 0">
          <div class="avue-crud__search"
               style="border: 0">
            <el-form>
              <el-row :gutter="20">
                <el-col :span="3">
                  <el-form-item label="">
                    <el-input v-model="query.millId"
                              placeholder="产线代码" />
                  </el-form-item>
                </el-col>
                <el-col :span="3">
                  <el-form-item label="">
                    <el-input v-model="query.millName"
                              placeholder="产线名称" />
                  </el-form-item>
                </el-col>
                <el-col :span="14">
                  <div class="el-form-item__content"
                       style="margin-left: 0px;">
                    <el-button v-hasPermi="['mill_doQueryAll']"
                               size="medium"
                               type="primary"
                               icon="el-icon-search"
                               @click="handleQuery">搜 索</el-button>
                    <el-button size="medium"
                               type="default"
                               icon="el-icon-refresh-left"
                               @click="handleEmpty">重 置</el-button>
                  </div>
                </el-col>
                <el-col :span="4">
                  <div class="el-form-item__content"
                       style="float: right">
                    <el-button v-hasPermi="['mill_doAdd']"
                               type="primary"
                               size="medium"
                               icon="el-icon-plus"
                               @click="handleOpenWindow('add')">添加产线
                    </el-button>
                  </div>
                </el-col>
              </el-row>
            </el-form>
          </div>
          <div>
            <el-form>
              <el-table height="67.1vh"
                        size="small"
                        :data="tableData"
                        stripe>
                <template v-for="column in columns">
                  <el-table-column :prop="column.prop"
                                   :label="column.label"
                                   :type="column.type"
                                   :width="column.width"
                                   :min-width="column.minWidth"
                                   :key="column.prop"
                                   :sortable="column.sortable">
                  </el-table-column>
                </template>
                <el-table-column prop="status"
                                 label="状态">
                  <template slot-scope="scope">
                    <el-switch v-model="scope.row.status"
                               @click.native.prevent="switchChange(scope.row)"
                               active-value="true"
                               inactive-value="false"
                               active-color="#13ce66"
                               inactive-color="#ff4949">
                    </el-switch>
                  </template>
                </el-table-column>
                <el-table-column fixed="right"
                                 label="操作"
                                 width="280px">
                  <template slot-scope="scope">
                    <el-button v-hasPermi="['mill_doQueryOne']"
                               size="mini"
                               plain
                               icon="el-icon-info"
                               type="primary"
                               @click="handleOpenWindow('info',scope.$index, scope.row)"> 详情
                    </el-button>
                    <el-button v-hasPermi="['mill_doEdit']"
                               size="mini"
                               plain
                               icon="el-icon-edit"
                               type="primary"
                               @click="handleOpenWindow('edit',scope.$index, scope.row)"> 编辑
                    </el-button>
                    <el-button v-hasPermi="['mill_doDelete']"
                               size="mini"
                               plain
                               icon="el-icon-delete"
                               type="danger"
                               @click="handleDelete(scope.$index, scope.row)">删除
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
              <div style="margin-top: 10px;right: 0"
                   class="avue-crud__pagination">
                <el-pagination background
                               @size-change="handleSizeChange"
                               @current-change="handleCurrentChange"
                               :current-page="page.currentPage"
                               :page-sizes="[20, 50, 100, 200]"
                               :page-size="page.pageSize"
                               :layout="page.layout"
                               :total="page.total">
                </el-pagination>
              </div>
            </el-form>
          </div>
          <div v-if="openDialog.open">
            <el-dialog :title="openDialog.title"
                       :visible.sync="openDialog.open"
                       :width="openDialog.width"
                       class="customDialogStyle"
                       append-to-body
                       :destroy-on-close="true"
                       :close-on-click-modal="false"
                       style="margin-top: -10vh">
              <template v-if="openDialog.type === 'add'">
                <ProductionLineDataEdit :data="openDialog.data"
                                        @submitSave="submitSave"
                                        :type="openDialog.type"
                                        @close="openDialog.open=false" />
              </template>
              <template v-if="openDialog.type === 'edit'">
                <ProductionLineDataEdit :detailBox="detailBox"
                                        :editBox="editBox"
                                        :data="openDialog.data"
                                        @submitSave="submitSave"
                                        :type="openDialog.type"
                                        @close="openDialog.open=false;detailBox=false;editBox=false" />
              </template>
              <template v-if="openDialog.type === 'info'">
                <ProductionLineDataEdit :detailBox="detailBox"
                                        :data="openDialog.data"
                                        :type="openDialog.type"
                                        @close="openDialog.open=false;detailBox=false" />
              </template>
            </el-dialog>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import ProductionLineDataEdit from "./ProductionLineDataEdit";
import { addInfo, delInfo, queryInfo, updateInfo } from "@/api/finance/ip/productionLineData";

export default {
  components: {
    ProductionLineDataEdit,
  },
  name: "ProductionLineData",
  data () {
    return {
      editBox: false,
      detailBox: false,
      openDialog: {
        open: false,
        type: '',
        title: '',
        width: '500px',
        data: {},
      },
      page: {
        pageSize: 20,
        currentPage: 1,
        total: 0,
        layout: "total, sizes, prev, pager, next, jumper",
      },
      currentPage: 0,
      total: 0,
      query: {
        millId: undefined,
        millName: undefined,
        pageSize: 20,
        pageNum: 1,
      },
      table: {
        border: true
      },
      columns: [
        { label: '产线代码', prop: "millId", sortable: true, minWidth: '150px' },
        { label: '产线名称', prop: "millName", sortable: true, minWidth: '150px' },
      ],
      tableData: []
    }
  },
  created () {
    this.getList()
  },
  methods: {
    //查询
    handleQuery () {
      let query = this.query
      queryInfo(query).then(response => {
        if (response.data.code == 0) {
          this.$message({
            type: 'success',
            message: response.data.msg
          })
        }
        this.tableData = response.data.data.list
        this.page.total = response.data.data.total
      })
    },
    // 清空
    handleEmpty () {
      this.query = {
        millId: '',
        millName: '',
        pageSize: 20,
        pageNum: 1,
      }
      this.getList()
    },
    // 打开窗口
    handleOpenWindow (type, index, row) {
      let title = '产线资料';
      this.openDialog.open = true;
      this.openDialog.type = type;
      this.openDialog.width = '1160px';
      this.openDialog.data = row;
      if (type === 'add') {
        this.openDialog.title = '新增' + title;
        this.openDialog.data = {};
      } else if (type === 'edit') {
        this.openDialog.title = '编辑' + title;
        this.detailBox = false;
        this.editBox = true;
      } else if (type === 'info') {
        this.openDialog.title = '查看' + title;
        this.openDialog.width = '1160px';
        this.editBox = false;
        this.detailBox = true;
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
        //调用删除的方法
        delInfo(row.millId).then(response => {
          this.getList();
          this.$message({ type: 'success', message: response.data.msg });
        })
      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    // 分页-每页多少条
    handleSizeChange (val) {
      this.query.pageSize = val
      this.getList()
    },
    // 分页-当前页
    handleCurrentChange (val) {
      this.query.pageNum = val
      this.getList()
    },
    //获取数据刷新页面
    getList () {
      queryInfo(this.query).then(response => {
        this.tableData = response.data.data.list
        this.page.total = response.data.data.total
      })
    },
    switchChange (row) {
      updateInfo(row).then(response => {
        this.$message({
          type: 'success',
          message: response.data.msg
        })
        this.getList();
      });
    },
    //提供给子类调用的方法
    submitSave (data) {
      this.openDialog.open = false;
      this.openDialog.data = {};
      if (data.id != null) {
        updateInfo(data).then(response => {
          if (response.data.code == 0) {
            this.$message({
              type: 'success',
              message: response.data.msg
            })
          }
          this.getList();
        });
      } else {
        data.status = true
        addInfo(data).then(response => {
          if (response.data.code == 0) {
            this.$message({
              type: 'success',
              message: response.data.msg
            })
          }
          this.getList();
        });
      }
    },
  }
}
</script>

<style scoped>
.energy_main {
  height: 86vh;
  background-color: #fff;
}

/*下拉框最后一个显示不完全*/
/deep/ .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
