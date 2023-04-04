<!--
  焦炭出厂实绩
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
                                value-format="yyyy-MM-dd" />
              </div>
            </div>
          </el-col>
          <el-col :span="3">
            <div class="el-form-item el-form-item--small">
              <div class="el-form-item__content">
                <el-date-picker v-model="query.end_time"
                                type="date"
                                placeholder="选择结束日期"
                                value-format="yyyy-MM-dd" />
              </div>
            </div>
          </el-col>
          <!--          <el-col :span="3">-->
          <!--            <div class="el-form-item el-form-item&#45;&#45;small">-->
          <!--              <div class="el-form-item__content">-->
          <!--                <el-select v-model="query.classType"-->
          <!--                           placeholder="选择班别"-->
          <!--                           clearable>-->
          <!--                  <el-option v-for="item in classTypeOptions"-->
          <!--                             :key="item.value"-->
          <!--                             :label="item.label"-->
          <!--                             :value="item.value">-->
          <!--                  </el-option>-->
          <!--                </el-select>-->
          <!--              </div>-->
          <!--            </div>-->
          <!--          </el-col>-->

          <!--          <el-col :span="3">-->
          <!--            <div class="el-form-item el-form-item&#45;&#45;small">-->
          <!--              <div class="el-form-item__content">-->
          <!--                <el-select v-model="query.shift"-->
          <!--                           placeholder="班次"-->
          <!--                           clearable>-->
          <!--                  <el-option v-for="item in shiftOptions"-->
          <!--                             :key="item.value"-->
          <!--                             :label="item.label"-->
          <!--                             :value="item.value">-->
          <!--                  </el-option>-->
          <!--                </el-select>-->
          <!--              </div>-->
          <!--            </div>-->
          <!--          </el-col>-->

          <el-col :span="3">
            <div class="el-form-item el-form-item--small">
              <div class="el-form-item__content">
                <el-select v-model="query.material_code"
                           placeholder="物料代码"
                           clearable>
                  <el-option v-for="item in materialsOptions"
                             :key="item.materials_code"
                             :label="item.materials_name"
                             :value="item.materials_code">
                  </el-option>
                </el-select>
              </div>
            </div>
          </el-col>

          <el-col :span="7">
            <div class="el-form-item__content"
                 style="margin-left: 0px;">
              <el-button v-hasPermi="['getProductionCokeFactoryPerformancePage']"
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
        </el-row>
      </el-form>
    </div>
    <div>
      <el-form>
        <el-table height="66vh"
                  size="small"
                  :data="tableData"
                  stripe
                  @sort-change="handleSort"
                  :default-sort="{prop: 'number', order: 'descending'}"
                  v-loading="table.loading">
          <!-- <template v-for="column in columns">
            <el-table-column :prop="column.prop"
                             :label="column.label"
                             :sortable="column.sortable"
                             :width="column.width"
                             :min-width="column.minWidth" :key="column.prop"/>
          </template> -->
          <!--          <el-table-column label="编号"-->
          <!--                           type="index"-->
          <!--                           sortable="true"-->
          <!--                           minWidth='100px' />-->
          <!--          <el-table-column label="班别"-->
          <!--                           prop="class_name"-->
          <!--                           sortable="true"-->
          <!--                           minWidth="80px"></el-table-column>-->
          <!--          <el-table-column label='班次'-->
          <!--                           prop="shift_name"-->
          <!--                           sortable="true"-->
          <!--                           minWidth="80px"-->
          <!--                           :formatter="shiftFormatter">-->
          <!--          </el-table-column>-->
          <el-table-column label='申请人'
                           prop="issue_emp_name"
                           sortable="true"
                           minWidth='80px'></el-table-column>
          <el-table-column label='客户名称'
                           prop="client_name"
                           sortable="true"
                           minWidth='150px'></el-table-column>
          <el-table-column label='物料名称'
                           prop="materials_name"
                           sortable="true"
                           minWidth='150px'></el-table-column>
          <el-table-column label='车牌号'
                           prop="vehicle_number"
                           sortable="true"
                           minWidth='100px'></el-table-column>
          <el-table-column label='装车日期'
                           prop="loading_time"
                           sortable="true"
                           minWidth='150px'>
            <template slot-scope="scope">
              {{scope.row.loading_time.substring(0,10)}}
            </template>
          </el-table-column>
          <el-table-column label='净重(t)'
                           prop="net_wgt"
                           sortable="true"
                           minWidth='100px'>
            <template slot-scope="scope">
              {{scope.row.net_wgt / 1000}}
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
                         :layout="page.layout"
                         :total="page.total">
          </el-pagination>
        </div>
      </el-form>
    </div>
  </div>
