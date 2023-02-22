<template>
  <div v-if="visible">
    <el-dialog
      class="customDialogStyle"
      :visible.sync="visible"
      custom-class="saveAsDialog">
      <el-tabs tab-position="left">
        <el-tab-pane label="基础资料" >
          <el-form :model="dataForm" ref="dataForm" size="small" :inline="true" v-show="showSearch" label-width="115px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="国别:" >
                  <el-select v-model="dataForm.countryId"  disabled placeholder="请选择国别" style="width: 200px">
                    <el-option
                      v-for="dict in dict.type.vendor_different_countries"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="厂商编号:" prop="manufacturerId">
                  <el-input v-model="dataForm.manufacturerId" style="width: 200px" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="税号:" prop="taxNo">
                  <el-input v-model="dataForm.taxNo" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="厂商类别:" prop="catalog">
                  <el-select v-model="dataForm.catalog" disabled  placeholder="请选择厂商类别" style="width: 200px">
                    <el-option
                      v-for="dict in dict.type.vendor_catalog"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="负责人:" prop="owner">
                  <el-input v-model="dataForm.owner" :disabled="true" style="width: 200px" ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="厂商简称:" prop="manufacturerShortName">
                  <el-input v-model="dataForm.manufacturerShortName" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="所在区码:" prop="mainAreaId">
                  <el-select v-model="dataForm.mainAreaId" disabled style="width: 100px" placeholder="请选择所在区码" >
                    <el-option
                      v-for="dict in dict.type.primary_area_code"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                  <el-input v-model="dataForm.detailAreaId"  style="width: 100px" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="是否集团中采购:" prop="isCentralizedPurchase">
                  <el-select v-model="dataForm.isCentralizedPurchase" disabled  placeholder="请选择厂商简称" style="width: 200px">
                    <el-option
                      v-for="dict in dict.type.sales_new_prod"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="中文名称:" prop="manufacturerChineseName">
                  <el-input v-model="dataForm.manufacturerChineseName" :disabled="true"
                            style="width: 732px"   ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="原中文名称:" prop="originalChineseName">
                  <el-input v-model="dataForm.originalChineseName" :disabled="true"
                            style="width: 732px"  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="英文名称:" prop="englishName">
                  <el-input v-model="dataForm.englishName" :disabled="true" style="width: 732px" ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="资本额:" prop="capital">
                  <el-input v-model="dataForm.capital" :disabled="true" style="width: 100px" ></el-input>
                  <el-select v-model="dataForm.capitalCurrencyCode" disabled style="width: 100px"  placeholder="请选择币别">
                    <el-option
                      v-for="dict in dict.type.manufacturertest_currency"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="营业额:" prop="turnover">
                  <el-input v-model="dataForm.turnover" :disabled="true" style="width: 100px" ></el-input>
                  <el-select v-model="dataForm.turnoverCurrencyCode" disabled style="width: 100px"  placeholder="请选择币别">
                    <el-option
                      v-for="dict in dict.type.manufacturertest_currency"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="发票地址:" prop="billZipCode">
                  <el-input v-model="dataForm.billAddress" :disabled="true" style="width: 100px"></el-input>
                  <el-input v-model="dataForm.billZipCode" :disabled="true" style="width: 635px" ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="网址:" prop="web">
                  <el-input v-model="dataForm.web" :disabled="true"  style="width: 735px"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="状态:" prop="status">
                  <el-select v-model="dataForm.status" disabled placeholder="请选择状态">
                    <el-option
                      v-for="dict in dict.type.finance_stuscode_name"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="清晰状态:" prop="clearStatus">
                  <el-input v-model="dataForm.clearStatus" :disabled="true" style="width: 200px"  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="建立者:" prop="createUser">
                  <el-input v-model="dataForm.createUser" :disabled="true" style="width: 200px" ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="建立日期:" prop="recordCrtDate">
                  <el-input v-model="dataForm.recordCrtDate" :disabled="true"
                            style="width: 200px"  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="修改者:" prop="updateUser">
                  <el-input v-model="dataForm.updateUser" :disabled="true" style="width: 200px" ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="修改日期:" prop="updateTime">
                  <el-input v-model="dataForm.updateTime" :disabled="true"
                            style="width: 200px" ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="报支关系" v-if="flagForm.FC"  >
          <ManufacturerFinance  ref="queryFc"></ManufacturerFinance>
        </el-tab-pane>
        <el-tab-pane label="采购关系" v-if="flagForm.MP">
          <ManufacturerPurchase ref="queryMp"></ManufacturerPurchase>
        </el-tab-pane>
        <el-tab-pane label="客户关系" v-if="flagForm.SO">
          <ManufacturerCustomer ref="querySo"></ManufacturerCustomer>
        </el-tab-pane>
        <el-tab-pane label="承运关系"  v-if="flagForm.ST">
          <ManufacturerTransport ref="querySt"></ManufacturerTransport>
        </el-tab-pane>
      </el-tabs>
      <div class="el-dialog__footer" style="padding:20px 0 0 0;">
          <span slot="footer" class="dialog-footer">
            <el-button @click="cancel">取 消</el-button>
          </span>
      </div>

    </el-dialog>
  </div>

