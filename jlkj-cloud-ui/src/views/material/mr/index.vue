<!--物料看板-->
<template>
  <div class="main">
    <div class="head">
      <div class="head-time">
        <div style="height:48%;"></div>
        <div class="time-box">
          <div style="margin-left: 25px;">时间</div>
          <el-date-picker class="aaa"
                          style="width:35%; margin-left: 20px"
                          v-model="start_date"
                          size="mini"
                          type="date"
                          value-format="yyyy-MM-dd"
                          format="yyyy-MM-dd"
                          placeholder="选择日期">
          </el-date-picker>
          <div>
            <el-button @click="onLoad"
                       size="mini" style="margin-left: 20px">确认</el-button>
          </div>
        </div>
      </div>
      <div class="head-title">
        <img src="/img/wel/jlogo.png"
             alt="">
        吉林建龙150万吨焦电集控平台-物料看板
      </div>
      <div class="head-weather">
        <div style="height:60%;"></div>
        <div v-if="city">
          <span>{{city}}</span>
          <span> / </span>
          <span> {{weather}} </span>
          <span> / </span>
          <span> {{temperature}} </span>
          <span> / </span>
          <span>{{windDirection}}{{windPower}}</span>
          <span> / </span>
          <span>{{humidity}}</span>
        </div>
      </div>
    </div>
    <div class="content_container">
      <el-row class="row" type="flex" justify="space-around" :gutter="0" style="height: 50%">
        <el-col :span="14" style="position: relative">
          <div class="rawCoalStockContainer">
            <div style="position: absolute ; display: flex ; flex-direction: row; ;left: 2%; top: 5%; right: 2%">
              <img src="/img/material/title.png" style="padding-top: 1px">
              <span style="color: #FFFFFF ; font-size: 1.2em; margin-left: 10px">原煤库存</span>
            </div>
            <div id="rawCoalStockEchart" style="position: absolute ; left:2% ; top: 15% ; right: 2%; bottom: 5%" />
          </div>
        </el-col>
        <el-col :span="10" style="position: relative">
          <div class="productionStockContainer">
            <div style="position: absolute ; display: flex ; flex-direction: row; ;left: 3%; top: 5%; right: 2%">
              <img src="/img/material/title.png" style="padding-top: 1px">
              <span style="color: #FFFFFF ; font-size: 1.2em; margin-left: 10px">产品库存</span>
            </div>
            <div id="productionStockEchart" style="position: absolute ; left:2% ; top: 15% ; right: 2%; bottom: 5%" />
          </div>
        </el-col>
      </el-row>
      <el-row class="row" type="flex" justify="space-around" :gutter="0" style="height: 50%">
        <el-col :span="14" style="position: relative">
          <div class="upCoalAmountContainer">
            <div style="position: absolute ; display: flex ; flex-direction: row; ;left: 2%; top: 5%; right: 2%">
              <img src="/img/material/title.png">
              <span style="color: #FFFFFF ; font-size: 1.2em ; margin-left: 10px">上煤量</span>
            </div>
            <div id="upCoalAmountEchart" style="position: absolute ; left:3% ; top: 15% ; right: 2%; bottom: 5%"/>
          </div>
        </el-col>
        <el-col :span="10" style="position: relative">
          <div class="upCoalWarehouseSurplusContainer">
            <div style="position: absolute ; display: flex ; flex-direction: row; ;left: 3%; top: 5%; right: 2%">
              <img src="/img/material/title.png" style="padding-top: 1px">
              <span style="color: #FFFFFF ; font-size: 1.2em; margin-left: 10px">上煤仓当日余量</span>
            </div>
            <div id="upCoalWarehouseSurplusEchart" style="position: absolute ; left:2% ; top: 15% ; right: 2%; bottom: 5%" />
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import {getCoalStock, getMaterialsCoalWarehouseStock, getUpCoalWeightByNowDay} from "@/api/material/mr/kanban/kanbanApi";
import moment from "moment";
import {getCokeStock} from "@/api/production/oi/kanbanApi";

