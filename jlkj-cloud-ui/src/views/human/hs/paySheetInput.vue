<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司" :popper-append-to-body="false">
          <el-option
            v-for="dict in companyList"
            :key="dict.deptCode"
            :label="dict.companyName"
            :value="dict.deptCode"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button v-hasPermi="['human:paySheetInput:add']" icon="el-icon-edit" type="primary" size="mini" plain :disabled="multiple" @click="handleSave">保存</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="paySheetInputList" :row-class-name="addIndex" @selection-change="handleSelectionChange" @row-click="addLine">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="薪等" align="center" prop="payTier" >
      <template v-slot="scope">
        <el-input v-model="scope.row.payTier" placeholder="请输入内容"></el-input>
      </template>
      </el-table-column>
      <el-table-column label="最低值" align="center" prop="minMon" >
        <template v-slot="scope">
          <el-input v-model="scope.row.minMon" placeholder="请输入内容"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="中值" align="center" prop="midMon" >
        <template v-slot="scope">
          <el-input v-model="scope.row.midMon" placeholder="请输入内容"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="最高值" align="center" prop="maxMon" >
        <template v-slot="scope">
          <el-input v-model="scope.row.maxMon" placeholder="请输入内容"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" >
        <template v-slot="scope">
          <el-radio-group v-model="scope.row.status" >
            <el-radio
              v-for="dict in salaryOptions.status"
              :key="dict.dicNo"
              :label="dict.dicNo"
            >{{dict.dicName}}</el-radio>
          </el-radio-group>
        </template>
      </el-table-column>
      <el-table-column label="输入人" align="center" prop="creator" />
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
  </div>
</template>

<script>
import { listPaySheetInput, addPaySheetInput, } from "@/api/human/hs/paySheetInput";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getDateTime} from "@/api/human/hd/ahumanUtils";
import {getSalaryOptions} from "@/api/human/hs/salaryBasis";

export default {
  name: "PaySheetInput",
  dicts: ['sys_classtype'],
  data() {
    return {
      //薪资选单选项列表
      salaryOptions: [],
      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['status'],
        compId:null,
      },
      //登录人姓名
      nickName: null,
      //公司列表
      companyList:[],
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
      // 各公司薪给输入表格数据
      paySheetInputList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      multipleSelection: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: this.$store.state.user.userInfo.compId,
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
    this.addLine();
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
    /** 查询各公司薪给输入列表 */
    getList() {
      this.loading = true;
      listPaySheetInput(this.queryParams).then(response => {
        this.paySheetInputList = response.rows;
        this.total = response.total;
        if(this.paySheetInputList.length===0){
          this.addLine();
        }
        this.loading = false;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        uuid: null,
        compId: null,
        payTier: null,
        minMon: null,
        maxMon: null,
        midMon: null,
        status: null,
        resvAttr1: null,
        resvAttr2: null,
        creator: null,
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
    /** 保存按钮 */
    handleSave() {
      addPaySheetInput(this.multipleSelection).then(res => {
        this.$modal.msgSuccess("保存成功");
        this.getList();
      })
    },
    //初始化数据
    initData(){
      this.userEmpId= this.$store.state.user.name;
      this.nickName= this.$store.state.user.userInfo.nickName;
      this.logincompId= this.$store.state.user.userInfo.compId;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.uuid)
      this.multipleSelection = selection;
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      if (!row||this.paySheetInputList.length == row.index + 1) {
        const newLine = {
          uuid: null,
          creator: this.nickName,
          creatorId: this.$store.state.user.name,
          createDate: getDateTime(1),
          status: "1",
          compId: this.queryParams.compId,
        }
        this.index++
        this.paySheetInputList.push(newLine)
      }
    },
    addIndex({row, rowIndex}) {
      row.index = rowIndex
    },
  }
};
</script>
