<template>
  <div style="padding: 0px 10px;">
    <div class="main">
      <div class="avue-crud el-card__body"
           style="width: 98%;border: 0;">
        <div class="avue-crud__search search"
             style="border: 0">
          <el-row>
            <!-- 表单筛选 -->
            <el-form :model="query"
                     ref="query"
                     :inline="true">
              <el-row>
                  <el-form-item prop="createdTime" label="创建时间">
                    <el-date-picker v-model="query.createdTime"
                                    type="daterange"
                                    range-separator="至"
                                    start-placeholder="创建开始时间"
                                    end-placeholder="创建结束时间"
                                    value-format="yyyy-MM-dd">
                    </el-date-picker>
                  </el-form-item>
                  <el-form-item prop="clearTime" label="清零时间">
                    <el-date-picker v-model="query.clearTime"
                                    type="daterange"
                                    range-separator="至"
                                    start-placeholder="清零开始时间"
                                    end-placeholder="清零结束时间"
                                    value-format="yyyy-MM-dd">
                    </el-date-picker>
                  </el-form-item>
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
                  <el-form-item prop="storageSpacesId" label="储位">
                    <el-select :popper-append-to-body="false"
                               class="customSelectStyle"
                               v-model="query.storageSpacesId"
                               clearable
                               filterable
                               placeholder="选择储位">
                      <el-option v-for="item in storageSpacesOptions"
                                 :key="item.id"
                                 :label="item.name"
                                 :value="item.id">
                      </el-option>
                    </el-select>
                  </el-form-item>
                <!-- 操作按钮 -->
                  <el-form-item>
                    <el-button type="primary" icon="el-icon-search" size="mini" v-hasPermi="['materialsCoalStock_list']" @click="handleQuery">搜索</el-button>
                    <el-button icon="el-icon-refresh" size="mini" @click="handleReset">重置</el-button>
                  </el-form-item>
              </el-row>
            </el-form>
          </el-row>
          <el-row :gutter="10" class="mb8">
            <el-col
                    style="text-align:left">
              <div class="el-form-item__content">
                <el-button
                  type="primary"
                  plain
                  icon="el-icon-plus"
                  size="mini"
                  @click="handleAdd('add')"
                  v-hasPermi="['materialsCoalStock_list']"
                >原始库存</el-button>
              </div>
            </el-col>
          </el-row>
        </div>
        <div>
          <el-table height="69vh"
                    size="small"
                    v-loading="table.loading"
                    :data="tableData"
                    stripe
                    @sort-change="handleSort">
            <el-table-column label="序号"
                             width="55"
                             type="index" />
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
            <el-table-column label="小煤种"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="materials_small_name" />
            <el-table-column label="储位名称"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="storage_spaces_name" />
            <el-table-column label="库存量(t)"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="inventory">
              <template slot-scope="scope">
                {{scope.row.inventory/1000}}
              </template>
            </el-table-column>
            <el-table-column label="今日卸煤净重(t)"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="today_unloaded_weight">
              <template slot-scope="scope">
                {{scope.row.today_unloaded_weight/1000}}
              </template>
            </el-table-column>
            <el-table-column label="今日配煤重量(t)"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="today_loading_weight">
              <template slot-scope="scope">
                {{scope.row.today_loading_weight/1000}}
              </template>
            </el-table-column>
            <!--            <el-table-column label="配煤重量(t)"-->
            <!--                             sortable-->
            <!--                             minWidth="150"-->
            <!--                             align="left"-->
            <!--                             prop="">-->
            <!--              &lt;!&ndash; <template slot-scope="scope">-->
            <!--                {{scope.row.today_loading_weight/1000}}-->
            <!--              </template> &ndash;&gt;-->
            <!--            </el-table-column>-->
            <el-table-column label="创建时间"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="create_time" />
            <!--            <el-table-column label="清零时间"-->
            <!--                             sortable-->
            <!--                             minWidth="150"-->
            <!--                             align="left"-->
            <!--                             prop="clear_name" />-->
            <el-table-column fixed="right"
                             label="操作"
                             width="180"
                             align="center">
              <template slot-scope="scope">
                <el-button v-hasPermi="['materialsCoalStock_list']"
                           size="mini"
                           plain
                           icon="el-icon-edit"
                           type="success"
                           @click="handleEdit('edit',scope.$index, scope.row)">修改
                </el-button>
                <el-button v-hasPermi="['materialsCoalStock_list']"
                           size="mini"
                           plain
                           icon="el-icon-view"
                           type="info"
                           @click="handleView(scope.$index, scope.row)">详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="margin-top: 10px;float: right;padding: 25px 0px 20px 20px;"
               class="avue-crud__pagination">
            <el-pagination
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
      </div>
      <!-- 弹窗 -->
      <div v-if="dialog.visible">
        <el-dialog :title="dialog.title"
                   :visible.sync="dialog.visible"
                   class="customDialogStyle"
                   append-to-body
                   :destroy-on-close="true"
                   :close-on-click-modal="false">
          <template>
            <Edit v-if=" dialog.type === 'edit'"
                  :data="dialog.data"
                  @submitSave="submitSave"
                  @close="dialog.visible=false" />
            <Add v-if="dialog.type === 'add'"
                 @close="dialog.visible=false"
                 :data="dialog.data"
                 :storageSpacesOptions="storageSpacesOptions"
                 @submitSave="submitSave"></Add>
          </template>
        </el-dialog>
      </div>
      <div v-if="ss.visible">
        <el-dialog title="煤场库存详情"
                   :visible.sync="ss.visible"
                   class="customDialogStyle"
                   append-to-body
                   :destroy-on-close="true"
                   :close-on-click-modal="false"
                   :width="dialog.width">
          <template>
            <Views :data="dialog.data"
                   @close="ss.visible=false" />
          </template>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import { listMaterialsCategoryBox } from "@/api/material/mr/parameter/materialType";
