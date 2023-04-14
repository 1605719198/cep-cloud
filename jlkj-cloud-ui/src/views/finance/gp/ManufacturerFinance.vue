<template>
<div>
  <el-form :rules="rules"  :model="fcForm" ref="fcForm" size="small" :inline="true" v-show="showSearch"  label-width="164px">
    <el-row>
      <el-col :span="12" >
        <el-form-item label="中文名称:" prop="manufacturerChineseName"  >
          {{this.manufacturerChineseName}}
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="厂商编号:" prop="manufacturerId"  >
          {{this.manufacturerId}}
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="汇款银行(1):" prop="remitBank1"  >
          <el-input v-model="fcForm.remitBank1"    style="width: 178px"  ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="汇款账号(1):" prop="remitAccount1"  >
          <el-input v-model="fcForm.remitAccount1"
                    style="width: 178px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="20">
        <el-form-item label="汇款账号名称(1):" prop="remitName1"   >
          <el-input v-model="fcForm.remitName1"
                    style="width: 713px;"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="行号(1):" prop="bankNum1"  >
          <el-input v-model="fcForm.bankNum1"
                    style="width: 713px;"   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="受款银行汇入地点(1):">
        </el-form-item>
        <el-form-item label="省" label-width="16px" prop="mainAreaId1">
          <el-select v-model="fcForm.mainAreaId1" style="width: 100px"placeholder="请选择">
            <el-option
              v-for="dict in dict.type.gp_main_areaid"
              :key="dict.value"
              :label="dict.value+'_'+dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item   prop="province1" label-width="16px">
          <el-select v-model="fcForm.province1" style="width: 100px"placeholder="请选择">
            <el-option
              v-for="dict in dict.type.gp_main_areaid"
              :key="dict.value"
              :label="dict.value+'_'+dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="市" label-width="12px"  prop="ville1">
          <el-input v-model="fcForm.ville1"
                    style="width: 100px;" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="地址:" prop="acceptRemitBank1">
          <el-input v-model="fcForm.acceptRemitBank1"
                    style="width: 195px;"     ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="汇款银行(2):" prop="remitBank2"  >
          <el-input v-model="fcForm.remitBank2"    style="width: 320px;" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="汇款账号(2):" prop="remitAccount2"  >
          <el-input v-model="fcForm.remitAccount2"
                    style="width: 185px;"      ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="汇款账号名称(2):" prop="remitName2"  >
          <el-input v-model="fcForm.remitName2"
                    style="width: 713px;" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="行号(2):" prop="bankNum2"  >
          <el-input v-model="fcForm.bankNum2"
                    style="width: 713px;"     ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="受款银行汇入地点(2):">
        </el-form-item>
        <el-form-item label="省" label-width="16px" prop="mainAreaId2">
          <el-select v-model="fcForm.mainAreaId2" style="width: 100px"placeholder="请选择">
            <el-option
              v-for="dict in dict.type.gp_main_areaid"
              :key="dict.value"
              :label="dict.value+'_'+dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item   prop="province2" label-width="16px">
          <el-input v-model="fcForm.province2" style="width: 100px;"></el-input>
        </el-form-item>
        <el-form-item label="市" label-width="12px"  prop="ville3">
          <el-input v-model="fcForm.ville2"
                    style="width: 100px;" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="地址:" prop="acceptRemitBank2">
          <el-input v-model="fcForm.acceptRemitBank2"
                    style="width: 195px;"     ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="汇款账号名称(3):" prop="remitName3"  >
          <el-input v-model="fcForm.remitName3"
                    style="width: 713px;"  ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="行号(3):" prop="bankNum3"  >
          <el-input v-model="fcForm.bankNum3"
                    style="width: 713px;"   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="受款银行汇入地点(3):">
        </el-form-item>
        <el-form-item label="省" label-width="16px" prop="mainAreaId3">
          <el-select v-model="fcForm.mainAreaId3" style="width: 100px"placeholder="请选择">
            <el-option
              v-for="dict in dict.type.gp_main_areaid"
              :key="dict.value"
              :label="dict.value+'_'+dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
          <el-form-item   prop="province3" label-width="16px">
          <el-input v-model="fcForm.province3" style="width: 100px;"></el-input>
          </el-form-item>
        <el-form-item label="市" label-width="12px"  prop="ville3">
          <el-input v-model="fcForm.ville3"
                    style="width: 100px;" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="地址:" prop="acceptRemitBank3">
          <el-input v-model="fcForm.acceptRemitBank3"
                    style="width: 190px;" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="付款方式:" prop="payType"  >
          <el-select v-model="fcForm.payType" style="width: 190px"placeholder="请选择">
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
        <el-form-item label="电子邮件:" prop="Email"  >
          <el-input v-model="fcForm.Email"
                    style="width: 180px;"   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="联络人:" prop="contact"  >
          <el-input v-model="fcForm.contact"
                    style="width: 190px;"    ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="联络电话:" prop="officeTelephoneNo"  >
          <el-input v-model="fcForm.officeTelephoneNo"
                    style="width: 180px;"   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="行动电话:" prop="mobile"  >
          <el-input v-model="fcForm.mobile"
                    style="width: 190px;"     ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="传真号码:" prop="fax"  >
          <el-input v-model="fcForm.fax"
                    style="width: 180px;"        ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="邮寄支票地址:" prop="billSendZip"  >
          <el-input v-model="fcForm.billSendZip"    style="width: 190px;"   ></el-input>
        </el-form-item>
          <el-form-item  prop="billSendAddr"  >
          <el-input v-model="fcForm.billSendAddr"
                    style="width: 520px;"     ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="建立人员:" prop="createUser"  >

          {{fcForm.createUser}}
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="建立人员日期:" prop="recordCrtDate"  >
          {{fcForm.recordCrtDate}}

        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="修改者:" prop="updateUser"  >
          {{fcForm.updateUser}}

        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="修改日期:" prop="update"  >
          {{fcForm.update}}
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
  <div class="el-dialog__footer" style="padding:20px 0 0 0;">
          <span slot="footer" class="dialog-footer">
                <el-button type="primary"
                           @click="submitForm('fcForm')">确 定</el-button>
                 <el-button @click="cancel">取 消</el-button>
          </span>
  </div>
