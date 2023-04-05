<!--
 配煤实绩
-->
<template>
  <div class="avue-crud el-card__body"
       style="width: 98%;border: 0">
    <div class="avue-crud__search"
         style="border: 0">
      <el-form>
        <el-row :gutter="20">
          <el-col :span="3">
            <div class="el-form-item el-form-item--small">
              <div class="el-form-item__content">
                <el-date-picker v-model="query.start_time"
                                type="date"
                                placeholder="选择起始日期"
                                value-format="yyyy-MM-dd HH:mm:ss" />
              </div>
            </div>
          </el-col>

          <el-col :span="3">
            <div class="el-form-item el-form-item--small">
              <div class="el-form-item__content">
                <el-date-picker v-model="query.end_time"
                                type="date"
                                placeholder="选择结束日期"
                                value-format="yyyy-MM-dd HH:mm:ss" />
              </div>
            </div>
          </el-col>

          <el-col :span="3">
            <div class="el-form-item el-form-item--small">
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
          </el-col>

          <el-col :span="3">
            <div class="el-form-item el-form-item--small">
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
          </el-col>

          <el-col :span="10">
            <div class="el-form-item__content"
                 style="margin-left: 0px;">
              <el-button v-hasPermi="['listProductionCfgCokePlans']"
                         size="medium"
                         type="primary"
                         icon="el-icon-search"
                         @click="handleQuery">搜 索</el-button>
              <el-button size="medium"
                         type="default"
                         icon="el-icon-refresh-left"
                         @click="handleEmpty">重 置</el-button>
            </div>
          </el-col>
          <el-button v-hasPermi="['listProductionCfgCokePlans']"
                     icon="el-icon-plus"
                     size="medium"
                     type="primary"
                     @click="handleAdd('add')">手动配煤</el-button>
        </el-row>
      </el-form>
    </div>
    <div>
      <el-form>
        <el-table height="66vh"
                  size="small"
                  :data="tableData"
                  stripe
                  v-loading="table.loading"
                  @sort-change="sortChangeListener">
          <el-table-column v-for="column in columns"
                           :prop="column.prop"
                           :label="column.label"
                           :sortable="column.sortable"
                           :width="column.width"
                           :min-width="column.minWidth"
                           :formatter="column.formatter"
                           :type="column.type"
                           :key="column.prop" />
          <el-table-column fixed="right"
                           label="操作"
                           width="280px">
            <template slot-scope="scope">
              <el-button v-hasPermi="['listProductionCfgCokePlans']"
                         size="mini"
                         plain
                         type="info"
                         icon="el-icon-info"
                         @click="handleDetail(scope.$index)">详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px;right: 0;padding:25px 0 20px 20px ;"
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
        <el-dialog title="查看配煤实绩详情"
                   :visible.sync="detailBox"
                   width="1300px"
                   append-to-body
                   :destroy-on-close="true"
                   :close-on-click-modal="false">
          <coalBlendingActualDetail :dataEdit="dataEdit"
                                    @close="detailBox=false" />
        </el-dialog>
      </div>
      <!-- 新增弹窗 -->
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
                 :type="dialog.type"
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

