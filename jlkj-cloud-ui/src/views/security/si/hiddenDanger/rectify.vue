<template>
  <div>
    <div style="padding:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               :disabled="formData.type=='view' || formData.type=='check'"
               label-width="100px">
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="duty_person_name"
                          label="整改人">
              <el-input disabled
                        v-model="form.duty_person_name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12"
                  v-if="formData.type!='edit'">
            <el-form-item prop="dispose_time"
                          label="整改时间">
              <el-input v-model="form.dispose_time"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item prop="dispose_content"
                      label="整改内容">
          <el-input type="textarea"
                    :autosize="{ minRows: 5, maxRows: 5 }"
                    v-model="form.dispose_content">
          </el-input>
        </el-form-item>
        <!-- <el-form-item prop="dispose_measure"
                      label="整改措施">
          <el-input type="textarea"
                    :autosize="{ minRows: 5, maxRows: 5 }"
                    v-model="form.dispose_measure">
          </el-input>
        </el-form-item> -->
      </el-form>
      <div style="width:100%;text-align: right;">
        <el-button @click="cancel">取消</el-button>
        <el-button v-if="formData.type=='edit'"
                   :loading="submitLoading"
                   type="primary"
                   @click="submitForm('form')">确认</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { dangerDispose } from "@/api/security/si/hiddenDanger/index";
export default {
  inject: ['fatherClose', 'fatherSubmitSave'],
  props: {
    formData: {
      type: Object
    }
  },
  data () {
    return {
      form: {},
      // 表单校验
      rules: {
        dispose_content: [{ required: true, message: '整改内容不能为空', trigger: 'blur' }],
        // dispose_measure: [{ required: true, message: '整改措施不能为空', trigger: 'blur' }],
      },
      submitLoading: false
    }
  },
  created () {
    if (this.formData.danger_status == 0 && this.formData.type == 'view') {
      this.form = {}
    } else {
      this.form = this.formData
    }
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form.disposer_id = this.$store.getters.userInfo.userId;
          this.form.disposer_name = this.userInfo.userName;
          this.submitLoading = true
          dangerDispose(this.form).then(res => {
            if (res.data.code === "0") {
              this.$message({ type: "success", message: res.data.msg });
              this.fatherSubmitSave()
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
      this.fatherClose()
    }
  }
}
</script>

<style>
</style>
