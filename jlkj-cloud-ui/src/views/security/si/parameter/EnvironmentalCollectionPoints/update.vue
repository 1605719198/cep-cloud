<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" :rules="rules" label-position="right" label-width="100px">
      <el-row>
        <el-form-item label="位号" prop="sort">
          <!--          <el-input v-model="form.sort"/>-->
          <el-select v-model="form.sort" clearable filterable :popper-append-to-body="false">
            <el-option v-for="i in 32" :key="i" :label="i" :value="i"/>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="device_name">
          <el-input v-model="form.device_name"/>
        </el-form-item>
        <el-form-item label="污染物名称" prop="pollutant_name">
          <el-input v-model="form.pollutant_name"/>
        </el-form-item>
        <el-form-item label="采集点位" prop="collection_point">
          <el-input v-model="form.collection_point"/>
        </el-form-item>
        <el-form-item label="排放限值" prop="emission_limit">
          <el-input v-model="form.emission_limit"/>
        </el-form-item>
        <el-form-item label="单位"
                      prop="unit">
          <el-select v-model="form.unit"
                     placeholder="请选择"
                     class="customSelectStyle"
                     :popper-append-to-body="false">
            <el-option v-for="item in unit"
                       :key="item.value"
                       :label="item.label"
                       :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <div class="el-dialog__footer" style="padding: 0;  padding-bottom: 25px; ">
          <span slot="footer" class="dialog-footer">
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary" @click="handleChange('form')" :loading="submitLoading">确 定</el-button>
          </span>
        </div>
      </el-row>
    </el-form>

  </div>
</template>

<script>
  import {
    updateEnvironmentalCollectionPoints,
    saveEnvironmentalCollectionPoints
  } from "@/api/security/si/parameter/EnvironmentalCollectionPoints";
  import {mapGetters} from "vuex";

  export default {
    name: "update",
    props: ['data', 'type'],
    data() {
      return {
        form: {
          device_name: '',
          pollutant_name: '',
          collection_point: '',
          emission_limit: '',
          sort: '',
        },
        rules: {
          sort: [{required: true, message: '请输入位号', trigger: 'blur'}],
          device_name: [{required: true, message: '请输入名称', trigger: 'blur'}],
          pollutant_name: [{required: true, message: '请输入污染物名称', trigger: 'blur'}],
          collection_point: [{required: true, message: '请输入采集点位', trigger: 'blur'}],
          emission_limit: [{required: true, message: '请输入排放限值', trigger: 'blur'},
            {type: 'number', message: '请输入数字', trigger: 'blur', transform: (value) => Number(value)}],
          measuring_unit: [{ required: true, message: '请选择计量单位', trigger: 'blur' },],
        },
        submitLoading: false,
        unit: [
        { value: '%', label: '%' },
        { value: 'm³', label: 'm³' },
        { value: 't', label: 't' },
        { value: 'μg/m³', label: 'μg/m³' },
        { value: 'mg/m³', label: 'mg/m³' },
      ],
      }
    },
    computed: {
      ...mapGetters(["userInfo"]),
    },
    created() {
      this.form = {...this.data}
    },
    methods: {
      //限制只有两位小数的正整数
      changeInput(name) {
        this.form[name] = this.form[name].replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1')
      },
      handleChange(form) {
        this.$refs[form].validate((valid) => {
          if (valid) {
            this.submitLoading = true;
            if (this.type === 'add') {
              saveEnvironmentalCollectionPoints(this.form).then(res => {
                if (res.data.code === "0") {
                  this.$message({
                    type: "success", message: res.data.msg, duration: 1000,
                    onClose: () => {
                      this.$emit('submitSave', res.data.msg)
                    }
                  });
                }else {
                  this.$message({ type: "error", message: res.data.msg });
                }
                this.submitLoading = false;
              }, error => {
                this.submitLoading = false;
                window.console.log(error);
              });
            } else if (this.type === 'edit') {
              updateEnvironmentalCollectionPoints(this.form).then(res => {
                if (res.data.code === "0") {
                  this.$message({
                    type: "success", message: res.data.msg, duration: 1000,
                    onClose: () => {
                      this.$emit('submitSave', res.data.msg)
                    }
                  });
                }else {
                  this.$message({ type: "error", message: res.data.msg });
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
      }
    }
  }
</script>

<style scoped>

</style>
