<template>
  <div class="main">
    <!--表单筛选 -->
    <div>
      <el-row>
        <el-col :span="20">
          <el-form ref="form"
                   :inline="true"
                   class="opetions1 ">
            <el-form-item label="年">
              <el-date-picker v-model="query.year"
                              type="year"
                              placeholder="选择年"
                              value-format="yyyy"
                              @change="">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="月">
              <el-date-picker v-model="query.month"
                              type="month"
                              placeholder="选择月"
                              format="MM"
                              value-format="MM">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="作业票类型">
              <el-select placeholder="作业票类型"
                         v-model="query.type"
                         clearable>
                <el-option v-for="item in options"
                           :key="item.value"
                           :label="item.label"
                           :value="item.label">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="作业区">
              <el-select placeholder="作业区"
                         clearable
                         v-model="query.areaId"
                         class="customSelectStyle"
                         :popper-append-to-body="false">
                <el-option v-for="item in safe_area_name"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button v-hasPermi="['safeAnalysis_list']"
                         type="primary"
                         icon="el-icon-search"
                         size="mini"
                         style="margin-left: 20px"
                         @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh-left"
                         size="mini"
                         @click="clear()">重置</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>

    </div>
    <!-- 表格 -->
    <div style="margin-left: 20px; padding-right: 0px;">
      <el-table v-loading="table.loading"
                ref="multipleTable"
                height="30vh"
                border
                size="small"
                :data="tableData"
                :stripe="true"
                @sort-change="handleSort"
                :header-cell-style="{
          'background-color': '#FAFAFA',
          'color': 'black',
          'font-weight': '700px'
        }">
        <el-table-column label="日期"
                         minWidth="200"
                         align="center"
                         :stripe="true"
                         prop="datetime"
                         sortable />
        <el-table-column label="作业区"
                         minWidth="150"
                         align="center"
                         prop="safe_area_name" />
        <el-table-column label="作业票类型"
                         minWidth="150"
                         align="center"
                         prop="type" />
        <el-table-column label="资料建立"
                         minWidth="150"
                         align="center"
                         prop="prof" />
        <el-table-column label="审批中"
                         minWidth="200"
                         align="center"
                         prop="appr" />
        <el-table-column label="作业中"
                         minWidth="150"
                         align="center"
                         prop="working" />
        <el-table-column label="已验收"
                         minWidth="150"
                         align="center"
                         prop="done" />
      </el-table>
      <div style="margin-top: 10px;">
        <el-row>

            <el-pagination style="margin-top: 10px; float: right;padding: 0;"
                           class="avue-crud__pagination"
                           background
                           @size-change="handleSizeChange"
                           @current-change="handleCurrentChange"
                           :current-page="page.current"
                           :page-sizes="[20, 50, 100, 500]"
                           :page-size="100"
                           layout=" prev, pager, next,sizes"
                           :total="page.total">
            </el-pagination>
        </el-row>
      </div>
    </div>
    <!-- 饼图和折线图联动 -->
    <div>
      <div class="mm"
           id="chart"></div>
    </div>
  </div>
</template>
<script>
import { arealist } from "@/api/security/si/parameter/operationArea";
import { getSafetyOperationAnalysis, getSafetyOperationAnalysisLine } from "@/api/security/si/StatisticalAnalysis/SafetyOperationAnalysis";
import { mapGetters } from "vuex";

