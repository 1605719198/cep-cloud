<template>
  <div>
    <el-row>
      <el-col :span="24"
              style="padding:0 10px">

        <!--       table主体 -->
        <div class="plan_main">
          <div class="avue-crud el-card__body"
               style="width: 98%;border: 0">
            <el-row :gutter="20">
              <!--      树形结构数据-->
              <el-col :span="4"
                      :xs="24">
                <div class="head-container">
                  <el-input placeholder="搜索会计科目"
                            v-model="filterText"
                            style="margin-bottom: 20px">
                  </el-input>
                </div>
                <div class="el-form-item el-form-item--small">
                  <el-button style="width: 160px"
                             size="medium"
                             type="primary">
                    吉林建龙-会计科目
                  </el-button>
                </div>
                <div class="head-container"
                     style="height:700px;overflow:auto;">
                  <el-tree class="filter-tree"
                           node-key="id"
                           :data="treeData"
                           :props="defaultProps"
                           :filter-node-method="filterNode"
                           ref="tree"
                           @node-click="handleNodeClick">
                  </el-tree>
                </div>
              </el-col>
              <!--      信息数据查询-->
              <el-col :span="20"
                      :xs="24">
                <!--           条件搜索 -->
                <div class="avue-crud__search"
                     style="border: 0">
                  <el-form :model="queryParams"
                           ref="queryForm">
                    <el-row :gutter="20">
                      <el-col :span="4">
                        <div class="el-form-item ">
                          <el-input v-model="queryParams.accountCodeX"
                                    type="text"
                                    clearable
                                    placeholder="请输入会计科目名称"
                                    @keyup.enter.native="handleQuery" />
                        </div>
                      </el-col>
                      <el-col :span="5">
                        <div class="el-form-item__content"
                             style="margin-left: 0px;">
                          <el-button v-hasPermi="['accountCode_doQueryAll']"
                                     size="medium"
                                     type="primary"
                                     @click="handleQuery"
                                     icon="el-icon-search">搜索
                          </el-button>
                          <el-button size="medium"
                                     type="default"
                                     @click="resetQuery"
                                     icon="el-icon-refresh-left">重置
                          </el-button>
                        </div>
                      </el-col>
                      <el-col :span="15">
                        <div class="el-form-item el-form-item--small"
                             style="float: right">
                          <el-button v-if="addAccount"
                                     v-hasPermi="['accountCode_doAdd']"
                                     type="primary"
                                     size="medium"
                                     icon="el-icon-plus"
                                     @click="handleAdd">添加科目
                          </el-button>
                        </div>
                      </el-col>
                    </el-row>
                  </el-form>
                </div>
                <!-- 表单数据 -->
                <el-row>
                  <el-table key="table2"
                            v-if="costAccount"
                            v-loading="loading"
                            height="67vh"
                            size="small"
                            :data="tableData"
                            stripe
                            width="100%"
                            @selection-change="handleSelectionChange">
                    <el-table-column label="会计科目"
                                     sortable
                                     align="center"
                                     prop="accountCode" />
                    <el-table-column label="会计科目中文名称"
                                     sortable
                                     align="center"
                                     prop="accountChineseName" />
                    <el-table-column label="是否传票性科目"
                                     sortable
                                     align="center"
                                     prop="isVoucher" />
                    <el-table-column label="状态"
                                     align="center"
                                     prop="disabledCode">
                      <template slot-scope="scope">
                        <dict-tag :options="dict.type.finance_disabledCode"
                                  :value="scope.row.disabledCode" />
                      </template>
                    </el-table-column>
                    <el-table-column width="550"
                                     label="操作"
                                     align="center"
                                     class-name="small-padding fixed-width">
                      <template slot-scope="scope">
                        <el-button v-hasPermi="['accountCode_doQueryOne_id']"
                                   plain
                                   icon="el-icon-info"
                                   type="info"
                                   size="mini"
                                   @click="handleDetailsAccount(scope.row)">详情
                        </el-button>
                        <el-button v-hasPermi="['accountCode_doEdit']"
                                   size="mini"
                                   plain
                                   icon="el-icon-edit"
                                   type="primary"
                                   @click="handleUpdate(scope.row)">编辑
                        </el-button>
                        <el-button v-hasPermi="['accountCode_doEditState']"
                                   size="mini"
                                   plain
                                   icon="el-icon-caret-left"
                                   type="warning"
                                   @click="handleUpdateDisable(scope.row)">禁用/启用
                        </el-button>
                        <el-button v-hasPermi="['accountCode_doDelete_ids']"
                                   size="mini"
                                   plain
                                   icon="el-icon-delete"
                                   type="danger"
                                   @click="handleDelete(scope.row)">删除
                        </el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                  <!--               分页 -->
                  <div style="right: 0"
                       class="avue-crud__pagination">
                    <el-pagination v-if="pagingYN"
                                   background
                                   @size-change="handleSizeChange"
                                   @current-change="handleCurrentChange"
                                   :current-page="queryParams.page"
                                   :page-sizes="[20, 50, 100, 200]"
                                   :page-size="queryParams.limit"
                                   layout="total, sizes, prev, pager, next, jumper"
                                   :total="queryParams.total">
                    </el-pagination>
                  </div>
                </el-row>
              </el-col>
            </el-row>
          </div>
        </div>
      </el-col>
    </el-row>
    <!--    添加或修改弹框-->
    <div v-if="open">
      <el-dialog class="customDialogStyle"
                 :title="title"
                 v-if="UpdateSubject"
                 :visible.sync="open"
                 width="1000px"
                 append-to-body
                 :close-on-click-modal="false">
        <el-form ref="formAccount"
                 :model="formAccount"
                 :rules="rules"
                 label-width="150px">
          <el-row style="height: 70px">
            <el-col :span="12">
              <el-form-item label="会计科目"
                            prop="accountCode">
                <el-input style="width: 65px;"
                          v-if="codeCostCodeLevelCode"
                          v-model="form.costCodeLevelCode"
                          placeholder=""
                          :disabled="true" />
                <el-input :disabled="editInput"
                          style="width: 160px"
                          v-model="formAccount.accountCode"
                          placeholder="自定义编码" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="会计科目中文名称"
                            prop="accountChineseName">
                <el-input style="width: 210px"
                          v-model="formAccount.accountChineseName"
                          placeholder="请输入" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="传票性科目"
                            prop="isVoucher">
                <el-radio-group v-model="formAccount.isVoucher">
                  <el-radio v-for="dict in dict.type.sys_yes_no"
                            :key="dict.value"
                            :label="dict.value">{{dict.label}}</el-radio>
                </el-radio-group>

              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="户号类别"
                            prop="idTypeStus">
                <el-select v-model="formAccount.idTypeStus"
                           placeholder="请选择">
                  <el-option v-for="dict in dict.type.finance_id_type"
                             :key="dict.value"
                             :label="dict.value+'_'+dict.label"
                             :value="dict.value" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="借贷方"
                            prop="drOrCr">
                <el-radio-group v-model="formAccount.drOrCr">
                  <el-radio v-for="dict in dict.type.finance_drcr"
                            :key="dict.value"
                            :label="dict.value">{{dict.label}}</el-radio>
                </el-radio-group>

              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="参号类别"
                            prop="refTypeStus">
                <el-select v-model="formAccount.refTypeStus"
                           placeholder="请选择">
                  <el-option v-for="dict in dict.type.finance_ref_type"
                             :key="dict.value"
                             :label="dict.value+'_'+dict.label"
                             :value="dict.value" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="到期日"
                            prop="isDueDate">
                <el-radio-group v-model="formAccount.isDueDate">
                  <el-radio v-for="dict in dict.type.sys_yes_no"
                            :key="dict.value"
                            :label="dict.value">{{dict.label}}</el-radio>
                </el-radio-group>

              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="悬记账"
                            prop="isLqdt">
                <el-radio-group v-model="formAccount.isLqdt">
                  <el-radio v-for="dict in dict.type.sys_yes_no"
                            :key="dict.value"
                            :label="dict.value">{{dict.label}}</el-radio>
                </el-radio-group>

              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="级别"
                            prop="level">
                <el-select v-model="formAccount.level"
                           placeholder="请选择">
                  <el-option v-for="dict in dict.type.finance_acctCodeLevel"
                             :key="dict.value"
                             :label="dict.value+'_'+dict.label"
                             :value="dict.value" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="现金科目"
                            prop="isCash">
                <el-radio-group v-model="formAccount.isCash">
                  <el-radio v-for="dict in dict.type.sys_yes_no"
                            :key="dict.value"
                            :label="dict.value">{{dict.label}}</el-radio>
                </el-radio-group>

              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="机器专用"
                            prop="isPrvlg">
                <el-radio-group v-model="formAccount.isPrvlg">
                  <el-radio v-for="dict in dict.type.sys_yes_no"
                            :key="dict.value"
                            :label="dict.value">{{dict.label}}</el-radio>
                </el-radio-group>

              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="币别"
                            prop="isFrnCrcy">
                <el-radio-group v-model="formAccount.isFrnCrcy">
                  <el-radio v-for="dict in dict.type.sys_yes_no"
                            :key="dict.value"
                            :label="dict.value">{{dict.label}}</el-radio>
                </el-radio-group>

              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="单位"
                            prop="isUnit">
                <el-radio-group v-model="formAccount.isUnit">
                  <el-radio v-for="dict in dict.type.sys_yes_no"
                            :key="dict.value"
                            :label="dict.value">{{dict.label}}</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer"
             class="dialog-footer">
          <el-button @click="cancel"
                     size="medium">取 消</el-button>
          <el-button type="primary"
                     size="medium"
                     v-if="determine"
                     @click="submitForm('formAccount')"
                     :loading="lisetloading">确 定
          </el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  queryDataByParams,
  getTreeNode,
  getData,
  deleteCodeData,
  updateCodeData,
  doAddCode,
  updateState
} from "@/api/finance/aa/accountingSubject";

