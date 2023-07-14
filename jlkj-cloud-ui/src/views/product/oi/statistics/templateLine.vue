<template>
  <div style="position: relative;height:33vh;">
    <div :id="charId"
         style="position: absolute ; left:2% ; top: 2% ; right: 2%;height:32vh;width:80vw" />
  </div>
</template>

<script>
export default {
  name: "templateLine",
  props: ['char', 'charId', 'query', 'planData', 'actualData', 'xAxisData'],
  data () {
    return {
      myChart: null,
      option: null,
      // xAxisData: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
    }
  },
  mounted () {
    this.initEcharts();
  },
  watch: {
    planData () {
      this.initEcharts();
    },
    actualData () {
      this.initEcharts();
    }
  },
  methods: {
    initEcharts () {
      this.myChart = this.$echarts.init(document.getElementById(this.charId));
      let planData = this.planData;
      let actualData = this.actualData;
      let text = '';
      if (this.query.month) {
        text = this.query.year + '-' + this.query.month;
      } else {
        text = this.query.year;
      }
      this.option = {
        title: { text: text, },
        tooltip: { trigger: 'axis' },
        legend: {
          data: ['计划', '实际']
        },
        grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: {
          type: 'category',
          data: this.xAxisData
        },
        yAxis: { type: 'value' },
        series: [
          { name: '计划', type: 'line', data: planData },
          { name: '实际', type: 'line', data: actualData },
        ]
      };
      this.myChart.setOption(this.option);
    },
  }
}
</script>

<style scoped>
</style>
