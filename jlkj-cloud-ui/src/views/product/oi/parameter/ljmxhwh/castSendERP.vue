<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="120px">
        <el-form-item label="财务日期"
                      prop="account_date">
          <el-date-picker type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择日期"
                          v-model="form.account_date"
                          style="width: 100%;"></el-date-picker>
        </el-form-item>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;margin-bottom: 25px;">
      <el-button @click="cancel">取消</el-button>
      <el-button :loading="submitLoading"
                 type="primary"
                 @click="submitForm('form')">确定</el-button>
    </div>
  </div>
</template>

<script>
import { confirmProductionCoalConsume } from "@/api/production/oi/parameter";
import { mapGetters } from "vuex";
export default {
  data () {
    return {
      form: {},
      // 表单校验
      rules: {
        account_date: [{ required: true, message: '财务日期不能为空', trigger: 'change' }],
      },
      submitLoading: false
    }
  },
  created () { },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.submitLoading = true
          confirmProductionCoalConsume(this.form).then(res => {
            if (res.data.code === "0") {
              this.$message({ type: "success", message: res.data.msg });
              this.$emit('submitSave', res.data.msg)
            }
            this.submitLoading = false
          }, error => {
            this.submitLoading = false
            window.console.log(error);
          });
        }
      });
    },
    cancel () {
      this.$emit('close')
    }
  }
}
</script>

<style>
</style>
