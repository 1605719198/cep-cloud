<template>
  <div class="avue-crud">
    <el-form ref="addForm" :model="addForm" status-icon :rules="rules" label-width="80px">
      <el-form-item label="能源代码" prop="engyId">
        <el-input v-model="addForm.engyId" :disabled="engyInput"/>
        <span class="el-text"> *</span>
      </el-form-item>
      <el-form-item label="计量单位" prop="engyUnit">
        <el-input v-model="addForm.engyUnit" :disabled="engyInput"/>
        <span class="el-text"> *</span>
      </el-form-item>
      <el-form-item label="热值单位" prop="calUnit">
        <el-input v-model="addForm.calUnit" :disabled="engyInput"/>
        <span class="el-text"> *</span>
      </el-form-item>
      <el-form-item label="能源种类" prop="engyType">
        <el-radio-group v-model="addForm.engyType">
          <el-radio
            v-for="dict in optionsEngyType"
            :key="dict.dictValue"
            :label="dict.dictValue"
            :disabled="engyInput"
          >{{dict.dictLabel}}</el-radio>
        </el-radio-group>
        <span class="el-text"> *</span>
      </el-form-item>
      <el-form-item label="来源方式" prop="srcType">
        <el-radio-group v-model="addForm.srcType">
          <el-radio
            v-for="dict in optionsSrcType"
            :key="dict.dictValue"
            :label="dict.dictValue"
          >{{dict.dictLabel}}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="能源缩写" prop="engyAc">
        <el-input v-model="addForm.engyAc" />
      </el-form-item>
      <el-form-item label="热值系数" prop="calValue">
        <el-input v-model="addForm.calValue" :disabled="engyInput"/>
        <span class="el-text"> *</span>
      </el-form-item>
      <el-form-item label="能源名称" prop="engyName">
        <el-input v-model="addForm.engyName" :disabled="engyInput"/>
        <span class="el-text"> *</span>
      </el-form-item>
      <el-form-item label="抛帐系统" prop="acctSys">
        <el-radio-group v-model="addForm.acctSys">
          <el-radio
            v-for="dict in optionsAcctSys"
            :key="dict.dictValue"
            :label="dict.dictLabel"
          >{{dict.dictLabel}}</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <div slot="footer" class="el-dialog__footer">
      <el-button @click="cancel('addForm')">取 消</el-button>
      <el-button type="primary" @click="handleChange('addForm')" :loading="states1">确 定</el-button>
    </div>
  </div>
</template>
<script>
  import {mapGetters} from "vuex";

  export default {
    name: "solidLiquidEnergyAdd",
    props: ['dataEdit','editBox','addBox'],
    data() {
      var checkA = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('能源代码栏位为空，请重新输入！'));
        }
        setTimeout(() => {
          if (value.length > 8) {
            callback(new Error('能源代码参数长度为 8 ，请重新输入！'));
          } else {
            callback();
          }
        }, 500);
      };
      var checkB = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('计量单位栏位为空，请重新输入！'));
        }
        setTimeout(() => {
          if (value.length > 8) {
            callback(new Error('计量单位参数长度为 8 ，请重新输入！'));
          } else {
            callback();
          }
        }, 500);
      };
      var checkC = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('热值单位栏位为空，请重新输入！'));
        }
        setTimeout(() => {
          if (value.length > 6) {
            callback(new Error('热值单位参数长度为 6 ，请重新输入！'));
          } else {
            callback();
          }
        }, 500);
      };
      var checkD = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('能源种类栏位为空，请重新输入！'));
        } else {
          callback();
        }
      };
      var checkE = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('热值系数栏位为空，请重新输入！'));
        }
        setTimeout(() => {
          if (isNaN(value)) {
            callback(new Error('请输入数字值'));
          } else {
            if (value.length > 7) {
              callback(new Error('热值系数参数长度为 7 ，请重新输入！'));
            } else {
              callback();
            }
          }
        }, 500);
      };
      var checkF = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('能源名称栏位为空，请重新输入！'));
        }
        setTimeout(() => {
          if (value.length > 30) {
            callback(new Error('能源名称参数长度为 30 ，请重新输入！'));
          } else {
            callback();
          }
        }, 500);
      };
      var checkG = (rule, value, callback) => {
        if (value != null) {
          if (value.length > 10) {
            callback(new Error('能源缩写参数长度为 10 ，请重新输入！'));
          } else {
            callback();
          }
        } else {
          callback();
        }
      };
      return {
        addForm: {
          planYear: '',
          engyId: '',
          engyName: '',
          engyAc: '',
          engyUnit: '',
          calValue: '',
          calUnit: '',
          engyType: '',
          srcType: '',
          acctSys: '',
          createEmpNo: '',
          createTime: '',
          updateEmpNo: '',
          updateTime: ''
        },
        rules: {
          engyId: [
            { validator: checkA, trigger: 'blur' }
          ],
          engyUnit: [
            { validator: checkB, trigger: 'blur' }
          ],
          calUnit: [
            { validator: checkC, trigger: 'blur' }
          ],
          engyType: [
            { validator: checkD, trigger: 'blur' }
          ],
          calValue: [
            { validator: checkE, trigger: 'blur' }
          ],
          engyName: [
            { validator: checkF, trigger: 'blur' }
          ],
          engyAc: [
            { validator: checkG, trigger: 'blur' }
          ]
        },
        optionsEngyType: [],
        optionsSrcType: [],
        optionsAcctSys: [],
        tableData: [],
        engyInput:'',
        states1:false
      }
    },
    created() {
      this.getDicts("engy_solid_type").then(response =>{
        this.optionsEngyType = response.data;
      })
      this.getDicts("engy_acct_sys").then(response =>{
        this.optionsAcctSys = response.data;
      })
      this.getDicts("engy_src_type").then(response =>{
        this.optionsSrcType = response.data;
      })
      if(this.addBox==true){
        this.engyInput = false;
      }
      if(this.editBox==true){
        this.engyInput = true;
      }
    },
    mounted() {
      this.addForm = {...this.dataEdit}
    },
    // computed: {
    //   ...mapGetters(["userInfo"])
    // },
    methods: {
      handleChange(form) {
        this.states1 = true;
        this.$refs[form].validate((valid) => {
          if (valid) {
            // this.addForm.createEmpNo = this.userInfo.userName;
            this.$emit('submitAdd', this.addForm)
          } else {
            this.states1 = false;
            return false;
          }
        });
      },
      cancel(formName) {
        this.$refs[formName].resetFields();
        this.$emit('close')
      }
    }
  }
</script>

<style scoped>
.el-dialog__footer {
  padding: 10px 0 25px;
}

::v-deep .el-form-item__content {
  white-space: nowrap;
}
::v-deep .el-text{
  color: red;
}
::v-deep .el-input{
  width: 90%;
}
::v-deep .el-select{
  width: 90%!important;
}
::v-deep .el-input.el-input--suffix{
  width: auto;
}
/*下拉框最后一个显示不完全*/
::v-deep .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
