<template>
  <div style="height:100%">
    <div id="myChart"
         :style="{width: '100%', height: '88%'}"></div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      myChart: {}
    }
  },
  mounted () {
    this.drawLine()
  },
  methods: {
    onresize () {
      this.myChart.resize()
    },
    drawLine () {
      this.myChart = this.$echarts.init(document.getElementById('myChart'))
      // let myChart = this.$echarts.init(document.getElementById('myChart'))
      var option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          bottom: '5%',
          itemGap: 30,
          itemWidth: 10,
          itemHeight: 10,
          textStyle: {
            color: "#5cdbe5",
          },
          icon: "circle"
          // data: ['优秀', '良好', '轻度', '中度', '重度']
        },
        series: [
          {
            // name: 'Access From',
            type: 'pie',
            radius: ['45%', '55%'],
            bottom: "10%",
            data: [
              { value: 1048, name: '优秀', label: { color: '#61e6f0' } },
              { value: 735, name: '良好', label: { color: '#60ea7f' } },
              { value: 580, name: '轻度', label: { color: '#f5e68e' } },
              { value: 484, name: '中度', label: { color: '#f06f59' } },
              { value: 300, name: '重度', label: { color: '#d93837' } }
            ],
            // label文字不同颜色设置
            // data: this.pieData.map((item, index) => {
            //   item.label = {
            //     color: this.colorList[index]
            //   }
            //   return item;
            // }),
            //样式
            itemStyle: {
              color: function (colors) {
                var colorList = [
                  '#61e6f0',
                  '#60ea7f',
                  '#f5e68e',
                  '#f06f59 ',
                  '#d93837',
                ];
                return colorList[colors.dataIndex];
              }
            },
            //指针
            label: {
              show: true,
              // color: '#fff',
              formatter: function (arg) {
                return arg.name + arg.percent + '%'
              }
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      // 使用刚指定的配置项和数据显示图表。
      this.myChart.setOption(option);
    }
  }
}
</script>

<style>
</style>