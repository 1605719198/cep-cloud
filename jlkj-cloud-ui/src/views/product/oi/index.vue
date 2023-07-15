<template>
    <div class="main">
        <div class="head">
            <div class="head-time">
                <div style="height:48%;"></div>
                <!-- <div class="time-box">
                    <div>时间</div>
                    <el-date-picker class="aaa"
                                    style="width:31%"
                                    v-model="start_time"
                                    size="mini"
                                    type="date"
                                    value-format="yyyy-MM-dd"
                                    format="yyyy/MM/dd"
                                    placeholder="选择日期">
                    </el-date-picker>
                    <div>
                        至
                    </div>
                    <el-date-picker style="width:31%"
                                    v-model="end_time"
                                    size="mini"
                                    type="date"
                                    value-format="yyyy-MM-dd"
                                    format="yyyy/MM/dd"
                                    placeholder="选择日期">
                    </el-date-picker>
                    <div>
                        <el-button @click="onLoad"
                                   size="mini">确认
                        </el-button>
                    </div>
                </div> -->
                <div class="time-box" style="justify-content: space-between;">
                    <div style="margin-left: 5%;color: #fff;">
                       {{currentDate}}&nbsp;&nbsp;&nbsp;&nbsp;
                       {{week}}&nbsp;&nbsp;&nbsp;&nbsp;
                       {{currentTime}}
                    </div>
                </div>
            </div>
            <div class="head-title">
                <img src="/img/wel/jlogo.png"
                     alt="">
                吉林建龙150万吨焦电集控平台-生产看板
            </div>
            <div class="head-weather">
                <div style="height:60%;"></div>
                <div v-if="city">
                    <span>{{city}}</span>
                    <span>&nbsp;&nbsp; / &nbsp;&nbsp;</span>
                    <span>{{weather}}</span>
                    <span>&nbsp;&nbsp; / &nbsp;&nbsp;</span>
                    <span>{{temperature}}</span>
                    <span>&nbsp;&nbsp; / &nbsp;&nbsp;</span>
                    <span>{{windDirection}}{{windPower}}</span>
                    <span>&nbsp;&nbsp; / &nbsp;&nbsp;</span>
                    <span>{{humidity}}</span>
                </div>
            </div>
        </div>
        <div class="content_container">
            <div class="column_box">
                <div class="column_one">
                    <div class="yield">
                        <div class="headline"
                             style="height: 20%;">
                            收率(%)
                        </div>
                        <div class="yield_echarts_box">
                            <div @mouseenter="mouseenter(1)"
                                 @mouseleave="mouseleave"
                                 class="yield_echarts"
                                 style="width:25%;height:80%;position: absolute; left: 8%; top:16%;">
                                <YieldEcharts chartId='YieldEcharts'
                                              :chartData="yieldData[0]"
                                              :colorlist="['#30dfef','#2e78d7', '#0f1e40']"
                                              ref="YieldEcharts" />
                                <div class="yield_echarts_title">{{yieldData[0].name}}</div>
                            </div>
                            <!-- <div class="popover">全焦率(干基)=全焦产量(1-水分)/入炉煤重*(1-水分)*100 %</div> -->
                            <div @mouseenter="mouseenter(2)"
                                 @mouseleave="mouseleave"
                                 class="yield_echarts"
                                 style="width:25%;height:80%;position: absolute;  left: 38%; top:16%;">
                                <YieldEcharts chartId='YieldEcharts2'
                                              :colorlist="['#e3a544','#f77a4a', '#28222f']"
                                              :chartData="yieldData[1]"
                                              ref="YieldEcharts2" />
                                <div class="yield_echarts_title"
                                     style="left:26%;">{{yieldData[1].name}}
                                </div>
                            </div>
                            <!-- <div class="popover">冶金焦率=冶金焦量/全焦产量*100 %</div> -->
                            <div @mouseenter="mouseenter(3)"
                                 @mouseleave="mouseleave"
                                 class="yield_echarts"
                                 style="width:25%;height:80%;position: absolute; right: 8%;top:16%;">
                                <YieldEcharts chartId='YieldEcharts3'
                                              :colorlist="['#e34677','#f75e59', '#271b33']"
                                              :chartData="yieldData[2]"
                                              ref="YieldEcharts3" />
                                <div class="yield_echarts_title"
                                     style="left:26%;">{{yieldData[2].name}}
                                </div>
                            </div>
                            <!-- <div class="popover">干熄焦率=干熄炉数/总炉数*100%</div> -->
                            <transition name="el-fade-in-linear">
                                <div class="popover"
                                     v-show="show">{{explain}}
                                </div>
                            </transition>
                        </div>
                    </div>
                    <div class="gxj_target">
                        <div class="headline"
                             style="height:7.5%;margin: 0 0 7.5% 9%;">
                            干熄焦技术指标
                        </div>
                        <div class="progress_box">
                            <span>主蒸汽压力(Mpa)</span>
                            <div>
                                <el-progress style="width: 86%;margin-right: 3%;"
                                             :show-text="false"
                                             :percentage="percentageObj.steamPressure"
                                             :stroke-width="15">
                                </el-progress>
                                <span>{{cokeTechnologyIndexData.main_steam_pressure}}</span>
                            </div>
                        </div>
                        <div class="progress_box">
                            <span>主蒸汽流量(t/h)</span>
                            <div>
                                <el-progress style="width: 86%;margin-right: 3%;"
                                             :show-text="false"
                                             :percentage="percentageObj.steamevaporation"
                                             :stroke-width="15"></el-progress>
                                <span>{{cokeTechnologyIndexData.main_steam_evaporation}}</span>
                            </div>
                        </div>
                        <div class="progress_box">
                            <span>排焦温度(°C)</span>
                            <div>
                                <el-progress style="width: 86%;margin-right: 3%;"
                                             :show-text="false"
                                             :percentage="percentageObj.exhausttemp"
                                             :stroke-width="15"></el-progress>
                                <span>{{cokeTechnologyIndexData.exhaust_temp}}</span>
                            </div>
                        </div>
                        <div class="progress_box">
                            <span>T5(°C)</span>
                            <div>
                                <el-progress style="width: 86%;margin-right: 3%;"
                                             :show-text="false"
                                             :percentage="percentageObj.t5temp"
                                             :stroke-width="15"></el-progress>
                                <span>{{cokeTechnologyIndexData.t5_temp}}</span>
                            </div>
                        </div>
                        <div class="progress_box">
                            <span>T6(°C)</span>
                            <div>
                                <el-progress style="width: 86%;margin-right: 3%;"
                                             :show-text="false"
                                             :percentage="percentageObj.t6temp"
                                             :stroke-width="15"></el-progress>
                                <span>{{cokeTechnologyIndexData.t6_temp}}</span>
                            </div>
                        </div>
                        <div class="progress_box">
                            <span>循环风量(Nm3/h)</span>
                            <div>
                                <el-progress style="width: 86%;margin-right: 3%;"
                                             :show-text="false"
                                             :percentage="percentageObj.airVolume"
                                             :stroke-width="15"></el-progress>
                                <span>{{cokeTechnologyIndexData.air_volume}}</span>
                            </div>
                        </div>
                        <div class="progress_box">
                            <span>循环气体CO(%)</span>
                            <div>
                                <el-progress style="width: 86%;margin-right: 3%;"
                                             :show-text="false"
                                             :percentage="percentageObj.gasCo"
                                             :stroke-width="15"></el-progress>
                                <span>{{cokeTechnologyIndexData.gas_co}}</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="column_two">
                    <div class="today_yield">
                        <div class="headline"
                             style="height: 14.3%;margin: 0 0 5% 0;">
                            <div style="margin-left: 7%;">今日产量(t)</div>
                            <div style="margin-right: 2.5%;color: #00fffc;">炉数:{{stoveCount}}</div>
                        </div>
                        <div class="today_yield_content">
                            <div class="kind">
                                <div class="num">{{coke1Sum}}</div>
                                <div class="name">干熄焦({{gxStoveCount}}炉)</div>
                                <div class="level">
                                    <p>一级: {{coke11}}</p>
                                    <p>准一级: {{coke12}}</p>
                                    <p>二级: {{coke13}}</p>
                                </div>
                            </div>
                            <div class="kind">
                                <div class="num">{{coke2Sum}}</div>
                                <div class="name">湿熄焦({{sxStoveCount}}炉)</div>
                                <div class="level">
                                    <p>一级: {{coke21}}</p>
                                    <p>准一级: {{coke22}}</p>
                                    <p>二级: {{coke23}}</p>
                                </div>
                            </div>
                            <div class="kind">
                                <div class="num">{{coke3Sum}}</div>
                                <div class="name">冶金焦</div>
                                <div class="level">
                                    <p>一级: {{coke31}}</p>
                                    <p>准一级: {{coke32}}</p>
                                    <p>二级: {{coke33}}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="aggregate_consumption">
                        <div class="headline"
                             style="height: 18%;margin-left: 7%;">
                            总耗用量
                        </div>
                        <div class="each_out_box">
                            <div class="each_box">
                                <div class="total">
                                    <img src="/img/production/micon.png"
                                         alt="">
                                    <div>
                                        <p>当日耗煤量</p>
                                        <span>{{consumption.coal}}t</span>
                                    </div>
                                </div>
                                <div class="one">
                                    <img src="/img/production/mlicon.png"
                                         alt="">
                                    <div>
                                        <p>当日单吨煤耗</p>
                                        <span>{{consumption.ton_coal}}t</span>
                                    </div>
                                </div>
                            </div>
                            <div class="each_box">
                                <div class="total">
                                    <img src="/img/production/sicon.png"
                                         alt="">
                                    <div>
                                        <p>昨日耗水量</p>
                                        <span>{{consumption.water}}t</span>
                                    </div>
                                </div>
                                <div class="one">
                                    <img src="/img/production/slicon.png"
                                         alt="">
                                    <div>
                                        <p>昨日单吨水耗</p>
                                        <span>{{consumption.ton_water}}t</span>
                                    </div>
                                </div>
                            </div>
                            <div class="each_box">
                                <div class="total">
                                    <img src="/img/production/dicon.png"
                                         alt="">
                                    <div>
                                        <p>昨日耗电量</p>
                                        <span>{{consumption.electricity}}kwh</span>
                                    </div>
                                </div>
                                <div class="one">
                                    <img src="/img/production/dlicon.png"
                                         alt="">
                                    <div>
                                        <p>昨日单吨电耗</p>
                                        <span>{{consumption.ton_electricity}}kwh</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="quantity_box">
                        <div class="jp_stock">
                            <div class="headline"
                                 style="height: 16%; margin: 0; justify-content: center;">
                                焦棚库存量(t)
                            </div>
                            <div class="jp_stock_echarts">
                                <JiaoPengInventory ref="JiaoPengInventory"
                                                   :proportionData="proportionData" />
                            </div>
                        </div>
                        <div class="jt_forwarding">
                            <div class="headline"
                                 style="height: 16%; margin: 0; justify-content: center;">
                                焦炭发运量(t)
                            </div>
                            <div class="jt_forwarding_echarts">
                                <CokeForwardingQuantity chartId="CokeForwardingQuantity"
                                                        :legendList="['内耗','外发']"
                                                        :xAxisData="xData"
                                                        :oneData="interiorData"
                                                        :twoData="outData"
                                                        ref="CokeForwardingQuantity" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="column_three">
                    <div class="steam_yield">
                        <div class="headline"
                             style="height: 20%;margin-left: 9%;">
                            昨日蒸汽产量(t)
                        </div>
                        <div class="steam_yield_content">
                            <div class="steam_content_box">
                                <img src="/img/production/clicon.png"
                                     alt="">
                                <div>
                                    <p>{{steam.yield}}</p>
                                    <span>蒸汽产出量</span>
                                </div>
                            </div>
                            <div class="steam_content_box">
                                <img src="/img/production/hsicon.png"
                                     alt="">
                                <div>
                                    <p>{{steam.retrieve.toFixed(2)}}</p>
                                    <span>再热蒸汽量</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="jl_target">
                        <div class="headline"
                             style="height: 7.5%;margin-left: 9%;">
                            焦炉技术指标数据
                        </div>
                        <div class="jl_target_content_box">
                            <ul class="jl_target_title">
                                <li>炉号</li>
                                <li>碳化室温度</li>
                                <li>四联拱温度(机/焦)</li>
                                <li>总管吸力</li>
                            </ul>
                            <div class="jl_target_content">
                                <ul v-for="(item,index) in cokeOvenTechnicalIndexData"
                                    :key="index">
                                    <li>{{item.coke_oven_number}}#炉</li>
                                    <li>{{Math.round(item.coke_temp_chamber)}}°C</li>
                                    <li>{{Math.round(item.coke_temp_side)}}/{{Math.round(item.coke_temp_Focal)}}°C</li>
                                    <li>{{Math.round(item.main_suction)}}Pa</li>
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
    getProductionHomeStatistics,
    getTodayYield,
    getTodayConsumption,
    getTodayCokeStock,
    getTodayCokeShipment,
    getTodayCokeSteam,
    getCraftCokeTemp,
    getCokeTotalYield,
    getCokeTechnologyIndex
} from '@/api/production/oi/index'
import moment from 'moment'
import YieldEcharts from './echarts/yieldEcharts'
import CokeForwardingQuantity from './echarts/cokeForwardingQuantity'
import JiaoPengInventory from './echarts/jiaoPengInventory'

