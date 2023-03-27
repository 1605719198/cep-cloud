<template>
  <div class="avue-crud el-card__body" style="background-color: #FFF;width:99%">
    <div class="avue-crud__search">
      <el-form :inline="true">
            <el-form-item label="计划日期" prop="planDate">
              <el-date-picker v-model="planDate" type="daterange"
                              start-placeholder="计划开始日期" range-separator="-" end-placeholder="计划结束日期"/>
            </el-form-item>
            <el-form-item label="状态" prop="status">
              <el-select v-model="query.status" placeholder="状态" :popper-append-to-body="false" clearable>
                <el-option v-for="item in selectJobTicketStatus" :key="item.value" :label="item.label"
                           :value="item.value"/>
              </el-select>
            </el-form-item>
            <el-form-item label="作业等级" prop="job_level">
              <el-select v-model="query.job_level" placeholder="请选择作业等级" :popper-append-to-body="false" clearable>
                <el-option label="一级" value="一级"/>
                <el-option label="二级" value="二级"/>
                <el-option label="三级" value="三级"/>
                <el-option label="特级" value="特级"/>
              </el-select>
            </el-form-item>
            <!--            <el-form-item label="" prop="safe_area_name">-->
            <!--              <el-input v-model="query.safe_area_name" placeholder="请输入区域名称" clearable/>-->
            <!--            </el-form-item>-->
            <el-form-item label="区域名称" prop="safe_area_id">
              <el-select v-model="query.safe_area_id" placeholder="请选择区域名称" :popper-append-to-body="false" filterable clearable>
                <el-option v-for="item in safeAreaOptions" :key="item.id" :label="item.name" :value="item.id"/>
              </el-select>
            </el-form-item>
            <el-form-item label="申请人姓名" prop="apply_person_name">
              <el-input v-model="query.apply_person_name" @focus="$refs.person.blur(),treeOpen=true" placeholder="请选择申请人姓名" ref="person"
                        clearable @clear="query.apply_person_name = '',query.apply_person_id=''"/>
            </el-form-item>

              <div class="el-form-item__content" style="margin-left: 0px;margin-bottom: 7px;">
                <el-button v-hasPermi="['high_list']" size="mini" icon="el-icon-search" type="primary"
                           @click="handleQuery">搜索
                </el-button>
                <el-button size="mini" icon="el-icon-refresh-left" type="default" @click="handleEmpty">重置</el-button>
              </div>

      </el-form>
      <div class="el-form-item__content">
        <el-button v-hasPermi="['high_list']" type="primary"
                   size="mini" icon="el-icon-plus"
                   plain
                   style="margin: 5px 0;" @click="handleOpenWindow('add')">新增
        </el-button>
      </div>
    </div>
    <div>
      <el-form>
        <el-table height="70vh" size="small" v-loading="table.loading" :data="tableData" stripe @sort-change="handleSort">
          <template v-for="(column, index) in columns">
            <el-table-column v-if="column.customStyle"
                             :prop="column.prop"
                             :label="column.label"
                             :type="column.type"
                             :key="index"
                             :width="column.width"
                             :min-width="column.minWidth"
                             :sortable="column.sortable">
              <template slot-scope="scope">
                <div v-html="column.customStyle(scope.row,scope.$index,scope)"></div>
              </template>
            </el-table-column>
            <el-table-column v-else
                             :prop="column.prop"
                             :label="column.label"
                             :type="column.type"
                             :key="index"
                             :width="column.width"
                             :min-width="column.minWidth"
                             :sortable="column.sortable"
                             :formatter="column.formatter"/>
          </template>
          <el-table-column label="操作" width="380px" fixed="right">
            <template slot-scope="scope">
              <el-button v-hasPermi="['high_list']" size="mini" plain icon="el-icon-edit" type="primary"
                         @click="handleOpenWindow('edit',scope.$index, scope.row)"> 查看/确定
              </el-button>
              <el-button v-hasPermi="['high_list']" size="mini" plain icon="el-icon-delete" type="text"
                         @click="handleDelete(scope.$index, scope.row)" :disabled="scope.row.status !== 0">删除
              </el-button>
              <el-button v-hasPermi="['high_list']" size="mini" plain icon="el-icon-document-copy" type="primary"
                         @click="handleOpenWindow('copy',scope.$index, scope.row)">复制
              </el-button>
              <el-button v-hasPermi="['high_list']" size="mini" plain icon="el-icon-printer" type="info"
                         v-if="scope.row.status === 3" @click="handlePrint(scope.row)">打印
              </el-button>
              <el-button v-hasPermi="['high_list']" size="mini" plain icon="el-icon-printer" type="info"
                         v-else disabled>打印
              </el-button>
              <!--              <el-button size="mini" plain icon="el-icon-printer" type="info"-->
              <!--                         @click="handlePrint(scope.$index, scope.row)">打印-->
              <!--              </el-button>-->
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px;padding: 25px 0px 20px 20px;right: 0" class="avue-crud__pagination">
          <el-pagination background
                         @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"
                         layout="total, sizes, prev, pager, next, jumper"
                         :current-page="page.current"
                         :page-sizes="[20, 50, 100, 200]"
                         :page-size="page.size"
                         :total="page.total">
          </el-pagination>
        </div>
      </el-form>
    </div>
    <div v-if="openDialog.open">
      <el-dialog :title="openDialog.title"
                 :visible.sync="openDialog.open"
                 :width="openDialog.width"
                 class="customDialogStyle securityOperationDialogStyle"
                 top="8vh"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false"
                 @close="onLoad()">
        <template v-if="openDialog.type === 'add' || openDialog.type === 'edit'">
          <heightInfo :type="openDialog.type"
                      :data="openDialog.data"
                      @submitSave="submitSave"
                      @close="openDialog.open=false,onLoad()"/>
        </template>
      </el-dialog>
    </div>

    <div v-if="treeOpen">
      <personnel-selection v-if="treeOpen" :userDialogVisible="treeOpen" @close-dialog="treeOpen = false"
                           :isMultiple="false" :height="500" @single-select="handleNodeClick"/>
    </div>
  </div>
