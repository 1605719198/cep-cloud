<template>
  <div class="main">
    <div class="avue-crud el-card__body"
         style="width: 98%;border: 0;">
      <!-- 头部 -->
      <div class="avue-crud__search"
           style="border: 0">
        <el-row>
            <!-- 表单筛选 -->
        </el-row>
        <div class="el-form-item__content">
          <el-button v-hasPermi="['safeIndex_insert']"
                     type="primary"
                     size="mini"
                     plain
                     icon="el-icon-plus"
                     style="margin: 5px 0;"
                     @click="handleAdd('add')">新增
          </el-button>
        </div>
      </div>
      <!-- 内容 -->
      <div>
        <el-table :data="tableData"
                  stripe
                  height="71vh"
                  size="small"
                  v-loading="table.loading"
                  @sort-change="handleSort">
          <!--这是转圈，加载状态，实时读取，默认true，加载成功后改false-->
          <el-table-column label="序号"
                           type="index"
                           width="55" />
          <el-table-column label="规则编号"
                           minWidth="55"
                           align="left"
                           prop="rule_number" />
          <el-table-column label="规则描述"
                           minWidth="120"
                           align="left"
                           prop="rule_describe" />
          <el-table-column label="规则等级"
                           minWidth="120"
                           align="left"
                           prop="rule_level" />
          <el-table-column label="数值上限"
                           minWidth="120"
                           align="left"
                           prop="rule_up" />
          <el-table-column label="数值下限"
                           minWidth="120"
                           align="left"
                           prop="rule_down" />
          <el-table-column label="创建人"
                           class-name=""
                           minWidth="120"
                           align="left"
                           prop="create_user_name" />
          <el-table-column label="创建时间"
                           sortable
                           minWidth="120"
                           align="left"
                           prop="create_time" />
          <el-table-column label="状态"
                           minWidth="120"
                           align="center"
                           prop="status" />
          <el-table-column fixed="right"
                           label="操作"
                           width="180"
                           align="center">
            <template slot-scope="scope">
              <el-button v-hasPermi="['safeIndex_update']"
                         size="mini"
                         plain
                         icon="el-icon-edit"
                         type="text"
                         @click="handleEdit('edit',scope.$index, scope.row)">修改
              </el-button>
              <el-button v-hasPermi="['safeIndex_delete']"
                         size="mini"
                         plain
                         icon="el-icon-delete"
                         type="text"
                         @click="handleDelete(scope.$index, scope.row)">删除
              </el-button>
            </template>
          </el-table-column>
          <!-- 分页 -->
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
            <!--数据总数-->
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
            <ES v-if="dialog.type === 'add' || dialog.type === 'edit'"
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
import { EList, EDelete } from "@/api/security/si/parameter/environmentalStandard";
import ES from "./ES.vue"

export default {
  components: {
    ES
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
      // query: {
      //     targetItemTypeId: 0, //查询条件
      // },
      table: {
        border: true,
        loading: true,
      },
      tableData: [],
    };
  },
  created () {
    this.onLoad();
  },
  methods: {
    //载入数据
    onLoad () {
      this.table.loading = true;//加载状态
      EList(this.page, this.query).then(res => {
        this.table.loading = false;
        let data = res.data;//表格相关数据
        this.page.total = data.total;//数据总数
        this.tableData = data.records;//表格数据
      }, error => {
        this.table.loading = false;
        window.console.log(error);
      });
    },
    //params写法
    // onLoad () {
    //     this.table.loading = true;//加载状态
    //     //{...this.page,...this.query}
    //     EList({
    //         size:this.page.size,
    //         current:this.page.current,
    //         order:this.page.order,
    //         orderby:this.page.orderby
    //     }).then(res => {
    //         this.table.loading = false;
    //         console.log(res);
    //         let data = res.data;//表格相关数据
    //         this.page.total = data.total;//数据总数
    //         this.tableData = data.records;//表格数据
    //     }, error => {
    //         this.table.loading = false;
    //         window.console.log(error);
    //     });
    // },
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
      this.page.current = 1
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
      this.dialog.title = '新增规则等级'
      this.dialog.data = {};
      this.dialog.visible = true;
    },
    //修改
    handleEdit (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '编辑规则等级'
      this.dialog.data = row;
      this.dialog.visible = true;
    },
    // 删除
    //index————主键标识，不能为空，必须写
    handleDelete (index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        EDelete({ id: row.id }).then(res => {
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
    submitSave (msg) {
      this.dialog.visible = false;
      this.dialog.data = {};
      this.onLoad();
    },
  },
};
</script>

<style scoped lang="scss">
.main {
  height: 89vh;
  background-color: #fff;
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

.el-dialog__wrapper {
  ::v-deep.el-col {
    margin-bottom: 0px;
  }
}
</style>
