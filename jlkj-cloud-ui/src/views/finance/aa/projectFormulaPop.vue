<template>
  <!-- 授权用户 -->
  <el-dialog title="印表项目" :visible.sync="visible" width="1080px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" :inline="true">
          <el-form-item label="项目代码" prop="itemCode">
            <el-input
              v-model="queryParams.itemCode"
              placeholder="项目代码"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="项目名称" prop="itemName">
            <el-input
              v-model="queryParams.itemName"
              placeholder="项目名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="baseList" @selection-change="handleSelectionChange"
                height="360px">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="项目代码" prop="itemCode" :show-overflow-tooltip="true"/>
        <el-table-column label="项目名称" prop="itemName" :show-overflow-tooltip="true"/>
        <el-table-column label="状态" align="center" prop="drOrCr" show-overflow-tooltip>
          <template v-slot="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
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
import {listProjectFormula} from "@/api/finance/aa/projectFormula";

export default {
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 判断选择单笔还是多笔
      isSingle: true,
      // 遮罩层
      visible: false,
      // 选中数组值
      projectIds: [],
      // 总条数
      total: 0,
      // 未授权用户数据
      userList: [],

      // 弹窗数据
      baseList: [],

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleId: 1,
        itemCode: null,
        itemName: null,
      }
    };
  },
  created() {
    //this.getDeptTree();
  },
  methods: {
    // 显示弹框
    show(val,itemCode) {
      this.queryParams.itemCode = itemCode;
      this.getList();
      this.visible = true;
      this.isSingle = val
    },

    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.projectIds = selection;
    },

    // 查询表数据
    getList() {
      this.loading = true;
      listProjectFormula(this.queryParams).then(response => {
        this.baseList = response.rows;
        this.total = response.total;
        this.loading = false;
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
    /** 选择会计科目 */
    handleSelectUser() {
      if (this.projectIds.length === 0) {
        this.$modal.msgError("请选择要分配的印表项目代码");
        return;
      }
      if (this.projectIds.length > 1 && this.isSingle === true) {
        this.$modal.msgError("只能选择一笔数据");
        return;
      }
      this.visible = false;
      this.$emit("ok",this.projectIds);
    }
  }
};
</script>
<style scoped>
</style>
