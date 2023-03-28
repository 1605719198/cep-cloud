<template>
  <div class="main">
    <div class="head">
      <div class="title">环保管理综合平台</div>
      <div class="message-box">
        <div>
          {{temperature}}
        </div>
        <span></span>
        <div class="time">
          {{showDate(date)}}
        </div>
      </div>
    </div>
    <div class="content_container">
      <div class="row-box">
        <div class="column-one">
          <div class="fine">
            <div class="small-title">
              <img src="/img/security/rl.png"
                   alt="">
              <span>
                本月优良天数
              </span>
            </div>
            <GoodDaysEcharts ref="GoodDaysEcharts"/>
          </div>
          <div class="exponent">
            <div class="small-title">
              <img src="/img/security/rl.png"
                   alt="">
              <span>
                近七日指数变化
              </span>
            </div>
            <ExponentialEcharts ref="ExponentialEcharts"/>
          </div>
          <div class="overproof">
            <div class="small-title"
                 style="height:12%;">
              <img src="/img/security/cbfx.png"
                   alt="">
              <span>
                超标次数分析
              </span>
            </div>
            <!-- <OverproofNum style="margin-top:12%;" /> -->
            <OverproofNum style="height:88%;"/>
          </div>
        </div>
        <div class="column-tow">
          <div class="monitor-box">
            <div class="small-title"
                 style="height:4%;padding-left: 1.5%;">
              <img src="/img/security/jc.png"
                   alt="">
              <span>
                实 时 监 测
              </span>
            </div>
            <div class="monitor">
              <!--              <img style="width:100%;height:100%;"-->
              <!--                   src="/img/security/jlpic.png"-->
              <!--                   alt="">-->
              <div id="mapid"
                   style="width:100%;height:100%;"></div>
            </div>
          </div>
          <div class="security-box">
            <div class="job-slip-box">
              <div class="small-title"
                   style="height:11%;">
                <img src="/img/security/jc.png"
                     alt="">
                <span>
                  安全作业票
                </span>
              </div>
              <div class="job-slip">
                <div class="project"
                     v-for="(item,index) in projectList"
                     :key="index">
                  <div>
                    <el-badge :value="item.value"
                              :max="99"
                              class="item">
                    </el-badge>
                  </div>
                  <img @click="goJobSlip(item.type)"
                       :src="item.url"
                       alt="">
                  <p>{{item.name}}</p>
                </div>
                <!-- <div class="project">
                  <div>
                    <el-badge :value="200"
                              :max="99"
                              hidden
                              class="item">
                    </el-badge>
                  </div>
                  <img @click="goJobSlip('dh')"
                       src="/img/security/doicon.png"
                       alt="">
                  <p>动火</p>
                </div>
                <div class="project">
                  <div>
                    <el-badge :value="10"
                              :max="99"
                              class="item">
                    </el-badge>
                  </div>
                  <img @click="goJobSlip('gc')"
                       src="/img/security/gcicon.png"
                       alt="">
                  <p>高处</p>
                </div>
                <div class="project">
                  <div>
                    <el-badge :value="50"
                              :max="99"
                              class="item">
                    </el-badge>
                  </div>
                  <img @click="goJobSlip('yxgj')"
                       src="/img/security/yxkjicon.png"
                       alt="">
                  <p>有限空间</p>
                </div>
                <div class="project">
                  <div>
                    <el-badge :value="20"
                              :max="99"
                              class="item">
                    </el-badge>
                  </div>
                  <img @click="goJobSlip('dt')"
                       src="/img/security/dficon.png"
                       alt="">
                  <p>动土</p>
                </div>
                <div class="project">
                  <div>
                    <el-badge :value="200"
                              :max="99"
                              class="item">
                    </el-badge>
                  </div>
                  <img @click="goJobSlip('dl')"
                       src="/img/security/dlicon.png"
                       alt="">
                  <p>断路</p>
                </div>
                <div class="project">
                  <div>
                    <el-badge :value="5"
                              :max="99"
                              class="item">
                    </el-badge>
                  </div>
                  <img @click="goJobSlip('cdmb')"
                       src="/img/security/cdmbicon.png"
                       alt="">
                  <p>抽堵盲板</p>
                </div>
                <div class="project">
                  <div>
                    <el-badge :value="200"
                              :max="99"
                              class="item">
                    </el-badge>
                  </div>
                  <img @click="goJobSlip('lsyd')"
                       src="/img/security/lsydicon.png"
                       alt="">
                  <p>临时用电</p>
                </div> -->
              </div>
            </div>
            <div class="alarm-item-box">
              <div class="small-title"
                   style="height:12%">
                <img src="/img/security/jc.png"
                     alt="">
                <span>
                  报警项
                </span>
              </div>
              <div style="margin-top:5%;height: 80%;">
                <ul class="alarm-item">
                  <li>设备类型</li>
                  <li>报警项</li>
                  <li>数值</li>
                </ul>
                <!-- <div style="height: 16vh;overflow-y: auto;">
                  <ul class="alarm-item alarm-item-details"
                      v-for="(item,index) in 20"
                      :key="index">
                    <li>供料皮带通廊3</li>
                    <li>pm10value</li>
                    <li>34.0</li>
                    <li>优秀</li>
                  </ul>
                </div> -->
                <div style="height: 72%;overflow-y: auto;">
                  <ul class="alarm-item alarm-item-details"
                      v-for="(item,index) in alertRecord"
                      :key="index">
                    <li>{{item.name}}</li>
                    <li>{{item.sourceh_name}}</li>
                    <li>{{item.alarm_value}}</li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="column-three">
          <div class="detection-box">
            <div class="small-title"
                 style="height:4%;">
              <img src="/img/security/jc.png"
                   alt="">
              <span>
                监测点检测
              </span>
            </div>
            <DetectionEcharts ref="DetectionEcharts" style="height:32%;" :xAxis="DetectionEchartsXAxis" :data="DetectionEchartsData" />
            <div style="height:62%">
              <ul class="detection">
                <li>名称</li>
                <li>污染物</li>
                <li>监测点</li>
                <li>限值</li>
                <li>操作</li>
              </ul>
              <!-- <div style="height: 50vh;overflow-y: auto;">
                <ul class="detection detection-details"
                    v-for="(item,index) in 20"
                    :key="index">
                  <li>管理中路1</li>
                  <li>微站</li>
                  <li>pm10value</li>
                  <li>328.4</li>
                  <li>
                    <i class="el-icon-location"></i>
                  </li>
                </ul>
              </div> -->
              <div style="height: 92%;overflow-y: auto;">
                <ul class="detection detection-details"
                    v-for="(item,index) in environmentalCollectionPoints"
                    :key="index">
                  <li>{{item.device_name}}</li>
                  <li>{{item.pollutant_name}}</li>
                  <li>{{item.collection_point}}</li>
                  <li>{{item.emission_limit}}</li>
                  <li>
                    <i class="el-icon-location" @click="getEnvironmentalCollectionPointsCharts(item.collection_point)" />
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {
    getAnalysisBoardTicketTotal,
    getIndexChangeCharts,
    getAlertRecord,
    getEnvironmentalCollectionPoints,
    getEnvironmentalCollectionPointsCharts

  } from '@/api/security/si/index'
  import GoodDaysEcharts from './goodDaysEcharts'
  import ExponentialEcharts from './exponentialEcharts'
  import OverproofNum from './overproofNum'
  import DetectionEcharts from './detectionEcharts'
  import {showDateFormat} from '@/utils/date'
  import AMap from "AMap";
  import L from 'leaflet';
  import "leaflet/dist/leaflet.css"
  import $ from 'jquery';

  export default {
    components: {
      GoodDaysEcharts,
      ExponentialEcharts,
      OverproofNum,
      DetectionEcharts
    },
    data() {
      return {
        date: new Date(),
        temperature: '',
        projectList: [],
        alertRecord: [],
        environmentalCollectionPoints: [],
        DetectionEchartsXAxis:[],
        DetectionEchartsData:[],
        points: [
          {"id": 1, "areaname": "1", "lat": 6.010433530916268, "lng": 14.238892875782902},
          {"id": 2, "areaname": "2", "lat": 9.842466632505463, "lng": 7.514834705352063},
          {"id": 3, "areaname": "3", "lat": 12.407023500505545, "lng": 2.131137757401778},
          {"id": 4, "areaname": "4", "lat": 14.909390504012864, "lng": -1.6263685590244805},
          {"id": 5, "areaname": "5", "lat": 10.577523640043468, "lng": 17.087481032167332},
          {"id": 6, "areaname": "6", "lat": 16.220409331110115, "lng": 5.131322500740195},
          {"id": 7, "areaname": "7", "lat": 11.243997253174319, "lng": 4.853351019570589},
          {"id": 8, "areaname": "8", "lat": 7.177837369338676, "lng": 7.061720075930817},
          {"id": 9, "areaname": "9", "lat": 11.654536736542736, "lng": -0.802800299784281},
          {"id": 10, "areaname": "10", "lat": 7.6243895886395014, "lng": 7.856623342462555},
          {"id": 11, "areaname": "11", "lat": 5.152298884996147, "lng": 8.577574217887435},
          {"id": 12, "areaname": "12", "lat": 12.374704497111683, "lng": 0.5886373647479949},
          {"id": 13, "areaname": "13", "lat": 11.428676349212202, "lng": -1.564405575465107},
          {"id": 14, "areaname": "14", "lat": 16.37542343611335, "lng": 3.0492578678486653},
          {"id": 15, "areaname": "15", "lat": 15.911074188299754, "lng": 1.6431890089339964},
          {"id": 16, "areaname": "16", "lat": 11.385597911850446, "lng": 13.90235187259629},
          {"id": 17, "areaname": "17", "lat": 10.60909328279714, "lng": 11.881127887906445},
          {"id": 18, "areaname": "18", "lat": 10.263337568075576, "lng": 11.17809345844911},
          {"id": 19, "areaname": "19", "lat": 13.82994492119012, "lng": 3.796231949147098},
          {"id": 20, "areaname": "20", "lat": 8.268280258704728, "lng": 12.584162317363777},
          {"id": 21, "areaname": "21", "lat": 11.266943984237528, "lng": 7.448840283379192},
          {"id": 22, "areaname": "22", "lat": 14.654476319178402, "lng": 0.6589322570696554},
          {"id": 23, "areaname": "23", "lat": 7.1026268353640925, "lng": 9.272758410039842},
          {"id": 24, "areaname": "24", "lat": 6.578657766843202, "lng": 7.954315631524028},
          {"id": 25, "areaname": "25", "lat": 12.774738505887553, "lng": -0.527666243594609},
          {"id": 26, "areaname": "26", "lat": 12.302527105553084, "lng": -1.8021609294932666},
          {"id": 27, "areaname": "27", "lat": 11.39636677511854, "lng": 6.54791411513687},
          {"id": 28, "areaname": "28", "lat": 10.705782091808349, "lng": 7.844382847344136},
          {"id": 29, "areaname": "29", "lat": 9.428453762023588, "lng": 10.17363175605545},
          {"id": 30, "areaname": "30", "lat": 10.532885687894147, "lng": 14.568441017774976},
          {"id": 31, "areaname": "31", "lat": 17.023505902573955, "lng": 6.020599556367424},
          {"id": 32, "areaname": "32", "lat": 16.602532570173086, "lng": 4.921897240937572}
        ]
      }
    },
    mounted() {
      let _this = this;// 声明一个变量指向Vue实例this，保证作用域一致
      // window.onresize = function () {
      //   setTimeout(() => {
      //     _this.$refs.GoodDaysEcharts.onresize()
      //     _this.$refs.ExponentialEcharts.onresize()
      //     _this.$refs.DetectionEcharts.onresize()
      //   }, 200)
      // }
      window.addEventListener('resize', this.listenResize);
      //显示当前日期时间
      this.timer = setInterval(() => {
        _this.date = new Date(); // 修改数据date
      }, 1000)
      this.getlocation();
      this.initMap();
      this.onLoad()
    },
    beforeDestroy() {
      if (this.timer) {
        clearInterval(this.timer); // 在Vue实例销毁前，清除我们的定时器
      }
      // window.onresize = null
      window.removeEventListener("resize", this.listenResize);
    },
    methods: {
      //时间格式
      showDate(time) {
        return showDateFormat(time)
      },
      //根据定位获取温度
      getlocation() {
        let _this = this
        //定位
        AMap.plugin('AMap.CitySearch', function () {
          var citySearch = new AMap.CitySearch()
          citySearch.getLocalCity(function (status, result) {
            if (status === 'complete' && result.info === 'OK') {
              // 查询成功，result即为当前所在城市信息
              // console.log(result)
              //加载天气查询插件
              AMap.plugin('AMap.Weather', function () {
                //创建天气查询实例
                var weather = new AMap.Weather();
                //执行实时天气信息查询
                weather.getLive(result.city, function (err, data) {
                  // console.log(err, data);
                  _this.temperature = data.temperature + '℃'
                });
              });
            }
          })
        })
      },
      //窗口变化处理事件
      listenResize() {
        setTimeout(() => {
          this.$refs.GoodDaysEcharts.onresize()
          this.$refs.ExponentialEcharts.onresize()
          this.$refs.DetectionEcharts.onresize()
        }, 200)
      },
      onLoad() {
        this.getJobSlip();
        this.getAlertRecordInfo();
        this.getEnvironmentalCollectionPointsInfo('');
      },
      getJobSlip() {
        getAnalysisBoardTicketTotal().then(res => {
          // console.log(res, '作业票数')
          let data = res.data
          this.projectList = []
          data.forEach(item => {
            if (item.name == "吊装作业票") {
              item.name = "吊装"
              this.$set(item, 'type', 'dz')
              this.$set(item, 'url', '/img/security/dzicon.png')
            }
            if (item.name == "动火作业票") {
              item.name = "动火"
              this.$set(item, 'type', 'dh')
              this.$set(item, 'url', '/img/security/doicon.png')
            }
            if (item.name == "高处作业票") {
              item.name = "高处"
              this.$set(item, 'type', 'gc')
              this.$set(item, 'url', '/img/security/gcicon.png')
            }
            if (item.name == "有限空间作业票") {
              item.name = "有限空间"
              this.$set(item, 'type', 'yxgj')
              this.$set(item, 'url', '/img/security/yxkjicon.png')
            }
            if (item.name == "动土作业票") {
              item.name = "动土"
              this.$set(item, 'type', 'dt')
              this.$set(item, 'url', '/img/security/dficon.png')
            }
            if (item.name == "断路安全作业票") {
              item.name = "断路"
              this.$set(item, 'type', 'dl')
              this.$set(item, 'url', '/img/security/dlicon.png')
            }
            if (item.name == "抽堵盲板作业票") {
              item.name = "抽堵盲板"
              this.$set(item, 'type', 'cdmb')
              this.$set(item, 'url', '/img/security/cdmbicon.png')
            }
            if (item.name == "临时用电作业票") {
              item.name = "临时用电"
              this.$set(item, 'type', 'lsyd')
              this.$set(item, 'url', '/img/security/lsydicon.png')
            }
            this.projectList.push(item)
          })
          // console.log(this.projectList, 'this.projectList')
        })
      },
      //作业票跳转
      goJobSlip(type) {
        if (type == 'dz') {
          this.$router.push('/security/operation/lifting')
        } else if (type == 'dh') {
          this.$router.push('/security/operation/hotWork')
        } else if (type == 'gc') {
          this.$router.push('/security/operation/height')
        } else if (type == 'yxgj') {
          this.$router.push('/security/operation/confinedSpace')
        } else if (type == 'dt') {
          this.$router.push('/security/operation/groundBreaking')
        } else if (type == 'dl') {
          this.$router.push('/security/operation/roadBreaking')
        } else if (type == 'cdmb') {
          this.$router.push('/security/operation/blindPlate')
        } else {
          this.$router.push('/security/operation/temporaryElectrical')
        }
      },
      getAlertRecordInfo() {
        getAlertRecord().then(res => {
          this.alertRecord = res.data.data;
        });
      },

      getEnvironmentalCollectionPointsInfo(sort) {
        getEnvironmentalCollectionPoints({sort: sort}).then(res => {
          this.environmentalCollectionPoints = res.data;
        });
      },
      getEnvironmentalCollectionPointsCharts(tag) {
        getEnvironmentalCollectionPointsCharts({tag: tag}).then(res => {
          let DetectionEchartsXAxis= [];
          let DetectionEchartsData= [];
          res.data.forEach(item=>{
            DetectionEchartsXAxis.push(item.time);
            DetectionEchartsData.push(item.valueNum);
          });
          this.$set(this,'DetectionEchartsXAxis',DetectionEchartsXAxis);
          this.$set(this,'DetectionEchartsData',DetectionEchartsData);
        });
      },



      initMap() {
        // let parentw, parenth;
        // parentw = $('#mapid').parent().width();
        // parenth = $('#mapid').parent().height();
        // $('#mapid').width(parentw).height(parenth);

        let map = L.map('mapid', {
          renderer: L.svg(),
          crs: L.CRS.EPSG3857,
          center: [10.00, 10.00],
          zoom: 7,
          maxBounds: [[-10.0, -13.0], [29.00, 33.00]],
          maxZoom: 7,
          minZoom: 5,
          zoomSnap: 1,
          maxBoundsViscosity: 1.0,
          zoomControl: false
        });
        map.fitBounds([[0.0, 0.0], [20, 20.00]], {
          paddingTopLeft: 0
        });
        //禁止map拖动
        //map.dragging.disable();
        var imageUrl = '/img/security/jlpic.png',
          imageBounds = [[-10.0, -13.0], [29.00, 33.00]];
        L.imageOverlay(imageUrl, imageBounds).addTo(map);

        var icon = L.icon({
          iconUrl: '/img/security/marker-icon-red.png',
          iconSize: [24, 35],
          iconAnchor: [12, 35],
          popupAnchor: [-3, -76],
          tooltipAnchor: [-88, -35],
          shadowUrl: '/img/security/marker-shadow.png',
          shadowSize: [41, 41],
          shadowAnchor: [13, 40]
        });
        let that = this;
        this.points.forEach(value => {
          L.marker(L.latLng(value.lat, value.lng), {
            icon: icon,
            title: value.areaname
          }).addTo(map).on('click', function (e) {
            that.getEnvironmentalCollectionPointsInfo(value.areaname);
          });
        });
        // map.on('click', function(ev) {
        //   console.log('latlng', ev.latlng);
        // });
      }
    }
  }
