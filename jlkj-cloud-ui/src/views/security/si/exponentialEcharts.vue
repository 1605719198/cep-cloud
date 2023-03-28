<template>
  <div style="height:100%;">
    <div id="myChart2" :style="{width: '100%', height: '70%'}"></div>
    <ul class="title-box">
      <li :class="isCheck === 0 ? 'liCheck':''" @click="isCheck=0">颗粒物</li>
      <li :class="isCheck === 1 ? 'liCheck':''" @click="isCheck=1">二氧化硫</li>
      <li :class="isCheck === 2 ? 'liCheck':''" @click="isCheck=2">氮氧化物</li>
    </ul>
  </div>
</template>

<script>
  import {getIndexChangeCharts} from '@/api/security/si/index'

  export default {
    data() {
      return {
        isCheck: 0,
        myChart: {},
        xAxis: [],
        data: [],
      }
    },
    mounted() {
      this.drawLine()
    },
    watch: {
      isCheck(val) {
        if (val === 0) {
          this.onLoad('颗粒物');
        } else if (val === 1) {
          this.onLoad('二氧化硫');
        } else if (val === 2) {
          this.onLoad('氮氧化物');
        }
      },
    },
    methods: {
      onLoad(type){
        getIndexChangeCharts({type:type}).then(res => {
          let xAxis= [];
          let data= [];
          res.data.data.forEach(item=>{
            xAxis.push(item.time);
            data.push(item.valueNum);
          });
          this.$set(this,'xAxis',xAxis);
          this.$set(this,'data',data);
        });
      },
      onresize() {
        this.myChart.resize()
      },
      drawLine() {
        this.myChart = this.$echarts.init(document.getElementById('myChart2'))
        // let myChart = this.$echarts.init(document.getElementById('myChart2'))
        var option = {
          color: "#133e4d",
          tooltip: {
            trigger: "item"
          },
          grid: {
            // top: "0%"
            bottom: "20%"
          },
          xAxis: {
            type: 'category',
            data: this.xAxis,
            axisLine: {
              lineStyle: {color: '#fff',}
            }
          },
          yAxis: [
            {
              type: 'value',
              axisLine: {
                show: false //y轴线消失
              },
              axisTick: {
                show: false //y轴坐标点消失
              },
              axisLabel: {
                show: false  //隐藏数字
              },
              splitLine: {show: false}, //背景网格
            }
          ],
          series: [
            {
              type: 'bar',
              data: this.data,
              barWidth: "60%",
              //显示数值
              itemStyle: {
                normal: {
                  label: {
                    show: true, //开启显示
                    position: 'top', //在上方显示
                    textStyle: {
                      //数值样式
                      color: '#fff796',
                      fontSize: 12,
                    },
                  }
                },
              },
            },
            {
              type: 'line',
              data: [120, 200, 150, 80, 70, 110, 130],
              itemStyle: {
                color: "#5bdae5"
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

<style lang="less" scoped>
  .title-box {
    margin: 0;
    padding: 0;
    list-style-type: none;
    width: 75%;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    color: #05849b;
    align-items: center;
    font-size: 14px;

    li {
      width: 30%;
      padding: 2px 0;
      text-align: center;
      background: #286d86;
      color: #fff;
      cursor: pointer;
    }

    .liCheck {
      background: #fff796;
      color: #000;
    }
  }
</style>
