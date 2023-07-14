
<template>
  <div class="avue-crud el-card__body" style="width: 98%;border: 0 ; background-color: #FFFFFF">
    <div class="avue-crud__search" style="border: 0 ">
      <el-row :gutter="20">
        <el-col :span="3">
          <el-date-picker type="year" v-model="query.year" placeholder="选择年" clearable value-format="yyyy" />
        </el-col>
        <el-col :span="2">
          <el-select class="customSelectStyle" :popper-append-to-body="false" v-model="query.month" clearable
            placeholder="选择月">
            <el-option v-for="item in monthOptions" :value="item.value" :label="item.label" :key="item.value" />
          </el-select>
        </el-col>
        <el-col :span="10">
          <div class="el-form-item__content" style="margin-left: 0px;">
            <el-button v-hasPermi="['getProductionYieldAnalysisMonth']" size="medium" icon="el-icon-search"
              type="primary" @click="handleQuery">搜 索</el-button>
            <el-button size="medium" icon="el-icon-refresh-left" type="default" @click="handleClearUp">重 置</el-button>
          </div>
        </el-col>
      </el-row>
    </div>
    <div style="height:35vh">
      <el-table ref="tableName" height="35vh" size="small" :data="tableData" stripe width="100%"
        v-loading="table.loading" style="margin-top:20px">
        <el-table-column label="日期" min-width="60" prop="mDate" align="center" />
        <!-- <template v-for="item in materialList">
          <el-table-column :label="item.target_item_name"
                           min-width="100"
                           align="center"
                           :key="item.id">
            <el-table-column label="计划产量"
                             min-width="50"
                             :prop="'plan_' + item.material_id"
                             align="center" />
            <el-table-column label="实绩产量"
                             min-width="50"
                             :prop="'performance_' + item.material_id"
                             align="center" />
          </el-table-column>
        </template> -->
        <!-- <el-table-column v-for="item in materialList"
                         :label="item.target_item_name"
                         min-width="100"
                         align="center"
                         :key="item.id">
          <el-table-column label="计划产量"
                           min-width="50"
                           :prop="'plan_' + item.id"
                           align="center" />
          <el-table-column label="实绩产量"
                           min-width="50"
                           :prop="'performance_' + item.id"
                           align="center" />
        </el-table-column> -->
        <el-table-column v-for="item in materialList" :label="item.materials_name" min-width="100" align="center"
          :key="item.id">
          <el-table-column v-if="item.materials_name != '干熄焦(t)' && item.materials_name != '湿熄焦(t)'" label="计划产量"
            min-width="50" :prop="'plan_' + item.id" align="center" :formatter="formatyield" />
          <el-table-column label="实绩产量" min-width="50" :prop="'performance_' + item.id" align="center"
            :formatter="formatyield" />
        </el-table-column>
      </el-table>
    </div>
    <div style="height:40vh;padding-top: 2vh">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <!-- <template v-for="item in materialList">
          <el-tab-pane :label="item.target_item_name"
                       :name="item.material_id"
                       :key="item.id">
            <YieldAnalysisBar :refresh="chart.refresh"
                              :char-id="item.material_id"
                              :legend="chart.legend"
                              :yieldData="chart.totalData[item.material_id]"
                              :text="chart.title" />
          </el-tab-pane>
        </template> -->
        <!-- <el-tab-pane v-for="item in materialList"
                     :label="item.target_item_name"
                     :name="item.id"
                     :key="item.id">
          <YieldAnalysisBar :refresh="chart.refresh"
                            :charId="item.id"
                            :legend="chart.legend"
                            :yieldData="chart.totalData[item.id]"
                            :text="chart.title" />
        </el-tab-pane> -->
        <el-tab-pane v-for="item in materialList" :label="item.materials_name" :name="item.id" :key="item.id">
          <YieldAnalysisBar :refresh="chart.refresh" :charId="item.id" :legend="chart.legend"
            :yieldData="chart.totalData[item.id]" :text="chart.title" />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { listProductionTargetItem, getProductionYieldAnalysisColList } from "@/api/production/oi/parameter"
import YieldAnalysisBar from "@/views/product/oi/statistics/YieldAnalysisBar";
import {
  getProductionYieldAnalysisDate,
  getProductionYieldAnalysisMonth,
  listMaterialsBox
} from "@/api/production/oi/YieldAnalysisApi";

