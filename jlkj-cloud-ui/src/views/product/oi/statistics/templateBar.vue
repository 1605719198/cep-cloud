<template>
  <div style="position: relative;height:35vh;">
    <div :id="charId"
         style="position: absolute ; left:2% ; top: 2% ; right: 2%;height:34vh;width:80vw" />
  </div>
</template>

<script>
export default {
  name: "templateBar",
  props: ['char', 'charId', 'text', 'frontData', 'currentData', 'legend', 'xAxisData'],
  data () {
    return {
      myChart: null,
      option: null,
    }
  },
  mounted () {
    this.initEcharts();
  },
  watch: {
    frontData () {
      this.initEcharts();
    },
    currentData () {
      this.initEcharts();
    }
  },
  methods: {
    initEcharts () {
      this.myChart = this.$echarts.init(document.getElementById(this.charId));

      let frontData = this.frontData;
      let currentData = this.currentData;

      // let d1 = [0, 0, 0, 0, 0, 0, 0, 0];
      // let d2 = [0, 0, 0, 0, 0, 0, 0, 0];

      // for (let i = 0; i < frontData.length; i++) {
      //   if (frontData[i] === 0 && currentData[i] === 0) {
      //     d1[i] = 0
      //   } else {
      //     d1[i] = frontData[i] / (frontData[i] + currentData[i]);
      //     d2[i] = currentData[i] / (frontData[i] + currentData[i]);
      //   }
      // }
      this.option = {
        title: { text: this.text },
        legend: { data: this.legend },
        xAxis: { type: 'category', data: this.xAxisData },
        yAxis: { type: 'value' },
        tooltip: {
          trigger: 'axis', axisPointer: { type: 'shadow' },
          formatter: function (params) {
            // console.log(params, 'params')
            return params[0].name + '<br />'
              // + params[0].marker + params[0].seriesName + ": " + frontData[params[0].dataIndex] + '<br />'
              // + params[1].marker + params[1].seriesName + ": " + currentData[params[1].dataIndex];
              + params[0].marker + params[0].seriesName + ": " + params[0].value + '<br />'
              + params[1].marker + params[1].seriesName + ": " + params[1].value;
          }
        },
        series: [
          {
            name: this.legend[0],
            // data: d1,
            data: frontData,
            type: 'bar',
            barWidth: 45,
            // label: {
            //   normal: {
            //     show: true,
            //     position: 'inside',
            //     formatter: function (params) {
            //       if (params.value) {
            //         return frontData[params.dataIndex]
            //       } else {
            //         return '';
            //       }
            //     }
            //   }
            // },
            itemStyle: { normal: { color: '#37a2da' } }
          },
          {
            name: this.legend[1],
            // data: d2,
            data: currentData,
            type: 'bar',
            barWidth: 45,
            // label: {
            //   normal: {
            //     show: true,
            //     position: 'inside',
            //     formatter: function (params) {
            //       if (params.value) {
            //         return currentData[params.dataIndex]
            //       } else {
            //         return '';
            //       }
            //     }
            //   }
            // },
            itemStyle: { normal: { color: '#67e0e3' } }
          }
        ]
      };
      this.myChart.setOption(this.option);
    },
  },
}
</script>

<style scoped>
</style>
