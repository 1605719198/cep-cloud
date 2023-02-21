<template>
  <div>
    <div style="height: 200px;width: 300px;overflow: scroll">
<!--      <el-input-->
<!--        placeholder="输入关键字进行过滤"-->
<!--        v-model="filterText">-->
<!--      </el-input>-->
      <el-tree :data="datas"
               :highlight-current="true"
               :expand-on-click-node="false"
               :check-on-click-node="true"
               node-key="id"
               :props="defaultProps"
               :filter-node-method="filterNode"
               @node-click="nodeClick"
               ref="tree">
    <span class="custom-tree-node" slot-scope="{ node, data }">
      <i :class="data.icon"></i><span style="padding-left: 5px;font-size: 12px;">{{ data.label }}</span>
    </span>
      </el-tree>
    </div>
  </div>
</template>

<script>
export default {
  name: 'humanTree',
  props: {
    datas: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data () {
    return {
      filterText: '',
      open: false,
      checkedNames: [],
      defaultProps: {
        label: 'label',
        children: 'children',
        id: 'id'
      },
    }
  },
  /**
   * 搜索过滤watch
   */
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    /**搜索过滤方法*/
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点点击事件
    nodeClick (node, ev) {
      this.$emit('handleData', node, ev)
    }
  }
}
</script>

<style scoped>
  @import './icon/iconfont.css';

</style>
