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
                      label="焦炭等级">
          <el-select style="width:100%;"
                     :popper-append-to-body="false"
                     class="customSelectStyle"
                     v-model="form.materials_code"
                     clearable
                     filterable
                     placeholder="选择焦炭等级">
            <el-option v-for="item in gradeOptions"
                       @click.native="changeName(item.name)"
                       :key="item.id"
                       :label="item.name"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="yield"
                      label="产量">
          <el-input v-model="form.yield"
                    @input="changeInput('yield')"
                    placeholder="输入内容">
            <i slot="suffix">kg</i>
          </el-input>
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
import { listMaterialsBoxJ } from "@/api/material/mr/parameter/materialCode"
import { insertProductionCokeShedOutput, updateProductionCokeShedOutput } from "@/api/production/oi/parameter";
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
        materials_code: [{ required: true, message: '焦炭等级不能为空', trigger: 'change' }],
        yield: [{ required: true, message: '产量不能为空', trigger: 'blur' }]
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
  },
  methods: {
    //获取焦炭等级
    getMaterialsOptions () {
      const param = {}
      listMaterialsBoxJ(param).then((res) => {
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
    //限制
    changeInput (name) {
      //正整数
      // this.form[name] = this.form[name].replace(/[^\d]/g, "")
      //el-input 只能输入整数（包括正整数、负整数、0）
      this.form[name] = this.form[name].replace(/[^\-\d]/g, ""); // 只能输入-和数字
      this.form[name] = this.form[name].replace(/\-{2,}/g, "-"); // -只能保留一个
      this.form[name] = this.form[name].replace(/(\d)\-/g, "$1"); // 数字后面不能接-，不能出现类似-11-2,12-，11-23
      this.form[name] = this.form[name].replace(/-(0+)/g, "0"); // 不能出现-0,-001,-0001类似
      this.form[name] = this.form[name].replace(/^0+(\d)/, "$1"); // 第一位0开头，0后面为数字，则过滤掉，取后面的数字
      this.form[name] = this.form[name].replace(/(-?\d{15})\d*/, '$1') // 最多保留15位整数
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            this.form.create_user_id = this.$store.getters.userInfo.userId;
            this.form.create_user_name = this.$store.state.user.userInfo.nickName;
            this.submitLoading = true
            insertProductionCokeShedOutput(this.form).then(res => {
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
            this.submitLoading = true
            updateProductionCokeShedOutput(this.form).then(res => {
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
