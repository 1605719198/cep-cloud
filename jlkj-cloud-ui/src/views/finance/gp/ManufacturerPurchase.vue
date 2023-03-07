<template>
<div>
  <el-form :model="mpForm" ref="dataForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
    <el-row>
      <el-col :span="12">
        <el-form-item label="厂商编号:" prop="manufacturerId">
          <el-input v-model="mpForm.manufacturerId" :disabled="true"
                   style="width: 220px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="税号:" prop="taxNo">
          <el-input v-model="mpForm.taxNo" :disabled="true"
                    style="width: 220px"    ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="中文名称:" prop="manufacturerChineseName">
          <el-input v-model="mppForm.manufacturerChineseName" :disabled="true"
                    style="width: 757px"     ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="厂商性质:" prop="vendorType">
          <el-select v-model="mpForm.vendorType" disabled style="width: 220px"  placeholder="请选择">
            <el-option
              v-for="dict in dict.type.vendor_type"
              :key="dict.value"
              :label="dict.value+'_'+dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="厂商性质代码:" prop="specialCode">
          <el-input v-model="mpForm.specialCode" :disabled="true"
                    style="width: 220px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="发票地址:" prop="billZipCode">
          <el-input v-model="mpForm.billAddress" :disabled="true" style="width: 100px" ></el-input>
          <el-input v-model="mpForm.billZipCode" :disabled="true" style="width: 657px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="联络地址:" prop="billZipCode">
          <el-input v-model="mpForm.contactZip" :disabled="true" style="width: 100px"></el-input>
          <el-input v-model="mpForm.contactAddr" :disabled="true"  style="width: 657px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="网址:" prop="web">
          <el-input v-model="mpForm.web" :disabled="true" style="width: 757px"  ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="联络人(0):" prop="contact">
          <el-input style="width: 91px" v-model="mpForm.contact" :disabled="true" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="联络人(1):" prop="contact1">
          <el-input   style="width: 91px" v-model="mpForm.contact1" :disabled="true" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="联络人(2):" prop="contact2">
          <el-input  style="width: 91px" v-model="mpForm.contact2" :disabled="true" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="联络人(3):" prop="contact3">
          <el-input  style="width: 91px" v-model="mpForm.contact3" :disabled="true" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="联络人(4):" prop="contact4">
          <el-input  style="width: 91px" v-model="mpForm.contact4" :disabled="true" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="部门(0):" prop="department">
          <el-input style="width: 91px" v-model="mpForm.department" :disabled="true" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="部门(1):" prop="department1">
          <el-input style="width: 91px" v-model="mpForm.department1" :disabled="true" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="部门(2):" prop="department2">
          <el-input v-model="mpForm.department2" :disabled="true" style="width: 91px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="部门(3):" prop="department3">
          <el-input v-model="mpForm.department3" :disabled="true" style="width: 91px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="部门(4):" prop="department4">
          <el-input v-model="mpForm.department4" :disabled="true" style="width: 91px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="职称(0):" prop="title">
          <el-input v-model="mpForm.title" :disabled="true" style="width: 91px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="职称(1):" prop="title1">
          <el-input v-model="mpForm.title1" :disabled="true" style="width: 91px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="职称(2):" prop="title2">
          <el-input v-model="mpForm.title2" :disabled="true" style="width: 91px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="职称(3):" prop="title3">
          <el-input v-model="mpForm.title3" :disabled="true" style="width: 91px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="职称(4):" prop="title4">
          <el-input v-model="mpForm.title4" :disabled="true" style="width: 91px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="电话(0):" prop="officeTelephoneNo">
          <el-input v-model="mpForm.officeTelephoneNo" :disabled="true"
                    style="width: 91px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="电话(1):" prop="officeTelephoneNo1">
          <el-input v-model="mpForm.officeTelephoneNo1" :disabled="true"
                    style="width: 91px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="电话(2):" prop="officeTelephoneNo2">
          <el-input v-model="mpForm.officeTelephoneNo2" :disabled="true"
                    style="width: 91px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="电话(3):" prop="officeTelephoneNo3">
          <el-input v-model="mpForm.officeTelephoneNo3" :disabled="true"
                    style="width: 91px"   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="电话(4):" prop="officeTelephoneNo4">
          <el-input v-model="mpForm.officeTelephoneNo4" :disabled="true"
                    style="width: 91px"   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="行动电话(0):" prop="mobile">
          <el-input v-model="mpForm.mobile" :disabled="true" style="width: 91px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="行动电话(1):" prop="mobile1">
          <el-input v-model="mpForm.mobile1" :disabled="true" style="width: 91px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="行动电话(2):" prop="mobile2">
          <el-input v-model="mpForm.mobile2" :disabled="true" style="width: 91px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="行动电话(3):" prop="mobile3">
          <el-input v-model="mpForm.mobile3" :disabled="true" style="width: 91px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="行动电话(4):" prop="mobile4">
          <el-input v-model="mpForm.mobile4" :disabled="true" style="width: 91px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="电子邮件(0):" prop="email">
          <el-input v-model="mpForm.email" :disabled="true" style="width: 91px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="电子邮件(1):" prop="email1">
          <el-input v-model="mpForm.email1" :disabled="true" style="width: 91px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="电子邮件(2):" prop="email2">
          <el-input v-model="mpForm.email2" :disabled="true" style="width: 91px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="电子邮件(3):" prop="email3">
          <el-input v-model="mpForm.email3" :disabled="true" style="width: 91px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label="电子邮件(4):" prop="email4">
          <el-input v-model="mpForm.email4" :disabled="true" style="width: 91px" ></el-input>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="传真:" prop="fax">
            <el-input v-model="mpForm.fax" :disabled="true"
                      style="width: 220px" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="关系企业:" prop="corpGroup">
            <el-input v-model="mpForm.corpGroup" :disabled="true"
                      style="width: 220px" ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-col :span="12">
        <el-form-item label="厂商资料来源:" prop="source">
          <el-input v-model="mpForm.source" :disabled="true"
                    style="width: 220px"   ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="厂商原来名称:" prop="originName">
          <el-input v-model="mpForm.originName" :disabled="true"
                    style="width: 220px"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="停权期限(起):" prop="stopPeriodFrom">
          <el-input v-model="mpForm.stopPeriodFrom" :disabled="true"
                    style="width: 220px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="停权期限(迄):" prop="stopPeriodTo">
          <el-input v-model="mpForm.stopPeriodTo" :disabled="true"
                    style="width: 220px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="主要产品说明:" prop="productDescp">
          <el-input style="width: 757px"  v-model="mpForm.productDescp" :disabled="true"   type="textarea"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="交易不良记录:" prop="badHistory">
          <el-input style="width: 757px"  v-model="mpForm.badHistory" :disabled="true"   type="textarea"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-form-item label="特别备注:" prop="memo">
          <el-input style="width: 757px"  v-model="mpForm.memo" :disabled="true"   type="textarea"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="建立者:" prop="createUser">
          <el-input v-model="mpForm.createUser" :disabled="true"  style="width: 220px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="建立日期:" prop="recordCrtDate">
          <el-input v-model="mpForm.recordCrtDate" :disabled="true"
                    style="width: 220px"    ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="修改者:" prop="updateUser">
          <el-input v-model="mpForm.updateUser" :disabled="true" style="width: 220px" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="修改日期:" prop="update">
          <el-input v-model="mpForm.update" :disabled="true" style="width: 220px" ></el-input>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</div>
</template>

<script>
import { queryMp} from "@/api/finance/gp/base1";

export default {
  name: "ccc",
  dicts: ['vendor_type',],
  data(){
    return{
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
      mppForm:{},
      dataRule: {}
    }
  },
  created() {

  },
  methods: {
    initMP(manufacturerId,manufacturerChineseName) {
      this.mppForm.manufacturerChineseName=manufacturerChineseName;
      this.visible = true
      queryMp(manufacturerId).then(response => {
        this.mpForm = response.data
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
