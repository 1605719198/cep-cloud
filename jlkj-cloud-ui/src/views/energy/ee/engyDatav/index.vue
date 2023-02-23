<template>
  <div id="data-view">
    <div id="full-screen-container">

      <top-header />

      <div class="main-content">
        <digital-flop />

        <div class="block-left-right-content">
          <ranking-board />

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import topHeader from './topHeader'
import digitalFlop from './digitalFlop'
import rankingBoard from './rankingBoard'

export default {
  name: 'DataView',
  components: {
    topHeader,
    digitalFlop,
    rankingBoard,
  },
  data () {
    return {}
  },
  methods: {},
}
</script>

<style lang="less">



#data-view {
  width: 100%;
  height: 100%;
  background-color: #030409;
  color: #fff;

  #full-screen-container {
    height: 100%;
    background-image: url('./img/bg-1.png');
    background-size: 100% 100%;
    box-shadow: 0 0 3px blue;
    display: flex;
    flex-direction: column;
  }

  .main-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    height: calc(100% - 100px);
  }

  .block-left-right-content {
    flex: 1;
    display: flex;
    margin-top: 20px;
    height: 58%;
  }

  .block-top-bottom-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
    padding-left: 20px;
  }

  .block-top-content {
    height: 55%;
    display: flex;
    flex-grow: 0;
    box-sizing: border-box;
    padding-bottom: 20px;
  }
}

</style>
