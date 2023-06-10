<template>
  <el-dialog title="选择岗位" :visible.sync="visible" width="1080px" top="5vh" append-to-body class="customDialogStyle">
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="head-container">
          <el-select v-model="queryParams.compId" placeholder="请选择公司名称" size="small" :popper-append-to-body="false">
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
          <el-form-item label="岗位名称" prop="postName">
            <el-input
              v-model="queryParams.postName"
              placeholder="请输入岗位名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery(1)">重置</el-button>
          </el-form-item>
        </el-form>
        <el-table @row-click="clickRow" ref="table" :data="postList" @selection-change="handleSelectionChange"
                  height="360px">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column label="岗位编码" prop="postCode" :show-overflow-tooltip="true"/>
          <el-table-column label="岗位名称" prop="postName" :show-overflow-tooltip="true"/>
          <el-table-column label="所属机构" prop="orgName" :show-overflow-tooltip="true"/>
<!--          <el-table-column label="专长" prop="specialityId" :show-overflow-tooltip="true"/>-->
<!--          <el-table-column label="岗位类别" prop="postTypeId" :show-overflow-tooltip="true"/>-->

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
import '@/assets/styles/humanStyles.scss';
import {selectCompany, treeselect} from "@/api/human/hp/deptMaintenance";
import { listPostMaintenance, getPostMaintenance, delPostMaintenance, addPostMaintenance, updatePostMaintenance } from "@/api/human/hp/postMaintenance";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo"
export default {
  components: {DictTagHuman},
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中岗位信息
      postId: [],
      postName: [],
      postCode: [],
      // 总条数
      total: 0,
      // 未授权用户数据
      postList: [],
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
        postName:null,
        compId: null,
        orgId: null,
      },
      //公司数据
      companyList:[],
      //是否展示树和表
      treeAndTable:true,
    };
  },
  created() {
    this.getCompanyList();
  },
  watch: {
    // 根据名称筛选部门树
    'queryParams.compId'(val) {
      // this.$refs.tree.filter(val);
      if(val){
        this.getList();
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
    show(compId) {
      this.resetQuery();
      this.postId = []
      this.postName = []
      this.postCode = []
      this.postList = []
      this.queryParams.compId = compId
      this.visible = true;
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.postId = selection.map(item => item.postId);
      this.postCode= selection.map(item => item.postCode);
      this.postName=  selection.map(item => item.postName);
    },
    // 查询表数据
    getList() {
      listPostMaintenance(this.queryParams).then(res => {
        this.postList = res.rows;
        this.total = res.total;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery(e) {
      this.queryParams.postName = null;
      this.queryParams.orgId = null;
      if(e==1){
        this.getList();
      }
    },
    /** 选择授权用户操作 */
    handleSelectUser() {
      const postId = this.postId.join(",");
      const postName = this.postName.join(",");
      const postCode = this.postCode.join(",");
      if (postId == "") {
        this.$modal.msgError("请选择要分配的用户");
        return;
      }
      if (this.postId.length > 1) {
        this.$modal.msgError("只能选择一笔数据");
        return;
      }
      this.visible = false;
      var postData={
        postId:postId,
        postName:postName,
        postCode:postCode,
      }
      this.$emit("ok", postData);
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      treeselect(this.queryParams).then(response => {
        this.deptOptions = response.data;
      });
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.orgId = data.id;
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
