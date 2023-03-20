<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-select v-model="compId" placeholder="请选择公司别" clearable size="small">
            <el-option
              v-for="dict in baseInfoData.comp_id"
              :key="dict.dicNo"
              :label="dict.dicName"
              :value="dict.dicNo"
            />
          </el-select>
        </div>
        <div class="head-container" style="height: 81vh;width: 100%;" v-show="treeandtable">
          <el-scrollbar style="height: 100%;">
            <el-tree
              :data="deptOptions"
              :props="defaultProps"
              :default-expand-al="false"
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
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" >
          <el-form-item label="状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
              <el-option
                v-for="dict in baseInfoData.dept_status"
                :key="dict.dicNo"
                :label="dict.dicName"
                :value="dict.dicNo"
              />
            </el-select>
          </el-form-item>
<!--          <el-form-item label="所属公司/机构id" prop="orgId" >-->
<!--            <el-input-->
<!--              v-model="queryParams.orgId"-->
<!--              placeholder="请输入所属公司/机构id"-->
<!--              clearable-->
<!--              @keyup.enter.native="handleQuery"-->
<!--            />-->
<!--          </el-form-item>-->
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
              v-hasPermi="['human:postMaintenance:add']"
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
              v-hasPermi="['human:postMaintenance:remove']"
            >删除</el-button>
          </el-col>
<!--          <el-col :span="1.5">-->
<!--            <el-button-->
<!--              type="warning"-->
<!--              plain-->
<!--              icon="el-icon-download"-->
<!--              size="mini"-->
<!--              @click="handleExport"-->
<!--              v-hasPermi="['human:postMaintenance:export']"-->
<!--            >导入</el-button>-->
<!--          </el-col>-->
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <el-table v-loading="loading" :data="postMaintenanceList" @selection-change="handleSelectionChange" height="67vh" v-show="treeandtable">
          <el-table-column type="selection" width="55" align="center" />
<!--          <el-table-column label="主键" align="center" prop="postId" />-->
          <el-table-column label="岗位名称" align="center" prop="postName" />
          <el-table-column label="定员" align="center" prop="planCapacity" />
          <el-table-column label="现员" align="center" prop="nowCapacity" />
          <el-table-column label="状态" align="center" prop="status">
            <template v-slot:default="scope">
              <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
            </template>
          </el-table-column>
          <el-table-column label="该岗位上级岗位名称" align="center" prop="parentPostName" />
          <el-table-column label="更新人" align="center" prop="updateBy" />
          <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
            <template v-slot:default="scope">
              <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template v-slot:default="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['human:postMaintenance:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['human:postMaintenance:remove']"
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
        <!-- 添加或修改参数配置对话框 -->
        <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @get-list="getList"></add-or-update>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getBaseInfo } from "@/api/human/hm/baseInfo"
