<template>
  <el-dialog title="选择一级机构" :visible.sync="visible" width="1080px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" :inline="true">
      <el-form-item>
<!--        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
<!--        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
      </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="deptList" @selection-change="handleSelectionChange"
                height="360px">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="部门id" prop="deptId" :show-overflow-tooltip="true"/>
        <el-table-column label="部门编码" prop="deptCode" :show-overflow-tooltip="true"/>
        <el-table-column label="部门名称" prop="deptName" :show-overflow-tooltip="true"/>
        <el-table-column label="状态" align="center" prop="status">
          <template v-slot="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template v-slot="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
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
import { listDeptmaintenance} from "@/api/human/hp/deptMaintenance";

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
      deptList: [],
      // 部门树选项
      deptOptionsa: undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId:null,
        orgTierId:'01',
        status:'0',
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
      listDeptmaintenance(this.queryParams).then(res => {
        this.deptList = res.rows;
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
