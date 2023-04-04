<template>
  <el-row>
    <el-col :span="24" style="padding:0 10px">
      <div class="coke_header">
        <el-row>
          <el-col :span="7">
            <div class="coke_header_title">炼焦实绩</div>
          </el-col>
          <el-col :span="11">
            <div style="text-align: center; min-width: 650px">
              <el-radio-group v-model="tabPosition">
                <el-radio-button label="CLSJ">出炉实绩</el-radio-button>
                <el-radio-button label="JTCC">焦炭出厂实绩</el-radio-button>
                <el-radio-button label="CWJL">出炉实绩测温记录</el-radio-button>
                <el-radio-button label="JLXS">焦炉系数记录</el-radio-button>
                <el-radio-button label="JYCL">焦炭产量记录</el-radio-button>
                <el-radio-button label="JTFXCL">焦炭分项产量</el-radio-button>
              </el-radio-group>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="coke_main">
        <outFurnaceActual v-if="tabPosition==='CLSJ'" />
        <CokeOutFactoryActual v-else-if="tabPosition==='JTCC'" />
        <OutActualTemparetureRecord v-else-if="tabPosition==='CWJL'" />
        <CokeCoefficientRecord v-else-if="tabPosition==='JLXS'" />
        <CokeYieldRecord v-else-if="tabPosition==='JYCL'" />
        <CokeItemYield v-else-if="tabPosition === 'JTFXCL'" />
        <div v-else></div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import role from "@/views/system/role";
import outFurnaceActual from "./coke/outFurnaceActual";
import CokeOutFactoryActual from "./coke/CokeOutFactoryActual";
import OutActualTemparetureRecord from "./coke/OutActualTemparetureRecord";
import CokeCoefficientRecord from "./coke/CokeCoefficientRecord";
import CokeYieldRecord from "./coke/CokeYieldRecord";
import CokeItemYield from "@/views/product/oi/actual/coke/CokeItemYield";
export default {
  name: "coke",
  components: {
    role,
    outFurnaceActual,
    CokeOutFactoryActual,
    OutActualTemparetureRecord,
    CokeCoefficientRecord,
    CokeYieldRecord,
    CokeItemYield
  },
  data() {
    return {
      tabPosition: 'CLSJ'
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
.coke_header {
  background-color: #F9F9F9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #E9E9E9 solid;
}

.coke_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold
}

.coke_main {
  height: 83vh;
  background-color: #FFF;
}

.el-card__body {
  padding: 10px 10px 20px 20px;
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
  top: 12px
}

.el-dialog__body {
  padding: 15px 40px;
}
</style>

