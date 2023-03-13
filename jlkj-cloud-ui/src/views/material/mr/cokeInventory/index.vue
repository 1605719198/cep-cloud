<template>
  <div class="app-container">
    <el-form :model="query" ref="query" :inline="true"  label-width="68px">
      <el-form-item prop="materialsId" label="物料代码">
        <el-select :popper-append-to-body="false"
                   class="customSelectStyle"
                   v-model="query.materialsId"
                   clearable
                   filterable
                   placeholder="选择物料代码">
          <el-option v-for="item in materialsOptions"
                     :key="item.id"
                     :label="item.name"
                     :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" v-hasPermi="['materialsCokeStock_list']" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd('add')"
          v-hasPermi="['materialsCokeStock_list']"
        >原始库存</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="attendancesList" @sort-change="handleSort" height="67vh">
      <el-table-column label="物料类别"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="category_name" />
      <el-table-column label="物料代码"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="materials_name" />
      <el-table-column label="库存(t)"
                       sortable
                       minWidth="150"
                       align="left"
                       prop="inventory">
        <template slot-scope="scope">
          {{scope.row.inventory / 1000}}
        </template>
      </el-table-column>
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
          <el-button v-hasPermi="['materialsCokeStock_list']"
                     size="mini"
                     plain
                     icon="el-icon-edit"
                     type="success"
                     @click="handleEdit('edit',scope.$index, scope.row)">修改
          </el-button>
          <el-button v-hasPermi="['materialsCokeStock_list']"
                     size="mini"
                     plain
                     icon="el-icon-view"
                     type="info"
                     @click="handleView(scope.$index, scope.row)">详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>

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

    <!-- 弹窗 -->
    <div v-if="dialog.visible">
      <el-dialog :title="dialog.title"
                 :visible.sync="dialog.visible"
                 class="customDialogStyle"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false">
        <template>
          <Add v-if="dialog.type === 'add'"
               :storageSpacesOptions="storageSpacesOptions"
               @submitSave="submitSave"
               @close="dialog.visible=false" />
          <Edit v-if=" dialog.type === 'edit'"
                :data="dialog.data"
                @submitSave="submitSave"
                @close="dialog.visible=false" />
        </template>
      </el-dialog>
    </div>
    <div v-if="ss.visible">
      <el-dialog title="焦炭库存详情"
                 :visible.sync="ss.visible"
                 :width="dialog.width"
                 class="customDialogStyle"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false">
        <template>
          <Views :data="dialog.data"
                 @close="dialog.visible=false" />
        </template>
      </el-dialog>
    </div>
  </div>
</template>


<script>
import { materialsCokeStockList } from "@/api/material/mr/cokeInventory/index";
// import { listMaterialsBox } from "@/api/material/mr/parameter/materialCode"
import { listMaterialsBoxJ } from "@/api/material/mr/parameter/materialCode"
import Edit from "./edit"
import Views from "./view"
import Add from "./add";

export default {
  components: {
    Edit,
    Views,
    Add
  },
  //爷孙组件传值
  provide () {
    return {
      row: this.dialog
    }
  },
  data () {
    return {
      //物资名称
      materialsOptions: [],
      dialog: {
        visible: false,
        type: '',
        title: '',
        width: '70%',
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
        materialsId: this.id
      },
      table: {
        border: true,
        loading: true,
      },
      tableData: [],
      ss: { visible: false }
    }
  },
  created () {
    this.getMaterialsOptions()
    this.onLoad();
  },
  methods: {
    //获取物料代码下拉列表
    getMaterialsOptions () {
      const param = {}
      listMaterialsBoxJ(param).then((res) => {
        // console.log(res)
        let options = []
        res.data.forEach(item => {
          let i = {
            id: item.materials_code,
            name: item.materials_name
          }
          options.push(i)
        });
        this.materialsOptions = options
      })
    },
    //载入数据
    onLoad () {
      this.table.loading = true;//加载状态
      materialsCokeStockList(this.page, this.query).then(res => {
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
    //查询
    handleQuery () {
      this.page.current = 1;
      // console.log(this.query.issueDate)
      this.onLoad();
    },
    // 重置
    handleReset () {
      this.page.current = 1
      this.$refs["query"].resetFields();
      this.handleQuery()
    },
    //修改
    handleEdit (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '焦炭库存调整'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    //新增
    handleAdd (type) {
      this.dialog.type = type
      this.dialog.title = '新增期初焦炭库存'
      this.dialog.visible = true;
    },
    //详情
    handleView (index, row) {
      this.dialog.data = row;
      // console.log(row, "row")
      this.ss.visible = true;
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
.el-card__body {
  padding: 10px 20px 20px 10px;
}

.pagination {
  white-space: nowrap;
  padding: 25px 0px 20px 0px;
  color: #303133;
  font-weight: 700;
}

.main {
  // height: 86vh;
  background-color: #fff;
}

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

.el-dialog__wrapper {
  ::v-deep .el-dialog {
    .el-dialog__header {
      background-color: whitesmoke;
      border-radius: 10px 10px 0 0;
      padding: 10px 20px 10px;
    }
  }
}

.el-dialog__wrapper {
  ::v-deep .el-dialog {
    border-radius: 10px;
  }
}

.el-dialog__wrapper {
  ::v-deep .el-dialog {
    .el-dialog__body {
      padding: 10px 40px 40px 40px;
    }
  }
}

.customDialogStyle .el-dialog__body {
  padding: 10px 40px 40px;
}

.el-dialog__wrapper {
  ::v-deep .el-col {
    margin-bottom: 0px;
  }
}

.el-button--success {
  background-color: #b3e19d;
  color: white;
  border: white;
}
</style>

