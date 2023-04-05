<template>
  <div style="width: 100%">
    <el-form :model="formData" :rules="rules" ref="formData" label-width="80px" class="demo-ruleForm">
      <el-row>
        <el-col>
          <el-form-item label="类型" prop="materials_code">
            <el-select v-model="formData.materials_code" placeholder="请选择类别" style="width: 100%" @change="materialsSelectListener">
              <el-option v-for="item in categoryOption" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col>
          <el-form-item label="产量(kg)" prop="yield">
            <el-input v-model="formData.yield" placeholder="请输入产量"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col>
          <el-form-item label="时间" prop="time">
            <el-date-picker v-model="formData.time" type="datetime" placeholder="选择日期"
              value-format="yyyy-MM-dd HH:mm:ss" style="width: 100%">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <div style="text-align:right; margin-bottom:25px;">
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="handleChange('formData')" :loading="submitLoading">确 定</el-button>
      </span>
    </div>
  </div>
</template>

<script>

import { listMaterialsBoxJ } from "@/api/material/mr/parameter/materialCode";
import { insertCokeItemYieldList, updateCokeItemYieldList } from "@/api/production/oi/actual/coke/CokeItemYieldApi";

export default {
  name: "CokeItemYieldEditor",
  props: ["dataBundle", "isAdd"],
  data() {
    var checkValue = (rule, value, callback) => {
      const float = /^[+]?(0|([1-9]\d*))(\.\d+)?$/;
      if (!float.test(value)) {
        callback(new Error('产量只能为正数'));
      } else {
        callback();
      }
    }
    return {
      formData: {
        yield: '',
        time: '',
        materials_code: "",
        materials_name: ""
      },
      categoryOption: [],
      submitLoading: false,
      rules: {
        materials_code: [{ required: true, message: '物料类型不能为空', trigger: 'blur' },],
        yield: [{ required: true, message: '产量不能为空', trigger: 'blur' }, { validator: checkValue, trigger: 'blur' }],
        time: [{ required: true, message: '时间不能为空', trigger: 'blur' },]
      },
    }
  },
  methods: {
    materialsSelectListener(material_code){
      this.formData.materials_code = material_code;
      this.categoryOption.forEach(i =>{
        if (i.value === material_code){
          this.formData.materials_name = i.label ;
        }
      });
    },
    handleChange(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          if (this.isAdd) {
            this.handleSave();
          } else {
            this.handleUpdate();
          }
        } else {
          return false;
        }
      });
    },
    cancel() {
      this.$emit("close");
    },

    handleSave() {
      this.submitLoading = true;
      let params = {
        yield: this.formData.yield,
        time: this.formData.time,
        materials_code: this.formData.materials_code,
        materials_name: this.formData.materials_name
      }
      insertCokeItemYieldList(params).then(res => {
        this.submitLoading = false;
        if (res.data.code === "0") {
          this.$message.success("保存成功");
          this.$emit("close");
        } else {
          this.$message.error("保存失败");
        }
      }, err => {
        this.submitLoading = false;
        window.console.log(err);
      });
    },

    handleUpdate() {
      this.submitLoading = true;
      let params = {
        id: this.dataBundle.id,
        yield: this.formData.yield,
        time: this.formData.time,
        materials_code: this.formData.materials_code,
        materials_name: this.formData.materials_name,

        old_yield: this.formData.old_yield,
        old_materials_name: this.formData.old_materials_name,
        old_materials_code: this.formData.old_materials_code,
        old_time: this.formData.old_time
      }
      updateCokeItemYieldList(params).then(res => {
        this.submitLoading = false;
        if (res.data.code === "0") {
          this.$message.success("修改成功");
          this.$emit("close");
        } else {
          this.$message.error("修改失败");
        }
      }, err => {
        this.submitLoading = false;
        window.console.log(err);
      });
    },
    //获取物料代码下拉列表
    getMaterialsOptions() {
      const param = {}
      listMaterialsBoxJ(param).then((res) => {
        console.log(res)
        let options = []
        res.data.forEach(item => {
          if (item.materials_code === "0150201" || item.materials_code === "0150301"){
            let i = {
              value: item.materials_code,
              label: item.materials_name
            }
            options.push(i);
          }
        });
        this.categoryOption = options
      })
    },

    getMaterialName(code) {
      for (let i = 0; i < this.categoryOption.length; i++) {
        let item = this.categoryOption[i];
        if (item.value === code) {
          return item.label;
        }
      }
    }
  },
  mounted() {
    this.getMaterialsOptions();
    if (!this.isAdd) {
      this.formData.time = this.dataBundle.create_time;
      this.formData.materials_code = this.dataBundle.materials_code;
      this.formData.materials_name = this.dataBundle.materials_name ;
      this.formData.yield = this.dataBundle.yield;

      this.formData.old_time = this.dataBundle.create_time;
      this.formData.old_materials_code = this.dataBundle.materials_code;
      this.formData.old_materials_name = this.dataBundle.materials_name ;
      this.formData.old_yield = this.dataBundle.yield;
    }
  }
}
</script>

<style scoped>
.el-col {
  margin-bottom: 0px;
}
</style>
