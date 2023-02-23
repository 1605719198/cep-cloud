<template>
  <div class="avue-crud">
    <el-form>
      <el-table height="60vh"
                size="small"
                :data="tableData"
                stripe
                :default-sort="{prop: 'createDate', order: 'descending'}">
        <template v-for="column in columns">
          <el-table-column :key="column.prop"
                           :prop="column.prop"
                           :label="column.label"
                           :sortable="column.sortable"
                           :width="column.width"
                           :min-width="column.minWidth" />
        </template>
        <el-table-column fixed="right"
                         label="操作"
                         width="270px"
                         v-if="showLabel">
          <template slot-scope="scope">
            <div v-if="editBtn">
              <el-button v-hasPermi="['mill_doProductAdd']"
                         size="mini"
                         plain
                         icon="el-icon-plus"
                         type="primary"
                         @click="handleBtn(scope.$index, scope.row)">添加
              </el-button>
              <el-button v-hasPermi="['mill_doProductDelete']"
                         size="mini"
                         plain
                         icon="el-icon-delete"
                         type="danger"
                         @click="handleDelete(scope.$index, scope.row)">删除
              </el-button>
            </div>
            <div v-if="addBtn">
              <el-button v-hasPermi="['mill_doProductAdd']"
                         size="mini"
                         plain
                         icon="el-icon-plus"
                         type="primary"
                         @click="handleBtn(scope.$index, scope.row)">添加
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </el-form>

    <el-dialog :title="title"
               v-if="open"
               :visible.sync="open"
               width="500px"
               :close-on-click-modal="false"
               class="customDialogStyle"
               append-to-body>
      <el-form ref="formDataCC"
               :model="formDataCC"
               :rules="rules"
               label-width="120px">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="产线代码："
                          prop="millId">
              <div>{{formDataCC.millId}}</div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="产线名称："
                          prop="millName">
              <div>{{formDataCC.millName}}</div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="产出产副品："
                          prop="productCode">
              <el-input v-model="formDataCC.productCode"
                        style="width: 250px;"
                        :disabled="true"
                        placeholder="请选择产出产副品">
                <i slot="suffix"
                   class="el-input__icon el-icon-plus"
                   @click="handleQueryPlus"
                   style="cursor:pointer;"></i>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer"
           class="el-dialog__footer">
        <el-button @click="cancel('formDataCC')">取 消</el-button>
        <el-button type="primary"
                   @click="submitForm('formDataCC')">确 定</el-button>
      </div>
    </el-dialog>

    <!-- "+"对话框 -->
    <el-dialog :title="titlePlus"
               v-if="openPlus"
               :visible.sync="openPlus"
               width="600px"
               :close-on-click-modal="false"
               class="customDialogStyle"
               append-to-body>
      <el-form label-width="">
        <el-row>
          <el-col :span="9">
            <el-form-item label=""
                          prop="productCode">
              <el-input v-model="queryParams.productCode"
                        placeholder="请输入产副品编码"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="9">
            <el-form-item label=""
                          prop="productCodeDesc">
              <el-input v-model="queryParams.productCodeDesc"
                        placeholder="请输入产副品名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-button type="primary"
                       size="medium"
                       icon="el-icon-search"
                       @click="getList2"
                       style="margin-left: 40px;">搜索</el-button>
          </el-col>
        </el-row>
      </el-form>

      <el-table stripe
                height="55h"
                :data="tableDataPlus"
                tooltip-effect="dark"
                @row-click="singleElection"
                style="margin: 0 20px;width: auto;height: 400px">
        <el-table-column label=""
                         width="30">
          <template slot-scope="scope">
            <el-radio class="radio"
                      v-model="templateSelection"
                      :label="scope.$index">&nbsp;</el-radio>
          </template>
        </el-table-column>
        <el-table-column label="产副品编码"
                         align="center"
                         prop="productCode" />
        <el-table-column label="产副品名称"
                         align="center"
                         prop="productCodeDesc" />
      </el-table>
      <div style="margin-top: 10px;right: 0"
           class="avue-crud__pagination">
        <el-pagination background
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="pagePlus.currentPage"
                       :page-sizes="[20, 50, 100, 200]"
                       :page-size="pagePlus.pageSize"
                       :layout="pagePlus.layout"
                       :total="pagePlus.total">
        </el-pagination>
      </div>
      <div slot="footer"
           class="el-dialog__footer">
        <el-button @click="cancelPlus()">取 消</el-button>
        <el-button type="primary"
                   @click="submitFormPlus()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addInfoProduct,
  deleteInfoProduct,
  queryInfoProd
} from "@/api/finance/ip/productionLineData";
import { listFinancetest } from "@/api/finance/ip/financeproduct";

