<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick" :before-leave="leaveTab" >
      <el-tab-pane label="现金流动量项目" name="first">
        <index @change="changeData"></index>
      </el-tab-pane>
      <el-tab-pane label="科目设置" name="second">
        <itemCompid :cashFlowCode="cashFlowCode"  :cashFlowName="cashFlowName" :id="id":companyId="companyId"></itemCompid>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import index from "@/views/finance/aa/codecompid/code";
import itemCompid from "@/views/finance/aa/itemCompid/index";
export default {
  name: "custominfo",
  components: {index,itemCompid},
  data() {
    return {
      activeName: 'first',
      cashFlowCode: null,
      cashFlowName: null,
      id: null,
      Verify:null,
      companyId:null,
      applyIdIf:false
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
      if (this.cashFlowCode == null){
        this.applyIdIf = false
        this.$message({
          message: '请从行点击进入折页',
          type: 'warning'
        });
        return this.applyIdIf;
      }else {
        this.applyIdIf = true
        return this.applyIdIf;
      }
    },


    changeData(tabName,cashFlowCode,cashFlowName,id,companyId){
      this.activeName = tabName;
      this.cashFlowCode = cashFlowCode;
      this.cashFlowName = cashFlowName;
      this.companyId=companyId;
      this.id = id;
    }
  }
};
</script>
