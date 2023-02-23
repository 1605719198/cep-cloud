<template>
  <el-row class="row" type="flex" justify="space-around" :gutter="10">
    <el-col :span="16" style="height: 100%">
      <div class="ranking-board-title1" style="justify-content: space-between;">
        <div class="ranking-board-title">燃气消耗数据统计分析</div>
        <div class="head-time"  style="width: 38%;padding-right: 2vw;">
          <div style="height:30%;"></div>
          <div class="time-box">
            <div>时间</div>
            <el-date-picker style="width:32%"
                            v-model="qry1.startTime"
                            size="mini"
                            type="date"
                            value-format="yyyy-MM-dd"
                            format="yyyy/MM/dd"
                            placeholder="选择日期">
            </el-date-picker>
            <div>
              至
            </div>
            <el-date-picker style="width:32%"
                            v-model="qry1.endTime"
                            size="mini"
                            type="date"
                            value-format="yyyy-MM-dd"
                            format="yyyy/MM/dd"
                            placeholder="选择日期">
            </el-date-picker>
            <div>
              <el-button size="mini"
                         @click="onLoad">确认</el-button>
            </div>
          </div>
        </div>
      </div>
      <div id="place7" style="position: relative; height: 40vh;">
        <div id="chart7" style="position:absolute; height: 35vh; width: 55vw;" />
      </div>
    </el-col>

    <el-col :span="8">
      <div class="ranking-board-title">燃气消耗数据统计分析</div>
      <div id="place8" style="position: relative; height: 35vh;">
        <div style="position:absolute; height: 35vh; width: 30vw;">
          <dv-scroll-board ref="scroll-board" :config="config1" style="width:30vw;height:41vh" />
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import moment from "moment";
import {getEngyTopAnalys} from "@/api/energy/ee/engyboard";

export default {
  name: 'RankingBoardD',
  props: {
    panel: Number,
  },
  data () {
    return {
      qry1:{
        startTime: '',
        endTime: moment().subtract(1, 'day').format('YYYY-MM-DD'),
      },
      engyBottomAnalys: [],
      xData: [],
      newX: [],
      planData: [],
      planData2: [],
      planData3: [],
      performance: [],
      config:{
        indexHeader: '序号',
        rowNum: [7],
        headerBGC: '#0e2147',
        evenRowBGC: '#0e2147',
        header: ['日期', '能源名称', '能量值'],
        index: true,
        columnWidth: [60],
        align: ['center']
      },
      config1:{
        data: [],
      }
    }
  },
  methods: {
    drawChart() {
      // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
      this.myChart7 = this.$echarts.init(document.getElementById("chart7"));
      // 指定图表的配置项和数据
      let option = {
        backgroundColor: "#05224d",
        tooltip : {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        grid: {
          top: "8%",
          left: "3%",
          right: "3%",
          bottom: "8%",
          containLabel: true,
        },
        legend: {
          x: "46%",
          top: "0%",
          textStyle: {
            color: "white",
          },
          icon: "rect",
          data: this.performance,
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: false,
            axisLine: {
              //坐标轴轴线相关设置。数学上的x轴
              show: true,
              lineStyle: {
                color: "#233e64",
              },
            },
            axisLabel: {
              //坐标轴刻度标签的相关设置
              textStyle: {
                color: "#6a9cd5",
                margin: 15,
              },
            },
            axisTick: { show: false },
            data: this.newX,
          },
        ],
        yAxis: [
          {
            type: "value",
            // min: 0,
            // max: 140,
            splitNumber: 6,
            splitLine: {
              show: true,
              lineStyle: {
                color: "#233e64",
              },
            },
            axisLine: { show: false },
            axisLabel: {
              margin: 20,
              textStyle: {
                color: "#6a9cd5",
              },
            },
            axisTick: { show: false },
          },
        ],
        series: [
          {
            name: "焦炉煤气",
            type: "line",
            smooth: true, //是否平滑曲线显示
            // 			symbol:'circle',  // 默认是空心圆（中间是白色的），改成实心圆
            // symbolSize: 0,

            // lineStyle: {
            //   normal: {
            //     color: "#3deaff", // 线条颜色
            //   },
            // },
            areaStyle: { //区域填充样式
              color: "#0c1e37"
            },
            data: this.planData,
          },
          // {
          //   name: "除盐水",
          //   type: "line",
          //   smooth: true, //是否平滑曲线显示
          //   areaStyle: { //区域填充样式
          //     color: "rgba(12,30,55,0.64)"
          //   },
          //   data: this.planData2,
          // },
          // {
          //   name: "生活水",
          //   type: "line",
          //   smooth: true, //是否平滑曲线显示
          //   areaStyle: { //区域填充样式
          //     color: "rgba(12,30,55,0.64)"
          //   },
          //   data: this.planData3,
          // },
        ],
      };

      // 使用刚指定的配置项和数据显示图表。
      this.myChart7.setOption(option);
    },
    onLoad(){
      this.engyBottom();
    },
    engyBottom () {
      this.qry1.energyTypeName='燃气'
      getEngyTopAnalys(this.qry1).then(res => {
        this.xData = []
        this.newX = []
        this.planData = []
        this.planData2 = []
        this.planData3 = []
        this.performance = []
        this.engyBottomAnalys = res.data.data.engyBottomRight
        this.config1={data:this.engyBottomAnalys};
        this.config1={...this.config1,...this.config};
        let data = res.data.data.engyBottom
        data.forEach(item => {
          this.xData.push(item.e_count_date)
          if(item.energy_code_name=='焦炉煤气'){this.planData.push(item.qty)}
          // if(item.energy_code_name=='除盐水'){this.planData2.push(item.qty)}
          // if(item.energy_code_name=='生活水'){this.planData3.push(item.qty)}
          this.performance.push(item.energy_code_name)
        })
        this.newX=[...new Set(this.xData)]
      })
    },
  },
  created () {
    this.onLoad()
  },
  watch: {
    xData () {
      this.drawChart()
    },
  },
  mounted() {
    window.addEventListener('resize', () => {
      if(this.panel>500){
        this.myChart7.resize({
          width:"1056px",
          height:"339px"
        });
      }else {
        this.myChart7.resize({
          width:"1200px",
          height:"484px"
        });
      }
    });
    this.drawChart();
  }
}
</script>

<style lang="less" scoped>
#ranking-board {
  width: 100%;
  box-shadow: 0 0 3px blue;
  display: flex;
  flex-direction: column;
  background-color: rgba(6, 30, 93, 0.5);
  border-top: 2px solid rgba(1, 153, 209, .5);
  box-sizing: border-box;
  padding: 0px 30px;

  .ranking-board-title1{
    display: flex;
  }
  .ranking-board-title {
    font-weight: bold;
    height: 50px;
    display: flex;
    align-items: center;
    font-size: 20px;
  }

  .bottom{
    display: flex;
    justify-content: space-around;
  }

}
.head-time {
  width: 70%;
  .time-box {
    height: 45%;
    margin-left: 5%;
    color: #a9baf3;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
}
.time-box ::v-deep.el-input__inner {
  color: #a9baf3;
  background: #0a1d5c;
  border: #2259c3 dashed 1px;
}
.time-box ::v-deep.el-button {
  color: #a9baf3;
  background: #0a1d5c;
  border: #2259c3 solid 1px;
  // height: 28px;
}
.time-box ::v-deep.el-range-input {
  background: #0a1d5c;
  color: #a9baf3;
}
.time-box ::v-deep.el-range-separator {
  color: #a9baf3;
}
</style>
