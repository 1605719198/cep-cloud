<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="120px">
        <el-form-item prop="rule_number"
                      label="指标编号">
          <el-input v-model="form.rule_number"
                    placeholder="请输入规则编号"></el-input>
        </el-form-item>
        <el-form-item prop="rule_describe"
                      label="指标名称">
          <el-input v-model="form.rule_describe"
                    placeholder="请输入规则描述"></el-input>
        </el-form-item>
        <el-form-item prop="rule_level"
                      label=" 指标等级">
          <el-select v-model="form.rule_level"
                     placeholder="请选择等级"
                     class="customSelectStyle"
                     :popper-append-to-body="false">
            <el-option label="优"
                       value="优"></el-option>
            <el-option label="良"
                       value="良"></el-option>
            <el-option label="轻度污染"
                       value="轻度污染"></el-option>
            <el-option label="中度污染"
                       value="中度污染"></el-option>
            <el-option label="重度污染"
                       value="重度污染"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="status"
                      label="状态">
          <el-select v-model="form.status"
                     placeholder="请选择状态"
                     class="customSelectStyle"
                     :popper-append-to-body="false">
            <el-option label="启用"
                       value="启用"></el-option>
            <el-option label="关闭"
                       value="关闭"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="计量单位"
                      prop="unit">
          <el-select v-model="form.unit"
                     placeholder="请选择"
                     class="customSelectStyle"
                     :popper-append-to-body="false">
            <el-option v-for="item in unit"
                       :key="item.value"
                       :label="item.label"
                       :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="rule_up"
                      label="数值上限">
          <el-input v-model="form.rule_up"
                    placeholder="请输入上限"></el-input>

        </el-form-item>
        <el-form-item prop="rule_down"
                      label="数值下限">
          <el-input v-model="form.rule_down"
                    placeholder="请输入下限"></el-input>
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
import { EInsert, EUpdate } from "@/api/security/si/parameter/environmentalStandard";
import { mapGetters } from "vuex";
export default {
  computed: {
    ...mapGetters(["userInfo"]),
  },
  mounted () {
    // this.form = {...this.data}
  },
  props: {
    type: {
      type: String
    },
    data: {
      type: Object
    }
  },
  data () {
    var rule_up = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入数值上限'));
      } else {
        if (Number(value) < Number(this.form.rule_down) && this.form.rule_down) {
          callback(new Error('数值上限不能小于数值下限'));
        } else if (value == this.form.rule_down && this.form.rule_down) {
          callback(new Error('数值上限不能等于数值下限'));
        } else {
          callback();
        }
      }
    };
    var rule_down = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入数值下限'));
      } else {
        if (Number(value) > Number(this.form.rule_up) && this.form.rule_up) {
          callback(new Error('数值下限不能大于数值上限'));
        } else if (value == this.form.rule_up && this.form.rule_up) {
          callback(new Error('数值下限不能等于数值上限'));
        } else {
          callback();
        }
      }
    };
    return {
      unit: [
        { value: '%', label: '%' },
        { value: 'm³', label: 'm³' },
        { value: 't', label: 't' },
        { value: 'μg/m³', label: 'μg/m³' },
        { value: 'mg/m³', label: 'mg/m³' },
      ],
      form: {},
      // 表单校验
      rules: {
        rule_level: [{ required: true, message: '请选择', trigger: 'blur' }],
        rule_number: [{ required: true, message: '规则编号不能为空', trigger: 'blur' }],
        rule_describe: [{ required: true, message: '规则描述不能为空', trigger: 'blur' }],
        status: [{ required: true, message: '请选择', trigger: 'blur' }],
        rule_up: [
          { required: true, validator: rule_up, trigger: 'blur' },
          { type: 'number', message: '请输入数字', trigger: 'blur', transform: (value) => Number(value) },
        ],
        rule_down: [
          { required: true, validator: rule_down, trigger: 'blur' },
          { type: 'number', message: '请输入数字', trigger: 'blur', transform: (value) => Number(value) },
        ],
        unit: [{ required: true, message: '单位不能为空', trigger: 'blur' }],
      },
      submitLoading: false//防止多次提交
    }
  },
  created () {
    this.form = { ...this.data }
  },
  methods: {
    //submitForm后的函数是随便起的
    //this.$ref[a]——————>$refs对应开头的ref，带上后面的参数即可获得所有的form内的参数
    //validate((valid) =>{})————————————>判断方法，后面的函数是随便起的，但是大部分都用这个valid，
    //其实他并没有啥特别的用意，但是一定要与下面的if判断的方法名字一致
    submitForm (a) {
      this.$refs[a].validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            this.form.create_user_id = this.$store.getters.userInfo.userId;
            this.form.create_user_name = this.userInfo.userName;
            // this.$emit('submitSave')
            this.submitLoading = true
            EInsert(this.form).then(res => {
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
            EUpdate(this.form).then(res => {
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
<style >
</style>