</template>

<script>
  import {req} from "@/api/security/si/common";
  import {getSafetyArea, heightInfoApi} from "@/api/security/si/WorkTicketApi";
  import heightInfo from "./heightInfo";

  export default {
    components: {
      heightInfo,
    },
    name: "height",
    data() {
      return {
        openDialog: {open: false, type: '', title: '', width: '500px', data: {},},
        treeData: [], treeOpen: false, treeOpenType: '',
        page: {size: 20, current: 1, total: 1, order: "job_code", orderby: "desc",},
        query: {
          start_time: '',
          end_time: '',
          status: '',
          job_level: '',
          safe_area_id: '',
          apply_person_id: '',
          apply_person_name: '',
        },
        table: {border: true, loading: true,},
        planDate: [],
        safeAreaOptions: [],
        selectJobTicketStatus: [
          {value: -1, label: '全部'},
          {value: 0, label: '资料建立'},
          {value: 1, label: '审批中'},
          {value: 2, label: '作业中'},
          {value: 3, label: '作业完成'},
        ],
        columns: [
          {label: '序号', type: 'index', width: '80px'},
          {label: '作业证编号', prop: "job_code", sortable: true, minWidth: '120px'},
          {label: 'ERP作业证编号', prop: "job_code_erp", sortable: true, minWidth: '140px'},
          {label: '审批状态', prop: "status_text", sortable: true, minWidth: '100px'},
          {label: '作业证状态', prop: "is_timeout", sortable: true, minWidth: '110px'},
          {label: '作业证级别', prop: "job_level", sortable: true, minWidth: '110px'},
          {label: '作业开始时间', prop: "start_time", sortable: true, minWidth: '140px'},
          {label: '作业结束时间', prop: "end_time", sortable: true, minWidth: '140px'},
          {label: '作业区域', prop: "safe_area_name", sortable: true, minWidth: '100px'},
          {label: '作业地点', prop: "place", sortable: true, minWidth: '100px'},
          {label: '申请单位', prop: "apply_depart_name", sortable: true, minWidth: '100px'},
          {label: '申请人', prop: "apply_person_name", sortable: true, minWidth: '100px'},

        ],
        tableData: [],
      }
    },
    created() {
      this.onLoad();
      getSafetyArea().then(res => {
        this.safeAreaOptions = res.data.data;
      }, error => {
        window.console.log(error);
      });
    },
    mounted() {
      let toDoId = this.$route.query.toDoId;
      if (toDoId !== undefined && toDoId !== null && toDoId !== ''){
        heightInfoApi(toDoId).then(res => {
          if (res.data.code === "0"){
            this.handleOpenWindow('edit', 0,res.data.data);
          }
        }, err => {
          window.console.log(err);
        });
      }
    },
    methods: {
      //载入数据
      onLoad() {
        this.table.loading = true;//加载状态
        let data = {...this.page, ...this.query};
        data.status = data.status === '' ? -1 : data.status;
        req('get', 'high/list', data).then(res => {
          this.table.loading = false;
          this.tableData = res.data.data.records;//表格数据
          this.page.total = res.data.data.total;
        }, error => {
          this.table.loading = false;
          window.console.log(error);
        });
      },
      // 分页-每页多少条
      handleSizeChange(val) {
        this.page.size = val;
        this.onLoad();
      },
      // 分页-当前页
      handleCurrentChange(val) {
        this.page.current = val;
        this.onLoad();
      },
      // 排序
      handleSort(column) {
        if (column.order === null) {
          //默认
          this.page.order = "job_code";
          this.page.orderby = "desc";
        } else {
          //选中项
          this.page.order = column.prop;
          this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
        }
        this.onLoad();
      },
      //查询
      handleQuery() {
        this.page.current = 1;
        this.onLoad();
      },
      // 清空
      handleEmpty() {
        this.planDate = [];
        this.query = {
          start_time: '',
          end_time: '',
          status: '',
          job_level: '',
          safe_area_id: '',
          apply_person_id: '',
          apply_person_name: ''
        };
        this.onLoad();
      },
      // 打开窗口
      handleOpenWindow(type, index, row) {
        let title = '高处安全作业票';
        this.openDialog.open = true;
        this.openDialog.type = type;
        this.openDialog.width = '1000px';
        this.openDialog.data = {...row};
        if (type === 'add') {
          this.openDialog.title = title + '申请';
          this.openDialog.data = {};
        } else if (type === 'edit') {
          this.openDialog.title = '确认' + title;
        } else if (type === 'copy') {
          this.openDialog.title = '复制' + title;
          this.openDialog.type = 'add';
          this.openDialog.data.id = '';
          this.openDialog.data.copyId = row.id;
        } else {
          this.openDialog.open = false;
          this.openDialog.data = {};
        }
      },
      // 删除
      handleDelete(index, row) {
        this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
          confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        }).then(() => {
          req('post', 'high/delete', {id: row.id}).then((res) => {
            if (res.data.code === "0") {
              this.$message({
                type: "success", message: "删除成功！", duration: 1000,
                onClose: () => {
                  this.onLoad();
                }
              });
            }
          }, error => {
            window.console.log(error);
          });
        }).catch(() => {
          this.$message({type: 'info', message: '已取消删除'});
        });
      },
      //打印
      handlePrint(row) {
        let link = document.createElement('a');
        link.target = '_blank';
        // link.href = this.GLOBAL.pdfFileUrl + '/safe/job/temporaryelectricity/file/getPdf?id=' + row.id;
        link.href = this.GLOBAL.pdfFileUrl + '/ureport/preview?_u=mysql:高处作业许可证.ureport.xml&id=' + row.id;
        link.click();
      },
      //提供给子类调用的方法
      submitSave() {
        this.openDialog.open = false;
        this.openDialog.data = {};
        this.onLoad();
      },
      handleNodeClick(data) {
        this.query.apply_person_id = data.id;
        this.query.apply_person_name = data.full_name;
        this.treeOpen = false;
        this.treeOpenType = '';
      },
    },
    // 侦听器
    watch: {
      planDate(newValue) {
        if (newValue && newValue.length > 0) {
          this.query.start_time = this.$moment(newValue[0]).format('YYYY-MM-DD');
          this.query.end_time = this.$moment(newValue[1]).format('YYYY-MM-DD');
        } else {
          this.query.start_time = "";
          this.query.end_time = "";
        }
      },
    }
  }
</script>

<style scoped lang="scss">
  .el-dialog__wrapper {
    ::v-deep .el-dialog__footer {
      padding: 10px 40px 40px;
      text-align: right;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
    }
  }

  .el-form--label-top .el-form-item__label {
    padding: 0px;
  }
  ::v-deep .el-form-item__content {
    line-height: unset !important;
  }
</style>

<style>
  .securityOperationDialogStyle .el-dialog__body {
    padding: 0 !important;
  }
</style>
