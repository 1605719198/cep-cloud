<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="80px">
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="name"
                          label="名称">
              <el-input v-model="form.name"
                        placeholder="请输入名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="gd"
                          label="工段">
              <el-select style="width: 100%;"
                         :popper-append-to-body="false"
                         v-model="form.gd"
                         clearable
                         placeholder="选择工段">
                <el-option v-for="item in options"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="conut"
                          label="数量">
              <el-input v-model="form.conut"
                        placeholder="请输入数量"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="xh"
                          label="型号">
              <el-input v-model="form.xh"
                        placeholder="请输入型号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;">
      <el-button @click="cancel">取消</el-button>
      <el-button type="primary"
                 @click="submitForm('form')">确认</el-button>
    </div>
  </div>
</template>

<script>

import { mapGetters } from "vuex";
export default {
  data () {
    return {
      form: {
        silo_id: '',
        storage_spaces_name: ''
      },
      // 表单校验
      rules: {
        name: [{ required: true, message: '名称不能为空', trigger: 'blur' }],
        gd: [{ required: true, message: '工段不能为空', trigger: 'change' }],
        conut: [{ required: true, message: '数量不能为空', trigger: 'blur' }],
      },
      options: []
    }
  },
  created () {
    // this.getStorageOptions()
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    // 获取料仓列表
    // getStorageOptions () {
    //   const param = {}
    //   listSilosBox(param).then((res) => {
    //     // console.log(res)
    //     res.data.forEach(item => {
    //       let i = {
    //         id: item.id,
    //         name: item.silo_name
    //       }
    //       this.options.push(i)
    //     });
    //   })
    // },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form.create_user_id = this.$store.getters.userInfo.userId;
          this.form.create_user_name = this.userInfo.userName;
          this.$emit('submitSave')
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
