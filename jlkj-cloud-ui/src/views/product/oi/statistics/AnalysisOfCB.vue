<template>
  <div class="avue-crud el-card__body" style="width: 98%;border: 0 ; background-color: #FFFFFF">
    <div class="avue-crud__search">
      <el-form>
        <el-row :gutter="20">
          <el-col :span="2" v-if="!isAll">
            <el-button @click="handleBack" type="default" icon="el-icon-caret-left" size="medium">后 退</el-button>
          </el-col>
          <template v-else>
            <el-col :span="3">
              <el-form-item prop="year">
                <el-date-picker v-model="year" type="year" placeholder="选择年" format="yyyy" value-format="yyyy"/>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item>
                <el-button @click="handleQuery" type="primary" icon="el-icon-search" size="medium">搜 索</el-button>
              </el-form-item>
            </el-col>
          </template>
        </el-row>
      </el-form>
    </div>
    <!-- 图标数据栏 -->
    <div style="height:75vh;" v-show="isAll">
      <div class="echart" id="mychart" :style="myChartStyle"/>
    </div>
    <div style="height:75vh;" v-show="!isAll">
      <div class="echart" id="mychart2" :style="myChartStyle"/>
    </div>
  </div>
</template>
<script>
  import {getConfigureCoalSpeciesAnalysisStatistics} from '@/api/production/oi/actual/coal/ConfigureCoalSpeciesPerformanceApi'

  export default {
    components: {},
    name: "AnalysisOfCB",
    data() {
      return {
        year: '',
        data: null,
        isAll: true,
        myChartStyle: {float: "left", width: "100%", height: "80vh"}, //图表样式
        xData: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"], //横坐标
        color: ['#5470c6', '#91cc75', '#fac858', '#ee6666', '#73c0de', '#3ba272', '#fc8452', '#9a60b4', '#ea7ccc', '#CCFFFF', '#CCCCFF', '#CCCCCC', '#666699', '#99CCFF', '#333399', '#6666CC', '#FF9999', '#9999FF', '#FFCC33', '#FFFFCC', '#0099CC', '#0000FF', '#6699FF', '#6666FF', '#66CCFF', '#CCFF66', '#669999', '#9999CC', '#003399', '#FFCCCC', '#6699CC', '#CC6666', '#CCCC00', '#000066', '#CCFF99', '#333333', '#333366', '#CC0033', '#336699', '#CCCC66', '#333300', '#FFFF99', '#000000', '#990066', '#CCCC33', '#CC3333', '#999966'],
        month: [],
      }
    },
    mounted() {
      this.onload();
    },
    methods: {
      onload() {
        getConfigureCoalSpeciesAnalysisStatistics({year: this.year}).then(res => {
          let data = res.data.data;
          let month = data.plan.map(item => Number(item.month));
          month = month.filter((item, index) => month.indexOf(item) === index);
          this.month = month;
          this.data = res.data.data;
          if (data.plan.length > 0) {
            this.initCharts(data);
          } else {
            this.$message({type: "error", message: "未能获取到" + this.year + "年度的数据！"});

          }
        }, error => {
          window.console.log(error);
        })
      },
      initCharts(data) {
        let series = [];
        let plan = data.plan;
        let performance = data.performance;
        data.material.forEach(material_name => {
          let planData = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0];
          plan.forEach(item => {
            if (material_name === item.material_name) {
              let month = parseInt(item.month);
              planData[month - 1] = (Number(planData[month - 1]) * 1000000 + Number(item.weight) * 1000000) / 1000000;
            }
          });
          series.push({name: material_name, type: 'bar', stack: 'plan', data: planData});
          let performanceData = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0];
          performance.forEach(item => {
            if (material_name === item.material_name) {
              let month = parseInt(item.month);
              performanceData[month - 1] = (Number(performanceData[month - 1]) * 1000000 + Number(item.weight) * 1000000) / 1000000;
            }
          });
          series.push({name: material_name, type: 'bar', stack: 'performance', data: performanceData});
        });

        if (!this.chartDom) {
          this.chartDom = document.getElementById('mychart');
          this.myChart = this.$echarts.init(this.chartDom);
          // 多列柱状图
          this.option = {
            legend: {
              data: data.material,
              icon: 'circle',
              top: "92%"
            },
            tooltip: {
              trigger: 'axis',
              formatter: function (list) {
                let text = list[0].name + '';
                data.planNumber.forEach(plan_number => {
                  let planData = data.plan.filter(x => {
                    return x.plan_number === plan_number && list[0].dataIndex === Number(x.month) - 1;
                  });
                  let performanceData = data.performance.filter(x => {
                    return x.plan_number === plan_number && list[0].dataIndex === Number(x.month) - 1;
                  });
                  if (planData.length > 0) {
                    text = text + '<br/>【<b>计划编号：' + plan_number + '</b>】';
                    planData.forEach(d => {
                      let material_code = d.material_code;
                      // text = text + '<br/>【' + d.material_name + '】' + '计划: <b>' + d.weight + '</b>吨(<b style="color:red">' + d.proportion + '%</b>)';
                      text = text + '<br/>【' + d.material_name + '】' + '计划: <b style="color:red">' + d.proportion + '%</b>';
                      performanceData.forEach(dd => {
                        if (material_code === dd.material_code) {
                          text = text + '实绩: <b>' + dd.weight + '</b>吨(<b style="color:red">' + dd.proportion.toFixed(2) + '%</b>)';
                        }
                      });
                    });
                  }
                });


                return text;
              }
            },
            xAxis: {
              data: this.xData,
              splitArea: {show: false}
            },
            yAxis: {},
            grid: {
              bottom: 100
            },
            series: series,
            color: this.color,
          };
          this.myChart.setOption(this.option);
        } else {
          let option = this.myChart.getOption();
          option.series = series;
          this.myChart.setOption(option);
        }

        // this.myChart.on('click', (params) => {
        //   console.log(params)
        // })
        this.myChart.getZr().on('click', (params) => {
          let pointInPixel = [params.offsetX, params.offsetY];
          if (this.myChart.containPixel('grid', pointInPixel)) {
            let xIndex = this.myChart.convertFromPixel({seriesIndex: 0}, [params.offsetX, params.offsetY])[0];
            console.log(this.month)
            if (this.month.includes(xIndex + 1)) {
              this.$echarts.init(document.getElementById("mychart")).dispose();
              this.isAll = false;
              this.$nextTick(function () {
                this.initCharts2(xIndex);
                this.chartDom = null;
                this.myChart = null;
              });
            }
          }
        })


        //随着屏幕大小调节图表
        // window.addEventListener("resize", () => {
        //   this.myChart.resize();
        // });
      },
      //查询
      handleQuery() {
        this.onload();
      },
      //查询
      handleBack() {
        this.$echarts.init(document.getElementById("mychart2")).dispose();
        this.isAll = true;
        this.$nextTick(function () {
          this.onload();
          this.chartDom2 = null;
          this.myChart2 = null;
        });
      },

      initCharts2(index) {
        let data = this.data;
        let series = [];
        this.chartDom2 = document.getElementById('mychart2');
        this.myChart2 = this.$echarts.init(this.chartDom2);
        let plan = data.plan.filter(x => index + 1 === Number(x.month));
        let performance = data.performance.filter(x => index + 1 === Number(x.month));
        let material = plan.map(x => x.material_name);
        material = material.filter((item, index) => material.indexOf(item) === index);
        let planNumber = plan.map(x => x.plan_number);
        planNumber = planNumber.filter((item, index) => planNumber.indexOf(item) === index);
        planNumber.sort();

        material.forEach(material_name => {
          let planData = planNumber.map(x => 0.0);
          plan.forEach(item => {
            if (material_name === item.material_name) {
              let i = planNumber.indexOf(item.plan_number);
              planData[i] = item.weight;
            }
          });
          series.push({name: material_name, type: 'bar', stack: 'plan', data: planData});
          let performanceData = planNumber.map(x => 0.0);
          performance.forEach(item => {
            if (material_name === item.material_name) {
              let i = planNumber.indexOf(item.plan_number);
              performanceData[i] = item.weight;
            }
          });
          series.push({name: material_name, type: 'bar', stack: 'performance', data: performanceData});
        });
        // 多列柱状图
        this.option2 = {
          legend: {
            data: material,
            icon: 'circle',
            top: "92%"
          },
          tooltip: {
            trigger: 'axis',
            formatter: function (list) {
              let text = list[0].name + '';
              material.forEach(material_name => {
                plan.forEach(d => {
                  if (material_name === d.material_name && list[0].name === d.plan_number) {
                    text = text + '<br/>【' + d.material_name + '】' + '计划: <b style="color:red">' + d.proportion + '%</b>';
                  }
                });
                performance.forEach(dd => {
                  if (material_name === dd.material_name && list[0].name === dd.plan_number) {
                    text = text + '实绩: <b>' + dd.weight + '</b>吨(<b style="color:red">' + dd.proportion.toFixed(2) + '%</b>)';
                  }
                });
              });
              return text;
            }
          },
          xAxis: {
            data: planNumber,
            splitArea: {show: false}
          },
          yAxis: {},
          grid: {bottom: 100},
          series: series,
          color: this.color,
        };
        this.myChart2.setOption(this.option2);
      }
    },

  }
</script>

<style scoped>
</style>
