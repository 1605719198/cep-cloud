<template>
  <div style="padding: 0px 10px;">
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
                <el-form-item prop="department_id">
                  <select-tree v-model="query.department_id"
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
                <el-form-item prop="completeState">
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
                  <el-button v-hasPermi="['safeSiteEnvironmentalGovernance_list']"
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
                <el-button v-hasPermi="['safeSiteEnvironmentalGovernance_save']"
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
                             minWidth="150"
                             align="left"
                             prop="department_name" />
            <el-table-column label="工作事项"
                             minWidth="150"
                             align="left"
                             prop="work_items" />
            <el-table-column label="工作目标"
                             minWidth="200"
                             align="left"
                             prop="work_target" />
            <el-table-column label="完成情况"
                             minWidth="150"
                             :formatter="stateFormat"
                             align="left"
                             prop="complete_state" />
            <el-table-column label="整改前照片"
                             minWidth="200"
                             align="left"
                             prop="rectification_before_url">
              <template slot-scope="scope">
                <!-- <img style="width:120px; height:80px;"
                   :src="scope.row.rectification_before_url[0].url"
                   alt=""> -->
                <el-image :z-index="10000"
                          style="width: 120px; height: 80px;"
                          :src="scope.row.rectification_before_url[0].url"
                          :preview-src-list="srcList">
                </el-image>
              </template>
            </el-table-column>
            <el-table-column label="整改后照片"
                             minWidth="200"
                             align="left"
                             prop="rectification_after_url">
              <template slot-scope="scope">
                <!-- <img style="width:120px; height:80px;"
                   :src="scope.row.rectification_after_url[0].url"
                   alt=""> -->
                <el-image :z-index="10000"
                          style="width: 120px; height: 80px;"
                          :src="scope.row.rectification_after_url[0].url"
                          :preview-src-list="afterSrcList">
                </el-image>
              </template>
            </el-table-column>
            <!-- <el-table-column label="负责人"
                           minWidth="200"
                           align="left"
                           prop="responsible_user_name" />
          <el-table-column label="工号"
                           minWidth="200"
                           align="left"
                           prop="job_number" />
          <el-table-column label="比例"
                           minWidth="200"
                           align="left"
                           prop="proportion" /> -->
            <el-table-column fixed="right"
                             label="操作"
                             width="260"
                             align="center">
              <template slot-scope="scope">
                <el-button v-hasPermi="['safeSiteEnvironmentalGovernance_update']"
                           size="mini"
                           plain
                           icon="el-icon-edit"
                           type="text"
                           @click="handleEdit('edit',scope.$index, scope.row)">修改
                </el-button>
                <el-button v-hasPermi="['safeSiteEnvironmentalGovernance_delete']"
                           v-if="scope.row.complete_state == 2"
                           size="mini"
                           plain
                           icon="el-icon-delete"
                           type="text"
                           @click="handleDelete(scope.$index, scope.row)">删除
                </el-button>
                <el-button v-hasPermi="['safeSiteEnvironmentalGovernance_listProportions']"
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
import { safeGovernanceList, safeGovernanceDelete } from "@/api/security/si/governance/index";
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
        department_id: null,
        completeState: null
      },
      table: {
        border: true,
        loading: true,
      },
      tableData: [],
      //单位
      deptData: [],
      //选中回显的数据
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
      //查看图片列表
      srcList: [],
      afterSrcList: []
    }
  },
  created () {
    this.onLoad();
    this.getOrganizationTree()
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
      this.query.department_id = obj.value;
    },
    //载入数据
    onLoad () {
      this.table.loading = true;//加载状态
      safeGovernanceList(this.page, this.query).then(res => {
        this.table.loading = false;
        let data = res.data;//表格相关数据
        this.page.total = data.total;//数据总数
        //JSON转数组
        if (data.records.length != 0) {
          data.records.forEach(item => {
            item.rectification_before_url = JSON.parse(item.rectification_before_url)
            item.rectification_after_url = JSON.parse(item.rectification_after_url)
          })
          //可查看图片的数组
          // data.records.forEach(item => {
          //   item.rectification_before_url.forEach(i => {
          //     this.srcList.push(i.url)
          //   })
          // })
          // data.records.forEach(item => {
          //   item.rectification_after_url.forEach(i => {
          //     this.afterSrcList.push(i.url)
          //   })
          // })
        }
        this.tableData = data.records;//表格数据
        // console.log(this.tableData)
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
      this.deptForm = { value: '', label: '' }
      this.handleQuery()
    },
    //新增
    handleAdd (type) {
      this.dialog.type = type
      this.dialog.title = '新增现场环境治理'
      this.dialog.data = {};
      this.dialog.visible = true;
    },
    //导出
    handleDerive () { },
    //修改
    handleEdit (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '编辑现场环境治理'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    //详情
    handleView (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '场环境治理详情'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    // 删除
    handleDelete (index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        safeGovernanceDelete({ id: row.id }).then(res => {
          this.onLoad();
          this.$message({ type: 'success', message: res.data.msg });
        }, error => {
          window.console.log(error);
        });
      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    // 同步默认单位翻译
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

