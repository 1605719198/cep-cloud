<!--
    抽堵盲板作业票
-->
<template>
  <div class="avue-crud el-card__body"
       style="background-color: #FFF ;width:99%">
    <div class="avue-crud__search">
      <el-form :inline="true">
            <div class="el-form-item el-form-item--small">
              <label class="el-form-item__label">作业时间</label>
              <div class="el-form-item__content">
                <el-date-picker v-model="query.dateRange"
                                type="daterange"
                                range-separator="至"
                                start-placeholder="开始时间"
                                end-placeholder="结束时间"
                                value-format="yyyy-MM-dd" />
              </div>
            </div>

          <div class="el-form-item el-form-item--small">
            <label class="el-form-item__label">状态</label>
            <div class="el-form-item__content">
                <el-select class="customSelectStyle"
                           :popper-append-to-body="false"
                           v-model="query.status"
                           placeholder="选择作业票状态"
                           clearable>
                  <el-option v-for="item in workTicketStatus"
                             :key="item.value"
                             :label="item.label"
                             :value="item.value"></el-option>
                </el-select>
              </div>
            </div>

            <div class="el-form-item el-form-item--small">
              <label class="el-form-item__label">申请人姓名</label>
              <div class="el-form-item__content">
                <el-input v-model="query.apply_person_name"
                          ref="person"
                          placeholder="申请人姓名"
                          @focus="$refs.person.blur(),personSelectDialog.selectDialogVisibility=true"
                          clearable @clear="query.apply_person_name = '',query.apply_person_id=''">
                </el-input>
              </div>
            </div>

            <div class="el-form-item el-form-item--small">
              <label class="el-form-item__label">作业区域</label>
              <div class="el-form-item__content">
                <el-select class="customSelectStyle"
                           filterable
                           :popper-append-to-body="false"
                           v-model="query.safe_area_name"
                           placeholder="作业区域"
                           clearable
                           style="width: 100%"
                           @change="selectSafeArea">
                  <el-option v-for="item in workAreaOptions"
                             :key="item.id"
                             :label="item.name"
                             :value="{value:item.id , label:item.name}" />
                </el-select>
              </div>
            </div>

            <div class="el-form-item__content"
                 style="margin-left: 0px;">
              <el-button v-hasPermi="['blindplateplugging_list']"
                         size="mini"
                         icon="el-icon-search"
                         type="primary"
                         @click="handleQuery">搜索</el-button>
              <el-button size="mini"
                         icon="el-icon-refresh-left"
                         type="default"
                         @click="handleResetting">重置</el-button>
            </div>
      </el-form>
      <div class="el-form-item__content">
        <el-button v-hasPermi="['blindplateplugging_list']"
                   type="primary"
                   size="mini"
                   icon="el-icon-plus"
                   plain
                   @click="handlerAdd('add')">新增
        </el-button>
      </div>
    </div>
    <div>
      <el-form>
        <el-table height="70vh"
                  size="small"
                  :data="tableData"
                  stripe
                  v-loading="table.loading" @sort-change="handleSort">
          <template v-for="column in columns">
            <el-table-column :prop="column.prop"
                             :label="column.label"
                             :sortable="column.sortable"
                             :width="column.width"
                             :min-width="column.minWidth"
                             :formatter="column.formatter"
                             :type="column.type"
                             :key="column.prop" />
          </template>
          <el-table-column fixed="right"
                           label="操作"
                           width="380px">
            <template slot-scope="scope">
              <el-button v-hasPermi="['blindplateplugging_list']"
                         size="mini"
                         plain
                         type="primary"
                         @click="handleDetail(scope.row)"
                         icon="el-icon-edit">查看/确定
              </el-button>

              <el-button v-hasPermi="['blindplateplugging_list']"
                         size="mini"
                         plain
                         type="text"
                         @click="deleteItem(scope.$index, tableData)"  :disabled="scope.row.status !== 0"
                         icon="el-icon-delete">删除
              </el-button>
              <el-button
                v-hasPermi="['blindplateplugging_list']"
                size="mini" plain type="info"
                @click="handleCopy(scope.$index, scope.row)"
                icon="el-icon-document-copy">复制
              </el-button>
              <el-button v-hasPermi="['blindplateplugging_list']" size="mini" plain icon="el-icon-success" type="info"
                         v-if="scope.row.status === 3" @click="handlePrint(scope.row)">打印
              </el-button>
              <el-button v-hasPermi="['blindplateplugging_list']" size="mini" plain icon="el-icon-success" type="info"
                         v-else disabled>打印
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px;padding: 25px 0px 20px 20px;float: right"
             class="avue-crud__pagination">
          <el-pagination background
                         @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"
                         :current-page="page.currentPage"
                         :page-sizes="[20, 50, 100, 200]"
                         :page-size="page.pageSize"
                         layout="total, sizes, prev, pager, next, jumper"
                         :total="page.total">
          </el-pagination>
        </div>
      </el-form>
      <div v-if="dialog.isVisibility">
        <el-dialog class="customDialogStyle securityOperationDialogStyle"
                   :title="dialog.isAdd?'申请抽堵盲板作业票' : '详情/审批抽堵盲板作业票'"
                   :visible.sync="dialog.isVisibility"
                   width="1060px"
                   append-to-body
                   :destroy-on-close="true"
                   :close-on-click-modal="false">
          <EditorContainer :dataBundle="dialog.data"
                           :isShowConfirm="dialog.isShowConfirm"
                           @close="dialog.isVisibility=false" />
        </el-dialog>
      </div>
      <personnel-selection v-if="personSelectDialog.selectDialogVisibility"
                           :userDialogVisible="personSelectDialog.selectDialogVisibility"
                           @close-dialog="personSelectDialog.selectDialogVisibility = false"
                           :isMultiple="false"
                           :height="500"
                           @single-select="handleSingleSelect"></personnel-selection>
    </div>
  </div>
