<template>
  <div style="padding: 0px 10px;">
    <div class="main">
      <div class="avue-crud el-card__body " style="width: 98%;border: 0;">
        <el-row :gutter="20">
          <!-- 左侧选单配置树 -->
          <el-col :span="4" class="left_tree">
            <div>
              <div style="background:#409EFF" align="center">
                缴费地区
              </div>
              <div id="changeColor" v-for="(dict,index) in salaryOptions.SocialSecurity" align="center" @click="changePostName(dict,index)">
                {{dict.dicName}}
              </div>
            </div>
          </el-col>
          <!-- 右侧列表 -->
          <el-col :span="20">
            <div class="avue-crud__search" style="border: 0">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="缴费地区" prop="payAreaId" >
        <el-select v-model="queryParams.payAreaId" placeholder="请选择缴费地区" disabled>
          <el-option
            v-for="dict in salaryOptions.SocialSecurity"
            :key="dict.dicNo"
            :label="dict.dicName"
            :value="dict.dicNo"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-hasPermi="['human:socialSecurityCompany:add']" icon="el-icon-edit" type="primary" size="mini" plain :disabled="multiple" @click="handleSave">保存</el-button>
      </el-form-item>
      <el-form-item>
        <el-button
          type="danger"
          icon="el-icon-delete"
          plain
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['human:socialSecurityCompany:remove']"
        >作废</el-button>
      </el-form-item>
    </el-form>
            </div>


    <el-table v-loading="table.loading" :data="socialSecurityCompanyList" :row-class-name="addIndex" @selection-change="handleSelectionChange" @row-click="addLine">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="公司别" align="center" prop="compId" >
        <template v-slot="scope">
          <el-select v-model="scope.row.compId" placeholder="请选择公司" >
            <el-option
              v-for="dict in companyList"
              :key="dict.deptCode"
              :label="dict.companyName"
              :value="dict.deptCode"
            />
          </el-select>
        </template>
      </el-table-column>
      <el-table-column label="输入人" align="center" prop="creator" />
<!--      <el-table-column label="输入人工号" align="center" prop="creatorNo" />-->
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
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
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
  </template>



<script>
import { listSocialSecurityCompany, addSocialSecurityCompany, delSocialSecurityCompany} from "@/api/human/hs/socialSecurityCompany";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getDateTime} from "@/api/human/hd/ahumanUtils";
import {getSalaryOptions} from "@/api/human/hs/salaryBasis";

export default {
  name: "SocialSecurityCompany",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      //公司列表
      companyList:[],
      multipleSelection: [],
      //登录人姓名
      nickName: null,
      //登录人工号
      userEmpId: null,
      //薪资选单选项列表
      salaryOptions: [],
      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['SocialSecurity'],
        compId:null,
      },
      //表格属性
      table: {
        border: true,
        loading: false,
      },
      // 遮罩层
      loading: false,
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
      // 社保公积金缴费公司维护表格数据
      socialSecurityCompanyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        payAreaId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.initData();
    this.getCompanyList();
    this.getDisc();
  },
  methods: {
    /** 查询公司列表 */
    getCompanyList(){
      selectCompany().then(response =>{
          this.companyList = response.data
        }
      )
    },
    //查询薪资选单
    getDisc(){
      this.salaryOptionType.compId = null
      getSalaryOptions(this.salaryOptionType).then(response=>{
        this.salaryOptions = response.data;
      })
    },
    /** 查询社保公积金缴费公司维护列表 */
    getList() {
      this.table.loading = true;
      listSocialSecurityCompany(this.queryParams).then(response => {
        this.socialSecurityCompanyList = response.rows;
        this.addLine();
        this.total = response.total;
        this.table.loading = false;
      }, error => {
        this.table.loading = false;
        window.console.log(error);
        const newLine = {
          creator: this.nickName,
          creatorId: this.$store.state.user.name,
        }
        this.socialSecurityCompanyList.push(newLine)
      });
    },
    //初始化数据
    initData(){
      this.userEmpId= this.$store.state.user.name;
      this.nickName= this.$store.state.user.userInfo.nickName;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除此数据项？').then(function () {
        return delSocialSecurityCompany(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        payAreaId: null,
        compId: null,
        creator: null,
        creatorNo: null,
        creatorId: null,
        createDate: null
      };
      this.resetForm("form");
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multipleSelection = selection;
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    //点击节点方法
    changePostName(data,index){
      this.queryParams.payAreaId = data.dicNo;
      this.getList()
    },
    /** 保存按钮 */
    handleSave() {
      addSocialSecurityCompany(this.multipleSelection).then(res => {
        this.$modal.msgSuccess("保存成功");
        this.getList();
      })
    },
    //添加行index
    addIndex({row, rowIndex}) {
      row.index = rowIndex
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      if (!row||this.socialSecurityCompanyList.length == row.index + 1) {
        const newLine = {
          uuid: null,
          payAreaId: this.queryParams.payAreaId,
          creator: this.nickName,
          creatorId: this.$store.state.user.name,
          createDate: getDateTime(1),
        }
        this.socialSecurityCompanyList.push(newLine)
      }
    },
  }
};
</script>
<style scoped>
.el-form-item {
  margin-bottom: 11px;
}
.aa >>> .el-form-item__content {
  margin-left:0px !important;
}
#changeColor:hover{
  background-color: #F2F6FC;
}
.el-scrollbar__wrap{
  overflow-x: hidden;
}
.el-scrollbar__bar.is-horizontal {
  display: none;
}
</style>
