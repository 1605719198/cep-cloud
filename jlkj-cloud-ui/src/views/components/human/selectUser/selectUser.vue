<template>
  <!-- 授权用户 -->
  <el-dialog title="选择用户" :visible.sync="visible" width="1080px" top="5vh" append-to-body>
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="head-container">
          <el-select v-model="companyId" placeholder="请选择公司名称" clearable size="small">
            <el-option
              v-for="dict in companyList"
              :key="dict.compId"
              :label="dict.companyName"
              :value="dict.compId"
            />
          </el-select>
        </div>
        <div class="head-container treeDept"  v-show="treeAndTable">
          <el-scrollbar class="treeScrollbar">
            <el-tree
              :data="deptOptions"
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
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
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
      </el-col>
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSelectUser">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {getAllUserList} from "@/api/human/hm/personnelBasicInfo";
import {selectCompany, treeselect} from "@/api/human/hp/deptMaintenance";

export default {
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
      // 选中id数组名称
      empId: [],
      companyId: undefined,
      // 总条数
      total: 0,
      // 未授权用户数据
      userList: [],
      // 部门树选项
      deptOptions: undefined,
      //el tree默认值
      defaultProps: {
        children: "children",
        label: "label",
      },
      //默认展开指定节点
      expandedKeys: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        empNo: undefined,
        myMobilePhone: undefined,
        departmentId: undefined,
        compId: undefined
      },
      //公司数据
      companyList:[],
      //是否展示树和表
      treeAndTable:true,
    };
  },
  created() {
    this.companyId = 'J00'
    this.getCompanyList();
  },
  watch: {
    // 根据名称筛选部门树
    companyId(val) {
      // this.$refs.tree.filter(val);
      this.queryParams.compId = val;
      this.queryParams.departmentId = null;
      if(val){
        this.treeAndTable=true;
        this.getDeptTree();
      }else{
        this.treeAndTable=false
      }
    },
    //监听数据 设置默认展示第一层数据
    deptOptions: {
      handler (val) {
        val.forEach(item => {
          this.expandedKeys.push(item.id);
        })
      },
      deep: true,
    },
  },
  methods: {
    //获取公司列表
    getCompanyList(){
      selectCompany().then(response=>{
        this.companyList = response.data
      })
    },
    // 显示弹框
    show(queryParams) {
      this.resetQuery();
      this.userIds = []
      this.userName = []
      if(queryParams){
        this.queryParams = queryParams;
      }
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
    },
    /** 选择授权用户操作 */
    handleSelectUser() {
      const userIds = this.userIds.join(",");
      const userName = this.userName.join(",");
      const compId = this.compId.join(",");
      const id = this.empId.join(",");
      if (userIds == "") {
        this.$modal.msgError("请选择要分配的用户");
        return;
      }
      if (this.userIds.length > 1) {
        this.$modal.msgError("只能选择一笔数据");
        return;
      }
      this.visible = false;
      this.$emit("ok", userIds, userName, compId, id);
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      treeselect(this.queryParams).then(response => {
        this.deptOptions = response.data;
      });
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.departmentId = data.id;
      this.handleQuery();
    },
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
