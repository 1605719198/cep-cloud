<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="公司" prop="compId">
            <el-select v-model="queryParams.compId" placeholder="请选择公司">
              <el-option
                v-for="dict in companyName"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['human:payTable:add']"
            >添加薪资表项目</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['human:payTable:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-circle-check"
              size="mini"
              @click="handleSave"
              v-hasPermi="['human:payTable:add']"
            >保存</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="form.payTableList" @selection-change="handleSelectionChange1">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="薪酬表项目编码" align="center" prop="payTabCode" />
          <el-table-column label="薪酬表项目名称" align="center" prop="payTabName" />
          <el-table-column label="薪资类别" align="center" prop="payTypeId">
            <template v-slot="scope">
              <el-select v-model="scope.row.payTypeId" placeholder="请选择">
                <el-option
                  v-for="dict in payTableData.payType"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="数据范围" align="center" prop="dataAreaId">
            <template v-slot="scope">
              <el-select v-model="scope.row.dataAreaId" placeholder="请选择">
                <el-option
                  v-for="dict in payTableData.dataRange"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="计算顺序" align="center" prop="computeNo">
            <template v-slot="scope">
              <el-input v-model="scope.row.computeNo" placeholder="请输入"/>
            </template>
          </el-table-column>
          <el-table-column label="是否抛送财务" align="center" prop="isThrowFinance">
            <template v-slot="scope">
              <el-select v-model="scope.row.isThrowFinance" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="是否统计项目" align="center" prop="isReport">
            <template v-slot="scope">
              <el-select v-model="scope.row.isReport" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="报表中显示顺序" align="center" prop="reportLocation">
            <template v-slot="scope">
              <el-input v-model="scope.row.reportLocation" placeholder="请输入"/>
            </template>
          </el-table-column>
          <el-table-column label="是否工资条项目" align="center" prop="isSheetPro">
            <template v-slot="scope">
              <el-select v-model="scope.row.isSheetPro" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="工资条显示位置" align="center" prop="sheetLocationId">
            <template v-slot="scope">
              <el-select v-model="scope.row.sheetLocationId" placeholder="请选择">
                <el-option
                  v-for="dict in payTableData.sheetLocation"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="是否二级团队绩效" align="center" prop="isTeamPerformance">
            <template v-slot="scope">
              <el-select v-model="scope.row.isTeamPerformance" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="计算公式" align="center">
            <template v-slot="scope">
              <el-button icon="el-icon-search" @click="openPayTablePop(scope.$index,scope.row)"></el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />

        <!-- 添加薪资表项目对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
          <el-row :gutter="20">
            <el-col :span="6">
              <div class="head-container treeDept">
                <el-scrollbar class="treeScrollbar">
                  <el-tree
                    :data="payTableOptions"
                    :props="defaultProps"
                    :default-expand-all="false"
                    :highlight-current="true"
                    :expand-on-click-node="false"
                    :default-expanded-keys="expandedKeys"
                    node-key="id"
                    ref="tree"
                    @node-click="handleNodeClick"
                  />
                </el-scrollbar>
              </div>
            </el-col>
            <el-col :span="18">
              <div style="margin-bottom: 10px">
                <el-button type="primary" @click="handleSelect">确 定</el-button>
                <el-button type="primary" @click="cancel">取 消</el-button>
              </div>
              <el-table @row-click="clickRow" v-loading="loading" ref="table" :data="tableData" @selection-change="handleSelectionChange"
                        height="360px">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column label="薪酬项目编码" prop="payProCode"/>
                <el-table-column label="薪酬项目名称" prop="payProName"/>
                <el-table-column label="说明" prop="description"/>
              </el-table>
              <pagination
                v-show="total1>0"
                :total="total1"
                :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize"
                @pagination="getList"
              />
            </el-col>
          </el-row>
        </el-dialog>

        <!-- 计算公式对话框 -->
        <el-dialog :title="title1" :visible.sync="open1" width="1000px" append-to-body>
          <el-row :gutter="20">
            <el-col>
              <div style="margin-bottom: 10px">
                <el-button type="primary" @click="handleSaveFormula">保存</el-button>
              </div>
              <el-form :model="calculationParams" :inline="true" ref="conditionForm">
                <el-form-item label="参数" prop="params">
                  <el-select v-model="calculationParams.params" placeholder="请选择">
                    <el-option
                      v-for="dict in payTableData.SalaryFormulaParamType"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                      @click.native="handleChange(dict.dicNo)"
                    ></el-option>
                  </el-select>
                  <el-select v-model="calculationParams.paramsA" placeholder="请选择">
                    <el-option
                      v-for="dict in paramsDetail"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                      @click.native="handleChange1(dict.dicNo)"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="逻辑运算符" prop="logicalOperator">
                  <el-select v-model="calculationParams.logicalOperator" placeholder="请选择">
                    <el-option
                      v-for="dict in payTableData.LogicalOperator"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                      @click.native="handleChange2(dict.dicNo)"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="算数运算符" prop="arithmeticOperator">
                  <el-select v-model="calculationParams.arithmeticOperator" placeholder="请选择">
                    <el-option
                      v-for="dict in payTableData.ArithmeticOperator"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                      @click.native="handleChange3(dict.dicNo)"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="数字" prop="digit">
                  <el-select v-model="calculationParams.digit" placeholder="请选择">
                    <el-option
                      v-for="dict in payTableData.Digit"
                      :key="dict.dicNo"
                      :label="dict.dicName"
                      :value="dict.dicNo"
                      @click.native="handleChange4(dict.dicNo)"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-form>
              <el-table v-loading="loading" ref="table" :data="formula.tableData1" :row-class-name="tableRowClassName" :cell-class-name="tableCellClassName" @cell-click="cellClick" @row-click="addLine" @selection-change="handleSelectionChange2" border
                        height="360px">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column label="条件" prop="conditions"/>
                <el-table-column label="公式" prop="formula"/>
                <el-table-column label="计算顺序" prop="seqNo"/>
              </el-table>
            </el-col>
          </el-row>
        </el-dialog>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {listPayTable, delPayTable, addPayTable, addPayTableFormula, listPayTableFormula} from "@/api/human/hs/payTable";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {listProjectPay, listProjectPayTree} from "@/api/human/hs/projectPay";
import {getSalaryDeepOptions, getSalaryOptions} from "@/api/human/hs/salaryBasis";
import '@/assets/styles/humanStyles.scss';

export default {
  name: "PayTable",
  dicts: ['sys_yes_no'],
  data() {
    return {
      // 遮罩层
      loading: false,
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
      total1: 0,
      // 弹出层标题
      title: "",
      // 公式层标题
      title1: "",
      // 是否显示弹出层
      open: false,
      // 是否显示计算公式弹窗
      open1: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: undefined,
        id: undefined,
        payTabId: undefined
      },
      calculationParams: {

      },
      // 表单参数
      form: {
        // 公司级薪资项目维护表格数据
        payTableList: [],
      },
      // 表单校验
      rules: {
      },
      // 公司别下拉选单
      companyName: [],
      payTableOptions: [],
      //el tree默认值
      defaultProps: {
        children: "children",
        label: "payProName",
      },
      //默认展开指定节点
      expandedKeys: [],
      //添加薪酬项目弹窗表格数据
      tableData: [],
      //计算公式弹窗表格数据
      formula: {
        tableData1: [
          {
            conditions: undefined,
            formula: undefined,
            seqNo: undefined
          }
        ]
      },
      //选中薪酬项目编码
      payProCode: undefined,
      //选中薪酬项目名称
      payProName: undefined,
      //选中薪酬项目ID
      id: undefined,
      //选中计算公式ID
      uuid: undefined,
      //薪资选单类型查询
      salaryOptionType: {
        id: '19',
        optionsType: [
          'dataRange',
          'payType',
          'sheetLocation',
          'SalaryFormulaParamType',
          'LogicalOperator',
          'ArithmeticOperator',
          'Digit'],
        compId:null,
      },
      payTableData: [],
      index: 0,
      paramsDetail: [],
      rowIndex: -1, //行索引
      columnIndex: -1, //列索引
      //登录人信息
      user: {},
    };
  },
  created() {
    this.initData();
    selectCompany().then(res => {
      this.companyName = res.data
    })
    getSalaryOptions(this.salaryOptionType).then(response =>{
      this.payTableData = response.data
    })
  },
  methods: {
    //初始化数据
    initData(){
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId
    },
    /** 查询公司级薪资项目维护列表 */
    getList() {
      this.loading = true;
      listPayTable(this.queryParams).then(response => {
        this.form.payTableList = response.data.rows;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.id = selection.map(item => item.id)
      this.payProCode = selection.map(item => item.payProCode)
      this.payProName = selection.map(item => item.payProName)
    },
    // 多选框选中数据
    handleSelectionChange2(selection) {
      this.uuid = selection.map(item => item.uuid)
    },
    // 多选框选中数据
    handleSelectionChange1(selection) {
      this.ids = selection.map(item => item.uuid)
      this.multiple = !selection.length
    },
    handleSelect() {
      const form = {
        payTabCodeId: this.id[0],
        payTabCode: this.payProCode[0],
        payTabName: this.payProName[0]
      }
      if (this.id == "") {
        this.$modal.msgError("请选择要分配的用户");
        return;
      }
      if (this.id.length > 1) {
        this.$modal.msgError("只能选择一笔数据");
        return;
      }
      this.open = false
      this.form.payTableList.push(form)
      this.total = this.form.payTableList.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      if (this.queryParams.compId === undefined) {
        this.$modal.msgError("请先选择公司别进行搜索！");
      } else {
        listProjectPayTree().then(response => {
          this.payTableOptions = this.handleTree(response, 'id', 'parentid', 'children')
          this.expandedKeys.push(this.payTableOptions[0].id)
          this.queryParams.id = this.payTableOptions[0].id
          this.onLoad();
        });
        this.open = true;
        this.title = "添加薪资表项目";
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uuids = row.uuid || this.ids;
      this.$modal.confirm('是否确认删除公司级薪资项目维护编号为"' + uuids + '"的数据项？').then(function() {
        return delPayTable(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    //点击节点方法
    handleNodeClick(data) {
      this.queryParams.id = data.id
      this.tableData = []
      this.onLoad()
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    //载入数据
    onLoad() {
      this.loading = true;//加载状态
      listProjectPay(this.queryParams).then(response => {
        this.total1 = response.total
        this.tableData = response.rows//表格数据
        this.loading = false
      })
    },
    handleSave() {
      this.form.payTableList.map(value => {
        value.compId = this.queryParams.compId
      })
      addPayTable(this.form).then(res => {
        this.$modal.msgSuccess("新增成功");
        this.getList();
      })
    },
    openPayTablePop(val,row) {
      this.resetForm("conditionForm");
      this.loading = true;
      this.queryParams.payTabId = row.uuid
      listPayTableFormula(this.queryParams).then(response => {
        this.formula.tableData1 = response.data.rows;
        if (this.formula.tableData1.length === 0) {
          this.formula.tableData1 = [
              {
                conditions: undefined,
                formula: undefined,
                seqNo: undefined
              }
            ]
        }
        this.loading = false;
      });
      this.index = val
      this.open1 = true
      this.title1 = "薪酬表项目名称：" + this.form.payTableList[val].payTabName;
    },
    handleChange(value) {
      const selectedItem = this.payTableData.SalaryFormulaParamType.find((item) => {
        return item.dicNo === value
      })
      this.salaryOptionType.id = selectedItem.id
      this.paramsDetail = []
      getSalaryDeepOptions(this.salaryOptionType).then(response => {
        this.paramsDetail = response.data
      });
    },
    handleChange1(value) {
      const selectedItem = this.paramsDetail.find((item) => {
        return item.dicNo === value
      })
      if (this.columnIndex === 1 ) {
        if (this.formula.tableData1[this.rowIndex].conditions === undefined) {
          this.formula.tableData1[this.rowIndex].conditions = selectedItem.dicName
        } else {
          this.formula.tableData1[this.rowIndex].conditions = this.formula.tableData1[this.rowIndex].conditions + selectedItem.dicName
        }
      } else if (this.columnIndex === 2) {
        if (this.formula.tableData1[this.rowIndex].formula === undefined) {
          this.formula.tableData1[this.rowIndex].formula = selectedItem.dicName
        } else {
          this.formula.tableData1[this.rowIndex].formula = this.formula.tableData1[this.rowIndex].formula + selectedItem.dicName
        }
      } else if (this.columnIndex === 3){
        if (this.formula.tableData1[this.rowIndex].seqNo === undefined) {
          this.formula.tableData1[this.rowIndex].seqNo = selectedItem.dicName
        } else {
          this.formula.tableData1[this.rowIndex].seqNo = this.formula.tableData1[this.rowIndex].seqNo + selectedItem.dicName
        }
      }
    },
    handleChange2(value) {
      const selectedItem = this.payTableData.LogicalOperator.find((item) => {
        return item.dicNo === value
      })
      if (this.columnIndex === 1 ) {
        if (this.formula.tableData1[this.rowIndex].conditions === undefined) {
          this.formula.tableData1[this.rowIndex].conditions = selectedItem.dicName
        } else {
          this.formula.tableData1[this.rowIndex].conditions = this.formula.tableData1[this.rowIndex].conditions + selectedItem.dicName
        }
      } else if (this.columnIndex === 2) {
        if (this.formula.tableData1[this.rowIndex].formula === undefined) {
          this.formula.tableData1[this.rowIndex].formula = selectedItem.dicName
        } else {
          this.formula.tableData1[this.rowIndex].formula = this.formula.tableData1[this.rowIndex].formula + selectedItem.dicName
        }
      } else if (this.columnIndex === 3){
        if (this.formula.tableData1[this.rowIndex].seqNo === undefined) {
          this.formula.tableData1[this.rowIndex].seqNo = selectedItem.dicName
        } else {
          this.formula.tableData1[this.rowIndex].seqNo = this.formula.tableData1[this.rowIndex].seqNo + selectedItem.dicName
        }
      }
    },
    handleChange3(value) {
      const selectedItem = this.payTableData.ArithmeticOperator.find((item) => {
        return item.dicNo === value
      })
      if (this.columnIndex === 1 ) {
        if (this.formula.tableData1[this.rowIndex].conditions === undefined) {
          this.formula.tableData1[this.rowIndex].conditions = selectedItem.dicName
        } else {
          this.formula.tableData1[this.rowIndex].conditions = this.formula.tableData1[this.rowIndex].conditions + selectedItem.dicName
        }
      } else if (this.columnIndex === 2) {
        if (this.formula.tableData1[this.rowIndex].formula === undefined) {
          this.formula.tableData1[this.rowIndex].formula = selectedItem.dicName
        } else {
          this.formula.tableData1[this.rowIndex].formula = this.formula.tableData1[this.rowIndex].formula + selectedItem.dicName
        }
      } else if (this.columnIndex === 3){
        if (this.formula.tableData1[this.rowIndex].seqNo === undefined) {
          this.formula.tableData1[this.rowIndex].seqNo = selectedItem.dicName
        } else {
          this.formula.tableData1[this.rowIndex].seqNo = this.formula.tableData1[this.rowIndex].seqNo + selectedItem.dicName
        }
      }
    },
    handleChange4(value) {
      const selectedItem = this.payTableData.Digit.find((item) => {
        return item.dicNo === value
      })
      if (this.columnIndex === 1 ) {
        if (this.formula.tableData1[this.rowIndex].conditions === undefined) {
          this.formula.tableData1[this.rowIndex].conditions = selectedItem.dicName
        } else {
          this.formula.tableData1[this.rowIndex].conditions = this.formula.tableData1[this.rowIndex].conditions + selectedItem.dicName
        }
      } else if (this.columnIndex === 2) {
        if (this.formula.tableData1[this.rowIndex].formula === undefined) {
          this.formula.tableData1[this.rowIndex].formula = selectedItem.dicName
        } else {
          this.formula.tableData1[this.rowIndex].formula = this.formula.tableData1[this.rowIndex].formula + selectedItem.dicName
        }
      } else if (this.columnIndex === 3){
        if (this.formula.tableData1[this.rowIndex].seqNo === undefined) {
          this.formula.tableData1[this.rowIndex].seqNo = selectedItem.dicName
        } else {
          this.formula.tableData1[this.rowIndex].seqNo = this.formula.tableData1[this.rowIndex].seqNo + selectedItem.dicName
        }
      }
    },
    tableRowClassName({row, rowIndex}) {
      // 把每一行的索引放到row里
      row.index = rowIndex
    },
    tableCellClassName({ column, columnIndex }) {
      // 把每一列的索引放到column里
      column.index = columnIndex;
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      if (this.formula.tableData1.length === row.index + 1) {
        const newLine = {
          conditions: undefined,
          formula: undefined,
          seqNo: undefined,
        }
        this.index++
        this.formula.tableData1.push(newLine)
      }
    },
    cellClick(row, column) {
      this.rowIndex = row.index;
      this.columnIndex = column.index;
    },
    handleSaveFormula() {
      this.formula.tableData1.map(value => {
        value.compId = this.queryParams.compId
        value.payTabId = this.queryParams.payTabId
      })
      addPayTableFormula(this.formula).then(res => {
        this.$modal.msgSuccess("新增成功");
      })
    }
  }
};
</script>
<style scoped>
.treeDept {
  height: 40vh;
  width: 100%;
}
.treeScrollbar {
  height: 100%;
}
</style>
