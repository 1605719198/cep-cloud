<template>
  <div class="avue-crud el-card__body"
       style="background-color: #FFF;width:99%">
    <!-- 头部 -->
    <div class="avue-crud__search"
         style="border: 0">
      <el-form ref="query"
               :model="query">
        <el-row :gutter="20">
          <el-col :span="3">
            <el-form-item label=""
                          prop="year">
              <el-date-picker v-model="query.year"
                              type="year"
                              placeholder="选择年"
                              format="yyyy"
                              value-format="yyyy" />
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-form-item label=""
                          prop="month">
              <!-- <el-date-picker v-model="query.month"
                              type="month"
                              placeholder="选择月"
                              format="MM"
                              value-format="MM" /> -->
              <el-select class="customSelectStyle"
                         :popper-append-to-body="false"
                         v-model="month"
                         clearable
                         placeholder="选择月">
                <el-option v-for="item in monthOptions"
                           :value="item.value"
                           :label="item.label"
                           :key="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label=""
                          prop="target_item_name">
              <el-input v-model="query.target_item_name"
                        placeholder="请输入指标名称" />
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <div class="el-form-item__content"
                 style="margin-left: 0px;">
              <el-button v-hasPermi="['getPageProductionRealOutputMonth']"
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
    <!-- 上部 -->
    <div style="height:33vh">
      <el-form>
        <el-table height="33vh"
                  size="small"
                  v-loading="table.loading"
                  :data="tableData"
                  stripe>
          <!-- <template v-for="column in columns">
            <el-table-column v-if="column.customStyle"
                             :key="column.prop"
                             :prop="column.prop"
                             :label="column.label"
                             :type="column.type"
                             :width="column.width"
                             :min-width="column.minWidth"
                             :sortable="column.sortable">
              <template slot-scope="scope">
                <div v-html="column.customStyle(scope.row,scope.$index,scope)"></div>
              </template>
            </el-table-column>
            <el-table-column v-else
                             :key="column.prop"
                             :prop="column.prop"
                             :label="column.label"
                             :type="column.type"
                             :width="column.width"
                             :min-width="column.minWidth"
                             :sortable="column.sortable"
                             :formatter="column.formatter" />
          </template> -->
          <el-table-column v-for="column in columns"
                           :key="column.prop"
                           :prop="column.prop"
                           :label="column.label"
                           :type="column.type"
                           :width="column.width"
                           :min-width="column.minWidth"
                           :sortable="column.sortable"
                           :formatter="column.formatter">
          </el-table-column>
        </el-table>
        <div style="margin-top: 10px;right: 0;padding: 0;"
             class="avue-crud__pagination">
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
    <!-- echart -->
    <div style="height:40vh;padding-top: 6vh">
      <el-tabs v-model="activeName"
               @tab-click="handleClick">
        <!-- <template v-for="item in materialList">
          <el-tab-pane :label="item.targetItemName"
                       :name="item.materialId"
                       :key="item.id">
            <templateLine :char="item.materialId"
                          :charId="item.materialId"
                          :query="query"
                          :planData="planData"
                          :actualData="actualData"
                          :xAxisData="xAxisData" />
          </el-tab-pane>
        </template> -->
        <el-tab-pane v-for="item in materialList"
                     :label="item.targetItemName+`(%)`"
                     :name="item.id"
                     :key="item.id">
          <templateLine :char="item.materialId"
                        :charId="item.id"
                        :query="query"
                        :planData="planData"
                        :actualData="actualData"
                        :xAxisData="xAxisData" />
        </el-tab-pane>
        <!-- <el-tab-pane label="全焦产率(%)"
                     name="tab1">
          <templateLine :char="charId"
                        :charId="'tab1'"
                        :query="query"
                        :planData="planData"
                        :actualData="actualData"
                        :xAxisData="xAxisData" />
        </el-tab-pane> -->

        <el-tab-pane label="同期比"
                     name="tab11">
          <templateBar :char="charId"
                       :charId="'tab11'"
                       :text="text"
                       :frontData="frontData"
                       :currentData="currentData"
                       :legend="legend"
                       :xAxisData="xAxisData" />
        </el-tab-pane>
        <el-tab-pane label="环期比"
                     name="tab12">
          <templateBar :char="charId"
                       :charId="'tab12'"
                       :text="text"
                       :frontData="frontData"
                       :currentData="currentData"
                       :legend="legend"
                       :xAxisData="xAxisData" />
        </el-tab-pane>
      </el-tabs>

    </div>

  </div>
