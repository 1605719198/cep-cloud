<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="150px">
        <el-form-item prop="start_time"
                      label="实际配煤开始时间">
          <el-date-picker type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择日期"
                          v-model="form.start_time"
                          @change="changeDate"
                          style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item prop="end_time"
                      label="实际配煤结束时间">
          <el-date-picker type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择日期"
                          v-model="form.end_time"
                          @change="changeDate"
                          style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item prop="duration"
                      label="配煤时长">
          <el-input disabled
                    v-model="form.duration"
                    placeholder="">
            <i slot="suffix">min</i>
          </el-input>
        </el-form-item>
        <el-form-item prop="materials_code"
                      label="煤种">
          <el-select style="width:100%;"
                     :popper-append-to-body="false"
                     class="customSelectStyle"
                     v-model="form.materials_code"
                     clearable
                     filterable
                     placeholder="选择煤种">
            <el-option v-for="item in gradeOptions"
                       @click.native="changeName(item.name)"
                       :key="item.id"
                       :label="item.name"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="bunker_number"
                      label="仓号">
          <el-select style="width:100%;"
                     :popper-append-to-body="false"
                     class="customSelectStyle"
                     v-model="form.bunker_number"
                     clearable
                     filterable
                     placeholder="选择仓号">
            <el-option v-for="item in bunkerNumberOptions"
                       @click.native="changeName(item.name)"
                       :key="item.id"
                       :label="item.name"
                       :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="material_weight"
                      label="实际重量">
          <el-input v-model="form.material_weight"
                    @input="changeInput('material_weight')"
                    placeholder="输入内容">
            <i slot="suffix">t</i>
          </el-input>
        </el-form-item>
        <el-form-item prop="before_material_level"
                      label="配煤前料位">
          <el-input v-model="form.before_material_level"
                    placeholder="输入内容">
            <!-- <i slot="suffix">t</i> -->
          </el-input>
        </el-form-item>
        <el-form-item prop="after_material_level"
                      label="配煤后料位">
          <el-input v-model="form.after_material_level"
                    placeholder="输入内容">
            <!-- <i slot="suffix">t</i> -->
          </el-input>
        </el-form-item>
        <el-form-item prop="before_stock"
                      label="配煤前存量">
          <el-input v-model="form.before_stock"
                    @input="changeInput('before_stock')"
                    placeholder="输入内容">
            <!-- <i slot="suffix">t</i> -->
          </el-input>
        </el-form-item>
        <el-form-item prop="after_stock"
                      label="配煤后存量">
          <el-input v-model="form.after_stock"
                    @input="changeInput('after_stock')"
                    placeholder="输入内容">
            <!-- <i slot="suffix">t</i> -->
          </el-input>
        </el-form-item>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;margin-bottom: 25px;">
      <el-button @click="cancel">取消</el-button>
      <el-button :loading="submitLoading"
                 type="primary"
                 @click="submitForm('form')">确定</el-button>
    </div>
  </div>
</template>

