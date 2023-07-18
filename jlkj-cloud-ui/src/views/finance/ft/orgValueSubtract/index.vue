<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司别" prop="companyId">
        <el-select v-model="queryParams.companyId" filterable placeholder="请选择公司">
          <el-option
            v-for="item in companyList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="单据编号" prop="changeNo">
        <el-input v-model="queryParams.changeNo" placeholder="请输入单据编号" />
      </el-form-item>
      <el-form-item label="变动日期">
        <el-date-picker
          v-model="daterangeChangeDate"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
          v-hasPermi="['finance:ft:orgValueSubtract:add']"
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
          v-hasPermi="['finance:ft:orgValueSubtract:edit']"
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
          v-hasPermi="['finance:ft:orgValueSubtract:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['finance:ft:orgValueSubtract:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orgValueSubtractList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="单据编号" align="center" prop="changeNo" sortable/>
      <el-table-column label="单据名称" align="center" prop="changeName" :show-overflow-tooltip="true"/>
      <el-table-column label="变动方式" align="center" prop="changeWay" :formatter="changeWayFormat"/>
      <el-table-column label="资产编码" align="center" prop="subAssetNo" />
      <el-table-column label="资产名称" align="center" prop="subAssetName" />
      <el-table-column label="减少金额" align="center" prop="subChangePrice" >
      <el-table-column label="申请人" align="center" prop="applyUser" :formatter="userFormat" sortable/>
      <el-table-column label="变动日期" align="center" prop="changeDate" width="180" sortable>
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.changeDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单据状态" align="center" prop="status" >
        <template v-slot="scope">
          <dict-tag :options="dict.type.ft_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:ft:orgValueSubtract:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:ft:orgValueSubtract:remove']"
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

    <!-- 添加或修改资产变动单主档对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" >
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="公司别" prop="companyId" label-width="80px">
              <el-select v-model="form.companyId" >
                <el-option
                  v-for="item in companyList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="单据名称" prop="changeName" label-width="80px">
              <el-input v-model="form.changeName" placeholder="请输入单据名称" maxlength="100"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="单据编号" prop="changeNo" label-width="80px">
              {{form.changeNo}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="变动方式" prop="changeWay" label-width="80px">
              <el-select v-model="form.changeWay" filterable placeholder="请输入变动方式" label-width="80px" >
                <el-option
                  v-for="item in changeWayList"
                  :key="item.value"
                  :label="item.value+'_'+item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="变动日期" prop="changeDate" label-width="80px">
              <el-date-picker clearable  style="width:160px"
                              v-model="form.changeDate"
                              type="date"
                              :picker-options="pickerOptions"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择变动日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="单据状态" prop="status" label-width="80px">
              <template >
                <dict-tag :options="dict.type.ft_status" :value="form.status"/>
              </template>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="变动原因描述" prop="changeDesc" label-width="80px">
              <el-input v-model="form.changeDesc"  type="textarea"  maxlength="200" show-word-limit placeholder="请输入">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="申请人" prop="applyUser" label-width="80px">
              {{this.UserDictFullName(this.resUserDiction,form.applyUser)}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="申请日期" prop="applyDate" label-width="80px">
              <span style="text-align: center;display:block;width: 121px">{{ parseTime(form.applyDate, '{y}-{m}-{d}') }}</span>
            </el-form-item>
          </el-col>
        </el-row>
<!--        <el-divider content-position="center">资产信息</el-divider>-->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="openSelectPop">挑选资产</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteFinanceFtChangeDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="form.financeFtChangeDetailList" :row-class-name="rowFinanceFtChangeDetailIndex" @selection-change="handleFinanceFtChangeDetailSelectionChange"
                  ref="financeFtChangeDetail"  :key="indexKey">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="资产编码" prop="assetNo" width="120" :render-header="addRedstar">
            <template slot-scope="scope">
              <el-input v-model="scope.row.assetNo"  readonly/>
            </template>
          </el-table-column>
          <el-table-column label="资产名称" prop="assetName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.assetName"  readonly/>
            </template>
          </el-table-column>
          <el-table-column label="减少金额" prop="changePrice" width="150" :render-header="addRedstar" >
            <template slot-scope="scope">
              <el-form-item :prop="'financeFtChangeDetailList.' + scope.$index + '.changePrice'" :rules="rules.changePrice" >
                <el-input  type="number" v-model="scope.row.changePrice"  placeholder="请输入"  :min="1" style="width:120px"/>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="原值" prop="orgValue" width="100" >
            <template slot-scope="scope">
              <el-input v-model="scope.row.orgValue" readonly/>
            </template>
          </el-table-column>
          <el-table-column label="数量" prop="qty" width="80" >
            <template slot-scope="scope">
              <el-input v-model="scope.row.qty" readonly/>
            </template>
          </el-table-column>
<!--          <el-table-column label="归属部门" prop="deptNo" width="150" >
            <template slot-scope="scope">
              <el-input v-model="scope.row.deptNo" readonly/>
            </template>
          </el-table-column>-->
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <select-card ref="selectCard" :queryData="this.queryCardParams" @ok="handleQuery"  @chooseData="parentChoose"></select-card>
  </div>
</template>

<script>
import { listOrgValueSubtract, getOrgValueSubtract, delOrgValueSubtract, addOrgValueSubtract, updateOrgValueSubtract } from "@/api/finance/ft/orgValueSubtract";
import { selectCompanyList } from "@/api/finance/aa/companyGroup";
import {getChangeTypeName, selectChangeTypeList} from "@/api/finance/ft/changeType";
import selectCard from "@/views/finance/ft/card/selectCard";
import {queryAllUser,getAvatorByUserName} from "@/api/system/user";
import {getParamValue} from "../../../../api/finance/ft/para";
import {strToDate} from "../../../../utils/util";
export default {
  name: "orgValueSubtract",
  components: { selectCard},
  dicts: ['ft_status'],
  data() {
    return {
      assetMonth :null ,
      //设置日期智能选择指定日期及以后
     //date-picker 加属性:picker-options="pickerOptions"
      pickerOptions:{},
      indexKey:0,
      resUserDiction:[],
      // 会计公司下拉选单
      companyList: [],
      // 变动方式下拉选单
      changeWayList: [],
      // 遮罩层
      loading: true,
      selectCardIds:[],
      // 选中数组
      ids: [],
      changeNos: [],
      // 子表选中数据
      checkedFinanceFtChangeDetail: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 资产变动单主档表格数据
      orgValueSubtractList: [],
      // 资产变动单主档表格数据
      financeFtChangeDetailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 修改时间时间范围
      daterangeChangeDate: [],
      // 修改时间时间范围
      daterangePeriod: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        changeNo: null,
        changeDate: null,
        changeType: "D",
      },
      // 查询资产卡片参数
      queryCardParams: {
        companyId: "J00",
        product: "D",
        isCleared: "0",
        assetId:[],

      },
      // 查询变动方式参数
      queryChangeWayParams: {
        billNature: "B1",
        companyId: "J00",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companyId: [
          { required: true, message: "公司别不能为空", trigger: "change" }
        ],
        changeName: [
          { required: true, message: "单据名称不能为空", trigger: "blur" }
        ],
        changeWay: [
          { required: true, message: "变动方式不能为空", trigger: "change" }
        ],
        changeDate: [
          { required: true, message: "变动日期不能为空", trigger: "change" },
        ],
        changeDesc: [
          { required: true, message: "变动原因描述不能为空", trigger: "blur" }
        ],
        changePrice: [
          { required: true, message: "减少金额不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getCompanyList();
    this.getList();
    this.getAssetMonth();
  },
  beforeMount() {
    this.getChangeTypeList();
    /** 装载人员信息 */
    queryAllUser().then(response => {
      this.resUserDiction = response.rows;
    })
  },
  methods: {
    getAssetMonth(){
        getParamValue("asset_month").then( response => {
          if(undefined!=response){
            this.assetMonth = strToDate(response+"-01","-").getTime();
          }else{
            this.assetMonth = Date.now() ;
          }
          const that = this;
          this.pickerOptions = {
            disabledDate(time) {
              return time.getTime() < that.assetMonth;
            }
          };
        })
    },
    // 获取当前登录用户名称/信息
    getName(){
      getAvatorByUserName(this.$store.state.user.name).then( response => {
        this.form.applyUser=response.data.userName
        this.logincompId=response.data.compId
        this.queryParams.companyId=response.data.compId;
        this.form.companyId=response.data.compId;
      })
    },
    userFormat(row,column){
      return this.UserDictFullName(this.resUserDiction,row.applyUser);

    },
    changeWayFormat(row,column){
      return getChangeTypeName(this.changeWayList,row.changeWay);

    },

    parentChoose(data){
      data.map((item) => {
        this.form.financeFtChangeDetailList.push(item);
        this.selectCardIds.push(item.assetId);
      });
      this.indexKey+=1;
      this.queryCardParams.assetId = this.selectCardIds;
    },
    /** 打开资产卡片挑选弹窗 */
    openSelectPop() {
      this.$refs.selectCard.show();
    },
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
      });
    },
    getChangeTypeList() {
      selectChangeTypeList(this.queryChangeWayParams.companyId,this.queryChangeWayParams.billNature).then(response => {
        this.changeWayList = response;
      });
    },
    /** 查询资产变动单主档列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeChangeDate && '' != this.daterangeChangeDate) {
        this.queryParams.params["beginChangeDate"] = this.daterangeChangeDate[0];
        this.queryParams.params["endChangeDate"] = this.daterangeChangeDate[1];
      }
      if (null != this.daterangePeriod && '' != this.daterangePeriod) {
        this.queryParams.params["beginPeriod"] = this.daterangePeriod[0];
        this.queryParams.params["endPeriod"] = this.daterangePeriod[1];
      }
      listOrgValueSubtract(this.queryParams).then(response => {
        this.orgValueSubtractList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        uuid: null,
        companyId: null,
        changeNo: null,
        changeName: null,
        changeType: "D",
        changeWay: null,
        billNature: "B1",
        changeDate: null,
        changeDesc: null,
        assetType: null,
        applyDept: null,
        newAssetUser: null,
        applyUser: null,
        applyDate: null,
        status: null,
        period: null,
        confirmUser: null,
        confirmDate: null,
        checkUser: null,
        checkDate: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateName: null,
        updateTime: null,
       financeFtChangeDetailList :[]
      };
      this.form.financeFtChangeDetailList = [];
      this.resetForm("form");
      this.queryCardParams.assetId =[];
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeChangeDate = [];
      this.daterangePeriod = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.uuid)
      this.changeNos = selection.map(item => item.changeNo)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加资产原值减少单";
      this.form.companyId = this.queryParams.companyId
      this.form.changeDate = new Date();
      this.form.applyDate = new Date();
      // this.form.status = "00";
      this.getName();
      if(this.changeWayList.length=1){
        this.form.changeWay = this.changeWayList[0].value;
      }
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const uuid = row.uuid || this.ids
      getOrgValueSubtract(uuid).then(response => {
        this.form = response.data;
        this.form.financeFtChangeDetailList = response.data.financeFtChangeDetailList;
        this.open = true;
        this.title = "修改资产原值减少单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uuid != null) {
            updateOrgValueSubtract(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrgValueSubtract(this.form).then(response => {
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
      const uuids = row.uuid || this.ids;
      const changeNos = row.changeNo || this.changeNos;
      this.$modal.confirm('是否确认删除资产变动单编号为"' + changeNos + '"的数据项？').then(function() {
        return delOrgValueSubtract(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
      this.indexKey+=1;
    },
	/** 资产变动单主档序号 */
    rowFinanceFtChangeDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 资产变动单主档添加按钮操作 */
    handleAddFinanceFtChangeDetail() {
      let obj = {};
      obj.companyId = "";
      obj.changeNo = "";
      obj.period = "";
      obj.changePrice = "";
      obj.assetId = "";
      obj.assetNo = "";
      obj.assetName = "";
      obj.orgValue = "";
      obj.getValue = "";
      obj.accuDepr = "";
      obj.deptNo = "";
      obj.assetUser = "";
      obj.userDept = "";
      obj.qty = "";
      this.form.financeFtChangeDetailList.push(obj);
    },
    /** 资产变动单主档删除按钮操作 */
    handleDeleteFinanceFtChangeDetail() {
      if (this.checkedFinanceFtChangeDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的资产数据");
      } else {
        const financeFtChangeDetailList = this.form.financeFtChangeDetailList;
        const checkedFinanceFtChangeDetail = this.checkedFinanceFtChangeDetail;
        this.form.financeFtChangeDetailList = financeFtChangeDetailList.filter(function(item) {
          return checkedFinanceFtChangeDetail.indexOf(item.index) == -1
        });
        this.indexKey+=1;
        this.selectCardIds = [];
        this.form.financeFtChangeDetailList.map((item) => {
          this.selectCardIds.push(item.assetId);
        });
        this.queryCardParams.assetId = this.selectCardIds;
      }
    },
    /** 复选框选中数据 */
    handleFinanceFtChangeDetailSelectionChange(selection) {
      this.checkedFinanceFtChangeDetail = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('change/orgValueSubtract/export', {
        ...this.queryParams
      }, `orgValueSubtract_${new Date().getTime()}.xlsx`)
    },
    /** 表头标记红星*/
    addRedstar(h, {column }){
      return[
        h("span",""+ column.label),
        h("span", {style: "color: red"},"*"),
      ];
    },
  }
};
</script>
