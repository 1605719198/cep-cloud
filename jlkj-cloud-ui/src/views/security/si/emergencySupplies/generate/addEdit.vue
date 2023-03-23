<template>
  <div>
    <div style="padding-top:20px;">
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="80px">
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="material_name"
                          label="名称">
              <el-input v-model="form.material_name"
                        placeholder="请输入名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="position"
                          label="工段">
              <el-select style="width: 100%;"
                         class="customSelectStyle"
                         :popper-append-to-body="false"
                         v-model="form.position"
                         clearable
                         placeholder="选择工段">
                <el-option v-for="item in options"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="amount"
                          label="数量">
              <el-input v-model="form.amount"
                        @input="changeInput"
                        placeholder="请输入数量"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="specification"
                          label="型号">
              <el-input v-model="form.specification"
                        placeholder="请输入型号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="type"
                          label="类别">
              <el-select style="width: 100%;"
                         class="customSelectStyle"
                         :popper-append-to-body="false"
                         v-model="form.type"
                         clearable
                         placeholder="选择类别">
                <el-option v-for="item in typeOptions"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="person_liable_name"
                          label="责任人">
              <el-input v-model="form.person_liable_name"
                        :readonly="true"
                        placeholder="请选责任人"
                        @focus="dialogVisible = true"></el-input>
            </el-form-item>
            <personnel-selection v-if="dialogVisible"
                                 :userDialogVisible="dialogVisible"
                                 @close-dialog="dialogVisible = false"
                                 :isMultiple="false"
                                 :height="500"
                                 @single-select="handleSingleSelect"></personnel-selection>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;">
      <el-button @click="cancel">取消</el-button>
      <el-button v-hasPermi="['workshopacc_insert','workshopacc_update']"
                 :loading="submitLoading"
                 type="primary"
                 @click="submitForm('form')">确认</el-button>
    </div>
  </div>
</template>

<script>
import { workshopaccInsert, workshopaccUpdate } from "@/api/security/si/emergencySupplies/workshop";
import { workshopsection } from "@/api/security/si/WorkTicketApi";
import { mapGetters } from "vuex";
export default {
  props: {
    workshop_name: {
      type: String,
      required: true
    },
    type: {
      type: String
    },
    data: {
      type: Object
    }
  },
  data () {
    return {
      dialogVisible: false,
      form: {
        //不传报错
        specification: '',
        person_liable_name: ''
      },
      // 表单校验
      rules: {
        material_name: [{ required: true, message: '名称不能为空', trigger: 'blur' }],
        position: [{ required: true, message: '工段不能为空', trigger: 'change' }],
        amount: [{ required: true, message: '数量不能为空', trigger: 'blur' }],
        type: [{ required: true, message: '类别不能为空', trigger: 'change' }],
        person_liable_name: [{ required: true, message: '责任人不能为空', trigger: 'change' }]
      },
      options: [],
      typeOptions: [
        {
          id: '01',
          name: '消防类'
        },
        {
          id: '02',
          name: '防护类'
        },
        {
          id: '03',
          name: '防汛类'
        },
      ],
      submitLoading: false
    }
  },
  created () {
    // console.log(this.workshop_name)
    this.getWorkshopsectionOptions()
    if (this.type == 'edit') {
      this.form = { ...this.data }
    }
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    //限制只有两位小数的正整数
    changeInput () {
      this.form.amount = this.form.amount.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
      this.form.amount = this.form.amount.replace(/^\./g, ""); //验证第一个字符是数字而不是.
      this.form.amount = this.form.amount.replace(/\.{2,}/g, "."); //只保留第一个. 清除多余的.
      this.form.amount = this.form.amount.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");//只允许输入一个小数点
      this.form.amount = this.form.amount.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
    },
    //获取工段下拉别表
    getWorkshopsectionOptions () {
      const param = {
        // workshop_name: '发电车间'
        workshop_name: this.workshop_name
      }
      workshopsection(param).then((res) => {
        // console.log(res)
        this.options = res.data.data
      })
    },
    //单选回调方法
    handleSingleSelect (val) {
      // console.log(val.my_mobile_phone, val.job_number, val.full_name, val.id)
      this.form.person_liable_id = val.id
      this.form.person_liable_name = val.full_name
      this.form.person_liable_no = val.job_number
      this.form.person_liable_tel = val.my_mobile_phone
    },
    submitForm (formName) {
      // console.log(this.form)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.type == 'add') {
            this.form.create_user = this.userInfo.userName;
            this.form.create_user_id = this.$store.getters.userInfo.userId;
            this.form.create_user_no = this.$store.getters.userInfo.userName;
            // this.form.workshop_name = '发电车间'
            this.form.workshop_name = this.workshop_name
            // this.$emit('submitSave')
            this.submitLoading = true
            workshopaccInsert(this.form).then(res => {
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
            this.form.modify_user = this.userInfo.userName;
            this.form.modify_user_id = this.$store.getters.userInfo.userId;
            this.form.modify_user_no = this.$store.getters.userInfo.userName;
            // this.form.workshop_name = '发电车间'
            this.form.workshop_name = this.workshop_name
            // this.$emit('submitSave')
            this.submitLoading = true
            workshopaccUpdate(this.form).then(res => {
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