// import { listMaterialsBox } from "@/api/material/mr/parameter/materialCode"
import { listMaterialsBoxM } from "@/api/material/mr/parameter/materialCode"
import { materialsCoalStockList } from "@/api/material/mr/coalYardInventory/index";
import { listMaterialsStorageSpacesBox } from "@/api/material/mr/parameter/storeLocation"
import Edit from "./edit"
import Add from "./add"
import Views from "./view"
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
      //物料类别
      categoryOptions: [],
      //物资名称
      materialsOptions: [],
      //物料储位
      storageSpacesOptions: [],
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
        //创建时间
        createdTime: null,
        //清零时间
        clearTime: null,
        materialsId: null,
        storageSpacesId: null
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
    this.onLoad();
    // this.getCategoryOptions()
    this.getMaterialsOptions()
    this.getSpacesOptions()
  },
  methods: {
    // 获取物料类别下拉列表
    getCategoryOptions () {
      const param = {}
      listMaterialsCategoryBox(param).then((res) => {
        let options = []
        res.data.forEach(item => {
          let i = {
            id: item.id,
            name: item.category_name
          }
          options.push(i)
        });
        this.categoryOptions = options
      })
    },
    //获取物料代码下拉列表
    getMaterialsOptions () {
      const param = {}
      listMaterialsBoxM(param).then((res) => {
        let options = []
        res.forEach(item => {
          let i = {
            id: item.materials_code,
            name: item.materials_name
          }
          options.push(i)
        });
        this.materialsOptions = options
      })
    },
    // 获取物料储位下拉列表
    getSpacesOptions () {
      const param = {}
      listMaterialsStorageSpacesBox(param).then((res) => {
        let options = []
        res.forEach(item => {
          let i = {
            id: item.id,
            name: item.materials_small_name
          }
          options.push(i)
        });
        this.storageSpacesOptions = options
      })
    },
    //载入数据
    onLoad () {
      //创建时间
      if (this.query.createdTime && this.query.createdTime[0]) {
        this.query.createStartTime = this.query.createdTime[0]
        this.query.createEndTime = this.query.createdTime[1]
      } else {
        this.query.createStartTime = ''
        this.query.createEndTime = ''
      }
      //清零时间
      if (this.query.clearTime && this.query.clearTime[0]) {
        this.query.clearStartTime = this.query.clearTime[0]
        this.query.clearEndTime = this.query.clearTime[1]
      } else {
        this.query.clearStartTime = ''
        this.query.clearEndTime = ''
      }

      this.table.loading = true;//加载状态
      materialsCoalStockList(this.page, this.query).then(res => {
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
      this.dialog.title = '煤场库存调整'
      this.dialog.data = row;
      // console.log(row, "row")
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
    //新增
    handleAdd (type) {
      this.dialog.type = type;
      this.dialog.title = '新增期初煤场库存 '
      this.dialog.visible = true;
    }
  }
}
</script>

<style scoped lang="scss">
.el-pagination {
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
  justify-content: space-between;
}

.el-button--success {
  background-color: #b3e19d;
  color: white;
  border: white;
}

// .el-form-item__conten {
//   margin-left: 500px;
// }
// .el-col-3 {
//   width: 12.5%;
//   text-align: right;
// }
// .el-dialog__wrapper {
//   ::v-deep.el-col-3 {
//     width: 12.5%;
//     text-align: right;
//   }
// }
// .el-button--success {
//   background-color: #b3e19d;
//   color: white;
//   border: white;
// }
// .el-dialog__wrapper {
//   ::v-deep.el-dialog {
//     .el-dialog__header {
//       background-color: whitesmoke;
//       border-radius: 10px 10px 0 0;
//       padding: 10px 20px 10px;
//     }
//   }
// }
// .el-dialog__wrapper {
//   ::v-deep.el-dialog {
//     border-radius: 10px;
//   }
// }
// .el-dialog__wrapper {
//   ::v-deep.el-dialog {
//     .el-dialog__body {
//       padding: 10px 40px 40px 40px;
//     }
//   }
// }

// .customDialogStyle .el-dialog__body {
//   padding: 10px 40px 40px;
// }

.el-card__body {
  padding: 10px 20px 20px 10px;
}
</style>

