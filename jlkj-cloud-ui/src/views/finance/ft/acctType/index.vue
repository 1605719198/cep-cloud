<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="账务分类" name="first">
        <acctType ref="acctType"  @change="changeData"/>
      </el-tab-pane>
      <el-tab-pane label="账务代码" name="second">
        <acctMain ref="acctMain" :applyId="applyId" :acctType="acctType" :acctName="acctName" :conpanyId="companyId"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>

import acctType from "@/views/finance/ft/acctType/acctType";
import acctMain from "@/views/finance/ft/acctType/acctMain";

export default {
  name: "Index",
  components: {acctType,acctMain},
  data() {
    return {
      activeName: 'first',
      applyId: null,
      Verify:null,
      applyIdIf:false,
      acctType:null,
      acctName:null,
      companyId: null
    };
  },

  methods: {
    handleClick(tab, event) {
      this.activeName = tab.name
    },
    changeData(tabName,id,acctType,acctName,companyId){
      this.activeName = tabName;
      this.applyId = id;
      this.acctType = acctType;
      this.acctName = acctName
      this.companyId = companyId
      this.$refs.acctMain.getVal(id, acctType, acctName,companyId);
    }
  }
};
</script>
