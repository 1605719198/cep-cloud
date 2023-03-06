<template>
  <div class="box">
    <div class="bg">
      <div class="title">区 域 配 置</div>

      <div class="content" v-if="data.length > 0">
        <div class="leftTitle">
          <div class="d1"><span>煤&nbsp;&nbsp;场</span></div>
          <div class="d2"><span>品&nbsp;&nbsp;名</span></div>
          <div class="d3"><span>库&nbsp;&nbsp;存（吨）</span></div>
        </div>
        <div class="rightContent">
          <template v-for="(item, index) in data">
            <div class="rc" :key="index">
              <div class="rc1">
                <div class="mq">煤 区</div>
                <!--                <div class="mqh">{{item.storage_spaces_name}}</div>-->
              </div>
              <div class="rc2">
                <div class="image" :style="svgStyle">
                  <img src="/svg/region.svg" width="70%"
                    :style="'filter: drop-shadow(' + item.color_value + ' 145px 0)'" />
                </div>
                <div>{{ item.storage_spaces_name }}</div>
              </div>
              <div class="rc3">
                <div class="tb">
                  <div :style="list[index]"></div>
                </div>
                <div class="tbValue">{{ item.storage_quantity }}</div>
              </div>
            </div>
          </template>
        </div>
      </div>
      <div class="nullTips" v-else>
        {{ form.silo_name }}未配置区域
      </div>

    </div>
    <el-select v-model="form.silo_id" placeholder="选 择 料 仓" class="region-select" :popper-append-to-body="false">
      <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"
        @click.native="changeSilo(item)">
      </el-option>
    </el-select>
    <el-button v-hasPermi="['listMaterialsSiloArea']" class="button region-button" @click="openDialog = true">
      <div>配置</div>
      <i class="el-icon-s-operation"></i>
    </el-button>
    <div v-if="openDialog">
      <el-dialog :title="form.silo_name + '-区域配置'" :visible.sync="openDialog" width="1280px"
        class="region customDialogStyle" append-to-body :destroy-on-close="true" :close-on-click-modal="false">
        <template>
          <regionConfigure :siloId="form.silo_id" :siloLength="form.silo_length" @close="openDialog = false"
            @submitSave="submitSave" />
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import '@/assets/font/font.css';
import regionConfigure from "../mr/regionConfigure";
import { listMaterialsSiloArea, listSilosBox } from "@/api/material/mr/region";

export default {
  name: "region",
  components: { regionConfigure },
  data() {
    return {
      svgStyle: 'padding-top: 25%;',
      data: [],
      list: [],
      form: { silo_id: "", silo_name: "", silo_length: "" },
      openDialog: false,
      options: [],
      value: ''
    }
  },
  mounted() {
    let that = this;
    listSilosBox().then(res => {
      that.form = {
        silo_id: res.data[0].id,
        silo_name: res.data[0].silo_name,
        silo_length: res.data[0].silo_length + 'm'
      };
      let options = [];
      res.data.forEach(item => {
        let i = { value: item.id, label: item.silo_name, silo_length: item.silo_length };
        options.push(i)
      });
      this.options = options;
      this.onLoad();
    });

  },
  methods: {
    onLoad() {
      listMaterialsSiloArea({ silo_id: this.form.silo_id, show_zero: 0 }).then(res => {
        res.data.length;
        this.data = res.data;
        let count = res.data.length;
        if (count > 10) {
          this.svgStyle = 'padding-top: ' + (30 + (count - 10) * 5) + '%;'
        }
        this.updateStorageQuantity();
      }, error => {
        window.console.log(error);
      });
    },
    updateStorageQuantity() {
      let max = 0;
      this.data.forEach(val => {
        if (max < val.storage_quantity)
          max = val.storage_quantity;
      });
      let list = [];
      this.data.forEach(val => {
        let n = val.storage_quantity / max * 20;
        list.push({ height: n + 'vh' });
      });
      this.list = [...list];
    },
    submitSave() {
      this.openDialog = false;
    },
    changeSilo(item) {
      this.onLoad();
      this.form.silo_name = item.label;
      this.form.silo_length = item.silo_length;
    }
  },
  watch: {
    openDialog(val) {
      if (!val) {
        this.onLoad();
      }
    }

  }
}
</script>

<style scoped>
.box {
  position: relative;
  padding-bottom: 0;
}

.bg {
  background: url("/img/material/region_bg.png") no-repeat;
  background-size: 100% auto;
  width: 100%;
  height: 100vh;
  display: flex !important;
  flex-direction: column;
}

.title {
  flex: 15;
  width: 100%;
  height: 15vh;
  min-height: 125px;
  text-align: center;
  color: #0bebff;
  font-size: 3.33vh;
  line-height: 15vh;
}

.content {
  width: 96%;
  flex: 85;
  display: flex !important;
  margin: 3vh 2vw 0 2vw;
}

.leftTitle {
  flex: 1;
  height: 70vh;
  background: url("/img/material/rectangle.png") no-repeat;
  background-size: 100% 100%;
  padding: 2px;
  margin-left: 2px;
  display: flex !important;
  flex-direction: column;
  text-align: center;
  color: #03f3f1;
}

