<template>
  <div class="avue-crud">
    <el-form>
      <el-table v-if="visibleTab"
                height="60vh"
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
              <el-button v-hasPermi="['mill_doAttriAdd']"
                         size="mini"
                         plain
                         icon="el-icon-edit"
                         type="primary"
                         @click="handleEditBtn(scope.$index, scope.row)">编辑
              </el-button>
              <el-button v-hasPermi="['mill_doAttriDelete']"
                         size="mini"
                         plain
                         icon="el-icon-delete"
                         type="danger"
                         @click="handleDelete(scope.$index, scope.row)">删除
              </el-button>
            </div>
            <div v-if="addBtn">
              <el-button v-hasPermi="['mill_doAttriAdd']"
                         size="mini"
                         plain
                         icon="el-icon-edit"
                         type="primary"
                         @click="handleBtn(scope.$index, scope.row)">编辑
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
      <el-form ref="formDataSX"
               :model="formDataSX"
               :rules="rules"
               label-width="100px">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="属性名称："
                          prop="itemA">
              <div>{{formDataSX.itemA}}</div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="属性说明："
                          prop="label">
              <div>{{formDataSX.dataA}}</div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="属性值："
                          prop="attriValue">
              <el-input v-if="formDataSX.itemA!=='ERRMSG'"
                        v-model="formDataSX.attriValue"
                        style="width: 250px;"
                        :disabled="true"
                        placeholder="请选择成本中心">
                <i slot="suffix"
                   class="el-input__icon el-icon-plus"
                   @click="handleQueryPlus"
                   style="cursor:pointer;"></i>
              </el-input>
              <el-select v-else
                         class="activityId"
                         v-model="formDataSX.attriValue"
                         :popper-append-to-body="false"
                         placeholder="请选择成本中心">
                <el-option v-for="dict in optionsAttriValue"
                           :key="dict.dictValue"
                           :label="dict.dictLabel"
                           :value="dict.dictValue">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer"
           class="el-dialog__footer">
        <el-button @click="cancel('formDataSX')">取 消</el-button>
        <el-button type="primary"
                   @click="submitForm('formDataSX')">确 定</el-button>
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
                          prop="costCenter">
              <el-input v-model="queryParams.costCenter"
                        placeholder="请输入成本中心代码"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="9">
            <el-form-item label=""
                          prop="costCenterDesc">
              <el-input v-model="queryParams.costCenterDesc"
                        placeholder="请输入成本中心名称"></el-input>
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
        <el-table-column label="成本中心编码"
                         align="center"
                         prop="costCenter" />
        <el-table-column label="成本中心中文名称"
                         align="center"
                         prop="costCenterDesc" />
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
  queryInfoOne,
  deleteInfoAttri,
} from "@/api/finance/ip/productionLineData";
import { listFinancetest } from "@/api/finance/ac/financetest";

