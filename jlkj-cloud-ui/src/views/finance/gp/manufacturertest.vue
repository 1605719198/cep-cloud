<template>
  <div v-if="visible">
    <el-dialog
      class="customDialogStyle"
      :visible.sync="visible"
      custom-class="saveAsDialog">
      <el-tabs tab-position="left" >
        <el-tab-pane label="基础资料">
          <el-form :rules="rules" :model="dataForm" ref="dataForm" size="small" :inline="true" v-show="showSearch"
                   label-width="144px"
          >
            <el-row>
              <el-col :span="12">
                <el-form-item label="国别:" prop="countryId">
                  <el-select v-model="dataForm.countryId" :disabled="currencyDisabled" placeholder="请选择国别"
                             style="width: 200px">
                    <el-option
                      v-for="dict in dict.type.gp_countryid_type"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="厂商编号:" prop="manufacturerId">
                  {{dataForm.manufacturerId}}
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="统一社会信用代码:" prop="taxNo">
                  <el-input v-model="dataForm.taxNo" :disabled="currencyDisabled" style="width: 200px"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="中文名称:" prop="manufacturerChineseName">
                  <el-input v-model="dataForm.manufacturerChineseName" :disabled="currencyDisabled"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">

                <el-form-item label="法定代表人/责任人:" prop="owner">
                  <el-input v-model="dataForm.owner" :disabled="currencyDisabled" style="width: 200px"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="厂商简称:" prop="manufacturerShortName">
                  <el-input v-model="dataForm.manufacturerShortName" :disabled="currencyDisabled"
                            style="width: 200px"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="所在区码:" prop="mainAreaId">

                  <dict-tag  v-if="mainAreaIdIfN" :options="dict.type.gp_main_areaid" :value="dataForm.mainAreaId"/>
                  <!--                  <el-select v-model="dataForm.mainAreaId" @change="selectGoodsByGroupId($event)" clearable placeholder="请选择所在区码" filterable>
                                      <el-option v-for="dict in dict.type.gp_main_areaid" :label="dict.value+'_'+dict.label" :key="dict.value" :value="dict.value"></el-option>
                                    </el-select>
                                    <el-select v-model="dataForm.detailAreaId" clearable placeholder="请选择所在区码" filterable>
                                      <el-option v-for="item in goods" :label="item.value+'_'+item.label" :key="item.value" :value="item.value"></el-option>
                                    </el-select>
                                    -->

                  <el-select v-if="mainAreaIdIf" v-model="dataForm.mainAreaId" :disabled="mainAreaIdDisabled" style="width: 100px"
                             placeholder="请选择所在区码">
                    <el-option
                      v-for="dict in dict.type.gp_main_areaid"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    />
                  </el-select>

                </el-form-item>
                <el-form-item   prop="detailAreaId">
                  <dict-tag  v-if="mainAreaIdIfN" :options="dict.type.gp_detail_areaid" :value="dataForm.detailAreaId"/>
                  <el-select v-if="mainAreaIdIf"  v-model="dataForm.detailAreaId" clearable placeholder="请选择所在区码" :disabled="mainAreaIdDisabled" filterable style="width: 100px">
                    <el-option v-for="dict in dict.type.gp_detail_areaid" :label="dict.value+'_'+dict.label" :key="dict.value" :value="dict.value"></el-option>
                  </el-select>
                  <!--                    <el-input v-model="dataForm.detailAreaId" style="width: 100px"
                                                :disabled="currencyDisabled"></el-input>-->
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="英文名称:" prop="manufacturerEnglishName">
                  <el-input v-model="dataForm.manufacturerEnglishName" :disabled="currencyDisabled" ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="厂商类别:" prop="catalog">
                  <el-select v-model="dataForm.catalog" :disabled="currencyDisabled" placeholder="请选择厂商类别"
                             style="width: 200px">
                    <el-option
                      v-for="dict in dict.type.gp_manufacturer_type"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="原中文名称:" prop="originalChineseName">
                  <el-input v-model="dataForm.originalChineseName" :disabled="currencyDisabled"
                            ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="是否关联方:" prop="relatedParty">
                  <el-radio-group v-model="dataForm.relatedParty">
                    <el-radio v-for="dict in dict.type.sys_yes_no"
                              :key="dict.value"
                              :label="dict.value">{{dict.label}}</el-radio>
                  </el-radio-group>

                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="关联日期:" prop="correlationDate">
                  <el-date-picker clearable
                                  v-model="dataForm.correlationDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择关联日期"
                                  style="width: 100%;">
                  </el-date-picker>

                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="是否集采:" prop="isCentralizedPurchase">
                  <el-select v-model="dataForm.isCentralizedPurchase" :disabled="currencyDisabled" placeholder="请选择厂商简称"
                             style="width: 200px">
                    <el-option
                      v-for="dict in dict.type.gp_cntralized_purchase"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>



              <el-col :span="12">
                <el-form-item label="资本额:" prop="capital">
                  <el-input maxlength="18" v-model="dataForm.capital" :disabled="currencyDisabled" style="width: 100px"></el-input>
                </el-form-item>
                  <el-form-item prop="capitalCurrencyCode">
                  <el-select v-model="dataForm.capitalCurrencyCode" :disabled="currencyDisabled" style="width: 100px"
                             placeholder="请选择币别">
                    <el-option
                      v-for="dict in dict.type.gp_currency_type"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                  </el-form-item>

              </el-col>
