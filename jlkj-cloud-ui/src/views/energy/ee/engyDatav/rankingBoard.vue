<template>
  <div id="ranking-board">
    <el-carousel id="carousel" :interval="4000" arrow="always" class="el-carousel" indicator-position="none" :autoplay="true" @change="changeImg" :height="this.panel + 'px'">
      <el-carousel-item>
        <rankingBoardA :panel="panel"/>
      </el-carousel-item>
      <el-carousel-item>
        <rankingBoardB :panel="panel"/>
      </el-carousel-item>
      <el-carousel-item>
        <rankingBoardC :panel="panel"/>
      </el-carousel-item>
      <el-carousel-item>
        <rankingBoardD :panel="panel"/>
      </el-carousel-item>
      <el-carousel-item>
        <rankingBoardE :panel="panel"/>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>

import bus from '../engyDatav/js/eventBus.js';
import rankingBoardA from '../engyDatav/rankingBoardA.vue'
import rankingBoardB from '../engyDatav/rankingBoardB.vue'
import rankingBoardC from '../engyDatav/rankingBoardC.vue'
import rankingBoardD from '../engyDatav/rankingBoardD.vue'
import rankingBoardE from '../engyDatav/rankingBoardE.vue'
export default {
  name: 'RankingBoard',
  data () {
    return {
      panel:400
    }
  },
  components :{
    rankingBoardA,
    rankingBoardB,
    rankingBoardC,
    rankingBoardD,
    rankingBoardE,
  },
  methods: {
    changeImg(index) {
      bus.$emit('changeImg',index)
    },

  },
  mounted() {
    window.addEventListener('resize', () => {
      setTimeout(()=>{
        if(document.getElementById('ranking-board') != null){
          let bottom = document.getElementById('ranking-board').offsetHeight;
          this.panel = bottom-60
        }
      } ,100)
    });
  }
}
</script>

<style lang="less" scoped>
#ranking-board {
  width: 100%;
  height: 103%;
  box-shadow: 0 0 3px blue;
  display: flex;
  flex-direction: column;
  background-color: #07255a;
  border-top: 2px solid rgba(1, 153, 209, .5);
  box-sizing: border-box;
  padding: 0px 30px;
  justify-content: center;
}

::v-deep .el-carousel__arrow {
  height: 165px;
  font-size: 30px;
}
</style>