</template>
<script>
import {
  getMaterialsCodeListByType,
  getProductionCokeFactoryPerformancePage
} from "@/api/production/oi/actual/coke/ProductionCokeFactoryPerformancePage";
import { getDicts } from '@/api/system/dict/data'
export default {
  name: "CokeOutFactoryActual",
  data () {
    return {
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
        layout: "total, sizes, prev, pager, next, jumper",
        order: "loading_time",
        orderby: "desc"
      },
      currentPage: 0,
      total: 0,
      classTypeOptions: [],
      shiftOptions: [],
      materialsOptions: [],
      query: {
        start_time: null,
        end_time: null,
        classType: '',
        shift: '',
        material_code: ''
      },
      table: {
        border: true,
        loading: true
      },
      tableData: [
      ],
    }
  },
  created () {
    // getDicts("sys_classtype").then(response => {
    //   this.classTypeOptions = response.data.data.map((i)=>{
    //     return { value: i.dictLabel, label:  i.dictLabel };
    //   });
    // });
    // getDicts("sys_shift_no").then(response => {
    //   this.shiftOptions = response.data.data.map((i)=>{
    //     return { value: i.dictValue, label:  i.dictLabel };
    //   });
    // });
    this.getMaterialsOptions();
    this.getProductionCokeFactoryPerformancePage()
  },
  methods: {
    // 加载
    getProductionCokeFactoryPerformancePage () {
      let query = this.query;
      let page = this.page;
      let params = {
        "size": page.pageSize,
        "current": page.currentPage,
        // "order": "id",
        // "orderby": "",
        "order": page.order,
        "orderby": page.orderby,
        "shift_name": query.shift,
        "start_time": query.start_time === null ? "" : query.start_time + " 00:00:00",
        "end_time": query.end_time === null ? "" : query.end_time + " 23:59:59",
        "class_name": query.classType,
        "material_code": query.material_code === null ? "" : query.material_code
      }
      this.table.loading = true;
      getProductionCokeFactoryPerformancePage(params).then(response => {
        this.table.loading = false;
        this.tableData = response.data.records;
        this.page.total = response.data.total;
      }, error => {
        this.table.loading = false;
        window.console.log(error);
      })
    },
    // 排序
    handleSort (column) {
      // console.log(column, "column")
      if (column.order === null) {
        //默认
        this.page.order = "loading_time";
        this.page.orderby = "desc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.getProductionCokeFactoryPerformancePage();
    },
    //查询
    handleQuery () {
      this.page.currentPage = 1;
      this.getProductionCokeFactoryPerformancePage();
    },
    // 清空
    handleEmpty () {
      this.page.currentPage = 1
      this.query = {
        shift: '',
        classType: '',
        start_time: null,
        end_time: null,
      }
      this.getProductionCokeFactoryPerformancePage();
    },
    // 分页-每页多少条
    handleSizeChange (val) {
      this.page.pageSize = val;
      this.getProductionCokeFactoryPerformancePage();
    },
    // 分页-当前页
    handleCurrentChange (val) {
      this.page.currentPage = val;
      this.getProductionCokeFactoryPerformancePage();
    },
    //提供给子类调用父类的方法
    submitAdd (data) {
      console.log(JSON.stringify(data))
      this.addBox = false;
      this.editBox = false;
      this.$message({
        type: "success",
        message: "操作成功!"
      });
    },

    shiftFormatter (row) {
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
    },

    //获取物料代码下拉列表
    getMaterialsOptions () {
      getMaterialsCodeListByType("01").then(res => {
        if (res.code === 200) {
          this.materialsOptions = res.data;
        }
      }, err => {
        window.console.log(err);
      });
    }
  }
}
</script>
<style scoped>
</style>
