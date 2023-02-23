<template>
  <div>
    <el-row>
      <el-col :span="24"
              style="padding:0 10px">
        <!--       头部标题 -->

        <!--       table主体 -->
        <div class="plan_main">
          <div class="avue-crud el-card__body"
               style="width: 98%;border: 0">
            <!--          条件搜索  -->
            <div class="avue-crud__search"
                 style="border: 0">
              <el-row :gutter="20">
                <el-col :span="18">
                  <div class="el-form-item__content"
                       style="margin-left: 0px;">
                    <el-button v-hasPermi="['domain_tree_addTreeNode']"
                               size="mini"
                               plain
                               type="primary"
                               icon="el-icon-plus"
                               @click="handleAdd">新增</el-button>
                    <el-button v-hasPermi="['domain_tree_deleteTreeNode_ids']"
                               type="danger"
                               icon="el-icon-delete"
                               size="mini"
                               plain
                               @click="handleBatchDelete">删除</el-button>
                  </div>
                </el-col>
              </el-row>
            </div>
            <!--           表单数据 -->
            <div>
              <el-tree check-strictly
                       check-on-click-node
                       style="padding: 10px"
                       v-if="refreshTable"
                       :data="treeData"
                       show-checkbox
                       :default-expand-all="isExpandAll"
                       node-key="id"
                       ref="tree"
                       highlight-current
                       :expand-on-click-node="false"
                       :props="defaultProps">
                <span class="custom-tree-node"
                      slot-scope="{ node, data }">
                  <span>{{ node.label }}</span>
                  <span>
                    <el-button v-if="data.parentId != 0"
                               v-hasPermi="['domain_tree_updateTreeNode']"
                               size="mini"
                               type="text"
                               icon="el-icon-edit"
                               @click="handleUpdate(node,data)">
                      修改
                    </el-button>
                    <el-button v-if="data.parentId != 0"
                               v-hasPermi="['domain_tree_deleteTreeNode_ids']"
                               size="mini"
                               type="text"
                               icon="el-icon-delete"
                               @click="handleDelete(data)">
                      删除
                    </el-button>
                  </span>
                </span>
              </el-tree>
            </div>
            <!-- 添加或修改部门对话框 -->
            <div v-if="open">
              <el-dialog :title="title"
                         class="customDialogStyle"
                         :visible.sync="open"
                         width="600px"
                         append-to-body
                         :close-on-click-modal="false">
                <el-form ref="form"
                         :model="form"
                         :rules="rules"
                         label-width="120px"
                         label-position="left">
                  <el-row>
                    <el-col :span="24"
                            v-if="form.parentId !== 0">
                      <el-form-item label="上级域名称"
                                    prop="parentId">
                        <treeselect v-model="form.parentId"
                                    :options="sortOptions"
                                    :normalizer="normalizer"
                                    placeholder="选择上级域"
                                    @select="selectTree" />
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="域名称"
                                    prop="domainName">
                        <el-row>
                          <el-col :span="8">
                            <el-input v-model="domainNamea"
                                      disabled />
                          </el-col>
                          <el-col :span="16">
                            <el-input v-model="form.domainName"
                                      placeholder="请输入域名称。" />
                          </el-col>
                        </el-row>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="域编码"
                                    prop="domainNo">
                        <el-input v-model="form.domainNo"
                                  placeholder="请输入域编码"
                                  disabled />
                      </el-form-item>
                    </el-col>
                    <el-col :span="24">
                      <el-form-item label="域描述"
                                    prop="domainDescription">
                        <el-input v-model="form.domainDescription"
                                  placeholder="请输入域描述" />
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-form>
                <div slot="footer"
                     class="dialog-footer">
                  <el-button @click="cancel">取 消</el-button>
                  <el-button type="primary"
                             @click="submitForm"
                             :loading="states">确 定</el-button>
                </div>
              </el-dialog>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

import { getTreeNode, addTreeNode, updateTreeNode, deleteTreeNode, getDomainById } from '@/api/equip/mg/deviceDomain'

