<template>
  <div>
    <div style="padding:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="80px">
        <el-form-item prop="category_name"
                      label="类别名称">
          <el-input v-model="form.category_name"
                    placeholder="输入内容"></el-input>
        </el-form-item>
        <el-form-item prop="category_code"
                      label="类别代码">
          <el-input v-model="form.category_code"
                    placeholder="输入内容"></el-input>
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
import { updateMaterialsCategory } from "@/api/material/mr/parameter/materialType";
import { mapGetters } from "vuex";
export default {
  props: {
    data: {
      type: Object
    }
  },
  data () {
    return {
      form: {
        category_name: '',
        category_code: ''
      },
      // 表单校验
      rules: {
        category_name: [{ required: true, message: '类别名称不能为空', trigger: 'blur' }],
        category_code: [{ required: true, message: '类别代码不能为空', trigger: 'blur' }],
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
          this.form.modify_user_id = '123456'
          this.form.modify_user_name = '姓名';
          this.submitLoading = true
          // this.$emit('submitSave')
          updateMaterialsCategory(this.form).then(res => {
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
