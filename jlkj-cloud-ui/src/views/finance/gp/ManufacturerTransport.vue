<template>
<div>
  <el-form :model="sttForm" ref="dataForm" size="small" :inline="true" v-show="showSearch" label-width="115px">
    <el-row>
      <el-col :span="12">
        <el-form-item label="中文名称:" prop="manufacturerChineseName">
          <el-input v-model="sttForm.manufacturerChineseName" :disabled="true"
                   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="厂商编号:" prop="manufacturerId">
          <el-input v-model="stForm.manufacturerId" :disabled="true"
                   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="厂商简称:" prop="vendorName">
          <el-input v-model="stForm.vendorName" :disabled="true"
                   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="企业代码:" prop="enterpriseNo">
          <el-input v-model="stForm.enterpriseNo" :disabled="true"
                   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="法人代表:" prop="custOwner">
          <el-input v-model="stForm.custOwner" :disabled="true"
                   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="发票传递方式:" prop="billTransType">
          <el-select v-model="stForm.billTransType" disabled style="width: 185px"    placeholder="请选择发票传递方式">
            <el-option
              v-for="dict in dict.type.vendor_customer_pay_way"
              :key="dict.value"
              :label="dict.value+'_'+dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="电话号码:" prop="officeTelephoneNo">
          <el-input v-model="stForm.officeTelephoneNo" :disabled="true"
                   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="传真号码:" prop="fax">
          <el-input v-model="stForm.fax" :disabled="true"
                   ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="中文地址:" prop="zip">
          <el-input v-model="stForm.zip" :disabled="true" style="width: 100px" ></el-input>
          <el-input v-model="stForm.addr" :disabled="true"
                    style="width: 620px"></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="英文地址:" prop="engAddr">
          <el-input v-model="stForm.engAddr" :disabled="true"  style="width: 720px"  ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="联络中文地址:" prop="contactZip">
          <el-input v-model="stForm.contactZip" :disabled="true" style="width: 100px" ></el-input>
          <el-input v-model="stForm.contactAddr" :disabled="true"
                    style="width: 620px"    ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="联络英文地址:" prop="contactEngAddr">
          <el-input v-model="stForm.contactEngAddr" :disabled="true"
                    style="width: 720px"   ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="开户银行:" prop="remitBank1">
          <el-input v-model="stForm.remitBank1" :disabled="true"
                    ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="开户银行账号:" prop="remitAccount1">
          <el-input v-model="stForm.remitAccount1" :disabled="true"
                    ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="征信:" prop="credit">
          <el-select v-model="stForm.credit" style="width: 185px"  disabled placeholder="请选择征信">
            <el-option
              v-for="dict in dict.type.vendor_credit"
              :key="dict.value"
              :label="dict.value+'_'+dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="税号:" prop="taxNo">
          <el-input v-model="stForm.taxNo" :disabled="true"
                  ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="最终用户:" prop="lastUser">
          <el-input v-model="stForm.lastUser" :disabled="true"
                    ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="电子邮件:" prop="email">
          <el-input v-model="stForm.email" :disabled="true"
                    ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="建立者:" prop="createUser">
          <el-input v-model="stForm.createUser" :disabled="true" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="建立日期:" prop="recordCrtDate">
          <el-input v-model="stForm.recordCrtDate" :disabled="true"
                    ></el-input>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="修改者:" prop="updateUser">
          <el-input v-model="stForm.updateUser" :disabled="true" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="修改日期:" prop="update">
          <el-input v-model="stForm.update" :disabled="true"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</div>
</template>

<script>
import {querySt} from "@/api/finance/gp/base1";

export default {
  name: "fff",
  dicts: [ 'vendor_customer_pay_way', 'vendor_credit'],
  data(){
    return{
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
      sttForm:{},
      dataRule: {},

    }
  },
  created() {

  },
  methods: {
    initSt(manufacturerId,manufacturerChineseName) {
      this.visible = true
      this.sttForm.manufacturerChineseName=manufacturerChineseName;
      querySt(manufacturerId).then(response => {
        this.stForm = response.data.data
        console.log(response.data.data);
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
