<template>
  <div style="padding: 0px 10px;">
    <div class="main">
      <div class="avue-crud el-card__body " style="width: 98%;border: 0;">
        <el-row :gutter="20">
          <!-- 左侧选单配置树 -->
          <el-col :span="4" class="left_tree">
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

            <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="50px">
              <el-form-item label="公司" prop="compId" label-width="80px">
                <el-select :popper-append-to-body="false" v-model="queryParams.compId" placeholder="请选择公司别" clearable ref="selectCompany">
                  <el-option
                    v-for="dict in companyList"
                    :key="dict.compId"
                    :label="dict.companyName"
                    :value="dict.compId"
                  />
                </el-select>
              </el-form-item>

                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form>
            <div>
              <div class="avue-crud__search" style="border: 0">
                <el-row>
                  <el-col :span="20">
                    <el-form :inline="true">
                      <!-- 操作按钮 -->
                      <el-form-item>
                        <el-button v-hasPermi="['human:accountRules:add']" type="primary" size="mini" plain  @click="handleSave">保存</el-button>
                      </el-form-item>
                      <el-form-item>
                        <el-button
                          type="danger"
                          plain
                          size="mini"
                          @click="cancellation"
                        >作废</el-button>
                      </el-form-item>
                    </el-form>
                  </el-col>
                </el-row>
              </div>
              <div>
                <el-table height="70vh" size="small" v-loading="table.loading" :row-class-name="addIndex" :data="tableData" @selection-change="handleSelectionChange" @row-click="addLine" stripe>
                  <el-table-column type="selection" width="55" align="center" />
                  <el-table-column label="费用类别" prop="apprgroup" align="center">
                    <template v-slot="scope">
                      <el-select v-model="scope.row.apprgroup" placeholder="请选择费用类别" clearable size="small">
                        <el-option
                          v-for="dict in salaryOptions.feeType"
                          :key="dict.dicNo"
                          :label="dict.dicName"
                          :value="dict.dicNo"
                        />
                      </el-select>
                    </template>
                  </el-table-column>

                  <el-table-column label="借方会计科目" align="center" prop="acctcoded" >
                    <template v-slot="scope">
                      <el-input v-model="scope.row.acctcoded" placeholder="请输入内容"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="借方户号" align="center" prop="idcoded" >
                    <template v-slot="scope">
                      <el-input v-model="scope.row.idcoded" placeholder="请输入内容"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="借方参号" align="center" prop="efnod" >
                    <template v-slot="scope">
                      <el-input v-model="scope.row.efnod" placeholder="请输入内容"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="借方到期日" align="center" prop="duedated" >
                    <template v-slot="scope">
                        <el-date-picker clearable
                                        v-model="scope.row.duedated"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        placeholder="请选择借方到期日">
                        </el-date-picker>
                    </template>
                  </el-table-column>
                  <el-table-column label="贷方会计科目" align="center" prop="acctcodec" >
                    <template v-slot="scope">
                      <el-input v-model="scope.row.acctcodec" placeholder="请输入内容"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="贷方户号" align="center" prop="idcodec" >
                    <template v-slot="scope">
                      <el-input v-model="scope.row.idcodec" placeholder="请输入内容"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="贷方参考号" align="center" prop="refnoc" >
                    <template v-slot="scope">
                      <el-input v-model="scope.row.refnoc" placeholder="请输入内容"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="贷方到期日" align="center" prop="duedatec" >
                    <template v-slot="scope">
                      <el-date-picker clearable
                                      v-model="scope.row.duedatec"
                                      type="date"
                                      value-format="yyyy-MM-dd"
                                      placeholder="请选择贷方到期日">
                      </el-date-picker>
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


          <!-- 添加或修改集团级薪资项目输入维护对话框 -->
          <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button type="primary" @click="submitForm">确 定</el-button>
              <el-button @click="cancel">取 消</el-button>
            </div>
          </el-dialog>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import {getDateTime} from "@/api/human/hd/ahumanUtils";
import { listAccountRules, delAccountRules, addAccountRules,listAccountRulesTree } from "@/api/human/hs/accountRules";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getSalaryOptions} from "@/api/human/hs/salaryBasis";
import {delPerformanceLevel} from "@/api/human/pa/performanceLevel";


