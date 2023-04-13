<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="120px">
        <el-form-item prop="account_date"
                      label="财务日期">
          <el-date-picker type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择日期"
                          v-model="form.account_date"
                          style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item prop="materials_code"
                      label="煤种">
          <el-select style="width:100%;"
                     :popper-append-to-body="false"
                     class="customSelectStyle"
                     v-model="form.materials_code"
                     clearable
                     filterable
                     placeholder="选择煤种">
            <el-option v-for="item in gradeOptions"
                       @click.native="changeName(item.name)"
                       :key="item.id"
                       :label="item.name"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="consumption"
                      label="消耗量">
          <el-input v-model="form.consumption"
                    @input="changeInput('consumption')"
                    placeholder="输入内容">
            <i slot="suffix">kg</i>
          </el-input>
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
import { listMaterialsBoxM } from "@/api/material/mr/parameter/materialCode"
import { insertProductionCoalConsume, updateProductionCoalConsume } from "@/api/production/oi/parameter";
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
        account_date: [{ required: true, message: '财务日期不能为空', trigger: 'change' }],
        materials_code: [{ required: true, message: '煤种不能为空', trigger: 'change' }],
        consumption: [{ required: true, message: '消耗量不能为空', trigger: 'blur' }]
      },
      gradeOptions: [],
      submitLoading: false
    }
  },
  created () {
    this.getMaterialsOptions()
    this.form = { ...this.data }
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    //获取煤种
    getMaterialsOptions () {
      const param = {}
      listMaterialsBoxM(param).then((res) => {
        // console.log(res)
        let options = []
        res.forEach(item => {
          let i = {
            id: item.materials_code,
            name: item.materials_name
          }
          options.push(i)
        });
        this.gradeOptions = options
      })
    },
    //获取下拉name
    changeName (name) {
      this.form.materials_name = name
    },
    //限制正整数
    changeInput (name) {
      this.form[name] = this.form[name].replace(/[^\d]/g, "")
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            this.form.create_user_id = this.$store.getters.userInfo.userId;
            this.form.create_user_name = this.userInfo.userName;
            this.submitLoading = true
            insertProductionCoalConsume(this.form).then(res => {
              if (res.data.code === "0") {
                this.$message({ type: "success", message: res.data.msg });
                this.$emit('submitSave', res.data.msg)
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
            updateProductionCoalConsume(this.form).then(res => {
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
