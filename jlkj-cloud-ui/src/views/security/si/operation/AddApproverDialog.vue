<template>
  <div>
    <el-tree
      :data="data"
      :props="defaultProps"
      :accordion="true"
      @node-click="handleNodeClick"
      v-loading="isloading">
    </el-tree>
  </div>
</template>

<script>
import {getHumanTree} from "@/api/human/hm/humanResourceTree";
export default {
  name: "AddApproverDialog",
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },

  methods: {
    handleNodeClick(node){
      // console.log("-------------------审批人node节点----------------");
      // console.log(node);
      if (node.flag === 'user'){
         this.$emit("get-approver-info", node);
      }
    },

  },

  data() {
    return {
      filterText: '',
      data: [],
      defaultProps: {
        children: 'children',
        label: 'label',
        id:'id',
      },
      isloading : true
    };
  },

  mounted() {
    //获取人员树
    getHumanTree().then(res => {
      this.data = res.data.data;
      // console.log(this.data);
      this.isloading = false;
    }, error => {
      window.console.log(error);
      this.isloading = false;
    });
  }
}
</script>

<style scoped>

</style>
