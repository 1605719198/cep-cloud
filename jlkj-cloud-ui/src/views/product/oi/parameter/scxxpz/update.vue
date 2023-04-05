<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item prop="materials_code" label="焦炭等级">
          <el-select style="width:100%;" :popper-append-to-body="false" class="customSelectStyle"
            v-model="form.materials_code" clearable filterable placeholder="选择焦炭等级">
            <el-option v-for="item in gradeOptions" @click.native="changeName(item.name)" :key="item.id"
              :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="quenching_method" label="熄焦方式">
          <el-select style="width:100%;" :popper-append-to-body="false" class="customSelectStyle"
            v-model="form.quenching_method" clearable filterable placeholder="选择熄焦方式">
            <el-option v-for="item in typeOptions" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="single_hole_weight" label="单孔理论重量">
          <el-input v-model="form.single_hole_weight" @input="changeInput('single_hole_weight')"
            placeholder="请输入单孔理论重量">
            <i slot="suffix">kg</i>
          </el-input>
        </el-form-item>
        <el-form-item prop="single_consumption" label="理论单耗">
          <el-input v-model="form.single_consumption" @input="changeInput('single_consumption')" placeholder="请输入理论单耗">

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
import { insertProductionInformationConfiguration, updateProductionInformationConfiguration } from "@/api/production/oi/parameter";
export default {
  props: {
    type: {
      type: String
    },
    data: {
      type: Object
    }
  },
  data() {
    return {
      form: {},
      // 表单校验
      rules: {
        materials_code: [{ required: true, message: '焦炭等级不能为空', trigger: 'change' }],
        quenching_method: [{ required: true, message: '熄焦方式不能为空', trigger: 'change' }],
        single_hole_weight: [{ required: true, message: '单孔理论重量不能为空', trigger: 'blur' }],
        single_consumption: [{ required: true, message: '理论单耗不能为空', trigger: 'blur' }],
      },
      gradeOptions: [],
      typeOptions: [
        {
          id: '1',
          name: '干熄'
        },
        {
          id: '2',
          name: '湿熄'
        }
      ],
      submitLoading: false
    }
  },
  created() {
    this.getMaterialsOptions()
    this.form = { ...this.data }
  },
  computed: {
  },
  methods: {
    //获取焦炭等级
    getMaterialsOptions() {
      const param = {}
      listMaterialsBoxJ(param).then((res) => {
        // console.log(res)
        let options = []
        res.data.forEach(item => {
          let i = {
            id: item.id,
            name: item.materials_name
          }
          options.push(i)
        });
        this.gradeOptions = options
      })
    },
    //获取下拉name
    changeName(name) {
      this.form.materials_name = name
    },
    //限制方法
    changeInput(name) {
      if (name == 'single_consumption') {
        this.form[name] = this.form[name].replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1')
      } else {
        //限制正整数
        this.form[name] = this.form[name].replace(/[^\d]/g, "")
      }
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            this.form.create_user_id = this.$store.state.user.userInfo.userName,
            this.form.create_user_name = this.$store.state.user.userInfo.nickName,
            this.submitLoading = true
            insertProductionInformationConfiguration(this.form).then(res => {
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
            this.form.modify_user_id = this.$store.state.user.userInfo.userName,
            this.form.modify_user_name = this.$store.state.user.userInfo.nickName,
            this.submitLoading = true
            updateProductionInformationConfiguration(this.form).then(res => {
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
    cancel() {
      this.$emit('close')
    }
  }
}
</script>

<style>

</style>
