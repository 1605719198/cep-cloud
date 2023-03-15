<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-select v-model="compId" placeholder="请选择公司名称" clearable size="small">
            <el-option
              v-for="dict in dict.type.comp_id"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </div>
        <div class="head-container" style="height: 81vh;width: 100%;" v-show="treeandtable">
          <el-scrollbar style="height: 100%;">
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
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="机构id值" prop="deptId">
            <el-input maxlength="10"
                      v-model="queryParams.deptId"
                      type="number"
                      placeholder="请输入机构id"
            />
          </el-form-item>
          <!--      <el-form-item label="机构层级" prop="compId">-->
          <!--        <el-select v-model="form.compId" placeholder="请选择机构层级" clearable size="small">-->
          <!--          <el-option-->
          <!--            v-for="dict in dict.type.comp_id"-->
          <!--            :key="dict.value"-->
          <!--            :label="dict.label"-->
          <!--            :value="dict.value"-->
          <!--          />-->
          <!--        </el-select>-->
          <!--      </el-form-item>-->
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
            >删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="deptmaintenanceList" @selection-change="handleSelectionChange" height="67vh" v-show="treeandtable">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="机构id" align="center" prop="deptId" />
          <el-table-column label="机构编码" align="center" prop="deptCode" />
          <el-table-column label="机构名称" align="center" prop="deptName" />
          <el-table-column label="排序序号" align="center" prop="orderNum" />
          <el-table-column label="领导" align="center" prop="leader" />
          <el-table-column label="创建人" align="center" prop="createBy" />
          <el-table-column label="状态" align="center" prop="status">
            <template v-slot:default="scope">
              <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template v-slot:default="scope">
              <!--          <el-button-->
              <!--            size="mini"-->
              <!--            type="text"-->
              <!--            icon="el-icon-info"-->
              <!--            @click="handleView(scope.row)"-->
              <!--          >详情</el-button>-->
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="(total>0)&&treeandtable"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>
    <!-- 添加或修改部门资料维护对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="机构编码" prop="deptCode">
              <el-input maxlength="20" v-model="form.deptCode" placeholder="请输入机构编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="机构名称" prop="deptName">
              <el-input maxlength="30" v-model="form.deptName" placeholder="请输入机构名称" />
            </el-form-item>
          </el-col>
        </el-row>

        <!--        <el-form-item label="上级机构id" prop="parentId">-->
        <!--          <el-input maxlength="20" v-model="form.parentId" placeholder="请输入上级机构id" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="上级机构全称" prop="parentName">-->
        <!--          <el-input maxlength="20" v-model="form.parentFullName" placeholder="请输入上级机构全称" />-->
        <!--        </el-form-item>-->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="上级机构" prop="parentId">
              <treeselect v-model="form.parentId" :options="allDeptOptions" :show-count="true" placeholder="请选择上级机构" :normalizer="normalizer" @select="deptChange"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="机构层级" prop="orgTierId">
              <el-select v-model="form.orgTierId" placeholder="请选择机构层级" clearable size="small" style="width: 100%;">
                <el-option
                  v-for="dict in dict.type.org_tier"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="当前状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序序号" prop="orderNum">
              <el-input maxlength="22" v-model="form.orderNum" type="number" placeholder="请输入排序序号" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="领导" prop="leader">
              <el-input maxlength="20"  v-model="form.leader" placeholder="请输入领导" >
                <el-button slot="append" icon="el-icon-search" @click="inputClick()"></el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="phone">
              <el-input maxlength="11" v-model="form.phone" placeholder="请输入电话" type="number" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input maxlength="50" v-model="form.email" placeholder="请输入邮箱" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="传真" prop="fax">
              <el-input maxlength="11" v-model="form.fax" placeholder="请输入传真" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="成本中心" prop="costCenterId">
              <el-input maxlength="100" v-model="form.costCenterId"  placeholder="请输入内容" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生效日期" prop="effectDate">
              <el-date-picker clearable size="small"
                              style="width: 100%"
                              v-model="form.effectDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择日期" >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="变更原因" prop="changeReason" v-show="ifupdate">
          <el-input maxlength="300" v-model="form.changeReason" type="textarea" placeholder="请输入变更原因" />
        </el-form-item>

        <el-row :gutter="20" v-show="!ifupdate">
          <el-col :span="12">
            <el-form-item label="创建人员" prop="createBy">
              <el-input maxlength="20" v-model="form.createBy"  placeholder="请输入内容" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建日期" prop="createTime">
              <el-date-picker clearable
                              disabled
                              style="width: 100%;"
                              v-model="form.createTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择创建日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <div v-show="ifupdate">
          <el-divider content-position="center" >变更记录</el-divider>
        </div>

        <el-table :data="deptversionlist"  ref="deptversion" v-show="ifupdate">
          <el-table-column label="版本号" prop="versionNo" align="center" show-overflow-tooltip>
            <template v-slot:default="scope">
              <span>{{scope.row.versionNo}}</span>
            </template>
          </el-table-column>
          <el-table-column label="机构编码" prop="deptCode" align="center" show-overflow-tooltip>
            <template v-slot:default="scope">
              <span>{{scope.row.deptCode}}</span>
            </template>
          </el-table-column>
          <el-table-column label="机构名称" prop="deptName" align="center" show-overflow-tooltip>
            <template v-slot:default="scope">
              <span>{{scope.row.deptName}}</span>
            </template>
          </el-table-column>
          <el-table-column label="该机构上级" prop="parentName" align="center" show-overflow-tooltip>
            <template v-slot:default="scope">
              <span>{{scope.row.parentName}}</span>
            </template>
          </el-table-column>
          <el-table-column label="机构层级" prop="orgTierId" align="center" show-overflow-tooltip>
            <template v-slot:default="scope">
              <dict-tag :options="dict.type.org_tier" :value="scope.row.orgTierId"/>
            </template>
          </el-table-column>
          <el-table-column label="变更原因" prop="changeReason" align="center" show-overflow-tooltip>
            <template v-slot:default="scope">
              <span>{{scope.row.changeReason}}</span>
            </template>
          </el-table-column>
          <el-table-column label="变更人" prop="updateBy" show-overflow-tooltip>
            <template v-slot:default="scope">
              <span>{{scope.row.updateBy}}</span>
            </template>
          </el-table-column>
          <el-table-column label="变更日期" prop="updateTime" show-overflow-tooltip>
            <template v-slot:default="scope">
              <span v-text="scope.row.updateTime"   ></span>
            </template>
          </el-table-column>
        </el-table>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <select-user ref="select" @ok="getJobNumber"></select-user>
  </div>