</div>
</template>

<script>
import {queryFc,updateManufacturerBasicFc} from "@/api/finance/gp/base1";
import {validateContacts} from "../../../utils/jlkj";

export default {
  name: "bbb",
  dicts: ['gp_main_areaid','gp_customer_payway'],
  data(){
    return{
      statusTabs:false,
      manufacturerId:"",
      manufacturerChineseName:"",
      // 显示搜索条件
      showSearch: true,
      visible: false,
      sortOptions: [],
      fcForm: {
        remitName1:'',
        mainAreaId1:'',
        bankNum1:'',
        province1:'',
        ville1:'',
        acceptRemitBank1:'',
        remitBank2:'',
        remitAccount2:'',
        remitName2:'',
        bankNum2:'',
        mainAreaId2:'',
        province2:'',
        ville2:'',
        acceptRemitBank2:'',
        remitBank3:'',
        remitAccount3:'',
        remitName3:'',
        bankNum3:'',
        mainAreaId3:'',
        province3:'',
        ville3:'',
        acceptRemitBank3:'',
        payType:'',
        Email:'',
        contact:'',
        fax:'',
        officeTelephoneNo:'',
        billSendZip:'',
        billSendAddr:''
      },
      fctForm:{},
      rules: {
        remitBank1: [
          { pattern:/^[0-9]*$/, message: "请输入正确的汇款银行(1)", trigger: "blur"},
          {max: 60, message: '最大长度60个字符', trigger: 'blur'}],
        remitAccount1: [
          { pattern:/^[0-9]*$/, message: "请输入正确的汇款账号(1)", trigger: "blur"},
          {max: 30, message: '最大长度30个字符', trigger: 'blur'}],
        remitName1: [
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的汇款账号名称(1)", trigger: "blur"},
          {max: 30, message: '最大长度30个字符', trigger: 'blur'}],
        bankNum1: [
          { pattern:/^[0-9]*$/, message: "请输入正确的行号(1)", trigger: "blur"},
          {max: 30, message: '最大长度30个字符', trigger: 'blur'}],
        ville1 :[
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的市", trigger: "blur"},
          {max: 10, message: '最大长度10个字符', trigger: 'blur'}],
        acceptRemitBank1: [
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的地址", trigger: "blur"},
          {max: 100, message: '最大长度100个字符', trigger: 'blur'}],

        remitBank2: [
          { pattern:/^[0-9]*$/, message: "请输入正确的汇款银行(2)", trigger: "blur"},
          {max: 60, message: '最大长度60个字符', trigger: 'blur'}],
        remitAccount2: [
          { pattern:/^[0-9]*$/, message: "请输入正确的汇款账号(2)", trigger: "blur"},
          {max: 30, message: '最大长度30个字符', trigger: 'blur'}],
        remitName2: [
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的汇款账号名称(2)", trigger: "blur"},
          {max: 30, message: '最大长度30个字符', trigger: 'blur'}],
        bankNum2: [
          { pattern:/^[0-9]*$/, message: "请输入正确的行号(2)", trigger: "blur"},
          {max: 30, message: '最大长度30个字符', trigger: 'blur'}],
        ville2 :[
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的市", trigger: "blur"},
          {max: 10, message: '最大长度10个字符', trigger: 'blur'}],
        acceptRemitBank2: [
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的地址", trigger: "blur"},
          {max: 100, message: '最大长度100个字符', trigger: 'blur'}],


        remitBank3: [
          { pattern:/^[0-9]*$/, message: "请输入正确的汇款银行(3)", trigger: "blur"},
          {max: 60, message: '最大长度60个字符', trigger: 'blur'}],
        remitAccount3: [
          { pattern:/^[0-9]*$/, message: "请输入正确的汇款账号(3)", trigger: "blur"},
          {max: 30, message: '最大长度30个字符', trigger: 'blur'}],
        remitName3: [
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的汇款账号名称(3)", trigger: "blur"},
          {max: 30, message: '最大长度30个字符', trigger: 'blur'}],
        bankNum3: [
          { pattern:/^[0-9]*$/, message: "请输入正确的行号(3)", trigger: "blur"},
          {max: 30, message: '最大长度30个字符', trigger: 'blur'}],
        ville3 :[
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的市", trigger: "blur"},
          {max: 10, message: '最大长度10个字符', trigger: 'blur'}],
        acceptRemitBank3: [
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的地址", trigger: "blur"},
          {max: 100, message: '最大长度100个字符', trigger: 'blur'}],
        Email: [
          { pattern:/^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/, message: "请输入正确的邮箱", trigger: "blur"},
          {max: 60, message: '最大长度60个字符', trigger: 'blur'}],
        contact: [
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的联络人", trigger: "blur"},
          {max: 30, message: '最大长度30个字符', trigger: 'blur'}],
        officeTelephoneNo: [
          { pattern:/^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/, message: "请输入正确的电话号码", trigger: "blur"},
          {max: 30, message: '最大长度30个字符', trigger: 'blur'}],
        mobile: [
          { pattern:/^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/, message: "请输入正确的行动电话", trigger: "blur"},
          {max: 30, message: '最大长度30个字符', trigger: 'blur'}],
        fax: [
          { pattern:/^(?:\d{3,4}-)?\d{7,8}(?:-\d{1,6})?$/, message: "请输入正确的传真号码", trigger: "blur"},
          {max: 30, message: '最大长度为30个字符', trigger: 'blur'}],
        billSendZip: [
          { pattern:/^[0-9]*$/, message: "请输入正确的邮寄支票地址", trigger: "blur"},
          {max: 10, message: '最大长度10个字符', trigger: 'blur'}],
        billSendAddr: [
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的发票寄送地址", trigger: "blur"},
          {max: 100, message: '最大长度100个字符', trigger: 'blur'}],
      },

      from:{},
      dataRule: {},
      flag: true
    }
  },
  created() {

  },
  methods: {
    cancel() {
      this.$emit('getLists',false);
      this.reset()
    },
    /** 提交按钮 */
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.fcForm.manufacturerChineseName =   this.manufacturerChineseName
          this.fcForm.manufacturerId = this.manufacturerId
            updateManufacturerBasicFc(this.fcForm).then(response => {
              this.$modal.msgSuccess("报支关系修改成功");
              this.statusTabsFc=true
            });
          }
      });
    },
    initFc(manufacturerId,manufacturerChineseName) {
      this.manufacturerChineseName=manufacturerChineseName;
      this.manufacturerId=manufacturerId;
      this.visible = true
      queryFc(manufacturerId).then(response => {
        if (response.data!=null){
          this.fcForm = response.data

        }else {
          this.fcForm ={
            remitName1:'',
            mainAreaId1:'',
            bankNum1:'',
            province1:'',
            ville1:'',
            acceptRemitBank1:'',
            remitBank2:'',
            remitAccount2:'',
            remitName2:'',
            bankNum2:'',
            mainAreaId2:'',
            province2:'',
            ville2:'',
            acceptRemitBank2:'',
            remitBank3:'',
            remitAccount3:'',
            remitName3:'',
            bankNum3:'',
            mainAreaId3:'',
            province3:'',
            ville3:'',
            acceptRemitBank3:'',
            payType:'',
            Email:'',
            contact:'',
            fax:'',
            officeTelephoneNo:'',
            billSendZip:'',
            billSendAddr:''
          }
        }
      });

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
  width: 2250px !important;
}
.dialog-wrap {
  ::v-deep .el-form-item__label {
    width: 86px !important;
    text-align: right;
  }
}

</style>
