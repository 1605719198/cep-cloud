<template>
  <div>
    <div style="padding:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="80px">
        <el-form-item prop="silo_name"
                      label="料仓名称">
          <el-input v-model="form.silo_name"
                    placeholder="输入内容"></el-input>
        </el-form-item>
        <el-form-item prop="silo_length"
                      label="料仓长度">
          <el-input v-model="form.silo_length"
                    @input="changeInput"
                    placeholder="输入内容">
            <span slot="suffix"
                  style="margin-right:10px;">米</span>
          </el-input>
        </el-form-item>
        <!-- <el-form-item style="text-align: right;">
          <el-button @click="cancel">取消</el-button>
          <el-button type="primary"
                     @click="submitForm('form')">提交</el-button>

        </el-form-item> -->
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
import { saveSilo } from "@/api/material/mr/parameter/stockMaintain";
import { mapGetters } from "vuex";
export default {
  data () {
    return {
      form: {
        silo_name: '',
        silo_length: ''
      },
      // 表单校验
      rules: {
        silo_name: [{ required: true, message: '料仓名称不能为空', trigger: 'blur' }],
        silo_length: [{ required: true, message: '料仓长度不能为空', trigger: 'blur' }],
      },
      submitLoading: false
    }
  },
  computed: {
  },
  methods: {
    //限制正整数
    changeInput () {
      this.form.silo_length = this.form.silo_length.replace(/[^\d]/g, ""); //只能输入数字
      // this.form.silo_length = this.form.silo_length.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
      // this.form.silo_length = this.form.silo_length.replace(/^\./g, ""); //验证第一个字符是数字而不是.
      // this.form.silo_length = this.form.silo_length.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的.
      // this.form.silo_length = this.form.silo_length.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");//只允许输入一个小数点
      // this.form.silo_length = this.form.silo_length.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form.create_user_id = '123456'
          this.form.create_user_name = '姓名';
          this.submitLoading = true
          // setTimeout(() => {
          //   this.submitLoading = false
          //   this.$emit('submitSave')
          // }, 3000)
          saveSilo(this.form).then(res => {
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