</template>

<script>
import { listDeptmaintenance, getDeptmaintenance, delDeptmaintenance, addDeptmaintenance, updateDeptmaintenance, treeselect } from "@/api/human/hp/deptMaintenance";
import Treeselect from "@riophae/vue-treeselect";
import { getAvatorByUserName } from "@/api/system/user";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { listDeptversion } from '@/api/human/hp/deptVersion'
import selectUser from "@/views/human/hp/selectUser/selectUser";
export default {
  name: "Deptmaintenance",
  dicts: ['org_tier', 'sys_normal_disable', 'comp_id'],
  components: {Treeselect,selectUser},
  data() {
    return {
      //登录人姓名
      nickName: undefined,
      //登录人公司
      logincompId:undefined,
      // 选择人员单笔或多笔
      isSingle: true,
      // 部门名称
      compId: undefined,
      //默认展开指定节点
      expandedKeys: [],
      // 部门树选项
      deptOptions: undefined,
      //所有部门树
      allDeptOptions: undefined,
      //是否展示树和表
      treeandtable:true,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 部门资料维护表格数据
      deptmaintenanceList: [],
      //部门维护历史版本
      deptversionlist:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: null,
        compId: null,
        ifCompany : 0,
        ancestors :null,
      },
      queryParams2: {
        pageNum: 1,
        pageSize: 100,
        deptId: null,
      },
      queryParams3:{
        compId: null,
        ifCompany:0,
      },
      currentNodeId: '',
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label",
        ancestors: "ancestors"
      },
      //是否修改
      ifupdate:false,
      // 表单校验
      rules: {
        deptCode: [
          { required: true, message: '请输入机构编码', trigger: 'blur' },
        ],
        deptName: [
          { required: true, message: '请输入机构名称', trigger: 'blur' },
        ],
        parentId: [
          { required: true, message: '请选择该机构上级', trigger: 'blur' },
          { pattern:/^(?!100$)/, message: '不能选择集团为机构上级', trigger: 'blur'}
        ],
        orgTierId: [
          { required: true, message: '请选择机构层级', trigger: 'change' },
        ],
        status: [
          { required: true, message: '请选择当前状态', trigger: 'change' },
        ],
        leader: [
          {required: true,message: "请输入领导", trigger: "blur"}
        ],
        phone: [
          {required: true,message: "请输入领导电话", trigger: "blur"}
        ],
        effectDate:[
          { required: true, message: '生效日期不能为空', trigger: 'change' },
        ]
        // changeReason: [
        //   {required: true,message: "请输入变更原因", trigger: "blur"}
        // ]
      }
    };
  },
  watch: {
    // 根据名称筛选部门树
    compId(val) {
      // this.$refs.tree.filter(val);
      this.queryParams.compId = val;
      this.queryParams.deptId = null;
      this.queryParams3.compId =val;
      if(val){
        this.treeandtable=true
      }else{
        this.treeandtable=false
      }
      this.getList();
      this.getTreeselect();
    }
  },
  created() {
    this.getName()
    this.getTreeselect();
    // this.currentNodeId = this.$store.state.user.deptId
  },
  methods: {
    // 获取当前登录用户名称/信息
    getName(){
      getAvatorByUserName(this.$store.state.user.name).then( response => {
        this.nickName=response.data.nickName
        this.logincompId=response.data.compId
        this.compId=response.data.compId
      })
    },
    /** 点选获取人员信息 */
    getJobNumber(val) {
      this.form.leader = val[0].nickName;
      if(val[0].phonenumber!=null){
        this.form.phone = val[0].phonenumber;
        this.form.email = val[0].email;
      }
      //   this.form.jobNumber = val[0].userName
      //   this.form.jobName = val[0].nickName
    },
    /** 获取当前日期 */
    getDate(e){
      var today = new Date(); //日期
      var DD = String(today.getDate()).padStart(2, '0'); // 获取日
      var MM = String(today.getMonth() + 1).padStart(2, '0'); //获取月份，1 月为 0
      var yyyy = today.getFullYear(); // 获取年
      var hh = String(today.getHours()).padStart(2, '0'); //获取当前小时数(0-23)
      var mm = String(today.getMinutes()).padStart(2, '0'); //获取当前分钟数(0-59)
      var ss = String(today.getSeconds()).padStart(2, '0'); //获取当前秒数(0-59)
      var time =yyyy + '-' + MM + '-' + DD+' '+hh+':'+mm+':'+ss;
      var date = yyyy + '-' + MM + '-' + DD;
      if(e==0){
        return time;
      }else {
        return date;
      }
    },
    /** 用户选单事件 */
    inputClick() {
      this.isSingle = true
      this.$refs.select.show(this.isSingle);
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect(this.queryParams3).then(response => {
        this.deptOptions = response.data;
        this.expandedKeys.push(this.$store.state.user.deptId);
      });
      treeselect().then(response => {
        this.allDeptOptions = response.data;
      });
    },
    /** 转换部门数据结构 */
    normalizer(node) {
      // if (node.children && !node.children.length) {
      //   delete node.children;
      // }
      return {
        id: node.id,
        label: node.label,
        label2: node.label2,
        children: node.children
      };
    },
    /** 上级部门切换事件 */
    deptChange(val) {
      this.form.parentName = val.label
      this.form.parentId = val.id
      this.form.compId = val.label2
    },
    // 筛选节点
    filterNode(value, data) {
      return true;
      // this.collapseAll(this.deptOptions)
    },
    //折叠节点
    collapseAll(data) {
      let self = this;
      data.forEach((el) => {
        self.$refs.tree.store.nodesMap[el.id].expanded = false;
        el.children && el.children.length > 0
          ? self.collapseAll(el.children)
          : ""; // 子级递归
      });
    },
    // 节点单击事件
    handleNodeClick(data) {
      if(data.label3!=1){
        this.queryParams.deptId = data.id;
        this.handleQuery();
      }
    },
    /** 查询部门资料维护列表 */
    getList() {
      this.loading = true;
      listDeptmaintenance(this.queryParams).then(response => {
        this.deptmaintenanceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        deptId: null,
        deptCode: null,
        deptName: null,
        boardId: null,
        parentId: null,
        parentName: null,
        parentFullName: null,
        ancestors: null,
        orgTierId: null,
        orderNum: null,
        leader: null,
        email: null,
        delFlag: null,
        costCenterId: null,
        fax: null,
        phone: null,
        changeReason: null,
        effectDate: null,
        versionNo: null,
        ifCompany: null,
        compId: null,
        ifDisplay: null,
        createBy: null,
        status: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        isNew: null,
        companyName: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.compId=this.logincompId
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.deptId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.ifupdate=false;
      this.reset();
      this.form.createBy=this.nickName;
      this.form.updateBy=this.nickName;
      this.form.createTime=this.getDate(0);
      this.form.updateTime=this.getDate(0);
      this.form.effectDate=this.getDate(1);
      this.form.versionNo=1;
      this.form.ifCompany = 0;
      this.form.isNew=1;
      this.form.status="0";
      this.open = true;
      this.title = "添加部门资料维护";
    },
    /** 详情按钮操作 */
    handleView(row){

    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.ifupdate=true;
      this.reset();
      const deptId = row.deptId || this.ids
      getDeptmaintenance(deptId).then(response => {
        this.form = response.data;
        this.form.updateBy=this.nickName;
        this.form.updateTime=this.getDate(0);
        this.form.effectDate=this.getDate(1);
        this.form.versionNo++;
        this.form.isNew=1;
        this.open = true;
        this.title = "修改部门资料维护";
      });
      this.queryParams2.deptId = deptId
      listDeptversion(this.queryParams2).then(response => {
        this.deptversionlist = response.rows;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.deptId != null) {
            updateDeptmaintenance(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.getTreeselect();
            });
          } else {
            addDeptmaintenance(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.getTreeselect();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const deptIds = row.deptId || this.ids;
      this.$modal.confirm('是否确认删除部门资料维护编号为"' + deptIds + '"的数据项？').then(function() {
        return delDeptmaintenance(deptIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
        this.getTreeselect();
      }).catch(() => {});
    },
  }
};
</script>

<style scoped>
</style>