<!--              <el-col :span="12">
                <el-form-item label="营业额:" prop="turnover">
                  <el-input v-model="dataForm.turnover" :disabled="currencyDisabled" style="width: 100px"></el-input>
                </el-form-item>
                <el-form-item prop="turnoverCurrencyCode">
                  <el-select v-model="dataForm.turnoverCurrencyCode" :disabled="currencyDisabled" style="width: 100px"
                             placeholder="请选择币别">
                    <el-option
                      v-for="dict in dict.type.gp_currency_type"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>-->
              <el-col :span="12">
                <el-form-item label="发票地址:" prop="billAddress">

                  <el-input v-model="dataForm.billAddress" :disabled="currencyDisabled" ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="状态:" prop="status">
                  <el-select v-model="dataForm.status" :disabled="currencyDisabled" placeholder="请选择状态">
                    <el-option
                      v-for="dict in dict.type.cost_stuscode_name"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
<!--              <el-col :span="24">
                <el-form-item label="网址:" prop="web">
                  <el-input v-model="dataForm.web" :disabled="currencyDisabled" style="width: 735px"></el-input>
                </el-form-item>
              </el-col>-->
              <el-col :span="24">
                <el-form-item label="业务关系:" prop="relation">
                  <el-select multiple v-model="dataForm.relation" :disabled="currencyDisabled" placeholder="请选择业务关系">
                    <el-option
                      v-for="dict in dict.type.finance_gp_relation"
                      :key="dict.value"
                      :label="dict.value+'_'+dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="建立者:" prop="createUser">

                  {{dataForm.createUser}}
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="建立日期:" prop="recordCrtDate">

                  {{dataForm.recordCrtDate}}
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="修改者:" prop="updateUser">
                  {{dataForm.updateUser}}
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="修改日期:" prop="updateTime">
                  {{dataForm.updateTime}}
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div class="el-dialog__footer" style="padding:20px 0 0 0;">
          <span slot="footer" class="dialog-footer">
                <el-button type="primary"
                           @click="submitForm('dataForm')" v-if="defineIf">确 定</el-button>
          </span>
          </div>
        </el-tab-pane>
        <el-tab-pane label="报支关系" v-if="flagForm.FC">
          <ManufacturerFinance ref="queryFc" @getLists="getQueryFc"></ManufacturerFinance>
        </el-tab-pane>
        <el-tab-pane label="采购关系" v-if="flagForm.MP">
          <ManufacturerPurchase ref="queryMp"  @getLists="getQueryMp"></ManufacturerPurchase>
        </el-tab-pane>
        <el-tab-pane label="客户关系" v-if="flagForm.SO">
          <ManufacturerCustomer ref="querySo"  @getLists="getQuerySo"></ManufacturerCustomer>
        </el-tab-pane>
        <el-tab-pane label="承运关系" v-if="flagForm.ST">
          <ManufacturerTransport ref="querySt"  @getLists="getQuerySt"></ManufacturerTransport>
        </el-tab-pane>
      </el-tabs>


    </el-dialog>
  </div>

</template>
<script>
import {queryRelations, getBase1, updateManufacturerBasics, addManufacturerBasics,selectGoodsByGroupId} from "@/api/finance/gp/base1";
import ManufacturerFinance from './ManufacturerFinance'
import ManufacturerPurchase from './ManufacturerPurchase'
import ManufacturerCustomer from './ManufacturerCustomer'
import ManufacturerTransport from './ManufacturerTransport'

