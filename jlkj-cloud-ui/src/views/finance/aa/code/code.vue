<template>
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
              <el-col :span="4"
                      :xs="24">
                <div class="head-container">
                  <el-input placeholder="搜索代码或名称索引"
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
              <el-col :span="20"
                      :xs="24">
                <!--           条件搜索 -->
                <div class="avue-crud__search"
                     style="border: 0">
                  <el-form :model="queryParams"
                           ref="queryForm" size="small" :inline="true" v-show="showSearch"
                           label-width="148px">
                    <el-form-item label="现金流量名称" prop="cashFlowName">
                      <el-input
                        v-model="queryParams.cashFlowName"
                        placeholder="请输入现金流量名称"
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
                <el-row :gutter="10" class="mb8">
                  <el-col :span="1.5">
                    <el-button
                      type="primary"
                      plain
                      icon="el-icon-plus"
                      size="mini"
                      @click="handleAdd"
                      v-hasPermi="['aa:code:add']"
                    >保存
                    </el-button>
                  </el-col>
                  <el-col :span="1.5">
                    <el-button
                      type="success"
                      plain
                      icon="el-icon-edit"
                      size="mini"
                      :disabled="single"
                      @click="handleExport"
                      v-hasPermi="['aa:code:export']"
                    >导出
                    </el-button>
                  </el-col>
                  <el-col :span="1.5">
                    <el-button
                      type="primary"
                      icon="el-icon-plus"
                      size="mini"
                      v-hasPermi="['aa:code:Add']"
                      @click="handleAddTCapitalDetail">添加
                    </el-button>
                  </el-col>

                  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </el-row>

                  <el-form ref="form" :model="form" :rules="rules">
                    <el-table v-loading="loading" :data="form.tCodeList"
                              @selection-change="handleSelectionChange"
                              @row-dblclick="rowClick"
                              :row-class-name="rowTCapitalDetailIndex" ref="tCapitalDetail">
                      <el-form-item  >
                      <el-table-column type="selection" width="55" align="center"/>
                      </el-form-item>
                      <el-table-column prop="cashFlowCode" label="现金流量代码">
                        <template slot-scope="scope">
                          <el-form-item
                            :prop="'tCodeList.' + scope.$index + '.cashFlowCode'" :rules="rules.cashFlowCode">
                            <el-input v-model="scope.row.cashFlowCode" placeholder="请输入现金流量代码"/>
                          </el-form-item>
                        </template>
                      </el-table-column>
                      <el-table-column label="现金流量名称" align="center" prop="cashFlowName">
                        <template slot-scope="scope">
                          <el-form-item :prop="'tCodeList.' + scope.$index + '.cashFlowName'" :rules="rules.cashFlowName">
                          <el-input v-model="scope.row.cashFlowName" placeholder="请输入现金流量名称"/>
                          </el-form-item>
                        </template>
                      </el-table-column>
                      <!--      <el-table-column label="上层ID" align="center" prop="parentId" />
                            <el-table-column label="上层代码" align="center" prop="parentCode" />-->
                      <el-table-column label="是否可用" align="center" prop="isunabl">
                        <template slot-scope="scope">
                          <el-form-item :prop="'tCodeList.' + scope.$index + '.isunabl'" :rules="rules.isunabl">
                          <el-select v-model="scope.row.isunabl" placeholder="请选择是否可用">
                            <el-option
                              v-for="dict in dict.type.sys_yes_no"
                              :key="dict.value"
                              :label="dict.label"
                              :value="dict.value"
                            ></el-option>
                          </el-select>
                          </el-form-item>
                        </template>
                      </el-table-column>
                      <el-table-column label="流入流出" align="center" prop="inorout">
                        <template slot-scope="scope">
                          <el-form-item :prop="'tCodeList.' + scope.$index + '.inorout'" >
                          <el-select v-model="scope.row.inorout" placeholder="请选择流入流出">
                            <el-option
                              v-for="dict in dict.type.aa_inorout"
                              :key="dict.value"
                              :label="dict.label"
                              :value="dict.value"
                            ></el-option>
                          </el-select>
                          </el-form-item>
                        </template>
                      </el-table-column>
                      <el-table-column label="取值方式" align="center" prop="quedataway">
                        <template slot-scope="scope">
                          <el-form-item :prop="'tCodeList.' + scope.$index + '.quedataway'" :rules="rules.quedataway">
                          <el-select v-model="scope.row.quedataway" placeholder="请选择取值方式">
                            <el-option
                              v-for="dict in dict.type.aa_quedataway"
                              :key="dict.value"
                              :label="dict.label"
                              :value="dict.value"
                            ></el-option>
                          </el-select>
                          </el-form-item>
                        </template>
                      </el-table-column>
                      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                          <el-form-item >
                          <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-edit"
                            @click="handleUpdate(scope.row)"
                            v-hasPermi="['aa:code:edit']"
                          >修改
                          </el-button>
                          <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-delete"
                            @click="handleDeleteTCapitalDetail(scope.row)"
                            v-hasPermi="['aa:code:remove']"
                          >删除
                          </el-button>
                          <el-button
                            size="mini"
                            type="text"
                            icon="el-icon-s-promotion"
                            @click="handleSkipAddFile(scope.row)"
                            v-hasPermi="['aa:code:remove']"
                          >设置
                          </el-button>
                          </el-form-item>
                        </template>
                      </el-table-column>
                    </el-table>
                  </el-form>


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
</template>

