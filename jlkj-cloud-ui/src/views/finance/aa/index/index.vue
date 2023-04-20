<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick" :before-leave="leaveTab" >
      <el-tab-pane label="索引设定" name="first">
        <index @change="changeData"></index>
      </el-tab-pane>
      <el-tab-pane label="基本设定" name="second">
        <baseLeafId :nodeNo="nodeNo"  :nodeName="nodeName" :companyId="companyId"  @nodeNoAdd="tabNameSecond"></baseLeafId>
      </el-tab-pane>
      <el-tab-pane label="明细设定" name="third">
        <detail  ref="detail" :nodeNo="nodeNo"  :nodeName="nodeName" :companyId="companyId"></detail>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import index from "@/views/finance/aa/index/menu";
import baseLeafId from "@/views/finance/aa/base/index";
import detail from "@/views/finance/aa/detail/index";

export default {
  name: "custominfo",
  components: {index,baseLeafId,detail},
  data() {
    return {
      activeName: 'first',
      nodeNo: null,
      nodeName: null,
      id: null,
      Verify:null,
      applyIdIf:false,
      tabName:null,
      companyId:''
    };
  },
  methods: {
    handleClick(tab, event) {
      if (this.applyIdIf!=false){
        this.activeName =tab.name
        if(tab.name=='third'){
          this.$refs.detail.detailGet()
        }

      }

    },

    // tab切换前判断
    leaveTab() {
      if (this.nodeNo == null){
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

    tabNameSecond(tabName){
      this.activeName = tabName

    },
    changeData(tabName,nodeNo,nodeName,companyId){
      this.activeName = tabName;
      this.nodeNo = nodeNo;
      this.nodeName = nodeName;
      this.companyId = companyId

    }
  }
};
</script>
