<template>
  <div class="main">
    <div class="avue-crud el-card__body"
         style="width: 98%;border: 0;">
      <div class="avue-crud__search"
           style="border: 0">
        <el-row>
          <el-col :span="20">
            <!-- 表单筛选 -->
            <el-form :model="query"
                     ref="query"
                     :inline="true">
              <el-form-item>
                <select-tree v-model="query.construction_unit_id"
                             placeholder="所属单位"
                             :selectForm="deptForm"
                             :data="deptData"
                             :validate="{flag:'indep'}"
                             :isSelectNode="true"
                             :isClearable="true"
                             :isAccordion="true"
                             :isFilterable="true"
                             :isNodeChildrenNull="true"
                             @change="getDept" />
              </el-form-item>
              <el-form-item prop="person_name">
                <el-input v-model="query.person_name"
                          placeholder="人员姓名"></el-input>
              </el-form-item>
              <!-- 操作按钮 -->
              <el-form-item>
                <el-button v-hasPermi="['constructionunitpersons_list']"
                           @click="handleQuery"
                           type="primary"
                           icon="el-icon-search"
                           size="mini">搜索</el-button>
                <el-button @click="handleReset"
                           icon="el-icon-refresh-left"
                           size="mini">重置</el-button>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="4">
            <div style="text-align: right;margin-right: 10px;">
              <el-button v-hasPermi="['constructionunitpersons_insert']"
                         type="primary"
                         size="mini"
                         icon="el-icon-plus"
                           style="margin: 5px 0;"
                         @click="handleAdd('add')">新增
              </el-button>
            </div>
          </el-col>
        </el-row>
      </div>
      <div>
        <el-table height="71vh"
                  size="small"
                  v-loading="table.loading"
                  :data="tableData"
                  stripe
                  @sort-change="handleSort">
          <el-table-column label="序号"
                           type="index"
                           width="55"
                           align="center" />
          <el-table-column label="单位名称"
                           sortable
                           minWidth="150"
                           align="left"
                           prop="construction_unit_name" />
          <el-table-column label="单位类型"
                           sortable
                           minWidth="150"
                           align="left"
                           :formatter="typeFormat"
                           prop="type" />
          <el-table-column label="姓名"
                           sortable
                           minWidth="150"
                           align="left"
                           prop="person_name" />
          <el-table-column label="工种"
                           sortable
                           minWidth="150"
                           align="left"
                           prop="work_type_name" />
          <el-table-column label="作业证编号"
                           sortable
                           minWidth="150"
                           align="left"
                           prop="work_card_code" />
          <el-table-column label="是否负责人"
                           sortable
                           minWidth="150"
                           align="left"
                           prop="is_leader_text" />
          <el-table-column label="创建人"
                           sortable
                           minWidth="150"
                           align="left"
                           prop="create_user_name" />
          <el-table-column label="创建时间"
                           sortable
                           minWidth="150"
                           align="left"
                           prop="ceate_time" />
          <el-table-column fixed="right"
                           label="操作"
                           width="180"
                           align="center">
            <template slot-scope="scope">
              <el-button v-hasPermi="['constructionunitpersons_update']"
                         size="mini"
                         plain
                         icon="el-icon-edit"
                         type="text"
                         @click="handleEdit('edit',scope.$index, scope.row)">修改
              </el-button>
              <el-button v-hasPermi="['constructionunitpersons_delete']"
                         size="mini"
                         plain
                         icon="el-icon-delete"
                         type="text"
                         @click="handleDelete(scope.$index, scope.row)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px;right: 0;padding: 25px 0px 20px 20px;"
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
      <!-- 弹窗 -->
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
  </div>

</template>

<script>
import { constructionunitpersonsList, constructionunitpersonsDelete } from "@/api/security/si/parameter/constructionPersonnel";
import { getconstructionunitTree } from "@/api/security/si/WorkTicketApi"
import { treeDataformat } from "@/utils/data"
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
        width: '60%',
        data: {},
      },
      page: {
        size: 20,
        current: 1,
        total: 1,
        order: "ceate_time",
        orderby: "desc",
      },
      query: {
        targetItemTypeId: 0,
        construction_unit_id: '',
        person_name: '',
        type: 0

      },
      table: {
        border: true,
        loading: true,
      },
      tableData: [],
      //所属单位
      deptData: [],
      //绑定属性和值
      deptForm: { value: '', label: '' }
    }
  },
  created () {
    this.onLoad();
    this.getOrganizationTree()
  },
  methods: {
    //获取施工单位下拉树
    getOrganizationTree () {
      getconstructionunitTree().then(res => {

        this.deptData = treeDataformat(res.data.data, 'id', 'pid', 'children');
        // console.log(this.deptData)
      })
    },
    //载入数据
    onLoad () {
      this.table.loading = true;//加载状态

      constructionunitpersonsList(this.page, this.query).then(res => {
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
        this.page.order = "ceate_time";
        this.page.orderby = "desc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.onLoad();
    },
    //更改赋值
    getDept (obj) {
      this.query.construction_unit_id = obj.value;
    },
    //查询
    handleQuery () {
      this.page.current = 1;
      // console.log(this.query.issueDate)
      // console.log(this.query,this.page)
      // console.log(this.query)
      // console.log(this.page)
      this.onLoad();

    },
    // 重置
    handleReset () {
      this.page.current = 1
      this.query = {
        targetItemTypeId: 0,
        construction_unit_id: '',
        person_name: '',
        type: 0
      }
      this.deptForm = { value: '', label: '' }
      this.onLoad()
    },
    //新增
    handleAdd (type) {
      this.dialog.type = type
      this.dialog.title = '新增施工人员'
      this.dialog.data = {};
      this.dialog.visible = true;
    },
    //修改
    handleEdit (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '编辑施工人员'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    // 删除
    handleDelete (index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        constructionunitpersonsDelete({ id: row.id }).then(res => {
          this.onLoad();
          this.$message({ type: 'success', message: res.data.msg });
        }, error => {
          window.console.log(error);
        });
      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    //同步单位类型翻译
    typeFormat (row) {
      return row.type == 1 ? '本厂' : '外委'
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
.main {
  height: 89vh;
  background-color: #fff;
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
  ::v-deep.el-col {
    margin-bottom: 0px;
  }
}

.el-button--success {
  background-color: #b3e19d;
  color: white;
  border: white;
}
</style>

