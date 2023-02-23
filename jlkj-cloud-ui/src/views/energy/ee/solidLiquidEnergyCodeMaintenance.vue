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
                <el-col :span="10">
                  <div class="el-form-item el-form-item--small">
                    <div class="el-form-item__content">
                      <el-form-item label="">
                        <el-select v-model="query.engyIdStart"
                                   clearable
                                   :popper-append-to-body="false"
                                   placeholder="请选择能源代码"
                                   style="width: 185.84px!important;">
                          <el-option v-for="item in optionsEngyIdStart"
                                     :key="item.value"
                                     :label="item.value"
                                     :value="item.value">
                          </el-option>
                        </el-select>
                        ~
                        <el-select v-model="query.engyIdEnd"
                                   clearable
                                   :popper-append-to-body="false"
                                   placeholder="请选择能源代码"
                                   style="width: 185.84px!important;padding-left: 0.5%">
                          <el-option v-for="item in optionsEngyIdEnd"
                                     :key="item.value"
                                     :label="item.value"
                                     :value="item.value">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </div>
                  </div>
                </el-col>
                <el-col :span="10">
                  <div class="el-form-item__content"
                       style="margin-left: -274px;">
                    <el-button v-hasPermi="['code_querySolid']"
                               type="primary"
                               size="mini"
                               icon="el-icon-search"
                               @click="handleQuery">搜 索</el-button>
                    <el-button size="mini"
                               type="default"
                               icon="el-icon-refresh-left"
                               @click="handleEmpty">重 置</el-button>
                  </div>
                </el-col>
                <el-col :span="4">
                  <div class="el-form-item__content"
                       style="float: right">
                    <el-button v-hasPermi="['code_addSolid']"
                               type="primary"
                               plain
                               size="mini"
                               icon="el-icon-plus"
                               @click="handleAdd">新增</el-button>
                    <el-button v-hasPermi="['code_deleteSolid']"
                               type="danger"
                               size="mini"
                               icon="el-icon-delete"
                               :disabled="openIsDisabled"
                               @click="handleDelete">删除</el-button>
                  </div>
                </el-col>
              </el-row>
            </el-form>
          </div>
          <div>
            <el-form>
              <el-table height="68.1vh"
                        size="small"
                        :data="tableData"
                        stripe
                        @selection-change="handleSelectionChange"
                        :default-sort="{prop: 'createTime', order: 'descending'}">
                <el-table-column type="selection"
                                 width="55"
                                 align="center" />
                <template v-for="column in columns">
                  <el-table-column :key="column.prop"
                                   :prop="column.prop"
                                   :label="column.label"
                                   :sortable="column.sortable"
                                   :width="column.width"
                                   :min-width="column.minWidth" />
                </template>
                <el-table-column fixed="right"
                                 label="操作"
                                 width="180px">
                  <template slot-scope="scope">
                    <el-button v-hasPermi="['code_updateSolid']"
                               size="mini"
                               plain
                               icon="el-icon-edit"
                               type="primary"
                               @click="handleEdit(scope.$index, scope.row)">编辑
                    </el-button>
                    <el-button v-hasPermi="['code_deleteSolid']"
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
          <div v-if="addBox">
            <el-dialog :title="(editBox?'编辑':'新建')+'固液体能源代码'"
                       :visible.sync="addBox"
                       width="500px"
                       class="customDialogStyle"
                       append-to-body
                       :destroy-on-close="true"
                       :close-on-click-modal="false">
              <solidLiquidEnergyAdd :dataEdit="dataEdit"
                                    :addBox="addBox"
                                    :editBox="editBox"
                                    @submitAdd="submitAdd"
                                    @close="addBox=false,editBox=false" />
            </el-dialog>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>

import solidLiquidEnergyAdd from "./solidLiquidEnergyAdd";
import { addInfo, delInfo, queryInfo, updateInfo, queryEngyIds } from "@/api/energy/ee/solidLiquidEnergyCodeMaintenance";