import Add from "./handCoalBlending/coalStorageTower"
import coalBlendingActualDetail from "./coalBlendingActualDetail";
import { getConfigureCoalSpeciesList } from '@/api/production/oi/actual/coal/ConfigureCoalSpeciesPerformanceApi'
import moment from 'moment';
import { getDicts } from '@/api/system/dict/data'
export default {
  components: {
    coalBlendingActualDetail,
    Add
  },
  name: "coalBlendingActual",
  //爷孙组件传值
  provide () {
    return {
      grandpaOnLoad: this.handleQuery
    }
  },
  data () {
    return {
      detailBox: false,
      dataEdit: {},
      page: {
        pageSize: 20,
        currentPage: 1,
        total: 1,
        order: "create_time",
        orderby: "desc"
      },
      classTypeOptions: [],
      shiftOptions: [],
      query: {
        start_time: '',
        end_time: '',
        classType: '',
        shift: '',
      },
      table: {
        border: true,
        loading: true
      },
      columns: [
        { label: '编号', type: "index", minWidth: '100px' },
        { label: '班别', prop: "class_name", sortable: true, minWidth: '100px' },
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
        { label: '储煤塔号', prop: "coal_tower_number", sortable: true, minWidth: '100px' },
        {
          label: '配煤日期', prop: "configure_coal_species_date", sortable: true, minWidth: '150px', formatter (row) {
            return row.configure_coal_species_date === null ? "" : moment(row.configure_coal_species_date).format("YYYY-MM-DD HH:mm:ss");
          }
        },
        {
          label: '开始时间', prop: "start_time", sortable: true, minWidth: '150px', formatter (row) {
            return row.start_time === null ? "" : moment(row.start_time).format("YYYY-MM-DD HH:mm:ss");
          }
        },
        {
          label: '结束时间', prop: "end_time", sortable: true, minWidth: '150px', formatter (row) {
            return row.end_time === null ? "" : moment(row.end_time).format("YYYY-MM-DD HH:mm:ss");
          }
        },
        { label: '配煤时长(min)', prop: "duration", sortable: true, minWidth: '150px' },
        {
          label: '配煤总量(t)', prop: "coal_tower_weight", sortable: true, minWidth: '100px', formatter (row) {
            return row.coal_tower_weight / 1000;
          }
        },
        {
          label: '创建时间', prop: "create_time", sortable: true, minWidth: '150px', formatter (row) {
            return row.create_time === null ? "" : moment(row.create_time).format("YYYY-MM-DD HH:mm:ss");
          }
        },

      ],
      tableData: [],
      buttonLoading: false,
      dialog: {
        visible: false,
        type: '',
        title: '',
        width: '60%',
        data: {},
      },
    }
  },
  mounted () {
    getDicts("sys_classtype").then(response => {
      this.classTypeOptions = response.data.data.map((i)=>{
        return { value: i.dictLabel, label:  i.dictLabel };
      });
    });
    getDicts("sys_shift_no").then(response => {
      this.shiftOptions = response.data.data.map((i)=>{
        return { value: i.dictValue, label:  i.dictLabel };
      });
    });
    this.handleQuery();
  },
  methods: {
    //查询
    handleQuery () {
      this.page.currentPage = 1;
      this.getCoalBlendingListPage();
    },
    // 清空
    handleEmpty () {
      this.query = {
        start_time: '',
        end_time: '',
        classType: '',
        shift: ''
      }
      this.handleQuery();
    },
    //新增
    handleAdd (type) {
      this.dialog.type = type
      this.dialog.title = '手动配煤'
      this.dialog.data = {};
      this.dialog.visible = true;
    },
    //详情
    handleDetail (index) {
      this.dataEdit = this.tableData[index];
      this.detailBox = true;
    },

    // 分页-每页多少条
    handleSizeChange (val) {
      this.page.pageSize = val;
      this.getCoalBlendingListPage();
    },
    // 分页-当前页
    handleCurrentChange (val) {
      this.page.currentPage = val;
      this.getCoalBlendingListPage();
    },
    //提供给子类调用的方法
    submitSave () {
      this.dialog.visible = false;
      this.dialog.data = {};
      this.handleQuery();
    },

    //查找配煤实绩记录
    getCoalBlendingListPage () {
      let query = this.query;
      let page = this.page;
      let params = {
        "shift_name": query.shift,
        "class_name": query.classType,
        "start_time": query.start_time === "" ? "" : query.start_time,
        "end_time": query.end_time === "" ? "" : query.end_time,
        "current": page.currentPage,
        "size": page.pageSize,
        "order": page.order,
        "orderby": page.orderby
      }
      this.table.loading = true;
      this.buttonLoading = true;
      getConfigureCoalSpeciesList(params).then(response => {
        this.table.loading = false;
        this.tableData = response.data.records;
        this.page.total = response.data.total;
      }, error => {
        this.table.loading = false;
        window.console.log(error);
      })
    },

    sortChangeListener (data) {
      this.page.order = data.prop;
      if (data.order === "ascending") {
        this.page.orderby = "asc";
      } else if (data.order === "descending") {
        this.page.orderby = "desc";
      } else {
        this.page.orderby = "desc";
      }
      this.getCoalBlendingListPage();
    }
  }
}
</script>

<style scoped>
</style>
