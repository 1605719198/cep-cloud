<template>
  <div style="padding: 0px 10px;">
    <div class="main">
      <div class="avue-crud el-card__body"
           style="width: 99%;border: 0;">
        <div class="avue-crud__search"
             style="border: 0">
          <el-row>
              <!-- 表单筛选 -->
              <el-form :model="query"
                       ref="query"
                       :inline="true">
                <el-form-item label="上报日期" prop="danger_time">
                  <el-date-picker v-model="query.danger_time"
                                  type="daterange"
                                  range-separator="至"
                                  start-placeholder="上报开始日期"
                                  end-placeholder="上报结束日期"
                                  value-format="yyyy-MM-dd">
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="作业区域" prop="safe_area_id">
                  <el-select class="customSelectStyle"
                             v-model="query.safe_area_id"
                             :popper-append-to-body="false"
                             style="width:100%;"
                             clearable
                             filterable
                             placeholder="作业区域">
                    <el-option v-for="item in operation0ptions"
                               :key="item.id"
                               :label="item.name"
                               :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="隐患等级" prop="danger_type_id">
                  <el-select class="customSelectStyle"
                             v-model="query.danger_type_id"
                             :popper-append-to-body="false"
                             style="width:100%;"
                             clearable
                             placeholder="隐患等级">
                    <el-option v-for="item in hidden0ptions"
                               :key="item.id"
                               :label="item.name"
                               :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="状态" prop="danger_status">
                  <el-select class="customSelectStyle"
                             v-model="dangerStatus"
                             :popper-append-to-body="false"
                             style="width:100%;"
                             clearable
                             placeholder="状态">
                    <el-option v-for="item in stateOptions"
                               :key="item.id"
                               :label="item.name"
                               :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="是否超时" prop="timeout_status">
                  <el-select class="customSelectStyle"
                             v-model="timeoutStatus"
                             :popper-append-to-body="false"
                             style="width:100%;"
                             clearable
                             placeholder="是否超时">
                    <el-option v-for="item in overtimeOptions"
                               :key="item.id"
                               :label="item.name"
                               :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
                <!-- 操作按钮 -->
                <div class="el-form-item__content"
                     style="margin-left: 0px;">
                  <el-button v-hasPermi="['danger_list']"
                             @click="handleQuery"
                             type="primary"
                             icon="el-icon-search"
                             size="mini">搜索</el-button>
                  <el-button @click="handleReset"
                             icon="el-icon-refresh-left"
                             type="default"
                             size="mini">重置</el-button>
                </div>

              </el-form>
            <div class="el-form-item__content">
              <el-button v-hasPermi="['danger_insert']"
                         type="primary"
                         size="mini"
                         plain
                         icon="el-icon-plus"
                         style="margin: 5px 0;"
                         @click="handleAdd('add')">新增
              </el-button>
              <!-- <el-button size="mini"
                         icon="el-icon-s-promotion"
                         @click="handleDerive('add')">导出
              </el-button> -->
            </div>
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
            <el-table-column label="等级"
                             minWidth="150"
                             align="left"
                             prop="danger_type_name" />
            <el-table-column label="上报时间"
                             minWidth="150"
                             align="left"
                             prop="danger_time" />
            <el-table-column label="上报单位"
                             minWidth="150"
                             align="left"
                             prop="submit_depart_name" />
            <el-table-column label="上报人"
                             minWidth="150"
                             align="left"
                             prop="submit_person_name" />
            <el-table-column label="工作区域"
                             minWidth="150"
                             align="left"
                             prop="safe_area_name" />
            <el-table-column label="责任单位"
                             minWidth="150"
                             align="left"
                             prop="duty_depart_name" />
            <el-table-column label="负责人"
                             minWidth="150"
                             align="left"
                             prop="duty_person_name" />
            <el-table-column label="验收人"
                             minWidth="150"
                             align="left"
                             prop="acceptance_person_name" />
            <el-table-column label="限期完成"
                             minWidth="150"
                             align="left"
                             prop="danger_deadline_time" />
            <el-table-column label="隐患内容"
                             minWidth="150"
                             align="left"
                             prop="danger_content"
                             show-overflow-tooltip />
            <el-table-column label="上报图片"
                             minWidth="150"
                             align="left">
              <template slot-scope="scope">
                <el-button v-if="scope.row.submit_image1"
                           size="mini"
                           plain
                           type="primary"
                           @click="handleLook(scope.row)">查看图片
                </el-button>
              </template>
            </el-table-column>
            <el-table-column label="超时状态"
                             minWidth="150"
                             align="left"
                             prop="timeout_status"
                             :formatter="timeoutStateFormat" />
            <el-table-column label="整改状态"
                             fixed="right"
                             minWidth="150"
                             align="left"
                             prop="danger_status"
                             :formatter="stateFormat" />
            <el-table-column fixed="right"
                             label="操作"
                             width="260"
                             align="center">
              <template slot-scope="scope">
                <el-button v-hasPermi="['danger_info']"
                           size="mini"
                           plain
                           icon="el-icon-view"
                           type="text"
                           @click="handleView('view',scope.$index, scope.row)">详情
                </el-button>
                <el-button v-hasPermi="['danger_acceptance']"
                           v-if="scope.row.danger_status==0 && scope.row.duty_person_id == userInfo.userId"
                           size="mini"
                           plain
                           icon="el-icon-edit"
                           type="primary"
                           @click="handleEdit('edit',scope.$index, scope.row)">整改
                </el-button>
                <el-button v-hasPermi="['danger_dispose']"
                           v-if="scope.row.danger_status==1 && scope.row.acceptance_person_id == userInfo.userId"
                           size="mini"
                           plain
                           icon="el-icon-notebook-1"
                           type="primary"
                           @click="handleCheck('check',scope.$index, scope.row)">验收
                </el-button>
                <el-button v-hasPermi="['danger_delete']"
                           v-if="scope.row.danger_status==0 && scope.row.submit_person_id == userInfo.userId"
                           size="mini"
                           plain
                           icon="el-icon-delete"
                           type="text"
                           @click="handleDelete(scope.$index, scope.row)">删除
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
            <Add v-if="dialog.type === 'add'"
                 @submitSave="submitSave"
                 @close="dialog.visible=false" />
            <TabsMessage v-if="dialog.type === 'view' || dialog.type === 'edit' ||  dialog.type === 'check'"
                         :type="dialog.type"
                         :danger_id="dialog.data.danger_id"
                         @submitSave="submitSave"
                         @close="dialog.visible=false" />
          </template>
        </el-dialog>
      </div>
      <!-- element图片预览组件 -->
      <el-image-viewer v-if="showViewer"
                       :on-close="()=>{showViewer=false}"
                       :url-list="srcList" />
    </div>
  </div>
