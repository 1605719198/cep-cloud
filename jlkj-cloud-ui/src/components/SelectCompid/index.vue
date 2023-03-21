<template>
  <div :class="{'show':show}" >
    <svg-icon class-name="location-icon" icon-class="location" />
    <treeselect :options="deptOptions" :normalizer="normalizer" placeholder="选择上级部门" />
  </div>
</template>

<script>

import {listDept} from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";

export default {
  name: 'SelectCompid',
  components: { Treeselect },
  data() {
    return {
      show: true,
      // 部门树选项
      deptOptions: []
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询部门列表 */
    getList() {
      listDept().then(response => {
        this.deptOptions = this.handleTree(response.data, "deptId");
      });
    },
    /** 转换部门数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children
      };
    }
  }
}
</script>

<style lang="scss" scoped>
.header-search {
  font-size: 0 !important;

  .location-icon {
    cursor: pointer;
    font-size: 20px;
    vertical-align: middle;
  }

  .header-search-select {
    font-size: 18px;
    transition: width 0.2s;
    width: 0;
    overflow: hidden;
    background: transparent;
    border-radius: 0;
    display: inline-block;
    vertical-align: middle;

    ::v-deep .el-input__inner {
      border-radius: 0;
      border: 0;
      padding-left: 0;
      padding-right: 0;
      box-shadow: none !important;
      border-bottom: 1px solid #d9d9d9;
      vertical-align: middle;
    }
  }

  &.show {
    .header-search-select {
      width: 210px;
      margin-left: 10px;
    }
  }
}
</style>
