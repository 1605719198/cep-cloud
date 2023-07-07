<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" >
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="业务单号" prop="billNo">
            <el-input
              v-model="queryParams.billNo"
              placeholder="请输入业务单号"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="申请人" prop="accountName">
            <el-input
              v-model="queryParams.accountName"
              placeholder="请输入申请人"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          </el-form-item>
        </el-form>
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="待处理" name="first">
                <el-form ref="form"
                         :key="index" :model="form" :rules="rules"
                         label-width="97px"v-for="(item, index) in tableColumnsInput"
              >
                  <el-button type="primary" plain  @click="fromButton(item)">
                  <el-row>
                    <el-col :span="24" style="height: 30px">
                      <el-form-item label="单据类型:" prop="billType">
                        <span style="text-align: center;display:block;width: 121px">{{ item.billType }}</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24" style="height: 30px">
                      <el-form-item label="单据编号:" prop="billNo"  >
                        <span style="text-align: center;display:block;width: 121px">{{ item.billNo }}</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24" style="height: 30px">
                      <el-form-item label="单据总额:" prop="amt">
                        <span style="text-align: center;display:block;width: 121px">{{ item.amt }}</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24" style="height: 30px">
                      <el-form-item label="申请人:" prop="accountName">
                        <span style="text-align: center;display:block;width: 121px">{{ item.accountName }}</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  </el-button>
                </el-form>
            </el-tab-pane>
            <el-tab-pane label="已处理" name="second">
              <el-form ref="form"
                       :key="index" :model="form" :rules="rules"
                       label-width="97px"v-for="(item, index) in tableColumnsInput"
              >
                <el-button type="primary" plain  @click="fromButtonQrocessed(item)">
                  <el-row>
                    <el-col :span="24" style="height: 30px">
                      <el-form-item label="单据类型:" prop="billType">
                        <span style="text-align: center;display:block;width: 121px">{{ item.billType }}</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24" style="height: 30px">
                      <el-form-item label="单据编号:" prop="billNo"  >
                        <span style="text-align: center;display:block;width: 121px">{{ item.billNo }}</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24" style="height: 30px">
                      <el-form-item label="单据总额:" prop="amt">
                        <span style="text-align: center;display:block;width: 121px">{{ item.amt }}</span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="24" style="height: 30px">
                      <el-form-item label="申请人:" prop="accountName">
                        <span style="text-align: center;display:block;width: 121px">{{ item.accountName }}</span>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-button>
              </el-form>
            </el-tab-pane>
          </el-tabs>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col >

      <el-col :span="18">

        <div v-if="borrowingIf">
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                v-if="buttonSecondIf"
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleBorrowingAdd"
                v-hasPermi="['ao:detailAccount:query']"
              >保存</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                v-if="buttonConfirmSecondIf"
                type="success"
                plain
                icon="el-icon-edit"
                size="mini"
                @click="handleBorrowingConfirm"
                v-hasPermi="['ao:detailAccount:query']"
              >确认</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                v-if="cancellationIf"
                type="danger"
                plain
                icon="el-icon-delete"
                size="mini"
                @click="handleCancelConfirmation"
                v-hasPermi="['ao:detailAccount:query']"
              >取消确认</el-button>
            </el-col>

            <el-col :span="1.5">
              <el-button
                v-if="buttonIf"
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleBorrowingCase"
                v-hasPermi="['ao:detailAccount:query']"
              >结案</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                v-if="cancellationCaseIf"
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleBorrowingCancelCase"
                v-hasPermi="['ao:detailAccount:query']"
              >取消结案</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                v-if="buttonReturnIf"
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleBorrowingReturn"
                v-hasPermi="['ao:detailAccount:query']"
              >退回</el-button>
            </el-col>
          </el-row>
          <el-form ref="borrowingForm" :model="borrowingForm" :rules="rules" label-width="118px">
            <el-row>
              <el-col :span="24" style="height: 30px">
                <span>借支主档信息维护</span>
              </el-col>
              <el-col :span="6" style="height: 30px">
                <el-form-item label="公司别" prop="companyId">
                  <budget-tag  :options="companyList" :value="borrowingForm.companyId"/>
                </el-form-item>
              </el-col>
              <el-col :span="6" style="height: 30px">
                <el-form-item label="申请单号" prop="billNo">
                  <span style="text-align: center;display:block;width: 121px">{{ borrowingForm.billNo }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="支出类别" prop="itemName">
                  <span style="text-align: center;display:block;width: 227px">{{ borrowingForm.itemName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6" style="height: 30px">
                <el-form-item label="申请日期" prop="createTime">
                  <span style="text-align: center;display:block;width: 121px">{{ parseTime(borrowingForm.createTime, '{y}-{m}-{d}') }}</span>
<!--                  <span style="text-align: center;display:block;width: 121px">{{ borrowingForm.createTime }}</span>-->
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="申请人" prop="applyName">
                  <span style="text-align: center;display:block;width: 121px">{{ borrowingForm.applyName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px"  v-if="payTypeReadIf">
                <el-form-item label="付款方式" prop="payType">
                  <dict-tag :options="dict.type.ao_repayment_type" :value="borrowingForm.payType"/>
                </el-form-item>
              </el-col>

              <el-col :span="6"style="height: 30px">
                <el-form-item label="签核会计" prop="accountName">
                  <span style="text-align: center;display:block;width: 121px">{{ borrowingForm.accountName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="案由摘要" prop="billDesc" :show-overflow-tooltip='true'>
                  <span style="text-align: center;display:block;width: 121px">{{ borrowingForm.billDesc }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="经办人" prop="createName">
                  <span style="text-align: center;display:block;width: 121px">{{ borrowingForm.createName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="经办人部门名称" prop="createDeptName">
                  <span style="text-align: center;display:block;width: 182px">{{ borrowingForm.createDeptName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="到期日" prop="dueDate">
                  <span style="text-align: center;display:block;width: 121px">{{ parseTime(borrowingForm.dueDate, '{y}-{m}-{d}') }}</span>

                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                  <el-form-item label="状态" prop="status">
                    <dict-tag :options="dict.type.ao_status" :value="borrowingForm.status"/>
                  </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="会计确认日" prop="accountDate">
                  <span style="text-align: center;display:block;width: 121px">{{ parseTime(borrowingForm.accountDate, '{y}-{m}-{d}') }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="实际付款日" prop="payDate">
                  <span style="text-align: center;display:block;width: 121px">{{ parseTime(borrowingForm.payDate, '{y}-{m}-{d}') }}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <el-divider content-position="center"></el-divider>
          <el-form ref="formDetail" :model="borrowingForm" >
            <el-row>
              <el-col :span="24" style="height: 30px">
              <span>
                借支细项信息维护
              </span>
              </el-col>
            </el-row>
            <el-table :data="borrowingForm.financeAoLoanDetailList"
                      :row-class-name="rowAoLoanDetaiIndex"
                      ref="financeAoLoanDetailList">
              <el-table-column type="index" label="序号" width="60px" />
              <el-table-column label="原始凭证类型" prop="invoiceType" width="150" >
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.ao_voucher_type" :value="scope.row.invoiceType"/>
                </template>
              </el-table-column>
              <el-table-column label="币别" prop="crcyCode" width="150">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.gp_currency_type" :value="scope.row.crcyCode"/>
                </template>
              </el-table-column>
              <el-table-column label="金额" prop="netAmt" width="150" >
                <template slot-scope="scope">
                  <el-form-item :prop="'financeAoLoanDetailList.' + scope.$index + '.netAmt'" :rules="rules.netAmt">
                    <el-input v-model="scope.row.netAmt" type="number" placeholder="请输入税前金额" />
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="记账币金额" prop="totalAmt" width="150">
                <template slot-scope="scope">
                  <el-form-item :prop="'financeAoLoanDetailList.' + scope.$index + '.totalAmt'" :rules="rules.totalAmt">
                    <el-input v-model="scope.row.totalAmt" type="number" placeholder="请输入记账币金额" />
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column label="摘要说明" prop="srlDesc" width="150" :show-overflow-tooltip='true' >
                <template slot-scope="scope">
                  <el-form-item :prop="'financeAoLoanDetailList.' + scope.$index + '.srlDesc'" :rules="rules.srlDesc">
                    <el-input v-model="scope.row.srlDesc" placeholder="请输入摘要说明" />
                  </el-form-item>
                </template>
              </el-table-column>
            </el-table>
          </el-form>
          <el-divider content-position="center"></el-divider>
          <el-row>
            <el-col :span="24" style="height: 30px">
              <span>
                抛账信息
              </span>
            </el-col>
          </el-row>
          <el-table v-loading="loading"
                    :data="detailAccountLoanList"
                    :key="tableKey2">
            <el-table-column prop="indexAccount" label="序号" width="50" />
            <el-table-column label="借贷别" align="center" prop="drCr"  width="120"  >
              <template slot-scope="scope">
                                <dict-tag :options="dict.type.aa_drcr" value="D"/>
<!--                <el-select v-model="scope.row.drCr" placeholder="请选择">
                  <el-option
                    v-for="dict in dict.type.aa_drcr"
                    :key="dict.value"
                    :label="dict.value+'_'+dict.label"
                    :value="dict.value"
                  ></el-option>
                </el-select>-->
              </template>
            </el-table-column>
            <el-table-column label="会计科目编号" align="center" prop="acctCode"  width="180" >
              <template slot-scope="scope">
                <el-input v-model="scope.row.acctCode" placeholder="请输入会计科目">
                  <el-button slot="append" icon="el-icon-search" @click="inputAcctName(scope.row)"
                  ></el-button>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="核算项目一" align="center" prop="calCodea"  width="180">
              <template slot-scope="scope">
                <el-input v-model="scope.row.calCodea" placeholder="请输入核算项目一名称">
                  <el-button slot="append" icon="el-icon-search" @click="inputCalNamea(scope.row)"
                  ></el-button>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="核算项目二" align="center" prop="calCodeb"  width="180">
              <template slot-scope="scope">
                <el-input v-model="scope.row.calCodeb" placeholder="请输入核算项目二名称">
                  <el-button slot="append" icon="el-icon-search" @click="inputCalNameb(scope.row)"
                  ></el-button>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="币别" align="center" prop="crcyUnit" >
              <template slot-scope="scope">
                <dict-tag :options="dict.type.gp_currency_type" value="CNY"/>
              </template>
            </el-table-column>
            <el-table-column label="记帐币金额" align="center" prop="amt" >
              <template slot-scope="scope">
              <el-input v-model="scope.row.amt" type="number" placeholder="请输入记账币金额" />
              </template>
            </el-table-column>
            <el-table-column label="摘要" align="center" prop="srlDesc" :show-overflow-tooltip='true' >
              <template slot-scope="scope">
                <el-input v-model="scope.row.srlDesc" placeholder="请输入摘要">
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="到期日" align="center" prop="dueDate" width="145">
              <template slot-scope="scope">
                <el-date-picker clearable
                                v-model="scope.row.dueDate"
                                type="date"
                                style="width: 135px"
                                value-format="yyyy-MM-dd"
                                placeholder="请选择到期日"
                >
                </el-date-picker>
              </template>
            </el-table-column>
          </el-table>
          <el-divider content-position="center"></el-divider>
          <el-row>
            <el-col :span="24" style="height: 30px">
              <span>
                审批记录
              </span>
            </el-col>
          </el-row>
          <el-table v-loading="loading" :data="approvalRecordList" >
            <el-table-column label="序号" align="center" prop="" />
            <el-table-column label="审核类别" align="center" prop="" />
            <el-table-column label="审核者" align="center" prop="" />
            <el-table-column label="审批意见" align="center" prop="" />
            <el-table-column label="接受时间" align="center" prop="" />
            <el-table-column label="处理时间" align="center" prop="" />
            <el-table-column label="处理状态" align="center" prop="" />
          </el-table>
        </div>

        <div  v-if="reimbursementIf">
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                v-if="buttonSecondIf"
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleReimbursementAdd"
                v-hasPermi="['ao:detailAccount:query']"
              >保存</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                v-if="buttonConfirmSecondIf"
                type="success"
                plain
                icon="el-icon-edit"
                size="mini"

                @click="handleReimbursementConfirm"
                v-hasPermi="['ao:detailAccount:query']"
              >确认</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                v-if="cancellationIf"
                type="danger"
                plain
                icon="el-icon-delete"
                size="mini"
                @click="handleReimbursementCancelConfirmation"
                v-hasPermi="['ao:detailAccount:query']"
              >取消确认</el-button>
            </el-col>

            <el-col :span="1.5">
              <el-button
                v-if="buttonIf"
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"

                @click="handleReimbursementBorrowingCase"
                v-hasPermi="['ao:detailAccount:query']"
              >结案</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                v-if="cancellationCaseIf"
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleReimbursementBorrowingCancelCase"
                v-hasPermi="['ao:detailAccount:query']"
              >取消结案</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                v-if="buttonReturnIf"
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleReimbursementReturn"
                v-hasPermi="['ao:detailAccount:query']"
              >退回</el-button>
            </el-col>
          </el-row>
          <el-form ref="form" :model="reimbursementForm" :rules="rules" label-width="118px">
            <el-row>
                <el-col :span="24" style="height: 30px">
              <span>
                报销单主档
              </span>
                </el-col>
              <el-col :span="6" style="height: 30px">
                <el-form-item label="公司" prop="companyId">
                  <budget-tag  :options="companyList" :value="reimbursementForm.companyId"/>
                </el-form-item>
              </el-col>
              <el-col :span="6" style="height: 30px">
                <el-form-item label="申请单号" prop="billNo">
                  <span style="text-align: center;display:block;width: 121px">{{ reimbursementForm.billNo }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="支出类别" prop="itemName">
                  <span style="text-align: center;display:block;width: 227px">{{ reimbursementForm.itemName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6" style="height: 30px">
                <el-form-item label="申请日期" prop="createTime">
                  <span style="text-align: center;display:block;width: 121px">{{ reimbursementForm.createTime }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="费用报销人" prop="applyName">
                  <span style="text-align: center;display:block;width: 121px">{{ reimbursementForm.applyName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="案由摘要" prop="billDesc" :show-overflow-tooltip='true'>
                  <span style="text-align: center;display:block;width: 121px">{{ reimbursementForm.billDesc }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="签核会计" prop="accountName">
                  <span style="text-align: center;display:block;width: 121px">{{ reimbursementForm.accountName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="报销单状态" prop="status">
                  <dict-tag :options="dict.type.ao_status" :value="reimbursementForm.status"/>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px" v-if="payTypeReadIf">
                <el-form-item label="付款方式" prop="payType">
                  <dict-tag :options="dict.type.ao_repayment_type" :value="reimbursementForm.payType"/>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="传票编号" prop="voucherNo">
                  <span style="text-align: center;display:block;width: 121px">{{ reimbursementForm.voucherNo }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="经办人" prop="createName">
                  <span style="text-align: center;display:block;width: 121px">{{ reimbursementForm.createName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="资金调度编号" prop="arrangeNo">
                  <span style="text-align: center;display:block;width: 121px">{{ reimbursementForm.arrangeNo }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="经办人部门名称" prop="createDeptName" :show-overflow-tooltip='true'>
                  <span style="text-align: center;display:block;width: 182px">{{ reimbursementForm.createDeptName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="实际付款日" prop="payDate">
                  <span style="text-align: center;display:block;width: 121px">{{ reimbursementForm.payDate }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="会计确认日" prop="accountDate">
                  <span style="text-align: center;display:block;width: 121px">{{ parseTime(reimbursementForm.accountDate, '{y}-{m}-{d}') }}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <el-divider content-position="center"></el-divider>
          <el-form ref="formDetail" :model="reimbursementForm"  :rules="reimbursementRules">
            <el-row>
              <el-col :span="24" style="height: 30px">
              <span>
                报销细项信息维护
              </span>
              </el-col>
            </el-row>
            <el-table :data="reimbursementForm.financeAoReimbursementDetailList"
                      :row-class-name="rowAoLoanDetaiIndex"
                      ref="financeAoReimbursementDetailList"
            :key="tableReimbursementKey">
              <el-table-column type="index" label="序号" width="60px" />
              <el-table-column label="费用细项" prop="detailNo" >
              </el-table-column>
              <el-table-column label="原始凭证类型" prop="invoiceType">
              </el-table-column>
              <el-table-column label="币别" prop="crcyCode">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.gp_currency_type" :value="scope.row.crcyUnit"/>
                </template>
              </el-table-column>
              <el-table-column label="税前金额" prop="netAmt">
                <template slot-scope="scope">
                    <el-input v-model="scope.row.netAmt" placeholder="请输入税前金额" disabled>
                      <el-button slot="append" icon="el-icon-search" @click="inputNetAmt(scope.row)"
                      ></el-button>
                    </el-input>
                </template>
              </el-table-column>
              <el-table-column label="税后金额" prop="totalAmt"  ></el-table-column>
              <el-table-column label="税额" prop="taxAmt" >
              </el-table-column>
              <el-table-column label="成本中心" prop="costCenter"  >
                <template slot-scope="scope">
                <el-input v-model="scope.row.costCenter" placeholder="请输入会计科目" disabled>
                  <el-button slot="append" icon="el-icon-search" @click="inputNetAmt(scope.row)"
                  ></el-button>
                </el-input>
                </template>
              </el-table-column>
              <el-table-column label="预算项目" prop="budgetName" >
              </el-table-column>
              <el-table-column label="摘要说明" prop="srlDesc"  :show-overflow-tooltip='true'>
              </el-table-column>
            </el-table>
          </el-form>
          <el-divider content-position="center"></el-divider>
          <el-form ref="reimbursementForm" :model="reimbursementForm" >
            <el-row>
              <el-col :span="24" style="height: 30px">
              <span>
                借款资料确认
              </span>
              </el-col>
            </el-row>
            <el-table :data="reimbursementForm.loanList"
                      :row-class-name="rowAoLoanDetaiIndex"
                      ref="loanList">
              <el-table-column type="index" label="序号" width="60px" />
              <el-table-column label="借支单号" prop="detailNo" >
              </el-table-column>
              <el-table-column label="请款日期" prop="createTime">
              </el-table-column>
              <el-table-column label="请款人" prop="applyName">
              </el-table-column>
              <el-table-column label="借支金额" prop="totalAmt">>
              </el-table-column>
              <el-table-column label="已销账金额" prop="cancelledAmt" />
              <el-table-column label="未销账金额" prop="uncollectedAccountsAmt" >
              </el-table-column>
              <el-table-column label="本次销账金额" prop="amt"  >
              </el-table-column>
            </el-table>
          </el-form>
          <el-divider content-position="center"></el-divider>
          <el-row>
            <el-col :span="24" style="height: 30px">
              <span>
                抛账信息
              </span>
            </el-col>
          </el-row>
          <el-table v-loading="loading"
                    :data="detailAccountList"

                    :row-class-name="rowDetailAccountIndex"
                    :key="tableKey">
            <el-table-column type="index" label="序号" width="50" />
            <el-table-column label="借贷别" align="center" prop="drCr"  width="120"  >
              <template slot-scope="scope">
                <el-select v-model="scope.row.drCr" placeholder="请选择">
                  <el-option
                    v-for="dict in dict.type.aa_drcr"
                    :key="dict.value"
                    :label="dict.value+'_'+dict.label"
                    :value="dict.value"
                  ></el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="会计科目编号" align="center" prop="acctCode"  width="180" >
              <template slot-scope="scope">
                <el-input v-model="scope.row.acctCode" placeholder="请输入会计科目">
                  <el-button slot="append" icon="el-icon-search" @click="inputReimbuRsemenAcctName(scope.row)"
                  ></el-button>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="核算项目一" align="center" prop="calCodea"  width="180">
              <template slot-scope="scope">
                <el-input v-model="scope.row.calCodea" placeholder="请输入核算项目一名称">
                  <el-button slot="append" icon="el-icon-search" @click="inputReimbuRsemenCalNamea(scope.row)"
                  ></el-button>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="核算项目二" align="center" prop="calCodeb"  width="180">
              <template slot-scope="scope">
                <el-input v-model="scope.row.calCodeb" placeholder="请输入核算项目二名称">
                  <el-button slot="append" icon="el-icon-search" @click="inputReimbuRsemenCalNameb(scope.row)"
                  ></el-button>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="币别" align="center" prop="crcyUnit" >
              <template slot-scope="scope">
                <dict-tag :options="dict.type.gp_currency_type" value="CNY"/>
              </template>
            </el-table-column>
            <el-table-column label="记帐币金额" align="center" prop="amt" >
              <template slot-scope="scope">
                <el-input v-model="scope.row.amt" type="number" placeholder="请输入记账币金额" />
              </template>
            </el-table-column>
            <el-table-column label="摘要" align="center" prop="srlDesc" :show-overflow-tooltip='true' >
              <template slot-scope="scope">
                <el-input v-model="scope.row.srlDesc" placeholder="请输入摘要">
                </el-input>
              </template>
            </el-table-column>
            <el-table-column label="到期日" align="center" prop="dueDate" width="145">
              <template slot-scope="scope">
                  <el-date-picker clearable
                                  v-model="scope.row.dueDate"
                                  type="date"
                                  style="width: 135px"
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择到期日"
                  >
                  </el-date-picker>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width"  width="100">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleAddAccountList(scope.row)"
                  v-hasPermi="['ao:detailAccount:query']"
                >插行
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDeleteAccountList(scope.row)"
                  v-hasPermi="['ao:detailAccount:query']"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div  v-if="repaymentIf">
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                v-if="buttonSecondIf"
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleRepaymentAdd"
                v-hasPermi="['ao:detailAccount:query']"
              >保存</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                v-if="buttonConfirmSecondIf"
                type="success"
                plain
                icon="el-icon-edit"
                size="mini"
                @click="handleRepaymentConfirm"
                v-hasPermi="['ao:detailAccount:query']"
              >确认</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                v-if="cancellationIf"
                type="danger"
                plain
                icon="el-icon-delete"
                size="mini"
                @click="handleRepaymentCancelConfirmation"
                v-hasPermi="['ao:detailAccount:query']"
              >取消确认</el-button>
            </el-col>
          </el-row>
          <el-form ref="repaymentForm" :model="repaymentForm" :rules="rules" label-width="118px">
            <el-row>
              <el-col :span="24" style="height: 30px">
                <span>还款单主档</span>
              </el-col>
              <el-col :span="6" style="height: 30px">
                <el-form-item label="还款单号" prop="billNo">
                  <span style="text-align: center;display:block;width: 121px">{{ repaymentForm.billNo }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="借支单号" prop="detailNo">
                  <span style="text-align: center;display:block;width: 121px">{{ repaymentForm.detailNo }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6" style="height: 30px">
                <el-form-item label="本次还款额" prop="amt">
                  <span style="text-align: center;display:block;width: 121px">{{ repaymentForm.amt }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="借支总额" prop="totalAmt">
                  <span style="text-align: center;display:block;width: 121px">{{ repaymentForm.totalAmt }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px" >
                <el-form-item label="还款方式" prop="payType">
                  <dict-tag :options="dict.type.ao_repayment_type" :value="repaymentForm.payType"/>
                </el-form-item>
              </el-col>

              <el-col :span="6"style="height: 30px">
                <el-form-item label="已报销金额" prop="cancelledAmt">
                  <span style="text-align: center;display:block;width: 121px">{{ repaymentForm.cancelledAmt}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="币别" prop="crcyCode">
                    <dict-tag :options="dict.type.gp_currency_type" :value="repaymentForm.crcyUnit"/>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="未报销金额" prop="uncollectedAccountsAmt">
                  <span style="text-align: center;display:block;width: 121px">{{ repaymentForm.uncollectedAccountsAmt }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="还款日期" prop="payDate">
                  <span style="text-align: center;display:block;width: 121px">{{ parseTime(repaymentForm.payDate, '{y}-{m}-{d}') }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="6"style="height: 30px">
                <el-form-item label="借款人" prop="loanName">
                  <span style="text-align: center;display:block;width: 121px">{{ repaymentForm.loanName }}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <el-divider content-position="center"></el-divider>
          <el-form ref="abandoningAccountsForm" :model="abandoningAccountsForm" >
            <el-row>
              <el-col :span="24" style="height: 30px">
              <span>
                抛账信息
              </span>
              </el-col>
            </el-row>
            <el-table v-loading="loading"
                      :data="detailRepaymentAccountList"
                      :row-class-name="rowDetailRepaymentIndex"
                      :key="tableKey3">
              <el-table-column prop="indexRepayment" label="序号" width="50" />
              <el-table-column label="借贷别" align="center" prop="drCr"  width="120"  >
                <template slot-scope="scope">
                  <!--                <dict-tag :options="dict.type.aa_drcr" :value="scope.row.drCr"/>-->
                  <el-select v-model="scope.row.drCr" placeholder="请选择">
                    <el-option
                      v-for="dict in dict.type.aa_drcr"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    ></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column label="会计科目编号" align="center" prop="acctCode"  width="180" >
                <template slot-scope="scope">
                  <el-input v-model="scope.row.acctCode" placeholder="请输入会计科目">
                    <el-button slot="append" icon="el-icon-search" @click="inputRepaymentAcctName(scope.row)"
                    ></el-button>
                  </el-input>
                </template>
              </el-table-column>
              <el-table-column label="核算项目一" align="center" prop="calCodea"  width="180">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.calCodea" placeholder="请输入核算项目一名称">
                    <el-button slot="append" icon="el-icon-search" @click="inputRepaymentCalNamea(scope.row)"
                    ></el-button>
                  </el-input>
                </template>
              </el-table-column>
              <el-table-column label="核算项目二" align="center" prop="calCodeb"  width="180">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.calCodeb" placeholder="请输入核算项目二名称">
                    <el-button slot="append" icon="el-icon-search" @click="inputRepaymentCalNameb(scope.row)"
                    ></el-button>
                  </el-input>
                </template>
              </el-table-column>
              <el-table-column label="币别" align="center" prop="crcyUnit" >
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.gp_currency_type" value="CNY"/>
                </template>
              </el-table-column>
              <el-table-column label="记帐币金额" align="center" prop="amt" >
                <template slot-scope="scope">
                  <el-input v-model="scope.row.amt" type="number" placeholder="请输入记账币金额" />
                </template>
              </el-table-column>
              <el-table-column label="摘要" align="center" prop="srlDesc" :show-overflow-tooltip='true' >
                <template slot-scope="scope">
                  <el-input v-model="scope.row.srlDesc" placeholder="请输入摘要">
                  </el-input>
                </template>
              </el-table-column>
              <el-table-column label="到期日" align="center" prop="dueDate" width="145">
                <template slot-scope="scope">
                  <el-date-picker clearable
                                  v-model="scope.row.dueDate"
                                  type="date"
                                  style="width: 135px"
                                  value-format="yyyy-MM-dd"
                                  placeholder="请选择到期日"
                  >
                  </el-date-picker>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width"  width="100">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleAddDetailList(scope.row)"
                    v-hasPermi="['ao:detailAccount:query']"
                  >插行
                  </el-button>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    @click="handleDeleteList(scope.row)"
                    v-hasPermi="['ao:detailAccount:query']"
                  >删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-form>
        </div>
      </el-col>
    </el-row>
    <acctcodeCorpPop ref="selectAcctCodeCorpPop" :companyId="this.queryParams.companyId" @ok="getAcctCodeCorpPop"/>
    <acctcodeCorpPop ref="selectAcctCodeRepayment" :companyId="this.queryParams.companyId" @ok="getAcctCodeRepayment"/>
    <acctcodeCorpPop ref="selectAcctCodeReimbursement" :companyId="this.queryParams.companyId" @ok="getAcctCodeReimbursement"/>
    <calTypePOP ref="selectPOP" @pop="getCalTypePOP"/>
    <calTypePOP ref="selectPOP2" @pop="getCalTypePOP2"/>
    <calTypePOP ref="selectReimbuRsemenPOP" @pop="getReimbuRsemenCalTypePOP"/>
    <calTypePOP ref="selectReimbursemenPOP2" @pop="getReimbuRsemenCalTypePOP2"/>
    <calTypePOP ref="selectRepaymentPOP" @pop="getRepaymentCalTypePOP"/>
    <calTypePOP ref="selectRepaymentPOP2" @pop="getRepaymentCalTypePOP2"/>
  </div>
</template>

<script>
import {
  addDetailAccount,
  updateDetailAccount,
  getLoanADetailAccount,
  listDetailAccount,
} from "@/api/finance/ao/detailAccount";
import budgetTag from "@/views/finance/ao/budget/budgetTag";
import {selectCompanyList} from "@/api/finance/aa/companyGroup";
import acctcodeCorpPop from "@/views/finance/aa/acctcodeCorpPop";
import calTypePOP from "@/views/components/finance/calTypePOP";
import {addAccountHost, detailList, updateAccountHost,updateAccountHostRepayment} from "@/api/finance/ao/accountHost";
import { getLoanApplyFrom} from "@/api/finance/ao/loanApply";

import { getReimbursementFrom} from "@/api/finance/ao/reimbursement";
import { getFormLoan} from "@/api/finance/ao/loan";
import {  getRepayment} from "@/api/finance/ao/repayment";
export default {
  name: "DetailAccount",
  dicts: ['aa_drcr','ao_voucher_type','ao_repayment_type','gp_currency_type','ao_status'],
  components: { budgetTag,acctcodeCorpPop,calTypePOP},
  data() {
    return {
      // 表单参数
      formDetail: {
        detailList:[]
      },
      tableColumnsInput:[],
      activeName:'first',
      // 遮罩层
      visible: false,
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
      // 审批记录表格
      approvalRecordList: [],
      // 报销表格数据
      detailAccountList: [],

      // 借支
      detailAccountLoanList:[],
      //还款单数组
      detailRepaymentAccountList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyId: 'J00',
        accountId: null,
        billNo: null,
        billId: null,
        seqNo: null,
        drCr: null,
        acctId: null,
        acctCode: null,
        calIda: null,
        calCodea: null,
        calIdb: null,
        calCodeb: null,
        calIdc: null,
        calCodec: null,
        calIdd: null,
        calCoded: null,
        dueDate: null,
        srlDesc: null,
        crcyUnit: null,
        frnAmt: null,
        amt: null,

      },
      // 表单参数
      form: {},
      // 报支表单
      borrowingForm: {
        /** 报支管理-借支申请明细档信息 */
        financeAoLoanDetailList:[],
        /** 抛账信息 */
        detailAccountLoanList:[]
      },
      abandoningAccountsForm: {
        abandoningAccountsList:[]
      },
      // 报销表单
      reimbursementForm: {
        financeAoReimbursementDetailList:[],
        loanList:[]
      },
      //借款资料确认

      //还款单主档
      repaymentForm:{
        detailRepaymentAccountList:[],
      },
      // 表单校验
      rules: {
      },
      // 会计公司下拉选单
      companyList: [],
      indexRow:'',
      borrowingIf:false,
      reimbursementIf:false,
      repaymentIf:false,
      payTypeReadIf:false,
      //结案
      buttonIf:false,
      //退回
      buttonReturnIf:false,
      //保存
      buttonSecondIf :false,
      //确认
      buttonConfirmSecondIf:false,
      //取消结案
      cancellationCaseIf :false,
      //取消确认
      cancellationIf :false,
      tableKey:0,
      tableKey2:1,
      tableKey3:3,
      tableReimbursementKey:4,
      calTypeCodea:'',
      calTypeCodeb:'',
      reimbursementRules:{
        detailNo: [
          { required: true, message: "费用细项不能为空", trigger: "change" },
        ]
      },
      valString:'',
      ReimbursementVal:'',
      repaymentVal:'',
    };
  },
  created() {
    this.init();

  },
  watch:{
    $route(){
      this.init();
    }
  },
  methods: {
    init(){
      this.queryParams=this.$route.query
      if (this.$route.query.processingStatus ==null){
        this.queryParams.processingStatus='N'
      }
      this.getList();
      this.getCompanyList()
    },
    //待处理
    fromButton(val){
      this.buttonSecondIf=true,
        this.buttonConfirmSecondIf = true
      this.buttonIf=false,
      this.borrowingIf=false;
      this.reimbursementIf=false;
      this.repaymentIf=false;
      this.cancellationCaseIf =true;
      this.cancellationIf =true;
      this.buttonReturnIf=false;

      if (val.billType =='借支单'){
        this.borrowingIf = true
        this.buttonIf= true
        this.buttonReturnIf=true,
        this.getListLoan(val.billId)
      }
      if (val.billType =='费用报销单'){
        this.reimbursementIf = true
        this.buttonIf= true
        this.buttonReturnIf=true
        this.getReimbursement(val)
      }  if (val.billType =='还款单'){
        this.repaymentIf = true
        this.buttonConfirmSecondIf = true
        this.cancellationCaseIf = false
        this.getRepaymentId(val)
      }

    },
    //已处理
    fromButtonQrocessed(val){
      this.cancellationCaseIf =true;
      this.buttonConfirmSecondIf=false,
      this.cancellationIf =true;
      this.buttonIf= true
      this.buttonSecondIf=false,
        this.borrowingIf=false;
      this.reimbursementIf=false;
      this.repaymentIf=false;
      if (val.billType =='借支单'){
        this.borrowingIf = true
        this.getListLoan(val.billId)
      }
      if (val.billType =='费用报销单'){
        this.reimbursementIf = true
        this.getReimbursement(val)

      }  if (val.billType =='还款单'){
        this.repaymentIf = true
        this.getRepaymentId(val)
      }

    },
    handleClick(tab, event) {
      this.cancellationCaseIf =true;
      this.cancellationIf =true;
      this.payTypeReadIf=false;
      this.buttonIf=false;
      this.buttonReturnIf=false,
      this.buttonSecondIf=false;
      if (tab.name=='second'){
        this.queryParams.processingStatus='Y'
        this.payTypeReadIf=true
      }else {
        this.queryParams.processingStatus='N'
        this.buttonIf=true;
        this.buttonReturnIf=true,
        this.buttonSecondIf=true;
      }
      this.getList()
    },
    /** 序号 */
    rowAoLoanDetaiIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    /** 序号 */
    rowDetailAccountIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    rowDetailRepaymentIndex({row, rowIndex}) {
      row.indexRepayment = rowIndex + 1;
    },
    /** 借支会计科目点击事件 */
    inputAcctName(val) {
      this.indexAccount= val.indexAccount
      this.$refs.selectAcctCodeCorpPop.show();
    },
    inputNetAmt(){
      this.$refs.selectNetAmtPop.show();
    },
    /** 报销会计科目点击事件 */
    inputReimbuRsemenAcctName(val) {
      this.index= val.index
      this.$refs.selectAcctCodeReimbursement.show();
    },
    /** 还款会计科目点击事件 */
    inputRepaymentAcctName(val) {
      this.indexRepayment= val.indexRepayment
      this.$refs.selectAcctCodeRepayment.show();
    },
    /**借支会计科目弹窗 */
    getAcctCodeCorpPop(val){
      this.$set(this.detailAccountLoanList[0], 'acctCode', val[0].acctCode);
      this.$set(this.detailAccountLoanList[this.indexRow-1], 'acctId', val[0].acctId);
      this.$set(this.detailAccountLoanList[0], 'acctName', val[0].acctName);
      this.calTypeCodea=val[0].calTypeCodea
      this.calTypeCodeb=val[0].calTypeCodeb
    },
    /**报销会计科目弹窗 */
    getAcctCodeReimbursement(val){
      this.$set(this.detailAccountList[this.index-1], 'acctCode', val[0].acctCode);
      this.$set(this.detailAccountList[this.index-1], 'acctId', val[0].acctId);
      this.$set(this.detailAccountList[this.index-1], 'acctName', val[0].acctName);
      this.calTypeCodea=val[0].calTypeCodea
      this.calTypeCodeb=val[0].calTypeCodeb
    },
    /**还款会计科目弹窗 */
    getAcctCodeRepayment(val){
      this.$set(this.detailRepaymentAccountList[this.indexRepayment-1], 'acctCode', val[0].acctCode);
      this.$set(this.detailRepaymentAccountList[this.indexRepayment-1], 'acctId', val[0].acctId);
      this.$set(this.detailRepaymentAccountList[this.indexRepayment-1], 'acctName', val[0].acctName);
      this.calTypeCodea=val[0].calTypeCodea
      this.calTypeCodeb=val[0].calTypeCodeb
    },
    /** 借支项目1点击事件 */
    inputCalNamea(val) {
      this.indexRow= val.indexAccount
      this.queryParams.calTypeCode =  this.calTypeCodea
      this.$refs.selectPOP.show(this.queryParams);
    },
    getCalTypePOP(val){
      this.$set(this.detailAccountLoanList[this.indexRow-1], 'calCodea', val.calNo);
      this.$set(this.detailAccountLoanList[this.indexRow-1], 'calIda', val.Id);
      this.$set(this.detailAccountLoanList[this.indexRow-1], 'calNamea', val.calName);
    },
    /**借支项目2点击事件 */
    inputCalNameb(val) {
      this.indexRow= val.indexAccount
      this.queryParams.calTypeCode =  this.calTypeCodeb
      this.$refs.selectPOP2.show( this.queryParams);
    },
    getCalTypePOP2(val){
      this.$set(this.detailAccountLoanList[this.indexRow-1], 'calCodeb', val.calNo);
      this.$set(this.detailAccountLoanList[this.indexRow-1], 'calIdb', val.Id);
      this.$set(this.detailAccountLoanList[this.indexRow-1], 'calNameb', val.calName);
    },
    /** 报销项目1点击事件 */
    inputReimbuRsemenCalNamea(val) {
      this.index= val.index
      this.queryParams.calTypeCode =  this.calTypeCodea
      this.$refs.selectReimbuRsemenPOP.show(this.queryParams);
    },
    getReimbuRsemenCalTypePOP(val){
      this.$set(this.detailAccountList[this.index-1], 'calCodea', val.calNo);
      this.$set(this.detailAccountList[this.index-1], 'calIda', val.Id);
      this.$set(this.detailAccountList[this.index-1], 'calNamea', val.calName);
    },
    /** 报销项目2点击事件 */
    inputReimbuRsemenCalNameb(val) {
      this.index= val.index
      this.queryParams.calTypeCode =  this.calTypeCodeb
      this.$refs.selectReimbursemenPOP2.show( this.queryParams);
    },
    getReimbuRsemenCalTypePOP2(val){
      this.$set(this.detailAccountList[this.index-1], 'calCodeb', val.calNo);
      this.$set(this.detailAccountList[this.index-1], 'calIdb', val.Id);
      this.$set(this.detailAccountList[this.index-1], 'calNameb', val.calName);
    },
    /** 还款项目1点击事件 */
    inputRepaymentCalNamea(val) {
      this.index= val.index
      this.queryParams.calTypeCode =  this.calTypeCodea
      this.$refs.selectRepaymentPOP.show(this.queryParams);
    },
    getRepaymentCalTypePOP(val){
      this.$set(this.detailRepaymentAccountList[this.indexRepayment-1], 'calCodea', val.calNo);
      this.$set(this.detailRepaymentAccountList[this.indexRepayment-1], 'calIda', val.Id);
      this.$set(this.detailRepaymentAccountList[this.indexRepayment-1], 'calNamea', val.calName);
    },
    /** 还款项目2点击事件 */
    inputRepaymentCalNameb(val) {
      this.index= val.index
      this.queryParams.calTypeCode =  this.calTypeCodeb
      this.$refs.selectRepaymentPOP2.show( this.queryParams);
    },
    getRepaymentCalTypePOP2(val){
      this.$set(this.detailRepaymentAccountList[this.indexRepayment-1], 'calCodeb', val.calNo);
      this.$set(this.detailRepaymentAccountList[this.indexRepayment-1], 'calIdb', val.Id);
      this.$set(this.detailRepaymentAccountList[this.indexRepayment-1], 'calNameb', val.calName);
    },
    /** 公司别下拉选单 */
    getCompanyList() {
      selectCompanyList().then(response => {
        this.companyList = response;
        this.queryParams.companyId = this.companyList[0].value
      });
    },
    // 显示弹框
    show(queryParams) {
      if(!queryParams.companyId){
        this.$message.error('公司别不能为空');
        return
      }
      if(!queryParams.billNo){
        this.$message.error('凭证单号不能为空');
        return
      }
      this.queryParams.companyId=queryParams.companyId
      this.getList();
      this.getCompanyList()
      this.visible = true;
    },
    /** 查询会计确认主档列表 */
    getList() {
      this.buttonIf=false,
        this.buttonReturnIf=false,
        this.borrowingIf=false;
      this.reimbursementIf=false;
      this.repaymentIf=false;
      this.loading = true;
      detailList(this.queryParams).then(response => {
        console.log(this.tableColumnsInput);

        this.tableColumnsInput = response.rows;
        this.tableColumnsInput = this.tableColumnsInput.map(item => {
          switch (item.billType) {
            case 'PB': item.billType = '借支单'; break;
            case 'PC': item.billType = '费用报销单'; break;
            case 'PD': item.billType =  '还款单'; break;
          }
          return item;
        })
        if (this.tableColumnsInput.length>0){
          console.log(this.tableColumnsInput[0].processingStatus);
            if ( this.tableColumnsInput[0].processingStatus =='N'){
              this.activeName="first"
            }else {
              this.activeName="second"
            }
        }

        this.total = response.total;
        this.loading = false;
      });
    },
    /** 借支单查询 */
    getListLoan(val){
      getLoanApplyFrom(val).then(response => {
        this.borrowingForm = response.data
        listDetailAccount( this.borrowingForm).then(response => {
          if ( response.rows.length == 0){
            getLoanADetailAccount(this.borrowingForm).then(response => {
              console.log(response.data.acctCode);
              let itemAccount= {
                indexAccount: 1,
                id: null,
                companyId: null,
                accountId: null,
                billNo: null,
                billId: null,
                seqNo: null,
                drCr: null,
                acctId: null,
                acctCode: null,
                calIda: null,
                calCodea: null,
                calIdb: null,
                calCodeb: null,
                calIdc: null,
                calCodec: null,
                calIdd: null,
                calCoded: null,
                dueDate: null,
                srlDesc: null,
                crcyUnit: null,
                frnAmt: null,
                amt: null,
                createBy: null,
                createName: null,
                createTime: null,
                updateBy: null,
                updateTime: null,
                updateName: null
              };
              this.detailAccountLoanList.push(itemAccount)
              this.detailAccountLoanList[0].acctCode = response.data.acctCode
              this.detailAccountLoanList[0].acctId = response.data.acctId
              this.detailAccountLoanList[0].calTypeCodea= response.data.calTypeCodea
              this.detailAccountLoanList[0].calTypeCodeb= response.data.calTypeCodeb
              this.calTypeCodea=response.data.calTypeCodea
              this.calTypeCodeb= response.data.calTypeCodeb
            });
          }else {
            this.detailAccountLoanList= response.rows
            this.detailAccountLoanList[0].indexAccount = 1
          }

        });

    });
    },
    /** 报销点击查询 */
    getReimbursement(val){
      getReimbursementFrom(val.billId).then(response => {
        this.reimbursementForm = response.data;
        getFormLoan( this.reimbursementForm).then(response => {
          this.reimbursementForm.loanList = response.data;
          listDetailAccount( this.reimbursementForm).then(response => {
            console.log(response.rows.length+"++++++++++++++++++");
            if ( response.rows.length == 0){
              this.detailAccountList=[]
              let item = {
                index: null,
                id: null,
                companyId: null,
                accountId: null,
                billNo: null,
                billId: null,
                seqNo: null,
                drCr: null,
                acctId: null,
                acctCode: null,
                calIda: null,
                calCodea: null,
                calIdb: null,
                calCodeb: null,
                calIdc: null,
                calCodec: null,
                calIdd: null,
                calCoded: null,
                dueDate: null,
                srlDesc: null,
                crcyUnit: null,
                frnAmt: null,
                amt: null,
                createBy: null,
                createName: null,
                createTime: null,
                updateBy: null,
                updateTime: null,
                updateName: null
              };
              this.detailAccountList.push(item)
            }else {
              this.detailAccountList = response.rows
            }

          });
        });
      });
    },
    /** 还款点击查询 */
    getRepaymentId(val){
      getRepayment(val.billId).then(response => {
        this.repaymentForm = response.data;
          listDetailAccount( this.repaymentForm).then(response => {
            if ( response.rows.length == 0){
              this.detailRepaymentAccountList=[]
              let itemRepayment= {
                indexRepayment: null,
                id: null,
                companyId: null,
                accountId: null,
                billNo: null,
                billId: null,
                seqNo: null,
                drCr: null,
                acctId: null,
                acctCode: null,
                calIda: null,
                calCodea: null,
                calIdb: null,
                calCodeb: null,
                calIdc: null,
                calCodec: null,
                calIdd: null,
                calCoded: null,
                srlDesc: null,
                crcyUnit: null,
                frnAmt: null,
                amt: null,
                dueDate: null,
              };
              this.detailRepaymentAccountList.push(itemRepayment)
            }else {
              this.detailRepaymentAccountList = response.rows
            }
          });
          });
    },
    /** 还款插入 */
    handleAddDetailList(row) {
      let item = {
        indexRepayment: row.indexRepayment + 1,
        id: null,
        companyId: null,
        accountId: null,
        billNo: null,
        billId: null,
        seqNo: null,
        drCr: null,
        acctId: null,
        acctCode: null,
        calIda: null,
        calCodea: null,
        calIdb: null,
        calCodeb: null,
        calIdc: null,
        calCodec: null,
        calIdd: null,
        calCoded: null,
        dueDate: null,
        srlDesc: null,
        crcyUnit: null,
        frnAmt: null,
        amt: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        updateName: null
      };
      this.detailRepaymentAccountList.splice(row.indexRepayment, 0, item)
    },
    /** 还款删除 */
    handleDeleteList(row) {
      if (row.indexRepayment-1>0){
        this.detailRepaymentAccountList.splice(row.indexRepayment-1,1);     //splice删除方法,1代表删除几个
      }else {
        this.$message.error('至少保留一行！');
        return
      }
      //splice删除方法,1代表删除几个

    },
    /** 报销插行 */
    handleAddAccountList(row) {
      let item = {
        index: row.index + 1,
        id: null,
        companyId: null,
        accountId: null,
        billNo: null,
        billId: null,
        seqNo: null,
        drCr: null,
        acctId: null,
        acctCode: null,
        calIda: null,
        calCodea: null,
        calIdb: null,
        calCodeb: null,
        calIdc: null,
        calCodec: null,
        calIdd: null,
        calCoded: null,
        dueDate: null,
        srlDesc: null,
        crcyUnit: null,
        frnAmt: null,
        amt: null,
        createBy: null,
        createName: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        updateName: null
      };
      this.detailAccountList.splice(row.index, 0, item)
    },
    /** 报销删除 */
    handleDeleteAccountList(row) {
      if (row.index-1>0){
        this.detailAccountList.splice(row.index-1,1);     //splice删除方法,1代表删除几个
      }else {
        this.$message.error('至少保留一行！');
        return
      }
     //splice删除方法,1代表删除几个

    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    /** 借支保存 */
    handleBorrowingAdd() {
      this.$refs["borrowingForm"].validate(valid => {
        if (valid) {
          this.borrowingForm.detailAccountLoanList = this.detailAccountLoanList;
          this.borrowingForm.detailAccountLoanList[0].drCr='D'
          console.log(this.borrowingForm.detailAccountLoanList[0].id);
          if (this.borrowingForm.detailAccountLoanList[0].id != null) {
            updateDetailAccount(this.borrowingForm).then(response => {
              if (!this.valString){
                this.$modal.msgSuccess("修改成功");
                this.getList();
                this.borrowingIf = true
              }else {
                this.$modal.msgSuccess(this.valString);
                this.getList();
                this.borrowingIf = false
              }
              this.open = false;
              this.buttonIf= true
              this.buttonReturnIf=true
            });
          } else {
            addDetailAccount(this.borrowingForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();

              this.buttonIf= true
              this.buttonReturnIf=true
            });
          }
        }
      });
    },
    /** 借支确认 */
    handleBorrowingConfirm() {
      if(this.borrowingForm.status=='04'){
        this.borrowingForm.accountDate = new Date()
        this.borrowingForm.status='10'
        this.valString ="确认成功"
        this.handleBorrowingAdd()
      }else {
        this.$message.error('当前状态无法确认！');
        return
      }
    },
    /** 借支取消确认 */
    handleCancelConfirmation() {
      if(this.borrowingForm.status=='10'){
        this.borrowingForm.accountDate = new Date()
        this.borrowingForm.status='04'
        this.valString ="取消确认成功"
        this.handleBorrowingAdd("取消确认成功")
      }else {
        this.$message.error('当前状态无法取消确认！');
        return
      }
    },
    /** 结案 */
    handleBorrowingCase(){
      if(this.borrowingForm.status=='10'){
        this.borrowingForm.accountDate = new Date()
        this.borrowingForm.status='40'
        this.valString ="结案成功"
        this.handleBorrowingAdd("结案成功")
      }else {
        this.$message.error('当前状态无法结案！');
        return
      }
    },

    /** 取消结案 */
    handleBorrowingCancelCase(){
  if(this.borrowingForm.status=='40'){
    this.borrowingForm.accountDate = new Date()
    this.borrowingForm.status='04'
    this.valString ="取消结案成功"
    this.handleBorrowingAdd()
  }else {
    this.$message.error('当前状态无法取消结案！');
    return
  }
},
    /** 退回 */
    handleBorrowingReturn(){
        this.borrowingForm.accountDate = new Date()
        this.borrowingForm.status='OR'
        this.valString ="退回成功"
        this.handleBorrowingAdd()

    },
    /** 报销保存 */
    handleReimbursementAdd() {
      this.$refs["reimbursementForm"].validate(valid => {
        if (valid) {
          this.reimbursementForm.detailAccountList = this.detailAccountList;
          if (this.reimbursementForm.detailAccountList[0].id != null) {
            updateAccountHost(this.reimbursementForm).then(response => {
              if (!this.ReimbursementVal){
                this.$modal.msgSuccess("修改成功");
                this.getList();
                this.reimbursementIf = true
              }else {
                this.$modal.msgSuccess(this.ReimbursementVal);
                this.getList();
                this.reimbursementIf = false
              }
              this.open = false;

              this.buttonIf= true
              this.buttonReturnIf=true
            });
          } else {
            addAccountHost(this.reimbursementForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.buttonIf= true
              this.buttonReturnIf=true
            });
          }
        }
      });
    },

    /** 报销确认 */
    handleReimbursementConfirm() {
      if(this.reimbursementForm.status=='04'){
        this.reimbursementForm.accountDate = new Date()
        this.reimbursementForm.status='10'
        this.ReimbursementVal = '确认成功'
        this.handleReimbursementAdd()
      }else {
        this.$message.error('当前状态无法确认！');
        return
      }
    },
    /** 报销取消确认 */
    handleReimbursementCancelConfirmation() {
      if(this.reimbursementForm.status=='10'){
        this.reimbursementForm.accountDate = new Date()
        this.reimbursementForm.status='04'
        this.ReimbursementVal = '取消确认成功'
        this.handleReimbursementAdd()
      }else {
        this.$message.error('当前状态无法取消确认！');
        return
      }
    },
    /** 报销结案 */
    handleReimbursementBorrowingCase(){
      if(this.reimbursementForm.status=='10'){
        this.reimbursementForm.accountDate = new Date()
        this.reimbursementForm.status='40'
        this.ReimbursementVal = '结案成功'
        this.handleReimbursementAdd()
      }else {
        this.$message.error('当前状态无法结案！');
        return
      }
    },
    /** 报销取消结案 */
    handleReimbursementBorrowingCancelCase(){
      if(this.reimbursementForm.status=='40'){
        this.reimbursementForm.accountDate = new Date()
        this.reimbursementForm.status='04'
        this.ReimbursementVal = '取消结案成功'
        this.handleReimbursementAdd()
      }else {
        this.$message.error('当前状态无法取消结案！');
        return
      }
    },
    /** 报销退回取消结案 */
    handleReimbursementReturn(){
        this.reimbursementForm.accountDate = new Date()
        this.reimbursementForm.status='OR'
        this.ReimbursementVal = '退回成功'
        this.handleReimbursementAdd()

    },
    /** 还款保存 */
    handleRepaymentAdd() {
      this.$refs["repaymentForm"].validate(valid => {
        if (valid) {
          this.repaymentForm.detailAccountList = this.detailRepaymentAccountList;
          if (this.repaymentForm.detailAccountList[0].id != null) {
            updateAccountHostRepayment(this.repaymentForm).then(response => {
              if (!this.repaymentVal){
                this.$modal.msgSuccess("修改成功");
                this.getList();
                this.repaymentIf = true
              }else {
                this.$modal.msgSuccess(this.repaymentVal);
                this.getList();
                this.repaymentIf = false
              }
              this.open = false;
              this.buttonIf= true
              this.buttonReturnIf=true
            });
          } else {
            addAccountHost(this.repaymentForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.buttonIf= true
              this.buttonReturnIf=true
            });
          }
        }
      });
    },
    /** 还款确认 */
    handleRepaymentConfirm() {
      if(this.repaymentForm.status=='20'){
        this.repaymentForm.accountDate = new Date()
        this.repaymentForm.status='40'
        this.repaymentVal = "确认成功"
        this.handleRepaymentAdd()
      }else {
        this.$message.error('当前状态无法确认！');
        return
      }
    },
    /** 还款取消确认 */
    handleRepaymentCancelConfirmation() {
      if(this.repaymentForm.status=='40'){
        this.repaymentForm.accountDate = new Date()
        this.repaymentForm.status='20'
        this.repaymentVal = "取消确认成功"
        this.handleRepaymentAdd("取消确认成功")
      }else {
        this.$message.error('当前状态无法取消确认！');
        return
      }
    },
  }
};
</script>
<style scoped>
 /deep/ .el-input--medium .el-input__inner {
  height: 28px !important;
  line-height: 36px;
}
</style>
