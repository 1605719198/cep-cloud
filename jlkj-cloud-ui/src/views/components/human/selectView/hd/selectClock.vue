<template>
  <el-dialog title="选择公司卡钟" :visible.sync="visible" width="1280px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item>
        <!--        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
        <!--        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
      </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="clockList" @selection-change="handleSelectionChange"
                height="360px">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="刷卡钟编码" align="center" prop="code" width="120" sortable/>
        <el-table-column label="刷卡钟名称" align="center" prop="name" width="180" sortable/>
        <el-table-column label="一级机构" align="center" prop="firstDeptName" show-overflow-tooltip width="300" sortable/>
        <el-table-column label="备注" align="center" prop="note" show-overflow-tooltip sortable/>
        <el-table-column label="输入人" align="center" prop="creator" width="120" sortable/>
        <el-table-column label="输入日期" align="center" prop="createDate" width="180" sortable>
          <template v-slot="scope">
            <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
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
import { listClockwork } from "@/api/human/hd/clockwork";

export default {
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 判断选择单笔还是多笔
      isSingle: true,
      // 遮罩层
      visible: false,
      // 选中数组值
      userIds: [],
      // 总条数
      total: 0,
      // 一级部门
      clockList: [],
      // 部门树选项
      deptOptionsa: undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId:null,
      }
    };
  },
  created() {
  },
  methods: {
    // 显示弹框
    show(val) {
      this.queryParams.compId=val;
      this.getList();
      this.visible = true;
      this.isSingle = false;

    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.userIds = selection;
    },
    // 查询表数据
    getList() {
      listClockwork(this.queryParams).then(res => {
        this.clockList = res.rows;
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
    /** 选择卡钟操作 */
    handleSelectUser() {
      if (this.userIds.length === 0) {
        this.$modal.msgError("请选择要分配的用户");
        return;
      }
      if (this.userIds.length > 1 && this.isSingle === true) {
        this.$modal.msgError("只能选择一笔数据");
        return;
      }
      this.visible = false;
      this.$emit("ok",this.userIds);
    }
  }
};
</script>
<style scoped>
</style>
