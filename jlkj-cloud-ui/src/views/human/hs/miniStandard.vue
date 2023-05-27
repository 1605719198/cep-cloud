<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select v-model="queryParams.compId" placeholder="请选择公司别" ref="selectCompany" @change="changeCompId()">
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="版本号" prop="version">
        <el-select v-model="queryParams.version" placeholder="请选择版本号"   >
          <el-option
            v-for="dict in versionList"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="生效日期" prop="effectDate">
        <el-date-picker
          v-model="queryParams.effectDate"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择日期"
          @change="dateFormat">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['human:personalIncomeTax:add']"
          type="primary"
          size="mini"
          plain
          @click="handleSave"
        >保存</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['human:miniStandard:remove']"
        >作废</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>



    <el-table v-loading="loading" :data="miniStandardList" @selection-change="handleSelectionChange" :row-class-name="addIndex" @row-click="addLine">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工资标准类型" align="center" prop="salaryStandard" >
      <template v-slot="scope">
        <el-select v-model="scope.row.salaryStandard" >
          <el-option
            v-for="dict in salaryOptions.salaryBasis"
            :key="dict.dicNo"
            :label="dict.dicName"
            :value="dict.dicNo"
          />
        </el-select>
      </template>
      </el-table-column>
      <el-table-column label="金额" align="center" prop="money" width="220">
        <template v-slot="scope">
          <el-input v-model="scope.row.money" placeholder="请输入内容"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="生效日期" align="center" prop="effectDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effectDate, '{y}-{m}-{d}') }}</span>
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
import { listMiniStandard, delMiniStandard, addMiniStandard, selectVersion} from "@/api/human/hs/miniStandard";
import {getDateTime} from "@/api/human/hd/ahumanUtils";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getSalaryOptions} from "@/api/human/hs/salaryBasis";


export default {
  name: "MiniStandard",
  dicts: ['sys_classtype'],
  data() {
    return {
      //登录人姓名
      nickName: null,
      multipleSelection: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      //版本号数据
      versionList: [],
      //公司数据
      companyList: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 各公司最低工资标准及生活保障标准设定表格数据
      miniStandardList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      salaryOptions: [],
      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['salaryBasis'],
        compId:null,
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        version: null,
        effectDate: null,
      },
      // 表单参数
      form: {
        miniStandardList: [],
      },
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.initData();
    this.getList();
    this.getCompanyList();
    this.getVersionList();
    this.getDisc();
  },
  methods: {
    /** 查询各公司最低工资标准及生活保障标准设定列表 */
    getList() {
      this.loading = true;
      listMiniStandard(this.queryParams).then(response => {
        this.miniStandardList = response.rows;
        this.companyName = this.$refs.selectCompany.selectedLabel
        this.total = response.total;
        this.loading = false;
      });
    },
    //初始化数据
    initData(){
      this.nickName= this.$store.state.user.userInfo.nickName;
    },
    // 表单重置
    reset() {
      this.form = {
        uuid: null,
        compId: null,
        version: null,
        salaryStandard: null,
        money: null,
        effectDate: null,
        resvAttr1: null,
        resvAttr2: null,
        creator: null,
        creatorId: null,
        createDate: null
      };
      this.incometaxDetailListList = [],
      this.resetForm("form");
    },
    addIndex({row, rowIndex}) {
      row.index = rowIndex
    },
    /** 日期查询变更*/
    dateFormat(val){
      this.queryParams.date =this.queryParams.effectDate;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.uuid)
      this.multipleSelection = selection;
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    //查询薪资选单
    getDisc(){
      this.salaryOptionType.compId = "J00"
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
    //获取公司列表
    getVersionList() {
      selectVersion(this.queryParams.compId).then(response => {
        this.versionList = response.data
      })
    },
    changeCompId(){
      // 切换公司别，重新查询版本号
      this.queryParams.version = null;
      this.queryParams.effectDate = null;
      this.queryParams.date = null;
      this.getVersionList()
    },
    /** 保存按钮 */
    handleSave() {
      if(this.queryParams.effectDate > getDateTime(1)) {
        for(let i = 0; i < this.multipleSelection.length; i++) {
          this.multipleSelection[i].effectDate = this.queryParams.effectDate
        }
        addMiniStandard(this.multipleSelection).then(res => {
          this.$modal.msgSuccess("保存成功");
          this.getList();
        })
      }else{
        this.$modal.msgError("生效日期必须大于当前日期");
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uuids = row.uuid || this.ids;
      this.$modal.confirm('是否确认删除各公司最低工资标准及生活保障标准设定编号为"' + uuids + '"的数据项？').then(function() {
        return delMiniStandard(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      if (this.queryParams.compId != null) {
        if (this.queryParams.effectDate != null) {
          if (this.miniStandardList.length == row.index + 1) {
            const newLine = {
              uuid: null,
              creator: this.nickName,
              creatorId: this.$store.state.user.name,
              createDate: getDateTime(1),
              effectDate: this.queryParams.effectDate,
              compId: this.queryParams.compId,
            }
            this.index++
            this.miniStandardList.push(newLine)
          }
        } else {
          this.$modal.msgError("生效日期不能为空");
        }
      }else {
        this.$modal.msgError("公司别不能为空");
      }
    },
  }
};
</script>