export default {
  name: "deviceDomain",
  components: { Treeselect },
  data () {
    return {
      normalizer (node) {
        if (node.children == null || node.children == 'null' || node.children.length == 0) {
          delete node.children
        }
        return {
          id: node.id,
          label: node.domainNo + "-" + node.domainName,
          children: node.children
        }
      },
      // 域名称
      domainNamea: "",
      // 遮罩层
      loading: true,
      // 表格树数据
      treeData: [],
      //提交状态
      states: false,
      // 上级域树选项
      sortOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        parentId: [
          { required: true, message: "上级域名称不能为空", trigger: "blur" },

        ],
        domainName: [
          { required: true, message: "域名称不能为空", trigger: "blur" },
          { max: 30, message: '"域名称名称最长为30 个字符', trigger: 'blur' }
        ],
        domainNo: [
          { required: true, message: "域编码不能为空", trigger: "blur" },
        ]
      },
      defaultProps: {
        children: 'children',
        label: function (data) {
          return data.domainNo + "-" + data.domainName
        },
        disabled: function (data) {
          if (data.parentId === "0") {
            return true
          }
        }
      }
    }
  },
  created () {
    this.getList()
  },
  methods: {
    /** 选择树节点 */
    selectTree (val) {
      // 如果有子节点，并且自己点长度大于0
      if (val.children != undefined && val.children.length > 0) {
        const domainNoList = []
        val.children.forEach((item, index) => {
          domainNoList.push(item.domainNo * 1);
        })
        // 取出域编码最大值+1
        const maxNo = Math.max.apply(null, domainNoList) + 1
        // 如果域编码小于10或者maxNo长度为单数
        if (maxNo < 10 || (maxNo.toString().length) % 2 > 0) {
          this.domainNamea = "0" + maxNo + "-"
          this.form.domainNo = "0" + maxNo
        } else {
          this.domainNamea = maxNo + "-"
          this.form.domainNo = maxNo
        }
      } else {
        // 如果没有子节点则直接拼接 01
        const maxNo = val.domainNo * 1
        if (maxNo < 10 || (maxNo.toString().length) % 2 > 0) {
          this.domainNamea = "0" + maxNo + "01-"
          this.form.domainNo = "0" + maxNo + "01"
        } else {
          this.domainNamea = maxNo + "01-"
          this.form.domainNo = maxNo + "01"
        }
      }
    },
    /** 查询树结构列表 */
    getList () {
      this.loading = true;
      getTreeNode().then(response => {
        this.treeData = response.data
        this.loading = false;
      });
    },
    // 取消按钮
    cancel () {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset () {
      this.form = {
        id: undefined,
        parentId: undefined,
        domainName: undefined,
        domainNo: undefined,
        domainDescription: undefined
      }
    },
    /** 新增按钮操作 */
    handleAdd () {
      this.reset();
      this.open = true;
      this.sortOptions = this.treeData
      this.domainNamea = ""
      this.title = "添加";
    },
    /** 修改按钮操作 */
    handleUpdate (node, data) {
      this.reset();
      this.sortOptions = this.treeData
      getDomainById(data.id).then(response => {
        this.form = response.data
        this.domainNamea = this.form.domainNo + "-"
        this.open = true
        this.title = "修改"
      })

    },
    /** 删除按钮操作 */
    handleDelete (data) {
      const ids = data.id;
      this.$confirm('此操作将永久删除此节点及其子节点，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {//点击确定，执行then方法
        //调用删除的方法
        deleteTreeNode(ids).then(response => {
          if (response.data) {
            this.$modal.msgSuccess('删除成功')
            this.getList()
          }
        })
      })
    },
    /** 多选删除按钮操作 */
    handleBatchDelete () {
      const ids = this.$refs.tree.getCheckedKeys();
      if (ids.length > 0) {
        this.$confirm('此操作将永久删除此节点及其子节点，是否继续？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {//点击确定，执行then方法
          //调用删除的方法
          deleteTreeNode(ids).then(response => {
            if (response.data) {
              this.$modal.msgSuccess('删除成功')
              this.getList()
            }
          })
        })
      } else {
        this.$modal.msgWarning('请至少选择一笔数据')
      }
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.states = true
          if (this.form.id != undefined) {
            updateTreeNode(this.form).then(response => {
              this.states = false
              if (response.data) {
                this.states = false
                this.$modal.msgSuccess('修改成功')
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTreeNode(this.form).then(response => {
              this.states = false
              if (response.data) {
                this.$modal.msgSuccess('新增成功')
                this.open = false;
                this.getList();
              }
            });
          }
        } else {
          this.$modal.msgWarning('请完善信息')
          this.states = false
        }
      })
    },
  }
}
</script>

<style scoped>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 150px;
}
.avue-crud .el-input {
  width: auto !important;
}
.plan_header {
  background-color: #f9f9f9;
  height: 50px;
  padding-top: 10px;
  line-height: 40px;
  border-radius: 20px 20px 0 0;
  border-bottom: 1px #e9e9e9 solid;
}

.plan_header_title {
  margin-left: 20px;
  color: #666666;
  font-size: 18px;
  font-weight: bold;
}

.plan_main {
  height: 86vh;
  background-color: #fff;
}
</style>
  <style>
.el-checkbox {
  margin-right: 10px !important;
}
.vue-treeselect--has-value .vue-treeselect__input {
  vertical-align: middle !important;
}
.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}

/* 弹窗圆角 */
.customDialogStyle .el-dialog {
  border-radius: 8px;
}

/* 标题样式 */
.customDialogStyle .el-dialog__header {
  background-color: rgb(245, 245, 245);
  border-radius: 8px 8px 0 0;
  padding: 10px 20px 10px;
}

/* 标题文字样式 */
.customDialogStyle .el-dialog__title {
  font-family: 微软雅黑 Bold, 微软雅黑 Regular, 微软雅黑;
  letter-spacing: normal;
  font-size: 14px;
  font-weight: 700;
  font-style: normal;
  text-align: left;
  line-height: 20px;
  border-width: 0;
  color: rgb(102, 102, 102);
}

/* 关闭符号位置调整 */
.customDialogStyle .el-dialog__headerbtn {
  top: 12px;
}

.customDialogStyle .el-dialog__body {
  padding: 15px 40px;
}
</style>
