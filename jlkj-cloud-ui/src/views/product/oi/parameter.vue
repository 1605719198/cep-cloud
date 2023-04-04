<template>
  <el-row class="theme-hey">
    <el-col :span="4"
            class="avue-sidebar leftClass">
      <div class="titleClass">
        <i class="el-icon-s-flag" />
        <span>&nbsp;&nbsp;&nbsp;生产参数维护项</span>
      </div>
      <el-menu default-active="1" class="el-menu-vertical-demo">
        <el-menu-item index="1" @click="handleChange('ZBX')">
          <i class="el-icon-menu" />
          <span slot="title">指标项维护</span>
        </el-menu-item>
        <el-menu-item index="3" @click="handleChange('KZBZ')">
          <i class="el-icon-setting" />
          <span slot="title">K值标准</span>
        </el-menu-item>
        <el-menu-item index="4" @click="handleChange('RZXMC')">
          <i class="el-icon-setting" />
          <span slot="title">日志项名称</span>
        </el-menu-item>
        <el-menu-item index="5" @click="handleChange('SCXXPZ')">
          <i class="el-icon-setting" />
          <span slot="title">生产信息配置</span>
        </el-menu-item>
        <el-menu-item index="7" @click="handleChange('JPCLWH')">
          <i class="el-icon-setting" />
          <span slot="title">焦棚产量维护</span>
        </el-menu-item>
      </el-menu>
    </el-col>
    <el-col :span="20">
      <div class="rightClass">
        <indexItem v-if="tabPosition==='ZBX' " />
        <cokeOvenHoleYield v-else-if="tabPosition==='JLDKCL'" />
        <kValue v-else-if="tabPosition==='KZBZ'" />
        <LogName v-else-if="tabPosition==='RZXMC'" />
        <InformationConfiguration v-else-if="tabPosition==='SCXXPZ'" />
        <EverydayCokeOvenNumber v-else-if="tabPosition==='MRTJLSWH'" />
        <FocalTentProduction v-else-if="tabPosition==='JPCLWH'" />
        <CokingCoalConsume v-else-if="tabPosition==='LJMXHWH'" />
        <div v-else></div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import indexItem from "./parameter/zbx/indexItem";
import cokeOvenHoleYield from "./parameter/jldkcl/cokeOvenHoleYield";
import kValue from "./parameter/kzbz/kValue";
import LogName from "./parameter/rzxmc/logName";
import InformationConfiguration from "./parameter/scxxpz/index";
import EverydayCokeOvenNumber from "./parameter/mrtjlswh/index";
import FocalTentProduction from "./parameter/jpclwh/index";
import CokingCoalConsume from "./parameter/ljmxhwh/index";

export default {
  name: "parameter",
  components: {
    indexItem,
    cokeOvenHoleYield,
    kValue,
    LogName,
    InformationConfiguration,
    EverydayCokeOvenNumber,
    FocalTentProduction,
    CokingCoalConsume
  },
  data () {
    return {
      tabPosition: 'ZBX'
    };
  },
  computed: {
  },
  created () {
    // console.log(this.userInfo)
  },
  methods: {
    handleChange (val) {
      this.tabPosition = val
    }
  }
}
</script>

<style scoped>
.titleClass {
  height: 70px;
  margin-top: -70px;
  text-align: center;
  line-height: 70px;
  font-size: 20px;
  color: #999999;
  font-weight: 400;
  text-decoration: none;
}

.theme-hey {
  padding-left: 10px;
}

.leftClass {
  height: 89vh;
  background-color: #f9f9f9;
  border-radius: 20px 0 0 20px;
}

.rightClass {
  height: 89vh;
  width: 99%;
  background-color: #fff;
  border-radius: 0 20px 20px 0;
}
</style>
<style>
.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