export default {
  name: "index",
  data(){
    return {
      date:'',
      start_date:moment(new Date()).format("yyyy-MM-DD"),
      rawCoalStockEchart:null ,
      upCoalAmountEchart:null ,
      productionStockEchart:null ,
      upCoalWarehouseSurplusEchart:null ,
      city: '',
      humidity: '',
      weather: '',
      windDirection: '',
      windPower: '',
      temperature: '',
    }
  },
  methods:{
    initEcharts(){
      this.rawCoalStockEchart = this.$echarts.init(document.getElementById("rawCoalStockEchart"));
      this.upCoalAmountEchart = this.$echarts.init(document.getElementById("upCoalAmountEchart"));
      this.productionStockEchart = this.$echarts.init(document.getElementById("productionStockEchart"));
      this.upCoalWarehouseSurplusEchart = this.$echarts.init(document.getElementById("upCoalWarehouseSurplusEchart"));

      let rawCoalStockEchartOpt = {
        xAxis: {
          type: 'category',
          data: [],
          boundaryGap: true,
          axisLabel: {//x轴文字的配置
            show: true,
            textStyle: {
              fontSize:10,
              color: "#fff",
            }
          },
          axisLine:{
            show:true,
            lineStyle:{
              color:'#ffffff'
            }
          }
        },
        yAxis: {
          type: 'value',
          scale: true,
          min: 0,
          axisLabel: {//x轴文字的配置
            show: true,
            textStyle: {
              color: "#fff",
              fontSize:10
            }
          },
          splitLine:{
            show:true,
            lineStyle:{
              color:'#204693',
              type:'dashed'
            }
          },
          axisLine:{
            show:true,
            lineStyle:{
              color:'#ffffff'
            }
          },
          axisTick:{
            show:false
          },
          boundaryGap: [0.2, 0.2]
        },
        grid:{
          left: '5%',
          top: '5%',
          right: '5%',
          bottom: '6%'
        },
        tooltip:{
          show:true
        },
        series: {
          data: [],
          type: 'bar',
          label:{
            show:true,
            position:'insideTop',
            padding:[10,0, 0, 0]
          },
          itemStyle:{
            borderColor:'#18828c',
            borderWidth:1,
            color:{
              type:'linear',
              x:0,
              y:0,
              x2:0,
              y2:1,
              colorStops:[
                {offset:0, color:'#02203F'},
                {offset:1, color:'#246582'}
              ]
            },
          },
        }
      };
      let upCoalAmountEchartOpt = {
        xAxis:[
          {
            position:'bottom',
            type: 'category',
            axisLabel: {//x轴文字的配置
              show: true,
              textStyle: {
                color: "#fff",
                fontSize:10
              }
            },
            axisLine:{
              show:true,
              lineStyle:{
                color:'#ffffff'
              }
            },
            data: []
          },
        ],
        yAxis: {
          type: 'value',
          axisLabel: {//x轴文字的配置
            show: true,
            textStyle: {
              color: "#fff",
              fontSize:10
            }
          },
          axisLine:{
            show:true,
            lineStyle:{
              color:'#fff'
            }
          },
          axisTick:{
            show:false
          },
          splitLine:{
            show:true,
            lineStyle:{
              color:'#204693',
              type:'dashed'
            }
          },
        },
        grid:{
          left: '5%',
          top: '5%',
          right: '5%',
          bottom: '6%'
        },
        tooltip:{
          show:true
        },
        series: [
          {
            data: [],
            type: 'pictorialBar',
            //图形形状
            symbol: 'path://M150 50 L130 130 L170 130  Z',
            label:{
              show:true,
              position:'top',
              distance:15,
              textStyle:{
                color:'#fff'
              }
            },
            itemStyle:{
              color:'#04B2F9'
            }
          },
          {
            type:'line',
            symbol:'circle',
            symbolSize:6,
            data: [],
            itemStyle:{
              color:'#F9DD54'
            }
          }
        ]
      };
      let productionStockEchartOpt = {
        xAxis: {
          type: 'category',
          axisLabel: {//x轴文字的配置
            show: true,
            textStyle: {
              fontSize:9,
              color: "#fff",
            }
          },
          axisLine:{
            show:true,
            lineStyle:{
              color:'#ffffff'
            }
          },
          data: []
        },
        yAxis: {
          type: 'value',
          axisLabel: {//x轴文字的配置
            show: true,
            textStyle: {
              color: "#fff",
              fontSize:10
            }
          },
          splitLine:{
            show:true,
            lineStyle:{
              color:'#204693',
              type:'dashed'
            }
          },
          axisLine:{
            show:true,
            lineStyle:{
              color:'#ffffff'
            }
          },
          axisTick:{
            show:false
          },
        },
        grid:{
          left: '9%',
          top: '5%',
          right: '5%',
          bottom: '6%'
        },
        tooltip:{
          show:true
        },
        series: {
            data: [],
            type: 'line',
            symbol:'circle',
            symbolSize:6,
            itemStyle:{
              color:'#1EF5DB'
            },
            areaStyle:{
              color:{
                type:'linear',
                x:0,
                y:0,
                x2:0,
                y2:1,
                colorStops:[
                  {offset:0, color:'#011A41'},
                  {offset:0.3, color:'#094656'},
                  {offset:0.5, color:'#094656'},
                  {offset:0.7, color:'#094656'},
                  {offset:1,color:'#011A41'}
                ]
              }
            }
          }
      };
      let upCoalWarehouseSurplusEchartOpt = {
        xAxis: {
          type: 'value',
          axisLabel: {//x轴文字的配置
            show: true,
            textStyle: {
              fontSize:10,
              color: "#fff",
            }
          },
          axisLine:{
            show:true,
            lineStyle:{
              color:'#ffffff'
            }
          },
          splitLine:{
            show:true,
            lineStyle:{
              color:'#204693',
              type:'dashed'
            }
          },
        },
        yAxis: {
          type: 'category',
          axisLabel: {//x轴文字的配置
            show: true,
            textStyle: {
              color: "#fff",
              //fontSize:10
            },
          },
          axisTick:{
            show:false
          },
          axisLine:{
            show:true,
            lineStyle:{
              color:'#ffffff',
            }
          },
          data: []
        },
        grid:{
          left: '9%',
          top: '5%',
          right: '5%',
          bottom: '6%'
        },
        tooltip:{
          show:true
        },
        series: {
            name: '2011',
            type: 'bar',
            itemStyle:{
              borderColor:'#18828c',
              borderWidth:1,
              color:{
                type:'linear',
                x:0,
                y:0,
                x2:1,
                y2:0,
                colorStops:[
                  {offset:0, color:'#05D8D0'},
                  {offset:1, color:'#052A49'}
                ]
              },
            },
            label:{
              show:true,
              position:'insideRight',
              padding:[0,10, 0, 0]
            },
            data:[]
          },

      };

      this.rawCoalStockEchart.setOption(rawCoalStockEchartOpt);
      this.productionStockEchart.setOption(productionStockEchartOpt);
      this.upCoalWarehouseSurplusEchart.setOption(upCoalWarehouseSurplusEchartOpt);
      this.upCoalAmountEchart.setOption(upCoalAmountEchartOpt);
    },

    //根据定位获取温度
    getlocation(){
      let _this = this
      //定位
      AMap.plugin('AMap.CitySearch', function () {
        var citySearch = new AMap.CitySearch()
        citySearch.getLocalCity(function (status, result) {
          if (status === 'complete' && result.info === 'OK') {
            // 查询成功，result即为当前所在城市信息
            //加载天气查询插件
            AMap.plugin('AMap.Weather', function () {
              //创建天气查询实例
              var weather = new AMap.Weather();
              //执行实时天气信息查询
              weather.getLive(result.city, function (err, data) {
                _this.city = data.city
                _this.humidity = data.humidity + '%'
                _this.weather = data.weather
                _this.windDirection = data.windDirection
                _this.windPower = data.windPower + '级'
                _this.temperature = data.temperature + '℃'
              });
            });
          }
        })
      })
    },

    onLoad(){
      this.getCoalStock();
      this.getUpCoalWeightByNowDay();
      this.getProductionStock();
      this.getMaterialsCoalWarehouseStock();
    },

    //原煤库存
    getCoalStock(){
      getCoalStock({start_date:this.start_date}).then(response => {
        if (response.data.code === '0'){
          let stockData = response.data ;
          let xAxisData = [] ;
          let yAxisData = [] ;
          for (let i = 0; i < stockData.length; i++) {
            xAxisData.push(stockData[i].materials_name);
            yAxisData.push(stockData[i].inventory);
          }
          this.rawCoalStockEchart.setOption(
            {
            xAxis:{
              data:xAxisData
            },
            series:{
              data:yAxisData
            }
          });
        }
      }, error =>{
      })
    },

    //上煤量
    getUpCoalWeightByNowDay(){
      getUpCoalWeightByNowDay({start_date:this.start_date}).then(response => {
        if (response.data.code === '0'){
          let stockData = response.data ;
          let xAxisData = [] ;
          let yAxisData = [] ;
          for (let i = 0; i < stockData.length; i++) {
            xAxisData.push(stockData[i].material_name);
            yAxisData.push(stockData[i].weight);
          }
          this.upCoalAmountEchart.setOption({
            xAxis:{
              data:xAxisData
            },
            series:[
              { data:yAxisData},
              { data:yAxisData}
            ]
          });
        }
      }, error =>{
      })
    },

    //获取产量库存
    getProductionStock(){
      getCokeStock({start_date:this.start_date}).then(res => {
        if (res.code == 200){
          let productionArray = [];
          let inventoryArray = [];
          res.data.forEach(i => {
            productionArray.push(i.materials_name);
            inventoryArray.push(i.inventory);
          });
          this.productionStockEchart.setOption({
            xAxis:{
              data:productionArray
            },
            series:{
              data:inventoryArray
            }
          });
        }
      }, err => {
      });
    },

    //上煤仓余量
    getMaterialsCoalWarehouseStock(){
      getMaterialsCoalWarehouseStock().then(res => {
        if (res.code == 200){
          let coalNameArray = [];
          let coalStockArray = [];
          res.data.forEach(i => {
            coalNameArray.push(i.coal_warehouse_number);
            coalStockArray.push(i.coal_surplus);
          });
          this.upCoalWarehouseSurplusEchart.setOption({
            yAxis:{
              data:coalNameArray
            },
            series:{
              data:coalStockArray
            }
          });
        }
      }, err => {
      });
    }

  },
  mounted() {
    this.date = moment(new Date).format("yyyy年M月D日")
    this.initEcharts();
    window.onresize = ()=>{
      setTimeout(()=>{
        this.rawCoalStockEchart.resize();
        this.upCoalAmountEchart.resize();
        this.productionStockEchart.resize();
        this.upCoalWarehouseSurplusEchart.resize();
      }, 200);
    }
    this.getlocation();
    this.getCoalStock();
    this.getUpCoalWeightByNowDay();
    this.getProductionStock();
    this.getMaterialsCoalWarehouseStock();
  },
  beforeDestroy() {
    this.rawCoalStockEchart.clear();
    this.rawCoalStockEchart = null ;

    this.upCoalAmountEchart.clear();
    this.upCoalAmountEchart = null ;

    this.productionStockEchart.clear();
    this.productionStockEchart = null ;

    this.upCoalWarehouseSurplusEchart.clear();
    this.upCoalWarehouseSurplusEchart = null ;
  },
}
</script>

