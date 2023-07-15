<template>
  <div style="position: relative;height:38vh ;">
    <div :id="charId"
         style="position: absolute ; left:2% ; top: 2% ; right: 2%;height:32vh;width:80vw" />
  </div>
</template>

<script>
export default {
  name: "YieldAnalysisBar",
  props: ['refresh', 'charId', 'text', 'yieldData', 'legend'],
  data () {
    return {
      myChart: null,
      option: null,
    }
  },
  mounted () {
    this.initEcharts();
  },
  methods: {
    initEcharts () {
      this.myChart = this.$echarts.init(document.getElementById(this.charId))
      let planData = [];
      let performance = [];
      let xAxisData = [];
      if (this.yieldData && this.yieldData.length > 1) {
        for (let i = 0; i < this.yieldData.length; i++) {
          let item = this.yieldData[i];
          planData.push(item.plan === "" ? 0 : (item.plan / 1000).toFixed(3));
          performance.push(item.performance === "" ? 0 : (item.performance / 1000).toFixed(3));
          xAxisData.push(item.xAxisText);
        }
      }

      this.option = {
        title: {
          text: this.text,
        },
        grid: { left: '2%', right: '3%', bottom: '2%', containLabel: true },
        legend: { data: this.legend },
        xAxis: { type: 'category', data: xAxisData },
        yAxis: {},
        tooltip: {
          trigger: 'axis', axisPointer: { type: 'shadow' },
          // formatter: function (params) {
          //   if (params) {
          //     if (params.length === 1) {
          //       return params[0].name + '<br />'
          //         + params[0].marker + params[0].seriesName + ": " + planData[params[0].dataIndex]
          //     } else {
          //       return params[0].name + '<br />'
          //         + params[0].marker + params[0].seriesName + ": " + planData[params[0].dataIndex] + '<br />'
          //         + params[1].marker + params[1].seriesName + ": " + performance[params[1].dataIndex];
          //     }
          //   }
          // }
        },
        // series: [
        //   {
        //     name: this.legend[0],
        //     data: planData,
        //     type: 'bar',
        //     itemStyle: { normal: { color: '#37a2da' } }
        //   },
        //   {
        //     name: this.legend[1],
        //     data: performance,
        //     type: 'bar',
        //     itemStyle: { normal: { color: '#67e0e3' } }
        //   }
        // ]
        series: this.legend.length == 1 ? [
          {
            name: this.legend[0],
            data: performance,
            type: 'bar',
            itemStyle: { normal: { color: '#67e0e3' } }
          }
        ] : [
          {
            name: this.legend[0],
            data: planData,
            type: 'bar',
            itemStyle: { normal: { color: '#37a2da' } }
          },
          {
            name: this.legend[1],
            data: performance,
            type: 'bar',
            itemStyle: { normal: { color: '#67e0e3' } }
          }
        ]
      };
      this.myChart.setOption(this.option, true);
      // window.addEventListener("resize", () => {
      //   this.myChart.resize()
      // })
    },
  },

  beforeDestroy () {
    this.myChart = null;
  },
  watch: {
    yieldData () {
      this.myChart = null;
      this.initEcharts();
    },
    refresh () {
      this.myChart.dispose();
      this.initEcharts();
    }
  },
}
</script>

<style scoped>
</style>
