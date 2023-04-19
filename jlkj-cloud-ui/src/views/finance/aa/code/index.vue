<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick" :before-leave="leaveTab" >
      <el-tab-pane label="现金流动量项目" name="first">
        <index @change="changeData"></index>
      </el-tab-pane>
      <el-tab-pane label="现金流动量项目" name="second">
        <item :cashFlowCode="cashFlowCode"  :cashFlowName="cashFlowName" :id="id"></item>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import index from "@/views/finance/aa/code/code";
import item from "@/views/finance/aa/item/index";
export default {
  name: "custominfo",
  components: {index,item},
  data() {
    return {
      activeName: 'first',
      cashFlowCode: null,
      cashFlowName: null,
      id: null,
      Verify:null,
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


    changeData(tabName,cashFlowCode,cashFlowName,id){
      this.activeName = tabName;
      this.cashFlowCode = cashFlowCode;
      this.cashFlowName = cashFlowName;
      this.id = id;
    }
  }
};
</script>
