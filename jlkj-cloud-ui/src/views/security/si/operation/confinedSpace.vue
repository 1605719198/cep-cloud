<template>
  <div class="avue-crud el-card__body"
       style="background-color: #FFFFFF ;width:99%">
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
                             :value="item.value">
                  </el-option>
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
                          clearable
                          @clear="query.apply_person_name = '',query.apply_person_id=''">
                </el-input>
              </div>
            </div>

        <!--          <el-col :span="3">-->
          <!--            <div class="el-form-item el-form-item&#45;&#45;small">-->
          <!--              <div class="el-form-item__content">-->
          <!--                <el-select class="customSelectStyle"-->
          <!--                           filterable-->
          <!--                           :popper-append-to-body="false"-->
          <!--                           v-model="query.safe_area_name"-->
          <!--                           placeholder="作业区域"-->
          <!--                           clearable-->
          <!--                           style="width: 100%"-->
          <!--                           @change="selectSafeArea">-->
          <!--                  <el-option v-for="item in workAreaOptions"-->
          <!--                             :key="item.id"-->
          <!--                             :label="item.name"-->
          <!--                             :value="{value:item.id , label:item.name}" />-->
          <!--                </el-select>-->
          <!--              </div>-->
          <!--            </div>-->
          <!--          </el-col>-->

          <!--          <el-col :span="3">-->
          <!--            <div class="el-form-item el-form-item&#45;&#45;small">-->
          <!--              <div class="el-form-item__content">-->
          <!--                <el-select class="customSelectStyle"-->
          <!--                           :popper-append-to-body="false"-->
          <!--                           v-model="query.job_level"-->
          <!--                           placeholder="作业级别"-->
          <!--                           clearable-->
          <!--                           style="width: 100%">-->
          <!--                  <el-option v-for="item in workLevelOptions"-->
          <!--                             :key="item.value"-->
          <!--                             :label="item.label"-->
          <!--                             :value="item.value" />-->
          <!--                </el-select>-->
          <!--              </div>-->
          <!--            </div>-->
          <!--          </el-col>-->

            <div class="el-form-item__content"
                 style="margin-left: 0px;">
              <el-button v-hasPermi="['space_list']"
                         size="mini"
                         icon="el-icon-search"
                         type="primary"
                         @click="handleQuery">搜索</el-button>
              <el-button size="mini"
                         icon="el-icon-refresh-left"
                         type="default"
                         @click="handleResetting">重置
              </el-button>
            </div>
      </el-form>
      <div class="el-form-item__content">
        <el-button v-hasPermi="['space_list']"
                   type="primary"
                   size="mini"
                   plain
                   icon="el-icon-plus"
                   style="margin: 5px 0;"
                   @click="handleAdd()">新增
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
          <el-table-column label="序号"
                           type="index"
                           width="80px" />
          <el-table-column label="作业证编号"
                           prop="job_code"
                           minWidth="130px"></el-table-column>
          <el-table-column label="ERP作业证编号"
                           prop="job_code_erp"
                           minWidth="140px"></el-table-column>
          <el-table-column label='审批状态'
                           prop="status_text"
                           sortable="true"
                           minWidth="100px"></el-table-column>
          <el-table-column label='作业证状态'
                           prop="is_timeout"
                           sortable="true"
                           minWidth='110px'></el-table-column>
          <el-table-column label='作业开始时间'
                           prop="start_time"
                           sortable="true"
                           minWidth='150px'></el-table-column>
          <el-table-column label='作业结束时间'
                           prop="end_time"
                           sortable="true"
                           minWidth='150px'></el-table-column>
          <el-table-column label='设备部门'
                           prop="equip_dep_name"
                           sortable="true"
                           minWidth='180px'></el-table-column>
          <el-table-column label='作业内容描述'
                           prop="job_content"
                           sortable="true"
                           minWidth='120px'
                           show-overflow-tooltip>
            <template slot-scope="scope">
              <span>{{ scope.row.job_content }}</span>
            </template>
          </el-table-column>
          <el-table-column label='申请单位'
                           prop="apply_depart_name"
                           sortable="true"
                           minWidth='100px'></el-table-column>
          <el-table-column label="申请人"
                           prop="apply_person_name"
                           sortable="true"
                           width="80px">
          </el-table-column>
          <el-table-column label="操作"
                           width="380px"
                           fixed="right">
            <template slot-scope="scope">
              <el-button v-hasPermi="['space_list']"
                         size="mini"
                         plain
                         icon="el-icon-edit"
                         type="primary"
                         @click="handleDetail(scope.$index, scope.row)">查看/确定
              </el-button>
              <el-button v-hasPermi="['space_list']"
                         size="mini"
                         plain
                         icon="el-icon-delete"
                         type="text"
                         :disabled="scope.row.status !== 0"
                         @click="handleDelete(scope.$index, tableData)">删除
              </el-button>
              <el-button v-hasPermi="['space_list']"
                         size="mini"
                         plain
                         type="info"
                         @click="handleCopy(scope.$index, scope.row)"
                         icon="el-icon-document-copy">复制
              </el-button>
              <el-button v-hasPermi="['space_list']" size="mini" plain icon="el-icon-printer" type="info"
                         v-if="scope.row.status === 3" @click="handlePrint(scope.row)">打印
              </el-button>
              <el-button v-hasPermi="['space_list']" size="mini" plain icon="el-icon-printer" type="info"
                         v-else disabled>打印
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px; float: right"
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
      <div v-if="detailBox">
        <el-dialog class="customDialogStyle securityOperationDialogStyle"
                   top="8vh"
                   :title="openDialog.isAdd ? '申请有限空间作业票' : '详情/审批有限空间作业票'"
                   :visible.sync="detailBox"
                   width="1050px"
                   append-to-body
                   :destroy-on-close="true"
                   :close-on-click-modal="false">
          <EditorContainer :dataEdit="dataEdit"
                           :dataBundle="openDialog.data"
                           :isShowConfirm="openDialog.isShowConfirm"
                           @close="detailBox = false"
                           @submitSave="submitSave" />
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
import moment from "moment";
import { delProductionTargetItem } from "@/api/production/parameter";
import { spaceDelete, spaceList } from "@/api/security/si/ConfinedSpaceApi";
import { getSafetyArea } from "@/api/security/si/WorkTicketApi";
export default {
  components: {
    EditorContainer
  },
  name: "confinedSpace",
  data () {
    return {
      openDialog: {
        open: false,
        isAdd: true,
        width: "500px",
        isShowConfirm:true,
        data: {
          from: "confinedSpace",
          id: '', //作业票的ID
          type: 0,//如果是0表示新增，1表示确认\详情
          jobCode: '',
          approvalStatus: 0
        },
      },
      detailBox: false,
      dataEdit: {},
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

        apply_person_name: '',
        safe_area_name: '',
        apply_person_id: '',
        safe_area_id: '',
        job_level: ""
      },
      table: {
        border: true,
        loading: false
      },
      tableData: [],
      personSelectDialog: {
        selectDialogVisibility: false,
        selectPersonType: ''
      },
      workAreaOptions: [],
      workLevelOptions: [
        { value: '一级', label: "一级" },
        { value: '二级', label: "二级" },
        { value: '三级', label: "三级" },
      ]
    }
  },
  mounted () {
    //获取安全区域
    getSafetyArea().then(response => {
      this.workAreaOptions = response.data.data;
    });
    this.handleQuery()
    this.$EventBus.$on('event-refresh-space-work-ticket-list-data', () => {
      this.handleQuery();
    })
    let toDoId = this.$route.query.toDoId;
    if (toDoId !== undefined && toDoId !== null && toDoId !== '') {
      this.openDialog.data.id = toDoId;
      this.openDialog.data.type = 1;
      this.openDialog.data.jobCode = '';
      this.openDialog.data.approvalStatus = '';
      this.openDialog.isAdd = false;
      this.detailBox = true;
    }
  },
  beforeDestroy () {
    this.$EventBus.$off('event-refresh-space-work-ticket-list-data');
  },
  methods: {
    //查询
    handleQuery () {
      let params = {
        current: this.page.currentPage,
        start_time: this.query.dateRange && this.query.dateRange[0] !== undefined ? this.query.dateRange[0] : "",
        end_time: this.query.dateRange && this.query.dateRange[1] !== undefined ? this.query.dateRange[1] : "",
        apply_person_id: this.query.apply_person_id === undefined ? '' : this.query.apply_person_id,
        safe_area_id: "",
        job_level: "",
        status: this.query.status === null || this.query.status === '' ? -1 : this.query.status,
        order: this.page.order,
        orderby:this.page.orderby,
        size: this.page.pageSize,
      }
      this.table.loading = true;
      spaceList(params).then(response => {
        this.table.loading = false;
        this.tableData = response.data.data.records;
        this.page.total = response.data.data.total;
      }, error => {
        this.table.loading = false;
        console.log(error);
      });
    },
    // 清空
    handleResetting () {
      this.query = {
        dateRange: '',
        status: '-1',

        apply_person_name: '',
        apply_person_id: '',
        safe_area_name: '',
        safe_area_id: '',
        job_level: ''
      }
      this.handleQuery();
    },

    //新增作业票
    handleAdd () {
      this.openDialog.data.type = 0;
      this.openDialog.isAdd = true;
      this.openDialog.isShowConfirm = true ;
      this.detailBox = true;
    },

    //详情
    handleDetail (index, row) {
      this.openDialog.data.id = row.id;
      this.openDialog.data.type = 1;
      this.openDialog.data.jobCode = row.job_code;
      this.openDialog.data.approvalStatus = row.status;
      this.openDialog.isAdd = false;
      this.openDialog.isShowConfirm = row.status === 0;
      this.detailBox = true;
    },

    handleDelete (index, tableData) {
      this.$confirm('是否确认删除数据？', '操作确认', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        spaceDelete({ id: tableData[index].id }).then(res => {
          if (res.data.msg === '受限空间作业证删除成功') {
            tableData.splice(index, 1);
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          }
        }, error => {
          window.console.log(error);
        });
      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },

    //复制
    handleCopy (index, row) {
      this.openDialog.data.id = row.id;
      this.openDialog.data.type = 2;
      this.openDialog.data.jobCode = row.job_code;
      this.openDialog.isAdd = false;
      this.openDialog.isShowConfirm = true ;
      this.detailBox = true;
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
    //打印
    handlePrint(row) {
      let link = document.createElement('a');
      link.target = '_blank';
      // link.href = this.GLOBAL.pdfFileUrl + '/safe/job/temporaryelectricity/file/getPdf?id=' + row.id;
      // console.log(this.GLOBAL.pdfFileUrl + '/ureport/preview?_u=mysql:临时用电作业许可证.ureport.xml&id=' + row.id)
      link.href = this.GLOBAL.pdfFileUrl + '/ureport/preview?_u=mysql:有限空间作业许可证.ureport.xml&id=' + row.id;
      link.click();
    },
    submitSave (data) {
      console.log(JSON.stringify(data));
      this.detailBox = false;
      this.openDialog.data = {};
      this.$message({ type: "success", message: "操作成功!" });
    },
    selectPersonInputClick (type) {
      this.personSelectDialog.selectPersonType = type;
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
      this.query.safe_area_id = value;
    },
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
  /* margin-left: 20px; */
  color: #666666;
  font-size: 18px;
  font-weight: bold;
}

.plan_main {
  height: 80vh;
  background-color: #fff;
}
</style>
<!-- tooltip文字提示样式 -->
<style>
.el-tooltip__popper {
  font-size: 14px;
  max-width: 50%;
}
</style>