export default {
  name: "AccountingSubject",
  dicts: ['sys_yes_no', 'finance_acctCodeLevel', 'finance_drcr', 'finance_disabledCode', 'finance_id_type', 'finance_ref_type'],
  components: {},
  data () {
    var accountCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('会计科目不能为空'));
      } else if (value !== '' && this.levelCodeNode.length === 1) {
        if (value.length !== 3) {
          callback(new Error('会计科目只能输入三位编码'));
        }

      } else if (value !== '' && this.levelCodeNode.length === 4 && this.updateAccountCode.length !== 10) {
        if (value.length > 14) {
          callback(new Error('会计科目编码不可超过14位 '));
        }
      } else if (value !== '' && this.levelCodeNode.length === 4 && this.updateAccountCode.length === 10) {
        callback();
      } else {
        callback();
      }
      callback();
    };


    var accountChineseName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('会计科目中文名称不能为空'));
      } else {
        callback();
      }
      callback();
    };
    return {
      //修改验证
      updateAccountCode: "",
      //分页显示判断
      pagingYN: false,
      nodeKey: [],
      //弹窗栏位是否编辑
      editInput: false,
      costCodeLevelCode: '',
      levelCodeNode: '',
      parentId: '',
      nodeTypeNode: '',
      levelNameNode: '',
      placeholder: '',
      costCodeNode: '',
      codeCostCodeLevelCode: false,
      placeholde: '请选择区域',
      //节点建立人日期显示
      menuPopup: false,
      //确定
      determine: true,
      //增改加载按钮
      lisetloading: false,
      accountCodeX: '',
      //添加科目按钮
      addAccount: false,
      //节点弹窗判断
      UpdateNode: false,
      //科目弹窗判断
      UpdateSubject: false,
      //科目表格
      costAccount: false,
      // 遮罩层
      loading: true,
      // 树节点过滤
      filterText: '',
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: { compId: 'J00' },
      //成本科目表单
      formAccount: {
        isUnit: '',
        drOrCr: '',
        accountCodeTree: '',
        parentId: '',
        compId: 'J00',
        accountCode: '',
        accountChineseName: '',
        isVoucher: '',
        isDueDate: '',
        isLqdt: '',
        isPrvlg: '',
        isCash: '',
        level: '1',
        isFrnCrcy: '',
        disabledCode: ' '
      },
      // 选中数组
      ids: [],
      // 表格数据
      tableData: [],
      // 树节点数据
      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'accountName'
      },
      //树节点 查询参数
      queryParam: {
        accountCodeX: '',
        // 总条数
        total: 0,
        // 当前页
        page: 1,
        // 每页记录数
        limit: 10,
        compId: 'J00'
      },
      // 查询参数
      queryParams: {
        accountCodeX: '',
        // 总条数
        total: 0,
        // 当前页
        page: 1,
        // 每页记录数
        limit: 10,
        compId: 'J00'
      },
      // 表格字段
      columns: [],
      // 表单校验
      rules: {
        accountCode: [
          { validator: accountCode, required: true }
        ],
        accountChineseName: [
          { validator: accountChineseName, required: true }
        ],

        refTypeStus: [
          { required: true, message: "参号不能为空", trigger: "blur" }
        ],
        idTypeStus: [
          { required: true, message: "户号不能为空", trigger: "blur" }
        ],
      },
    }
  },
  created () {
    this.initTree()
  },
  computed: {},
  watch: {
    filterText (val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    // 提交操作
    submitForm: function (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.formAccount.id !== undefined) {
            // this.formAccount.updateUser = this.userInfo.jobNumber
            // this.formAccount.updateUserName = this.userInfo.userName
            this.lisetloading = true
            updateCodeData(this.formAccount).then(response => {
              if (response.data.data) {
                this.$message({
                  type: 'success',
                  message: '修改成功'
                })
              }
              this.lisetloading = false
              this.open = false;

              this.reset()
              this.updateAccountCode = ""
              this.queryParams.accountCodeX = this.accountCodeX
              this.getListAccount(this.queryParams);
              this.initTree();
            });
          } else {
            // this.formAccount.createUser = this.userInfo.jobNumber
            // this.formAccount.createUserName = this.userInfo.userName
            this.formAccount.accountCodeX = this.levelCodeNode + this.formAccount.accountCode
            this.formAccount.parentId = this.parentId
            this.formAccount.disabledCode = " "
            this.lisetloading = true
            doAddCode(this.formAccount).then(response => {
              if (response.data.data) {
                this.$message({
                  type: 'success',
                  message: '新增成功'
                })
              }
              this.lisetloading = false
              this.open = false;
              this.reset()
              this.updateAccountCode = ""
              this.queryParams.accountCodeX = this.accountCodeX
              this.getListAccount(this.queryParams);
              this.initTree();
            });
          }
        } else {
          return false;
        }
      });
    },
    resetForm (formName) {
      this.$refs[formName].resetFields();
    },
    // 切换页面显示条数查询
    handleSizeChange (val) {
      this.queryParams.limit = val
      this.getListAccount()
    },

    // 切换当前页查询
    handleCurrentChange (val) {
      this.queryParams.page = val
      this.getListAccount()

    },
    // 初始化树节点
    initTree () {
      getTreeNode().then(response => {
        this.treeData = response.data.data
        this.nodeKey.push(this.treeData[0].id)
      })
    },
    //初始化数据说DOC
    getListAccount () {
      this.loading = true
      queryDataByParams(this.queryParams).then(response => {
        if (response.data.data == null) {
          this.tableData = []
          this.total = 0;
          this.costAccount = true
        } else {
          this.queryParams.total = response.data.data.total
          this.tableData = response.data.data.list
          this.queryParams.accountCodeX = ""
          this.costAccount = true
          response.data.data.list = this.tableData.map(item => {
            switch (item.isVoucher) {
              case "N":
                item.isVoucher = "N-否";
                break
              case "Y":
                item.isVoucher = "Y-是";
                break
            }
            return item;
          })
        }
      })
      this.loading = false
    },
    //树节点初始化数据说DOC
    getListAccountTree () {
      this.loading = true
      queryDataByParams(this.queryParam).then(response => {
        this.queryParams.accountCodeX = ''
        this.queryParams.total = response.data.data.total
        this.tableData = response.data.data.list
        response.data.data.list = this.tableData.map(item => {
          switch (item.isVoucher) {
            case "N":
              item.isVoucher = "N-否";
              break
            case "Y":
              item.isVoucher = "Y-是";
              break
          }
          return item;
        })
        this.costAccount = true
      })
      this.loading = false
    },
    // 树节点搜索过滤
    filterNode (value, data) {
      if (!value) return true;
      return data.accountName.indexOf(value) !== -1;
    },
    handleNodeClick (data) {
      this.costAccount = false;
      this.addAccount = true;
      this.pagingYN = false;
      this.tableData = []
      this.levelCodeNode = data.accountCode
      this.parentId = data.id
      this.queryParam.accountCodeX = data.accountCode
      this.accountCodeX = data.accountCode
      this.costCodeLevelCode = data.costCodeLevelCode
      console.log(data.children)
      if (data.children === null) {
        this.costAccount = true
        this.pagingYN = true
        this.getListAccountTree();
      }
    },
    // 点击搜索查询
    handleQuery () {
      this.getListAccount()
    },
    // 点击重置按钮
    resetQuery () {
      this.queryParams = {
        Id: '',
        // 阶层的代码
        levelCode: '',
        // 阶层的名称
        levelName: '',
        //成本科目
        costCode: '',
        parentId: '',
        // 总条数
        total: 0,
        // 当前页
        page: 1,
        // 每页记录数
        limit: 10,
        compId: 'J00'
      }
      this.getListAccount()
    },
    // 添加方法
    handleAdd () {
      this.UpdateNode = false
      this.UpdateSubject = true
      this.open = true
      this.editInput = false
      this.codeCostCodeLevelCode = true
      this.determine = true
      this.form.costCodeLevelCode = this.levelCodeNode
      this.title = '添加科目'
    },
    //科目详情方法
    handleDetailsAccount (row) {
      this.UpdateSubject = true
      this.determine = false
      this.editInput = true
      this.codeCostCodeLevelCode = false
      const typeId = row.id
      getData(typeId).then(response => {
        this.formAccount = response.data.data
        this.open = true
        this.title = '查看科目'
      })
    },

    // 成本科目修改方法
    handleUpdate (row) {
      const typeId = row.id
      this.UpdateSubject = true
      this.editInput = true
      this.codeCostCodeLevelCode = false
      getData(typeId).then(response => {
        this.formAccount = response.data.data
        this.updateAccountCode = this.formAccount.accountCode
        this.open = true
        this.determine = true
        this.title = '修改科目'
      })
    },
    //禁用启用
    handleUpdateDisable (row) {
      this.updateAccountCode = ""
      const typeId = row.id
      getData(typeId).then(response => {
        this.formAccount = response.data.data
        if (this.formAccount.disabledCode === 'D') {
          this.formAccount.disabledCode = ' '
          updateState(this.formAccount).then(response => {

            if (response.data.data) {
              this.$message({
                type: 'success',
                message: '启用修改成功'
              })
              this.costAccount = true
              this.queryParams.accountCodeX = this.accountCodeX
              this.getListAccount(this.queryParams);

            }
          });
        } else if (this.formAccount.disabledCode === ' ') {
          this.formAccount.disabledCode = 'D'
          updateState(this.formAccount).then(response => {

            if (response.data.data) {
              this.$message({
                type: 'success',
                message: '禁用修改成功'
              })
              this.queryParams.accountCodeX = this.accountCodeX
              this.getListAccount(this.queryParams);

            }
          });
        }

      })
    },
    // 科目删除方法
    handleDelete (row) {
      const ids = row.id || this.ids;
      if (ids.length > 0) {
        this.$confirm('确认删除吗?删除后数据无法找回？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteCodeData(ids).then(response => {
            if (response.data.data) {
              this.$message({
                type: 'success',
                message: '删除成功'
              })
            }
            this.queryParams.accountCodeX = this.accountCodeX
            this.getListAccount(this.queryParams);
            this.updateAccountCode = ""
            this.initTree();
          })
        }).catch(() => {
        });
      }
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },

    // 表单重置
    reset () {
      this.formAccount = {
        compId: 'J00',
        isVoucher: 'N',
        isDueDate: 'N',
        isLqdt: 'N',
        isPrvlg: 'N',
        isCash: 'N',
        level: '1',
        drCr: 'N',
        unitYn: 'N',
        isFrnCrcy: 'N',
        disabledCode: ''
      }
    },
    // 取消按钮
    cancel () {
      this.updateAccountCode = ""
      this.lisetloading = false
      this.open = false;
      this.reset();
    },
  }
}
</script>
<style scoped>
.link-type {
  color: #337ab7;
  cursor: pointer;
}

/*.avue-crud .el-date-editor.el-input {*/
/*  width: auto !important;*/
/*}*/
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
/deep/.el-tree {
  display: inline-block;
  min-width: 100%;
}
</style>
<style>
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
