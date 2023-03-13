<template>
  <div>
    <div style="padding:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="80px">
        <el-form-item label="物料类别"
                      prop="category_id">
          <el-select v-model="form.category_id"
                     :popper-append-to-body="false"
                     style="width:100%;"
                     clearable
                     placeholder="请选择物料类别">
            <el-option v-for="item in options"
                       :key="item.id"
                       :label="item.name"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="materials_name"
                      label="物料名称">
          <el-input v-model="form.materials_name"
                    placeholder="输入内容"></el-input>
        </el-form-item>
        <el-form-item prop="materials_code"
                      label="物料代码">
          <el-input v-model="form.materials_code"
                    placeholder="输入内容"></el-input>
        </el-form-item>
        <el-form-item prop="measurement_unit"
                      label="计量单位">
          <el-input v-model="form.measurement_unit"
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
import { updateMaterials } from "@/api/material/mr/parameter/materialCode";
import { listMaterialsCategoryBox } from "@/api/material/mr/parameter/materialType";

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
        category_id: [{ required: true, message: '物料类别不能为空', trigger: 'change' }],
        materials_name: [{ required: true, message: '物料名称不能为空', trigger: 'blur' }],
        materials_code: [{ required: true, message: '物料代码不能为空', trigger: 'blur' }],
        measurement_unit: [{ required: true, message: '计量单位不能为空', trigger: 'blur' }],
      },
      options: [],
      submitLoading: false
    }
  },
  created () {
    this.form = { ...this.data }
    this.getStorageOptions()
  },
  computed: {
  },
  methods: {
    // 获取料仓列表
    getStorageOptions () {
      const param = {}
      listMaterialsCategoryBox(param).then((res) => {
        // console.log(res)
        res.data.forEach(item => {
          let i = {
            id: item.id,
            name: item.category_name
          }
          this.options.push(i)
        });
      })
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form.modify_user_id = '123456'
          this.form.modify_user_name = '姓名';
          this.submitLoading = true
          // this.$emit('submitSave')
          updateMaterials(this.form).then(res => {
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

<style scoped lang="scss">
::v-deep.el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
</style>
