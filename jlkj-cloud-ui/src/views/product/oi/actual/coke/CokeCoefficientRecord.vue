<!--
  焦炉系数记录
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
                                value-format="yyyy-MM-dd " />
              </div>
            </div>
          </el-col>
          <el-col :span="3">
            <div class="el-form-item el-form-item--small">
              <div class="el-form-item__content">
                <el-date-picker v-model="query.end_time"
                                type="date"
                                placeholder="选择结束日期"
                                value-format="yyyy-MM-dd " />
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
              <el-button v-hasPermi="['getProductionCoefficientRecord']"
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
                  v-loading="table.loading"
                  :default-sort="{ prop: 'number', order: 'descending' }">
          <!-- <template v-for="column in columns ">

            <el-table-column :prop="column.prop" :label="column.label" :sortable="column.sortable" :width="column.width"
              :min-width="column.minWidth" :key="column.prop" />
          </template> -->
          <el-table-column label="测温日期"
                           prop="measure_time"
                           sortable="true"
                           minWidth='150px' />
          <el-table-column label="班别"
                           prop="class_name"
                           sortable="true"
                           minWidth="80px"></el-table-column>
          <el-table-column label='班次'
                           prop="shift_name"
                           sortable="true"
                           minWidth="80px"
                           :formatter="shiftFormatter">
          </el-table-column>
          <el-table-column label='炉号'
                           prop="coke_oven_number"
                           sortable="true"
                           minWidth='80px'></el-table-column>
          <el-table-column label='K均'
                           prop="k_jun"
                           sortable="true"
                           minWidth='100px'></el-table-column>
          <el-table-column label='K安'
                           prop="k_an"
                           sortable="true"
                           minWidth='100px'></el-table-column>
          <el-table-column label='K1'
                           prop="k1"
                           sortable="true"
                           minWidth='100px'></el-table-column>
          <el-table-column label='K2'
                           prop="k2"
                           sortable="true"
                           minWidth='100px'></el-table-column>
          <el-table-column label='K3'
                           prop="k3"
                           sortable="true"
                           minWidth='100px'></el-table-column>
        </el-table>
        <div style="margin-top: 10px;right: 0;padding:25px 0 20px 20px ;"
             class="avue-crud__pagination">
          <el-pagination background
                         @size-change="handleSizeChange"
                         @current-change="handleCurrentChange"
                         :current-page="page.current"
                         :page-sizes="[20, 50, 100, 200]"
                         :page-size="page.size"
                         layout="total, sizes, prev, pager, next, jumper"
                         :total="page.total">
          </el-pagination>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { getProductionCoefficientRecord } from "@/api/production/oi/actual/coke/ProductionCoefficientRecord";
import { getDicts } from '@/api/system/dict/data'
export default {
  name: "CokeCoefficientRecord",
  data () {
    return {
      page: {
        size: 20,
        current: 1,
        total: 1,
        layout: "total, sizes, prev, pager, next, jumper",
        order: "measure_time",
        orderby: "desc",
      },

      classTypeOptions: [],
      shiftOptions: [],
      query: {
        start_time: '',
        end_time: '',
        classType: '',
        shift: ''
      },
      table: {
        border: true,
        loading: true
      },
      // columns: [
      //   { label: '测温日期', prop: "measure_time", sortable: true, minWidth: '100px' },
      //   { label: '班别', prop: "class_name", sortable: true, minWidth: '80px' },
      //   { label: '班次', prop: "shift_name", sortable: true, minWidth: '80px' },
      //   { label: '炉号', prop: "coke_oven_number", sortable: true, minWidth: '80px' },
      //   { label: 'K均', prop: "k_jun", sortable: true, minWidth: '150px' },
      //   { label: 'K安', prop: "k_an", sortable: true, minWidth: '150px' },
      //   { label: 'K1', prop: "K1", sortable: true, minWidth: '100px' },
      //   { label: 'K2', prop: "K2", sortable: true, minWidth: '150px' },
      //   { label: 'K3', prop: "K3", sortable: true, minWidth: '100px' },
      // ],
      tableData: [
      ],
    }
  },
  created () {
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
    this.getProductionCoefficientRecord()
  },
  methods: {
    getProductionCoefficientRecord () {
      let query = this.query;
      let page = this.page;
      let params = {
        "size": page.size,
        "current": page.current,
        // "order": "id",
        // "orderby": "asc",
        "order": page.order,
        "orderby": page.orderby,
        "shift_name": query.shift,
        "class_name": query.classType,
        "start_time": query.start_time,
        "end_time": query.end_time
      }
      this.table.loading = true;
      getProductionCoefficientRecord(params).then(response => {
        this.table.loading = false;
        //  console.log(response);
        this.tableData = response.data.records;
        this.page.total = response.data.total;
        // console.log(this.page.total);
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
        this.page.order = "measure_time";
        this.page.orderby = "desc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.handleQuery();
    },
    //查询
    handleQuery () {
      // let query = this.query
      // console.log(query)
      this.getProductionCoefficientRecord()
    },
    // 清空
    handleEmpty () {
      this.page.current = 1;
      this.query = {
        shift: '',
        classType: '',
        start_time: '',
        end_time: '',
      }
      this.getProductionCoefficientRecord()
    },

    // 分页-每页多少条
    handleSizeChange (val) {

      this.page.size = val;
      this.getProductionCoefficientRecord()
    },
    // 分页-当前页
    handleCurrentChange (val) {
      this.page.current = val;
      this.getProductionCoefficientRecord()
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
    }
  }
}
</script>

<style scoped>
</style>
