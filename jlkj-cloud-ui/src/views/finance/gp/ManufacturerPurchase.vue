<template>
<div>
  <el-form :rules="rules" :model="mpForm" ref="mpForm" size="small" :inline="true" v-show="showSearch" label-width="115px">
    <el-row>
      <el-col :span="12">
        <el-form-item label="中文名称:" prop="manufacturerChineseName">
          {{this.manufacturerChineseName}}
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="厂商编号:" prop="manufacturerId">
          {{this.manufacturerId}}
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="统一社信用代码:" prop="taxNo">
          {{this.taxNo}}

        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="厂商性质:" prop="vendorType">
          <el-select v-model="mpForm.vendorType"  style="width: 220px"  placeholder="请选择">
            <el-option
              v-for="dict in dict.type.gp_purchase_vendortype"
              :key="dict.value"
              :label="dict.value+'_'+dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
<!--      <el-col :span="12">
        <el-form-item label="厂商性质代码:" prop="specialCode">
          <el-input v-model="mpForm.specialCode"
                    style="width: 220px" ></el-input>
        </el-form-item>
      </el-col>-->
      <el-col :span="24">
<!--        <el-form-item label="发票地址:" prop="billZipCode">
          <el-input v-model="mpForm.billZipCode"   style="width: 100px" ></el-input>
        </el-form-item>-->
        <el-form-item label="发票地址:"  prop="billAddress">
          <el-input v-model="mpForm.billAddress"   style="width: 757px" ></el-input>
          <el-button  icon="el-icon-download" @click="billAddressRenewal(mpForm.billAddress)"></el-button>
        </el-form-item>
      </el-col>
      <el-col :span="24">
<!--        <el-form-item label="联络地址:" prop="contactZip">
          <el-input v-model="mpForm.contactZip"   style="width: 100px"></el-input>
        </el-form-item>-->
        <el-form-item  label="联络地址:"  prop="contactAddr">
          <el-input v-model="mpForm.contactAddr"    style="width: 757px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="网址:" prop="web">
          <el-input v-model="mpForm.web"   style="width: 757px"  ></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="传真:" prop="fax">
            <el-input v-model="mpForm.fax"
                      style="width: 220px" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="关系企业:" prop="corpGroup">
            <el-input v-model="mpForm.corpGroup"
                      style="width: 220px" ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-col :span="12">
        <el-form-item label="厂商资料来源:" prop="source">
          <el-input v-model="mpForm.source"
                    style="width: 220px"   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="厂商原来名称:" prop="originName">
          <el-input v-model="mpForm.originName"
                    style="width: 220px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="停权期限(起):" prop="stopPeriodFrom">
          <el-date-picker clearable size="small"
                          style="width: 220px"
                          v-model="mpForm.stopPeriodFrom"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择停权期限(起)">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="停权期限(迄):" prop="stopPeriodTo">
          <el-date-picker clearable size="small"
                          style="width: 220px"
                          v-model="mpForm.stopPeriodTo"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择停权期限(起)">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="主要产品说明:" prop="productDescp">
          <el-input style="width: 757px"  v-model="mpForm.productDescp" type="textarea"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="10">
            <el-form-item label="交易不良记录:" prop="badHistory">
              <el-input style="width: 318px"  v-model="mpForm.badHistory"  type="textarea"></el-input>
            </el-form-item>
          </el-col>
      <el-col :span="14">
            <el-form-item label="特别备注:" prop="memo">
              <el-input style="width: 315px"  v-model="mpForm.memo"type="textarea"></el-input>
            </el-form-item>
          </el-col>
    </el-row>
    <el-divider content-position="center">厂商采购关系联系人信息</el-divider>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddFinanceGpPurchaseLink">添加</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteFinanceGpPurchaseLink">删除</el-button>
      </el-col>
    </el-row>
    <el-table :data="tfinanceGpPurchaseLinkList"
              :row-class-name="rowFinanceGpPurchaseLinkIndex" @selection-change="handleFinanceGpPurchaseLinkSelectionChange" ref="financeGpPurchaseLink">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="序号" align="center" prop="index" width="50"/>
      <el-table-column label="公司" prop="compId" width="150">
        <template slot-scope="scope">
          <el-input v-model="scope.row.compId" placeholder="请输入公司" />
        </template>
      </el-table-column>
      <el-table-column label="联络人" prop="contact" width="150">
        <template slot-scope="scope">
          <el-input v-model="scope.row.contact" placeholder="请输入联络人" />
        </template>
      </el-table-column>
      <el-table-column label="部门" prop="dept" width="150">
        <template slot-scope="scope">
          <el-input v-model="scope.row.dept" placeholder="请输入部门" />
        </template>
      </el-table-column>
      <el-table-column label="职称" prop="title" width="150">
        <template slot-scope="scope">
          <el-input v-model="scope.row.title" placeholder="请输入职称" />
        </template>
      </el-table-column>
      <el-table-column label="电话" prop="tel" width="150">
        <template slot-scope="scope">
          <el-input v-model="scope.row.tel" placeholder="请输入电话" />
        </template>
      </el-table-column>
      <el-table-column label="行动电话" prop="moblie" width="150">
        <template slot-scope="scope">
          <el-input v-model="scope.row.moblie" placeholder="请输入行动电话" />
        </template>
      </el-table-column>
      <el-table-column label="邮件" prop="email" width="150">
        <template slot-scope="scope">
          <el-input v-model="scope.row.email" placeholder="请输入邮件" />
        </template>
      </el-table-column>
    </el-table>
  </el-form>

  <div class="el-dialog__footer" style="padding:20px 0 0 0;">
          <span slot="footer" class="dialog-footer">
                <el-button type="primary"
                           @click="submitForm('mpForm')" >确 定</el-button>
          </span>
  </div>
