<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="120px">
        <el-form-item label="财务日期"
                      prop="account_date">
          <el-date-picker type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择日期"
                          v-model="form.account_date"
                          :disabled="type=='edit'"
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
        <el-form-item prop="out_num"
                      label="日推焦总炉数">
          <el-input v-model="form.out_num"
                    @input="changeInput('out_num')"
                    placeholder="输入内容">
          </el-input>
        </el-form-item>
        <el-form-item prop="dry_out_num"
                      label="干熄炉数">
          <el-input v-model="form.dry_out_num"
                    @input="changeInput('dry_out_num')"
                    placeholder="输入内容">
          </el-input>
        </el-form-item>
        <el-form-item prop="wet_out_num"
                      label="湿熄炉数">
          <el-input v-model="form.wet_out_num"
                    disabled
                    placeholder="总炉数-干熄炉数">
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
import { listMaterialsBoxJ } from "@/api/material/mr/parameter/materialCode"
import { insertProductionCokeOvens, updateProductionCokeOvens } from "@/api/production/oi/parameter";
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
    var checkDryOutNum = (rule, value, callback) => {
      if (!value) {
        callback(new Error('干熄炉数不能为空'))
      } else if (Number(value) > Number(this.form.out_num)) {
        callback(new Error('干熄炉数不能大于日推焦总炉数'))
      }
      else {
        callback()
      }
    }
    return {
      form: {

      },
      // 表单校验
      rules: {
        account_date: [{ required: true, message: '财务日期不能为空', trigger: 'change' }],
        materials_code: [{ required: true, message: '焦炭等级不能为空', trigger: 'change' }],
        out_num: [{ required: true, message: '日推焦总炉数不能为空', trigger: 'blur' }],
        dry_out_num: [{ required: true, validator: checkDryOutNum, trigger: 'blur' }],
        wet_out_num: [{ required: true, message: '湿熄炉数不能为空', trigger: 'change' }]
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
    //获取焦炭等级
    getMaterialsOptions () {
      const param = {}
      listMaterialsBoxJ(param).then((res) => {
        // console.log(res)
        let options = []
        res.data.forEach(item => {
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
      //计算湿熄炉数
      if (this.form.out_num && this.form.dry_out_num) {
        this.form.wet_out_num = parseFloat((Number(this.form.out_num) - Number(this.form.dry_out_num)).toFixed(2))
      }
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            this.form.create_user_id = this.$store.getters.userInfo.userId;
            this.form.create_user_name = this.userInfo.userName;
            this.submitLoading = true
            insertProductionCokeOvens(this.form).then(res => {
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
            this.submitLoading = true
            updateProductionCokeOvens(this.form).then(res => {
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
