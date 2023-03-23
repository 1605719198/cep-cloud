<template>
  <div style="padding: 0px 10px;">
    <div class="main">
      <div class="avue-crud el-card__body"
           style="width: 98%;border: 0;">
        <div class="avue-crud__search"
             style="border: 0">
          <el-row>
            <el-col :span="22">
              <!-- 表单筛选 -->
              <el-form :model="query"
                       ref="query"
                       :inline="true">
                <el-form-item prop="checkTimeArr">
                  <el-date-picker v-model="query.checkTimeArr"
                                  type="daterange"
                                  range-separator="至"
                                  start-placeholder="巡检开始日期"
                                  end-placeholder="巡检结束日期"
                                  value-format="yyyy-MM-dd">
                  </el-date-picker>
                </el-form-item>
                <el-form-item prop="safe_area_id">
                  <el-select class="customSelectStyle"
                             v-model="query.safe_area_id"
                             :popper-append-to-body="false"
                             style="width:100%;"
                             filterable
                             clearable
                             placeholder="作业区域">
                    <el-option v-for="item in operation0ptions"
                               :key="item.id"
                               :label="item.name"
                               :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item prop="check_type_id">
                  <el-select class="customSelectStyle"
                             v-model="query.check_type_id"
                             :popper-append-to-body="false"
                             style="width:100%;"
                             clearable
                             placeholder="安全类型">
                    <el-option v-for="item in securityType0ptions"
                               :key="item.id"
                               :label="item.name"
                               :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item prop="check_status">
                  <el-select class="customSelectStyle"
                             v-model="checkStatus"
                             :popper-append-to-body="false"
                             style="width:100%;"
                             clearable
                             placeholder="巡检状况">
                    <el-option v-for="item in conditionOptions"
                               :key="item.id"
                               :label="item.name"
                               :value="item.id">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item prop="check_person_name">
                  <el-input v-model="query.check_person_name"
                            placeholder="巡检人员"></el-input>
                </el-form-item>
                <!-- 操作按钮 -->
                <el-form-item>
                  <el-button v-hasPermi="['check_list']"
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
            <el-col :span="2">
              <div style="text-align: right;">
                <el-button v-hasPermi="['check_insert']"
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
            <el-table-column label="巡检时间"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="check_time" />
            <el-table-column label="巡检人"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="check_person_name" />
            <el-table-column label="作业区域"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="safe_area_name" />
            <el-table-column label="巡检类型"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="check_type_name" />
            <el-table-column label="地点状况"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="check_status"
                             :formatter="conditionFormat" />
            <el-table-column label="描述问题"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="check_content" />
            <el-table-column label="巡检图片"
                             minWidth="150"
                             align="left">
              <template slot-scope="scope">
                <el-button v-if="scope.row.check_image1"
                           size="mini"
                           plain
                           type="primary"
                           @click="handleLook(scope.row)">查看图片
                </el-button>
              </template>
            </el-table-column>
            <el-table-column label="处理时间"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="dispose_time" />
            <el-table-column label="处理人"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="disposer_name" />
            <el-table-column label="处理内容"
                             sortable
                             minWidth="150"
                             align="left"
                             prop="dispose_content" />
            <el-table-column label="处理图片"
                             minWidth="150"
                             align="left">
              <template slot-scope="scope">
                <el-button v-if="scope.row.disposer_image1"
                           size="mini"
                           plain
                           type="primary"
                           @click="handleLookDisposer(scope.row)">查看图片
                </el-button>
              </template>
            </el-table-column>
            <el-table-column label="状态"
                             minWidth="150"
                             align="left"
                             prop="check_disposed"
                             :formatter="stateFormat" />
            <el-table-column fixed="right"
                             label="操作"
                             width="260"
                             align="center">
              <template slot-scope="scope"
                        v-if="scope.row.check_disposed==0">
                <el-button v-hasPermi="['check_dispose']"
                           size="mini"
                           plain
                           icon="el-icon-edit"
                           type="primary"
                           @click="handleEdit('edit',scope.$index, scope.row)">处理
                </el-button>
                <el-button v-hasPermi="['check_delete']"
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
            <Dispose v-if="dialog.type === 'edit'"
                     :safeCheckId="safeCheckId"
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
import { getSafetyArea, checktype } from "@/api/security/si/WorkTicketApi"
import { safeCheckList, safeCheckDelete, safeCheckInfo } from "@/api/security/si/inspection/index";
import Add from "./add.vue"
import Dispose from "./dispose.vue"
import { mapGetters } from "vuex";
export default {
  components: {
    Add,
    Dispose,
    //注册element图片预览组件
    'el-image-viewer': () => import('element-ui/packages/image/src/image-viewer')
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
        order: "check_time",
        orderby: "desc",
      },
      //巡检状况（解决双向绑定回显问题，为空的时候不显示数据）
      checkStatus: '',
      query: {
        checkTimeArr: null,
        check_person_name: '',
        check_status: '',
        check_type_id: '',
        safe_area_id: ''
      },
      table: {
        border: true,
        loading: false,
      },
      tableData: [],
      //安全类型下拉
      securityType0ptions: [],
      //作业下拉
      operation0ptions: [],
      //巡检状况下拉
      conditionOptions: [
        {
          id: -1,
          name: "全部"
        },
        {
          id: 1,
          name: "正常"
        },
        {
          id: 2,
          name: "异常"
        }
      ],
      //预览图片列表
      srcList: [],
      //预览图片显示
      showViewer: false,
      safeCheckId: null //巡检id
    }
  },
  created () {
    this.onLoad();
    this.getSafeArea()
    this.getChecktype()
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    //获取安全区域
    getSafeArea () {
      getSafetyArea().then(res => {
        // console.log(res)
        this.operation0ptions = res.data.data
      })
    },
    //获取安全巡检类型
    getChecktype () {
      checktype().then(res => {
        // console.log(res)
        this.securityType0ptions = res.data.data
      })
    },
    //载入数据
    onLoad () {
      if (this.query.checkTimeArr && this.query.checkTimeArr[0]) {
        this.query.check_time_start = this.query.checkTimeArr[0] + ' ' + '00:00:00'
        this.query.check_time_end = this.query.checkTimeArr[1] + ' ' + '23:59:59'
      } else {
        this.query.check_time_start = ''
        this.query.check_time_end = ''
      }
      //巡检状况
      if (this.checkStatus == '' || this.checkStatus == -1) {
        this.query.check_status = -1
      } else {
        this.query.check_status = this.checkStatus
      }
      this.table.loading = true;//加载状态
      safeCheckList(this.page, this.query).then(res => {
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
        this.page.order = "check_time";
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
      this.checkStatus = ''
      this.$refs["query"].resetFields()
      this.deptForm = { value: '', label: '' }
      // console
      this.handleQuery()
    },
    //新增
    handleAdd (type) {
      this.dialog.type = type
      this.dialog.title = '新增安全巡检'
      this.dialog.data = {};
      this.dialog.visible = true;
    },
    //处理
    handleEdit (type, index, row) {
      this.dialog.type = type
      this.dialog.title = '异常处理'
      this.safeCheckId = row.safe_check_id
      this.dialog.data = {};
      this.dialog.visible = true;
    },
    // 删除
    handleDelete (index, row) {
      this.$confirm('是否确认删除数据项?', '警告', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        safeCheckDelete({ safe_check_id: row.safe_check_id }).then(res => {
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
    conditionFormat (row) {
      var stateCode = ''
      for (var i = 0; i < this.conditionOptions.length; i++) {
        if (this.conditionOptions[i].id == row.check_status) {
          stateCode = this.conditionOptions[i].name
          break
        }
      }
      return stateCode
    },
    // 同步状态翻译
    stateFormat (row) {
      var stateCode = ''
      if (row.check_disposed == '0') {
        stateCode = '待处理'
      } else if (row.check_disposed == '1') {
        stateCode = '已处理'
      }
      return stateCode
    },
    //查看图片
    handleLook (row) {
      safeCheckInfo({ safe_check_id: row.safe_check_id }).then(res => {
        // console.log(res)
        this.srcList = []
        let data = res.data.data
        if (data.check_image1) {
          this.srcList.push(this.GLOBAL.serveAddress + data.check_image1_path)
        }
        if (data.check_image2) {
          this.srcList.push(this.GLOBAL.serveAddress + data.check_image2_path)
        }
        if (data.check_image3) {
          this.srcList.push(this.GLOBAL.serveAddress + data.check_image3_path)
        }
        if (data.check_image4) {
          this.srcList.push(this.GLOBAL.serveAddress + data.check_image4_path)
        }
        this.showViewer = true
      })
    },
    //查看处理图片
    handleLookDisposer (row) {
      safeCheckInfo({ safe_check_id: row.safe_check_id }).then(res => {
        // console.log(res)
        this.srcList = []
        let data = res.data.data
        if (data.disposer_image1) {
          this.srcList.push(this.GLOBAL.serveAddress + data.disposer_image1_path)
        }
        if (data.disposer_image2) {
          this.srcList.push(this.GLOBAL.serveAddress + data.disposer_image2_path)
        }
        if (data.disposer_image3) {
          this.srcList.push(this.GLOBAL.serveAddress + data.disposer_image3_path)
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