export default {
  name: "ProductionLineDataSX",
  props: {
    formDataSX: Object,
    type: String,
    millId: String,
    millName: String,
    millId2: String,
    millName2: String,
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
      visibleTab: true,
      // 弹出层标题
      title: "",
      titlePlus: "",
      addForm: {
      },
      rules: {
        attriValue: [{ required: true, message: '请输入属性值', trigger: 'blur' },],
      },
      clickIndex: '',
      pagePlus: {
        pageSize: 20,
        currentPage: 1,
        total: 0,
        layout: "total, sizes, prev, pager, next, jumper",
        fiscalYear: '',
        costCenter: null,
        costCenterDesc: null,
        ccType: null,
        compId: 'J00'
      },
      // 查询参数
      queryParams: {
        // 总条数
        total: 0,
        pageNum: 1,
        pageSize: 20,
        fiscalYear: '',
        costCenter: null,
        costCenterDesc: null,
        ccType: null,
        compId: 'J00'
      },
      columns: [
        { label: '属性名称', prop: "itemA", sortable: true, minWidth: '100px' },
        { label: '属性说明', prop: "dataA", sortable: true, minWidth: '100px' },
        { label: '属性值', prop: "attriValue", sortable: true, minWidth: '100px' },
      ],
      tableData: [
        { attriNam: '2016', '全焦产率': '11', '冶金焦产率': '23', '干熄焦产率': '41', '石膏产率': '43', '蒸汽产率': '55', },
      ],
      tableDataPlus: [
        { attriNam: '2016', '全焦产率': '11', '冶金焦产率': '23', '干熄焦产率': '41', '石膏产率': '43', '蒸汽产率': '55', },
      ],
      optionsAttriValue: [],
      dataTemp: {
        millId: '',
      },
      attriMillId: this.millId
    }
  },
  watch: {
    formDataSX: {
      immediate: true,
      handler (val) {
        this.addForm = val
      }
    }
  },
  created () {
    this.getDicts("cost_line_attri").then(response => {
      this.optionsAttriValue = response.data.data;
    })
    if (this.type == 'add') {
      this.addBtn = true
      this.getList();
    }
    if (this.type == 'edit' || this.type == 'info') {
      this.editBtn = true
      this.getList();
    }
    if (this.type == 'info') {
      this.showLabel = false
    }
    this.formDataSX.millName = this.millName
    this.formDataSX.millId = this.millId
    this.dataTemp.millId = this.millId2
  },
  computed: {},
  methods: {
    // 删除
    handleDelete (index, row) {
      const attriValue = row.attriValue;
      this.$confirm('此操作将删除该条记录, 是否继续?', '提示', {
        confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
      }).then(() => {
        //调用删除的方法
        deleteInfoAttri(this.millId, attriValue).then(response => {
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
    getList2 () {
      listFinancetest(this.queryParams).then(response => {
        if (response.data.data == null) {
          this.tableDataPlus = []
          this.pagePlus.total = 0;
          this.costAccount = true
        } else {
          this.tableDataPlus = response.data.data.list;
          this.pagePlus.total = response.data.data.total;
        }
      })
    },
    /** 默认显示格式"+"按钮操作 */
    handleQueryPlus () {
      this.query = {
        sampleClass: '',
        pageSize: 20,
        pageNum: 1,
      },
        this.openPlus = true;
      this.titlePlus = "成本中心";
      this.getList2();
    },
    // 取消按钮
    cancel (formName) {
      this.open = false;
      this.reset();
      this.$refs[formName].resetFields();
    },
    // 表单重置
    reset () {
      this.$set(this.formDataSX, 'attriValue', undefined);
    },
    cancelPlus () {
      this.openPlus = false;
      this.queryParams = {
        // 总条数
        total: 0,
        pageNum: 1,
        pageSize: 20,
        fiscalYear: '',
        costCenter: null,
        costCenterDesc: null,
        ccType: null,
        compId: 'J00'
      },
        this.templateSelection = ''
    },
    singleElection (row) {
      this.templateRadio = row.id;
      this.formDataSX.attriValue = row.costCenter
    },
    handleEditBtn (index, row) {
      this.clickIndex = index
      this.open = true;
      this.title = "属性";
      this.$set(this.formDataSX, 'itemA', row.itemA);
      this.$set(this.formDataSX, 'dataA', row.dataA);
      this.$set(this.formDataSX, 'attriValue', row.attriValue);
      this.$set(this.formDataSX, 'id', row.id);
    },
    /** 按钮操作 */
    handleBtn (index, row) {
      this.clickIndex = index
      this.formDataSX.millName = this.millName
      this.formDataSX.millId = this.millId
      if (this.formDataSX.millId === '' || typeof this.formDataSX.millId == 'undefined') {
        this.$message({
          type: 'error',
          message: '请填写产线代码'
        });
      } else {
        this.open = true;
        this.title = "属性";
        this.reset();
        this.$set(this.formDataSX, 'itemA', row.itemA);
        this.$set(this.formDataSX, 'dataA', row.dataA);
        this.$set(this.formDataSX, 'attriValue', row.attriValue);
        this.$set(this.formDataSX, 'id', row.id);
      }
    },
    /** 提交按钮 */
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.visibleTab = false;
          this.tableData[this.clickIndex].id = this.formDataSX.id
          this.tableData[this.clickIndex].attriValue = this.formDataSX.attriValue
          this.tableData[this.clickIndex].millId = this.millId
          this.tableData[this.clickIndex].attriName = this.formDataSX.itemA
          if (this.type == 'edit') {
            // this.tableData[this.clickIndex].updateUser = this.userInfo.userName
          }
          this.$nextTick(() => {
            this.visibleTab = true;
          })
          this.$emit('finalSX', this.tableData)
          this.open = false;
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
      let addAttriData = [
        { itemA: 'CC', dataA: '对应成本中心' },
        { itemA: 'ERRMSG', dataA: '待调整数据通知群组' },
        { itemA: 'SS', dataA: '销售成本中心' },
      ]
      if (this.type == 'info') {
        queryInfoOne(this.dataTemp).then(response => {
          this.tableData = response.data.data.attriData
        })
      } else if (this.type == 'edit') {
        queryInfoOne(this.dataTemp).then(response => {
          this.tableData = response.data.data.attriData
          this.tableData = [...this.tableData, ...addAttriData]
          for (let x = 0; x < this.tableData.length; x++) {
            for (let y = x + 1; y < this.tableData.length; y++) {
              if (this.tableData[x].itemA == this.tableData[y].itemA) {
                if (typeof (this.tableData[y].attriValue) == "undefined") {
                  this.tableData.splice(y, 1)
                }
              }
            }
          }
        })
      } else {
        this.tableData = addAttriData
      }
    },
  },
  mounted () {
    this.$emit('update:formDataSX', this.addForm)
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
.activityId /deep/ .el-input {
  width: 121.5%;
}
</style>
