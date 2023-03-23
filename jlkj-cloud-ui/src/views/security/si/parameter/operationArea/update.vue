<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="120px">
        <el-form-item prop="safe_area_name"
                      label="作业区域名称">
          <el-input v-model="form.safe_area_name"
                    placeholder="请输入安全区域名称"></el-input>
        </el-form-item>
        <el-form-item prop="lat"
                      label="纬度坐标">
          <el-input v-model="form.lat"
                    placeholder="请输入纬度坐标"></el-input>
        </el-form-item>
        <el-form-item prop="lng"
                      label="经度坐标">
          <el-input v-model="form.lng"
                    placeholder="请输入经度坐标"></el-input>
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
import { safeInsert, areaUpdate } from "@/api/security/si/parameter/operationArea";
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
        safe_area_name: [{ required: true, message: '作业区域名称不能为空', trigger: 'blur' }],
        lat: [{ required: true, message: '纬度坐标不能为空', trigger: 'blur' }],
        lng: [{ required: true, message: '经度坐标不能为空', trigger: 'blur' }]
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
            safeInsert(this.form).then(res => {
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
            areaUpdate(this.form).then(res => {
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
