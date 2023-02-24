<template>
  <div class="avue-crud">
    <el-form ref="addForm" :model="addForm" status-icon :rules="rules" label-width="180px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="直读仪设备代码1" prop="equipmenteNumberA">
            <el-select v-model="addForm.equipmenteNumberA" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsEquipmenteNumberA" :key="item" :label="item" :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="X荧光设备代码1" prop="equipmentxNumberA">
            <el-select v-model="addForm.equipmentxNumberA" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsEquipmentxNumberA" :key="item" :label="item" :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="直读仪设备代码2" prop="equipmenteNumberB">
            <el-select v-model="addForm.equipmenteNumberB" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsEquipmenteNumberB" :key="item" :label="item" :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="X荧光设备代码2" prop="equipmentxNumberB">
            <el-select v-model="addForm.equipmentxNumberB" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsEquipmentxNumberB" :key="item" :label="item" :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="直读仪设备代码3" prop="equipmenteNumberC">
            <el-select v-model="addForm.equipmenteNumberC" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsEquipmenteNumberC" :key="item" :label="item" :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="X荧光设备代码3" prop="equipmentxNumberC">
            <el-select v-model="addForm.equipmentxNumberC" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsEquipmentxNumberC" :key="item" :label="item" :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="直读仪设备代码4" prop="equipmenteNumberD">
            <el-select v-model="addForm.equipmenteNumberD" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsEquipmenteNumberD" :key="item" :label="item" :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="X荧光设备代码4" prop="equipmentxNumberD">
            <el-select v-model="addForm.equipmentxNumberD" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsEquipmentxNumberD" :key="item" :label="item" :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="直读仪设备代码5" prop="equipmenteNumberE">
            <el-select v-model="addForm.equipmenteNumberE" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsEquipmenteNumberE" :key="item" :label="item" :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="X荧光设备代码5" prop="equipmentxNumberE">
            <el-select v-model="addForm.equipmentxNumberE" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsEquipmentxNumberE" :key="item" :label="item" :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="直读仪设备代码6" prop="equipmenteNumberF">
            <el-select v-model="addForm.equipmenteNumberF" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsEquipmenteNumberF" :key="item" :label="item" :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="X荧光设备代码6" prop="equipmentxNumberF">
            <el-select v-model="addForm.equipmentxNumberF" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsEquipmentxNumberF" :key="item" :label="item" :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { queryDropDownMenu } from "@/api/quality/ts/sampleData";

export default {
  name: "sampleDataZD",
  props: {
    formDataZD: Object
  },
  data(){
    return {
      addForm: {
        equipmenteNumberA: undefined,
        equipmenteNumberB: undefined,
        equipmenteNumberC: undefined,
        equipmenteNumberD: undefined,
        equipmenteNumberE: undefined,
        equipmenteNumberF: undefined,
        equipmentxNumberA: undefined,
        equipmentxNumberB: undefined,
        equipmentxNumberC: undefined,
        equipmentxNumberD: undefined,
        equipmentxNumberE: undefined,
        equipmentxNumberF: undefined
      },
      rules: {},
      optionsEquipmenteNumberA: [],
      optionsEquipmenteNumberB: [],
      optionsEquipmenteNumberC: [],
      optionsEquipmenteNumberD: [],
      optionsEquipmenteNumberE: [],
      optionsEquipmenteNumberF: [],
      optionsEquipmentxNumberA: [],
      optionsEquipmentxNumberB: [],
      optionsEquipmentxNumberC: [],
      optionsEquipmentxNumberD: [],
      optionsEquipmentxNumberE: [],
      optionsEquipmentxNumberF: [],

    }
  },
  watch: {
    formDataZD: {
      immediate: true,
      handler (val) {
        this.addForm = val
      }
    }
  },
  created() {
    //编辑查询
    queryDropDownMenu().then(response => {
      this.optionsEquipmenteNumberA = response.data.data.listA
      this.optionsEquipmenteNumberB = response.data.data.listA
      this.optionsEquipmenteNumberC = response.data.data.listA
      this.optionsEquipmenteNumberD = response.data.data.listA
      this.optionsEquipmenteNumberE = response.data.data.listA
      this.optionsEquipmenteNumberF = response.data.data.listA
      this.optionsEquipmentxNumberA = response.data.data.listB
      this.optionsEquipmentxNumberB = response.data.data.listB
      this.optionsEquipmentxNumberC = response.data.data.listB
      this.optionsEquipmentxNumberD = response.data.data.listB
      this.optionsEquipmentxNumberE = response.data.data.listB
      this.optionsEquipmentxNumberF = response.data.data.listB
    })
  },
  mounted() {
    this.$emit('update:formDataZD', this.addForm)
  }
}
</script>

<style scoped>
/*下拉框最后一个显示不完全*/
/deep/ .el-select-dropdown__wrap.el-scrollbar__wrap {
  margin-bottom: 0 !important;
}
/deep/ .el-form-item__content {
  width: 250px;
}
</style>
