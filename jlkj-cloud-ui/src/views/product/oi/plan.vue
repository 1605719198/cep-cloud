<template>
  <el-row>
    <el-col :span="24" style="padding:0 10px">
      <div class="plan_header" style="padding-top: 5px;">
        <el-row>
          <el-col :span="7">
            <div class="plan_header_title">计划管理</div>
          </el-col>
          <el-col :span="10">
            <div style="text-align: center;min-width: 585px">
              <el-radio-group v-model="tabPosition">
                <el-radio-button label="JH">焦化指标</el-radio-button>
                <el-radio-button label="FD">发电指标</el-radio-button>
                <el-radio-button label="FDCL">发电产量</el-radio-button>
                <el-radio-button label="CL">焦化产量</el-radio-button>
                <el-radio-button label="PM">配煤计划</el-radio-button>
                <el-radio-button label="WX">维修计划</el-radio-button>
                <el-radio-button label="NH">能耗计划</el-radio-button>
              </el-radio-group>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="plan_main">
        <planCoking2 v-if="tabPosition==='JH' " />
        <planElectricity v-else-if="tabPosition==='FD'" />
        <planEnergyYield v-else-if="tabPosition==='FDCL'" />
        <planYield v-else-if="tabPosition==='CL'" />
        <planCoalBlending v-else-if="tabPosition==='PM'" />
        <planRepair v-else-if="tabPosition==='WX'" />
        <planEnergy v-else-if="tabPosition==='NH'" />
        <div v-else></div>
      </div>
    </el-col>
  </el-row>

</template>

<script>
import planCoking2 from "./plan/planCoking2";
import planElectricity from "./plan/planElectricity";
import planYield from "./plan/planYield";
import planCoalBlending from "./plan/planCoalBlending";
import planRepair from "./plan/planRepair";
import planEnergy from "./plan/planEnergy";
import planEnergyYield from "./plan/planEnergyYield";

export default {
  name: "plan",
  components: {
    planCoking2,
    planElectricity,
    planYield,
    planCoalBlending,
    planRepair,
    planEnergy,
    planEnergyYield,
  },
  data() {
    return {
      tabPosition: 'JH'
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
.plan_header {
  background-color: #F9F9F9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #E9E9E9 solid;
}

.plan_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold
}

.plan_main {
  height: 82vh;
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
</style>
