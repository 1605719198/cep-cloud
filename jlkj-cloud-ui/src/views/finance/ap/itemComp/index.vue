<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="报支类别" name="first">
        <itemComp ref="itemComp"  @change="changeData"></itemComp>
      </el-tab-pane>
      <el-tab-pane label="报支细项" name="second">
      <itemDetailCompany :applyId="applyId" :itemNo="itemNo" :itemName="itemName"></itemDetailCompany>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>

import itemComp from "@/views/finance/ap/itemComp/itemComp";
import itemDetailCompany from "@/views/finance/ap/itemComp/itemDetailCompany";

export default {
  name: "ItemComp",
  components: {itemComp,itemDetailCompany},
  data() {
    return {
      activeName: 'first',
      applyId: null,
      Verify:null,
      applyIdIf:false,
      itemNo:null,
      itemName:null
    };
  },

  methods: {
    handleClick(tab, event) {
      this.activeName = tab.name
    },
    changeData(tabName,id,itemNo,itemName){
      this.activeName = tabName;
      this.applyId = id;
      this.itemNo=itemNo;
      this.itemName=itemName
      this.$refs.itemDetail.getVal(id, itemNo, itemName)
    },

  }
};
</script>

