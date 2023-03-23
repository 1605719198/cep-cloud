<template>
  <div class="avue-crud el-card__body"
       style="background-color: #FFF;width:99%">
    <div class="avue-crud__search">
      <el-form>
        <el-row :gutter="20">
          <el-col :span="5">
            <div>
              <div>
                <el-date-picker v-model="query.dateRange"
                                type="daterange"
                                range-separator="至"
                                start-placeholder="开始时间"
                                end-placeholder="结束时间"
                                value-format="yyyy-MM-dd" />
              </div>
            </div>
          </el-col>
          <el-col :span="3">
            <div>
              <div>
                <el-select v-model="query.status"
                           placeholder="选择作业票状态"
                           clearable>
                  <el-option v-for="item in workTicketStatus"
                             :key="item.value"
                             :label="item.label"
                             :value="item.value"></el-option>
                </el-select>
              </div>
            </div>
          </el-col>

          <el-col :span="3">
            <div class="el-form-item el-form-item--small">
              <div class="el-form-item__content">
                <el-input v-model="query.apply_person_name"
                          ref="person"
                          placeholder="申请人姓名"
                          @focus="$refs.person.blur(),personSelectDialog.selectDialogVisibility=true"
                          clearable @clear="query.apply_person_name = '',query.apply_person_id=''">
                </el-input>
              </div>
            </div>
          </el-col>

          <el-col :span="3">
            <div class="el-form-item el-form-item--small">
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
          </el-col>

          <el-col :span="8">
            <div class="el-form-item__content"
                 style="margin-left: 0px;">
              <el-button v-hasPermi="['soil_list']"
                         size="mini"
                         icon="el-icon-search"
                         type="primary"
                         @click="handleQuery">搜索</el-button>
              <el-button size="mini"
                         icon="el-icon-refresh-left"
                         type="default"
                         @click="handleResetting">重置</el-button>
            </div>
          </el-col>
          <el-col :span="2">
            <div class="el-form-item__content"
                 style="float: right">
              <el-button v-hasPermi="['soil_list']"
                         type="primary"
                         size="mini"
                         icon="el-icon-plus"
                           style="margin: 5px 0;"
                         @click="handlerAdd('add')">新增
              </el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
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
              <el-button v-hasPermi="['soil_list']"
                         size="mini"
                         plain
                         type="primary"
                         @click="handleDetail(scope.row)"
                         icon="el-icon-edit">查看/确定
              </el-button>

              <el-button v-hasPermi="['soil_list']"
                         size="mini"
                         plain
                         type="text"
                         @click="deleteItem(scope.$index, tableData)"  :disabled="scope.row.status !== 0"
                         icon="el-icon-delete">删除
              </el-button>
              <el-button
                v-hasPermi="['soil_list']"
                size="mini" plain type="info"
                @click="handleCopy(scope.$index, scope.row)"
                icon="el-icon-document-copy">复制
              </el-button>
              <el-button v-hasPermi="['soil_list']" size="mini" plain icon="el-icon-printer" type="info"
                         v-if="scope.row.status === 3" @click="handlePrint(scope.row)">打印
              </el-button>
              <el-button v-hasPermi="['soil_list']" size="mini" plain icon="el-icon-printer" type="info"
                         v-else disabled>打印
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px;right: 0"
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
                   :title="dialog.isAdd ? '申请动土作业票' : '详情/审批动土作业票'"
                   :visible.sync="dialog.isVisibility"
                   width="1040px"
                   append-to-body
                   :destroy-on-close="true"
                   :close-on-click-modal="false">
          <EditorContainer :dataBundle="dialog.data"
                           :isShowConfirm="dialog.isShowConfirm"
                           @close="dialog.isVisibility = false" />
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
import { soilList, soilDelete } from "@/api/security/si/GroundBreakingApi";
import {getSafetyArea} from "@/api/security/si/WorkTicketApi";
export default {
  components: {
    EditorContainer
  },
  name: 'groundBreaking',
  data () {
    return {
      dialog: {
        data: {
          from: 'groundBreaking',
          id: '',//作业票id
          type: 0,//类型：0位新增，1为详情\审批
          jobCode: '',
          approvalStatus: 0
        },
        isShowConfirm:true,
        isAdd: false,
        isVisibility: false
      },
      page: {
        pageSize: 20,
        currentPage: 1,
        total: 1,
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
        { label: '作业证编号', prop: "job_code", minWidth: '150px' },
        {
          label: '审批状态', prop: "status_text", sortable: true, minWidth: '150px', formatter (row) {
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
        { label: '作业证状态', prop: "is_timeout", sortable: true, minWidth: '150px' },
        //{ label: '作业类型', prop: "作业证级别", sortable: true, minWidth: '110px' },
        { label: '作业开始时间', prop: "start_time", sortable: true, minWidth: '150px' },
        { label: '作业结束时间', prop: "end_time", sortable: true, minWidth: '150px' },
        { label: '作业区域', prop: "safe_area_name", sortable: true, minWidth: '150px' },
        { label: '作业地点', prop: "place", sortable: true, minWidth: '150px' },
        { label: '申请单位', prop: "apply_depart_name", sortable: true, minWidth: '150px' },
        { label: '申请人', prop: "apply_person_name", sortable: true, minWidth: '150px' },
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
      };
      this.table.loading = true;
      soilList(params).then(response => {
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
        soilDelete({ id: tableData[index].id }).then(response => {
          if (response.data.msg === '动土安全作业证删除成功') {
            tableData.splice(index, 1);
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          }
        }, error => {
          this.$message.error("删除失败");
          // console.log(error);
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    //复制
    handleCopy(index, row){
      this.dialog.data.id = row.id;
      this.dialog.data.type = 2;
      this.dialog.data.jobCode = row.job_code;
      this.dialog.isAdd = false;
      this.dialog.isShowConfirm = true ;
      this.dialog.isVisibility = true;
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
      this.query.apply_person_id = data.id ;
    },

    //选着安全区域的监听
    selectSafeArea (data) {
      let { value, label } = data;
      this.query.safe_area_name = label;
      this.query.safe_area_id = value ;
    },
    //打印
    handlePrint(row) {
      let link = document.createElement('a');
      link.target = '_blank';
      // link.href = this.GLOBAL.pdfFileUrl + '/safe/job/soil/file/getPdf?id=' + row.id;
      link.href = this.GLOBAL.pdfFileUrl + '/ureport/preview?_u=mysql:动土安全作业许可证.ureport.xml&id=' + row.id;
      link.click();
    },
  },
  created () {
    this.$EventBus.$on('event-refresh-groundBreaking-work-ticket-list-data', () => {
      this.handleQuery();
    })
  },

  beforeDestroy() {
    this.$EventBus.$off("event-refresh-groundBreaking-work-ticket-list-data");
  },

  mounted () {
    //获取安全区域
    getSafetyArea().then(response => {
      this.workAreaOptions = response.data.data;
    });
    this.handleQuery();
    let toDoId = this.$route.query.toDoId ;
    if (toDoId !== undefined && toDoId !== null && toDoId !== ''){
      this.dialog.data.id = toDoId;
      this.dialog.data.type = 1;
      this.dialog.data.jobCode = '';
      this.dialog.isAdd = false;
      this.dialog.isVisibility = true;
    }
  }
}
</script>
<style scoped>
.plan_header {
  background-color: #f9f9f9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #e9e9e9 solid;
}

.plan_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold;
}

.plan_main {
  height: 80vh;
  background-color: #fff;
}
::v-deep .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: -17px !important;
}
</style>
<style>
.securityOperationDialogStyle .el-dialog__body {
  padding: 0 !important;
}
</style>
