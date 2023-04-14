<template>
  <el-dialog title="选择厂商" :visible.sync="visible" width="1080px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small"
             :inline="true" >
      <el-form-item label="厂商编码" prop="manufacturerId">
        <el-input
          v-model="queryParams.manufacturerId"
          placeholder="请输入厂商编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="中文名称" prop="manufacturerChineseName">
        <el-input
          v-model="queryParams.manufacturerChineseName"
          placeholder="请输入中文名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>

      </el-form-item>
    </el-form>
    <el-row>
    <el-table @row-click="clickRow"ref="table"
              :data="baseList" @selection-change="handleSelectionChange"
              height="360px">
      <el-table-column type="selection" width="55"></el-table-column>
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
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSelectUser">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listBaseSelect } from "@/api/finance/gp/base1";
export default {
  name: "selectManufacturer",
  dicts: ['gp_main_areaid', 'gp_detail_areaid','sys_yes_no'],
  data() {
    return {
      // 遮罩层
      visible: false,
      // 选中数组
      manufacturerId: [],
      // 选中数组
      manufacturerChineseName: [],
      // 选中数组
      compId: [],
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
        manufacturerChineseName: undefined,
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
    // 显示弹框
    show(queryParams) {
      if(queryParams){
        this.queryParams = queryParams;
      }
      this.getList();
      this.visible = true;
    },

    /** 选择授权用户操作 */
    handleSelectUser() {
      const manufacturerId = this.manufacturerId.join(",");
      const manufacturerChineseName = this.manufacturerChineseName.join(",");
      const compId = this.compId.join(",");
      if (manufacturerId == "") {
        this.$modal.msgError("请选择要分配的厂商编码");
        return;
      }
      if (this.manufacturerId.length > 1) {
        this.$modal.msgError("只能选择一笔数据");
        return;
      }
      this.visible = false;
      this.$emit("ok", manufacturerId, manufacturerChineseName, compId);
    },
    /** 查询销售管理-厂商基本资料列表 */
    getList() {
      this.loading = true;
      listBaseSelect(this.queryParams).then(response => {
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
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.manufacturerId = selection.map(item => item.manufacturerId)
      this.manufacturerChineseName = selection.map(item => item.manufacturerChineseName)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },

  }
};
</script>
