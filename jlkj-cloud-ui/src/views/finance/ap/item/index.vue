<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="报支类别" name="first">
        <item ref="item"  @change="changeData"></item>
      </el-tab-pane>
      <el-tab-pane label="报支细项" name="second">
        <itemDetail ref="itemDetail" :applyId="applyId" :itemNo="itemNo" :itemName="itemName"></itemDetail>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>

import item from "@/views/finance/ap/item/item";
import itemDetail from "@/views/finance/ap/item/itemDetail";

export default {
  name: "Item",
  components: {item,itemDetail},
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

