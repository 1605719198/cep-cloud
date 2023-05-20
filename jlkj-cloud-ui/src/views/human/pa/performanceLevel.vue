<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" :rules="rules" label-width="68px">
      <el-form-item label="公司别" prop="compId">
        <el-select :popper-append-to-body="false" v-model="queryParams.compId" placeholder="请选择公司别" clearable ref="selectCompany">
          <el-option
            v-for="dict in companyList"
            :key="dict.compId"
            :label="dict.companyName"
            :value="dict.compId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="考核周期" prop="perfTypeId" label-width="120px">
        <el-select v-model="queryParams.perfTypeId" placeholder="请选择考核周期" >
          <el-option
            v-for="dict in performanceOptions.PerType"
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
          v-hasPermi="['human:performanceLevel:add']"
        >保存</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['human:performanceLevel:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['human:performanceLevel:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-form ref="form" :model="form" :rules="rules"  v-if="showForm">
      <el-table v-loading="loading" :data="form.levelList" @selection-change="handleSelectionChange" height="67vh" :row-class-name="addIndex" @row-click="addLine">
    <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="绩效等级编码" align="center" prop="perfTierCode">
        <template slot-scope="scope">
          <el-form-item :prop="'levelList.' + scope.$index + '.perfTierCode'" :rules="rules.perfTierCode">
            <el-input  v-model="scope.row.perfTierCode" placeholder="请输入绩效等级编码" />
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column label="绩效等级名称" align="center" prop="perfIterName">
        <template slot-scope="scope">
          <el-form-item :prop="'levelList.' + scope.$index + '.perfIterName'" :rules="rules.perfIterName">
            <el-input  v-model="scope.row.perfIterName" placeholder="请输入绩效等级名称" />
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column label="绩效系数" align="center" prop="perfCoefficient">
        <template slot-scope="scope">
          <el-form-item :prop="'levelList.' + scope.$index + '.perfIterName'" :rules="rules.perfCoefficient">
            <el-input  v-model="scope.row.perfCoefficient" placeholder="请输入绩效系数" />
          </el-form-item>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="remark" prop="perfCoefficient">
        <template v-slot="scope">
          <el-input v-model="scope.row.remark" placeholder="请输入备注"></el-input>
        </template>
      </el-table-column>
      <el-table-column label="新增人" align="center" prop="creatorName" />
      <el-table-column label="新增时间" align="center" prop="creatdate" />
    </el-table>
    </el-form>
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
import { listPerformanceLevel, delPerformanceLevel, addPerformanceLevel } from "@/api/human/pa/performanceLevel";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getDateTime} from "@/api/human/hd/ahumanUtils";
import { getPerformanceOptions } from "@/api/human/pa/basis";
import {listYearAmt} from "@/api/human/hs/yearAmt";
export default {
  name: "PerformanceLevel",
  data() {
    return {
      //表格属性
      table: {
        border: true,
        loading: false,
      },
      //出勤选单类型查询
      performanceOptionType:{
        id:'',
        optionsType:['PerType']
      },
      multipleSelection: [],
      //出勤选单选项列表
      performanceOptions:{},
      //列表数据
      tableData: [],
      companyList:[],
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
      // 绩效系数设定表格数据
      levelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        perfTypeId: null,
      },

      // 表单参数
      form: {
        compId: null,
        perfTypeId: null,
        levelList: [],
      },
      // 表单校验
      rules: {
        compId: [
          { required: true, message: "公司不能为空", trigger: "blur" }
        ],
        perfTypeId: [
          { required: true, message: "考核周期不能为空", trigger: "blur" }
        ],
        perfTierCode:[
          {required: true, message: '请输入绩效等级编码', trigger: 'blur'},
        ],
        perfIterName:[
          {required: true, message: '请输入绩效等级名称', trigger: 'blur'},
        ],
        perfCoefficient:[
          {required: true, message: '请输入绩效系数', trigger: 'blur'},
        ],
      },
      //登录人信息
      user: {},
      //是否显示表单
      showForm:true,
    };
  },
  created() {
    this.initData();
    this.getDisc();
    this.loading = false;
    //this.addLine();
    this.getCompanyList();
  },
  methods: {
    getDisc(){
      getPerformanceOptions(this.performanceOptionType).then(response=> {
        this.performanceOptions=response.data;
      })
    },
    //初始化数据
    initData(){
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId
    },
    //获取公司列表
    getCompanyList(){
      selectCompany().then(response=>{
        this.companyList = response.data
      })
    },

    getList() {
      this.loading = true;
      listPerformanceLevel(this.queryParams).then(response => {
        this.form.levelList = response.rows;
        this.total = response.total;
        this.loading = false;
        if(this.form.levelList.length==0){
          this.addLine();
        }
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
        compId: null,
        perfTierCode: null,
        perfIterName: null,
        perfTypeId: null,
        perfCoefficient: null,
        remark: null,
        creator: null,
        creatorId: null,
        creatorName: null,
        creatdate: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.$refs["queryForm"].validate(valid => {
        if (valid) {
          this.queryParams.pageNum = 1;
          this.getList();
        }
      });
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.uuid)
      this.single = selection.length !== 1
      this.multiple = !selection.length
      this.multipleSelection = selection;

    },

    /** 保存按钮 */
    handleSave() {
      this.form.compId = this.queryParams.compId;
      this.form.perfTypeId =  this.queryParams.perfTypeId;
    //  if(this.multipleSelection.length()==0){
       // this.$modal.msgError("请至少勾选一笔数据");
     // }
      this.form.levelList =   this.multipleSelection;
console.log(this.form.levelList)
      this.$nextTick(() => {

        this.$refs["form"].validate(valid => {
          if (valid) {
            addPerformanceLevel(this.form).then(response => {
              this.$modal.msgSuccess("保存成功");
              this.open = false;
              this.getList();
            });
          }
        });
      });
    },
    addIndex({row, rowIndex}) {
      row.index = rowIndex
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      if (!row||this.form.levelList.length == row.index + 1) {
        const newLine = {
          uuid : null,
          creator: this.nickName,
          creatorName: this.$store.state.user.name,
          creatdate: getDateTime(1),
        }
        this.index++
        this.form.levelList.push(newLine)
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      debugger
      const ids = row.uuid || this.ids;
      this.$modal.confirm('是否确认删除绩效系数设定编号为"' + ids + '"的数据项？').then(function() {
        return delPerformanceLevel(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('human/performanceLevel/export', {
        ...this.queryParams
      }, `performanceLevel_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
