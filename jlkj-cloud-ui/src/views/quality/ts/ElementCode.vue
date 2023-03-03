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
                    <el-input v-model="query.elementIndex"
                              placeholder="试验元素代号" />
                  </el-form-item>
                </el-col>
                <el-col :span="3">
                  <el-form-item label="">
                    <el-input v-model="query.elementName"
                              placeholder="元素名称/试验项目" />
                  </el-form-item>
                </el-col>
                <el-col :span="14">
                  <div class="el-form-item__content"
                       style="margin-left: 0px;">
                    <el-button v-hasPermi="['elementId_queryList']"
                               size="mini"
                               type="primary"
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
                    <el-button v-hasPermi="['elementId_add']"
                               type="primary"
                               plain
                               size="mini"
                               icon="el-icon-plus"
                               @click="handleAdd">新增</el-button>
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
                        stripe
                        :default-sort="{prop: 'createTime', order: 'descending'}">
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
                    <el-button v-hasPermi="['elementId_update']"
                               size="mini"
                               icon="el-icon-edit"
                               type="text"
                               @click="handleEdit(scope.$index, scope.row)">编辑
                    </el-button>
                    <el-button v-hasPermi="['elementId_delete']"
                               size="mini"
                               icon="el-icon-delete"
                               type="text"
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
            <el-dialog :title="(editBox?'编辑':'新建')+'试验元素代号'"
                       :visible.sync="addBox"
                       width="500px"
                       class="customDialogStyle"
                       append-to-body
                       :destroy-on-close="true"
                       :close-on-click-modal="false">
              <elementCodeAdd :dataEdit="dataEdit"
                              :status="status"
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
import elementCodeAdd from "./ElementCodeAdd";
import { addInfo, delInfo, queryInfo, updateInfo } from "@/api/quality/ts/elementCode";

export default {
  components: {
    elementCodeAdd
  },
  name: "elementCode",
  data () {
    return {
      addBox: false,
      editBox: false,
      dataEdit: {},
      status: false,
      page: {
        pageSize: 20,
        currentPage: 1,
        total: 0,
        layout: "total, sizes, prev, pager, next, jumper",
      },
      query: {
        elementType: '1',
        elementIndex: '',
        elementName: '',
        pageSize: 20,
        pageNum: 1,
      },
      table: {
        border: true
      },
      columns: [
        { label: '试验元素代号', prop: "elementIndex", sortable: true, minWidth: '120px' },
        { label: '元素名称/试验项目', prop: "elementName", sortable: true, minWidth: '150px' },
        { label: '单位', prop: "elementUnit", sortable: true, minWidth: '100px' },
        { label: '新增人员', prop: "createUser", sortable: true, minWidth: '100px' },
        { label: '新增时间', prop: "createTime", sortable: true, minWidth: '100px' },
        { label: '修改人员', prop: "updateUser", sortable: true, minWidth: '100px' },
        { label: '修改时间', prop: "updateTime", sortable: true, minWidth: '100px' },
      ],
      tableData: []
    }
  },
  created () {
    this.getList()
  },
  computed: {},
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
        this.tableData = response.data.list
        this.page.total = response.data.total
      })
    },
    // 清空
    handleEmpty () {
      this.query = {
        elementType: '1',
        elementIndex: '',
        elementName: '',
        pageSize: 20,
        pageNum: 1,
      },
        queryInfo(this.query).then(response => {
          this.tableData = response.data.list
          this.page.total = response.data.total
        })
    },
    // 新增
    handleAdd () {
      this.dataEdit = {};
      this.addBox = true;
      this.status = false;
      this.editBox = false;
    },
    // 修改
    handleEdit (index, row) {
      this.dataEdit = row;//传递子组件
      this.editBox = true;
      this.status = true;
      this.addBox = true;
    },
    // 删除
    handleDelete (index, row) {
      const ids = row.elementIndex;
      this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        //调用删除的方法
        delInfo(ids).then(response => {
          this.getList();
          this.$message({
            type: 'success',
            message: response.data.msg
          });
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
        this.tableData = response.data.list
        this.page.total = response.data.total
      })
    },
    //提供给子类调用父类的方法
    submitAdd (data) {
      this.addBox = false;
      this.editBox = false;
      if (data.id != null) {
        // data.updateUser = this.userInfo.userName
        updateInfo(data).then(response => {
          if (response.data.code == '0') {
            this.$message({
              type: 'success',
              message: response.data.msg
            })
          }
          this.getList();
        });
      } else {
        // data.createUser = this.userInfo.userName;
        addInfo(data).then(response => {
          if (response.data.code == '0') {
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
