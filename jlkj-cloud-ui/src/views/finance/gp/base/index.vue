<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="104px">
      <el-form-item label="地区码" prop="mainAreaId">
        <el-select v-model="queryParams.mainAreaId" placeholder="请选择地区码" filterable  clearable>
          <el-option
            v-for="dict in dict.type.gp_main_areaid"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="详细地区码" prop="detailAreaId">
        <el-select v-model="queryParams.detailAreaId" placeholder="请选择详细地区码" filterable  clearable style="height: 100%">
          <el-option
            v-for="dict in dict.type.gp_detail_areaid"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="新增加日期" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="dutyDateChange">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="是否更名" prop="originalManufacturerChineseName">
        <el-radio-group v-model="queryParams.originalManufacturerChineseName">
          <el-radio v-for="dict in dict.type.sys_yes_no"
                    :key="dict.value"
                    :label="dict.value">{{dict.label}}</el-radio>
        </el-radio-group>

      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="baseList" @selection-change="handleSelectionChange">
      <el-table-column label="厂商编码" align="center" prop="manufacturerId" />
      <el-table-column label="中文名称" align="center" prop="manufacturerChineseName"  :show-overflow-tooltip='true'/>
      <el-table-column label="英文名称" align="center" prop="manufacturerEnglishName" />
      <el-table-column label="厂商简称" align="center" prop="manufacturerShortName" />
      <el-table-column label="状态" align="center" prop="status" />
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
import { listBase } from "@/api/finance/gp/base1";

export default {
  name: "Base",
  dicts: ['gp_main_areaid', 'gp_detail_areaid','sys_yes_no'],
  data() {
    return {
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
      // 销售管理-厂商基本资料表格数据
      baseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        manufacturerId: null,
        status: null,
        mainAreaId: null,
        detailAreaId: null,
        originalManufacturerChineseName: null,
        createTime: null,
        startDate: null,
        endDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 分页数据
    handleSizeChange(val) {
      this.queryParams.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.queryParams.pageNum = val
      this.getList()
    },
    //查询日期切换事件
    dutyDateChange(val) {
      console.log(val);
      if (val!=null){
        this.queryParams.startDate = val[0]
        this.queryParams.endDate = val[1]
      }else {
        this.queryParams.startDate = ''
        this.queryParams.endDate = ''
      }

    },
    /** 查询销售管理-厂商基本资料列表 */
    getList() {
      this.loading = true;

      if (this.queryParams.originalManufacturerChineseName=="N"||this.queryParams.originalManufacturerChineseName==""){
        this.queryParams.originalManufacturerChineseName =''
      }
      listBase(this.queryParams).then(response => {

        this.baseList = response.rows;
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
        id: null,
        compId: null,
        manufacturerId: null,
        countryId: null,
        countryName: null,
        catalog: null,
        catalogName: null,
        areaId: null,
        areaName: null,
        manufacturerChineseName: null,
        manufacturerEnglishName: null,
        manufacturerShortName: null,
        taxNo: null,
        owner: null,
        ownerTitle: null,
        capital: null,
        capitalCurrencyCode: null,
        turnover: null,
        turnoverCurrencyCode: null,
        createUser: null,
        updateUser: null,
        originaManufacturerId: null,
        billAddress: null,
        billZipCode: null,
        billZipDesc: null,
        web: null,
        status: null,
        corelation: null,
        professional: null,
        mainAreaId: null,
        mainAreaDesc: null,
        detailAreaId: null,
        detailAreaDesc: null,
        groupClass: null,
        relatedParty: null,
        relatedPartyItem: null,
        departmentCode: null,
        clearStatus: null,
        isCentralizedPurchase: null,
        originalManufacturerChineseName: null,
        createTime: null,
        updateTime: null,
        parentId: null,
        delFlag: null
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加销售管理-厂商基本资料";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBase(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改销售管理-厂商基本资料";
      });
    },
/*    /!** 提交按钮 *!/
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBase(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBase(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },*/
    /** 删除按钮操作 */
/*    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除销售管理-厂商基本资料编号为"' + ids + '"的数据项？').then(function() {
        return delBase(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },*/
    /** 导出按钮操作 */
    handleExport() {
 /*     this.download('system/base/export', {
        ...this.queryParams
      }, `base_${new Date().getTime()}.xlsx`)*/
    }
  }
};
</script>
