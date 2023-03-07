<template>
  <div class="app-container">
    <el-form  :inline="true"  label-width="68px" v-show="showSearch">
      <el-form-item label="产线代码">
        <el-select v-model="query.millIdCodeStart"
                   clearable
                   :popper-append-to-body="false"
                   placeholder="请选择工厂产线代码"
                   style="width: 215px!important;">
          <el-option v-for="item in optionsMillIdCodeStart"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
        ~
        <el-select v-model="query.millIdCodeEnd"
                   clearable
                   :popper-append-to-body="false"
                   placeholder="请选择工厂产线代码"
                   style="width: 215px!important;">
          <el-option v-for="item in optionsMillIdCodeEnd"
                     :key="item.value"
                     :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-hasPermi="['mill_cost_query']"
                   type="primary"
                   size="mini"
                   icon="el-icon-search"
                   @click="handleQuery"
                   style="margin-left: 20px">搜 索</el-button>
        <el-button size="mini"
                   type="default"
                   icon="el-icon-refresh-left"
                   @click="handleEmpty">重 置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-hasPermi="['mill_cost_add']"
                   type="primary"
                   plain
                   size="mini"
                   icon="el-icon-plus"
                   @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['mill_cost_delete']"
                   type="danger"
                   size="mini"
                   icon="el-icon-delete"
                   :disabled="openIsDisabled"
                   @click="handleDelete">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch"  @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table size="small"
              height="67vh"
              :data="tableData"
              stripe
              style="margin: 0 0px 0 10px;width: auto;"
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
          <el-button v-hasPermi="['mill_cost_delete']"
                     size="mini"
                     type="text"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination background
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="page.currentPage"
                :page-sizes="[20, 50, 100, 200]"
                :page-size="page.pageSize"
                :layout="page.layout"
                :total="page.total">
    </pagination>

    <!-- 添加或修改产线代码对应成本中心资料 -->
    <div v-if="addBox">
      <el-dialog :title="(editBox?'编辑':'新建')+'产线代码对应成本中心资料'"
                 :visible.sync="addBox"
                 width="500px"
                 class="customDialogStyle"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false">
        <productionLineAdd :dataEdit="dataEdit"
                           @submitAdd="submitAdd"
                           @close="addBox=false,editBox=false" />
      </el-dialog>
    </div>

  </div>
</template>

<script>

import productionLineAdd from "./productionLineAdd";
import { addInfo, delInfo, queryInfo } from "@/api/energy/ee/productionLineCostCenter";

export default {
  components: {
    productionLineAdd
  },
  name: "productionLineCostCenter",
  data () {
    return {
      // 显示搜索条件
      showSearch: true,
      addBox: false,
      editBox: false,
      dataEdit: {},
      page: {
        pageSize: 20,
        currentPage: 1,
        total: 0,
        layout: "total, sizes, prev, pager, next, jumper",
      },
      optionsMillIdCodeStart: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }],
      optionsMillIdCodeEnd: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }],
      query: {
        millIdCodeStart: undefined,
        millIdCodeEnd: undefined,
        pageSize: 20,
        pageNum: 1,
      },
      table: {
        border: true
      },
      columns: [
        { label: '工厂产线代码', prop: "millIdCode", sortable: true, minWidth: '100px' },
        { label: '产线名称', prop: "millIdName", sortable: true, minWidth: '100px' },
        { label: '成本中心', prop: "costCenter", sortable: true, minWidth: '100px' },
        { label: '建立人员', prop: "createEmpNo", sortable: true, minWidth: '100px' },
        { label: '建立日期', prop: "createTime", sortable: true, minWidth: '100px' },
        { label: '修改人员', prop: "updateEmpNo", sortable: true, minWidth: '100px' },
        { label: '修改日期', prop: "updateTime", sortable: true, minWidth: '100px' },
      ],
      tableData: [],
      openIsDisabled: ''
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
        this.tableData = response.data.list
        this.page.total = response.data.total
      })
    },
    // 清空
    handleEmpty () {
      this.query = {
        millIdCodeStart: undefined,
        millIdCodeEnd: undefined,
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
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //调用删除的方法
        delInfo(ids).then(response => {
          this.$message({
            type: 'success',
            message: response.msg
          });
          this.getList();
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
      addInfo(data).then(response => {
        if (response.code == 0) {
          this.$message({
            type: 'success',
            message: response.msg
          });
        }
        this.getList();
      });
    },
  }
}
</script>

<style scoped>
.energy_main {
  height: 86vh;
  background-color: #fff;
}
::v-deep .el-form-item__content {
  white-space: nowrap;
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
