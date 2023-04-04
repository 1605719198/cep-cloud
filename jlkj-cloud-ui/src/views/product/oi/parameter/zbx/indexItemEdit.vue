<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" :rules="rules" label-position="left" label-width="110px">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="指标项名称" prop="target_item_name">
            <el-input v-model="form.target_item_name" style="width: 217px;" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="指标类型" prop="target_item_type_id">
            <el-select v-model="form.target_item_type_id" placeholder="请选择">
              <el-option v-for="item in itemTypeOptions" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="计算单位名称" prop="calc_unit_id">
            <el-select v-model="form.calc_unit_id" placeholder="请选择">
              <el-option v-for="item in calcUnitOptions" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="计量单位" prop="measuring_unit">
            <el-select v-model="form.measuring_unit" placeholder="请选择">
              <el-option v-for="item in measuringUnitOptions" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="物料代码" prop="material_name">
            <el-select v-model="form.material_name" placeholder="选择状态" clearable @change="selectChangeListener">
              <el-option v-for="item in materialCodeOption" :key="item.id" :label="item.materials_name" :value="item" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <input type="hidden" v-model="form.id" value="0" />
      <div class="el-dialog__footer" style="padding: 0; padding-bottom: 25px;">
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="handleChange('form')" :loading="submitLoading">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </span>
      </div>
    </el-form>

  </div>
</template>
<script>
import { addProductionTargetItem, editProductionTargetItem, materialCodeList } from "@/api/production/oi/parameter";

export default {
  name: "indexItemEdit",
  props: ['data', 'type'],
  data() {
    return {
      itemTypeOptions: [
        { value: 1, label: '焦化指标' },
        { value: 2, label: '发电指标' }
      ],
      calcUnitOptions: [
        { value: 1, label: '百分比' },
        { value: 2, label: '量值' }
      ],
      measuringUnitOptions: [
        { value: '%', label: '%' },
        { value: 't', label: 't' },
        { value: 'kwh', label: 'kwh' },
        { value: 'm3', label: 'm3' },
      ],
      materialCodeOption: [],
      form: {
        id: 0,
        target_item_name: '',
        target_item_type_id: 0,
        target_item_type_name: '',
        calc_unit_id: 0,
        calc_unit_name: '',
        measuring_unit: '',
        create_user_id: 0,
        create_user_name: '',
        modify_user_id: 0,
        modify_user_name: '',
        material_id: '',
        material_name: ''
      },
      rules: {
        target_item_name: [{ required: true, message: '请输入指标项名称', trigger: 'blur' },],
        target_item_type_id: [{ required: true, message: '请选择指标类型', trigger: 'blur' },],
        calc_unit_id: [{ required: true, message: '请选择计算单位名称', trigger: 'blur' },],
        measuring_unit: [{ required: true, message: '请选择计量单位', trigger: 'blur' },],
        material_name: [{ required: true, message: '请输入物料代码', trigger: 'blur' },],
      },
      submitLoading: false,
    }
  },
  computed: {
  },
  created() {
    // console.log(this.type)
    // console.log(this.userInfo)
  },
  mounted() {
    this.form = { ...this.data }
    this.getMaterialCodeList();
  },
  methods: {
    handleChange(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.submitLoading = true;
          if (this.type === 'add') {
            this.form.create_user_id = this.$store.state.user.userInfo.userName;
            this.form.create_user_name = this.$store.state.user.userInfo.nickName;
            addProductionTargetItem(this.form).then(res => {
              if (res.code === 200) {
                this.$message({ type: "success", message: res.msg });
                this.$emit('submitSave', res.msg)
              }
              this.submitLoading = false;
            }, error => {
              this.submitLoading = false;
              window.console.log(error);
            });
          } else if (this.type === 'edit') {
            this.form.modify_user_id = this.$store.state.user.userInfo.userName;
            this.form.modify_user_name = this.$store.state.user.userInfo.nickName;
            editProductionTargetItem(this.form).then(res => {
              if (res.code === 200) {
                this.$message({ type: "success", message: res.msg });
                this.$emit('submitSave', res.msg)
              }
              this.submitLoading = false;
            }, error => {
              this.submitLoading = false;
              window.console.log(error);
            });
          }
        } else {
          return false;
        }
      });
    },
    cancel() {
      this.$emit('close')
    },
    getMaterialCodeList() {
      materialCodeList().then(response => {
        if (response.code === 200) {
          this.materialCodeOption = response.data;
        }
      }, error => {
        window.console.log(error);
      });
    },
    selectChangeListener(val) {
      this.form.material_name = val.materials_name;
      this.form.material_id = val.id;
    }
  }
}
</script>

<style scoped>
.avue-crud {
  padding: 0px;
}
</style>
