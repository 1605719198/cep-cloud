<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-row>
        <el-col :span="20">
          <el-form-item prop="type">
            <el-radio-group v-model="queryParams.type" @input="changePostName()">
              <el-radio
                v-for="dict in salaryOptions.residentType"
                :key="dict.dicNo"
                :label="dict.dicNo"
              >{{dict.dicName}}</el-radio>
            </el-radio-group>
          </el-form-item>
        <el-form-item label="版本号" prop="versionNo">
          <el-select v-model="queryParams.versionNo" placeholder="请选择版本号">
              <el-option
                v-for="dict in versionList"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
          </el-select>
        </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-hasPermi="['human:personalIncomeTax:add']" type="primary" size="mini" plain  @click="handleSave">保存</el-button>
      </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="20">
      <el-form-item label="生效日期" prop="effectDate">
        <el-date-picker
          v-model="queryParams.effectDate"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择日期"
          @change="dateFormat">
        </el-date-picker>
      </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-table v-loading="loading" :data="personalIncomeTaxList" :row-class-name="addIndex" @selection-change="handleSelectionChange" @row-click="addLine">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="所得税级距" align="center" prop="taxSpace" >
        <template v-slot="scope">
          <el-input v-model="scope.row.taxSpace" placeholder="请输入内容"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="税率" align="center" prop="taxRate" >
        <template v-slot="scope">
          <el-input v-model="scope.row.taxRate" placeholder="请输入内容"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="速算扣除数" align="center" prop="deductNum" >
        <template v-slot="scope">
          <el-input v-model="scope.row.deductNum" placeholder="请输入内容"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="生效日期" align="center" prop="effectDate" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effectDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="输入人" align="center" prop="creator" />
      <el-table-column label="输入日期" align="center" prop="createDate" >
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
  </div>
</template>

<script>

import { listPersonalIncomeTax, addPersonalIncomeTax, selectVersion } from "@/api/human/hs/personalIncomeTax";
import {getDateTime} from "@/api/human/hd/ahumanUtils";
import {getSalaryOptions} from "@/api/human/hs/salaryBasis";

export default {
  name: "PersonalIncomeTax",
  data() {
    return {
      //版本号数据
      versionList: [],
      //薪资选单选项列表
      salaryOptions: [],
      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['residentType'],
        compId:null,
      },
      //登录人姓名
      nickName: null,
      radio: '1',
      multipleSelection: [],
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
      // 个人所得税比例维护表格数据
      personalIncomeTaxList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        date: null,
        type: null,
        versionNo: null,
        effectDate: null,
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
    this.getDisc();
  },
  methods: {
    /** 查询个人所得税比例维护列表 */
    getList() {
      this.loading = true;
      listPersonalIncomeTax(this.queryParams).then(response => {
        this.personalIncomeTaxList = response.rows;
        this.addLine();
        this.total = response.total;
        this.loading = false;
      });
    },
    //获取类别列表
    getVersionList() {
      console.log("ddddd",this.queryParams.type)
      selectVersion(this.queryParams.type).then(response => {
        this.versionList = response.data
      })
    },
    //单选切换
    changePostName(){
      this.queryParams.versionNo = null;
      this.queryParams.effectDate = null;
      this.queryParams.date = null;
      this.getVersionList()

      // this.personalIncomeTaxList = []
      // this.handleQuery();
    },
    //查询薪资选单
    getDisc(){
      this.salaryOptionType.compId = null
      getSalaryOptions(this.salaryOptionType).then(response=>{
        this.salaryOptions = response.data;
      })
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        taxSpace: null,
        taxRate: null,
        deductNum: null,
        versionNo: null,
        effectDate: null,
        type: null,
        creator: null,
        createDate: null,
      };
      this.resetForm("form");
    },
    //初始化数据
    initData(){
      this.userEmpId= this.$store.state.user.name;
      this.nickName= this.$store.state.user.userInfo.nickName;
      this.logincompId= this.$store.state.user.userInfo.compId;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multipleSelection = selection;
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 日期查询变更*/
    dateFormat(val){
      this.queryParams.date =this.queryParams.effectDate;
    },
    /** 保存按钮 */
    handleSave() {
      if(this.queryParams.effectDate > getDateTime(1)) {
        for (let i = 0; i < this.multipleSelection.length; i++) {
          this.multipleSelection[i].effectDate = this.queryParams.effectDate
        }
        addPersonalIncomeTax(this.multipleSelection).then(res => {
          this.$modal.msgSuccess("保存成功");
          this.getList();
        })
      }else{
          this.$modal.msgError("生效日期必须大于当前日期");
        }
    },
    addIndex({row, rowIndex}) {
      row.index = rowIndex
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      if (!row||this.personalIncomeTaxList.length == row.index + 1) {
        const newLine = {
          id: null,
          creator: this.nickName,
          creatorId: this.$store.state.user.name,
          createDate: getDateTime(1),
          parentid: this.queryParams.id,
          type: this.queryParams.type,
          effectDate: this.queryParams.effectDate,
        }
        this.personalIncomeTaxList.push(newLine)
      }
    },
  }
};
</script>