export default {
  components: {
    solidLiquidEnergyAdd
  },
  name: "solidLiquidEnergyCodeMaintenance",
  data () {
    return {
      addBox: false,
      editBox: false,
      delBox: false,
      dataEdit: {},
      page: {
        pageSize: 20,
        currentPage: 1,
        total: 0,
        layout: "total, sizes, prev, pager, next, jumper",
      },
      query: {
        engyIdStart: undefined,
        engyIdEnd: undefined,
        pageSize: 20,
        pageNum: 1,
      },
      table: {
        border: true
      },
      columns: [
        { label: '能源缩写', prop: "engyAc", sortable: true, minWidth: '80px' },
        { label: '能源代码', prop: "engyId", sortable: true, minWidth: '100px' },
        { label: '能源名称', prop: "engyName", sortable: true, minWidth: '100px' },
        { label: '计量单位', prop: "engyUnit", sortable: true, minWidth: '100px' },
        { label: '热值系数', prop: "calValue", sortable: true, minWidth: '100px' },
        { label: '热值系数单位', prop: "calUnit", sortable: true, minWidth: '100px' },
        { label: '来源方式', prop: "srcType", sortable: true, minWidth: '100px' },
        { label: '能源种类', prop: "engyType", sortable: true, minWidth: '80px' },
        { label: '抛帐系统', prop: "acctSys", sortable: true, minWidth: '80px' },
        { label: '建立人员', prop: "createEmpNo", sortable: true, minWidth: '80px' },
        { label: '建立日期', prop: "createTime", sortable: true, minWidth: '80px' },
      ],
      tableData: [],
      optionsEngyIdStart: [],
      optionsEngyIdEnd: [],
      openIsDisabled: "",
    }
  },
  created () {
    this.getList();
    queryEngyIds().then(response => {
      this.optionsEngyIdStart = response.data.data;
      this.optionsEngyIdEnd = response.data.data;
      this.loading = false
    })
  },
  methods: {
    //查询
    handleQuery () {
      let query = this.query
      queryInfo(query).then(response => {
        this.tableData = response.data.data.list
        this.page.total = response.data.data.total
      })
    },
    // 清空
    handleEmpty () {
      this.query = {
        engyIdStart: undefined,
        engyIdEnd: undefined,
        pageSize: 20,
        pageNum: 1,
      },
        this.getList();
    },
    // 新增
    handleAdd () {
      this.dataEdit = {};
      this.addBox = true;
      this.editBox = false;
    },
    // 修改
    handleEdit (index, row) {
      this.dataEdit = row;//传递子组件
      this.editBox = true;
      this.addBox = true;
    },
    // 删除
    handleDelete (index, row) {
      //单条或多条 id
      let ids = null;
      if (typeof (row) == 'undefined') {
        ids = this.ids;
      } else {
        ids = row.id;
      }
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        //调用删除的方法
        delInfo(ids).then(response => {
          this.$message({
            type: 'success',
            message: response.data.msg
          });
          this.getList();
        })
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
        if (response.data.data != null) {
          this.tableData = response.data.data.list
          this.page.total = response.data.data.total
        } else {
          this.tableData = [];
          this.page.total = 0;
        }
      })
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length

      if (this.ids.length != 0) {
        this.openIsDisabled = false
      } else {
        this.openIsDisabled = true
      }
    },
    //提供给子类调用父类的方法
    submitAdd (data) {
      this.addBox = false;
      this.editBox = false;
      if (data.id != null) {
        updateInfo(data).then(response => {
          this.$message({
            type: 'success',
            message: response.data.msg
          });
          this.getList();
        });
      } else {
        addInfo(data).then(response => {
          if (response.data.code == '0') {
            this.$message({
              type: 'success',
              message: response.data.msg
            });
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
::v-deep .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
/*页面大小修改时新增删除串行*/
::v-deep .el-col {
  white-space: nowrap;
}
</style>
