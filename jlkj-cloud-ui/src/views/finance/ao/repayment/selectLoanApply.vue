<template>
  <!-- 会计科目 -->
  <el-dialog title="新增还款" :visible.sync="visible" width="1400px" top="5vh" append-to-body>
    <el-row>
      <el-table v-loading="loading" ref="table" :data="dataList" :row-class-name="rowDetailIndex" @selection-change="handleSelectionChange" height="618px" >
        <el-table-column type="selection" width="50" align="center"></el-table-column>
        <el-table-column label="借支单号" prop="billNo" align="center" width="120"/>
        <el-table-column label="借款人" align="center" width="180">
          <template v-slot="scope">
            {{scope.row.loanBy+'_'+scope.row.loanName}}
          </template>
        </el-table-column>
        <el-table-column label="支出类别" align="center" width="200">
          <template v-slot="scope">
            <span>{{ transToName(scope.row.itemNo, '_') }}</span>
          </template>
        </el-table-column>

        <el-table-column label="借支总额" prop="totalAmt"  align="center" width="150"/>
        <el-table-column label="已报销金额" prop="reimbAmt" align="center" width="150"/>
        <el-table-column label="未报销金额" prop="unreimbAmt" align="center" width="150"/>
        <el-table-column label="本次还款金额" prop="amt" width="150" align="center" :render-header="addRedstar">
          <template slot-scope="scope">
              <el-input v-model="scope.row.amt" type="number" :ref="'amt' + scope.$index"/>
          </template>
        </el-table-column>

        <el-table-column label="还款方式" prop="payType" width="150" align="center" :render-header="addRedstar">
          <template slot-scope="scope">
              <el-select v-model="scope.row.payType" placeholder="请选择"  :ref="'payType' + scope.$index">
                <el-option
                  v-for="dict in dict.type.ao_repayment_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
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
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSelectUser">确 定</el-button>
      <el-button  @click="reset" >取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { selectLoanApply,addRepaymentBatch }  from "@/api/finance/ao/repayment";
import { getItemNoList } from "@/api/finance/ao/reimbItemComp";
export default {
  dicts: ['sys_normal_disable','ao_repayment_type'],
  data() {
    return {
      // 支付类别下拉选单
      itemNoList: [],
      // 遮罩层
      visible: false,

      // 遮罩层
      loading: true,
      // 选中数组值
      billNos: [],
      // 选中的会计科目
      checkedList: [],
      // 总条数
      total: 0,
      // 未授权用户数据
      dataList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: ''
      }
    };
  },
  methods: {
    // 显示弹框
    show(companyId) {
      this.queryParams.companyId = companyId
      this.getItemNoList()
      this.getList();
      this.visible = true;
    },

    getItemNoList() {
      getItemNoList(this.queryParams.companyId).then(response => {
        this.itemNoList = response;
      });
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.billNos = selection.map(item => item.billNo);
      this.checkedList.splice(0,this.checkedList.length);
      selection.map((item) => this.checkedList.push(item));
    },

    // 查询表数据
    getList() {
      this.loading = true;
      selectLoanApply(this.queryParams).then(res => {
        this.dataList = res.rows;
        this.total = res.total;
        this.loading = false;
      });
    },
    reset() {
      this.visible = false;
      this.clearRed();
    },
    /**红色边框重置*/
    clearRed(){
      for(let i=0; i<this.dataList.length; i++){
        if(this.$refs['amt'+i].$el.firstElementChild.style.borderColor ==='red'){
          this.$refs['amt'+i].$el.firstElementChild.style.borderColor = '#DCDFE6';
        }
        if(this.$refs['payType'+i].$el.firstElementChild.firstElementChild.style.borderColor === 'red'){
          this.$refs['payType'+i].$el.firstElementChild.firstElementChild.style.borderColor = '#DCDFE6';
        }
      }
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
    /** 选择授权用户操作 */
    handleSelectUser() {
      const billNos = this.billNos.join(",");
      if (billNos == "") {
        this.$modal.msgError("请至少勾选一笔数据");
        return;
      }
      // 栏位验证
      let msg = this.validate();
      if(msg != ""){
        this.$modal.msgError(msg);
        return;
      }
      addRepaymentBatch(this.checkedList).then(res => {
        this.$modal.msgSuccess(res.msg);
        if (res.code === 200) {
          this.visible = false;
          this.$emit("ok");
        }
      });
    },
    validate(){
      this.clearRed();
      let rtn = true;
      // 1. 必填项检查
      let emptyAmts=[];
      let emptyPayTypes=[];
      //2. 数字格式检查
      let formats=[];
      // 3. 数值范围检车
      let outstrips=[];
      const pattern = /^[0-9]+(\.[0-9]{1,2})?$/
      for(let i=0;i<this.checkedList.length;i++){
        let idx = this.checkedList[i].index - 1;
        if(!this.checkedList[i].amt){
          emptyAmts.push(idx);
        }
        if(!this.checkedList[i].payType){
          emptyPayTypes.push(idx);
        }
        if (!pattern.test(this.checkedList[i].amt)) {
          formats.push(idx);
        }
        if(this.checkedList[i].amt > this.checkedList[i].unreimbAmt){
          outstrips.push(idx);
        }
      }
      emptyAmts.forEach(idx => this.$refs['amt'+idx].$el.firstElementChild.style.borderColor = 'red')
      emptyPayTypes.forEach(idx => this.$refs['payType'+idx].$el.firstElementChild.firstElementChild.style.borderColor = 'red')
      if(emptyAmts.length>0 || emptyPayTypes.length>0){
        return "栏位不可为空";
      }
      if(formats.length>0){
        formats.forEach(idx => this.$refs['amt'+idx].$el.firstElementChild.style.borderColor = 'red')
        return "请输入≥0并且小数点≤2位的数据";
      }
      if(outstrips.length>0){
        outstrips.forEach(idx => this.$refs['amt'+idx].$el.firstElementChild.style.borderColor = 'red')
        return "本次还款金额不能大于未报销金额";
      }
      return "";
    },
    /** 明细档序号 */
    rowDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 表头标记红星*/
    addRedstar(h, {column }){
      return[
        h("span",""+ column.label),
        h("span", {style: "color: red"},"*"),
      ];
    },
    /** 支出类别转中文 */
    transToName(value, delimiter) {
      if(value == '') return '';
      if(delimiter!=''){
        return value + delimiter + this.itemNoList.find(item => item.value == value)?.label;
      }
      return this.itemNoList.find(item => item.value == value)?.label;
    },
  }
};
</script>
