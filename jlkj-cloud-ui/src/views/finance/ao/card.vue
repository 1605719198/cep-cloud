<template>
  <div class="app-container">
    <h3>个人信息设置</h3>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="员工" prop="userNo">
          {{form.userNo}}_{{form.name}}
        </el-form-item>
        <el-form-item label="部门" prop="deptId">
          {{form.deptId}}_{{form.deptName}}
        </el-form-item>
        <el-form-item label="银行卡号" prop="cardNo">
          <el-input v-model="form.cardNo" placeholder="请输入银行卡号" class="input"/>
        </el-form-item>
        <el-form-item label="开户银行" prop="janBudget">
          <el-input v-model="form.janBudget" placeholder="请输入开户银行" class="input"/>
        </el-form-item>
        <el-form-item label="联行号" prop="bankNo">
          <el-input v-model="form.bankNo" placeholder="请输入联行号" class="input"/>
        </el-form-item>
        <el-form-item label="开户地区" prop="area">
          <el-input v-model="form.area" placeholder="请输入开户地区" class="input"/>
        </el-form-item>
        <el-form-item label="省份" prop="province">
          <el-input v-model="form.province" placeholder="请输入省份" class="input"/>
        </el-form-item>
        <el-form-item label="城市" prop="city">
          <el-input v-model="form.city" placeholder="请输入城市" class="input"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保存</el-button>
      </div>
  </div>
</template>

<script>
import { addCard } from "@/api/finance/ao/card";

export default {
  name: "Card",
  data() {
    return {
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        cardNo: [
          { required: true, message: "银行卡号不能为空", trigger: "blur" },
          {max: 19  ,trigger: 'blur',message: "银行卡号不能超过19位"}
        ],
        janBudget: [
          { required: true, message: "开户银行不能为空", trigger: "blur" },
        ],
        area: [
          { required: true, message: "开户地区不能为空", trigger: "blur" },
        ],
        province: [
          { required: true, message: "省份不能为空", trigger: "blur" },
        ],
        city: [
          { required: true, message: "城市不能为空", trigger: "blur" },
        ],
      }
    };
  },
  created() {
    this.form.userNo = this.$store.state.user.userInfo.userName
    this.form.name = this.$store.state.user.userInfo.nickName
    this.form.deptId = this.$store.state.user.userInfo.compId
    this.form.deptName = this.$store.state.user.userInfo.dept.deptName
  },
  methods: {
    /** 保存按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addCard(this.form).then(response => {
            this.$modal.msgSuccess("保存成功");
            this.open = false;
          });
        }
      });
    },
  }
};
</script>
<style scoped>
.input {
  width: 20%;
}
</style>
