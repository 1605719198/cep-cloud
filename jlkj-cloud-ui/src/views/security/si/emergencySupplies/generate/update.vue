<template>
  <div class="avue-crud">
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="80px">
        <el-form-item prop="section_name"
                      label="工段名称">
          <el-input v-model="form.section_name"
                    placeholder="请输入工段名称"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;">
      <el-button @click="cancel">取消</el-button>
      <el-button :loading="submitLoading"
                 type="primary"
                 @click="submitForm('form')">确认</el-button>
    </div>
  </div>
</template>

<script>
import { workshopaccSectionInsert, workshopaccSectionUpdate } from "@/api/security/si/emergencySupplies/section";
import { mapGetters } from "vuex";
export default {
  //孙子组件接受
  inject: ["fatherMethod"],
  props: {
    type: {
      type: String
    },
    data: {
      type: Object
    },
    workshop_name: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      form: {},
      // 表单校验
      rules: {
        section_name: [{ required: true, message: '工段名称不能为空', trigger: 'blur' }]
      },
      submitLoading: false
    }
  },
  created () {
    this.form = { ...this.data }
    // console.log(this.workshop_name)
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    submitForm (formName) {
      this.form.workshop_name = this.workshop_name
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            this.form.create_user = this.userInfo.userName;
            // this.$emit('submitSave')
            this.submitLoading = true
            workshopaccSectionInsert(this.form).then(res => {
              if (res.data.code === "0") {
                this.$message({ type: "success", message: res.data.msg });
                this.$emit('submitSave', res.data.msg)
                //调用祖父组件方法
                this.fatherMethod()
              }
              this.submitLoading = false
            }, error => {
              this.submitLoading = false
              window.console.log(error);
            });
          } else {
            this.form.create_user = this.userInfo.userName;
            // this.form.modify_user_id = this.$store.getters.userInfo.userId;
            // this.form.modify_user_name = this.userInfo.userName;
            // this.$emit('submitSave')
            this.submitLoading = true
            workshopaccSectionUpdate(this.form).then(res => {
              if (res.data.code === "0") {
                this.$message({ type: "success", message: res.data.msg });
                this.$emit('submitSave', res.data.msg)
                //调用祖父组件方法
                this.fatherMethod()
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
