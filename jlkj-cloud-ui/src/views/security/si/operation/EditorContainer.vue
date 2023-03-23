<template>
  <div>
    <el-tabs type="card" v-model="activeName">
      <el-tab-pane label="申请信息" name="申请信息">
        <LiftingInfo :dataBundle="dataBundle" v-if="containerConfig.from === 'lifting'" :isShowComfirm="isShowConfirm" @close="closeInfo"/>
        <BlindPlateInfo :dataBundle="dataBundle" v-if="containerConfig.from === 'blindPlate'" :isShowComfirm="isShowConfirm"  @close="closeInfo"/>
<!--        <RoadBreakingInfo v-if="containerConfig.from ==='roadBreaking'" @close="closeInfo"/>-->
        <ConfinedSpaceInfo :dataBundle="dataBundle" v-if="containerConfig.from === 'confinedSpace'" :isShowComfirm="isShowConfirm"  @close="closeInfo"/>
        <GroundBreakingInfo :dataBundle="dataBundle" v-if ='containerConfig.from === "groundBreaking"' :isShowComfirm="isShowConfirm"  @close="closeInfo"/>
      </el-tab-pane>
      <el-tab-pane label="安全措施" name="安全措施"><SecurityMeasures :dataBundle="dataBundle" :flag="flag" style="min-height: 400px ; margin-bottom: 20px"/></el-tab-pane>
      <el-tab-pane label="作业审批" name="作业审批"><WorkApprove :dataBundle="dataBundle" :flag="flag" style="min-height: 400px ; margin-bottom: 20px"/></el-tab-pane>
      <el-tab-pane label="审批记录" name="审批记录"><approvalRecord v-if="activeName==='审批记录'" ref="record" :data="dataBundle" :jobType="jobType"/></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import SecurityMeasures from "@/views/security/si/operation/SecurityMeasures";
import WorkApprove from "@/views/security/si/operation/WorkApprove";
import LiftingInfo from "@/views/security/si/operation/LiftingInfo";
import BlindPlateInfo from "@/views/security/si/operation/BlindPlateInfo";
import RoadBreakingInfo from "@/views/security/si/operation/RoadBreakingInfo";
import ConfinedSpaceInfo from "@/views/security/si/operation/ConfinedSpaceInfo";
import GroundBreakingInfo from "./GroundBreakingInfo";
import approvalRecord from "@/views/security/si/operation/common/approvalRecord";
export default {
  components:{
    ConfinedSpaceInfo,
    SecurityMeasures,
    WorkApprove,
    LiftingInfo,
    BlindPlateInfo,
    RoadBreakingInfo,
    GroundBreakingInfo,
    approvalRecord
  },
  name: "EditorContainer",
  props:['dataBundle', "isShowConfirm"],
  created() {
    switch (this.dataBundle.from){
      case "lifting":
        this.flag = "DZ";
        this.jobType = 1;
        break;
      case "blindPlate":
        this.flag = "MD"
        this.jobType = 6;
        break;
      case "confinedSpace":
        this.flag = "SX"
        this.jobType = 4;
        break;
      case "groundBreaking":
        this.flag = "DT"
        this.jobType = 5;
        break;
    }
  },
  data(){
    return {
      flag:'',
      props: {
        multiple: true,
      },
      containerConfig:{
        from : this.dataBundle.from
      },
      activeName:'申请信息',
      jobType:'',
    }
  },
  methods:{
    closeInfo(){
      this.$emit('close');
    }
  }
}
</script>

<style scoped>

</style>
