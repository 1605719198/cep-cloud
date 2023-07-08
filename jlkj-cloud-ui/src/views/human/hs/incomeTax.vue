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
          v-hasPermi="['human:incomeTax:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['human:incomeTax:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['human:incomeTax:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="incomeTaxList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="生效日期" align="center" prop="effectDate" width="180">
      <template slot-scope="scope">
          <span>{{ parseTime(scope.row.effectDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="国籍" align="center" prop="empTypeId">
        <template v-slot="scope">
          <dict-tag-human :options="salaryOptions.AchievementType" :value="scope.row.empTypeId"/>
        </template>
      </el-table-column>
      <el-table-column label="月扣除数" align="center" prop="deductNum" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="输入人" align="center" prop="creatorName" />
      <el-table-column label="输入日期" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['human:incomeTax:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['human:incomeTax:remove']"
          >删除</el-button>
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

    <!-- 添加或修改各公司所得税起征点设定对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司" prop="compId">
              {{this.form.compName}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="版本" prop="version">
              <el-input v-model="form.version" disabled size="small" style="width:220px"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="生效日期" prop="effectDate">
              <el-date-picker clearable
                              v-model="form.effectDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择生效日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="输入人" prop="creator">
                {{form.creatorName}}
              </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="输入日期" prop="createDate">
              {{form.createDate}}
            </el-form-item>
          </el-col>
          <el-col :span="12">

          </el-col>
        </el-row>
        <el-divider content-position="center">扣除明细设定</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddIncomeTaxDetail">添加
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteIncomeTaxDetail">删除
            </el-button>
          </el-col>
        </el-row>
        <el-table :data="incometaxDetailListList" :row-class-name="rowIncomTaxIndex"
                  @selection-change="handleIncomeTaxSelectionChange" >
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="国籍" prop="empTypeId" align="center">

            <template v-slot="scope">
              <el-select v-model="scope.row.empTypeId" placeholder="请选择国籍" clearable size="small">
              <el-option
                v-for="dict in salaryOptions.EmpIdType"
                :key="dict.dicNo"
                :label="dict.dicName"
                :value="dict.dicNo"
              />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="月扣除数" prop="deductNum" align="center">
            <template slot-scope="scope">
              <el-input-number v-model="scope.row.deductNum" align="center" placeholder="请输入月扣除数" />
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="remark" align="center">
            <template v-slot="scope">
              <el-input v-model="scope.row.remark" placeholder="请输入备注" size="small"/>
            </template>
          </el-table-column>
        </el-table>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listIncomeTax, getIncomeTax, delIncomeTax, addIncomeTax, updateIncomeTax,selectVersion} from "@/api/human/hs/incomeTax";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getSalaryOptions} from "@/api/human/hs/salaryBasis";
import {getDateTime} from "@/api/human/hd/ahumanUtils";
import DictTagHuman from "@/views/components/human/dictTag/humanBaseInfo";

export default {
  name: "IncomeTax",
  components: { DictTagHuman },
  data() {
    return {

      //薪资选单类型查询
      salaryOptionType: {
        id: '',
        optionsType: ['AchievementType'],
        compId:null,
      },
      //薪资选单选项列表
      salaryOptions: [],
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
      //登录用户信息
      user:{},
      //公司数据
      companyList: [],
      //版本号数据
      versionList: [],
      // 各公司所得税起征点设定表格数据
      incomeTaxList: [],
      //明细数据表格
      incometaxDetailListList: [],

      checkedincometaxDetail: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
        deductNum: null,
        version: null,
      },
      // 表单参数
      form: {
        incometaxDetailListList : [],
      },
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getCompanyList();
    this.initData();
    this.getDisc();
    this.getVersionList();

  },
  methods: {
    //查询薪资选单
    getDisc(){
      this.salaryOptionType.compId = this.queryParams.compId;
     // console.log("salaryOptionTyp", this.salaryOptionType.compId)
      getSalaryOptions(this.salaryOptionType).then(response=>{
        this.salaryOptions = response.data;
      })
    },
    /** 查询各公司所得税起征点设定列表 */
    getList() {
      this.loading = true;
      listIncomeTax(this.queryParams).then(response => {
        this.incomeTaxList = response.rows;
        this.companyName = this.$refs.selectCompany.selectedLabel
        this.total = response.total;
        this.loading = false;
      });
    },

    //获取公司列表
    getCompanyList() {
      selectCompany().then(response => {
        this.companyList = response.data
      })
    },
    //获取公司列表
    getVersionList() {
      console.log("ddddd",this.queryParams.compId)
      selectVersion(this.queryParams.compId).then(response => {
        this.versionList = response.data
      })
    },
    changeCompId(){
      // 切换公司别，重新查询版本号
      this.getVersionList()
      this.getDisc();
    },
    //初始化数据
    initData(){
      this.user.empNo = this.$store.state.user.userInfo.userName;
      this.user.empId = this.$store.state.user.userInfo.userId;
      this.user.empName = this.$store.state.user.userInfo.nickName;
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        uuid: null,
        compId: null,
        compName: null,
        empTypeId: null,
        deductNum: null,
        remark: null,
        effectDate: null,
        version: null,
        resvAttr1: null,
        resvAttr2: null,
        creator: null,
        creatorId: null,
        creatorName: null,
        createDate: null,

      };
      this.incometaxDetailListList = [],
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
      this.ids = selection.map(item => item.uuid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.setForm(0);
      this.open = true;
      this.title = "添加各公司所得税起征点设定";
    },

    //表单值设置
    setForm(e){
      this.form.creatorName = this.user.empName;
      this.form.creator = this.user.empNo;
      this.form.creatorId = this.user.empId;
      this.form.createDate = getDateTime(0);

      if(e===0){
        this.form.compId = this.queryParams.compId;
        this.form.compName = this.$refs.selectCompany.selectedLabel;
      }
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.setForm(0);
      const uuid = row.uuid || this.ids
      getIncomeTax(uuid).then(response => {
      this.form = response.data;
      this.incometaxDetailListList = response.data.incometaxDetailListList;
      this.form.compName = this.$refs.selectCompany.selectedLabel;
      this.open = true;
      this.title = "修改各公司所得税起征点设定";
      });
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const uuids = row.uuid || this.ids;
      this.$modal.confirm('是否确认删除数据项？').then(function() {
        return delIncomeTax(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

    /** 扣除明细序号 */
    rowIncomTaxIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    /** 扣除记录明细添加按钮操作 */
    handleAddIncomeTaxDetail() {
      debugger
      let obj = {};
      obj.empTypeId = "";
      obj.deductNum = "0";
      obj.remark = "";
      this.incometaxDetailListList.push(obj);
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.incometaxDetailListList = this.incometaxDetailListList;
          if (this.form.uuid != null) {
            updateIncomeTax(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addIncomeTax(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 复选框选中数据 */
    handleIncomeTaxSelectionChange(selection) {
      this.checkedincometaxDetail = selection.map(item => item.index)
    },
    /** 扣除记录明细删除按钮操作 */
    handleDeleteIncomeTaxDetail() {
      if (this.checkedincometaxDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的疫苗记录明细数据");
      } else {
        const incometaxDetailListList = this.incometaxDetailListList;
        const checkedincometaxDetail = this.checkedincometaxDetail;
        this.incometaxDetailListList = incometaxDetailListList.filter(function (item) {
          return checkedincometaxDetail.indexOf(item.index) == -1
        });
        console.log("删除后incometaxDetailListList", this.incometaxDetailListList)
      }
    },


  }
};
</script>
