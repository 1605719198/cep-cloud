<template>
  <div class="avue-crud">
    <el-form ref="addForm" :model="addForm" status-icon :rules="rules" label-width="80px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="能源代码" prop="engyId">
            <el-input v-model="addForm.engyId" :disabled="engyInput"/>
          </el-form-item>
          <el-form-item label="能源缩写" prop="engyAc">
            <el-input v-model="addForm.engyAc"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="能源名称" prop="engyName">
            <el-input v-model="addForm.engyName" :disabled="engyInput"/>
          </el-form-item>
          <el-form-item label="计量单位" prop="engyUnit">
            <el-input v-model="addForm.engyUnit" :disabled="engyInput"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="热值单位" prop="calUnit">
            <el-input v-model="addForm.calUnit" :disabled="engyInput"/>
          </el-form-item>
          <el-form-item label="热值系数" prop="calValue">
            <el-input v-model="addForm.calValue" :disabled="engyInput"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="能源种类" prop="engyType">
            <el-radio-group v-model="addForm.engyType">
              <el-radio
                v-for="dict in optionsEngyType"
                :key="dict.dictValue"
                :label="dict.dictValue"
                :disabled="engyInput"
              >{{ dict.dictLabel }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="来源方式" prop="srcType">
            <el-radio-group v-model="addForm.srcType">
              <el-radio
                v-for="dict in optionsSrcType"
                :key="dict.dictValue"
                :label="dict.dictValue"
              >{{ dict.dictLabel }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="抛帐系统" prop="acctSys">
            <el-radio-group v-model="addForm.acctSys">
              <el-radio
                v-for="dict in optionsAcctSys"
                :key="dict.dictValue"
                :label="dict.dictLabel"
              >{{ dict.dictLabel }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="el-dialog__footer">
      <el-button @click="cancel('addForm')">取 消</el-button>
      <el-button type="primary" @click="handleChange('addForm')" :loading="states1">确 定</el-button>
    </div>
  </div>
</template>
<script>
import SampleDataYP from '../../quality/ts/SampleDataYP'

export default {
  name: "solidLiquidEnergyAdd",
  components: {SampleDataYP},
  props: ['dataEdit', 'editBox', 'addBox'],
  data() {
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
        srcType: "I",
        acctSys: "IP",
        createBy: '',
        createTime: '',
        updateBy: '',
        updateTime: ''
      },
      rules: {
        engyId: [
          {required: true, message: "能源代码不能为空", trigger: "blur"}
        ],
        engyUnit: [
          {required: true, message: "计量单位不能为空", trigger: "blur"}
        ],
        calUnit: [
          {required: true, message: "热值单位不能为空", trigger: "blur"}
        ],
        engyType: [
          {required: true, message: "能源种类不能为空", trigger: "blur"}
        ],
        calValue: [
          {required: true, message: "热值系数不能为空", trigger: "blur"}
        ],
        engyName: [
          {required: true, message: "能源名称不能为空", trigger: "blur"}
        ],
        engyAc: [
          {required: true, message: "能源缩写不能为空", trigger: "blur"}
        ]
      },
      optionsEngyType: [],
      optionsSrcType: [],
      optionsAcctSys: [],
      tableData: [],
      engyInput: '',
      states1: false
    }
  },
  created() {
    this.getDicts("engy_solid_type").then(response => {
      this.optionsEngyType = response.data;
    })
    this.getDicts("engy_acct_sys").then(response => {
      this.optionsAcctSys = response.data;
    })
    this.getDicts("engy_src_type").then(response => {
      this.optionsSrcType = response.data;
    })
    if (this.addBox == true) {
      this.engyInput = false;
    }
    if (this.editBox == true) {
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
          this.addForm.createEmpNo = '姓名';
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

::v-deep .el-text {
  color: red;
}

::v-deep .el-input {
  width: 90%;
}

::v-deep .el-select {
  width: 90% !important;
}

::v-deep .el-input.el-input--suffix {
  width: auto;
}

/*下拉框最后一个显示不完全*/
::v-deep .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
