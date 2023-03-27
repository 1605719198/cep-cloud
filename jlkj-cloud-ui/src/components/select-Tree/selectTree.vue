<template>
  <el-select v-loading="loading"
             v-model="value"
             @clear="clearhandle"
             :placeholder="placeholder"
             ref="selectTree"
             :disabled="isDisabled"
             :popper-append-to-body="false"
             :clearable="isClearable"
             :filterable="isFilterable"
             :filter-method="searchTree">
    <el-option class="selectTreeOption"
               :value="selectForm.value"
               :label="selectForm.label">
      <template v-if="isIcon">
        <el-tree node-key="id"
                 ref="refSelectTree"
                 v-loading="loading"
                 :data="treeData"
                 :show-checkbox="false"
                 :highlight-current="isHighlightCurrent"
                 :default-expand-all="isDefaultExpandAll"
                 :expand-on-click-node="!isSelectNode"
                 :default-expanded-keys="[selectForm.value]"
                 :accordion="isAccordion"
                 @node-click="handleNodeClick"
                 :filter-node-method="filterNode">
          <span class="custom-tree-node"
                slot-scope="{ node, data }">
            <slot name="front"
                  :data="node" />
            <span><i :class="data.icon" /></span><span>{{ node.label }}</span>
            <slot name="after"
                  :data="node" />
          </span>
        </el-tree>
      </template>
      <template v-else>
        <el-tree node-key="id"
                 ref="refSelectTree"
                 :disabled="isDisabled"
                 v-loading="loading"
                 :data="treeData"
                 :show-checkbox="false"
                 :highlight-current="isHighlightCurrent"
                 :default-expand-all="isDefaultExpandAll"
                 :expand-on-click-node="!isSelectNode"
                 :default-expanded-keys="[selectForm.value]"
                 :accordion="isAccordion"
                 @node-click="handleNodeClick"
                 :filter-node-method="filterNode">
          <span class="custom-tree-node"
                slot-scope="{ node, data }">
            <slot name="front"
                  :data="data" />
            <span>{{ node.label }}</span>
            <slot name="after"
                  :data="data" />
          </span>
        </el-tree>
      </template>
    </el-option>
  </el-select>
</template>