<script>
import { listMaterialsBoxM } from "@/api/material/mr/parameter/materialCode"
// import { insertProductionCoalConsume, updateProductionCoalConsume } from "@/api/production/parameter";
import { mapGetters } from "vuex";
export default {
  props: {
    type: {
      type: String
    },
    data: {
      type: Object
    }
  },
  data () {
    let validateStartTime = (rule, value, callback) => {
      if (value) {
        if (this.form.end_time) {
          let times = this.compareDate(value, this.form.end_time);
          if (times > 0) {
            callback();
          } else if (times === 0) {
            callback(new Error("实际配煤开始时间和实际配煤结束时间不能相同"));
          } else {
            callback(new Error("实际配煤开始时间不能在实际配煤结束时间之后，请重新选择"));
          }
        }
      } else {
        callback(new Error('实际配煤开始时间不能为空'));
      }
    };
    var validateEndTime = (rule, value, callback) => {
      if (value) {
        if (this.form.start_time) {
          let times = this.compareDate(this.form.start_time, value);
          if (times > 0) {
            callback();
          } else if (times === 0) {
            callback(new Error("实际配煤开始时间和实际配煤结束时间不能相同"));
          } else {
            callback(new Error("实际配煤结束时间不能在实际配煤开始时间之前，请重新选择"));
          }
        }
      } else {
        callback(new Error('实际配煤结束时间不能为空'));
      }
    }
    return {
      form: {
        duration: null
      },
      // 表单校验
      rules: {
        // start_time: [{ required: true, message: '实际配煤开始时间不能为空', trigger: 'change' }],
        // end_time: [{ required: true, message: '实际配煤结束时间不能为空', trigger: 'change' }],
        start_time: [{ required: true, validator: validateStartTime, trigger: 'change' }],
        end_time: [{ required: true, validator: validateEndTime, trigger: 'change' }],
        materials_code: [{ required: true, message: '煤种不能为空', trigger: 'change' }],
        bunker_number: [{ required: true, message: '仓号不能为空', trigger: 'change' }],
        material_weight: [{ required: true, message: '实际重量不能为空', trigger: 'blur' }],
        before_material_level: [{ required: true, message: '配煤前料位不能为空', trigger: 'blur' }],
        after_material_level: [{ required: true, message: '配煤后料位不能为空', trigger: 'blur' }],
        before_stock: [{ required: true, message: '配煤前存量不能为空', trigger: 'blur' }],
        after_stock: [{ required: true, message: '配煤后存量不能为空', trigger: 'blur' }],
      },
      gradeOptions: [],
      bunkerNumberOptions: [
        { id: 1, name: '1号仓' },
        { id: 2, name: '2号仓' },
        { id: 3, name: '3号仓' },
        { id: 4, name: '4号仓' },
        { id: 5, name: '5号仓' },
        { id: 6, name: '6号仓' },
        { id: 7, name: '7号仓' },
        { id: 8, name: '8号仓' },
        { id: 9, name: '9号仓' },
        { id: 10, name: '10号仓' },
      ],
      submitLoading: false
    }
  },
  created () {
    this.getMaterialsOptions()
    // this.form = { ...this.data }
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    //获取煤种
    getMaterialsOptions () {
      const param = {}
      listMaterialsBoxM(param).then((res) => {
        // console.log(res)
        let options = []
        res.data.forEach(item => {
          let i = {
            id: item.id,
            name: item.materials_name
          }
          options.push(i)
        });
        this.gradeOptions = options
      })
    },
    // 比较两个日期
    compareDate (start, end) {
      return new Date(end).getTime() - new Date(start).getTime();
    },
    //计算配煤时长
    changeDate () {
      // console.log(this.form.start_time, this.form.end_time)
      if (this.form.start_time && this.form.end_time) {
        let time = (new Date(this.form.end_time).getTime() - new Date(this.form.start_time).getTime()) / 1000 / 60;
        // console.log(time)
        this.form.duration = time
      }
    },
    //获取下拉name
    changeName (name) {
      this.form.materials_name = name
    },
    // //限制正整数
    // changeInput (name) {
    //   this.form[name] = this.form[name].replace(/[^\d]/g, "")
    // },
    //限制只有两位小数的正整数
    changeInput (name) {
      this.form[name] = this.form[name].replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1')
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            this.form.create_user_id = this.userInfo.userId;
            this.form.create_user_name = this.userInfo.userName;
            this.submitLoading = true
            insertProductionCoalConsume(this.form).then(res => {
              if (res.data.code === "0") {
                this.$message({ type: "success", message: res.data.msg });
                this.$emit('submitSave', res.data.msg)
              }
              this.submitLoading = false
            }, error => {
              this.submitLoading = false
              window.console.log(error);
            });
          } else {
            this.form.modify_user_id = this.userInfo.userId;
            this.form.modify_user_name = this.userInfo.userName;
            // this.$emit('submitSave')
            this.submitLoading = true
            updateProductionCoalConsume(this.form).then(res => {
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