</template>

<script>
import { getDays } from '@/utils/date'
import templateLine from "./templateLine";
import templateBar from "./templateBar";
import moment from 'moment'
import {
  getPageProductionRealOutputMonth,
  getPageProductionRealOutputDate,
  getProductionRealOutputMonthTargetItemChart,
  getProductionPlanOutputMonthTargetItemChart,
  getProductionRealOutputDateTargetItemChart,
  getProductionPlanOutputDateTargetItemChart,
  getProductionRealOutputMonthRangeChart,
  getList
} from "@/api/production/oi/statistics";
export default {
  name: "indexAnalysis",
  components: {
    templateLine,
    templateBar,
  },
  data () {
    return {
      monthOptions: [
        { value: '01', label: '1月' },
        { value: '02', label: '2月' },
        { value: '03', label: '3月' },
        { value: '04', label: '4月' },
        { value: '05', label: '5月' },
        { value: '06', label: '6月' },
        { value: '07', label: '7月' },
        { value: '08', label: '8月' },
        { value: '09', label: '9月' },
        { value: '10', label: '10月' },
        { value: '11', label: '11月' },
        { value: '12', label: '12月' },
      ],
      page: {
        size: 20,
        current: 1,
        total: 1,
        order: "month",
        orderby: "asc",
      },
      query: {
        year: moment().format('YYYY'),
        month: 0,
        target_item_name: ''
      },
      table: {
        border: true,
        loading: false,
      },
      columns: [
        { label: '序号', type: 'index', width: '80px' },
        // { label: '计划编号', prop: "aaaaaa", sortable: true, minWidth: '90px' },
        {
          label: '日期', prop: "year", formatter (row) {
            return row.date ? `${row.year}-${row.month}-${row.date}` : `${row.year}-${row.month}`
          }
        },
        { label: '指标项名', prop: "target_item_name", minWidth: '90px' },
        { label: '计划指标值(%)', prop: "target_item_output", minWidth: '90px' },
        { label: '实绩指标值(%)', prop: "target_item_output_real", minWidth: '90px' },
        { label: '去年平均(%)', prop: "average_last_year", minWidth: '90px' },
        { label: '去年同期(%)', prop: "same_time_last_year", minWidth: '90px' },
        { label: '历史最大(%)', prop: "historical_maximum", minWidth: '90px' },
        { label: '历史最小(%)', prop: "historical_minimum", minWidth: '90px' },
        // { label: '责任人', prop: "aaaaaa", sortable: true, minWidth: '90px' },
      ],
      materialList: [],
      tableData: [],
      activeName: '',
      charId: '',
      year: '',
      month: '', //区分显示和传入的月份 （显示）
      planData: [],
      actualData: [],
      frontData: [],
      currentData: [],
      legend: [],
      text: '',
      xAxisData: [],
      // targetItemName: '全焦率1',
      targetItemName: '',
      previousYear: '',
      previousMonth: '',
      presentMonth: '',
      // activeName: '',
    }
  },
  created () {
    this.onLoad();
    // this.year = moment().format('YYYY');
    // this.month = moment().format('MM');
  },
  methods: {
    //载入数据
    onLoad () {
      // if (!this.query.month) {
      //   this.query.month = 0
      // }

      //从新赋值月份
      if (this.month === '') {
        this.query.month = 0
        this.page.order = 'month'
        getPageProductionRealOutputMonth(this.page, this.query).then(res => {
          this.table.loading = false;
          let data = res.data.data;//表格相关数据
          this.page.total = data.total;//数据总数
          this.tableData = data.records;//表格数据
        }, error => {
          this.table.loading = false;
          window.console.log(error);
        });
      } else {
        this.query.month = this.month
        this.page.order = 'date'
        getPageProductionRealOutputDate(this.page, this.query).then(res => {
          this.table.loading = false;
          let data = res.data.data;//表格相关数据
          this.page.total = data.total;//数据总数
          this.tableData = data.records;//表格数据
        }, error => {
          this.table.loading = false;
          window.console.log(error);
        });
      }
      this.table.loading = true;//加载状态
      let save = {
        "targetItemTypeId": 1,
        "size": 10000,
        "current": 1,
        "total": 1,
        "order": "create_time",
        "orderby": "asc",
      }
      getList(save).then(response => {
        console.log(response, "response")
        this.materialList = response.data.data;
        // this.activeName = response.data.data[2].materialId;
        this.activeName = response.data.data[0].id;
        this.targetItemName = response.data.data[0].targetItemName;
        // getPageProductionRealOutputMonth(this.page, this.query).then(res => {
        //   this.table.loading = false;
        //   let data = res.data.data;//表格相关数据
        //   this.page.total = data.total;//数据总数
        //   this.tableData = data.records;//表格数据
        // }, error => {
        //   this.table.loading = false;
        //   window.console.log(error);
        // });
        this.getChartData()
      })
    },
    //获取计划实际图表数据
    getChartData () {
      this.xAxisData = []
      this.planData = []
      this.actualData = []
      if (this.query.month) {
        let Days = getDays(this.query.year, Number(this.query.month))
        // console.log(Days, "Days")
        let actualDay = []
        for (let i = 0; i < Days; i++) {
          actualDay[i] = 0
        }
        // console.log(actualDay)
        let params = {
          year: this.query.year,
          month: this.query.month,
          target_item_name: this.targetItemName
        }
        getProductionPlanOutputDateTargetItemChart(params).then(res => {
          // console.log(res, '日计划')
          res.data.forEach(item => {
            this.planData.push(item.target_item_output)
            this.xAxisData.push(item.date+'日')
          })
        })
        getProductionRealOutputDateTargetItemChart(params).then(res => {
          // console.log(res, '日实际')
          res.data.forEach(item => {
            // this.actualData.push(item.target_item_output)
            if (item.target_item_output) {
              actualDay[(item.date - 1)] = item.target_item_output
            }
          })
          // console.log(actualDay, "actualDay")
          this.actualData = actualDay
        })
      } else {
        let actual = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        let params = {
          year: this.query.year,
          target_item_name: this.targetItemName
        }
        getProductionPlanOutputMonthTargetItemChart(params).then(res => {
          // console.log(res.data, '月计划')
          res.data.forEach(item => {
            this.planData.push(item.target_item_output)
            this.xAxisData.push(item.month + '月')
          })
          // console.log(this.planData, this.xAxisData, 'this.planData.')
        })
        getProductionRealOutputMonthTargetItemChart(params).then(res => {
          // console.log(res, '月实际')
          res.data.forEach(item => {
            if (item.target_item_output) {
              actual[(item.month - 1)] = item.target_item_output
            }
            // this.actualData.push(item.target_item_output)
          })
          // console.log(actual, "actual")
          this.actualData = actual
        })
        // console.log('月的数据')
      }
    },
    handleClick (data) {
      // console.log(data.label)
      //截取字符串 获取target_item_name
      let labelStr = data.label
      let index = labelStr.indexOf("(");
      if (index != -1) {
        this.targetItemName = labelStr.substring(0, index);
      } else {
        this.targetItemName = labelStr
      }
      //上一年
      this.previousYear = (this.query.year - 1) + ''
      //上个月
      if (this.query.month) {
        this.previousMonth = (this.query.month == '01' ? 12 : this.query.month - 1) + ''
      } else {
        this.previousMonth = (moment().format('MM') == '01' ? 12 : moment().format('MM') - 1) + ''
      }
      //当前月或者选中的月
      this.presentMonth = this.query.month ? this.query.month : moment().format('MM')
      //点前年或者选中的年
      this.presentYear = this.query.month == '01' ? this.previousYear : this.query.year
      //判断点击的是否是同期比和环期比
      if (this.activeName == 'tab11') {
        this.xAxisData = []
        this.currentData = []
        this.frontData = []
        this.text = this.presentMonth + '月';
        this.legend = [this.previousYear, this.query.year]
        //上一年的
        let previousParams = {
          start_year: this.previousYear,
          end_year: this.previousYear,
          start_month: this.presentMonth,
          end_month: this.presentMonth
        }
        getProductionRealOutputMonthRangeChart(previousParams).then(res => {
          console.log(res, '上一年同期比')
          res.data.forEach(item => {
            this.frontData.push(item.target_item_output)
            this.xAxisData.push(item.target_item_name)
          })
        })
        //当前年
        let params = {
          start_year: this.query.year,
          end_year: this.query.year,
          start_month: this.presentMonth,
          end_month: this.presentMonth,
        }
        getProductionRealOutputMonthRangeChart(params).then(res => {
          console.log(res, '同期比')
          res.data.forEach(item => {
            this.currentData.push(item.target_item_output)
          })
        })
      } else if (this.activeName == 'tab12') {
        this.xAxisData = []
        this.currentData = []
        this.frontData = []
        this.text = ''
        this.legend = [this.presentYear + '-' + this.previousMonth, this.query.year + '-' + this.presentMonth]
        //上个月
        let previousYearParams = {
          start_year: this.presentYear,
          end_year: this.presentYear,
          start_month: this.previousMonth,
          end_month: this.previousMonth
        }
        getProductionRealOutputMonthRangeChart(previousYearParams).then(res => {
          console.log(res, '上个月环期比')
          res.data.forEach(item => {
            this.frontData.push(item.target_item_output)
            this.xAxisData.push(item.target_item_name)
          })
        })
        //当前月
        let params = {
          start_year: this.query.year,
          end_year: this.query.year,
          start_month: this.query.month ? this.query.month : moment().format('MM'),
          end_month: this.query.month ? this.query.month : moment().format('MM')
        }
        getProductionRealOutputMonthRangeChart(params).then(res => {
          console.log(res, '环期比')
          res.data.forEach(item => {
            this.currentData.push(item.target_item_output)
          })
        })
      } else {
        this.getChartData()
      }
    },
    // 分页-每页多少条
    handleSizeChange (val) {
      this.page.size = val;
      this.onLoad();
    },
    // 分页-当前页
    handleCurrentChange (val) {
      this.page.current = val;
      this.onLoad();
    },
    // 排序
    handleSort (column) {
      if (column.order === null) {
        //默认
        this.page.order = this.page.order;
        this.page.orderby = "desc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.onLoad();
    },
    //查询
    handleQuery () {
      // this.activeName = 'tab1'
      // this.targetItemName = '全焦率'
      this.onLoad();
    },
    // 清空
    handleEmpty () {
      this.month = ''
      this.page.current = 1
      this.$refs["query"].resetFields()
      this.handleQuery()
    },
  },
  // watch: {
  //   activeName (val) {
  //     this.charId = '-' + new Date().getTime();
  //     this.planData = [];
  //     this.actualData = [];
  //     this.frontData = [];
  //     this.currentData = [];
  //     if (val === 'tab11') {
  //       for (let i = 0; i < 8; i++) {
  //         this.frontData.push(Math.floor(Math.random() * 1000));
  //         this.currentData.push(Math.floor(Math.random() * 1000));
  //       }
  //       this.text = this.month + '月';
  //       this.legend = ['2021', '2022'];
  //       this.xAxisData = ['全焦产率', '冶金焦率', '焦末收率', '焦油收率', '粗苯收率', '硫铵收率', '硫酸收率', '煤气收率'];
  //     } else if (val === 'tab12') {
  //       for (let i = 0; i < 8; i++) {
  //         this.frontData.push(Math.floor(Math.random() * 1000));
  //         this.currentData.push(Math.floor(Math.random() * 1000));
  //       }
  //       this.text = this.year + '年';
  //       this.legend = ['2022-06', '2022-07'];
  //       this.xAxisData = ['全焦产率', '冶金焦率', '焦末收率', '焦油收率', '粗苯收率', '硫铵收率', '硫酸收率', '煤气收率'];
  //     } else {
  //       for (let i = 0; i < 12; i++) {
  //         this.planData.push(Math.floor(Math.random() * 1000));
  //         this.actualData.push(Math.floor(Math.random() * 1000));
  //       }
  //     }
  //   }
  // }
}
</script>

<style scoped>
.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}

.el-card__body {
  padding: 10px 10px 20px 20px;
}
</style>
