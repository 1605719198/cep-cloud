<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="80px">
        <el-form-item prop="unloading_time"
                      label="卸车时间">
          <el-date-picker type="datetime"
                          placeholder="选择时间"
                          v-model="form.unloading_time"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          :picker-options="pickerOptions"
                          style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item prop="storage_maintain_id "
                      label="储位">
          <el-select style="width: 100%;"
                     @change="storageChange"
                     :popper-append-to-body="false"
                     v-model="form.storage_maintain_id"
                     clearable
                     placeholder="选择储位">
            <el-option v-for="item in options"
                       :key="item.id"
                       :label="item.name"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item style="text-align: right;">
          <el-button @click="cancel">取消</el-button>
          <el-button type="primary"
                     @click="submitForm('form')">提交</el-button>
        </el-form-item> -->
      </el-form>
    </div>
    <!-- <div style="  border: 1px solid #ebeef5;border-bottom: unset;margin:10px 0"></div> -->
    <div style="width:100%;text-align: right;">
      <el-button @click="cancel">取消</el-button>
      <el-button :loading="submitLoading"
                 type="primary"
                 @click="submitForm('form')">确定</el-button>
    </div>
  </div>
</template>

<script>
import { updateMaterialUnloadingPerformance } from "@/api/material/mr/unloadCoal/index";
import { listMaterialsStorageSpacesBox } from "@/api/material/mr/parameter/storeLocation"

export default {
  props: {
    data: {
      type: Object
    }
  },
  data () {
    return {
      form: {
        storage_maintain_id: ''
      },
      // 表单校验
      rules: {
        unloading_time: [{ required: true, message: '卸车时间不能为空', trigger: 'change' }],
        storage_maintain_id: [{ required: true, message: '储位名称不能为空', trigger: 'blur' }],
      },
      //下拉
      options: [],
      submitLoading: false,
      pickerOptions: {
        disabledDate (time) {
          let start = new Date();
          start.setDate(start.getDate() - 2);
          let end = new Date();
          end.setDate(end.getDate() + 1);
          return time.getTime() > Date.now() || time.getTime() < start.getTime();
        },
      }
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
      listMaterialsStorageSpacesBox(param).then((res) => {
        // console.log(res)
        res.data.forEach(item => {
          let i = {
            id: item.id,
            name: item.storage_spaces_name
          }
          this.options.push(i)
        });
      })
    },
    //改变储位
    storageChange (val) {
      // console.log(val)
      this.options.forEach((item) => {
        if (item.id == val) {
          this.form.storage_maintain_space_name = item.name
          // console.log(this.form.storage_maintain_space_name)
        }
      })
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // this.form.id = '1'
          this.form.modify_user_id = '123456'
          this.form.modify_user_name = '姓名';
          this.submitLoading = true
          // this.$emit('submitSave')
          updateMaterialUnloadingPerformance(this.form).then(res => {
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
