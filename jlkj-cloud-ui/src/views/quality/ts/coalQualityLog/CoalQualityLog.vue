<template>
  <el-row>
    <el-col :span="24" style="padding:0 10px">
      <div class="energy_header">
        <el-row>
          <el-col :span="7">
            <div class="energy_header_title">质量记录</div>
          </el-col>
          <el-col :span="10">
            <div style="text-align: center ; min-width: 500px">
              <el-radio-group v-model="tabPosition">
                <el-radio-button label="tabA">配合煤质量记录</el-radio-button>
                <el-radio-button label="tabB">入炉煤质量记录</el-radio-button>
              </el-radio-group>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="energy_main">
        <IncomingCoalQuality v-if="tabPosition==='tabA'"/>
        <LoadingCoalQuality v-else-if="tabPosition==='tabB'"/>
        <div v-else></div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import IncomingCoalQuality from "./IncomingCoalQuality"
import LoadingCoalQuality from "./LoadingCoalQuality"

export default {
  name: "coalQualityLog",
  components: {
    IncomingCoalQuality,
    LoadingCoalQuality
  },
  data() {
    return {
      tabPosition: 'tabA'
    }
  },
  created() {
  },
  methods: {
  }
}
</script>

<style scoped>
.energy_header {
  background-color: #F9F9F9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #E9E9E9 solid;
}

.energy_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold
}

.energy_main {
  height: 80vh;
  background-color: #FFF;
}
</style>

<style>
.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
/* 弹窗圆角 */
.el-dialog{
  border-radius: 8px;
}
/* 标题样式 */
.el-dialog__header{
  background-color: rgb(245, 245, 245);
  border-radius: 8px 8px 0 0;
  padding: 10px 20px 10px;
}
/* 标题文字样式 */
.el-dialog__title{
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  letter-spacing: normal;
  font-size: 14px;
  font-weight: 700;
  font-style: normal;
  text-align: left;
  line-height: 20px;
  border-width: 0;
  color: rgb(102, 102, 102);
}
/* 关闭符号位置调整 */
.el-dialog__headerbtn{
  top:12px
}

.el-dialog__body{
  padding: 15px 40px;
}
</style>
