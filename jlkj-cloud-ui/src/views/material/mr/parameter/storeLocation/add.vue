<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item prop="silo_id" label="料仓名称">
          <el-select style="width: 100%;" v-model="form.silo_id" clearable placeholder="选择料仓">
            <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="储位名称" prop="materials_small_code">
          <el-select v-model="form.materials_small_code" style="width:100%;" clearable placeholder="请选择储位名称">
            <el-option v-for="item in selectSmallCoalType" :key="item.id" :label="item.materials_small_name"
                       :value="item.materials_small_code"
                       @click.native="changeSmallCoalType(item)"/>
          </el-select>
        </el-form-item>
        <el-form-item label="煤排" prop="coal_sort">
          <el-select v-model="form.coal_sort" style="width:100%;" placeholder="请选择煤排">
            <el-option label="1" :value="1"/>
            <el-option label="2" :value="2"/>
            <el-option label="3" :value="3"/>
            <el-option label="4" :value="4"/>
          </el-select>
        </el-form-item>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;">
      <el-button @click="cancel">取消</el-button>
      <el-button :loading="submitLoading" type="primary" @click="submitForm('form')">确定</el-button>
    </div>
  </div>
</template>

<script>
import {saveStorageSpaces} from "@/api/material/mr/parameter/storeLocation";
import {listSilosBox} from "@/api/material/mr/parameter/stockMaintain";
import {listMaterialsBoxM, getMaterialsCodeSmallDic} from "@/api/material/mr/parameter/materialCode";
import {mapGetters} from "vuex";

export default {
  data() {
    return {
      form: {silo_id: '', storage_spaces_name: '', material_category_id: '', coal_sort: ''},
      // 表单校验
      rules: {
        silo_id: [{required: true, message: '料仓名称不能为空', trigger: 'change'}],
        // material_category_id: [{required: true, message: '储位名称不能为空', trigger: 'change'}],
        materials_small_code: [{required: true, message: '储位名称不能为空', trigger: 'change'}],
        coal_sort: [{required: true, message: '煤排不能为空', trigger: 'change'}],
      },
      options: [],
      categoryOptions: [],
      selectSmallCoalType: [],
      submitLoading: false
    }
  },
  created() {
    this.getStorageOptions();
    this.getCategoryOptions();
  },
  computed: {
  },
  methods: {
    // 获取料仓列表
    getStorageOptions() {
      const param = {};
      listSilosBox(param).then((res) => {
        res.forEach(item => {
          let i = {id: item.id, name: item.silo_name}
          this.options.push(i)
        });
      })
    },
    // 获取物料类别下拉列表
    getCategoryOptions() {
      getMaterialsCodeSmallDic().then(res => {
        // console.log(res);
        this.selectSmallCoalType = res.data;//表格数据
      }, error => {
        window.console.log(error);
      });
    },
    //物料类别下拉的点击事件
    categoryChange(obj) {
      // console.log(obj)
      this.form.storage_spaces_name = obj.name
    },
    changeSmallCoalType(item) {
      this.form.material_category_id = item.materials_code;
      this.form.storage_spaces_name = item.materials_small_name;
      this.form.materials_small_code = item.materials_small_code;
      this.form.materials_small_name = item.materials_small_name;
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form.create_user_id = this.$store.getters.userInfo.userId;
          this.form.create_user_name = this.$store.getters.userInfo.nickName;
          this.submitLoading = true
          // this.$emit('submitSave')
          saveStorageSpaces(this.form).then(res => {
            if (res.data.code === "0") {
              this.$message({type: "success", message: res.data.msg});
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
    cancel() {
      this.$emit('close')
    }
  }
}
</script>

<style>
</style>
