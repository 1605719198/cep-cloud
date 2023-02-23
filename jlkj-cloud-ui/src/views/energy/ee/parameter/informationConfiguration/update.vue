<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form" :model="form" :rules="rules" label-width="135px">
        <el-row>
          <el-col :span="12">
          <el-form-item prop="cost_center_no" label="成本中心">
            <el-select v-model="form.cost_center_no"
                       style="width:100%;"
                       placeholder="选择成本中心"
                       clearable>
              <el-option v-for="item in gradeOptions" @click.native="changeName(item.name)" :key="item.id"
                :label="item.name" :value="item.no">
              </el-option>
            </el-select>
          </el-form-item>
          </el-col>
          <el-col :span="12">
          <el-form-item prop="charge_cost_center_no" label="卖方成本中心">
            <el-select v-model="form.charge_cost_center_no"
                       style="width:100%;"
                       placeholder="选择成本中心"
                       clearable>
              <el-option v-for="item in gradeOptions" @click.native="changeName2(item.name)" :key="item.id"
                         :label="item.name" :value="item.no">
              </el-option>
            </el-select>
          </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
          <el-form-item prop="in_out_type_id" label="耗用产出类别">
            <el-select v-model="form.in_out_type_id"
                       placeholder="选择耗用产出类别"
                       style="width:100%;"
                       clearable>
              <el-option v-for="dict in dict.type.energy_in_out_type"
                         :key="dict.value"
                         :label="dict.label"
                         :value="dict.value" />
            </el-select>
          </el-form-item>
          </el-col>
          <el-col :span="12">
          <el-form-item prop="engy_id" label="能源代码">
            <el-select v-model="form.engy_id"
                       clearable
                       :popper-append-to-body="false"
                       placeholder="请选择能源代码"
                       style="width:100%;">
              <el-option v-for="(item,index) in optionsEngyIdStart"
                         :key="index"
                         :label="item.EngyName"
                         :value="item.EngyId">
              </el-option>
            </el-select>
          </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
          <el-form-item label="上限" prop="up_limit">
            <el-input v-model="form.up_limit" />
          </el-form-item>
          </el-col>
          <el-col :span="12">
          <el-form-item label="下限" prop="down_limit">
            <el-input v-model="form.down_limit" />
          </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="是否超百分比提醒"
                      prop="is_percent_tips">
          <el-radio-group v-model="form.is_percent_tips" @input="selectCategory">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-row>
          <el-col :span="12">
          <el-form-item label="上限超差比率" :rules="[{required:notHave,message:'请输入数据项！',trigger:'change'},{ validator: checkB, trigger: 'change' }]" prop="up_limit_percent" class="customSelectStyle">
            <el-input v-model="form.up_limit_percent" >
              <i slot="suffix">%</i>
            </el-input>
          </el-form-item>
          </el-col>
          <el-col :span="12">
          <el-form-item label="下限超差比率" :rules="[{required:notHave,message:'请输入数据项！',trigger:'change'},{ validator: checkB, trigger: 'change' }]" prop="down_limit_percent" class="customSelectStyle">
            <el-input v-model="form.down_limit_percent" >
              <i slot="suffix">%</i>
            </el-input>
          </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
          <el-form-item prop="tips_color" label="警示色">
            <el-select v-model="form.tips_color"
                       placeholder="选择警示色"
                       style="width:100%;"
                       clearable>
              <el-option v-for="dict in dict.type.tips_color"
                         :key="dict.value"
                         :label="dict.label"
                         :value="dict.value" />
            </el-select>
          </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;margin-bottom: 25px;">
      <el-button @click="cancel">取消</el-button>
      <el-button :loading="submitLoading" type="primary" @click="submitForm('form')">确定</el-button>
    </div>
  </div>
</template>

<script>
import {
  insertEnergyInformationConfiguration,
  updateEnergyInformationConfiguration,
  queryEngyIds
} from "@/api/energy/ee/parameter";
import { mapGetters } from "vuex";
import {listFinancetest} from "@/api/energy/ee/parameter";

