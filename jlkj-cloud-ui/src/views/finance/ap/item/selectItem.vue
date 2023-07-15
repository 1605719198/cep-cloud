<template>
  <!-- 集团报支类别 -->
  <el-dialog title="选取集团报支类别" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="userList" @selection-change="handleSelectionChange" height="260px" >
        <el-table-column type="selection" width="50" align="center"></el-table-column>
        <el-table-column label="报支类别编码" prop="itemNo" :show-overflow-tooltip="true" align="center"/>
        <el-table-column label="报支类别名称" prop="itemName" :show-overflow-tooltip="true" align="center"/>
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
import { listItemAndDetail } from "@/api/finance/ap/item";
import {addBatchData} from "@/api/finance/ap/itemComp";

export default {
  props: ['companyId'],
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中数组值
      itemNos: [],
      // 选中的报支类别
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
      this.getList();
      this.visible = true;
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.itemNos = selection.map(item => item.itemNo);
      this.checkedList.splice(0,this.checkedList.length);
      selection.map((item) => this.checkedList.push(item));
    },

    // 查询表数据
    getList() {
      listItemAndDetail(this.companyId).then(res => {
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
      const itemNos = this.itemNos.join(",");
      if (itemNos == "") {
        this.$modal.msgError("请选择要分配的用户");
        return;
      }
      this.checkedList.forEach(value => value.companyId = this.companyId);
      addBatchData(this.checkedList).then(res => {
        this.$modal.msgSuccess(res.msg);
        if (res.code === 200) {
          this.visible = false;
          this.$emit("ok");
        }
      });
    }
  }
};
</script>
