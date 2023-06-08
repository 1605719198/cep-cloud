<template>
  <div style="padding: 0px 10px;">
    <div class="main">
      <div class="avue-crud el-card__body " style="width: 98%;border: 0;">
        <el-row :gutter="20">
          <!-- 左侧选单配置树 -->
          <el-col :span="4" class="left_tree">
            <div class="head-container">
              <el-select v-model="queryParams.compId" placeholder="请选择公司" size="small">
                <el-option
                  v-for="dict in companyList"
                  :key="dict.compId"
                  :label="dict.companyName"
                  :value="dict.compId"
                />
              </el-select>
            </div>
            <div class="head-container" style="height: 75vh;width: 100%;">
              <el-scrollbar style="height: 100%;">
                <el-tree ref="tree"
                         class="filter-tree"
                         node-key="id"
                         :props="defaultProps"
                         :data="menuData"
                         :expand-on-click-node="false"
                         :default-expanded-keys="defaultShowNodes"
                         @node-click="handleNodeClick">
                </el-tree>
              </el-scrollbar>
            </div>
          </el-col>
          <!-- 右侧列表 -->
          <el-col :span="20">
            <div>
              <div class="avue-crud__search" style="border: 0">
                <el-row>
                  <el-col :span="24">
                    <el-form :inline="true" >
                      <!-- 操作按钮 -->
                      <el-form-item>
                        <el-button @click="handleAdd" plain type="primary" icon="el-icon-plus" size="mini" v-hasPermi="['human:salaryBasis:add']" >新增
                        </el-button>
                      </el-form-item>
                      <el-form-item label="父节点编码:" v-show="this.parentCode!=null">
                        {{parentCode}}
                      </el-form-item>
                    </el-form>
                  </el-col>
                </el-row>
              </div>
              <div>
                <el-table height="70vh" size="small" v-loading="table.loading" :data="tableData" stripe>
                  <el-table-column label="资料代号" minWidth="150" align="center" prop="infoCode"/>
                  <el-table-column label="资料名称" minWidth="150" align="center" prop="infoName"/>
                  <el-table-column label="状态" minWidth="150" align="center" prop="status">
                    <template v-slot="scope">
                      <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
                    </template>
                  </el-table-column>
                  <el-table-column label="输入人" minWidth="150" align="center" prop="creator"/>
                  <el-table-column label="输入日期" minWidth="150" align="center" prop="createDate"/>
                  <el-table-column label="操作" align="center" min-width="160px">
                    <template v-slot="scope">
                      <el-button size="mini" type="text" icon="el-icon-edit"
                                 v-hasPermi="['human:salaryBasis:edit']"
                                 @click="handleUpdate(scope.row)">
                        编辑
                      </el-button>
                      <el-button size="mini" type="text" icon="el-icon-delete"
                                 v-hasPermi="['human:salaryBasis:remove']"
                                 @click="handleDelete(scope.row)">
                        删除
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <pagination
                  v-show="total>0"
                  :total="total"
                  :page.sync="queryParams.pageNum"
                  :limit.sync="queryParams.pageSize"
                  @pagination="onLoad"
                />
              </div>
            </div>
          </el-col>
        </el-row>
        <!-- 修改员工薪资基本资料维护对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body class="customDialogStyle">
          <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="资料编码" prop="infoCode">
                  <el-input v-model="form.infoCode" placeholder="请输入资料编码" maxlength="30" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="资料名称" prop="infoName">
                  <el-input v-model="form.infoName" placeholder="请输入资料名称" maxlength="200" />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="状态" prop="status">
                  <el-radio-group v-model="form.status">
                    <el-radio
                      v-for="dict in dict.type.sys_normal_disable"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="输入人" prop="creator">
                  {{form.creatorNo}}-{{form.creator}}
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="输入日期" prop="createDate">
                  {{form.createDate}}
                </el-form-item>
              </el-col>
            </el-row>

          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>

        <!-- 添加员工薪资基本资料维护对话框 -->
        <el-dialog :title="title" :visible.sync="opencreate" width="400px" append-to-body class="customDialogStyle">
          <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="资料编码" prop="infoCode">
              <el-input v-model="form.infoCode" placeholder="请输入资料编码" />
            </el-form-item>
            <el-form-item label="资料名称" prop="infoName">
              <el-input v-model="form.infoName" placeholder="请输入资料名称" />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import '@/assets/styles/humanStyles.scss';
