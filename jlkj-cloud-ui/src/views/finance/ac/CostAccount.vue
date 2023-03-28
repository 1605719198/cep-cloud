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
                  <el-input placeholder="搜索科目索引"
                            v-model="filterText"
                            style="margin-bottom: 20px">
                  </el-input>
                </div>
                <div class="head-container"
                     style="height:700px;overflow:auto;">
                  <el-tree class="filter-tree"
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
                          <el-input v-model="queryParams.costCode"
                                    type="text"
                                    clearable
                                    placeholder="请输入成本科目"
                                    @keyup.enter.native="handleQuery" />
                        </div>
                      </el-col>
                      <el-col :span="5">
                        <div class="el-form-item__content"
                             style="margin-left: 0px;">
                          <el-button v-hasPermi="['code_doQueryAll']"
                                     size="mini"
                                     type="primary"
                                     @click="handleQuery"
                                     icon="el-icon-search">搜索</el-button>
                          <el-button size="mini"
                                     type="default"
                                     @click="resetQuery"
                                     icon="el-icon-refresh-left">重置</el-button>
                        </div>
                      </el-col>

                      <el-col :span="15">
                        <div class="el-form-item el-form-item--small"
                             style="float: right">
<!--                          v-hasPermi="['code_doAdd']"-->
                          <el-button v-if="addNode"
                                     type="primary"
                                     size="medium"
                                     icon="el-icon-plus"
                                     @click="AddleUpdate">添加节点
                          </el-button>
