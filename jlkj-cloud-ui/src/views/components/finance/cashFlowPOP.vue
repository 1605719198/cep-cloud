<template>
  <el-dialog title="现金流量代码pop" :visible.sync="visible" width="1080px" top="5vh"
             append-to-body :close-on-click-modal="false">
  <div class="app-container">
    <el-row>
      <el-col :span="24"
              style="padding:0 10px">
        <!--       table主体 -->
        <div class="plan_main">
          <div class="avue-crud el-card__body"
               style="width: 98%;border: 0">
            <el-row :gutter="20">
              <!--      树形结构数据-->
              <el-col :span="8"
                      >
                <div class="head-container">
                  <el-select v-model="queryParams.companyId" style="margin-bottom: 20px;    width: 228px;" filterable placeholder="请输入会计公司">
                    <el-option
                      v-for="item in companyList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                      @click.native="changCompanyId(item)"
                      @keyup.enter.native="handleQuery">
                    </el-option>
                  </el-select>
                  <el-input placeholder="搜索科目索引"
                            v-model="filterText"
                            style="margin-bottom: 20px;width: 230px;">
                  </el-input>
                </div>
                <div class="head-container"
                     style="overflow:auto;">
                  <el-tree class="filter-tree"
                           :data="treeData"
                           :props="defaultProps"
                           :filter-node-method="filterNode"
                           ref="tree"
                           @node-click="handleNodeClick">
                  </el-tree>
                </div>
              </el-col>
              <el-col :span="16">
                <!--           条件搜索 -->
                <div class="avue-crud__search"
                     style="border: 0">
                  <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                          >
                    <el-form-item label="" prop="cashFlowName">
                      <el-input
                        v-model="queryParams.cashFlowName"
                        placeholder="请输入现金流量表代码名称"
                        clearable
                        @keyup.enter.native="handleQuery"
                      />
                    </el-form-item>
                    <el-form-item>
                      <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                      <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                    </el-form-item>
                  </el-form>
                </div>
                  <el-table v-loading="loading" :data="tCodeList"
                            @selection-change="handleSelectionChange"
                            @row-dblclick="rowClick"
                            ref="table">
                      <el-table-column type="selection" width="55" align="center"/>
                    <el-table-column prop="cashFlowCode" label="现金流量表代码"/>
                    <el-table-column label="现金流量表代码名称" align="center" prop="cashFlowName"/>
                  </el-table>
                <pagination
                  v-show="total>0"
                  :total="total"
                  :page.sync="queryParams.pageNum"
                  :limit.sync="queryParams.pageSize"
                  @pagination="getList"
                />

              </el-col>

            </el-row>

          </div>
        </div>
      </el-col>
    </el-row>
  </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSelectCoder">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>

import {listCodecompid, getTreeNodeCompId} from "@/api/finance/aa/codecompid";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
export default {
  name: "cashFlowPOP",
  dicts: ['aa_quedataway', 'aa_inorout', 'sys_yes_no'],
  data() {
    return {
      defaultProps: {
        children: 'children',
        label: 'cashFlowName'
      },
      // 遮罩层
      visible: false,
      // 会计公司下拉选单
      companyList: [],
      // 树节点过滤
      filterText: '',
      // 树节点数据
      treeData: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 现金流量代码表格数据
      tCodeList: [],
      codeList: [],
      // 子表选中数据
      checkedTCapitalDetail: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cashFlowName: null,
        companyId: null,
      },
      // 表单参数
      form: {tCodeList: []},
      // 表单校验
      compIdList:{}
    };
  },
  created() {
    this.queryParams.parentId = "1"
    this.getList()
    this.getCompanyList();

  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    // 显示弹框
    show(queryParams) {
      if(queryParams){
        this.queryParams = queryParams;
      }
      this.queryParams.parentId = "1"
      this.getList();
      this.getCompanyList();
      this.visible = true;
    },
    handleSelectCoder(){

      console.log(this.codeList.length);
      if (this.codeList.length>1){
        this.$modal.msgError("只能选择一笔数据");
        return;
      }
      if (this.codeList.length==0){
        this.$modal.msgError("请选择要分配的现金流量代码");
        return;
      }
      this.visible = false;
      this.$emit("pop",this.compIdList);
    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
        this.queryParams.companyId = this.companyList[0].value
        this.initTree()
      });
    },
    renderHeaderStar(h, {column}) {
      return h(
        'div',
        [
          h('i', '*'),
          h('span', column.label)
        ]
      )
    },

    initTree() {
      getTreeNodeCompId(this.queryParams.companyId).then(response => {
        this.treeData = response.data
      })
    },
    //
    // 树节点搜索过滤
    filterNode(value, data) {
      if (!value) return true;
      return data.levelName.indexOf(value) !== -1;
    },
    //公司下拉选单掉用方法
    changCompanyId(val) {
      this.queryParams.companyId = val.value
      this.initTree()
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.parentId = data.id
      this.form.parentId = data.id
      this.form.parentCode = data.cashFlowCode
      this.form.parentName = data.cashFlowName
      this.form.companyId = data.companyId
      this.getList()
    },
    /** 查询现金流量代码列表 */
    getList() {
      this.loading = true;
      listCodecompid(this.queryParams).then(response => {
        this.tCodeList = response.rows;
        this.total = response.total;
        if (response.rows.length > 0) {
          this.form.parentId = response.rows[0].parentId
          this.form.parentCode = response.rows[0].parentCode
          this.form.parentName = response.rows[0].parentName
        }

        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        companyId: null,
        cashFlowCode: null,
        cashFlowName: null,
        parentId: null,
        parentCode: null,
        isunabl: null,
        inorout: null,
        quedataway: null,
        leve: null,
        status: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateName: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },


    /** 细项维护添加按钮操作 */
    handleAddTCapitalDetail() {
      let obj = {};
      obj.cashFlowCode = "";
      obj.cashFlowName = "";
      obj.isunabl = "";
      obj.inorout = "";
      obj.quedataway = "";
      this.tCodeList.push(obj);
    },
    // 行点击事件
    rowClick(row){
      this.$refs.table.toggleRowSelection(row);

    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.codeList=selection
      this.compIdList=  this.codeList[0]
      console.log(this.compIdList);
      this.checkedTCapitalDetail = selection.map(item => item.index)
      this.codeList = selection
    },
  }
};
</script>
<style scoped>
.billDialog {
}

</style>

