<template>
  <!-- 授权用户 -->
  <el-dialog title="会计科目" :visible.sync="visible" width="1080px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" :inline="true">
          <el-form-item label="会计科目代码" prop="acctCode">
            <el-input
              v-model="queryParams.acctCode"
              placeholder="会计科目代码"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="会计科目名称" prop="acctName">
            <el-input
              v-model="queryParams.acctName"
              placeholder="会计科目名称"
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
      <el-table @row-click="clickRow" ref="table" :data="baseList" @selection-change="handleSelectionChange"
                height="360px">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column label="会计科目代码" prop="acctCode" :show-overflow-tooltip="true"/>
        <el-table-column label="会计科目名称" prop="acctName" :show-overflow-tooltip="true"/>
        <el-table-column label="核算项目一" align="center" prop="calTypeCodea" show-overflow-tooltip>
          <template v-slot="scope">
            <span>{{ transCalTypeName(scope.row.calTypeCodea, '') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="核算项目二" align="center" prop="calTypeCodeb" show-overflow-tooltip>
          <template v-slot="scope">
            <span>{{ transCalTypeName(scope.row.calTypeCodeb, '') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="核算项目三" align="center" prop="calTypeCodec" show-overflow-tooltip>
          <template v-slot="scope">
            <span>{{ transCalTypeName(scope.row.calTypeCodec, '') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="核算项目四" align="center" prop="calTypeCoded" show-overflow-tooltip>
          <template v-slot="scope">
            <span>{{ transCalTypeName(scope.row.calTypeCoded, '') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="是否凭证科目" prop="isVoucher" :show-overflow-tooltip="true">
          <template v-slot="scope">
            <dict-tag :options="dict.type.aa_yes_no" :value="scope.row.isVoucher"/>
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
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleSelectUser">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {listAcctcodeCorpPop} from "@/api/finance/aa/acctcodeCorp";
import { selectCalTypeList } from "@/api/finance/aa/calType";
export default {
  dicts: ['aa_yes_no','aa_disabled_code','aa_drcr'],
  props: ['companyId'],
  data() {
    return {
      // 核算项目类别选单
      calTypeList: [],
      // 判断选择单笔还是多笔
      isSingle: true,
      // 遮罩层
      visible: false,
      // 选中数组值
      projectIds: [],
      // 总条数
      total: 0,
      // 未授权用户数据
      userList: [],

      // 弹窗数据
      baseList: [],

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleId: 1,
        companyId: null,
        acctCode: null,
        acctName: null,
      }
    };
  },
  created() {
    this.getCalTypeList();
  },
  methods: {
    // 显示弹框
    show(val) {
      this.getList();
      this.visible = true;
      this.isSingle = val
    },

    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.projectIds = selection;
    },

    // 查询表数据
    getList() {
      this.loading = true;
      this.queryParams.companyId = this.companyId;
      listAcctcodeCorpPop(this.queryParams).then(response => {
        this.baseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getCalTypeList() {
      selectCalTypeList().then(response => {
        this.calTypeList = response;
      });
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
    /** 选择会计科目 */
    handleSelectUser() {
      if (this.projectIds.length === 0) {
        this.$modal.msgError("请选择要分配的会计科目代码");
        return;
      }
      if (this.projectIds.length > 1 && this.isSingle === true) {
        this.$modal.msgError("只能选择一笔数据");
        return;
      }
      this.visible = false;
      this.$emit("ok",this.projectIds);
    },
    /** 核算项目转中文 */
    transCalTypeName(calType,delimiter) {
      if(calType == '') return '';
      if(delimiter!=''){
        return calType + delimiter + this.calTypeList.find(item => item.value == calType).label;
      }
      return this.calTypeList.find(item => item.value == calType).label;
    },
  }
};
</script>
<style scoped>
</style>