<!--                          v-hasPermi="['code_doAdd']"-->
                          <el-button v-if="addAccount"
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
                            width="100%">
                    <el-table-column type="index"
                                     sortable
                                     label="序号"
                                     :index="indexMethod"
                                     align="center"
                                     width="140" />
                    <el-table-column label="成本科目"
                                     sortable
                                     align="center"
                                     prop="costCode" />
                    <el-table-column label="成本科目名称"
                                     sortable
                                     align="center"
                                     prop="costDesc" />
                    <el-table-column label="会计科目"
                                     sortable
                                     align="center"
                                     prop="accountCode" />
                    <el-table-column label="会计科目名称"
                                     sortable
                                     align="center"
                                     prop="accountName" />
                    <el-table-column label="系统使用时机"
                                     sortable
                                     align="center"
                                     prop="expenseSource" />
                    <el-table-column label="操作"
                                     align="center"
                                     class-name="small-padding fixed-width"
                                     fixed="right"
                                     width="280">
                      <template slot-scope="scope">
                        <el-button v-hasPermi="['code_doQueryOne_id']"
                                   icon="el-icon-info"
                                   type="text"
                                   size="mini"
                                   @click="handleDetailsAccount(scope.row)">详情
                        </el-button>
                        <el-button v-hasPermi="['code_doEdit']"
                                   size="mini"
                                   icon="el-icon-edit"
                                   type="text"
                                   @click="handleUpdate(scope.row,)">编辑
                        </el-button>
                        <el-button v-hasPermi="['code_doDelete_ids']"
                                   size="mini"
                                   type="text"
                                   icon="el-icon-delete"
                                   @click="handleDelete(scope.row)">删除
                        </el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                  <el-table key="table1"
                            v-if="costIndexes"
                            v-loading="loading"
                            height="67vh"
                            size="small"
                            :data="tableDataNode"
                            width="100%">
                    <el-table-column label="阶层编码"
                                     sortable
                                     align="center"
                                     prop="upperCodex" />
                    <el-table-column label="阶层名称"
                                     sortable
                                     align="center"
                                     prop="upperName" />
                    <el-table-column label="上层节点编码"
                                     sortable
                                     align="center"
                                     prop="levelCode" />
                    <el-table-column label="上层节点名称"
                                     sortable
                                     align="center"
                                     prop="levelName" />
                    <el-table-column label="节点类别"
                                     sortable
                                     align="center"
                                     prop="nodeType">
                      <template slot-scope="scope">
                        <dict-tag :options="dict.type.cost_nodeTypeAccount"
                                  :value="scope.row.nodeType" />
                      </template>
                    </el-table-column>
                    <el-table-column label="操作"
                                     align="center"
                                     class-name="small-padding fixed-width"
                                     fixed="right"
                                     width="280">
                      <template slot-scope="scope">
                        <el-button plain
                                   icon="el-icon-info"
                                   type="info"
                                   size="mini"
                                   @click="handleDetails(scope.row,scope.$index)">详情
                        </el-button>
                        <el-button size="mini"
                                   type="text"
                                   icon="el-icon-info"
                                   @click="menuUpdate(scope.row,scope.$index)">修改
                        </el-button>
                        <el-button size="mini"
                                   type="text"
                                   icon="el-icon-delete"
                                   @click="menuDelete(scope.row)">删除
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
                                   :page-sizes="[20, 50, 100, 2000]"
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
                 v-if="UpdateNode"
                 :visible.sync="open"
                 width="1000px"
                 append-to-body
                 :close-on-click-modal="false">
        <el-form ref="form"
                 :model="form"
                 :rules="rules"
                 label-width="150px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="阶层编码"
                            prop="upperCodex">
                <el-input style="width: 225px"
                          v-model.number="form.upperCodex"
                          placeholder="请输入阶层编码(数字)"
                          :disabled="editInput" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="阶层名称"
                            prop="upperName">
                <el-input style="width: 225px"
                          v-model="form.upperName"
                          placeholder="请输入阶层名称" />
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="阶层类别"
                            prop="nodeType">
                <el-radio-group v-model="form.nodeType"
                                :disabled="editInput">
                  <el-radio v-for="dict in dict.type.cost_nodeTypeAccount"
                            :key="dict.value"
                            :label="dict.value">{{dict.label}}</el-radio>
                </el-radio-group>

              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="上层节点编码"
                            prop="levelCode">
                <el-input style="width: 225px"
                          v-model="form.levelCode"
                          placeholder="请输入上层节点编码"
                          :disabled="true" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="上层节点名称"
                            prop="levelName">
                <el-input style="width: 225px"
                          v-model="form.levelName"
                          placeholder="请输入上层节点名称"
                          :disabled="true" />
              </el-form-item>
            </el-col>
            <el-row v-if="menuPopup">
              <el-col :span="12">
                <el-form-item label="建立人"
                              prop="createUserName">
                  <el-input style="width: 225px"
                            v-model="form.createUserName"
                            :disabled="true" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="建立时间"
                              prop="createTime">
                  <el-input style="width: 225px"
                            v-model="form.createTime"
                            :disabled="true" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="修改人"
                              prop="updateUserName">
                  <el-input style="width: 225px"
                            v-model="form.updateUserName"
                            :disabled="true" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="修改时间"
                              prop="updateTime">
                  <el-input style="width: 225px"
                            v-model="form.updateTime"
                            :disabled="true" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-row>
        </el-form>
        <div slot="footer"
             class="dialog-footer"
             style="padding:20px 0 0 0;">
          <el-button size="medium"
                     @click="cancel">取 消</el-button>
          <el-button size="medium"
                     type="primary"
                     v-if="determine"
                     @click="submitForm('formAccount')"
                     :loading="lisetloading">确 定</el-button>
        </div>
      </el-dialog>
    </div>

    <div v-if="openAccount">
      <el-dialog class="customDialogStyle"
                 :title="title"
                 v-if="UpdateSubject"
                 :visible.sync="openAccount"
                 width="1000px"
                 append-to-body
                 :close-on-click-modal="false">
        <el-form ref="formAccount"
                 :model="formAccount"
                 :rules="rulesCode"
                 label-width="150px">
          <el-row style="height: 70px">
            <el-col :span="12">
              <el-form-item label="成本科目编码"
                            prop="costCode">
                <el-input style="width: 55px;"
                          v-if="codeCostCodeLevelCode"
                          v-model="form.costCodeLevelCode"
                          placeholder="请输入阶层编码(数字)"
                          :disabled="true" />
                <el-input :disabled="editInput"
                          style="width: 170px"
                          v-model="formAccount.costCode"
                          placeholder="请输入阶层编码(数字)" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="成本科目中文说明"
                            prop="costDesc">
                <el-input style="width: 225px"
                          v-model="formAccount.costDesc"
                          placeholder="请输入成本科目中文说明" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="计量单位"
                            prop="unit">
                <el-input style="width: 225px"
                          v-model="formAccount.unit"
                          placeholder="请输入计量单位" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="会计科目"
                            prop="accountCode">
                <el-input style="width: 225px"
                          v-model="formAccount.accountCode"
                          placeholder="请输入会计科目"
                          @keyup.enter.native="handlerAccountList"
                          :disabled="true">
                  <el-button slot="append"
                             icon="el-icon-search"
                             @click.native="handlerAccountList">
                  </el-button>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="使用时机"
                            prop="expenseSource">
                <el-radio-group v-model="formAccount.expenseSource"
                                :disabled="editInput">
                  <el-radio v-for="dict in dict.type.cost_expenseSource"
                            :key="dict.value"
                            :label="dict.value">{{dict.label}}</el-radio>
                </el-radio-group>

              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="是否AA抛AC"
                            prop="assignMark">
                <el-radio-group v-model="formAccount.assignMark"
                                :disabled="editInput">
                  <el-radio v-for="dict in dict.type.sys_yes_no"
                            :key="dict.value"
                            :label="dict.value">{{dict.label}}</el-radio>
                </el-radio-group>

              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="状态"
                            prop="stusCode">
                <el-radio-group v-model="formAccount.stusCode"
                                :disabled="editInput">
                  <el-radio v-for="dict in dict.type.cost_stuscode_name"
                            :key="dict.value"
                            :label="dict.value">{{dict.label}}</el-radio>
                </el-radio-group>

              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="建立人"
                            prop="createUserName">
                <el-input style="width: 225px"
                          v-model="formAccount.createUserName"
                          :disabled="true" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="建立时间"
                            prop="createTime">
                <el-input style="width: 225px"
                          v-model="formAccount.createTime"
                          :disabled="true" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer"
             class="dialog-footer"
             style="padding:20px 0 0 0;">
          <el-button size="medium"
                     @click="cancel">取 消</el-button>
          <el-button size="medium"
                     type="primary"
                     v-if="determine"
                     @click="submitForm('formAccount')"
                     :loading="lisetloading">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <FinanceAccountingAllTree :getTreeAccounting="getTreeAccounting"
                              :productAccountTree="productAccountTree"
                              ref="productAccountTree"
                              @closeFinanceAccountDialog="closeFinanceAccountDialog"></FinanceAccountingAllTree>

  </div>
