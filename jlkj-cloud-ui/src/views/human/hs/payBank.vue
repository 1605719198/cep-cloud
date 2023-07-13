<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="公司" prop="compId">
            <el-select v-model="queryParams.compId" placeholder="请选择公司">
              <el-option
                v-for="dict in companyName"
                :key="dict.compId"
                :label="dict.companyName"
                :value="dict.compId"
              ></el-option>
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
              v-hasPermi="['human:payBank:add']"
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
              v-hasPermi="['human:payBank:remove']"
            >删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="form.payBankList" :row-class-name="tableRowClassName" @row-click="addLine" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="银行编码" align="center" prop="bankCode">
            <template v-slot="scope">
              <el-input v-model="scope.row.bankCode" placeholder="请输入"/>
            </template>
          </el-table-column>
          <el-table-column label="银行名称" align="center" prop="bankName">
            <template v-slot="scope">
              <el-select v-model="scope.row.bankName" placeholder="请选择">
                <el-option
                  v-for="dict in payBankData.BankName"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" prop="status">
            <template v-slot="scope">
              <el-select v-model="scope.row.status" placeholder="请选择">
                <el-option
                  v-for="dict in payBankData.status"
                  :key="dict.dicNo"
                  :label="dict.dicName"
                  :value="dict.dicNo"
                ></el-option>
              </el-select>
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
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listPayBank, delPayBank, addPayBank } from "@/api/human/hs/payBank";
import {selectCompany} from "@/api/human/hp/deptMaintenance";
import {getSalaryOptions} from "@/api/human/hs/salaryBasis";

export default {
  name: "PayBank",
  dicts: ['sys_yes_no'],
  data() {
    return {
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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        compId: null,
      },
      // 表单参数
      form: {
        // 各公司薪资支付银行维护表格数据
        payBankList: []
      },
      // 表单校验
      rules: {
      },
      //登录人信息
      user: {},
      // 公司别下拉选单
      companyName: [],
      salaryOptionType: {
        id: '19',
        optionsType: [
          'BankName',
          'status'
        ],
        compId:null,
      },
      payBankData: [],
      index: 0,
    };
  },
  created() {
    this.initData();
    selectCompany().then(res => {
      this.companyName = res.data
    })
    getSalaryOptions(this.salaryOptionType).then(response =>{
      this.payBankData = response.data
    })
  },
  methods: {
    //初始化数据
    initData(){
      this.user.compId = this.$store.state.user.userInfo.compId;
      this.queryParams.compId = this.user.compId
    },
    /** 查询各公司薪资支付银行维护列表 */
    getList() {
      this.loading = true;
      listPayBank(this.queryParams).then(response => {
        this.form.payBankList = response.data.rows;
        if (this.form.payBankList.length === 0) {
          this.form.payBankList = [
            {
              bankCode: undefined
            }
          ]
        }
        this.total = response.data.total;
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
        compId: null,
        bankCode: null,
        bankName: null,
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
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.uuid)
      this.multiple = !selection.length
    },
    /** 保存按钮操作 */
    handleSave() {
      this.$refs['queryForm'].validate(valid => {
        this.form.payBankList.map(value => {
          value.compId = this.queryParams.compId
        })
        addPayBank(this.form).then(res => {
          this.$modal.msgSuccess("新增成功");
          this.getList();
        })
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uuids = this.ids;
      this.$modal.confirm('是否确认删除各公司薪资支付银行维护编号为"' + uuids + '"的数据项？').then(function() {
        return delPayBank(uuids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    tableRowClassName({row, rowIndex}) {
      // 把每一行的索引放到row里
      row.index = rowIndex
    },
    // 增加一个空行, 用于录入或显示第一行
    addLine(row) {
      if (this.form.payBankList.length === row.index + 1) {
        const newLine = {
          bankCode: undefined
        }
        this.index++
        this.form.payBankList.push(newLine)
      }
    },
  }
};
</script>