<style scoped>
  html,body{
    width: 100%;
  }
  .main{
    display: flex;
    flex-direction: column;
    background-image: url("/img/wel/bg3.png");
    background-position: center;
    background-size: 100% 100%;
    background-repeat: no-repeat;
    height: 100%;
  }

  .head {
    box-sizing: border-box;
    display: flex;
    justify-content: space-between;
    height: 8.5%;
  }
  .head-weather {
    width: 27%;
    text-align: center;
    color: #fff;
  }
  .head-time {
    width: 24%;
    height: 100%;
  }
  .time-box {
    height: 45%;
    margin-left: 2%;
    color: #a9baf3;
    display: flex;
    //justify-content: space-around;
    align-items: center;
  }
  .img {
    height: 80%;
    vertical-align: middle;
  }
  .head-title {
    width: 40%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #fff;
    font-size: 1.8rem;
    letter-spacing: 0.2rem;
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
     height: 28px;
  }

  .content_container{
    padding: 0 20px 10px 20px;
    margin-top: 10px;
    height: 93%;
  }

  .rawCoalStockContainer{
    position: absolute;
    background-image: url('/img/material/da1.png');
    background-position: center;
    background-size: 100% 100%;
    background-repeat: no-repeat;
    left: 1%;
    top: 1%;
    right: 1%;
    bottom:1%;
  }
  .productionStockContainer{
    position: absolute;
    background-image: url('/img/material/xiao2.png');
    background-position: center;
    background-size: 100% 100%;
    background-repeat: no-repeat;
    left: 1%;
    top: 1%;
    right: 1%;
    bottom:1%;
  }
  .upCoalAmountContainer{
    position: absolute;background-image:
    url('/img/material/da1.png');
    background-position: center;
    background-size: 100% 100%;
    background-repeat: no-repeat;
    left: 1%;
    top: 1%;
    right: 1%;
    bottom:1%;
  }
  .upCoalWarehouseSurplusContainer{
    position: absolute;
    background-image: url('/img/material/xiao2.png');
    background-position: center;
    background-size: 100% 100%;
    background-repeat: no-repeat;
    left: 1%;
    top: 1%;
    right: 1%;
    bottom:1%;
  }
</style>
