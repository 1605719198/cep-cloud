<template>
  <div class="avue-crud">
    <el-form ref="addForm" :model="addForm" status-icon :rules="rules" label-width="166px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="红外碳硫仪结果发送状态" prop="csResultCheck">
            <el-select v-model="addForm.csResultCheck" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsCsResultCheck" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="氮氧分析仪结果发送状态" prop="noResultCheck">
            <el-select v-model="addForm.noResultCheck" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsNoResultCheck" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="红外碳硫仪编号" prop="csEquipmentNumber">
            <el-select v-model="addForm.csEquipmentNumber" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsCsEquipmentNumber" :key="item" :label="item" :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="氮氧分析仪编号" prop="noEquipmentNumber">
            <el-select v-model="addForm.noEquipmentNumber" :popper-append-to-body="false" placeholder="请选择">
              <el-option v-for="item in optionsNoEquipmentNumber" :key="item" :label="item" :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="元素代号  -C-:" prop="cElementIndex" label-width="100px">
            <el-input v-model="addForm.cElementIndex" style="width: 50%" :disabled="true"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="-S-:" prop="sElementIndex" label-width="50px">
            <el-input v-model="addForm.sElementIndex" style="width: 50%" :disabled="true"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="元素代号  -N-:" prop="nElementIndex" label-width="100px">
            <el-input v-model="addForm.nElementIndex" style="width: 50%" :disabled="true"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="-O-:" prop="oElementIndex" label-width="50px">
            <el-input v-model="addForm.oElementIndex" style="width: 50%" :disabled="true"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="MIN" prop="cequipmentMin" label-width="100px">
            <el-input v-model="addForm.cequipmentMin" onkeyup="value=value.replace(/[^0-9.]/g,'')" style="width: 50%">
              <i slot="suffix">%</i>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="" prop="sequipmentMin" label-width="50px">
            <el-input v-model="addForm.sequipmentMin" onkeyup="value=value.replace(/[^0-9.]/g,'')" style="width: 50%" >
              <i slot="suffix">%</i>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="MIN" prop="nequipmentMin" label-width="100px">
            <el-input v-model="addForm.nequipmentMin" onkeyup="value=value.replace(/[^0-9.]/g,'')" style="width: 50%" >
              <i slot="suffix">%</i>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="" prop="oequipmentMin" label-width="50px">
            <el-input v-model="addForm.oequipmentMin" onkeyup="value=value.replace(/[^0-9.]/g,'')" style="width: 50%">
              <i slot="suffix">%</i>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="MAX" prop="cequipmentMax" label-width="100px">
            <el-input v-model="addForm.cequipmentMax" onkeyup="value=value.replace(/[^0-9.]/g,'')" style="width: 50%">
              <i slot="suffix">%</i>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="" prop="sequipmentMax" label-width="50px">
            <el-input v-model="addForm.sequipmentMax" onkeyup="value=value.replace(/[^0-9.]/g,'')" style="width: 50%" >
              <i slot="suffix">%</i>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="MAX" prop="nequipmentMax" label-width="100px">
            <el-input v-model="addForm.nequipmentMax" onkeyup="value=value.replace(/[^0-9.]/g,'')" style="width: 50%" >
              <i slot="suffix">%</i>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="" prop="oequipmentMax" label-width="50px">
            <el-input v-model="addForm.oequipmentMax" onkeyup="value=value.replace(/[^0-9.]/g,'')" style="width: 50%" >
              <i slot="suffix">%</i>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

  </div>
</template>

<script>
import { queryDropDownMenu } from "@/api/quality/ts/sampleData";

export default {
  name: "sampleDataTL",
  props: {
    formDataTL: Object
  },
  data(){
    return {
      addForm: {
        csResultCheck: undefined,
        noResultCheck: undefined,
        csEquipmentNumber: undefined,
        noEquipmentNumber: undefined,
        cElementIndex: undefined,
        sElementIndex: undefined,
        nElementIndex: undefined,
        oElementIndex: undefined,
        cequipmentMin: undefined,
        sequipmentMin: undefined,
        nequipmentMin: undefined,
        oequipmentMin: undefined,
        cequipmentMax: undefined,
        sequipmentMax: undefined,
        nequipmentMax: undefined,
        oequipmentMax: undefined,
      },
      rules: {},
      optionsCsResultCheck: [{
        value: 'W',
        label: 'W-警告'
      }, {
        value: 'E',
        label: 'E-错误'
      }, {
        value: 'C',
        label: 'C-确认'
      }],
      optionsNoResultCheck: [{
        value: 'W',
        label: 'W-警告'
      }, {
        value: 'E',
        label: 'E-错误'
      }, {
        value: 'C',
        label: 'C-确认'
      }],
      optionsCsEquipmentNumber: [],
      optionsNoEquipmentNumber: []
    }
  },
  watch: {
    formDataTL: {
      immediate: true,
      handler (val) {
        this.addForm = val
      }
    }
  },
  created() {
    //编辑查询
    queryDropDownMenu().then(response => {
      this.optionsCsEquipmentNumber = response.data.data.listC
      this.optionsNoEquipmentNumber = response.data.data.listD
    })
    // this.addForm.csResultCheck = 'W'
    this.addForm.cElementIndex = 'A001'
    // this.addForm.noResultCheck = 'W'
    this.addForm.sElementIndex = 'A005'
    this.addForm.nElementIndex = 'A023'
    this.addForm.oElementIndex = 'A022'
    // this.addForm = {...this.addForm}
  },
  mounted() {
    this.$emit('update:formDataTL', this.addForm)
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
/deep/ .el-form-item__label {
  text-align: left;
}
</style>
