<template>
  <div style="width:100%;height:100%;">
    <div :id="chartId"
         :style="{width: '100%', height: '100%'}"></div>
  </div>
</template>

<script>
export default {
  props: {
    chartId: {
      type: String,
      required: true,
      // default: 'myCharts'
    },
    turbineData: {
      type: Array,
      default: () => {
        return []
      }
    },
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
    turbineData () {
      this.drawLine()
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
            min: 0,
            max: 120,
            splitNumber: 6,
            // center: ['50%', '50%'],
            radius: "100%",
            axisLine: {
              lineStyle: {
                // width: 6,
                color: [
                  [0.17, '#4fb78e'],
                  [0.83, '#ead2a5'],
                  [1, '#fc2273']
                ]
              }
            },
            splitLine: { //分隔线样式
              lineStyle: {
                color: "#37cbe0"
              },
              distance: 1
            },
            axisTick: { //刻度样式
              lineStyle: {
                color: "#37cbe0"
              },
              distance: 1
            },
            axisLabel: { //刻度标签
              color: "#37cbe0"
            },
            pointer: { //指针

            },
            detail: { //仪表盘详情，用于显示数据。
              color: "#fff",
              fontSize: 20,
            },
            title: {
              offsetCenter: [0, '75%'],
              color: "#7ab0f7"
            },
            // data: [
            //   {
            //     value: 60,
            //     name: '1#气耗'
            //   }
            // ]
            data: this.turbineData
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