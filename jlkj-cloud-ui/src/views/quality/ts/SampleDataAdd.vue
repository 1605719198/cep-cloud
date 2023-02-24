<template>
  <el-row>
    <el-col :span="24" style="padding:0 10px">
      <div class="plan_header">
        <el-row>
          <el-col>
            <div style="text-align: center;min-width: 585px;padding-bottom: 40px">
              <el-radio-group v-model="tabPosition">
                <el-radio-button label="YP">样品说明</el-radio-button>
                <el-radio-button label="FX">分析规则</el-radio-button>
                <el-radio-button label="ZD">自动设备优先顺序</el-radio-button>
                <el-radio-button label="TL">碳硫/氧氮分析仪规则</el-radio-button>
                <el-radio-button label="JG">结果发送</el-radio-button>
              </el-radio-group>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="plan_main">
        <keep-alive>
          <sampleDataYP :formDataYP.sync="addForm" ref="form1" v-if="tabPosition==='YP' "/>
          <sampleDataFX :formDataFX.sync="addForm" v-else-if="tabPosition==='FX'"/>
          <sampleDataZD :formDataZD.sync="addForm" v-else-if="tabPosition==='ZD'"/>
          <sampleDataTL :formDataTL.sync="addForm" v-else-if="tabPosition==='TL'"/>
          <sampleDataJG :detailBox="detailBox" :editBox="editBox" :sendDataJG="sendDataJG" :formDataJG.sync="addForm" v-else-if="tabPosition==='JG'"/>
          <div v-else></div>
        </keep-alive>
        <div slot="footer" class="el-dialog__footer" v-show="footerButton">
          <el-button @click="cancel('addForm')">取 消</el-button>
          <el-button type="primary" @click="handleChange()" :loading="states1">确 定</el-button>
        </div>
      </div>
    </el-col>
  </el-row>

</template>
<script>
import sampleDataYP from "./sampleDataYP";
import sampleDataFX from "./sampleDataFX";
import sampleDataZD from "./sampleDataZD";
import sampleDataTL from "./sampleDataTL";
import sampleDataJG from "./sampleDataJG";

export default {
  name: "sampleDataAdd",
  props: ['dataEdit','detailBox','editBox'],
  data() {
    return {
      tabPosition: 'YP',
      addForm: {
      },
      footerButton: true,
      sendDataJG: [],
      states1:false
    }
  },
  created() {
    if (this.editBox == true) {
      this.sendDataJG = this.dataEdit.sendValues
    }
  },
  components: {
    sampleDataYP,
    sampleDataFX,
    sampleDataZD,
    sampleDataTL,
    sampleDataJG,
  },
  mounted() {
    this.addForm = {...this.dataEdit};
    if (this.detailBox == true) {
      this.footerButton = false;
      this.sendDataJG = this.dataEdit.sendValues
    }
  },
  methods: {
    handleChange() {
      this.states1 = true;
      if(typeof(this.addForm.sendValues) != "undefined") {
        this.addForm.sendValues = this.addForm.sendValues.filter(item => item.sendFormat != '' || item.sendStation != '')
        this.addForm.sendValues.forEach(item => {
          item.sampleClass = this.addForm.sampleClass
        })
      }
      if(typeof(this.$refs.form1) != "undefined") {
        //验证表单
        this.$refs.form1.$refs.addForm.validate((valid) => {
          if (valid) {
            this.$emit('submitAdd', this.addForm)
          } else {
            this.states1 = false;
            return false;
          }
        })
      }else {
        //其他表单
        this.states1 = true;
        this.$emit('submitAdd', this.addForm)
      }
    },
    cancel() {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>
/deep/ .el-form-item__content {
  white-space: nowrap;
}
/deep/ .el-text{
  color: red;
}
/deep/ .el-form-item--feedback{
  display: inline-block;
  padding-left: 5px;
}

/*下拉框最后一个显示不完全*/
/deep/ .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
/deep/ .el-dialog__footer {
  margin: 10px -30px -2px;
}

</style>
