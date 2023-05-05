<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="总行资料" name="first">
        <bank ref="bank"  @change="changeData"></bank>
      </el-tab-pane>
      <el-tab-pane label="分行资料" name="second">
        <branch ref="branch" :applyId="applyId" :bankNo="bankNo" :bankchnName="bankchnName" ></branch>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>

import branch from "@/views/finance/aa/bank/branch";
import bank from "@/views/finance/aa/bank/bank";

export default {
  name: "Bank",

  components: {branch,bank},
  data() {
    return {
      activeName: 'first',
      applyId: null,
      Verify:null,
      applyIdIf:false,
      bankNo:null,
      bankchName:null
    };
  },

  methods: {
    handleClick(tab, event) {


      this.activeName = tab.name


    },
    changeData(tabName,id,bankNo,bankchnName){
      this.activeName = tabName;
      this.applyId = id;
      this.bankNo=bankNo;
      this.bankchnName=bankchnName
    }
  }
};
</script>
