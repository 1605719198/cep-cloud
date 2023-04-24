<template>
  <!-- 会计科目 -->
  <el-dialog title="添加科目" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="上级科目" >
        <el-input v-model="parentAcctCode" disabled style="width: 80px;margin-right: 10px"/>
        <el-input v-model="parentAcctName" disabled style="width: 130px"/>
      </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="userList" @selection-change="handleSelectionChange" height="260px" >
        <el-table-column type="selection" width="50" align="center"></el-table-column>
        <el-table-column label="会计科目" prop="groupAcctCode" :show-overflow-tooltip="true" align="center"/>
        <el-table-column label="会计科目中文名称" prop="groupAcctName" :show-overflow-tooltip="true" align="center"/>
        <el-table-column label="是否传票性科目" prop="isVoucher" :show-overflow-tooltip="true" align="center"/>
        <el-table-column label="现金科目" prop="isCash" :show-overflow-tooltip="true" align="center"/>
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
import { selectCodeGroupList, addBatchAcctcodeCorp }  from "@/api/finance/aa/acctcodeCorp";
export default {
  dicts: ['sys_normal_disable'],
  props: {
    // 上层会计科目编号
    parentAcctCode: {
      type: [Number, String]
    },
    // 上层会计科目名称
    parentAcctName: {
      type: [String]
    },
    companyId: {
      type: [Number, String]
    }
  },
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中数组值
      groupAcctIds: [],
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
      this.groupAcctIds = selection.map(item => item.groupAcctId);
      this.checkedList.splice(0,this.checkedList.length);
      selection.map((item) => this.checkedList.push(item));
      console.log("checkedList======>",this.checkedList,typeof this.checkedList)
    },

    // 查询表数据
    getList() {
      selectCodeGroupList(this.queryParams.acctCode || '0', this.companyId).then(res => {
        this.userList = res.rows;
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
      const groupAcctIds = this.groupAcctIds.join(",");
      if (groupAcctIds == "") {
        this.$modal.msgError("请选择要分配的用户");
        return;
      }
      let list = [];
      let item = {"groupAcctId":"group_acct_id","groupAcctCode":"group_acct_code"};
      list.push(item)
      let info = {"companyId":this.companyId,"checkedList":this.checkedList};
      console.log("JSON.stringify(info)====",JSON.stringify(info))
      console.log("info===",info,typeof info)
      addBatchAcctcodeCorp(info).then(res => {
        this.$modal.msgSuccess(res.msg);
        if (res.code === 200) {
          this.visible = false;
          this.$emit("ok");
          this.$parent.getCodeTree();
        }
      });
    }
  }
};
</script>