</script>

<style lang="less" scoped>
  /deep/ .leaflet-right {
    display: none;
  }

  .main {
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    background-image: url("/img/security/bj.png");
    background-position: center;
    background-size: 100% 100%;
    background-repeat: no-repeat;
    //height: 100%;
    height: 100vh;
  }

  //头部
  .head {
    height: 10%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0% 2% 0.8%;

    .title {
      // font-size: 30px;
      font-size: 1.9rem;
      font-weight: bold;
      color: #fff;
    }

    .message-box {
      min-width: 15%;
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 26px;
      color: #57dbe5;
      font-weight: 500;
      height: 100%;

      span {
        display: inline-block;
        width: 2px;
        height: 20px;
        margin-top: 1%;
        background: #317a84;
      }
    }

    .time {
      min-width: 65%;
    }
  }

  //内容
  .content_container {
    box-sizing: border-box;
    padding: 0 10px 10px 20px;
    height: 90%;
  }

  //内容盒子
  .row-box {
    display: flex;
    height: 100%;
  }

  //第一列
  .column-one {
    width: 26%;
    box-sizing: border-box;
    padding: 0 1% 1%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  //小标题
  .small-title {
    height: 10%;
    display: flex;
    align-items: center;
    padding: 1% 0 0 3%;

    img {
      margin-right: 10px;
      // height: 60%;
      height: 1.5vh;
    }

    span {
      color: #5cdbe5;
    }
  }

  //本月优良天数
  .fine {
    height: 35%;
    background-image: url("/img/security/a01.png");
    background-size: 100% 100%;
    background-position: center;
  }

  //近七日指数变化
  .exponent {
    height: 32%;
    background-image: url("/img/security/a02.png");
    background-size: 100% 100%;
    background-position: center;
  }

  //超标次数
  .overproof {
    height: 28%;
    background-image: url("/img/security/a03.png");
    background-size: 100% 100%;
    background-position: center;
  }

  //第二列
  .column-tow {
    width: 48.8%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    box-sizing: border-box;
    padding: 0 0.8%;
  }

  //实时监测
  .monitor-box {
    height: 68%;
    background-image: url("/img/security/a07.png");
    background-size: 100% 100%;
    background-position: center;
  }

  .monitor {
    box-sizing: border-box;
    padding: 3% 2% 3%;
    height: 96%;
  }

  //安全
  .security-box {
    display: flex;
    justify-content: space-between;
    height: 30%;

    .job-slip-box {
      width: 49.5%;
      height: 100%;
      background-image: url("/img/security/a04.png");
      background-size: 100% 100%;
      background-position: center;

      .job-slip {
        height: 80%;
        box-sizing: border-box;
        padding: 5% 5% 0 5%;
        display: flex;
        align-items: center;
        flex-wrap: wrap;

        .project {
          width: 25%;
          text-align: center;
          position: relative;
          margin-bottom: 3%;

          div {
            position: absolute;
            left: 60%;
            top: -5%;
          }

          img {
            margin: 0;
            padding: 0;
            width: 54%;
            cursor: pointer;
          }

          p {
            margin: 0;
            padding: 0;
            color: #5cdbe5;
            font-size: 16px;
          }
        }
      }
    }

    .alarm-item-box {
      width: 49.5%;
      height: 100%;
      background-image: url("/img/security/a05.png");
      background-size: 100% 100%;
      background-position: center;
      //鼠标悬停样式
      .alarm-item-details:hover {
        background: #4f5a46;
        color: #eae284;
        cursor: pointer;
      }

      .alarm-item {
        display: flex;
        padding: 0;
        margin: 0 3% 2%;
        list-style-type: none;
        color: #5cdbe5;
        background: #01374f;
        font-size: 14px;

        li {
          padding: 1% 0;
          text-align: center;
        }

        li:nth-of-type(1) {
          width: 45%;
        }

        li:nth-of-type(2) {
          width: 35%;
        }

        li:nth-of-type(3) {
          width: 20%;
        }
      }
    }
  }

  //第三列
  .column-three {
    width: 26%;
    box-sizing: border-box;
    padding: 0 1%;

    .detection-box {
      padding: 0 1%;
      height: 100%;
      overflow: hidden;
      background-image: url("/img/security/a06.png");
      background-size: 100% 100%;
      background-position: center;
    }
  }

  //监测点检测
  .detection {
    display: flex;
    padding: 0;
    margin: 0 3% 2%;
    list-style-type: none;
    color: #5cdbe5;
    background: #01374f;
    font-size: 13px;

    li {
      padding: 1% 0;
      text-align: center;
    }

    li:nth-of-type(1) {
      width: 40%;
    }

    li:nth-of-type(2) {
      width: 20%;
    }

    li:nth-of-type(3) {
      width: 25%;
    }

    li:nth-of-type(4) {
      width: 15%;
    }

    li:nth-of-type(5) {
      width: 10%;
    }
  }

  //鼠标悬停增加背景色
  .detection-details:hover {
    background: #4f5a46;
    color: #eae284;
    cursor: pointer;
  }

  /*滚动条整体粗细样式*/
  ::-webkit-scrollbar {
    /*高宽分别对应横竖滚动条的尺寸*/
    width: 5px;
    height: 5px;
  }

  /*滚动条里面小方块*/
  ::-webkit-scrollbar-thumb {
    border-radius: 10px !important;
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2) !important;
    /* 颜色 */
    background: #42b5e9 !important;
  }

  /*滚动条轨道*/
  ::-webkit-scrollbar-track {
    border-radius: 10px !important;
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2) !important;
    background: #ededed !important;
  }
</style>
