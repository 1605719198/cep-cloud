<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="80px">
        <el-form-item prop="silo_id"
                      label="料仓名称">
          <el-select style="width: 100%;"
                     :popper-append-to-body="false"
                     v-model="form.silo_id"
                     clearable
                     placeholder="选择料仓">
            <el-option v-for="item in options"
                       :key="item.id"
                       :label="item.name"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item prop="storage_spaces_name"
                      label="储位名称">
          <el-input v-model="form.storage_spaces_name"
                    placeholder="输入内容"></el-input>
        </el-form-item> -->
        <el-form-item label="储位名称"
                      prop="material_category_id">
          <el-select v-model="form.material_category_id"
                     class="customSelectStyle"
                     :popper-append-to-body="false"
                     style="width:100%;"
                     clearable
                     placeholder="请选择储位名称">
            <el-option @click.native="categoryChange(item)"
                       v-for="item in categoryOptions"
                       :key="item.id"
                       :label="item.name"
                       :value="item.id">
            </el-option>
          </el-select>
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
import { updateStorageSpaces } from "@/api/material/mr/parameter/storeLocation";
import { listSilosBox } from "@/api/material/mr/parameter/stockMaintain";
import { listMaterialsBoxM } from "@/api/material/mr/parameter/materialCode";
import { mapGetters } from "vuex";
export default {
  props: {
    data: {
      type: Object
    }
  },
  data () {
    return {
      form: {
        silo_id: '',
        storage_spaces_name: '',
        material_category_id: ''
      },
      // 表单校验
      rules: {
        silo_id: [{ required: true, message: '料仓名称不能为空', trigger: 'change' }],
        material_category_id: [{ required: true, message: '储位名称不能为空', trigger: 'change' }],
      },
      options: [],
      categoryOptions: [],
      submitLoading: false
    }
  },
  created () {
    this.getStorageOptions()
    this.getCategoryOptions()
    this.form = { ...this.data }
  },
  computed: {

  },
  methods: {
    // 获取料仓列表
    getStorageOptions () {
      const param = {}
      listSilosBox(param).then((res) => {
        // console.log(res)
        res.data.forEach(item => {
          let i = {
            id: item.id,
            name: item.silo_name
          }
          this.options.push(i)
        });
      })
    },
    // 获取物料类别下拉列表
    getCategoryOptions () {
      const param = {}
      listMaterialsBoxM(param).then((res) => {
        // console.log(res)
        let options = []
          res.data.forEach(item => {
            let i = {id: item.id, name: item.materials_name};
            options.push(i)
          });
        this.categoryOptions = options
      })
    },
    //物料类别下拉的点击事件
    categoryChange (obj) {
      // console.log(obj)
      this.form.storage_spaces_name = obj.name
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form.modify_user_id = '123456'
          this.form.modify_user_name = '姓名';
          this.submitLoading = true
          // this.$emit('submitSave')
          updateStorageSpaces(this.form).then(res => {
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

<style>
</style>
