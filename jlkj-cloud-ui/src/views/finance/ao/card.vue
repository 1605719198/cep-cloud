<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="80px">
    <el-form-item label="银行卡号" prop="cardNo">
      <el-input v-model="form.cardNo" placeholder="请输入银行卡号" />
    </el-form-item>
    <el-form-item label="开户银行" prop="janBudget">
      <el-input v-model="form.janBudget" placeholder="请输入开户银行" />
    </el-form-item>
    <el-form-item label="联行号" prop="bankNo">
      <el-input v-model="form.bankNo" placeholder="请输入联行号" />
    </el-form-item>
    <el-form-item label="开户地区" prop="area">
      <el-input v-model="form.area" placeholder="请输入开户地区" />
    </el-form-item>
    <el-form-item label="省份" prop="province">
      <el-input v-model="form.province" placeholder="请输入省份" />
    </el-form-item>
    <el-form-item label="城市" prop="city">
      <el-input v-model="form.city" placeholder="请输入城市" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submitForm">保存</el-button>
      <el-button type="danger" size="mini" @click="close">关闭</el-button>
    </el-form-item>
  </el-form>

</template>

<script>
import {addCard, queryCard} from "@/api/finance/ao/card";

export default {
  name: "Card",
  data() {
    return {
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        cardNo: [
          {required: true, message: "银行卡号不能为空", trigger: "blur"},
          {max: 19, trigger: 'blur', message: "银行卡号不能超过19位"}
        ],
        janBudget: [
          {required: true, message: "开户银行不能为空", trigger: "blur"},
        ],
        area: [
          {required: true, message: "开户地区不能为空", trigger: "blur"},
        ],
        province: [
          {required: true, message: "省份不能为空", trigger: "blur"},
        ],
        city: [
          {required: true, message: "城市不能为空", trigger: "blur"},
        ],
      }
    };
  },
  created() {
   this.getUserInfo()
  },
  mounted() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo(){
      queryCard(this.$store.state.user.userInfo.userName).then(res => {
        this.form = res.data;
        this.form.name = this.$store.state.user.userInfo.nickName;
        this.form.deptName = this.$store.state.user.userInfo.dept.deptName;
        this.form.userNo = this.$store.state.user.userInfo.userName;
        this.form.deptId = this.$store.state.user.userInfo.compId;
      })
    },
    /** 保存按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          console.log(this.form)
          addCard(this.form).then(response => {
            this.$modal.msgSuccess("保存成功");
          });
        }
      });
    },
    close() {
      this.$tab.closePage();
    }
  }
};
</script>

<style scoped>
.input {
  width: 20%;
}
</style>
