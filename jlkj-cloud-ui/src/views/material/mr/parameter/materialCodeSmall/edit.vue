<template>
  <div>
    <div style="padding:20px;">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item prop="category_name" label="物料类别">
          <el-input v-model="form.category_name" readonly/>
        </el-form-item>
        <el-form-item prop="materials_code" label="物料代码">
          <el-input v-model="form.materials_code" readonly/>
        </el-form-item>
        <el-form-item prop="materials_name" label="物料名称">
          <el-input v-model="form.materials_name" readonly/>
        </el-form-item>
        <el-form-item prop="materials_small_name" label="小煤种简称">
          <el-input v-model="form.materials_small_name" placeholder="输入小煤种简称"/>
        </el-form-item>
        <el-form-item prop="vendor_name" label="供应商名称">
          <el-input v-model="form.vendor_name" readonly/>
        </el-form-item>
        <el-form-item prop="send_vendor_name" label="发货地名称">
          <el-input v-model="form.send_vendor_name" readonly/>
        </el-form-item>
        <el-form-item prop="measurement_unit" label="计量单位">
          <el-input v-model="form.measurement_unit" readonly/>
        </el-form-item>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;">
      <el-button @click="cancel">取消</el-button>
      <el-button :loading="submitLoading" type="primary" @click="submitForm('form')">确定</el-button>
    </div>
  </div>
</template>

<script>
  import {updateSmallMaterials} from "@/api/material/mr/parameter/materialCode";
  import {mapGetters} from "vuex";

  export default {
    props: {
      data: {
        type: Object
      }
    },
    data() {
      return {
        form: {
          materials_small_name: '',
        },
        // 表单校验
        rules: {
          materials_small_name: [{required: true, message: '小煤种简称不能为空', trigger: 'blur'}],
        },
        options: [],
        submitLoading: false
      }
    },
    created() {
      this.form = {...this.data};
    },
    computed: {
      ...mapGetters(["userInfo"]),
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.form.modify_user_id = this.$store.getters.userInfo.userId;
            this.form.modify_user_name = this.$store.getters.userInfo.nickName;
            this.submitLoading = true;
            updateSmallMaterials(this.form).then(res => {
              if (res.code == 200) {
                this.$message({type: "success", message: res.msg});
                this.$emit('submitSave', res.msg)
              }
              this.submitLoading = false;
            }, error => {
              this.submitLoading = false;
              window.console.log(error);
            });
          }
        });
      },
      cancel() {
        this.$emit('close')
      }
    }
  }
</script>

<style scoped lang="scss">

</style>
