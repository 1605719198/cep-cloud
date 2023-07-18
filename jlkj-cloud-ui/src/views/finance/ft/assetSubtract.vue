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
          v-hasPermi="['finance:assetSubtract:add']"
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
          v-hasPermi="['finance:assetSubtract:edit']"
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
          v-hasPermi="['finance:assetSubtract:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="assetSubtractList" @selection-change="handleSelectionChange">
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
            v-hasPermi="['finance:assetSubtract:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:assetSubtract:remove']"
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

    <!-- 添加或修改资产减少单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
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
            <el-form-item label="变动原因描述" prop="changeDesc">
              <el-input v-model="form.changeDesc" placeholder="请输入变动原因描述" />
            </el-form-item>
          </el-col>
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

        <el-divider content-position="center" direction="horizontal">资产信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="资产编码" prop="assetNo">
              <el-input v-model="form.assetNo" placeholder="请输入资产编码" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="资产名称" prop="assetName">
              <el-input v-model="form.assetName" placeholder="请输入资产名称" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="规格型号" prop="assetSpec" >
              <el-input v-model="form.assetSpec" placeholder="请输入规格型号" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="异动数量" prop="newQty">
              <el-input v-model="form.newQty" placeholder="请输入异动数量" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="数量" prop="qty">
              <el-input v-model="form.qty" placeholder="请输入数量" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="产权单位" prop="deptNo">
              <el-input v-model="form.deptNo" placeholder="请输入产权单位" />
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
            <el-form-item label="存放地点" prop="location">
              <el-input v-model="form.location" placeholder="请输入存放地点" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="重量" prop="weight">
              <el-input v-model="form.weight" placeholder="请输入重量" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="原值" prop="orgValue">
              <el-input v-model="form.orgValue" placeholder="请输入原值" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="净值" prop="getValue">
              <el-input v-model="form.getValue" placeholder="请输入净值" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="累计折旧" prop="accuDepr">
              <el-input v-model="form.accuDepr" placeholder="请输入累计折旧" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="折旧年限" prop="deprCount">
              <el-input v-model="form.deprCount" placeholder="请输入折旧年限" />
            </el-form-item>
          </el-col>
<!--          <el-col :span="8">-->
<!--            <el-form-item label="已使用月数*" prop="location">-->
<!--              <el-input v-model="form.location" placeholder="请输入净值" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--          <el-col :span="8">-->
<!--            <el-form-item label="实物审核人*" prop="accuDepr">-->
<!--              <el-input v-model="form.accuDepr" placeholder="请输入累计折旧" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
        </el-row>

<!--        <el-row :gutter="20">-->
<!--          <el-col :span="8">-->
<!--            <<el-form-item label="财务审核人" prop="deprCount">-->
<!--            <el-input v-model="form.deprCount" placeholder="请输入折旧年限" />-->
<!--          </el-form-item>-->
<!--          </el-col>-->
<!--          <el-col :span="8">-->
<!--            <el-form-item label="财务审核时间" prop="location">-->
<!--              <el-input v-model="form.location" placeholder="请输入净值" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--          <el-col :span="8">-->
<!--            <el-form-item label="实物审核时间" prop="accuDepr">-->
<!--              <el-input v-model="form.accuDepr" placeholder="请输入累计折旧" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--        </el-row>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAssetSubtract, getAssetSubtract, delAssetSubtract, addAssetSubtract, updateAssetSubtract } from "@/api/finance/ft/assetSubtract";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import {selectChangeTypeList} from "@/api/finance/ft/changeType";

export default {
  name: "AssetSubtract",
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
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 资产减少单表格数据
      assetSubtractList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 单据状态时间范围
      daterangeChangeDate: [],
      // 单据状态时间范围
      daterangePeriod: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: null,
        changeNo: null,
        status: null,
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
    /** 查询资产减少单列表 */
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
      listAssetSubtract(this.queryParams).then(response => {
        this.assetSubtractList = response.rows;
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
        changeType: null,
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
      this.title = "添加资产减少单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const uuid = row.uuid || this.ids
      getAssetSubtract(uuid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改资产减少单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uuid != null) {
            updateAssetSubtract(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAssetSubtract(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除资产减少单编号为"' + uuids + '"的数据项？').then(function() {
        return delAssetSubtract(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('finance/assetSubtract/export', {
        ...this.queryParams
      }, `assetSubtract_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
