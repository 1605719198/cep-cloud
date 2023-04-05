<template>
  <el-row>
    <el-col :span="24" style="padding:0 10px">
      <div class="electricity_header">
        <el-row>
          <el-col :span="7">
            <div class="electricity_header_title">发电实绩</div>
          </el-col>
          <el-col :span="10">
            <div style="text-align: center ; min-width: 500px">
              <el-radio-group v-model="tabPosition">
                <el-radio-button label="zqclsj">蒸汽产量实绩</el-radio-button>
                <el-radio-button label="fdlsj">发电量实绩</el-radio-button>
                <el-radio-button label="clyctz">产量异常台账</el-radio-button>
              </el-radio-group>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="electricity_main">
        <SteamYieldActual v-if="tabPosition==='zqclsj'" />
        <ElectricProductionActual v-else-if="tabPosition==='fdlsj'" />
        <YieldExceptionAccount v-else-if="tabPosition==='clyctz'" />
        <div v-else></div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import role from "@/views/system/role";
import SteamYieldActual from "./electricity/SteamYieldActual";
import ElectricProductionActual from "./electricity/ElectricProductionActual";
import YieldExceptionAccount from "./electricity/YieldExceptionAccount";
export default {
  name: "electricity",
  components: {
    role,
    SteamYieldActual,
    ElectricProductionActual,
    YieldExceptionAccount
  },
  data() {
    return {
      tabPosition: 'zqclsj'
    };
  },
  computed: {
  },
  created() {
  },
  methods: {
    handleChange(val) {
      console.log(val);
    }
  }
};
</script>

<style scoped>
.electricity_header {
  background-color: #F9F9F9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #E9E9E9 solid;
}

.electricity_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold
}

.electricity_main {
  height: 83vh;
  background-color: #FFF;
}

.el-card__body {
  padding: 10px 10px 20px 20px;
}
</style>

<style>
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
  top: 12px
}

.el-dialog__body {
  padding: 15px 40px;
}
</style>