</template>

<script>
import { getSafetyArea, dangertype } from "@/api/security/si/WorkTicketApi"
import { dangerList, safeDangerDelete, safeDangerInfo } from "@/api/security/si/hiddenDanger/index";
import Add from "./add.vue"
import TabsMessage from './tabsMessage.vue'
import { mapGetters } from "vuex";
import moment from "moment";
export default {
  components: {
    Add,
    TabsMessage,
    //引入element图片预览组件
    'el-image-viewer': () => import('element-ui/packages/image/src/image-viewer')
  },
  provide () {
    return {
      fatherSubmitSave: this.submitSave,
      fatherClose: this.close
    }
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
        order: "danger_time",
        orderby: "desc",
      },
      //状态（解决双向绑定回显问题，为空的时候不显示数据）
      dangerStatus: '',
      //超时状态（解决双向绑定回显问题，为空的时候不显示数据）
      timeoutStatus: '',
      query: {
        danger_type_id: '',
        safe_area_id: '',
        danger_status: '',
        timeout_status: '',
        danger_time: null,
      },
      table: {
        border: true,
        loading: true,
      },
      tableData: [],
      //隐患下拉
      hidden0ptions: [],
      //作业下拉
      operation0ptions: [],
      //状态下拉
      stateOptions: [
        {
          id: -1,
          name: "全部"
        },
        {
          id: 0,
          name: "待整改"
        },
        {
          id: 1,
          name: "待验收"
        },
        {
          id: 2,
          name: "已验收"
        }
      ],
      //是否超时列表
      overtimeOptions: [
        {
          id: -1,
          name: "全部"
        },
        {
          id: 0,
          name: "未超时"
        },
        {
          id: 1,
          name: "已超时"
        }
      ],
      //预览图片列表
      srcList: [],
      //预览图片显示
      showViewer: false
    }
  },
  created () {
    this.onLoad();
    this.getSafeArea()
    this.getDangertype()
  },
  mounted() {
    let params = this.$route.query ;
    let toDoId = params.toDoId;
    if (toDoId !== undefined && toDoId !== null && toDoId !== ''){
      if (params.danger_status === 0){
        this.dialog.type = 'edit'
        this.dialog.title = '安全隐患-整改'
        this.dialog.data = {danger_id: toDoId};
        this.dialog.visible = true;
      }else if (params.danger_status === 1){
        this.dialog.type = 'check'
        this.dialog.title = '安全隐患-验收'
        this.dialog.data = {danger_id: toDoId};
        this.dialog.visible = true;
      }
    }
  },
  computed: {
    ...mapGetters(["userInfo"])
  },
  methods: {
    //获取安全区域
    getSafeArea () {
      getSafetyArea().then(res => {
        // console.log(res)
        this.operation0ptions = res.data.data
      })
    },
    //获取安全隐患等级
    getDangertype () {
      dangertype().then(res => {
        // console.log(res)
        this.hidden0ptions = res.data.data
      })
    },
    //载入数据
    onLoad () {
      if (this.query.danger_time && this.query.danger_time[0]) {
        this.query.danger_time_start = this.query.danger_time[0] + ' ' + '00:00:00'
        this.query.danger_time_end = this.query.danger_time[1] + ' ' + '23:59:59'
      } else {
        this.query.danger_time_start = ''
        this.query.danger_time_end = ''
      }
      //状态
      if (this.dangerStatus === '') {
        this.query.danger_status = -1
      } else {
        this.query.danger_status = this.dangerStatus
      }
      //超时状态
      if (this.timeoutStatus === '') {
        this.query.timeout_status = -1
      } else {
        this.query.timeout_status = this.timeoutStatus
      }
      this.query.id = '' ;
      this.table.loading = true;//加载状态
      dangerList(this.page, this.query).then(res => {
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
        this.page.order = "danger_time";
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
      this.dangerStatus = ''
      this.timeoutStatus = ''
      this.$refs["query"].resetFields()
      this.deptForm = { value: '', label: '' }
      // console
      this.handleQuery()
    },
    //新增
    handleAdd (type) {
      this.dialog.type = type
      this.dialog.title = '新增安全隐患'
      this.dialog.data = {};
      this.dialog.visible = true;
    },
    //详情
    handleView (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '安全隐患详情'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    //整改
    handleEdit (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '安全隐患-整改'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    handleCheck (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '安全隐患-验收'
      this.dialog.data = row;
      // console.log(row, "row")
      this.dialog.visible = true;
    },
    // 删除
    handleDelete (index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        safeDangerDelete({ danger_id: row.danger_id }).then(res => {
          if (res.data.code == 0) {
            this.onLoad();
            this.$message({ type: 'success', message: res.data.msg });
          }
        }, error => {
          window.console.log(error);
        });
      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    // 同步状态翻译
    stateFormat (row) {
      var stateCode = ''
      for (var i = 0; i < this.stateOptions.length; i++) {
        if (this.stateOptions[i].id == row.danger_status) {
          stateCode = this.stateOptions[i].name
          break
        }
      }
      return stateCode
    },
    // 同步超时时间翻译
    timeoutStateFormat (row) {
      var stateCode = ''
      for (var i = 0; i < this.overtimeOptions.length; i++) {
        if (this.overtimeOptions[i].id == row.timeout_status) {
          stateCode = this.overtimeOptions[i].name
          break
        }
      }
      return stateCode
    },
    //查看图片
    handleLook (row) {
      safeDangerInfo({ danger_id: row.danger_id }).then(res => {
        // console.log(res)
        this.srcList = []
        let data = res.data.data
        if (data.submit_image1) {
          this.srcList.push(this.GLOBAL.serveAddress + data.submit_image1)
        }
        if (data.submit_image2) {
          this.srcList.push(this.GLOBAL.serveAddress + data.submit_image2)
        }
        if (data.submit_image3) {
          this.srcList.push(this.GLOBAL.serveAddress + data.submit_image3)
        }
        if (data.submit_image4) {
          this.srcList.push(this.GLOBAL.serveAddress + data.submit_image4)
        }
        this.showViewer = true
      })
    },
    //提供给子类调用的方法
    submitSave () {
      this.dialog.visible = false;
      this.dialog.data = {};
      this.onLoad();
    },
    close () {
      this.dialog.visible = false;
    }
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

