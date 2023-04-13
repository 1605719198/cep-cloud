<template>
  <el-select v-model="value" :placeholder="placeholder" ref="selectTree"
             :clearable="isClearable" :filterable="isFilterable" :filter-method="searchTree">
    <el-option class="selectTreeOption" :value="selectForm.value" :label="selectForm.label">
      <template v-if="isIcon">
        <el-tree
          node-key="id"
          ref="refSelectTree"
          v-loading="loading"
          :data="treeData"
          :show-checkbox="false"
          :highlight-current="true"
          :default-expand-all="false"
          :expand-on-click-node="!isSelectNode"
          :default-expanded-keys="[selectForm.value]"
          :accordion="isAccordion"
          @node-click="handleNodeClick"
          :filter-node-method="filterNode"
        >
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span><i :class="data.icon"/></span><span>{{ node.label }}</span>
          </span>
        </el-tree>
      </template>
      <template v-else>
        <el-tree
          node-key="id"
          ref="refSelectTree"
          v-loading="loading"
          :data="treeData"
          :show-checkbox="false"
          :highlight-current="true"
          :default-expand-all="false"
          :expand-on-click-node="!isSelectNode"
          :default-expanded-keys="[selectForm.value]"
          :accordion="isAccordion"
          @node-click="handleNodeClick"
          :filter-node-method="filterNode"
        >
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span>{{ node.label }}</span>
          </span>
        </el-tree>
      </template>
    </el-option>
  </el-select>
</template>

<script>
  import {getHumanTree} from "@/api/human/hm/humanResourceTree";

  export default {
    name: "selectDeptUserTree",
    //不包含多选-多选功能需要单独开发
    //数据过多所以不能展开全部节点，否则会卡死
    //仅为部门用户下拉树，非通用下拉树，copy时基于需求调整代码
    props: {
      //默认参数
      selectForm: {
        type: Object,
        default: () => {
          return {value: '', label: ''};
        }
      },
      //输入框占位文本
      placeholder: {type: String, default: '请选择'},
      //是否可以选择节点(可选节点的情况下，默认部门用户都可选)
      isSelectNode: {type: Boolean, default: false},
      //是否可以选择非节点的部门
      isSelectDept: {type: Boolean, default: false},
      //是否显示图标
      isIcon: {type: Boolean, default: false},
      //是否可以清空选项
      isClearable: {type: Boolean, default: false},
      //是否开启手风琴模式
      isAccordion: {type: Boolean, default: false},
      //是否查询
      isFilterable: {type: Boolean, default: false},
      //是否只需要部门
      isOnlyDept: {type: Boolean, default: false},
    },
    data() {
      return {
        selectTree: false,
        loading: false,
        treeData: [],
        filterText: '',
        value: '',
      }
    },
    created: function () {
      this.loading = true;//加载状态
      getHumanTree().then(res => {
        this.updateTreeData(res.data);
      }, error => {
        this.loading = false;
        window.console.log(error);
      });
    },
    updated: function () {
      if (this.selectForm.value === '') {
        this.value = '';
      }
    },
    methods: {
      handleNodeClick: function (node) {
        if (this.isSelectNode) {
          this.selectForm.value = node.id;
          this.selectForm.label = node.label;
          this.selectForm.flag = node.flag;
          this.value = node.id;
          this.$refs.selectTree.blur();
        } else {
          if (node.children.length === 0) {
            if (this.isSelectDept) {
              this.selectForm.value = node.id;
              this.selectForm.label = node.label;
              this.selectForm.flag = node.flag;
              this.value = node.id;
              this.$refs.selectTree.blur();
            } else {
              if (node.flag === 'user') {
                this.selectForm.value = node.id;
                this.selectForm.label = node.label;
                this.selectForm.flag = node.flag;
                this.value = node.id;
                this.$refs.selectTree.blur();
              }
            }
          }
        }
      },
      searchTree(val) {
        this.filterText = val;
      },
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      updateTreeData: function (data) {
        if (this.isOnlyDept) {
          this.updateDeptTreeData(data, (res) => {
            this.treeData = [...res];
            this.isSelectNode = true;
          });
        } else {
          this.treeData = data;
        }
        this.loading = false;
        this.selectTree = true;
        if (this.selectForm.value) {
          this.$nextTick(() => {
            //读取传入ID对应的nodes
            let nodes = this.$refs.refSelectTree.getNode(this.selectForm.value);
            this.value = this.selectForm.value;
            this.selectForm.label = nodes.data.label;
            this.selectForm.flag = nodes.data.flag;
          });
        }
      },
      //递归过滤数据，将部门筛选出来
      updateDeptTreeData: function (data, callback) {
        let that = this;
        let list = [];
        data.forEach(function (obj) {
          if (obj.flag === 'dept') {
            if (obj.children) {
              let children = [];
              that.updateDeptTreeData(obj.children, (res) => {
                children = [...res];
              });
              list.push({...obj, children: children})
            } else {
              list.push({...obj})
            }
          }
        });
        callback(list);
      }
    },
    // 侦听器
    watch: {
      value(newValue) {
        if (newValue === '') {
          this.selectForm.value = '';
          this.selectForm.label = '';
          this.selectForm.flag = '';
          this.$refs.refSelectTree.filter('');
        }
        this.$emit('change', this.selectForm)
      },
      filterText(val) {
        this.$refs.refSelectTree.filter(val);
      },
    }
  }
</script>

<style scoped>
  .selectTreeOption {
    overflow: auto;
    background-color: #fff;
    height: auto;
  /*min-height: 32px;*/
  }

  .custom-tree-node {
    padding-right: 15px;
  }
</style>
