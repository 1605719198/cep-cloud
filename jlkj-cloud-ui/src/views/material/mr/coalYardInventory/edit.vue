<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="80px"
               label-position="top">
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="category_name"
                          label="物料类型">
              <el-input v-model="form.category_name"
                        disabled
                        placeholder="输入内容"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="materials_name"
                          label="物料名称">
              <el-input v-model="form.materials_name"
                        disabled
                        placeholder="输入内容"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="materials_small_name"
                          label="小煤种">
              <el-input v-model="form.materials_small_name"
                        disabled
                        placeholder="输入内容"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="storage_spaces_name"
                          label="储位名称">
              <el-input v-model="form.storage_spaces_name"
                        disabled
                        placeholder="输入内容">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="inventory"
                          label="库存量">
              <el-input v-model="form.inventory"
                        @input="changeInput"
                        placeholder="输入内容">
                <i slot="suffix">kg</i>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注">
          <el-input type="textarea"
                    :disabled="false"
                    :autosize="{ minRows: 8, maxRows: 8 }"
                    placeholder="请输入内容"
                    v-model="form.remark">
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
import { adjust } from "@/api/material/mr/coalYardInventory/index";

export default {
  props: {
    data: {
      type: Object
    }
  },
  data () {
    return {
      form: {
        category_id: '',
        materials_name: '',
      },
      // 表单校验
      rules: {
        inventory: [{ required: true, message: '物资品名不能为空', trigger: 'blur' }],
      },
      options: [],
      submitLoading: false
    }
  },
  created () {
    this.form = { ...this.data }
  },
  computed: {
  },
  methods: {
    //限制只有两位小数的正整数
    changeInput () {
      this.form.inventory = this.form.inventory.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
      this.form.inventory = this.form.inventory.replace(/^\./g, ""); //验证第一个字符是数字而不是.
      this.form.inventory = this.form.inventory.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的.
      this.form.inventory = this.form.inventory.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");//只允许输入一个小数点
      this.form.inventory = this.form.inventory.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form.modify_user_id = this.$store.getters.userInfo.userId;
          this.form.modify_user_name = this.$store.getters.userInfo.nickName;
          this.submitLoading = true
          adjust(this.form).then(res => {
            if (res.code == 200) {
              this.$message({ type: "success", message: res.msg });
              this.$emit('submitSave', res.msg)
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

<style scoped lang="scss">
::v-deep.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
