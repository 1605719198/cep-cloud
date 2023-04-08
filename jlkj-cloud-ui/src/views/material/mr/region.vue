<template>
  <div class="box">
    <div class="bg">
      <div class="title">区 域 配 置</div>
      <div class="content" v-if="data.length > 0">
        <div class="leftTitle">
          <div class="d1">
            <el-button v-hasPermi="['listMaterialsSiloArea']" class="button region-button"
                       @click="openDialog = true,coal_sort = 1">
              <div>煤排1</div>
              <i class="el-icon-s-operation"/>
            </el-button>
          </div>
          <div class="d1">
            <el-button v-hasPermi="['listMaterialsSiloArea']" class="button region-button"
                       @click="openDialog = true,coal_sort = 2">
              <div>煤排2</div>
              <i class="el-icon-s-operation"/>
            </el-button>
          </div>
          <div class="d1">
            <el-button v-hasPermi="['listMaterialsSiloArea']" class="button region-button"
                       @click="openDialog = true,coal_sort = 3">
              <div>煤排3</div>
              <i class="el-icon-s-operation"/>
            </el-button>
          </div>
          <div class="d1">
            <el-button v-hasPermi="['listMaterialsSiloArea']" class="button region-button"
                       @click="openDialog = true,coal_sort = 4">
              <div>煤排4</div>
              <i class="el-icon-s-operation"/>
            </el-button>
          </div>
        </div>
        <div class="rightContent">
          <template v-for="i in count">
            <div class="rc">
              <div class="rc1">
                <template v-if="data1.length > i-1">
                  <div class="image" :style="svgStyle">
                    <img src="/svg/region.svg" width="50%" alt=""
                         :style="'filter: drop-shadow(' + data1[i-1].color_value + ' 145px 0)'"/>
                  </div>
                  <div>{{ data1[i-1].materials_small_name }}</div>
                </template>
              </div>
              <div class="rc1">
                <template v-if="data2.length > i-1">
                  <div class="image" :style="svgStyle">
                    <img src="/svg/region.svg" width="50%" alt=""
                         :style="'filter: drop-shadow(' + data2[i-1].color_value + ' 145px 0)'"/>
                  </div>
                  <div>{{ data2[i-1].materials_small_name }}</div>
                </template>
              </div>
              <div class="rc1">
                <template v-if="data3.length > i-1">
                  <div class="image" :style="svgStyle">
                    <img src="/svg/region.svg" width="50%" alt=""
                         :style="'filter: drop-shadow(' + data3[i-1].color_value + ' 145px 0)'"/>
                  </div>
                  <div>{{ data3[i-1].materials_small_name }}</div>
                </template>
              </div>
              <div class="rc1" style="border: 0">
                <template v-if="data4.length > i-1">
                  <div class="image" :style="svgStyle">
                    <img src="/svg/region.svg" width="50%" alt=""
                         :style="'filter: drop-shadow(' + data4[i-1].color_value + ' 145px 0)'"/>
                  </div>
                  <div>{{ data4[i-1].materials_small_name }}</div>
                </template>
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
    <div v-if="openDialog">
      <el-dialog :title="form.silo_name + '-区域配置'" :visible.sync="openDialog" width="1280px"
                 class="region customDialogStyle" append-to-body :destroy-on-close="true" :close-on-click-modal="false">
        <template>
          <regionConfigure :siloId="form.silo_id" :siloLength="form.silo_length" :coalSort="coal_sort"
                           @close="openDialog = false"
                           @submitSave="submitSave"/>
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
    components: {regionConfigure},
    data() {
      return {
        svgStyle: 'padding-top: 25%;',
        data: [],
        data1: [],
        data2: [],
        data3: [],
        data4: [],
        count: 0,

        list: [],
        form: {silo_id: "", silo_name: "", silo_length: ""},
        openDialog: false,
        coal_sort: 0,
        options: [],
        value: '',
      }
    },
    mounted() {
      let that = this;
      listSilosBox().then(res => {
        that.form = {
          silo_id: res.data[0].id,
          silo_name: res.data[0].silo_name,
          silo_length: res.data[0].silo_length
        };
        let options = [];
        res.data.forEach(item => {
          let i = {value: item.id, label: item.silo_name, silo_length: item.silo_length};
          options.push(i)
        });
        this.options = options;
        this.onLoad();
      });

    },
    methods: {
      onLoad() {
        let that = this;
        listMaterialsSiloArea({silo_id: this.form.silo_id, show_zero: 0}).then(res => {
          let data = res.data;
          this.data = data;
          this.data1 = [];
          this.data2 = [];
          this.data3 = [];
          this.data4 = [];
          data.forEach(item => {
            switch (item.coal_sort) {
              case 1:
                that.data1.push(item);
                break;
              case 2:
                that.data2.push(item);
                break;
              case 3:
                that.data3.push(item);
                break;
              case 4:
                that.data4.push(item);
                break;
              default:
                break;
            }
          });
          let max = [that.data1.length, that.data2.length, that.data3.length, that.data4.length];
          console.log(max)
          max.sort(function (a, b) {
            return a - b
          });
          that.count = max[max.length - 1];
        }, error => {
          window.console.log(error);
        });
      },
      submitSave() {
        this.openDialog = false;
        this.coal_sort = 0;
      },
      changeSilo(item) {
        this.onLoad();
        this.form.silo_id = item.value;
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
    height: 72vh;
    background: url("/img/material/rectangle.png") no-repeat;
    background-size: 100% 100%;
    padding: 2px;
    margin-left: 2px;
    display: flex !important;
    flex-direction: column;
    text-align: center;
    color: #03f3f1;
  }

  .leftTitle .d1 {
    height: 18vh;
    line-height: 18vh;
  }

  .leftTitle div span {
    height: 9vh;
    line-height: 9vh;
    font-size: 3vh;
    text-align: center;
  }

  .rightContent {
    flex: 17;
    display: flex !important;
    flex-direction: row;
    height: 72vh;
    /*overflow: hidden;*/
    /*overflow-x: auto;*/
  }

  .rc {
    flex: 1;
    height: 72vh;
    border: 2px solid #005eb4;
    margin-left: -2px;
    width: calc(5.32vw - 2px);
    min-width: 100px;
    max-width: 150px;
    text-align: center;
  }

  .rc1 {
    height: calc(18vh - 2px);
    border-bottom: 2px solid #005eb4;
    color: #00f9f6;
    font-size: 18px;
    font-weight: bold;
  }


  .image {
    display: inline-block;
    overflow: hidden;
    padding-top: 70%;
  }

  .image img {
    position: relative;
    left: -150px;
    filter: drop-shadow(#00f9f6 145px 0);
    border-left: 30px solid transparent;
    border-right: 30px solid transparent;
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
    width: 150px;
    height: 53px;
    background: url("/img/material/region-button.png") no-repeat;
    font-size: 30px;
    border: 0;
    padding-left: 27px;
  }

  .region-button div {
    float: left;
    text-align: center;
    font-size: 24px;
    color: #03f3f1;
    margin-top: 2px;
    margin-right: 10px;
  }

  .region-button i {
    float: left;
    color: #0bebff;
  }


  .region-select {
    position: absolute;
    width: 219px;
    background: url("/img/material/region-select.png") no-repeat;
    right: 5vw;
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
