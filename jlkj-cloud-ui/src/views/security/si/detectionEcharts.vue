<template>
  <div style="height:100%">
    <div id="myChart3" :style="{width: '100%', height: '100%'}"></div>
  </div>
</template>

<script>
export default {
  props: {
    data: {type: Array, default: () => []},
    xAxis: {type: Array, default: () => []},
  },
  mounted () {
    this.drawLine()
  },
  watch: {
    data() {
      this.drawLine();
    },
  },
  methods: {
    onresize () {
      this.myChart.resize()
    },
    drawLine () {
      this.myChart = this.$echarts.init(document.getElementById('myChart3'))
      // let myChart = this.$echarts.init(document.getElementById('myChart3'))
      var option = {
        tooltip: {
          trigger: 'item'
        },
        grid: {
          top: "20%",
          bottom: "20%"
        },
        xAxis: {
          type: 'category',
          data: this.xAxis,
          axisLine: {//横向坐标线的样式
            lineStyle: {
              color: "#64eef8"
            }
          },
        },
        yAxis: {
          type: 'value',
          splitLine: { show: false }, //背景网格
          axisLine: { //纵向坐标线的样式
            lineStyle: {
              color: "#64eef8"
            }
          },
        },
        series: [
          {
            data: this.data,
            type: 'line',
            itemStyle: {
              color: "#64eef8"
            }
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      this.myChart.setOption(option);
    }
  }
}
</script>

<style>
</style>