export default {
  name: "SafeWorkMain",
  data () {
    return {
      option: {
        xAxis: {
          type: 'category',
          data: ['2022-01', '2022-02', '2022-03', '2022-04', ' 2022-05', '2022-06', '2022-07', '2022-08', '2022-09', '2022-10', '2022-11', '2022-12']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [150, 230, 224, 218, 135, 147, 260],
            type: 'line'
          },
        ]
      },
      options: [
        { value: "type1", label: "吊装作业票" },
        { value: "type2", label: "动火作业票" },
        { value: "type3", label: "高处作业票" },
        { value: "type4", label: "有限空间作业票" },
        { value: "type5", label: "动土作业票" },
        { value: "type6", label: "抽堵盲板作业票" },
        { value: "type7", label: "断路作业票" },
        { value: "type8", label: "临时用电作业票" },
      ],
      page: {
        size: 20,
        current: 1,
        total: 100,
        order: "datetime",
        orderby: "desc",
        yearmonth: '',
        type: ' '
      },
      formLabelWidth: '120px',
      // 筛选表单数据
      query: {
        year: '',
        month: '',
        type: '',
        areaId: ''
      },
      // 表格数据存储
      tableData: [],
      table: {
        loading: true,
      },
      arrLinePie: [],
      page1: {
        size: 2000,
        current: 1,
        total: 1,
        order: "ceate_time",
        orderby: "desc",
      },
      query1: {
        safe_area_name: null
      },
      //获取存储作业区
      safe_area_name: [],
    }
  },
  created () {
    this.getNowYear();
  },
  mounted () {
    this.getSafetyOperationAnalysis();
  },
  computed: {
    ...mapGetters(["userInfo"])
  },
  methods: {
    //加载数据
    getNowYear () {
      this.query.year = new Date().getFullYear().toString();
    },
    getSafetyOperationAnalysis () {

      if (!this.query.month) {
        this.query.month = ''
      }

      arealist(this.page1, this.query1).then(response => {
        // console.log(response);
        let data = response.data.data.records;
        let options = []
        data.map(item => {
          let i = {
            id: item.id,
            name: item.safe_area_name,
          }
          options.push(i)
        });
        this.safe_area_name = options

      });
      let query = this.query
      let page = this.page;
      let params = {
        "type": query.type,
        "size": page.size,
        "current": page.current,
        "order": page.order,
        "orderby": page.orderby,
        // "order": "datetime",
        // "orderby": "desc",
        "areaId": query.areaId,
        "yearmonth": !this.query.month ? this.query.year : this.query.year + '-' + this.query.month,
        // "yearmonth":query.year||query.year+""+query.month,
      }
      console.log(params)
      this.table.loading = true;
      getSafetyOperationAnalysis(params).then(response => {
        // console.log(response);
        this.table.loading = false;
        this.tableData = response.data.records;
        this.page.total = response.data.total;

      }, error => {
        this.table.loading = false;
        window.console.log(error);
      })
      // 查询折线图数据接口搜索条件是日期传的参数
      let paramsa = {
        "areaId": query.areaId,
        "type": query.type,
        // "yearmonth": !this.query.month ? this.query.year : this.query.year + '-' + this.query.month,
        "yearmonth": query.year

      }
      getSafetyOperationAnalysisLine(paramsa).then(response => {
        // console.log(response);
        this.arrLinePie = []
        response.data.map(i => {
          if (this.query.month === '') {
            this.arrLinePie.push([i.name, ...i.data])
          } else {
            this.arrLinePie.push([i.name, i.data[Number(this.query.month) - 1]])
          }
        })
        // console.log(response)
        // console.log(this.arrLinePie);
        this.initChart()
      }, error => {
        this.table.loading = false;
        window.console.log(error);
      })
    },
    // 重置
    clear () {
      this.query = {
        year: '',
        month: '',
        type: '',
        areaId: ''
      }
      this.page.current = 1
      this.getNowYear()
      this.getSafetyOperationAnalysis()

    },

    //分页-每页多少条
    handleSizeChange (val) {
      this.page.size = val;
      this.handleQuery()
    },
    // 分页-当前页
    handleCurrentChange (val) {
      this.page.current = val;
      this.handleQuery()
    },
    // 排序
    handleSort (column) {
      // console.log(column, "column")
      if (column.order === null) {
        //默认
        this.page.order = "datetime";
        this.page.orderby = "desc";
      } else {
        //选中项
        this.page.order = column.prop;
        this.page.orderby = column.order === 'ascending' ? 'asc' : 'desc';
      }
      this.getSafetyOperationAnalysis()
    },
    //查询
    handleQuery () {
      this.page.current = 1;
      this.getSafetyOperationAnalysis()
    },
    initChart () {
      // console.log(this.arrLinePie)
      let date = ['product']

      if (this.query.year && !this.query.month) {
        date.push(
          this.query.year + '-' + '01',
          this.query.year + '-' + '02',
          this.query.year + '-' + '03',
          this.query.year + '-' + '04',
          this.query.year + '-' + '05',
          this.query.year + '-' + '06',
          this.query.year + '-' + '07',
          this.query.year + '-' + '08',
          this.query.year + '-' + '09',
          this.query.year + '-' + '10',
          this.query.year + '-' + '11',
          this.query.year + '-' + '12')
      }
      else if (this.query.year && this.query.month) {
        date.push(this.query.year + '-' + this.query.month)
      }
      let source = [date, ...this.arrLinePie]
      let chart = this.$echarts.init(document.getElementById("chart"))
      // 渲染图表
      let option = {
        legend: {},
        color: ['#fc8251', '#5470c6', '#91cd77', '#ef6567', '#f9c956', '#75bedc', '#4D4DFF', ' #00FF7F'],
        dataset: {
          source
        },
        tooltip: {
          trigger: 'axis', axisPointer: { type: 'line' },
        },
        xAxis: { type: 'category' },
        yAxis: { gridIndex: 0 },
        grid: { top: '50%' },
        series: [
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          }, {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },

          {
            type: 'pie',
            id: 'pie',
            radius: '30%',
            center: ['50%', '30%'],
            emplasis: {
              focus: 'self',

            },
            label: {
              formatter: '{b}: {@[' + 1 + ']}({d}%)'
            },
            encode: {
              itemName: 'product',
              value: !this.query.month ? this.query.year + '-' + "01" : this.query.year + '-' + this.query.month,

            }
          }
        ]
      };
      chart.setOption(option)

      // 联动效果
      chart.on('updateAxisPointer', function (event) {

        const xAxisInfo = event.axesInfo[0];

        if (xAxisInfo) {
          const dimension = xAxisInfo.value + 1;
          chart.setOption({
            series: {
              id: 'pie',
              label: {
                formatter: '{b}: {@[' + dimension + ']} ({d}%)'
              },
              encode: {
                value: dimension,
                tooltip: dimension
              }
            }
          });
        }
      });
      // 图表自动改变宽高
      window.addEventListener("resize", () => {
        chart.resize()
      })

    }

  },
}
</script>
<style lang="less" scoped>
.mm {
  height: 400px;
  width: 100%;
}

.main {
  height: calc(100vh - 10px);
  background-color: #fff;
}

.welMain {
  width: 98%;
  height: 100vh;
  background-color: #fff;
  margin-left: 10px;
}

.opetions1 {
  padding: 20px;
  margin-bottom: 40px;
}

.opetions1 {
  height: 0px;
}

.item .el-form-item__label {
  color: #999999;
  font-weight: 700;
}

element.style {
  height: 500px;
}
</style>
