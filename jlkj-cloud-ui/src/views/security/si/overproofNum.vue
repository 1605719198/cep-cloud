<template>
  <div style="height:100%;">
    <div class="box">
      <div>
        <div class="circle">
          <div class="circleIn">
            <span style=" margin: 0 auto;">{{ParticulateMatter}}</span>
          </div>
        </div>
        <div class="triangle"></div>
        <div class="title">颗粒物</div>
      </div>
      <div>
        <div class="circle">
          <div class="circleIn"
               style="border: 5px dashed #e86b56;color: #e86b56;">
            <span style=" margin: 0 auto;">{{SO2}}</span>
          </div>
        </div>
        <div class="triangle"></div>
        <div class="title">二氧化硫</div>
      </div>
      <div>
        <div class="circle">
          <div class="circleIn"
               style="border: 5px dashed #b7b74c;color: #b7b74c;">
            <span style=" margin: 0 auto;">{{NitrogenOxides}}</span>
          </div>
        </div>
        <div class="triangle"></div>
        <div class="title">氮氧化物</div>
      </div>
    </div>
  </div>
</template>
<script>
  import {getAlertRecordExceedance} from '@/api/security/si/index'

  export default {
    data() {
      return {
        ParticulateMatter:0,
        SO2:0,
        NitrogenOxides:0,
      }
    },
    mounted () {
      this.onLoad();
    },
    methods: {
      onLoad () {
        getAlertRecordExceedance().then(res => {
          if(res.data.data !== null){
            this.ParticulateMatter = res.data.data.ParticulateMatter;
            this.SO2 = res.data.data.SO2;
            this.NitrogenOxides = res.data.data.NitrogenOxides;
          }
        });
      }
    },
  }
</script>

<style lang="less" scoped>
  .box {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }

  .circle {
    margin: 0 auto;
    display: flex;
    align-items: center;
    width: 7.5vh;
    height: 7.5vh;
    border-radius: 50%;
    background: none;
    border: 1px solid #1cb5d4;
  }

  .circleIn {
    margin: 0 auto;
    display: flex;
    align-items: center;
    width: 6vh;
    height: 6vh;
    border-radius: 50%;
    background: none;
    border: 5px dashed #8ce6f9;
    color: #8ce6f9;

    span {
      font-size: 24px;
      font-weight: bold;
    }
  }

  .triangle {
    width: 0px;
    height: 0px;
    border: 8px solid transparent;
    border-top-color: #31cce8;
    margin: 0 auto;
    margin-top: 5px;
  }

  .title {
    width: 90%;
    margin: 0 auto;
    margin-top: 8px;
    padding: 2px 5px;
    border: 1px dashed #31cce8;
    font-size: 16px;
    font-weight: bold;
    text-align: center;
    color: #31cce8;
  }
</style>

