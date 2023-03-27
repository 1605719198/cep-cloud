<template>
  <div style="padding: 0px 10px;">
    <div class="main">
      <div class="avue-crud el-card__body "
           style="width: 98%;border: 0;">
        <div class="avue-crud__search"
             style="border: 0">
          <el-row>
            <el-col :span="20">
              <!-- 表单筛选 -->
              <el-form :model="query"
                       ref="query"
                       :inline="true">
                <el-form-item label="计划日期" prop="planTime">
                  <el-date-picker v-model="query.planTime"
                                  type="daterange"
                                  range-separator="至"
                                  start-placeholder="计划完成开始日期"
                                  end-placeholder="计划完成结束日期"
                                  value-format="yyyy-MM-dd">
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="单位" prop="departmentId">
                  <select-tree v-model="query.departmentId"
                               placeholder="单位"
                               :selectForm="deptForm"
                               :data="deptData"
                               :isSelectNode="true"
                               :isClearable="true"
                               :isAccordion="true"
                               :isFilterable="true"
                               :isNodeChildrenNull="true"
                               @change="getDept" />
                </el-form-item>
                <el-form-item label="完成情况" prop="completeState">
                  <el-select class="customSelectStyle"
                             v-model="query.completeState"
                             :popper-append-to-body="false"
                             style="width:100%;"
                             clearable
                             placeholder="完成情况">
                    <el-option v-for="item in options"
                               :key="item.id"
                               :label="item.name"
                               :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
                <!-- 操作按钮 -->
                <el-form-item>
                  <el-button v-hasPermi="['safeImportantWork_list']"
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
                <el-button v-hasPermi="['safeImportantWork_save']"
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
            <el-table-column label="单位"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="department_name" />
            <el-table-column label="主体"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="subject" />
            <!-- <el-table-column label="专业"
                           minWidth="200"
                           align="left"
                           prop="major" /> -->
            <el-table-column label="完成目标"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="complete_target"
                             show-overflow-tooltip />
            <el-table-column label="完成情况"
                             sortable
                             minWidth="200"
                             align="left"
                             :formatter="stateFormat"
                             prop="complete_state" />
            <el-table-column label="计划完成时间"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="plan_complete_time" />
            <!-- <el-table-column label="负责人"
                           minWidth="200"
                           align="left"
                           prop="responsible_user_name" />
          <el-table-column label="工号"
                           minWidth="200"
                           align="left"
                           prop="job_number" />
          <el-table-column label="奖励比例"
                           minWidth="200"
                           align="left"
                           prop="reward_proportion" /> -->
            <el-table-column label="备注"
                             minWidth="200"
                             align="left"
                             prop="remarks" />
            <el-table-column fixed="right"
                             label="操作"
                             width="260"
                             align="center">
              <template slot-scope="scope">
                <el-button v-hasPermi="['safeImportantWork_update']"
                           size="mini"
                           plain
                           icon="el-icon-edit"
                           type="text"
                           @click="handleEdit('edit',scope.$index, scope.row)">修改
                </el-button>
                <el-button v-if="scope.row.complete_state == 2"
                           v-hasPermi="['safeImportantWork_delete']"
                           size="mini"
                           plain
                           icon="el-icon-delete"
                           type="text"
                           @click="handleDelete(scope.$index, scope.row)">删除
                </el-button>
                <el-button v-hasPermi="['safeImportantWork_listProportions']"
                           size="mini"
                           plain
                           icon="el-icon-view"
                           type="text"
                           @click="handleView('view',scope.$index, scope.row)">详情
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
            <Views v-if="dialog.type === 'view'"
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
import { safeImportantWorkList, safeImportantWorkDelete } from "@/api/security/si/keySafetyWork/index";
import { getOrganizationDeptTree } from '@/api/human/hm/humanResourceTree'
import Update from "./update.vue"
import Views from "./view.vue"
export default {
  components: {
    Update,
    Views
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
        order: "plan_complete_time",
        orderby: "desc",
      },
      query: {
        planTime: null,
        departmentId: null,
        completeState: null
      },
      table: {
        border: true,
        loading: true,
      },
      tableData: [],
      //单位
      deptData: [],
      //绑定属性和值
      deptForm: { value: '', label: '' },
      options: [
        {
          id: '',
          name: "全部"
        },
        {
          id: 1,
          name: "已完成"
        },
        {
          id: 2,
          name: "未完成"
        }
      ],
    }
  },
  created () {
    this.getOrganizationTree()
    this.onLoad();
  },
  methods: {
    //获取单位下拉树
    getOrganizationTree () {
      getOrganizationDeptTree().then(res => {
        // console.log(res, "res")
        this.deptData = res.data
      })
    },
    //更改赋值
    getDept (obj) {
      this.query.departmentId = obj.value;
    },
    //载入数据
    onLoad () {
      if (this.query.planTime && this.query.planTime[0]) {
        this.query.planStartTime = this.query.planTime[0] + ' ' + '00:00:00'
        this.query.planEndTime = this.query.planTime[1] + ' ' + '23:59:59'
      } else {
        this.query.planStartTime = ''
        this.query.planEndTime = ''
      }
      this.table.loading = true;//加载状态
      safeImportantWorkList(this.page, this.query).then(res => {
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
        this.page.order = "plan_complete_time";
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
      this.deptForm = { value: '', label: '' }
      // console
      this.handleQuery()
    },
    //新增
    handleAdd (type) {
      this.dialog.type = type
      this.dialog.title = '新增安全重点工作'
      this.dialog.data = {};
      this.dialog.visible = true;
    },
    //导出
    handleDerive () { },
    //修改
    handleEdit (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '编辑安全重点工作'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    //详情
    handleView (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '安全重点工作详情'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    // 删除
    handleDelete (index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        safeImportantWorkDelete({ id: row.id }).then(res => {
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
  // height: calc(100vh - 10px);
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

.el-pagination {
  white-space: nowrap;
  padding: 25px 0px 20px 0px;
  color: #303133;
  font-weight: 700;
}
</style>
<!-- tooltip文字提示样式 -->
<style>
.el-tooltip__popper {
  font-size: 14px;
  max-width: 50%;
}
</style>