import {validateContacts, checkRealName, validatAlphabets, validateURL} from "../../../utils/jlkj";
export default {
  dicts: ['gp_countryid_type', 'gp_manufacturer_type', 'gp_main_areaid','gp_detail_areaid', 'gp_cntralized_purchase', 'gp_currency_type',
    'cost_stuscode_name', 'finance_gp_relation','sys_yes_no'],
  components: {
    ManufacturerFinance,
    ManufacturerPurchase,
    ManufacturerCustomer,
    ManufacturerTransport
  },
  data() {
    return {
      //所在区码是否显示
      mainAreaIdIf:false,
      mainAreaIdIfN:false,
      //联动数组
      goods:[],
      //确定按钮是否显示
      defineIf: false,
      //厂商编码输入框是否禁止
      manufacturerIdDisabled: true,
      //其他栏位输入框是否禁止
      currencyDisabled: true,
      //地区码修改禁止
      mainAreaIdDisabled:false,
      // 显示搜索条件
      showSearch: true,
      visible: false,
      sortOptions: [],
      dataForm: {
        countryId: '',
        catalog: '',
        recordCrtDate: '',
        clearStatus: '',
        mainAreaId: '',
        detailAreaId: '',
        isCentralizedPurchase: '',
        originalChineseName: '',
        manufacturerEnglishName: '',
        capitalCurrencyCode: '',
        turnover: '',
        turnoverCurrencyCode: '',
        billAddress: '',
        billZipCode: '',
        web: '',
        capital:''
      },
      statusTabs:'',
      rules: {
        countryId: [
          {required: true, message: '请输入国别', trigger: 'blur'},
          { required: true, validator: validateContacts, trigger: "blur" },
          {max: 10, message: '最大长度为10个字符', trigger: 'blur'}],
        taxNo: [
          { pattern:/^[a-zA-Z\d]+$/, message: "请输入正确的税号(数字和字母)", trigger: "blur"},
          {required: true, message: '请输入税号', trigger: 'blur'},
          {max: 50, message: '最大长度为50个字符', trigger: 'blur'}],
        catalog: [{required: true, message: '请输入厂商类别', trigger: 'blur'},
          {max: 10, message: '最大长度为10个字符', trigger: 'blur'}],
        owner: [
          { required: true, validator: checkRealName, trigger: "blur" },
          {max: 30, message: '最大长度为30个字符', trigger: 'blur'}],
        manufacturerShortName: [
          { required: true, validator: validateContacts, trigger: "blur" },
          {required: true, message: '请输入厂商简称', trigger: 'blur'},
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}],
        mainAreaId: [
          {required: true, message: '请输入所在区码', trigger: 'blur'},
          {max: 2, message: '最大长度为2个字符', trigger: 'blur'}],
        detailAreaId: [{required: true, message: '请输入所在区码', trigger: 'blur'},
          {max: 2, message: '最大长度为2个字符', trigger: 'blur'}],
        isCentralizedPurchase: [{max: 1, message: '最大长度为1个字符', trigger: 'blur'}],
        manufacturerChineseName: [
          { required: true, validator: validateContacts, trigger: "blur" },
          { message: '请输入中文名称', trigger: 'blur'},
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}],
        originalChineseName: [
          { pattern:/^[\u0391-\uFFE5]+$/, message: "请输入正确的原中文名称", trigger: "blur"},
          {max: 1000, message: '最大长度为1000个字符', trigger: 'blur'}],
        manufacturerEnglishName: [
          {required: true, message: '请输入英文名称', trigger: 'blur'},
         { pattern: /^[A-Za-z]+$/, message: "请输入英文", trigger: "blur"},
          {max: 100, message: '最大长度为100个字符', trigger: 'blur'}],
        capital: [
          {required: true, message: '请输入资本额', trigger: 'blur'},
          { pattern:/^[0-9]*$/, message: "请输入正确的资本额", trigger: "blur"},
         ],
        capitalCurrencyCode: [
          {required: true, message: '请输入资本额币别"', trigger: 'blur'},],
        turnover:[
          {required: true, message: '请输入营业额', trigger: 'blur'},
          { pattern:/^[0-9]*$/, message: "请输入正确的营业额", trigger: "blur"},
          {max: 18, message: '最大长度为18个字符', trigger: 'blur'}],
        turnoverCurrencyCode: [
          {required: true, message: '请输入营业额币别"', trigger: 'blur'},],
        billAddress: [
          {max: 50, message: '最大长度为50个字符', trigger: 'blur'}],
        billZipCode: [
          { pattern:/^[0-9]*$/, message: "请输入邮递区号", trigger: "blur"},
          {max: 10, message: '最大长度为10个字符', trigger: 'blur'}],
        web: [
          { pattern: /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/, message: "请输入正确的网址", trigger: "blur"},
          {max: 60, message: '最大长度为60个字符', trigger: 'blur'}],
        clearStatus:[
          {max: 50, message: '最大长度为50个字符', trigger: 'blur'}]

      },
      relationsForm: {
        relation: ''
      },
      fcForm: {},
      dataRule: {},
      lage: true,
      resultList: [],
      flagForm: {}
    }
  },
  created() {

  },

  methods: {
    cancel() {
      this.visible = false;
      this.flagForm = {}
      this.reset()
    },


    getQueryFc(val) {
      this.statusTabs = val
      this.beforeLeave()
    },

    getQueryMp(val) {
     this.statusTabs = val
      this.beforeLeave()
    },
    getQuerySo(val) {
      this.statusTabs = val
      this.beforeLeave()
    },
    getQuerySt(val) {
      this.statusTabs = val
      this.beforeLeave()
    },

    // 表单重置
    reset() {
      this.dataForm = {},
        this.resetForm("dataForm");

    },
    submitForm(formName) {

      this.$refs[formName].validate(valid => {

        if (valid) {
          if (this.dataForm.manufacturerId != null) {
            updateManufacturerBasics(this.dataForm).then(response => {
              this.$modal.msgSuccess("基础资料修改成功");
              this.$emit('getLists');
              this.statusTabs=true
            });
          } else {
            addManufacturerBasics(this.dataForm).then(response => {
              this.$modal.msgSuccess("基础资料新增成功");
              this.$emit('getLists');
              this.statusTabs=true
            });
          }
        } else {
          return false;
        }
      });
      this.statusTabs=false
    },

    AddPage(dictType) {
      this.reset()
      this.mainAreaIdIf = true
      this.mainAreaIdIfN = false
      this.visible = true
      this.manufacturerIdDisabled = true
      this.currencyDisabled = false
      this.mainAreaIdDisabled= false
      this.defineIf = true
      this.flagForm.FC = false
      this.flagForm.MP = false
      this.flagForm.SO = false
      this.flagForm.ST = false
    },
    init(id, manufacturerChineseName,taxNo) {
      this.mainAreaIdIf = false
      this.mainAreaIdIfN = true
      this.visible = true
      this.manufacturerIdDisabled = true
      this.currencyDisabled = true
      this.mainAreaIdDisabled= true
      this.defineIf = false
      queryRelations(id).then(response => {
        this.relationsForm = response.data
        this.flagForm = {}
        this.relationsForm.map(item => {
          if (item.relation === "FC") {
            this.flagForm.FC = item.relation
            this.$nextTick(() => {
              this.$refs.queryFc.initFc(id, manufacturerChineseName)
            })
          } else if (item.relation === "MP") {
            this.flagForm.MP = item.relation
            this.$nextTick(() => {
              this.$refs.queryMp.initMP(id, manufacturerChineseName,taxNo)
            })
          } else if (item.relation === "ST") {
            this.flagForm.ST = item.relation
            this.$nextTick(() => {
              this.$refs.querySt.initSt(id, manufacturerChineseName)
            })
          } else if (item.relation === "SO") {
            this.flagForm.SO = item.relation
            this.$nextTick(() => {
              this.$refs.querySo.initSo(id, manufacturerChineseName,taxNo)
            })
          }
        })
      });
      getBase1(id).then(response => {
        this.dataForm = response.data
      });
    },
    revise(id, manufacturerChineseName,taxNo) {
      console.log(id, manufacturerChineseName, taxNo+"修改");

      this.visible = true
      this.mainAreaIdIf = false
      this.mainAreaIdIfN = true
      this.manufacturerIdDisabled = true
      this.currencyDisabled = false
      this.mainAreaIdDisabled= true
      this.defineIf = true
      queryRelations(id).then(response => {
        this.relationsForm = response.data
        this.flagForm = {}
        this.relationsForm.map(item => {
          if (item.relation === "FC") {
            this.flagForm.FC = item.relation
            this.$nextTick(() => {
              this.$refs.queryFc.initFc(id, manufacturerChineseName)
            })
          } else if (item.relation === "MP") {
            this.flagForm.MP = item.relation
            this.$nextTick(() => {
              this.$refs.queryMp.initMP(id, manufacturerChineseName,taxNo)
            })
          } else if (item.relation === "ST") {
            this.flagForm.ST = item.relation
            this.$nextTick(() => {
              this.$refs.querySt.initSt(id, manufacturerChineseName)
            })
          } else if (item.relation === "SO") {
            this.flagForm.SO = item.relation
            this.$nextTick(() => {
              this.$refs.querySo.initSo(id, manufacturerChineseName,taxNo)
            })
          }
        })
      });
      getBase1(id).then(response => {
        this.dataForm = response.data
        console.log(this.dataForm);
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
<style scoped>
/deep/ .el-form-item--small .el-form-item__label {
  line-height: 47px !important;
}
</style>
