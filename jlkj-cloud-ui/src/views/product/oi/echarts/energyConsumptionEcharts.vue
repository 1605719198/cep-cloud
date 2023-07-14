<template>
  <div style="width:100%;height:100%;">
    <div :id="chartId"
         :style="{width: '100%', height: '100%'}"></div>
  </div>
</template>

<script>
import { Handler } from 'leaflet'

export default {
  props: {
    chartId: {
      type: String,
      required: true,
      // default: 'myCharts1'
    },
    colorlist: {
      type: Array,
      default: () => {
        return ['#30dfef', '#2e78d7', '#0f1e40']
      }
    },
    chartData: {
      type: Object,
    }
  },
  data () {
    return {
      myChart: {}
    }
  },
  mounted () {
    this.drawLine()
  },
  watch: {
    chartData: {
      handler () {
        this.drawLine()
      },
      deep: true
    }
  },
  methods: {
    onresize () {
      this.myChart.resize()
    },
    drawLine () {
      this.myChart = this.$echarts.init(document.getElementById(this.chartId))
      // let myChart = this.$echarts.init(document.getElementById(this.chartId))
      var option = {
        //图表配置
        series: [
          {
            type: 'gauge',
            startAngle: 200,
            endAngle: -20,
            radius: "100%",
            progress: {
              show: true,
              width: 15,
              itemStyle: {
                color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: this.colorlist[0] },
                  { offset: 1, color: this.colorlist[1] }
                ]),
              },
            },
            axisLine: {
              lineStyle: {
                width: 15,
                color: [[1, this.colorlist[2]]]
              }
            },
            axisTick: {
              show: false
            },
            pointer: { //指针
              icon: 'none'
            },
            splitLine: {
              show: false,
            },
            axisLabel: {
              show: false,
            },
            detail: {
              offsetCenter: [0, 0],
              color: '#fff',
            },
            title: {
              offsetCenter: [0, '60%'],
              color: "#708acc"
            },
            data: [this.chartData]
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      this.myChart.setOption(option);
    }
  }
}
</script>

<style lang="less" scoped>
</style>