export default {
    components: {
        YieldEcharts,
        JiaoPengInventory,
        CokeForwardingQuantity,
    },
    data() {
        return {
            show: false,
            explain: '',
            start_time: moment().subtract(0, 'day').format('YYYY-MM-DD'),
            end_time: moment().subtract(0, 'day').format('YYYY-MM-DD'),
            currentDate: moment().format('YYYY年MM月DD日'),
            weekNum: moment().day(),
            currentTime: moment().format('h时mm分ss秒'),
            city: '',
            humidity: '',
            weather: '',
            windDirection: '',
            windPower: '',
            temperature: '',
            yieldData: [
                { name: '全焦率', value: 0 },
                { name: '冶金焦率', value: 0 },
                { name: '干熄焦率', value: 0 },
            ],
            xData: [],
            interiorData: [],
            outData: [],
            stoveCount: 0,
            coke1Sum: 0.0,
            coke2Sum: 0.0,
            coke3Sum: 0.0,
            coke11: 0.0,
            coke12: 0.0,
            coke13: 0.0,
            coke21: 0.0,
            coke22: 0.0,
            coke23: 0.0,
            coke31: 0.0,
            coke32: 0.0,
            coke33: 0.0,
            gxStoveCount:0,
            sxStoveCount:0,
            consumption: {
                coal: 0.0,
                water: 0.0,
                electricity: 0.0,
                ton_coal: 0.0,
                ton_water: 0.0,
                ton_electricity: 0.0,
            },
            proportionData: [],
            steam: {
                yield: 0.0,
                retrieve: 0.0,
            },
            cokeOvenTechnicalIndexData: [],
            cokeTechnologyIndexData:{},
            percentageObj:{
                steamPressure:0,
                steamevaporation:0,
                exhausttemp:0,
                t5temp:0,
                t6temp:0,
                airVolume:0,
                gasCo:0,
            }
        }
    },
    computed:{
        week(){
          return this.weekNum ==1?'星期一':
          this.weekNum ==2?'星期二' :
          this.weekNum ==3?'星期三' :
          this.weekNum ==4?'星期四' :
          this.weekNum ==5?'星期五' :
          this.weekNum ==6?'星期六' : '星期日'
        }
    },
    created() {
        this.getlocation()
        this.onLoad()
    },
    mounted() {
        let _this = this;// 声明一个变量指向Vue实例this，保证作用域一致
        this.timer = setInterval(() => {
            _this.currentDate = moment().format('YYYY年MM月DD日')
            _this.weekNum = moment().day()
            _this.currentTime =  moment().format('h时mm分ss秒')
        }, 1000)
        //监听窗口大小变化
        window.addEventListener('resize', this.listenResize)
    },
    //摧毁组件
    beforeDestroy() {
        if (this.timer) {
            clearInterval(this.timer); // 在Vue实例销毁前，清除我们的定时器
        }
        //移除监听
        window.removeEventListener('resize', this.listenResize)
    },
    methods: {
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
                            var weather = new AMap.Weather()
                            //执行实时天气信息查询
                            weather.getLive(result.city, function (err, data) {
                                // console.log(err, data);
                                _this.city = data.city
                                _this.humidity = data.humidity + '%'
                                _this.weather = data.weather
                                _this.windDirection = data.windDirection
                                _this.windPower = data.windPower + '级'
                                _this.temperature = data.temperature + '℃'
                            })
                        })
                    }
                })
            })
        },
        //窗口变化处理事件
        listenResize() {
            setTimeout(() => {
                this.$refs.YieldEcharts.onresize()
                this.$refs.YieldEcharts2.onresize()
                this.$refs.YieldEcharts3.onresize()
                this.$refs.JiaoPengInventory.onresize()
                this.$refs.CokeForwardingQuantity.onresize()
            }, 200)
        },
        onLoad() {
            this.getYield()
            this.getTodayYield()
            this.getTodayCokeStock()
            this.getTodayCokeShipment()
            this.getTodayCokeSteam()
            this.getCokeOvenTechnicalIndexData()
            this.getCokeTechnologyIndexData()
        },
        //鼠标移入
        mouseenter(i) {
            if (i === 1) {
                this.explain =
                    '全焦率(干基)=全焦产量(1-水分)/入炉煤重*(1-水分)*100 %'
            } else if (i === 2) {
                this.explain = '冶金焦率=冶金焦量/全焦产量*100 %'
            } else {
                this.explain = '干熄焦率=干熄炉数/总炉数*100%'
            }
            this.show = true
        },
        //鼠标移除
        mouseleave() {
            this.show = false
        },
        //收率
        getYield() {
            let params = {
                start_date: this.start_time,
                end_date: this.end_time,
            }
            getCokeTotalYield(params).then(res=>{
                console.log(res.data, '收率')
                if(res.data.code == 0){
                    res.data.data.forEach(item=>{
                        if(item.name == '全焦率' && item.value){
                          this.yieldData[0]=item
                        }
                        if(item.name == '冶金焦率' && item.value){
                          this.yieldData[1]=item
                        }
                        if(item.name == '干熄焦率' && item.value){
                          this.yieldData[2]=item
                        }
                    })
                }
            })
            // getProductionHomeStatistics(params).then((res) => {
            //     console.log(res.data, '收率')
            //     let data = res.data
            //     this.yieldData[0].value = data.full_coke_rate
            //         ? data.full_coke_rate
            //         : 0
            //     this.yieldData[2].value = data.coke_dry_quenching_rate
            //         ? data.coke_dry_quenching_rate
            //         : 0
            // })
        },
        //生产看板- 干熄焦技术指标
        getCokeTechnologyIndexData(){
            let params = {
                start_date: this.start_time,
                end_date: this.end_time,
            }
            getCokeTechnologyIndex(params).then(res=>{
                console.log(res,'干熄焦技术指标')
                if(res.data.code==0){
                    let data = res.data.data[0]
                    if(data){
                        this.cokeTechnologyIndexData=data
                        this.percentageObj.steamPressure = data.main_steam_pressure/1500*100 > 100 ? 100 : data.main_steam_pressure/1500*100
                        this.percentageObj.steamevaporation = data.main_steam_evaporation/500*100 > 100 ? 100: data.main_steam_evaporation/500*100
                        this.percentageObj.exhausttemp = data.exhaust_temp/800*100 > 100 ? 100 : data.exhaust_temp/800*100
                        this.percentageObj.t5temp = data.t5_temp/800*100 > 100 ? 100 : data.t5_temp/800*100
                        this.percentageObj.t6temp = data.t6_temp/800*100 > 100 ? 100 : data.t6_temp/800*100
                        this.percentageObj.airVolume = data.air_volume/500*100 > 100 ? 100 : data.air_volume/500*100
                        this.percentageObj.gasCo = data.gas_co/500*100 > 100 ? 100 : data.gas_co/500*100
                    }
                }
            })
        },
        //今日产量
        getTodayYield() {
            let that = this
            let params = {
                start_date: this.start_time,
                end_date: this.end_time,
            }
            getTodayYield(params).then((res) => {
                console.log(res.data, '今日产量')
                let data = res.data.data
                this.stoveCount = data.stoveCount
                this.coke1Sum = data.coke1Sum
                this.coke2Sum = data.coke2Sum
                this.coke3Sum = data.coke3Sum
                this.gxStoveCount = data.gxStoveCount
                this.sxStoveCount = data.sxStoveCount
                if (data.coke1.length > 0) {
                    data.coke1.forEach((item) => {
                        if (item.materials_code === '0150104')
                            that.coke11 = item.weight
                        if (item.materials_code === '0150105')
                            that.coke12 = item.weight
                        if (item.materials_code === '0150106')
                            that.coke13 = item.weight
                    })
                }
                if (data.coke2.length > 0) {
                    data.coke2.forEach((item) => {
                        if (item.materials_code === '0150104')
                            that.coke21 = item.weight
                        if (item.materials_code === '0150105')
                            that.coke22 = item.weight
                        if (item.materials_code === '0150106')
                            that.coke23 = item.weight
                    })
                }
                if (data.coke3.length > 0) {
                    data.coke3.forEach((item) => {
                        if (item.materials_code === '0150104')
                            that.coke31 = item.weight
                        if (item.materials_code === '0150105')
                            that.coke32 = item.weight
                        if (item.materials_code === '0150106')
                            that.coke33 = item.weight
                    })
                }
                this.getTodayConsumption()
            })
        },
        //总耗用量
        getTodayConsumption() {
            let params = {
                start_date: moment().subtract(1, 'day').format('YYYY-MM-DD'),
                end_date: moment().subtract(1, 'day').format('YYYY-MM-DD'),
            }
            getTodayConsumption(params).then((res) => {
                console.log(res.data, '总耗用量')
                if(res.data.code == 0){
                  this.consumption= res.data.data
                }
                // this.consumption.coke = data.coke
                // this.consumption.electricity = data.electricity
                // this.consumption.water = data.water
                // if (this.coke3Sum > 0) {
                //     this.consumption.coke_ton = data.coke / this.coke3Sum
                //     this.consumption.electricity_ton =
                //         data.electricity / this.coke3Sum
                //     this.consumption.water_ton = data.water / this.coke3Sum
                // }
            })
        },
        //焦棚库存量
        getTodayCokeStock() {
            let params = {
                start_date: this.start_time,
                end_date: this.end_time,
            }
            getTodayCokeStock(params).then((res) => {
                console.log(res.data, '焦棚库存量')
                this.proportionData = res.data.data
            })
        },
        //焦炭发运量
        getTodayCokeShipment() {
            let that = this
            let params = {
                start_date: this.start_time,
                end_date: this.end_time,
            }
            getTodayCokeShipment(params).then((res) => {
                console.log(res.data, '焦炭发运量')
                let data = res.data.data
                let xData = data.coke
                let interiorData = xData.map((i) => 0.0)
                let outData = xData.map((i) => 0.0)
                data.internal.forEach((item) => {
                    interiorData[xData.indexOf(item.materials_name)] =
                        (item.weight/1000).toFixed(2)
                })
                data.outgoing.forEach((item) => {
                    outData[xData.indexOf(item.materials_name)] = (item.weight/1000).toFixed(2)
                })
                that.xData = xData
                that.interiorData = interiorData
                that.outData = outData
            })
        },
        //蒸汽产量
        getTodayCokeSteam() {
            let params = {
                start_date: moment().subtract(1, 'day').format('YYYY-MM-DD'),
                end_date: moment().subtract(1, 'day').format('YYYY-MM-DD'),
            }
            getTodayCokeSteam(params).then((res) => {
                console.log(res.data, '蒸汽产量')
                let data = res.data.data
                this.steam.yield = data.yield ? data.yield : 0
                this.steam.retrieve = data.retrieve ? data.retrieve : 0
            })
        },
        //焦炉技术指标数据
        getCokeOvenTechnicalIndexData() {
            let params = {
                start_date: this.start_time,
                end_date: this.end_time,
            }
            getCraftCokeTemp(params).then((res) => {
                console.log(res, '焦炉技术指标数据')
                if (res.data.code == 0) {
                    this.cokeOvenTechnicalIndexData = res.data.data
                }
            })
        },
    },
}
</script>

