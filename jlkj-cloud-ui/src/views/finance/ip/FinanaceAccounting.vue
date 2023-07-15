<template>
  <div v-if="open">
    <el-dialog class="customDialogStyle" :data="openDialog.type" title="帐务资料" :visible.sync="open" width="400px" append-to-body>
      <el-form ref="form" :model="accountform" :rules="rules" label-width="80px">
        <el-form-item label="产副品" prop="productCode">
          <el-input v-model="accountform.productCode" :disabled="true"/>
        </el-form-item>
        <el-form-item label="科目名称" prop="accountName">
          <el-input v-model="accountform.accountName" :disabled="true"/>
        </el-form-item>
        <el-form-item label="成本科目" prop="costCode">
          <el-input v-model="accountform.costCode" placeholder="请输入成本科目" @keyup.enter.native="handlerDeptList"  :disabled="true" >
            <el-button slot="append" icon="el-icon-search" @click.native="handlerDeptList">
            </el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="会计科目" prop="accountCode">
          <el-input v-model="accountform.accountCode" placeholder="请输入会计科目" @keyup.enter.native="handlerAccountList"  :disabled="true" >
            <el-button slot="append" icon="el-icon-search" @click.native="handlerAccountList">
            </el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="户号" prop="accountIdIdex">
          <el-input v-model="accountform.accountIdIdex"/>
        </el-form-item>
        <el-form-item label="参号" prop="accountRefnoIndex">
          <el-input v-model="accountform.accountRefnoIndex"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="padding:20px 0 0 0;">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
      <FinanceCostAllTree :getTreeCost="getTreeCost" :productCodeTree="productCodeTree"  ref="productCodeTree" @closeFinanceCodeDialog="closeFinanceCodeDialog" ></FinanceCostAllTree>
      <FinanceAccountingAllTree :getTreeAccounting="getTreeAccounting" :productAccountTree="productAccountTree" ref="productAccountTree" @closeFinanceAccountDialog="closeFinanceAccountDialog"></FinanceAccountingAllTree>
    </el-dialog>
  </div>


</template>

<script>
import FinanceCostAllTree from '@/components/finance-manufacturer-tree/FinanceCostAllTree'
import FinanceAccountingAllTree from '@/components/finance-manufacturer-tree/FinanceAccountingAllTree'
export default {
  name: "FinanaceAccounting",
  components: {
    FinanceCostAllTree,
    FinanceAccountingAllTree
  },
  data() {
    return {
      openDialog: {
        open: false,
        type: '',
        title: '',
        width: '500px',
        data: {},
      },
      //成本科目是否编辑
      costCodePop:'',

      //成本科目树弹窗
      productCodeTree:false,
      //会计树弹窗
      productAccountTree:false,
      // 是否显示弹出层
      open: false,
      // 表单参数
      accountform: {
        Id: '',
        productCode: '',
        accountName: '',
        costCode:'',
        accountCode: '',
        accountIdIdex: '',
        accountRefnoIndex:''
      },
      rules: {
        costCode: [
          {required: true, message: '请输入成本科目', trigger: 'blur'},
        ],
        accountCode: [
          {required: true, message: '请输入会计科目', trigger: 'blur'},
        ],
        accountIdIdex: [
          {required: true, message: '请输入户号', trigger: 'blur'}
        ],
        accountRefnoIndex: [
          {required: true, message: '请输入参号', trigger: 'blur'}
        ]
      },
    }
  },
  methods: {
    //财务查询
    AddAccount(index, account, produce) {
      this.open = true;
      this.accountform.Id= index
      this.accountform.accountName = account[index].accountName
      this.accountform.productCode = produce
      this.costCodePop = account[index].dataB
    },
    //财务修改查询
    UpAccount(index, account, produce) {
      this.open = true;
      this.accountform.Id= index
      this.accountform.accountName = account[index].accountName
      this.accountform.productCode = produce


      this.accountform.costCode = account[index].costCode
      this.accountform.accountCode = account[index].accountCode
      this.accountform.accountIdIdex = account[index].accountIdIdex
      this.accountform.accountRefnoIndex = account[index].accountRefnoIndex
      this.costCodePop = account[index].dataB

    },
    submitForm() {
      this.$emit('account',this.accountform)
      this.open = false;
      this.accountform = {}
    },
    cancel() {
      this.open = false;
      this.accountform = {}
    },
    handlerDeptList(){
      if (this.costCodePop == "WCE"){
        this.productAccountTree = false
        this.productCodeTree = true
      }
    },
    handlerAccountList(){
      if (this.costCodePop == "ACCT"){
        this.productCodeTree = false
        this.productAccountTree = true
      }
    },
    getTreeCost(data){
      this.accountform.costCode = data.costCode
      this.accountform.accountCode = data.accountCode
      this.accountform.accountIdIdex = data.idTypeStus
      this.accountform.accountRefnoIndex = data.refTypeStus
    },
    getTreeAccounting(data){
      this.accountform.accountCode = data.accountCode
      this.accountform.accountIdIdex = data.idTypeStus
      this.accountform.accountRefnoIndex = data.refTypeStus
    },
    closeFinanceAccountDialog(){
      this.productAccountTree=false;
    },
    closeFinanceCodeDialog(data){
      this.accountform.accountIdIdex = data.idTypeStus
      this.accountform.accountRefnoIndex = data.refTypeStus
      this.productCodeTree=false;
    }
  }
}
</script>