import { treeselect } from "@/api/human/hp/deptMaintenance";
import Treeselect from "@riophae/vue-treeselect";
import { getAvatorByUserName} from "@/api/system/user";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { listPostMaintenance, getPostMaintenance, delPostMaintenance, addPostMaintenance, updatePostMaintenance } from "@/api/human/hp/postMaintenance";
import AddOrUpdate from '@/views/human/hp/postView/base'
export default {
  name: "PostMaintenance",
  dicts: ['sys_normal_disable'],
  components: {Treeselect,AddOrUpdate},
  data() {
    return {
      //新增修改界面是否显示
      addOrUpdateVisible:false,
      //选单列表
      baseInfo: {
        uuid: '',
        baseInfoList: [
          'comp_id',
          'dept_status'
        ]
      },
      //选单数据
      baseInfoData: [],
      // 公司名称
      compId: undefined,
      //登录人姓名
      nickName: undefined,
      //登录人公司
      logincompId:undefined,
      //是否展示树和表
      treeandtable:true,
      // 遮罩层
      loading: true,
      //默认展开指定节点
      expandedKeys: [],
      // 部门树选项
      deptOptions: undefined,
      //所有部门树
      allDeptOptions: undefined,
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
      // 岗位信息数据维护表格数据
      postMaintenanceList: [],
      // 弹出层标题
      title: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        status: '2',
        orgId: null,
        compId: null,
        deptId:null,
      },
      queryParams2: {
        postId:null,
      },
      queryParams3: {
        compId:null,
      },
      // 表单参数
      form: {},
      //el tree默认值
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 表单校验
      rules: {
        postCode: [
          { required: true, message: "岗位编码不能为空", trigger: "blur" }
        ],
        postName: [
          { required: true, message: "岗位名称不能为空", trigger: "blur" }
        ],
        deptId: [
          { required: true, message: "所属公司/机构不能为空", trigger: "blur" }
        ],
        specialityTypeId: [
          { required: true, message: "专长类别id不能为空", trigger: "change" }
        ],
        specialityId: [
          { required: true, message: "专长id不能为空", trigger: "blur" }
        ],
        postSequenceId: [
          { required: true, message: "岗位序列id不能为空", trigger: "change" }
        ],
        postTypeId: [
          { required: true, message: "岗位类别id不能为空", trigger: "change" }
        ],
        serialNumber: [
          { required: true, message: "流水号不能为空", trigger: "blur" }
        ],
        workAddress: [
          { required: true, message: "公司不能为空", trigger: "change" }
        ],
        overseasLocations: [
          { required: true, message: "驻外地点不能为空", trigger: "blur" }
        ],
        planCapacity: [
          { required: true, message: "定员不能为空", trigger: "blur" }
        ],
        nowCapacity: [
          { required: true, message: "现员不能为空", trigger: "blur" }
        ],
        postSort: [
          { required: true, message: "显示顺序不能为空", trigger: "blur" }
        ],
        costCenterId: [
          { required: true, message: "成本中心不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
        parentPostName: [
          { required: true, message: "该岗位上级岗位名称不能为空", trigger: "change" }
        ],
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
        this.getTreeselect();
      }else{
        this.treeandtable=false
      }
      this.getList();
    }
  },
  created() {
    this.getHumandisc();
    this.getName();
    // this.getTreeselect();
  },
  methods: {
    //获取人事选单字典
    getHumandisc(){
      getBaseInfo(this.baseInfo).then(response => {
        this.baseInfoData = response.data;
        // for(var i =0;i<this.baseInfoData.comp_id.length;i++){
        //   console.log(JSON.stringify(baseInfoData.comp_id[i]))
        // }
        this.baseInfoData.comp_id.forEach((item,index,array)=>{
          // this.expandedKeys.push(101);
          // alert(item.dicNo)
        })
        this.expandedKeys.push(101);
        this.expandedKeys.push(102);
        this.expandedKeys.push(200);

      });
    },
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
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect(this.queryParams3).then(response => {
        this.deptOptions = response.data;
      });
      treeselect().then(response => {
        this.allDeptOptions = response.data;
      });
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
        this.queryParams.deptId = data.id;
        this.handleQuery();
    },
    /** 查询岗位信息数据维护列表 */
    getList() {
      this.loading = true;
      listPostMaintenance(this.queryParams).then(response => {
        this.postMaintenanceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.addOrUpdateVisible = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        postId: null,
        postCode: null,
        postName: null,
        deptId: null,
        specialityTypeId: null,
        specialityId: null,
        postSequenceId: null,
        postTypeId: null,
        jobTitleId: null,
        serialNumber: null,
        shiftWorkId: null,
        employmentNatureId: null,
        workAddressId: null,
        workAddress: null,
        overseasLocations: null,
        ifSpeciallyAssignedPerson: null,
        planCapacity: null,
        nowCapacity: null,
        postSort: null,
        capacityBasis: null,
        costCenterId: null,
        status: '0',
        parentPostId: null,
        parentPostName: null,
        positionsClassId: null,
        orgId: null,
        orgName: null,
        orgFullName: null,
        positionExpenseCategory: null,
        costCenterName: null,
        changeReason: null,
        effectDate: this.getDate(1),
        versionNo: 1,
        ifNew: 1,
        compId: null,
        createBy: this.nickName,
        createTime: this.getDate(0),
        updateBy: this.nickName,
        updateTime: this.getDate(0),
        workDesc: null,
        workCon: null,
        apprKpi: null,
        reviewKpi: null,
        workSta: null,
        workAut: null,
        workEnv: null,
        opeEquHp: null,
        toolUsed: null,
        officeEqu: null,
        labourSta: null,
        minAge: null,
        maxAge: null,
        gender: null,
        recordId: null,
        speClaim: null,
        proQua: null,
        workExp: null,
        otherClaim: null,
        knowTec: null,
        proAct: null
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
      this.compId = this.logincompId;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.postId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(this.form)
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const postId = row.postId || this.ids
      getPostMaintenance(postId).then(response => {
        this.form = response.data;
        this.form.versionNo++;
        this.form.updateBy = this.nickName;
        this.form.updateTime = this.getDate(0);
        this.form.effectDate = this.getDate(1);
        this.addOrUpdateVisible = true;
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(this.form)
        })
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const postIds = row.postId || this.ids;
      this.$modal.confirm('是否确认删除岗位信息数据维护编号为"' + postIds + '"的数据项？').then(function() {
        return delPostMaintenance(postIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/postMaintenance/export', {
        ...this.queryParams
      }, `postMaintenance_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
