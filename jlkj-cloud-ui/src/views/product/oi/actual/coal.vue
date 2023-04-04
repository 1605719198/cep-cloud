<!--煤焦生产实绩的home页面-->
<template>
  <el-row>
    <el-col :span="24" style="padding:0 10px">
      <div class="cokecoking_header" style="padding-top: 5px;">
        <el-row>
          <el-col :span="7">
            <div class="cokecoking_header_title">煤焦实绩</div>
          </el-col>
          <el-col :span="10">
            <div style="text-align: center; min-width: 600px">
              <el-radio-group v-model="tabPosition">
                <el-radio-button label="XC">卸车实绩</el-radio-button>
                <el-radio-button label="SMJL">上煤记录</el-radio-button>
                <el-radio-button label="SM">上煤实绩</el-radio-button>
                <el-radio-button label="DQLJ">堆取料机实绩</el-radio-button>
                <el-radio-button label="PM">配煤实绩</el-radio-button>
                <el-radio-button label="JJBCC">交接班仓存核验</el-radio-button>
              </el-radio-group>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="cokecoking_main">
        <unloadActual v-if="tabPosition==='XC'"></unloadActual>
        <UpCoalRecord v-else-if="tabPosition==='SMJL'"></UpCoalRecord>
        <coalingActual v-else-if="tabPosition==='SM'"></coalingActual>
        <stackerReclaimerActual v-else-if="tabPosition==='DQLJ'">DQLJ</stackerReclaimerActual>
        <coalBlendingActual v-else-if="tabPosition==='PM'">PMdiv</coalBlendingActual>
        <storageVerify v-else-if="tabPosition==='JJBCC'">JJBCC</storageVerify>
        <div v-else></div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import role from "@/views/system/role";
import unloadActual from "./coal/unloadActual";
import UpCoalRecord from "./coal/upCoalRecord/index";
import coalingActual from "./coal/coalingActual";
import stackerReclaimerActual from "./coal/stackerReclaimerActual";
import coalBlendingActual from "./coal/coalBlendingActual";
import storageVerify from "./coal/storageVerify";

export default {
  name: "coalActual",
  components: {
    role,
    unloadActual,
    UpCoalRecord,
    coalingActual,
    stackerReclaimerActual,
    coalBlendingActual,
    storageVerify
  },
  data () {
    return {
      tabPosition: 'XC'
    };
  },
  computed: {
  },
  created () {
  },
  methods: {
    handleChange (val) {
      console.log(val);
    }
  }
};
</script>

<style scoped>
.el-card__body {
  padding: 10px 10px 20px 20px;
}

.cokecoking_header {
  background-color: #f9f9f9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #e9e9e9 solid;
}

.cokecoking_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold;
}

.cokecoking_main {
  height: 83vh;
  background-color: #fff;
}
</style>
<style >
.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}

/* 弹窗圆角 */
.el-dialog {
  border-radius: 8px;
}

/* 标题样式 */
.el-dialog__header {
  background-color: rgb(245, 245, 245);
  border-radius: 8px 8px 0 0;
  padding: 10px 20px 10px;
}

/* 标题文字样式 */
.el-dialog__title {
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
.el-dialog__headerbtn {
  top: 12px;
}

.el-dialog__body {
  padding: 15px 40px;
}
</style>
