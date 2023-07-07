<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="会计公司" prop="companyId">
        <el-select v-model="queryParams.companyId" filterable placeholder="请输入会计公司">
          <el-option
            v-for="item in companyList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="processingStatus">
        <el-select v-model="queryParams.processingStatus" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.ao_accounting_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
            @click.native="statusId(dict)"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="业务单号" prop="billNo">
        <el-input
          v-model="queryParams.billNo"
          placeholder="请输入业务单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请日期" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          format="yyyy-MM-dd"
          @change="dutyDateChange">
        </el-date-picker>

      </el-form-item>
      <el-form-item label="申请人" prop="accountName">
        <el-input
          v-model="queryParams.accountName"
          placeholder="请输入申请人"
          clearable
          @keyup.enter.native="handleQuery"
        />
<!--        <el-input v-model="queryParams.accountName" placeholder="请输入申请人">
          <el-button slot="append" icon="el-icon-search" @click="deptNoButtonForm"
          ></el-button>
        </el-input>-->
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
          @click="batchReview"
          v-hasPermi="['ao:accountHost:add']"
        >批量审核</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="accountHostList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="单据编号" align="center" prop="billNo" />
      <el-table-column label="单据类型" align="center" prop="billType" />
      <el-table-column label="申请日期" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请人" align="center" prop="accountName" />
      <el-table-column label="单据总额" align="center" prop="amt" />
      <el-table-column label="处理状态" align="center" prop="processingStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ao_accounting_status" :value="scope.row.processingStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="单据状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.ao_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="reviewIf"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="review(scope.row)"
            v-hasPermi="['ao:accountHost:edit']"
          >审核</el-button>
          <el-button
            v-if="viewOrCancelIf"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="review(scope.row)"
            v-hasPermi="['ao:accountHost:edit']"
          >查看或取消</el-button>
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

    <!-- 添加或修改会计确认主档对话框 -->

    <selectOrgPerson ref="selectOrgPerson" @ok="getOrgPerson"/>

  </div>
</template>
<script>
import { listAccountHost} from "@/api/finance/ao/accountHost";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import selectOrgPerson from "@/views/components/human/selectUser/selectOrgPerson";
import detailAccount from "@/views/finance/ao/detailAccount/index";
export default {
  name: "AccountHost",
  components: {selectOrgPerson},
  dicts: ['ao_accounting_status','ao_status'],
  data() {
    return {
      //审核判断
      reviewIf:true,
      //查看或取消判断
      viewOrCancelIf:false,
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
      // 会计确认主档表格数据
      accountHostList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: 'J00',
        billNo: null,
        billId: null,
        accountBy: null,
        accountName: null,
        payType: null,
        postDate: null,
        approvalDate: null,
        approvalComments: null,
        approvalStatus: null,
        processingStatus:'N',
        status: null,
        startDate: null,
        endDate: null,
      },
      // 会计公司下拉选单
      companyList: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getCompanyList()
  },

  methods: {
    /** 部门编码点击 */
    deptNoButtonForm() {
      this.$refs.selectOrgPerson.show();
    },

    getOrgPerson(node, id, name, val) {
    },
    //查询日期切换事件
    dutyDateChange(val) {
      if (val!=null){
        this.queryParams.startDate = val[0]
        this.queryParams.endDate = val[1]
      }else {
        this.queryParams.startDate = ''
        this.queryParams.endDate = ''
      }

    },

    /** 状态下拉选单 */
    statusId(val){
      this.reviewIf=false
      this.viewOrCancelIf=false
      if (val.value=='Y'){
        this.viewOrCancelIf=true
      }else {
        this.reviewIf=true
      }
      this.getList()
    },
    /** 公司别下拉选单 */
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
        this.queryParams.companyId = this.companyList[0].value
        this.getList();
      });
    },
    /** 查询会计确认主档列表 */
    getList() {
      this.loading = true;
      listAccountHost(this.queryParams).then(response => {
        this.accountHostList = response.rows;
        this.accountHostList = this.accountHostList.map(item => {
          switch (item.billType) {
            case 'PB': item.billType = '借支单'; break;
            case 'PC': item.billType = '费用报销单'; break;
            case 'PD': item.billType =  '还款单'; break;
          }
          return item;
        })
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
        companyId: null,
        billNo: null,
        billId: null,
        accountBy: null,
        accountName: null,
        payType: null,
        postDate: null,
        approvalDate: null,
        approvalComments: null,
        approvalStatus: null,
        status: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        updateName: null
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
    /** 批量审核按钮操作 */
    batchReview(){
      this.$router.push({
        name: 'DetailAccount', query: {
          companyId: this.queryParams.companyId
        }
      });
    },

    /** 审核按钮操作 */
    review(row){
      this.$router.push({
        name: 'DetailAccount', query: {
          companyId: row.companyId,
          billNo: row.billNo,
          accountName: row.accountName,
         processingStatus: row.processingStatus,
        }
      });
    },
  },

};
</script>
