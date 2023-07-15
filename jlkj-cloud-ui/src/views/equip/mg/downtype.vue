<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <!--       table主体 -->
        <div class="head-container">
          <el-input placeholder="输入关键字进行过滤"
                    v-model="filterText"
                    clearable
                    size="small"
                    prefix-icon="el-icon-search"
                    style="margin-bottom: 20px">
          </el-input>
        </div>
        <div class="head-container">
          <el-tree
            class="filter-tree"
            :data="treeData"
            :props="defaultProps"
            default-expand-all
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            highlight-current
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--      信息数据查询-->
      <el-col :span="20" :xs="24">
        <!--           条件搜索 -->
        <el-form :model="queryParams" v-show="showSearch" ref="queryForm" size="small" :inline="true">
          <el-form-item label="停机原因群码" prop="groupcode" label-width="100px">
            <el-input
              v-model="queryParams.groupcode"
              type="text"
              clearable
              placeholder="请输入停机原因群码"
            />
          </el-form-item>
          <el-form-item>
            <el-button size="mini" type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
            <el-button size="mini" type="default" icon="el-icon-refresh-left" @click="handleReset">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              size="mini"
              plain
              type="primary"
              icon="el-icon-plus"
              @click="handleAdd">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              plain
              @click="handleDelete">删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="8">
            <label class="el-form-item__label"
                   style="width: 100%;line-height: 40px;font-weight: bold;font-size: 14px;text-align: left">
              停机细类型编号: {{this.groupcode}}
            </label>
          </el-col>
          <el-col :span="8">
            <label class="el-form-item__label"
                   style="width: 100%;line-height: 40px;font-weight: bold;font-size: 14px;text-align: left">
              停机细类型名称: {{this.groupname}}
            </label>
          </el-col>
        </el-row>
        <el-table v-loading="loading"
                  :data="tableData"
                  stripe
                  :default-sort="{prop: 'groupcode', order: 'descending'}"
                  @selection-change="handleSelectionChange">
          <el-table-column type="selection"
                           width="60"
                           align="center" />
          <el-table-column type="index"
                           label="序号"
                           :index="indexMethod"
                           align="center" />
          <template v-for="column in columns">
            <el-table-column :key="column.prop"
                             :fixed="column.fixed"
                             :prop="column.prop"
                             :label="column.label"
                             :sortable="column.sortable"
                             :width="column.width"
                             :align="column.align">
            </el-table-column>
          </template>
          <el-table-column label="操作"
                           header-align="center"
                           align="center">
            <template slot-scope="scope">
              <el-button
                size="mini"
                plain
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)">
                修改
              </el-button>
              <el-button
                size="mini"
                plain
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.page"
          :limit.sync="queryParams.limit"
          @pagination="getList"
        />
      </el-col>
    </el-row>
    <!--    添加或修改弹框-->
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
                 label-width="120px">
          <el-row>
            <el-col :span="24">
              <el-form-item label="停机原因群码"
                            prop="groupcode">
                <el-input v-model="form.groupcode"
                          placeholder="请输入停机原因群码" />
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="停机原因编号"
                            prop="mgmc2no">
                <el-input v-model="form.mgmc2no"
                          placeholder="请输入停机原因编号" />
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="停机原因名称"
                            prop="mgmc2name">
                <el-input v-model="form.mgmc2name"
                          placeholder="请输入停机原因名称" />
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="备注说明"
                            prop="memo">
                <el-input v-model="form.memo"
                          placeholder="请输入备注说明" />
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
</template>

<script>