.leftTitle .d1,
.leftTitle .d1 span {
  height: 13vh;
}

.leftTitle .d2,
.leftTitle .d2 span {
  height: 25vh;
}

.leftTitle .d3,
.leftTitle .d3 span {
  height: 32vh;
}

.leftTitle div span {
  writing-mode: vertical-lr;
  font-size: 3vh;
  text-align: center;
}

.rightContent {
  flex: 17;
  display: flex !important;
  flex-direction: row;
  height: 72vh;
  overflow: hidden;
  overflow-x: auto;
}

.rc {
  flex: 1;
  height: 70vh;
  border: 2px solid #005eb4;
  margin-left: -2px;
  width: calc(5.32vw - 2px);
  min-width: 100px;
  max-width: 150px;
  text-align: center;
}

.rc1 {
  height: calc(13vh - 2px);
  line-height: 3vh;
  font-weight: bold;
  border-bottom: 2px solid #005eb4;
}

.rc1 .mq {
  color: #39c5ff;
  font-size: 2vh;
  padding-top: 3vh;
}

.rc1 .mqh {
  margin-top: 1vh;
  margin-left: 15%;
  width: 70%;
  height: 3vh;
  font-size: 1.8vh;
  background-color: #034e94;
  color: #fff;
}

.rc2 {
  height: calc(25vh - 2px);
  border-bottom: 2px solid #005eb4;
  color: #00f9f6;
  font-size: 18px;
  font-weight: bold;
}

.image {
  display: inline-block;
  overflow: hidden;
  padding-top: 70%;
  margin-bottom: 20%;
}

.image img {
  position: relative;
  left: -160px;
  filter: drop-shadow(#00f9f6 145px 0);
  border-left: 30px solid transparent;
  border-right: 30px solid transparent;
}

.rc3 {
  height: 32vh;
  color: #fff;
  position: relative;
}

.tb {
  position: absolute;
  bottom: 25%;
  left: 25%;
  width: 50%;
  background-color: red;
  /* 针对不支持渐变的浏览器 */
  background-image: linear-gradient(#838fff, #4174ff);
}

/*过渡动画*/
.tb div {
  transition-property: height;
  transition-duration: 2s;
  -moz-transition-property: height;
  /* Firefox 4 */
  -moz-transition-duration: 2s;
  /* Firefox 4 */
  -webkit-transition-property: height;
  /* Safari and Chrome */
  -webkit-transition-duration: 2s;
  /* Safari and Chrome */
  -o-transition-property: height;
  /* Opera */
  -o-transition-duration: 2s;
  /* Opera */
}

.tbValue {
  font-family: digital7, 微软雅黑, sans-serif;
  font-size: 17px;
  letter-spacing: 2px;
  position: absolute;
  width: 100%;
  bottom: 13%;
}

/*滚动条样式*/
::-webkit-scrollbar-track-piece {
  background-color: transparent;
}

::-webkit-scrollbar {
  width: 7px;
  height: 7px;
  background-color: transparent;
}

::-webkit-scrollbar-thumb {
  border-radius: 5px;
  background-color: hsla(220, 4%, 58%, 0.3);
}

.region-button {
  position: absolute;
  width: 150px;
  height: 53px;
  background: url("/img/material/region-button.png") no-repeat;
  right: 5vw;
  top: 3vh;
  font-size: 30px;
  padding-left: 30px;
}

.region-button div {
  float: left;
  font-size: 24px;
  color: white;
  margin-right: 10px;
}

.region-button i {
  float: left;
  color: #0bebff;
}

.el-button {
  border: 0;
}

.region-select {
  position: absolute;
  width: 219px;
  background: url("/img/material/region-select.png") no-repeat;
  right: 15vw;
  top: 3vh;
  font-size: 30px;
}

.el-select-dropdown {
  background: url("/img/material/region-select.png") no-repeat !important;
}

.nullTips {
  width: 100%;
  flex: 85;
  line-height: 65vh;
  text-align: center;
  color: #0bebff;
  font-size: 3.33vh;
}
</style>
<style>
.region-select .el-input__inner {
  background-color: rgba(0, 0, 0, 0) !important;
  height: 53px;
  font-size: 20px;
  font-weight: bold;
  color: #0bebff;
  border: 0;
}

.region-select .el-select-dropdown {
  border: 1px solid #0766bd;
  background-color: rgba(0, 0, 0, 0.5) !important;
}

.region-select .el-select-dropdown__item {
  color: #fff !important;
}

.region-select .el-select-dropdown__item.hover,
.region-select .el-select-dropdown__item:hover,
.region-select .selected,
.region-select .selected:hover {
  background-color: rgba(7, 102, 189, 0.5) !important;
}

.region-select .el-popper[x-placement^="bottom"] .popper__arrow::after {
  top: 0;
  border-bottom-color: #0766bd !important;
}

.region-select .el-popper[x-placement^="bottom"] .popper__arrow {
  border-bottom-color: #0766bd !important;
}

.region-select .el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