</template>
<script>
import {queryRelations, getBase1} from "@/api/finance/GP/base1";
import ManufacturerFinance from './ManufacturerFinance'
import ManufacturerPurchase from './ManufacturerPurchase'
import ManufacturerCustomer from './ManufacturerCustomer'
import ManufacturerTransport from './ManufacturerTransport'
export default {
  dicts: ['vendor_different_countries','vendor_catalog','primary_area_code','sales_new_prod','manufacturertest_currency',
    'finance_stuscode_name'],
  components: {
    ManufacturerFinance,
    ManufacturerPurchase,
    ManufacturerCustomer,
    ManufacturerTransport
  },
  data() {
    return {
      // 显示搜索条件
      showSearch: true,
      visible: false,
      sortOptions: [],
      dataForm: {
        countryId:'',
        catalog:'',
        recordCrtDate:'',
        clearStatus:'',
        mainAreaId:'',
        detailAreaId:'',
        isCentralizedPurchase:'',
        originalChineseName:'',
        englishName:'',
        capitalCurrencyCode:'',
        turnover:'',
        turnoverCurrencyCode:'',
        billAddress:'',
        billZipCode:'',
        web:'',
      },
      relationsForm:{
        relation:''
      },
      fcForm: {},
      dataRule: {},
      lage:true,
      resultList: [],
      flagForm: {}
    }
  },
  created() {

  },
  methods: {
    cancel() {
      this.visible = false;
      this.flagForm={}
    },
    init(id,manufacturerChineseName) {
      this.visible = true
      console.log(this.visible);
      queryRelations(id).then(response => {
        this.relationsForm = response.data.data
        this.flagForm={}
        this.relationsForm.map(item => {
          if (item.relation === "FC") {
            this.flagForm.FC = item.relation
            this.$nextTick(() => {
              this.$refs.queryFc.initFc(id,manufacturerChineseName)
            })
          }else if (item.relation === "MP"){
            this.flagForm.MP = item.relation
            this.$nextTick(() => {
              this.$refs.queryMp.initMP(id,manufacturerChineseName)
            })
          }else if (item.relation === "ST"){
            this.flagForm.ST = item.relation
            this.$nextTick(() => {
              this.$refs.querySt.initSt(id,manufacturerChineseName)
            })
          }else if (item.relation === "SO"){
            this.flagForm.SO = item.relation
            this.$nextTick(() => {
              this.$refs.querySo.initSo(id,manufacturerChineseName)
            })
          }
        })
      });
      getBase1(id).then(response => {
        this.dataForm = response.data.data
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