export default {
  name: "AccountRules",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['feeType'],
        compId:null,
      },
      //薪资选单选项列表
      salaryOptions: [],
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
      // 弹出层标题
      title: "",
      //表格属性
      table: {
        border: true,
        loading: false,
      },
      //列表数据
      tableData: [],
      //登录人信息
      user: {},
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        //id: null,
        compId: null,
        code : null,
      },
      //选单树数据转化
      defaultProps: {
        children: 'children',
        label: 'label2'
      },
      companyList:[],
      //选单目录数据
      menuData: [],
      //默认显示id
      defaultShowNodes: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      index: 0,
      multipleSelection: []
    };

  },
  created() {
    this.getCompanyList();
    this.initData();
    this.getDisc();
    this.getBaseInfoTree();

  },
  methods: {
    //查询薪资选单
    getDisc(){
      this.salaryOptionType.compId = this.queryParams.compId;
      getSalaryOptions(this.salaryOptionType).then(response=>{
        this.salaryOptions = response.data;
      })
    },
    //初始化数据
    initData(){
     console.log("user",this.user)
      this.user.empNo = this.$store.state.user.userInfo.userName;
      this.user.empId = this.$store.state.user.userInfo.userId;
      this.user.empName = this.$store.state.user.userInfo.nickName;
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId
    },
    /** 查询集团级薪资项目输入维护列表 */
    getList() {
      this.getBaseInfoTree();
      this.onLoad();
    },
    //获取公司列表
    getCompanyList(){
      selectCompany().then(response=>{
        this.companyList = response.data
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        code: null,
        apprgroup: null,
        infocode: null,
        infoname: null,
        costunit: null,
        costtype: null,
        acctcoded: null,
        efnod: null,
        idcoded: null,
        duedated: null,
        acctcodec: null,
        idcodec: null,
        refnoc: null,
        duedatec: null,
        compId: null,
        creator: null,
        creatorNo: null,
        creatorId: null,
        createDate: null
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
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multipleSelection = selection;
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 保存按钮 */
    handleSave() {
      //薪酬项目id 新增到 list里
      for (let i = 0; i < this.multipleSelection.length; i++) {
        this.multipleSelection[i].code = this.queryParams.code
        this.multipleSelection[i].compId = this.user.compId
      }
      addAccountRules(this.multipleSelection).then(res => {
        this.$modal.msgSuccess("保存成功");
        this.getList();
      })
    },
    // 作废按钮
    cancellation() {
    const ids = row.uuid || this.ids;
    this.$modal.confirm('是否确认删除绩效系数设定编号为"' + ids + '"的数据项？').then(function() {
      return delAccountRules(ids);
    }).then(() => {
      this.getList();
      this.$modal.msgSuccess("删除成功");
    }).catch(() => {});
  },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAccountRules(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAccountRules(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除此集团级薪资项目输入维护的数据项？').then(function() {
        return delAccountRules(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    //获取选单配置树
    getBaseInfoTree() {

      listAccountRulesTree(this.queryParams.compId).then(response => {
        this.menuData = response.data
        this.defaultShowNodes.push(this.menuData[0].id)
        if(this.queryParams.code===null){
          this.queryParams.code = this.defaultShowNodes[0];
        }
      })
    },

    //点击节点方法
    handleNodeClick(data) {
      this.queryParams.code = data.id;
      this.tableData = []
      this.onLoad()
    },
    //载入数据
    onLoad() {
      this.table.loading = true;//加载状态
      listAccountRules(this.queryParams).then(response => {
        this.total = response.total;
        this.tableData = response.rows;//表格数据
        this.table.loading = false;
        console.log("table",  this.tableData)
        if(this.tableData.length==0){
          const newLine = {
            creator: this.nickName,
            creatorId: this.$store.state.user.name,
            createDate: getDateTime(1),
            apprGroup: null,
            acctCodeD: null,
            efNoD:null,
            isShowno: "0",
          }
          this.tableData.push(newLine)
        }
      });
    },
    /** 添加下级操作 */
    handlechild(row){
      this.reset();
      this.form.parentid = row.id
      this.form.creator = this.nickName;
      this.form.creatorId = this.$store.state.user.name;
      this.form.createDate = getDateTime(1)
      this.open = true;
      this.title = "添加下级窗口";
    },
    addIndex({row, rowIndex}) {
      row.index = rowIndex
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      if (this.tableData.length === row.index + 1) {
        const newLine = {
          creator: this.nickName,
          creatorId: this.$store.state.user.name,
          createDate: getDateTime(1),
          status: "0",
          payType: "2",
          isPostPro: "0",
          isEmpPro: "0",
          payProCode: "",
          parentid: this.queryParams.id,
          isShowno: "0",
        }
        this.index++
        this.tableData.push(newLine)
      }
    },
  }
};
</script>
