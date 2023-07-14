<template>
  <div style="height:100%">
    <div id="proportionEcharts"
         :style="{width: '100%', height: '100%'}"></div>
  </div>
</template>

<script>
  export default {
    props: {
      proportionData: {
        type: Array,
        default: () => {
          return []
        }
      }
    },
    data() {
      return {
        myChart: {},
        valueData: []
      }
    },
    mounted() {
      this.drawLine()
    },
    watch: {
      proportionData(newValue) {
        let that = this;
        this.valueData = [];
        newValue.forEach(item => {
          let i = {
            value: item.inventory / 1000,
            name: item.materials_name
          };
          that.valueData.push(i);
        });
        this.drawLine()
      }
    },
    methods: {
      onresize() {
        this.myChart.resize()
      },
      drawLine() {
        this.myChart = this.$echarts.init(document.getElementById('proportionEcharts'))
        // let myChart = this.$echarts.init(document.getElementById('proportionEcharts'))
        var option = {
          //提示框组件
          tooltip: {
            trigger: 'item'
          },
          //图例组件
          legend: {
            bottom: '5%',
            // left: 'center',
            // width: "94%",
            // itemGap: 8, //图例每项之间的间隔
            itemWidth: 10,//图例标记的图形宽度
            itemHeight: 10,//图例标记的图形高度
            textStyle: {
              color: "#fff", //字体颜色
              fontSize: 10
            },
            icon: "circle" //图例项的 icon(形状)
            // data: ['优秀', '良好', '轻度', '中度', '重度']
          },
          //颜色
          color: ['#4fca58', '#f7c82f', '#0af6ff', '#b4ff64', '#8374f7', '#649eff'],
          //图表配置
          series: [
            {
              // name: 'Access From',  //系列名称，用于tooltip的显示，legend 的图例筛选，在 setOption 更新数据和配置项时用于指定对应的系列。
              type: 'pie',
              radius: ['45%', '75%'],
              top: "0%",
              bottom: '10%',
              // roseType: 'radius',  //是否展示成南丁格尔图
              itemStyle: {
                // borderRadius: 10,
                borderColor: '#0a1632',
                borderWidth: 5
              },
              data: this.valueData,
              // data: this.valueData,
              //指针
              label: {
                show: false,
                // fontWeight: "bold",
                // fontSize: 13,
                // color: '#fff',
                // formatter: function (arg) {
                //   return arg.name + arg.percent + '%'
                // }
                // formatter: '{b}\n{d}%'
              },
              //高亮状态的扇区和标签样式
              // emphasis: {
              //   itemStyle: {
              //     shadowBlur: 10,  //图形阴影的模糊大小
              //     shadowOffsetX: 0,  //阴影水平方向上的偏移距离。
              //     shadowColor: 'rgba(0, 0, 0, 0.5)' //阴影颜色
              //   }
              // }
            }
          ]
        }
        // 使用刚指定的配置项和数据显示图表。
        this.myChart.setOption(option);
      }
    }
  }
</script>

<style lang="scss" scoped>
</style>
