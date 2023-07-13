<template>
  <div class="avue-crud">
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item prop="dicNo"
                          label="资料代号">
              <el-input v-model="form.dicNo" :maxlength="50"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item prop="dicName"
                          label="资料名称">
              <el-input v-model="form.dicName" :maxlength="50"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item prop="status"
                          label="资料状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item prop="updateEmp"
                          label="输入人">
              {{form.updateEmp}}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="updateDate"
                          label="输入日期">
              {{form.updateDate}}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="width:100%;text-align: center;">
      <el-button :loading="submitLoading"
                 type="primary"
                 @click="submitForm('form')">确定</el-button>
      <el-button @click="cancel">取消</el-button>
    </div>
  </div>
</template>

<script>
import { addBaseInfo,updateBaseInfo } from "@/api/human/hm/baseInfo"
export default {
  dicts: ['sys_normal_disable'],
  props: {
    type: {
      type: String
    },
    data: {
      type: Object
    },
    uuid: {
      uuid: String
    }
  },
  data () {
    return {
      form: {},
      // 表单校验
      rules: {
        dicNo: [
          { required: true, message: '资料代号不能为空', trigger: 'blur' },
          { max: 50, message: '资料代号长度不能超过50', trigger: 'blur' }
        ],
        dicName: [
          { required: true, message: '资料名称不能为空', trigger: 'blur' },
          { max: 50, message: '资料名称长度不能超过50', trigger: 'blur' }
        ],
        status: [{ required: true, message: '资料状态不能为空', trigger: 'blur' }]
      },
      submitLoading: false,
    }
  },
  created() {
    this.form = {...this.data}
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            this.submitLoading = true
            this.form.parentId = this.uuid
            addBaseInfo(this.form).then(res => {
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
            this.submitLoading = true
            updateBaseInfo(this.form).then(res => {
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

<style scoped>
.el-row {
  margin-bottom: 10px;
}
</style>
