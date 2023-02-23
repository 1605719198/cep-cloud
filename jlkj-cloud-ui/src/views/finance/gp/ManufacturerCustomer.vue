<template>
  <div>
    <el-form  :model="sooForm" ref="dataForm" size="small" :inline="true" v-show="showSearch" label-width="115px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="中文名称:" prop="manufacturerChineseName">
            <el-input v-model="sooForm.manufacturerChineseName" :disabled="true"
                    ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="厂商编号:" prop="manufacturerId">
            <el-input v-model="soForm.manufacturerId" :disabled="true"
                    ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="客户简称:" prop="customerName">
            <el-input v-model="soForm.customerName" :disabled="true"
                 style="width: 720px"   ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="开户银行:" prop="remitAccount1">
            <el-input v-model="soForm.remitAccount1" :disabled="true"
                      style="width: 720px"   ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="开户银行账号:" prop="remitBank1">
            <el-input v-model="soForm.remitBank1" :disabled="true"
                      style="width: 720px"  ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联络人:" prop="customerContract">
            <el-input v-model="soForm.customerContract" :disabled="true"
                    ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联络人职称:" prop="customerPost">
            <el-input v-model="soForm.customerPost" :disabled="true"
                    ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联络人电话:" prop="custTel">
            <el-input v-model="soForm.custTel" :disabled="true"
                    ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="行动电话:" prop="custMobile">
            <el-input v-model="soForm.custMobile" :disabled="true"
                    ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="送货地址:" prop="custAddr">
            <el-input v-model="soForm.custAddr" :disabled="true"
                      style="width: 720px" ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="传真号码:" prop="custFax">
            <el-input v-model="soForm.custFax" :disabled="true"
                      style="width: 720px"   ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发票联式:" prop="custBill">
            <el-select v-model="soForm.custBill" style="width: 180px"   disabled placeholder="请选择发票联式">
              <el-option
                v-for="dict in dict.type.vendor_customer_bill"
                :key="dict.value"
                :label="dict.value+'_'+dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="交货条件:" prop="custDelivery">
            <el-select v-model="soForm.custDelivery" style="width: 180px"  disabled  placeholder="请选择交货条件">
              <el-option
                v-for="dict in dict.type.vendor_customer_delivery"
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
                v-for="dict in dict.type.vendor_customer_pay_way"
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
                v-for="dict in dict.type.vendor_customer_pay_condition"
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
                v-for="dict in dict.type.vendor_customer_pay_day"
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
                v-for="dict in dict.type.vendor_customer_close_date"
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
                v-for="dict in dict.type.vendor_credit"
                :key="dict.value"
                :label="dict.value+'_'+dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="电子邮件:" prop="custEmail">
            <el-input v-model="soForm.custEmail" :disabled="true"
                    ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item label="备注:" prop="custMemo">
            <el-input v-model="soForm.custMemo"  type="textarea" :disabled="true" style="width: 720px" ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="建立者:" prop="createUser">
            <el-input v-model="soForm.createUser" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="建立日期:" prop="recordCrtDate">
            <el-input v-model="soForm.recordCrtDate" :disabled="true"
                    ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="修改者:" prop="updateUser">
            <el-input v-model="soForm.updateUser" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="修改日期:" prop="update">
            <el-input v-model="soForm.update" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import {querySo} from "@/api/finance/gp/base1";

export default {
  name: "ddd",
  dicts: ['vendor_customer_bill', 'vendor_customer_delivery', 'vendor_customer_pay_way',
    'vendor_customer_pay_condition', 'vendor_customer_pay_day', 'vendor_customer_close_date', 'vendor_credit'],
  data() {
    return {
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
      sooForm: {},
      dataRule: {}
    }
  },
  created() {

  },
  methods: {
    initSo(manufacturerId, manufacturerChineseName) {
      this.visible = true
      this.sooForm.manufacturerChineseName = manufacturerChineseName;
      querySo(manufacturerId).then(response => {
        this.soForm = response.data.data
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
