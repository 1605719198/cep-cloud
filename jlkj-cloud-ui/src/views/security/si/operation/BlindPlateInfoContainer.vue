<template>
  <div>
    <el-tabs  type="card" >
      <el-tab-pane label="基本内容" name="jbnr"><BlindPlateInfo/></el-tab-pane>
      <el-tab-pane label="其他内容" name="qtnr">配置管理</el-tab-pane>
    </el-tabs>
    <el-divider></el-divider>
    <div class="el-dialog__footer" style="padding:10px 0 0 0;">
      <span slot="footer" class="dialog-footer">
        <el-button size="small" >取 消</el-button>
        <el-button type="primary"  size="small">保 存</el-button>
      </span>
    </div>
  </div>

</template>

<script>
import BlindPlateInfo from "@/views/security/si/operation/BlindPlateInfo";
export default {
  components:{
    BlindPlateInfo
  },
  name: "BlindPlateInfoContainer"
}
</script>

<style scoped>

</style>
