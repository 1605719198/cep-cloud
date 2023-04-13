<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" :rules="rules" label-position="left" label-width="140px" class="kForm">
      <el-row>
        <el-form-item label="规定装煤量" prop="standard_coal_loading">
          <el-input @input="changeInput('standard_coal_loading')" v-model="form.standard_coal_loading">
            <template slot="append">t</template>
          </el-input>
        </el-form-item>
        <el-form-item label="规定装焦时间" prop="coking_cycle">
          <el-input @input="changeInput('coking_cycle')" v-model="form.coking_cycle">
            <template slot="append">min</template>
          </el-input>
        </el-form-item>
        <el-form-item label="焦侧标准火道温度" prop="fire_temperature">
          <el-input @input="changeInput('fire_temperature')" v-model="form.fire_temperature">
            <template slot="append">℃</template>
          </el-input>
        </el-form-item>
        <el-form-item label="直行标准温度" prop="zhi_hang_temperature">
          <el-input @input="changeInput('zhi_hang_temperature')" v-model="form.zhi_hang_temperature">
            <template slot="append">℃</template>
          </el-input>
        </el-form-item>
        <el-form-item label="K装正负" prop="coal_loading_factor">
          <el-input v-model="form.coal_loading_factor" />
        </el-form-item>
        <el-form-item label="K计正负" prop="planning_factor">
          <el-input v-model="form.planning_factor" />
        </el-form-item>
        <el-form-item label="K均正负" prop="average_coefficient">
          <el-input v-model="form.average_coefficient" />
        </el-form-item>
        <el-form-item label="K安正负" prop="stability_factor">
          <el-input v-model="form.stability_factor" />
        </el-form-item>
        <div class="el-dialog__footer" style="padding: 0;  padding-bottom: 25px; ">
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="handleChange('form')" :loading="submitLoading">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </span>
        </div>
      </el-row>
    </el-form>

  </div>
</template>
<script>
import { addCokeOvenParameterStandard, editCokeOvenParameterStandard } from "@/api/production/oi/parameter";

export default {
  name: "kValueEdit",
  props: ['data', 'type'],
  data() {
    return {
      form: {
        standard_coal_loading: '',
        coking_cycle: '',
        fire_temperature: '',
        zhi_hang_temperature: '',
        coal_loading_factor: '',
        planning_factor: '',
        average_coefficient: '',
        stability_factor: '',
      },
      rules: {
        standard_coal_loading: [{ required: true, message: '请输入规定装煤量', trigger: 'blur' },
        { type: 'number', message: '请输入数字', trigger: 'blur', transform: (value) => Number(value) },],
        coking_cycle: [{ required: true, message: '请选择规定结焦时间', trigger: 'blur' },
        { type: 'number', message: '请输入数字', trigger: 'blur', transform: (value) => Number(value) },],
        fire_temperature: [{ required: true, message: '请输入焦侧标准火道温度', trigger: 'blur' },
        { type: 'number', message: '请输入数字', trigger: 'blur', transform: (value) => Number(value) },],
        zhi_hang_temperature: [{ required: true, message: '请输入直行标准温度', trigger: 'blur' },
        { type: 'number', message: '请输入数字', trigger: 'blur', transform: (value) => Number(value) },],
        coal_loading_factor: [{ required: true, message: '请输入K装正负', trigger: 'blur' },
        { type: 'number', message: '请输入数字', trigger: 'blur', transform: (value) => Number(value) },],
        planning_factor: [{ required: true, message: '请输入K计正负', trigger: 'blur' },
        { type: 'number', message: '请输入数字', trigger: 'blur', transform: (value) => Number(value) },],
        average_coefficient: [{ required: true, message: '请输入K均正负', trigger: 'blur' },
        { type: 'number', message: '请输入数字', trigger: 'blur', transform: (value) => Number(value) },],
        stability_factor: [{ required: true, message: '请输入K安正负', trigger: 'blur' },
        { type: 'number', message: '请输入数字', trigger: 'blur', transform: (value) => Number(value) },],
      },
      submitLoading: false,
    }
  },
  computed: {
  },
  created() {
  },
  mounted() {
    this.form = { ...this.data }
  },
  methods: {
    //限制只有两位小数的正整数
    changeInput(name) {
      this.form[name] = this.form[name].replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1')
    },
    handleChange(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.submitLoading = true;
          if (this.type === 'add') {
            this.form.create_user_id = this.$store.getters.userInfo.userId,
            this.form.create_user_name = this.$store.state.user.userInfo.nickName,
            addCokeOvenParameterStandard(this.form).then(res => {
              if (res.code === 200) {
                this.$message({
                  type: "success", message: res.msg, duration: 1000,
                  onClose: () => {
                    this.$emit('submitSave', res.msg)
                  }
                });
              }
              this.submitLoading = false;
            }, error => {
              this.submitLoading = false;
              window.console.log(error);
            });
          } else if (this.type === 'edit') {
            this.form.modify_user_id = this.$store.getters.userInfo.userId,
            this.form.modify_user_name = this.$store.state.user.userInfo.nickName,
            editCokeOvenParameterStandard(this.form).then(res => {
              if (res.code === 200) {
                this.$message({
                  type: "success", message: res.msg, duration: 1000,
                  onClose: () => {
                    this.$emit('submitSave', res.msg)
                  }
                });
              }
              this.submitLoading = false;
            }, error => {
              this.submitLoading = false;
              window.console.log(error);
            });
          }
        } else {
          return false;
        }
      });
    },
    cancel() {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>
.avue-crud {
  padding-top: 20px;
}

.el-dialog__body {
  word-break: unset;
}

.el-col-22 {
  padding-right: 10px;
}
</style>
<style>
.kForm .el-input-group__append {
  width: 30px !important;
  text-align: center;
}
</style>
