<template>
  <!-- 授权用户 -->
  <el-dialog title="选择组织机构" :visible.sync="visible" width="800px" top="5vh" append-to-body>
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
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
          <el-form-item label="机构编码" prop="deptCode">
            <el-input maxlength="8"
                      v-model="queryParams.deptCode"
                      placeholder="请输入机构编码"
            />
          </el-form-item>
          <el-form-item label="机构名称" prop="deptName">
            <el-input maxlength="200"
                      v-model="queryParams.deptName"
                      placeholder="请输入机构名称"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-table :data="deptmaintenanceList" ref="table" @selection-change="handleSelectionChange" height="360px" v-show="treeAndTable">
          <el-table-column type="selection" width="55" align="center" show-overflow-tooltip />
          <el-table-column label="机构编码" align="center" width="120" prop="deptCode" show-overflow-tooltip />
          <el-table-column label="机构名称" align="center" width="300" prop="deptName" show-overflow-tooltip />
        </el-table>

        <pagination
          v-show="(total>0)"
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
import {listDeptmaintenance, selectCompany, treeselect} from "@/api/human/hp/deptMaintenance";

export default {
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中数组值
      deptCode: [],
      // 选中数组名称
      deptId: [],
      // 选中部门名称
      deptName: [],
      // 选中公司别数组名称
      compId: [],
      companyId: undefined,
      // 总条数
      total: 0,
      // 部门资料维护表格数据
      deptmaintenanceList: [],
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
        deptId: null,
        compId: null,
        ancestors :null,
        deptCode:null,
        deptName:null,
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
      this.getList();
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
    show() {
      this.resetQuery();
      this.deptCode = []
      this.deptId = []
      this.visible = true;
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.deptCode = selection.map(item => item.deptCode);
      this.deptId = selection.map(item => item.deptId);
      this.deptName = selection.map(item => item.deptName);
    },
    // 查询表数据
    getList() {
      listDeptmaintenance(this.queryParams).then(response => {
        this.deptmaintenanceList = response.rows;
        this.total = response.total;
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
      const deptCode = this.deptCode.join(",");
      const deptId = this.deptId.join(",");
      const deptName = this.deptName.join(",");
      if (deptCode === "") {
        this.$modal.msgError("请选择组织机构");
        return;
      }
      if (this.deptCode.length > 1) {
        this.$modal.msgError("只能选择一笔数据");
        return;
      }
      this.visible = false;
      this.$emit("ok", deptCode, deptId, deptName);
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      treeselect(this.queryParams).then(response => {
        this.deptOptions = response.data;
      });
    },
    // 节点单击事件
    handleNodeClick(data) {
      if(data.label3!==1){
        this.queryParams.deptId = data.id;
        this.handleQuery();
      }
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
