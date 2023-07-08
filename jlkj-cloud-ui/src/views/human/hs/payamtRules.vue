<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别" ref="selectCompany">
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="薪资项目" prop="code">
        <el-select v-model="queryParams.code" placeholder="请选择薪资项目" clearable size="small">
          <el-option
            v-for="dict in salaryOptions.payamtProject"
            :key="dict.dicNo"
            :label="dict.dicName"
            :value="dict.dicNo"
          />
        </el-select>
      </el-form-item>
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
          @click="handleSave"
          v-hasPermi="['human:payamtRules:add']"
        >保存</el-button>
      </el-col>
      <el-col :span="1.5">

        <el-button
          type="danger"
          plain
          size="mini"
          @click="handleDelete"
          v-hasPermi="['human:payamtRules:add']"
        >删除</el-button>

      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>


    <el-table v-loading="loading" :data="payamtRulesList" :row-class-name="rowTCapitalDetailIndex" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="薪资项目" align="center" prop="infoname" />
      <el-table-column label="借方会计科目" align="center" prop="acctcoded">
        <template slot-scope="scope">
          <el-input v-model="scope.row.acctcoded" placeholder="请输入借方会计科目" disabled>
            <el-button slot="append" icon="el-icon-search" @click="inputAcctName(scope.row,'coded')"
            ></el-button>
          </el-input>
        </template>
      </el-table-column>
      <el-table-column label="借方户号" align="center" prop="idcoded" >
        <template v-slot="scope">
          <el-input v-model="scope.row.idcoded"  disabled></el-input>
        </template>
      </el-table-column>
      <el-table-column label="借方参号" align="center" prop="efnod" >
        <template v-slot="scope">
          <el-input v-model="scope.row.efnod"  disabled></el-input>
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
      <el-table-column label="贷方会计科目" align="center" prop="acctcodec">
        <template slot-scope="scope">
          <el-input v-model="scope.row.acctcodec" placeholder="请输入贷方会计科目" disabled>
            <el-button slot="append" icon="el-icon-search" @click="inputAcctName(scope.row,'codec')"
            ></el-button>
          </el-input>
        </template>
      </el-table-column>
      <el-table-column label="贷方户号" align="center" prop="idcodec" >
        <template v-slot="scope">
          <el-input v-model="scope.row.idcodec"  disabled></el-input>
        </template>
      </el-table-column>
      <el-table-column label="贷方参考号" align="center" prop="refnoc" >
        <template v-slot="scope">
          <el-input v-model="scope.row.refnoc"  disabled></el-input>
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
      @pagination="getList"
    />
    <acctcodeCorpPop ref="selectAcctCodeCorpPop" :companyId="this.queryParams.compId" @ok="getAcctCodeCorpPop"/>
  </div>
</template>

<script>
import { listPayamtRules, getPayamtRules, delPayamtRules, addPayamtRules, updatePayamtRules } from "@/api/human/hs/payamtRules";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getSalaryOptions} from "@/api/human/hs/salaryBasis";
import acctcodeCorpPop from "@/views/finance/aa/acctcodeCorpPop";

export default {
  name: "PayamtRules",
  components: {
    acctcodeCorpPop
  },
  data() {
    return {
      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['payamtProject'],
        compId:null,
      },
      //薪资选单选项列表
      salaryOptions: [],
      // 遮罩层
      loading: true,
      //登录人信息
      user: {},
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
      // 应付抛帐设定表格数据
      payamtRulesList: [],
      companyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      multipleSelection: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        code: null,
        apprgroup: null,
        infocode: null,
        infoname: null,
        acctcoded: null,
        efnod: null,
        idcoded: null,
        duedated: null,
        acctcodec: null,
        idcodec: null,
        refnoc: null,
        duedatec: null,
        status:null,
        compId: null,
        creatBy: null,
        creatNo: null,
        creatId: null,
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
    this.getList();
  },
  methods: {

    //初始化数据
    initData(){
      console.log("user",this.user)
      this.user.empNo = this.$store.state.user.userInfo.userName;
      this.user.empId = this.$store.state.user.userInfo.userId;
      this.user.empName = this.$store.state.user.userInfo.nickName;
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId
    },
    /** 查询应付抛帐设定列表 */
    getList() {
      this.loading = true;
      listPayamtRules(this.queryParams).then(response => {
        debugger
        this.payamtRulesList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    //查询薪资选单
    getDisc(){
      this.salaryOptionType.compId = this.queryParams.compId;
      getSalaryOptions(this.salaryOptionType).then(response=>{
        this.salaryOptions = response.data;
      })
    },
    //获取公司列表
    getCompanyList() {
      selectCompany().then(response => {
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
        acctcoded: null,
        efnod: null,
        idcoded: null,
        duedated: null,
        acctcodec: null,
        idcodec: null,
        refnoc: null,
        duedatec: null,
        compId: null,
        creatBy: null,
        creatNo: null,
        creatId: null,
        createTime: null
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
      for (let i = 0; i < this.multipleSelection.length; i++) {
        this.multipleSelection[i].compId = this.user.compId
      }
      addPayamtRules(this.multipleSelection).then(res => {
        this.$modal.msgSuccess("保存成功");
        this.getList();
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPayamtRules(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改应付抛帐设定";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePayamtRules(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPayamtRules(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除应付抛帐设定编号为"' + ids + '"的数据项？').then(function() {
        return delPayamtRules(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

    /** 会计科目点击事件 */
    inputAcctName(val,val1) {
      this.tagSrc = val1
      this.indexRow= val.index
      this.$refs.selectAcctCodeCorpPop.show();
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/payamtRules/export', {
        ...this.queryParams
      }, `payamtRules_${new Date().getTime()}.xlsx`)
    },
    getAcctCodeCorpPop(val){
      if( this.tagSrc =='coded'){
        this.payamtRulesList[this.indexRow-1].acctcoded =val[0].acctCode
        this.payamtRulesList[this.indexRow-1].idcoded =val[0].calTypeCodea
        this.payamtRulesList[this.indexRow-1].efnod =val[0].calTypeCodeb
      }
      if( this.tagSrc =='codec') {
        this.payamtRulesList[this.indexRow - 1].acctcodec = val[0].acctCode
        this.payamtRulesList[this.indexRow - 1].idcodec = val[0].calTypeCodea
        this.payamtRulesList[this.indexRow - 1].refnoc = val[0].calTypeCodeb
      }
    },

    /** 序号 */
    rowTCapitalDetailIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },

  }
};
</script>
