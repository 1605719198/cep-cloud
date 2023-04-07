<template>
<div>
  <el-form :rules="rules" :model="stForm" ref="stForm" size="small" :inline="true" v-show="showSearch" label-width="115px">
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
        <el-form-item label="联系人:" prop="custOwner">
          <el-input v-model="stForm.custOwner"
                   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="发票传递方式:" prop="billTransType">
          <el-select v-model="stForm.billTransType"  style="width: 185px"    placeholder="请选择发票传递方式">
            <el-option
              v-for="dict in dict.type.gp_customer_payway"
              :key="dict.value"
              :label="dict.value+'_'+dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="电话号码:" prop="officeTelephoneNo">
          <el-input v-model="stForm.officeTelephoneNo"
                   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="传真号码:" prop="fax">
          <el-input v-model="stForm.fax"
                   ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="24">

        <el-form-item label="中文地址:" prop="addr">
          <el-input v-model="stForm.addr"
                    style="width: 720px"></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="英文地址:" prop="engAddr">
          <el-input v-model="stForm.engAddr"    style="width: 720px"  ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="24">
<!--        <el-form-item label="联络中文地址:" prop="contactZip">
          <el-select v-model="stForm.contactZip"  style="width: 100px"
                     placeholder="请选择邮编">
            <el-option
              v-for="dict in dict.type.primary_area_code"
              :key="dict.value"
              :label="dict.value+'_'+dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>-->
        <el-form-item label="网络中文地址:" prop="contactAddr">
          <el-input v-model="stForm.contactAddr"
                    style="width: 720px"    ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="网络英文地址:" prop="contactEngAddr">
          <el-input v-model="stForm.contactEngAddr"
                    style="width: 720px"   ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="开户银行:" prop="remitBank1">
          <el-input v-model="stForm.remitBank1"
                    ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="开户银行账号:" prop="remitAccount1">
          <el-input v-model="stForm.remitAccount1"
                    ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="征信:" prop="credit">
          <el-select v-model="stForm.credit" style="width: 185px"   placeholder="请选择征信">
            <el-option
              v-for="dict in dict.type.gp_credit_type"
              :key="dict.value"
              :label="dict.value+'_'+dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="企业代码:" prop="taxNo">
          <el-input v-model="stForm.taxNo"
                  ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="户号:" prop="lastUser">
          <el-input v-model="stForm.lastUser"
                    ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="电子邮件:" prop="email">
          <el-input v-model="stForm.email"
                    ></el-input>
        </el-form-item>
      </el-col>

    </el-row>
  </el-form>
  <div class="el-dialog__footer" style="padding:20px 0 0 0;">
          <span slot="footer" class="dialog-footer">
                <el-button type="primary"
                           @click="submitForm('stForm')">确 定</el-button>
          </span>
  </div>
</div>
</template>

<script>
import {querySt,updateManufacturerBasicSt} from "@/api/finance/gp/base1";

export default {
  name: "fff",
  dicts: [ 'gp_customer_payway', 'gp_credit_type','primary_area_code'],
  data(){
    return{
      manufacturerId:"",
      manufacturerChineseName:"",
      // 显示搜索条件
      showSearch: true,
      visible: false,
      sortOptions: [],
      stForm: {
        vendorName:'',
        enterpriseNo:'',
        custOwner:'',
        billTransType:'',
        addr:'',
        zip:'',
        engAddr:'',
        contactEngAddr:'',
        lastUser:''
      },
      rules:{
        custOwner: [
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的法人代表", trigger: "blur"},
          {max: 30, message: '最大长度为30个字符', trigger: 'blur'}],
        officeTelephoneNo: [
          { pattern: /^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/, message: "请输入正确的电话号码", trigger: "blur"},
          {max: 30, message: '最大长度为30个字符', trigger: 'blur'}],
        fax: [
          { pattern:/^(?:\d{3,4}-)?\d{7,8}(?:-\d{1,6})?$/, message: "请输入正确的传真号码", trigger: "blur"},
          {max: 30, message: '最大长度为30个字符', trigger: 'blur'}],
        zip: [
          { pattern:/^[0-9]*$/, message: "请输入正确的邮递区号", trigger: "blur"},
          {max: 10, message: '最大长度为10个字符', trigger: 'blur'}],
        addr: [
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的中文地址", trigger: "blur"},
          {max: 200, message: '最大长度为200个字符', trigger: 'blur'}],
        engAddr: [
          { pattern: /^[A-Za-z]+$/, message: "请输入正确的英文地址", trigger: "blur"},
          {max: 200, message: '最大长度为200个字符', trigger: 'blur'}],
        contactAddr: [
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的联络中文地址", trigger: "blur"},
          {max: 200, message: '最大长度为200个字符', trigger: 'blur'}],
      },
      dataRule: {},

    }
  },
  created() {

  },
  methods: {
    /** 提交按钮 */
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.stForm.manufacturerChineseName =   this.manufacturerChineseName
          this.stForm.manufacturerId = this.manufacturerId
            updateManufacturerBasicSt(this.stForm).then(response => {
              this.$modal.msgSuccess("承运关系修改成功");
              this.statusTabs=true
              this.$emit('getLists',this.statusTabs);
            });
          }
      });

    },
    initSt(manufacturerId,manufacturerChineseName) {
      this.visible = true
      this.manufacturerChineseName = manufacturerChineseName;
      this.manufacturerId = manufacturerId;
      querySt(manufacturerId).then(response => {
        this.stForm = response.data
        if (response.data!=null){
          this.stForm = response.data

        }else {
          this.stForm = {
            vendorName:'',
            enterpriseNo:'',
            custOwner:'',
            billTransType:'',
            addr:'',
            zip:'',
            engAddr:'',
            contactEngAddr:'',
            lastUser:'',
            email:""
          }
        }
      });
    },
  }
}

</script>


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
