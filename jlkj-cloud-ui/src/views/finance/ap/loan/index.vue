<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick"  type="card"     :before-leave="leaveTab" >
      <el-tab-pane label="预付申请" name="first">
        <loan @change="changeData"></loan>
      </el-tab-pane>
      <el-tab-pane label="审核信息" name="second">
        <process :billNo="billNo"></process>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import loan from "@/views/finance/ap/loan/loan";
import process from "@/views/finance/ap/loan/process";
export default {
  name: "index",
  components: {loan,process},
  data() {
    return {
      activeName: 'first',
      billNo: null
    };
  },
  methods: {
    handleClick(tab, event) {
      if (this.applyIdIf!=false){
        this.activeName = tab.name
      }
    },
    // tab切换前判断
    leaveTab() {
      if (this.billNo == null){
        this.applyIdIf = false
        this.$message({
          message: '请点击审核按钮进入折页',
          type: 'warning'
        });
        return this.applyIdIf;
      }else {
        this.applyIdIf = true
        return this.applyIdIf;
      }
    },
    changeData(tabName,billNo){
      this.activeName = tabName;
      this.billNo = billNo;
    }
  }
};
</script>
