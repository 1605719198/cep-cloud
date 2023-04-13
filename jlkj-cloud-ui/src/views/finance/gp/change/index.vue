<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick" :before-leave="leaveTab" >
      <el-tab-pane label="异动申请" name="first">
        <index @change="changeData"></index>
      </el-tab-pane>
      <el-tab-pane label="证照上传" name="second">
        <addfile :applyId="applyId"></addfile>
      </el-tab-pane>
      <el-tab-pane label="核实信息" name="third">
        <verify :applyId="applyId"></verify>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import index from "@/views/finance/gp/change/change";
import addfile from "@/views/finance/gp/addfile/addfile";
import verify from "@/views/finance/gp/Verify/Verify";
export default {
  name: "custominfo",
  components: {index,addfile,verify},
  data() {
    return {
      activeName: 'first',
      applyId: null,
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

      if (this.applyId == null){
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


    changeData(tabName,args){
      this.activeName = tabName;
      this.applyId = args;

    }
  }
};
</script>
