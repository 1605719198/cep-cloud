<template>
  <div class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="增加用户申请" name="first">
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
import index from "@/views/finance/gp/add/add";
import addfile from "@/views/finance/gp/addfile/index";
import verify from "@/views/finance/gp/Verify/index";
export default {
  name: "custominfo",
  components: {index,addfile,verify},
  data() {
    return {
      activeName: 'first',
      applyId: null,
      Verify:null
    };
  },
  methods: {
    handleClick(tab, event) {
      this.activeName = tab.name
    },
    changeData(tabName,args){
      console.log(tabName, args);
      this.activeName = tabName;
      this.applyId = args;
    }
  }
};
</script>
