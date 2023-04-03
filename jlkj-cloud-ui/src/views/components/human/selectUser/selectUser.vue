<template>
  <!-- 授权用户 -->
  <el-dialog title="选择用户" :visible.sync="visible" width="1080px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" :inline="true">
          <el-form-item label="用户工号" prop="empNo">
            <el-input
              v-model="queryParams.empNo"
              placeholder="请输入用户工号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="手机号码" prop="myMobilePhone">
            <el-input
              v-model="queryParams.myMobilePhone"
              placeholder="请输入手机号码"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="归属部门" prop="deptId">
            <treeselect v-model="queryParams.deptId"
                        class="treeselect-main"
                        :options="deptOptions"
                        :show-count="true"
                        placeholder="请选择部门"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="userList" @selection-change="handleSelectionChange"
                height="360px">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="用户工号" prop="empNo" :show-overflow-tooltip="true"/>
        <el-table-column label="用户姓名" prop="fullName" :show-overflow-tooltip="true"/>
        <el-table-column label="部门" prop="departmentName" :show-overflow-tooltip="true"/>
        <el-table-column label="邮箱" prop="officeEmail" :show-overflow-tooltip="true"/>
        <el-table-column label="手机" prop="myMobilePhone" :show-overflow-tooltip="true"/>
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
import {getAllUserList} from "@/api/human/hm/personnelBasicInfo";
import {deptTreeSelect} from "@/api/system/user";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  components: {Treeselect},
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中数组值
      userIds: [],
      // 选中数组名称
      userName: [],
      // 选中公司别数组名称
      compId: [],
      // 总条数
      total: 0,
      // 未授权用户数据
      userList: [],
      // 部门树选项
      deptOptions: undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empNo: undefined,
        myMobilePhone: undefined,
        deptId: undefined,
        compId: undefined
      }
    };
  },
  created() {
    this.getDeptTree();
  },
  methods: {
    // 显示弹框
    show(queryParams) {
      if(queryParams){
        this.queryParams = queryParams;
      }
      this.getList();
      this.visible = true;
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.userIds = selection.map(item => item.empNo);
      this.userName = selection.map(item => item.fullName);
    },
    // 查询表数据
    getList() {
      getAllUserList(this.queryParams).then(res => {
        this.userList = res.data.rows;
        this.total = res.data.total;
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
      const userIds = this.userIds.join(",");
      const userName = this.userName.join(",");
      const compId = this.compId.join(",");
      if (userIds == "") {
        this.$modal.msgError("请选择要分配的用户");
        return;
      }
      if (this.userIds.length > 1) {
        this.$modal.msgError("只能选择一笔数据");
        return;
      }
      this.visible = false;
      this.$emit("ok", userIds, userName, compId);
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
  }
};
</script>
<style scoped>
.treeselect-main {
  width: 200px;
}
</style>
