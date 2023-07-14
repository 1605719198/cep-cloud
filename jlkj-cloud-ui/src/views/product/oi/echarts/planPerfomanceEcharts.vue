<template>
  <div style="height:100%;">
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
      default: 'myCharts'
    },
    legendList: {
      type: Array,
      default: () => {
        return []
      }
    },
    xAxisData: {
      type: Array,
      default: () => {
        return []
      }
    },
    planDataCoke: {
      type: Array,
      default: () => {
        return []
      }
    },
    performanceCoke: {
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
    xAxisData () {
      this.drawLine()
    },
    // perFormanceCoke () {
    //   this.drawLine()
    // },
    // planDataCoke () {
    //   this.drawLine()
    // }
  },
  methods: {
    onresize () {
      this.myChart.resize()
    },
    drawLine () {
      this.myChart = this.$echarts.init(document.getElementById(this.chartId))
      // let myChart = this.$echarts.init(document.getElementById(this.chartId))
      var option = {
        //提示框组件
        tooltip: {
          trigger: 'axis',
        },
        //图例组件
        legend: {
          top: "12%",
          right: "5%",
          data: this.legendList,
          textStyle: {
            // color: []  //为空数组时 自动对应颜色
            color: '#fff'
          },
        },
        //直角坐标系内绘图网格(也就是容器)
        grid: {
          top: "25%",
          left: '3%',
          right: "3%",
          bottom: "8%",
          containLabel: true //grid 区域是否包含坐标轴的刻度标签(也就是坐标线在grid里面)
        },
        //全局文字样式
        textStyle: {
          color: '#fff',
          // fontWeight: 'bold'
        },
        //X轴
        xAxis: [
          {
            type: 'category',
            // data: ['08-01', '08-02', '08-03', '08-04', '08-05', '08-06', '08-07'],
            data: this.xAxisData,
            // axisLabel: {
            //   fontWeight: "bold" //坐标文字文字加粗
            // },
            axisTick: {
              show: false //坐标点消失
            },
            axisLine: {
              // lineStyle: {
              //   color: '#fff' //轴线颜色
              // }
              show: false  //轴线消失
            },
          }
        ],
        //Y轴
        yAxis: [
          {
            type: 'value',
            // axisLabel: {
            //   fontWeight: "bold" //坐标文字文字加粗
            // },
            axisLine: {
              show: false //轴线消失
            },
            axisTick: {
              show: false //轴坐标点消失
            },
            splitLine: {
              // show: false //背景网格
              lineStyle: {
                // type: "dashed",  //网格线类型
                color: '#04257f'  //网格线颜色
              }
            },
          }
        ],
        //图表配置
        series: [
          {
            name: this.legendList[0],
            type: 'bar',
            // color: '#be73e7',
            barCategoryGap: "60%",//同一系列的柱间距离
            // data: [100, 52, 200, 134, 110, 120, 200],
            data: this.planDataCoke,
            itemStyle: { //渐变色
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: '#ffbe1f' },
                // { offset: 0.5, color: '#188df0' },
                { offset: 1, color: '#ff5f1f' }
              ]),
              barBorderRadius: [50, 50, 0, 0],  //圆角
            },
          },
          {
            name: this.legendList[1],
            type: 'bar',
            // color: '#e1a260',
            barCategoryGap: "60%",//同一系列的柱间距离
            // data: [150, 120, 150, 80, 70, 110, 130],
            data: this.performanceCoke,
            itemStyle: { //渐变色
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: '#27d6e8' },
                // { offset: 0.5, color: '#188df0' },
                { offset: 1, color: '#058af0' }
              ]),
              barBorderRadius: [50, 50, 0, 0]  //圆角
            },
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