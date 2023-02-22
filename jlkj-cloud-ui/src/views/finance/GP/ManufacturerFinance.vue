<template>
<div>
  <el-form :model="fctForm" ref="dataForm" size="small" :inline="true" v-show="showSearch" >
    <el-row>
      <el-col :span="12" >
        <el-form-item label="中文名称:" prop="manufacturerChineseName" label-width="144px">
          <el-input v-model="fctForm.manufacturerChineseName" :disabled="true"
                    style="width: 178px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="厂商编号:" prop="manufacturerId" label-width="144px">
          <el-input v-model="fcForm.manufacturerId" :disabled="true"
                    style="width: 178px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="汇款银行(1):" prop="remitBank1" label-width="144px">
          <el-input v-model="fcForm.remitBank1" :disabled="true" style="width: 178px"  ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="汇款账号(1):" prop="remitAccount1" label-width="144px">
          <el-input v-model="fcForm.remitAccount1" :disabled="true"
                    style="width: 178px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="20">
        <el-form-item label="汇款账号名称(1):" prop="remitName1"  label-width="144px">
          <el-input v-model="fcForm.remitName1" :disabled="true"
                    style="width: 713px;"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="行号(1):" prop="bankNum1" label-width="144px">
          <el-input v-model="fcForm.bankNum1" :disabled="true"
                    style="width: 713px;"   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="14">
        <el-form-item label="受款银行汇入地点(1):" prop="mainAreaId1" label-width="144px">
          <el-select v-model="fcForm.mainAreaId1" style="width: 100px" disabled   placeholder="请选择">
            <el-option
              v-for="dict in dict.type.vendor_primary_area_code"
              :key="dict.value"
              :label="dict.value+'_'+dict.label"
              :value="dict.value"
            />
          </el-select>
          <el-input v-model="fcForm.province1" style="width: 100px;" :disabled="true"></el-input>
          <el-form-item label="省" style="margin-right: -28px;"/>
         <el-input v-model="fcForm.ville1"
                     style="width: 100px;" :disabled="true"  ></el-input>  <el-form-item style="margin-right: -28px;" label="市"/>
        </el-form-item>
      </el-col>
      <el-col :span="10">
        <el-form-item label="地址:" prop="acceptRemitBank1">
          <el-input v-model="fcForm.acceptRemitBank1" :disabled="true"
                    style="width: 195px;"     ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="汇款银行(2):" prop="remitBank2" label-width="144px">
          <el-input v-model="fcForm.remitBank2" :disabled="true" style="width: 320px;" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="汇款账号(2):" prop="remitAccount2" label-width="144px">
          <el-input v-model="fcForm.remitAccount2" :disabled="true"
                    style="width: 185px;"      ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="汇款账号名称(2):" prop="remitName2" label-width="144px">
          <el-input v-model="fcForm.remitName2" :disabled="true"
                    style="width: 713px;" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="行号(2):" prop="bankNum2" label-width="144px">
          <el-input v-model="fcForm.bankNum2" :disabled="true"
                    style="width: 713px;"     ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="14">
        <el-form-item label="受款银行汇入地点(2):" prop="mainAreaId2" label-width="144px">
          <el-select v-model="fcForm.mainAreaId2" style="width: 100px" disabled    placeholder="请选择">
            <el-option
              v-for="dict in dict.type.vendor_primary_area_code"
              :key="dict.value"
              :label="dict.value+'_'+dict.label"
              :value="dict.value"
            />
          </el-select>
          <el-input :disabled="true" v-model="fcForm.province2" style="width: 100px;"></el-input>
          <el-form-item label="省" style="margin-right: -28px;"/>
          <el-input :disabled="true" v-model="fcForm.ville2"
                    style="width: 100px;"></el-input>
          <el-form-item style="margin-right: -28px;" label="市"/>
        </el-form-item>
      </el-col>
      <el-col :span="10">
        <el-form-item label="地址:" prop="acceptRemitBank2">
          <el-input v-model="fcForm.acceptRemitBank2" :disabled="true"
                    style="width: 195px;"     ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="汇款账号名称(3):" prop="remitName3" label-width="144px">
          <el-input v-model="fcForm.remitName3" :disabled="true"
                    style="width: 713px;"  ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="行号(3):" prop="bankNum3" label-width="144px">
          <el-input v-model="fcForm.bankNum3" :disabled="true"
                    style="width: 713px;"   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="14">
        <el-form-item label="受款银行汇入地点(3):" prop="mainAreaId3" label-width="144px">
          <el-select v-model="fcForm.mainAreaId3" style="width: 100px" placeholder="请选择" disabled >
            <el-option
              v-for="dict in dict.type.vendor_primary_area_code"
              :key="dict.value"
              :label="dict.value+'_'+dict.label"
              :value="dict.value"
            />
          </el-select>
          <el-input v-model="fcForm.province3" style="width: 100px;" :disabled="true"></el-input>
          <el-form-item label="省" style="margin-right: -28px;"/>
          <el-input v-model="fcForm.ville3"
                    style="width: 100px;" :disabled="true"></el-input>
          <el-form-item style="margin-right: -28px;" label="市"/>
        </el-form-item>
      </el-col>
      <el-col :span="10">
        <el-form-item label="地址:" prop="acceptRemitBank3">
          <el-input v-model="fcForm.acceptRemitBank3" :disabled="true"
                    style="width: 190px;" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="付款方式:" prop="payType" label-width="144px">
          <el-input v-model="fcForm.payType" :disabled="true"
                    style="width: 190px;"   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="电子邮件:" prop="Email" label-width="144px">
          <el-input v-model="fcForm.Email" :disabled="true"
                    style="width: 180px;"   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="联络人:" prop="contact" label-width="144px">
          <el-input v-model="fcForm.contact" :disabled="true"
                    style="width: 190px;"    ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="联络电话:" prop="officeTelephoneNo" label-width="144px">
          <el-input v-model="fcForm.officeTelephoneNo" :disabled="true"
                    style="width: 180px;"   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="行动电话:" prop="mobile" label-width="144px">
          <el-input v-model="fcForm.mobile" :disabled="true"
                    style="width: 190px;"     ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="传真号码:" prop="fax" label-width="144px">
          <el-input v-model="fcForm.fax" :disabled="true"
                    style="width: 180px;"        ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="邮寄支票地址:" prop="billSendZip" label-width="144px">
          <el-input v-model="fcForm.billSendZip" :disabled="true" style="width: 190px;"   ></el-input>
          <el-input v-model="fcForm.billSendAddr" :disabled="true"
                    style="width: 520px;"     ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="建立人员:" prop="createUser" label-width="144px">
          <el-input v-model="fcForm.createUser" :disabled="true"  style="width: 190px;"  ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="建立人员日期:" prop="recordCrtDate" label-width="144px">
          <el-input v-model="fcForm.recordCrtDate" :disabled="true"
                    style="width: 180px;"     ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="修改者:" prop="updateUser" label-width="144px">
          <el-input v-model="fcForm.updateUser" :disabled="true" style="width: 190px;"   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="修改日期:" prop="update" label-width="144px">
          <el-input v-model="fcForm.update" :disabled="true" style="width: 180px;"></el-input>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</div>
</template>

<script>
import {queryFc} from "@/api/finance/GP/base1";

export default {
  name: "bbb",
  dicts: ['vendor_primary_area_code'],
  data(){
    return{
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
      from:{},
      dataRule: {},
      flag: true
    }
  },
  created() {

  },
  methods: {

    initFc(manufacturerId,manufacturerChineseName) {
      this.fctForm.manufacturerChineseName=manufacturerChineseName;
      this.visible = true
      queryFc(manufacturerId).then(response => {
        this.fcForm = response.data.data
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
