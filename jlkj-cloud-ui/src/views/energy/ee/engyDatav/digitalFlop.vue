<template>
  <div id="digital-flop">
    <div
      class="digital-flop-item-active0" id="flop1"
    >
      <div class="top">
        <img src="../engyDatav/img/电.png">
        <span style="color: #05aa35;">电</span>
        <span @click="handle" style="cursor:pointer; margin-left: auto; color: #0dd0fe; font-size: 15px">查看详情</span>
      </div>
      <span style="border-top: 2px solid black; width: 100%; padding-bottom: 3%;"></span>
      <div class="digital-flop">
        <dv-digital-flop
          :config="{
            number: [engyTop.sum1==null ? 0 : engyTop.sum1],
            content: '{nt}',
            textAlign: 'left',
            style: {
              fill: '#069f39',
              fontWeight: 'normal',
              fontSize: '35'
            },
            formatter:function(number) {
              const numbers = number.toString().split('').reverse()
              const segs = []

              while (numbers.length) segs.push(numbers.splice(0, 3).join(''))

              return segs.join(',').split('').reverse().join('')
            },
          }"
          style="width:155px;height:80px;"
        />
<!--          <div class="unit">{{ item.unit }}</div>-->
        <img id="image1" class="image" src="../engyDatav/img/07_统计分析拷贝2.png">
      </div>
      <div class="digital-flop-title">当前日耗</div>
      <div style="display: flex; width: 80%;flex-wrap: wrap;">
        <div class="digital-flop-title2">电力-峰</div>
        <div class="digital-flop-title3">{{engyTop["电力-峰"]==null ? 0 : engyTop["电力-峰"]}}</div>
        <div class="digital-flop-title2">电力-谷</div>
        <div class="digital-flop-title3">{{engyTop["电力-谷"]==null ? 0 : engyTop["电力-谷"]}}</div>
        <div class="digital-flop-title2">电力-平</div>
        <div class="digital-flop-title3">{{engyTop["电力-平"]==null ? 0 : engyTop["电力-平"]}}</div>
      </div>
    </div>


    <div
      class="digital-flop-item" id="flop2"
    >
      <div class="top">
        <img src="../engyDatav/img/水.png">
        <span style="color: #0de0ff;">水</span>
        <span @click="handle" style="cursor:pointer; margin-left: auto; color: #0dd0fe; font-size: 15px">查看详情</span>
      </div>
      <span style="border-top: 2px solid black; width: 100%; padding-bottom: 3%;"></span>
      <div class="digital-flop">
        <dv-digital-flop
          :config="{
            number: [engyTop.sum2==null ? 0 : engyTop.sum2],
            content: '{nt}',
            textAlign: 'left',
            style: {
              fill: '#0dcefe',
              fontWeight: 'normal',
              fontSize: '35'
            },
            formatter:function(number) {
              const numbers = number.toString().split('').reverse()
              const segs = []

              while (numbers.length) segs.push(numbers.splice(0, 3).join(''))

              return segs.join(',').split('').reverse().join('')
            },
          }"
          style="width:155px;height:80px;"
        />
        <!--          <div class="unit">{{ item.unit }}</div>-->
        <img id="image2" class="image" src="../engyDatav/img/07_统计分析拷贝.png">
      </div>
      <div class="digital-flop-title">当前日耗</div>
      <div style="display: flex; width: 80%;flex-wrap: wrap;">
        <div class="digital-flop-title2">生产消防水</div>
        <div class="digital-flop-title3">{{engyTop.生产消防水==null ? 0 : engyTop.生产消防水}}</div>
        <div class="digital-flop-title2">除盐水</div>
        <div class="digital-flop-title3">{{engyTop.除盐水==null ? 0 : engyTop.除盐水}}</div>
        <div class="digital-flop-title2">生活水</div>
        <div class="digital-flop-title3">{{engyTop.生活水==null ? 0 : engyTop.生活水}}</div>
      </div>
    </div>


    <div
      class="digital-flop-item" id="flop3"
    >
      <div class="top">
        <img src="../engyDatav/img/动力气体.png">
        <span style="color: #ff088e;">动力气体</span>
        <span @click="handle" style="cursor:pointer; margin-left: auto; color: #0dd0fe; font-size: 15px">查看详情</span>
      </div>
      <span style="border-top: 2px solid black; width: 100%; padding-bottom: 3%;"></span>
      <div class="digital-flop">
        <dv-digital-flop
          :config="{
            number: [engyTop.sum3==null ? 0 : engyTop.sum3],
            content: '{nt}',
            textAlign: 'left',
            style: {
              fill: '#ff088e',
              fontWeight: 'normal',
              fontSize: '35'
            },
            formatter:function(number) {
              const numbers = number.toString().split('').reverse()
              const segs = []

              while (numbers.length) segs.push(numbers.splice(0, 3).join(''))

              return segs.join(',').split('').reverse().join('')
            },
          }"
          style="width:155px;height:80px;"
        />
        <!--          <div class="unit">{{ item.unit }}</div>-->
        <img id="image3" class="image" src="../engyDatav/img/07_统计分析拷贝.png">
      </div>
      <div class="digital-flop-title">当前日耗</div>
      <div style="display: flex; width: 80%;flex-wrap: wrap;">
        <div class="digital-flop-title2">压缩空气</div>
        <div class="digital-flop-title3">{{engyTop.压缩空气==null ? 0 : engyTop.压缩空气}}</div>
        <div class="digital-flop-title2"> </div>
        <div class="digital-flop-title3"> </div>
        <div class="digital-flop-title2"></div>
        <div class="digital-flop-title3"></div>
      </div>
    </div>


    <div
      class="digital-flop-item" id="flop4"
    >
      <div class="top">
        <img src="../engyDatav/img/燃气.png">
        <span style="color: #ff8e00;">燃气</span>
        <span @click="handle" style="cursor:pointer; margin-left: auto; color: #0dd0fe; font-size: 15px">查看详情</span>
      </div>
      <span style="border-top: 2px solid black; width: 100%; padding-bottom: 3%;"></span>
      <div class="digital-flop">
        <dv-digital-flop
          :config="{
            number: [engyTop.sum4==null ? 0 : engyTop.sum4],
            content: '{nt}',
            textAlign: 'left',
            style: {
              fill: '#ff8d00',
              fontWeight: 'normal',
              fontSize: '35'
            },
            formatter:function(number) {
              const numbers = number.toString().split('').reverse()
              const segs = []

              while (numbers.length) segs.push(numbers.splice(0, 3).join(''))

              return segs.join(',').split('').reverse().join('')
            },
          }"
          style="width:155px;height:80px;"
        />
        <!--          <div class="unit">{{ item.unit }}</div>-->
        <img id="image4" class="image" src="../engyDatav/img/07_统计分析拷贝.png">
      </div>
      <div class="digital-flop-title">当前日耗</div>
      <div style="display: flex; width: 80%;flex-wrap: wrap;">
        <div class="digital-flop-title2">焦炉煤气</div>
        <div class="digital-flop-title3">{{engyTop.焦炉煤气==null ? 0 : engyTop.焦炉煤气}}</div>
        <div class="digital-flop-title2"></div>
        <div class="digital-flop-title3"></div>
        <div class="digital-flop-title2"></div>
        <div class="digital-flop-title3"></div>
      </div>
    </div>


    <div
      class="digital-flop-item" id="flop5"
    >
      <div class="top">
        <img src="../engyDatav/img/水蒸汽.png">
        <span style="color: #537be0;">蒸汽</span>
        <span @click="handle" style="cursor:pointer; margin-left: auto; color: #0dd0fe; font-size: 15px">查看详情</span>
      </div>
      <span style="border-top: 2px solid black; width: 100%; padding-bottom: 3%;"></span>
      <div class="digital-flop">
        <dv-digital-flop
          :config="{
            number: [engyTop.sum5==null ? 0 : engyTop.sum5],
            content: '{nt}',
            textAlign: 'left',
            style: {
              fill: '#537be0',
              fontWeight: 'normal',
              fontSize: '35'
            },
            formatter:function(number) {
              const numbers = number.toString().split('').reverse()
              const segs = []

              while (numbers.length) segs.push(numbers.splice(0, 3).join(''))

              return segs.join(',').split('').reverse().join('')
            },
          }"
          style="width:155px;height:80px;"
        />
        <!--          <div class="unit">{{ item.unit }}</div>-->
        <img id="image5" class="image" src="../engyDatav/img/07_统计分析拷贝.png">
      </div>
      <div class="digital-flop-title">当前日耗</div>
      <div style="display: flex; width: 80%;flex-wrap: wrap;">
        <div class="digital-flop-title2">低压蒸汽</div>
        <div class="digital-flop-title3">{{engyTop.低压蒸汽==null ? 0 : engyTop.低压蒸汽}}</div>
        <div class="digital-flop-title2">超高压蒸汽</div>
        <div class="digital-flop-title3">{{engyTop.超高压蒸汽==null ? 0 : engyTop.超高压蒸汽}}</div>
        <div class="digital-flop-title2">蒸汽回收量</div>
        <div class="digital-flop-title3">{{engyTop.蒸汽回收量==null ? 0 : engyTop.蒸汽回收量}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import bus from '../engyDatav/js/eventBus.js'
import srcAct from '../engyDatav/img/07_统计分析拷贝2.png'
import srcNormal from '../engyDatav/img/07_统计分析拷贝.png'
import {getEngyTopAnalys} from "@/api/energy/ee/engyboard";
export default {
  name: 'DigitalFlop',
  data () {
    return {
      digitalFlopData: [],
      engyTop:{}
    }
  },
  created() {
    getEngyTopAnalys().then(res=>{
      this.engyTop = res.data.engyTop;
    })
  },
  methods: {
    // createData () {
    //   const { randomExtend } = this
    //
    //   this.digitalFlopData = [
    //     {
    //       title: '当前日耗',
    //       number: {
    //         number: [randomExtend(20000000, 30000000)],
    //         content: '{nt}',
    //         textAlign: 'left',
    //         style: {
    //           fill: '#069f39',
    //           fontWeight: 'normal',
    //           fontSize: '35'
    //         },
    //         formatter:function(number) {
    //           const numbers = number.toString().split('').reverse()
    //           const segs = []
    //
    //           while (numbers.length) segs.push(numbers.splice(0, 3).join(''))
    //
    //           return segs.join(',').split('').reverse().join('')
    //         },
    //       },
    //       unit: '公里'
    //     },
    //     {
    //       title: '当前日耗',
    //       number: {
    //         number: [randomExtend(20, 30)],
    //         content: '{nt}',
    //         textAlign: 'right',
    //         style: {
    //           fill: '#f46827',
    //           fontWeight: 'bold'
    //         }
    //       },
    //       unit: '座'
    //     },
    //     {
    //       title: '当前日耗',
    //       number: {
    //         number: [randomExtend(20, 30)],
    //         content: '{nt}',
    //         textAlign: 'right',
    //         style: {
    //           fill: '#40faee',
    //           fontWeight: 'bold'
    //         }
    //       },
    //       unit: '个'
    //     },
    //     {
    //       title: '当前日耗',
    //       number: {
    //         number: [randomExtend(10, 20)],
    //         content: '{nt}',
    //         textAlign: 'right',
    //         style: {
    //           fill: '#4d99fc',
    //           fontWeight: 'bold'
    //         },
    //       },
    //       unit: '个'
    //     },
    //     {
    //       title: '当前日耗',
    //       number: {
    //         number: [randomExtend(5, 10)],
    //         content: '{nt}',
    //         textAlign: 'right',
    //         style: {
    //           fill: '#f46827',
    //           fontWeight: 'bold'
    //         }
    //       },
    //       unit: '个'
    //     }
    //   ]
    // },
    handle (){
      //  路径/home对应我在router目录下index.js中定义的path属性值
      this.$router.push('/home');
    },
    randomExtend (minNum, maxNum) {
      if (arguments.length === 1) {
        return parseInt(Math.random() * minNum + 1, 10)
      } else {
        return parseInt(Math.random() * (maxNum - minNum + 1) + minNum, 10)
      }
    }
  },
  mounted () {

    // const { createData } = this
    //
    // createData()
    //
    // setInterval(createData, 30000)

    bus.$on('changeImg',data=>{

      var flop1 = document.getElementById( "flop1")
      var flop2 = document.getElementById( "flop2")
      var flop3 = document.getElementById( "flop3")
      var flop4 = document.getElementById( "flop4")
      var flop5 = document.getElementById( "flop5")
      var image1 = document.getElementById( "image1")
      var image2 = document.getElementById( "image2")
      var image3 = document.getElementById( "image3")
      var image4 = document.getElementById( "image4")
      var image5 = document.getElementById( "image5")

      switch (data){
        case 0:
          flop5.className = "digital-flop-item";
          image5.src = srcNormal
          flop1.className = "digital-flop-item-active0";
          image1.src = srcAct
          flop2.className = "digital-flop-item";
          image2.src = srcNormal
          break;
        case 1:
          flop1.className = "digital-flop-item";
          image1.src = srcNormal
          flop2.className = "digital-flop-item-active1";
          image2.src = srcAct
          flop3.className = "digital-flop-item";
          image3.src = srcNormal
          break;
        case 2:
          flop2.className = "digital-flop-item";
          image2.src = srcNormal
          flop3.className = "digital-flop-item-active2";
          image3.src = srcAct
          flop4.className = "digital-flop-item";
          image4.src = srcNormal
          break;
        case 3:
          flop3.className = "digital-flop-item";
          image3.src = srcNormal
          flop4.className = "digital-flop-item-active3";
          image4.src = srcAct
          flop5.className = "digital-flop-item";
          image5.src = srcNormal
          break;
        case 4:
          flop4.className = "digital-flop-item";
          image4.src = srcNormal
          flop5.className = "digital-flop-item-active4";
          image5.src = srcAct
          flop1.className = "digital-flop-item";
          image1.src = srcNormal
          break;
      }

    })
  }
}
</script>

<style lang="less">
#digital-flop {
  position: relative;
  height: 31.5vh;
  flex-shrink: 0;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  background-color: rgba(6, 30, 93, 0.5);
  margin: 0px 1%;

  .top {
    display: flex;
    align-items: center;
    width: 90%;
  }

  .dv-decoration-10 {
    position: absolute;
    width: 95%;
    left: 2.5%;
    height: 5px;
    bottom: 0px;
  }

  .digital-flop-item {
    width: 18%;
    height: 31.5vh;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    border-left: 3px solid rgb(6, 30, 93);
    border-right: 3px solid rgb(6, 30, 93);
  }

  .digital-flop-item-active0 {
    width: 18%;
    height: 31.5vh;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    border-left: 3px solid rgb(6, 30, 93);
    border-right: 3px solid rgb(6, 30, 93);
    box-shadow: 0 0 25px #05a038;
  }

  .digital-flop-item-active1 {
    width: 18%;
    height: 31.5vh;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    border-left: 3px solid rgb(6, 30, 93);
    border-right: 3px solid rgb(6, 30, 93);
    box-shadow: 0 0 25px #0dcefe;
  }

  .digital-flop-item-active2 {
    width: 18%;
    height: 31.5vh;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    border-left: 3px solid rgb(6, 30, 93);
    border-right: 3px solid rgb(6, 30, 93);
    box-shadow: 0 0 25px #ff088e;
  }

  .digital-flop-item-active3 {
    width: 18%;
    height: 31.5vh;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    border-left: 3px solid rgb(6, 30, 93);
    border-right: 3px solid rgb(6, 30, 93);
    box-shadow: 0 0 25px #ff8e00;
  }

  .digital-flop-item-active4 {
    width: 18%;
    height: 31.5vh;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    border-left: 3px solid rgb(6, 30, 93);
    border-right: 3px solid rgb(6, 30, 93);
    box-shadow: 0 0 25px #0600ff;
  }

  .digital-flop-title {
    font-size: 18px;
    margin-bottom: 9%;
    color: rgb(12, 204, 252);
    width: 80%;
  }

  .digital-flop-title2 {
    width: 60%;
    color: rgb(12, 204, 252);
    padding-bottom: 4%;
  }
  .digital-flop-title3 {
    width: 30%;
  }

  .digital-flop {
    display: flex;
    width: 80%;
  }

  .image {
    width: 70px;
    height: 70px;
    padding-top: 5%;
    padding-left: 5%;
  }
}
</style>