<script>
import {listCode, getCode, delCode,
  addCode, updateCode, getTreeNode} from "@/api/finance/aa/code";
import {isPassword, validateContacts} from "../../../../utils/jlkj";

export default {
  name: "Code",
  dicts: ['aa_quedataway', 'aa_inorout', 'sys_yes_no'],
  data() {
    return {
      defaultProps: {
        children: 'children',
        label: 'cashFlowName'
      },

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
      },
      // 表单参数
      form: {tCodeList: []},
      // 表单校验
      rules: {
        cashFlowCode: [
          { required: true, validator: isPassword, trigger: "blur" },
          {required: true, message: '请输入现金流量表代码', trigger: 'blur'},
          {max: 32, message: '最大长度32个字符', trigger: 'blur'}
        ],
        cashFlowName: [

          {required: true, message: '请输入现金流量表代码名称', trigger: 'blur'},
          {max: 150, message: '最大长度150个字符', trigger: 'blur'}
        ],
        isunabl: [
          {required: true, message: '请输入是否显示', trigger: 'blur'},
        ],
        quedataway: [
          {required: true, message: '请输入取值方式', trigger: 'blur'},
        ],
      }
    };
  },
  created() {
    this.queryParams.parentId = "1"
    this.getList()
    this.initTree()
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
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
      getTreeNode().then(response => {
        this.treeData = response.data
      })
    },
    //
    // 树节点搜索过滤
    filterNode(value, data) {
      if (!value) return true;
      return data.levelName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.parentId = data.id
      this.form.parentId = data.id
      this.form.parentCode = data.cashFlowCode
      this.form.parentName = data.cashFlowName

      this.getList()
    },
    /** 查询现金流量代码列表 */
    getList() {
      this.loading = true;
      listCode(this.queryParams).then(response => {
        this.form.tCodeList = response.rows;
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
    rowClick(row, column, e) {
      if (row.quedataway == '02') {
        this.$emit('change', 'second', row.cashFlowCode, row.cashFlowName,row.id);
      } else {
        this.$modal.msgError("请将取值方式不是取值，不能进入科目设置");
      }

    },
    handleSkipAddFile(row) {
      if (row.quedataway == '01') {
        this.$emit('change', 'second', row.cashFlowCode, row.cashFlowName,row.id);
      } else {
        this.$modal.msgError("请将取值方式不是取值，不能进入科目设置");
      }

    },

    /** 细项维护序号 */
    rowTCapitalDetailIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },

    /** 细项维护添加按钮操作 */
    handleAddTCapitalDetail() {
      let obj = {};
      obj.cashFlowCode = "";
      obj.cashFlowName = "";
      obj.isunabl = "";
      obj.inorout = "";
      obj.quedataway = "";
      this.form.tCodeList.push(obj);
    },
    /** 现金流量代码删除 */
    handleDeleteTCapitalDetail(row) {
      if (row.id == null||row.id==''||row.id==undefined){
        if (this.checkedTCapitalDetail.length == 0) {
          this.$modal.msgError("请先选择要删除的现金流量代码数据");
        } else {
          console.log(this.checkedTCapitalDetail);
          const tCodeList = this.form.tCodeList;
          const checkedTCapitalDetail = this.checkedTCapitalDetail;
          this.form.tCodeList = tCodeList.filter(function (item) {
            return checkedTCapitalDetail.indexOf(item.index) == -1
          });
        }
      }else {
        const ids = row.id;
        this.$modal.confirm('是否确认删除现金流量代码编号为"' + row.cashFlowName + '"的数据项？').then(function () {
          return delCode(ids);
        }).then(() => {
          this.getList();
          this.initTree()
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      }


    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.checkedTCapitalDetail = selection.map(item => item.index)
      console.log(this.checkedTCapitalDetail);
      this.codeList = selection
    },
    /** 新增按钮操作 */
    handleAdd() {

      if (this.codeList.length == 0) {
        this.$modal.msgError("请至少勾选一笔数据");
      }
      if (this.form.parentId == null) {
        this.$modal.msgError("请点击树节点进行保存");
        return
      }
      for (let i = 0; i < this.codeList.length; i++) {
          this.codeList[i].parentId = this.form.parentId
          this.codeList[i].parentCode = this.form.parentCode
          this.codeList[i].parentName = this.form.parentName
          this.form.codeList = this.codeList

      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          addCode(this.form).then(response => {
            this.$modal.msgSuccess("保存成功");
            this.open = false;
            this.getList();
            this.initTree()
          });
        }
      });


    },
    /** 修改按钮操作 */
    handleUpdate(row) {

      updateCode(row).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.open = false;
        this.getList();
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCode(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCode(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除现金流量代码编号为"' + ids + '"的数据项？').then(function () {
        return delCode(ids);
      }).then(() => {
        this.getList();
        this.initTree()
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      /* this.download('aa/code/export', {
         ...this.queryParams
       }, `code_${new Date().getTime()}.xlsx`)*/
    }
  }
};
</script>
<style scoped>
.billDialog {
}

</style>

