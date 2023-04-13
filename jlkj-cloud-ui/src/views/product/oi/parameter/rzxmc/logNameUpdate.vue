<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="100px">
        <el-form-item prop="logs_name"
                      label="日志项名称">
          <el-input v-model="form.logs_name"
                    placeholder="请输入日志项名称"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;margin-bottom: 25px;">
      <el-button :loading="submitLoading" type="primary" @click="submitForm('form')">确定</el-button>
      <el-button @click="cancel">取消</el-button>
    </div>
  </div>
</template>

<script>
import { productionLogsNameSave, productionLogsNameUpdate } from "@/api/production/oi/parameter";
export default {
  props: {
    type: {
      type: String
    },
    data: {
      type: Object
    }
  },
  data () {
    return {
      form: {},
      // 表单校验
      rules: {
        logs_name: [{ required: true, message: '日志项名称不能为空', trigger: 'blur' }]
      },
      submitLoading: false
    }
  },
  created () {
    this.form = { ...this.data }
  },
  computed: {
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            this.form.create_user_id = this.$store.getters.userInfo.userId;
            this.form.create_user_name = this.$store.state.user.userInfo.nickName;
            // this.$emit('submitSave')
            this.submitLoading = true
            productionLogsNameSave(this.form).then(res => {
              if (res.code === 200) {
                this.$message({ type: "success", message: res.msg });
                this.$emit('submitSave', res.msg)
              }
              this.submitLoading = false
            }, error => {
              this.submitLoading = false
              window.console.log(error);
            });
          } else {
            this.form.modify_user_id = this.$store.getters.userInfo.userId;
            this.form.modify_user_name = this.$store.state.user.userInfo.nickName;
            // this.$emit('submitSave')
            this.submitLoading = true
            productionLogsNameUpdate(this.form).then(res => {
              if (res.code === 200) {
                this.$message({ type: "success", message: res.msg });
                this.$emit('submitSave', res.msg)
              }
              this.submitLoading = false
            }, error => {
              this.submitLoading = false
              window.console.log(error);
            });
          }

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