export default {
  dicts: ["energy_in_out_type", "tips_color"],
  props: {
    type: {
      type: String
    },
    data: {
      type: Object
    }
  },
  data() {
    //限制长度
    var checkA = (rule, value, callback) => {
      if (value != null) {
        if (isNaN(value)) {
          callback(new Error('请输入数字值'));
        }else {
          if (value > 999999999999999.99999) {
            callback(new Error('整数参数长度为 15，请重新输入！'));
          } else {
            if(typeof (value.toString().split(".")[1]) != 'undefined'){
              if(value.toString().split(".")[1].length>5){
                callback(new Error('小数参数长度为 5，请重新输入！'));
              }else {
                callback();
              }
            }
            callback();//无小数返回校验
          }
        }
      } else {
        callback(new Error('请输入数据项！'));
      }
    };
    return {
      notHave: false,
      form: {},
      // 表单校验
      rules: {
        tips_color: [{ required: true, message: '警示色不能为空', trigger: 'change' }],
        is_percent_tips: [{ required: true, message: '超百分比提醒不能为空', trigger: 'change' }],
        cost_center_no: [{ required: true, message: '成本中心不能为空', trigger: 'change' }],
        charge_cost_center_no: [{ required: true, message: '卖方成本中心不能为空', trigger: 'change' }],
        engy_id: [{ required: true, message: '能源代码不能为空', trigger: 'change' }],
        in_out_type_id: [{ required: true, message: '耗用产出类别不能为空', trigger: 'change' }],
        up_limit: [{ required: true, validator: checkA, trigger: 'change' }],
        down_limit: [{ required: true, validator: checkA, trigger: 'change' }],
      },
      gradeOptions: [],
      optionsEngyIdStart: [],
      submitLoading: false
    }
  },
  created() {
    if (this.type == 'edit') {
      if(this.data.is_percent_tips==0){
        this.notHave = false
      } else {
        this.notHave = true
      }
    }
    queryEngyIds().then(response => {
      this.optionsEngyIdStart = response.data.data;
      // console.log(this.optionsEngyIdStart);
      this.loading = false
    })
    this.getMaterialsOptions()
    this.form = { ...this.data }
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    checkB: (rule, value, callback) => {
      if (value != null) {
        if (isNaN(value)) {
          callback(new Error('请输入数字值'));
        }else {
          if (value > 99999.99999) {
            callback(new Error('整数参数长度为 5，请重新输入！'));
          } else {
            if(typeof (value.toString().split(".")[1]) != 'undefined'){
              if(value.toString().split(".")[1].length>5){
                callback(new Error('小数参数长度为 5，请重新输入！'));
              }else {
                callback();
              }
            }
            callback();//无小数返回校验
          }
        }
      } else {
        callback();
      }
    },
    // 发生改变时触发
    selectCategory (id) {
      if (id == 1) {
        this.notHave = true
      } else {
        this.notHave = false
      }
    },
    //获取成本中心
    getMaterialsOptions() {
      const param = {}
      listFinancetest(param).then((res) => {
        // console.log(res)
        let options = []
        res.data.data.forEach(item => {
          let i = {
            id: item.id,
            no: item.costCenter,
            name: item.costCenterDesc
          }
          options.push(i)
        });
        this.gradeOptions = options
      })
    },
    //获取下拉name
    changeName(name) {
      this.form.cost_center_name = name
    },
    changeName2(name) {
      this.form.charge_cost_center_name = name
    },
    //限制方法
    // changeInput(name) {
    //   if (name == 'single_consumption') {
    //     this.form[name] = this.form[name].replace(/^\D*(\d*(?:\.\d{0,2})?).*$/g, '$1')
    //   } else {
    //     //限制正整数
    //     this.form[name] = this.form[name].replace(/[^\d]/g, "")
    //   }
    // },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            // this.form.create_user = this.userInfo.userId;
            this.form.create_user_name = this.userInfo.userName;
            this.submitLoading = true
            insertEnergyInformationConfiguration(this.form).then(res => {
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
            // this.form.update_user = this.userInfo.userId;
            this.form.update_user_name = this.userInfo.userName;
            this.submitLoading = true
            updateEnergyInformationConfiguration(this.form).then(res => {
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
    cancel() {
      this.$emit('close')
    }
  }
}
</script>

<style>

</style>
