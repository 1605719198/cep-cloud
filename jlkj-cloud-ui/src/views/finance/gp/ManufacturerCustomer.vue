<template>
  <div>
    <el-form :rules="rules" :model="soForm" ref="soForm" size="small" :inline="true" v-show="showSearch" label-width="115px">
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
<!--        <el-col :span="24">
          <el-form-item label="客户简称:" prop="customerName">
            <el-input v-model="soForm.customerName"
                 style="width: 720px"   ></el-input>
          </el-form-item>
        </el-col>-->
        <el-col :span="12">
          <el-form-item label="开户银行:" prop="remitBank1">
            <el-input v-model="soForm.remitBank1"
                      type="textarea" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label=" 开户银行账号:" prop="remitAccount1">
            <el-input v-model="soForm.remitAccount1"
                      type="textarea"  ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="联络人:" prop="customerContract">
            <el-input v-model="soForm.customerContract"
                    ></el-input>
          </el-form-item>
        </el-col>
<!--        <el-col :span="12">
          <el-form-item label="联络人职称:" prop="customerPost">
            <el-input v-model="soForm.customerPost"
                    ></el-input>
          </el-form-item>
        </el-col>-->
        <el-col :span="12">
          <el-form-item label="行动电话:" prop="custMobile">
            <el-input v-model="soForm.custMobile"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="联络人电话:" prop="custTel">
            <el-input v-model="soForm.custTel"
                    ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item label="商住所:" prop="custAddr">
            <el-input v-model="soForm.custAddr"
                      style="width: 720px" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="传真号码:" prop="custFax">
            <el-input v-model="soForm.custFax"
                      style="width: 720px"   ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发票联式:" prop="custBill">
            <el-select v-model="soForm.custBill" style="width: 180px"   disabled placeholder="请选择发票联式">
              <el-option
                v-for="dict in dict.type.gp_customer_bill"
                :key="dict.value"
                :label="dict.value+'_'+dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="交易条件:" prop="custDelivery">
            <el-select v-model="soForm.custDelivery" style="width: 180px"  disabled  placeholder="请选择交货条件">
              <el-option
                v-for="dict in dict.type.gp_customer_delivery"
                :key="dict.value"
                :label="dict.value+'_'+dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="付款方式:" prop="custPayWay">
            <el-select v-model="soForm.custPayWay" style="width: 180px"  disabled placeholder="请选择付款方式">
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
          <el-form-item label="付款条件:" prop="custPayCondition">
            <el-select v-model="soForm.custPayCondition" style="width: 180px" disabled  placeholder="请选择付款条件">
              <el-option
                v-for="dict in dict.type.gp_customer_paycondition"
                :key="dict.value"
                :label="dict.value+'_'+dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="付款天数:" prop="custPayDay">
            <el-select v-model="soForm.custPayDay" style="width: 180px"  disabled placeholder="请选择付款天数">
              <el-option
                v-for="dict in dict.type.gp_customer_payday"
                :key="dict.value"
                :label="dict.value+'_'+dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="结算日:" prop="custCloseDate">
            <el-select v-model="soForm.custCloseDate" disabled style="width: 180px"  placeholder="请选择结算日">
              <el-option
                v-for="dict in dict.type.gp_customer_closedate"
                :key="dict.value"
                :label="dict.value+'_'+dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="征信:" prop="credit">
            <el-select v-model="soForm.credit" disabled style="width: 180px"   placeholder="请选择征信">
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
          <el-form-item label="电子邮件:" prop="custEmail">
            <el-input v-model="soForm.custEmail"
                    ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item label="备注:" prop="custMemo">
            <el-input v-model="soForm.custMemo"  type="textarea"   style="width: 720px" ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="建立者:" prop="createUser">
            {{soForm.createUser}}
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="建立日期:" prop="recordCrtDate">
            {{soForm.recordCrtDate}}

          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="修改者:" prop="updateUser">
            {{soForm.updateUser}}
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="修改日期:" prop="update">
            {{soForm.update}}
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="el-dialog__footer" style="padding:20px 0 0 0;">
          <span slot="footer" class="dialog-footer">
                <el-button type="primary"
                           @click="submitForm('soForm')" >确 定</el-button>
               <el-button @click="cancel">取 消</el-button>
          </span>
    </div>
  </div>