<script>
export default {
  name: "selectTree",
  props: {
    //传入数据
    data: { type: Array, default: () => [] },
    //默认参数{value: '', label: ''}
    selectForm: {
      type: Object,
      default: () => {
        return { value: '', label: '' };
      }
    },
    //输入框占位文本
    placeholder: { type: String, default: '请选择' },
    //返回的其他参数，必须是在data内存在的参数
    otherForm: { type: Array, default: () => [] },
    //自定义验证 基于data内对象的参数值，判断是否可选（传入参数为对象例如｛type:1｝表示只有type=1数据可选）
    validate: {
      type: Object, default: () => {
        return {}
      },
    },
    //空节点验证 是否允许选择内容为空的节点（默认是只有节点对象才存在children参数，选项是不存在children参数的）
    isNodeChildrenNull: { type: Boolean, default: false },
    //是否可以选择节点(可选节点的情况下，点击三角形才会展开)
    isSelectNode: { type: Boolean, default: false },
    //是否可以清空选项
    isClearable: { type: Boolean, default: false },
    //是否开启查询
    isFilterable: { type: Boolean, default: false },

    //是否显示图标
    isIcon: { type: Boolean, default: false },
    //是否开启手风琴模式
    isAccordion: { type: Boolean, default: false },
    //是否全部展开（数据量较大时，全部展开会出现卡顿）
    isDefaultExpandAll: { type: Boolean, default: false },
    //是否高亮当前选中节点
    isHighlightCurrent: { type: Boolean, default: true },
    //是否禁用
    isDisabled: { type: Boolean, default: false },
  },
  data () {
    return {
      //加载状态
      loading: false,
      //树数据(基本结构[{id:'',label:'',icon:'',children:[{id:'',label:'',icon:''}]}])
      treeData: [],
      //筛选条件字符串
      filterText: '',
      //当前选中值
      value: '',
    }
  },
  created: function () {
    this.loading = true;//加载状态
    if (this.data.length > 0) {
      this.updateTreeData(this.data);
    } else {
      this.loading = false;//加载状态
    }

  },
  updated: function () {
    if (this.data.length === 0) {
      this.value = '';
    } else {
      this.updateTreeData(this.data);
    }
    if (this.selectForm.value === '') {
      this.value = '';
    }
  },
  methods: {
    //清空事件
    clearhandle () {
      this.selectForm.value = ''
      this.selectForm.label = ''
      this.$emit('change', this.selectForm)
    },
    //点击事件
    handleNodeClick: function (node) {
      let that = this;
      //自定义验证
      let validate = true;
      if (Object.keys(this.validate).length !== 0) {
        for (const key in this.validate) {
          if (this.validate[key] === node[key]) {
            validate = true;
          } else {
            validate = false;
            break;
          }
        }
      }
      if (this.isSelectNode) {
        if (validate) {
          this.selectForm.value = node.id;
          this.selectForm.label = node.label;
          if (this.otherForm.length > 0) {
            this.otherForm.forEach(function (val) {
              if (node[val])
                that.selectForm[val] = node[val];
            });
          }
          this.value = node.id;
          this.$refs.selectTree.blur();
        }
      } else {
        //空节点验证
        let isNodeChildrenNull = false;
        if (this.isNodeChildrenNull) {
          isNodeChildrenNull = (node.children === undefined || node.children.length === 0);
        }
        //验证是否为节点
        if ((!node.children || isNodeChildrenNull) && validate) {
          this.selectForm.value = node.id;
          this.selectForm.label = node.label;
          if (this.otherForm.length > 0) {
            this.otherForm.forEach(function (val) {
              if (node[val])
                that.selectForm[val] = node[val];
            });
          }
          this.value = node.id;
          this.$refs.selectTree.blur();
        }
      }
      //每次选中后，调用外部修改接口
      this.$emit('change', this.selectForm)
    },
    //筛选查询功能
    searchTree (val) {
      this.filterText = val;
    },
    //节点筛选方法
    filterNode (value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    updateTreeData: function (data) {
      let that = this;
      this.treeData = data;
      this.loading = false;
      //存在默认选中参数时，如果列表数据存在，则读取出全部返回数据
      if (this.selectForm.value && this.data.length > 0) {
        this.$nextTick(() => {
          //读取传入ID对应的nodes
          let nodes = this.$refs.refSelectTree.getNode(this.selectForm.value);
          this.value = this.selectForm.value;
          if (nodes) {
            this.selectForm.label = nodes.data.label;
            //判断是否存在额外的返回参数
            if (this.otherForm.length > 0) {
              this.otherForm.forEach(function (val) {
                if (nodes.data[val])
                  that.selectForm[val] = nodes.data[val];
              });
            }
          }
        });
      }
    },
  },
  // 侦听器
  watch: {
    //监听选中值
    value (newValue) {
      if (newValue === '') {
        let that = this;
        this.selectForm.value = '';
        this.selectForm.label = '';
        //判断是否存在额外的返回参数
        if (this.otherForm.length > 0) {
          this.otherForm.forEach(function (val) {
            if (that.selectForm[val])
              that.selectForm[val] = '';
          });
        }
        this.$refs.refSelectTree.filter('');
      }
      //每次选中后，调用外部修改接口
      // this.$emit('change', this.selectForm)
    },
    //查询监听
    filterText (val) {
      this.$refs.refSelectTree.filter(val);
    },
    //监听外部传入数据，动态刷新树
    data () {
      this.updateTreeData(this.data);
    },
  }
}
</script>

<style scoped lang="scss">
.selectTreeOption {
   //overflow: auto;
  background-color: #fff;
  height: auto;
  //min-height: 32px;
}

.custom-tree-node {
  padding-right: 15px;
}
::v-deep .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