<style lang="scss" scoped>
.main {
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    // background-image: url("/img/production/scx.png");
    background-image: url('/img/wel/bg3.png');
    background-position: center;
    background-size: 100% 100%;
    background-repeat: no-repeat;
    height: 100%;
    // height: 100vh;
}

.head {
    box-sizing: border-box;
    display: flex;
    justify-content: space-between;
    height: 8.5%;

    .head-time {
        width: 24%;
        height: 100%;

        .time-box {
            height: 45%;
            margin-left: 2%;
            color: #a9baf3;
            display: flex;
            justify-content: space-around;
            align-items: center;
        }
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

        img {
            height: 80%;
            vertical-align: middle;
        }
    }

    .head-weather {
        width: 27%;
        text-align: center;
        color: #fff;
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

.content_container {
    box-sizing: border-box;
    padding: 2% 1.2% 1%;
    width: 100%;
    height: 91.5%;

    .column_box {
        display: flex;
        justify-content: space-between;
        height: 100%;

        .headline {
            color: #fff;
            font-size: 1.2rem;
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-left: 10%;
        }

        .column_one {
            width: 29.5%;
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: space-between;

            .yield {
                width: 100%;
                height: 27.5%;
                background-image: url('/img/production/sl.png');
                background-size: 100% 100%;
                background-position: center;
                position: relative;

                .yield_echarts_box {
                    height: 80%;
                    position: relative;
                }

                .yield_echarts {
                    font-size: 1rem;
                }

                .popover {
                    width: 68%;
                    height: 38%;
                    padding: 0 3%;
                    background-image: url('/img/production/gs.png');
                    background-size: 100% 100%;
                    background-position: center;
                    color: #fff;
                    font-size: 0.8rem;
                    position: absolute;
                    top: -24%;
                    right: 0.5%;
                    z-index: 1000;
                    // display: none;
                    display: flex;
                    align-items: center;
                }

                .yield_echarts_title {
                    color: #fff;
                    position: absolute;
                    top: 70%;
                    left: 32%;
                }

                // .yield_echarts:hover + .popover {
                //   display: flex;
                //   align-items: center;
                // }
                .yield_echarts:hover .yield_echarts_title {
                    color: #00eaff;
                }
            }

            .gxj_target {
                width: 100%;
                height: 70%;
                background-image: url('/img/production/gxj.png');
                background-size: 100% 100%;
                background-position: center;
                color: #fff;
            }

            .progress_box {
                height: 10%;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                margin-bottom: 1.8%;
                box-sizing: border-box;
                padding: 0% 4%;

                span {
                    font-size: 1.1rem;
                }

                div {
                    display: flex;
                    align-items: center;
                }
            }

          ::v-deep .el-progress-bar__inner {
                background: linear-gradient(to right, #056afc, #0cdbf9);
            }

          ::v-deep .el-progress-bar__outer {
                background: #093661;
            }

          ::v-deep .el-progress__text {
                color: #fff;
            }
        }

        .column_two {
            width: 37%;
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: space-between;

            .today_yield {
                width: 100%;
                height: 38.1%;
                background-image: url('/img/production/jlcl.png');
                background-size: 100% 100%;
                background-position: center;

                .today_yield_content {
                    height: 78%;
                    display: flex;
                    justify-content: space-between;
                    box-sizing: border-box;
                    padding: 0% 4% 0%;
                }

                .kind {
                    width: 28%;
                    height: 100%;
                    display: flex;
                    flex-direction: column;
                    // justify-content: space-between;
                    color: #fff;

                    .num {
                        height: 34%;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        background-image: url('/img/production/clbg.png');
                        background-size: 100% 100%;
                        background-position: center;
                        font-size: 1.5rem;
                    }

                    .name {
                        // text-align: center;
                        height: 22%;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                    }

                    .level {
                        height: 34%;
                        border: #4c6791 solid 1px;
                        background-color: rgba(18, 52, 98, 0.7);
                        display: flex;
                        flex-direction: column;
                        justify-content: space-around;
                        box-sizing: border-box;
                        padding: 2% 0 2%;

                        p {
                            margin: 0 0 0 8%;
                            font-size: 0.9rem;
                        }
                    }
                }
            }

            .aggregate_consumption {
                width: 100%;
                height: 28%;
                background-image: url('/img/production/zhl.png');
                background-size: 100% 100%;
                background-position: center;

                .each_out_box {
                    height: 82%;
                    display: flex;
                    justify-content: space-between;
                    box-sizing: border-box;
                    padding: 3% 4% 3%;
                }

                .each_box {
                    width: 30%;
                    height: 100%;
                    display: flex;
                    flex-direction: column;
                    justify-content: space-between;
                    color: #fff;

                    .total,
                    .one {
                        width: 100%;
                        height: 42.5%;
                        display: flex;
                        align-items: center;

                        img {
                            margin: 0 8%;
                            height: 70%;
                        }

                        p {
                            margin: 0;
                        }

                        span {
                            font-size: 1.2rem;
                            color: #00eaff;
                        }
                    }

                    .total {
                        background-image: url('/img/production/zhbg.png');
                        background-size: 100% 100%;
                        background-position: center;
                    }

                    .one {
                        background-image: url('/img/production/ddbg.png');
                        background-size: 100% 100%;
                        background-position: center;
                    }
                }
            }

            .quantity_box {
                width: 100%;
                height: 30%;
                display: flex;
                justify-content: space-between;

                .jp_stock {
                    width: 48.95%;
                    height: 100%;
                    background-image: url('/img/production/jpkcl.png');
                    background-size: 100% 100%;
                    background-position: center;

                    .jp_stock_echarts {
                        height: 84%;
                    }
                }

                .jt_forwarding {
                    width: 48.95%;
                    height: 100%;
                    background-image: url('/img/production/jpkcl.png');
                    background-size: 100% 100%;
                    background-position: center;

                    .jt_forwarding_echarts {
                        height: 84%;
                    }
                }
            }
        }

        .column_three {
            width: 31%;
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: space-between;

            .steam_yield {
                width: 100%;
                height: 27.5%;
                background-image: url('/img/production/zqcl.png');
                background-size: 100% 100%;
                background-position: center;

                .steam_yield_content {
                    height: 80%;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    padding: 0 7% 0;
                    box-sizing: border-box;
                    color: #fff;

                    .steam_content_box {
                        width: 46%;
                        height: 60.5%;
                        background-image: url('/img/production/ccbg.png');
                        background-size: 100% 100%;
                        background-position: center;
                        display: flex;
                        align-items: center;

                        img {
                            height: 45%;
                            margin: 0 10% 0;
                        }

                        p {
                            margin: 0 0 5% 0;
                            font-size: 1.2rem;
                        }

                        span {
                            color: #93aacb;
                        }
                    }
                }
            }

            .jl_target {
                width: 100%;
                height: 70%;
                background-image: url('/img/production/jljsbg.png');
                background-size: 100% 100%;
                background-position: center;
                color: #fff;

                .jl_target_content_box {
                    height: 90%;
                    box-sizing: border-box;
                    padding: 2%;

                    ul {
                        height: 11%;
                        display: flex;
                        padding: 0;
                        margin: 0;
                        list-style-type: none;
                        background-image: url('/img/production/r1.png');
                        background-size: 100% 100%;
                        background-position: center;
                        margin-bottom: 1.5%;
                        font-size: 1.1rem;
                    }

                    li {
                        text-align: center;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                    }

                    li:nth-of-type(1) {
                        width: 15%;
                    }

                    li:nth-of-type(2) {
                        width: 30%;
                    }

                    li:nth-of-type(3) {
                        width: 30%;
                    }

                    li:nth-of-type(4) {
                        width: 27%;
                    }
                }

                .jl_target_content {
                    height: 8.8%;

                    ul {
                        height: 100%;
                        background: #0b2e5f;
                        margin-bottom: 2%;
                        border-bottom: #044ee5 dashed 2px;
                        font-size: 1rem;
                    }

                    li:nth-of-type(1) {
                        background: #0c4594;
                        border-radius: 0 25px 0 0;
                    }
                }
            }
        }
    }
}
</style>
