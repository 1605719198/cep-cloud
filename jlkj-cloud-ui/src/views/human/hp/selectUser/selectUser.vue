<template>
  <!-- 授权用户 -->
  <el-dialog title="选择用户" :visible.sync="visible" width="1080px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" :inline="true">
          <el-form-item label="用户工号" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="请输入用户工号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="手机号码" prop="phonenumber">
            <el-input
              v-model="queryParams.phonenumber"
              placeholder="请输入手机号码"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="归属部门" prop="deptId">
            <treeselect v-model="queryParams.deptId"
                        class="treeselect-main"
                        :options="deptOptionsa"
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
        <el-table-column label="用户工号" prop="userName" :show-overflow-tooltip="true"/>
        <el-table-column label="用户姓名" prop="nickName" :show-overflow-tooltip="true"/>
        <el-table-column label="邮箱" prop="email" :show-overflow-tooltip="true"/>
        <el-table-column label="手机" prop="phonenumber" :show-overflow-tooltip="true"/>
        <el-table-column label="状态" align="center" prop="status">
          <template v-slot:default="scope">
            <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template v-slot:default="scope">
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
import {getAllUserList} from "@/api/system/role";
import {deptTreeSelect} from "@/api/system/user";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  dicts: ['sys_normal_disable'],
  components: {Treeselect},
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
      // 未授权用户数据
      userList: [],
      // 部门树选项
      deptOptionsa: undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleId: 1,
        userName: undefined,
        phonenumber: undefined,
        deptId: undefined
      }
    };
  },
  created() {
    this.getDeptTree();
  },
  methods: {
    // 显示弹框
    show(val) {
      this.getList();
      this.visible = true;
      this.isSingle = val

    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptionsa = response.data;
      });

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
      getAllUserList(this.queryParams).then(res => {
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
.treeselect-main {
  width: 200px;
}
</style>
