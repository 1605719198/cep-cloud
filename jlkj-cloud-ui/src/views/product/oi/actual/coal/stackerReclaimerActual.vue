<!--
  堆取料机实绩
-->
<template>
  <div class="avue-crud el-card__body"
       style="width: 98%;border: 0">
    <div class="avue-crud__search"
         style="border: 0">
      <el-form :inline="true">
        <div class="el-form-item el-form-item--small">
          <label class="el-form-item__label">起始日期</label>
          <div class="el-form-item__content">
            <el-date-picker v-model="query.startTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择起始日期" />
          </div>
        </div>
        <div class="el-form-item el-form-item--small">
          <label class="el-form-item__label">结束日期</label>
          <div class="el-form-item__content">
            <el-date-picker v-model="query.endTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择结束日期" />
          </div>
        </div>
        <div class="el-form-item el-form-item--small">
          <label class="el-form-item__label">班别</label>
          <div class="el-form-item__content">
            <el-select v-model="query.classType"
                       placeholder="选择班别"
                       clearable>
              <el-option v-for="item in classTypeOptions"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
          </div>
        </div>

        <div class="el-form-item el-form-item--small">
          <label class="el-form-item__label">班次</label>
          <div class="el-form-item__content">
            <el-select v-model="query.shift"
                       placeholder="班次"
                       clearable>
              <el-option v-for="item in shiftOptions"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
          </div>
        </div>

        <div class="el-form-item__content"
             style="margin-left: 0px;">
          <el-button v-hasPermi="['listStackerReclaimerPerformance']"
                     size="mini"
                     type="primary"
                     icon="el-icon-search"
                     @click="handleQuery">搜 索</el-button>
          <el-button size="mini"
                     type="default"
                     icon="el-icon-refresh-left"
                     @click="handleEmpty">重 置</el-button>
        </div>
        <el-row class="mb8">
          <el-button v-hasPermi="['listStackerReclaimerPerformance']"
                     type="primary"
                     size="mini"
                     plain
                     icon="el-icon-plus"
                     @click="handleAdd">手工填报
          </el-button>
        </el-row>
      </el-form>
    </div>
    <div>
      <el-form>
        <el-table height="66vh"
                  size="small"
                  :data="tableData"
                  stripe
                  v-loading="table.loading">
          <template v-for="column in columns">
            <el-table-column :prop="column.prop"
                             :label="column.label"
                             :sortable="column.sortable"
                             :width="column.width"
                             :min-width="column.minWidth"
                             :type="column.type"
                             :formatter="column.formatter"
                             :key="column.prop" />
          </template>
         <el-table-column fixed="right"
                          label="操作"
                          width="180px" >
           <template slot-scope="scope">
             <el-button v-hasPermi="['listStackerReclaimerPerformance']"
                        size="mini"
                        icon="el-icon-edit"
                        type="text"
                        @click="handleEdit(scope.row)">修改
             </el-button>
             <el-button v-hasPermi="['listStackerReclaimerPerformance']"
                        size="mini"
                        icon="el-icon-delete"
                        type="text"
                        @click="handleDelete(scope.$index, tableData)">删除
             </el-button>
           </template>
         </el-table-column>
        </el-table>
        <div style="margin-top: 10px;float: right;padding:25px 0 20px 20px ;"
             class="avue-crud__pagination">
          <el-pagination background
                         @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"
                         layout="total, sizes, prev, pager, next, jumper"
                         :current-page="page.currentPage"
                         :page-sizes="[20, 50, 100, 200]"
                         :page-size="page.pageSize"
                         :total="page.total">
          </el-pagination>
        </div>
      </el-form>
    </div>
    <!-- 弹窗 -->
    <div v-if="openDialog.open">
      <el-dialog :title="openDialog.title"
                 :visible.sync="openDialog.open"
                 width="500px"
                 class="customDialogStyle"
                 append-to-body
                 :destroy-on-close="true"
                 :close-on-click-modal="false"
                 @close="openDialog.open = false">
        <StackerReclaimerActualEditor :isAdd="openDialog.isAdd"
                             :data="openDialog.data"
                             @close="handleClose" />
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  deleteProductionStackerReclaimerPerformance,
  getStackerReclaimerPerformanceList
} from "@/api/production/oi/actual/coal/StackerReclaimerPerformanceApi";
import StackerReclaimerActualEditor from "@/views/product/oi/actual/coal/StackerReclaimerActualEditor";
import moment from "moment";
import { getDicts } from '@/api/system/dict/data'
export default {
  name: "stackerReclaimerActual",
  components:{StackerReclaimerActualEditor},
  data () {
    return {
      dataEdit: {},
      page: {
        pageSize: 20,
        currentPage: 1,
        total: 1,
      },
      classTypeOptions: [],
      shiftOptions: [],

      query: {
        startTime: '',
        endTime: '',
        classType: '',
        shift: '',
      },
      table: {
        border: true,
        loading: true
      },
      columns: [
        { label: '编号', type: "index", minWidth: '100px' },
        { label: '班别', prop: "class_name", sortable: true, minWidth: '80px' },
        {
          label: '班次', prop: "shift_name", sortable: true, minWidth: '80px', formatter (row) {
            switch (row.shift_name) {
              case "01":
                return "常白班";
              case "2201":
                return "白班";
              case "2202":
                return "夜班";
              case "00":
                return "休息";
              default:
                return row.shift_name;
            }
          }
        },
        { label: '堆取料机号', prop: "stacker_reclaimer_number", sortable: true, minWidth: '100px' },
        { label: '储位名称', prop: "slot_name", sortable: true, minWidth: '100px' },
        {
          label: '上煤开始', prop: "start_time", sortable: true, minWidth: '150px', formatter (row) {
            return row.start_time === null ? "" : moment(row.start_time).format("YYYY-MM-DD HH:mm:ss");
          }
        },
        {
          label: '上煤结束', prop: "end_time", sortable: true, minWidth: '150px', formatter (row) {
            return row.end_time === null ? "" : moment(row.end_time).format("YYYY-MM-DD HH:mm:ss");
          }
        },
        { label: '上煤时长(min)', prop: "duration", sortable: true, minWidth: '150px' },
        { label: '物料名称', prop: "material_name", sortable: true, minWidth: '100px' },
        {
          label: '创建时间', prop: "create_time", sortable: true, minWidth: '150px', formatter (row) {
            return row.create_time === null ? "" : moment(row.create_time).format("YYYY-MM-DD HH:mm:ss");
          }
        },

      ],
      tableData: [],
      openDialog:{
        open:false ,
        isAdd:true ,
        data:{},
        title:'',
      }
    }
  },
  mounted () {
    getDicts("sys_classtype").then(response => {
      this.classTypeOptions = response.data.map((i)=>{
        return { value: i.dictLabel, label:  i.dictLabel };
      });
    });
    getDicts("sys_shift_no").then(response => {
      this.shiftOptions = response.data.map((i)=>{
        return { value: i.dictValue, label:  i.dictLabel };
      });
    });
    this.handleQuery();
  },
  methods: {
    //查询
    handleQuery () {
      this.page.currentPage = 1;
      this.getStackerReclaimerActualList();
    },

    //获取列表数据
    getStackerReclaimerActualList(){
      let query = this.query;
      let page = this.page;
      let params = {
        "shiftName": query.shift,
        "className": query.classType,
        "startTime": query.startTime === "" ? "" : query.startTime,
        "endTime": query.endTime === "" ? "" : query.endTime,
        "size": page.pageSize,
        "current": page.currentPage,
        "order": "create_time",
        "orderby": "desc"
      }
      this.table.loading = true;
      getStackerReclaimerPerformanceList(params).then(response => {
        this.table.loading = false;
        this.tableData = response.data.records;
        this.page.total = response.data.total;
      }, error => {
        this.table.loading = false;
        window.console.log(error);
      });
    },

    // 清空
    handleEmpty () {
      this.query = {
        startTime: '',
        endTime: '',
        classType: '',
        shift: '',
      }
      this.handleQuery();
    },

    // 分页-每页多少条
    handleSizeChange (val) {
      this.page.pageSize = val ;
      this.getStackerReclaimerActualList();
    },
    // 分页-当前页
    handleCurrentChange (val) {
      this.page.currentPage = val ;
      this.getStackerReclaimerActualList();
    },

    handleClose(){
      this.openDialog.open = false;
      this.getStackerReclaimerActualList();
    },

    handleAdd(){
      this.openDialog.isAdd = true ;
      this.openDialog.data = {} ;
      this.openDialog.title = "手工填报堆取料机实绩";
      this.openDialog.open = true ;
    },

    handleEdit(row){
      this.openDialog.isAdd = false ;
      this.openDialog.data = {...row} ;
      this.openDialog.title = "修改堆取料机实绩";
      this.openDialog.open = true ;
    },

    handleDelete(index, rows){
      this.$confirm('此操作将永久删除数据记录，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {//点击确定，执行then方法
        deleteProductionStackerReclaimerPerformance(rows[index].id).then(res => {
          if (res.code=== 200){
            this.$message.success(res.msg);
            this.getStackerReclaimerActualList();
          }
        }, err => {
          window.console.log(err);
        });
      }).catch(()=>{
        this.$message.info("取消删除");
      })
    }
  }
}
</script>

<style scoped>
</style>