</div>
</template>

<script>
import { queryMp,updateManufacturerBasicsMp} from "@/api/finance/gp/base1";


export default {
  name: "ccc",
  dicts: ['gp_purchase_vendortype',],
  data(){
    return{
      billAddressName:'',
      manufacturerId:"",
      manufacturerChineseName:"",
      taxNo:"",
      // 厂商采购关系联系人表格数据
      tfinanceGpPurchaseLinkList: [],
      // 子表选中数据
      checkedFinanceGpPurchaseLink: [],
      // 显示搜索条件
      showSearch: true,
      visible: false,
      sortOptions: [],
      mpForm: {
        vendorType:'',
        specialCode:'',
        contactZip:'',
        contactAddr:'',
        contact1:'',
        contact2:'',
        contact3:'',
        contact4:'',
        department1:'',
        department2:'',
        department3:'',
        department4:'',
        title1:'',
        title2:'',
        title3:'',
        title4:'',
        officeTelephoneNo1:'',
        officeTelephoneNo2:'',
        officeTelephoneNo3:'',
        officeTelephoneNo4:'',
        mobile1:'',
        mobile2:'',
        mobile3:'',
        mobile4:'',
        email1:'',
        email2:'',
        email3:'',
        email4:'',
        corpGroup:'',
        originName:'',
        stopPeriodFrom:'',
        stopPeriodTo:'',
        productDescp:'',
        badHistory:''

      },
  rules:{
    specialCode: [
      { pattern:/^[0-9]*$/, message: "请输入正确的厂商性质代码", trigger: "blur"},
      {max: 10, message: '最大长度为10个字符', trigger: 'blur'}],
    billZipCode: [
      { pattern:/^[0-9]*$/, message: "请输入正确的发票地址(区号)", trigger: "blur"},
      {max: 10, message: '最大长度为10个字符', trigger: 'blur'}],
    billAddress: [
      { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的发票地址(中文)", trigger: "blur"},
      {max: 101, message: '最大长度为101个字符', trigger: 'blur'}],
    contactZip: [
      { pattern:/^[0-9]*$/, message: "请输入正确的联络地址(区号)", trigger: "blur"},
      {max: 10, message: '最大长度为10个字符', trigger: 'blur'}],
    contactAddr: [
      { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的联络地址(中文)", trigger: "blur"},
      {max: 10, message: '最大长度为10个字符', trigger: 'blur'}],
    web: [
      { pattern: /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/, message: "请输入正确的网址", trigger: "blur"},
      {max: 60, message: '最大长度为60个字符', trigger: 'blur'}],
    fax: [
      { pattern:/^(?:\d{3,4}-)?\d{7,8}(?:-\d{1,6})?$/, message: "请输入正确的传真号码", trigger: "blur"},
      {max: 30, message: '最大长度为30个字符', trigger: 'blur'}],
    corpGroup:[
      { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的关系企业", trigger: "blur"},
      {max: 60, message: '最大长度为60个字符', trigger: 'blur'}],
    source:[
      { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的厂商资料来源", trigger: "blur"},
      {max: 60, message: '最大长度为60个字符', trigger: 'blur'}],
    originName:[
      { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的厂商原来名称", trigger: "blur"},
      {max: 60, message: '最大长度为60个字符', trigger: 'blur'}],
  },
      mppForm:{},
      dataRule: {}
    }
  },
  created() {

  },
  methods: {
    billAddressRenewal(billAddress){

      this.mpForm.contactAddr = billAddress
    },
    // 取消按钮
    cancel() {
      this.$emit('getLists');
    },
    /** 提交按钮 */
    submitForm(formName) {
      this.mpForm.financeGpPurchaseLinkList = this.tfinanceGpPurchaseLinkList;
      console.log(this.mpForm);
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.mpForm.manufacturerChineseName =   this.manufacturerChineseName
          this.mpForm.manufacturerId = this.manufacturerId
          this.mpForm.taxNo = this.taxNo
            updateManufacturerBasicsMp(this.mpForm).then(response => {
              this.$modal.msgSuccess("采购关系修改成功");
              this.statusTabs=true
              this.$emit('getLists',this.statusTabs);
            });
          }

      });

    },

    initMP(manufacturerId,manufacturerChineseName,taxNo,billAddress) {
      this.manufacturerChineseName=manufacturerChineseName;
      this.manufacturerId=manufacturerId;
      this.taxNo  = taxNo
      this.billAddress = billAddress
      this.visible = true
      queryMp(manufacturerId).then(response => {
        if (response.data!=null){
          this.mpForm = response.data
        }else {
          this.mpForm ={
            vendorType:'',
            specialCode:'',
            contactZip:'',
            contactAddr:'',
            contact1:'',
            contact2:'',
            contact3:'',
            contact4:'',
            department1:'',
            department2:'',
            department3:'',
            department4:'',
            title1:'',
            title2:'',
            title3:'',
            title4:'',
            officeTelephoneNo1:'',
            officeTelephoneNo2:'',
            officeTelephoneNo3:'',
            officeTelephoneNo4:'',
            mobile1:'',
            mobile2:'',
            mobile3:'',
            mobile4:'',
            email1:'',
            email2:'',
            email3:'',
            email4:'',
            corpGroup:'',
            originName:'',
            stopPeriodFrom:'',
            stopPeriodTo:'',
            productDescp:'',
            badHistory:'',
            memo:'',
            billAddress:''
          }
        }
        if ( response.data.financeGpPurchaseLinkList!=null){
          this.tfinanceGpPurchaseLinkList = response.data.financeGpPurchaseLinkList
        }
        console.log(this.mpForm.billAddress);
        if (this.mpForm.billAddress == ""){
          this.mpForm.billAddress =  this.billAddress
        }


      });

    },
    /** 厂商采购关系联系人添加按钮操作 */
    handleAddFinanceGpPurchaseLink() {
      let obj = {};
      obj.uuid ="";
      obj.compId = "";
      obj.contact = "";
      obj.manufacturerId = this.manufacturerId;
      obj.dept = "";
      obj.title = "";
      obj.tel = "";
      obj.moblie = "";
      obj.email = "";
      this.tfinanceGpPurchaseLinkList.push(obj);
    },
    /** 厂商采购关系联系人删除按钮操作 */
    handleDeleteFinanceGpPurchaseLink() {
      if (this.checkedFinanceGpPurchaseLink.length == 0) {
        this.$modal.msgError("请先选择要删除的厂商采购关系联系人数据");
      } else {
        const tfinanceGpPurchaseLinkList = this.tfinanceGpPurchaseLinkList;
        const checkedFinanceGpPurchaseLink = this.checkedFinanceGpPurchaseLink;
        this.tfinanceGpPurchaseLinkList = tfinanceGpPurchaseLinkList.filter(function(item) {
          return checkedFinanceGpPurchaseLink.indexOf(item.index) == -1
        });
      }
    },
    /** 厂商采购关系联系人序号 */
    rowFinanceGpPurchaseLinkIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 复选框选中数据 */
    handleFinanceGpPurchaseLinkSelectionChange(selection) {
      this.checkedFinanceGpPurchaseLink = selection.map(item => item.index)
    },
  }
}
</script>

<style scoped>

</style>
<style lang="scss" scoped>

.el-form-item {
  margin-bottom: 0;
}
::v-deep .saveAsDialog {
  width: 1250px !important;
}
.dialog-wrap {
  ::v-deep .el-form-item__label {
    width: 86px !important;
    text-align: right;
  }
}
</style>
