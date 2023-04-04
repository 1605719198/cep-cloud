<template>
  <div class="avue-crud">
    <el-form :model="form" ref="form" :rules="rules" label-position="left" label-width="100px">
      <el-row>
        <el-form-item label="产量" prop="yield">
          <el-input @input="changeInput('yield')" v-model="form.yield">
            <template slot="append">千克</template>
          </el-input>
        </el-form-item>
      </el-row>
      <input type="hidden" v-model="form.id" value="0" />
      <div class="el-dialog__footer" style="padding: 0;  padding-bottom: 25px; ">
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="handleChange('form')" :loading="submitLoading">确 定</el-button>
        </span>
      </div>
    </el-form>

  </div>
</template>
<script>
import { addProductionSingleHoleYield, editProductionSingleHoleYield } from "@/api/production/oi/parameter";
import { mapGetters } from "vuex";

export default {
  name: "cokeOvenHoleYieldmEdit",
  props: ['data', 'type'],
  data() {
    return {
      form: {
        yield: '',
      },
      rules: {
        yield: [
          { required: true, message: '请输入产量', trigger: 'blur' },
          { type: 'number', message: '请输入数字', trigger: 'blur', transform: (value) => Number(value) },
        ],
      },
      submitLoading: false,
    }
  },
  computed: {
    ...mapGetters(["userInfo"]),
  },
  created() {
  },
  mounted() {
    this.form = { ...this.data }
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
            this.form.create_user_id = this.userInfo.userId;
            this.form.create_user_name = this.userInfo.userName;
            addProductionSingleHoleYield(this.form).then(res => {
              if (res.data.code === "0") {
                this.$message({ type: "success", message: res.data.msg });
                this.$emit('submitSave', res.data.msg)
              }
              this.submitLoading = false;
            }, error => {
              this.submitLoading = false;
              window.console.log(error);
            });
          } else if (this.type === 'edit') {
            this.form.modify_user_id = this.userInfo.userId;
            this.form.modify_user_name = this.userInfo.userName;
            editProductionSingleHoleYield(this.form).then(res => {
              if (res.data.code === "0") {
                this.$message({ type: "success", message: res.data.msg });
                this.$emit('submitSave', res.data.msg)
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
.avue-crud {
  padding-top: 20px;
}
</style>