</template>

<script>
import {querySo,updateManufacturerBasicSo} from "@/api/finance/gp/base1";
import {validateContacts} from "../../../utils/jlkj";

export default {
  name: "Customer",
  dicts: ['gp_customer_bill', 'gp_customer_delivery', 'gp_customer_payway',
    'gp_customer_paycondition', 'gp_customer_payday', 'gp_customer_closedate', 'gp_credit_type'],
  data() {
    return {
      manufacturerId:"",
      manufacturerChineseName:"",
      // 显示搜索条件
      showSearch: true,
      visible: false,
      sortOptions: [],
      soForm: {
        customerName:'',
        remitAccount1:'',
        remitBank1:'',
        customerContract:'',
        customerPost:'',
        custTel:'',
        custMobile:'',
        custAddr:'',
        custFax:'',
        custBill:'',
        custDelivery:'',
        custPayWay:'',
        custPayCondition:'',
        custPayDay:'',
        custCloseDate:'',
        credit:'',
        custEmail:'',
        custMemo:''
      },
      rules: {
        manufacturerChineseName: [
          { message: '请输入中文名称', trigger: 'blur'},
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}],
        customerName: [
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的客户简称", trigger: "blur"},
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}],
        remitAccount1:[
          { pattern:/^[0-9]*$/, message: "请输入正确的开户银行账号", trigger: "blur"},
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}],
        remitBank1:[
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的开户银行", trigger: "blur"},
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}],
        customerContract:[
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的联络人", trigger: "blur"},
          {max: 30, message: '最大长度为30个字符', trigger: 'blur'}],
        customerPost:[
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的联络人职称", trigger: "blur"},
          {max: 20, message: '最大长度为20个字符', trigger: 'blur'}],
        custTel:[
          {pattern:/^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/, message: "请输入正确的联络人电话", trigger: "blur"},
          {max: 30, message: '最大长度为30个字符', trigger: 'blur'}],
        custMobile:[
          {pattern:/^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/, message: "请输入正确的行动电话", trigger: "blur"},
          {max: 30, message: '最大长度为30个字符', trigger: 'blur'}],
        custAddr:[
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的送货地址", trigger: "blur"},
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}],
        custFax:[
          { pattern:/^(?:\d{3,4}-)?\d{7,8}(?:-\d{1,6})?$/, message: "请输入正确的传真号码", trigger: "blur"},
          {max: 30, message: '最大长度为30个字符', trigger: 'blur'}],
        custEmail:[
          { pattern:/^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/, message: "请输入正确的电子邮件", trigger: "blur"},
          {max: 50, message: '最大长度为50个字符', trigger: 'blur'}],
        custMemo:[ {max: 150, message: '最大长度为150个字符', trigger: 'blur'}]
      },
      sooForm: {},
      dataRule: {}
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
          this.soForm.manufacturerChineseName =   this.manufacturerChineseName
          this.soForm.manufacturerId = this.manufacturerId
            updateManufacturerBasicSo(this.soForm).then(response => {
              this.$modal.msgSuccess("客户关系修改成功");
              this.statusTabs=true

            });
          }

      });

    },
    initSo(manufacturerId, manufacturerChineseName,taxNo) {
      this.visible = true
      this.manufacturerChineseName = manufacturerChineseName;
      this.manufacturerId = manufacturerId;
      querySo(manufacturerId).then(response => {
        if (response.data!=null){
          this.soForm = response.data

        }else {
          this.soForm = {
            customerName:'',
            remitAccount1:'',
            remitBank1:'',
            customerContract:'',
            customerPost:'',
            custTel:'',
            custMobile:'',
            custAddr:'',
            custFax:'',
            custBill:'',
            custDelivery:'',
            custPayWay:'',
            custPayCondition:'',
            custPayDay:'',
            custCloseDate:'',
            credit:'',
            custEmail:'',
            custMemo:''
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
  width: 1250px !important;
}

.dialog-wrap {
  ::v-deep .el-form-item__label {
    width: 86px !important;
    text-align: right;
  }
}
</style>