export default {
  name: "ProductionLineDataCC",
  props: {
    formDataCC: Object,
    type: String,
    millId: String,
    millName: String,
    millId2: String,
  },
  data () {
    return {
      showLabel: true,
      editBtn: false,
      addBtn: false,
      templateSelection: '',
      // 是否显示弹出层
      open: false,
      openPlus: false,
      // 弹出层标题
      title: "",
      titlePlus: "",
      addForm: {
      },
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        productCode: '',
        productCodeDesc: '',
        ccType: null,
        compId: 'J00'
      },
      rules: {
        productCode: [{ required: true, message: '请输入产出产副品', trigger: 'blur' },],
      },
      pagePlus: {
        pageSize: 20,
        currentPage: 1,
        total: 0,
        layout: "total, sizes, prev, pager, next, jumper",
      },
      columns: [
        { label: '产副品编码', prop: "productCode", sortable: true, minWidth: '300px' },
        { label: '产副品名称', prop: "productCodeDesc", sortable: true, minWidth: '300px' },
      ],
      tableData: [
        { sampleClass: '2016', '全焦产率': '11', },
      ],
      tableDataPlus: [
        { sampleClass: '2016', '全焦产率': '11', },
      ],
      dataTemp: {
        millId: '',
      }
    }
  },
  watch: {
    formDataCC: {
      immediate: true,
      handler (val) {
        this.addForm = val
      }
    }
  },
  created () {
    if (this.type == 'add') {
      this.addBtn = true
    }
    if (this.type == 'edit' || this.type == 'info') {
      this.editBtn = true
      this.getList();
    }
    if (this.type == 'info') {
      this.showLabel = false
    }
    this.formDataCC.millName = this.millName
    this.formDataCC.millId = this.millId
    this.dataTemp.millId = this.millId2
  },
  methods: {
    getList2 () {
      this.loading = false;
      listFinancetest(this.queryParams).then(response => {
        if (response.data.data == null) {
          this.tableDataPlus = []
          this.pagePlus.total = 0;
          this.costAccount = true
        } else {
          this.tableDataPlus = response.data.data.list;
          this.pagePlus.total = response.data.data.total;
          this.loading = false;
        }
      });

    },
    singleElection (row) {
      this.templateRadio = row.id;
      this.formDataCC.productCode = row.productCode
    },
    /** 按钮操作 */
    handleBtn () {
      this.formDataCC.millName = this.millName
      this.formDataCC.millId = this.millId
      if (this.formDataCC.millId === '' || typeof this.formDataCC.millId == 'undefined') {
        this.$message({
          type: 'error',
          message: '请填写产线代码'
        });
      } else {
        this.open = true;
        this.title = "产出产副品";
        this.$set(this.formDataCC, 'millName', this.millName);
        this.$set(this.formDataCC, 'millId', this.millId);
        this.reset();
      }
    },
    // 删除
    handleDelete (index, row) {
      const productCode = row.productCode;
      this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        //调用删除的方法
        deleteInfoProduct(this.millId, productCode).then(response => {
          this.getList();
          this.$message({
            type: 'success',
            message: response.data.msg
          });
        })
      }).catch(() => {
        this.$message({ type: 'info', message: '已取消删除' });
      });
    },
    /** 默认显示格式"+"按钮操作 */
    handleQueryPlus () {
      this.getList2()
      this.query = {
        sampleClass: '',
        pageSize: 20,
        pageNum: 1,
      },
        this.openPlus = true;
      this.titlePlus = "产副品选单";
    },
    // 取消按钮
    cancel (formName) {
      this.open = false;
      this.reset();
      this.$refs[formName].resetFields();
    },
    // 表单重置
    reset () {
      this.$set(this.formDataCC, 'productCode', undefined);
    },
    cancelPlus () {
      this.openPlus = false;
      this.queryParams = {
        pageNum: 1,
        pageSize: 20,
        productCode: '',
        productCodeDesc: '',
        ccType: null,
        compId: 'J00'
      };
      this.templateSelection = ''
    },
    /** 提交按钮 */
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          addInfoProduct(this.formDataCC).then(response => {
            this.open = false;
            this.getList()
            if (response.data.code == 0) {
              this.$message({
                type: 'success',
                message: response.data.msg
              });
            }
          });
        } else {
          return false;
        }
      });
    },
    submitFormPlus () {
      this.openPlus = false;
    },
    // 分页-每页多少条
    handleSizeChange (val) {
      this.queryParams.pageSize = val
      this.getList2()
    },
    // 分页-当前页
    handleCurrentChange (val) {
      this.queryParams.pageNum = val
      this.getList2()
    },
    //获取数据刷新页面
    getList () {
      if (this.type == 'edit' || this.type == 'info') {
        queryInfoProd(this.dataTemp).then(response => {
          this.tableData = response.data.data.productData
          if (this.tableData.length == 0) {
            if (this.type != 'info') {
              this.tableData = [
                { attriNam: '2016', '全焦产率': '11', },
              ]
            }
          }
        })
      } else {
        queryInfoProd(this.addForm).then(response => {
          this.tableData = response.data.data.productData
          if (this.tableData.length == 0) {
            if (this.type != 'info') {
              this.tableData = [
                { attriNam: '2016', '全焦产率': '11', },
              ]
            }
          }
        })
      }
    },
  },
  mounted () {
    this.$emit('update:formDataCC', this.addForm)
  }
}
</script>

<style scoped>
/*下拉框最后一个显示不完全*/
/deep/ .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
/deep/ .el-input {
  width: 190px;
}
/deep/ .is-in-pagination {
  width: 50px;
}
.el-dialog__footer {
  padding: 0px 20px 20px;
}
</style>