</template>

<script>
import {
  queryDataByParams,
  getTreeNode,
  getData,
  queryMenu,
  deleteCodeData,
  updateCodeData,
  doAddCode,
  getMenuData,
  deleteMenu,
  doAddMenu,
  updateMenuData
} from "@/api/finance/ac/financeaccount";
import FinanceAccountingAllTree from '@/components/finance-manufacturer-tree/FinanceAccountingAllTree'
import { mapGetters } from "vuex";

export default {
  name: "CostAccount",

  dicts: ['cost_stuscode_name', 'cost_expensesource', 'sys_yes_no', 'cost_nodeTypeAccount'],
  components: { FinanceAccountingAllTree },
  data () {
    var accountCode = (rule, value, callback) => {
      if (value === undefined) {
        callback(new Error('成本科目编码不能为空'));
      } else if (this.formAccount.costCode.length !== 5) {
        if (value !== undefined && this.formAccount.costCode.length + this.form.costCodeLevelCode.length !== 5) {
          callback(new Error('成本科目编码为5位'));
        }
      }
      else {
        callback();
      }
      callback();
    };
    return {
      //增改加载按钮
      lisetloading: false,
      //分页显示判断
      pagingYN: false,
      //弹窗栏位是否编辑
      editInput: false,

      levelCodeNode: '',
      nodeTypeNode: '',
      levelNameNode: '',
      placeholder: '',
      costCodeNode: '',
      codeCostCodeLevelCode: false,
      costCodeLevelCodeUp: '',
      placeholde: '请选择区域',
      //节点建立人日期显示
      menuPopup: false,
      //确定
      determine: true,
      //会计树弹窗
      productAccountTree: false,
      //添加节点按钮
      addNode: false,
      //添加科目按钮
      addAccount: false,
      //节点弹窗判断
      UpdateNode: false,
      //科目弹窗判断
      UpdateSubject: false,
      //索引表格
      costIndexes: false,
      //科目表格
      costAccount: false,
      // 遮罩层
      loading: true,
      costCodeLevelCode: '',
      // 树节点过滤
      filterText: '',
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openAccount: false,
      // 表单参数
      form: { compId: 'J00' },
      //成本科目表单
      formAccount: {
        compId: 'J00',
        expenseSource: 'R'
      },
      // 选中数组
      ids: [],
      // 表格数据
      tableData: [],
      //树表格数据
      tableDataNode: [],
      // 树节点数据
      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'levelName'
      },
      // 查询参数
      queryParams: {
        Id: '',
        // 阶层的代码
        levelCode: '',
        // 阶层的名称
        levelName: '',
        //成本科目
        costCode: '',
        //树节点成本科目
        costCodex: '',
        parentId: '',
        // 总条数
        total: 0,
        // 当前页
        page: 1,
        // 每页记录数
        limit: 10,
        compId: 'J00'
      },
      // 树节点查询参数
      queryParam: {
        Id: '',
        // 阶层的代码
        levelCode: '',
        // 阶层的名称
        levelName: '',
        //成本科目
        costCode: '',
        //树节点成本科目
        costCodex: '',
        parentId: '',
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
        upperCodex: [
          { required: true, message: "阶层编码不能为空", trigger: "blur" },
        ],
        nodeType: [
          { required: true, message: "阶层类别不能为空", trigger: "blur" }
        ],
        upperName: [
          { required: true, message: "阶层名称不能为空", trigger: "blur" }
        ],
        levelName: [
          { required: true, message: "上层节点名称不能为空", trigger: "blur" }
        ],
      },
      rulesCode: {

        costCode: [
          { validator: accountCode, required: true }
        ],
        costDesc: [
          { required: true, message: "成本科目中文说明不能为空", trigger: "blur" }
        ],
        unit: [
          { required: true, message: "计量单位不能为空", trigger: "blur" }
        ],
        accountCode: [
          { required: true, message: "会计科目不能为空,至少为4位编码", trigger: "blur", min: 4 }
        ],
        expenseSource: [
          { required: true, message: "使用时机不能为空", trigger: "blur" }
        ],
        assignMark: [
          { required: true, message: "是否AA抛AC不能为空", trigger: "blur" }
        ],
        stusCode: [
          { required: true, message: "状态不能为空", trigger: "blur" }
        ]
      },
    }
  },
  created () {
    this.initTree()
  },
  computed: {
  },
  watch: {
    filterText (val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    // 切换页面显示条数查询
    handleSizeChange (val) {
      if (this.costIndexes) {
        this.queryParams.limit = val
        this.getListNode()
      }
      if (this.costAccount) {
        this.queryParams.limit = val
        this.getListAccount()
      }
    },

    closeFinanceAccountDialog () {
      this.productAccountTree = false;
    },
    handlerAccountList () {
      this.productAccountTree = true

    },
    getTreeAccounting (data) {
      this.formAccount.accountCode = data.accountCode
    },
    closeFinanceCodeDialog () {
      this.productCodeTree = false;
    },
    // 切换当前页查询
    handleCurrentChange (val) {
      if (this.costIndexes) {
        this.queryParams.page = val
        this.getListNode()
      }
      if (this.costAccount) {
        this.queryParams.page = val
        this.getListAccount()
      }
    },
    // 初始化树节点
    initTree () {
      getTreeNode().then(response => {
        this.treeData = response.data
        if (this.treeData.length === 0) {
          this.addNode = true
        }
      })
    },
    //初始化数据DIR
    getListNode () {
      this.loading = true
      this.costIndexes = true
      queryMenu(this.queryParams).then(response => {
        this.tableData = []
        this.queryParams.total = response.data.total
        this.tableDataNode = response.data.list
        let t = response.data.list.length
        for (let i = 0; i < t; i++) {
          this.tableDataNode[i].upperCodex = response.data.list[i].costCodeLevelCode
          this.tableDataNode[i].upperName = response.data.list[i].levelName
          this.tableDataNode[i].levelCode = response.data.list[i].upperCode
          this.tableDataNode[i].levelName = this.levelNameNode;
        }
      })
      this.loading = false
    },
    //初始化数据说DOC
    getListAccount () {
      this.loading = true
      this.costIndexes = false

      queryDataByParams(this.queryParams).then(response => {
        if (response.data == null) {
          this.tableData = []
          this.total = 0;
        } else {
          this.tableData = response.data.list;
          this.total = response.data.total;
          this.tableData = this.tableData.map(item => {
            switch (item.expenseSource) {
              case 'R':
                item.expenseSource = 'R-内部转移';
                break
              case 'N':
                item.expenseSource = 'N-直接使用';
                break
            }
            return item;
          })
        }
        this.costAccount = true
        this.tableDataNode = []
        this.queryParams.costCode = ''
        this.loading = false
      })
    },

    //树查询初始化数据说DOC
    getListAccountTree () {
      this.loading = true
      this.costIndexes = false
      this.queryParams.costCode = ''
      queryDataByParams(this.queryParam).then(response => {
        if (response.data == null) {
          this.tableData = []
          this.total = 0;
        } else {
          this.tableData = response.data.list;
          this.total = response.data.total;
          this.tableData = this.tableData.map(item => {
            switch (item.expenseSource) {
              case 'R':
                item.expenseSource = 'R-内部转移';
                break
              case 'N':
                item.expenseSource = 'N-直接使用';
                break
            }
            return item;
          })
        }
        this.costAccount = true
        this.tableDataNode = []
      })
      this.loading = false
    },
    // 树节点搜索过滤
    filterNode (value, data) {
      if (!value) return true;
      return data.levelName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick (data) {
      this.pagingYN = false;
      this.costAccount = false;
      this.costIndexes = false;
      this.addNode = false;
      this.addAccount = false;
      this.tableData = []
      this.tableDataNode = []
      this.queryParams.Id = data.id
      this.queryParams.parentId = data.id
      this.form.id = data.id
      this.form.levelCode = data.costCodeLevelCode
      this.form.levelName = data.levelName
      this.levelCodeNode = data.levelCode
      this.levelNameNode = data.levelName
      this.nodeTypeNode = data.nodeType
      if (data.nodeType !== 'DIR') {
        this.costAccount = true
        this.addAccount = true
        this.pagingYN = true
        this.queryParam.costCode = data.costCodeLevelCode
        this.costCodeLevelCode = data.costCodeLevelCode
        this.getListAccountTree();//doc
      } else {
        this.addNode = true
        this.costIndexes = true
        this.pagingYN = true
        this.queryParams.levelCode = data.levelCode
        this.costCodeLevelCode = data.costCodeLevelCode
        this.queryParams.Id = data.id
        this.getListNode();//dir
      }
    },
    //点击重置
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
      this.pagingYN = true,
        this.loading = false,
        this.costIndexes = false
      queryDataByParams(this.queryParams).then(response => {
        this.queryParams.total = response.data.total
        this.tableData = response.data.list
        this.tableData = this.tableData.map(item => {
          switch (item.expenseSource) {
            case 'R':
              item.expenseSource = 'R-内部转移';
              break
            case 'N':
              item.expenseSource = 'N-直接使用';
              break
          }
          return item;
        })
        this.costAccount = true
        this.tableDataNode = []
      })
    },
    // 点击搜索查询
    handleQuery () {
      this.pagingYN = true
      this.getListAccount()
    },
    // 点击重置按钮
    handleReset () {
      this.queryParams.reasonCode = null
    },
    // 序号设置
    indexMethod (index) {
      index = (index + 1) + (this.queryParams.page - 1) * this.queryParams.limit
      return index
    },
    // 添加方法
    handleAdd () {
      this.formAccount = { compId: 'J00', expenseSource: 'R', assignMark: 'Y', stusCode: '空' }
      this.UpdateNode = false
      this.UpdateSubject = true
      this.openAccount = true
      this.editInput = false
      this.codeCostCodeLevelCode = true
      this.determine = true
      this.form.costCodeLevelCode = this.costCodeLevelCode
      this.title = '添加科目'
    },
    AddleUpdate () {
      this.form.nodeType = 'DIR'
      this.editInput = false
      this.UpdateSubject = false
      this.open = true
      this.UpdateNode = true
      this.menuPopup = false
      this.title1 = '添加节点'
    },
    //科目详情方法
    handleDetailsAccount (row) {
      this.UpdateSubject = true
      this.determine = false
      this.editInput = true
      this.codeCostCodeLevelCode = false
      const typeId = row.id
      getData(typeId).then(response => {
        this.formAccount = response.data
        this.openAccount = true
        this.title = '查看科目'
      })
    },
    //节点查看详情
    handleDetails (row, index) {
      this.editInput = true
      this.UpdateNode = true
      this.determine = false
      this.menuPopup = true
      const typeId = row.id
      getMenuData(typeId).then(response => {
        this.form = response.data
        this.form.levelName = this.levelNameNode
        this.form.levelCode = this.tableDataNode[index].levelCode
        this.form.nodeType = this.tableDataNode[index].nodeType
        this.open = true
        this.title = '查看节点'
      })
    },
    // 成本科目修改方法
    handleUpdate (row) {
      const typeId = row.id
      this.UpdateSubject = true
      this.editInput = false
      this.codeCostCodeLevelCode = false
      getData(typeId).then(response => {
        this.formAccount = response.data
        this.openAccount = true
        this.determine = true
        this.title = '修改科目'
      })
    },
    // 节点修改方法
    menuUpdate (row, index) {
      const typeId = row.id
      this.UpdateNode = true
      this.menuPopup = false
      this.editInput = true
      getMenuData(typeId).then(response => {
        this.form = response.data
        this.form.levelName = this.levelNameNode
        this.form.levelCode = this.tableDataNode[index].levelCode
        this.form.nodeType = this.tableDataNode[index].nodeType
        this.open = true
        this.determine = true
        this.title = '修改节点'
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
            if (response.data) {
              this.$message({
                type: 'success',
                message: '删除成功'
              })
            }
            this.queryParams.costCode = this.costCodeLevelCode

            this.getListAccount(this.queryParams)
          })
        }).catch(() => {
        });
      }
    },
    // 节点删除方法
    menuDelete (row) {
      const ids = row.id || this.ids;
      if (ids.length > 0) {
        this.$confirm('确认删除吗?删除后数据无法找回？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteMenu(ids, row.upperCodex).then(response => {
            if (response.data) {
              this.$message({
                type: 'success',
                message: '删除成功'
              })
            }
            this.getListNode()
            this.initTree();
          })
        }).catch(() => {
        });
      }
    },
    // 提交操作
    submitForm: function (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.formAccount.id !== undefined) {
            this.formAccount.updateUser = '123456'
            this.formAccount.updateUserName = '姓名'
            this.lisetloading = true
            updateCodeData(this.formAccount).then(response => {
              if (response.data) {
                this.$message({
                  type: 'success',
                  message: '科目修改成功'
                })
                this.lisetloading = false
                this.openAccount = false;
                this.reset()
                this.queryParams.costCode = this.costCodeLevelCode
                this.getListAccount(this.queryParams);
              }
            });
          } else {
            this.formAccount.createUser = '123456'
            this.formAccount.createUserName = '姓名'

            this.formAccount.costCode = this.form.costCodeLevelCode + this.formAccount.costCode
            this.lisetloading = true
            doAddCode(this.formAccount).then(response => {
              if (response.data) {
                this.$message({
                  type: 'success',
                  message: '科目新增成功'
                })
              }
              this.lisetloading = false
              this.openAccount = false;
              this.reset()
              this.queryParams.costCode = this.costCodeLevelCode
              this.getListAccount(this.queryParams);

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
    // 树提交操作
    submitFormMenu: function (formName) {
      this.lisetloading = true
      this.$refs[formName].validate((valid) => {
        if (valid) {

          if (this.form.parentId !== undefined) {
            this.form.updateUser = '123456'
            this.form.updateUserName = '姓名'
            this.lisetloading = false
            updateMenuData(this.form).then(response => {
              if (response.data) {
                this.$message({
                  type: 'success',
                  message: '修改成功'
                })
                this.open = false;
                this.reset()
                this.getListNode()
                this.initTree();
              }
            });
          } else {
            this.form.createUser = '123456'
            this.form.createUserName = '姓名'
            this.form.costCodeLevelCode = this.costCodeLevelCode + this.form.upperCodex
            this.lisetloading = false
            doAddMenu(this.form).then(response => {
              if (response.data) {
                this.$message({
                  type: 'success',
                  message: '新增成功'
                })
              }
              this.open = false;
              this.reset()
              this.getListNode();
              this.initTree();
            });
          }
        } else {
          return false;
        }
      })
      this.lisetloading = false
    },
    // 表单重置
    reset () {
      this.form = { compId: 'J00', nodeType: 'DIR' };
      this.formAccount = { compId: 'J00', expenseSource: 'R', assignMark: 'Y', stusCode: '空' }
    },
    // 取消按钮
    cancel () {
      this.lisetloading = false
      this.open = false;
      this.openAccount = false;
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
</style>
<style scoped>
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

::v-deep .el-tree {
  display: inline-block;
  min-width: 100%;
}
</style>

