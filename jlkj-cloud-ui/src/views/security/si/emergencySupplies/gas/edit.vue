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
            <el-form-item prop="charge_person_id"
                          label="负责人">
              <select-tree v-model="form.charge_person_id"
                           style="width:100%;"
                           placeholder="请选择"
                           :selectForm="mainForm"
                           :data="userData"
                           :validate="{flag:'user'}"
                           :isSelectNode="false"
                           :isClearable="true"
                           :isAccordion="true"
                           :isNodeChildrenNull="true"
                           :isFilterable="true"
                           @change="getMainUser" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="50">
          <el-col :span="12">
            <el-form-item prop="amount"
                          label="数量">
              <el-input v-model="form.amount"
                        onkeyup="value=value.replace(/[^\d]/g,'')"
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
        <el-form-item label="备注">
          <el-input type="textarea"
                    :autosize="{ minRows: 5, maxRows: 5 }"
                    placeholder="备注"
                    v-model="form.remark">
          </el-input>
        </el-form-item>
      </el-form>
    </div>
    <div style="width:100%;text-align: right;">
      <el-button @click="cancel">取消</el-button>
      <el-button :loading="submitLoading"
                 type="primary"
                 @click="submitForm('form')">确认</el-button>
    </div>
  </div>
</template>

<script>
import { emergencyreliefmaterialsUpdate } from "@/api/security/si/emergencySupplies/gas";
import { getHumanTree } from "@/api/human/hm/humanResourceTree";
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
    return {
      form: {},
      //人员下拉树
      userData: [],
      //选中回显的数据
      mainForm: { value: '', label: '' },
      // 表单校验
      rules: {
        material_name: [{ required: true, message: '名称不能为空', trigger: 'blur' }],
        charge_person_id: [{ required: true, message: '负责人不能为空', trigger: 'blur' }],
        amount: [{ required: true, message: '数量不能为空', trigger: 'blur' }],
      },
      submitLoading: false
    }
  },
  watch: {
    'form.charge_person_id' (newValue) {
      this.mainForm.value = newValue;
    },
  },
  created () {
    this.getUserTree()
    this.form = { ...this.data }
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  methods: {
    //人员下拉树
    getUserTree () {
      getHumanTree().then(res => {
        this.userData = res.data.data;
      }, error => {
        window.console.log(error);
      });
    },
    //赋值
    getMainUser (obj) {
      this.form.charge_person_id = obj.value;
      this.form.charge_person = obj.label;
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.form.modify_user_id = this.$store.getters.userInfo.userId;
          this.form.modify_user_name = this.userInfo.userName;
          // this.$emit('submitSave')
          this.submitLoading = true
          emergencyreliefmaterialsUpdate(this.form).then(res => {
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
