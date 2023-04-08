<template>
  <div class="app-container">
    <el-form :model="query" ref="query" :inline="true"  label-width="68px">
      <el-form-item prop="shiftName" label="班次">
        <el-select placeholder="选择班次" v-model="query.shiftName" clearable :popper-append-to-body="false"
                   class="customSelectStyle">
          <el-option v-for="item in shiftOptions" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="className" label="班别">
        <el-select placeholder="选择班别" v-model="query.className" clearable :popper-append-to-body="false"
                   class="customSelectStyle">
          <el-option v-for="item in classTypeOptions" :key="item.value" :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="cokeMaterialsCode" label="料号名称">
        <el-select :popper-append-to-body="false" class="customSelectStyle"
                   v-model="query.cokeMaterialsCode" clearable filterable placeholder="选择煤的料号名称">
          <el-option v-for="item in gradeOptions" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" v-hasPermi="['listMaterialLoadingPerformance']" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="handleEmpty">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="table.loading" :data="tableData" @sort-change="handleSort" height="67vh">
      <el-table-column label="序号" type="index" width="55" align="center" />
      <el-table-column label="班次" sortable minWidth="150" align="left" prop="shift_name"
                       :formatter="shiftFormatter" />
      <el-table-column label="班别" sortable minWidth="150" align="left" prop="class_name" />
      <el-table-column label="配煤计划编号" sortable minWidth="150" align="left" prop="plan_number" />
      <el-table-column label="焦炭等级名称" minWidth="150" align="left" prop="coal_level_materials_name" />
      <el-table-column label="配煤仓号" minWidth="150" align="left" prop="coke_warehouse_number" />
      <el-table-column label="煤的料号名称" minWidth="150" align="left" prop="coke_materials_name" />
      <el-table-column label="上煤重量(t)" minWidth="150" align="left" prop="coke_weight">
        <template slot-scope="scope">
          {{ (scope.row.coke_weight / 1000).toFixed(3) }}
        </template>
      </el-table-column>
      <el-table-column label="创建人姓名" minWidth="150" align="left" prop="create_user_name" />
      <el-table-column label="创建时间" minWidth="150" align="left" prop="create_time" sortable />
    </el-table>
    <pagination v-show="page.total > 0" background @size-change="handleSizeChange"
                @current-change="handleCurrentChange" layout="total, sizes, prev, pager, next, jumper"
                :current-page="page.current" :page-sizes="[20, 50, 100, 200]" :page-size="page.size"
                :total="page.total">
    </pagination>
  </div>
</template>

<script>
import { listMaterialsBoxM } from "@/api/material/mr/parameter/materialCode"
import { getProductionConveyingCoalActualPage } from "@/api/production/oi/actual/coal/MaterialLoadingPerformanceApi";
import { getDicts } from '@/api/system/dict/data'
export default {
  data() {
    return {
      dataEdit: {},
      page: {
        size: 20,
        current: 1,
        total: 0,
        order: "create_time",
        orderby: "desc",
      },
      classTypeOptions: [
        { value: "甲", label: "甲" },
        { value: "乙", label: "乙" },
        { value: "丙", label: "丙" },
      ],
      shiftOptions: [
        { value: "01", label: "常白班" },
        { value: "2201", label: "白班" },
        { value: "2202", label: "夜班" },
        { value: "00", label: "休息" }
      ],
      query: {
        className: null,
        shiftName: null,
        cokeMaterialsCode: null
      },
      table: {
        border: true,
        loading: true
      },
      tableData: [],
      dialogVisible: false,
      gradeOptions: []
    }
  },
  created() {
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
    this.getMaterialsOptions()
    this.handleQuery();
  },
  methods: {
    //获取物料代码（煤）
    getMaterialsOptions() {
      const param = {}
      listMaterialsBoxM(param).then((res) => {
        // console.log(res)
        let options = []
        res.forEach(item => {
          let i = {
            id: item.materials_code,
            name: item.materials_name
          }
          options.push(i)
        });
        this.gradeOptions = options
      })
    },
    //查询
    handleQuery() {
      this.table.loading = true;//加载状态
      getProductionConveyingCoalActualPage(this.page, this.query).then(res => {
        this.table.loading = false;
        let data = res.data;//表格相关数据
        this.page.total = data.total;//数据总数
        this.tableData = data.records;//表格数据
      }, error => {
        this.table.loading = false;
        window.console.log(error);
      });
    },
    // 清空
    handleEmpty() {
      this.page.current = 1
      this.$refs["query"].resetFields()
      this.handleQuery();
    },

    // 排序
    handleSort(column) {
      // console.log(column, "column")
      if (column.order === null) {
        //默认
        this.page.order = "create_time";
        this.page.orderby = "desc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.handleQuery();
    },

    // 分页-每页多少条
    handleSizeChange(val) {
      this.page.size = val;
      this.handleQuery();
    },
    // 分页-当前页
    handleCurrentChange(val) {
      this.page.current = val;
      this.handleQuery();
    },

    jump() {
      this.dialogVisible = true;
    },
    shiftFormatter(row) {
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
  }
}
</script>

<style scoped lang="scss">
.el-card__body {
  padding: 10px 20px 20px 10px;
}

.pagination {
  white-space: nowrap;
  padding: 25px 0px 20px 0px;
  color: #303133;
  font-weight: 700;
}

.main {
  // height: 86vh;
  background-color: #fff;
}

.search {
  width: 100%;
  display: flex;
  justify-content: space-between;
}

::v-deep.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>