import { getTreeNode } from '@/api/equip/mg/parameter'
import { addMgmc2Data, queryDataByParams, getData, updateMgmc2Data, deleteMgmc2Data } from '@/api/equip/mg/downtype'
export default {
  name: "analyze",
  components: {},
  data () {
    return {
      // 显示搜索条件
      showSearch: true,
      // 遮罩层
      loading: true,
      // 树节点过滤
      filterText: '',
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //停机细类型编号
      groupcode: '',
      //停机细类型名称
      groupname: '',
      // 表单参数
      form: {},
      // 选中数组
      ids: [],
      //提交状态
      states: false,
      // 表格数据
      tableData: [],
      // 树节点数据
      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        // 停机原因群码
        groupcode: '',
        //停机类型编码
        mgmc1no: '',
        // 当前页
        page: 1,
        // 每页记录数
        limit: 10,
      },
      // 表格字段
      columns: [
        { label: '停机原因群码', prop: "groupcode", align: "center", fixed: false },
        { label: '停机原因编号', prop: "mgmc2no", align: "center", fixed: false },
        { label: '停机原因名称', prop: "mgmc2name", align: "center", fixed: false },
        { label: '备注', prop: "memo", fixed: false },
      ],
      // 表单校验
      rules: {
        groupcode: [
          { required: true, message: "停机原因群码不能为空", trigger: "blur" },
          { max: 6, message: '停机原因群码最长为6 个字符', trigger: 'blur' }
        ],
        mgmc2no: [
          { required: true, message: "停机原因编号不能为空", trigger: "blur" },
          { max: 6, message: '停机原因编号最长为6 个字符', trigger: 'blur' }
        ],
        mgmc2name: [
          { required: true, message: "停机原因名称不能为空", trigger: "blur" },
          { max: 20, message: '停机原因名称最长为20 个字符', trigger: 'blur' }
        ],
      },
    }
  },
  created () {
    this.initTree()
    this.getList()
  },
  watch: {
    filterText (val) {
      this.$refs.tree.filter(val);
    }
  },
  computed: {
  },
  methods: {
    // 重置
    handleReset() {
      this.resetForm("queryForm")
      this.handleQuery();
    },
    // 初始化树节点
    initTree () {
      getTreeNode().then(response => {
        this.treeData = response.data
      })
    },
    //初始化数据
    getList () {
      // this.queryParams.page = page
      this.loading = true
      queryDataByParams(this.queryParams).then(response => {
        this.total = response.data.total
        this.tableData = response.data.list
      })
      this.loading = false
    },
    // 树节点搜索过滤
    filterNode (value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick (data) {
      this.groupcode = data.code
      this.groupname = data.label
      this.queryParams.mgmc1no = data.code
      this.getList()
    },
    // 点击搜索查询
    handleQuery () {
      this.getList()
    },
    // 序号设置
    indexMethod (index) {
      index = (index + 1) + (this.queryParams.page - 1) * this.queryParams.limit
      return index
    },
    // 新增方法
    handleAdd () {
      this.reset()
      if (this.queryParams.mgmc1no == '' || this.queryParams.mgmc1no == null || this.queryParams.mgmc1no == undefined) {
        this.$modal.msgWarning('请先选择对应树节点')
      } else {
        this.open = true
        this.title = '添加'
      }
    },
    // 修改方法
    handleUpdate (row) {
      this.reset()
      const typeId = row.id || this.ids
      if (this.ids.length > 1) {
        this.$modal.msgWarning('只能选择一笔数据进行修改操作')
      } else if (this.ids.length < 1 && (typeId == null || typeId.length < 1)) {
        this.$modal.msgWarning('请选择一笔数据进行操作')
      } else {
        getData(typeId).then(response => {
          this.form = response.data
          this.open = true
          this.title = '修改'
        })
      }
    },
    // 删除方法
    handleDelete (row) {
      const ids = row.id || this.ids;
      if (ids.length > 0) {
        this.$confirm('确认删除吗?删除后数据无法找回？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteMgmc2Data(ids).then(response => {
            if (response.data) {
              this.$modal.msgSuccess('删除成功')
            }
            this.getList()
          })
        }).catch(() => { });
      }
    },
    // 导出方法
    handleExport () {
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 提交操作
    submitForm: function () {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.states = true
          if (this.form.id != undefined) {
            updateMgmc2Data(this.form).then(response => {
              this.states = false
              if (response.data) {
                this.$modal.msgSuccess('修改成功')
                this.open = false;
                this.getList(this.queryParams.page);
              }
            });
          } else {
            this.form.mgmc1no = this.queryParams.mgmc1no
            addMgmc2Data(this.form).then(response => {
              this.states = false
              if (response.data) {
                this.$modal.msgSuccess('新增成功')
                this.open = false;
                this.getList();
              }
            });
          }
        }
      })
    },
    // 表单重置
    reset () {
      this.form = {
        mgmc1no: undefined,
        groupcode: undefined,
        mgmc2no: undefined,
        mgmc2name: undefined,
        memo: undefined,
      }
    },
    // 取消按钮
    cancel () {
      this.open = false;
      this.reset();
    },
  }
}
</script>

<style scoped>
/*.el-input {*/
/*  width: auto !important;*/
/*}*/
.avue-crud .el-date-editor.el-input {
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

