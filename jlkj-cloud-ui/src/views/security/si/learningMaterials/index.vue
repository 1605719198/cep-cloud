<template>
  <div style="padding:0 10px">
    <div class="main">
      <div class="avue-crud el-card__body"
           style="width: 98%;border: 0;">
        <el-row :gutter="20">
          <!-- 左侧筛选树 -->
          <el-col :span="4"
                  class="left_tree">
            <!-- <h5>知识类别</h5> -->
            <el-tree ref="tree"
                     node-key="id"
                     :data="knowledgeData"
                     default-expand-all
                     :expand-on-click-node="false"
                     @node-click="handleNodeClick">
            </el-tree>
          </el-col>
          <!-- 右侧列表 -->
          <el-col :span="20">
            <div>
              <div class="avue-crud__search"
                   style="border: 0">
                <el-row>
                  <el-col :span="20">
                    <!-- 表单筛选 -->
                    <el-form :model="query"
                             ref="query"
                             :inline="true">
                      <el-form-item prop="title">
                        <el-input v-model="query.title"
                                  placeholder="标题"></el-input>
                      </el-form-item>
                      <el-form-item prop="keywords">
                        <el-input v-model="query.keywords"
                                  placeholder="关键字"></el-input>
                      </el-form-item>
                      <!-- 操作按钮 -->
                      <el-form-item>
                        <el-button v-hasPermi="['knowledgelibrary_list']"
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
                    <div style="text-align: right;">
                      <el-button v-hasPermi="['knowledgelibrary_insert']"
                                 type="primary"
                                 size="mini"
                                 icon="el-icon-plus"
                           style="margin: 5px 0;"
                                 @click="handleAdd('add')">新增
                      </el-button>
                      <!-- <el-button size="mini"
                         icon="el-icon-s-promotion"
                         @click="handleDerive('add')">导出
              </el-button> -->
                    </div>
                  </el-col>
                </el-row>
              </div>
              <div>
                <el-table height="70vh"
                          size="small"
                          v-loading="table.loading"
                          :data="tableData"
                          stripe
                          @sort-change="handleSort">
                  <el-table-column label="序号"
                                   type="index"
                                   width="55"
                                   align="center" />
                  <el-table-column label="标题"
                                   sortable
                                   minWidth="150"
                                   align="left"
                                   prop="title" />
                  <el-table-column label="知识类别"
                                   sortable
                                   minWidth="150"
                                   align="left"
                                   prop="knowledge_category_name" />
                  <el-table-column label="关键字"
                                   sortable
                                   minWidth="150"
                                   align="left"
                                   prop="keywords" />
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
                                   width="260"
                                   align="center">
                    <template slot-scope="scope">
                      <el-button v-hasPermi="['knowledgelibrary_update']"
                                 size="mini"
                                 plain
                                 icon="el-icon-edit"
                                 type="text"
                                 @click="handleEdit('edit',scope.$index, scope.row)">修改
                      </el-button>
                      <el-button v-hasPermi="['knowledgelibrary_delete']"
                                 size="mini"
                                 plain
                                 icon="el-icon-delete"
                                 type="text"
                                 @click="handleDelete(scope.$index, scope.row)">删除
                      </el-button>
                      <el-button v-hasPermi="['knowledgelibrary_list']"
                                 size="mini"
                                 plain
                                 icon="el-icon-view"
                                 type="text"
                                 @click="handleView('view',scope.$index, scope.row)">详情
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
            </div>
          </el-col>
        </el-row>
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
              <Update v-if="dialog.type === 'add' || dialog.type === 'edit' || dialog.type === 'view'"
                      :type="dialog.type"
                      :data="dialog.data"
                      @submitSave="submitSave"
                      @close="dialog.visible=false" />
            </template>
          </el-dialog>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { knowledgelibraryList, knowledgelibraryDelete } from "@/api/security/si/learningMaterials/index";
import { getknowledgecategoryTree } from "@/api/security/si/WorkTicketApi";
import { treeDataformat } from "@/utils/data"
import Update from "./update.vue"
export default {
  components: {
    Update
  },
  data () {
    return {
      //知识类别
      knowledgeData: [],
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
        order: "ceate_time",
        orderby: "desc",
      },
      query: {
        keywords: '',
        title: ''
      },
      table: {
        border: true,
        loading: true,
      },
      tableData: [
        // {contents: '<p>模拟 Ajax 异步设置内容 HTML</p>'}
      ],
      //知识类别id (0查全部)
      knowledge_category_id: '0'
    }
  },
  created () {
    this.getknowledgecategoryTreeData()
    this.onLoad();
  },
  methods: {
    //获取知识类别树
    getknowledgecategoryTreeData () {
      getknowledgecategoryTree().then(res => {
        // console.log(res)
        this.knowledgeData = treeDataformat(res.data.data, 'id', 'pid', 'children');
        // console.log(this.knowledgeData, "knowledgeData")
      })
    },
    handleNodeClick (data) {
      // console.log(data);
      this.knowledge_category_id = data.id
      this.onLoad();
    },
    //载入数据
    onLoad () {
      this.query.knowledge_category_id = this.knowledge_category_id
      this.table.loading = true;//加载状态
      knowledgelibraryList(this.page, this.query).then(res => {
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
    //查询
    handleQuery () {
      this.page.current = 1;
      // console.log(this.query.issueDate)
      this.onLoad();
    },
    // 重置
    handleReset () {
      this.page.current = 1
      this.$refs["query"].resetFields()
      //清空选中状态
      this.knowledge_category_id = '0'
      this.$refs.tree.setCurrentKey(null)
      this.handleQuery()
    },
    //新增
    handleAdd (type) {
      this.dialog.type = type
      this.dialog.title = '新增资料学习'
      this.dialog.data = {};
      this.dialog.visible = true;
    },
    //修改
    handleEdit (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '编辑资料学习'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    //详情
    handleView (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '资料学习详情'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    // 删除
    handleDelete (index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        knowledgelibraryDelete({ id: row.id }).then(res => {
          this.onLoad();
          this.$message({ type: 'success', message: res.data.msg });
        }, error => {
          window.console.log(error);
        });
      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    // 同步默认状态翻译
    stateFormat (row) {
      var stateCode = ''
      for (var i = 0; i < this.options.length; i++) {
        if (this.options[i].id == row.complete_state) {
          stateCode = this.options[i].name
          break
        }
      }
      return stateCode
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
.main {
  // min-height: 86vh;
  // height: 86vh;
  background-color: #fff;
}
.left_tree {
  background-color: #fff;
  h5 {
    margin: 0;
    font-size: 18px;
    height: 70px;
    line-height: 70px;
    box-sizing: border-box;
    text-align: center;
  }
  .el-tree {
    // height: 76vh;
    height: 71vh;
    overflow: auto;
  }
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

.el-pagination {
  white-space: nowrap;
  padding: 25px 0px 20px 0px;
  color: #303133;
  font-weight: 700;
}
</style>

