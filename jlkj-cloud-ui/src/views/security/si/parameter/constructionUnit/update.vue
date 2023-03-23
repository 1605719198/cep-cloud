<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="80px">
        <el-form-item prop="construction_unit_name"
                      label="单位名称">
          <el-input v-model="form.construction_unit_name"
                    placeholder="请输入单位名称"></el-input>
        </el-form-item>
        <el-form-item prop=""
                      label="施工内容">
          <el-input type="textarea"
                    :autosize="{ minRows: 3, maxRows: 3}"
                    placeholder="请输入施工内容"
                    v-model="form.content">
          </el-input>
        </el-form-item>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;">
      <el-button @click="cancel">取消</el-button>
      <el-button :loading="submitLoading"
                 type="primary"
                 @click="submitForm('form')">确定</el-button>
    </div>
  </div>
</template>

<script>
import { constructionunitInsert, constructionunitUpdate } from "@/api/security/si/parameter/constructionUnit";
import { mapGetters } from "vuex";
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
        construction_unit_name: [{ required: true, message: '单位名称不能为空', trigger: 'blur' }]
      },
      options: [],
      submitLoading: false
    }
  },
  created () {
    this.form = { ...this.data }
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            this.form.create_user_id = this.$store.getters.userInfo.userId;
            this.form.create_user_name = this.userInfo.userName;
            // this.$emit('submitSave')
            this.submitLoading = true
            constructionunitInsert(this.form).then(res => {
              if (res.data.code === "0") {
                this.$message({ type: "success", message: res.data.msg });
                this.$emit('submitSave', res.data.msg)
              }else {
                this.$message({ type: "error", message: res.data.msg });
              }
              this.submitLoading = false
            }, error => {
              this.submitLoading = false
              window.console.log(error);
            });
          } else {
            this.form.modify_user_id = this.$store.getters.userInfo.userId;
            this.form.modify_user_name = this.userInfo.userName;
            // this.$emit('submitSave')
            this.submitLoading = true
            constructionunitUpdate(this.form).then(res => {
              if (res.data.code === "0") {
                this.$message({ type: "success", message: res.data.msg });
                this.$emit('submitSave', res.data.msg)
              }else {
                this.$message({ type: "error", message: res.data.msg });
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
