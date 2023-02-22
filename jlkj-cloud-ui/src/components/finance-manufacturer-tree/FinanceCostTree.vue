<template>
  <div v-if="productCodeTree">
    <el-dialog class="customDialogStyle" :data="openDialog.type" title="帐务资料" :visible.sync="productCodeTree" width="400px" :before-close="cancel" :append-to-body="true">
      <div class="head-container" style = "height:700px;overflow:auto;">
        <el-input
          placeholder="搜索科目索引"
          v-model="filterText"
          style="margin-bottom: 20px"
        >
        </el-input>
        <el-tree
          class="filter-tree"
          :data="treeData"
          :props="defaultProps"
          :filter-node-method="filterNode"
          ref="tree"
          @node-click="handleNodeClick"
        >
        </el-tree>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
 import {
   getTreeCostNode
 } from "@/api/finance/financeCost";
export default {
  name: "FinanceCostTree",
  components: {},
  props:['getTreeCost','productCodeTree'],
  data() {
    return {
      openDialog: {
        open: false,
        type: '',
        title: '',
        width: '500px',
        data: {},
      },
      open :  true,
      // 树节点过滤
      filterText: '',
      // 树节点数据
      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'levelName'
      },
    }
  },
  created() {

    this.initTree()
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    // 树节点搜索过滤
    filterNode(value, data) {
      if (!value) return true;
      return data.levelName.indexOf(value) !== -1;
    },
    // 初始化树节点
    initTree() {
     getTreeCostNode().then(response => {
        this.treeData = response.data.data
    })
     },
    handleNodeClick(data) {
      console.log(data);
      if (data.children === null){
        this.getTreeCost(data)
      }
    },
    submitForm() {
      this.$emit('closeFinanceCodeDialog')
    },
    cancel() {
      this.$emit('closeFinanceCodeDialog')
    },
  }
}
</script>

<style scoped>

</style>
