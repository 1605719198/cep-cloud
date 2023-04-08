<template>
  <div style="padding-top:20px;width: 100%;border: 0"
        class="avue-crud el-card__body"
           >
    <div class="avue-crud__search search"
             style="border: 0">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="120px">
              <el-form-item label="物料类别名称"
                    prop="category_name">
                <el-select v-model="form.category_name"
                          class="customSelectStyle"
                          :popper-append-to-body="false"
                          style="width:100%;"
                          clearable
                          @change="changeSlider"
                          placeholder="请选择物料类别">
                  <el-option
                            v-for="item in options"
                            :key="item.id"
                            :label="item.category_name"
                            :value="item.category_name">
                  </el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="物料名称"
                    prop="materials_name">
                <el-select v-model="form.materials_name"
                          class="customSelectStyle"
                          :popper-append-to-body="false"
                          style="width:100%;"
                          clearable
                          filterable
                          placeholder="请选择物料名称"
                          @change="select">
                  <el-option v-for="item in pp"
                            :key="item.id"
                            :label="item.name"
                            :value="item.name">
                  </el-option>
                </el-select>
              </el-form-item>

               <el-form-item prop="inventory"
                        label="库存量">
                  <el-input v-model="form.inventory"
                              @input="changeInput"
                              placeholder="输入内容">
                      <i slot="suffix">kg</i>
                  </el-input>
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
import { listMaterialsCategoryBox } from "@/api/material/mr/parameter/materialType";

import { listMaterialsBoxJ } from "@/api/material/mr/parameter/materialCode"
import { saveMaterials } from "@/api/material/mr/cokeInventory/index";
export default {
  data () {
    return {
      materialsOptions: [],
      form: {
                category_id: '',
                materials_name: '',
                remark: '',
                inventory: '',
                storage_spaces_name: '',
                category_name: '',
                materials_id: '',
                storageSpacesId: '',
                storage_spaces_id: ''


            },
      // 表单校验
      rules: {
        materials_name: [{  required: true,message: '物料名称不能为空', trigger: 'change' }],
        inventory: [{ required: true, message: '库存量不能为空', trigger: 'change' }],
        category_name:  [{ required: true, message: '物料类别名称不能为空', trigger: 'change' }],
      },
      options: [],
      categoryOptions: [],
      submitLoading: false,
      pp: [],
      dd: []
    }
  },
  created () {
    this.onload()

  },
  computed: {
  },
  methods: {
    onload(){
          listMaterialsCategoryBox().then(res => {
                  // console.log(res)
                  this.options = []
                  let data = res
                  data.map(i => {
                      if (i.mtrlType == '01') {
                          this.options.push(i)
                      }
                  })
              }, error => {
                  this.table.loading = false;
                  window.console.log(error);
              });

          listMaterialsBoxJ().then((res) => {
              let options = []
              res.forEach(item => {
                  let i = {
                    id: item.materials_code,
                    name: item.materials_name,
                    category_id: item.category_id
                    }
                  options.push(i)
              });
              this.materialsOptions = options
              console.log(this.materialsOptions);
          })
    },
  cancel () {
    this.$emit('close')
  },
  changeSlider(e) {
    // console.log(e);
            let obj = {};
            let setBuilding = {};
            obj = this.options.find((item) => {
                if (item.category_name === e) {
                    this.dd = item.id;
                }
            })
            // console.log(this.dd)
            this.pp = []
            for (var j = 0; j < this.materialsOptions.length; j++) {
                if (this.dd == this.materialsOptions[j].category_id) {
                    this.pp.push(this.materialsOptions[j])
                }
            }
            // console.log(this.form.storage_spaces_name)
  },
  changeInput() {
          this.form.inventory = this.form.inventory.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
          this.form.inventory = this.form.inventory.replace(/^\./g, ""); //验证第一个字符是数字而不是.
          this.form.inventory = this.form.inventory.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的.
          this.form.inventory = this.form.inventory.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");//只允许输入一个小数点
          this.form.inventory = this.form.inventory.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
      },
      select(val) {
            this.pp.find((item) => {
                // console.log(item)
                if (item.name === val) {
                    this.form.materials_id = item.id;


                }
            })
        },
  submitForm (formName) {
    this.$refs[formName].validate((valid) => {
      if (valid) {
        this.form.create_user_id = '123456'
        this.form.create_user_name = '姓名';
        this.submitLoading = true
        // this.$emit('submitSave')
        saveMaterials(this.form).then(res => {
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
  }

}
</script>
  <style scoped lang="scss">
    .el-card__body[data-v-1b2f366b] {
    padding: 0;
}
  </style>
