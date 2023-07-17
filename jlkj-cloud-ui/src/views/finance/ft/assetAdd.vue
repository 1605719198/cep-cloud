<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="公司" prop="companyId">
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
        <el-input
          v-model="queryParams.changeNo"
          placeholder="请输入单据编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单据状态" prop="status">
        <el-input
          v-model="queryParams.status"
          placeholder="请输入单据状态"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['finance:assetAdd:add']"
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
          v-hasPermi="['finance:assetAdd:edit']"
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
          v-hasPermi="['finance:assetAdd:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="assetAddList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="申请单号" align="center" prop="changeNo" />
      <el-table-column label="变动方式" align="center" prop="changeWay" />
      <el-table-column label="单据名称" align="center" prop="changeName" />
      <el-table-column label="申请人" align="center" prop="applyUser" />
      <el-table-column label="单位" align="center" prop="applyDept" />
      <el-table-column label="变动日期" align="center" prop="changeDate" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:assetAdd:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:assetAdd:remove']"
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

    <!-- 添加或修改资产增加单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-divider content-position="center" direction="horizontal">申请单信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="公司别" prop="companyId">
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
            <el-form-item label="单据名称" prop="changeName">
              <el-input v-model="form.changeName" placeholder="请输入单据名称" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="单据编号" prop="changeNo" >
              {{form.changeNo}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="变动方式" prop="changeWay">
              <el-select v-model="form.changeWay" filterable placeholder="请输入变动方式"  >
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
            <el-form-item label="变动日期" prop="changeDate">
              <el-date-picker clearable  style="width:160px"
                              v-model="form.changeDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择变动日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="单据状态" prop="status">
              {{form.status}}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="申请人" prop="applyUser">
              {{form.applyUser}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="申请日期" prop="applyDate">
              {{form.applyDate}}
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="center" direction="horizontal">资产其他信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="资产分类" prop="assetGroup">
              <el-input v-model="form.assetGroup" placeholder="请输入资产分类" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="资产名称" prop="assetName">
              <el-input v-model="form.assetName" placeholder="请输入资产名称" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="资产编码" prop="assetNo">
              <el-input v-model="form.assetNo" placeholder="请输入资产编码" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="数量" prop="qty">
              <el-input v-model="form.qty" placeholder="请输入数量" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="计量单位" prop="unit">
              <el-input v-model="form.unit" placeholder="请输入计量单位" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="使用状况" prop="assetStatus">
              <el-input v-model="form.assetStatus" placeholder="请输入使用状况" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="产权单位" prop="deptNo">
              <el-input v-model="form.deptNo" placeholder="请输入产权单位" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="成本中心" prop="costCenter">
              <el-input v-model="form.costCenter" placeholder="请输入成本中心" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="经济用途" prop="economyPurpose">
              <el-input v-model="form.economyPurpose" placeholder="请输入经济用途" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="存放地点" prop="location">
              <el-input v-model="form.location" placeholder="请输入存放地点" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="资产规格" prop="assetSpec">
              <el-input v-model="form.assetSpec" placeholder="请输入资产规格" m/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="增加方式" prop="cardModel">
              <el-input v-model="form.cardModel" placeholder="请输入增加方式" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="资产类别" prop="assetType">
              <el-input v-model="form.assetType" placeholder="请输入资产类别" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="供应商" prop="supplyerNo">
              <el-input v-model="form.supplyerNo" placeholder="请输入供应商" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="制造商" prop="manufacturer">
              <el-input v-model="form.manufacturer" placeholder="请输入制造商" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="料号" prop="materialNo">
              <el-input v-model="form.materialNo" placeholder="请输入料号" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="储位" prop="locationNo">
              <el-input v-model="form.locationNo" placeholder="请输入储位" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="交易单号" prop="tradeNo">
              <el-input v-model="form.tradeNo" placeholder="请输入交易单号" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="center" direction="horizontal">财务代码明细资料</el-divider>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="保管人" prop="assetUser">
              <el-input v-model="form.assetUser" placeholder="请输入保管人" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="保管部门" prop="userDept">
              <el-input v-model="form.userDept" placeholder="请输入保管部门" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="工程编码" prop="pgrmId">
              <el-input v-model="form.pgrmId" placeholder="请输入工程编码" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="重量" prop="weight">
              <el-input v-model="form.weight" placeholder="请输入重量" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="容量" prop="capacity">
              <el-input v-model="form.capacity" placeholder="请输入容量" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="长度" prop="length">
              <el-input v-model="form.length" placeholder="请输入长度" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="建筑面积" prop="buildingArea">
              <el-input v-model="form.buildingArea" placeholder="请输入建筑面积" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="占地面积" prop="floorArea">
              <el-input v-model="form.floorArea" placeholder="请输入占地面积" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="厂房层数" prop="layerNum">
              <el-input v-model="form.layerNum" placeholder="请输入厂房层数" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="center" direction="horizontal">资产原值及折旧信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="原值" prop="orgValue">
              <el-input v-model="form.orgValue" placeholder="请输入原值" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="折旧年限" prop="deprCount">
              <el-input v-model="form.deprCount" placeholder="请输入折旧年限" />
            </el-form-item>
          </el-col>
        </el-row>


        <el-divider content-position="center">附属设备信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddFinanceFtChangeDetail">增行</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteFinanceFtChangeDetail">删除</el-button>
          </el-col>
        </el-row>

        <el-table :data="financeFtChangeDetailList" :row-class-name="rowFinanceFtChangeDetailIndex" @selection-change="handleFinanceFtChangeDetailSelectionChange" ref="financeFtChangeDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="附属设备编码" prop="deviceNo" width="200">
            <template slot-scope="scope">
              <el-input v-model="scope.row.deviceNo" placeholder="请输入附属设备编码" />
            </template>
          </el-table-column>
          <el-table-column label="附属设备名称" prop="deviceName" width="250">
            <template slot-scope="scope">
              <el-input v-model="scope.row.deviceName" placeholder="请输入附属设备名称" />
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="deviceRemark" width="450">
            <template slot-scope="scope">
              <el-input v-model="scope.row.deviceRemark" placeholder="请输入备注" />
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
import { listAssetAdd, getAssetAdd, delAssetAdd, addAssetAdd, updateAssetAdd } from "@/api/finance/ft/assetAdd";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {queryAllUser} from "@/api/system/user";
import {selectChangeTypeList} from "@/api/finance/ft/changeType";

export default {
  name: "AssetAdd",
  data() {
    return {
      // 变动方式下拉选单
      changeWayList: [],
      // 会计公司下拉选单
      companyList: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
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
      // 资产增加单表格数据
      assetAddList: [],
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
        status: null,
      },
      // 查询变动方式参数
      queryChangeWayParams: {
        billNature: "A2",
        companyId: "J00",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getCompanyList();
    this.getList();
  },
  beforeMount() {
    this.getChangeTypeList();
    // /** 装载人员信息 */
    // queryAllUser().then(response => {
    //   this.resUserDiction = response.rows;
    // })
  },
  methods: {
    /** 查询资产增加单列表 */
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
      listAssetAdd(this.queryParams).then(response => {
        this.assetAddList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        changeType: 'A',
        changeWay: null,
        billNature: null,
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
        updateTime: null
      };
      this.financeFtChangeDetailList = [];
      this.resetForm("form");
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加资产增加单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const uuid = row.uuid || this.ids
      getAssetAdd(uuid).then(response => {
        this.form = response.data;
        this.financeFtChangeDetailList = response.data.financeFtChangeDetailList;
        this.open = true;
        this.title = "修改资产增加单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.financeFtChangeDetailList = this.financeFtChangeDetailList;
          if (this.form.uuid != null) {
            updateAssetAdd(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAssetAdd(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除资产增加单编号为"' + uuids + '"的数据项？').then(function() {
        return delAssetAdd(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
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
      obj.assetGroup = "";
      obj.newAssetGroup = "";
      obj.assetName = "";
      obj.newAssetName = "";
      obj.assetDesc = "";
      obj.assetType = "";
      obj.orgValue = "";
      obj.getValue = "";
      obj.accuDepr = "";
      obj.deprCount = "";
      obj.newDeprCount = "";
      obj.deptNo = "";
      obj.newDeptNo = "";
      obj.assetUser = "";
      obj.newAssetUser = "";
      obj.userDept = "";
      obj.newUserDept = "";
      obj.qty = "";
      obj.newQty = "";
      obj.unit = "";
      obj.newUnit = "";
      obj.assetStatus = "";
      obj.newAssetStatus = "";
      obj.location = "";
      obj.newLocation = "";
      obj.assetSpec = "";
      obj.newAssetSpec = "";
      obj.contractNo = "";
      obj.budgetNo = "";
      obj.supplyerNo = "";
      obj.newSupplyerNo = "";
      obj.manufacturer = "";
      obj.newManufacturer = "";
      obj.weight = "";
      obj.newWeight = "";
      obj.capacity = "";
      obj.newCapacity = "";
      obj.length = "";
      obj.newLength = "";
      obj.buildingArea = "";
      obj.newBuildingArea = "";
      obj.floorArea = "";
      obj.newFloorArea = "";
      obj.layerNum = "";
      obj.newLayerNum = "";
      obj.economyPurpose = "";
      obj.source = "";
      obj.newSource = "";
      obj.character = "";
      obj.newCharacter = "";
      obj.levels = "";
      obj.newLevels = "";
      obj.materialNo = "";
      obj.locationNo = "";
      obj.tradeNo = "";
      obj.cardModel = "";
      obj.newCardModel = "";
      obj.splitType = "";
      obj.splitCount = "";
      obj.sourceId = "";
      obj.pgrmId = "";
      obj.createName = "";
      obj.updateName = "";
      this.financeFtChangeDetailList.push(obj);
    },
    /** 资产变动单主档删除按钮操作 */
    handleDeleteFinanceFtChangeDetail() {
      if (this.checkedFinanceFtChangeDetail.length == 0) {
        this.$modal.msgError("请先选择要删除的资产变动单主档数据");
      } else {
        const financeFtChangeDetailList = this.financeFtChangeDetailList;
        const checkedFinanceFtChangeDetail = this.checkedFinanceFtChangeDetail;
        this.financeFtChangeDetailList = financeFtChangeDetailList.filter(function(item) {
          return checkedFinanceFtChangeDetail.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleFinanceFtChangeDetailSelectionChange(selection) {
      this.checkedFinanceFtChangeDetail = selection.map(item => item.index)
    },
  }
};
</script>