export default {
  components: {
    YieldAnalysisBar
  },
  name: "YieldAnalysis",
  data() {
    return {
      query: {
        year: '',
        month: ''
      },
      page: {
        size: 20,
        total: 100
      },
      monthOptions: [
        { value: '1', label: '1月' },
        { value: '2', label: '2月' },
        { value: '3', label: '3月' },
        { value: '4', label: '4月' },
        { value: '5', label: '5月' },
        { value: '6', label: '6月' },
        { value: '7', label: '7月' },
        { value: '8', label: '8月' },
        { value: '9', label: '9月' },
        { value: '10', label: '10月' },
        { value: '11', label: '11月' },
        { value: '12', label: '12月' },
      ],
      materialList: [],
      columns: [],
      tableData: [],
      chart: {
        legend: ['计划产量', '实绩产量'],
        totalData: {},
        title: '',
        refresh: ''
      },
      table: {
        loading: false
      },
      activeName: '',
    }
  },
  methods: {

    initData() {
      //年份默认是当年的
      this.query.year = new Date().getFullYear().toString();
      this.getYieldList();
    },

    //查询
    handleQuery() {
      this.getYieldList();
    },

    //清空
    handleClearUp() {
      this.query.year = new Date().getFullYear().toString();
      this.query.month = '';
      this.getYieldList();
    },
    //产量格式化
    formatyield(row, column, cellValue) {
      // console.log(cellValue, "cellValue")
      return (cellValue / 1000).toFixed(3)
    },
    //Tabs 点击回调
    handleClick(tab, event) {

      if (tab._props.label == '干熄焦(t)' || tab._props.label == '湿熄焦(t)') {
        this.chart.legend = ['实绩产量']
      } else {
        this.chart.legend = ['计划产量', '实绩产量']
      }
    },
    //获取产量列表
    getYieldList() {
      if (!this.query.year) {
        this.$message.error("请选择年份");
        return
      }
      let that = this;
      //这里必须在请求的时候把数据先置空 ， 不然表格绘制高度会发生错误
      this.tableData = [];
      this.materialList = [];
      this.table.loading = true;
      // let save = {
      //   "targetItemTypeId": 1,
      //   "size": 10000,
      //   "current": 1,
      //   "total": 1,
      //   "order": "create_time",
      //   "orderby": "asc",
      // }
      // listProductionTargetItem(save).then(response => {
      //   console.log(response.data.data.records, 'response')
      //   that.materialList = response.data.data.records;
      //   // that.activeName = response.data.data.records[0].material_id;
      //   that.activeName = response.data.data.records[0].id;

      //   //获取完列表的标题才能获取数据
      //   if (!this.query.month) {
      //     axios.all([this.getMaterialList(), this.getMonthYieldList()])
      //       .then(axios.spread(function (materialListRes, yieldListRes) {
      //         that.table.loading = false;
      //         // if (materialListRes.status === 200) {
      //         //   that.materialList = materialListRes.data;
      //         //   that.activeName = materialListRes.data[0].id;
      //         // }
      //         if (yieldListRes.status === 200) {
      //           that.tableData = yieldListRes.data;
      //           that.chart.title = that.query.year;
      //           for (let i = 0; i < that.tableData.length; i++) {
      //             that.tableData[i]['mDate'] = that.tableData[i].year + "-" + that.tableData[i].month;
      //           }
      //           that.chart.totalData = { ...that.getEChartYieldListByMaterialID('month') };
      //         }
      //       }));
      //   } else {
      //     axios.all([this.getMaterialList(), this.getDayYieldList()])
      //       .then(axios.spread(function (materialListRes, yieldListRes) {
      //         that.table.loading = false;
      //         // if (materialListRes.status === 200) {
      //         //   that.materialList = materialListRes.data;
      //         //   that.activeName = materialListRes.data[0].id;
      //         // }
      //         if (yieldListRes.status === 200) {
      //           that.tableData = yieldListRes.data;
      //           that.chart.title = that.query.year + "-" + that.query.month;
      //           for (let i = 0; i < that.tableData.length; i++) {
      //             that.tableData[i]['mDate'] = that.tableData[i].year + "/" + that.tableData[i].month + "/" + that.tableData[i].date;
      //           }
      //           that.chart.totalData = { ...that.getEChartYieldListByMaterialID('day') };
      //         }
      //       }));
      //   }
      //   //el-table进行重载
      //   this.$nextTick(() => {
      //     this.$refs.tableName.doLayout();
      //   });
      // })

      getProductionYieldAnalysisColList().then(res => {
        // console.log(res.data, 'res')
        // that.materialList = res.data
        that.activeName = res.data[0].id;
        this.chart.legend = ['计划产量', '实绩产量']
        res.data.forEach(function (val) {
          let column = { id: val.id, materials_name: val.materials_name + '(t)' };

          if (val.materials_name == '蒸汽') {
            column.materials_name = val.materials_name + '(t)'
          }

          that.materialList.push(column);
          // console.log(val.materials_name)
          // let id = that.materialList[1].id
          // let art = { materials_name: "first", id: id };
          // this.$set(that.materialList, 1, art);
        });

        console.log(that.materialList[1].materials_name)
        //获取完列表的标题才能获取数据
        if (!this.query.month) {
          axios.all([this.getMaterialList(), this.getMonthYieldList()])
            .then(axios.spread(function (materialListRes, yieldListRes) {
              that.table.loading = false;
              // if (materialListRes.status === 200) {
              //   that.materialList = materialListRes.data;
              //   that.activeName = materialListRes.data[0].id;
              // }
              if (yieldListRes.status === 200) {
                that.tableData = yieldListRes.data;
                that.chart.title = that.query.year;
                for (let i = 0; i < that.tableData.length; i++) {
                  that.tableData[i]['mDate'] = that.tableData[i].year + "-" + that.tableData[i].month;
                }
                that.chart.totalData = { ...that.getEChartYieldListByMaterialID('month') };
              }
            }));
        } else {
          axios.all([this.getMaterialList(), this.getDayYieldList()])
            .then(axios.spread(function (materialListRes, yieldListRes) {
              that.table.loading = false;
              // if (materialListRes.status === 200) {
              //   that.materialList = materialListRes.data;
              //   that.activeName = materialListRes.data[0].id;
              // }
              if (yieldListRes.status === 200) {
                that.tableData = yieldListRes.data;
                that.chart.title = that.query.year + "-" + that.query.month;
                for (let i = 0; i < that.tableData.length; i++) {
                  that.tableData[i]['mDate'] = that.tableData[i].year + "/" + that.tableData[i].month + "/" + that.tableData[i].date;
                }
                that.chart.totalData = { ...that.getEChartYieldListByMaterialID('day') };
              }
            }));
        }
        //el-table进行重载
        this.$nextTick(() => {
          this.$refs.tableName.doLayout();
        });
      })
    },

    //获取物料列表
    getMaterialList() {
      return listMaterialsBox();
    },

    //获取一年内每个月的产量列表
    getMonthYieldList() {
      let params = {
        "year": this.query.year,
        "cols": "string",
        "cols_max": "string"
      }
      return getProductionYieldAnalysisMonth(params);
    },

    //获取一个月内每天的产量俩表
    getDayYieldList() {
      let params = {
        "year": this.query.year,
        "month": this.query.month,
        "cols": "string",
        "cols_max": "string"
      }
      return getProductionYieldAnalysisDate(params)
    },

    //通过物料ID获取产量列表
    getEChartYieldListByMaterialID(type) {
      let yieldTotalData = [...this.tableData];
      let resultObj = {};
      let obj = null;
      if (yieldTotalData && yieldTotalData.length >= 1) {
        obj = yieldTotalData[0];
      }
      if (obj) {
        for (let item in obj) {
          if (item) {
            if (item.startsWith("performance_") || item.startsWith("plan_")) {
              let keyArray = item.split("_");
              let materialID = keyArray[1];
              resultObj[materialID] = [];
              for (let i = 0; i < yieldTotalData.length; i++) {
                let itemObj = yieldTotalData[i];
                for (const itemObjKey in itemObj) {
                  if (itemObjKey.includes(materialID)) {
                    let itemYieldObj = resultObj[materialID][i] === undefined ? {} : resultObj[materialID][i];
                    let xAxisText = '';
                    if (type === 'month') {
                      xAxisText = itemObj['month'] + '月';
                    } else {
                      xAxisText = itemObj['date'] + '日';
                    }
                    itemYieldObj['xAxisText'] = xAxisText;
                    if (itemObjKey.startsWith("performance_")) {
                      itemYieldObj['performance'] = itemObj[itemObjKey];
                    } else if (itemObjKey.startsWith("plan_")) {
                      itemYieldObj['plan'] = itemObj[itemObjKey];
                    }
                    resultObj[materialID][i] = itemYieldObj;

                  }
                }
              }
            }
          }
        }
      }
      return resultObj;
    },

  },

  mounted() {
    this.initData();
  },

  watch: {
    activeName() {
      console.log("--------------" + new Date().getTime());
      this.chart.refresh = new Date().getTime();
    }
  }
}
</script>

<style scoped>
.el-card__body {
  padding: 10px 10px 20px 20px;
}
</style>
