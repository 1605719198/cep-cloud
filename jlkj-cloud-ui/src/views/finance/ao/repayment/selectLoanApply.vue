<template>
  <!-- 会计科目 -->
  <el-dialog title="新增还款" :visible.sync="visible" width="1000px" top="5vh" append-to-body>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="userList" :row-class-name="rowDetailIndex" @selection-change="handleSelectionChange" height="500px" >
        <el-table-column type="selection" width="50" align="center"></el-table-column>
        <el-table-column label="借支单号" prop="billNo" align="center"/>
        <el-table-column label="借支总额" prop="totalAmt"   width="150"/>
        <el-table-column label="已报销金额" prop="reimbAmt" width="150"/>
        <el-table-column label="未报销金额" prop="unreimbAmt" width="150"/>
        <el-table-column label="本次还款金额" prop="amt" width="150">
          <template slot-scope="scope">
              <el-input v-model="scope.row.amt" type="number"  :ref="'amt' + scope.$index"/>
          </template>
        </el-table-column>

        <el-table-column label="还款方式" prop="payType" width="150" >
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
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { selectLoanApply,addRepaymentBatch }  from "@/api/finance/ao/repayment";
export default {
  dicts: ['sys_normal_disable','ao_repayment_type'],
  props: {
    companyId: {
      type: [Number, String]
    }
  },
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中数组值
      billNos: [],
      // 选中的会计科目
      checkedList: [],
      // 总条数
      total: 0,
      // 未授权用户数据
      userList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        acctCode: null,
        roleId: undefined,
        userName: undefined,
        phonenumber: undefined
      }
    };
  },
  methods: {
    // 显示弹框
    show() {
      this.queryParams.acctCode = this.parentAcctCode;
      this.getList();
      this.visible = true;
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.billNos = selection.map(item => item.billNo);
      this.checkedList.splice(0,this.checkedList.length);
      selection.map((item) => this.checkedList.push(item));
      console.log("checkedList======>",this.checkedList,typeof this.checkedList)
    },

    // 查询表数据
    getList() {
      selectLoanApply(this.companyId).then(res => {
        this.userList = res.rows;
        console.log("userList====>",this.userList)
        this.total = res.total;
      });
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
      const roleId = this.queryParams.roleId;
      const billNos = this.billNos.join(",");
      if (billNos == "") {
        this.$modal.msgError("请选择要分配的用户");
        return;
      }
      console.log("ref===>",this.$refs)
      // for(let i=0;i<this.checkedList.length;i++){
      //   console.log("ref===>",this.$refs['amt'+this.checkedList[i].index])
      // }
      return;
      addRepaymentBatch(this.checkedList).then(res => {
        this.$modal.msgSuccess(res.msg);
        if (res.code === 200) {
          this.visible = false;
          this.$emit("ok");
        }
      });
    },
    /** 明细档序号 */
    rowDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
  }
};
</script>