</template>

<script>
import EditorContainer from "@/views/security/si/operation/EditorContainer";
import {blindplatepluggingList, deleteBlindingPlatePlugging, getSafetyArea} from '@/api/security/si/WorkTicketApi'
export default {
  components: {
    EditorContainer
  },
  name: "blindPlate",
  data () {
    return {
      dialog: {
        data: {
          from: 'blindPlate',
          id: '',
          type: 0,
          jobCode: '',
        },
        isShowConfirm:true,
        isAdd: false,
        isVisibility: false,
        ticketTypeVisibility: false
      },
      page: {
        pageSize: 20,
        currentPage: 1,
        total: 0,
        order: "job_code",
        orderby: "desc"
      },
      workTicketStatus: [
        { value: "-1", label: "全部" },
        { value: "0", label: "资料建立" },
        { value: "1", label: "审批中" },
        { value: "2", label: "作业中" },
        { value: "3", label: "作业完成" }
      ],
      query: {
        dateRange: '',
        status: '-1',
        apply_person_name:'',
        safe_area_name:'',
        apply_person_id:'',
        safe_area_id:'',
      },
      table: {
        border: true,
        loading: false
      },
      columns: [
        { label: '序号', type: 'index', width: '80px' },
        { label: '作业证编号', prop: "job_code", minWidth: '130px' },
        {
          label: '审批状态', prop: "status_text", sortable: true, minWidth: '100px', formatter (row) {
            switch (row.status) {
              case 0:
                return '资料建立'
              case 1:
                return '审批中'
              case 2:
                return '作业中'
              case 3:
                return '作业完成'
            }
          }
        },
        { label: '作业证状态', prop: "is_timeout", sortable: true, minWidth: '110px' },
        {
          label: '作业类型', prop: "pumping_blocking", sortable: true, minWidth: '110px', formatter (row) {
            return row.pumping_blocking === 0 ? '抽作业' : '堵作业';
          }
        },
        { label: '开始时间', prop: "start_time", sortable: true, minWidth: '150px' },
        { label: '结束时间', prop: "end_time", sortable: true, minWidth: '150px' },
        { label: '作业区域', prop: "safe_area_name", sortable: true, minWidth: '150px' },
        { label: '作业地点', prop: "place", sortable: true, minWidth: '150px' },
        { label: '申请单位', prop: "apply_depart_name", sortable: true, minWidth: '100px' },
        { label: '申请人', prop: "apply_person_name", sortable: true, minWidth: '100px' },
      ],
      tableData: [],
      personSelectDialog:{
        selectDialogVisibility: false,
        selectPersonType: ''
      },
      workAreaOptions: [],
      workLevelOptions:[
        {value:'一级', label :"一级"},
        {value:'二级', label :"二级"},
        {value:'三级', label :"三级"},
      ]
    }
  },
  mounted () {
    let toDoId = this.$route.query.toDoId ;
    if (toDoId !== undefined && toDoId !== null && toDoId !== ''){
      this.dialog.data.id = toDoId;
      this.dialog.data.type = 1;
      this.dialog.data.jobCode = '';
      this.dialog.isAdd = false;
      this.dialog.isVisibility = true;
    }

    //获取安全区域
    getSafetyArea().then(response => {
      this.workAreaOptions = response.data.data;
    });
    this.handleQuery()
    this.$EventBus.$on('event-refresh-blinding-work-ticket-list-data', () => {
      this.handleQuery();
    })
  },
  beforeDestroy () {
    this.$EventBus.$off('event-refresh-blinding-work-ticket-list-data');
  },
  methods: {
    //查询
    handleQuery () {
      let params = {
        current: this.page.currentPage,
        start_time: this.query.dateRange && this.query.dateRange[0] !== undefined ? this.query.dateRange[0] : "",
        end_time: this.query.dateRange && this.query.dateRange[1] !== undefined ? this.query.dateRange[1] : "",
        apply_person_id:this.query.apply_person_id === undefined ? '' : this.query.apply_person_id,
        safe_area_id:this.query.safe_area_id === undefined ? '' : this.query.safe_area_id,
        status: this.query.status === null ||this.query.status === '' ? -1 : this.query.status ,
        order: this.page.order,
        orderby:this.page.orderby,
        size: this.page.pageSize,
      }
      this.table.loading = true;
      blindplatepluggingList(params).then(response => {
        this.table.loading = false;
        this.tableData = response.data.data.records;
        this.page.total = response.data.data.total;
      }, error => {
        this.table.loading = false;
        // console.log(error);
      });
    },
    // 清空
    handleResetting () {
      this.query = {
        dateRange: '',
        status: '-1',
        apply_person_name: '',
        safe_area_name: '',
        apply_person_id: '',
        safe_area_id: '',
      }
      this.handleQuery();
    },
    //详情
    handleDetail (row) {
      this.dialog.data.id = row.id;
      this.dialog.data.type = 1;
      this.dialog.data.jobCode = row.job_code;
      this.dialog.isAdd = false;
      this.dialog.isShowConfirm = row.status === 0;
      this.dialog.isVisibility = true;
    },
    //添加
    handlerAdd () {
      this.dialog.data.type = 0;
      this.dialog.isAdd = true;
      this.dialog.isShowConfirm = true ;
      this.dialog.isVisibility = true;
    },

    //删除
    deleteItem (index, tableData) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteBlindingPlatePlugging({ id: tableData[index].id }).then(response => {
          if (response.data.msg === '盲板抽堵安全作业证删除成功') {
            tableData.splice(index, 1);
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          }
        }, error => {
          this.$message.error("删除失败");
          // console.log(error);
        });
      }).catch((data) => {
        // console.log(data);
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    // 分页-每页多少条
    handleSizeChange (val) {
      this.page.pageSize = val;
      this.handleQuery();
    },
    // 分页-当前页
    handleCurrentChange (val) {
      this.page.currentPage = val;
      this.handleQuery();
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
      this.handleQuery();
    },

    selectPersonInputClick(type){
      this.personSelectDialog.selectPersonType = type ;
      this.personSelectDialog.selectDialogVisibility = true;
    },
    //选人的时候返回数据，分类进行赋值
    handleSingleSelect (data) {
      this.query.apply_person_name = data.full_name;
      this.query.apply_person_id = data.id;
    },

    //选着安全区域的监听
    selectSafeArea (data) {
      let { value, label } = data;
      this.query.safe_area_name = label;
      this.query.safe_area_id = value ;
    },

    handleCopy(index, row){
      this.dialog.data.id = row.id;
      this.dialog.data.type = 2;
      this.dialog.data.jobCode = row.job_code;
      this.dialog.isAdd = false;
      this.dialog.isShowConfirm = true ;
      this.dialog.isVisibility = true;
    },
    //打印
    handlePrint(row) {
      let link = document.createElement('a');
      link.target = '_blank';
      // link.href = this.GLOBAL.pdfFileUrl + '/safe/job/blindplateplugging/file/getPdf?id=' + row.id;
      link.href = this.GLOBAL.pdfFileUrl + '/ureport/preview?_u=mysql:抽堵盲板作业许可证.ureport.xml.ureport.xml&id=' + row.id;
      link.click();
    },
  }
}
</script>

<style scoped lang="scss">
.el-dialog__wrapper {
  ::v-deep.el-dialog__footer {
    padding: 10px 40px 40px;
    text-align: right;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
  }
}
.el-form--label-top .el-form-item__label {
  padding: 0px;
}


::v-deep .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}

// ::v-deep .el-form-item__content {
//   line-height: unset !important;
// }

::v-deep .customDialogStyle .el-dialog__body {
  padding: 0 !important;
}
</style>
<style>
.securityOperationDialogStyle .el-dialog__body {
  padding: 0 !important;
}
</style>