import { selectCompany } from '@/api/human/hp/deptMaintenance'
import { getDateTime } from '@/api/human/hd/ahumanUtils'
import { listSalaryBasis, getSalaryBasis, delSalaryBasis, addSalaryBasis, updateSalaryBasis, listSalaryBasisTree, getSalaryOptions, getSalaryDeepOptions } from "@/api/human/hs/salaryBasis";
export default {
  name: "BaseInfo",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      //公司数据
      companyList: [],
      // 是否显示修改弹出层
      open: false,
      //是否显示新增弹出层
      opencreate: false,
      //登录人信息
      user: {
        empNo: null,
        empName: null,
        empId: null,
        compId: null
      },
      //选单树数据转化
      defaultProps: {
        children: 'children',
        label: 'infoName'
      },
      //选单目录数据
      menuData: [],
      // 表单参数
      form: {},
      //弹出窗标题
      title: '',
      //查询参数
      queryParams: {
        pageSize: 10,
        pageNum: 1,
        id: null,
        compId: null,
      },
      // 总条数
      total: 0,
      table: {
        border: true,
        loading: false,
      },
      tableData: [],
      //薪资选单类型查询
      salaryOptionType: {
        id: '19',
        optionsType: ['PerformanceLevel'],
        compId:'J00',
      },
      //薪资选单选项列表
      options: [],
      //默认显示id
      defaultShowNodes: [],
      //父节点编码
      parentCode:null,
      // 表单校验
      rules: {
        infoCode: [
          { required: true, message: "资料编码不能为空", trigger: "blur" }
        ],
        infoName: [
          { required: true, message: "资料名称不能为空", trigger: "blur" }
        ],
      }
    }
  },
  watch: {
    'queryParams.compId':{
      handler(val) {
        this.getBaseInfoTree();
      },
      deep: true,
      immediate: true,
    },
  },
  created() {
    // this.test();
    this.getCompanyList()
    this.initData();
  },
  methods: {
    //选单查询测试方法
    test(){
      getSalaryOptions(this.salaryOptionType).then(response =>{
        alert(JSON.stringify(response))
      })
      getSalaryDeepOptions(this.salaryOptionType).then(response =>{
        alert(JSON.stringify(response))
      })
    },
    //获取公司列表
    getCompanyList() {
      selectCompany().then(response => {
        let gerenal ={
          compId:null,
          companyName:'集团通用',
        }
        this.companyList = response.data
        this.companyList.unshift(gerenal)
      })
    },
    //初始化数据
    initData(){
      this.user.empNo = this.$store.state.user.userInfo.userName;
      this.user.empId = this.$store.state.user.userInfo.userId;
      this.user.empName = this.$store.state.user.userInfo.nickName;
      this.user.compId = this.$store.state.user.userInfo.compId;
    },
    //表单值设置
    setForm(e){
      this.form.creator = this.user.empName;
      this.form.creatorNo = this.user.empNo;
      this.form.creatorId = this.user.empId;
      this.form.createDate = getDateTime(0)
      if(e===0){
        this.form.parentid = this.queryParams.id;
        this.form.compId = this.queryParams.compId;
        this.form.status = '0'
      }
    },
    //获取选单配置树
    getBaseInfoTree() {
      var params ={
        compId:this.queryParams.compId
      }
      listSalaryBasisTree(params).then(response => {
        this.menuData = this.handleTree(response,"id","parentid","children");
        this.defaultShowNodes.push(this.menuData[0].id);
        if(!this.queryParams.id){
          this.queryParams.id = this.defaultShowNodes[0];
        }
        this.onLoad()
        this.loading = false;
      })
    },
    //点击节点方法
    handleNodeClick(data) {
      this.queryParams.id = data.id;
      this.parentCode = data.infoCode;
      this.tableData = []
      this.onLoad()
    },
    //载入数据
    onLoad() {
      this.table.loading = true;//加载状态
      listSalaryBasis(this.queryParams).then(response => {
        this.total = response.total;
        this.tableData = response.rows;//表格数据
        this.table.loading = false;
      }, error => {
        this.table.loading = false;
        window.console.log(error);
      });
    },
    /** 添加下级操作 */
    handlechild(row){
      this.reset();
      this.setForm()
      this.form.parentid = row.id
      this.open = true;
      this.title = "添加下级窗口";
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      if(this.queryParams.id!=null){
        this.reset();
        this.setForm(0);
        this.opencreate = true;
        this.title = "新增窗口";
      }else{
        this.$modal.msgWarning("请先选择父菜单");
      }
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSalaryBasis(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改窗口";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.setForm();
            updateSalaryBasis(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.opencreate = false;
              this.getList();
            });
          } else {
            addSalaryBasis(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.opencreate = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除员工薪资基本资料维护编号为"' + ids + '"的数据项？').then(function() {
        return delSalaryBasis(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.opencreate = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        infoCode: null,
        infoName: null,
        status: null,
        compId: null,
        isShowno: null,
        ischecked: null,
        orderNum: null,
        parentid: null,
        parents: null,
        creator: null,
        creatorNo: null,
        creatorId: null,
        createDate: null
      };
      this.resetForm("form");
    },
    //获取列表数据
    getList(){
      this.getBaseInfoTree();
    }
  }
}
</script>

<style>
.el-scrollbar__wrap{
  overflow-x: hidden;
}
.el-scrollbar__bar.is-horizontal {
  display: none;
}
</style>

